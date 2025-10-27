package utt.cntt.httt.manager.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.model.User;
import utt.cntt.httt.manager.utils.Utils;

public class ThongTinActivity extends AppCompatActivity {
    TextView txtAd;
    Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        txtAd = findViewById(R.id.txtAd);

        if (Utils.user_current.getRole() == 1) {
            // Tìm admin có role = 2
            String adminName = "";
            for (User u : Utils.manguser) {
                if (u.getRole() == 2) {
                    adminName = u.getUsername();
                    break;
                }
            }
            txtAd.setText("Admin: " + adminName);
        } else if (Utils.user_current.getRole() == 2) {
            // Admin đăng nhập thì hiển thị tên của chính admin
            txtAd.setText("Admin: " + Utils.user_current.getUsername());
        }
    }
}