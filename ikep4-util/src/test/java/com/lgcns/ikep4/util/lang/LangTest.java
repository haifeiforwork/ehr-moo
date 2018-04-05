package com.lgcns.ikep4.util.lang;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


public class LangTest {

	Log log = LogFactory.getLog(getClass());
	MockHttpServletRequest req;
	
	@Before
	public void setUp() {
		req = new MockHttpServletRequest();
	}

	@Test
	public void StringTest() throws Exception {
		String str = "";
		str = StringUtil.cutString("aaaaa", 5, "..");
		log.debug(str);
		str = StringUtil.cutString("안녕 하세요", 5, "..");
		log.debug(str);

	}

	@Test
	public void DateTest() throws Exception {
		String today = DateUtil.getToday("");
		String todaytime = DateUtil.getTodayDateTime("");
		String prevday = DateUtil.getPrevDate("20010201", 1, "yyyy-MM-dd");
		log.debug(today);
		log.debug(todaytime);
		log.debug(prevday);

	}

	@Test
	public void randomStr() throws Exception {
		log.debug(StringUtil.randomStr(20));
	}


	@Test
	public void getTimeInterval() throws Exception {
		Date ss = new Date();
		Date ee = new Date();
		log.debug(DateUtil.getTimeInterval(ss, ee, ""));

		log.debug(DateUtil.getToday("yyMMdd") + StringUtil.randomStr(4));
		
		
		log.debug(HttpUtil.getWebAppUrl(req));
	}

}
