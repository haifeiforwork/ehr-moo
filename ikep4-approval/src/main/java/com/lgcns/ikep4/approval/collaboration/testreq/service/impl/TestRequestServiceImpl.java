/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.testreq.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaborationException;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.testreq.dao.TestRequestDao;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequestPermission;
import com.lgcns.ikep4.approval.collaboration.testreq.search.TestRequestSearchCondition;
import com.lgcns.ikep4.approval.collaboration.testreq.service.TestRequestService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 시험의뢰서 TestRequestService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class TestRequestServiceImpl implements TestRequestService{
	
	/** The log. */
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	/** The FileService service. */
	@Autowired
	private FileService fileService;
	
	/** The commonCode service. */
	@Autowired
	private CommonCodeService commonCodeService;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private CollaboCommonService collaboCommonService;
	
	@Autowired
	private CollaboUtils collaboService;
	
	@Autowired
	private TestRequestDao testRequestDao;
	
	@Autowired
	private CollaboUtils collaboUtils;
	
	/**
	 * 시험의뢰서 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<TestRequest> getTestRequestList( TestRequestSearchCondition testRequestSearchCondition, User user) throws Exception {
		
		SearchResult<TestRequest> searchResult = null;
		try {
			
			if( StringUtils.isEmpty( testRequestSearchCondition.getSearchStartWriteDate()) ) {
				
				testRequestSearchCondition.setSearchStartWriteDate( DateUtil.getToday("yyyy.MM") + ".01");
				testRequestSearchCondition.setSearchEndWriteDate( DateUtil.getToday("yyyy.MM.dd"));
				testRequestSearchCondition.setSearchCompanyChkVal("111");
			}
			
			
			boolean isSystemAdmin = aclService.isSystemAdmin( "Approval", user.getUserId());
			testRequestSearchCondition.setIsAdmin( isSystemAdmin);
			testRequestSearchCondition.setSessionEmpNo( user.getEmpNo());
			testRequestSearchCondition.setSessionGoupId( user.getGroupId());
			
			int totalCnt = testRequestDao.getTestRequestCount( testRequestSearchCondition);
			testRequestSearchCondition.terminateSearchCondition( totalCnt);
			
			if( testRequestSearchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<TestRequest>( testRequestSearchCondition);
			}else{
				List<TestRequest> testRequestList = testRequestDao.getTestRequestList( testRequestSearchCondition);
				searchResult = new SearchResult<TestRequest>( testRequestList, testRequestSearchCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return searchResult;
	}
	
	/**
	 * 시험의뢰서 조회 및 기본셋팅
	 * @param newProductDevSearchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTestRequestObject( TestRequestSearchCondition searchCondition, User user) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonCode> c00014List = commonCodeService.getCommonCodeList( "C00014");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");	// 기안자품의상태코드
		TestRequest testRequest = null;
		TestRequestPermission testRequestPermission = null;
		
		if( StringUtils.equals( "modify", searchCondition.getViewMode() ) ) {
			
			searchCondition.setSessionEmpNo( user.getEmpNo());
			searchCondition.setSessionGoupId( user.getGroupId());
			
			// 1. 게시물 정보 가져오고
			
			testRequest = testRequestDao.getTestRequest( searchCondition);
			// 2. 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty( testRequest.getFileItemId())) {
				
				fileDataList = this.fileService.getItemFile( testRequest.getFileItemId(), TestRequest.ITEM_FILE_TYPE);
			}
			testRequest.setFileDataList(fileDataList);
			
			testRequestPermission = makePermission( testRequest, user);
			
		}else{
			
			// 작성자 정보
			testRequest = new TestRequest();
			testRequest.setWriteEmpNo( user.getEmpNo());
			testRequest.setWriteEmpNm( user.getUserName());
			testRequest.setWriteDate( DateUtil.getToday("yyyy.MM.dd"));
			testRequest.setWriteDeptId( user.getGroupId());
			testRequest.setWriteDeptNm( user.getTeamName());
			
			// 회사코드 디폴트 
			testRequest.setCompanyChkVal("111");
			
			// 진행상태코드
			testRequest.setProcessStatus( c00014List.get(0).getComCd());
			// 품의상태코드
			testRequest.setApprStsCd( c00005List.get(0).getComCd());
			
			//-------------------------------- 결재자 정보
			// 의뢰부서 정보 -기안자
			testRequest.setReqDraftEmpNo( user.getEmpNo());
			testRequest.setReqDraftEmpNm( user.getUserName());
			testRequest.setReqDraftDate(  DateUtil.getToday("MM/dd"));
			testRequest.setReqDraftStsCd( c00005List.get(0).getComCd());
			
			testRequest.setProcessGroupNo( String.valueOf(c00014List.get(0).getNumCol1()));
			
			// 승인자(팀장) 정보
			String empNo = user.getEmpNo();
			String reqApprEmpNo = "";
			String reqApprEmpNm = "";
			if( StringUtils.isNotEmpty( empNo)) {
				
				Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( user.getEmpNo());
				reqApprEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
				reqApprEmpNm = MapUtils.getString( reqApprMap, "userName", "");
			}
			testRequest.setReqApprEmpNo( reqApprEmpNo);
			testRequest.setReqApprEmpNm( reqApprEmpNm);
			testRequest.setReqApprStsCd( c00005List.get(0).getComCd());
        	
			testRequestPermission = new TestRequestPermission(true);
		}
		
		testRequestPermission.setEcmRoll( collaboCommonService.isEcmUser( user));
		
		resultMap.put("testRequest", testRequest);
		resultMap.put("testRequestPermission", testRequestPermission);
		
		return resultMap;
	}
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( TestRequest testRequest, User user) throws Exception {
		
		updateFile( testRequest, user);
		TestRequest resultTestRequest = this.getFileObject( testRequest, user);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put( "fileItemId", resultTestRequest.getFileItemId());
		resultMap.put( "fileDataListJson", resultTestRequest.getFileDataList());
		
		return CollaboUtils.convertJsonString( "datas", resultMap);
	}
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TestRequest getFileObject( TestRequest testRequest, User user) throws Exception {
		
		if( StringUtils.isNotEmpty( testRequest.getFileItemId()) ){
			
			List<FileData> fileDataList = new ArrayList<FileData>();
			fileDataList = this.fileService.getItemFile( testRequest.getFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			testRequest.setFileDataList( fileDataList);
			
			List<FileData> ecmFileDataList = new ArrayList<FileData>();
			ecmFileDataList = this.fileService.getItemFileEcm( testRequest.getFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			testRequest.setEcmFileDataList( ecmFileDataList);
		}
		
		return testRequest;
	}
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( TestRequest testRequest, User user) throws Exception {
		
		TestRequest oldTestRequest = null;
		TestRequestSearchCondition searchCondition = new TestRequestSearchCondition();
		
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		String fileItemId = testRequest.getFileItemId();
		// 1. 게시물 정보 가져오고
		// 1-1. 기존문서이면
		if( StringUtils.isNotEmpty( testRequest.getTrMgntNo())){
			
			searchCondition.setTrMgntNo( testRequest.getTrMgntNo());
			oldTestRequest = testRequestDao.getTestRequest( searchCondition);
			fileItemId = oldTestRequest.getFileItemId();
		}
		
		// 1. 게시물 X 파일등록이 최초이면 DB x
		// 2. 게시물 X 파일이 있으면 DB X
		// 3. 게시물 O 파일이 최초이면 DB 업데이트 <-- 일관되게 업데이트하자
		// 4. 게시물 O 파일이 있으면 DB 업데이트  <--무조건 되고 있고
		if( StringUtils.isEmpty( fileItemId)) {
			
			fileItemId = idgenService.getNextId();
			testRequest.setFileItemId( fileItemId);
			
			// 파일이 x, 게시물존재 O fileId 업데이트
			if( StringUtils.isNotEmpty( testRequest.getTrMgntNo())){
				testRequestDao.updateFileId( testRequest);
			}
		}
		
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( fileItemId, NewProductDev.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile( fileItemId, NewProductDev.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (testRequest.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : testRequest.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			testRequest.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				testRequest.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				testRequest.setAttachFileCount(attachCnt);
			}
		}
		
		// 파일링크 생성
		if ( testRequest.getFileLinkList() != null) {
			
			this.fileService.saveFileLink( testRequest.getFileLinkList(), fileItemId, NewProductDev.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split( testRequest.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split( testRequest.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split( testRequest.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split( testRequest.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split( testRequest.getEcmFileUrl2(), "|");
		String [] ecmOldFiles = new String[ecmFileDataList.size()];
		String [] uploadFlgs = new String[ecmFileIds.length];
		int ecmOldCnt = 0;
		 
		for(FileData ecmFiles0 : ecmFileDataList) {
			ecmOldFiles[ecmOldCnt] = ecmFiles0.getFileName();
			ecmOldCnt++;
		}
		
		//첨부파일명 갱신
		FileData tempFileData = new FileData();
		for(FileData tempFiles : ecmFileDataList) {
			for(int i=0;i<ecmFileIds.length;i++){
				if(tempFiles.getFileName().equals(ecmFileIds[i])){
					tempFileData.setFileId(tempFiles.getFileId());
					tempFileData.setFileRealName(ecmFileNames[i]);
					this.fileDao.updateEcmFileName(tempFileData);
					this.fileDao.updateFileName(tempFileData);
					attachCnt = i+1;
				}
			}
		}
		
		for(int j = 0 ; j < ecmOldFiles.length ; j++) {
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				if(ecmOldFiles[j].equals(ecmFileIds[i])){
					ecmOldFiles[j] = "";
				}
			}
		}
		
		for( int i = 0 ; i < ecmFileIds.length ; i++ ) {
			for(FileData ecmFiles2 : ecmFileDataList){
				if(ecmFiles2.getFileName().equals(ecmFileIds[i])){
					uploadFlgs[i] = "N";
				}
			}
		}
		FileLink fileLink1 = new FileLink();
		
		for(FileData ecmFiles3 : ecmFileDataList){
			for( int i = 0 ; i < ecmOldFiles.length ; i++ ) {
				if(ecmFiles3.getFileName().equals(ecmOldFiles[i])){
					fileLink1.setFileId(ecmFiles3.getFileid());
					fileLink1.setItemId( fileItemId);
					this.fileLinkDao.removeFileLink(fileLink1);
					this.fileLinkDao.removeEcmFileLink(fileLink1);
				}
			}
		}
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
		
		if(ecmFileIds.length > 0){
			for(int i = 0 ; i < ecmFileIds.length ; i++) {
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
				if(uploadFlgs[i] != "N"){
					
					/*File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}*/
					
					attachCnt++;
					
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
					if(checkImageFile(ecmFileNames[i])){
						fileData.setFilePath(uploadFolderForImage);
					}else{
						fileData.setFilePath(uploadFolderForFile);
					}
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
					fileLink.setItemId( fileItemId);
					fileLink.setItemTypeCode(NewProductDev.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.createEcmFileLink(fileLink);
					
					fileLink.setFileId(fileId);
					fileLink.setItemId( fileItemId);
					fileLink.setItemTypeCode(NewProductDev.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.create(fileLink);
					
				}
				if(checkImageFile(ecmFileNames[i])){
					File folder = new File(tempUploadRootImage);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRootImage+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}else{
					File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}
			}
		}
		
		return CollaboUtils.convertJsonString( "fileItemId", fileItemId);
	}
	
	/**
	 * 시험의뢰서 등록
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void createTestRequest( TestRequest testRequest, User user) throws Exception {
		
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");	// 기안자품의상태코드
		
		// 1. 시험 의뢰서 저장 
		testRequest.setTrMgntNo( DateUtil.getToday("yyyy-MM-"));
		testRequest.setReqApprStsCd( c00005List.get(0).getComCd());
		
		testRequestDao.insertTestRequest(testRequest);
		
		// 2. 시험의뢰서 결재선 저장
		// 2-1. 기안자
		testRequest.setReqDraftEmpNo( user.getEmpNo());
		testRequest.setReqDraftStsCd( c00005List.get(0).getComCd());
		
		// 2-2. 검토자
		if( StringUtils.isNotEmpty( testRequest.getReqReviewEmpNo())) {
			testRequest.setReqReviewStsCd( c00005List.get(0).getComCd());
		}
		
		// 2-3. 승인자
		testRequest.setReqApprStsCd( c00005List.get(0).getComCd());
		testRequestDao.insertTestReqApprLine( testRequest);
	}
	
	/**
	 * 수정, 상신 동시
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public void updateWithApprove( TestRequest testRequest,User user) throws Exception {
		
		TestRequest oldTestRequest = this.updateTestRequest( testRequest, user);
		this.approveTestRequest( testRequest, oldTestRequest, user);
	}
	
	/**
	 * 시험의뢰서 수정
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public TestRequest updateTestRequest( TestRequest testRequest, User user) throws Exception {
		
		TestRequest oldTestRequest = checkPermission(testRequest, user, CbrConstants.STAT_UPDATE);
		// TO-do 수정권한
		testRequestDao.updateTestRequest( testRequest);
		testRequestDao.updateTestReqApprLine( testRequest);
		
		return oldTestRequest;
	}
	
	/**
	 * 시험의뢰서 삭제
	 * @param testRequest
	 * @param user
	 * @throws Exception
	 */
	public void deleteTestRequest( TestRequest testRequest, User user) throws Exception {
		
		checkPermission(testRequest, user, CbrConstants.STAT_DELETE);
		// TO-do 삭제권한
		testRequestDao.deleteTestRequest( testRequest);
		testRequestDao.deleteTestReqApprLine( testRequest);
	}
	
	/**
	 * 승인/부결
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest, User user) throws Exception {
		
		this.approveTestRequest(testRequest, null, user);
	}
	
	/**
	 * 승인/부결
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveTestRequest( TestRequest testRequest, TestRequest oldTestRequest,User user) throws Exception {
		
		List<CommonCode> C00014List = commonCodeService.getCommonCodeList( "C00014");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");
		
		// 파라미터용 VO
		TestRequest paramTestRequest = new TestRequest();
		paramTestRequest.setTrMgntNo( testRequest.getTrMgntNo());
		
		if( oldTestRequest == null) {
			
			// DB 저장되어 있는 OBject 가져오기
			TestRequestSearchCondition searchCondition = new TestRequestSearchCondition();
			searchCondition.setTrMgntNo( testRequest.getTrMgntNo());
			oldTestRequest = testRequestDao.getTestRequest( searchCondition);
		}
		
		String processStatus = "";
		String apprStsCd = testRequest.getApproveStsCd();	// 승인, 접수등의  승인상태 코드
		
		int processGroup = 0;
		boolean isModViewEnable = false;		// 작성단계인지 확인
		boolean isApproveEnable = false;	// 결재가능한 진행중 단계인지 확인
		boolean isApprove = false;			// 승인여부
		boolean isReject = false;			// 부결여부
		int mailType= 0;
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		
		String paramStsCd = testRequest.getApproveStsCd();
		
		// 1. 현재 프로세스 코드로 , 화면 수정가능, 승인/부결 가능여부를 가져온다.
		for (CommonCode commonCode : C00014List) {
			if( StringUtils.equals( oldTestRequest.getProcessStatus(), commonCode.getComCd()) ) {
				
				processGroup = commonCode.getNumCol1();
				isModViewEnable = convIntToBoolean( commonCode.getNumCol2());
				isApproveEnable = convIntToBoolean( commonCode.getNumCol3());
				isApprove = convIntToBoolean( commonCode.getNumCol4());
				isReject = convIntToBoolean( commonCode.getNumCol5());
				break;
			}
		}
		
		paramTestRequest.setProcessGroupNo( String.valueOf( processGroup));
		
		//현재 단계가 의뢰부서이면
		if( processGroup == 1) {
			
			processStatus = CbrConstants.PROCESS_CD_11;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldTestRequest.getReqDraftStsCd(), c00005List);
				checkCurApprUser( oldTestRequest.getReqDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramTestRequest.setReqDraftStsCd( paramStsCd);
				paramTestRequest.setApprReqDate( DateUtil.getToday("yyyy.mm.dd"));	// 품의 요청일자
				
				mailType = CbrConstants.REQ_MAIL_TYPE_01;
				
			}else{
				if( isApproveEnable) {
					
					// 반려
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_13;
						paramTestRequest.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramTestRequest.setRejectReason( testRequest.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_02;
					}
					
					boolean isCompleteAppr = isApprCompleted( oldTestRequest.getReqReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldTestRequest.getReqReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldTestRequest.getReqReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							// 검토자이므로 메인 TABLE은 진행상태
							apprStsCd = CbrConstants.STATUS_CD_02;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_03;
						}
						
						paramTestRequest.setReqReviewStsCd( paramStsCd);	// 검토자 STSCD
					// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldTestRequest.getReqApprStsCd(), c00005List);
						checkCurApprUser( oldTestRequest.getReqApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							//자동접수로 만들자
							processStatus = CbrConstants.PROCESS_CD_20;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_04;
							
							paramTestRequest.setRcvDraftEmpNo(  oldTestRequest.getRcvEmpNo());
							paramTestRequest.setRcvDraftStsCd( CbrConstants.STATUS_CD_01);
							
							paramTestRequest.setProcessGroupNo( String.valueOf( processGroup ));
						}
						paramTestRequest.setReqApprStsCd( paramStsCd);		// 승인자 STSCD
					}
				}
			}
		// 주관부서 결재단계
		} else if( processGroup == 2 ){
			processStatus = CbrConstants.PROCESS_CD_21;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldTestRequest.getRcvDraftStsCd(), c00005List);
				checkCurApprUser( oldTestRequest.getRcvDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramTestRequest.setRcvDraftStsCd( paramStsCd);
				
				mailType = CbrConstants.REQ_MAIL_TYPE_06;
				
				// [체크리스트-14] TCS 담당자도 승인 또는 부결을 처리할 수 있어야 함.
				if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
					
					apprStsCd = CbrConstants.STATUS_CD_04;
					processStatus = CbrConstants.PROCESS_CD_23;
					paramTestRequest.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
					paramTestRequest.setRejectReason( testRequest.getRejectReason());
					
					mailType = CbrConstants.REQ_MAIL_TYPE_05;
					mailTargetEmpNoList.add( oldTestRequest.getReqDraftEmpNo());
				}
			}else{
				
				if( isApproveEnable) {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_23;
						paramTestRequest.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramTestRequest.setRejectReason( testRequest.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_05;
						mailTargetEmpNoList.add( oldTestRequest.getReqDraftEmpNo());
					}
					
					boolean isCompleteAppr = isApprCompleted( oldTestRequest.getRcvReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldTestRequest.getRcvReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldTestRequest.getRcvReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							apprStsCd = CbrConstants.STATUS_CD_02;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_07;
						}
						
						paramTestRequest.setRcvReviewStsCd( paramStsCd);	// 검토자 품의상태코드
						// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldTestRequest.getRcvApprStsCd(), c00005List);
						checkCurApprUser( oldTestRequest.getRcvApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							processStatus = CbrConstants.PROCESS_CD_22;
							mailType = CbrConstants.REQ_MAIL_TYPE_08;
						}
						paramTestRequest.setRcvApprStsCd( paramStsCd);		// 승인자 품의상태코드
						paramTestRequest.setApprDate( DateUtil.getToday("yyyy.mm.dd")); //품의 확정일자
					}
					// 다음단계 접수 확인
				}
			}
			
		}
		
		paramTestRequest.setProcessStatus( processStatus); 
		paramTestRequest.setApprStsCd( apprStsCd); 
		
		// 1. 의뢰서 수정
		testRequestDao.approveTestRequest( paramTestRequest);
		
		// 2. 플로우 수정
		testRequestDao.approveTestReqApprLine( paramTestRequest);
		
		try {
			//메일 발송오류시 승인과 영향받지 않게함.
			
			oldTestRequest.setRejectReason( testRequest.getRejectReason());
			sendApproveMail( mailType, oldTestRequest , processGroup);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	/**
	 * 시험의뢰서 추가사항 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateAddWriteDetail( TestRequest testRequest, User user) throws Exception {
		
		checkPermission(testRequest, user, CbrConstants.STAT_ADD_WRITE_DETAIL);
		testRequestDao.updateAddWriteDetail( testRequest);
	}
	
	/**
	 * 시험의뢰서 재접수
	 * @param testRequest
	 * @throws Exception
	 */
	public void initTestRequest( TestRequest testRequest, User user) throws Exception {
		
		checkPermission(testRequest, user, CbrConstants.STAT_INIT);
		testRequestDao.initTestRequest(testRequest);
		testRequestDao.initTestReqApprLine(testRequest);
	}
	
	/**
	 * 메일보내기
	 * @param mailType
	 * @param newProductDev
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendApproveMail( int mailType, TestRequest testRequest, int processGroup) throws Exception {
		if( mailType == 0) {
			return;
		}
		
		String serverMode = collaboService.getServerMode();
		
		String title = "";
		if( testRequest != null) {
			
			title = testRequest.getTestReqTitle() + " 시험 의뢰서 ";
		}
		
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		
		switch ( mailType) {
		case CbrConstants.REQ_MAIL_TYPE_01:
			title += "승인 요청"; 
			if( StringUtils.isNotEmpty( testRequest.getReqReviewEmpNo() )) {
				mailTargetEmpNoList.add( testRequest.getReqReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( testRequest.getReqApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_02:
			title += "반려";
			mailTargetEmpNoList.add( testRequest.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_03:
			title += "승인 요청"; 
			mailTargetEmpNoList.add( testRequest.getReqApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_04:
			title += "접수 요청"; 
			mailTargetEmpNoList.add( testRequest.getRcvEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_05:
			title += "반려";
			mailTargetEmpNoList.add( testRequest.getReqDraftEmpNo());
			mailTargetEmpNoList.add( testRequest.getRcvDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_06:
			title += "접수 승인 요청"; 
			if( StringUtils.isNotEmpty( testRequest.getRcvReviewEmpNo() )) {
				mailTargetEmpNoList.add( testRequest.getRcvReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( testRequest.getRcvApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_07:
			title += "접수 승인 요청";
			mailType = CbrConstants.REQ_MAIL_TYPE_07;
			mailTargetEmpNoList.add( testRequest.getRcvApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_08:
			title += "접수 승인"; 
			mailTargetEmpNoList.add( testRequest.getReqDraftEmpNo());
			// [통합테스트] 접최종승인, 반려시 기안자, 접수자 메일전송
			mailTargetEmpNoList.add( testRequest.getRcvDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_09:
			title += "반려"; 
			break;
		default:
			break;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List receiverList = new ArrayList();
		
		if( mailTargetEmpNoList.size() > 0) {
			
			paramMap.put( "mailTargetEmpNoList", mailTargetEmpNoList);
			List<Map<String, Object>> mailList = (List<Map<String, Object>>) collaboCommonService.getUserMailAddrList( paramMap);
			if( mailList != null) {
				
				for (Map<String, Object> map : mailList) {
					
					String mailAddr = MapUtils.getString( map, "mail", "");
					String userName = MapUtils.getString( map, "userName", "");
					if( StringUtils.isNotEmpty( mailAddr)) {
						HashMap<String, String> recieverMap = new HashMap<String, String>();
//						if( StringUtils.equals( serverMode, "local")) {
//							
//							recieverMap.put("email", "admin@eptest.co.kr");
//						}else{
							
							recieverMap.put("email", mailAddr);
//						}
						recieverMap.put("name", userName);
						receiverList.add(recieverMap);
					}
				}
			}
		}
		
		String mainFrameUrl = "/approval/collaboration/testreq/editTestRequestView.do?trMgntNo="+testRequest.getTrMgntNo() +"&viewMode=modify";
		
		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("testReqApprovalTemplate.vm");
		mail.setTitle( title);
		
		User sendUser = new User();
		//[통합테스트]메일 발신자 변경. 관리자에서 상신자로 변경
//		sendUser.setMail("admin@eptest.co.kr");
		String sendUserMail =  collaboCommonService.getUserMailAddr( testRequest.getReqDraftEmpNo());
		sendUser.setMail( sendUserMail);
		
		mail.setToEmailList( receiverList);
		
		Map dataMap = new HashMap();
		dataMap.put("testRequest", testRequest);
		dataMap.put("mailType", mailType);
		dataMap.put("url", collaboService.getServerURL( mainFrameUrl));
		
		mailSendService.sendMail(mail, dataMap, sendUser);
	}
	
	/**
	 * 메일 배치
	 * @throws Exception
	 */
	public void sendScheduleLimitSend() throws Exception {
		
		String serverMode = collaboUtils.getServerMode();
		
		List<TestRequest> ScheduleLimitList = testRequestDao.getScheduleLimitTarget();
		
		if( ScheduleLimitList == null || ScheduleLimitList.size() < 1) {
			return;
		}
		
		// 게시물별 구분
		Map<String, List<TestRequest>> sendMailMap = new HashMap<String, List<TestRequest>>();
		for ( TestRequest vo : ScheduleLimitList) {
			
			if( sendMailMap.containsKey( vo.getTrMgntNo()) ) {
				
				List<TestRequest> senderList = sendMailMap.get( vo.getTrMgntNo());
				senderList.add( vo);
			}else {
				List<TestRequest> senderList = new ArrayList<TestRequest>();
				senderList.add(vo);
				sendMailMap.put( vo.getTrMgntNo(), senderList);
			}
		}
		
		// 메일 발송
		for( String key : sendMailMap.keySet() ){
			
			List receiverList = new ArrayList();
			TestRequest testRequest = null;
			String title = "";
			String content = "";
			String mainFrameUrl = "";
			
			List<TestRequest> senderList = sendMailMap.get( key);
			for ( TestRequest vo : senderList) {
				
				HashMap<String, String> recieverMap = new HashMap<String, String>();
					
				recieverMap.put("email", vo.getEmail());
				recieverMap.put("name", vo.getRcvEmpNm());
				receiverList.add( recieverMap);
				if( testRequest == null) {
					title = vo.getTestReqTitle() + " 의 시험 의뢰서 접수 확인 요청";
					
					mainFrameUrl = "/approval/collaboration//testreq/editTestRequestView.do?trMgntNo="+ vo.getTrMgntNo() +"&viewMode=modify";
					
					content = "<html><body style='background: font: 100%/1.5em Tahoma,'돋움','Dotum';height: 100%;width:100%;'>"
						+ "<div style='border-top:2px solid #919fb2;width:1000px;height: 100%;padding:3px 5px;'>" 
						+ 	"<div style='margin-top:3px; border:1px solid #e0e0e0;width:100%;padding:3px 5px;'>"+vo.getWriteDeptNm() + " 에서 요청한 <span style='color: #5F84C2;'>" + vo.getTestReqTitle() + "</span> 의 시험 의뢰서에 대한 접수 진행이 안되었습니다.<br /> 빠른 처리 부탁드립니다.</div>"
						+ 	"<div style='position:relative; text-align:right; margin-bottom:7px;width:100%;'>"
						+ 		"<ul style='list-style-type: none;'><li style='display:inline;'><li><a  class='button' href="+collaboUtils.getServerURL( mainFrameUrl)+" title='신제품 개발 의뢰서 바로가기'><span>구매 시험 의뢰서 바로가기</span></a></li></li></ul>"
						+ 	"</div>" 
						+"</div></body></html>";
					testRequest = vo;
				}
			}
			
			User sendUser = new User();
//			sendUser.setMail("admin@eptest.co.kr");
			sendUser.setMail( testRequest.getSenderEmail());
			
			Mail mail = new Mail();
			mail.setMailType(MailConstant.MAIL_TYPE_HTML);
			mail.setTitle( title);
			mail.setContent(content);
			mail.setToEmailList( receiverList);
			
			Map dataMap = new HashMap();
			
			mailSendService.sendMail(mail, dataMap, sendUser);
		}
	}
	
	/**
	 * 권한확인  : 수정, 삭제, 재접수등
	 * @param testRequest
	 * @param user
	 * @param statusCd
	 * @throws Exception
	 */
	public TestRequest checkPermission( TestRequest testRequest, User user, int statCd) throws Exception {
		
		TestRequestSearchCondition testRequestSearchCondition = new TestRequestSearchCondition();
		testRequestSearchCondition.setTrMgntNo( testRequest.getTrMgntNo());
		testRequestSearchCondition.setSessionEmpNo( user.getEmpNo());
		testRequestSearchCondition.setSessionGoupId( user.getGroupId());
		
		TestRequest oldTestRequest = testRequestDao.getTestRequest(testRequestSearchCondition);
		TestRequestPermission permission =  makePermission(oldTestRequest, user);
		
		if( oldTestRequest == null) {
			throw new CollaborationException("더이상 존재하지 않는 게시물입니다.");
		}
		
		switch ( statCd) {
		case CbrConstants.STAT_DELETE:
			if( !permission.isDeleteBtnActive()) {
				throw new CollaborationException("삭제 권한이 없습니다.");
			}
			break;
		case CbrConstants.STAT_UPDATE:
			
			if( !permission.isSaveBtnActive()) {
				throw new CollaborationException("수정 권한이 없습니다.");
			}
			break;
		case CbrConstants.STAT_INIT:
			
			if( !permission.isInitRejctBtnActive()) {
				throw new CollaborationException("재접수 권한이 없습니다.");
			}
			break;
		case CbrConstants.STAT_REJECT:
			
			if( !permission.isRejected()) {
				throw new CollaborationException("반려 권한이 없습니다.");
			}
			break;
		case CbrConstants.STAT_ADD_WRITE_DETAIL:
			
			if( !permission.isWriteDetailViewActive()) {
				throw new CollaborationException("추가사항 수정 권한이 없습니다.");
			}
			break;

		default:
			
			String AuthYn = oldTestRequest.getAuthYn();
			if( !StringUtils.equals( AuthYn, CbrConstants.YES)) {
				throw new IKEP4AuthorizedException();
			}
			break;
		}
		
		return oldTestRequest;
	}
	
	/**
	 * 승인자 정보(팀장) 넣기
	 * @param user
	 * @param newProductDev
	 * @param nGroupCd
	 * @throws Exception
	 */
	private void setApprTeamLeader( User user, TestRequest testRequest, int nGroupCd) throws Exception{
		
		String empNo = user.getEmpNo();
		String apprEmpNo = "";
		String apprEmpNm = "";
		if( StringUtils.isNotEmpty( empNo)) {
			
			Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( user.getEmpNo());
			apprEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
			apprEmpNm = MapUtils.getString( reqApprMap, "userName", "");
		}
		
		
		if( nGroupCd == 1) {
			
			testRequest.setReqApprEmpNo( apprEmpNo);
			testRequest.setReqApprEmpNm( apprEmpNm);
		}else if( nGroupCd == 2) {
			
			testRequest.setRcvApprEmpNo( apprEmpNo);
			testRequest.setRcvApprEmpNm( apprEmpNm);
		}
	}
	
	/**
	 * 승인자와 세션유저 확인
	 * @param targetEmpNo
	 * @param user
	 * @throws Exception
	 */
	private void checkCurApprUser( String targetEmpNo, User user) throws Exception{
		
		if( !StringUtils.equals( targetEmpNo, user.getEmpNo())) {
			
			throw new CollaborationException( "승인할 수 없는 사용자입니다.");
		}
	}
	
	
	/**
	 * 현재단계 결재가 완료되었는지 확인
	 * @param stsCd
	 * @param c00005List
	 * @param checkCompleted
	 * @return
	 * @throws Exception
	 */
	private boolean checkAppCompleted( String stsCd, List<CommonCode> c00005List) throws Exception{
		
		boolean isCompleteAppr = isApprCompleted( stsCd, c00005List);
		
		if( isCompleteAppr) {
			
			throw new CollaborationException( "승인/부결이 완료되었습니다.");
		}
		
		return isCompleteAppr;
	}
	
	/**
	 * 승인/부결 여부 확인
	 * @param stsCd
	 * @param c00005List
	 * @return
	 * @throws Exception
	 */
	private boolean isApprCompleted( String stsCd, List<CommonCode> c00005List) throws Exception{
		
		boolean isCompleteAppr = false;
		
		for (CommonCode commonCode : c00005List) {
			
			if( StringUtils.equals( stsCd, commonCode.getComCd())) {
				
				isCompleteAppr = convIntToBoolean( commonCode.getNumCol1());
				break;
			}
		}
		
		return isCompleteAppr;
	}
	
	/**
	 * 화면 권한 확인
	 * @param newProductDev
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private TestRequestPermission makePermission( TestRequest testRequest, User user) throws Exception {
		
		// 조회권한 확인
		if( !StringUtils.equals( testRequest.getAuthYn(), CbrConstants.YES)) {
			
			throw new IKEP4AuthorizedException();
		}
		
		TestRequestPermission permission = new TestRequestPermission();
		
		List<CommonCode> c00014List = commonCodeService.getCommonCodeList( "C00014");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");
		
		String processStatus = testRequest.getProcessStatus();
		
		int processGroup = 0;
		boolean isViewModi = false;
		boolean isApproveEnable = false;	// 결재진행중
		boolean isApprove = false;	// 승인완료
		boolean isReject = false;	// 부결
		
		// 부결
		if( StringUtils.equals( testRequest.getApprStsCd(), CbrConstants.STATUS_CD_04)) {
			
			permission.setRejectBtnActive();
			
			// [단위테스트-12] 의뢰부서의 기안자는 반려된 의뢰서에 대해 접수버튼을 눌러 다시 결재를 진행할 수 있게 처리
			if( StringUtils.equals( testRequest.getReqDraftEmpNo(), user.getEmpNo() )) {
				permission.setInitRejctBtnActive( true);
			}
			
		} else {
			
			// 1. 현재 프로세스 코드로 , 화면 수정가능, 승인/부결 가능여부를 가져온다.
			for (CommonCode commonCode : c00014List) {
				if( StringUtils.equals( processStatus, commonCode.getComCd()) ) {
					
					processGroup = commonCode.getNumCol1();
					isViewModi = convIntToBoolean( commonCode.getNumCol2());
					isApproveEnable = convIntToBoolean( commonCode.getNumCol3());
					isApprove = convIntToBoolean( commonCode.getNumCol4());
					isReject = convIntToBoolean( commonCode.getNumCol5());
					break;
				}
			}
			
			// 1. 의뢰부서 의뢰단계
			if( processGroup == 1 ) {
				
				if( StringUtils.equals( testRequest.getWriteDeptId(), user.getGroupId()) ) {
					
					//permission.setAddWriteDetailBtnActive( true);
					permission.setWriteDetailViewActive( true);
				}
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( testRequest.getReqDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					
					permission.setReqModActive();
				}
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				// [통합테스트]  기안 이후 시험의뢰서 내용수정 불가
//				if( StringUtils.equals( testRequest.getReqDraftEmpNo(), user.getEmpNo()) 
//						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_10) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_11) )
//						&& !( isApprove || isReject)
//						&& !isApproved( testRequest.getReqReviewStsCd())
//					)) 
//				{
//					permission.setReqModActive2();
//				}
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					boolean isReviewApprComplete = isApprCompleted( testRequest.getReqReviewStsCd(), c00005List);
					// 로그인사용자가 의뢰부서이고 승인/부결이 되지 않았을경우 추가사항 버튼 활성화
					
					
					
					// 검토자 승인/부결 확인
					if( StringUtils.isNotEmpty( testRequest.getReqReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( testRequest.getReqReviewEmpNo(), user.getEmpNo())) {
							
							permission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( testRequest.getReqApprEmpNo(), user.getEmpNo()) && !isApprCompleted( testRequest.getReqApprStsCd(), c00005List)) {
							
							permission.setBtnApprRejectActive();
						}
					}
					
				}
				
				// 승인 완료 - 주관부서 접수 활성화
				if( isApprove) {
					// To-do1 사용자 관리에 관리부서로 등록되었을경우 확인해야함
					if( StringUtils.equals( testRequest.getRcvDeptId(), user.getGroupId()) && StringUtils.equals( testRequest.getRcvEmpNo(), user.getEmpNo())  ) {
						
						permission.setReceiptBtnActive( true);
					}
				}
				// 2. TCS부서 단계
			} else if( processGroup == 2) {
				
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( testRequest.getRcvDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					// 상위자 넣어주기
					permission.setRcvModActive();
					
					if( StringUtils.isEmpty( testRequest.getRcvApprEmpNo())) {
						permission.setApproveBtnActive( false);
						setApprTeamLeader( user, testRequest, processGroup);
					}
				}
				
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				if( StringUtils.equals( testRequest.getRcvDraftEmpNo(), user.getEmpNo()) 
						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_20) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_21) )
						&& !( isApprove || isReject)
						&& !isApproved( testRequest.getRcvReviewStsCd())
					)) 
				{
					permission.setRcvModActive2();
				}
				
				// [통합테스트]  기안 이후 시험의뢰서 내용수정 불가
				//[단위테스트-18] 검토자 또는 승인자가 승인/부결을 하지 않은 의뢰서에는 다른 동료가 접수를 하여 업무 이관을 할 수 있게 처리
//				if( !StringUtils.equals( testRequest.getRcvDraftEmpNo(), user.getEmpNo())
//					&& !( isApprove || isReject)
//					&& StringUtils.equals( testRequest.getRcvDeptId(), user.getGroupId())
//					//[단위테스트-23]접수 버튼은 검토자/승인자가 승인 또는 부결을 안한 상태에서 로그인 사원이 검토자/기안자가 아닌 경우에만 보이도록 수정요함
//					&& StringUtils.equals( testRequest.getRcvReviewEmpNo(), user.getEmpNo())
//					&& StringUtils.equals( testRequest.getRcvApprEmpNo(), user.getEmpNo())
//					) 
//				{
//					
//					permission.setReReceiptBntActive( true);
//				}
//				
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					// 검토자 승인/부결 확인
					boolean isReviewApprComplete = isApprCompleted( testRequest.getRcvReviewStsCd(), c00005List);
					if( StringUtils.isNotEmpty( testRequest.getRcvReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( testRequest.getRcvReviewEmpNo(), user.getEmpNo())) {
							
							permission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( testRequest.getRcvApprEmpNo(), user.getEmpNo()) && !isApprCompleted( testRequest.getRcvApprStsCd(), c00005List)) {
							
							permission.setBtnApprRejectActive();
						}
					}
					
				}
			}
		}
		
		// 작성자와 동일부서일 경우 추가사항 버튼 활성화
		// [통테]해당부서 임원일때(상위부서포함) 추가사항이 보여야한다.
		if( StringUtils.equals( testRequest.getWriteDeptId(), user.getGroupId()) || StringUtils.equals( testRequest.getImwonYn(), CbrConstants.YES)) {
			
			permission.setAddWriteDetailBtnActive( true);
		}
		
		testRequest.setProcessGroupNo( String.valueOf( processGroup));
		
		// [통테-체크리스트] 상신단계에서 '승인'을 '상신' 으로 변경
		if( permission.isApproveBtnActive() ) {
			
			if( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_10) || StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_20) ) {
				permission.setDrafter( true);
			}
		}
		
		return permission;
	}
	
	/**
	 * 승인/부결여부 확인
	 * @param cd1
	 * @return
	 */
	private boolean isApproved( String cd1) {
		
		if( StringUtils.equals( cd1, CbrConstants.STATUS_CD_03) || StringUtils.equals( cd1, CbrConstants.STATUS_CD_04)) {
			return true;
		}
		
		return false;
	}
	
	private String getFilePath(String path) {

		String date = DateUtil.getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
	
	private boolean convIntToBoolean( int nInt) {
		
		boolean result = false;
		if( nInt > 0) {
			result = true;
		}
		return result;
	}
}
