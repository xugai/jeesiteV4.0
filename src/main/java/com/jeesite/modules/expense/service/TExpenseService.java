/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.expense.entity.TExpense;
import com.jeesite.modules.expense.dao.TExpenseDao;

/**
 * expenseService
 * @author mkj
 * @version 2018-06-22
 */
@Service
@Transactional(readOnly=true)
public class TExpenseService extends CrudService<TExpenseDao, TExpense> {
	
	@Autowired
	private TExpenseDao tExpenseDap;
	/**
	 * 获取单条数据
	 * @param tExpense
	 * @return
	 */
	@Override
	public TExpense get(TExpense tExpense) {
		return super.get(tExpense);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tExpense
	 * @return
	 */
	@Override
	public Page<TExpense> findPage(Page<TExpense> page, TExpense tExpense) {
		return super.findPage(page, tExpense);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tExpense
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TExpense tExpense) {
		super.save(tExpense);
	}
	
	/**
	 * 更新状态
	 * @param tExpense
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TExpense tExpense) {
		super.updateStatus(tExpense);
	}
	
	/**
	 * 删除数据
	 * @param tExpense
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TExpense tExpense) {
		super.delete(tExpense);
	}

	public TExpense getBypiid(String piid) {
		// TODO Auto-generated method stub
		return tExpenseDap.getBypiid(piid);
	}

	public List<TExpense> getByEmpCode(String userCode) {
		// TODO Auto-generated method stub
		return tExpenseDap.getByEmpCode(userCode);
	}

	public List<TExpense> getCaiwuApproveList(String officeName) {
		// TODO Auto-generated method stub
		return tExpenseDap.getCaiwuApproveList(officeName);
	}

	public List<TExpense> getElseApproveList(String officeName) {
		// TODO Auto-generated method stub
		return tExpenseDap.getElseApproveList(officeName);
	}




	
}