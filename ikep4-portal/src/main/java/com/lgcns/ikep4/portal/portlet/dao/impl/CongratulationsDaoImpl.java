package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.CongratulationsDao;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;

/**
 * 
 * 축하합니다 포틀릿 DAO 구현 클래스
 *
 * @author 박철종
 * @version $Id: CongratulationsDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository
public class CongratulationsDaoImpl extends GenericDaoSqlmap<Congratulations, String> implements CongratulationsDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.portlet.congratulations.";

	/**
	 * 사용자 별 축하합니다 포틀릿 설정 정보
	 * @param param Map(portletConfigId:포틀릿 설정 아이디, propertyName:"PEROPD", userId:사용자 아이디)
	 * @return Congratulations 사용자 별 축하합니다 포틀릿 설정 정보
	 */
	public Congratulations getPortletConfigByUserId(Map<String, String> param) {
		
		Congratulations congratulations = (Congratulations) this.sqlSelectForObject(NAMESPACE + "getPortletConfigByUserId", param);
		
		return congratulations;
		
	}
	
	/**
	 * 생일자 리스트
	 * @param map Map(resultColumn:"BIRTHDAY"/"WEDDING_ANNIV", portalId:포탈 아이디, groupId:사용자 조직 아이디, startDate:검색 시작일, endDate:검색 종료일)
	 * @return
	 */
	public List<Congratulations> list(Map<String, String> param) {
		
		List<Congratulations> list = (List<Congratulations>) this.sqlSelectForList(NAMESPACE + "list", param);

		return list;
		
	}
	

	
	/**
	 * 사용자 별 축하합니다 포틀릿 설정 저장
	 * @param congratulations 축하합니다 포틀릿 설정 정보
	 * @return String 포틀릿 설정 아이디
	 */
	public String createUserConfig(Congratulations object) {
		
		this.sqlInsert(NAMESPACE + "createUserConfig", object);
		
		return object.getPortletConfigId();
		
	}
	
	/**
	 * 사용자 별 축하합니다 포틀릿 설정 수정
	 * @param congratulations 축하합니다 포틀릿 설정 정보
	 */
	public void updateUserConfig(Congratulations object) {
		
		this.sqlInsert(NAMESPACE + "updateUserConfig", object); 
		
	}

	@Deprecated
	public Congratulations get(String id) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public String create(Congratulations object) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public void update(Congratulations object) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
}