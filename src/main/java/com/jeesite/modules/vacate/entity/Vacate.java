/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.vacate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请假表信息维护Entity
 * @author BeHe
 * @version 2018-05-24
 */
@Table(name="t_vacate", alias="a", columns={
		@Column(name="va_id", attrName="vaId", label="请假编号", isPK=true),
		@Column(name="emp_code", attrName="empCode", label="员工编号"),
		@Column(name="emp_reason", attrName="empReason", label="员工请假理由"),
		@Column(name="man_reason", attrName="manReason", label="经理审批理由"),
		@Column(name="start_time", attrName="startTime", label="请假开始时间"),
		@Column(name="end_time", attrName="endTime", label="请假结束时间"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
@Alias("Vacate")
public class Vacate extends DataEntity<Vacate> {
	
	private static final long serialVersionUID = 1L;
	private String vaId;		// 请假编号
	private String empCode;		// 员工编号
	private String empReason;		// 员工请假理由
	private String manReason;		// 经理审批理由
	private Date startTime;		// 请假开始时间
	private Date endTime;		// 请假结束时间
	
	public Vacate() {
		this(null);
	}

	public Vacate(String id){
		super(id);
	}
	
	public String getVaId() {
		return vaId;
	}

	public void setVaId(String vaId) {
		this.vaId = vaId;
	}
	
	@NotBlank(message="员工编号不能为空")
	@Length(min=0, max=100, message="员工编号长度不能超过 100 个字符")
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	@NotBlank(message="员工请假理由不能为空")
	@Length(min=0, max=1024, message="员工请假理由长度不能超过 1024 个字符")
	public String getEmpReason() {
		return empReason;
	}

	public void setEmpReason(String empReason) {
		this.empReason = empReason;
	}
	
	@Length(min=0, max=1024, message="经理审批理由长度不能超过 1024 个字符")
	public String getManReason() {
		return manReason;
	}

	public void setManReason(String manReason) {
		this.manReason = manReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假开始时间不能为空")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假结束时间不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}