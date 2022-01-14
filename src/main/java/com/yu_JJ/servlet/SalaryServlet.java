package com.yu_JJ.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.bean.User;
import com.yu_JJ.service.SalaryService;
import com.yu_JJ.utils.GetRequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: SalaryServlet
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
@WebServlet("/salary")
public class SalaryServlet extends HttpServlet {
    private SalaryService salaryService = new SalaryService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        Result rst = null;


        if (!"".equals(method) || method!= null){
            if ("querySalaryList".equals(method)){
                String userName = req.getParameter("userName");
                Integer page = Integer.valueOf(req.getParameter("page"));
                Integer limit = Integer.valueOf(req.getParameter("limit"));
                rst =  salaryService.querySalaryList(userName,page,limit);

            }else if("querySalaryById".equals(method)){
                Integer id = Integer.valueOf(req.getParameter("salaryId"));
                rst = salaryService.querySalaryById(id);
            }else if("addSalary".equals(method)){
                Salary salary = new Salary();
                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);
                Integer userId = Integer.valueOf(json.getString("userId"));
                String monthId = json.getString("monthId");
                Float salary_money = json.getFloat("salary");
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");

                salary.setUserId(userId);
                salary.setMonthId(monthId);
                salary.setSalary(salary_money);
                salary.setCreateTime(createTime);
                salary.setCreater(creater);
                salary.setUpdateTime(updateTime);
                salary.setUpdater(updater);
                rst = salaryService.addSalary(salary);
            }else if("updateSalary".equals(method)){
                Salary salary = new Salary();

                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);

                Integer salaryId = json.getInteger("salaryId");
                Integer userId = json.getInteger("userId");
                String monthId = json.getString("monthId");
                Float salary_money = Float.valueOf(json.getString("salary"));
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");

                salary.setSalaryId(salaryId);
                salary.setUserId(userId);
                salary.setMonthId(monthId);
                salary.setSalary(salary_money);
                salary.setCreateTime(createTime);
                salary.setCreater(creater);
                salary.setUpdateTime(updateTime);
                salary.setUpdater(updater);
                rst = salaryService.updateSalary(salary);
            }else if("deleteSalary".equals(method)){
                Integer salaryId = Integer.valueOf(req.getParameter("salaryId"));
                rst = salaryService.deleteSalary(salaryId);
            } else{
                rst.setRetMsg("请求不合法");
                out.write("请求不合法");
            }
        }



        String resStr = new ObjectMapper().writeValueAsString(rst);
        out.write(resStr);
        out.flush();
        out.close();

    }
}
