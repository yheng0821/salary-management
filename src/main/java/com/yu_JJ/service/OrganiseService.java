package com.yu_JJ.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yu_JJ.bean.Organise;
import com.yu_JJ.bean.Result;
import com.yu_JJ.bean.Salary;
import com.yu_JJ.bean.User;
import com.yu_JJ.dao.OrganiseDao;
import com.yu_JJ.dao.UserDao;
import com.yu_JJ.utils.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @className: Organise
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class OrganiseService {
    private OrganiseDao organiseDao =new OrganiseDao();

    public Result queryOrganiseList(String orgName, Integer page, Integer limit){
        List<Organise> organiseList = organiseDao.queryOrganiseList(orgName,page,limit);
        int count = 0;
        if("".equals(orgName) || orgName==null){
            count = organiseDao.countAllOrganise();

        }else {
            count = organiseList.size();
        }
        if (count>0){
            return new Result(1,"sucess",count,organiseList);
        }
        return new Result(0,"failed",0,null);
    }

    public Result querySalaryById(Integer id){
        Organise organise = organiseDao.queryOrganiseById(id);
        if (organise != null){
            return new Result(1,"success",1,organise);
        }
        return new Result(0,"failed",0,null);
    }

    public Result addOrganise(Organise organise){
        organise.setCreateTime(DateUtil.getCurrentTime());
        int i = organiseDao.addOrganise(organise);
        if (i > 0){
            return new Result(1,"success",organise);
        }
        return new Result(0,"failed",null);
    }

    public Result updateOrganise(Organise organise){
        OrganiseDao organiseDao = new OrganiseDao();
        Organise oldOrganise = organiseDao.queryOrganiseById(organise.getOrgId());
        if (organise.getParOrgId() == null){
            organise.setParOrgId(oldOrganise.getParOrgId());
        }
        if (organise.getOrgLevel() == null){
            organise.setOrgLevel(oldOrganise.getOrgLevel());
        }
        if (organise.getCreater()     == null){
            organise.setCreater(oldOrganise.getCreater());
        }
        if (organise.getUpdateTime() == null){
            organise.setUpdateTime(oldOrganise.getUpdateTime());
        }
        if (organise.getUpdater() == null){
            organise.setUpdater(oldOrganise.getUpdater());
        }

        int i = organiseDao.updateOrganise(organise);
        if (i > 0){
            return new Result(1,"success",organiseDao.queryOrganiseById(organise.getOrgId()));
        }
        return new Result(0,"failed",null);
    }

    public Result deleteOrganise(Integer id){
        int i = organiseDao.deleteOrganise(id);
        if (i > 0){
            return new Result(1,"success",null);
        }
        return new Result(0,"failed",null);
    }

}
