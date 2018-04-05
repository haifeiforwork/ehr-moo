/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.message.model.MessageSpecialUser;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSpecialUserDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface MessageSpecialUserDao extends GenericDao<MessageSpecialUser, String> {
	
	public List<MessageSpecialUser> getSpecialUserList(MessageMonitorSearchCondition messageMonitorSearchCondition) ;
	
	public Integer countSpecialUserList(MessageMonitorSearchCondition messageMonitorSearchCondition); 
	
}
