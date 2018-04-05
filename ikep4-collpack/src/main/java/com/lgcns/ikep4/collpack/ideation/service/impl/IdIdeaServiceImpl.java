/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyRecommendDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdPolicyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdRecommendDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdReferenceDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.tagfree.util.MimeUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdIdeaServiceImpl.java 13086 2011-05-25 06:36:17Z loverfairy $
 */
@Service("idIdeaService")
public class IdIdeaServiceImpl extends GenericServiceImpl<IdIdea, String> implements IdIdeaService {


	@Autowired
	private IdIdeaDao idIdeaDao;
	
	@Autowired
	private IdLinereplyDao idLinereplyDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private IdUserActivitiesDao idUserActivitiesDao;
	
	@Autowired
	private IdReferenceDao idReferenceDao;
	
	@Autowired
	private IdLinereplyRecommendDao idLinereplyRecommendDao;
	
	@Autowired
	private IdRecommendDao idRecommendDao;
	
	@Autowired
	private IdPolicyDao idPolicyDao;
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private FileService fileService; 
	
	@Override
	public String create(IdIdea idIdea) {
		
		String id = idgenService.getNextId();
		
		idIdea.setItemId(id);
		
		idIdea.setStep(0);
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(idIdea.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(idIdea.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						idIdea.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					idIdea.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		
		idIdeaDao.create(idIdea);
		
		User user = new User();
		user.setUserId(idIdea.getRegisterId());
		user.setUserName(idIdea.getRegisterName());
		
		//첨부파일 업데이트
		if(idIdea.getFileLinkList() != null) {
			idIdea.setAttachFileCount(idIdea.getFileLinkList().size());
			
			fileService.saveFileLink(idIdea.getFileLinkList(), idIdea.getItemId(), IKepConstant.ITEM_TYPE_CODE_IDEATION, user);
		} 
		
		
		//파일업로드 - edit
		if(idIdea.getEditorFileLinkList() != null) {

			fileService.saveFileLinkForEditor(idIdea.getEditorFileLinkList(), idIdea.getItemId(), IKepConstant.ITEM_TYPE_CODE_IDEATION, user);
	
		}
		
		//태그 등록
		createTag(idIdea);
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_IDEATION, IKepConstant.ACTIVITY_CODE_DOC_POST, idIdea.getItemId(), idIdea.getTitle());

		
		return id;
	}
	

	public String createReplyItem(IdIdea idIdea) {
		
		String id = idgenService.getNextId();
		
		idIdea.setItemId(id);		
		
		idIdeaDao.create(idIdea);		
		
		return id;
	}
	
	public IdIdea get(String itemId, String registerId) {
		
		
		IdIdea idIdea = idIdeaDao.get(itemId);
		
		if(idIdea != null){
			boolean exists = idReferenceDao.exists(itemId, registerId);
			
			//조회이력 등록
			if(!exists){
				idReferenceDao.create(itemId, registerId);
				
				idIdeaDao.updateHitCount(itemId);
				idIdea.setHitCount(idIdea.getHitCount()+1);	//화면에 1개 더해서 보여줌
				
			} 
			
			//파일 조회
			List<FileData> fileDataList = fileService.getItemFile(itemId,"");
			idIdea.setFileDataList(fileDataList);
		}
		return idIdea;
	}
	
	
	public IdIdea getCountes(IdSearch idSearch) {
		return idIdeaDao.getCountes(idSearch);
	}

	public List<IdIdea> list(IdSearch idSearch) {
		return idIdeaDao.list(idSearch);
	}

	public int getCountList(IdSearch idSearch) {
		return idIdeaDao.getCountList(idSearch);
	}
	


	@Override
	public void update(IdIdea idIdea) {
		
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(idIdea.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(idIdea.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						idIdea.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					idIdea.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		idIdeaDao.update(idIdea);
		
		//첨부파일 업데이트
		if(idIdea.getFileLinkList() != null) {
			idIdea.setAttachFileCount(idIdea.getFileLinkList().size());
			User user = new User();
			user.setUserId(idIdea.getRegisterId());
			user.setUserName(idIdea.getRegisterName());
			
			fileService.saveFileLink(idIdea.getFileLinkList(), idIdea.getItemId(), IKepConstant.ITEM_TYPE_CODE_IDEATION, user);
		} 
		
		//태그 등록
		createTag(idIdea);
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_IDEATION, IKepConstant.ACTIVITY_CODE_DOC_EDIT, idIdea.getItemId(), idIdea.getTitle());

	}
	
	

	public void updateFavoriteCount(String itemId, int favoriteCount, String userId) {
		
		idIdeaDao.updateFavoriteCount(itemId, favoriteCount);
		
		//사용자 이력 등록
		IdUserActivities idUserActivities = new IdUserActivities();
		idUserActivities.setUserId(userId);
		
		if(favoriteCount == IdeationConstants.NUM_INCRASE){	//즐겨찾기 추가면
			idUserActivities.setFavoriteCount(IdeationConstants.NUM_INCRASE);
		} else {	//즐겨찾기 삭제면
			idUserActivities.setFavoriteCount(IdeationConstants.NUM_DECREASE);
		}
		
		boolean activityExists = idUserActivitiesDao.exists(userId);
		
		if(activityExists){
			int fCount = idIdeaDao.getFavorite(userId, IKepConstant.ITEM_TYPE_CODE_IDEATION);
			idUserActivitiesDao.updateFavoriteCount(userId, fCount);
		} else {
			idUserActivitiesDao.create(idUserActivities);
		}
		
	}
	
	

	public void updateMailCount(String itemId) {
		
		idIdeaDao.updateMailCount(itemId);
		
	}

	public void updateMblogCount(String itemId) {
		idIdeaDao.updateMblogCount(itemId);
	}

	public void updateBusinessItem(String itemId, String businessItem) {
		
		idIdeaDao.updateBusinessItem(itemId, businessItem);
		
		//사용자 이력 등록 
		if(businessItem == IdeationConstants.BUSINESS_TYPE_COMPLETE){
			
			IdIdea idIdea = idIdeaDao.get(itemId);
			
			IdUserActivities idUserActivities = new IdUserActivities();
			idUserActivities.setUserId(idIdea.getRegisterId());
			idUserActivities.setBusinessItemCount(IdeationConstants.NUM_INCRASE);
			
			boolean activityExists = idUserActivitiesDao.exists(idIdea.getRegisterId());
			
			if(activityExists){
				idUserActivitiesDao.updateBusinessItemCount(idIdea.getRegisterId());
			} else {
				idUserActivitiesDao.create(idUserActivities);
			}
		} else if(businessItem == IdeationConstants.BUSINESS_TYPE_NONE){
			
			IdIdea idIdea = idIdeaDao.get(itemId);
			
			idUserActivitiesDao.updateBusinessItemCount(idIdea.getRegisterId());
		}
	
	}


	public void updateExamination(String itemId, String examinationComment) {
		
		idIdeaDao.updateExamination(itemId, examinationComment);
		
		//사업화 상태 변경
		if(!StringUtil.isEmpty(examinationComment)){
			idIdeaDao.updateBusinessItem(itemId, IdeationConstants.BUSINESS_TYPE_ABOPT);
		}
		
		IdIdea idIdea = idIdeaDao.get(itemId);
		
		//사용자 이력 등록
		IdUserActivities idUserActivities = new IdUserActivities();
		idUserActivities.setUserId(idIdea.getRegisterId());
		idUserActivities.setBusinessItemCount(IdeationConstants.NUM_INCRASE);
		
		boolean activityExists = idUserActivitiesDao.exists(idIdea.getRegisterId());
		
		if(activityExists){
			idUserActivitiesDao.updateBusinessItemCount(idIdea.getRegisterId());
		} else {
			idUserActivitiesDao.create(idUserActivities);
		}
		
	}

	public void remove(String itemId) {
		
		//토론글  등록 이력 재등록
		List<String> userIdList = new ArrayList<String>();
		
		IdIdea idIdea = idIdeaDao.get(itemId);
		userIdList.add(idIdea.getRegisterId());
		
		userIdList.addAll(idRecommendDao.listUserIdByItemId(itemId));	
		
		userIdList.addAll(idReferenceDao.listUserId(itemId));
		
		userIdList.addAll(idLinereplyDao.listUserId(itemId));
		
		userIdList.addAll(idLinereplyRecommendDao.listUserIdByItemId(itemId));
		
		
		//토론의견추천삭제
		idLinereplyRecommendDao.removeByItemId(itemId);
		
		//토론의견삭제
		idLinereplyDao.removeByItemId(itemId);		
		
		//토론글조회이력삭제
		idReferenceDao.removebyItemId(itemId);
		
		//토론글스코어 삭제
		idRecommendDao.removeByItemId(itemId);
		
		//토론 글 삭제
		idIdeaDao.remove(itemId);
	
			
		//태그 삭제
		tagService.delete(itemId, TagConstants.ITEM_TYPE_IDEATION);
		
		
		//전체 파일 삭제
		fileService.removeItemFile(itemId);
		
		//사용자 활동 점수 계산
		List<String> newUserIdList = new ArrayList<String>();
		for(String userId : userIdList){
			if(!newUserIdList.contains(userId)){
				newUserIdList.add(userId);
			}
		}	
		
		//자료 삭제하고 나서 계산해야 함.
		for(String userId : newUserIdList){
			
			IdUserActivities idUserActivities = new IdUserActivities();
			idUserActivities.setUserId(userId);
			
			//토론주제 같은 사용자가 등록한 토론글 개수
			IdSearch activitySearch = new IdSearch();
			activitySearch.setUserId(userId);
			IdIdea itemCountes = idIdeaDao.getCountes(activitySearch);
			
			
			IdSearch bisinessSearch = new IdSearch();
			bisinessSearch.setUserId(userId);
			bisinessSearch.setIsBusiness(IdeationConstants.IS_BUSINESS);
			IdIdea bisinessCountes = idIdeaDao.getCountes(bisinessSearch);
			
			idUserActivities.setItemCount(itemCountes.getCount());
			idUserActivities.setRecommendItemCount(itemCountes.getRecommendItem());
			idUserActivities.setAdoptItemCount(itemCountes.getAdoptCount());
			idUserActivities.setBestItemCount(itemCountes.getBestCount());
			idUserActivities.setBusinessItemCount(bisinessCountes.getCount());
			idUserActivities.setFavoriteCount(itemCountes.getFavoriteCount());
			
			
			IdSearch lineSearch = new IdSearch();
			lineSearch.setUserId(userId);
			IdLinereply linereplyCountes = idLinereplyDao.getCountes(lineSearch);
			idUserActivities.setLinereplyCount(linereplyCountes.getCount());
			idUserActivities.setRecommendCount(linereplyCountes.getRecommendCount());
			idUserActivities.setAdoptCount(linereplyCountes.getAdoptLinereply());
			
			
			idUserActivitiesDao.update(idUserActivities);
			
		}
		
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_IDEATION, IKepConstant.ACTIVITY_CODE_DOC_DELETE, idIdea.getItemId(), idIdea.getTitle());

		
		//파일 삭제
		fileService.removeItemFile(itemId);
	}
	
	/**
	 * 태그 등록 - 등록, 수정시 사용
	 * @param qna
	 */
	private void createTag(IdIdea idIdea){
		
		//태그 등록	- 태그 있을때 등록
		if(!StringUtil.isEmpty(idIdea.getTag())){
			
			Tag tag = new Tag();
			
			tag.setTagName(idIdea.getTag());										//사용자가 작성한 tag
			tag.setTagItemId(idIdea.getItemId());									//게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_IDEATION);						//모듈 타입 TagConstants에 정의되어 있음.
			tag.setTagItemName(idIdea.getTitle());									//게시물 제목
			tag.setTagItemContents(idIdea.getContents());  						//게시물 내용
			tag.setTagItemUrl("/collpack/ideation/getView.do?docPopup=true&itemId="+idIdea.getItemId());	//게시물 팝업창 url
			tag.setRegisterId(idIdea.getRegisterId());
			tag.setRegisterName(idIdea.getRegisterName());
			tag.setPortalId(idIdea.getPortalId());
			
			tagService.create(tag);
		}
	}
	

	
	public void updateBest() {
		//정책
		List<IdPolicy> policyList = idPolicyDao.list(IdeationConstants.POLICY_BEST);

		idIdeaDao.updateBestItemInit();	//베스트 토론의견 초기화
		
		// 이력 재등록
		List<String> userIdList = new ArrayList<String>();
		
		//우수 아이디어 선정
		for(IdPolicy idPolicy : policyList){
			
			IdSearch idSearch = new IdSearch();
			idSearch.setPortalId(idPolicy.getPortalId());
			
			List<IdIdea> ideaList = idIdeaDao.list(idSearch);
			
			for(IdIdea idea : ideaList){
				
				if(idea.getRecommendCount() >= idPolicy.getRecommendWeight()){
					idIdeaDao.updateBestItem(idea.getItemId());
					userIdList.add(idea.getRegisterId());
				}
			}
		}
		
		
		//사용자 활동 점수 계산
		List<String> newUserIdList = new ArrayList<String>();
		for(String userId : userIdList){
			if(!newUserIdList.contains(userId)){
				newUserIdList.add(userId);
			}
		}
		
		
		//사용자 베스트 개수 업데이트
		for(String userId : newUserIdList){
			
			IdUserActivities idUserActivities = new IdUserActivities();
			idUserActivities.setUserId(userId);
			
			//토론주제 같은 사용자가 등록한 토론글 개수
			IdSearch activitySearch = new IdSearch();
			activitySearch.setUserId(userId);
			IdIdea itemCountes = idIdeaDao.getCountes(activitySearch);
			
			idUserActivities.setBestItemCount(itemCountes.getBestCount());
			
			idUserActivitiesDao.update(idUserActivities);
			
		}
		
	}

	public int getFavorite(String userId, String itemTypeCode) {
		return idIdeaDao.getFavorite(userId, itemTypeCode);
	}
}
