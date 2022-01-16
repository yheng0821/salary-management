package com.yu_JJ.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.User;
import com.yu_JJ.service.UserService;
import com.yu_JJ.utils.GetRequestJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: UserServlet
 * @description:   方位接口/user/{method}
 * 1.登录验证
 * 2.批量用户查询
 * 3.用户明细查询
 * 4.新增用户
 * 5.修改用户
 * 6.删除用户
 * @author: yheng
 * @date: 2022/1/6
 **/

public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
//        resp.setHeader("Content-type", "textml;charset=UTF-8");
//        resp.setCharacterEncoding("UTF-8");

        String method = req.getParameter("method");
        Result result = null;
        if (!"".equals(method) || method != null) {
            if ("update".equals(method)) {//更新用户操作，传入user的json数据，由id对指定用户更新
                User user = new User();
//                String userName = req.getParameter("userName");


                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);

                Integer userId = json.getInteger("userId");
                String userName = json.getString("userName");
                String userAcct = json.getString("userAcct");
                String userPwd = json.getString("userPwd");
                String alias = json.getString("alias");
                String mail = json.getString("mail");
                String telephone = json.getString("telephone");
                String address = json.getString("address");
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");

                user.setUserId(userId);
                user.setUserName(userName);
                user.setUserAcct(userAcct);
                user.setUserPwd(userPwd);
                user.setAlias(alias);
                user.setMail(mail);
                user.setTelephone(telephone);
                user.setAddress(address);
                user.setCreateTime(createTime);
                user.setCreater(creater);
                user.setUpdateTime(updateTime);
                user.setUpdater(updater);
                result = userService.updateUser(user);
            } else if ("delete".equals(method)) {             //删除用户，通过id找到用户删除
                Integer userId = Integer.valueOf(req.getParameter("userId"));
                result = userService.deleteUser(userId);
            } else if ("add".equals(method)) {                //添加用户，不允许对id进行编辑，由逐渐自动递增
                User user = new User();
                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);
                String userName = json.getString("userName");
                String userAcct = json.getString("userAcct");
                String userPwd = json.getString("userPwd");
                String alias = json.getString("alias");
                String mail = json.getString("mail");
                String telephone = json.getString("telephone");
                String address = json.getString("address");
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");


                user.setUserName(userName);
                user.setUserAcct(userAcct);
                user.setUserPwd(userPwd);
                user.setAlias(alias);
                user.setMail(mail);
                user.setTelephone(telephone);
                user.setAddress(address);
                user.setCreateTime(createTime);
                user.setCreater(creater);
                user.setUpdateTime(updateTime);
                user.setUpdater(updater);
                result = userService.addUser(user);
            } else if ("queryUserById".equals(method)) {       //查询用户，通过主键查询
                Integer id = Integer.valueOf(req.getParameter("userId"));
                result = userService.queryUserById(id);
            } else if ("queryUserList".equals(method)) {         //批量查询，userName，开始行，截至行
                String userName = req.getParameter("userName");
                Integer page = Integer.valueOf(req.getParameter("page"));
                Integer limit = Integer.valueOf(req.getParameter("limit"));
                result = userService.queryUserList(userName, page, limit);
            } else if ("login".equals(method)) {                //用户登录验证
//                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);
//                String userName = json.getString("userAcct");
                String userAcct = req.getParameter("userAcct");
                String userPwd = req.getParameter("userPwd");
                result = userService.loginCheck(userAcct, userPwd);
                if (result.getRetCode() == 1){
                    req.getSession().setAttribute("userSession", (User)result.getRetObj());
                }
            } else if ("queryAllUser".equals(method)) {
                Integer page = Integer.valueOf(req.getParameter("page"));
                Integer limit = Integer.valueOf(req.getParameter("limit"));
                result = userService.queryAllUser(page, limit);
            } else {
                result.setRetMsg("请求不合法");
                out.write("请求不合法");
            }
        }

        String resStr = new ObjectMapper().writeValueAsString(result);
        out.write(resStr);
        out.flush();
        out.close();
    }

}
