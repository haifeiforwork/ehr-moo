package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;

/**
 * 
 * 오늘의 명언 포틀릿 DAO
 *
 * @author 박철종
 * @version $Id: PortletWiseSayingDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortletWiseSayingDao extends GenericDao<PortletWiseSaying, String> {
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 리스트
	 * @param searchCondition SearchCondition
	 * @return List<PortletWiseSaying> 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 리스트
	 */
	public List<PortletWiseSaying> listBySearchCondition(SearchCondition searchCondition);
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 개수
	 * @param searchCondition SearchCondition
	 * @return Integer 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 개수
	 */
	public Integer countBySearchCondition(SearchCondition searchCondition);
	
	/**
	 * 전체 오늘의 명언 개수
	 * @return Integer 전체 오늘의 명언 개수
	 */
	public Integer countByList();
	
	/**
	 * 오늘의 명언 등록 확인
	 * @param object 오늘의 명언 정보
	 * @return boolean 오늘의 명언 등록 확인(등록:true, 미등록:false)
	 */
	public boolean existsWiseSaying(PortletWiseSaying object);
	
	/**
	 * 오늘의 명언 무작위 선택
	 * @param integer 랜덤 변수
	 * @return PortletWiseSaying 오늘의 명언 무작위 선택
	 */
	public PortletWiseSaying getRandomWiseSaying(Integer integer);

	/**
	 * 오늘의 명언 삭제
	 * @param id 오늘의 명언 아이디
	 */
	public void delete(String id);
	
}