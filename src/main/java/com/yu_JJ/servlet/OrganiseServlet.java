package com.yu_JJ.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu_JJ.bean.Organise;
import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.service.OrganiseService;
import com.yu_JJ.utils.GetRequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: OrganiseServlet
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
@WebServlet("/organise")
public class OrganiseServlet extends HttpServlet {
    private OrganiseService organiseService = new OrganiseService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        String method = req.getParameter("method");
        Result rst = null;

        if (!"".equals(method) || method != null) {
            if ("queryOrganiseList".equals(method)) {
                String orgName = req.getParameter("orgName");

                Integer page = Integer.valueOf(req.getParameter("page"));
                Integer limit = Integer.valueOf(req.getParameter("limit"));

                rst = organiseService.queryOrganiseList(orgName, page, limit);

            } else if ("queryOrganiseById".equals(method)) {
                Integer id = Integer.valueOf(req.getParameter("ordId"));
                rst = organiseService.querySalaryById(id);
            } else if ("addOrganise".equals(method)) {
                Organise organise = new Organise();
                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);
                Integer parOrgId = json.getInteger("parOrgId");
                String orgName = json.getString("orgName");
                String orgPath = json.getString("orgPath");
                Integer orgLevel = json.getInteger("orgLevel");
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");

                organise.setParOrgId(parOrgId);
                organise.setOrgName(orgName);
                organise.setOrgPath(orgPath);
                organise.setOrgLevel(orgLevel);
                organise.setCreateTime(createTime);
                organise.setCreater(creater);
                organise.setUpdateTime(updateTime);
                organise.setUpdater(updater);
                rst = organiseService.addOrganise(organise);
            } else if ("updateOrganise".equals(method)) {
                Organise organise = new Organise();

                JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);

                Integer orgId = json.getInteger("orgId");
                Integer parOrgId = json.getInteger("parOrgId");
                String orgName = json.getString("orgName");
                String orgPath = json.getString("orgPath");
                Integer orgLevel = json.getInteger("orgLevel");
                String createTime = json.getString("createTime");
                String creater = json.getString("creater");
                String updateTime = json.getString("updateTime");
                String updater = json.getString("updater");

                organise.setOrgId(orgId);
                organise.setParOrgId(parOrgId);
                organise.setOrgName(orgName);
                organise.setOrgPath(orgPath);
                organise.setOrgLevel(orgLevel);
                organise.setUpdateTime(updateTime);
                organise.setCreater(creater);
                organise.setCreateTime(createTime);
                organise.setUpdater(updater);
                rst = organiseService.updateOrganise(organise);
            } else if ("deleteOrganise".equals(method)) {
                Integer orgId = Integer.valueOf(req.getParameter("orgId"));
                rst = organiseService.deleteOrganise(orgId);
            } else {
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
