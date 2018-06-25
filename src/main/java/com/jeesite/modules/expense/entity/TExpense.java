/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expense.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * expenseEntity
 * @author mkj
 * @version 2018-06-23
 */
@Table(name="t_expense", alias="a", columns={
		@Column(name="ex_id", attrName="exId", label="报销单编号", isPK=true),
		@Column(name="emp_code", attrName="empCode", label="员工编号"),
		@Column(name="emp_matter", attrName="empMatter", label="报销事件"),
		@Column(name="money", attrName="money", label="报销金额"),
		@Column(name="office", attrName="office", label="部门"),
		@Column(name="ex_time", attrName="exTime", label="报销时间"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="piid", attrName="piid", label="流程实例id"),
	}, orderBy="a.update_date DESC"
)
public class TExpense extends DataEntity<TExpense> {
	
	private static final long serialVersionUID = 1L;
	private String exId;		// 报销单编号
	private String empCode;		// 员工编号
	private String empMatter;		// 报销事件
	private Long money;		// 报销金额
	private String office;		// 部门
	private Date exTime;		// 报销时间
	private String piid;		// 流程实例id
	
	public TExpense() {
		this(null);
	}

	public TExpense(String id){
		super(id);
	}
	
	public String getExId() {
		return exId;
	}

	public void setExId(String exId) {
		this.exId = exId;
	}
	
	@NotBlank(message="员工编号不能为空")
	@Length(min=0, max=100, message="员工编号长度不能超过 100 个字符")
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	@Length(min=0, max=100, message="报销事件长度不能超过 100 个字符")
	public String getEmpMatter() {
		return empMatter;
	}

	public void setEmpMatter(String empMatter) {
		this.empMatter = empMatter;
	}
	
	@NotNull(message="报销金额不能为空")
	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}
	
	@Length(min=0, max=100, message="部门长度不能超过 100 个字符")
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="报销时间不能为空")
	public Date getExTime() {
		return exTime;
	}

	public void setExTime(Date exTime) {
		this.exTime = exTime;
	}
	
	@Length(min=0, max=100, message="流程实例id长度不能超过 100 个字符")
	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}
	
}