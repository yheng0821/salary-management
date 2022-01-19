package com.yu_JJ.utils;

import com.yu_JJ.bean.Salary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: JudgeUtil
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/17
 **/
public class JudgeUtil {
    //判断Email合法性
    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }


    public static boolean isMobileNum(String telNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

}
