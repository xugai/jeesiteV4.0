/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expense.web;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.employee.entity.MyEmployee;
import com.jeesite.modules.employee.service.MyEmployeeService;
import com.jeesite.modules.expense.entity.TExpense;
import com.jeesite.modules.expense.service.TExpenseService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * expenseController
 * @author mkj
 * @version 2018-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/expense/tExpense")
public class TExpenseController extends BaseController {

	@Autowired
	private TExpenseService tExpenseService;
	@Autowired
	private MyEmployeeService myEmployeeService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TExpense get(String exId, boolean isNewRecord) {
		return tExpenseService.get(exId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = {"list", ""})
	public String list(TExpense tExpense, Model model) {
		model.addAttribute("tExpense", tExpense);
		return "modules/expense/tExpenseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TExpense> listData(TExpense tExpense, HttpServletRequest request, HttpServletResponse response) {
		//获取登陆人的编号
		String userCode = UserUtils.getUser().getUserCode();
		tExpense.setEmpCode(userCode);
		// tExpenseService.findPage(new Page<TExpense>(request, response), tExpense);
		Page<TExpense> page = new Page<TExpense>(request, response);
		List<TExpense> list = tExpenseService.getByEmpCode(userCode);
		page.setList(list);
		return page;
	}

	/**
	 * 审批列表
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = "approve")
	public String approveList(TExpense tExpense, Model model) {
		model.addAttribute("tExpense", tExpense);
		return "modules/expense/tExpenseApprove";
	}
	
	/**
	 * 审批列表数据
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = "approveListData")
	@ResponseBody
	public Page<TExpense> approveListData(TExpense tExpense, HttpServletRequest request, HttpServletResponse response) {
		//获取登陆人的编号
		String userCode = UserUtils.getUser().getUserCode();
		MyEmployee myEmployee = myEmployeeService.get(userCode);
		String officeName = myEmployee.getOffice().getOfficeName();
		tExpense.setOffice(officeName);

		Page<TExpense> page = new Page<TExpense>(request, response);
		/*if(officeName.equals("财务部")){
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("财务部").list();
			List<TExpense> tExpenseList = new ArrayList<TExpense>();
			TExpense e = new TExpense();
			for(Task task:list){
				String piid = task.getProcessInstanceId();
				e = tExpenseService.getBypiid(piid);
				tExpenseList.add(e);
			}
			page.setList(tExpenseList);
			return page;
		}else{
			page = tExpenseService.findPage(new Page<TExpense>(request, response), tExpense);
			return page;
		}*/
		List<TExpense> list = new ArrayList<TExpense>();
		if(officeName.equals("财务部")){
			list = tExpenseService.getCaiwuApproveList(officeName);
		}else{
			list = tExpenseService.getElseApproveList(officeName);
		}
		page.setList(list);
		return page;
	}
	
	/**
	 * 查看报销详情
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = "show")
	public String show(TExpense tExpense, Model model) {
		model.addAttribute("tExpense", tExpense);
		return "modules/expense/tExpenseFormShow";
	}

	
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("expense:tExpense:view")
	@RequestMapping(value = "form")
	public String form(TExpense tExpense, Model model) {
		model.addAttribute("tExpense", tExpense);
		return "modules/expense/tExpenseForm";
	}

	/**
	 * 保存expense
	 */
	@RequiresPermissions("expense:tExpense:view")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(TExpense tExpense) {
		//获取登陆人的编号
		String userCode = UserUtils.getUser().getUserCode();
		tExpense.setEmpCode(userCode);
		//获取部门
		MyEmployee myEmployee = myEmployeeService.get(userCode);
		String officeName = myEmployee.getOffice().getOfficeName();
		Map<String,Object> var = new HashMap<String,Object>();
		if(officeName.equals("研发部")){
			var.put("message", 2);
			var.put("manager", "研发部");
			
		}else if(officeName.equals("运营部")){
			var.put("message", 1);
			var.put("manager", "运营部");
			
		}else if(officeName.equals("财务部")){
			var.put("message", 3);
			var.put("manager", "财务部");
		}
		//启动流程
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("expense");
		
		//查询任务
		List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
		String taskId = taskList.get(0).getId();
		//完成员工申请
		processEngine.getTaskService().complete(taskId, var);
		
		//保存tExpense
		tExpense.setPiid(processInstance.getProcessInstanceId());
		tExpense.setOffice(officeName);
		tExpenseService.save(tExpense);
		return renderResult(Global.TRUE, "审批成功！");
	}
	
	/**
	 * 审批通过
	 */
	@RequiresPermissions("expense:tExpense:view")
	@PostMapping(value = "pass")
	@ResponseBody
	public String pass(TExpense tExpense) {
		//获取登陆人的编号
		String userCode = UserUtils.getUser().getUserCode();
		tExpense.setEmpCode(userCode);
		//获取部门
		MyEmployee myEmployee = myEmployeeService.get(userCode);
		String officeName = myEmployee.getOffice().getOfficeName();
		Map<String,Object> var = new HashMap<String,Object>();
		var.put("manager", "财务部");

		TExpense t = tExpenseService.get(tExpense);
		if(officeName.equals("财务部")){
			t.setStatus("2");
		}else{
			t.setStatus("1");
		}
		tExpenseService.updateStatus(t);
		
		//获取流程实例id
		String piid = tExpense.getPiid();
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(piid).singleResult();
		processEngine.getTaskService().complete(task.getId(),var);
		return renderResult(Global.TRUE, "审批成功！");
	}
	
	/**
	 * 驳回
	 */
	@RequiresPermissions("expense:tExpense:view") 
	@RequestMapping(value = "reject")
	@ResponseBody
	public String reject(TExpense t) {
		/*Map<String,Object> var = new HashMap<String,Object>();
		var.put("message",-1);*/
		//获取流程实例id
		TExpense tExpense = tExpenseService.get(t.getId());
		String piid = tExpense.getPiid();
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService().deleteProcessInstance(piid, "");
		tExpense.setStatus("3");
		tExpenseService.updateStatus(tExpense);
		return renderResult(Global.TRUE, "驳回成功！");
	}
	
	
	
	
	/**
	 * 删除expense
	 */
	@RequiresPermissions("expense:tExpense:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TExpense tExpense) {
		tExpenseService.delete(tExpense);
		return renderResult(Global.TRUE, "删除expense成功！");
	}
	
}