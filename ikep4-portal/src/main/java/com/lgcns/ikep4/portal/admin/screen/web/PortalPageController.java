package com.lgcns.ikep4.portal.admin.screen.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemUrlService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 레이아웃 Controller
 *
 * @author 임종상
 * @version $Id: PortalPageController.java 19462 2012-06-22 07:19:41Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/page")
public class PortalPageController extends BaseController {

	@Autowired
	private PortalPageService portalPageService;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private PortalSystemService portalSystemMapService;
	
	@Autowired
	private PortalSystemUrlService portalSystemUrlService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 포탈 페이지 등록폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPageForm.do")
	public String createPageForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalPage portalPage = new PortalPage();
		
		// ######## 로케일 입력을 위해 필요한 부분
		portalPage.setI18nMessageList(i18nMessageService.makeInitLocaleList("pageName"));
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		List<PortalSystem> systemMapList = portalSystemMapService.portalSystemListView(portal.getPortalId());
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		model.addAttribute("portalPage", portalPage);
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());
		model.addAttribute("systemMapList", systemMapList);
		
		return "portal/admin/screen/page/createPageForm";
	}
	
	/**
	 * 포탈 페이지 저장
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalPage 포탈 페이지 model
	 * @param result Validation 체크 결과
	 * @param request Request 객체
	 * @param model 모델 객체
	 * @return 페이지 ID
	 */
	@RequestMapping(value = "/createPage.do", method = RequestMethod.POST)
	public @ResponseBody String createPage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalPage portalPage, BindingResult result, HttpServletRequest request, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}

		String pageId = idgenService.getNextId();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		portalPage.setPageId(pageId);
		portalPage.setCommon(1);
		portalPage.setOwnerId(user.getUserId());
		portalPage.setRegisterId(user.getUserId());
		portalPage.setRegisterName(user.getUserName());
		portalPage.setUpdaterId(user.getUserId());
		portalPage.setUpdaterName(user.getUserName());
		
		// 필수 입력 정보를 채운다 .
		List<I18nMessage> i18nMessageList = null;
		
		//VO에서 받아온 메세지를 사용
		i18nMessageList = i18nMessageService.fillMandatoryField(portalPage.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPage.getPageId());
		
		String systemUrlId = idgenService.getNextId();
		
		PortalSystemUrl portalSystemUrl = new PortalSystemUrl();
		portalSystemUrl.setSystemUrlId(systemUrlId);
		portalSystemUrl.setUrl("/portal/main/body.do?pageId="+portalPage.getPageId());
		portalSystemUrl.setSystemUrlName(portalPage.getPageName());
		portalSystemUrl.setRegisterId(user.getUserId());
		portalSystemUrl.setRegisterName(user.getUserName());
		portalSystemUrl.setUpdaterId(user.getUserId());
		portalSystemUrl.setUpdaterName(user.getUserName());
		portalSystemUrl.setPortalId(portalId);
		
		// 필수 입력 정보를 채운다 .(시스템 URL)
		List<I18nMessage> i18nMessageSystemUrlList = new ArrayList<I18nMessage>();
		
		for(I18nMessage i18nMessage : portalPage.getI18nMessageList()) {
			I18nMessage i18nMessageSystemUrl = new I18nMessage();
			
			i18nMessageSystemUrl.setItemMessage(i18nMessage.getItemMessage());
			i18nMessageSystemUrl.setLocaleCode(i18nMessage.getLocaleCode());
			i18nMessageSystemUrl.setFieldName("systemUrlName");
			i18nMessageSystemUrl.setItemId(systemUrlId);
			i18nMessageSystemUrl.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_PORTAL);
			i18nMessageSystemUrl.setRegisterId(user.getUserId());
			i18nMessageSystemUrl.setRegisterName(user.getUserName());
			i18nMessageSystemUrl.setUpdaterId(user.getUserId());
			i18nMessageSystemUrl.setUpdaterName(user.getUserName());
			
			i18nMessageSystemUrlList.add(i18nMessageSystemUrl);	
		}
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		return pageId;
	}
	
	/**
	 * 포탈 페이지 수정폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updatePageForm.do")
	public String updatePageForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String pageId, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();
		
		// 메인 페이지 조회
		PortalPage portalPage = portalPageService.readPage(pageId, userLocaleCode);
		
		// ######## 로케일 입력을 위해 필요한 부분
		portalPage.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, pageId, "pageName"));
		
		model.addAttribute("portalPage", portalPage);
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());
		
		return "/portal/admin/screen/page/updatePageForm";
	}
	
	/**
	 * 포탈 페이지 수정
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalPage 포탈 페이지 model
	 * @param result Validation 체크 결과
	 * @param request Request 객체
	 * @return 페이지 ID
	 */
	@RequestMapping(value = "/updatePage.do", method = RequestMethod.POST)
	public @ResponseBody String updatePage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalPage portalPage, BindingResult result, HttpServletRequest request) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		portalPage.setUpdaterId(user.getUserId());
		portalPage.setUpdaterName(user.getUserName());
		
		PortalSystemUrl portalSystemUrl = new PortalSystemUrl();
		portalSystemUrl.setUrl("/portal/main/body.do?pageId="+portalPage.getPageId());
		portalSystemUrl.setSystemUrlName(portalPage.getPageName());
		portalSystemUrl.setRegisterId(user.getUserId());
		portalSystemUrl.setRegisterName(user.getUserName());
		portalSystemUrl.setUpdaterId(user.getUserId());
		portalSystemUrl.setUpdaterName(user.getUserName());
		portalSystemUrl.setPortalId(portalId);
		
		// 필수 입력 정보를 채운다 .
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalPage.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPage.getPageId());
		
		String systemUrlId = portalSystemUrlService.readSystemUrlId(portalSystemUrl.getUrl());
		boolean systemAddFlag = false; 
		
		if(systemUrlId == null) {
			systemUrlId = idgenService.getNextId();
			portalSystemUrl.setSystemUrlId(systemUrlId);
			systemAddFlag = true;
		} else {
			portalSystemUrl.setSystemUrlId(systemUrlId);
		}
		
		// 필수 입력 정보를 채운다 .(시스템 URL)
		List<I18nMessage> i18nMessageSystemUrlList = new ArrayList<I18nMessage>();
		
		for(I18nMessage i18nMessage : portalPage.getI18nMessageList()) {
			I18nMessage i18nMessageSystemUrl = new I18nMessage();
			
			//업데이트시 메세지 ID 조회
			if(!systemAddFlag) {
				i18nMessageSystemUrl.setMessageId(i18nMessageService.readMessagesId(IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrl.getSystemUrlId(), "systemUrlName", i18nMessage.getLocaleCode()));
			}
			
			i18nMessageSystemUrl.setItemMessage(i18nMessage.getItemMessage());
			i18nMessageSystemUrl.setLocaleCode(i18nMessage.getLocaleCode());
			i18nMessageSystemUrl.setFieldName("systemUrlName");
			i18nMessageSystemUrl.setItemId(systemUrlId);
			i18nMessageSystemUrl.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_PORTAL);
			i18nMessageSystemUrl.setRegisterId(user.getUserId());
			i18nMessageSystemUrl.setRegisterName(user.getUserName());
			i18nMessageSystemUrl.setUpdaterId(user.getUserId());
			i18nMessageSystemUrl.setUpdaterName(user.getUserName());
			
			i18nMessageSystemUrlList.add(i18nMessageSystemUrl);	
		}
		
		portalPageService.updatePage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl, systemAddFlag);

		return portalPage.getPageId();
	}
	
	/**
	 * 포탈 페이지 메인
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @param createSaveFlag 저장 플래그
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/readPageMain.do")
	public String readPageMain(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value="pageId", required=false) String pageId, @RequestParam(value="createSaveFlag", required=false) String createSaveFlag, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userLocaleCode = user.getLocaleCode();
		
		//페이지 아이디가 없는경우 처리
		String defPortalPageId = portalPageService.readPageId(portalId); 
		String tempPageId = pageId;
		
		if(StringUtil.isEmpty(pageId)) {
			tempPageId = defPortalPageId;
		}
		
		PortalMain portalDefaultMain = portalPageService.readPortletDefaultMain(user.getUserId(), userLocaleCode, tempPageId, user, portalId);
		
		model.addAttribute("portalDefaultMain", portalDefaultMain);
		model.addAttribute("createSaveFlag", createSaveFlag);
		model.addAttribute("defPortalPageId", defPortalPageId);
		
		return "portal/admin/screen/page/readPageMain";
	}
	
	/**
	 * 포탈 페이지 조회
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/readPage.do")
	public String readPage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String pageId, Model model) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userLocaleCode = user.getLocaleCode();
		
		PortalPage portalPage = portalPageService.readPage(pageId, userLocaleCode);
		
		// 다국어 조회
		portalPage.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, pageId, "pageName"));
		
		model.addAttribute("portalPage", portalPage);
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());
		model.addAttribute("defPortalPageId", portalPageService.readPageId(portalId));
		
		return "/portal/admin/screen/page/readPage";
	}
	
	/**
	 * 포탈 페이지 리스트
	 * 
	 * @param accessResult 권한체크 결과
	 * @param searchCondition 검색 조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listPage.do")
	public ModelAndView listPage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			PortalPageSearchCondition searchCondition) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/page/listPage");

		PortalCode portalCode = new PortalCode();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setFieldName("pageName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPageId(portalPageService.readPageId(portalId));
		searchCondition.setPortalId(portalId);
		
		SearchResult<PortalPage> searchResult = portalPageService.listPage(searchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("portalCode", portalCode);
		
		return mav;
	}
	
	/**
	 * 포탈 페이지 삭제
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/removePage.do", method = RequestMethod.POST)
	public String removePage(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String pageId) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		portalPageService.removePage(pageId);
		
		return "redirect:/portal/admin/screen/page/listPage.do";
	}
	
	/**
	 * 페이지 디폴트 포틀릿 등록
	 * 
	 * @param accessResult 권한체크 결과
	 * @param pageId 페이지 ID
	 * @param defLayoutData 디폴트 레이아웃 데이타
	 * @param defPortletData 디폴트 포틀릿 데이타
	 * @param commonLayoutData 공통 레이아웃 데이타 
	 * @param commonPortletData 공통 포틀릿 데이타
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPageDefLayoutPortlet.do", method = RequestMethod.POST)
	public @ResponseBody String createPageDefLayoutPortlet(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam String pageId, @RequestParam(value="defLayoutData", required=false) String defLayoutData, @RequestParam(value="defPortletData", required=false) String defPortletData 
			, @RequestParam(value="commonLayoutData", required=false) String commonLayoutData, @RequestParam(value="commonPortletData", required=false) String commonPortletData) throws JsonParseException, JsonMappingException, IOException {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		ObjectMapper mapper = new ObjectMapper();
		
		List<PortalDefPageLayout> defLayoutDataList = getDefLayoutDataList(defLayoutData, mapper);
		
		List<PortalDefPortletConfig> defPortletDataList = getDefPortletDataList(defPortletData, mapper);
		
		List<PortalPageLayout> commonLayoutDataList = getCommonLayoutDataList(commonLayoutData, mapper);
		
		List<PortalPortletConfig> commonPortletDataList = getCommonPortletDataList(commonPortletData, mapper);
		
		portalPageService.createPageDefLayoutPortlet(pageId, defLayoutDataList, defPortletDataList, commonLayoutDataList, commonPortletDataList, user);
		
		// 공용 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("commonPortlet");
		
		return "success";
	}
	
	@RequestMapping(value="/updateCommonPortletWidth.do", method=RequestMethod.POST)
	public @ResponseBody String updateCommonPortletWidth(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value="pageId") String pageId, @RequestParam(value="width") int width
			) {
		
		PortalPage portalPage = new PortalPage();
		portalPage.setPageId(pageId);
		portalPage.setCommonPortletVerticalWidth(width);
		
		portalPageService.updateCommonPortletWidth(portalPage);
		
		return "success";
	}
	
	/**
	 * 디폴트 레이아웃 리스트 
	 * @param defLayoutData 디폴트 레이아웃 데이타
	 * @param mapper mapper객체
	 * @return 디폴트 레이아웃 리스트 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private List<PortalDefPageLayout> getDefLayoutDataList(String defLayoutData, ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		List<PortalDefPageLayout> defLayoutDataList = new ArrayList<PortalDefPageLayout>();
		if(defLayoutData != null && !defLayoutData.equals("")) {
			String[] defLayoutDataCut = defLayoutData.split("\\^");
			
			for(int i = 0; i < defLayoutDataCut.length; i++) {
				PortalDefPageLayout portalDefPageLayout = mapper.readValue(defLayoutDataCut[i], PortalDefPageLayout.class);
				defLayoutDataList.add(portalDefPageLayout);
			}
		}
		
		return defLayoutDataList;
	}
	
	/**
	 * 디폴트 포틀릿 리스트
	 * @param defPortletData 디폴트 포틀릿 데이타
	 * @param mapper mapper객체
	 * @return 디폴트 포틀릿 리스트
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private List<PortalDefPortletConfig> getDefPortletDataList(String defPortletData, ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		List<PortalDefPortletConfig> defPortletDataList = new ArrayList<PortalDefPortletConfig>();  
		if(defPortletData != null && !defPortletData.equals("")) {
			String[] defPortletDataCut = defPortletData.split("\\^");
			
			for(int i = 0; i < defPortletDataCut.length; i++) {
				PortalDefPortletConfig portalDefPortletConfig = mapper.readValue(defPortletDataCut[i], PortalDefPortletConfig.class);
				defPortletDataList.add(portalDefPortletConfig);
			}
		}
		
		return defPortletDataList;
	}
	
	/**
	 * 공통 레이아웃 리스트
	 * @param commonLayoutData 공통 레이아웃 데이타
	 * @param mapper mapper객체
	 * @return 공통 레이아웃 리스트
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private List<PortalPageLayout> getCommonLayoutDataList(String commonLayoutData, ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		List<PortalPageLayout> commonLayoutDataList = new ArrayList<PortalPageLayout>();  
		if(commonLayoutData != null && !commonLayoutData.equals("")) {
			String[] commonLayoutDataCut = commonLayoutData.split("\\^");
			
			for(int i = 0; i < commonLayoutDataCut.length; i++) {
				PortalPageLayout portalPageLayout = mapper.readValue(commonLayoutDataCut[i], PortalPageLayout.class);
				commonLayoutDataList.add(portalPageLayout);
			}
		}
		
		return commonLayoutDataList;
	}
	
	/**
	 * 공통 포틀릿 리스트
	 * @param commonPortletData 공통 포틀릿 데이타
	 * @param mapper mapper객체
	 * @return 공통 포틀릿 리스트
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private List<PortalPortletConfig> getCommonPortletDataList(String commonPortletData, ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		List<PortalPortletConfig> commonPortletDataList = new ArrayList<PortalPortletConfig>();  
		if(commonPortletData != null && !commonPortletData.equals("")) {
			String[] commonPortletDataCut = commonPortletData.split("\\^");
			
			for(int i = 0; i < commonPortletDataCut.length; i++) {
				PortalPortletConfig portalPortletConfig = mapper.readValue(commonPortletDataCut[i], PortalPortletConfig.class);
				commonPortletDataList.add(portalPortletConfig);
			}
		}
		
		return commonPortletDataList;
	}
}