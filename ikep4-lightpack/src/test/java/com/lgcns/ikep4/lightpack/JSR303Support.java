/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack;

import org.junit.Test;

/**
 * TODO Javadoc주석작성
 *
 * @author 占쏙옙占쏙옙占쏙옙
 * @version $Id: JSR303Support.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class JSR303Support {


	@Test
	public void ddd() {
		String text = "<h1>12312312312</h1><img>고드림</img><a>강아지<t>123123123</t></a> 고구마";

		String textWithoutTag = text.replaceAll("(?:<!--.*?(?:--.*?--\\s*)*.*?-->)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)", "");

		System.out.println(textWithoutTag);
	}

}
