/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.comment.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.comment.entity.CmsComment;

import java.util.List;

/**
 * cms_commentDAO接口
 * @author redandelion
 * @version 2018-06-07
 */
@MyBatisDao
public interface CmsCommentDao extends CrudDao<CmsComment> {
    public List<CmsComment> findByGusetbook(CmsComment cmsComment);
    public void deleteByGusetbookId(CmsComment cmsComment);
}