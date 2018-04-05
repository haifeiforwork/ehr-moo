/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtConfigDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfigKey;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 
 * 사용량 통계 configure daoimpl
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtConfigDaoImpl extends GenericDaoSqlmap<UtConfig,UtConfigKey>  implements UtConfigDao {

private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utConfig.";
	
	/**
	 * 읽기
	 */
	public UtConfig get(UtConfigKey id) {
		return (UtConfig) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 존재여부
	 */
	public boolean exists(UtConfigKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 생성
	 */
	public UtConfigKey create(UtConfig utConfig) {
		sqlInsert(NAMESPACE+"create", utConfig);
		
		return new UtConfigKey(utConfig.getLogTarget(), utConfig.getPortalId());
	}

	/**
	 * 수정
	 */
	public void update(UtConfig UtConfig) {
		sqlUpdate(NAMESPACE+"update", UtConfig);
	}

	/**
	 * 삭제
	 */
	public void remove(UtConfigKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	public List<UtConfig> selectUtConfigList(SearchUtil searchUtil){
		return sqlSelectForList(NAMESPACE+"selectUtConfigList", searchUtil);
	}
}