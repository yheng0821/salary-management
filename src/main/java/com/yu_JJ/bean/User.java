package com.yu_JJ.bean;

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
    private Date createTime;
    private String creater;
    private Date updateTime;
    private String updater;
}
