/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.who.constants.WhoConstants;
import com.lgcns.ikep4.collpack.who.dao.WhoDao;
import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoDaoImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Repository("whoDao")
public class WhoDaoImpl extends GenericDaoSqlmap<Who, String> implements WhoDao {

	/**
	 * 인물 목록
	 */	
	public List<Who> listWhoBySearchCondition(WhoSearchCondition whoSearchCondition) {
		return sqlSelectForList("who.selectAll", whoSearchCondition);
	}
	
	/**
	 * 인물 목록수
	 */		
	public Integer countWhoBySearchCondition(WhoSearchCondition whoSearchCondition) {
		return (Integer)sqlSelectForObject("who.countWhoBySearchCondition", whoSearchCondition);
	}	

	/**
	 * 인물 정보 입력
	 */		
	public String create(Who who) {	
		return (String) sqlInsert("who.insert", who);
	}

	/**
	 * 조회 정보 입력
	 */	
	public String insertHit(Who who) {
		return (String) sqlInsert("who.insertHit", who);
	}
	
	/**
	 * 인물 정보 존재여부
	 */	
	public boolean exists(String profileId) {
		return false;
	}
	
	/**
	 * 인물 정보 가져오기
	 */	
	public Who get(String profileId) {
		return (Who) sqlSelectForObject("who.select", profileId);
	}
	
	/**
	 * 중복 조회여부 체크
	 */	
	public List<Who> selectProfileHistoryList(String profileGroupId) {
		return (List) sqlSelectForListOfObject("who.selectProfileHistoryList", profileGroupId);
	}
	
	/**
	 * 중복 조회여부 체크
	 */		
	public Integer checkAlreadyRead(Who who) {
		return (Integer) sqlSelectForObject("who.selectAlreadyRead", who);
	}

	/**
	 * 이메일 중복 체크
	 */		
	public Integer checkAlreadyMailExists(Who who) {
		return (Integer) sqlSelectForObject("who.selectAlreadyMailExists", who);
	}
	
	/**
	 * 인물 정보 버전 정보 가져오기(insert된 버전중 가장 최신 정보)
	 */		
	public String selectOriginVersion(Who who) {
		String version = (String) sqlSelectForObject("who.selectVersion", who);
		version =Double.toString(Double.parseDouble(version));
		return version;
	}	
	
	/**
	 * 인물 정보 버전 정보 가져오기(insert해야 될 version을 가져옴)
	 */		
	public String selectVersion(Who who) {
		String version = (String) sqlSelectForObject("who.selectVersion", who);
		Double dVersion = 1.0;
		if ( "0.0".equals(version) ) {
			version = "1.0";
		} else {
			dVersion = Double.parseDouble(version);
			dVersion = dVersion + WhoConstants.VERSION_INCREASE;
			version = Double.toString(dVersion);
		}
		return version;
	}
	
	/**
	 * 최근 등록자 ID
	 * @param profileGroupId
	 * @return
	 */
	public String selectRecentInputRegisterId(String profileGroupId) {
		return (String) sqlSelectForObject("who.selectRecentInputRegisterId", profileGroupId);
	}
	
	/**
	 * 인물 정보 삭제
	 */		
	public void remove(String profileId) {
		sqlDelete("who.delete", profileId);
	}
	
	/**
	 * 프로파일ID로 인물 정보 삭제
	 */		
	public void deleteReferenceByProfileId(String profileId) {
		sqlDelete("who.deleteReferenceByProfileId", profileId);
	}	

	/**
	 * 인물 정보 수정
	 */		
	public void update(Who who) {
		sqlUpdate("who.update", who);
	}
	
	/**
	 * 조회 정보 수정
	 */		
	public void updateHit(String profileId) {
		sqlUpdate("who.updateHit", profileId);
	}	
	
	/**
	 * 조회 시각 수정
	 */		
	public void updateHitDate(Who who) {
		sqlUpdate("who.updateHitDate", who);
	}
	
	/**
	 * 최신 인물 ID
	 * @param profileGroupId
	 * @return
	 */
	public String selectRecentProfileId(String profileGroupId) {
		return (String) sqlSelectForObject("who.selectRecentProfileId", profileGroupId);
	}	
}
