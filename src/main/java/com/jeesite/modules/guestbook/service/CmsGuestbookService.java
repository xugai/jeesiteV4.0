/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.guestbook.service;


import com.jeesite.modules.comment.entity.CmsComment;
import com.jeesite.modules.comment.service.CmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.guestbook.entity.CmsGuestbook;
import com.jeesite.modules.guestbook.dao.CmsGuestbookDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cms_guestbookService
 * @author redandelion
 * @version 2018-06-07
 */
@Service
@Transactional(readOnly=true)
public class CmsGuestbookService extends CrudService<CmsGuestbookDao, CmsGuestbook> {
	@Autowired
	private CmsCommentService cmsCommentService;
	/**
	 * 获取单条数据
	 * @param cmsGuestbook
	 * @return
	 */
	@Override
	public CmsGuestbook get(CmsGuestbook cmsGuestbook) {
		return super.get(cmsGuestbook);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param cmsGuestbook
	 * @return
	 */
	@Override
	public Page<CmsGuestbook> findPage(Page<CmsGuestbook> page, CmsGuestbook cmsGuestbook) {
		return super.findPage(page, cmsGuestbook);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param cmsGuestbook
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CmsGuestbook cmsGuestbook) {
		super.save(cmsGuestbook);
	}
	
	/**
	 * 更新状态
	 * @param cmsGuestbook
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CmsGuestbook cmsGuestbook) {
		super.updateStatus(cmsGuestbook);
	}
	
	/**
	 * 删除数据
	 * @param cmsGuestbook
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CmsGuestbook cmsGuestbook) {
		CmsComment cmsComment = new CmsComment();
		cmsComment.setContentId(cmsGuestbook.getId());
		// 根据留言ID删除评论
		cmsCommentService.deleteByGuestbookId(cmsComment);
		super.delete(cmsGuestbook);
	}

	@Transactional(readOnly=false)
	public Page cmsGuestbook(){
		Map map =new HashMap<String,List<CmsComment>>();
		List<CmsGuestbook> list = dao.findList(new CmsGuestbook());
		Page page = new Page();
		page.setList(list);
		List<CmsComment> cmsCommentList = new ArrayList<CmsComment>();
		for (CmsGuestbook guestbook:list){
			CmsComment cmsComment= new  CmsComment();
			cmsComment.setContentId(guestbook.getId());
			cmsCommentList = cmsCommentService.findByBooks(cmsComment);
			map.put(guestbook.getId(),cmsCommentList);
		}
		page.setOtherData(map);
		return page;
	}

}