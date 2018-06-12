/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.guestbook.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * cms_guestbookEntity
 * @author redandelion
 * @version 2018-06-07
 */
@Table(name="cms_guestbook", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="content", attrName="content", label="留言内容"),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="ip", attrName="ip", label="IP"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class CmsGuestbook extends DataEntity<CmsGuestbook> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 留言内容
	private String name;		// 姓名
	private String img;		// 图片
	private String ip;		// IP
	
	public CmsGuestbook() {
		this(null);
	}

	public CmsGuestbook(String id){
		super(id);
	}
	
	@NotBlank(message="留言内容不能为空")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotBlank(message="姓名不能为空")
	@Length(min=0, max=100, message="姓名长度不能超过 100 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="图片长度不能超过 255 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=100, message="IP长度不能超过 100 个字符")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}