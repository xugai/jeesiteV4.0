/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.comment.web;

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
import com.jeesite.modules.comment.entity.CmsComment;
import com.jeesite.modules.comment.service.CmsCommentService;

import java.util.List;

/**
 * cms_commentController
 * @author redandelion
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/comment/cmsComment")
public class CmsCommentController extends BaseController {

	@Autowired
	private CmsCommentService cmsCommentService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CmsComment get(String id, boolean isNewRecord) {
		return cmsCommentService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
//	@RequiresPermissions("comment:cmsComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsComment cmsComment, Model model) {
		model.addAttribute("cmsComment", cmsComment);
		return "modules/comment/cmsCommentList";
	}
	
	/**
	 * 查询列表数据
	 */
//	@RequiresPermissions("comment:cmsComment:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CmsComment> listData(CmsComment cmsComment, HttpServletRequest request, HttpServletResponse response) {
		Page<CmsComment> page = cmsCommentService.findPage(new Page<CmsComment>(request, response), cmsComment); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
//	@RequiresPermissions("comment:cmsComment:view")
	@RequestMapping(value = "form")
	public String form(CmsComment cmsComment, Model model) {
		model.addAttribute("cmsComment", cmsComment);
		return "modules/comment/cmsCommentForm";
	}

	/**
	 * 保存cms_comment
	 */
//	@RequiresPermissions("comment:cmsComment:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CmsComment cmsComment) {
		cmsCommentService.save(cmsComment);
		return renderResult(Global.TRUE, "保存cms_comment成功！");
	}
	
	/**
	 * 删除cms_comment
	 */
//	@RequiresPermissions("comment:cmsComment:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CmsComment cmsComment) {
		cmsCommentService.delete(cmsComment);
		return renderResult(Global.TRUE, "删除cms_comment成功！");
	}
	// 文章ID查询
	@RequestMapping(value = "findByGusetBookId")
	@ResponseBody
	public Page<CmsComment> findByGusetBookId(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request);
		List<CmsComment> cmsCommentList= null;
		Page page = new Page();
		CmsComment cmsComment = new CmsComment();
		if(!request.getParameter("contentId").isEmpty()){
			//设置查询ID
			cmsComment.setContentId(request.getParameter("contentId"));
			cmsCommentList = cmsCommentService.findByBooks(cmsComment);
			// 封装为PAge对象
			page.setList(cmsCommentList);
		}
		return page;
	}
}