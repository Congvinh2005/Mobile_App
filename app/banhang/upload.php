<?php
    include "connect.php";
    $target_dir = "images/";

    // Lấy id cao nhất trong bảng sanphammoi
    $query = "SELECT MAX(id) as id FROM sanphammoi";
    $data = mysqli_query($conn, $query);
    $result = array();

    while ($row = mysqli_fetch_assoc($data)) {
        $result = $row;
    }

    // Xử lý tên file ảnh
    if ($result['id'] == null) {
        $name = 1;
    } else {
        $name = ++$result['id'];
    }

    $name = $name . ".jpg";  
    $target_file_name = $target_dir .$name;

    // Kiểm tra file gửi lên
    if (isset($_FILES['file'])) {
        if (move_uploaded_file($_FILES['file']['tmp_name'], $target_file_name)) {
            $arr = [
                'success' => true,
                'message' => "Thành công",   
                'name' => $name              
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "Không thành công"
            ];
        }
    } else {
        $arr = [
            'success' => false,
            'message' => "Lỗi"
        ];
    }

    echo json_encode($arr);

?>
