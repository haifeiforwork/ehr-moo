/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.batch;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.fileupload.service.ThumbnailService;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * Thumbnail 배치 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailBatch.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class ThumbnailBatch extends QuartzJobBean {

	/**
	 * 파일 서비스
	 */
	private FileService fileService;

	/**
	 * 섬네일 서비스
	 */
	private ThumbnailService thumbnailService;

	/**
	 * 썸네일 이미지 변환 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			log.debug("=== ThumbnailBatch start ===");

			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");

			this.fileService = (FileService) appContext.getBean("fileServiceImpl");

			this.thumbnailService = (ThumbnailService) appContext.getBean("thumbnailServiceImpl");

			List<FileData> fileList = fileService.getAllForThumbnailBatch();

			for (FileData file : fileList) {

				try {

					log.debug("start.. " + uploadRootForImage + file.getFilePath() + "/" + file.getFileName());

					File originFile = new File(uploadRootForImage + file.getFilePath(), file.getFileName());

					if (originFile.exists()) {

						log.debug("converting.. " + file.getFileRealName());

						File thumbnailFile = ThumbnailConverter.thumbnailConvert(originFile,
								file.getThumbnailWidthSize(), file.getThumbnailHeightSize());

						log.debug("inserting.. " + file.getFileRealName());

						thumbnailService.createThumbnailForBatch(file, thumbnailFile);
					}

				} catch (Exception e) {
					log.debug(e.getMessage());
					// e.printStackTrace();
				}
			}

			log.debug("=== ThumbnailBatch end ===");

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
