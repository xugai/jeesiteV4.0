/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expense.dao;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.expense.entity.TExpense;

/**
 * expenseDAO接口
 * @author mkj
 * @version 2018-06-23
 */
@MyBatisDao
public interface TExpenseDao extends CrudDao<TExpense> {
	public TExpense getBypiid(String piid);

	public List<TExpense> getByEmpCode(String userCode);

	public List<TExpense> getCaiwuApproveList(String officeName);

	public List<TExpense> getElseApproveList(String officeName);

}