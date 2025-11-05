Ứng Dụng Bán Hàng (BanHang) - Điện thoại, laptop, phụ kiện

## Mô Tả Tổng Quan

Đây là một ứng dụng di động Android cho hệ thống bán hàng, được xây dựng bằng ngôn ngữ Java. Ứng dụng cho phép người dùng xem danh sách sản phẩm, loại sản phẩm và tương tác với các tính năng mua sắm thông qua API backend.

## Tính Năng Chính

- Hiển thị banner quảng cáo trượt (ViewFlipper)
- Hiển thị danh sách loại sản phẩm từ API backend
- Kết nối mạng để tải dữ liệu sản phẩm
- Giao diện người dùng hiện đại với Navigation Drawer và Toolbar
- Sử dụng Glide để tải và hiển thị hình ảnh sản phẩm
- Quản lý sản phẩm, đơn hàng, người dùng và thống kê bán hàng

## Kiến Trúc Ứng Dụng

### Cấu Trúc Thư Mục
```
com.example.banhang/
├── activity/                    # Các Activity của ứng dụng
│   ├── ChiTietActivity.java
│   ├── DangKiActivity.java
│   ├── DangNhapActivity.java
│   ├── DienThoaiActivity.java
│   ├── DongHoActivity.java
│   ├── GioHangActivity.java
│   ├── LaptopActivity.java
│   ├── LienHeActivity.java
│   ├── MainActivity.java
│   ├── QuanLiActivity.java
│   ├── QuanLiTkActivity.java
│   ├── ResetPassActivity.java
│   ├── SearchActivity.java
│   ├── SearchTkActivity.java
│   ├── ThanhToanActivity.java
│   ├── ThemSPActivity.java
│   ├── ThemTkActivity.java
│   ├── ThongKeActivity.java
│   ├── ThongTinActivity.java
│   └── XemDonActivity.java
├── adapter/                     # Các Adapter cho RecyclerView/ListView
│   ├── ChiTietAdapter.java
│   ├── DienThoaiAdapter.java
│   ├── DonHangAdapter.java
│   ├── GioHangAdapter.java
│   ├── LoaiSpAdapter.java
│   ├── SanPhamMoiAdapter.java
│   └── UserAdapter.java
├── Interface/                   # Các interface
│   ├── IImageClickListenner.java
│   ├── ItemClickDeleteListener.java
│   └── ItemClickListener.java
├── model/                       # Các lớp mô hình dữ liệu
│   ├── DonHang.java
│   ├── DonHangModel.java
│   ├── GioHang.java
│   ├── Item.java
│   ├── LoaiSp.java
│   ├── LoaiSpModel.java
│   ├── MessageModel.java
│   ├── SanPhamMoi.java
│   ├── SanPhamMoiModel.java
│   ├── ThongKe.java
│   ├── ThongKeModel.java
│   ├── User.java
│   ├── UserModel.java
│   └── EventBus/                # Các lớp sự kiện
│       ├── DonHangEvent.java
│       ├── SuaXoaEvent.java
│       └── TinhTongEvent.java
├── retrofit/                    # Các lớp cho kết nối API
│   ├── ApiBanHang.java
│   └── RetrofitClient.java
├── Service/                     # Các dịch vụ nền
│   └── FirebaseMessagerReceiver.java
└── utils/                       # Các tiện ích chung
    ├── MomoPaymentHelper.java
    └── Utils.java
```

### Mô Tả Các Thành Phần

#### 1. Activity
- **MainActivity.java**: Là activity chính của ứng dụng, chứa:
  - ViewFlipper để hiển thị banner quảng cáo
  - ListView để hiển thị danh sách loại sản phẩm
  - Navigation Drawer cho menu điều hướng
  - Toolbar làm thanh công cụ
  - Kiểm tra kết nối mạng khi khởi động

#### 2. Adapter
- **LoaiSpAdapter.java**: Adapter để hiển thị danh sách loại sản phẩm trong ListView, sử dụng mẫu ViewHolder để tối ưu hiệu suất

#### 3. Model (Mô hình dữ liệu)
- **LoaiSp.java**: Mô hình đại diện cho một loại sản phẩm với các thuộc tính:
  - `id`: Mã định danh
  - `tensanpham`: Tên sản phẩm
  - `hinhanh`: Đường dẫn hình ảnh
- **LoaiSpModel.java**: Mô hình phản hồi từ API với các thuộc tính:
  - `success`: Trạng thái thành công/thất bại
  - `message`: Thông báo
  - `result`: Danh sách sản phẩm loại

#### 4. Retrofit (Kết nối API)
- **ApiBanHang.java**: Giao diện API định nghĩa các phương thức gọi API, hiện tại có:
  - `getLoaiSp()`: Lấy danh sách loại sản phẩm
- **RetrofitClient.java**: Lớp hỗ trợ tạo và quản lý instance của Retrofit với Gson và RxJava3

#### 5. Utils (Tiện ích)
- **Utils.java**: Chứa hằng số chung, đặc biệt là URL cơ sở của API:
  - `BASE_URL = "http://192.168.65.103/banhang/"`

## Các Thư Viện Chính

- **Retrofit 2**: Thư viện để thực hiện các yêu cầu mạng REST API
- **Gson**: Chuyển đổi JSON sang Java Objects và ngược lại
- **Glide**: Thư viện tải và cache hình ảnh
- **RxJava3**: Xử lý lập trình phản ứng và bất đồng bộ
- **Material Design**: Thư viện giao diện người dùng của Google
- **AndroidX**: Thư viện hỗ trợ ngược cho Android

## Cấu Hình Gradle
- API Target: 36
- Minimum SDK: 24
- Java Version: 11

## Cấu Hình Mạng
Ứng dụng yêu cầu các quyền sau:

- **INTERNET**: Để kết nối mạng và tải dữ liệu từ API
- **ACCESS_NETWORK_STATE**: Để kiểm tra trạng thái kết nối mạng

## Cách Cài Đặt và Chạy

### Điều Kiện Tiên Quyết
- Android Studio (phiên bản mới nhất được khuyến nghị)
- SDK Android API 24 trở lên
- Máy chủ backend API đang chạy tại http://192.168.65.103/banhang/ tuy theo ip mang cua may chay localhost

### Các Bước Cài Đặt
1. Clone hoặc tải về dự án
2. Mở dự án trong Android Studio
3. Đảm bảo máy chủ backend API đang chạy
4. Thay đổi URL trong Utils.java nếu cần thiết để phù hợp với địa chỉ máy chủ của bạn
5. Build và chạy dự án

## Cấu Hình Backend
Ứng dụng hiện đang gọi endpoint:

- GET /getloaisp.php để lấy danh sách loại sản phẩm

## Tính Năng Ứng Dụng

Ứng dụng bao gồm các tính năng chính sau:

### 1. Quản Lý Người Dùng
- Đăng ký tài khoản mới
- Đăng nhập hệ thống
- Quên mật khẩu và đặt lại mật khẩu
- Quản lý thông tin cá nhân

### 2. Quản Lý Sản Phẩm
- Hiển thị danh sách sản phẩm theo danh mục (Điện thoại, Laptop, Đồng hồ)
- Thêm, sửa, xóa sản phẩm
- Tìm kiếm sản phẩm
- Xem chi tiết sản phẩm

### 3. Quản Lý Đơn Hàng
- Quản lý giỏ hàng
- Đặt hàng và thanh toán
- Xem lịch sử đơn hàng
- Cập nhật trạng thái đơn hàng

### 4. Quản Lý Người Dùng (Admin)
- Thêm, sửa, xóa tài khoản người dùng
- Tìm kiếm tài khoản
- Phân quyền người dùng

### 5. Thống Kê & Báo Cáo
- Thống kê doanh thu
- Thống kê sản phẩm bán chạy
- Báo cáo theo thời gian thực

### 6. Tính Năng Khác
- Gọi điện trực tiếp (Liên hệ)
- Tích hợp Firebase cho thông báo
- Upload hình ảnh sản phẩm

## Các Màn Hình Chính

### 1. Màn Hình Đăng Nhập (`DangNhapActivity.java`)
- Giao diện đăng nhập cho người dùng
- Chuyển sang màn hình đăng ký nếu chưa có tài khoản
- Quên mật khẩu

### 2. Màn Hình Đăng Ký (`DangKiActivity.java`)
- Giao diện đăng ký tài khoản mới
- Nhập thông tin cá nhân và mật khẩu

### 3. Màn Hình Chính (`MainActivity.java`)
- Giao diện chính của ứng dụng
- Menu điều hướng đến các chức năng
- Hiển thị sản phẩm nổi bật

### 4. Màn Hình Danh Mục Sản Phẩm
- **`DienThoaiActivity.java`**: Hiển thị danh sách điện thoại
- **`LaptopActivity.java`**: Hiển thị danh sách laptop
- **`DongHoActivity.java`**: Hiển thị danh sách đồng hồ

### 5. Màn Hình Chi Tiết Sản Phẩm (`ChiTietActivity.java`)
- Hiển thị thông tin chi tiết sản phẩm
- Nút thêm vào giỏ hàng
- Mô tả sản phẩm

### 6. Màn Hình Giỏ Hàng (`GioHangActivity.java`)
- Hiển thị các sản phẩm trong giỏ hàng
- Sửa số lượng, xóa sản phẩm
- Tính tổng tiền

### 7. Màn Hình Thanh Toán (`ThanhToanActivity.java`)
- Nhập thông tin giao hàng
- Xác nhận đơn hàng
- Tiến hành thanh toán

### 8. Màn Hình Quản Lý Sản Phẩm (`QuanLiActivity.java`)
- Hiển thị danh sách sản phẩm để quản lý
- Thêm, sửa, xóa sản phẩm

### 9. Màn Hình Thêm Sản Phẩm (`ThemSPActivity.java`)
- Giao diện thêm sản phẩm mới
- Upload hình ảnh, nhập thông tin sản phẩm

### 10. Màn Hình Quản Lý Tài Khoản (`QuanLiTkActivity.java`)
- Quản lý các tài khoản người dùng
- Tìm kiếm tài khoản

### 11. Màn Hình Thêm Tài Khoản (`ThemTkActivity.java`)
- Giao diện thêm tài khoản người dùng mới

### 12. Màn Hình Xem Đơn Hàng (`XemDonActivity.java`)
- Hiển thị lịch sử đơn hàng
- Xem chi tiết từng đơn hàng

### 13. Màn Hình Thống Kê (`ThongKeActivity.java`)
- Hiển thị báo cáo thống kê bán hàng
- Biểu đồ doanh thu, sản phẩm bán chạy

### 14. Màn Hình Tìm Kiếm
- **`SearchActivity.java`**: Tìm kiếm sản phẩm
- **`SearchTkActivity.java`**: Tìm kiếm tài khoản

### 15. Màn Hình Liên Hệ (`LienHeActivity.java`)
- Thông tin liên hệ
- Nút gọi điện trực tiếp

### 16. Màn Hình Thông Tin Cá Nhân (`ThongTinActivity.java`)
- Xem và cập nhật thông tin cá nhân

### 17. Màn Hình Đặt Lại Mật Khẩu (`ResetPassActivity.java`)
- Giao diện đặt lại mật khẩu

## API Bán Hàng (PHP)

Thư mục `app/banhang/` chứa các API phía server được viết bằng PHP, cung cấp các chức năng chính cho hệ thống bán hàng:

### Các API Chính

#### 1. Đăng Nhập & Đăng Ký
- **`dangnhap.php`**: Xử lý đăng nhập người dùng
  - Phương thức: POST
  - Tham số: `email`, `pass`
  - Trả về: Thông tin người dùng nếu đăng nhập thành công

- **`dangki.php`**: Xử lý đăng ký người dùng mới
  - Phương thức: POST
  - Tham số: Thông tin người dùng

#### 2. Quản Lý Sản Phẩm
- **`getspmoi.php`**: Lấy danh sách sản phẩm mới
- **`insertsp.php`**: Thêm sản phẩm mới
- **`updatesp.php`**: Cập nhật thông tin sản phẩm
- **`xoa.php`**: Xóa sản phẩm
- **`timkiem.php`**: Tìm kiếm sản phẩm

#### 3. Quản Lý Người Dùng
- **`inserttk.php`**: Thêm tài khoản người dùng
- **`updatetk.php`**: Cập nhật thông tin tài khoản
- **`xoatk.php`**: Xóa tài khoản
- **`gettk.php`**: Lấy thông tin tài khoản
- **`timkiemtk.php`**: Tìm kiếm tài khoản

#### 4. Đơn Hàng
- **`donhang.php`**: Tạo đơn hàng mới
  - Phương thức: POST
  - Tham số: `sdt`, `email`, `tongtien`, `iduser`, `diachi`, `soluong`, `chitiet`
  - Trả về: Kết quả tạo đơn hàng
- **`xemdonhang.php`**: Xem danh sách đơn hàng
- **`updateorder.php`**: Cập nhật trạng thái đơn hàng
- **`deleteorder.php`**: Xóa đơn hàng

#### 5. Chi Tiết Đơn Hàng
- **`chitiet.php`**: Xử lý chi tiết đơn hàng

#### 6. Thống Kê
- **`thongke.php`**: Cung cấp dữ liệu thống kê bán hàng
  - Trả về: Sản phẩm bán chạy, doanh thu, v.v.

#### 7. Email & Xác Thực
- **`reset.php`**: Gửi yêu cầu đặt lại mật khẩu
- **`reset_pass.php`**: Cập nhật mật khẩu mới
- **`updatetoken.php`**: Cập nhật token thiết bị
- **`upload.php`**: Upload hình ảnh sản phẩm

#### 8. Danh Mục
- **`getloaisp.php`**: Lấy danh sách loại sản phẩm

### Cấu Trúc Kết Nối Cơ Sở Dữ Liệu

- **`connect.php`**: Cấu hình kết nối đến cơ sở dữ liệu MySQL
  - Host: localhost
  - Username: root
  - Password: (rỗng)
  - Database: daotaoline

## Cài Đặt

### Đối với API PHP

1. Đảm bảo máy chủ hỗ trợ PHP và MySQL
2. Cài đặt MySQL và tạo cơ sở dữ liệu với tên `daotaoline`
3. Cập nhật thông tin kết nối trong file `connect.php` nếu cần
4. Đặt thư mục `banhang/` vào thư mục web (htdocs nếu dùng XAMPP)

### Đối với Ứng dụng Android

Dự án sử dụng Gradle làm công cụ build:
- Sử dụng Android Studio để mở và build dự án
- Chạy lệnh `./gradlew build` để build ứng dụng

## Cấu Hình Cơ Sở Dữ Liệu

File `connect.php` chứa thông tin cấu hình kết nối cơ sở dữ liệu:

```php
$host = "localhost";
$user = "root";
$pass = "";
$database = "daotaoline";
```

Cập nhật các giá trị này theo cấu hình máy chủ của bạn nếu cần.

## Các Thư Viện Được Sử Dụng

- **PHPMailer**: Cho chức năng gửi email (trong thư mục `PHPMailer/`)

## API Response Format

Các API trả về dữ liệu theo định dạng JSON với cấu trúc:

```json
{
  "success": true/false,
  "message": "Thông báo",
  "result": Dữ liệu trả về (nếu có)
}
```

## Bảo Mật

Lưu ý rằng mã nguồn hiện tại sử dụng các truy vấn SQL trực tiếp có thể dễ bị tấn công SQL Injection. Cân nhắc sử dụng prepared statements trong môi trường sản xuất.

## Lưu Ý Phát Triển
- Ứng dụng hiện chỉ có MainActivity, cần phát triển thêm các chức năng như chi tiết sản phẩm, giỏ hàng, đăng nhập, v.v.
- Địa chỉ API hiện được đặt cứng trong Utils.java, nên xem xét sử dụng BuildConfig cho các môi trường khác nhau
- Một số phần code bị comment (getSpMoi), có thể được phát triển trong tương lai

## Cấu Trúc API Đang Sử Dụng
- Dữ liệu trả về theo định dạng JSON
- Định dạng phản hồi: { "success": true/false, "message": "string", "result": [...] }
- Sử dụng RxJava3 để xử lý bất đồng bộ

## Tương Lai Phát Triển
- Thêm chức năng giỏ hàng
- Thêm trang chi tiết sản phẩm
- Thêm tính năng đăng nhập/người dùng
- Thêm tìm kiếm và lọc sản phẩm
- Cải thiện trải nghiệm người dùng

## Giấy Phép
Dự án này không có thông tin giấy phép cụ thể.

## Tính Năng Ứng Dụng

Ứng dụng bao gồm các tính năng chính sau:

### 1. Quản Lý Người Dùng
- Đăng ký tài khoản mới
- Đăng nhập hệ thống
- Quên mật khẩu và đặt lại mật khẩu
- Quản lý thông tin cá nhân

### 2. Quản Lý Sản Phẩm
- Hiển thị danh sách sản phẩm theo danh mục (Điện thoại, Laptop, Đồng hồ)
- Thêm, sửa, xóa sản phẩm
- Tìm kiếm sản phẩm
- Xem chi tiết sản phẩm

### 3. Quản Lý Đơn Hàng
- Quản lý giỏ hàng
- Đặt hàng và thanh toán
- Xem lịch sử đơn hàng
- Cập nhật trạng thái đơn hàng

### 4. Quản Lý Người Dùng (Admin)
- Thêm, sửa, xóa tài khoản người dùng
- Tìm kiếm tài khoản
- Phân quyền người dùng

### 5. Thống Kê & Báo Cáo
- Thống kê doanh thu
- Thống kê sản phẩm bán chạy
- Báo cáo theo thời gian thực

### 6. Tính Năng Khác
- Gọi điện trực tiếp (Liên hệ)
- Tích hợp Firebase cho thông báo
- Upload hình ảnh sản phẩm

## Các Màn Hình Chính

### 1. Màn Hình Đăng Nhập (`DangNhapActivity.java`)
- Giao diện đăng nhập cho người dùng
- Chuyển sang màn hình đăng ký nếu chưa có tài khoản
- Quên mật khẩu

### 2. Màn Hình Đăng Ký (`DangKiActivity.java`)
- Giao diện đăng ký tài khoản mới
- Nhập thông tin cá nhân và mật khẩu

### 3. Màn Hình Chính (`MainActivity.java`)
- Giao diện chính của ứng dụng
- Menu điều hướng đến các chức năng
- Hiển thị sản phẩm nổi bật

### 4. Màn Hình Danh Mục Sản Phẩm
- **`DienThoaiActivity.java`**: Hiển thị danh sách điện thoại
- **`LaptopActivity.java`**: Hiển thị danh sách laptop
- **`DongHoActivity.java`**: Hiển thị danh sách đồng hồ

### 5. Màn Hình Chi Tiết Sản Phẩm (`ChiTietActivity.java`)
- Hiển thị thông tin chi tiết sản phẩm
- Nút thêm vào giỏ hàng
- Mô tả sản phẩm

### 6. Màn Hình Giỏ Hàng (`GioHangActivity.java`)
- Hiển thị các sản phẩm trong giỏ hàng
- Sửa số lượng, xóa sản phẩm
- Tính tổng tiền

### 7. Màn Hình Thanh Toán (`ThanhToanActivity.java`)
- Nhập thông tin giao hàng
- Xác nhận đơn hàng
- Tiến hành thanh toán

### 8. Màn Hình Quản Lý Sản Phẩm (`QuanLiActivity.java`)
- Hiển thị danh sách sản phẩm để quản lý
- Thêm, sửa, xóa sản phẩm

### 9. Màn Hình Thêm Sản Phẩm (`ThemSPActivity.java`)
- Giao diện thêm sản phẩm mới
- Upload hình ảnh, nhập thông tin sản phẩm

### 10. Màn Hình Quản Lý Tài Khoản (`QuanLiTkActivity.java`)
- Quản lý các tài khoản người dùng
- Tìm kiếm tài khoản

### 11. Màn Hình Thêm Tài Khoản (`ThemTkActivity.java`)
- Giao diện thêm tài khoản người dùng mới

### 12. Màn Hình Xem Đơn Hàng (`XemDonActivity.java`)
- Hiển thị lịch sử đơn hàng
- Xem chi tiết từng đơn hàng

### 13. Màn Hình Thống Kê (`ThongKeActivity.java`)
- Hiển thị báo cáo thống kê bán hàng
- Biểu đồ doanh thu, sản phẩm bán chạy

### 14. Màn Hình Tìm Kiếm
- **`SearchActivity.java`**: Tìm kiếm sản phẩm
- **`SearchTkActivity.java`**: Tìm kiếm tài khoản

### 15. Màn Hình Liên Hệ (`LienHeActivity.java`)
- Thông tin liên hệ
- Nút gọi điện trực tiếp

### 16. Màn Hình Thông Tin Cá Nhân (`ThongTinActivity.java`)
- Xem và cập nhật thông tin cá nhân

### 17. Màn Hình Đặt Lại Mật Khẩu (`ResetPassActivity.java`)
- Giao diện đặt lại mật khẩu

## API Bán Hàng (PHP)

Thư mục `app/banhang/` chứa các API phía server được viết bằng PHP, cung cấp các chức năng chính cho hệ thống bán hàng:

### Các API Chính

#### 1. Đăng Nhập & Đăng Ký
- **`dangnhap.php`**: Xử lý đăng nhập người dùng
  - Phương thức: POST
  - Tham số: `email`, `pass`
  - Trả về: Thông tin người dùng nếu đăng nhập thành công

- **`dangki.php`**: Xử lý đăng ký người dùng mới
  - Phương thức: POST
  - Tham số: Thông tin người dùng

#### 2. Quản Lý Sản Phẩm
- **`getspmoi.php`**: Lấy danh sách sản phẩm mới
- **`insertsp.php`**: Thêm sản phẩm mới
- **`updatesp.php`**: Cập nhật thông tin sản phẩm
- **`xoa.php`**: Xóa sản phẩm
- **`timkiem.php`**: Tìm kiếm sản phẩm

#### 3. Quản Lý Người Dùng
- **`inserttk.php`**: Thêm tài khoản người dùng
- **`updatetk.php`**: Cập nhật thông tin tài khoản
- **`xoatk.php`**: Xóa tài khoản
- **`gettk.php`**: Lấy thông tin tài khoản
- **`timkiemtk.php`**: Tìm kiếm tài khoản

#### 4. Đơn Hàng
- **`donhang.php`**: Tạo đơn hàng mới
  - Phương thức: POST
  - Tham số: `sdt`, `email`, `tongtien`, `iduser`, `diachi`, `soluong`, `chitiet`
  - Trả về: Kết quả tạo đơn hàng
- **`xemdonhang.php`**: Xem danh sách đơn hàng
- **`updateorder.php`**: Cập nhật trạng thái đơn hàng
- **`deleteorder.php`**: Xóa đơn hàng

#### 5. Chi Tiết Đơn Hàng
- **`chitiet.php`**: Xử lý chi tiết đơn hàng

#### 6. Thống Kê
- **`thongke.php`**: Cung cấp dữ liệu thống kê bán hàng
  - Trả về: Sản phẩm bán chạy, doanh thu, v.v.

#### 7. Email & Xác Thực
- **`reset.php`**: Gửi yêu cầu đặt lại mật khẩu
- **`reset_pass.php`**: Cập nhật mật khẩu mới
- **`updatetoken.php`**: Cập nhật token thiết bị
- **`upload.php`**: Upload hình ảnh sản phẩm

#### 8. Danh Mục
- **`getloaisp.php`**: Lấy danh sách loại sản phẩm

### Cấu Trúc Kết Nối Cơ Sở Dữ Liệu

- **`connect.php`**: Cấu hình kết nối đến cơ sở dữ liệu MySQL
  - Host: localhost
  - Username: root
  - Password: (rỗng)
  - Database: daotaoline

## Cài Đặt

### Đối với API PHP

1. Đảm bảo máy chủ hỗ trợ PHP và MySQL
2. Cài đặt MySQL và tạo cơ sở dữ liệu với tên `daotaoline`
3. Cập nhật thông tin kết nối trong file `connect.php` nếu cần
4. Đặt thư mục `banhang/` vào thư mục web (htdocs nếu dùng XAMPP)

### Đối với Ứng dụng Android

Dự án sử dụng Gradle làm công cụ build:
- Sử dụng Android Studio để mở và build dự án
- Chạy lệnh `./gradlew build` để build ứng dụng

## Cấu Hình Cơ Sở Dữ Liệu

File `connect.php` chứa thông tin cấu hình kết nối cơ sở dữ liệu:

```php
$host = "localhost";
$user = "root";
$pass = "";
$database = "daotaoline";
```

Cập nhật các giá trị này theo cấu hình máy chủ của bạn nếu cần.

## Các Thư Viện Được Sử Dụng

- **PHPMailer**: Cho chức năng gửi email (trong thư mục `PHPMailer/`)

## API Response Format

Các API trả về dữ liệu theo định dạng JSON với cấu trúc:

```json
{
  "success": true/false,
  "message": "Thông báo",
  "result": Dữ liệu trả về (nếu có)
}
```

## Bảo Mật

Lưu ý rằng mã nguồn hiện tại sử dụng các truy vấn SQL trực tiếp có thể dễ bị tấn công SQL Injection. Cân nhắc sử dụng prepared statements trong môi trường sản xuất.