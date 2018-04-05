package com.lgcns.ikep4.supportpack.abusereporting.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseQueryReturn;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * ArAbuseHistoryService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: ArAbuseHistoryServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */

public class ArAbuseHistoryServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private ArAbuseHistoryService arAbuseHistoryService;
	
	private ArAbuseHistory arAbuseHistory;

	String pk = "";
	ArAbuseHistory paramArAbuseHistory = null;

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		user.setPortalId("P000001");
		session.setAttribute("ikep.user", user);

		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		arAbuseHistory = new ArAbuseHistory();

		this.pk = idgenService.getNextId();
		System.out.println("this.pk  bf:"+this.pk);
		
		arAbuseHistory.setHistoryId(pk);
		arAbuseHistory.setModuleCode("MB");
		arAbuseHistory.setModuleName("Micro Blogging");
		arAbuseHistory.setItemId("100000734442");
		arAbuseHistory.setKeyword("욕11|욕13");
		arAbuseHistory.setContents("욕11 욕13");
		arAbuseHistory.setIsDetected(1);
		arAbuseHistory.setRegisterId(user.getUserId());
		arAbuseHistory.setRegisterName(user.getUserName());
		arAbuseHistory.setPortalId(user.getPortalId());

		arAbuseHistoryService.create(arAbuseHistory);
		
		paramArAbuseHistory = new ArAbuseHistory();
		paramArAbuseHistory.setHistoryId(pk);
		paramArAbuseHistory.setPortalId(user.getPortalId());
	}
	
	
	@Test
	public void create() {
		ArAbuseHistory returnArAbuseHistory = arAbuseHistoryService.read(paramArAbuseHistory);
		assertNotNull(returnArAbuseHistory);	
	}

	
	@Test
	public void read() {
		ArAbuseHistory arAbuseHistory = arAbuseHistoryService.read(paramArAbuseHistory);
		assertNotNull(arAbuseHistory);	
		
	}

	@Test
	public void checkAndSaveProhibitWord() {
		arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
	}

	@Test
	public void listBySearchconditionForReport() {
		ArAbuseSearchCondition searchCondition = new ArAbuseSearchCondition();
		
		SearchResult<ArAbuseHistory> list = arAbuseHistoryService.listBySearchconditionForReport(searchCondition);
		assertNotNull(list);	
	}

	@Test
	public void listBySearchconditionForStatistics() {
		ArAbuseSearchCondition searchCondition = new ArAbuseSearchCondition();
		
		List<ArAbuseQueryReturn> list = arAbuseHistoryService.listBySearchconditionForStatistics(searchCondition);
		assertNotNull(list);	
	}

	@Test
	public void getCheckProhibitWordList() {
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		arAbuseHistory.setModuleCode("MB");
		arAbuseHistory.setContents("욕13");
		arAbuseHistory.setPortalId(user.getPortalId());
		
		String prohibitWord = arAbuseHistoryService.getCheckProhibitWordList(arAbuseHistory);
	}

}
