/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.interceptor.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor;

/**
 * 게시판 파일업로드 인터셉터
 * 
 * 위지윅에디터에서 이미지파일업로드작업시 파일저장 직전에 비즈니스 처리를 한다.
 *
 * @author ${user}
 * @version $$Id: BoardFileUploadInterceptor.java 12772 2011-05-23 13:14:41Z designtker $$
 */
@Component("pfBoardFileUploadInterceptor")
public class BoardFileUploadInterceptor implements FileUploadInterceptor {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#getKey()
	 */
	public String getKey() {
		return "lightpack.gallery";
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#beforeProcess(java.util.Map)
	 */
	@SuppressWarnings("static-access")
	public UPLOAD_ERROR_MESSAGE beforeProcess(Map<String, Object> parameterMap) {
		//게시판 아이디
		final String boardId  = String.valueOf(parameterMap.get("boardId"));

		//파일의 이름
		//final String fileName = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_NAME));

		//파일의 컨텐트 타입
		final String fileContentType = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_CONTENT_TYPE));

		//업로드된 파일의 원본 이미지
		//final String fileOriginalFilename = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_ORIGINAL_FILE_NAME));

		//업로드된 파일 싸이즈
		final Long fileSize = (Long)parameterMap.get(FileUploadInterceptor.FILE_SIZE);

		//게시판 관리 정보 가져오기
		//final Board board = this.boardAdminService.readBoard(boardId);

		//게시판 관리 정보에 있는 이미지 파일 크기 제한 값
		final Long imageFileSize = 1000000L;//board.getImageFileSize();

		/* 이미지 파일의 싸이즈가 게시판에 설정되어 있는 싸이즈와 비교합니다.*/
		if(fileSize > imageFileSize  * KBYTE) {
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_SIZE;

		}

		/* 이미지 파일만 올리게 확장자 체크를 합니다. */
		Boolean allowImageExtension = false;

		//이미지 파일 확장자 체크
		for(String imageExtension : this.IMAGE_EXTENSION) {
			if(StringUtils.contains(fileContentType, imageExtension)) {
				allowImageExtension = true;
				break;
			}
		}
		if(!allowImageExtension) {
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.NOT_SUPPORT_EXT;
		}
		
		return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.OK;
	}

}
