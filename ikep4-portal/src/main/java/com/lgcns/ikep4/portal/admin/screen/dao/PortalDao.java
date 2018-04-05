package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 DAO
 *
 * @author 임종상
 * @version $Id: PortalDao.java 19021 2012-05-31 06:36:11Z malboru80 $
 */
public interface PortalDao extends GenericDao<Portal, String> {

	/**
	 * 도메인으로 포탈 조회
	 * @param domain 도메인
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalDomain(String domain);
	
	/**
	 * contextPath 로 포탈 조회
	 * @param contextPath 루트 패스
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalContextPath(String contextPath);

	/**
	 * 디폴트 포탈 조회
	 * @return Portal 포탈 정보
	 */
	public Portal getPortalDefault();
	
	/**
	 * 포탈 조회
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal getPortal(String portalId);
	
	/**
	 * 로그인 이미지 아이디 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortalLoginImageId(Portal portal);
	
	/**
	 * 로고 이미지 아이디 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortalLogoImageId(Portal portal);
	
	/**
	 * 포탈 생성
	 * @param portal 포탈 모델
	 */
	public void createPortal(Portal portal);
	
	/**
	 * 포탈 수정
	 * @param portal 포탈 모델
	 */
	public void updatePortal(Portal portal);
	
	/**
	 * 포탈 리스트 갯수
	 * @param searchCondition 검색 조건
	 * @return 리스트 갯수
	 */
	public Integer countListPortal(PortalSearchCondition searchCondition);
	
	/**
	 * 포탈 리스트
	 * @param searchCondition 검색 조건
	 * @return List<Portal> 포탈 목록
	 */
	public List<Portal> listPortal(PortalSearchCondition searchCondition);
	
	/**
	 * 포탈 삭제
	 * @param portalId 포탈 ID
	 */
	public void removePortal(String portalId);

	/**
	 * 모든 포탈의 기본 포탈 여부를 아님으로 수정
	 */
	public void resetDefaultOption();

	/**
	 * 포탈의 관리자 리스트
	 * @param assignUserIdList 포탈 권한을 가지는 사용자들
	 * @param portalId 포탈 아이디
	 * @return List<User> 포탈의 관리자 리스트
	 */
	public List<User> listAdminUser(List<String> assignUserIdList, String portalId);
	
	/**
	 * 포탈의 관리자 생성
	 * @param user 관리자
	 * @return String 관리자 아이디
	 */
	public String createAdminUser(User user); 

	/**
	 * 포탈의 관리자 수정
	 * @param user 관리자
	 * @param portalId 포탈 아이디
	 */
	public void updateAdminUser(User user, String portalId);

	/**
	 * 포탈의 관리자 삭제
	 * @param userId 관리자 아이디
	 * @param portalId 포탈 아이디
	 */
	public void deleteAdminUser(String userId, String portalId);
	
	/**
	 * 전체 포탈 리스트
	 * @return List<Portal> 포탈 목록
	 */
	public List<Portal> listPortalAll();
}
