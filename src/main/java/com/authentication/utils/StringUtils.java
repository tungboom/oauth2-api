package com.authentication.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.joda.time.format.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author TungBoom
 *
 */
public class StringUtils {
    public static final String BLANK_STRING_VALUE = "";
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || BLANK_STRING_VALUE.equals(obj1.toString().trim());
    }
    public static boolean isNotNullOrEmpty(String obj1) {
        return obj1 != null && !BLANK_STRING_VALUE.equals(obj1.trim());
    }
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public static List<?> stringToArray(Class<?> persistentClass, String value, String character) {
    	List<String> listString = new ArrayList<>(Arrays.asList(value.split(character)));
    	if(persistentClass.equals(String.class)) {
    		return listString;
    	}
    	if(persistentClass.equals(Long.class)) {
    		List<Long> listLong = new ArrayList<>();
    		for (String string : listString) {
    			listLong.add(Long.parseLong(string));
    		}
    		return listLong;
    	}
    	return listString;
    }
    
    public static String dateToStringYYYYMM(Timestamp timestamp) {
    	return DateTimeFormat.forPattern("YYYYMM").print(timestamp.getTime());
    }
    
    public static String convertLowerParamFirst(String value) {
    	String result = value.toLowerCase()
                .replace("\\", "\\\\")
                .replaceAll("%", "\\\\%")
                .replaceAll("_", "\\\\_");
    	return "" + result + "%";
    }
    
    public static String convertLowerParamEnd(String value) {
    	String result = value.toLowerCase()
                .replace("\\", "\\\\")
                .replaceAll("%", "\\\\%")
                .replaceAll("_", "\\\\_");
    	return "%" + result + "";
    }
    
    public static String convertLowerParamContains(String value) {
    	String result = value.toLowerCase()
                .replace("\\", "\\\\")
                .replaceAll("%", "\\\\%")
                .replaceAll("_", "\\\\_");
    	return "%" + result + "%";
    }

    public static String removeSeparator(String pathInput) {
        String path = pathInput;
        path = path.replace("\\\\", "\\").replace("//", "/");
        path = path.replace("\\/", "/").replace("/\\", "/");
        return path;
    }
    
    public static String getBarcodeRandom(String length, String typeBarcode) {
    	String charsNumber = "0123456789";
    	String charsWord = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	int len = Integer.parseInt(length);
    	if("1".equals(typeBarcode)) {
    		char[] chars = charsNumber.toCharArray();
        	Random rnd = new Random();
        	StringBuilder sb = new StringBuilder("");
        	for (int i = 0; i < len; i++)
        	    sb.append(chars[rnd.nextInt(chars.length)]);

        	return sb.toString();
    	} else if("2".equals(typeBarcode)) {
    		char[] chars = charsWord.toCharArray();
        	Random rnd = new Random();
        	StringBuilder sb = new StringBuilder("");
        	for (int i = 0; i < len; i++)
        	    sb.append(chars[rnd.nextInt(chars.length)]);

        	return sb.toString();
    	} else if("3".equals(typeBarcode)) {
    		char[] chars = (charsNumber + charsWord).toCharArray();
        	Random rnd = new Random();
        	StringBuilder sb = new StringBuilder("");
        	for (int i = 0; i < len; i++)
        	    sb.append(chars[rnd.nextInt(chars.length)]);

        	return sb.toString();
    	}
    	return "";
    }
}
