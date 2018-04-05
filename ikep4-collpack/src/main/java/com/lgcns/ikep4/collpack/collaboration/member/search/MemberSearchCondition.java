package com.lgcns.ikep4.collpack.collaboration.member.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class MemberSearchCondition extends SearchCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6031368888140196449L;

	/**
	 * 포탈ID
	 */
	private String portalId;
	/**
	 * 목록 타입
	 */
	private String listType;

	/**
	 * 워크스페이스 ID
	 */
	private String workspaceId;

	/**
	 * 워크스페이스 상태(WO:개설대기, O:개설, WC:폐쇄대기, C:폐쇄)
	 */
	private String workspaceStatus;

	/**
	 * 워크스페이스 타입(O:조직, C:Cop, T:TFT, I:Informal)
	 */
	private String typeId;
	/**
	 * 멤버 레벨
	 */
	private String memberLevel;
	/**
	 * 저장 타입 ( 내용 수정 : modify, 승인:join, 등급 :level, 운영진 :manage, 디폴트 : defaultSet, 디폴트 초기화 :defautInit )
	 */
	private String updateType;
	
	/**
	 * 멤버조회용 : 멤버(시샵제외):member,전체:all,가입신청:associate
	 */
	private String memberType;
	
	/**
	 * 멤버ID
	 */
	private String memberId;
	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * Search 컬럼
	 */
	private String searchColumn;

	/**
	 * Search Word
	 */
	private String searchWord;

	private boolean init = Boolean.TRUE;

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the workspaceStatus
	 */
	public String getWorkspaceStatus() {
		return workspaceStatus;
	}

	/**
	 * @param workspaceStatus the workspaceStatus to set
	 */
	public void setWorkspaceStatus(String workspaceStatus) {
		this.workspaceStatus = workspaceStatus;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}

	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @return the init
	 */
	public boolean isInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public void setInit(boolean init) {
		this.init = init;
	}

	/**
	 * @return the updateType
	 */
	public String getUpdateType() {
		return updateType;
	}

	/**
	 * @param updateType the updateType to set
	 */
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}