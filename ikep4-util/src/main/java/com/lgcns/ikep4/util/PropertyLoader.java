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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;


/**
 * Properties 파일 로더<br/>
 * 클래스패쓰 상의 properties 파일 또는 ServletContext 객체 접근이 가능한 위치에서 properties 파일을 읽는다.
 * 
 * @author modified by 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: PropertyLoader.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class PropertyLoader {

	private static final String SUFFIX = ".properties";

	private static final Log log = LogFactory.getLog(PropertyLoader.class);

	private PropertyLoader() {
		// 객체 생성 불가능--> 유티 클래스화
	}

	/**
	 * 클래스 패쓰 상에 있는 리소스를 로딩한다. 리소스는 프로퍼티 파일이어야 하며, "." 또는 "/"로 패키지 패쓰를 모두 담은 이름을
	 * 넘겨주어야 한다. 특히 .properties 확장자가 없어도 무방하다.
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Reousrce
	 * some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 * @param loader
	 * @return
	 */
	public static Properties loadProperties(String nameParam, ClassLoader loaderParam) {

		String name = nameParam;
		ClassLoader loader = loaderParam;

		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}

		Properties result = null;

		InputStream in = null;

		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}

			name = name.replace('.', '/');

			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}

			in = loader.getResourceAsStream(name);

			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
					// 예외 무시
					log.error("InputStream of Property close error", ignore);
				}
			}
		}

		if (result == null) {
			throw new IllegalArgumentException("Could not load [" + name + "] as a classloader resource");
		}

		return result;
	}

	/**
	 * 클래스 패쓰 상에 있는 리소스를 로딩한다. 리소스는 프로퍼티 파일이어야 하며, "." 또는 "/"로 패키지 패쓰를 모두 담은 이름을
	 * 넘겨주어야 한다. 특히 .properties 확장자가 없어도 무방하다.
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Reousrce
	 * some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public static Properties loadProperties(final String name) {
		return loadProperties(name, Thread.currentThread().getContextClassLoader());
	}

	/**
	 * Web Controller 등의 ServletContext가 접근 가능한 클래스에서 사용하는 .properties 파일 로더
	 * 
	 * @param servletContext
	 * @param path .properties 파일 위치
	 * @return
	 */
	public static Properties loadProperties(ServletContext servletContext, String path) {

		Resource location = new ServletContextResource(servletContext, path);

		InputStream is = null;
		Properties props = new Properties();
		try {
			is = location.getInputStream();
			doLoad(props, new InputStreamReader(is, "UTF-8"));
		} catch (IOException e) {
			if (log.isWarnEnabled()) {
				log.warn("Could not load properties from " + location + ": " + e.getMessage(), e);
			}
		}

		return props;
	}

	/**
	 * SpringMVC의 Web Controller 에서 사용하는 .properties 파일 로더
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static Properties loadProperties(WebApplicationContext context, String path) {
		return loadProperties(context.getServletContext(), path);
	}

	private static void doLoad(Properties props, Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				return;
			}
			line = StringUtils.trimLeadingWhitespace(line);
			if (line.length() > 0) {
				char firstChar = line.charAt(0);
				if (firstChar != '#' && firstChar != '!') {
					while (endsWithContinuationMarker(line)) {
						String nextLine = in.readLine();
						line = line.substring(0, line.length() - 1);
						if (nextLine != null) {
							line += StringUtils.trimLeadingWhitespace(nextLine);
						}
					}
					int separatorIndex = line.indexOf('=');
					if (separatorIndex == -1) {
						separatorIndex = line.indexOf(':');
					}
					String key = (separatorIndex != -1 ? line.substring(0, separatorIndex) : line);
					String value = (separatorIndex != -1) ? line.substring(separatorIndex + 1) : "";
					key = StringUtils.trimTrailingWhitespace(key);
					value = StringUtils.trimLeadingWhitespace(value);
					props.put(unescape(key), unescape(value));
				}
			}
		}
	}

	private static boolean endsWithContinuationMarker(String line) {
		boolean evenSlashCount = true;
		int index = line.length() - 1;
		while (index >= 0 && line.charAt(index) == '\\') {
			evenSlashCount = !evenSlashCount;
			index--;
		}
		return !evenSlashCount;
	}

	private static String unescape(String str) {
		StringBuilder result = new StringBuilder(str.length());
		for (int index = 0; index < str.length();) {
			char c = str.charAt(index++);
			if (c == '\\') {
				c = str.charAt(index++);
				if (c == 't') {
					c = '\t';
				} else if (c == 'r') {
					c = '\r';
				} else if (c == 'n') {
					c = '\n';
				} else if (c == 'f') {
					c = '\f';
				}
			}
			result.append(c);
		}
		return result.toString();
	}

}
