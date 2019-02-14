package com.authentication.utils;

/**
 * @author TungBoom
 *
 */
public final class Constants {
    public static final String API_PATH_PREFIX = "/demo/";
    
    public static final String LANGUAGE = "i18n/language";

    public interface ROLE {
        public static final String ADMIN = "ADMIN";
    }

    public static final String formatterDateText = "dd/MM/yyyy";
    public static final String formatterDateTimeText = "dd/MM/yyyy HH:mm:ss";
    
    public interface RESULT {
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String DUPLICATE = "DUPLICATE";
        String FILE_IS_NULL = "FILE_IS_NULL";
        String NODATA = "NODATA";
        String FILE_ERROR = "FILE_ERROR";
        String FILE_INVALID = "FILE_INVALID";
        String FILE_TYPE_INVALID = "FILE_TYPE_INVALID";
        String DATA_OVER = "DATA_OVER";
        String NOT_ACCESS = "NOT_ACCESS";
        String NO_CAN_DELETE = "NO_CAN_DELETE";
    }
    
    private Constants() {
    }
}
