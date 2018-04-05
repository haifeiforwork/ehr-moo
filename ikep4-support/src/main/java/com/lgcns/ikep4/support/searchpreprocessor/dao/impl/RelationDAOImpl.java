/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.RelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Relation;
import com.lgcns.ikep4.support.searchpreprocessor.model.RelationKey;

/**
 * 
 * 연관검색어
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RelationDAOImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class RelationDAOImpl extends GenericDaoSqlmap<Relation,RelationKey> implements RelationDAO {

private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.relation.";
	
/**
 * 연관검색어 읽기
 */
	public Relation get(RelationKey id) {
		return (Relation) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 연관 검색어 존재여부
	 */
	public boolean exists(RelationKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 연관검색어 생성
	 */
	public RelationKey create(Relation relation) {
		sqlInsert(NAMESPACE+"create", relation);
		
		return new RelationKey(relation.getSearchKeywordId(),relation.getRelationKeywordId());
	}

	/**
	 * 연관 검색어 수정
	 */
	public void update(Relation relation) {
		sqlUpdate(NAMESPACE+"update", relation);
	}

	/**
	 * 연관 검색어 제거
	 */
	public void remove(RelationKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
}