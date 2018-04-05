package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeUrlDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

@Repository
public class UtResTimeUrlDaoImpl extends GenericDaoSqlmap<UtResTimeUrl,String>  implements UtResTimeUrlDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utResTimeUrl.";

	/**
	 * 응답시간 URL 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	/**
	 * 응답시간 URL 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtResTimeUrl> listBySearchCondition(UtSearchCondition searchCondition) {
		return (List<UtResTimeUrl>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * 응답시간 URL 목록
	 * @param portalId
	 * @return
	 */
	public List<UtResTimeUrl> listResTimeUrl(String portalId) {
		return (List<UtResTimeUrl>)this.sqlSelectForList(NAMESPACE + "listResTimeUrl", portalId);
	}
	
	/**
	 * 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 읽기
	 */
	public UtResTimeUrl get(String resTimeUrlId) {
		return (UtResTimeUrl) sqlSelectForObject(NAMESPACE+"get", resTimeUrlId);
	}
	
	/**
	 * URL존재여부
	 * @param resTimeUrl
	 */
	public boolean existsURL(String resTimeUrl) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"existsURL", resTimeUrl);
		return count > 0;
	}
	
	/**
	 * 생성
	 */
	public String create(UtResTimeUrl utResTimeUrl) {
		sqlInsert(NAMESPACE+"create", utResTimeUrl);
		
		return utResTimeUrl.getResTimeUrlId();
	}
	
	/**
	 * 수정
	 */
	public void update(UtResTimeUrl utResTimeUrl) {
		sqlUpdate(NAMESPACE+"update", utResTimeUrl);
	}
	
	/**
	 * 등록된 URL, 사용여부 체크 조회
	 * @param utResTimeUrl
	 * @return
	 */
	public UtResTimeUrl getResTimeUrl(UtResTimeUrl utResTimeUrl) {
		return (UtResTimeUrl) sqlSelectForObject(NAMESPACE+"getResTimeUrl", utResTimeUrl);
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}
}