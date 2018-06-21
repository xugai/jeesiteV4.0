/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.proposal.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.proposal.entity.Proposal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提案管理DAO接口
 * @author BeHe
 * @version 2018-06-03
 */
@MyBatisDao
public interface ProposalDao extends CrudDao<Proposal> {

    /**
     * 通过部门名获得该部门部长的姓名
     * @param officeName
     * @return
     */
    public String getMinisterName(String officeName);

    /**
     * 修改提案中的审核情况
     * @param newStatus
     */
    public void updateProposalStatus(@Param("proId") String proId,
                                     @Param("newStatus")String newStatus);


    public List<Proposal> getHandleProposal(Proposal proposal);

    /**
     * 获取该员工所提交过的所有提案
     * @param proposal
     * @return
     */
    public List<Proposal> getProposal(Proposal proposal);
}