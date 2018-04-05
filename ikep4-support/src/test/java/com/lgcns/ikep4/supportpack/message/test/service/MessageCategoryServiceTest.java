/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.service;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.message.model.MessageCategory;
import com.lgcns.ikep4.support.message.service.MessageCategoryService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageCategoryServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageCategoryServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private MessageCategoryService messageCategoryService;
	

	@Test
	public void testGetCategoryList() {
		List<MessageCategory> result = this.messageCategoryService.getCategory();
		assertFalse(result.isEmpty());		
	}
}
