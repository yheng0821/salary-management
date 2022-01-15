package com.yu_JJ.service;

import com.yu_JJ.bean.Organise;
import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.bean.User;
import com.yu_JJ.dao.SalaryDao;
import com.yu_JJ.dao.UserDao;

import java.util.List;

/**
 * @className: SalaryService
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class SalaryService {
    private SalaryDao salaryDao = new SalaryDao();
    public Result querySalaryList(String userName,Integer page,Integer limit){
        List<Salary> salaries = salaryDao.querySalaryList(userName,page, limit);
        int count = 0;
        if("".equals(userName) || userName==null){
            count = salaryDao.countAllSalary();

        }else {
            count = salaries.size();
        }
        if (count>0){
            return new Result(1,"sucess",count,salaries);
        }
        return new Result(0,"failed",0,null);
    }


    public Result querySalaryById(Integer id){
        Salary salary = salaryDao.querySalaryById(id);
        if (salary != null){
            return new Result(1,"success",1,salary);
        }
        return new Result(0,"failed",0,null);
    }

    public Result addSalary(Salary salary){
        UserDao userDao = new UserDao();
        User exist = userDao.queryUserById(salary.getUserId());
        if (exist == null){
            return new Result(0,"用户不存在",null);
        }


        int i = salaryDao.addSalary(salary);
        if (i > 0){
            return new Result(1,"success",salary);
        }
        return new Result(0,"failed",null);
    }

    public Result updateSalary(Salary salary){
        UserDao userDao = new UserDao();
        Salary oldSalery = salaryDao.querySalaryById(salary.getSalaryId());
        if (salary.getCreateTime() == null){
            salary.setCreateTime(oldSalery.getCreateTime());
        }
        if (salary.getCreater() == null){
            salary.setCreater(oldSalery.getCreater());
        }
        if (salary.getUpdateTime() == null){
            salary.setUpdateTime(oldSalery.getUpdateTime());
        }
        if (salary.getUpdater() == null){
            salary.setUpdater(oldSalery.getUpdater());
        }
        User exist = userDao.queryUserById(salary.getUserId());
        if (exist == null){
            return new Result(0,"用户不存在",null);
        }

        int i = salaryDao.updateSalary(salary);
        if (i > 0){
            return new Result(1,"success",salaryDao.querySalaryById(salary.getSalaryId()));
        }
        return new Result(0,"failed",null);
    }

    public Result deleteSalary(Integer id){
        int i = salaryDao.deleteSalary(id);
        if (i > 0){
            return new Result(1,"success",null);
        }
        return new Result(0,"failed",null);
    }
    public Result getSaleryByOrganise(){
        List<Organise> saleryByOrganise = salaryDao.getSaleryByOrganise();
        if (saleryByOrganise.size() > 0){
            return new Result(1,"sucess",saleryByOrganise.size(),saleryByOrganise);
        }
        return new Result(0,"failed",null);
    }
}
