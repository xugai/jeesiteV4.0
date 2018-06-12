/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.guestbook.web;

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
import com.jeesite.modules.guestbook.entity.CmsGuestbook;
import com.jeesite.modules.guestbook.service.CmsGuestbookService;

/**
 * cms_guestbookController
 * @author redandelion
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/guestbook/cmsGuestbook")
public class CmsGuestbookController extends BaseController {

	@Autowired
	private CmsGuestbookService cmsGuestbookService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CmsGuestbook get(String id, boolean isNewRecord) {
		return cmsGuestbookService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("guestbook:cmsGuestbook:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsGuestbook cmsGuestbook, Model model) {

		model.addAttribute("cmsGuestbook", cmsGuestbook);
		return "modules/guestbook/cmsGuestbookList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("guestbook:cmsGuestbook:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CmsGuestbook> listData(CmsGuestbook cmsGuestbook, HttpServletRequest request, HttpServletResponse response) {
		Page<CmsGuestbook> page = cmsGuestbookService.findPage(new Page<CmsGuestbook>(request, response), cmsGuestbook); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("guestbook:cmsGuestbook:view")
	@RequestMapping(value = "form")
	public String form(CmsGuestbook cmsGuestbook, Model model) {
		model.addAttribute("cmsGuestbook", cmsGuestbook);
		return "modules/guestbook/cmsGuestbookForm";
	}

	/**
	 * 保存cms_guestbook
	 */
	@RequiresPermissions("guestbook:cmsGuestbook:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CmsGuestbook cmsGuestbook) {
		cmsGuestbookService.save(cmsGuestbook);
		return renderResult(Global.TRUE, "保存cms_guestbook成功！");
	}
	
	/**
	 * 删除cms_guestbook
	 */
	@RequiresPermissions("guestbook:cmsGuestbook:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CmsGuestbook cmsGuestbook) {
		cmsGuestbookService.delete(cmsGuestbook);
		return renderResult(Global.TRUE, "删除cms_guestbook成功！");
	}

	//	留言管理,查询留言以及评论
	@RequiresPermissions("guestbook:cmsGuestbook:view")
	@RequestMapping(value = "guestbook")
	public String guestbook(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/guestbook/cmsGuestbook";
	}
	@RequiresPermissions("guestbook:cmsGuestbook:view")
	@RequestMapping(value = "guestbookData")
	@ResponseBody
	public Page guestbookData() {
		// 调用service
		Page page = cmsGuestbookService.cmsGuestbook();
		return page;
	}
}