<?php
include "connect.php";

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/PHPMailer/src/Exception.php';
require 'PHPMailer/PHPMailer/src/PHPMailer.php';
require 'PHPMailer/PHPMailer/src/SMTP.php';

// Đảm bảo request là POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(['success' => false, 'message' => 'Phải gửi bằng phương thức POST']);
    exit;
}

// Lấy email từ POST
$email = trim($_POST['email'] ?? '');
if (empty($email) || !filter_var($email, FILTER_VALIDATE_EMAIL)) {
    echo json_encode(['success' => false, 'message' => 'Email không hợp lệ']);
    exit;
}

// Kiểm tra user có tồn tại
$sql = "SELECT id FROM user WHERE email = ? LIMIT 1";
$stmt = mysqli_prepare($conn, $sql);
mysqli_stmt_bind_param($stmt, "s", $email);
mysqli_stmt_execute($stmt);
$res = mysqli_stmt_get_result($stmt);
if (mysqli_num_rows($res) == 0) {
    echo json_encode(['success' => false, 'message' => 'Email không tồn tại trong hệ thống']);
    exit;
}

// Tạo token + thời gian hết hạn (1 giờ)
$token = bin2hex(random_bytes(16)); // 32 ký tự hexgettk.phpgettkgettk
$expires = date("Y-m-d H:i:s", strtotime("+1 hour"));

// Lưu token vào DB
$sql2 = "UPDATE user SET reset_token = ?, reset_expires = ? WHERE email = ?";
$stmt2 = mysqli_prepare($conn, $sql2);
mysqli_stmt_bind_param($stmt2, "sss", $token, $expires, $email);
mysqli_stmt_execute($stmt2);

// Tạo link reset
$reset_link = "http://192.168.35.101/banhang/reset_pass.php?token=".$token;

// Gửi email
$mail = new PHPMailer(true);

try {
    $mail->CharSet = 'utf-8';
    $mail->isSMTP();
    $mail->SMTPAuth = true;
    $mail->Host = 'smtp.gmail.com';
    $mail->Username = 'doquangah@gmail.com';      // Gmail gửi đi
    $mail->Password = 'ozspqfxsmphsxzik';         // App Password
    $mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;
    $mail->Port = 465;

    $mail->setFrom('doquangah@gmail.com', 'App Bán Hàng');
    $mail->addAddress($email);
    $mail->isHTML(true);
    $mail->Subject = 'Yêu cầu đặt lại mật khẩu';
    $mail->Body = "
        <p>Xin chào,</p>
        <p>Bạn (hoặc ai đó) đã yêu cầu đặt lại mật khẩu. Nhấn vào liên kết bên dưới để đổi mật khẩu (hết hạn sau 1 giờ):</p>
        <p><a href='{$reset_link}'>Đặt lại mật khẩu</a></p>
        <p>Nếu bạn không yêu cầu, hãy bỏ qua email này.</p>
    ";

    $mail->send();
    echo json_encode(['success' => true, 'message' => 'Email đặt lại mật khẩu đã được gửi']);
} catch (Exception $e) {
    echo json_encode(['success' => false, 'message' => 'Lỗi gửi email: '.$mail->ErrorInfo]);
}
