/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

import com.lgcns.ikep4.util.PropertyLoader;


/**
 * PropertyLoader 테스트 코드
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: PropertyLoaderTest.java 16247 2011-08-18 04:54:29Z giljae $
 */

public class PropertyLoaderTest {

	@Test
	public void testLoadedByName() {
		Properties propsLoadedByName = PropertyLoader.loadProperties("fileupload-conf.properties");

		assertNotNull(propsLoadedByName);
		assertEquals("/abc/temp", propsLoadedByName.get("ikep4.support.fileupload.upload_root"));
		assertEquals("/def/", propsLoadedByName.get("ikep4.support.fileupload.upload_folder"));
	}

	@Test
	public void testLoadedByClassLoader() {
		Properties propsLoadedByClassLoader = PropertyLoader.loadProperties("fileupload-conf.properties", this
				.getClass().getClassLoader());

		assertNotNull(propsLoadedByClassLoader);
		assertEquals("/abc/temp", propsLoadedByClassLoader.get("ikep4.support.fileupload.upload_root"));
		assertEquals("/def/", propsLoadedByClassLoader.get("ikep4.support.fileupload.upload_folder"));
	}

}
