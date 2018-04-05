package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.Congratulations;

/**
 * 
 * 축하합니다 포틀릿 DAO
 *
 * @author 박철종
 * @version $Id: CongratulationsDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface CongratulationsDao extends GenericDao<Congratulations, String> {

	/**
	 * 사용자 별 축하합니다 포틀릿 설정 정보
	 * @param param Map(portletConfigId:포틀릿 설정 아이디, propertyName:"PEROPD", userId:사용자 아이디)
	 * @return Congratulations 사용자 별 축하합니다 포틀릿 설정 정보
	 */
	public Congratulations getPortletConfigByUserId(Map<String, String> param);

	/**
	 * 생일자 리스트
	 * @param map Map(resultColumn:"BIRTHDAY"/"WEDDING_ANNIV", portalId:포탈 아이디, groupId:사용자 조직 아이디, startDate:검색 시작일, endDate:검색 종료일)
	 * @return
	 */
	public List<Congratulations> list(Map<String, String> map);
	
	/**
	 * 사용자 별 축하합니다 포틀릿 설정 저장
	 * @param congratulations 축하합니다 포틀릿 설정 정보
	 * @return String 포틀릿 설정 아이디
	 */
	public String createUserConfig(Congratulations congratulations);
	
	/**
	 * 사용자 별 축하합니다 포틀릿 설정 수정
	 * @param congratulations 축하합니다 포틀릿 설정 정보
	 */
	public void updateUserConfig(Congratulations congratulations);
	
}