package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;

/**
 * 
 * 오늘의 명언 포틀릿 DAO 구현 클래스
 *
 * @author 박철종
 * @version $Id: PortletWiseSayingDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository
public class PortletWiseSayingDaoImpl extends GenericDaoSqlmap<PortletWiseSaying, String> implements PortletWiseSayingDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.portlet.portletWiseSaying.";
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 리스트
	 * @param searchCondition SearchCondition
	 * @return List<PortletWiseSaying> 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 리스트
	 */
	public List<PortletWiseSaying> listBySearchCondition(SearchCondition searchCondition) {
		
		List<PortletWiseSaying> wiseSayingList = (List<PortletWiseSaying>) this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);

		return wiseSayingList;
	}
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 개수
	 * @param searchCondition SearchCondition
	 * @return Integer 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지별 오늘의 명언 개수
	 */
	public Integer countBySearchCondition(SearchCondition searchCondition) {
		
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

		return count;
	}
	
	/**
	 * 전체 오늘의 명언 개수
	 * @return Integer 전체 오늘의 명언 개수
	 */
	public Integer countByList() {
		
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countByList");

		return count;
	}
	
	/**
	 * 오늘의 명언 정보
	 * @param id 오늘의 명언 아이디
	 * @return PortletWiseSaying 오늘의 명언 정보
	 */
	public PortletWiseSaying get(String id) {
		
		PortletWiseSaying portletWiseSaying = (PortletWiseSaying) this.sqlSelectForObject(NAMESPACE + "get", id);
		
		return portletWiseSaying;
	}
	
	/**
	 * 오늘의 명언 무작위 선택
	 * @param integer 랜덤 변수
	 * @return PortletWiseSaying 오늘의 명언 무작위 선택
	 */
	public PortletWiseSaying getRandomWiseSaying(Integer integer) {
		
		PortletWiseSaying portletWiseSaying = (PortletWiseSaying) this.sqlSelectForObject(NAMESPACE + "getRandomWiseSaying", integer);
		
		return portletWiseSaying;
		
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 오늘의 명언 등록 확인
	 * @param object 오늘의 명언 정보
	 * @return boolean 오늘의 명언 등록 확인(등록:true, 미등록:false)
	 */
	public boolean existsWiseSaying(PortletWiseSaying object) {
		
		boolean exist = false;

		int count = (Integer) sqlSelectForObject(NAMESPACE + "existsWiseSaying", object);
		
		if(count > 0) {
			exist = true;
		} else {
			exist = false;
		}
		
		return exist;
		
	}
	
	/**
	 * 오늘의 명언 등록
	 * @param object 오늘의 명언 정보
	 * @return String오늘의 명언 아이디
	 */
	public String create(PortletWiseSaying object) {
		
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getWiseSayingId();
		
	}
	
	/**
	 * 오늘의 명언 수정
	 * @param object 오늘의 명언 정보
	 */
	public void update(PortletWiseSaying object) {
		
		this.sqlInsert(NAMESPACE + "update", object); 
		
	}
	
	/**
	 * 오늘의 명언 삭제
	 * @param id 오늘의 명언 아이디
	 */
	public void delete(String id) {
		
		this.sqlDelete(NAMESPACE + "delete", id);
		
	}

	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
}