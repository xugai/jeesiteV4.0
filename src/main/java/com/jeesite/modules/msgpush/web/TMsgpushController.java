/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.msgpush.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.msgpush.entity.TMsgpush;
import com.jeesite.modules.msgpush.service.TMsgpushService;

/**
 * t_msgpushController
 * @author redandelion
 * @version 2018-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/msgpush/tMsgpush")
public class TMsgpushController extends BaseController {

	@Autowired
	private TMsgpushService tMsgpushService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TMsgpush get(String msgId, boolean isNewRecord) {
		return tMsgpushService.get(msgId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("msgpush:tMsgpush:view")
	@RequestMapping(value = {"list", ""})
	public String list(TMsgpush tMsgpush, Model model) {
		model.addAttribute("tMsgpush", tMsgpush);
		return "modules/msgpush/tMsgpushList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("msgpush:tMsgpush:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TMsgpush> listData(TMsgpush tMsgpush, HttpServletRequest request, HttpServletResponse response) {
		Page<TMsgpush> page = tMsgpushService.findPage(new Page<TMsgpush>(request, response), tMsgpush); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("msgpush:tMsgpush:view")
	@RequestMapping(value = "form")
	public String form(TMsgpush tMsgpush, Model model) {
		model.addAttribute("tMsgpush", tMsgpush);
		return "modules/msgpush/tMsgpushForm";
	}

	/**
	 * 保存t_msgpush
	 */
	@RequiresPermissions("msgpush:tMsgpush:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TMsgpush tMsgpush) {
		tMsgpushService.save(tMsgpush);
		return renderResult(Global.TRUE, "保存t_msgpush成功！");
	}
	
	/**
	 * 删除t_msgpush
	 */
	@RequiresPermissions("msgpush:tMsgpush:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TMsgpush tMsgpush) {
		tMsgpushService.delete(tMsgpush);
		return renderResult(Global.TRUE, "删除t_msgpush成功！");
	}
	
}