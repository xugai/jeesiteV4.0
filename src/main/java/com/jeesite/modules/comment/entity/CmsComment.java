/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.comment.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * cms_commentEntity
 * @author redandelion
 * @version 2018-06-07
 */
@Table(name="cms_comment", alias="a", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="content_id", attrName="contentId", label="栏目内容的编号"),
		@Column(name="content", attrName="content", label="评论内容"),
		@Column(name="like", attrName="like", label="", comment="(0,不点赞，1点赞)"),
		@Column(name="name", attrName="name", label="评论姓名", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class CmsComment extends DataEntity<CmsComment> {
	
	private static final long serialVersionUID = 1L;
	private String contentId;		// 栏目内容的编号
	private String content;		// 评论内容
	private String like;		// (0,不点赞，1点赞)
	private String name;		// 评论姓名
	
	public CmsComment() {
		this(null);
	}

	public CmsComment(String id){
		super(id);
	}
	
	@NotBlank(message="栏目内容的编号不能为空")
	@Length(min=0, max=64, message="栏目内容的编号长度不能超过 64 个字符")
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@NotBlank(message="评论内容不能为空")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="长度不能超过 1 个字符")
	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}
	
	@Length(min=0, max=100, message="评论姓名长度不能超过 100 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}