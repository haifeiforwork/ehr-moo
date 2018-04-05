/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.batch;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;


/**
 * 파일 삭제처리 배치 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileDeleteBatch.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class FileDeleteBatch extends QuartzJobBean {

	private FileService fileService;

	/**
	 * 연결정보가 없는 파일 삭제처리 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.fileService = (FileService) appContext.getBean("fileServiceImpl");


			List<FileData> fileList = this.fileService.getAllForFileDeleteBatch();

			for (FileData file : fileList) {

				try {

					this.fileService.removeFile(file.getFileId());

				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
