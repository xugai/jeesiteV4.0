/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.comment.entity.CmsComment;
import com.jeesite.modules.comment.dao.CmsCommentDao;

/**
 * cms_commentService
 * @author redandelion
 * @version 2018-06-07
 */
@Service
@Transactional(readOnly=true)
public class CmsCommentService extends CrudService<CmsCommentDao, CmsComment> {
	
	/**
	 * 获取单条数据
	 * @param cmsComment
	 * @return
	 */
	@Override
	public CmsComment get(CmsComment cmsComment) {
		return super.get(cmsComment);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param cmsComment
	 * @return
	 */
	@Override
	public Page<CmsComment> findPage(Page<CmsComment> page, CmsComment cmsComment) {
		return super.findPage(page, cmsComment);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param cmsComment
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CmsComment cmsComment) {
		super.save(cmsComment);
	}
	
	/**
	 * 更新状态
	 * @param cmsComment
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CmsComment cmsComment) {
		super.updateStatus(cmsComment);
	}
	
	/**
	 * 删除数据
	 * @param cmsComment
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CmsComment cmsComment) {
		super.delete(cmsComment);
	}

	/**
	 *
	 * @param cmsComment
	 */
	@Transactional(readOnly=false)
	public List<CmsComment> findByBooks(CmsComment cmsComment) {
		return dao.findByGusetbook(cmsComment);
	}
	@Transactional(readOnly=false)
	public void deleteByGuestbookId(CmsComment cmsComment){
		dao.deleteByGusetbookId(cmsComment);
	}
}