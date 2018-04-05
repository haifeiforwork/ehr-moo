/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.interceptor;

import java.util.Map;


/**
 * 파일업로드 인터셉트
 * 
 * @author 유승목
 * @version $Id: FileUploadInterceptor.java 18121 2012-04-18 08:20:26Z yu_hs $
 */
public interface FileUploadInterceptor {
	/** 키로바이트  싸이즈*/
	Integer KBYTE = 1024;

	/** 업로드 허용되는 이미지 확장자들 */
	String[] IMAGE_EXTENSION = { "png", "bmp", "jpg", "gif", "jpeg" };

	/** 파일 이름에 해당되는 키 paramterMap 의 키로 사용됨. */
	String FILE_NAME = "fileName";

	/** 파일 컨텐츠 타입에 해당되는 키 paramterMap 의 키로 사용됨. */
	String FILE_CONTENT_TYPE = "fileContentType";

	/** 파일 원본 파일명에 해당되는 키 paramterMap 의 키로 사용됨. */
	String FILE_ORIGINAL_FILE_NAME = "fileOriginalFilename";

	/** 파일 싸이즈에 해당되는 키 paramterMap 의 키로 사용됨. */
	String FILE_SIZE = "fileSize";
	
	/** 이미지 넓이에 해당되는 키 paramterMap 의 키로 사용됨. */
	String IMAGE_WIDTH = "fileImageWidth";
	
	/** 업로드 오류 코드 */
	public enum UPLOAD_ERROR_MESSAGE {
		OK,	//성공
		MAX_SIZE,	// 용량 초과
		MAX_WIDTH,	// 넓이 초과
		NOT_SUPPORT_EXT;	// 허용하지 않는 파일
	};

	/**
	 * FileUploadInterceptor의 키를 가져온다.
	 * 
	 * @return the key
	 */
	String getKey();

	/**
	 * 파일 업로드 직전의 업무적인 처리 (예 : 파일의 유효성 체크)를 실행한다.
	 * 
	 * @param parameterMap the parameter map
	 */
	UPLOAD_ERROR_MESSAGE beforeProcess(Map<String, Object> parameterMap);

}
