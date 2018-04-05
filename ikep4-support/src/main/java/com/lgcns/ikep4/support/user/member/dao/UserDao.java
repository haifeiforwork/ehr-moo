/* 
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.member.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.UserDetail;


/**
 * 사용자 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserDao.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
public interface UserDao extends GenericDao<User, String> {

	/**
	 * 모든 사용자 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectAll(UserSearchCondition searchCondition);

	/**
	 * 모든 사용자 정보 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UserSearchCondition searchCondition);

	
	public String selectAllChildGroupId(String groupId);
	
	public List<User> getList(String userId);
	
	public List<User> listUserInfo(List<String> idList);
	
	
	/**
	 * Timezone Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectTimezoneAll(String localeCode);

	/**
	 * Locale Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectLocaleCodeAll(String localeCode);

	/**
	 * 국가 Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectNationAll(String localeCode);

	/**
	 * JobClass Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobClassAll(String portalId);

	/**
	 * JobRank code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobRankAll(String portalId);

    /**
	 * Jobposition Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobPositionAll(String portalId);
    
	
	/**
	 * 사업장 Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectWorkPlaceCodeAll(String portalId);

	/**
	 * Jobtitle code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobTitleAll(String portalId);

	/**
	 * Jobduty code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobDutyAll(String portalId);
	
	/**
	 * Company code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectCompanyCodeAll(String portalId);	
	
	
	/**
	 * 사용자 추가시 그룹정보에도 추가함
	 * 
	 * @param user 추가할 사용자 객체
	 */
	public void addUserToGroup(User user);

	/**
	 * 사용자의 대표그룹을 업데이트(수정/생성시)
	 * 
	 * @param user 대표그룹을 업데이트할 사용자 객체
	 */
	public void updateRepresentGroup(User user);
	
	public void requestCertification(User user);
	
	public int getUserInfo(User user);
	
	public int getUserInfoCheck(User user);

	/**
	 * 사용자 정보 수정시 그룹정보도 수정
	 * 
	 * @param user 그룹 정보를 업데이트할 사용자 객체
	 */
	public void updateUserToGroup(User user);

	/**
	 * 사용자 정보 삭제 시 그룹정보에서도 삭제함
	 * 
	 * @param id 삭제할 그룹 ID
	 */
	public void removeUserFromGroup(String id);

	/**
	 * USER_GROUP에서 특정그룹과 사용자 아이디 매핑 정보 삭제
	 * 
	 * @param user 매핑 정보를 삭제할 사용자 객체
	 */
	public void removeUserFromCertainGroup(User user);

	/**
	 * user_group에서 특정 user_id로 전체 매핑 정보 삭제(user delete 에서 사용)
	 * 
	 * @param userId 매핑 정보를 삭제할 사용자 ID
	 */
	public void deleteUserFromGroup(String userId);

	/**
	 * user_role에서 특정 user_id로 전체 매핑 정보 삭제(user delete 에서 사용)
	 * 
	 * @param userId 매핑 정보를 삭제할 사용자 ID
	 */
	public void deleteUserFromRole(String userId);

	/**
	 * user_sys_permission에서 특정 user_id로 전체 매핑 정보 삭제(user delete 에서 사용)
	 * 
	 * @param userId 매핑 정보를 삭제할 사용자 ID
	 */
	public void deleteUserFromSysPermission(String userId);

	/**
	 * user_con_permission에서 특정 user_id로 전체 매핑 정보 삭제(user delete 에서 사용)
	 * 
	 * @param userId 매핑 정보를 삭제할 사용자 ID
	 */
	public void deleteUserFromConPermission(String userId);

	/**
	 * user_absence에서 특정 user_id로 전체 매핑 정보 삭제(user delete 에서 사용)
	 * 
	 * @param userId 매핑 정보를 삭제할 사용자 ID
	 */
	public void deleteUserFromAbsence(String userId);

	/**
	 * 조직도용 사용자 조회
	 * 
	 * @param map 검색조건
	 * @return
	 */
	public List<User> selectAllForTree(Map<String, Object> map);
	
	public List<User> selectAgentUserList(String roleName);

	public List<User> selectUserPwUpdateList(UserSearchCondition searchCondition);
	
	public List getWorkPlaceNameList();
	
	public List listTeamCodes(String workPlaceName);
	
	public Integer countUserPwUpdateList(UserSearchCondition searchCondition);
/**
	 * 조직도용 사용자 조회
	 * 
	 * @param map 검색조건
	 * @return
	 */
	public List<User> selectJobTitleUserForTree(Map<String, Object> map);
	
	/**
	 * 조직도용 사용자 조회
	 * 
	 * @param map 검색조건
	 * @return
	 */
	public List<User> selectJobDutyUserForTree(Map<String, Object> map);
	/**
	 * 조직도용 사용자 검색 조회
	 * 
	 * @param map 검색 조건
	 * @return
	 */
	public List<User> selectSearchForTree(Map<String, Object> map);
	
	/**
	 * 자신의 프로파일에서 정보 업데이트 하기 위한 부분
	 * 
	 * @param profile
	 */
	public void updateMyPsInfo(User profile);

	/**
	 * 자신의 프로파일에서 정보 업데이트 하기 위한 부분
	 * 
	 * @param profile
	 */
	public void updateProfile(User profile);

	/**
	 * 프로파일에서 요즘은 (프로파일 대상자 현재 상태 )을 업데이트 한다. PROFILE_STATUS
	 * 
	 * @param profile
	 */
	public void updateProfileStaus(User profile);

	/**
	 * 프로파일의 사진을 등록하기 위해 이미지 ID를 업데이트 한다. IKEP4_EV_USER.PICTURE_ID
	 * 
	 * @param profile
	 */
	public void updatePictureId(User profile);

	/**
	 * 프로파일의 사진을 등록하기 위해 이미지 ID를 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateProfilePictureId(User profile);
	
	/**
	 * 프로파일의 사진 경로를 등록한다.
	 * 
	 * @param profile
	 */
	public void updateProfilePicture(User profile);

	/**
	 * 트위트 계정과 인증 정보를 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateTwitterInfo(User profile);

	/**
	 * 페이스북 계정과 인증 정보를 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateFacebookInfo(User profile);

	/**
	 * 프로파일에서 자신의 전문가 정보의 상세 정보를 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateExportField(User profile);

	/**
	 * 프로파일에서 자신의 Current Job 상세 정보를 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateCurrentJob(User profile);

	/**
	 * 코드명을 기준으로 코드값을 가져오는 함수
	 * 
	 * @param param
	 * @return
	 */
	public String selectJobCode(Map<String, String> param);

	/**
	 * 패스워드 초기화를 위해서 사용자 리스트 가져오기
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectForPassword(UserSearchCondition searchCondition);

	/**
	 * 패스워드 초기화 하기
	 * 
	 * @param profile 초기화할 사용자 정보
	 */
	public void updateForPassword(User profile);

	/**
	 * 해당 사용자가 속한 그룹 목록을 가져옴
	 * 
	 * @param userId 사용자 ID
	 * @return
	 */
	public List<Map<String, String>> selectGroupForUser(String userId);

	/**
	 * 해당 사용자가 리더로 있는 그룹 정보를 가져옴 : 단일 그룹
	 * 
	 * @param userId 사용자 ID
	 * @return
	 */
	public Group selectLeadingGroup(String userId);
	
	/**
	 * 해당 사용자가 리더로 있는 그룹 정보를 가져옴
	 * 
	 * @param userId 사용자 ID
	 * @return
	 */
	public List<Group> selectLeadingGroupAll(String userId);

	/**
	 * 해당 그룹의 리더 정보를 삭제함
	 * (사용자가 삭제되는 경우에 그 사용자가 리더라면 수행됨)
	 * 
	 * @param groupId 그룹 ID
	 */
	public void updateLeaderInfo(String groupId);
	
	/**
	 * 설문대상 사용자 목록
	 * @param surveyId
	 * @return
	 */
	public List<User> getTargetUser(String surveyId);

	/**
	 * 아이디, 그룹아이디로 사용자 기본 조회
	 * @param map
	 * @return
	 */
	public User getUserByGroupId(Map<String, String> map);
	
	/**
	 * SAP 사용자 입력
	 * 
	 * @param userDetailList 사용자 정보
	 */
	public void updateSapUser(UserDetail userDetail);
	
	public void updateSapNewUser(UserDetail userDetail);
	
	public void updateEntryDate();
	
	public void deleteRoleUser(Map<String, String> map);
	
	public void insertRoleUser(Map<String, String> map);
	
	/**
	 * 무림 사용자를 업데이트-주요정보를 SAP에서 취급하기 때문에 별도의 업데이트 로직이 필요
	 * @param user
	 */
	
	public void updateMoorimUser(User user);
	
	/**
	 * Sap에서 수신된 사용자 정보가 담긴 tmp 테이블에서 실 테이블로 전송한다
	 * @return 수행된 결과값
	 */
	public String updateEpUserTableFromTmpUserTable();
	
	/**
	 * 사용자의 메뉴 접근 권한을 업데이트 한다.	 
	 * @return 수행된 결과값
	 */
	public String updateUserMenuAcl();
	
	
	public String readJobCondition(String jobName);
	
	/**
	 * 공용 주소록을 업데이트 한다
	 * @return
	 */
	public void updatePublicAddressbook();
	
	/**
	 * 팀 유저 리스트
	 * @param groupId
	 * @return
	 */
	public List<User> listTeamUser(String groupId);
	
	public List<User> listTeamLeader(String groupId);
	
	public Object empNoToUserId(String empNo);
	
	public Object getKmsUserGroup(String userId);
	
	public Object empNoToUserInfo(String empNo);
	
	public Object getRoleId(String userId);
	
	public int getUserRoleCheck(Map<String, String> map);
	
	public void loginLogInput(String userId);
	
	public void deleteTmpUser(String tmp);
	
	public void deleteTmpNewUser(String tmp);
	
	/**
	 * 모든 사용자 정보 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySelectGroupList(UserSearchCondition searchCondition);
	
	/**
	 * 조건에 따라 부서 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectGroupList(UserSearchCondition searchCondition);
	
	/**
	 * 조건에 따라 부서 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectGroupListForTreeMobile(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySelectGroupListForTreeMobile(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보 갯수(조직도)
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionAll(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectAllUser(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<User> selectAllForMobile(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보 갯수(조직도)
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionForMobile(UserSearchCondition searchCondition);
	
	/**
	 * 모바일정보로 사용자 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public User getMobileUser(String mobile);
	
	/**
	 * Mapping userinfo with DeviceOn system
	 * 
	 * @return
	 */
	public void executeMappingDB();
	
	public User searchUser(UserSearchCondition userSearchCondition);
	
	public List<User> selectRoleUser(String roleName);
	
	public List<User> selectOfficeRoleUsers(String roleName);
	
	public List<User> selectOfficeRoleUser(Map<String, String> map);
}
