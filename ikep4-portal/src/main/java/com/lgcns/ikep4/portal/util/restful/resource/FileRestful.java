/* 
 * Copyright (C) 2013 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.util.restful.resource;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.util.restful.model.fileinfo.FileInfoBody;
import com.lgcns.ikep4.portal.util.restful.model.fileinfo.FileInfoHead;
import com.lgcns.ikep4.portal.util.restful.model.fileinfo.FileInfoReturnData;
import com.lgcns.ikep4.portal.util.restful.model.param.FileParam;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.htmlcoverter.HtmlConverter;

/**
 * File Restful
 * 
 * @author jmjeon
 * @since 2013. 2. 28. 오전 10:48:18
 * @version 1.0.0 
 */
@Path("/file")
@Component
public class FileRestful extends BaseController {
	
	/**
	 * 첨부파일 변환 
	 */
	@POST 
	@Path("/fileToHtml")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FileInfoHead fileToHtml( FileParam fileParam ) {
		
		// 변수
		FileInfoHead result = null;
		Head head = null;
		FileInfoBody body = null;
		
		try {
			
			FileInfoReturnData returnData = new FileInfoReturnData();
			
			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String uploadFolderPath = prop.getProperty("ikep4.support.fileupload.upload_root");
	
			String converter = prop.getProperty("ikep4.support.synap.htmlConverterPath");
			String templatePath = prop.getProperty("ikep4.support.synap.templatePath");
			String modulePath = prop.getProperty("ikep4.support.synap.modulePath");
			String inputFile =  uploadFolderPath+fileParam.getAttachFileUrl()+"/"+fileParam.getAttachFileStoredName();
			inputFile = inputFile.replace('/', '\\');
			
			String outputPath = prop.getProperty("ikep4.support.synap.outputPath");
			
			HtmlConverter htmlConverter = new HtmlConverter(converter);
			String resultUrl = htmlConverter.fileToHtml(templatePath, modulePath, inputFile, outputPath);
			
			if(resultUrl==null) {
				head = new Head(2, "Error");
				body = new FileInfoBody();
			} else {
				returnData.setAttachTargetUrl(resultUrl);
				
				head = new Head(0, Response.Status.OK.toString());
				body = new FileInfoBody(returnData);
			}
			result = new FileInfoHead(head, body);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new FileInfoBody();
			result = new FileInfoHead(head, body);
		} 
		
		return result;
	}
}
