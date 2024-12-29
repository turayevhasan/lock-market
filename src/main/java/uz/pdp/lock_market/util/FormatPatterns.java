package uz.pdp.lock_market.util;

public interface FormatPatterns {
    String EMAIL_REGEX = "^\\w*?[a-zA-Z]\\w+@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.[a-z]+\\z";
    String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    String MOBILE_PHONE_NUMBER = "^\\+998[0-9]{9}$";
}
