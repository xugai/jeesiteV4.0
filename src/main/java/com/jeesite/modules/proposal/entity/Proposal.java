/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.proposal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 提案管理Entity
 * @author BeHe
 * @version 2018-06-03
 */
@Table(name="t_proposal", alias="a", columns={
		@Column(name="pro_id", attrName="proId", label="提案Id，主键", isPK=true),
		@Column(name="pro_emp_id", attrName="proEmpId", label="创建提案的员工id"),
		@Column(name="pro_name", attrName="proName", label="提案名", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
		@Column(name="end_date", attrName="endDate", label="提案结束时间"),
		@Column(name="pro_status", attrName="proStatus", label="提案审核情况"),
		@Column(name="pro_result", attrName="proResult", label="提案审核结果"),
	}, orderBy="a.update_date DESC"
)
@Alias("Proposal")
public class Proposal extends DataEntity<Proposal> {
	
	private static final long serialVersionUID = 1L;
	private String proId;		// 提案Id，主键
	private String proEmpId;		// 创建提案的员工id
	private String proName;		// 提案名
	private Date endDate;		// 提案结束时间
	private String proStatus;		// 提案审核情况
	private String proResult;		// 提案审核结果
	
	public Proposal() {
		this(null);
	}

	public Proposal(String id){
		super(id);
	}
	
	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
	@NotBlank(message="创建提案的员工id不能为空")
	@Length(min=0, max=255, message="创建提案的员工id长度不能超过 255 个字符")
	public String getProEmpId() {
		return proEmpId;
	}

	public void setProEmpId(String proEmpId) {
		this.proEmpId = proEmpId;
	}
	
	@NotBlank(message="提案名不能为空")
	@Length(min=0, max=255, message="提案名长度不能超过 255 个字符")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=255, message="提案审核情况长度不能超过 255 个字符")
	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}
	
	@Length(min=0, max=255, message="提案审核结果长度不能超过 255 个字符")
	public String getProResult() {
		return proResult;
	}

	public void setProResult(String proResult) {
		this.proResult = proResult;
	}
	
}