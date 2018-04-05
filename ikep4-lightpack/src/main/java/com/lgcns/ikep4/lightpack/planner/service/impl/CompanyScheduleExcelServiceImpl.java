/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.planner.dao.CompanyScheduleExcelDao;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.lightpack.planner.service.CompanyScheduleExcelService;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;



@Service("companyScheduleExcelService")
public class CompanyScheduleExcelServiceImpl extends GenericServiceImpl<FileData, String> implements CompanyScheduleExcelService {

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private CompanyScheduleExcelDao companyScheduleExcelDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	public CompanyScheduleExcelServiceImpl(CompanyScheduleExcelDao dao) {
		this.dao = dao;
	}

	public  SearchResult<Map<String, Object>> getCompanyScheduleExcelFileList(CSExcelDownSearchCondition searchCondition) {

	
		Integer count = companyScheduleExcelDao.selectCount(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = companyScheduleExcelDao.getCompanyScheduleExcelFileList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}

	public void upLoadCompanyScheduleExcel(String fileId, User user, String portalId) {

		//파일 첨부
		if(fileId != null && !"".equals(fileId)) {
			fileService.createFileLink(fileId, portalId, "SDC", user);
		}
		
	}
	
	public void upLoadCompanyScheduleExcelEcm(String ecmFileName,String ecmFileUrl1,String ecmFileUrl2,String ecmFilePath,String ecmFileId, User user){
		String [] ecmFileIds = StringUtils.split(ecmFileId, "|");
		String [] ecmFileNames = StringUtils.split(ecmFileName, "|");
		String [] ecmFilePaths = StringUtils.split(ecmFilePath, "|");
		String [] ecmFileUrl1s = StringUtils.split(ecmFileUrl1, "|");
		String [] ecmFileUrl2s = StringUtils.split(ecmFileUrl2, "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		
		for( int i = 0 ; i < ecmFileIds.length ; i++ ){
			String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
			int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
			String fileId = StringUtil.replaceQuot(EncryptUtil.encryptText(idgenService.getNextId()));
			FileData fileData = new FileData();
			fileData.setFileId(fileId);
			fileData.setFilePath(ecmFilePaths[i]);
			fileData.setFileUrl1(ecmFileUrl1s[i]);
			fileData.setFileUrl2(ecmFileUrl2s[i]);
			fileData.setFileRealName(ecmFileNames[i]);
			fileData.setFileSize(0);
			fileData.setFileName(ecmFileIds[i]);
			fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
			fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
			fileData.setEditorAttach(0);
			fileData.setRegisterId(user.getUserId());
			fileData.setRegisterName(user.getUserName());
			fileData.setUpdaterId(user.getUserId());
			fileData.setUpdaterName(user.getUserName());
			this.fileDao.createEcmFile(fileData);
			
			fileData.setFileId(fileId);
			fileData.setFilePath(uploadFolderForFile);
			fileData.setFileRealName(ecmFileNames[i]);
			fileData.setFileSize(0);
			fileData.setFileName(ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExtsSize]);
			fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
			fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
			fileData.setEditorAttach(0);
			fileData.setRegisterId(user.getUserId());
			fileData.setRegisterName(user.getUserName());
			fileData.setUpdaterId(user.getUserId());
			fileData.setUpdaterName(user.getUserName());
			this.fileDao.create(fileData);
			
			//ECM 파일 링크 정보 넣기
			FileLink fileLink = new FileLink();
			String fileLinkId = idgenService.getNextId();
			fileLink.setFileId(fileId);
			fileLink.setItemId("P000001");
			fileLink.setItemTypeCode("SDC");
			fileLink.setFileLinkId(fileLinkId);
			fileLink.setRegisterId(user.getUserId());
			fileLink.setRegisterName(user.getUserName());
			fileLink.setUpdaterId(user.getUserId());
			fileLink.setUpdaterName(user.getUserName());

			this.fileLinkDao.createEcmFileLink(fileLink);
			
			fileLink.setFileId(fileId);
			fileLink.setItemId("P000001");
			fileLink.setItemTypeCode("SDC");
			fileLink.setFileLinkId(fileLinkId);
			fileLink.setRegisterId(user.getUserId());
			fileLink.setRegisterName(user.getUserName());
			fileLink.setUpdaterId(user.getUserId());
			fileLink.setUpdaterName(user.getUserName());

			this.fileLinkDao.create(fileLink);
			
		}
		
		
		try{
			
			File folder = new File(tempUploadRoot);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				URL url = new URL(ecmFileUrl2s[i]);
				File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
				FileUtils.copyURLToFile(url, srcFile2);
			}
			
		}
			catch(Exception e) {
		}
		
	}
	
	public String getFilePath(String path) {

		String date = getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}
	
	public void delete(String[] chkFileIds) {

		//for (String fileId : chkFileIds) {
		//	fileService.removeFile(fileId);
		//}
		
		java.util.List<String> list = new ArrayList<String>(Arrays.asList(chkFileIds));
		fileService.removeFile(list);
	}

}
