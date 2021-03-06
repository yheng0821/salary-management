package com.yu_JJ.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int userId;
    private String userName;
    private String userAcct;
    private String userPwd;
    private String alias;
    private String mail;
    private String telephone;
    private String address;
    private String createTime;
    private String creater;
    private String updateTime;
    private String updater;

    private Salary organiseName;

}
