/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.employee.entity.MyEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工表DAO接口
 * @author kyrie
 * @version 2018-05-25
 */
@MyBatisDao
public interface MyEmployeeDao extends CrudDao<MyEmployee> {
    /**
     *
     * @param first         偏移量
     * @param pageSize      页面数据数
     * @param myEmployee    条件查询的条件
     * @param myEmp_code    当前部长的编号
     * @return
     */
    public List<MyEmployee> findListByRole(@Param("first")int first, @Param("pageSize")int pageSize, @Param("myEmployee")MyEmployee myEmployee,@Param("myEmp_code")String myEmp_code);
    public long findCountByRole(@Param("roleCode")String roleCode,@Param("myEmp_code")String myEmp_code);
    public List<MyEmployee> findList(MyEmployee myEmployee);


    /**
     * 通过员工名字获得当前员工部门的部门编码
     * @author BeHe
     * @param empName
     * @return
     */
    public String getOfficeCodeByEmpNme(String empName);

    /**
     * 通过部门编码获取当前部门的经理姓名
     * @author BeHe
     * @param officeCode
     * @return
     */
    public String getEmpNameByOfficeCode(String officeCode);

    /**
     * 通过员工姓名获取员工编号
     * @author BeHe
     * @param empName
     */
    public String getEmpCodeByEmpName(String empName);

    /**
     * 通过员工编号获取当前员工的职位编码
     * @BeHe
     * @param empCode
     * @return
     */
    public List<String> getPostcodeByEmpcode(String empCode);
}