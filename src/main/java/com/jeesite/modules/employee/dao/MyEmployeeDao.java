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
    public List<MyEmployee> findListByRole(@Param("first")int first, @Param("pageSize")int pageSize, @Param("myEmployee")MyEmployee myEmployee);
    public long findCountByRole(@Param("roleCode")String roleCode);
    public List<MyEmployee> findList(MyEmployee myEmployee);
}