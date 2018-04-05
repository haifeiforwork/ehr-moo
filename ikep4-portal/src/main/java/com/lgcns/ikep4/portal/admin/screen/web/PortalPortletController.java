/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletCategoryService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 포탈 포틀릿 관리 Controller
 * 
 * @author 한승환
 * @version $Id: PortalPortletController.java 19462 2012-06-22 07:19:41Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/portlet")
public class PortalPortletController extends BaseController {

	@Autowired
	private PortalPortletService portalPortletService;

	@Autowired
	private PortalSystemService portalSystemMapService;

	@Autowired
	private PortalPortletCategoryService portalPortletCategoryService;

	@Autowired
	private I18nMessageService i18nMessageService;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private CacheService cacheService;

	public static final int SUPPORT = 1;

	/**
	 * 포틀릿 목록 조회 
	 * 
	 * @param accessResult 권한체크 결과
	 * @param searchCondition 포틀릿 검색조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listPortlet.do")
	public ModelAndView listPortlet(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			AdminSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("portal/admin/screen/portlet/listPortlet");

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<PortalPortlet> searchResult = portalPortletService.listPortalPortletByCondition(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);

		return mav;
	}

	/**
	 * 포틀릿 등록 폼 조회
	 * 
	 * @param accessResult 권한체크 결과
	 * @param model 포틀릿 검색조건
	 * @param searchCondition 포틀릿 검색조건
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPortletForm.do")
	public String createPortletForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model, AdminSearchCondition searchCondition) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		if (getModelAttribute("portalPortlet") == null) {
			PortalPortlet portalPortlet = new PortalPortlet();

			// ######## 로케일 입력을 위해 필요한 부분
			portalPortlet.setI18nMessageList(i18nMessageService.makeInitLocaleList("portletName,portletDesc"));

			// 등록화면의 Default Radio Button 값 셋팅
			portalPortlet.setMoveable(SUPPORT);
			portalPortlet.setReloadMode(SUPPORT);
			portalPortlet.setRemoveMode(SUPPORT);
			portalPortlet.setHeaderMode(SUPPORT);
			portalPortlet.setShareYn("N");
			portalPortlet.setCacheYn("N");

			// i18nMessage 생성
			model.addAttribute("portalPortlet", portalPortlet);
		}

		List<PortalSystem> portalSystemList = portalSystemMapService.portalSystemListView(portal.getPortalId());
		List<PortalPortletCategory> portalPortletCategoryList = portalPortletCategoryService
				.listPortletCategory(portalSystemList.get(0).getSystemCode());

		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();

		Properties prop = PropertyLoader.loadProperties("/configuration/portal-conf.properties");
		String cssPath = prop.getProperty("portal.admin.screen.portlet.cssPath");

		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());
		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("portalSystemList", portalSystemList);
		model.addAttribute("portalPortletCategoryList", portalPortletCategoryList);
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("cssPath", cssPath);

		return "portal/admin/screen/portlet/createPortletForm";
	}

	/**
	 * 포틀릿 등록 
	 * 
	 * @param model  모델객체
	 * @param accessResult 권한체크 결과
	 * @param file  파일객체
	 * @param portalPortlet 포틀릿 ValueObject
	 * @param result 바인딩 결과
	 * @param request Request 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPortlet.do")
	public String createPortlet(
			Model model,
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("file") CommonsMultipartFile file, @Valid PortalPortlet portalPortlet, BindingResult result,
			HttpServletRequest request)  throws IOException{

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "portalPortlet", result);
			return "forward:/portal/admin/screen/portlet/createPortletForm.do";
		}

		User user = (User) getRequestAttribute("ikep.user");

		/* start war file deploy */
		if (!portalPortlet.getPortletType().equals("HTML")) {

			/* start load portlet.xml */

			String baseUrl = "";
				
			List<PortalPortlet> portletList = portalPortletService.convertXmlToPortalPortlet(portalPortlet, file,
					baseUrl);
			
			// was deploy location 으로 파일 카피
			// portal-conf.properties >  portal.admin.screen.portlet.deployPath
			Properties prop = PropertyLoader.loadProperties("/configuration/portal-conf.properties");
			String deployPath = prop.getProperty("portal.admin.screen.portlet.deployPath");
			
			File folder = new File(deployPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String physicalFileName = file.getFileItem().getName();
			
			int beginIndex = physicalFileName.lastIndexOf('\\') == -1 ? physicalFileName.lastIndexOf('/')
					: physicalFileName.lastIndexOf('\\');
			String fileName = physicalFileName.substring(beginIndex + 1);

			File saveFile = new File(folder, fileName);
			file.transferTo(saveFile);
			

			/* upload file */
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");

			/* end war file deploy */

			String id = idgenService.getNextId();
			// 임의로 아이템 아이디 저장 fileid를 포틀릿에 저장함.
			List<FileData> uploadList = fileService.uploadFile(fileList, id,IKepConstant.ITEM_TYPE_CODE_PORTAL , user);

			String fileId = uploadList.get(0).getFileId();

			for (int i = 0; i < portletList.size(); i++) {
				
				PortalPortlet portalPortletTemp = new PortalPortlet();

				portalPortletTemp = portletList.get(i);

				portalPortletTemp.setPortletId(idgenService.getNextId());
				portalPortletTemp.setRegisterId(user.getUserId());
				portalPortletTemp.setRegisterName(user.getUserName());
				portalPortletTemp.setUpdaterId(user.getUserId());
				portalPortletTemp.setUpdaterName(user.getUserName());
				portalPortletTemp.setOwnerId(user.getUserId());

				portalPortletTemp.setWarFileId(fileId);

				portalPortletService.createPortalPortlet(portalPortletTemp);
			}

			return "redirect:/portal/admin/screen/portlet/listPortlet.do";
		}

		String id = idgenService.getNextId();

		portalPortlet.setPortletId(id);
		portalPortlet.setRegisterId(user.getUserId());
		portalPortlet.setRegisterName(user.getUserName());
		portalPortlet.setUpdaterId(user.getUserId());
		portalPortlet.setUpdaterName(user.getUserName());
		portalPortlet.setOwnerId(user.getUserId());

		portalPortletService.createPortalPortlet(portalPortlet);
		
		// 포탈 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("portlet");
		
		return "redirect:/portal/admin/screen/portlet/readPortlet.do?portletId=" + portalPortlet.getPortletId();
	}


	/**
	 * 포틀릿 수정 
	 * 
	 * @param model  모델객체
	 * @param accessResult 권한체크 결과
	 * @param portalPortlet 포틀릿 ValueObject
	 * @param result 바인딩 결과
	 * @param request Request 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updatePortlet.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonJsonView updatePortlet(
			Model model,
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalPortlet portalPortlet, 
			BindingResult result, 
			HttpServletRequest request)
			{

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource); 
		}

		String id = portalPortlet.getPortletId();

		// ######## 로케일 입력을 위해 필요한 부분

		User user = (User) getRequestAttribute("ikep.user");

		portalPortlet.setPortletId(id);
		portalPortlet.setUpdaterId(user.getUserId());
		portalPortlet.setUpdaterName(user.getUserName());

		portalPortletService.updatePortalPortlet(portalPortlet);
		
		// 포탈 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("portlet");
		
		// 포탈 공용 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("commonPortlet");
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("userPortlet");
		
		// 해당 포틀릿 컨텐츠 캐시 삭제
		cacheService.removeCachePortletContent(id);
		
		// 뷰페이지로 이동하기 위해 portletId값 셋팅
		MappingJacksonJsonView json = new MappingJacksonJsonView();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("portletId", portalPortlet.getPortletId());
		json.setAttributesMap(map);
		return json;
	}

	/**
	 * 포틀릿 조회 
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portletId  포틀릿 아이디
	 * @param searchCondition 포틀릿 검색조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readPortlet.do")
	public ModelAndView readPortlet(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("portletId") String portletId, 
			AdminSearchCondition searchCondition) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/screen/portlet/readPortlet");

		PortalPortlet portalPortlet = portalPortletService.readPortalPortlet(portletId);

		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();

		// 다국어 조회
		portalPortlet.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
				portletId, "portletName,portletDesc"));

		// 포틀릿 미리보기 CSS 경로
		Properties prop = PropertyLoader.loadProperties("/configuration/portal-conf.properties");
		String cssPath = prop.getProperty("portal.admin.screen.portlet.cssPath");

		mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		mav.addObject("portalPortlet", portalPortlet);
		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("searchCondition", searchCondition);
		mav.addObject("cssPath", cssPath);

		return mav;
	}

	/**
	 * 포틀릿 수정폼 조회
	 * 
	 * @param model 모델정보
	 * @param accessResult 권한체크 결과
	 * @param portletId  포틀릿 아이디
	 * @param searchCondition 포틀릿 검색조건
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updatePortletForm.do")
	public ModelAndView updatePortletForm(
			Model model,
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("portletId") String portletId, 
			AdminSearchCondition searchCondition) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/screen/portlet/updatePortletForm");

		PortalPortlet portalPortlet = portalPortletService.readPortalPortlet(portletId);

		// ######## 로케일 입력을 위해 필요한 부분
		portalPortlet.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
				portletId, "portletName,portletDesc"));

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		List<PortalSystem> portalSystemList = portalSystemMapService.portalSystemListView(portal.getPortalId());
		List<PortalPortletCategory> portalPortletCategoryList = portalPortletCategoryService
				.listPortletCategory(portalPortlet.getSystemCode());

		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();

		// 포틀릿 미리보기 CSS 경로
		Properties prop = PropertyLoader.loadProperties("/configuration/portal-conf.properties");
		String cssPath = prop.getProperty("portal.admin.screen.portlet.cssPath");

		mav.addObject("portalPortlet", portalPortlet);
		mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		mav.addObject("portalSystemList", portalSystemList);
		mav.addObject("portalPortletCategoryList", portalPortletCategoryList);
		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("searchCondition", searchCondition);
		mav.addObject("cssPath", cssPath);

		return mav;
	}

	/**
	 * 포틀릿 삭제
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portletId  포틀릿 아이디
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/deletePortlet.do")
	public String deletePortlet(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("portletId") String portletId)  {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException();
		}

		portalPortletService.deletePortalPortlet(portletId);
		
		// 포탈 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("portlet");
		
		// 포탈 공용 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("commonPortlet");
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 전체 삭제
		cacheService.removeCacheElementAll("userPortlet");
		
		// 해당 포틀릿 컨텐츠 캐시 삭제
		cacheService.removeCachePortletContent(portletId);

		return "redirect:/portal/admin/screen/portlet/listPortlet.do";

	}
	
	/**
	 * 포틀릿 content 체크
	 * @param portletId
	 * @return
	 */
	/*
	@RequestMapping(value = "/portletContentsCacheCheck.do")
	public @ResponseBody String portletContentsCacheCheck(String portletId, String portletConfigId) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		PortalPortlet portalPortlet = portalPortletService.readPortalPortlet(portletId);
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		String cacheMode = cacheprop.getProperty(StringUtil.nvl(portalPortlet.getCacheModeStr(), ""));
		
		//포틀릿 contents 캐시에서 조회
		Map<String, String> cacheParam = new HashMap<String, String>();
		cacheParam.put("cacheName", cacheprop.getProperty(StringUtil.nvl(portalPortlet.getCacheNameStr(), "")));
		cacheParam.put("elementKey", cacheprop.getProperty(StringUtil.nvl(portalPortlet.getElementKeyStr(), "")));
		cacheParam.put("portalId", portal.getPortalId());
		cacheParam.put("userId", user.getUserId());
		cacheParam.put("portletConfigId", portletConfigId);
		cacheParam.put("portalCacheYn", portal.getCacheYn());
		cacheParam.put("cacheMode", cacheMode);
		cacheParam.put("portletCacheYn", portalPortlet.getCacheYn());
		cacheParam.put("cacheMaxCount", portalPortlet.getCacheMaxCount());
		cacheParam.put("cacheLiveSecond", portalPortlet.getCacheLiveSecond());
		
		String cacheResult = (String) CacheUtil.cacheCheckPortletContent(cacheParam);
		
		if(StringUtil.isEmpty(cacheResult)) {
			if("Y".equals(portal.getCacheYn()) && ("1".equals(cacheMode) || "2".equals(cacheMode)) && "Y".equals(portalPortlet.getCacheYn())) {
				cacheResult = "";
			} else {
				cacheResult = "none";
			}
		}
		
		return cacheResult;
	}
	
	
	@RequestMapping(value = "/portletContentsAddCache.do", method = RequestMethod.POST)
	public @ResponseBody String portletContentsAddCache(String portletId, String portletConfigId, String portletContents) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		PortalPortlet portalPortlet = portalPortletService.readPortalPortlet(portletId);
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		
		// 캐시에 저장
		Map<String, String> cacheParam = new HashMap<String, String>();
		cacheParam.put("cacheName", cacheprop.getProperty(StringUtil.nvl(portalPortlet.getCacheNameStr(), "")));
		cacheParam.put("cacheMode", cacheprop.getProperty(StringUtil.nvl(portalPortlet.getCacheModeStr(), "")));
		cacheParam.put("elementKey", cacheprop.getProperty(StringUtil.nvl(portalPortlet.getElementKeyStr(), "")));
		cacheParam.put("portalId", portal.getPortalId());
		cacheParam.put("userId", user.getUserId());
		cacheParam.put("portletConfigId", portletConfigId);
		cacheParam.put("portalCacheYn", portal.getCacheYn());
		cacheParam.put("portletCacheYn", portalPortlet.getCacheYn());
		
		CacheUtil.addCacheElementPortletContent(cacheParam, portletContents);
		
		return "";
	}
	*/
}