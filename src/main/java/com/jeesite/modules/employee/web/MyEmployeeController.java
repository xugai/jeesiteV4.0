/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.employee.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.employee.entity.MyEmployee;
import com.jeesite.modules.employee.service.MyEmployeeService;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.vacate.entity.Vacate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工表Controller
 * @author kyrie
 * @version 2018-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/employee/myEmployee")
public class MyEmployeeController extends BaseController {

	@Autowired
	private MyEmployeeService myEmployeeService;
	@Autowired
	private EmpUserService empUserService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MyEmployee get(String empCode, boolean isNewRecord) {
		return myEmployeeService.get(empCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("employee:myEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(MyEmployee myEmployee, Model model, HttpServletRequest request) {
		//判断登录用户的身份（员工|管理员）
		User user = UserUtils.getUser();
		if (user.isAdmin()){
			System.out.println("管理员");
			request.getSession().setAttribute("role","manager");
			model.addAttribute("role","manager");
			//管理员查看所有的员工信息
			model.addAttribute("myEmployee", myEmployee);
			return "modules/employee/myEmployeeList";
		}else{
			//判断登录员工属于基本员工还是部长
			EmpUser empUser  =  new EmpUser();
			Employee employee = new Employee();
			employee.setEmpCode(EmpUtils.getEmployee().getEmpCode());
			empUser.setRoleCode("user");
			empUser.setEmployee(employee);
			List<EmpUser> empUserList = empUserService.findList(empUser);
			if (empUserList != null && empUserList.size()>0) {
				System.out.println("普通员工:"+empUserList.size());
				request.getSession().setAttribute("role","user");
				model.addAttribute("role","user");
				EmpUser empUser1 = empUserList.get(0);
				employee = empUser1.getEmployee();
				myEmployee = myEmployeeService.get(employee.getEmpCode());
				//员工只能查看自己的员工信息
				model.addAttribute("myEmployee", myEmployee);
				return "modules/employee/myEmployeeShow";
			}else{
				System.out.println("部长。");
				request.getSession().setAttribute("roleCode","user");
				request.getSession().setAttribute("role","dept");
				model.addAttribute("role","dept");
				//部长查看所有的员工信息，不包括部长
				model.addAttribute("myEmployee", myEmployee);
				return "modules/employee/myEmployeeList";

			}
		}

	}


	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("employee:myEmployee:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MyEmployee> listData(MyEmployee myEmployee, HttpServletRequest request, HttpServletResponse response,Model model) {
		System.out.println("listData");
		myEmployee.getOffice().setIsQueryChildren(true);
		myEmployee.getCompany().setIsQueryChildren(true);
		Page<MyEmployee> page = new Page<MyEmployee>(request, response);
		//根据角色返回数据
		try{
			String roleCode = request.getSession().getAttribute("roleCode").toString();
			System.out.println(roleCode);
			if (roleCode != null) {
				myEmployee.setRoleCode(roleCode);

			}
			request.getSession().setAttribute("roleCode",null);
		}catch (NullPointerException ex){
			ex.getStackTrace();
		}
		page = myEmployeeService.findListByRole(page,myEmployee);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("employee:myEmployee:view")
	@RequestMapping(value = "form")
	public String form(MyEmployee myEmployee,String op, Model model, HttpServletRequest request) {
		model.addAttribute("role",request.getSession().getAttribute("role"));
//		try {
//		    String userType = request.getSession().getAttribute("role").toString();
//		    if (userType.equals("user")){
//		        myEmployee.setEmpCode(EmpUtils.getEmployee().getEmpCode());
//            }
//        }catch (NullPointerException ex){
//		    ex.getStackTrace();
//        }
        model.addAttribute("myEmployee", myEmployee);
		model.addAttribute("op", op);
		return "modules/employee/myEmployeeForm";
	}

	/**
	 * 保存员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MyEmployee myEmployee) {
		myEmployeeService.save(myEmployee);
		return renderResult(Global.TRUE, "保存员工表成功！");
	}
	/**
	 * 修改员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@PostMapping(value = "SaveOrUpdate")
	@ResponseBody
	public String SaveOrUpdate(@Validated MyEmployee myEmployee, HttpServletRequest request,Model model) {
		String role = request.getSession().getAttribute("role").toString();
		if (role.equals("dept") ){
			//部长操作
			update(myEmployee);
			return renderResult(Global.TRUE, "修改权限成功！");
		}else if (role.equals("user")){
			//员工操作
			myEmployee.setFlag("0");
			update(myEmployee);
			return renderResult(Global.TRUE, "修改员工信息成功！");
		}else{
			//管理员操作
			save(myEmployee);
			return renderResult(Global.TRUE, "新增员工成功！");
		}
	}

	/**
	 * 修改员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@PostMapping(value = "update")
	@ResponseBody
	public String update(@Validated MyEmployee myEmployee) {
		myEmployeeService.update(myEmployee);
		return renderResult(Global.TRUE, "保存员工表成功！");
	}

	/**
	 * 停用员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(MyEmployee myEmployee) {
		myEmployee.setStatus(MyEmployee.STATUS_DISABLE);
		myEmployeeService.updateStatus(myEmployee);
		return renderResult(Global.TRUE, "停用员工表成功");
	}
	
	/**
	 * 启用员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(MyEmployee myEmployee) {
		myEmployee.setStatus(MyEmployee.STATUS_NORMAL);
		myEmployeeService.updateStatus(myEmployee);
		return renderResult(Global.TRUE, "启用员工表成功");
	}
	
	/**
	 * 删除员工表
	 */
	@RequiresPermissions("employee:myEmployee:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MyEmployee myEmployee) {
		myEmployeeService.delete(myEmployee);
		return renderResult(Global.TRUE, "删除员工表成功！");
	}


	/**
	 * 员工创建新的请假单
	 * @author BeHe
	 * @return
	 */
	@RequestMapping(value = "create_vacation")
	public String createVacation(Vacate vacate, Model model){
//		Vacate vacate = new Vacate();
		model.addAttribute("vacate", vacate);
		return "modules/vacate/vacateForm1";
	}

	/**
	 * 普通员工进行请假申请
	 * @author BeHe
	 * @param startTime
	 * @param endTime
	 * @param empReason
	 */
	@RequestMapping(value = "do_rest_apply", method = RequestMethod.POST)
	@ResponseBody
	public String restApply(String startTime, String endTime, String empReason){
		User user = UserUtils.getUser();
		com.jeesite.modules.sys.entity.Employee employee = (com.jeesite.modules.sys.entity.Employee) user.getRefObj();
		if("true".equals(myEmployeeService.restApply(employee.getEmpName(), startTime, endTime, empReason))){
			return renderResult(Global.TRUE, "提交请假单成功！");
		}else{
			return renderResult(Global.FALSE, "请假单提交失败，请重试！");
		}
	}

	/**
	 *部门经理提交审核
	 * @author BeHe
	 * @param status
	 * @param manReason
	 * @return
	 */
	@RequestMapping(value = "handle_task", method = RequestMethod.POST)
	@ResponseBody
	public String handleTask(String vaId, String empCode, String status, String manReason){
		User user = UserUtils.getUser();
		if("true".equals(myEmployeeService.handleTask(vaId, empCode, status, manReason, user.getUserCode()))){
			return renderResult(Global.TRUE, "审核成功！");
		}
		return renderResult(Global.FALSE, "审核失败，请查看错误原因！");
	}
}