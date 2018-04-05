package com.lgcns.ikep4.approval.collaboration.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 공통 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface CollaboCommonService {
	
	/**
	 * 임직원 메일주소 목록
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getUserMailAddrList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * EMC User 확인
	 * @param user
	 * @return
	 */
	public boolean isEcmUser( User user);
	
	/**
	 * 해당 사원의 부서 팀장 조회
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTeamLeaderInfo( String empNo) throws Exception;
	
	/**
	 * 메일주소 조회
	 * @param empNO
	 * @return
	 * @throws Exception
	 */
	public String getUserMailAddr(String empNO) throws Exception;
}
