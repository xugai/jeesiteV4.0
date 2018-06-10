/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.proposal.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.employee.dao.MyEmployeeDao;
import com.jeesite.modules.proposal.entity.Proposal;
import com.jeesite.modules.proposal.service.ProposalService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 提案管理Controller
 * @author BeHe
 * @version 2018-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/proposal/proposal")
public class ProposalController extends BaseController {

	@Autowired
	private ProposalService proposalService;

	@Autowired
	private MyEmployeeDao myEmployeeDao;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Proposal get(String proId, boolean isNewRecord) {
		return proposalService.get(proId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
//	@RequiresPermissions("proposal:proposal:view")
	@RequestMapping(value = {"list", ""})
	public String list(Proposal proposal, Model model, HttpServletRequest request) {
		//从当前会话中获取isDept标识属性，若内容为true则说明当前用户是部门经理；否则为普通员工
		//mapping url 为 */*/form 的身份判断逻辑一样
		String isDept = (String) request.getSession().getAttribute("isDept");
		//如果标识属性为空则直接访问Dao层进行获取遍历，不为空则直接拿来判断
		if(StringUtils.isBlank(isDept)) {
			com.jeesite.modules.sys.entity.Employee employee = (com.jeesite.modules.sys.entity.Employee) UserUtils.getUser().getRefObj();
			String empCode = employee.getEmpCode();
			List<String> postCodeList = myEmployeeDao.getPostcodeByEmpcode(empCode);
			for (String postCode : postCodeList) {
				if ("dept".equals(postCode)) {
					//若判断到当前用户是部门经理，则把身份标识isDept放入当前会话中，用以后续访问方便拿取
					isDept = "true";
					request.getSession().setAttribute("isDept", isDept);
					model.addAttribute("proposal", proposal);
					return "modules/proposal/proposalList2";
				}
			}
			//若判断到当前用户是普通员工，则把身份标识isDept放入当前会话中，用以后续访问方便拿取
			isDept = "false";
			request.getSession().setAttribute("isDept", isDept);
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalList1";
		}else if("true".equals(isDept)){
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalList2";
		}else if("false".equals(isDept)){
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalList1";
		}
		return "modules/proposal/proposalList";
	}
	
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("proposal:proposal:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Proposal> listData(Proposal proposal, HttpServletRequest request, HttpServletResponse response) {
		String isDept = (String) request.getSession().getAttribute("isDept");
		if(StringUtils.isBlank(isDept)){
			System.out.println("无法辨别当前用户身份是经理还是普通员工！");
			return null;
		}
		if("true".equals(isDept)) {
			Page<Proposal> page = proposalService.findPage(new Page<Proposal>(request, response), proposal);
			return page;
		}else{
			Page<Proposal> page = proposalService.findMyProposal(new Page<Proposal>(request,response), proposal);
			return page;
		}
	}

	/**
	 * 查看编辑表单
	 */
//	@RequiresPermissions("proposal:proposal:view")
	@RequestMapping(value = "form")
	public String form(Proposal proposal, Model model, HttpServletRequest request) {
		String isDept = (String) request.getSession().getAttribute("isDept");
		if(StringUtils.isBlank(isDept)) {
			com.jeesite.modules.sys.entity.Employee employee = (com.jeesite.modules.sys.entity.Employee) UserUtils.getUser().getRefObj();
			String empCode = employee.getEmpCode();
			List<String> postCodeList = myEmployeeDao.getPostcodeByEmpcode(empCode);
			for (String postCode : postCodeList) {
				if ("dept".equals(postCode)) {
					isDept = "true";
					request.getSession().setAttribute("isDept", isDept);
					model.addAttribute("proposal", proposal);
					return "modules/proposal/proposalForm2";
				}
			}
			isDept = "false";
			request.getSession().setAttribute("isDept", isDept);
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalForm1";
		}else if("true".equals(isDept)){
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalForm2";
		}else if("false".equals(isDept)){
			model.addAttribute("proposal", proposal);
			return "modules/proposal/proposalForm1";
		}
		model.addAttribute("proposal", proposal);
		return "modules/proposal/proposalForm";
	}

	/**
	 * 保存提案管理
	 */
//	@RequiresPermissions("proposal:proposal:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Proposal proposal, String proEmpId, @RequestParam(required = false) String advice,
					   HttpServletRequest request) {
		proposal.setProEmpId(proEmpId);
		proposalService.save(proposal, advice);
		String isDept = (String) request.getSession().getAttribute("isDept");
		if("true".equals(isDept)){
			return renderResult(Global.TRUE, "提案审核成功！");
		}else if("false".equals(isDept)){
			return renderResult(Global.TRUE, "提案申报成功！");
		}
		return renderResult(Global.TRUE, "提案成功！");
	}
	
	/**
	 * 删除提案管理
	 */
	@RequiresPermissions("proposal:proposal:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Proposal proposal) {
		proposalService.delete(proposal);
		return renderResult(Global.TRUE, "删除提案管理成功！");
	}


	
}