package com.lgcns.ikep4.servicepack.seamless.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.seamless.dao.SendBoxDao;
import com.lgcns.ikep4.servicepack.seamless.model.AttachFile;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.Recipient;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SendBoxDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class SendBoxDaoImpl extends GenericDaoSqlmap<MessageBox,String> implements SendBoxDao{

	private String nameSpace = "servicepack.seamlessmessage.sendbox.";
	
	@Deprecated
	public MessageBox get(String id) {
		return null;
	}
	
	public MessageBox get(SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		return (MessageBox) sqlSelectForObject(nameSpace+"get", seamlessmessageSearchCondition);
	}

	@Deprecated
	public boolean exists(String messageId) {
		return false;
	}
	public boolean exists(SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		Integer count = (Integer) sqlSelectForObject(nameSpace+"exists", seamlessmessageSearchCondition);
		if (count > 0) { return true; }
		return false;
	}

	public String create(MessageBox messageBox) {
		String messageId = messageBox.getMessageId();
		this.sqlInsert(nameSpace+"create",messageBox);
		return messageId;
	}

	public void update(MessageBox messageBox) {
		this.sqlUpdate(nameSpace+"update",messageBox);
	}

	@Deprecated
	public void remove(String id) {
	}
	
	public void remove(SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		this.sqlUpdate(nameSpace+"remove",seamlessmessageSearchCondition);
	}

	@SuppressWarnings("unchecked")
	public List<Recipient> getRecipientList(String messageId) {
		return (List<Recipient>) this.getSqlMapClientTemplate().queryForList(nameSpace+"getRecipientList",messageId);
	}

	public void createRecipient(Recipient recipient) {
		this.sqlInsert(nameSpace+"createRecipient",recipient);
	}

	public void removeRecipient(String messageId) {
		this.sqlUpdate(nameSpace+"removeRecipient",messageId);
	}

	@SuppressWarnings("unchecked")
	public List<AttachFile> getAttachFileList(String messageId) {
		return (List<AttachFile>) this.getSqlMapClientTemplate().queryForList(nameSpace+"getAttachFileList",messageId);
	}

	public void createAttachFile(AttachFile attachFile) {
		this.sqlInsert(nameSpace+"createAttachFile",attachFile);
	}

	public void removeAttachFile(String messageId) {
		this.sqlUpdate(nameSpace+"removeAttachFile",messageId);
		
	}

}
