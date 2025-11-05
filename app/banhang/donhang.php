<?php
include "connect.php";

$sdt      = isset($_POST['sdt']) ? $_POST['sdt'] : '';
$email    = isset($_POST['email']) ? $_POST['email'] : '';
$tongtien = isset($_POST['tongtien']) ? $_POST['tongtien'] : 0;
$iduser   = isset($_POST['iduser']) ? $_POST['iduser'] : 0;
$diachi   = isset($_POST['diachi']) ? $_POST['diachi'] : '';
$soluong  = isset($_POST['soluong']) ? $_POST['soluong'] : 0;
$chitiet  = isset($_POST['chitiet']) ? $_POST['chitiet'] : null;

// Insert đơn hàng trước
$query = "INSERT INTO `donhang`(`iduser`, `diachi`, `sodienthoai`, `email`, `soluong`, `tongtien`) 
          VALUES ('$iduser', '$diachi', '$sdt', '$email', '$soluong', '$tongtien')";

$data = mysqli_query($conn, $query);

if ($data == true) {
    // Lấy id đơn hàng mới nhất
    $iddonhang = mysqli_insert_id($conn);

    // Nếu có chi tiết sản phẩm thì insert tiếp
    if (!empty($iddonhang) && $chitiet != null) {
        $chitiet = json_decode($chitiet, true);
        if (is_array($chitiet)) {
            foreach ($chitiet as $value) {
                $idsp    = $value["idsp"];
                $sl      = $value["soluong"];
                $gia     = $value["giasp"];
                $truyvan = "INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluong`, `gia`) 
                            VALUES ('$iddonhang', '$idsp', '$sl', '$gia')";
                mysqli_query($conn, $truyvan);
            }
        }
    }

    $arr = [
        'success' => true,
        'message' => "thanh cong"
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "khong thanh cong"
    ];
}

print_r(json_encode($arr));
?>
