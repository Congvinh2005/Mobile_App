<?php
include 'connect.php';
$email = $_POST['email'];
$pass = $_POST['pass'];

$query = "SELECT * FROM `user` WHERE `email` = '".$email."' AND `pass` = '".$pass."'";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
}else{
    $arr = [
        'success' => false,
        'message' => "Email hoặc mật khẩu không chính xác",
        'result' => $result
    ];
}
echo json_encode($arr);


?>
