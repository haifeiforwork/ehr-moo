/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.proposal.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaborationException;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.proposal.dao.ProposalDao;
import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.proposal.model.ProposalPermission;
import com.lgcns.ikep4.approval.collaboration.proposal.search.ProposalSearchCondition;
import com.lgcns.ikep4.approval.collaboration.proposal.service.ProposalService;
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
 * 개발제안서 ProposalService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class ProposalServiceImpl implements ProposalService{
	
	/** The log. */
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	/** The FileService service. */
	@Autowired
	private FileService fileService;
	
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
	private CollaboUtils collaboUtils;
	
	@Autowired
	private ProposalDao proposalDao;
	
	/**
	 * 개발제안서 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<Proposal> getProposalList( ProposalSearchCondition searchCondition, User user) throws Exception {
		
		SearchResult<Proposal> searchResult = null;
		try {
			
			if( StringUtils.isEmpty( searchCondition.getSearchStartReqDate() ) ) {
				searchCondition.setSearchStartReqDate( DateUtil.getToday("yyyy.MM") + ".01");
				searchCondition.setSearchEndReqDate( DateUtil.getToday("yyyy.MM.dd"));
			}
			
			
			boolean isSystemAdmin = aclService.isSystemAdmin( "Approval", user.getUserId());
			searchCondition.setIsAdmin( isSystemAdmin);
			searchCondition.setSessionEmpNo( user.getEmpNo());
			searchCondition.setSessionGoupId( user.getGroupId());
			
			int totalCnt = proposalDao.getProposaltCount( searchCondition);
			searchCondition.terminateSearchCondition( totalCnt);
			
			if( searchCondition.isEmptyRecord()) {
				searchResult = new SearchResult<Proposal>( searchCondition);
			}else{
				List<Proposal> proposalList = proposalDao.getProposalList( searchCondition);
				searchResult = new SearchResult<Proposal>( proposalList, searchCondition);
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
	public Map<String, Object> getProposalObject( ProposalSearchCondition searchCondition, User user) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Proposal proposal = null;
		ProposalPermission permission = null;
		
		if( StringUtils.equals( "modify", searchCondition.getViewMode() ) ) {
			
			searchCondition.setSessionEmpNo( user.getEmpNo());
			searchCondition.setSessionGoupId( user.getGroupId());
			
			// 1. 게시물 정보 가져오고
			proposal = proposalDao.getProposal( searchCondition);
//			// 2. 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty( proposal.getFileItemId())) {
				
				fileDataList = this.fileService.getItemFile( proposal.getFileItemId(), Proposal.ITEM_FILE_TYPE);
			}
			proposal.setFileDataList(fileDataList);
//			
			permission = makePermission( proposal, user);
			
		}else{
			
			// 작성자 정보
			proposal = new Proposal();
			proposal.setReqEmpNo( user.getEmpNo());
			proposal.setReqEmpNm( user.getUserName());
			proposal.setReqDeptId( user.getGroupId());
			proposal.setReqDeptNm( user.getTeamName());
			proposal.setReqUserRank( user.getJobTitleName());
			proposal.setReqDate( DateUtil.getToday("yyyy.MM.dd"));
			
			permission = new ProposalPermission( true);
			
			searchCondition.setSessionGoupId( user.getGroupId());
			
			ProposalPermission permission2 =  proposalDao.getDeptOpinionPermission( searchCondition);
			if( permission2.isTcsAuth()) {
				permission.setSaveBtnActive( true);
				permission.setTcsViewActive( true);
			}
			
		}
		
		permission.setEcmRoll( collaboCommonService.isEcmUser( user));
		
		resultMap.put( "proposal", proposal);
		resultMap.put( "permission", permission);
		
		return resultMap;
	}
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( Proposal proposal, User user) throws Exception {
		
		updateFile( proposal, user);
		Proposal resultProposal = this.getFileObject( proposal, user);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put( "fileItemId", resultProposal.getFileItemId());
		resultMap.put( "fileDataListJson", resultProposal.getFileDataList());
		
		return CollaboUtils.convertJsonString( "datas", resultMap);
	}
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Proposal getFileObject( Proposal proposal, User user) throws Exception {
		
		if( StringUtils.isNotEmpty( proposal.getFileItemId()) ){
			
			List<FileData> fileDataList = new ArrayList<FileData>();
			fileDataList = this.fileService.getItemFile( proposal.getFileItemId(), Proposal.ITEM_FILE_TYPE);
			proposal.setFileDataList( fileDataList);
			
			List<FileData> ecmFileDataList = new ArrayList<FileData>();
			ecmFileDataList = this.fileService.getItemFileEcm( proposal.getFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			proposal.setEcmFileDataList( ecmFileDataList);
		}
		
		return proposal;
	}
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( Proposal proposal, User user) throws Exception {
		
		Proposal oldProposal = null;
		ProposalSearchCondition searchCondition = new ProposalSearchCondition();
		
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		String fileItemId = proposal.getFileItemId();
		// 1. 게시물 정보 가져오고
		// 1-1. 새문서이면
		if( StringUtils.isNotEmpty( proposal.getProposalNo())){
			
			searchCondition.setProposalNo( proposal.getProposalNo());
			oldProposal = proposalDao.getProposal( searchCondition);
			fileItemId = oldProposal.getFileItemId();
		}
		
		// 1. 게시물 X 파일등록이 최초이면 DB x
		// 2. 게시물 X 파일이 있으면 DB X
		// 3. 게시물 O 파일이 최초이면 DB 업데이트 <-- 일관되게 업데이트하자
		// 4. 게시물 O 파일이 있으면 DB 업데이트  <--무조건 되고 있고
		if( StringUtils.isEmpty( fileItemId)) {
			
			fileItemId = idgenService.getNextId();
			proposal.setFileItemId( fileItemId);
			
			// 파일이 x, 게시물존재 O fileId 업데이트
			if( StringUtils.isNotEmpty( proposal.getProposalNo())){
				proposalDao.updateFileId( proposal);
			}
		}
		
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( fileItemId, Proposal.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile( fileItemId, Proposal.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (proposal.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : proposal.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			proposal.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				proposal.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				proposal.setAttachFileCount(attachCnt);
			}
		}
		
		// 파일링크 생성
		if ( proposal.getFileLinkList() != null) {
			
			this.fileService.saveFileLink( proposal.getFileLinkList(), fileItemId, Proposal.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split( proposal.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split( proposal.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split( proposal.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split( proposal.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split( proposal.getEcmFileUrl2(), "|");
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
	 * 개발제안서 등록
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void createProposal( Proposal proposal, User user) throws Exception {
		
		proposal.setReqEmpNo( user.getEmpNo());
		proposal.setReqDeptId( user.getGroupId());
		proposal.setReqDate( DateUtil.getToday("yyyy.MM.dd"));
		proposal.setReqUserRank( user.getJobTitleName());
		
		proposal.setProposalNo( DateUtil.getToday("yyyy-MM-"));
		
		proposalDao.insertProposal(proposal);
		if( StringUtils.equals( proposal.getSaveYn(), CbrConstants.YES ) ) {
			sendMail( proposal);
		}
	}
	
	/**
	 * 개발제안서 수정
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void updateProposal( Proposal proposal, User user) throws Exception {
		
		Proposal oldProposal = checkPermission( proposal, user, CbrConstants.STAT_UPDATE);
		
		if( StringUtils.equals( oldProposal.getSaveYn(), CbrConstants.YES) && !StringUtils.equals( proposal.getProposalStatus(), oldProposal.getProposalStatus()) ) {
			
			proposal.setSessionEmpNo( user.getEmpNo());
		}
		
		proposalDao.updateProposal( proposal);
		
		// 임시저장상태
		if( StringUtils.equals( oldProposal.getSaveYn(), CbrConstants.NO) || StringUtils.isEmpty( oldProposal.getSaveYn())) {
			
			if( StringUtils.equals( proposal.getSaveYn(), CbrConstants.YES)) {
				sendMail( proposal);
			}
		}
	}
	
	/**
	 * 개발제안서 삭제
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void deleteProposal( Proposal proposal, User user) throws Exception {
		
		checkPermission( proposal, user, CbrConstants.STAT_DELETE);
		proposalDao.deleteProposal( proposal);
	}
	
	/**
	 * 부서의견등록 권한 확인
	 * @param proposal
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Proposal getOpinionProposal( Proposal proposal, User user) throws Exception {
		
		return checkPermission( proposal, user, CbrConstants.STAT_OPINION);
	}
	
	/**
	 * 부서의견 수정
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void UpdateOpinion( Proposal proposal, User user) throws Exception {
		
		checkPermission( proposal, user, CbrConstants.STAT_OPINION);
		proposalDao.updateOpinion( proposal);
	}
	
	
	
	/**
	 * 메일보내기
	 * @param proposal
	 * @throws Exception
	 */
	private void sendMail( Proposal proposal) throws Exception {
		
		List<Proposal> mailTargetList = proposalDao.getMailTargetUser( proposal);
		if( mailTargetList == null || mailTargetList.size() < 1) {
			
			return;
		}
		
		String serverMode = collaboService.getServerMode();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List receiverList = new ArrayList();
		
		for (Proposal proposal2 : mailTargetList) {
			
			String mailAddr = proposal2.getEmail();
			String userName = proposal2.getUserName();
			
			if( StringUtils.isNotEmpty( mailAddr)) {
				
				HashMap<String, String> recieverMap = new HashMap<String, String>();
				if( StringUtils.equals( serverMode, "local")) {
					
					recieverMap.put("email", "admin@eptest.co.kr");
				}else{
					
					recieverMap.put("email", mailAddr);
				}
				
				recieverMap.put("name", userName);
				
				receiverList.add(recieverMap);
				
				if( StringUtils.isEmpty( proposal.getReqDeptNm() ) ) {
					proposal.setReqDeptNm( proposal2.getReqDeptNm());
				}
			}
		}
		
		
		String title = proposal.getProductName() + " 개발 제안서에 대한 부서 의견 요청";
		
		String mainFrameUrl = "/approval/collaboration/proposal/editProposalView.do?proposalNo="+proposal.getProposalNo() +"&viewMode=modify";
		
		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("proposalTemplate.vm");
		mail.setTitle( title);
		
		User sendUser = new User();
		//[통합테스트]메일 발신자 변경. 관리자에서 상신자로 변경
//		sendUser.setMail("admin@eptest.co.kr");
		String sendUserMail =  collaboCommonService.getUserMailAddr( proposal.getReqEmpNo());
		sendUser.setMail( sendUserMail);
		
		mail.setToEmailList( receiverList);
		
		Map dataMap = new HashMap();
		dataMap.put("proposal", proposal);
		dataMap.put("url", collaboService.getServerURL( mainFrameUrl));
		
		mailSendService.sendMail(mail, dataMap, sendUser);
	}
	
	/**
	 * 권한 확인
	 * @param proposal
	 * @param user
	 * @param statCd
	 * @throws Exception
	 */
	public Proposal checkPermission (Proposal proposal, User user, int statCd) throws Exception {
		
		ProposalSearchCondition searchCondition = new ProposalSearchCondition();
		searchCondition.setProposalNo( proposal.getProposalNo());
		searchCondition.setSessionEmpNo( user.getEmpNo());
		searchCondition.setSessionGoupId( user.getGroupId());
		
		// 1. 게시물 정보 가져오고
		Proposal oldProposal = proposalDao.getProposal( searchCondition);
		
		if( oldProposal == null) {
			throw new CollaborationException("더이상 존재하지 않는 게시물입니다.");
		}
		
		ProposalPermission permission = makePermission(oldProposal, user);
		
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
			proposal.setSaveType( permission.getSaveType());
			break;
		case CbrConstants.STAT_OPINION:
			
			String opinionType = proposal.getOpinionType();
			boolean isAuth = false;
			String opinionValue = "";
			if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_QAD) ) {
				
				isAuth = permission.isQadAuth();
				opinionValue = oldProposal.getQadOpinion();
			}else if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_LAB) ) {

				isAuth = permission.isLabAuth();
				opinionValue = oldProposal.getLabOpinion();
			}else if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_SALES) ) {
			
				isAuth = permission.isSalesAuth();
				opinionValue = oldProposal.getSalesOpinion();
			}else if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_SCM) ) {
			
				isAuth = permission.isScmAuth();
				opinionValue = oldProposal.getScmOpinion();
			}else if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_MARKET) ) {
			
				isAuth = permission.isMarketAuth();
				opinionValue = oldProposal.getMarketOpinion();
			}else if( StringUtils.equals( opinionType, CbrConstants.STAT_OPINION_TCS) ) {
				
				isAuth = permission.isTcsAuth();
				opinionValue = oldProposal.getTcsTotalOpinion();
			}
			if( !isAuth) {
				throw new CollaborationException("권한이 없습니다.");
			}
			oldProposal.setOpinionType(opinionType);
			oldProposal.setOpinionValue(opinionValue);
			break;

		default:
			// [P001] 부서별권한이 막혀져 있어서 권한체크 임시로 막아둠
			String AuthYn = oldProposal.getAuthYn();
			if( !StringUtils.equals( AuthYn, CbrConstants.YES)) {
				throw new IKEP4AuthorizedException();
			}
		
			break;
		}
		
		return oldProposal;
	}
	
	/**
	 * 권한
	 * @param proposal
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private ProposalPermission makePermission( Proposal proposal, User user) throws Exception {
		
		// 조회권한 확인
		//TO-DO [P001] 부서별권한이 막혀져 있어서 권한체크 임시로 막아둠
		if( !StringUtils.equals( proposal.getAuthYn(), CbrConstants.YES)) {
			
			throw new IKEP4AuthorizedException();
		}
		
		String userEmpNo = user.getEmpNo();
		
		ProposalSearchCondition searchCondition = new ProposalSearchCondition();
		searchCondition.setSessionGoupId( user.getGroupId());
		
		ProposalPermission permission =  proposalDao.getDeptOpinionPermission( searchCondition);
		
		// SCM등록자만 의견 가능
		if( permission.isScmAuth()) {
			
			if( !StringUtils.equals( proposal.getScmEmpNo(), userEmpNo)  ){
				permission.setScmAuth( false);
			}
		}
		
		// 마케팅팀 등록자만 의견 가능
		if( permission.isMarketAuth()) {
			
			if( !StringUtils.equals( proposal.getMarketEmpNo(), userEmpNo)  ){
				permission.setMarketAuth( false);
			}
			
		}
		
		// 1. 등록자일때 화면 권한
		// [통테-변경사항] 초안 TCS부서외 작성시 임시저장만 가능 .
		if( StringUtils.equals( userEmpNo, proposal.getReqEmpNo())) {
			
			permission.reqViewEnable();
			
			// 부서별 의견이 없을때만 삭제가능 
			if( StringUtils.isEmpty( proposal.getQadOpinion()) && StringUtils.isEmpty( proposal.getLabOpinion()) 
					&&  StringUtils.isEmpty( proposal.getSalesOpinion() ) &&  StringUtils.isEmpty( proposal.getScmOpinion() ) 
					&&  StringUtils.isEmpty( proposal.getMarketOpinion() ) &&  StringUtils.isEmpty( proposal.getTcsTotalOpinion() ) 
					) {
				permission.setDeleteBtnActive( true);
			}
			
			permission.setSaveType( CbrConstants.STAT_DRAFT_ONLY);
			// 임시저장버튼 활성화
			if( StringUtils.isEmpty( proposal.getSaveYn()) ||StringUtils.equals( proposal.getSaveYn(), CbrConstants.NO) ) {
				permission.setTempSaveBtnActive( true);
			}
		}
		
		// 2. TCS부서일경우 저장버튼, 승인코멘트, 제안서 상태 변경활성화
//		if( StringUtils.equals( proposal.getSaveYn(), CbrConstants.YES ) && permission.isTcsAuth()) {
//			
//			permission.setSaveType( CbrConstants.STAT_TCS_ONLY);
//			permission.setTcsViewActive( true);
//			permission.setSaveBtnActive( true);
//			if( StringUtils.equals( userEmpNo, proposal.getReqEmpNo())) {
//				
//				permission.setSaveType( CbrConstants.STAT_BOTH);
//			}
//		}
		// [통테-변경사항] TCS 부서에서 수정보안하여 저장( TCS부서 임직원만 저장 가능).
		// [통테-변경사항] TCS부서에서 저장 이후 화면 수정불가.
		if( permission.isTcsAuth()) {
			
			if( StringUtils.equals( proposal.getSaveYn(), CbrConstants.NO )) {
				permission.setSaveType( CbrConstants.STAT_BOTH);
				permission.reqViewEnable();
				permission.setTcsViewActive( true);
				permission.setSaveBtnActive( true);
				permission.setTempSaveBtnActive(true);
			}else{
				permission.setSaveType( CbrConstants.STAT_TCS_ONLY);
				permission.setTcsViewActive( false);
			}
		}
		
		
		proposal.setSaveType( permission.getSaveType());
		// ECM User확인
		boolean isEcmUser = collaboCommonService.isEcmUser( user);
		permission.setEcmRoll( isEcmUser);
		
		
		return permission;
	}
}
