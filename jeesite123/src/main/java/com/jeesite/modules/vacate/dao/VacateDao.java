/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.vacate.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.vacate.entity.Vacate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请假表信息维护DAO接口
 * @author BeHe
 * @version 2018-05-24
 */
@MyBatisDao
public interface VacateDao extends CrudDao<Vacate> {
    /**
     * 通过员工姓名查询获取员工编码
     * @param empName
     * @return
     */
    public String getEmpCodeByEmpName(String empName);

    public Vacate getByEmpCode(String empCode);

    public String getVaIdByUpdator(String empCode);

    /**
     * 通过经理审批后更新当前请假单信息,根据请假单的主键Id进行更新
     * @param vaId
     * @param status
     * @param manReason
     * @return
     */
    public int updateByVerify(@Param("vaId") String vaId,
                              @Param("manReason") String manReason,
                              @Param("status") String status,
                              @Param("updator") String updator);


    /**
     * 通过员工编号查询该员工提交过的所有请假单
     * @param vacate
     * @return
     */
    public List<Vacate> queryVacation(Vacate vacate);
}