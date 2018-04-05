package com.lgcns.ikep4.support.fileupload.restful.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.restful.model.FileInfoBody;
import com.lgcns.ikep4.support.fileupload.restful.model.FileInfoHead;
import com.lgcns.ikep4.support.fileupload.restful.model.FileInfoParam;
import com.lgcns.ikep4.support.fileupload.restful.model.FileInfoReturnData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.restful.model.Head;


@Path("/support/fileupload")
@Component
public class FileUploadRestful {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 파일 정보 불러오기.
	 * @return
	 */
	@POST
	@Path("/retrieveFileInfo")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FileInfoHead getEntrustUserList(FileInfoParam param) {
		Head head = null;
		FileInfoBody body = null;
		FileInfoHead result = null;
		
		try{
			head = new Head(0, Response.Status.OK.toString());
			
			FileData fileInfo = fileService.getFileInfo(param.getFileId());
			FileInfoReturnData returnData = new FileInfoReturnData();
		 
			if(fileInfo!=null) {
				returnData.setFileId(fileInfo.getFileId());
				returnData.setFilePath(fileInfo.getFilePath());
				returnData.setFileName(fileInfo.getFileName());
				returnData.setFileRealName(fileInfo.getFileRealName());
				returnData.setFileContentsType(fileInfo.getFileContentsType());
				returnData.setFileSize(fileInfo.getFileSize()+"");
			}
			
			body = new FileInfoBody(returnData);
			result = new FileInfoHead(head,body);
				
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new FileInfoBody();
			result = new FileInfoHead(head,body);
		} 
		
		return result;
	}

}
