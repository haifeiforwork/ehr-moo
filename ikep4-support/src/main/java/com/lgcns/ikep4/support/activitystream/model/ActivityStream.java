package com.lgcns.ikep4.support.activitystream.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * ActivityStream
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStream.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ActivityStream extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6671338906598704505L;

	/**
	 * id
	 */
	private String activityStreamId;

	/**
	 * item type code
	 */
	private String itemTypeCode;

	/**
	 * item type 명
	 */
	private String itemTypeName;

	/**
	 * activity code
	 */
	private String activityCode;

	/**
	 * activity 명
	 */
	private String activityName;

	/**
	 * activity 설명
	 */
	private String activityDescription;

	/**
	 * activity 영문 설명
	 */
	private String activityEnglishDescription;

	/**
	 * 처리자 
	 */
	private String actorId;

	/**
	 * 처리자명
	 */
	private String actorName;
	
	/**
	 * 처리자 영문명
	 */
	private String actorEnglishName;

	/**
	 * 대상자
	 */
	private String targetId;

	/**
	 * 대상자명
	 */
	private String targetName;
	
	/**
	 * 대상자 영문명
	 */
	private String targetEnglishName;

	/**
	 * object item id
	 */
	private String objectId;

	/**
	 * object item title
	 */
	private String objectTitle;

	/**
	 * object item url
	 */
	private String objectUrl;

	/**
	 * object item type
	 */
	private String objectType;

	/**
	 * 대상 갯수
	 */
	private int targetCount;

	/**
	 * 처리일
	 */
	private Date activityDate;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 장소
	 */
	private String placeId;
	
	/**
	 * 장소명
	 */
	private String placeName;

	public String getActivityStreamId() {
		return activityStreamId;
	}

	public void setActivityStreamId(String activityStreamId) {
		this.activityStreamId = activityStreamId;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getActivityEnglishDescription() {
		return activityEnglishDescription;
	}

	public void setActivityEnglishDescription(String activityEnglishDescription) {
		this.activityEnglishDescription = activityEnglishDescription;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectUrl() {
		return objectUrl;
	}

	public void setObjectUrl(String objectUrl) {
		this.objectUrl = objectUrl;
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

	public String getObjectTitle() {
		return objectTitle;
	}

	public void setObjectTitle(String objectTitle) {
		this.objectTitle = objectTitle;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public String getActorEnglishName() {
		return actorEnglishName;
	}

	public void setActorEnglishName(String actorEnglishName) {
		this.actorEnglishName = actorEnglishName;
	}

	public String getTargetEnglishName() {
		return targetEnglishName;
	}

	public void setTargetEnglishName(String targetEnglishName) {
		this.targetEnglishName = targetEnglishName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	

}
