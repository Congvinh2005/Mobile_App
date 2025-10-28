package utt.cntt.httt.manager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MomoPaymentHelper {
    private static final String TAG = "MomoPaymentHelper";
    private static final String MOMO_PACKAGE_NAME = "com.mservice.momotransfer";
    private static final String MOMO_URL = "momo://pay";
    
    private Context context;
    private PaymentResultListener resultListener;
    
    public interface PaymentResultListener {
        void onSuccess(String orderId, String requestId, String amount);
        void onError(String message);
        void onCancel();
    }

    public MomoPaymentHelper(Context context) {
        this.context = context;
    }

    public void setPaymentResultListener(PaymentResultListener listener) {
        this.resultListener = listener;
    }

    /**
     * Kiểm tra xem ứng dụng Momo có được cài đặt trên thiết bị không
     */
    public boolean isMomoInstalled() {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(MOMO_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Yêu cầu thanh toán qua Momo
     */
    public void requestPayment(long amount, String orderId, String orderInfo) {
        // Kiểm tra ứng dụng Momo có được cài đặt không
        if (!isMomoInstalled()) {
            if (resultListener != null) {
                resultListener.onError("Vui lòng cài đặt ứng dụng Momo để thực hiện thanh toán");
            }
            // Mở CH Play để cài Momo
            openMomoInPlayStore();
            return;
        }

        try {
            // Tạo URI để mở ứng dụng Momo
            Uri.Builder uriBuilder = Uri.parse(MOMO_URL).buildUpon();
            
            // Thêm các tham số cần thiết
            uriBuilder.appendQueryParameter("action", "gettoken");
            uriBuilder.appendQueryParameter("merchantname", "Cửa hàng Quản lý");
            uriBuilder.appendQueryParameter("merchantcode", "MOMOIQA420180810"); // Mã test
            uriBuilder.appendQueryParameter("amount", String.valueOf(amount));
            uriBuilder.appendQueryParameter("orderid", orderId);
            uriBuilder.appendQueryParameter("orderinfo", orderInfo != null ? orderInfo : "Thanh toán đơn hàng");
            uriBuilder.appendQueryParameter("requestid", orderId);
            uriBuilder.appendQueryParameter("partner", "Cửa hàng Quản lý");
            uriBuilder.appendQueryParameter("description", orderInfo != null ? orderInfo : "Thanh toán đơn hàng");

            Uri uri = uriBuilder.build();
            
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            context.startActivity(intent);
            
            // Trong thực tế, bạn cần xử lý phản hồi từ Momo ở Activity cha sau khi chuyển về ứng dụng
            // qua onActivityResult hoặc dùng PendingIntent
            if (resultListener != null) {
                resultListener.onSuccess(orderId, orderId, String.valueOf(amount));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initiating Momo payment: " + e.getMessage());
            if (resultListener != null) {
                resultListener.onError("Lỗi khởi tạo thanh toán: " + e.getMessage());
            }
        }
    }
    
    private void openMomoInPlayStore() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + MOMO_PACKAGE_NAME));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + MOMO_PACKAGE_NAME));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}