/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.dao;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * DAO 레이어 최상위 테스트 케이스<br/>
 * 모든 DAO 구현 클래스들은 이 클래스를 상속하여 JUnit4 테스트 케이스를 작성한다.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @author modified by 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: BaseDaoTestCase.java 16904 2011-10-25 01:39:09Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/configuration/spring/context-dao.xml", "classpath*:/configuration/spring/context-service.xml","classpath:/context-test-schedule.xml" })
public abstract class BaseDaoTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * src/test/resources/${package.name}/ClassName.properties 로 부터
	 * ResourceBundle 로딩 (만일 파일이 있다면)
	 */
	protected ResourceBundle rb;

	/**
	 * Default constructor - populates "rb" variable if properties file exists
	 * for the class in src/test/resources.
	 */
	public BaseDaoTestCase() {
		String className = this.getClass().getName();

		try {
			rb = ResourceBundle.getBundle(className);
		} catch (MissingResourceException mre) {
			log.debug("No resource bundle found for: " + className);
		}
	}

	/**
	 * Utility method to populate a javabean-style object with values from a
	 * Properties file
	 * 
	 * @param obj the model object to populate
	 * @return Object populated object
	 * @throws Exception if BeanUtils fails to copy properly
	 */
	protected Object populate(final Object obj) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}

		BeanUtils.copyProperties(obj, map);

		return obj;
	}
}

