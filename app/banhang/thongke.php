<?php
include "connect.php";

$query = "SELECT sanphammoi.tensp, COUNT(ct.soluong) AS tong FROM `chitietdonhang` ct INNER JOIN sanphammoi ON sanphammoi.id = ct.idsp GROUP BY ct.idsp";
$data = mysqli_query($conn, $query);
$result = array();

if (mysqli_num_rows($data) > 0) {
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
    }

    if (!empty($result)) {
        $arr = [
            "success" => true,
            "message" => "thành công",
            "result" => $result
        ];
    } else {
        $arr = [
            "success" => false,
            "message" => "không thành công",
            "result" => $result
        ];
    }

    print_r(json_encode($arr));
}
?>
