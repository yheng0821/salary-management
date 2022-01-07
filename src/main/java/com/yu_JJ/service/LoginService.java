package com.yu_JJ.service;

import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.User;
import com.yu_JJ.dao.LoginDao;
import com.yu_JJ.dao.UserDao;

import java.util.List;

public class LoginService {
    private LoginDao loginDao = new LoginDao();
    private UserDao userDao = new UserDao();
    public Result loginCheck(String userAcct, String userPwd){
        User user = loginDao.loginCheck(userAcct, userPwd);
        if (user != null){
            return new Result("1","success",user);
        }
        return new Result("0","failed",null);

    }

}
