/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.interceptor.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.service.NoteFolderService;
import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor;


/**
 * 노트 파일업로드 인터셉터 위지윅에디터에서 이미지파일업로드작업시 파일저장 직전에 비즈니스 처리를 한다.
 * 
 * @author ${user}
 * @version $$Id: NoteFileUploadInterceptor.java 12772 2011-05-23 13:14:41Z
 *          designtker $$
 */
@Component
public class NoteFileUploadInterceptor implements FileUploadInterceptor {
	@Autowired
	private NoteFolderService noteFolderService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * getKey()
	 */
	public String getKey() {
		return "support.note";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * beforeProcess(java.util.Map)
	 */
	@SuppressWarnings("static-access")
	public UPLOAD_ERROR_MESSAGE beforeProcess(Map<String, Object> parameterMap) {
		// 게시판 아이디
		final String folderId = String.valueOf(parameterMap.get("folderId"));

		// 파일의 이름
		// final String fileName =
		// String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_NAME));

		// 파일의 컨텐트 타입
		final String fileContentType = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_CONTENT_TYPE));

		// 업로드된 파일의 원본 이미지
		// final String fileOriginalFilename =
		// String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_ORIGINAL_FILE_NAME));

		// 업로드된 파일 싸이즈
		final Long fileSize = (Long) parameterMap.get(FileUploadInterceptor.FILE_SIZE);

		// 업로드된 파일 싸이즈
		final Integer imageWidth = (Integer) parameterMap.get(FileUploadInterceptor.IMAGE_WIDTH);
		
		
		// 폴더 관리 정보를  가져오기
		NoteSearchCondition searchCondition = new NoteSearchCondition();		
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		// 폴더 정보에 있는 이미지 파일 크기 제한 값
		final Long imageFileSize = noteFolder.getImageFileSize();

		/* 이미지 파일의 싸이즈를 폴더에 설정되어 있는 싸이즈와 비교합니다. */
		if (fileSize > imageFileSize) {	// * KBYTE
			// throw new
			// IKEP4ApplicationException("${fileOriginalFilename} file size is over max limit.",
			// new IllegalArgumentException());
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_SIZE;
			//return "file size is over max limit!";
		}

		// 폴더 정보에 있는 이미지 파일 넓이 제한 값
		final Integer imageFileWidth = noteFolder.getImageWidth();
		
		/* 이미지 파일의 넓이를 폴더에 설정되어 있는 싸이즈와 비교합니다. */
		if (imageWidth > imageFileWidth) {	// * Pixel
			// throw new
			// IKEP4ApplicationException("${fileOriginalFilename} file size is over max limit.",
			// new IllegalArgumentException());
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_WIDTH;
			//return "file size is over max limit!";
		}

		/* 이미지 파일만 올리게 확장자 체크를 합니다. */
		Boolean allowImageExtension = false;
		for (String imageExtension : this.IMAGE_EXTENSION) {	// 이미지 파일 확장자 체크
			if (StringUtils.contains(fileContentType, imageExtension)) {
				allowImageExtension = true;
			}
		}
		if (!allowImageExtension) {
			// throw new
			// IKEP4ApplicationException("${fileOriginalFilename} file size is over max limit.",
			// new IllegalArgumentException());
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.NOT_SUPPORT_EXT;
			//return "file type is not image file!";
		}

		return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.OK;
	}

}
