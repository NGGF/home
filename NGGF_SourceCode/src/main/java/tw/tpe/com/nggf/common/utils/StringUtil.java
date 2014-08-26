/*
 * $Id: StringUtil.java 16202 2012-11-26 02:28:35Z 96003 $
 * Copyright 2011 Hyweb Technology Corporation.
 * All Rights Reserved.
 */
package tw.tpe.com.nggf.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SamSon
 * 
 */
public class StringUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
    /**
     * pad character to the left of the string
     * 
     * @param s
     *            - original string
     * @param len
     *            - desired len of string after padded
     * @param c
     *            - padding char
     * @return padded string
     */
    public static String padLeft(String s, int len, char c) {
        if (s.length() > len)
            return s;

        String sTrim = s.trim();
        StringBuffer d = new StringBuffer(len);
        int fill = len - sTrim.length();
        while (fill-- > 0)
            d.append(c);
        d.append(sTrim);
        return d.toString();
    }

    /**
     * pad character to the right of the string
     * 
     * @param s
     *            - original string
     * @param len
     *            - desired len of string after padded
     * @param c
     *            - padding char
     * @return padded string
     */
    public static String padRight(String s, int len, char c) {
        StringBuffer d = new StringBuffer(s);
        while (d.length() < len)
            d.append(c);
        return d.toString();
    }

    /**
     * Unpad from left. In case the string to be returned is empty, the result
     * is c
     * 
     * @param s
     *            - original string
     * @param c
     *            - padding char
     * @return unPadded string.
     */
    public static String unPadLeft(String s, char c) {
        if ((s.trim().length() == 0) && (c == ' '))
            return Character.toString(c);
        else if ((s.trim().length() == 0))
            return s;
        String sTrim = s.trim();
        int fill = 0, end = sTrim.length();
        while ((fill < end) && (sTrim.charAt(fill) == c))
            fill++;
        return (fill < end) ? sTrim.substring(fill, end) : sTrim.substring(
                fill - 1, end);
    }

    /**
     * Unpad from right. In case the string to be returned is empty, the result
     * is c
     * 
     * @param s
     *            - original string
     * @param c
     *            - padding char
     * @return unPadded string.
     */
    public static String unPadRight(String s, char c) {
        if ((s.trim().length() == 0) && (c == ' '))
            return Character.toString(c);
        else if ((s.trim().length() == 0))
            return s;
        String sTrim = s.trim();
        int end = sTrim.length();
        while ((0 < end) && (sTrim.charAt(end - 1) == c))
            end--;
        return (0 < end) ? sTrim.substring(0, end) : sTrim.substring(0, 1);
    }

    /**
     * converts a byte array to hex string (suitable for dumps and ASCII
     * packaging of Binary fields
     * 
     * @param b
     *            - byte array
     * @return String representation
     */
    public static String hexString(byte[] b) {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
            char lo = Character.forDigit(b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }

    /**
     * @param b
     *            source byte array
     * @param offset
     *            starting offset
     * @param len
     *            number of bytes in destination (processes len*2)
     * @return byte[len]
     */
    public static byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++) {
            int shift = i % 2 == 1 ? 0 : 4;
            d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
        }
        return d;
    }

    /**
     * Convert hex String to byte[]. ex."12345678" to
     * byte[]={0x12,0x34,0x56,0x78}
     * 
     * @param s
     *            source string (with Hex representation)
     * @return byte array
     */
    public static byte[] hex2byte(String s) {
        return hex2byte(s.getBytes(), 0, s.length() >> 1);
    }

    /**
     * check the string has '0' character
     * 
     * @param s
     *            input string
     * @return true if the string is zero-filled ( '0' char filled )
     */
    public static boolean isZero(String s) {
        int i = 0, len = s.length();
        while (i < len && (s.charAt(i) == '0')) {
            i++;
        }
        return (i >= len);
    }

    /**
     * check the string is null or empty string
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * 檢查傳進來的字串是否為數值型態的字串
     * 
     * @param fldValue
     *            - value
     * @return is Numeric
     */
    public static boolean isNumeric(String fldValue) {
        boolean ret = true;

        if ((fldValue == null) || (fldValue.equals(""))) {
            ret = false;

            return ret;
        }

        for (int i = 0; i < fldValue.length(); i++) {
            if (!Character.isDigit(fldValue.charAt(i))) {
                ret = false;

                break;
            }
        }

        return ret;
    }

    /**
     * 檢查傳進來的字串是否為數值+空白 型態的字串
     * 
     * @param fldValue
     *            - value
     * @return is Numeric Space
     */
    public static boolean isNumericSpace(String fldValue) {
        boolean ret = true;

        if ((fldValue == null) || (fldValue.equals(""))) {
            ret = false;

            return ret;
        }

        char ch = 0;

        for (int i = 0; i < fldValue.length(); i++) {
            ch = fldValue.charAt(i);

            if (!Character.isDigit(ch) && (ch != ' ')) {
                ret = false;

                break;
            }
        }

        return ret;
    }


    /**
     * Return true if the string is alphanum.
     * 
     * @param s
     *            input string
     * @return true if the string is alphanum.
     */
    public static boolean isAlphaNumeric(String s) {
        int i = 0, len = s.length();
        while (i < len && (Character.isLetterOrDigit(s.charAt(i)))) {
            i++;
        }
        return (i >= len);
    }
    
    public static boolean isEngName(String s) {
    	int i = 0, len = s.length();
        while (i < len && (Character.isLetter(s.charAt(i)) || s.charAt(i)==' ') ) {
            i++;
        }
        return (i >= len);
    }
    public static boolean isChnName(String s) {
    	//先簡單判斷是否有雙位元組字元
        return s.getBytes().length!=s.length();
    }
    
    /**
     * Parses the int. 0 will be return while any exception
     *
     * @param intStr the int str
     * @return the int
     */
    public static int parseInt(String intStr){
        return parseInt(intStr,0);
    }
    
    /**
     * Parses the int.
     *
     * @param intStr the int str
     * @param defValue the def value will return while any Exception
     * @return the int
     */
    public static int parseInt(String intStr, int defValue){
        int ret = defValue;
        if (intStr==null){
            return ret;
        }
        if (!StringUtil.isNumeric(intStr)){
            return ret;
        }
        try{
            ret = Integer.parseInt(intStr);
        }catch(Exception e){
            logger.warn(e.toString());
        }
        return ret;
    }
    
    public static long parseLong(String intStr){
        return parseLong(intStr,0);
    }
    
    public static long parseLong(String intStr, long defValue){
        long ret = defValue;
        if (intStr==null){
            return ret;
        }
        if (!StringUtil.isNumeric(intStr)){
            return ret;
        }
        try{
            ret = Long.parseLong(intStr);
        }catch(Exception e){
        	logger.warn(e.toString());
        }
        return ret;
    }
    
    public static double parseDouble(String intStr){
        return parseDouble(intStr,0);
    }
    
    public static double parseDouble(String intStr, double defValue){
        double ret = defValue;
        if (intStr==null){
            return ret;
        }
        
        try{
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < intStr.length(); i++) {
                char ch = intStr.charAt(i);
                if (Character.isDigit(ch) || ch=='.') {
                    sb.append(ch);
                }
            }
            ret = Double.parseDouble(sb.toString());
        }catch(Exception e){
        	logger.warn(e.toString());
        }
        return ret;
    }
    /**
     * Filter date.過濾輸入日期非數字
     *
     * @param fldValue the fld value
     * @return the string
     */
    public static String filterDate(String fldValue){
        String ret = fldValue;
        if (fldValue==null){
            return ret;
        }
        
        return ret.replaceAll("[^0-9]", "");
    }
    
    public static String chkNull2empty(String str){
        if (str==null){
            return "";
        }else{
            return str;
        }
    }
        
    /**
     * 判斷是否是身分證字號
     * @param input
     * @return
     */
    public static boolean isPersonId(String input) {
    	if(isEmpty(input)) {
    		return false;
    	}
    	return Pattern.compile("^[A-Za-z]{1}[1-2]{1}[0-9]{8}$").matcher(input).matches();
    }
    
    public static boolean isEmptyOrBlank(String str) {
    	return isEmpty(str) || StringUtils.isBlank(str);
    }
}
