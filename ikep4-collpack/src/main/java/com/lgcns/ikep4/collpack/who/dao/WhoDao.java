/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WhoDao extends GenericDao<Who, String>  {
	/**
	 * 인물검색목록
	 * @param whoSearchCondition 
	 * @return
	 */
	public List<Who> listWhoBySearchCondition(WhoSearchCondition whoSearchCondition);
	
	/**
	 * 인물검색목록수
	 * @param whoSearchCondition 
	 * @return
	 */	
	public Integer countWhoBySearchCondition(WhoSearchCondition whoSearchCondition);
	
	/**
	 * 인물별 수정이력목록
	 * @param profileGroupId 
	 * @return
	 */		
	public List<Who> selectProfileHistoryList(String profileGroupId);

	/**
	 * verion정보
	 * @param who
	 * @return
	 */
	public String selectOriginVersion(Who who);
	
	/**
	 * verion정보 (0.1증가)
	 * @param who
	 * @return
	 */
	public String selectVersion(Who who);	
	
	/**
	 * 최근 등록자 ID
	 * @param profileGroupId
	 * @return
	 */
	public String selectRecentInputRegisterId(String profileGroupId);
	
	/**
	 * hit 카운트 올리기
	 * @param profileId
	 * @return
	 */
	public void updateHit(String id);
	
	/**
	 * hit 날짜변경
	 * @param profileId
	 * @return
	 */
	public void updateHitDate(Who who);	
	
	/**
	 * 조회여부
	 * @param who
	 * @return
	 */
	public Integer checkAlreadyRead(Who who);	
	
	/**
	 * hit 정보 입력
	 * @param who
	 * @return
	 */
	public String insertHit(Who who);
	
	/**
	 * hit 정보 삭제
	 * @param profileId
	 * @return
	 */
	public void deleteReferenceByProfileId(String profileId);
	
	/**
	 * 메일중복여부
	 * @param who
	 * @return
	 */
	public Integer checkAlreadyMailExists(Who who);	

	/**
	 * 최근 인물 ID
	 * @param profileGroupId
	 * @return
	 */	
	public String selectRecentProfileId(String profileGroupId);
}
