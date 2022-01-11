package com.yu_JJ.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.StrToDate("2000-06-15 08:45:35"));
    }
}
