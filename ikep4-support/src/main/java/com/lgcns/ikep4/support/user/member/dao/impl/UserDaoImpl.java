/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.UserDetail;


/**
 * 사용자 관리 DAO 구현
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserDaoImpl.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Repository("UserDao")
public class UserDaoImpl extends GenericDaoSqlmap<User, String> implements UserDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectAll()
	 */
	public List<User> selectAll(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.member.dao.User.selectAllBySearchCondition", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#countBySearchCondition
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySearchCondition(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.member.dao.User.countBySearchCondition", searchCondition);
	}

	
	public String selectAllChildGroupId(String groupId){
		
		return (String) sqlSelectForObject("support.user.member.dao.User.selectAllChildGroupId", groupId);
	}
	
	public List<User> getList(String userId){

		return sqlSelectForList("support.user.member.dao.User.getListUser" ,userId);

	}
	
	public List<User> listUserInfo(List<String> idList){
		return this.sqlSelectForList("support.user.member.dao.User.listUserInfo", idList);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public User get(String id) {

		return (User) sqlSelectForObject("support.user.member.dao.User.select", id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public User getUserByGroupId(Map<String, String> map) {

		return (User) sqlSelectForObject("support.user.member.dao.User.selectByGroupId", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.member.dao.User.checkId", id);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(User user) {

		return (String) sqlInsert("support.user.member.dao.User.insert", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(User user) {

		sqlUpdate("support.user.member.dao.User.update", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {

		sqlDelete("support.user.member.dao.User.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectTimezoneAll()
	 */
	@SuppressWarnings("rawtypes")
	public List selectTimezoneAll(String localeCode) {

		return sqlSelectForList("support.user.member.dao.User.selectTimezoneAll", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectNationAll(java.
	 * lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public List selectNationAll(String localeCode) {

		return sqlSelectForList("support.user.member.dao.User.selectNationAll", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectLocaleCodeAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectLocaleCodeAll(String localeCode) {

		return sqlSelectForList("support.user.member.dao.User.selectLocaleCodeAll", localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobClassAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectJobClassAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectJobClassAll", portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobRankAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectJobRankAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectJobRankAll", portalId);
	}
	
     /*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobPositionAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectJobPositionAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectJobPositionAll", portalId);
	}
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobPositionAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectWorkPlaceCodeAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectWorkPlaceAll", portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobTitleAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectJobTitleAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectJobTitleAll", portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobDutyAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectJobDutyAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectJobDutyAll", portalId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectCompanyCodeAll()
	 */
	@SuppressWarnings({ "rawtypes" })
	public List selectCompanyCodeAll(String portalId) {

		return sqlSelectForList("support.user.member.dao.User.selectCompanyCodeAll", portalId);
	}
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#addUserToGroup(com.lgcns
	 * .ikep4.support.user.member.model.User)
	 */
	public void addUserToGroup(User user) {

		sqlInsert("support.user.member.dao.User.addUserToGroup", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateRepresentGroup(
	 * com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateRepresentGroup(User user) {

		sqlUpdate("support.user.member.dao.User.updateRepresentGroup", user);
	}
	
	public void requestCertification(User user){
		sqlUpdate("support.user.member.dao.User.requestCertification", user);
	}
	
	public int getUserInfo(User user) {
		return (Integer) sqlSelectForObject("support.user.member.dao.User.getUserInfo", user);
	}
	
	public int getUserInfoCheck(User user) {
		return (Integer) sqlSelectForObject("support.user.member.dao.User.getUserInfoCheck", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateUserToGroup(com
	 * .lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateUserToGroup(User user) {

		sqlUpdate("support.user.member.dao.User.updateUserToGroup", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#removeUserFromGroup(java
	 * .lang.String)
	 */
	public void removeUserFromGroup(String id) {

		sqlDelete("support.user.member.dao.User.removeUserFromGroup", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#removeUserFromCertainGroup
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void removeUserFromCertainGroup(User user) {

		sqlDelete("support.user.member.dao.User.removeUserFromCertainGroup", user);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#deleteUserFromGroup(java
	 * .lang.String)
	 */
	public void deleteUserFromGroup(String userId) {

		sqlDelete("support.user.member.dao.User.deleteUserFromGroup", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#deleteUserFromRole(java
	 * .lang.String)
	 */
	public void deleteUserFromRole(String userId) {

		sqlDelete("support.user.member.dao.User.deleteUserFromRole", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#deleteUserFromSysPermission
	 * (java.lang.String)
	 */
	public void deleteUserFromSysPermission(String userId) {

		sqlDelete("support.user.member.dao.User.deleteUserFromSysPermission", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#deleteUserFromConPermission
	 * (java.lang.String)
	 */
	public void deleteUserFromConPermission(String userId) {

		sqlDelete("support.user.member.dao.User.deleteUserFromConPermission", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#deleteUserFromAbsence
	 * (java.lang.String)
	 */
	public void deleteUserFromAbsence(String userId) {

		sqlDelete("support.user.member.dao.User.deleteUserFromAbsence", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectAllForTree(java
	 * .util.Map)
	 */
	public List<User> selectAllForTree(Map<String, Object> map) {
		return sqlSelectForList("support.user.member.dao.User.selectAllForTree", map);
	}
			
	public List<User> selectAgentUserList(String roleName) {
		return sqlSelectForList("support.user.member.dao.User.selectAgentUserList", roleName);
	}
	
	public List<User> selectUserPwUpdateList(UserSearchCondition searchCondition){
		return sqlSelectForList("support.user.member.dao.User.selectUserPwUpdateList", searchCondition);
	}
	
	public List getWorkPlaceNameList() {
		return sqlSelectForListOfObject("support.user.member.dao.User.getWorkPlaceNameList");
	}
	
	public List listTeamCodes(String workPlaceName) {
		return this.sqlSelectForList("support.user.member.dao.User.listTeamCodes", workPlaceName);
	}

	public Integer countUserPwUpdateList(UserSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject("support.user.member.dao.User.countUserPwUpdateList", searchCondition);
	}
	
    	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectAllForTree(java
	 * .util.Map)
	 */
	public List<User> selectJobTitleUserForTree(Map<String, Object> map) {
		return sqlSelectForList("support.user.member.dao.User.selectJobTitleUserForTree", map);
	}
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectAllForTree(java
	 * .util.Map)
	 */
	public List<User> selectJobDutyUserForTree(Map<String, Object> map) {
		return sqlSelectForList("support.user.member.dao.User.selectJobDutyUserForTree", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectSearchForTree(java
	 * .util.Map)
	 */
	public List<User> selectSearchForTree(Map<String, Object> map) {
		return sqlSelectForList("support.user.member.dao.User.selectSearchForTree", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateProfile(com
	 * .lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateMyPsInfo(User profile) {
		sqlUpdate("support.user.member.dao.User.updateMyPsInfo", profile);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateProfile(com
	 * .lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfile(User profile) {
		sqlUpdate("support.user.member.dao.User.updateProfile", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateProfileStaus
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfileStaus(User profile) {
		sqlUpdate("support.user.member.dao.User.updateProfileStatus", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updatePictureId
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updatePictureId(User profile) {
		sqlUpdate("support.user.member.dao.User.updatePictureId", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateProfilePictureId
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfilePictureId(User profile) {
		sqlUpdate("support.user.member.dao.User.updateProfilePictureId", profile);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateProfilePicture
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfilePicture(User profile) {
		sqlUpdate("support.user.member.dao.User.updateProfilePicture", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateTwitterInfo
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateTwitterInfo(User profile) {
		sqlUpdate("support.user.member.dao.User.updateTwitterInfo", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateFacebookInfo
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateFacebookInfo(User profile) {
		sqlUpdate("support.user.member.dao.User.updateFacebookInfo", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateExportField
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateExportField(User profile) {
		sqlUpdate("support.user.member.dao.User.updateExportField", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#updateCurrentJob
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateCurrentJob(User profile) {
		sqlUpdate("support.user.member.dao.User.updateCurrentJob", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectJobCode(java.util
	 * .Map)
	 */
	public String selectJobCode(Map<String, String> param) {
		return (String) sqlSelectForObject("support.user.member.dao.User.selectJobCode", param);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectForPassword(com
	 * .lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public List<User> selectForPassword(UserSearchCondition searchCondition) {
		return sqlSelectForList("support.user.member.dao.User.selectForPassword", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateForPassword(com
	 * .lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateForPassword(User profile) {
		sqlUpdate("support.user.member.dao.User.updateForPassword", profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectGroupForUser(java
	 * .lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> selectGroupForUser(String userId) {

		return (List) sqlSelectForList("support.user.member.dao.User.selectGroupForUser", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#selectLeadingGroup(java
	 * .lang.String)
	 */
	public Group selectLeadingGroup(String userId) {
		return (Group) sqlSelectForObject("support.user.member.dao.User.selectLeadingGroup", userId);
	}
	
	public List<Group> selectLeadingGroupAll(String userId) {
		return (List) sqlSelectForList("support.user.member.dao.User.selectLeadingGroupAll", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#updateLeaderInfo(java
	 * .lang.String)
	 */
	public void updateLeaderInfo(String groupId) {

		sqlUpdate("support.user.member.dao.User.updateLeaderInfo", groupId);
	}
	
	/**
	 * 설문대상 사용자 목록
	 */
	public List<User> getTargetUser(String surveyId) {
		return  sqlSelectForList("support.user.member.dao.User.getTargetUser", surveyId);
	}
	
	public void updateSapUser(UserDetail userDetail){
		this.sqlInsert("support.user.member.dao.User.updateSapUser",  userDetail);
	}
	
	public void updateSapNewUser(UserDetail userDetail){
		this.sqlInsert("support.user.member.dao.User.updateSapNewUser",  userDetail);
	}
	
	public void updateEntryDate(){
		this.sqlInsert("support.user.member.dao.User.updateEntryDate",  "");
	}
	
	public User selectAdditionalInfo(String userId){
		
		return (User) sqlSelectForObject("support.user.member.dao.User.select", userId);
	}
	
	public void updateMoorimUser(User user)
	{
		sqlUpdate("support.user.member.dao.User.updateMoorimUser", user);
	}
	
	public String updateEpUserTableFromTmpUserTable()
	{
		return (String) sqlSelectForObject("support.user.member.dao.User.updateEpUserTabeFromTmpUserTable", "");
	}
		
	public String updateUserMenuAcl()
	{
		return (String) sqlSelectForObject("support.user.member.dao.User.updateUserMenuAcl", "");
	}
	
	public void updatePublicAddressbook()
	{
		sqlSelectForObject("support.user.member.dao.User.updatePublicAddressbook");
	}
	
	public String readJobCondition(String jobName){
		return (String) sqlSelectForObject("support.user.member.dao.User.readJobCondition", jobName);
	}
	
	/**
	 * 팀 유저 리스트
	 * @param groupId
	 * @return
	 */
	public List<User> listTeamUser(String groupId) {
		return sqlSelectForList("support.user.member.dao.User.listTeamUser", groupId);
	}
	
	public List<User> listTeamLeader(String groupId) {
		return sqlSelectForList("support.user.member.dao.User.listTeamLeader", groupId);
	}
	
	public Object empNoToUserId(String empNo){
		return sqlSelectForObject("support.user.member.dao.User.empNoToUserId", empNo);
	}	
	
	public Object getKmsUserGroup(String userId){
		return sqlSelectForObject("support.user.member.dao.User.getKmsUserGroup", userId);
	}
	
	public Object empNoToUserInfo(String empNo){
		return sqlSelectForObject("support.user.member.dao.User.empNoToUserInfo", empNo);
	}	
	
	public Object getRoleId(String userId){
		return sqlSelectForObject("support.user.member.dao.User.getRoleId", userId);
	}	
	
	 public int getUserRoleCheck(Map<String, String> map) {
		return (Integer) sqlSelectForObject("support.user.member.dao.User.getRoleCheck", map);
	}
	 
	 public void loginLogInput(String userId){
		 sqlUpdate("support.user.member.dao.User.loginLogInput", userId);
	 }
	 
	 public void deleteTmpUser(String tmp){
		sqlDelete("support.user.member.dao.User.deleteTmpUser", tmp);
	}
	 
	 public void deleteRoleUser(Map<String, String> map){
			sqlDelete("support.user.member.dao.User.deleteRoleUser", map);
	}
	 
	 public void insertRoleUser(Map<String, String> map) {
		sqlInsert("support.user.member.dao.User.insertRoleUser", map);
	}
	 
	 public void deleteTmpNewUser(String tmp){
			sqlDelete("support.user.member.dao.User.deleteTmpNewUser", tmp);
	}
	 
	 /*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#countBySelectGroupList
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySelectGroupList(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.member.dao.User.countBySelectGroupList", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectAll()
	 */
	public List<User> selectGroupList(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.member.dao.User.selectGroupList", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#countBySelectGroupListForTreeMobile
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySelectGroupListForTreeMobile(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.member.dao.User.countBySelectGroupListForTreeMobile", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectAll()
	 */
	public List<User> selectGroupListForTreeMobile(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.member.dao.User.selectGroupListForTreeMobile", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectAllUser()
	 */
	public List<User> selectAllUser(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.member.dao.User.selectAllUserBySearchCondition", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#countBySearchConditionAll
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySearchConditionAll(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.member.dao.User.countBySearchConditionAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.dao.UserDao#selectAll()
	 */
	public List<User> selectAllForMobile(UserSearchCondition searchCondition) {

		return sqlSelectForList("support.user.member.dao.User.selectAllBySearchConditionForMobile", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.dao.UserDao#countBySearchCondition
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public Integer countBySearchConditionForMobile(UserSearchCondition searchCondition) {
		return (Integer) this
				.sqlSelectForObject("support.user.member.dao.User.countBySearchConditionForMobile", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public User getMobileUser(String mobile) {

		return (User) sqlSelectForObject("support.user.member.dao.User.selectMobile", mobile);
	}
	
	public User searchUser(UserSearchCondition userSearchCondition){
		return (User) sqlSelectForObject("support.user.member.dao.User.searchUser", userSearchCondition);
	}

	public void executeMappingDB() {
		sqlUpdate("support.user.member.dao.User.executeMappingDB");
	}
	
	public List<User> selectRoleUser(String roleName){
		return sqlSelectForList("support.user.member.dao.User.selectRoleUser", roleName);
	}
	
	public List<User> selectOfficeRoleUsers(String roleName){
			return sqlSelectForList("support.user.member.dao.User.selectOfficeRoleUsers2");
	}
	
	public List<User> selectOfficeRoleUser(Map<String, String> map){
		return sqlSelectForList("support.user.member.dao.User.selectOfficeRoleUser", map);
	}
}
