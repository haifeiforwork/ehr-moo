/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.message.dao.MessageCategoryDao;
import com.lgcns.ikep4.support.message.model.MessageCategory;
import com.lgcns.ikep4.support.message.service.MessageCategoryService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageCategoryServiceImpl.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Service
public class MessageCategoryServiceImpl extends GenericServiceImpl<MessageCategory, String> implements MessageCategoryService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MessageCategoryDao messageCategoryDao;
	
	public List<MessageCategory> getCategory() {
		return messageCategoryDao.getCategoryList(); 
	}
}
