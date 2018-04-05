/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.SpSnRelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 동료목록
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SpSnRelationDAOImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class SpSnRelationDAOImpl extends GenericDaoSqlmap<User,String> implements SpSnRelationDAO {

private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.spsnrelation.";
	
/**
 * 동료목록
 */
	public List<User> getSnRelationList(SearchUtil searchUtil){
		return sqlSelectForList(NAMESPACE+"getSnRelationList", searchUtil);
	}

	/**
	 * 동료에관한 상세정보검색
	 */
	public List<User> getSnRelationDetailList(SearchUtil searchUtil){
		return sqlSelectForList(NAMESPACE+"getSnRelationDetailList", searchUtil,searchUtil.getStartIndex(),searchUtil.getEndIndex());
	}
	
	/**
	 * 동료검색어 상세정보카운트
	 */
	public Integer countSnRelationDetailList(SearchUtil searchUtil){
		return (Integer)sqlSelectForObject(NAMESPACE+"countSnRelationDetailList", searchUtil);
	}
	
	public User get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(User object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(User object) {
		// TODO Auto-generated method stub
		
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	
}