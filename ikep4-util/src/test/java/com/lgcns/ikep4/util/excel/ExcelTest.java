package com.lgcns.ikep4.util.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgcns.ikep4.util.excel.ExcelUtil;


public class ExcelTest {

	Log log = LogFactory.getLog(getClass());

	@Test
	public void dummyTest() throws Exception {
		log.debug("delete this");
	}

	public void setUp() {}

	public void read() throws Exception {

		String fileName = "test.xlsx";
		String className = "com.lgcns.ikep4.util.excel.RssEntry";

		InputStream inp = getClass().getResourceAsStream(fileName);

		List<Object> rssEntryList = (List<Object>) ExcelUtil.readExcel(className, inp, 0);

		for (Object object : rssEntryList) {
			log.debug(((RssEntry) object).toString());
		}

	}

	@Test
	public void write() throws Exception {

		MockHttpServletResponse response = new MockHttpServletResponse();

		String fileName = "test2.xlsx";

		List<Object> testList = new ArrayList<Object>();

		for (int i = 0; i < 3; i++) {

			RssEntry rssEntry = new RssEntry();
			rssEntry.setTitle("123123");
			rssEntry.setDescription("123123123");
			rssEntry.setPublishedDate(new Date());

			testList.add(rssEntry);
		}

		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("title", "제목");
		titleMap.put("description", "설명");
		titleMap.put("publishedDate", "발행일");

		// 파일 저장
		ExcelUtil.saveExcel(titleMap, testList, fileName, response);

	}

}
