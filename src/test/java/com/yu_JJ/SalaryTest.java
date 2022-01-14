package com.yu_JJ;

import com.yu_JJ.bean.Salary;
import com.yu_JJ.dao.SalaryDao;
import org.junit.Test;

import java.util.List;

/**
 * @className: SalaryTest
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class SalaryTest {
    @Test
    public void testSalary(){
        SalaryDao salaryDao = new SalaryDao();
        List<Salary> salaries = salaryDao.querySalaryList();
        System.out.println(salaries);
    }
}
