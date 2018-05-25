/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.service;


import com.jeesite.common.service.CrudService;
import com.jeesite.modules.employee.dao.MyEmployeeDao;
import com.jeesite.modules.employee.entity.MyEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;

import java.util.List;


/**
 * 员工表Service
 * @author kyrie
 * @version 2018-05-18
 */
@Service
@Transactional(readOnly=true)
public class MyEmployeeService extends CrudService<MyEmployeeDao, MyEmployee> {

	@Autowired
	MyEmployeeDao myEmployeeDao;
	/**
	 * 获取单条数据
	 * @param myEmployee
	 * @return
	 */
	@Override
	public MyEmployee get(MyEmployee myEmployee) {
		return super.get(myEmployee);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param myEmployee
	 * @return
	 */
	@Override
	public Page<MyEmployee> findPage(Page<MyEmployee> page, MyEmployee myEmployee) {
		return super.findPage(page, myEmployee);
	}
	/**
	 * 根据用户角色查询数据（插入或更新）
	 * @param page,myEmployee
	 * @return
	 */
	public Page<MyEmployee> findListByRole(Page page,MyEmployee myEmployee) {
		int first = (page.getPageNo()-1)*page.getPageSize();
		System.out.println("first:"+first);
		System.out.println("role:"+myEmployee.getRoleCode());
		page.setCount(myEmployeeDao.findCountByRole(myEmployee.getRoleCode()));
		page.setList(myEmployeeDao.findListByRole(first,page.getPageSize(),myEmployee));
		System.out.println("count:"+page.getCount());
		return page;
	}

	public List<MyEmployee> Test(MyEmployee myEmployee) {
		return myEmployeeDao.findList(myEmployee);
	}
	/**
	 * 保存数据（插入或更新）
	 * @param myEmployee
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MyEmployee myEmployee) {
		super.save(myEmployee);
	}

	/**
	 * 更新状态
	 * @param myEmployee
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MyEmployee myEmployee) {
		super.updateStatus(myEmployee);
	}

	/**
	 * 删除数据
	 * @param myEmployee
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MyEmployee myEmployee) {
		super.delete(myEmployee);
	}

}