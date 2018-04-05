/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalLineDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalUserDao;
import com.lgcns.ikep4.collpack.workmanual.dao.LineReplyDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualVersionDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ReferenceDao;
import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ManualVersionService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualVersionServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service 
@Transactional
public class ManualVersionServiceImpl extends GenericServiceImpl<ManualVersion, String> implements ManualVersionService {
	@Autowired
	private ManualVersionDao manualVersionDao;
	@Autowired
	private ManualDao manualDao;
	@Autowired
	private LineReplyDao lineReplyDao;
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private ApprovalUserDao approvalUserDao;
	@Autowired
	private ApprovalLineDao approvalLineDao;
	@Autowired
	private ReferenceDao referenceDao;
	
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TagService tagService;
	@Autowired
	private FileService fileService;
	@Autowired
	private ActivityStreamService activityStreamService;

	@Deprecated
	public String create(ManualVersion manualVersion) {
		return manualVersionDao.create(manualVersion);
	}

	@Deprecated
	public boolean exists(String versionId) {
		return manualVersionDao.exists(versionId);
	}

	@Deprecated
	public ManualVersion read(String versionId) {
		return manualVersionDao.get(versionId);
	}

	@Deprecated
	public void delete(String versionId) {
		manualVersionDao.remove(versionId);
	}

	@Deprecated
	public void update(ManualVersion manualVersion) {
		manualVersionDao.update(manualVersion);
	}
	////////////////////////////////////

	//버젼 올리기
	private float versionUp(float version) {
		int pre = (int)version;
		float post = version - pre;
		if(post < (float)0.9) {
			version += (float)0.1;
		} else if(post < (float)0.99) {
			version += (float)0.01;
		} else {
			version += (float)0.001;
		}
		
		return version;
	}
	
	//개인 업무 매뉴얼 버젼 조회
	public SearchResult<ManualVersion> listMyManualVersion(ManualSearchCondition manualSearchCondition) {
		Integer count = manualVersionDao.countMyManualVersion(manualSearchCondition);
		manualSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<ManualVersion> searchResult = null; 
		if(manualSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ManualVersion>(manualSearchCondition);
		} else {
			List<ManualVersion> manualVersionList = manualVersionDao.listMyManualVersion(manualSearchCondition); 
			searchResult = new SearchResult<ManualVersion>(manualVersionList, manualSearchCondition);  			
		}  
		
		return searchResult;
	}
	//업무 매뉴얼 버젼  조회
	public SearchResult<ManualVersion> listManualVersion(ManualSearchCondition manualSearchCondition) {
		Integer count = manualVersionDao.countManualVersion(manualSearchCondition);
		manualSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<ManualVersion> searchResult = null; 
		if(manualSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ManualVersion>(manualSearchCondition);
		} else {
			List<ManualVersion> manualVersionList = manualVersionDao.listManualVersion(manualSearchCondition); 
			searchResult = new SearchResult<ManualVersion>(manualVersionList, manualSearchCondition);  			
		}  
		
		return searchResult;
	}
	//버젼 되돌리기
	public void createRedoManualVersion(ManualSearchCondition manualSearchCondition, User user) {
		ManualVersion manualVersion = readManualVersion(manualSearchCondition.getVersionId(), user.getPortalId());
		manualVersion.setUpdateReason(messageSource.getMessage("ui.collpack.workmanual.message.history.redo", null, new Locale(user.getLocaleCode())));
		manualVersion.setRegisterId(user.getUserId());
		manualVersion.setRegisterName(user.getUserName());
		
		String versionId = idgenService.getNextId();
		manualVersion.setVersionId(versionId);
		
		//최상위 버젼 가져오기
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualVersion.getManualId());
		manualPk.setPortalId(manualVersion.getPortalId());
		ManualVersion topManualVersion = manualVersionDao.getTopManualVersion(manualPk);
		float newTopVersion = versionUp(topManualVersion.getVersion());
		manualVersion.setVersion(newTopVersion);
		manualVersionDao.create(manualVersion);
		
		//파일 복사
		if(manualVersion.getFileDataList() != null) {
			List<String> fileIdList = new ArrayList<String>();
			for(int i=0; i<manualVersion.getFileDataList().size(); i++) {
				fileIdList.add(manualVersion.getFileDataList().get(i).getFileId());
			}
			fileService.copyForTransfer(fileIdList, versionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
		}

		//activityStream
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_POST, manualVersion.getVersionId(), manualVersion.getVersionTitle());
		
		if(manualVersion.getVersion() < 1 || manualVersion.getIsPublic() == 1) {//매뉴얼 정보 업데이트	
			Manual manual = manualDao.getManual(manualPk);
			manual.setVersion(newTopVersion);
			manual.setTitle(manualVersion.getVersionTitle());
			manual.setContents(manualVersion.getVersionContents());
			manual.setAttachCount(manualVersion.getVersionAttachCount());
			manual.setUpdaterId(user.getUserId());
			manual.setUpdaterName(user.getUserName());
			manualDao.update(manual);

			if(manualVersion.getIsPublic() == 1 && !StringUtil.isEmpty(manualVersion.getTag())) {
			    Tag tag = new Tag();                     
			    tag.setTagName(manualVersion.getTag());                    		//사용자가 작성한 tag
			    tag.setTagItemId(manual.getManualId());               		//게시물 ID
			    tag.setTagItemType(TagConstants.ITEM_TYPE_WORK_MANUAL);  		//모듈 타입 TagConstants에 정의되어 있음. 맡은 모듈에 맞게 골라 사용.
			    //tag.setTagItemSubType(tagSubType); 
			    tag.setTagItemName(manual.getTitle());              		//게시물 제목
			    tag.setTagItemContents(manual.getContents());  			//게시물 내용
			    tag.setTagItemUrl("/collpack/workmanual/readManualPopupView.do?manualId="+manual.getManualId());  //게시물 상세화면 URL - 도메인과 contextPash는 빼주시기 바랍니다.
			    tag.setRegisterId(manual.getRegisterId());	          	//등록자 ID
			    tag.setRegisterName(manual.getRegisterName());     		//등록자 이름
			    tag.setPortalId(manual.getPortalId());            		 	//portalID
			    tagService.create(tag);				
			}
		}
	}
	//업무 매뉴얼 버젼 조회
	public ManualVersion readManualVersion(String versionId, String portalId) {
		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(versionId);
		manualVersionPk.setPortalId(portalId);
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		List<FileData> fileDataList = fileService.getItemFile(versionId, "N");
		manualVersion.setFileDataList(fileDataList);
		
		return manualVersion;
	}
	//상신중인 매뉴얼 버젼 갯수
	public Integer countSubmittingManualVersion(String manualId, String portalId) {
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		return manualVersionDao.countSubmittingManualVersion(manualPk);
	}
	//업무 매뉴얼 버젼 삭제
	public void deleteManualVersion(String versionId, String portalId, String userId, String userName) {
		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(versionId);
		manualVersionPk.setPortalId(portalId);
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		float versionInManualVersion = manualVersion.getVersion();
		manualVersionDao.removeManualVersion(manualVersionPk);

		//파일 삭제
		fileService.removeItemFile(versionId);
		
		//activityStream
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_DELETE, manualVersion.getVersionId(), manualVersion.getVersionTitle());
		
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualVersion.getManualId());
		manualPk.setPortalId(portalId);
		Manual manual = manualDao.getManual(manualPk);
		
		if(versionInManualVersion == manual.getVersion()) {
			ManualVersion topManualVersion = manualVersionDao.getTopManualVersion(manualPk);
			if(topManualVersion == null) {
				referenceDao.removeByManualId(manual.getManualId());
				lineReplyDao.removeByManualId(manual.getManualId());	
				manualDao.removeManual(manualPk);
			} else {
				manual.setVersion(topManualVersion.getVersion());
				manual.setTitle(topManualVersion.getVersionTitle());
				manual.setContents(topManualVersion.getVersionContents());
				manual.setAttachCount(topManualVersion.getVersionAttachCount());
				manual.setUpdaterId(userId);
				manual.setUpdaterName(userName);
				manualDao.update(manual);
			}
		}
	}
	//업무 매뉴얼 버젼 수정
	public void updateManualVersion(ManualVersion manualVersion, User user) {
		String versionId = idgenService.getNextId();

		//파일 처리 : 복사 후 변경 사항 반영
		ManualVersion oldManualVersion = readManualVersion(manualVersion.getVersionId(), user.getPortalId());
		if(oldManualVersion.getFileDataList() != null) {
			List<String> fileIdList = new ArrayList<String>();
			for(int i=0; i<oldManualVersion.getFileDataList().size(); i++) {
				fileIdList.add(oldManualVersion.getFileDataList().get(i).getFileId());
			}
			fileService.copyForTransfer(fileIdList, versionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
		}
		if(manualVersion.getFileLinkList() != null) {
			fileService.saveFileLink(manualVersion.getFileLinkList(), versionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
			manualVersion.setVersionAttachCount(manualVersion.getFileLinkList().size());
		} else {
			manualVersion.setVersionAttachCount(0);
		}

		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualVersion.getManualId());
		manualPk.setPortalId(manualVersion.getPortalId());
		ManualVersion topManualVersion = manualVersionDao.getTopManualVersion(manualPk);
		manualVersion.setVersion(versionUp(topManualVersion.getVersion()));
		manualVersion.setVersionId(versionId);
		manualVersion.setRegisterId(user.getUserId());
		manualVersion.setRegisterName(user.getUserName());
		manualVersionDao.create(manualVersion);
		
		//activityStream
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_POST, manualVersion.getVersionId(), manualVersion.getVersionTitle());
		
		if(manualVersion.getIsPublic() == 1 || manualVersion.getVersion() < 1) {
			Manual manual = manualDao.getManual(manualPk);
			manual.setVersion(manualVersion.getVersion());
			manual.setTitle(manualVersion.getVersionTitle());
			manual.setContents(manualVersion.getVersionContents());
			manual.setAttachCount(manualVersion.getVersionAttachCount());
			manual.setUpdaterId(manualVersion.getRegisterId());
			manual.setUpdaterName(manualVersion.getRegisterName());
			manualDao.update(manual);
			
			if(manualVersion.getIsPublic() == 1 && !StringUtil.isEmpty(manualVersion.getTag())) {
			    Tag tag = new Tag();                     
			    tag.setTagName(manualVersion.getTag());                    		//사용자가 작성한 tag
			    tag.setTagItemId(manual.getManualId());               		//게시물 ID
			    tag.setTagItemType(TagConstants.ITEM_TYPE_WORK_MANUAL);  		//모듈 타입 TagConstants에 정의되어 있음. 맡은 모듈에 맞게 골라 사용.
			    //tag.setTagItemSubType(tagSubType); 
			    tag.setTagItemName(manual.getTitle());              		//게시물 제목
			    tag.setTagItemContents(manual.getContents());  			//게시물 내용
			    tag.setTagItemUrl("/collpack/workmanual/readManualPopupView.do?manualId="+manual.getManualId());  //게시물 상세화면 URL - 도메인과 contextPash는 빼주시기 바랍니다.
			    tag.setRegisterId(manual.getRegisterId());	          	//등록자 ID
			    tag.setRegisterName(manual.getRegisterName());     		//등록자 이름
			    tag.setPortalId(manual.getPortalId());            		 	//portalID
			    tagService.create(tag);				
			}
		}
	}
	//상신
	public String createApproval(Approval approval, User user) {
		ManualVersion manualVersion = readManualVersion(approval.getVersionId(), user.getPortalId());
		
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualVersion.getManualId());
		manualPk.setPortalId(user.getPortalId());
		Manual manual = manualDao.getManual(manualPk);
		
		String approvalId = "";
		if(approval.getManualType().equals("C")) {//폐기
			//버젼 정보 생성
			ManualVersion topManualVersion = manualVersionDao.getTopManualVersion(manualPk);
			manualVersion.setVersion(versionUp(topManualVersion.getVersion()));
			String versionId = idgenService.getNextId();
			manualVersion.setVersionId(versionId);
			manualVersion.setIsPublic(0); //비공개
			manualVersion.setUpdateReason(approval.getRequestContents());
			manualVersion.setApprovalStatus("B");
			manualVersion.setRegisterId(approval.getRegisterId());
			manualVersion.setRegisterName(approval.getRegisterName());
			manualVersion.setIsAbolition(1);
			manualVersionDao.create(manualVersion);

			//파일 복사
			if(manualVersion.getFileDataList() != null) {
				List<String> fileIdList = new ArrayList<String>();
				for(int i=0; i<manualVersion.getFileDataList().size(); i++) {
					fileIdList.add(manualVersion.getFileDataList().get(i).getFileId());
				}
				fileService.copyForTransfer(fileIdList, versionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
			}
			
			//activityStream
			activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_POST, manualVersion.getVersionId(), manualVersion.getVersionTitle());
			
			//결재정보 생성
			approvalId = idgenService.getNextId();
			approval.setApprovalId(approvalId);
			approval.setVersionId(versionId);
			approvalDao.create(approval);
			
			//결재자 정보 생성
			List<ApprovalUser> approvalUserList = approvalUserDao.listApprovalUser(manual.getCategoryId());
			for(int i=0; i<approvalUserList.size(); i++) {
				ApprovalLine approvalLine = new ApprovalLine();
				approvalLine.setApprovalId(approvalId);
				approvalLine.setApprovalLine(approvalUserList.get(i).getApprovalLine());
				approvalLine.setApprovalUserId(approvalUserList.get(i).getApprovalUserId());
				if(i == 0) {
					approvalLine.setApprovalResult("B");
				} else {
					approvalLine.setApprovalResult("A");
				}
				approvalLineDao.create(approvalLine);
			}
		} else {
			//버젼 정보 수정
			manualVersion.setApprovalStatus("B");
			manualVersionDao.updateManualVersion(manualVersion);
			
			//결재정보 생성
			approvalId = idgenService.getNextId();
			approval.setApprovalId(approvalId);
			approvalDao.create(approval);

			//결재자 정보 생성
			List<ApprovalUser> approvalUserList = approvalUserDao.listApprovalUser(manual.getCategoryId());
			for(int i=0; i<approvalUserList.size(); i++) {
				ApprovalLine approvalLine = new ApprovalLine();
				approvalLine.setApprovalId(approvalId);
				approvalLine.setApprovalLine(approvalUserList.get(i).getApprovalLine());
				approvalLine.setApprovalUserId(approvalUserList.get(i).getApprovalUserId());
				if(i == 0) {
					approvalLine.setApprovalResult("B");
				} else {
					approvalLine.setApprovalResult("A");
				}
				approvalLineDao.create(approvalLine);
			}
		}
		
		return approvalId;
	}
	//상신 취소
	public void cancelApproval(String versionId, User user, String manualType) {
		Approval approval = approvalDao.getSubmittingApproval(versionId);
		
		//결재자 정보 삭제
		approvalLineDao.removeAll(approval.getApprovalId());
		
		//결재 정보 삭제
		approvalDao.remove(approval.getApprovalId());
		
		//매뉴얼 버젼 정보 처리
		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(versionId);
		manualVersionPk.setPortalId(user.getPortalId());
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		if(manualType.equals("C")) { //폐기-버젼 정보 삭제	
			manualVersionDao.removeManualVersion(manualVersionPk);	

			//파일 삭제
			fileService.removeItemFile(versionId);
			
			//activityStream
			activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_DELETE, manualVersion.getVersionId(), manualVersion.getVersionTitle());
		} else {//버젼 정보 수정;
			manualVersion.setApprovalStatus("A");
			manualVersionDao.updateManualVersion(manualVersion);
		}
	}
	//매뉴얼 테이블에 존재하는 매뉴얼 버젼 정보 조회
	public ManualVersion getManualVersionBymanualId(String manualId, String portalId) {
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		ManualVersion manualVersion = manualVersionDao.getManualVersionBymanualId(manualPk);
		
		List<FileData> fileDataList = fileService.getItemFile(manualVersion.getVersionId(), "N");
		manualVersion.setFileDataList(fileDataList);
		
		return manualVersion;
	}
	//매뉴얼 버젼 신규 등록
	public void createManualVersion(ManualVersion manualVersion, String categoryId, String tags, User user) {
		String manualVersionId = idgenService.getNextId();
		if(manualVersion.getFileLinkList() != null) {
			fileService.saveFileLink(manualVersion.getFileLinkList(), manualVersionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
			manualVersion.setVersionAttachCount(manualVersion.getFileLinkList().size());
		} else {
			manualVersion.setVersionAttachCount(0);
		}
		
		String manualId = idgenService.getNextId();
		Manual manual = new Manual();
		manual.setManualId(manualId);
		manual.setCategoryId(categoryId);
		manual.setManualType("A");
		manual.setVersion((float)0.1);
		manual.setTitle(manualVersion.getVersionTitle());
		manual.setContents(manualVersion.getVersionContents());
		manual.setAttachCount(manualVersion.getVersionAttachCount());
		manual.setHitCount(0);
		manual.setLinereplyCount(0);
		manual.setPortalId(manualVersion.getPortalId());
		manual.setRegisterId(manualVersion.getRegisterId());
		manual.setRegisterName(manualVersion.getRegisterName());
		manual.setUpdaterId(manualVersion.getRegisterId());
		manual.setUpdaterName(manualVersion.getRegisterName());
		manualDao.create(manual);

		manualVersion.setVersionId(manualVersionId);   
		manualVersion.setManualId(manualId);         
		manualVersion.setVersion((float)0.1);
		manualVersion.setIsPublic(0); // 0:비공개, 1:공개        
		manualVersion.setUpdateReason(messageSource.getMessage("ui.collpack.workmanual.message.write.first", null, new Locale(user.getLocaleCode())));
		manualVersion.setApprovalStatus("A"); //A:저장, B:상신, C:승인, D:반려  
		manualVersion.setIsAbolition(0);
		manualVersion.setTag(tags);
		manualVersionDao.create(manualVersion);

		//activityStream
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_DOC_POST, manualVersion.getVersionId(), manualVersion.getVersionTitle());
	}
}
