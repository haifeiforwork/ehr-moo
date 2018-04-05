/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;



import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;

/**
 * 아이디어 제안글 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdea.java 14840 2011-06-13 08:18:54Z loverfairy $
 */
public class IdIdea extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -6187552703240331979L;


	/**
	 * 토론글 아이템 ID
	 */
	private String itemId;


    /**
     * 토론글 제목
     */
	@NotEmpty
	@Size(min=1,max=500)
    private String title;
    
    /**
     * 토론글 내용
     */
	@NotEmpty
    private String contents;
    
    /**
     * 추천 아이디어 여부( 0 : 추천 아님 1 : 추천된 아이디어)
     */
    private int recommendItem;
    
    /**
     * 채택 아이디어 여부( 0 : 채택안됨 1 : 채택된 아이디어)
     */
    private int adoptItem;

    /**
     * 우수 아이디어 여부( 0 : 우수 아님 1 : 우수 아이디어)
     */
    private int bestItem;
    
    /**
     * 사업화 선정 아이템 여부( 0 : 비선정 1 : 사업화선정 2 : 사업화완료)
     */
    private String businessItem;

    /**
     * 토론글 조회수
     */
    private int hitCount;
    
    /**
     * 아이디어 추천수
     */
    private int recommendCount;
    
    /**
     * 토론글 즐겨찾기수
     */
    private int favoriteCount;
    
    /**
     * 아이디어 채택수
     */
    private int adoptCount;
    

    /**
     * 토론글 하위 토론의견(댓글) 수
     */
    private int linereplyCount;
    
    /**
     * 메일 보내기 건수
     */
    private int mailCount;
    
    /**
     * 마이크로블로깅 보내기 건수
     */
    private int mblogCount;
    
    /**
     * 아이디어 사업화 선정 검토의견
     */
    private String examinationComment;
    
    
    /**
	 * 포털 ID
	 */
	private String portalId;
    

    /**
     * 등록자 ID
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록일시
     */
    private Date registDate;

    /**
     * 수정자 ID
     */
    private String updaterId;

    /**
     * 수정자 이름
     */
    private String updaterName;

    /**
     * 수정일시
     */
    private Date updateDate;

    /**
     * 팀이름
     */
    private String teamName;
    
    /**
     * 직책이름
     */
    private String jobTitleName;
    
	 /**
	 * 개수
	 */
	private int count;
	
	/**
	 * 베스트 글 개수
	 */
	private int bestCount;
	
	/**
	 * 태그 url 경로
	 */
	private String pathUrl;
	
	
	/**
	 * 태그
	 */
	//@NotEmpty
	private String tag;
	
	/**
	 * 리스트 타입
	 */
	private String listType;
	
	/**
	 * 태그 리스트
	 */
	private List<Tag> tagList;
	
	/**
	 * 파일 링크 리스트
	 */
    private List<FileLink> fileLinkList;  
    
    
    /**
	 * 파일 데이터를 조회할때 사용함
	 */
    private List<FileData> fileDataList;

  
    /**
     * ckedit 파일 전송
     */
    private List<FileLink> editorFileLinkList;
    
    /**
	 * 첨부파일 개수
	 */
	private int attachFileCount = 0;
	
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
     * iframe 모드 인지
     */
    private String docIframe;
  
	
	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/** 같은 부모 아래 형제 순서. */
	private Integer step;
	
	/**
	 * 공모인지 제안인지
	 */
	private Integer itemType;
	
	/**
	 *  카테고리 ID
	 */
	private String categoryId;
	
	private String categoryName;
	
	
	/** 게시글의 쓰레드로 묶여 있는 글 리스트. */
	private List<IdIdea> replyItemThreadList;
	
	private String openContestFlag;
	
	
	private Integer contestCount;
	
	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
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


	public int getRecommendItem() {
		return recommendItem;
	}


	public void setRecommendItem(int recommendItem) {
		this.recommendItem = recommendItem;
	}


	public int getAdoptItem() {
		return adoptItem;
	}


	public void setAdoptItem(int adoptItem) {
		this.adoptItem = adoptItem;
	}


	public int getBestItem() {
		return bestItem;
	}


	public void setBestItem(int bestItem) {
		this.bestItem = bestItem;
	}


	public String getBusinessItem() {
		return businessItem;
	}


	public void setBusinessItem(String businessItem) {
		this.businessItem = businessItem;
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


	public int getFavoriteCount() {
		return favoriteCount;
	}


	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}


	public int getAdoptCount() {
		return adoptCount;
	}


	public void setAdoptCount(int adoptCount) {
		this.adoptCount = adoptCount;
	}


	public int getLinereplyCount() {
		return linereplyCount;
	}


	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
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


	public String getExaminationComment() {
		return examinationComment;
	}


	public void setExaminationComment(String examinationComment) {
		this.examinationComment = examinationComment;
	}


	public String getPortalId() {
		return portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
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


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getJobTitleName() {
		return jobTitleName;
	}


	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getBestCount() {
		return bestCount;
	}


	public void setBestCount(int bestCount) {
		this.bestCount = bestCount;
	}


	public String getPathUrl() {
		return pathUrl;
	}


	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
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


	public String getListType() {
		return listType;
	}


	public void setListType(String listType) {
		this.listType = listType;
	}


	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}


	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}


	public List<FileData> getFileDataList() {
		return fileDataList;
	}


	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}


	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}


	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}


	public int getAttachFileCount() {
		return attachFileCount;
	}


	public void setAttachFileCount(int attachFileCount) {
		this.attachFileCount = attachFileCount;
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


	public String getDocIframe() {
		return docIframe;
	}


	public void setDocIframe(String docIframe) {
		this.docIframe = docIframe;
	}

	/**
	 * Gets the msie.
	 *
	 * @return the msie
	 */
	public Integer getMsie() {
		return this.msie;
	}
	
	/**
	 * Sets the msie.
	 *
	 * @param msie the new msie
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	public Integer getStep() {
		return step;
	}


	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getItemType() {
		return itemType;
	}


	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}


	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public List<IdIdea> getReplyItemThreadList() {
		return replyItemThreadList;
	}


	public void setReplyItemThreadList(List<IdIdea> replyItemThreadList) {
		this.replyItemThreadList = replyItemThreadList;
	}


	public String getOpenContestFlag() {
		return openContestFlag;
	}


	public void setOpenContestFlag(String openContestFlag) {
		this.openContestFlag = openContestFlag;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public Integer getContestCount() {
		return contestCount;
	}


	public void setContestCount(Integer contestCount) {
		this.contestCount = contestCount;
	}

  
}