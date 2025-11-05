<?php
include "connect.php";

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $token = $_GET['token'] ?? '';
    if (empty($token)) { echo "Token không hợp lệ"; exit; }

    // kiểm tra token
    $sql = "SELECT id, reset_expires FROM user WHERE reset_token = ? LIMIT 1";
    $stmt = mysqli_prepare($conn, $sql);
    mysqli_stmt_bind_param($stmt, "s", $token);
    mysqli_stmt_execute($stmt);
    $res = mysqli_stmt_get_result($stmt);
    if (mysqli_num_rows($res) == 0) { echo "Token không đúng"; exit; }

    $row = mysqli_fetch_assoc($res);
    if (strtotime($row['reset_expires']) < time()) { echo "Token đã hết hạn"; exit; }

    // Hiển thị form đặt mật khẩu mới (nếu web)
    echo '
      <form method="POST">
        <input type="hidden" name="token" value="'.htmlspecialchars($token).'">
        <input type="password" name="newpass" placeholder="Mật khẩu mới">
        <input type="password" name="newpass2" placeholder="Nhập lại mật khẩu">
        <button type="submit">Đổi mật khẩu</button>
      </form>
    ';
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $token = $_POST['token'] ?? '';
    $newpass = $_POST['newpass'] ?? '';
    $newpass2 = $_POST['newpass2'] ?? '';

    if ($newpass !== $newpass2 || strlen($newpass) < 4) {
        echo "Mật khẩu không khớp hoặc quá ngắn";
        exit;
    }

    // kiểm tra token lần nữa
    $sql = "SELECT id, reset_expires FROM user WHERE reset_token = ? LIMIT 1";
    $stmt = mysqli_prepare($conn, $sql);
    mysqli_stmt_bind_param($stmt, "s", $token);
    mysqli_stmt_execute($stmt);
    $res = mysqli_stmt_get_result($stmt);
    if (mysqli_num_rows($res) == 0) { echo "Token không hợp lệ"; exit; }

    $row = mysqli_fetch_assoc($res);
    if (strtotime($row['reset_expires']) < time()) { echo "Token đã hết hạn"; exit; }

    $userId = $row['id'];

    // $hash = password_hash($newpass, PASSWORD_DEFAULT);

    // Tạm thời comment dòng hash để lưu trực tiếp mật khẩu thô (CHỈ DÙNG CHO TEST)
    // và thay đổi bind_param bên dưới để lưu $newpass thay vì $hash

    // Cập nhật mật khẩu (TẠM THỜI LƯU plaintext) và xóa token
    $sql2 = "UPDATE user SET pass = ?, reset_token = NULL, reset_expires = NULL WHERE id = ?";
    $stmt2 = mysqli_prepare($conn, $sql2);

    // Thay vì truyền $hash, truyền $newpass để lưu mật khẩu thật (CHỈ TEST)
    mysqli_stmt_bind_param($stmt2, "si", $newpass, $userId);
    // mysqli_stmt_bind_param($stmt2, "si", $hash, $userId);
    mysqli_stmt_execute($stmt2);

    // Hiển thị mật khẩu mới ra màn hình (CHỈ DÙNG TRONG MÔI TRƯỜNG TEST)
    echo "Đổi mật khẩu thành công<br>";
    

    echo "Mật khẩu mới (plain text): " . htmlspecialchars($newpass);//kh hiện pass thì//
    // ----- KẾT THÚC PHẦN TEST -----

    /* 
    ------- HƯỚNG DẪN QUAY LẠI SAU KHI TEST XONG: -------
    1) Bỏ comment dòng hash:
       $hash = password_hash($newpass, PASSWORD_DEFAULT);
    3) Xóa hoặc comment các echo hiện mật khẩu ở trên.
    */
}
