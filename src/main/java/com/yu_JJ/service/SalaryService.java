package com.yu_JJ.service;

import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.dao.SalaryDao;

import java.util.List;

/**
 * @className: SalaryService
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class SalaryService {
    private SalaryDao salaryDao = new SalaryDao();
    public Result querySalaryList(Integer page,Integer limit){
        List<Salary> salaries = salaryDao.queryAllSalary(page, limit);
        int count = salaryDao.countAllSalary();
        if (salaries.size()>0){
            return new Result(1,"sucess",count,salaries);
        }
        return new Result(0,"failed",0,null);
    }
}
