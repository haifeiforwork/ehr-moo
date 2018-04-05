/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.common.tree;

import org.codehaus.jackson.map.ObjectMapper;

import com.lgcns.ikep4.collpack.common.exception.JsonException;

/**
 * Tree Manager (더이상 사용하지 않음) support-common.base 로 이전됨
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeManager.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class TreeManager {

	private TreeManager() {
	}

	public static String getJSON(Object obj) {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = objectMapper.writeValueAsString(obj);
		} catch(Exception ex) {
			throw new JsonException(ex);
		}

		return jsonString;
	}
}
