package com.lgcns.ikep4.collpack.kms.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.addressbook.model.Category;

@Repository("AdminPermissionDao")
public class AdminPermissionDaoImpl extends GenericDaoSqlmap<Object, String> implements AdminPermissionDao {

	private static final String NAMESPACE = "collpack.kms.admin.dao.AdminPermission.";
	
	public String create(AdminPermission arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public AdminPermission get(String empNo) {
		return (AdminPermission)sqlSelectForObject(NAMESPACE + "get", empNo);
		//return null;
	}
	
	public List getUserList(KmsAdminSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getUserList", searchCondition);
	}
	
	public List getUserRecommendReplyList(KmsAdminSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getUserRecommendReplyList", searchCondition);
	}
	
	public List getCompulsionTimeLogList(KmsAdminSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "getCompulsionTimeLogList", searchCondition);
	}
	
	public List getLeaderPermissionList(KmsAdminSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getLeaderPermissionList", searchCondition);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(AdminPermission arg0) {
		// TODO Auto-generated method stub

	}

	public List getWorkPlaceNameList() {
		
		return sqlSelectForListOfObject(NAMESPACE + "getWorkPlaceNameList");
	}
	
	public List getPeriodList() {
		return sqlSelectForList(NAMESPACE + "getPeriodList");
	}

	public Integer countBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	public Integer countByRecommendReplySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByRecommendReplySearchCondition", searchCondition);
	}
	
	public Integer countByCompulsionTimeLogSearchCondition(KmsAdminSearchCondition searchCondition){
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByCompulsionTimeLogSearchCondition", searchCondition);
	}

	public Integer countLeaderPermBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countLeaderPermBySearchCondition", searchCondition);
	}
	

	public String getObliCntByTeam(KmsAdminSearchCondition searchCondition) {
		return (String)this.sqlSelectForObject(NAMESPACE + "getObliCntByTeam", searchCondition);
	}

	public AdminPermission getUserPermInfo(KmsAdminSearchCondition searchCondition) {
		return (AdminPermission)this.sqlSelectForObject(NAMESPACE + "getUserPermInfo", searchCondition);	
		
	}
	
	public void insertUserCnt(KmsAdminSearchCondition searchCondition){
		this.sqlInsert(NAMESPACE + "insertUserCnt", searchCondition);
	}
	
	public AdminPermission getSpecialUserInfo(KmsAdminSearchCondition searchCondition){
		return (AdminPermission)this.sqlSelectForObject(NAMESPACE + "getSpecialUserInfo", searchCondition);	
	}
	
	public AdminPermission getKeyInfoPermissionUser(KmsAdminSearchCondition searchCondition){
		return (AdminPermission)this.sqlSelectForObject(NAMESPACE + "getKeyInfoPermissionUser", searchCondition);	
	}

	public void updateUserCnt(Map<String, String> paramMap) {
		this.sqlUpdate(NAMESPACE + "updateUserCnt", paramMap); 
	}

	public List getDivisionHierachy() {		
		return this.sqlSelectForListOfObject(NAMESPACE + "getDivisionHierachy");
	}

	public void insertTeamLeader(Map<String, String> paramMap) {
		this.sqlInsert(NAMESPACE + "insertTeamLeader", paramMap);
	}
	
	public void insertObligationCnt() {
		this.sqlInsert(NAMESPACE + "insertObligationCnt");
	}

	public Integer countTeamByLeaderPermBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamByLeaderPermBySearchCondition", searchCondition);
	}
	
	public List getTeamByLeaderPermList(KmsAdminSearchCondition searchCondition){
		return this.sqlSelectForListOfObject(NAMESPACE + "getTeamByLeaderPermList", searchCondition);
	}

	public List getLeaderIdList() {
		return this.sqlSelectForListOfObject(NAMESPACE + "getLeaderIdList");		
	}

	public List getDivisionTree(String groupId) {
		return this.sqlSelectForListOfObject(NAMESPACE + "getDivisionTree", groupId);
	}

	public Integer getLeaderAndTeam(Map<String, String> paramMap) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "getLeaderAndTeam", paramMap);
	}

	public String create(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	public AdminPermission getUserInfo(String userId) {
		return (AdminPermission)this.sqlSelectForObject(NAMESPACE + "getUserInfo", userId);
	}

	public void deleteTeamByLeader(AdminTeamLeader adminTeamLeader) {
		this.sqlDelete(NAMESPACE + "deleteTeamByLeader", adminTeamLeader);
		
	}

	public List listTeamCodes(String workPlaceName) {
		return this.sqlSelectForList(NAMESPACE + "listTeamCodes", workPlaceName);
	}

	public Integer countTeamCntByWorkPlacBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamCntByWorkPlacBySearchCondition", searchCondition);
	}

	public void insertTeamByLeader(AdminTeamLeader adminTeamLeader) {
		this.sqlInsert(NAMESPACE +"insertTeamByLeader", adminTeamLeader);
		
	}

	public List getTeamCntByWorkPlaceList(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getTeamCntByWorkPlaceList", searchCondition);
	}
	
	public List getUnregistTeamLeader() {
		return this.sqlSelectForList(NAMESPACE + "getUnregistTeamLeader");
	}
	
	public List listCategory(AdminPermission categoryBoardId) {
		return this.sqlSelectForList(NAMESPACE + "listCategory",categoryBoardId);
	}
	
	public List listCategoryUser(AdminPermission categoryBoardId) {
		return this.sqlSelectForList(NAMESPACE + "listCategoryUser",categoryBoardId);
	}
	
	public List listSpecialUser() {
		return this.sqlSelectForList(NAMESPACE + "listSpecialUser");
	}
	
	public List listKeyInfoPermission() {
		return this.sqlSelectForList(NAMESPACE + "listKeyInfoPermission");
	}
	
	public void createCategoryNm(AdminPermission category) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",category);
	}
	
	public void createCategoryUsers(AdminPermission category) {
		this.sqlInsert(NAMESPACE+"createCategoryUsers",category);
	}
	
	public void deleteCategoryNm(AdminPermission category) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",category);
	}
	
	public void deleteSpecialUser(AdminPermission specialUser){
		this.sqlUpdate(NAMESPACE+"deleteSpecialUser",specialUser);
	}
	
	public void deleteKeyInfoPermission(AdminPermission keyInfoPermission){
		this.sqlUpdate(NAMESPACE+"deleteKeyInfoPermission",keyInfoPermission);
	}
	
	public void deleteCategoryUsers() {
		this.sqlUpdate(NAMESPACE+"deleteCategoryUsers","");
	}
	
	public void deleteMessage() {
		this.sqlUpdate(NAMESPACE+"deleteMessage","");
	}
	
	public void createMessage(AdminPermission category) {
		this.sqlInsert(NAMESPACE+"createMessage",category);
	}
	
	public void updateCategoryNm(AdminPermission category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",category);
	}
	
	public void updateCategoryAlign(AdminPermission category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",category);
	}
	
	public void insertSpecialUser(AdminPermission specialUser){
		this.sqlUpdate(NAMESPACE+"insertSpecialUser",specialUser);
	}
	
	public void insertKeyInfoPermission(AdminPermission keyInfoPermission){
		this.sqlUpdate(NAMESPACE+"insertKeyInfoPermission",keyInfoPermission);
	}
	
	public void saveCompulsionTimeSetting(CompulsionTime compulsionTime){
		this.sqlUpdate(NAMESPACE+"saveCompulsionTimeSetting",compulsionTime);
	}
	
	public void compulsionTimeClickSave(CompulsionTime compulsionTime){
		this.sqlInsert(NAMESPACE+"compulsionTimeClickSave",compulsionTime);
	}
	
	public void insertNewKeyInfoPermission(){
		this.sqlUpdate(NAMESPACE+"insertNewKeyInfoPermission");
	}
	
	public CompulsionTime selectCompulsionTimeSetting(){
		return (CompulsionTime)this.sqlSelectForObject(NAMESPACE + "selectCompulsionTimeSetting");	
	}
	
	public Integer selectCompulsionTimeClickCheck(CompulsionTime compulsionTime){
		return (Integer)this.sqlSelectForObject(NAMESPACE + "selectCompulsionTimeClickCheck",compulsionTime);
	}

}
