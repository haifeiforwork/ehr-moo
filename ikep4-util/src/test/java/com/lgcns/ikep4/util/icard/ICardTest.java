package com.lgcns.ikep4.util.icard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lgcns.ikep4.util.icard.ICardUtil;
import com.lgcns.ikep4.util.icard.model.IAddr;
import com.lgcns.ikep4.util.icard.model.ICard;
import com.lgcns.ikep4.util.icard.model.ITel;


public class ICardTest {

	Log log = LogFactory.getLog(getClass());

	@Test
	public void dummyTest() throws Exception {
		log.debug("delete this");
	}

	public void read() throws Exception {

		/*
		 * String fileDir = "D:/"; File f = new File(""); File[] testFiles = new
		 * File(fileDir).listFiles((FileFilter) VCardFileFilter.INSTANCE);
		 * InputStream[] inputStreamList = new InputStream[testFiles.length];
		 * for (int i = 0; i < testFiles.length; i++) { inputStreamList[i] = new
		 * FileInputStream((testFiles[i]).getPath()); } List<ICard> icardList =
		 * ICardUtil.readICard(inputStreamList);
		 */

		String fileName = "contacts.vcf";

		InputStream inp = getClass().getResourceAsStream(fileName);
		List<ICard> icardList = ICardUtil.readICard(inp);

		for (ICard icard : icardList) {
			log.debug(icard.toString());
		}

	}

	@Test
	public void write() throws Exception {

		MockHttpServletResponse response = new MockHttpServletResponse();

		String fileName = "test.vcf";

		List<ICard> icardList = new ArrayList<ICard>();

		for (int i = 0; i < 3; i++) {

			ICard icard = new ICard();
			icard.setFamilyName("김");
			icard.setGivenName("태희" + i);
			icard.setFullName("kim tae hee_" + i);
			icard.setBday("1990/01/01");
			icard.setOrg("seoul university");
			icard.setDept("maketing dept");
			icard.setTitle("manager");
			icard.setGender("F");
			icard.setCategories("category...");
			icard.setClazz("PUBLIC");
			icard.setEmail("daum@daum.net");

			ITel itel = new ITel();

			itel.setType("CELL");
			itel.setNumber("+1-206-937-9972");
			icard.addITelList(itel);

			ITel itel2 = new ITel();

			itel2.setType("FAX");
			itel2.setNumber("+1-206-937-9972");
			icard.addITelList(itel2);

			ITel itel3 = new ITel();

			itel3.setType("WORK");
			itel3.setNumber("+1-206-937-9972");
			icard.addITelList(itel3);

			IAddr iaddr = new IAddr();

			iaddr.setType("HOME");
			iaddr.setCountry("korea");
			iaddr.setRegion("dong dae mon");
			iaddr.setLocality("myong dong");
			iaddr.setStreet("111 ga");
			iaddr.setPostcode("111-111");
			icard.addIAddrList(iaddr);

			IAddr iaddr2 = new IAddr();

			iaddr2.setType("WORK");
			iaddr2.setCountry("korea");
			iaddr2.setRegion("dong dae mon");
			iaddr2.setLocality("myong dong");
			iaddr2.setStreet("222 ga");
			iaddr2.setPostcode("222-222");
			icard.addIAddrList(iaddr2);

			icardList.add(icard);

		}

		// 파일 저장
		ICardUtil.saveICard(icardList, fileName, response);

	}

}
