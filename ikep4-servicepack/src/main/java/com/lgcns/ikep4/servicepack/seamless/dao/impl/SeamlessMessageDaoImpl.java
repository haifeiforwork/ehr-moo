package com.lgcns.ikep4.servicepack.seamless.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessMessageDao;
import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MailSynchronize;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessMessageDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class SeamlessMessageDaoImpl extends GenericDaoSqlmap<MessageBox,String> implements SeamlessMessageDao {

	private String nameSpace = "servicepack.seamlessmessage.seamlessmessage.";
	
	@Deprecated
	public MessageBox get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(MessageBox object) {
		return null;
	}

	@Deprecated
	public void update(MessageBox object) {
	}

	@Deprecated
	public void remove(String id) {
	}
	
	public List<MessageBox> receiveList (SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.sqlSelectForList(nameSpace+"receiveList",searchCondition);
	}
	
	public List<MessageBox> sendList (SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.sqlSelectForList(nameSpace+"sendList",searchCondition);
	}
	
	public List<MessageBox> contactHistoryList (SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.sqlSelectForList(nameSpace+"contactHistoryList",searchCondition);
	}
	
	public Date lastContactDate (SeamlessmessageSearchCondition searchCondition) {
		return (Date) this.sqlSelectForObject(nameSpace+"lastContactDate",searchCondition);
	}
	
	@SuppressWarnings("unchecked")
	public List<ContacUser> contactUserList (SeamlessmessageSearchCondition searchCondition) {
		return (List<ContacUser>)  this.getSqlMapClientTemplate().queryForList(nameSpace+"contactUserList",searchCondition);
	}
	
	public Date getSyncDate (MailSynchronize mailSynchronize) {
		return (Date) this.sqlSelectForObject(nameSpace+"getSyncDate",mailSynchronize);
	}
	
	public void createSynchronize (MailSynchronize mailSynchronize) {
		this.sqlInsert(nameSpace+"createSynchronize",mailSynchronize);
	}

}
