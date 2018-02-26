package com.q18idc.ssm.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/1/16
 */
public class MyUtils {

    /**
     * 判断是否是合法的网址
     * @param url
     * @return
     */
    public boolean checkUrl(String url) {
        String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
        Pattern pattern = Pattern
                .compile(regex);
        return pattern.matcher(url).matches();
    }

    /**
     * 判读是否是合法手机号
     * @param telNum
     * @return
     */
    public static boolean isMobileNum(String telNum){
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 判断邮箱是否是正确邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        String emailAddressPattern = "\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2,6})$\\b";
        Pattern p = Pattern.compile(emailAddressPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 获得UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获得GUID
     * @return
     */
    public static String getGUID() {
        return new BigInteger(165,new Random()).toString(36).toUpperCase();
    }


    /**
     * 判断字符串中是否包含字母
     * @param s
     * @return
     */
    public static boolean strIsLetter(String s){
        byte[] bytes = s.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')){
                return true;
            }
        }
        return false;
    }

    public static String formatDateTime(long mss) {
        String dateTime = null;
        long days = mss / ( 60 * 60 * 24);
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;
        if(days>0){
            dateTime= days + ":" + hours + ":" + minutes + ":"
                    + seconds;
        }else if(hours>0){
            dateTime=hours + ":" + minutes + ":"
                    + seconds ;
        }else if(minutes>0){
            dateTime=minutes + ":"
                    + seconds ;
        }else{
            dateTime=seconds+"";
        }

        return dateTime;
    }

    /**
     * 判断输入的是否是数字
     * @param str
     * @return
     */
    public static boolean checkNumer(String str) {
        String regex = "[0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * md5加密
     * @param text
     * @return
     */
    public static String md5Encrypt(String text) {
        String result = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] results = md.digest(text.getBytes("utf-8"));
            StringBuffer buff=new StringBuffer();
            for(byte b: results){
                buff.append(String.format("%02x", b));
            }
            result = buff.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 将时间转换为时间戳
     * @param s 时间字符串
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间转换为时间戳
     * @param s 时间字符串
     * @param rule 转换规则 如 yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s,String rule) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(rule);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     * @param s 时间戳字符串
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     * @param s 时间戳字符串
     * @param rule 转换规则 如 yyyy-MM-dd
     * @return
     */
    public static String stampToDate(String s,String rule){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(rule);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 日期转字符串
     * @param date 日期类型
     * @return
     */
    public static String dateToStrTime(Date date){
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 日期转字符串
     * @param date 日期类型
     * @param rule 转换规则 如 yyyy-MM-dd
     * @return
     */
    public static String dateToStrTime(Date date,String rule){
        SimpleDateFormat sdf =   new SimpleDateFormat(rule);
        String str = sdf.format(date);
        return str;
    }

    /**
     * 字符串转日期
     * @param time 时间字符串
     * @return
     */
    public static Date strTimeToDate(String time){
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 字符串转日期
     * @param time 时间字符串
     * @param rule 转换规则 如 yyyy-MM-dd
     * @return
     */
    public static Date strTimeToDate(String time,String rule){
        SimpleDateFormat sdf =   new SimpleDateFormat(rule);
        try {
            Date date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 判断输入的字符串是否是时间格式
     * @param time 时间格式字符串
     * @return
     */
    public static boolean isTimeStr(String time){
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断输入的字符串是否是时间格式
     * @param time 时间格式字符串
     * @param rule 转换规则 如 yyyy-MM-dd
     * @return
     */
    public static boolean isTimeStr(String time,String rule){
        SimpleDateFormat sdf =  new SimpleDateFormat(rule);
        try {
            Date date = sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 非空，null， null字符串和"";
     * @param str
     * @return
     */
    public static boolean isNotEmpty (String str) {
        String s = "null";
        if (str != null && !"".equals(str) && !s.equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判空 空，null， null字符串和"";
     * @param str
     * @return
     */
    public static boolean isEmpty (String str) {
        String s = "null";
        if (str == null || "".equals(str) || s.equals(str)) {
            return true;
        }
        return false;
    }
}
