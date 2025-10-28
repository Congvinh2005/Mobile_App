package utt.cntt.httt.manager.utils;

import java.util.ArrayList;
import java.util.List;

import utt.cntt.httt.manager.model.GioHang;
import utt.cntt.httt.manager.model.User;

public class Utils {
    public static final String BASE_URL = "http://192.168.65.103/banhang/";
//    public static final String BASE_URL = "http://192.168.57.2:8888/banhang/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static List<User> manguser = new ArrayList<>(); // danh sách toàn bộ user
    public static User user_current = new User();
}
