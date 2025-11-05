<?php
include "connect.php";

$email = $_POST['email'];
$pass = $_POST['pass'];
$username = $_POST['username'];
$mobile = $_POST['mobile'];
$role = $_POST['role'];

$query = "INSERT INTO user (email, pass, username, mobile, role) VALUES ('$email', '$pass', '$username', '$mobile', '$role')";
$data = mysqli_query($conn, $query);

if ($data == true) {
    $arr = [
        'success' => true,
        'message' => "Thành công"
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công"
    ];
}

print_r(json_encode($arr));
?>
