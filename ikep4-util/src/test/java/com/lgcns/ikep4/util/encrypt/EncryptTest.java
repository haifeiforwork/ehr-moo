package com.lgcns.ikep4.util.encrypt;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.lgcns.ikep4.util.encrypt.EncryptUtil;


public class EncryptTest {
	
	Log log = LogFactory.getLog(getClass());
	
	@Before
	public void setUp() {}

	@Test
	public void encryptText() throws Exception {
		String enStr = EncryptUtil.encryptText("1q2w3e4r!@");
		String deStr = EncryptUtil.decryptText(enStr);
		log.debug(enStr);
		log.debug(deStr);
		assertEquals("1q2w3e4r!@", deStr);
	}
	
	@Test
	public void encryptSha() throws Exception {
		String enStr = EncryptUtil.encryptSha("user33portal");
		log.debug(enStr);
	}
	
	@Test
	public void encryptMd5() throws Exception {
		String enStr = EncryptUtil.encryptMd5("user33portal");
		log.debug(enStr);
	}

}
