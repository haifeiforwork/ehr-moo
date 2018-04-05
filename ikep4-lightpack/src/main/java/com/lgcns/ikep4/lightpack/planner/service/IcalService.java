/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;


/**
 * ICal4j 연동 서비스
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: IcalService.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface IcalService {
	
	// boolean importIcal(User user, String portalId, InputStream is) throws Exception;

	/**
	 * 특정 기간의 일정들을 ICalendar List로 변환
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	// List<ICalendar> getICalendarList(String userId, long start, long end);
	
}
