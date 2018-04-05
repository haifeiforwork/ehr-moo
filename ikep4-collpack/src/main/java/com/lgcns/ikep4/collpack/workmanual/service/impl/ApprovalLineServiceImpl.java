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
import com.lgcns.ikep4.collpack.workmanual.dao.ManualDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualVersionDao;
import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalLineService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualVersionService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalLineServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service 
@Transactional
public class ApprovalLineServiceImpl extends GenericServiceImpl<ApprovalLine, ApprovalLinePk> implements ApprovalLineService {
	@Autowired
	private ApprovalLineDao approvalLineDao;
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private ManualVersionDao manualVersionDao;
	@Autowired
	private ManualDao manualDao;
	
	@Autowired
	private ManualVersionService manualVersionService;

	@Autowired
	private IdgenService idgenService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TagService tagService;
	@Autowired
	private FileService fileService;

	@Deprecated
	public ApprovalLinePk create(ApprovalLine approvalLine) {
		return approvalLineDao.create(approvalLine);
	}

	@Deprecated
	public boolean exists(ApprovalLinePk approvalLinePk) {
		return approvalLineDao.exists(approvalLinePk);
	}

	@Deprecated
	public ApprovalLine read(ApprovalLinePk approvalLinePk) {
		return approvalLineDao.get(approvalLinePk);
	}

	@Deprecated
	public void delete(ApprovalLinePk approvalLinePk) {
		approvalLineDao.remove(approvalLinePk);
	}

	@Deprecated
	public void update(ApprovalLine approvalLine) {
		approvalLineDao.update(approvalLine);
	}
	////////////////////////////////////
	
	//결재자 정보 - ManualId
	public List<ApprovalLine> listApprovalLineByManualId(String manualId, String portalId) {
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		return approvalLineDao.listApprovalLineByManualId(manualPk);
	}
	//결재자 정보 - VersionId
	public List<ApprovalLine> listApprovalLineByVersionId(String versionId, String portalId) {
		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(versionId);
		manualVersionPk.setPortalId(portalId);
		return approvalLineDao.listApprovalLineByVersionId(manualVersionPk);
	}
	//결재자 정보
	public List<ApprovalLine> listApprovalLine(String approvalId) {
		return approvalLineDao.listApprovalLine(approvalId);
	}
	//결재
	public void updateApprovalLine(ApprovalLine approvalLine, User user) {
		approvalLineDao.update(approvalLine);
		
		if(approvalLine.getApprovalResult().equals("C")) {//승인
			ApprovalLinePk approvalLinePk = new ApprovalLinePk();
			approvalLinePk.setApprovalId(approvalLine.getApprovalId());
			approvalLinePk.setApprovalLine(approvalLine.getApprovalLine() + 1);
			if(!approvalLineDao.exists(approvalLinePk)) {//결재 마지막이면
				//결재정보 변경
				Approval approval = approvalDao.get(approvalLine.getApprovalId());
				approval.setApprovalStatus("B");
				approvalDao.update(approval);
				
				//버젼정보 변경
				ManualVersion manualVersion = manualVersionService.readManualVersion(approval.getVersionId(), user.getPortalId());
				manualVersion.setApprovalStatus("C");
				manualVersionDao.updateManualVersion(manualVersion);
				
				String manualType = approval.getManualType();
				String versionId = idgenService.getNextId();
				if(manualType.equals("A")) { //제정
					//버젼 정보 생성
					manualVersion.setVersionId(versionId);
					manualVersion.setVersion((float) 1.0);
					manualVersion.setIsPublic(1);
					manualVersion.setUpdateReason(messageSource.getMessage("ui.collpack.workmanual.message.manual.submit", null, new Locale(user.getLocaleCode())));
					manualVersion.setApprovalStatus("A");
					manualVersion.setRegisterId(approval.getRegisterId());
					manualVersion.setRegisterName(approval.getRegisterName());
					manualVersionDao.create(manualVersion);
				} else if(manualType.equals("B")) { //개정
					manualVersion.setVersionId(versionId);
					float preVersion = manualVersion.getVersion();
					int postVersion = (int) preVersion;
					manualVersion.setVersion((float) postVersion + 1);
					manualVersion.setIsPublic(1);
					manualVersion.setUpdateReason(messageSource.getMessage("ui.collpack.workmanual.message.manual.submit.revision", null, new Locale(user.getLocaleCode())));
					manualVersion.setApprovalStatus("A");
					manualVersion.setRegisterId(approval.getRegisterId());
					manualVersion.setRegisterName(approval.getRegisterName());
					manualVersionDao.create(manualVersion);
				}
				
				//매뉴얼정보 변경
				ManualPk manualPk = new ManualPk();
				manualPk.setManualId(manualVersion.getManualId());
				manualPk.setPortalId(user.getPortalId());
				Manual manual = manualDao.getManual(manualPk);
				manual.setManualType(manualType);
				manual.setVersion(manualVersion.getVersion());
				manual.setTitle(manualVersion.getVersionTitle());
				manual.setContents(manualVersion.getVersionContents());
				manual.setAttachCount(manualVersion.getVersionAttachCount());
				manual.setUpdaterId(approval.getRegisterId());
				manual.setUpdaterName(approval.getRegisterName());
				manualDao.updateManual(manual);

				//파일 복사
				if(manualVersion.getFileDataList() != null) {
					List<String> fileIdList = new ArrayList<String>();
					for(int i=0; i<manualVersion.getFileDataList().size(); i++) {
						fileIdList.add(manualVersion.getFileDataList().get(i).getFileId());
					}
					fileService.copyForTransfer(fileIdList, versionId, IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, user);
				}
				
				//태깅 처리
				if(!StringUtil.isEmpty(manualVersion.getTag())) {
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
			} else {//다음 결재자 준비하기
				approvalLineDao.updateNextApproval(approvalLinePk);
			}
		} else if(approvalLine.getApprovalResult().equals("D")) {//반려
			//결재정보 변경
			Approval approval = approvalDao.get(approvalLine.getApprovalId());
			approval.setApprovalStatus("C");
			approvalDao.update(approval);
			
			//버젼정보 변경
			ManualVersionPk manualVersionPk = new ManualVersionPk();
			manualVersionPk.setVersionId(approval.getVersionId());
			manualVersionPk.setPortalId(user.getPortalId());
			ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
			manualVersion.setApprovalStatus("D");
			manualVersionDao.updateManualVersion(manualVersion);
		}
	}
	//결재자 추가
	public void createApprovalUser(ApprovalLine approvalLine) {
		approvalLineDao.updateApprovalLineUp(approvalLine);
		approvalLine.setApprovalLine(approvalLine.getApprovalLine() + 1);
		approvalLine.setApprovalResult("A");
		approvalLineDao.create(approvalLine);
	}
	//결재자 제거
	public void deleteApprovalUser(ApprovalLinePk approvalLinePk) {
		approvalLineDao.remove(approvalLinePk);
		approvalLineDao.updateApprovalLineDown(approvalLinePk);
	}
}
