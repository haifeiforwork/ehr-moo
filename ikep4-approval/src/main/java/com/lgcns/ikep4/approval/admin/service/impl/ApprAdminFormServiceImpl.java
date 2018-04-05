/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprAdminFormDao;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * WorkPlace List Impl 
 * 
 * @author wonchu
 * @version $Id: ApprAdminFormServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("apprAdminFormServiceImpl")
@SuppressWarnings("unchecked")
public class ApprAdminFormServiceImpl extends GenericServiceImpl<ApprForm, String> implements ApprAdminFormService{
	
	/** The file service. */
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ApprAdminFormDao apprAdminFormDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * apprForm 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public SearchResult<ApprForm> listBySearchCondition(ApprFormSearchCondition searchCondition) {
		
		Integer count = apprAdminFormDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);
		
		SearchResult<ApprForm> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprForm>(searchCondition);
		} else {
			List<ApprForm> formList = apprAdminFormDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<ApprForm>(formList, searchCondition);
		}

		return searchResult;
		
	}
	
	/**
	 * apprForm 생성
	 * @param 	ApprForm
	 * @return 	String
	 */
	public String createApprForm(ApprForm apprForm) {
		apprForm.setFormId(idgenService.getNextId());
		
		// 빈결제선시 결제선 수정여부 초기화
		if(apprForm.getDefLineUse()==0 && apprForm.getIsDefLineUpdate()!=0) apprForm.setIsDefLineUpdate(0);
		
		// 수신자가 있을시
		if(!"".equals(apprForm.getReferenceId())){
			String [] referenceSet = StringUtils.split(apprForm.getReferenceId(), ";");

			for(int i=0; i<referenceSet.length; i++){
				Map<String, String> map = new HashMap<String, String>();
				
				String [] cols = StringUtils.split(referenceSet[i], ",");
				
				map.put("formId", 		apprForm.getFormId());
				map.put("userId", 		cols[0]);
				map.put("groupId", 		cols[1]);
				
				apprAdminFormDao.createApprReference(map);
			}
		}
		
		return apprAdminFormDao.create(apprForm);
	}
	
	/**
	 * apprForm 결재정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void updateApprInfoForm(ApprForm apprForm) {
		
		// 빈결제선시 결제선 수정여부 초기화
		if(apprForm.getDefLineUse()==0 && apprForm.getIsDefLineUpdate()!=0) apprForm.setIsDefLineUpdate(0);
		
		// 수신참조 삭제
		apprAdminFormDao.deleteApprReference(apprForm.getFormId());
		
		// 수신자가 있을시
		if(!"".equals(apprForm.getReferenceId())){
			String [] referenceSet = StringUtils.split(apprForm.getReferenceId(), ";");
			for(int i=0; i<referenceSet.length; i++){
				Map<String, String> map = new HashMap<String, String>();
				
				String [] cols = StringUtils.split(referenceSet[i], ",");
				
				map.put("formId", 		apprForm.getFormId());
				map.put("userId", 		cols[0]);
				map.put("groupId", 		cols[1]);
				
				apprAdminFormDao.createApprReference(map);
			}
		}
		
		apprAdminFormDao.update(apprForm);
	}
	
	/**
	 * apprForm 문서정보 수정
	 * @param 	ApprForm
	 * @return 	void
	 */
	public void updateApprContentForm(ApprForm apprForm, User user) {
		
		//첨부파일 업데이트
		if(apprForm.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(apprForm.getFileLinkList(), apprForm.getFormId(),  CommonCode.ITEM_TYPE, user);
		}
		
		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(apprForm.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(apprForm.getEditorFileLinkList(), apprForm.getFormId(), CommonCode.ITEM_TYPE, user);
		}
		
		if(apprForm.getIsNewVersion()==1){
			apprAdminFormDao.createApprContent(apprForm);
		}else{
			apprAdminFormDao.updateApprContent(apprForm);
		}
	}
	
	/**
	 * apprForm 폼상세
	 * @param 	formId
	 * @return 	ApprForm
	 */
	public ApprForm readApprForm(String formId) {
		ApprForm apprForm = apprAdminFormDao.get(formId);

		//첨부 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> fileDataList = this.fileService.getItemFile(formId, "N");
		apprForm.setFileDataList(fileDataList);
		return apprForm;
	}
	
	/**
	 * apprForm 시스템
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprSystem(Map<String, String> map) {
		
		return apprAdminFormDao.getApprSystem(map);
	}
	
	/**
	 * apprForm 수신참조자 조회
	 * @param 	formId, locale
	 * @return 	List<User>
	 */
	public List<User> getApprReferenceList(String formId, String locale) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("formId", 		formId);
		
		List <ApprForm> referenceList = apprAdminFormDao.getApprReferenceList(map);
		List userList = new ArrayList();
		
		Map map2 = new HashMap<String, String>();
		for(int i=0;i<referenceList.size();i++){
			User user = new User();
			if(groupDao.exists(referenceList.get(i).getGroupId())){
				map2.put("userId", 		referenceList.get(i).getUserId());
				map2.put("groupId", 	referenceList.get(i).getGroupId());
				
				user = userDao.getUserByGroupId(map2);
			}else{
				user = userDao.get(referenceList.get(i).getUserId());
			}
						
			if (!(locale).equals("ko")) {
				user.setUserName(user.getUserEnglishName());
				user.setTeamName(user.getTeamEnglishName());
				user.setJobTitleName(user.getJobTitleEnglishName());
			}
			userList.add(user);
		}
		
		return userList;
		
	}
	
	/**
	 * apprForm 열람권한 조회
	 * @param 	formId
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFormHistoryList(String formId) {

		return apprAdminFormDao.getApprFormHistoryList(formId);
		
	}
	
	/**
	 * apprForm 그룹 조회
	 * @param 	code, localeCode
	 * @return 	Group
	 */
	public Group getGroup(String code, String locale) {

		Group group = groupDao.get(code);
		if(group==null){
			group = new Group();
			group.setGroupName("isEmpty");
		}else{
			if (!(locale).equals("ko")) {
				group.setGroupName(group.getGroupEnglishName());
				group.setGroupTypeName(group.getGroupTypeEnglishName());
			}
		}
		
		return group;
	}
	
	
	/**
	 * apprForm 사용자 조회
	 * @param 	code, localeCode
	 * @return 	User
	 */
	public User getUser(String id, String locale) {

		User user = userDao.get(id);
		if(user==null){
			user = new User();
			user.setUserName("isEmpty");
		}else{
			if (!(locale).equals("ko")) {
				user.setUserName(user.getUserEnglishName());
				user.setTeamName(user.getTeamEnglishName());
				user.setJobTitleName(user.getJobTitleEnglishName());
			}
		}
		
		return user;
	}
	
	/**
	 * apprForm 즐겨찾기 
	 * @param 	Map
	 * @return 	void
	 */
	public void setApprFavorite(ApprForm apprForm){
		if("add".equals(apprForm.getMode())){
			apprForm.setFavoriteId(idgenService.getNextId());
			apprAdminFormDao.createApprFavorite(apprForm);
		}else{
			apprAdminFormDao.deleteApprFavorite(apprForm);
		}
	}
	
	/**
	 * apprForm 즐겨찾기 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprFavoriteList(Map<String, Object> map) {
		
		return apprAdminFormDao.getApprFavoriteList(map);
	}
	
	/**
	 * apprForm 최근 기안 문서 목록
	 * @param 	Map
	 * @return 	List<ApprForm>
	 */
	public List<ApprForm> getApprRecentList(Map<String, Object> map) {
		
		return apprAdminFormDao.getApprRecentList(map);
	}
}
