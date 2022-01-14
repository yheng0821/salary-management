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
    public Result querySalaryList(){
        List<Salary> salaries = salaryDao.querySalaryList();
        if (salaries.size()>0){
            return new Result(1,"sucess",salaries.size(),salaries);
        }
        return new Result(0,"failed",0,null);
    }
}
