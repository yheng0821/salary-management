package com.yu_JJ.service;

import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.bean.User;
import com.yu_JJ.dao.SalaryDao;
import com.yu_JJ.dao.UserDao;
import com.yu_JJ.utils.DateUtil;

import java.util.List;

/**
 * @className: UserService
 * @description: 用户登录服务
 * @author: yheng
 * @date: 2022/1/6
 **/
public class UserService {
    private UserDao userDao = new UserDao();

    public Result queryUserList(String userName, int page, int limit){
        List<User> userList = userDao.queryUserList(userName,page, limit);
        int count = 0;
        if("".equals(userName) || userName==null){
            count = userDao.countAllUser();

        }else {
            count = userList.size();
        }
        if (count>0){
            return new Result(1,"sucess",count,userList);
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
        user.setCreateTime(DateUtil.getCurrentTime());
        int i = userDao.addUser(user);
        if (i > 0){
            return new Result(1,"success",user);
        }
        return new Result(0,"failed",null);
    }

    public Result updateUser(User user){
        UserDao userDao = new UserDao();
        User oldUser = userDao.queryUserById(user.getUserId());
        if (user.getAddress() == null){
            user.setAddress(oldUser.getAddress());
        }
        if (user.getCreateTime() == null){
            user.setCreateTime(oldUser.getCreateTime());
        }
        if (user.getCreater() == null){
            user.setCreater(oldUser.getCreater());
        }
        if (user.getUpdateTime() == null){
            user.setUpdateTime(oldUser.getUpdateTime());
        }
        if (user.getUpdater() == null){
            user.setUpdater(oldUser.getUpdater());
        }



        user.setUpdateTime(DateUtil.getCurrentTime());
        int i = userDao.updateUser(user);
        if (i > 0){
            return new Result(1,"success",user);
        }
        return new Result(0,"failed",null);
    }
    public Result deleteUser(Integer id){
        int i = userDao.deleteUser(id);
        if (i > 0){
            return new Result(1,"success",null);
        }
        return new Result(0,"failed",null);
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
        int count = userDao.countAllUser();
        if (count>0){
            return new Result(1,"success",count,userList);
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
