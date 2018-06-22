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
    /**
     *
     * @param userCode
     * @return
     */
    TResume findByUserCode(String userCode);

    /**
     *
     * @param userCode
     * @return
     */
    List<String> findRole(@Param("userCode")String userCode);

    /**
     * 查找当前页面所属部门的简历数据
     * @param first
     * @param pageSize
     * @return
     */
    List<TResume> findListByOffice(@Param("first")int first, @Param("pageSize")int pageSize, @Param("tResume")TResume tResume);
    /**
     * 查找当前部门的简历数据量
     * @param tResume
     * @return
     */
    Long findCountByOffice(TResume tResume);
}