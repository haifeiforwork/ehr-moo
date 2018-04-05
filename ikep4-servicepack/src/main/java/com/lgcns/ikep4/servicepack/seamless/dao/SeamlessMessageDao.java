package com.lgcns.ikep4.servicepack.seamless.dao;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MailSynchronize;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;
/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessMessageDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface SeamlessMessageDao extends GenericDao<MessageBox,String> {
	
	public List<MessageBox> receiveList (SeamlessmessageSearchCondition searchCondition);
	
	public List<MessageBox> sendList (SeamlessmessageSearchCondition searchCondition);
	
	public List<MessageBox> contactHistoryList (SeamlessmessageSearchCondition searchCondition);
	
	public Date lastContactDate (SeamlessmessageSearchCondition searchCondition);
	
	public List<ContacUser> contactUserList (SeamlessmessageSearchCondition searchCondition);
	
	public Date getSyncDate (MailSynchronize mailSynchronize);
	
	public void createSynchronize (MailSynchronize mailSynchronize);
}
