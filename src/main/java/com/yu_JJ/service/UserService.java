package com.yu_JJ.service;

import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.User;
import com.yu_JJ.dao.UserDao;

import java.util.List;

/**
 * @className: UserService
 * @description: 用户登录服务
 * @author: yheng
 * @date: 2022/1/6
 **/
public class UserService {
    private UserDao userDao = new UserDao();

    public Result queryUserList(String userName, int start, int end){
        List<User> userList = userDao.queryUserList(userName, start, end);
        if (userList.size()>0){
            return new Result(1,"success",userList.size(),userList);
        }
        return new Result(0,"failed",0,null);
    }

    public Result queryUserById(Integer id){
        User user = userDao.queryUserById(id);
        if (user != null){
            return new Result(1,"success",1,user);
        }
        return new Result(0,"failed",0,null);
    }

    public Result addUser(User user){
        int i = userDao.addUser(user);
        if (i > 0){
            return new Result(1,"success",1,user);
        }
        return new Result(0,"failed",0,null);
    }

    public Result updateUser(User user){
        int i = userDao.updateUser(user);
        if (i > 0){
            return new Result(1,"success",1,user);
        }
        return new Result(0,"failed",0,null);
    }
    public Result deleteUser(Integer id){
        int i = userDao.deleteUser(id);
        if (i > 0){
            return new Result(1,"success",1,null);
        }
        return new Result(0,"failed",0,null);
    }

    public Result loginCheck(String userAcct, String userPwd){
        User user = userDao.loginCheck(userAcct, userPwd);
        if (user != null){
            return new Result(1,"success",1,user);
        }
        return new Result(0,"failed",0,null);

    }
    public Result queryAllUser(Integer page,Integer limit){
        List<User> userList = userDao.queryAllUser(page, limit);
        if (userList.size()>0){
            return new Result(1,"success",userList.size(),userList);
        }
        return new Result(0,"failed",0,null);
    }

    public static void main(String[] args){
        UserService userService = new UserService();
//        User user = new User();
//        user.setUserPwd("root");
//        user.setUserId(107);
        Result result = userService.deleteUser(107);
        System.out.println(result);
    }
}
