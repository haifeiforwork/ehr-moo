package com.lgcns.ikep4.servicepack.seamless.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.seamless.model.AttachFile;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.Recipient;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;
/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: ReceiveBoxDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface ReceiveBoxDao extends GenericDao<MessageBox,String> {
	
	public MessageBox get(SeamlessmessageSearchCondition seamlessmessageSearchCondition);
	
	public boolean exists(SeamlessmessageSearchCondition seamlessmessageSearchCondition);
	
	public void remove(SeamlessmessageSearchCondition seamlessmessageSearchCondition);
	
	public List<Recipient> getRecipientList (String messageId);
	
	public void createRecipient (Recipient recipient);
	
	public void removeRecipient (String messageId);
	
	public List<AttachFile> getAttachFileList (String messageId);
	
	public void createAttachFile (AttachFile attachFile);
	
	public void removeAttachFile (String messageId);
}
