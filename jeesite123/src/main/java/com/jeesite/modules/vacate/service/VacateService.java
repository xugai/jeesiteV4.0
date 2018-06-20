/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.vacate.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.activiti.activitiEngine;
import com.jeesite.modules.sys.activiti.vacateActiviti;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.vacate.dao.VacateDao;
import com.jeesite.modules.vacate.entity.Vacate;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 请假表信息维护Service
 * @author BeHe
 * @version 2018-05-24
 */
@Service
@Transactional(readOnly=true)
public class VacateService extends CrudService<VacateDao, Vacate> {

	@Autowired
	private VacateDao vacateDao;

	/**
	 * 获取单条数据
	 * @param vacate
	 * @return
	 */
	@Override
	public Vacate get(Vacate vacate) {
		return super.get(vacate);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param vacate
	 * @return
	 */
	@Override
	public Page<Vacate> findPage(Page<Vacate> page, Vacate vacate) {
		//设置页面显示记录数为3条
		page.setPageSize(3);
		vacate.setPage(page);
		//设置分页查询时不使用 status = ? 这个查询条件
		vacate.getSqlMap().getWhere().disableAutoAddStatusWhere();
		User user = UserUtils.getUser();
		vacate.setEmpCode(user.getUserCode());
		page.setList(this.queryVacation(vacate));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param vacate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Vacate vacate) {
		super.save(vacate);
	}

	/**
	 * 更新状态
	 * @param vacate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Vacate vacate) {
		super.updateStatus(vacate);
	}

	/**
	 * 删除数据
	 * @param vacate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Vacate vacate) {
		super.delete(vacate);
	}

	public String getVaId(String empCode){
		if(StringUtils.isNotBlank(empCode)){
			return vacateDao.getVaIdByUpdator(empCode);
		}else{
			System.out.println("员工编号为空或不存在！");
			return "null";
		}
	}

	public List<Vacate> queryVacation(Vacate vacate){
		//判断传进来的员工编号是否为空
		if(StringUtils.isBlank(vacate.getEmpCode())){
			return null;
		}
		return vacateDao.queryVacation(vacate);
	}

	/**
	 * 通过经理审批后更新当前请假单信息
	 * @param vaId
	 * @param manReason
	 * @param status
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateByVerify(String vaId, String manReason, String status, String updator){
		if(StringUtils.isBlank(vaId) || StringUtils.isBlank(manReason) || StringUtils.isBlank(status)){
			return 0;
		}else{
			return vacateDao.updateByVerify(vaId, manReason, status, updator);
		}
	}

	/**
	 * 查询当前经理需要处理的请假单
	 * @param manName
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Vacate> queryTask(Page<Vacate> page, String manName){
		List<Vacate> vacateList = new ArrayList<Vacate>();
		List<Task> taskList = vacateActiviti.queryTask(manName);
		if(taskList != null && taskList.size() > 0){
			TaskService taskService = activitiEngine.processEngine.getTaskService();
			for(Task task: taskList){
				//获取请假员工的姓名
				String empName = (String) taskService.getVariable(task.getId(), "base");
				//通过员工姓名获取员工编码
				String empCode = vacateDao.getEmpCodeByEmpName(empName);
				//实例化请假表
				Vacate vacate = new Vacate();
				//通过员工编号获得请假表
				vacate = vacateDao.getByEmpCode(empCode);
				vacate.setPage(page);
				vacateList.add(vacate);
			}
			page.setList(vacateList);
			page.setCount(vacateList.size());
			return page;
		}
		return null;
	}
	
}