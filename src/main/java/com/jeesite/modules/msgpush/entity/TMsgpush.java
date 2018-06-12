/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.msgpush.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * t_msgpushEntity
 * @author redandelion
 * @version 2018-06-05
 */
@Table(name="t_msgpush", alias="a", columns={
		@Column(name="msg_id", attrName="msgId", label="msg_id", isPK=true),
		@Column(name="msg_type", attrName="msgType", label="消息类型", comment="消息类型（PC APP 短信 邮件 微信）"),
		@Column(name="msg_title", attrName="msgTitle", label="消息标题", queryType=QueryType.LIKE),
		@Column(name="msg_content", attrName="msgContent", label="消息内容"),
		@Column(name="read_status", attrName="readStatus", label="读取状态", comment="读取状态（0未送达 1未读 2已读）"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class TMsgpush extends DataEntity<TMsgpush> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// msg_id
	private String msgType;		// 消息类型（PC APP 短信 邮件 微信）
	private String msgTitle;		// 消息标题
	private String msgContent;		// 消息内容
	private String readStatus;		// 读取状态（0未送达 1未读 2已读）
	
	public TMsgpush() {
		this(null);
	}

	public TMsgpush(String id){
		super(id);
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@NotBlank(message="消息类型不能为空")
	@Length(min=0, max=16, message="消息类型长度不能超过 16 个字符")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@NotBlank(message="消息标题不能为空")
	@Length(min=0, max=200, message="消息标题长度不能超过 200 个字符")
	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	
	@NotBlank(message="消息内容不能为空")
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	@Length(min=0, max=1, message="读取状态长度不能超过 1 个字符")
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
}