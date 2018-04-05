/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: Qna.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Qna extends BaseObject {

	public interface Create {
	} // Create group을 선언

	public interface Update {
	} // Update group을 선언

	/**
	 *
	 */
	private static final long serialVersionUID = 4182056323460781014L;

	/**
	 * 게시물 ID
	 */
	private String qnaId;

	/**
	 * 게시물 ID
	 */
	private String qnaGroupId;

	/**
	 * 질문인지 답변인지
	 */
	private Integer qnaType;

	/**
	 * 질문 카테고리 ID
	 */
	private String categoryId;

	/**
	 * 게시물 제목
	 */
	@NotEmpty
	@Size(min = 1, max = 500)
	private String title;

	/**
	 * 게시물 내용
	 */
	@NotEmpty
	private String contents;

	/**
	 * 질문/답변 진행 상태( 0 : 미답변, 1 : 해결중. 2 : 완료)
	 */
	private String status;

	/**
	 * hit 개수
	 */
	private int hitCount = 0;

	/**
	 * recommend 개수
	 */
	private int recommendCount = 0;

	/**
	 * reply 개수
	 */
	private int replyCount = 0;

	/**
	 * 한줄 리플 개수
	 */
	private int linereplyCount = 0;

	/**
	 * 첨부파일 개수
	 */
	private int attachFileCount = 0;

	/**
	 * 등록자 Id
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록 날짜
	 */
	private Date registDate;

	/**
	 * updater Id
	 */
	private String updaterId;

	/**
	 * updater 이름
	 */
	private String updaterName;

	/**
	 * update 날짜
	 */
	private Date updateDate;

	/**
	 * 채택 여부 (0, 미채택, 1, 채택답변)
	 */
	private Integer answerAdopt;

	/**
	 * 긴급질문 여부(0:일반질문, 1 : 긴급질문)
	 */
	private Integer urgent;

	/**
	 * 삭제필드
	 */
	private Integer itemDelete;

	/**
	 * 포탈 ID (for Multi Portal)
	 */
	private String portalId;

	/**
	 * 즐겨찾기 등록수
	 */
	private int favoriteCount = 0;

	/**
	 * BEST 게시글 점수(일배치프로시저로 BEST정책에 따라 점수 업데이트)
	 */
	private int score = 0;

	/**
	 * BEST 정책에 의해 일별 BEST게시물에 설정된 게시물 여부( 0 : 일반, 1 : BEST질문 or 답변)
	 */
	private Integer bestFlag;

	/**
	 * 질문에 대한 첫 답변이 등록될때까지 걸리린시간으로 첫답변 등록시에만 수정함( 0 : 답변이 등록되기 전 시간차이값으로
	 * 디폴트질문등록시간 - 첫답변등록시간)
	 */
	private double answerNecessaryTime = 0;

	/**
	 * 조회하는 리스트 타입
	 */
	private String listType;

	/**
	 * expert id
	 */
	private String expertId;

	/**
	 * expert id 들 (멀티셀렉트)
	 */
	private String[] expertIds;

	/**
	 * expert답변 요청 전달 채널
	 */
	private String expertChannel;

	/**
	 * 태그
	 */
	//@NotEmpty
	private String tag;

	/**
	 * 태그
	 */
	private List<Tag> tagList;

	/**
	 * 사용자 팀이름
	 */
	private String teamName;

	/**
	 * 프로파일 사진 파일 ID
	 */
	private String pictureId;

	/**
	 * 직책
	 */
	private String jobTitleName;

	/**
	 * 리스트 탭
	 */
	private Integer listTab;

	/**
	 * 질문에 대한 답변 등록시 알림 채널설정1000 : 메일1100 : 메일, SMS1110 : 메일, SMS, 쪽지 1111 : 메일,
	 * SMS, 쪽지, 마이크로블로깅
	 */
	private String alarmChannel = "0000";

	/**
	 * 파일 링크 리스트
	 */
	private List<FileLink> fileLinkList;

	/**
	 * 카테고리 이름
	 */
	private String categoryName;

	/**
	 * qna url 주소
	 */
	private String qnaPathUrl;

	/**
	 * 핸드폰 번호
	 */
	private String mobile;

	/**
	 * email
	 */
	private String mail;

	/**
	 * 메일 수
	 */
	private int mailCount;

	/**
	 * 블로그 수
	 */
	private int mblogCount;

	/**
	 * ckedit 파일 전송
	 */
	private List<FileLink> editorFileLinkList;

	/**
	 * 파일데이터리스트
	 */
	private List<FileData> fileDataList;

	/**
	 * 영문 이름
	 */
	private String userEnglishName;

	/**
	 * 영문 팀명
	 */
	private String teamEnglishName;

	/**
	 * 영문직책
	 */
	private String jobTitleEnglishName;

	/**
	 * 파일 리스트 json
	 */
	private String fileDataListJson;

	/**
	 * 게시물 ID 들
	 */
	private List<String> qnaIdList;

	/**
	 * 현재 페이지 번호
	 */
	private int pageIndex = 1;

	/**
	 * 게시판 가져올 끝 수
	 */
	private Integer endRowIndex;

	/**
	 * 게시판 가져올 처음 수
	 */
	private Integer startRowIndex;

	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 미채택인지
	 */
	private Integer IsNotAdopt;

	/**
	 * 최근 가져올 날짜
	 */
	private String newDate;

	/**
	 * 정렬타입
	 */

	private String orderType;

	/**
	 * 답변 Best
	 */
	private Integer answerBestFlag;

	/**
	 * 카테고리가 없는 것들
	 */
	private String isNullCategory;

	private String searchWord;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public String getQnaId() {
		return qnaId;
	}

	public void setQnaId(String qnaId) {
		this.qnaId = qnaId;
	}

	public String getQnaGroupId() {
		return qnaGroupId;
	}

	public void setQnaGroupId(String qnaGroupId) {
		this.qnaGroupId = qnaGroupId;
	}

	public Integer getQnaType() {
		return qnaType;
	}

	public void setQnaType(Integer qnaType) {
		this.qnaType = qnaType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public int getAttachFileCount() {
		return attachFileCount;
	}

	public void setAttachFileCount(int attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getAnswerAdopt() {
		return answerAdopt;
	}

	public void setAnswerAdopt(Integer answerAdopt) {
		this.answerAdopt = answerAdopt;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public Integer getItemDelete() {
		return itemDelete;
	}

	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Integer getBestFlag() {
		return bestFlag;
	}

	public void setBestFlag(Integer bestFlag) {
		this.bestFlag = bestFlag;
	}

	public double getAnswerNecessaryTime() {
		return answerNecessaryTime;
	}

	public void setAnswerNecessaryTime(double answerNecessaryTime) {
		this.answerNecessaryTime = answerNecessaryTime;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String[] getExpertIds() {
		return expertIds;
	}

	public void setExpertIds(String[] expertIds) {
		this.expertIds = expertIds;
	}

	public String getExpertChannel() {
		return expertChannel;
	}

	public void setExpertChannel(String expertChannel) {
		this.expertChannel = expertChannel;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getAlarmChannel() {
		return alarmChannel;
	}

	public void setAlarmChannel(String alarmChannel) {
		this.alarmChannel = alarmChannel;
	}

	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getQnaPathUrl() {
		return qnaPathUrl;
	}

	public void setQnaPathUrl(String qnaPathUrl) {
		this.qnaPathUrl = qnaPathUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getMailCount() {
		return mailCount;
	}

	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}

	public int getMblogCount() {
		return mblogCount;
	}

	public void setMblogCount(int mblogCount) {
		this.mblogCount = mblogCount;
	}

	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getFileDataListJson() {
		return fileDataListJson;
	}

	public void setFileDataListJson(String fileDataListJson) {
		this.fileDataListJson = fileDataListJson;
	}

	public List<String> getQnaIdList() {
		return qnaIdList;
	}

	public void setQnaIdList(List<String> qnaIdList) {
		this.qnaIdList = qnaIdList;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public Integer getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(Integer startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public Integer getIsNotAdopt() {
		return IsNotAdopt;
	}

	public void setIsNotAdopt(Integer isNotAdopt) {
		IsNotAdopt = isNotAdopt;
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getAnswerBestFlag() {
		return answerBestFlag;
	}

	public void setAnswerBestFlag(Integer answerBestFlag) {
		this.answerBestFlag = answerBestFlag;
	}

	public Integer getListTab() {
		return listTab;
	}

	public void setListTab(Integer listTab) {
		this.listTab = listTab;
	}

	public String getIsNullCategory() {
		return isNullCategory;
	}

	public void setIsNullCategory(String isNullCategory) {
		this.isNullCategory = isNullCategory;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public Integer getMsie() {
		return msie;
	}

	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

}
