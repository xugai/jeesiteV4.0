/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.proposal.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.proposal.dao.ProposalDao;
import com.jeesite.modules.proposal.entity.Proposal;
import com.jeesite.modules.sys.activiti.proposalActiviti;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提案管理Service
 * @author BeHe
 * @version 2018-06-03
 */
@Service
@Transactional(readOnly=true)
public class ProposalService extends CrudService<ProposalDao, Proposal> {

	@Autowired
	private ProposalDao proposalDao;

	/**
	 * 获取单条数据
	 * @param proposal
	 * @return
	 */
	@Override
	public Proposal get(Proposal proposal) {
		return super.get(proposal);
	}
	
	/**
	 * 部门经理查询分页数据
	 * @param page 分页对象
	 * @param proposal
	 * @return
	 */
	public Page<Proposal> findPage(Page<Proposal> page, Proposal proposal) {
		proposal.setPage(page);
		com.jeesite.modules.sys.entity.Employee employee = (com.jeesite.modules.sys.entity.Employee) UserUtils.getUser().getRefObj();
		String officeName = employee.getOffice().getOfficeName();
		List<Proposal> proposalList = proposalDao.getHandleProposal(proposal);
		List<Proposal> proposals = new ArrayList<Proposal>();
		for(Proposal p : proposalList){
			if(p.getProStatus().contains(officeName) || p.getProStatus().contains("双方")){
				proposals.add(p);
			}
		}
		page.setList(proposals);
		page.setCount(proposals.size());
		return page;
	}

	/**
	 * 普通员工查看自己已提交的提案
	 * @param page
	 * @param proposal
     * @return
     */
	public Page<Proposal> findMyProposal(Page<Proposal> page, Proposal proposal){
		proposal.setPage(page);
		proposal.setProEmpId(UserUtils.getUser().getUserCode());
		List<Proposal> proposalList = proposalDao.getProposal(proposal);
		page.setList(proposalList);
		page.setCount(proposalList.size());
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param proposal
	 */
	@Transactional(readOnly=false)
	public void save(Proposal proposal, String advice) {
		//如果当前传入的提案持久化类的主键为空，则做新增操作；否则做更新操作
		if(StringUtils.isBlank(proposal.getProId())) {
			String empName = UserUtils.getUser().getUserName();
			String boss1 = proposalDao.getMinisterName("研发部");
			String boss2 = proposalDao.getMinisterName("运营部");
			if (StringUtils.isBlank(boss1) || StringUtils.isBlank(boss2)) {
				System.out.println("不存在指定部门的部门经理？");
				return;
			}
			//调用提案工作流，开启流程实例
			String proId = proposalActiviti.startProcessEngine(empName, boss1, boss2);
			proposal.setProId(proId);
			proposal.setIsNewRecord(true);
			super.save(proposal);
			//上传员工提交的提案
			FileUploadUtils.saveFileUpload(proId, "proposal_type");
		}
		//部门经理审核提案并给出审核意见（"true" or "false"）
		else{
			com.jeesite.modules.sys.entity.Employee employee = (com.jeesite.modules.sys.entity.Employee) UserUtils.getUser().getRefObj();
			String officeName = employee.getOffice().getOfficeName();
			String result = proposalActiviti.handleTask(proposal.getProId(), officeName, advice);
			if(!StringUtils.isBlank(result)){
				System.out.println("进入修改提案结束时间、审核状态、审核结果的分支？？？？？？");
				Date date = new Date();
				proposal.setEndDate(date);
				proposal.setProStatus("审核完毕");
				proposal.setProResult(result);
				proposal.setIsNewRecord(false);
				super.save(proposal);
				return;
//				proposalDao.updateProposalEndTime(proposal.getProId(), date);
			}
			String newStatus = "";
			if("研发部".equals(officeName)){
				newStatus = "（运营部）单方审核中";
			}else if("运营部".equals(officeName)){
				newStatus = "（研发部）单方审核中";
			}
			proposalDao.updateProposalStatus(proposal.getProId(), newStatus);
		}
	}
	
	/**
	 * 更新状态
	 * @param proposal
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Proposal proposal) {
		super.updateStatus(proposal);
	}
	
	/**
	 * 删除数据
	 * @param proposal
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Proposal proposal) {
		super.delete(proposal);
	}
	
}