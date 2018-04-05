/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.model.UserDetail;

/**
 * 사용자 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserService.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Transactional
public interface UserService extends GenericService<User, String> {

	/**
	 * 모든 사용자 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<User> list(UserSearchCondition searchCondition);
	
	public SearchResult<User> listForMobile(UserSearchCondition searchCondition);
	
	public SearchResult<User> treelist(UserSearchCondition searchCondition, User user);
	
	/**
	 * 엑셀업로드를 이용한 신규 사용자 정보 생성
	 * 
	 * @param user 사용자 정보
	 */
	public String createForExcel(User user);
	
	/**
	 * 엑셀업로드를 이용한 사용자 정보 수정
	 * 
	 * @param user 사용자 정보
	 */
	public void updateForExcel(User user);
	
	public void authCheck(User user);
	
	public int requestCertification(User user);
	
	public int requestCertificationCheck(User user);

	/**
	 * 사용자를 삭제함
	 * 
	 * @param user 삭제할 사용자 정보
	 */
	public void delete(User user);

	/**
	 * 국가 Code 목록 불러옴
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectNationAll(String localeCode);

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
	 * WorkPlace Code 목록 불러옴
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
	 * 조직도용 사용자 조회
	 * 
	 * @param localeCode
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public List<User> selectAllForTree(String localeCode, String groupId, String userId);
	
	public List<User> selectAgentUserList(String roleName);
	
	SearchResult<User> selectUserPwUpdateList(UserSearchCondition searchCondition);
	
	public List getWorkPlaceNameList();

	/**
	 * 조직도용 사용자 조회 - Job Title
	 * 
	 * @param localeCode
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public List<User> selectJobTitleUserForTree(String localeCode, String jobTitleCode, String userId);
	

	/**
	 * 조직도용 사용자 조회 - Job Title
	 * 
	 * @param localeCode
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public List<User> selectJobDutyUserForTree(String localeCode, String jobDutyCode, String userId);
	
	
	/**
	 * 조직도용 사용자 검색시 조회
	 * 
	 * @param localeCode
	 * @param keyword
	 * @return
	 */
	public List<User> selectSearchForTree(String localeCode, String keyword, String registerId);

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
	 * 프로파일에서 요즘은 (프로파일 대상자 현재 상태 )을 업데이트 한다.
	 * 
	 * @param profile
	 */
	public void updateProfileStaus(User profile);

	/**
	 * 프로파일의 사진을 등록하기 위해 이미지 ID를 업데이트 한다.
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
	public void updateForPassword(List<User> userList);
	
	public void updateMsgForPassword(List<User> userList);

	/**
	 * 해당 사용자가 속한 그룹 목록을 가져옴
	 * 
	 * @param userId 사용자 ID
	 * @return
	 */
	public List<Map<String, String>> selectGroupForUser(String userId);

	/**
	 * 해당 사용자가 리더로 있는 그룹 정보를 가져옴  : 단일 그룹 - 삭제 대상
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
	 * Sap에서 수신된 사용자 정보를 DB의 temp 테이블에 저장
	 * @param userDetailList
	 */	
	public void updateSapUser(List<UserDetail> userDetailList);
	
	
	public void updateSapNewUser(List<UserDetail> userDetailList);
	
	
	/**
	 * Sap에서 수신된 사용자 정보가 담긴 tmp 테이블에서 실 테이블로 전송한다
	 * @return 수행된 결과값
	 */
	public String updateEpUserTabeFromTmpUserTable();
	
	/**
	 * 사용자의 메뉴 접근 권한을 업데이트 한다.	 
	 * @return 수행된 결과값
	 */
	public String updateUserMenuAcl();
	
	/**
	 * 공용 주소록을 업데이트 한다
	 * @return
	 */
	public void updatePublicAddressbook();
	
	public String readJobCondition(String jobName);
	
	
	/**
	 * 팀 유저 리스트
	 * @param groupId
	 * @return
	 */
	public List<User> listTeamUser(String groupId);
	
	public List<User> listTeamLeader(String groupId);
	
	public void loginLogInput(String userId);
	
	/**
	 * 조직 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<User> groupListMobile(UserSearchCondition searchCondition);
	
	/**
	 * 조직 정보를 불러옴
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<User> groupListForTreeMobile(UserSearchCondition searchCondition);
	
	/**
	 * 모든 사용자 정보를 불러옴(조직도)
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<User> listAll(UserSearchCondition searchCondition);
	
	/**
	 * Mapping userinfo with DeviceOn system
	 * 
	 * @return
	 */
	public void executeMappingDB();
	
	
		
}
