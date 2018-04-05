/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Knowledge Monitor KnowledgeMonitorModuleBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorModuleBody extends KnowledgeMonitorModulePK {

	/**
	 *
	 */
	private static final long serialVersionUID = 4261274706354022264L;

	/**
	 * 모듈 명
	 */
	private String moduleName;

	/**
	 * 1: 관리대상 모듈, 0: 관리대상 모듈 아님
	 */
	private int isUsage;

	/**
	 * 조회 가중치 (단위:%)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=0,max=1000, groups=ContentPolicyUpdate.class)
	private int hitWeight;

	/**
	 * 추천 가중치 (단위:%)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=0,max=1000, groups=ContentPolicyUpdate.class)
	private int recommendWeight;

	/**
	 * 댓글 가중치 (단위:%)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=0,max=1000, groups=ContentPolicyUpdate.class)
	private int linereplyWeight;

	/**
	 * Favorite 가중치 (단위:%)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=0,max=1000, groups=ContentPolicyUpdate.class)
	private int favoriteWeight;

	/**
	 * 배포 가중치 (단위:%)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=0,max=1000, groups=ContentPolicyUpdate.class)
	private int distributeWeight;

	/**
	 * 우수자료 기준 CVI점수 (단위:점)
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=1,max=999999999, groups=ContentPolicyUpdate.class)
	private int goodDocCutline;

	/**
	 * 우수 댓글 커트라인(댓글 수)
	 */
	@NotEmpty(groups=CommentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=CommentPolicyUpdate.class)
	@Size(min=1,max=1000, groups=CommentPolicyUpdate.class)
	private int goodLinereplyCutline;

	/**
	 * CVI 평가기간 (단위:월) 지정된 개월 수 이전 까지의 자료에 대해서만 CVI 정보 update
	 */
	@NotEmpty(groups=ContentPolicyUpdate.class)
	@Pattern(regexp="[0-9]", groups=ContentPolicyUpdate.class)
	@Size(min=1,max=100, groups=ContentPolicyUpdate.class)
	private int cviEvaluationTerm;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록
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
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the isUsage
	 */
	public int getIsUsage() {
		return isUsage;
	}

	/**
	 * @param isUsage the isUsage to set
	 */
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the hitWeight
	 */
	public int getHitWeight() {
		return hitWeight;
	}

	/**
	 * @param hitWeight the hitWeight to set
	 */
	public void setHitWeight(int hitWeight) {
		this.hitWeight = hitWeight;
	}

	/**
	 * @return the recommendWeight
	 */
	public int getRecommendWeight() {
		return recommendWeight;
	}

	/**
	 * @param recommendWeight the recommendWeight to set
	 */
	public void setRecommendWeight(int recommendWeight) {
		this.recommendWeight = recommendWeight;
	}

	/**
	 * @return the linereplyWeight
	 */
	public int getLinereplyWeight() {
		return linereplyWeight;
	}

	/**
	 * @param linereplyWeight the linereplyWeight to set
	 */
	public void setLinereplyWeight(int linereplyWeight) {
		this.linereplyWeight = linereplyWeight;
	}

	/**
	 * @return the favoriteWeight
	 */
	public int getFavoriteWeight() {
		return favoriteWeight;
	}

	/**
	 * @param favoriteWeight the favoriteWeight to set
	 */
	public void setFavoriteWeight(int favoriteWeight) {
		this.favoriteWeight = favoriteWeight;
	}

	/**
	 * @return the distributeWeight
	 */
	public int getDistributeWeight() {
		return distributeWeight;
	}

	/**
	 * @param distributeWeight the distributeWeight to set
	 */
	public void setDistributeWeight(int distributeWeight) {
		this.distributeWeight = distributeWeight;
	}

	/**
	 * @return the goodDocCutline
	 */
	public int getGoodDocCutline() {
		return goodDocCutline;
	}

	/**
	 * @param goodDocCutline the goodDocCutline to set
	 */
	public void setGoodDocCutline(int goodDocCutline) {
		this.goodDocCutline = goodDocCutline;
	}

	/**
	 * @return the goodLinereplyCutline
	 */
	public int getGoodLinereplyCutline() {
		return goodLinereplyCutline;
	}

	/**
	 * @param goodLinereplyCutline the goodLinereplyCutline to set
	 */
	public void setGoodLinereplyCutline(int goodLinereplyCutline) {
		this.goodLinereplyCutline = goodLinereplyCutline;
	}

	/**
	 * @return the cviEvaluationTerm
	 */
	public int getCviEvaluationTerm() {
		return cviEvaluationTerm;
	}

	/**
	 * @param cviEvaluationTerm the cviEvaluationTerm to set
	 */
	public void setCviEvaluationTerm(int cviEvaluationTerm) {
		this.cviEvaluationTerm = cviEvaluationTerm;
	}

}
