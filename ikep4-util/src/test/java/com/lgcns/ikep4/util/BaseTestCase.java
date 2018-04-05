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
package com.lgcns.ikep4.util;

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
 * Util 클래스 테스트 부모 케이스<br/>
 * 테스트 클래스를 작성하기 위해서는 본 클래스를 상속받아야 한다.
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: BaseTestCase.java 16247 2011-08-18 04:54:29Z giljae $
 */
@ContextConfiguration(locations = { "classpath:/context-test.xml" })
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
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
	public BaseTestCase() {
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