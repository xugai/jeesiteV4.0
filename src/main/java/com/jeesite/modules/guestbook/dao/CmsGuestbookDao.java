/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.guestbook.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.guestbook.entity.CmsGuestbook;

/**
 * cms_guestbookDAO接口
 * @author redandelion
 * @version 2018-06-07
 */
@MyBatisDao
public interface CmsGuestbookDao extends CrudDao<CmsGuestbook> {
	
}