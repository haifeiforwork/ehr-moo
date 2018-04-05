/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util;

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * TODO Javadoc주석작성
 * 
 * @author 占쏙옙占쏙옙占쏙옙
 * @version $Id: DateUtilsTest.java 18671 2012-05-11 04:03:59Z handul $
 */
public class DateUtilsTest {

	Log log = LogFactory.getLog(getClass());

	@Test
	public void testGetTwoDatesDifference1() {
		Date date1 = new Date();

		Date date2 = DateUtils.addDays(date1, -5);

		log.debug(DateUtil.toCalendar("20111231",0,0,0));

		Long result = null;

		result = DateUtil.getTwoDateDiff(date1, date2, "S");

		Assert.assertTrue((5 * 24 * 60 * 60) == result);

		result = DateUtil.getTwoDateDiff(date1, date2, "M");

		Assert.assertTrue((5 * 24 * 60) == result);

		result = DateUtil.getTwoDateDiff(date1, date2, "H");

		Assert.assertTrue((5 * 24) == result);

		result = DateUtil.getTwoDateDiff(date1, date2, "D");

		Assert.assertTrue(5 == result);
	}
}
