package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDao;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("portalDao")
public class PortalDaoImpl extends GenericDaoSqlmap<Portal, String> implements PortalDao {

	/**
	 * 도메인으로 포탈 조회
	 * @param domain 도메인
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalDomain(String domain) {
		return (Portal) sqlSelectForObject("portal.admin.screen.portal.getPortalDomain", domain);
	}
	
	/**
	 * contextPath 로 포탈 조회
	 * @param contextPath 루트 패스
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalContextPath(String contextPath) {
		return (Portal) sqlSelectForObject("portal.admin.screen.portal.getPortalContextPath", contextPath);
	}

	/**
	 * 디폴트 포탈 조회
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalDefault() {
		return (Portal) sqlSelectForObject("portal.admin.screen.portal.getPortalDefault");
	}
	
	/**
	 * 로그인 이미지 아이디 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortalLoginImageId(Portal portal) {
		sqlUpdate("portal.admin.screen.portal.updatePortalLoginImageId", portal);
	}
	
	/**
	 * 로고 이미지 아이디 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortalLogoImageId(Portal portal) {
		sqlUpdate("portal.admin.screen.portal.updatePortalLogoImageId", portal);
	}
	
	/**
	 * 포탈 조회
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal getPortal(String portalId) {
		return (Portal) sqlSelectForObject("portal.admin.screen.portal.getPortal", portalId);
	}
	
	/**
	 * 포탈 생성
	 * @param portal 포탈 모델
	 */
	public void createPortal(Portal portal) {
		sqlInsert("portal.admin.screen.portal.createPortal", portal);
	}
	
	/**
	 * 포탈 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortal(Portal portal) {
		sqlUpdate("portal.admin.screen.portal.updatePortal", portal);
	}
	
	/**
	 * 포탈 리스트 갯수
	 * @param searchCondition 검색 조건
	 * @return 리스트 갯수
	 */
	public Integer countListPortal(PortalSearchCondition searchCondition) {
		return (Integer) sqlSelectForObject("portal.admin.screen.portal.countListPortal", searchCondition);
	}
	
	/**
	 * 포탈 리스트
	 * @param searchCondition 검색 조건
	 * @return List<Portal> 포탈 목록
	 */
	public List<Portal> listPortal(PortalSearchCondition searchCondition) {
		return sqlSelectForList("portal.admin.screen.portal.listPortal", searchCondition);
	}
	
	/**
	 * 포탈 삭제
	 * @param portalId 포탈 ID
	 */
	public void removePortal(String portalId) {
		sqlDelete("portal.admin.screen.portal.removePortal", portalId);
	}
	
	/**
	 * 모든 포탈의 기본 포탈 여부를 아님으로 수정
	 */
	public void resetDefaultOption() {
		sqlUpdate("portal.admin.screen.portal.resetDefaultOption");
	}
	
	/**
	 * 포탈의 관리자 리스트
	 * @param assignUserIdList 포탈 권한을 가지는 사용자들
	 * @param portalId 포탈 아이디
	 * @return List<User> 포탈의 관리자 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<User> listAdminUser(List<String> assignUserIdList, String portalId) {
		List<User> adminUser = null;

		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("assignUserIdList", assignUserIdList);
		map.put("portalId", portalId);
		
		try {
			adminUser = getSqlMapClientTemplate().getSqlMapClient().queryForList("portal.admin.screen.portal.listAdminUser", map);
		} catch (SQLException sqlException) {
			// TODO: Exception 작성 필요
		}
		
		return adminUser;
	}
	
	/**
	 * 포탈의 관리자 생성
	 * @param user 관리자
	 * @return String 관리자 아이디
	 */
	public String createAdminUser(User user) {
		
		this.sqlInsert("portal.admin.screen.portal.createAdminUser", user);
		
		return user.getUserId();
		
	}
	
	/**
	 * 포탈의 관리자 수정
	 * @param user 관리자
	 * @param portalId 포탈 아이디
	 */
	public void updateAdminUser(User user, String portalId) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("userId", user.getUserId());
		map.put("userPassword", user.getUserPassword());
		map.put("userName", user.getUserName());
		map.put("portalId", user.getPortalId());
		map.put("updaterId", user.getUpdaterId());
		map.put("updaterName", user.getUpdaterName());
		
		sqlUpdate("portal.admin.screen.portal.updateAdminUser", map);
	}
	
	/**
	 * 포탈의 관리자 삭제
	 * @param userId 관리자 아이디
	 * @param portalId 포탈 아이디
	 */
	public void deleteAdminUser(String userId, String portalId) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("userId", userId);
		map.put("portalId", portalId);
		
		sqlDelete("portal.admin.screen.portal.deleteAdminUser", map);
	}
	
	public List<Portal> listPortalAll() {
		return sqlSelectForList("portal.admin.screen.portal.listPortalAll");
	}

	@Deprecated
	public Portal get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(Portal object) {
		return null;
	}

	@Deprecated
	public void update(Portal object) {}

	@Deprecated
	public void remove(String id) {}
}