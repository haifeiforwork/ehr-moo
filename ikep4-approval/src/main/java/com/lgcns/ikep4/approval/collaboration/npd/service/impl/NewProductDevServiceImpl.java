/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaborationException;
import com.lgcns.ikep4.approval.collaboration.npd.dao.DevReqShareDeptDao;
import com.lgcns.ikep4.approval.collaboration.npd.dao.NewProductDevDao;
import com.lgcns.ikep4.approval.collaboration.npd.model.DevReqShareDept;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.npd.model.NpdPermission;
import com.lgcns.ikep4.approval.collaboration.npd.search.NewProductDevSearchCondition;
import com.lgcns.ikep4.approval.collaboration.npd.service.NewProductDevService;
import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.service.UserAuthMgntService;
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
 * 신제품 개발 NewProductDevService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class NewProductDevServiceImpl implements NewProductDevService{
	
	/** The log. */
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/** The NewProductDev dao. */
	@Autowired
	private NewProductDevDao newProductDevDao;
	
	/** The DevReqShareDept dao. */
	@Autowired
	private DevReqShareDeptDao devReqShareDeptDao;
	
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
	private UserAuthMgntService authMgntService;
	
	@Autowired
	private CollaboUtils collaboUtils;
	
	/**
	 * 신제품 개발 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<NewProductDev> getNewProductDevList( NewProductDevSearchCondition newProductDevSearchCondition, User user) throws Exception {
		
		SearchResult<NewProductDev> searchResult = null;
		try {
			
			if( StringUtils.isEmpty( newProductDevSearchCondition.getSearchStartReqDate()) ) {
				newProductDevSearchCondition.setSearchStartReqDate( DateUtil.getToday("yyyy.MM") + ".01");
				newProductDevSearchCondition.setSearchEndReqDate( DateUtil.getToday("yyyy.MM.dd"));
			}
			
			
			boolean isSystemAdmin = aclService.isSystemAdmin( "Approval", user.getUserId());
			newProductDevSearchCondition.setIsAdmin( isSystemAdmin);
			newProductDevSearchCondition.setSessionEmpNo( user.getEmpNo());
			newProductDevSearchCondition.setSessionGoupId( user.getGroupId());
			
			int totalCnt = newProductDevDao.getProductDevCount( newProductDevSearchCondition);
			newProductDevSearchCondition.terminateSearchCondition( totalCnt);
			
			if( newProductDevSearchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<NewProductDev>( newProductDevSearchCondition);
			} else {
				List<NewProductDev> newProductDevList = newProductDevDao.getProductDevList( newProductDevSearchCondition);
				searchResult = new SearchResult<NewProductDev>( newProductDevList, newProductDevSearchCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return searchResult;
	}
	
	/**
	 * 게시물 단일 정보 조회
	 * @param searchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNewProductDev( NewProductDevSearchCondition searchCondition, User user) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		// 1. 게시물 정보 가져오고
		NewProductDev newProductDev = newProductDevDao.getDevRequest( searchCondition);
		NpdPermission npdPermission =  makePermission( newProductDev, user);
		
		if( StringUtils.equals( "rslt", searchCondition.getSearchFileType())  ) {
			
			List<FileData> rsltFilefileDataList = new ArrayList<FileData>();
			rsltFilefileDataList = this.fileService.getItemFile( newProductDev.getRsltFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			newProductDev.setRsltFileDataList( rsltFilefileDataList);
			
			newProductDev.setEcmFileDataList(rsltFilefileDataList);
			
			List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( newProductDev.getRsltFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			newProductDev.setEcmFileDataList(ecmFileDataList);
		}
		
		// 리스트 화면에서 결과파일 보기여부를 파라미터로 체크?
		if( StringUtils.equals( searchCondition.getRsltFileReadYn(), "Y")) {
//			npdPermission.setRsltFileViewActive( true);
//			newProductDev.setRsltFileReadYn( "Y");
			//[체크리스트-17] 결과파일 일기가능자이면서, 접수부서 결과 기안자이거나 접수부서의 결과 승인자가 승인한 경우에만 결과파일 버튼을 활성화
			String sndRsltApprStsCd = newProductDev.getSndRsltApprStsCd();
			String sndRsltDraftEmpNo = newProductDev.getSndRsltDraftEmpNo();
			if( StringUtils.equals( sndRsltDraftEmpNo, user.getEmpNo()) || StringUtils.equals( sndRsltApprStsCd, CbrConstants.STATUS_CD_03))
			{
				
				npdPermission.setRsltFileViewActive( true);
				newProductDev.setRsltFileReadYn( "Y");
			}
		}
		
		resultMap.put("newProductDev", newProductDev);
		resultMap.put("npdPermission", npdPermission);
		
		return resultMap;
	}
	
	/**
	 * 조회 및 기본셋팅
	 * @param newProductDevSearchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNewProductObject( NewProductDevSearchCondition searchCondition, User user) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonCode> c00002List = commonCodeService.getCommonCodeList( "C00002");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");	// 기안자품의상태코드
		NewProductDev newProductDev = null;
		NpdPermission npdPermission = null;
		
		if( StringUtils.equals( "modify", searchCondition.getViewMode() ) ) {
			
			searchCondition.setSessionEmpNo( user.getEmpNo());
			searchCondition.setSessionGoupId( user.getGroupId());
			
			// 1. 게시물 정보 가져오고
			newProductDev = newProductDevDao.getDevRequest( searchCondition);
			getDevReqShareDept( newProductDev);
			// 2. 파일정보 가져오기
			newProductDev.setFileEditType( "req");
			this.getFileObject( newProductDev, user);
			
			// 3. 권한가져오기
			npdPermission = makePermission( newProductDev, user);
			
			// 리스트 화면에서 결과파일 보기여부를 파라미터로 체크?
			if( StringUtils.equals( searchCondition.getRsltFileReadYn(), "Y")) {
//				npdPermission.setRsltFileViewActive( true);
//				newProductDev.setRsltFileReadYn( "Y");
				//[체크리스트-17] 결과파일 일기가능자이면서, 접수부서 결과 기안자이거나 접수부서의 결과 승인자가 승인한 경우에만 결과파일 버튼을 활성화
				String sndRsltApprStsCd = newProductDev.getSndRsltApprStsCd();
				String sndRsltDraftEmpNo = newProductDev.getSndRsltDraftEmpNo();
				if( StringUtils.equals( sndRsltDraftEmpNo, user.getEmpNo()) || StringUtils.equals( sndRsltApprStsCd, CbrConstants.STATUS_CD_03))
				{
					
					npdPermission.setRsltFileViewActive( true);
					newProductDev.setRsltFileReadYn( "Y");
				}
			}
		}else{
			newProductDev = new NewProductDev();
			newProductDev.setDivisionNo("본TCS-");
			//의뢰자 정보 
			newProductDev.setReqEmpNo( user.getEmpNo());
			newProductDev.setReqEmpNm( user.getUserName());
			newProductDev.setReqDate( DateUtil.getToday("yyyy.MM.dd"));
			newProductDev.setReqDeptId( user.getGroupId());
			newProductDev.setReqDeptNm( user.getTeamName());
			
			// TCS팀 부서명 부서ID
			newProductDev.setFstReviewDeptId( "51000651");
			newProductDev.setFstReviewDeptNm( "TCS팀");
			
			// 진행상태코드
			newProductDev.setProcessStatus( c00002List.get(0).getComCd());
			// 품의상태코드
			newProductDev.setApprStsCd( c00005List.get(0).getComCd());
			
			//-------------------------------- 결재자 정보
			// 의뢰부서 정보
			newProductDev.setReqDraftEmpNo( user.getEmpNo());
			newProductDev.setReqDraftEmpNm( user.getUserName());
			newProductDev.setReqDraftDate(  DateUtil.getToday("MM/dd"));
			newProductDev.setReqDraftStsCd( c00005List.get(0).getComCd());
			
			newProductDev.setProcessGroupNo( "1");
			
			// 승인자(팀장) 정보
			Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( user.getEmpNo());
			String reqApprEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
			String reqApprEmpNm = MapUtils.getString( reqApprMap, "userName", "");
        	newProductDev.setReqApprEmpNo( reqApprEmpNo);
        	newProductDev.setReqApprEmpNm( reqApprEmpNm);
//        	newProductDev.setReqApprStsCd( c00005List.get(0).getComCd());
        	
			newProductDev.setEstQty( "0");
			newProductDev.setEstPrice( "0");
        	
        	npdPermission = new NpdPermission( true);
        	boolean isEcmUser = collaboCommonService.isEcmUser( user);
    		npdPermission.setEcmRoll( isEcmUser);
		}
		
		resultMap.put("newProductDev", newProductDev);
		resultMap.put("npdPermission", npdPermission);
		
		return resultMap;
	}
	
	/**
	 * 개발 의뢰 검토 완료 기간 초과 알림 대상 조회
	 * @return
	 * @throws Exception
	 */
	public List<NewProductDev> getScheduleLimitTarget() throws Exception {
		
		return newProductDevDao.getScheduleLimitTarget();
	}
	
	/**
	 * 결과파일 저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void updateRsltFile( NewProductDev newProductDev, User user) throws Exception {
		
		NewProductDevSearchCondition searchCondition = new NewProductDevSearchCondition();
		searchCondition.setMgntNo( newProductDev.getMgntNo());
//		Map<String, Object> objectMap = getNewProductDev(searchCondition, user);
//		
//		NewProductDev oldNewProductDev  = (NewProductDev) objectMap.get("newProductDev");
//		NpdPermission npdPermission = (NpdPermission) objectMap.get("npdPermission");
		
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		// 1. 게시물 정보 가져오고
		NewProductDev oldNewProductDev = newProductDevDao.getDevRequest( searchCondition);
		
		String rsltFileItemId = oldNewProductDev.getRsltFileItemId();
		if( StringUtils.isEmpty( rsltFileItemId)) {
			
			rsltFileItemId = idgenService.getNextId();
			newProductDev.setRsltFileItemId( rsltFileItemId);
			newProductDevDao.updateRsltFileId( newProductDev);
		}
		
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( rsltFileItemId, NewProductDev.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile( rsltFileItemId, NewProductDev.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (newProductDev.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : newProductDev.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			newProductDev.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				newProductDev.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				newProductDev.setAttachFileCount(attachCnt);
			}
		}
		
		// 파일링크 생성
		if (newProductDev.getFileLinkList() != null) {
			
			this.fileService.saveFileLink( newProductDev.getFileLinkList(), rsltFileItemId, NewProductDev.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split( newProductDev.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split( newProductDev.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split( newProductDev.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split( newProductDev.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split( newProductDev.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId( rsltFileItemId);
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
					fileLink.setItemId( rsltFileItemId);
					fileLink.setItemTypeCode(NewProductDev.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.createEcmFileLink(fileLink);
					
					fileLink.setFileId(fileId);
					fileLink.setItemId( rsltFileItemId);
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
		
	}
	
	/**
	 * 신제품 개발  - 개발검토의뢰서 등록
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void createNewProductDev( NewProductDev newProductDev, User user) throws Exception {
		
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");	// 기안자품의상태코드
		
		// 1. 개발 검토 의뢰서 저장 
		newProductDev.setMgntNo( DateUtil.getToday("yyyy-MM-"));
		newProductDev.setApprStsCd( c00005List.get(0).getComCd());
		newProductDevDao.insertTbDevRequest( newProductDev);
		
		// 2. 개발 검토 의로서 결재선 저장
		// 2-1. 기안자
		newProductDev.setReqDraftEmpNo( user.getEmpNo());
		newProductDev.setReqDraftStsCd( c00005List.get(0).getComCd());
		// 2-2. 검토자
		if( StringUtils.isNotEmpty( newProductDev.getReqReviewEmpNo())) {
			newProductDev.setReqReviewStsCd( c00005List.get(0).getComCd());
		}
		// 2-3. 승인자
		newProductDev.setReqApprStsCd( c00005List.get(0).getComCd());
		
		if( StringUtils.isEmpty( newProductDev.getEstQty() ) ) {
			newProductDev.setEstQty( "0");
		}
		if( StringUtils.isEmpty( newProductDev.getEstPrice() ) ) {
			newProductDev.setEstPrice( "0");
		}
		newProductDevDao.insertTbDevReqApprLine( newProductDev);
	}
	
	/**
	 * 신제품개발 - 개발검토의뢰서 수정
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public NewProductDev updateNewProductDev( NewProductDev newProductDev, User user) throws Exception {
		
		NewProductDev oldNewProductDev = checkPermission( newProductDev, user, CbrConstants.STAT_UPDATE);
		
		if( StringUtils.isEmpty( newProductDev.getEstQty() ) ) {
			newProductDev.setEstQty( "0");
		}
		if( StringUtils.isEmpty( newProductDev.getEstPrice() ) ) {
			newProductDev.setEstPrice( "0");
		}
		// 1. 개발검토의뢰서 수정
		newProductDevDao.updateTbDevRequest( newProductDev);
		// 참조부서
		insertDevReqShareDept( newProductDev);
		// 2. 개발검토의뢰서 결재라인 수정
		newProductDevDao.updateTbDevReqApprLine( newProductDev);
		
		return oldNewProductDev;
	}
	
	/**
	 * 요구일정 수정
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void updateReqScheduleCd( NewProductDev newProductDev, User user) throws Exception {
		
		checkPermission( newProductDev, user, CbrConstants.STAT_ADD_REQ_SCH_CD);
		
		// 1. 요구일정 수정
		newProductDevDao.updateReqScheduleCd( newProductDev);
	}
	
	/**
	 * 상신/저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveWithSave( NewProductDev newProductDev, User user) throws Exception {
		
		NewProductDev oldNewProductDev = this.updateNewProductDev( newProductDev, user);
		
		newProductDev.setApproveStsCd( CbrConstants.STATUS_CD_03);
		this.approveNewProductDev( newProductDev, oldNewProductDev, user);
	}
	
	/**
	 * 기존 승인/부결
	 */
	public void approveNewProductDev( NewProductDev newProductDev, User user) throws Exception {
		
		this.approveNewProductDev(newProductDev, null, user);
	}
	/**
	 * 승인/부결
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveNewProductDev( NewProductDev newProductDev, NewProductDev oldNewProductDev, User user) throws Exception {
		
		List<CommonCode> c00002List = commonCodeService.getCommonCodeList( "C00002");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");
		// 파라미터용 VO
		NewProductDev paramNewProductDev = new NewProductDev();
		paramNewProductDev.setMgntNo( newProductDev.getMgntNo());
		
		// DB 저장되어 있는 OBject 가져오기
		if( oldNewProductDev == null) {
			
			NewProductDevSearchCondition searchCondition = new NewProductDevSearchCondition();
			searchCondition.setMgntNo( newProductDev.getMgntNo());
			oldNewProductDev = newProductDevDao.getDevRequest( searchCondition);
		}
		
		String processStatus = "";
		String apprStsCd = newProductDev.getApproveStsCd();
		
		int processGroup = 0;
		boolean isModViewEnable = false;		// 작성단계인지 확인
		boolean isApproveEnable = false;	// 결재가능한 진행중 단계인지 확인
		boolean isApprove = false;			// 승인여부
		boolean isReject = false;			// 부결여부
		int mailType= 0;
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		
		String paramStsCd = newProductDev.getApproveStsCd();
		
		// 1. 현재 프로세스 코드로 , 화면 수정가능, 승인/부결 가능여부를 가져온다.
		for (CommonCode commonCode : c00002List) {
			if( StringUtils.equals( oldNewProductDev.getProcessStatus(), commonCode.getComCd()) ) {
				
				processGroup = commonCode.getNumCol1();
				isModViewEnable = convIntToBoolean( commonCode.getNumCol2());
				isApproveEnable = convIntToBoolean( commonCode.getNumCol3());
				isApprove = convIntToBoolean( commonCode.getNumCol4());
				isReject = convIntToBoolean( commonCode.getNumCol5());
				break;
			}
		}
		
		paramNewProductDev.setProcessGroupNo( String.valueOf( processGroup));
		
		//현재 단계가 의뢰부서이면
		if( processGroup == 1) {
			
			processStatus = CbrConstants.PROCESS_CD_11;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldNewProductDev.getReqDraftStsCd(), c00005List);
				checkCurApprUser( oldNewProductDev.getReqDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramNewProductDev.setReqDraftStsCd( paramStsCd);
				
				mailType = CbrConstants.REQ_MAIL_TYPE_01;
				
			}else{
				if( isApproveEnable) {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_13;
						paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_02;
					}
					
					boolean isCompleteAppr = isApprCompleted( oldNewProductDev.getReqReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldNewProductDev.getReqReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldNewProductDev.getReqReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							apprStsCd = CbrConstants.STATUS_CD_02;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_03;
						}
						
						paramNewProductDev.setReqReviewStsCd( paramStsCd);	// 검토자 STSCD
					// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldNewProductDev.getReqApprStsCd(), c00005List);
						checkCurApprUser( oldNewProductDev.getReqApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							processStatus = CbrConstants.PROCESS_CD_12;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_04;
						}
						paramNewProductDev.setReqApprStsCd( paramStsCd);		// 승인자 STSCD
					}
				// 다음단계 TCS접수 확인
				} else {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_01)) {
						
						if( !isApprove || !StringUtils.equals( oldNewProductDev.getReqRcvDeptId(), user.getGroupId()) || !StringUtils.isEmpty( oldNewProductDev.getTcsDraftEmpNo())) {
							throw new CollaborationException( "접수 할수 없습니다.");
						}
						
						processStatus = CbrConstants.PROCESS_CD_20;
						paramNewProductDev.setTcsDraftEmpNo( user.getEmpNo());	// TCS 기안자		
						paramNewProductDev.setTcsDraftStsCd( apprStsCd);		// TCS 상태코드
						processGroup = processGroup +1;
						paramNewProductDev.setProcessGroupNo( String.valueOf( processGroup ));
						
						paramNewProductDev.setFstReviewEmpNo( user.getEmpNo());	// TCS팀 담당자사번
						// [체크리스트 -22] TCS팀 담당자가 의뢰서에 대해 접수를 할 시, 1차 검토일에 오늘 날짜가 세팅되게 처리요함
						paramNewProductDev.setFstReviewDate( DateUtil.getToday("yyyy.mm.dd"));
						
						// 상위부서관리자 가져오기
						// 승인자(팀장) 정보
//						Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( paramNewProductDev.getTcsDraftEmpNo());
//						String temLeaderEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
//						paramNewProductDev.setTcsApprEmpNo( temLeaderEmpNo);
//						paramNewProductDev.setTcsApprStsCd( "01");
					}
				}
			}
		// TCS 결재단계
		} else if( processGroup == 2 ){
			processStatus = CbrConstants.PROCESS_CD_21;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldNewProductDev.getTcsDraftStsCd(), c00005List);
				checkCurApprUser( oldNewProductDev.getTcsDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramNewProductDev.setTcsDraftStsCd( paramStsCd);
				
				mailType = CbrConstants.REQ_MAIL_TYPE_06;
				
				// [체크리스트-14] TCS 담당자도 승인 또는 부결을 처리할 수 있어야 함.
				if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
					
					apprStsCd = CbrConstants.STATUS_CD_04;
					processStatus = CbrConstants.PROCESS_CD_23;
					paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
					paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
					
					mailType = CbrConstants.REQ_MAIL_TYPE_05;
					mailTargetEmpNoList.add( oldNewProductDev.getReqDraftEmpNo());
				}
			}else{
				
				if( isApproveEnable) {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_23;
						paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_05;
						mailTargetEmpNoList.add( oldNewProductDev.getReqDraftEmpNo());
					}
					
					boolean isCompleteAppr = isApprCompleted( oldNewProductDev.getTcsReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldNewProductDev.getTcsReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldNewProductDev.getTcsReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							apprStsCd = CbrConstants.STATUS_CD_02;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_07;
						}
						
						paramNewProductDev.setTcsReviewStsCd( paramStsCd);	// 검토자 품의상태코드
						// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldNewProductDev.getTcsApprStsCd(), c00005List);
						checkCurApprUser( oldNewProductDev.getTcsApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							processStatus = CbrConstants.PROCESS_CD_22;
							mailType = CbrConstants.REQ_MAIL_TYPE_08;
						}
						paramNewProductDev.setTcsApprStsCd( paramStsCd);		// 승인자 품의상태코드
						// [체크리스트 -22] TCS팀 담당자가 의뢰서에 대해 접수를 할 시, 1차 검토일에 오늘 날짜가 세팅되게 처리요함
//						paramNewProductDev.setFstReviewDate( DateUtil.getToday("yyyy.MM.dd"));// 1차검토완료일
					}
					// 다음단계 접수 확인
				} else {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_01)) {
						
						if( !isApprove || !StringUtils.equals( oldNewProductDev.getSndReviewDeptId(), user.getGroupId()) || !StringUtils.isEmpty( oldNewProductDev.getSndRcvDraftEmpNo())) {
							throw new CollaborationException( "접수 할수 없습니다.");
						}
						processStatus = CbrConstants.PROCESS_CD_30;
						paramNewProductDev.setSndRcvDraftEmpNo( user.getEmpNo());	// 2차검토접수 기안자명		
						paramNewProductDev.setSndRcvDraftStsCd( apprStsCd);			// 2차검토접수 품의상태코드
						paramNewProductDev.setProcessGroupNo( String.valueOf( processGroup + 1 ));
						
						paramNewProductDev.setSndReviewDeptId( user.getGroupId());	// 2차 검토부서 명
						
						// 승인자(팀장) 정보
//						Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( paramNewProductDev.getSndRcvDraftEmpNo());
//						String temLeaderEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
//						paramNewProductDev.setSndRcvApprEmpNo( temLeaderEmpNo);
//						paramNewProductDev.setSndRcvApprStsCd("01");
					}
				}
			}
			
		} else if( processGroup == 3 ){
			processStatus = CbrConstants.PROCESS_CD_31;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldNewProductDev.getSndRcvDraftStsCd(), c00005List);
				checkCurApprUser( oldNewProductDev.getSndRcvDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramNewProductDev.setSndRcvDraftStsCd( paramStsCd);
				
				mailType = CbrConstants.REQ_MAIL_TYPE_10;
				
				// [체크리스트-14] TCS 담당자도 승인 또는 부결을 처리할 수 있어야 함.
				if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
					
					apprStsCd = CbrConstants.STATUS_CD_04;
					processStatus = CbrConstants.PROCESS_CD_33;
					paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
					paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
					
					mailType = CbrConstants.REQ_MAIL_TYPE_05;
					mailTargetEmpNoList.add( oldNewProductDev.getReqDraftEmpNo());
				}
			}else{
				
				if( isApproveEnable) {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_33;
						paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_09;
					}
					
					boolean isCompleteAppr = isApprCompleted( oldNewProductDev.getSndRcvReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldNewProductDev.getSndRcvReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldNewProductDev.getSndRcvReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							apprStsCd = CbrConstants.STATUS_CD_02;
							
							mailType = CbrConstants.REQ_MAIL_TYPE_11;
						}
						
						paramNewProductDev.setSndRcvReviewStsCd( paramStsCd);	// 검토자 품의상태코드
						// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldNewProductDev.getSndRcvApprStsCd(), c00005List);
						checkCurApprUser( oldNewProductDev.getSndRcvApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							processStatus = CbrConstants.PROCESS_CD_32;
							mailType = CbrConstants.REQ_MAIL_TYPE_12;
						}
						paramNewProductDev.setSndRcvApprStsCd( paramStsCd);		// 승인자 품의상태코드
					}
					// 다음단계 접수 확인
				} else {
						
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_01)) {
						
						if( !isApprove || !StringUtils.equals( oldNewProductDev.getSndReviewDeptId(), user.getGroupId()) || !StringUtils.isEmpty( oldNewProductDev.getSndRsltDraftEmpNo())) {
							throw new CollaborationException( "접수 할수 없습니다.");
						}
						processStatus = CbrConstants.PROCESS_CD_40;
						paramNewProductDev.setSndRsltDraftEmpNo( user.getEmpNo());	// 2차검토부서결과 기안자명		
						paramNewProductDev.setSndRsltDraftStsCd( apprStsCd);		// 2차검토부서결과 품의상태코드
						paramNewProductDev.setProcessGroupNo( String.valueOf( processGroup + 1 ));
						
						// 승인자(팀장) 정보
//						Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( paramNewProductDev.getSndRsltDraftEmpNo());
//						String temLeaderEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
//						paramNewProductDev.setSndRsltApprEmpNo( temLeaderEmpNo);
//						paramNewProductDev.setSndRsltApprStsCd("01");
						
					}
				}
			}
			
		} else if( processGroup == 4 ){
			processStatus = CbrConstants.PROCESS_CD_41;
			
			// 현재 단계가 작성중이면
			if( isModViewEnable) {
				
				checkAppCompleted( oldNewProductDev.getSndRsltDraftStsCd(), c00005List);
				checkCurApprUser( oldNewProductDev.getSndRsltDraftEmpNo(), user);
				
				apprStsCd = CbrConstants.STATUS_CD_02;
				paramNewProductDev.setSndRsltDraftStsCd( paramStsCd);
				
				mailType = CbrConstants.REQ_MAIL_TYPE_14;
				// [체크리스트-14] TCS 담당자도 승인 또는 부결을 처리할 수 있어야 함.
				if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
					
					apprStsCd = CbrConstants.STATUS_CD_04;
					processStatus = CbrConstants.PROCESS_CD_43;
					paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
					paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
					
					mailType = CbrConstants.REQ_MAIL_TYPE_05;
					mailTargetEmpNoList.add( oldNewProductDev.getReqDraftEmpNo());
				}
			}else{
				
				if( isApproveEnable) {
					
					if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_04)) {
						
						processStatus = CbrConstants.PROCESS_CD_33;
						paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
						paramNewProductDev.setRejectReason( newProductDev.getRejectReason());
						
						mailType = CbrConstants.REQ_MAIL_TYPE_13;
					}
					
					boolean isCompleteAppr = isApprCompleted( oldNewProductDev.getSndRsltReviewStsCd(), c00005List);
					// 검토자가 있는지 확인
					if( StringUtils.isNotEmpty( oldNewProductDev.getSndRsltReviewEmpNo() ) && !isCompleteAppr ){
						
						checkCurApprUser( oldNewProductDev.getSndRsltReviewEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							apprStsCd = CbrConstants.STATUS_CD_02;
							mailType = CbrConstants.REQ_MAIL_TYPE_15;
						}
						
						paramNewProductDev.setSndRsltReviewStsCd( paramStsCd);	// 검토자 품의상태코드
						// 	승인 단계일경우
					} else {
						
						checkAppCompleted( oldNewProductDev.getSndRsltApprStsCd(), c00005List);
						checkCurApprUser( oldNewProductDev.getSndRsltApprEmpNo(), user);
						// 승인일경우
						if( StringUtils.equals( paramStsCd, CbrConstants.STATUS_CD_03)) {	
							
							processStatus = CbrConstants.PROCESS_CD_32;
							mailType = CbrConstants.REQ_MAIL_TYPE_16;
						}
						paramNewProductDev.setSndRsltApprStsCd( paramStsCd);		// 승인자 품의상태코드
						
						paramNewProductDev.setApprDate( DateUtil.getToday("yyyy.mm.dd"));		// 품의확정일자
					}
					// 다음단계 접수 확인
				}
			}
			
		}
		
		paramNewProductDev.setProcessStatus( processStatus); 
		paramNewProductDev.setApprStsCd( apprStsCd); 
		
		// 1. 개발검토의뢰서 수정
		newProductDevDao.updateApproveTbDevRequest( paramNewProductDev);
		// 2. 플로우 수정
		newProductDevDao.updateApproveTbDevReqApprLine( paramNewProductDev);
		
		try {
			//메일 발송오류시 승인과 영향받지 않게함.
			
			oldNewProductDev.setRejectReason( paramNewProductDev.getRejectReason());
			sendApproveMail( mailType, oldNewProductDev );
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	/**
	 * 메일보내기
	 * @param mailType
	 * @param newProductDev
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendApproveMail( int mailType, NewProductDev newProductDev) throws Exception {
		if( mailType == 0) {
			return;
		}
		
		UserAuthMgntSearchCondition searchCondition = new UserAuthMgntSearchCondition();
		List<UserAuthMgnt> userAuthMgntList = null;
		String serverMode = collaboUtils.getServerMode();
		
		String title = "";
		if( newProductDev != null) {
			
			title = newProductDev.getProductName() + " 개발 검토 의뢰서 ";
		}
		
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		
		switch ( mailType) {
		case CbrConstants.REQ_MAIL_TYPE_01:
			title += "승인 요청"; 
			if( StringUtils.isNotEmpty( newProductDev.getReqReviewEmpNo() )) {
				mailTargetEmpNoList.add( newProductDev.getReqReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( newProductDev.getReqApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_02:
			title += "반려";
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_03:
			title += "승인 요청"; 
			mailTargetEmpNoList.add( newProductDev.getReqApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_04:
			title += "1차검토 요청"; 
			searchCondition.setSearchGroupId( newProductDev.getReqRcvDeptId());
			searchCondition.setSearchWorkGbnCd( "10");
			searchCondition.setSearchUseYn("Y");
			userAuthMgntList = authMgntService.getUserAuthMgntList(searchCondition);
			break;
		case CbrConstants.REQ_MAIL_TYPE_05:
			title += "반려";
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_06:
			title += "승인 요청"; 
			if( StringUtils.isNotEmpty( newProductDev.getTcsReviewEmpNo() )) {
				mailTargetEmpNoList.add( newProductDev.getTcsReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( newProductDev.getTcsApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_07:
			title += "승인 요청";
			mailType = CbrConstants.REQ_MAIL_TYPE_07;
			mailTargetEmpNoList.add( newProductDev.getTcsApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_08:
			title += "2차검토 요청"; 
			searchCondition.setSearchGroupId( newProductDev.getSndReviewDeptId());
			searchCondition.setSearchWorkGbnCd( "10");
			searchCondition.setSearchUseYn("Y");
			userAuthMgntList = authMgntService.getUserAuthMgntList(searchCondition);
			break;
		case CbrConstants.REQ_MAIL_TYPE_09:
			title += "반려"; 
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_10:
			title += "접수 승인 요청"; 
			if( StringUtils.isNotEmpty( newProductDev.getSndRcvReviewEmpNo() )) {
				mailTargetEmpNoList.add( newProductDev.getSndRcvReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( newProductDev.getSndRcvApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_11:
			title += "접수 승인 요청"; 
			mailTargetEmpNoList.add( newProductDev.getSndRcvApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_12:
			title += "접수 승인"; 
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_13:
			title += "반려"; 
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_14:
			title += "결과 승인 요청"; 
			
			if( StringUtils.isNotEmpty( newProductDev.getSndRsltReviewEmpNo() )) {
				mailTargetEmpNoList.add( newProductDev.getSndRsltReviewEmpNo());
			}else{
				mailTargetEmpNoList.add( newProductDev.getSndRsltApprEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_15:
			title += "결과 승인 요청"; 
			mailTargetEmpNoList.add( newProductDev.getSndRsltApprEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_16:
			title += "검토 완료"; 
			mailTargetEmpNoList.add( newProductDev.getReqDraftEmpNo());
			mailTargetEmpNoList.add( newProductDev.getTcsDraftEmpNo());
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
		}else{
			
			if( userAuthMgntList != null ) {
				
				for ( UserAuthMgnt userAuthMgnt : userAuthMgntList) {
					HashMap<String, String> recieverMap = new HashMap<String, String>();
//					if( StringUtils.equals( serverMode, "local")) {
//						
//						recieverMap.put("email", "admin@eptest.co.kr");
//					}else{
						
						recieverMap.put("email", userAuthMgnt.getMail());
//					}
					recieverMap.put("name", userAuthMgnt.getUserName());
					receiverList.add(recieverMap);
				}
				
			}
		}
		
		String mainFrameUrl = "/approval/collaboration/npd/editNewProductDevView.do?mgntNo="+newProductDev.getMgntNo() +"&viewMode=modify";
		
		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("npdApprovalTemplate.vm");
		mail.setTitle( title);
		
		User sendUser = new User();
		//[통합테스트]메일 발신자 변경. 관리자에서 상신자로 변경
//		sendUser.setMail("admin@eptest.co.kr");
		String sendUserMail =  collaboCommonService.getUserMailAddr( newProductDev.getReqDraftEmpNo());
		sendUser.setMail( sendUserMail);
		mail.setToEmailList( receiverList);
		
		Map dataMap = new HashMap();
		dataMap.put("newProductDev", newProductDev);
		dataMap.put("mailType", mailType);
		dataMap.put("url", collaboUtils.getServerURL( mainFrameUrl));
		
		mailSendService.sendMail(mail, dataMap, sendUser);
	}
	
	/**
	 * 메일 배치
	 * @throws Exception
	 */
	public void sendScheduleLimitSend() throws Exception {
		
		String serverMode = collaboUtils.getServerMode();
		
		List<NewProductDev> ScheduleLimitList = getScheduleLimitTarget();
		
		if( ScheduleLimitList == null || ScheduleLimitList.size() < 1) {
			return;
		}
		
		// 게시물별 구분
		Map<String, List<NewProductDev>> sendMailMap = new HashMap<String, List<NewProductDev>>();
		for ( NewProductDev vo : ScheduleLimitList) {
			
			if( sendMailMap.containsKey( vo.getMgntNo()) ) {
				
				List<NewProductDev> senderList = sendMailMap.get( vo.getMgntNo());
				senderList.add( vo);
			}else {
				List<NewProductDev> senderList = new ArrayList<NewProductDev>();
				senderList.add(vo);
				sendMailMap.put( vo.getMgntNo(), senderList);
			}
		}
		
		// 메일 발송
		for( String key : sendMailMap.keySet() ){
			
			String title = "";
			String content = "";
			String mainFrameUrl = "";
			List receiverList = new ArrayList();
			NewProductDev newProductDev = null;
			
			List<NewProductDev> senderList = sendMailMap.get( key);
			for (NewProductDev newProductDev2 : senderList) {
				
				HashMap<String, String> recieverMap = new HashMap<String, String>();
				
				recieverMap.put("email", newProductDev2.getEmail());
				recieverMap.put("name", newProductDev2.getUserName());
				receiverList.add(recieverMap);
				if( newProductDev == null) {
					
					title = newProductDev2.getProductName() + " 개발 검토 의뢰서 검토 기한 초과 알림";
					
					mainFrameUrl = "/approval/collaboration/npd/editNewProductDevView.do?mgntNo="+ newProductDev2.getMgntNo() +"&viewMode=modify";
					content = "<html><body style='background: font: 100%/1.5em Tahoma,'돋움','Dotum';height: 100%;width:100%;'>"
						+ "<div style='border-top:2px solid #919fb2;width:1000px;height: 100%;padding:3px 5px;'>" 
						+ 	"<div style='margin-top:3px; border:1px solid #e0e0e0;width:100%;padding:3px 5px;'>"+newProductDev2.getReqDeptNm() + "<span style='color: #5F84C2;'>" + newProductDev2.getProductName() + "</span> 제품에 대한 검토 기한 초과가 되었습니다.<br /></div>"
						+ 	"<div style='position:relative; text-align:right; margin-bottom:7px;width:100%;'>"
						+ 		"<ul style='list-style-type: none;'><li style='display:inline;'><li><a  class='button' href="+collaboUtils.getServerURL( mainFrameUrl)+" title='신제품 개발 의뢰서 바로가기'><span>신제품 개발 의뢰서 바로가기</span></a></li></li></ul>"
						+ 	"</div>" 
						+"</div></body></html>";
					newProductDev = newProductDev2;
				}
			}
			Mail mail = new Mail();
			mail.setMailType(MailConstant.MAIL_TYPE_HTML);
			
			mail.setTitle( title);
			mail.setContent(content);
			User sendUser = new User();
			
			//[통합테스트]메일 발신자 변경. 관리자에서 상신자로 변경
//			sendUser.setMail("admin@eptest.co.kr");
			sendUser.setMail( newProductDev.getSenderEmail());
			
			mail.setToEmailList( receiverList);
			
			Map dataMap = new HashMap();
			
			mailSendService.sendMail(mail, dataMap, sendUser);
        }
	}
	
	/**
	 * 참조부서 조회
	 * @param newProductDev
	 * @throws Exception
	 */
	private void getDevReqShareDept( NewProductDev newProductDev) throws Exception{
		
		List<DevReqShareDept> devReqShareDeptList = devReqShareDeptDao.getDevReqShareDept( newProductDev.getMgntNo());
		newProductDev.setDevReqShareDeptList( devReqShareDeptList);
	}
	
	/**
	 * 신제품 개발의뢰서 삭제
	 */
	public void deleteNewProductDev( NewProductDev newProductDev, User user) throws Exception {
		
		checkPermission(newProductDev, user, CbrConstants.STAT_DELETE);
		
		newProductDevDao.deleteTbDevRequest( newProductDev.getMgntNo());
		newProductDevDao.deleteTbDevReqApprLine( newProductDev.getMgntNo());
		
		devReqShareDeptDao.deleteDevReqShareDept( newProductDev.getMgntNo());
	}
	
	/**
	 * 신제품 개발 초기화
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void initRejctNewProductDev(  NewProductDev newProductDev, User user) throws Exception {
		
		newProductDevDao.initTbDevRequest(newProductDev);
		newProductDevDao.initTbDevReqApprLine(newProductDev);
		
		devReqShareDeptDao.deleteDevReqShareDept( newProductDev.getMgntNo());
	}
	
	/**
	 * 접수자 변경
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void changeReceiptNewProductDev(  NewProductDev newProductDev, User user) throws Exception {
		
		String processGroupNo = newProductDev.getProcessGroupNo();
		if( StringUtils.equals( processGroupNo, "2")) {
			
			newProductDev.setFstReviewEmpNo( user.getEmpNo());
			newProductDev.setTcsDraftEmpNo( user.getEmpNo());
		}else if( StringUtils.equals( processGroupNo, "3")) {
			
			newProductDev.setSndRcvDraftEmpNo( user.getEmpNo());
		}else if( StringUtils.equals( processGroupNo, "4")) {
			
			newProductDev.setSndRsltDraftEmpNo( user.getEmpNo());
		}
			
		newProductDevDao.updateReciptTbDevRequest(newProductDev);
		newProductDevDao.updateReciptTbDevReqApprLine(newProductDev);
	}
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public NewProductDev getFileObject( NewProductDev newProductDev, User user) throws Exception {
		
		String editType = newProductDev.getFileEditType();
		String fileId = "";
		if( StringUtils.equals( "req", editType)) {
			
			fileId = newProductDev.getReqFileItemId();
		}else if( StringUtils.equals( "rslt", editType)) {
			
			fileId = newProductDev.getRsltFileItemId();
		}
		
		if( StringUtils.isNotEmpty( fileId) ){
			
			List<FileData> fileDataList = new ArrayList<FileData>();
			fileDataList = this.fileService.getItemFile( fileId, NewProductDev.ITEM_FILE_TYPE);
			newProductDev.setFileDataList( fileDataList);
			
			List<FileData> ecmFileDataList = new ArrayList<FileData>();
			ecmFileDataList = this.fileService.getItemFileEcm( fileId, NewProductDev.ITEM_FILE_TYPE);
			newProductDev.setEcmFileDataList( ecmFileDataList);
		}
		
		return newProductDev;
	}
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( NewProductDev newProductDev, User user) throws Exception {
		
		updateFile( newProductDev, user);
		NewProductDev resultNewProductDev = this.getFileObject( newProductDev, user);
		
		String fileId = "";
		String editType = newProductDev.getFileEditType();
		if( StringUtils.equals( "req", editType)) {
			
			fileId = newProductDev.getReqFileItemId();
		}else if( StringUtils.equals( "rslt", editType)) {
			
			fileId = newProductDev.getRsltFileItemId();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put( "fileItemId", fileId);
		resultMap.put( "fileDataListJson", resultNewProductDev.getFileDataList());
		
		return CollaboUtils.convertJsonString( "datas", resultMap);
	}
	
	private String getFileId( NewProductDev newProductDev) throws Exception {
		
		String fileItemId = "";
		String editType = newProductDev.getFileEditType();
		if( StringUtils.equals( "req", editType)) {
			
			fileItemId = newProductDev.getReqFileItemId();
		}else if( StringUtils.equals( "rslt", editType)) {
			
			fileItemId = newProductDev.getRsltFileItemId();
		}
		return fileItemId;
	}
	
	private void setFileId( NewProductDev newProductDev, String fileId) throws Exception {
		
		String editType = newProductDev.getFileEditType();
		if( StringUtils.equals( "req", editType)) {
			
			newProductDev.setReqFileItemId( fileId);
		}else if( StringUtils.equals( "rslt", editType)) {
			
			newProductDev.setRsltFileItemId( fileId);
		}
	}
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( NewProductDev newProductDev, User user) throws Exception {
		
		NewProductDev oldNewProductDev = null;
		NewProductDevSearchCondition searchCondition = new NewProductDevSearchCondition();
		
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		String fileItemId = this.getFileId( newProductDev);
		
		// 1. 게시물 정보 가져오고
		// 1-1. 기존문서이면
		if( StringUtils.isNotEmpty( newProductDev.getMgntNo())){
			
			searchCondition.setMgntNo( newProductDev.getMgntNo());
			oldNewProductDev = newProductDevDao.getDevRequest( searchCondition);
			fileItemId = this.getFileId( newProductDev);
		}
		
		// 1. 게시물 X 파일등록이 최초이면 DB x
		// 2. 게시물 X 파일이 있으면 DB X
		// 3. 게시물 O 파일이 최초이면 DB 업데이트 <-- 일관되게 업데이트하자
		// 4. 게시물 O 파일이 있으면 DB 업데이트  <--무조건 되고 있고
		if( StringUtils.isEmpty( fileItemId)) {
			
			fileItemId = idgenService.getNextId();
			this.setFileId( newProductDev, fileItemId);
			
			// 파일이 x, 게시물존재 O fileId 업데이트
			if( StringUtils.isNotEmpty( newProductDev.getMgntNo())){
				newProductDevDao.updateFileId( newProductDev);
			}
		}
		
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( fileItemId, Proposal.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile( fileItemId, Proposal.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (newProductDev.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : newProductDev.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			newProductDev.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				newProductDev.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				newProductDev.setAttachFileCount(attachCnt);
			}
		}
		
		// 파일링크 생성
		if ( newProductDev.getFileLinkList() != null) {
			
			this.fileService.saveFileLink( newProductDev.getFileLinkList(), fileItemId, Proposal.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split( newProductDev.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split( newProductDev.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split( newProductDev.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split( newProductDev.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split( newProductDev.getEcmFileUrl2(), "|");
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
		String uploadFolderForFile = CollaboUtils.getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = CollaboUtils.getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
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
					if( CollaboUtils.checkImageFile(ecmFileNames[i])){
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
				if( CollaboUtils.checkImageFile(ecmFileNames[i])){
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
	 * 참조부서 등록( 모두 삭제후 등록)
	 * @param newProductDev
	 * @throws Exception
	 */
	private void insertDevReqShareDept( NewProductDev newProductDev) throws Exception{
		
		if( !StringUtils.equals( newProductDev.getProcessGroupNo(), CbrConstants.PROCESS_GROUP_2) ) {
			
			return;
		}
		
		DevReqShareDept devReqShareDept = new DevReqShareDept();
		devReqShareDept.setMgntNo( newProductDev.getMgntNo());
		
		devReqShareDeptDao.deleteDevReqShareDept( newProductDev.getMgntNo());
		
		List<String> devReqShareDeptIdList = newProductDev.getDevReqShareDeptIdList();
		if( devReqShareDeptIdList != null) {
			
			for (String devReqShareDeptId : devReqShareDeptIdList) {
				
				devReqShareDept.setShareDeptId( devReqShareDeptId);
				devReqShareDeptDao.insertDevReqShareDept( devReqShareDept);
			}
		}
	}
	
	/**
	 * 승인자 정보(팀장) 넣기
	 * @param user
	 * @param newProductDev
	 * @param nGroupCd
	 * @throws Exception
	 */
	private void setApprTeamLeader( User user, NewProductDev newProductDev, int nGroupCd) throws Exception{
		
		String empNo = user.getEmpNo();
		String apprEmpNo = "";
		String apprEmpNm = "";
		if( StringUtils.isNotEmpty( empNo)) {
			
			Map<String, Object> reqApprMap = collaboCommonService.getTeamLeaderInfo( user.getEmpNo());
			apprEmpNo = MapUtils.getString( reqApprMap, "empNo", "");
			apprEmpNm = MapUtils.getString( reqApprMap, "userName", "");
		}
		
		
		if( nGroupCd == 1) {
			
			newProductDev.setReqApprEmpNo( apprEmpNo);
			newProductDev.setReqApprEmpNm( apprEmpNm);
		}else if( nGroupCd == 2) {
			
			newProductDev.setTcsApprEmpNo( apprEmpNo);
			newProductDev.setTcsApprEmpNm( apprEmpNm);
		}else if( nGroupCd == 3) {
			
			newProductDev.setSndRcvApprEmpNo( apprEmpNo);
			newProductDev.setSndRcvApprEmpNm( apprEmpNm);
		}else if( nGroupCd == 4) {
			
			newProductDev.setSndRsltApprEmpNo( apprEmpNo);
			newProductDev.setSndRsltApprEmpNm( apprEmpNm);
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
	 * 권한 확인
	 * @param NewProductDev
	 * @param user
	 * @param statCd
	 * @throws Exception
	 */
	public NewProductDev checkPermission( NewProductDev newProductDev, User user, int statCd) throws Exception {
		
		NewProductDevSearchCondition searchCondition = new NewProductDevSearchCondition();
		searchCondition.setMgntNo( newProductDev.getMgntNo());
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		// 1. 게시물 정보 가져오고
		NewProductDev OldNewProductDev = newProductDevDao.getDevRequest(searchCondition);
		
		if( OldNewProductDev == null) {
			throw new CollaborationException("더이상 존재하지 않는 게시물입니다.");
		}
		
		NpdPermission permission = makePermission( OldNewProductDev, user);
		
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
		case CbrConstants.STAT_ADD_REQ_SCH_CD:
			
			if( !permission.isReqScheduleCdBtnActive()) {
				throw new CollaborationException("수정 권한이 없습니다.");
			}
			break;

		default:
			String AuthYn = OldNewProductDev.getAuthYn();
			if( !StringUtils.equals( AuthYn, CbrConstants.YES)) {
				throw new IKEP4AuthorizedException();
			}
			break;
		}
		
		return OldNewProductDev;
	}
	
	/**
	 * 화면 권한 확인
	 * @param newProductDev
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private NpdPermission makePermission( NewProductDev newProductDev, User user) throws Exception {
		
		// 조회권한 확인
		if( !StringUtils.equals( newProductDev.getAuthYn(), CbrConstants.YES)) {
			
			throw new IKEP4AuthorizedException();
		}
		
		NpdPermission npdPermission = new NpdPermission();
		
		List<CommonCode> c00002List = commonCodeService.getCommonCodeList( "C00002");
		List<CommonCode> c00005List = commonCodeService.getCommonCodeList( "C00005");
		
		String processStatus = newProductDev.getProcessStatus();
		
		int processGroup = 0;
		boolean isViewModi = false;
		boolean isApproveEnable = false;	// 결재진행중
		boolean isApprove = false;	// 승인완료
		boolean isReject = false;	// 부결
		
		// 부결
		if( StringUtils.equals( newProductDev.getApprStsCd(), CbrConstants.STATUS_CD_04)) {
			
			npdPermission.setRejectBtnActive();
			
			// [단위테스트-12] 의뢰부서의 기안자는 반려된 의뢰서에 대해 접수버튼을 눌러 다시 결재를 진행할 수 있게 처리
			if( StringUtils.equals( newProductDev.getReqDraftEmpNo(), user.getEmpNo() )) {
				npdPermission.setInitRejctBtnActive( true);
			}
			
		} else {
			
			// 1. 현재 프로세스 코드로 , 화면 수정가능, 승인/부결 가능여부를 가져온다.
			for (CommonCode commonCode : c00002List) {
				if( StringUtils.equals( processStatus, commonCode.getComCd()) ) {
					
					processGroup = commonCode.getNumCol1();
					isViewModi = convIntToBoolean( commonCode.getNumCol2());
					isApproveEnable = convIntToBoolean( commonCode.getNumCol3());
					isApprove = convIntToBoolean( commonCode.getNumCol4());
					isReject = convIntToBoolean( commonCode.getNumCol5());
					break;
				}
			}
			
			
			// 1.영업부서 의뢰단계
			if( processGroup == 1 ) {
				
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( newProductDev.getReqDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					
					npdPermission.setReqModActive();
				}
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				if( StringUtils.equals( newProductDev.getReqDraftEmpNo(), user.getEmpNo()) 
						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_10) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_11) )
						&& !( isApprove || isReject)
						&& !isApproved( newProductDev.getReqReviewStsCd())
					)) 
				{
					npdPermission.setReqModActive2();
				}
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					// 검토자 승인/부결 확인
					boolean isReviewApprComplete = isApprCompleted( newProductDev.getReqReviewStsCd(), c00005List);
					if( StringUtils.isNotEmpty( newProductDev.getReqReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( newProductDev.getReqReviewEmpNo(), user.getEmpNo())) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( newProductDev.getReqApprEmpNo(), user.getEmpNo()) && !isApprCompleted( newProductDev.getReqApprStsCd(), c00005List)) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}
					
				}
				
				// 승인 완료 - TCS부서일경우 활성화
				if( isApprove) {
					// To-do1 사용자 관리에 관리부서로 등록되었을경우 확인해야함
					if( StringUtils.equals( newProductDev.getReqRcvDeptId(), user.getGroupId()) && StringUtils.isEmpty( newProductDev.getTcsDraftEmpNo()) ) {
						
						npdPermission.setReceiptBtnActive( true);
					}
				}
				// 2. TCS부서 단계
			} else if( processGroup == 2) {
				
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( newProductDev.getTcsDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					// 상위자 넣어주기
					npdPermission.setTcsModActive();
					if( StringUtils.isEmpty(newProductDev.getTcsApprEmpNo())) {
						npdPermission.setApproveBtnActive( false);
						setApprTeamLeader( user, newProductDev, processGroup);
					}
				}
				
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				if( StringUtils.equals( newProductDev.getTcsDraftEmpNo(), user.getEmpNo()) 
						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_20) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_21) )
						&& !( isApprove || isReject)
						&& !isApproved( newProductDev.getTcsReviewStsCd())
					)) 
				{
					npdPermission.setTcsModActive2();
				}
				
				//[단위테스트-18] 검토자 또는 승인자가 승인/부결을 하지 않은 의뢰서에는 다른 동료가 접수를 하여 업무 이관을 할 수 있게 처리
				if( !StringUtils.equals( newProductDev.getTcsDraftEmpNo(), user.getEmpNo())
					&& !( isApprove || isReject)
					&& StringUtils.equals( newProductDev.getReqRcvDeptId(), user.getGroupId())
					//[단위테스트-23]접수 버튼은 검토자/승인자가 승인 또는 부결을 안한 상태에서 로그인 사원이 검토자/기안자가 아닌 경우에만 보이도록 수정요함
					&& StringUtils.equals( newProductDev.getTcsReviewEmpNo(), user.getEmpNo())
					&& StringUtils.equals( newProductDev.getTcsApprEmpNo(), user.getEmpNo())
					) 
				{
					
					npdPermission.setReReceiptBntActive( true);
				}
				
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					// 검토자 승인/부결 확인
					boolean isReviewApprComplete = isApprCompleted( newProductDev.getTcsReviewStsCd(), c00005List);
					if( StringUtils.isNotEmpty( newProductDev.getTcsReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( newProductDev.getTcsReviewEmpNo(), user.getEmpNo())) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( newProductDev.getTcsApprEmpNo(), user.getEmpNo()) && !isApprCompleted( newProductDev.getTcsApprStsCd(), c00005List)) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}
					
				}
				
				// 승인 완료 - 2차검토부서일경우 활성화
				if( isApprove) {
					
					if( StringUtils.equals( newProductDev.getSndReviewDeptId(), user.getGroupId()) && StringUtils.isEmpty( newProductDev.getSndRcvDraftEmpNo()) ) {
						
						npdPermission.setReceiptBtnActive( true);
					}
					
					// [통합테스트] 2차 검토부서 승인단계에서 TCS팀에서 요구일정을 수정할 수 있는팝업을 제공
					if( StringUtils.equals( newProductDev.getReqRcvDeptId() , user.getGroupId())) {
						npdPermission.setReqScheduleCdBtnActive( true);
					}
				}
				// 2차 검토부서 접수
			} else if( processGroup == 3) {
				
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( newProductDev.getSndRcvDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					// 상위자 넣어주기
					npdPermission.setSndRcvModActive();
					if( StringUtils.isEmpty( newProductDev.getSndRcvApprEmpNo())) {
						npdPermission.setApproveBtnActive( false);
						setApprTeamLeader( user, newProductDev, processGroup);
					}
				}
				
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				if( StringUtils.equals( newProductDev.getSndRcvDraftEmpNo(), user.getEmpNo()) 
						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_30) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_31) )
						&& !( isApprove || isReject)
						&& !isApproved( newProductDev.getSndRcvReviewStsCd())
					)) 
				{
					npdPermission.setSndRcvModActive2();
				}
				
				//[단위테스트-18] 검토자 또는 승인자가 승인/부결을 하지 않은 의뢰서에는 다른 동료가 접수를 하여 업무 이관을 할 수 있게 처리
				if( !StringUtils.equals( newProductDev.getSndRcvDraftEmpNo(), user.getEmpNo())
					&& !( isApprove || isReject)
					&& StringUtils.equals( newProductDev.getSndReviewDeptId(), user.getGroupId())
					//[단위테스트-23]접수 버튼은 검토자/승인자가 승인 또는 부결을 안한 상태에서 로그인 사원이 검토자/기안자가 아닌 경우에만 보이도록 수정요함
					&& StringUtils.equals( newProductDev.getSndRcvReviewEmpNo(), user.getEmpNo())
					&& StringUtils.equals( newProductDev.getSndRcvApprEmpNo(), user.getEmpNo())
					) 
				{
					
					npdPermission.setReReceiptBntActive( true);
				}
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					// 검토자 승인/부결 확인
					boolean isReviewApprComplete = isApprCompleted( newProductDev.getSndRcvReviewStsCd(), c00005List);
					if( StringUtils.isNotEmpty( newProductDev.getSndRcvReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( newProductDev.getSndRcvReviewEmpNo(), user.getEmpNo())) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( newProductDev.getSndRcvApprEmpNo(), user.getEmpNo()) && !isApprCompleted( newProductDev.getSndRcvApprStsCd(), c00005List)) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}
					
				}
				
				// 승인 완료 - 2차검토부서일경우 활성화
				if( isApprove) {
					
					if( StringUtils.equals( newProductDev.getSndReviewDeptId(), user.getGroupId()) && StringUtils.isEmpty( newProductDev.getSndRsltDraftEmpNo()) ) {
						
						npdPermission.setReceiptBtnActive( true);
					}
				}
				
				// [통합테스트] 2차 검토부서 승인단계에서 TCS팀에서 요구일정을 수정할 수 있는팝업을 제공
				if( StringUtils.equals( newProductDev.getReqRcvDeptId() , user.getGroupId())) {
					npdPermission.setReqScheduleCdBtnActive( true);
				}
				
				// 2차검토부서결과
			} else if( processGroup == 4) {
				// 1. 상신자의 화면, 버튼 활성화 여부
				if( StringUtils.equals( newProductDev.getSndRsltDraftEmpNo(), user.getEmpNo()) && isViewModi) {
					// 상위자 넣어주기
					npdPermission.setSndRsltModActive();
					if( StringUtils.isEmpty( newProductDev.getSndRsltApprEmpNo())) {
						npdPermission.setApproveBtnActive( false);
						setApprTeamLeader( user, newProductDev, processGroup);
					}
				}
				
				// [단위테스트-10]의뢰자는 의뢰부서의 검토자나 승인자가 승인 또는 반려하기 전까지 의뢰서를 수정할 수 있어야 함.
				if( StringUtils.equals( newProductDev.getSndRsltDraftEmpNo(), user.getEmpNo()) 
						&& ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_30) || ( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_31) )
						&& !( isApprove || isReject)
						&& !isApproved( newProductDev.getSndRsltReviewStsCd())
					)) 
				{
					npdPermission.setSndRsltModActive2();
				}
				
				//[단위테스트-18] 검토자 또는 승인자가 승인/부결을 하지 않은 의뢰서에는 다른 동료가 접수를 하여 업무 이관을 할 수 있게 처리
				if( !StringUtils.equals( newProductDev.getSndRsltDraftEmpNo(), user.getEmpNo())
					&& !( isApprove || isReject)
					&& StringUtils.equals( newProductDev.getSndReviewDeptId(), user.getGroupId())
					//[단위테스트-23]접수 버튼은 검토자/승인자가 승인 또는 부결을 안한 상태에서 로그인 사원이 검토자/기안자가 아닌 경우에만 보이도록 수정요함
					&& StringUtils.equals( newProductDev.getSndRsltReviewEmpNo(), user.getEmpNo())
					&& StringUtils.equals( newProductDev.getSndRsltApprEmpNo(), user.getEmpNo())
					) 
				{
					
					npdPermission.setReReceiptBntActive( true);
				}
				
				// 2. 검토자, 승인자의 승인/부결 버튼을 활성화 시킨다. 
				if( isApproveEnable ) {
					
					// 검토자 승인/부결 확인
					boolean isReviewApprComplete = isApprCompleted( newProductDev.getSndRsltReviewStsCd(), c00005List);
					if( StringUtils.isNotEmpty( newProductDev.getSndRsltReviewEmpNo() ) && !isReviewApprComplete ){
						
						if( StringUtils.equals( newProductDev.getSndRsltReviewEmpNo(), user.getEmpNo())) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}else{
						
						if( StringUtils.equals( newProductDev.getSndRsltApprEmpNo(), user.getEmpNo()) && !isApprCompleted( newProductDev.getSndRsltApprStsCd(), c00005List)) {
							
							npdPermission.setBtnApprRejectActive();
						}
					}
					
				}
				
				// [통합테스트] 2차 검토부서 승인단계에서 TCS팀에서 요구일정을 수정할 수 있는팝업을 제공
				if( StringUtils.equals( newProductDev.getReqRcvDeptId() , user.getGroupId())) {
					
					if( !( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_32) || StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_33) )) {
						
						npdPermission.setReqScheduleCdBtnActive( true);
					}
				}
				
			}
		}
		
		newProductDev.setProcessGroupNo( String.valueOf( processGroup));
		
		// [통테-체크리스트] 상신단계에서 '승인'을 '상신' 으로 변경
		if( npdPermission.isApproveBtnActive() ) {
			
			if( StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_10) || StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_20) 
					|| StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_30) || StringUtils.equals( processStatus, CbrConstants.PROCESS_CD_40)) {
				npdPermission.setDrafter( true);
			}
		}
		// ECM User확인
		boolean isEcmUser = collaboCommonService.isEcmUser( user);
		npdPermission.setEcmRoll( isEcmUser);
		
		return npdPermission;
	}
	
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
