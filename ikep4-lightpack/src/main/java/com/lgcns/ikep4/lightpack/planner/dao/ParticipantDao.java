/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.Participant;

/**
 * 일정 대상자 관리
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ParticipantDao.java 16297 2011-08-19 07:52:43Z giljae $
 */
public interface ParticipantDao extends GenericDao<Participant, String> {
	
	String NAMESPACE = "lightpack.planner.dao.Participant";

	/** 일정 대상자  목록 등록
	 * @param participants
	 */
	void create(List<Participant> participants);
	
	/**
	 * 일정 대상자 목록 가져오기
	 * @param scheduleId
	 * @return
	 */
	List<Participant> getList(String scheduleId);

	void create(List<Participant> participantList, String scheduleId);

	/**TODO Javadoc주석작성
	 * @param params
	 */
	void updateAcceptInfo(Map<String, String> params);

}
