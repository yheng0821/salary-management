package com.yu_JJ.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @className: DateUtil
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/6
 **/
public class DateUtil {
    /**
     * 字符串转换成日期
     * @param
     * @return date
     */
    public static Timestamp StrToDate(String date_str) {


        try {
            Calendar cal = Calendar.getInstance();//日期类
            java.sql.Timestamp timestampnow = new java.sql.Timestamp(cal.getTimeInMillis());//转换成正常的日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            java.util.Date current = formatter.parse(date_str, pos);
            timestampnow = new java.sql.Timestamp(current.getTime());
            return timestampnow;
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public static String getCurrentTime(){
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(date);
        return format;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getCurrentTime());
    }
}
