package uz.pdp.lock_market.util;

public interface BaseConstants {
    String SERVER_HOST = "http://8.210.211.217";
    String FILE_UPLOAD_PATH = "/uploads/";
    String JWT_SECRET_KEY = "TE9DS19NQVJLRVRfUFJPSkVDVF9KV1RfVE9LRU5fU0VDUkVUX0tFWQ==";  // key = LOCK_MARKET_PROJECT_JWT_TOKEN_SECRET_KEY // algorithm = base64
    long ACCESS_TOKEN_EXPIRE = 86400000;  // 1 day
    long REFRESH_TOKEN_EXPIRE = 86400000 * 7;  // 7 days
    String AUTHENTICATION_HEADER = "Authorization";
    String BEARER_TOKEN = "Bearer ";
}