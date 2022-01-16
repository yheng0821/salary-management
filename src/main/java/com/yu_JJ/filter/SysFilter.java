package com.yu_JJ.filter;

import com.yu_JJ.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: filterSysFilter
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/15
 **/

@WebFilter(filterName = "LoginFilter",urlPatterns = "/page/*")
public class SysFilter implements Filter {

    //初始化的方法，servlet第一次运行的时候调用
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("容器开始初始化===");
    }

    //拦截的方法
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("进来了拦截filter 方法===========");

        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rp = (HttpServletResponse)response;
        //获取登陆的session对象

        String spath = rq.getServletPath();
        String[] urls = {"/login","/json",".js",".css",".ico",".jpg",".png"};
        boolean flag = true;
        for (String str : urls) {
            if (spath.indexOf(str) != -1) {
                flag =false;
                break;
            }
        }
        User userSession = (User) rq.getSession().getAttribute("userSession");

        if (flag) {
            if(null == userSession){
                //未登录 让其重定向会登陆的页面
                rp.sendRedirect("/mavenWeb_war/login.html");
            }else{
                //已登陆放行
                chain.doFilter(request, response);
            }
        }else{
            chain.doFilter(request, response);
        }

    }

    //容器销毁后调用的方法
    @Override
    public void destroy() {
        System.out.println("容器销毁了===");
    }

}
