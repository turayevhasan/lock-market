package uz.pdp.lock_market.util;

public interface CoreUtils {
    String FILE_UPLOAD_PATH = "C:/PROJECT_DOWNLOAD_FILES/";
    String JWT_SECRET_KEY = "SECRET_KEY";
    long ACCESS_TOKEN_EXPIRE = 86400000;  // 1 day
    long REFRESH_TOKEN_EXPIRE = 86400000 * 7;  // 7 days
}