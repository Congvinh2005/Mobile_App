package utt.cntt.httt.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.model.GioHang;
import utt.cntt.httt.manager.retrofit.ApiBanHang;
import utt.cntt.httt.manager.retrofit.RetrofitClient;
import utt.cntt.httt.manager.utils.MomoPaymentHelper;
import utt.cntt.httt.manager.utils.Utils;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang;
    AppCompatButton btnThanhToanMomo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        countItem();
        initControl();
        
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);

        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtsodt.setText(Utils.user_current.getMobile());


        // Xử lý thanh toán qua Momo
        btnThanhToanMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    // Khởi tạo thanh toán qua Momo
                    MomoPaymentHelper momoHelper = new MomoPaymentHelper(ThanhToanActivity.this);
                    momoHelper.setPaymentResultListener(new MomoPaymentHelper.PaymentResultListener() {
                        @Override
                        public void onSuccess(String orderId, String requestId, String amount) {
                            // Lưu trạng thái đặt hàng để kiểm tra sau khi quay lại từ Momo
                            // Vì phương thức mở Momo trực tiếp không thể nhận phản hồi trực tiếp,
                            // nên chúng ta sẽ tạo đơn hàng trước, sau đó cập nhật trạng thái sau
                            
                            // Hiện thông báo hướng dẫn người dùng
                            Toast.makeText(getApplicationContext(), 
                                "Vui lòng hoàn tất thanh toán trong ứng dụng Momo. Sau đó quay lại để xác nhận.", 
                                Toast.LENGTH_LONG).show();
                            
                            // Gửi yêu cầu tạo đơn hàng trước, đánh dấu là "chưa thanh toán"
                            String str_email = Utils.user_current.getEmail();
                            String str_sdt = Utils.user_current.getMobile();
                            int id = Utils.user_current.getId();
                            
                            // Thêm thông tin xác nhận thanh toán sau vào đơn hàng
                            String chitiet = new Gson().toJson(Utils.mangmuahang) + 
                                "|MOMO|" + orderId + "|" + System.currentTimeMillis();
                            
                            compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, chitiet)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            userModel -> {
                                                // Chuyển về trang chủ sau khi tạo đơn
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            },
                                            throwable -> {
                                                Toast.makeText(getApplicationContext(), "Tạo đơn hàng thất bại: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                    ));
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), "Lỗi thanh toán qua Momo: " + message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getApplicationContext(), "Người dùng đã hủy thanh toán", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Gọi thanh toán với Momo
                    String orderId = "ORDER_" + System.currentTimeMillis();
                    String orderInfo = "Thanh toán đơn hàng #" + orderId;
                    momoHelper.requestPayment(tongtien, orderId, orderInfo);
                }
            }
        });

        // Xử lý thanh toán khi giao hàng
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    // Hiển thị hộp thoại xác nhận thanh toán khi giao hàng
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ThanhToanActivity.this);
                    builder.setTitle("Xác nhận thanh toán");
                    builder.setMessage("Bạn có xác nhận thanh toán khi nhận hàng không?");
                    
                    builder.setPositiveButton("Có", (dialog, which) -> {
                        // Xác nhận tạo đơn hàng
                        String str_email = Utils.user_current.getEmail();
                        String str_sdt = Utils.user_current.getMobile();
                        int id = Utils.user_current.getId();

                        Log.d("test", new Gson().toJson(Utils.mangmuahang));
                        compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        userModel -> {
                                            Toast.makeText(getApplicationContext(),
                                                    "Đặt hàng thành công!\nVui lòng thanh toán khi nhận hàng.",
                                                    Toast.LENGTH_LONG
                                            ).show();


                                            // clear manggiohang bang cach chay qua mangmuahang va clear item trong manggiohang
                                            for (int i = 0; i < Utils.mangmuahang.size(); i++) {
                                                GioHang gioHang = Utils.mangmuahang.get(i);
                                                if (Utils.manggiohang.contains(gioHang)) {
                                                    Utils.manggiohang.remove(gioHang);
                                                }
                                            }
                                            Utils.mangmuahang.clear();
                                            Paper.book().write("giohang", Utils.manggiohang);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(), "Lỗi khi đặt hàng: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                ));
                    });
                    
                    builder.setNegativeButton("Không", (dialog, which) -> {
                        Toast.makeText(getApplicationContext(), "Bạn chưa chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
                    
                    builder.show();
                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
        txtsodt = findViewById(R.id.txtsodienthoai);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnThanhToanMomo = findViewById(R.id.btn_thanhtoan_momo);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}