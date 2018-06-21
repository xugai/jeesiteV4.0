/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.resume.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.resume.dao.TResumeDao;
import com.jeesite.modules.resume.entity.TResume;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * t_resumeService
 * @author kyrie
 * @version 2018-05-12
 */
@Service
@Transactional(readOnly=true)
public class TResumeService extends CrudService<TResumeDao, TResume> {
	/**
	 * 获取单条数据
	 * @param tResume
	 * @return
	 */
	@Override
	public TResume get(TResume tResume) {
		return super.get(tResume);
	}

	public TResume findByUserCode(String userCode) {
		System.out.println("service------------->"+userCode);
		TResume tResume = dao.findByUserCode(userCode);
		System.out.println("dao");
		return tResume;
	}
	//查找用户的角色
	public List<String> findRole(String userCode) {
		return dao.findRole(userCode);
	}
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tResume
	 * @return
	 */
	@Override
	public Page<TResume> findPage(Page<TResume> page, TResume tResume) {
		return super.findPage(page, tResume);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tResume
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TResume tResume) {
		super.save(tResume);
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tResume.getReId(), "tResume_file");
	}

	/**
	 * 更新状态
	 * @param tResume
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TResume tResume) {
		super.updateStatus(tResume);
	}

	/**
	 * 删除数据
	 * @param tResume
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TResume tResume) {
		super.delete(tResume);
	}


}