package com.lgcns.ikep4.collpack.kms.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Category;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

public interface AdminPermissionService extends GenericService<Object, String> {
	
	/**
	 * 사원정보 목록
	 * @param AdminPermission 조건문
	 */
	public SearchResult<AdminPermission> getUserList(KmsAdminSearchCondition searchCondition);
	
	public SearchResult<AdminPermission> getUserRecommendReplyList(KmsAdminSearchCondition searchCondition);
	
	public SearchResult<AdminPermission> getCompulsionTimeLogList(KmsAdminSearchCondition searchCondition);
	/**
	 * 팀리더권한 목록
	 * @param AdminPermission 조건문
	 */
	public SearchResult<AdminPermission> getLeaderPermissionList(KmsAdminSearchCondition searchCondition);
	
	
	/**
	 * 사원정보
	 * @param userId
	 */
	public AdminPermission getUser(String userId);
	
	
	/**
	 * 사원정보 수정
	 * @param userId
	 */
	public void updateUser(AdminPermission adminPermission);

	public List getWorkPlaceNameList();
	
	public List<AdminPermission> getPeriodList();
	
	public AdminPermission getUserPermPopup(KmsAdminSearchCondition searchCondition);
	
	public AdminPermission getSpecialUserInfo(KmsAdminSearchCondition searchCondition);
	
	public AdminPermission getKeyInfoPermissionUser(KmsAdminSearchCondition searchCondition);

	public void updateUserCnt(Map<String, String> paramMap);

	public SearchResult<AdminTeamLeader> getTeamByLeaderPermissionList(KmsAdminSearchCondition searchCondition);
	
	public void insertCategoryNm(List<AdminPermission> receiveCategoryNmList) ;
	
	public void insertCategoryUsers(String categoryUsers, User user) ;

	public void deleteTeamByLeader(String userId, String teamCodes);

	public String listTeamCodes(String workPlaceName, String isTitle);

	public SearchResult<AdminTeamLeader> getTeamCntByWorkPlaceList(KmsAdminSearchCondition searchCondition);

	public void addTeamByLeader(AdminTeamLeader adminTeamLeader);
	
	public void registTeamPermission();
	
	public void registObligationCnt();

	public List<AdminPermission> listCategory(AdminPermission categoryBoardId);
	
	public List<AdminPermission> listCategoryUser(AdminPermission categoryBoardId);
	
	public List<AdminPermission> listSpecialUser();
	
	public List<AdminPermission> listKeyInfoPermission();
	
	public void insertSpecialUser(AdminPermission specialUser);
	
	public void insertKeyInfoPermission(AdminPermission keyInfoPermission);
	
	public void insertNewKeyInfoPermission();
	
	public void deleteSpecialUser(String userIds,String categoryIds);
	
	public void deleteKeyInfoPermission(String userIds);
	
	public void insertMessage(AdminPermission category) ;
	
	public void saveCompulsionTimeSetting(CompulsionTime compulsionTime) ;
	
	public CompulsionTime selectCompulsionTimeSetting();

	public Integer selectCompulsionTimeClickCheck(String checkFlg, String userId);
	
	public void compulsionTimeClickSave(CompulsionTime compulsionTime) ;	
	
}
