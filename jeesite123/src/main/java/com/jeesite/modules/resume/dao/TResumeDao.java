/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.resume.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.resume.entity.TResume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_resumeDAO接口
 * @author kyrie
 * @version 2018-05-25
 */
@MyBatisDao
public interface TResumeDao extends CrudDao<TResume> {
    TResume findByUserCode(String userCode);
    List<String> findRole(@Param("userCode")String userCode);
}