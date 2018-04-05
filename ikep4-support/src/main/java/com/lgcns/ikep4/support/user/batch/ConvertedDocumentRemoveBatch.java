package com.lgcns.ikep4.support.user.batch;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.util.PropertyLoader;

public class ConvertedDocumentRemoveBatch extends QuartzJobBean {
	
	/**
	 * 사용자 정보 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
    	 System.out.println(("=== ConvertedDocumentRemoveBatch start ==="));
    	 
    	 //ikep4.support.synap.resultUrl
    	Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
 		String downloadPath = prop.getProperty("ikep4.support.synap.rootPath");
 		
 		File downloadDirectory = new File(downloadPath);
 		File[] fileList = downloadDirectory.listFiles();
 		
 		deleteTempFiles(fileList);
	 		
 		System.out.println(("=== ConvertedDocumentRemoveBatch end ==="));
		
	}
	
	@Async
	public int deleteTempFiles(File[] fileList) {
			
		for(int i=0; i<fileList.length; i++) {
			
			if(fileList[i].isFile()) {
				fileList[i].delete();
			} else if(fileList[i].isDirectory()) {
				try {
					FileUtils.deleteDirectory(fileList[i]);					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("=== ConvertedDocumentRemoveBatch : Fail to delete files ===");
				}
			}
		}
		return 0;
	}
}