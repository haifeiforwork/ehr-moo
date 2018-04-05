package com.lgcns.ikep4.collpack.kms.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.addressbook.model.Category;

public interface AdminPermissionDao extends GenericDao<Object, String> {
	
		
	public List<AdminPermission> getUserList(KmsAdminSearchCondition searchCondition);
	
	public List<AdminPermission> getUserRecommendReplyList(KmsAdminSearchCondition searchCondition);
	
	public List<AdminPermission> getCompulsionTimeLogList(KmsAdminSearchCondition searchCondition);
	
	public List<AdminPermission> getLeaderPermissionList(KmsAdminSearchCondition searchCondition);

	public List getWorkPlaceNameList();
	
	public List<AdminPermission> getPeriodList();

	public Integer countBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	public Integer countByRecommendReplySearchCondition(KmsAdminSearchCondition searchCondition);
	
	public Integer countByCompulsionTimeLogSearchCondition(KmsAdminSearchCondition searchCondition);
	
	public Integer countLeaderPermBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	public List<AdminTeamLeader> getTeamByLeaderPermList(KmsAdminSearchCondition searchCondition);

	public AdminPermission getUserPermInfo(KmsAdminSearchCondition searchCondition);
	
	public void insertUserCnt(KmsAdminSearchCondition searchCondition);
	
	public AdminPermission getSpecialUserInfo(KmsAdminSearchCondition searchCondition);
	
	public AdminPermission getKeyInfoPermissionUser(KmsAdminSearchCondition searchCondition);
	
	public String getObliCntByTeam(KmsAdminSearchCondition searchCondition) ;

	public void updateUserCnt(Map<String, String> paramMap);
	
	public void insertTeamLeader(Map<String,String> paramMap);
	
	public void insertObligationCnt();

	public Integer countTeamByLeaderPermBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	public List getLeaderIdList();
	
	public List getDivisionTree(String groupId);
	
	public Integer getLeaderAndTeam(Map<String,String> paramMap);
	
	public AdminPermission getUserInfo(String userId);

	public void deleteTeamByLeader(AdminTeamLeader adminTeamLeader);

	public List listTeamCodes(String workPlaceName);

	public Integer countTeamCntByWorkPlacBySearchCondition(KmsAdminSearchCondition searchCondition);

	public void insertTeamByLeader(AdminTeamLeader adminTeamLeader);

	public List getTeamCntByWorkPlaceList(KmsAdminSearchCondition searchCondition);
	
	public List getUnregistTeamLeader();
	
	public List<AdminPermission> listCategory(AdminPermission categoryBoardId);
	
	public List<AdminPermission> listCategoryUser(AdminPermission categoryBoardId);
	
	public List<AdminPermission> listSpecialUser();
	
	public List<AdminPermission> listKeyInfoPermission();
	
	public void createCategoryNm(AdminPermission category);
	
	public void createCategoryUsers(AdminPermission category);
	
	public void updateCategoryNm(AdminPermission category);
	
	public void deleteCategoryNm(AdminPermission category);
	
	public void deleteSpecialUser(AdminPermission specialUser);
	
	public void deleteKeyInfoPermission(AdminPermission keyInfoPermission);
	
	public void deleteCategoryUsers();
	
	public void deleteMessage();
	
	public void createMessage(AdminPermission category);
	
	public void updateCategoryAlign(AdminPermission category);
	
	public void insertSpecialUser(AdminPermission specialUser);
	
	public void insertKeyInfoPermission(AdminPermission keyInfoPermission);
	
	public void insertNewKeyInfoPermission();
	
	public void saveCompulsionTimeSetting(CompulsionTime compulsionTime);
	
	public CompulsionTime selectCompulsionTimeSetting();
	
	public Integer selectCompulsionTimeClickCheck(CompulsionTime compulsionTime);

	public void compulsionTimeClickSave(CompulsionTime compulsionTime);
}
