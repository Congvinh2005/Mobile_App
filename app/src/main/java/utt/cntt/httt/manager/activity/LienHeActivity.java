package utt.cntt.httt.manager.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.paperdb.Paper;
import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.model.User;
import utt.cntt.httt.manager.utils.Utils;

public class LienHeActivity extends AppCompatActivity {

    // 🔹 Khai báo biến giao diện
    private TextView tvAdminName;
    private TextView tvAdminEmail;
    private TextView tvAdminPhone;
    private Button btnContactAdmin;
    private Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lien_he);

        //  Khởi tạo PaperDB
        Paper.init(this);

        //  Ánh xạ view
        initViews();

        //  Lấy thông tin user hiện tại
        User user = getCurrentUser();

        //  Hiển thị dữ liệu user
        mapUserToViews(user);
    }

    /**
     * Ánh xạ các View từ layout XML sang biến Java
     */
    private void initViews() {
        tvAdminName = findViewById(R.id.tvAdminName);
        tvAdminEmail = findViewById(R.id.tvAdminEmail);
        tvAdminPhone = findViewById(R.id.tvAdminPhone);
        btnContactAdmin = findViewById(R.id.btnContactAdmin);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Thiết lập sự kiện click cho nút liên hệ
        btnContactAdmin.setOnClickListener(v -> {
            // Lấy số điện thoại từ thông tin người dùng
            User user = getCurrentUser();
            if (user != null && user.getMobile() != null && !user.getMobile().isEmpty()) {
                makePhoneCall(user.getMobile());
            } else {
                Toast.makeText(this, "Không có số điện thoại để gọi", Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập sự kiện click cho nút quay lại màn hình chính
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng activity hiện tại để không quay lại bằng nút back
        });
    }

    /**
     * Lấy thông tin người dùng hiện tại (ưu tiên Utils, fallback Paper)
     */
    private User getCurrentUser() {
        if (Utils.user_current != null) {
            return Utils.user_current;
        }

        return Paper.book().read("user");
    }

    /**
     * Gán dữ liệu người dùng vào các TextView
     */
    private void mapUserToViews(User user) {
        if (user == null) {
            // Nếu chưa có dữ liệu user → hiển thị mặc định
            tvAdminName.setText("Tên Quản Trị");
            tvAdminEmail.setText("Email: admin@example.com");
            tvAdminPhone.setText("Điện thoại: +84 123 456 789");
            return;
        }

        tvAdminName.setText(
                user.getUsername() != null ? user.getUsername() : "Tên Quản Trị"
        );
        tvAdminEmail.setText(
                "Email: " + (user.getEmail() != null ? user.getEmail() : "admin@example.com")
        );
        tvAdminPhone.setText(
                "Điện thoại: " + (user.getMobile() != null ? user.getMobile() : "+84 123 456 789")
        );
    }

    /**
     * Hàm thực hiện gọi điện thoại
     */
    private void makePhoneCall(String phoneNumber) {
        // Kiểm tra quyền gọi điện thoại
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa có quyền, yêu cầu quyền
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            // Nếu đã có quyền, thực hiện cuộc gọi
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
    }
}
//import utt.cntt.httt.manager.R;
//import utt.cntt.httt.manager.model.User;
//import utt.cntt.httt.manager.utils.Utils;
//
//public class LienHeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_lien_he);
//
//        Paper.init(this);
//
//        TextView tvAdminName = findViewById(R.id.tvAdminName);
//        TextView tvAdminEmail = findViewById(R.id.tvAdminEmail);
//        TextView tvAdminPhone = findViewById(R.id.tvAdminPhone);
//
//        // Get user data and populate the TextViews
//        try {
//            if (Utils.user_current != null) {
//                // Set admin name
//                tvAdminName.setText(Utils.user_current.getUsername() != null ?
//                    Utils.user_current.getUsername() : "Tên Quản Trị");
//
//                // Set admin email
//                tvAdminEmail.setText("Email: " + (Utils.user_current.getEmail() != null ?
//                    Utils.user_current.getEmail() : "admin@example.com"));
//
//                // Set admin phone
//                tvAdminPhone.setText("Điện thoại: " + (Utils.user_current.getMobile() != null ?
//                    Utils.user_current.getMobile() : "+84 123 456 789"));
//            } else {
//                // Try to read from Paper if Utils.user_current is null
//                if (Paper.book().read("user") != null) {
//                    User user = Paper.book().read("user");
//                    // Set admin name
//                    tvAdminName.setText(user.getUsername() != null ?
//                        user.getUsername() : "Tên Quản Trị");
//
//                    // Set admin email
//                    tvAdminEmail.setText("Email: " + (user.getEmail() != null ?
//                        user.getEmail() : "admin@example.com"));
//
//                    // Set admin phone
//                    tvAdminPhone.setText("Điện thoại: " + (user.getMobile() != null ?
//                        user.getMobile() : "+84 123 456 789"));
//                }
//            }
//        } catch (Exception e) {
//            // If there's an error reading user data, keep default values
//            e.printStackTrace();
//        }
//
//    }
//}