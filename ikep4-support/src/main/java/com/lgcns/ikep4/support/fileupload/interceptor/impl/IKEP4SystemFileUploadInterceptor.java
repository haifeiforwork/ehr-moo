/*
 * 
 */

package com.lgcns.ikep4.support.fileupload.interceptor.impl;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * 파일업로드 인터셉트
 * 
 * @author 유승목
 * @version $Id: IKEP4SystemFileUploadInterceptor.java 12416 2011-05-20
 *          07:53:32Z handul32 $
 */
@Component
public class IKEP4SystemFileUploadInterceptor implements FileUploadInterceptor {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * getKey()
	 */
	public String getKey() {
		return "ikep4.system";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor#
	 * beforeProcess(java.util.Map)
	 */
	@SuppressWarnings("static-access")
	public UPLOAD_ERROR_MESSAGE beforeProcess(Map<String, Object> parameterMap) {

		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		final Long imageFileSize = Long.valueOf(prop.getProperty("ikep4.support.fileupload.upload_editor_image_size"));

		// 파일의 이름
		// final String fileName =
		// String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_NAME));

		// 파일의 컨텐트 타입
		final String fileContentType = String.valueOf(parameterMap.get(FileUploadInterceptor.FILE_CONTENT_TYPE));

		// 업로드된 파일의 원본 이미지
		// final String fileOriginalFilename = String.valueOf(parameterMap
		// .get(FileUploadInterceptor.FILE_ORIGINAL_FILE_NAME));

		// 업로드된 파일 싸이즈
		final Long fileSize = (Long) parameterMap.get(FileUploadInterceptor.FILE_SIZE);

		// 파일 싸이즈 체크
		if (fileSize > imageFileSize * KBYTE) {
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_SIZE;
			//return "file size is over max limit!";
		}

		Boolean allowImageExtension = false;

		// 이미지 파일 확장자 체크
		for (String imageExtension : this.IMAGE_EXTENSION) {
			if (StringUtils.contains(fileContentType, imageExtension)) {
				allowImageExtension = true;
			}
		}
		if (!allowImageExtension) {
			return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.NOT_SUPPORT_EXT;
			//return "file type is not image file!";
		}
		
		return FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.OK;
	}

}
