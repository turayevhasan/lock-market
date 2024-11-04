package uz.pdp.lock_market.util;

public interface BaseConstants {
    String FILE_UPLOAD_PATH = "C:/PROJECT_DOWNLOAD_FILES/";
    String JWT_SECRET_KEY = "SECRET_KEY";
    long ACCESS_TOKEN_EXPIRE = 86400000;  // 1 day
    long REFRESH_TOKEN_EXPIRE = 86400000 * 7;  // 7 days
    String AUTHENTICATION_HEADER = "Authorization";
    String BEARER_TOKEN = "Bearer ";
}