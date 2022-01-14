package com.yu_JJ.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu_JJ.bean.Result;
import com.yu_JJ.service.SalaryService;

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

                    rst =  salaryService.querySalaryList();

            }
        }



        String resStr = new ObjectMapper().writeValueAsString(rst);
        out.write(resStr);
        out.flush();
        out.close();

    }
}
