/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.interceptor.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor;
import com.lgcns.ikep4.socialpack.socialblog.base.Constant;


/**
 * 게시판 파일업로드 인터셉터 위지윅에디터에서 이미지파일업로드작업시 파일저장 직전에 비즈니스 처리를 한다.
 * 
 * @author ${user}
 * @version $Id: SocialFileUploadInterceptor.java 15955 2011-07-22 04:16:53Z
 *          handul32 $
 */
@Component
@Repository("socialBoardFileUploadInterceptor")
public class SocialFileUploadInterceptor implements FileUploadInterceptor {

	/**
	 * MessageSource 객체
	 */
	@Autowired
	protected MessageSource messageSource = null;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * getKey()
	 */
	public String getKey() {
		return "socialpack.socialblog";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * beforeProcess(java.util.Map)
	 */
	public UPLOAD_ERROR_MESSAGE beforeProcess(Map<String, Object> parameterMap) {

		// 게시판 아이디
		// final String boardId = String.valueOf(parameterMap.get("boardId"));

		// 파일의 이름
		// final String fileName =
		// String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_NAME));

		// 파일의 컨텐트 타입
		final String fileContentType = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_CONTENT_TYPE));

		// 업로드된 파일의 원본 이미지
		final String fileOriginalFilename = String.valueOf(parameterMap
				.get(FileUploadInterceptor.FILE_ORIGINAL_FILE_NAME));

		// 업로드된 파일 싸이즈
		final Long fileSize = (Long) parameterMap.get(FileUploadInterceptor.FILE_SIZE);

		// 게시판 관리 정보 가져오기
		// final Board board = boardAdminService.readBoard(boardId);

		// 게시판 관리 정보에 있는 이미지 파일 크기 제한 값
		final Long imageFileSize = Constant.SOCIAL_BLOG_IMAGE_FILE_SIZE;

		String[] paramMessage = new String[1];
		paramMessage[0] = fileOriginalFilename;

		/* 이미지 파일의 싸이즈가 게시판에 설정되어 있는 싸이즈와 비교합니다. */
		if (fileSize > imageFileSize * Constant.SOCIAL_BLOG_FILE_BUFFER_SIZE) {
			// throw new
			// IKEP4ApplicationException(messageSource.getMessage("message.socialpack.socialblog.socialBoardItem.createBoardView.maxFileUpload",
			// paramMessage, Locale.getDefault()), new
			// IllegalArgumentException());
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_SIZE;
		}

		/* 이미지 파일만 올리게 확장자 체크를 합니다. */
		Boolean allowImageExtension = false;

		// 이미지 파일 확장자 체크
		for (String imageExtension : FileUploadInterceptor.IMAGE_EXTENSION) {
			if (StringUtils.contains(fileContentType, imageExtension)) {
				allowImageExtension = true;
			}
		}

		if (!allowImageExtension) {
			// throw new
			// IKEP4ApplicationException("현재 등록된 ${fileOriginalFilename}파일은 업로드 허용하는 파일 크기보다 큼니다.",
			// new IllegalArgumentException());
			// throw new
			// IKEP4ApplicationException(messageSource.getMessage("message.socialpack.socialblog.socialBoardItem.createBoardView.maxFileUpload",
			// paramMessage, Locale.getDefault()), new
			// IllegalArgumentException());
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.NOT_SUPPORT_EXT;
		}
		
		return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.OK;
	}

}
