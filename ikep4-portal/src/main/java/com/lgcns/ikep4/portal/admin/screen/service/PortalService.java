package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;

/**
 * 
 * 포탈 service
 *
 * @author 임종상
 * @version $Id: PortalService.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Transactional
public interface PortalService extends GenericService<Portal, String> {
	
	/**
	 * 포탈 로고 이미지 ID 조회
	 * @param portalId 포탈 ID
	 * @return 이미지 로고 ID
	 */
	public String readLogoImageId(String portalId);
	
	/**
	 * 포탈 생성
	 * @param portal 포탈 객체
	 */
	public void createPortal(Portal portal);
	
	/**
	 * 포탈 조회
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal readPortal(String portalId);
	
	/**
	 * 포탈 수정
	 * @param portal 포탈 객체
	 */
	public void updatePortal(Portal portal);
	
	/**
	 * 포탈 리스트
	 * @param searchCondition 검색 조건
	 * @return SearchResult<Portal> 포탈 목록
	 */
	public SearchResult<Portal> listPortal(PortalSearchCondition searchCondition);
	
	/**
	 * 포탈 삭제
	 * @param portalId 포탈 ID
	 */
	public void removePortal(String portalId);
	
	/**
	 * 도메인으로 포탈 조회
	 * @param domain 도메인
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalDomain(String domain);
	
	/**
	 * 디폴트 포탈 조회
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalDefault();
	
	/**
	 * ContextPath 로 포탈 조회
	 * @param contextPath 루트 경로
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalContextPath(String contextPath);

	/**
	 * 포탈 관리자 권한 상세 정보를 조회
	 * @param aclResourcePermission
	 * @param portalId 포탈아이디
	 * @return aclResourcePermission 포탈 사용자 권한 상세 정보 리스트
	 */
	public ACLResourcePermission listPortalAdminPermission(ACLResourcePermission aclResourcePermission, String portalId);
	
	/**
	 * 전체 포탈 리스트
	 * @return List<Portal> 포탈 목록
	 */
	public List<Portal> listPortalAll();

}
