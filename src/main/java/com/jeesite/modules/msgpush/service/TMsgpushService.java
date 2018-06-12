/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.msgpush.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.msgpush.entity.TMsgpush;
import com.jeesite.modules.msgpush.dao.TMsgpushDao;

/**
 * t_msgpushService
 * @author redandelion
 * @version 2018-06-05
 */
@Service
@Transactional(readOnly=true)
public class TMsgpushService extends CrudService<TMsgpushDao, TMsgpush> {
	
	/**
	 * 获取单条数据
	 * @param tMsgpush
	 * @return
	 */
	@Override
	public TMsgpush get(TMsgpush tMsgpush) {
		return super.get(tMsgpush);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tMsgpush
	 * @return
	 */
	@Override
	public Page<TMsgpush> findPage(Page<TMsgpush> page, TMsgpush tMsgpush) {
		return super.findPage(page, tMsgpush);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tMsgpush
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TMsgpush tMsgpush) {
		super.save(tMsgpush);
	}
	
	/**
	 * 更新状态
	 * @param tMsgpush
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TMsgpush tMsgpush) {
		super.updateStatus(tMsgpush);
	}
	
	/**
	 * 删除数据
	 * @param tMsgpush
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TMsgpush tMsgpush) {
		super.delete(tMsgpush);
	}
	
}