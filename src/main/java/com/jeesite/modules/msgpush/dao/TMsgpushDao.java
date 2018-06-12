/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.msgpush.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.msgpush.entity.TMsgpush;

/**
 * t_msgpushDAO接口
 * @author redandelion
 * @version 2018-06-05
 */
@MyBatisDao
public interface TMsgpushDao extends CrudDao<TMsgpush> {
	
}