/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lgcns.ikep4.socialpack.socialanalyzer.test.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * Service 레이어 최상위 테스트 케이스<br/>
 * 모든 Service 구현 클래스들은 이 클래스를 상속하여 JUnit4 테스트 케이스를 작성한다.
 * 
 * @author mraible
 * @author modified by 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: BaseServiceTestCase.java 16246 2011-08-18 04:48:28Z giljae $
 */
@ContextConfiguration(locations = { "classpath*:/configuration/spring/context-dao.xml", "classpath*:/configuration/spring/context-service.xml" })
public abstract class BaseServiceTestCase extends AbstractTransactionalJUnit4SpringContextTests {

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
	public BaseServiceTestCase() {
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
	protected Object populate(Object obj) throws Exception {
		// loop through all the beans methods and set its properties from
		// its .properties file
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			// 한글 처리
			map.put(key, new String(rb.getString(key).getBytes("8859_1"), "UTF-8"));
		}
		BeanUtils.copyProperties(obj, map);
		return obj;
	}
}
