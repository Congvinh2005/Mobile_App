<?php
include 'connect.php';
$id = $_POST['iddonhang'];

$query1 = 'DELETE FROM chitietdonhang WHERE iddonhang = '.$id;
$data1 = mysqli_query($conn, $query1);

$query = 'DELETE FROM donhang WHERE id = '.$id;
$data = mysqli_query($conn, $query);

if ($data == true) {
    $arr = [
        'success' => true,
        'message' => "Thành công"
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Xóa không thành công"
    ];
}

print_r(json_encode($arr));
?>
