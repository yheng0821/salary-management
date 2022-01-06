package com.yu_JJ.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu_JJ.bean.Result;
import com.yu_JJ.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private LoginService loginService = new LoginService();
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("login start");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/json;charset=UTF-8");
        String userAcct = req.getParameter("userAcct");
        String userPwd = req.getParameter("userPwd");
        String method = req.getParameter("method");
        Result result = null;
        if (!"".equals(method) || method!= null){
            if ("login".equals(method)){
                result = loginService.loginCheck(userAcct,userPwd);
            }else if("logout".equals(method)){

            }
            String resStr = new ObjectMapper().writeValueAsString(result);
            out.write(resStr);
        }else {
            out.write("请求不合法");
        }

        out.flush();
        out.close();
        logger.info("login end");
    }
}
