/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormTplDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;
import com.lgcns.ikep4.wfapproval.admin.service.ApFormService;


/**
 * 결재 양식관리 Service 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apFormService")
public class ApFormServiceImpl extends GenericServiceImpl<ApForm, String> implements ApFormService {

	@Autowired
	private ApFormDao apFormDao;

	@Autowired
	private ApFormTplDao apFormTplDao;

	@Autowired
	private IdgenService idgenService;

	/** The file service. */
	@Autowired
	private FileService fileService;

	/*
	 * 양식 마스터 목록 조회 (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApFormService#getApFormList()
	 */
	@Transactional(readOnly = true)
	public SearchResult<ApForm> listApFormAll(ApFormSearchCondition apFormSearchCondition) {

		SearchResult<ApForm> searchResult = null;
		
		//=================================================
		//	기본값 설정.
		//=================================================
		if(StringUtil.isEmpty(apFormSearchCondition.getSortColumn())){
			apFormSearchCondition.setSortColumn("REGIST_DATE");
			apFormSearchCondition.setSortType("DESC");
		}

		Integer count = this.apFormDao.countApFormAll(apFormSearchCondition);

		apFormSearchCondition.terminateSearchCondition(count);

		// 목록이 없다면...
		if (apFormSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApForm>(apFormSearchCondition);
		} else {
			List<ApForm> apFormList = this.apFormDao.listApFormAll(apFormSearchCondition);

			searchResult = new SearchResult<ApForm>(apFormList, apFormSearchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	@Transactional(readOnly = true)
	public ApForm readApForm(String formId) {

		ApForm apForm = this.apFormDao.get(formId);

		if (apForm != null) {
			// get Form Template.
			if (this.apFormTplDao.exists(formId)) {
				apForm.setApFormTpl(this.apFormTplDao.get(formId));
				
				//------------------------------------------------
				//	CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
				//------------------------------------------------
				List<FileData> editorFileDataList = this.fileService.getItemFile(formId, ApFormTpl.EDITOR_FILE);
				apForm.getApFormTpl().setEditorFileDataList(editorFileDataList);
				
			} else {
				ApFormTpl apFormTpl = new ApFormTpl();

				apFormTpl.setFormId(apForm.getFormId());

				apForm.setApFormTpl(apFormTpl);
			}
		}

		return apForm;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.service.ApFormService#existsApForm(java
	 * .lang.String)
	 */
	@Transactional(readOnly = true)
	public boolean existsApForm(String formId) {
		return this.apFormDao.exists(formId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.service.ApFormService#existsApFormTpl(
	 * java.lang.String)
	 */
	@Transactional(readOnly = true)
	public boolean existsApFormTpl(String formId) {
		return this.apFormTplDao.exists(formId);
	}

	/*
	 * 양식 마스터 등록
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.service.ApFormService#createApForm(com
	 * .lgcns.ikep4.wfapproval.admin.model.ApForm)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String createApForm(ApForm apForm) {

		String sFormId = this.idgenService.getNextId();

		// 양식ID
		apForm.setFormId(sFormId);

		this.apFormDao.create(apForm);

		return sFormId;
	}

	/*
	 * 양식 템플릿 등록
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#createApFormTpl
	 * (java.lang .Object)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String createApFormTpl(ApFormTpl apFormTpl, User user) {

		//------------------------------------------------
		//	CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		//------------------------------------------------
		if (apFormTpl.getEditorFileLinkList() != null) {
			this.fileService.saveFileLink(apFormTpl.getEditorFileLinkList(), apFormTpl.getFormId(), ApFormTpl.ITEM_TYPE, user);
		}

		this.apFormTplDao.create(apFormTpl);

		return apFormTpl.getFormId();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateApForm(ApForm apform) {
		this.apFormDao.update(apform);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.wfapproval.admin.service.ApFormService#updateApFormTpl(
	 * com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateApFormTpl(ApFormTpl apFormTpl, User user) {
		
		//이미지 파일  업데이트
		if(apFormTpl.getEditorFileLinkList() != null) { 
			List<FileData> savedEditorFileList = this.fileService.getItemFile(apFormTpl.getFormId(), ApFormTpl.EDITOR_FILE); 
		
			List<FileLink> modifiedFileLinkList = new ArrayList<FileLink>(); 
			
			FileLink temp = null;
			
			Boolean added = true;
			
			//수정화면에서 온 이미지 파일 VS 저장되어 있는 이미지 파일 비교하여 수정화면에서만 존재하는 이미지 파일은 add됬다고 Flag를 넣어 준다.
			for(FileLink modified : apFormTpl.getEditorFileLinkList()) { 
				added = true;
				
				for(FileData saved : savedEditorFileList) {  
					if(modified.getFileId().equals(saved.getFileId())) {
						added = false; 
						break;
					} 
				}
				if(added) {
					modified.setFlag("add");
					modifiedFileLinkList.add(modified);   
				}
			}
			
			Boolean deleted = true;
			
			//저장되어 있는 이미지 파일 VS 수정화면에서 온 이미지 파일  비교하여 저장되어 있는 이미지만 존재하는 이미지 파일은 add됬다고 Flag를 넣어 준다.
			for(FileData saved : savedEditorFileList) {  
				deleted = true;
				
				for(FileLink modified : apFormTpl.getEditorFileLinkList()) { 
					if(modified.getFileId().equals(saved.getFileId())) {
						deleted = false; 
						break;
					} 
				}
				if(deleted) {
					temp = new FileLink();
					
					temp.setFileId(saved.getFileId()); 
					temp.setFlag("del");
					modifiedFileLinkList.add(temp);   
				}
			}
			
			this.fileService.saveFileLink(modifiedFileLinkList, apFormTpl.getFormId(), ApFormTpl.ITEM_TYPE, user);
		}
		
		this.apFormTplDao.update(apFormTpl);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteApForm(String formId) {
		
		//전체 파일 삭제
		this.fileService.removeItemFile(formId);
		
		// Template 삭제
		this.apFormTplDao.remove(formId);

		// Master 삭제
		this.apFormDao.remove(formId);
	}
}
