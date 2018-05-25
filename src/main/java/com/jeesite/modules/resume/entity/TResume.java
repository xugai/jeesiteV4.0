/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.resume.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * t_resumeEntity
 * @author kyrie
 * @version 2018-05-25
 */
@Table(name="t_resume", alias="a", columns={
		@Column(name="re_id", attrName="reId", label="re_id", isPK=true),
		@Column(name="emp_code", attrName="empCode", label="emp_code"),
		@Column(name="avatar", attrName="avatar", label="avatar"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class TResume extends DataEntity<TResume> {
	
	private static final long serialVersionUID = 1L;
	private String reId;		// re_id
	private String empCode;		// emp_code
	private String avatar;		// avatar
	
	public TResume() {
		this(null);
	}

	public TResume(String id){
		super(id);
	}
	
	public String getReId() {
		return reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}
	
	@NotBlank(message="emp_code不能为空")
	@Length(min=0, max=100, message="emp_code长度不能超过 100 个字符")
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	@Length(min=0, max=1000, message="avatar长度不能超过 1000 个字符")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "TResume{" +
				"reId='" + reId + '\'' +
				", empCode='" + empCode + '\'' +
				", avatar='" + avatar + '\'' +
				", createDate=" + createDate +
				", status='" + status + '\'' +
				", remarks='" + remarks + '\'' +
				", createByName='" + createByName + '\'' +
				", updateByName='" + updateByName + '\'' +
				", updateBy='" + updateBy + '\'' +
				", lastUpdateDateTime=" + lastUpdateDateTime +
				", createBy='" + createBy + '\'' +
				", updateDate=" + updateDate +
				'}';
	}
}