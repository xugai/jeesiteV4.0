/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.vacate.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.vacate.entity.Vacate;
import com.jeesite.modules.vacate.service.VacateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请假表信息维护Controller
 * @author BeHe
 * @version 2018-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/vacate/vacate")
public class VacateController extends BaseController {

	@Autowired
	private VacateService vacateService;

	/**
	 * 获取数据
	 * 说明一下，被 @ModelAttribute 注解的方法会在Controller每个方法执行之前都执行，因此对于一个Controller中包含多个URL的时候，要谨慎使用
	 */
	/*@ModelAttribute
	public Vacate get(String vaId, boolean isNewRecord) {
		return vacateService.get(vaId, isNewRecord);
	}*/

	/**
	 * 查询列表
	 */
	@RequiresPermissions("vacation:vacate:view")
	@RequestMapping(value = {"list", ""})
	public String list(Vacate vacate, Model model) {
		model.addAttribute("vacate", vacate);
		return "modules/vacation/vacateList2";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Vacate> listData(Vacate vacate, HttpServletRequest request, HttpServletResponse response) {
		Page<Vacate> page = vacateService.findPage(new Page<Vacate>(request, response), vacate);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:dept:view")
	@RequestMapping(value = "form")
	public String form(String vaId, boolean isNewRecord, Model model) {
		model.addAttribute("vacate", vacateService.get(vaId, isNewRecord));
		return "modules/vacate/vacateForm2";
	}

	/**
	 * 保存请假表信息维护
	 */
	@RequiresPermissions("vacation:vacate:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Vacate vacate) {
		vacateService.save(vacate);
		return renderResult(Global.TRUE, "保存请假表信息维护成功！");
	}

	/**
	 * 删除请假表信息维护
	 */
	@RequiresPermissions("vacation:vacate:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Vacate vacate) {
		vacateService.delete(vacate);
		return renderResult(Global.TRUE, "删除请假表信息维护成功！");
	}


	/**
	 * 普通员工点击查询请假单后需要跳转的页面
	 * @return
	 */
	@RequestMapping(value = "list_vacation")
	public String listVacation(Model model){
		Vacate vacate = new Vacate();
		model.addAttribute("vacate", vacate);
		return "modules/vacate/vacateList1";
	}

	/**
	 * 普通员工查询当前需要处理的请假单（进入查询请假单页面后自动执行的逻辑）
	 * @return
	 */
	@RequestMapping(value = "query_vacation")
	@ResponseBody
	public Page<Vacate> queryVacation(Vacate vacate, HttpServletRequest request, HttpServletResponse response){
//		User user = UserUtils.getUser();
		System.out.println(vacate);
		Page<Vacate> page =  vacateService.findPage(new Page<Vacate>(request, response), vacate);
		return page;
	}

	/**
	 * 部门经理点击查询请假单后需要跳转的页面
	 * @return
	 */
	@RequestMapping(value = "list_task")
	public String listTask(Model model){
		Vacate vacate = new Vacate();
		model.addAttribute("vacate", vacate);
		return "modules/vacate/vacateList2";
	}

	/**
	 * 部门经理查询当前需要处理的请假单（进入查询请假单页面后自动执行的逻辑）
	 * @return
	 */
	@RequestMapping(value = "query_task", method = RequestMethod.POST)
	@ResponseBody
	public Page<Vacate> queryTask(HttpServletRequest request, HttpServletResponse response){
		//获取部门经理的姓名
		Employee employee = (com.jeesite.modules.sys.entity.Employee) UserUtils.getUser().getRefObj();
		String manName = employee.getEmpName();
		return vacateService.queryTask(new Page<Vacate>(request,response,3) ,manName);
	}
	
}