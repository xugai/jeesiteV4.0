/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.service;


import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.employee.dao.MyEmployeeDao;
import com.jeesite.modules.employee.entity.MyEmployee;
import com.jeesite.modules.sys.activiti.vacateActiviti;
import com.jeesite.modules.sys.common.Const;
import com.jeesite.modules.sys.util.datetimeUtil;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.vacate.dao.VacateDao;
import com.jeesite.modules.vacate.entity.Vacate;
import com.jeesite.modules.vacate.service.VacateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
	private MyEmployeeDao myEmployeeDao;
	@Autowired
	private VacateDao vacateDao;
	@Autowired
	private VacateService vacateService;
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
		String emp_code = EmpUtils.getEmployee().getEmpCode();
		page.setCount(myEmployeeDao.findCountByRole(myEmployee.getRoleCode(),emp_code));
		page.setList(myEmployeeDao.findListByRole(first,page.getPageSize(),myEmployee,emp_code));
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


	/**
	 * 员工提交请假申请
	 * @author BeHe
	 * @param empName
	 * @param startTime
	 * @param endTime
	 * @param empReason
	 * @return
	 */
	@Transactional(readOnly=false)
	public String restApply(String empName, String startTime,String endTime, String empReason){
		if(StringUtils.isBlank(empName) || StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)
				|| StringUtils.isBlank(empReason)){
			System.out.println("员工的请假信息未填写完整！");
			return "false";
		}
		//通过请假的开始日期和结束日期，自动计算请假天数
		Date date1 = datetimeUtil.strToDate(startTime);
		Date date2 = datetimeUtil.strToDate(endTime);
		long msec1 = date1.getTime();
		long msec2 = date2.getTime();
		long currentDate = System.currentTimeMillis();
		//判断传入的日期是否超前于系统当前时间
		if(msec1 < currentDate || msec2 < currentDate){
			return "dateError1";
		}
		//判断请假开始日期是否滞后于请假结束日期
		if(msec1 > msec2){
			return "dateError2";
		}
		int days = ((int)(msec2 - msec1)) / Const.DAY_MSEC;
		String empCode = myEmployeeDao.getEmpCodeByEmpName(empName);
		//判断用户是否非法提交请假单
		if(vacateDao.getByEmpCode(empCode) != null){
			return "businessError";
		}
		//往请假表中插入相关信息
		Vacate vacate = new Vacate();
//		String empCode = myEmployeeDao.getEmpCodeByEmpName(empName);
		vacate.setEmpCode(empCode);
		vacate.setStartTime(date1);
		vacate.setEndTime(date2);
		vacate.setEmpReason(empReason);
		vacate.setIsNewRecord(true);	//为原生自带的属性进行赋值，告诉框架本次操作是新增记录操作

		//获取当前员工所在的部门编号
		String officeCode = myEmployeeDao.getOfficeCodeByEmpNme(empName);
		//获取当前部门经理的名字
		String manName = myEmployeeDao.getEmpNameByOfficeCode(officeCode);
		//获取本次请假流程的工作流编号，作为请假单的主键请假单编号进行存储
		String vaId = vacateActiviti.startProcessEngine(empName, manName, days, empReason);
		//执行请假流程--->员工提交请假表
		if(!StringUtils.isBlank(vaId)){
			vacate.setVaId(vaId);
			vacateService.save(vacate);
			return "true";
		}else{
			return "false";
		}
	}

	/**
	 * 部门经理提交审核
	 * @author BeHe
	 * @param status
	 * @param manReason
	 * @return
	 */
	@Transactional(readOnly=false)
	public String handleTask(String vaId, String empCode, String status, String manReason, String updator){
		//往当前员工提交的请假表中插入审核状态和审核说明
		vacateService.updateByVerify(vaId, manReason, status, updator);
		//通过当前请假的员工姓名获取他的请假单所对应的任务对象Id
		/*MyEmployee employee = new MyEmployee();
		employee.setEmpCode(empCode);
		employee = myEmployeeDao.get(employee);*/
		String taskId = vacateActiviti.queryTaskId(vaId, UserUtils.getUser().getUserName());
		if(vacateActiviti.handleTask(taskId)){
			return "true";
		}else{
			return "false";
		}
	}
}