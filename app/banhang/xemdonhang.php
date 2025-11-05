<?php
include 'connect.php';
$iduser = $_POST['iduser'];

// Lấy role từ bảng user
$truyvan_role = "SELECT role FROM user WHERE id = ".$iduser;
$data_role = mysqli_query($conn, $truyvan_role);
$row_role = mysqli_fetch_assoc($data_role);
$role = $row_role ? $row_role['role'] : 1;

if ($role == 2) {
    // Nếu là admin (role = 0) => lấy tất cả đơn hàng
    $query = "SELECT donhang.id, donhang.diachi, donhang.sodienthoai, donhang.email, donhang.soluong, donhang.tongtien, donhang.trangthai, user.username from donhang join user on donhang.iduser = user.id ORDER by donhang.id desc";
} else {
    // Nếu là user => chỉ lấy đơn hàng của người đós
    $query = "SELECT * FROM `donhang` WHERE `iduser` = ".$iduser." ORDER BY id DESC";
}

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $truyvan = "SELECT * FROM `chitietdonhang` 
                INNER JOIN sanphammoi ON chitietdonhang.idsp = sanphammoi.id 
                WHERE chitietdonhang.iddonhang = ".$row['id'];
    $data1 = mysqli_query($conn, $truyvan);
    $item = array();
    while ($row1 = mysqli_fetch_assoc($data1)) {
        $item[] = $row1;
    }
    $row['item'] = $item;
    $result[] = ($row);
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
        'result' => $result
    ];
}

print_r(json_encode($arr));
?>
