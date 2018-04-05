/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.who.constants.WhoConstants;
import com.lgcns.ikep4.collpack.who.dao.WhoDao;
import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.collpack.who.service.WhoService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service("whoService")
public class WhoServiceImpl extends GenericServiceImpl<Who, String> implements WhoService {
	@Autowired
	private WhoDao whoDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;

	@Autowired
	private ActivityStreamService activityStreamService;

	/**
	 * 인물등록
	 */
	public String create(Who who, User user) {
		String activityCode = IKepConstant.ACTIVITY_CODE_DOC_POST;
		String tagName = "";
		String profileId = who.getProfileId();

		// 신규
		if (profileId == null || "".equals(profileId)) {
			who.setProfileGroupId(idgenService.getNextId());
			who.setProfileId(idgenService.getNextId());
			activityCode = IKepConstant.ACTIVITY_CODE_DOC_EDIT;

			// 이미지 등록
			if (who.getFileId() != null) {
				fileService.createFileLink(who.getFileId(), who.getProfileId(), IKepConstant.ITEM_TYPE_CODE_WHOSWHO,
						user);
			}
		} else { // 수정이력적용
			if ("imgChange".equals(who.getMode())) { // 이미지추가,변경
				who.setProfileId(idgenService.getNextId());
				fileService.createFileLink(who.getFileId(), who.getProfileId(), IKepConstant.ITEM_TYPE_CODE_WHOSWHO,
						user);
			} else { // 이미지가 없거나 이력적용
				List<FileData> fileDataList = fileService.getItemFile(who.getProfileId(), "");
				List<String> fileIdList = new ArrayList<String>();
				if (!fileDataList.isEmpty()) {
					fileIdList.add(fileDataList.get(0).getFileId());
				}
				who.setProfileId(idgenService.getNextId());
				fileService.copyForTransfer(fileIdList, who.getProfileId(), IKepConstant.ITEM_TYPE_CODE_WHOSWHO, user);
			}
			// 이력적용일 경우 태그적용
			if ("apply".equals(who.getMode())) {
				List<Tag> tagList = tagService.listTagByItemId(profileId, TagConstants.ITEM_TYPE_WHO);
				Tag tag = new Tag();
				for (int i = 0; i < tagList.size(); i++) {
					tag = tagList.get(i);
					tagName = tagName + tag.getTagName();
					if (i < (tagList.size() - 1)) {
						tagName += ",";
					}
				}
				who.setTag(tagName);
				who.setUpdateReason(who.getVersion() + "버전으로 적용되었습니다."); // 이력적용일
																			// 경우
																			// 수정사유
																			// 적용
			}
		}

		String version = whoDao.selectVersion(who);
		who.setVersion(Double.parseDouble(version));
		whoDao.create(who);

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(who.getTag())) {
			Tag tag = new Tag();

			tag.setTagName(who.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(who.getProfileId()); // 게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_WHO); // 모듈 타입
															// TagConstants에
															// 정의되어 있음.
			tag.setTagItemSubType(who.getProfileGroupId()); // 모듈 서브 타입 - 있을때만
															// 넣기
			tag.setTagItemName(who.getName()); // 게시물 제목
			tag.setTagItemContents(who.getMemo()); // 게시물 내용
			tag.setTagItemUrl("/collpack/who/getWho.do?profileId=" + who.getProfileId()); // 게시물
																						// 팝업창
																						// url
			tag.setRegisterId(who.getRegisterId());
			tag.setRegisterName(who.getRegisterName());
			tag.setPortalId(who.getPortalId());

			tagService.create(tag);
		}

		// Activity Stream 추가
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WHOSWHO, activityCode, who.getProfileId(),
				who.getName());

		return who.getProfileId();
	}

	/**
	 * 사내관련인물 가져오기
	 */
	public List<Pro> selectProList(Tag searchTag) {
		List<Pro> proList = new ArrayList<Pro>();
		Pro pro = new Pro();
		Map<String, Object> qnaIdMap = null;
		List<String> qnaIdList = new ArrayList<String>();

		String itemType = searchTag.getTagItemType();
		String asTagItemType = searchTag.getAsTagItemType();
		Who who = new Who();
		int tmpCount = 0;

		// 사내인물검색
		if ("".equals(searchTag.getTagId())) { // 나의전문분야관련인물(처음 로딩시)
			List<Tag> tagList = tagService.listItemCount(searchTag.getTagItemId(), searchTag.getTagItemType());

			for (Tag tag : tagList) {
				searchTag.setTagId(tag.getTagId());
				qnaIdMap = tagService.listItemId(searchTag);
				qnaIdList = (List<String>) qnaIdMap.get("list");

				if (tmpCount == searchTag.getPagePer() && asTagItemType.equals(TagConstants.ITEM_TYPE_PROFILE_PRO)) {
					// 관련사내인물일 경우 10개만 리턴, 나의전문분야검색일 경우 결과수만큼 리턴
					return proList;

				}

				for (int i = 0; i < qnaIdList.size(); i++) {
					pro = new Pro();

					User userInfo = userDao.get(qnaIdList.get(i));
					if (userInfo == null) {
						userInfo = new User();
					}

					pro.setTagId(tag.getTagId());
					pro.setTagName(tag.getTagName());
					pro.setCount(tag.getCount() - 1); // 태그쪽에서 실제 수보다 1크게 리턴함.
					pro.setUserId(userInfo.getUserId());
					pro.setUserName(userInfo.getUserName());
					pro.setUserEnglishName(userInfo.getUserEnglishName());
					pro.setJobTitleName(userInfo.getJobTitleName());
					pro.setJobTitleEnglishName(userInfo.getJobTitleEnglishName());
					pro.setTeamName(userInfo.getTeamName());
					pro.setTeamEnglishName(userInfo.getTeamEnglishName());
					pro.setMobile(userInfo.getMobile());
					pro.setMail(userInfo.getMail());
					pro.setProfilePictureId(userInfo.getProfilePictureId());
					pro.setPicturePath(userInfo.getPicturePath());
					pro.setProfilePicturePath(userInfo.getProfilePicturePath());

					SearchCondition pageCondition = new SearchCondition();
					pageCondition = (SearchCondition) qnaIdMap.get("pageCondition");
					pro.setPageIndex(pageCondition.getPageIndex());
					pro.setPageCount(pageCondition.getPageCount());
					
					proList.add(pro);
					tmpCount++;

				}
				// 전문분야 관련인물이 없을 경우(나의전문분야관련인물-처음 로딩시)
				if (qnaIdList.size() == 0 && !asTagItemType.equals(TagConstants.ITEM_TYPE_PROFILE_PRO)) {
					pro = new Pro();
					pro.setTagId(tag.getTagId());
					pro.setTagName(tag.getTagName());
					pro.setCount(tag.getCount() - 1); // 태그쪽에서 실제 수보다 1크게 리턴함.

					proList.add(pro);
				}
			}
		} else { // 나의전문분야관련인물(추가 10건 보기)
			qnaIdMap = tagService.listItemId(searchTag);
			qnaIdList = (List<String>) qnaIdMap.get("list");
			for (int i = 0; i < qnaIdList.size(); i++) {
				pro = new Pro();
				User userInfo = userDao.get(qnaIdList.get(i));
				if (userInfo == null) {
					userInfo = new User();
				}
				pro.setUserId(userInfo.getUserId());
				pro.setUserName(userInfo.getUserName());
				pro.setUserEnglishName(userInfo.getUserEnglishName());
				pro.setJobTitleName(userInfo.getJobTitleName());
				pro.setJobTitleEnglishName(userInfo.getJobTitleEnglishName());
				pro.setTeamName(userInfo.getTeamName());
				pro.setTeamEnglishName(userInfo.getTeamEnglishName());
				pro.setMobile(userInfo.getMobile());
				pro.setMail(userInfo.getMail());
				pro.setProfilePictureId(userInfo.getProfilePictureId());
				pro.setPicturePath(userInfo.getPicturePath());
				pro.setProfilePicturePath(userInfo.getProfilePicturePath());

				SearchCondition pageCondition = new SearchCondition();
				pageCondition = (SearchCondition) qnaIdMap.get("pageCondition");
				pro.setPageIndex(pageCondition.getPageIndex());
				pro.setPageCount(pageCondition.getPageCount());

				proList.add(pro);
			}
		}

		return proList;
	}

	/**
	 * 외부관련인물 가져오기
	 */
	public List<Pro> selectWhoProList(Tag searchTag) {
		List<Pro> proList = new ArrayList<Pro>();
		Pro pro = new Pro();
		Map<String, Object> qnaIdMap = null;
		List<String> qnaIdList = new ArrayList<String>();

		String itemType = searchTag.getTagItemType();
		String asTagItemType = searchTag.getAsTagItemType();
		Who who = new Who();
		int tmpCount = 0;
		// 관련외부인물
		qnaIdMap = tagService.listItemId(searchTag);
		qnaIdList = (List<String>) qnaIdMap.get("list");
		for (int i = 0; i < qnaIdList.size(); i++) {
			pro = new Pro();
			who = whoDao.get(qnaIdList.get(i));
			// 같은 그룹이 아닌것
			pro.setTagItemId(who.getProfileId());
			pro.setUserName(who.getName());
			pro.setJobTitleName(who.getJobRankName());
			pro.setTeamName(who.getTeamName());
			

			// 이미지파일
			List<FileData> fileDataList = fileService.getItemFile(who.getProfileId(), "");
			if (!fileDataList.isEmpty()) {
				pro.setProfilePictureId(fileDataList.get(0).getFileId());
			}
			proList.add(pro);

		}
		return proList;
	}

	/**
	 * 인물정보 가져오기
	 */
	public Who read(String profileId) {
		Who who = whoDao.get(profileId);

		return who;
	}

	/**
	 * 인물정보,이미지파일 가져오기
	 */
	public Who readDetail(Who whoParam) {
		int checkCnt = whoDao.checkAlreadyRead(whoParam);
		String profileId = whoParam.getProfileId();
		String registerId = whoParam.getRegisterId();
		String referenceId;

		Who who = new Who();
		who = whoDao.get(profileId);
		String recentVersion = whoDao.selectOriginVersion(who);
		String curVersion = Double.toString(who.getVersion());

		// 최신 버전만 조회수 관련 데이터 갱신
		if (recentVersion.equals(curVersion)) {
			// 처음 조회한 경우
			if (checkCnt == 0) {
				whoDao.updateHit(profileId);
				referenceId = idgenService.getNextId();
				who.setReferenceId(referenceId);
				who.setRegisterId(registerId);
				whoDao.insertHit(who);
				who.setHitCount(who.getHitCount() + 1);
				// 다시 조회한 경우
			} else {
				// 로긴한 userID
				who.setViewId(registerId);
				whoDao.updateHitDate(who);

			}
		}

		// 이미지파일
		List<FileData> fileDataList = fileService.getItemFile(who.getProfileId(), "");
		if (!fileDataList.isEmpty()) {
			who.setFileDataList(fileDataList);
			who.setFileId(fileDataList.get(0).getFileId());
			who.setProfilePictureId(fileDataList.get(0).getFileId());

		}

		// 최근 입력자 ID
		String recentInputRegisterId = whoDao.selectRecentInputRegisterId(who.getProfileGroupId());
		who.setRecentInputRegisterId(recentInputRegisterId);

		// 최근 버전 정보
		who.setRecentVersion(recentVersion);

		// 최근 인물ID
		String recentProfileId = whoDao.selectRecentProfileId(who.getProfileGroupId());
		who.setRecentProfileId(recentProfileId);

		return who;
	}

	/**
	 * 이메일중복체크
	 */
	public Integer checkAlreadyMailExists(Who who) {
		int wordCount = 0;
		wordCount = whoDao.checkAlreadyMailExists(who);
		return wordCount;
	}

	/**
	 * 수정이력 리스트 가져오기
	 */
	public List<Who> selectProfileHistoryList(String profileGroupId) {
		List<Who> who = whoDao.selectProfileHistoryList(profileGroupId);

		return who;
	}

	/**
	 * 인물 삭제
	 */
	public void delete(String profileId) {
		// Activity Stream 추가
		Who who = whoDao.get(profileId);
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WHOSWHO, IKepConstant.ACTIVITY_CODE_DOC_DELETE,
				who.getProfileId(), who.getName());

		whoDao.deleteReferenceByProfileId(profileId);
		whoDao.remove(profileId);
		tagService.delete(profileId, TagConstants.ITEM_TYPE_WHO);
		fileService.removeItemFile(profileId);
	}

	/**
	 * 인물 갱신
	 */
	public void update(Who who) {
		whoDao.update(who);
	}

	/**
	 * 인물 목록 가져오기
	 */
	public SearchResult<Who> listWhoBySearchCondition(WhoSearchCondition searchCondition) {
		boolean sortIndexNullYn = getSortIndexNullYn(searchCondition.getWhoSortIndex());
		// 인덱스버튼을 클릭한 경우가 아닌 경우 검색조건 변경
		if (sortIndexNullYn) {
			searchCondition.setStartSortChar("");
			searchCondition.setEndSortChar("");
			// 인덱스버튼을 클릭한 경우
		} else {
			searchCondition.setSearchColumn("");
			searchCondition.setSearchWord("");
		}
		Integer count = whoDao.countWhoBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Who> searchResult = new SearchResult<Who>(searchCondition);
		List<Tag> tagList = null;

		if (!searchCondition.isEmptyRecord()) {
			List<Who> whoItemList = new ArrayList<Who>();
			if ("tag".equals(searchCondition.getSearchColumn())) {
				Tag tag = new Tag();
				tag.setPortalId(searchCondition.getPortalId()); // 포탈 ID
				tag.setTagItemType(TagConstants.ITEM_TYPE_WHO); // 모듈 타입
																// TagConstants에
																// 정의되어 있음.-team
																// coll은 type
																// ID를 넣으셔야 합니다.
																// - 없으면 않넣으면 됨.
				tag.setTagName(searchCondition.getSearchWord());
				tag.setGroupType("tagItemSubType");
				tag.setPageIndex(1);
				tag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);

				// Map<String, Object> itemIdMap =
				// tagService.listItemAsCount(tag,
				// searchCondition.getPageIndex(),
				// searchCondition.getPagePerRecord());
				Map<String, Object> itemIdMap = tagService.listItemId(tag);

				count = (Integer) itemIdMap.get("count");
				searchCondition.terminateSearchCondition(count);

				List<String> tmpTagList = (List<String>) itemIdMap.get("list");
				for (int i = 0; i < tmpTagList.size(); i++) {
					whoItemList.add(read(tmpTagList.get(i)));
				}
			} else {
				whoItemList = whoDao.listWhoBySearchCondition(searchCondition);
			}
			boolean getTagYnFlag = getTagYn(searchCondition.getMode(), searchCondition.getIsMore(), whoItemList.size());
			if (getTagYnFlag) {

				for (Who who : whoItemList) {
					tagList = tagService.listTagByItemId(who.getProfileId(), TagConstants.ITEM_TYPE_WHO);
					who.setTagList(tagList);

					// 이미지
					List<FileData> fileDataList = fileService.getItemFile(who.getProfileId(), "");
					who.setFileDataList(fileDataList);
					if (!fileDataList.isEmpty()) {
						who.setProfilePictureId(fileDataList.get(0).getFileId());
					}
				}

			}
			searchResult = new SearchResult<Who>(whoItemList, searchCondition);

		}

		return searchResult;
	}

	/**
	 * 태그 조회 여부
	 */
	public boolean getTagYn(String mode, String isMore, int size) {
		boolean result = false;
		if (((!"myInputList".equals(mode) || "Y".equals(isMore)) && (!"myViewList".equals(mode) || "Y".equals(isMore)))
				&& size > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 인덱스 널인지 여부
	 */
	public boolean getSortIndexNullYn(String sortIndex) {
		boolean result = false;
		if ("-1".equals(sortIndex) || "".equals(sortIndex) || sortIndex == null) {
			result = true;
		}
		return result;
	}
}
