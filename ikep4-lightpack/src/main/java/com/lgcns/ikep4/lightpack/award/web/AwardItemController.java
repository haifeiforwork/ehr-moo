/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItem.CreateItemGroup;
import com.lgcns.ikep4.lightpack.award.model.AwardItem.CreateReplyItemGroup;
import com.lgcns.ikep4.lightpack.award.model.AwardItem.ModifyItemGroup;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.model.AwardPermission;
import com.lgcns.ikep4.lightpack.award.model.AwardRecommend;
import com.lgcns.ikep4.lightpack.award.model.AwardReference;
import com.lgcns.ikep4.lightpack.award.search.AwardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemCategoryService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemReaderService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.searchpreprocessor.util.DateUtil;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeMaker;


/**
 * 게시글 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/award/awardItem")
public class AwardItemController extends BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The award admin service. */
	@Autowired
	private AwardAdminService awardAdminService;

	/** The award item service. */
	@Autowired
	private AwardItemService awardItemService;
	
	/** The award item service. */
	@Autowired
	private AwardItemDao awardItemDao;
	
	/** The award item service. */
	@Autowired
	private AwardItemCategoryService awardItemCategoryService;

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
    private UserDao userDao;

	@Autowired
	private AwardItemReaderService awardItemReaderService;
	
	private final static int DEFAULT_INTERVAL = 5;
	
	private final String SUFFIX = ".properties";

	private int interval = DEFAULT_INTERVAL;

	private static String fileUrl;
	
	private static final String DEFAULT_AWARD_ROOT_ID = "0";
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("BBS", user.getUserId());
	}
	
	public boolean isWritePermission(User user, String awardId) {
		return this.aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "WRITE", awardId, user.getUserId());
	}

	public boolean isReadPermission(User user, String awardId) {
		return this.aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "READ", awardId, user.getUserId());
	}
	
	public boolean isAdminPermission(User user, String awardId) {
		return this.aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "ADMIN", awardId, user.getUserId());
	}

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		return (User) this.getRequestAttribute("ikep.user");
	}

	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private Portal readPortal() {
		return (Portal) this.getRequestAttribute("ikep.portal");
	}

	private AwardPermission initPermission(User user, Award award) {
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Boolean isWritePermission = this.isWritePermission(user, award.getAwardId());

		Boolean isReadPermission = this.isReadPermission(user, award.getAwardId());
		
		Boolean isAdminPermission = this.isAdminPermission(user, award.getAwardId());

		// 관리자의 경우 쓰기 권한과 읽기 권한을 True 한다.
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
		} else if (isWritePermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		} 
		
		if (isAdminPermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		}

		// 게시판 읽기가 공개인 경우 읽기 권한을 True 한다.
		if (award.getReadPermission() == 1) {
			isReadPermission = Boolean.TRUE;

		}

		// 게시판 쓰기가 공개인 경우 쓰기, 읽기권한을 True 한다.
		if (award.getWritePermission() == 1) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		}
		
		//System.out.println("@@@@@@@@@@@@@@@@@isSystemAdmin:"+isSystemAdmin);
		//System.out.println("@@@@@@@@@@@@@@@@@isWritePermission:"+isWritePermission);
		//System.out.println("@@@@@@@@@@@@@@@@@isReadPermission:"+isReadPermission);
		
		return new AwardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}

	
	public static String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}
	
	
	/**
	 * 게시글 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listAwardItemView")
	public ModelAndView listAwardItemView(
			 AwardItemSearchCondition searchCondition,
			 @RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "isSystemAdmin", defaultValue = "false") Boolean isSystemAdmin,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode

	) {
		User user = this.readUser();
		isSystemAdmin = this.isSystemAdmin(user);
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin){
			searchCondition.setAdmin(isSystemAdmin);
		}
		
		
		

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin","searchStorageLocCd","searchCompanyCode","searchAwardKind","searchAwardGrade","searchAwardMaterial","startPeriod","endPeriod");
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@1:"+tempSearchConditionString);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@2:"+tempSearchConditionString);
		}
		String nowYear = getToday("yyyy");
		String nowMonth = getToday("MM");
		String nowDate = getToday("dd");
		if(searchCondition.getStartPeriod() == null){
			searchCondition.setStartPeriod(nowYear+"."+nowMonth+".01");
		}
		if(searchCondition.getEndPeriod() == null){
			searchCondition.setEndPeriod(nowYear+"."+nowMonth+"."+nowDate);
		}

		Portal portal = this.readPortal();

		// 게시판 관리 정보를 가져온다.
		Award award = this.awardAdminService.readAward2(searchCondition.getAwardId());
		searchCondition.setAwardAdmin(false);
		searchCondition.setAwardAdminUse(false);
		boolean awardAdminRole = false;
		if(award.getAdminPermission()==0){
			awardAdminRole = aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "ADMIN", award.getAwardId(), user.getUserId());
			searchCondition.setAwardAdminUse(true);
		}
		
		if(awardAdminRole){
			searchCondition.setAwardAdmin(awardAdminRole);
		}
		
		Boolean tempPopupYn = Boolean.FALSE;

		if (BooleanUtils.isTrue(popupYn)) {
			tempPopupYn = Boolean.TRUE;

		} else {
			tempPopupYn = Boolean.FALSE;
			tempPopupYn = award.getDocPopup() == 1;
		}

		searchCondition.setPopupYn(tempPopupYn);

		// Award 설정 정보에 보관되어 있는 정보 SearchCondition에 매핑
		if (searchCondition.getViewMode() == null) {
			// 게시판 정보에서 뷰모드 설정
			searchCondition.setViewMode(award.getListType());
			if(searchCondition.getPagePerRecord()==null){
				searchCondition.setPagePerRecord(award.getPageNum());
			}
		}

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

		Boolean createUserConfig = (userConfig != null);

		if (SearchCondition.ACTION_PAGE_PER_RECORD_CHANAGE.equals(searchCondition.getActionType())) {
			if (createUserConfig) {
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				this.userConfigService.updateUserConfig(userConfig);

			} else {
				userConfig = new UserConfig();

				// 개인화 설정 정보를 저장한다.
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 개인화 설정이 없으면 게시판설정의 페이지 정보를 저장한다.
		if (createUserConfig) {
			searchCondition.setPagePerRecord(userConfig.getPageCount() == null ? award.getPageNum() : userConfig
					.getPageCount());
			searchCondition.setLayoutType(userConfig.getLayout());
		} else {
			searchCondition.setPagePerRecord(award.getPageNum());
		}

		// 게시판 권한 정보를 설정한다.
		//AwardPermission awardPermission = this.initPermission(user, award);
		if(searchCondition.getAdmin()==null) searchCondition.setAdmin(false);
		
		AwardPermission awardPermission = new AwardPermission(searchCondition.getAdmin(), true, true);
		searchCondition.setUserId(user.getUserId());

		SearchResult<AwardItem> searchResult =null;
		if(!StringUtil.isEmpty(searchCondition.getSearchMaterial())){
			String[] tempWords = searchCondition.getSearchMaterial().split(",");
			List<String> tempWorkspaces = new ArrayList<String>();
			for(int i=0;i<tempWords.length;i++){
				tempWorkspaces.add(i, tempWords[i]);
			}
			searchCondition.setSearchMaterials(tempWorkspaces);
		}
		
		searchResult = this.awardItemService.listAwardItemBySearchCondition3(searchCondition);
		
		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(searchCondition.getAwardId());
		
		List<AwardItemCategory> awardItemCategoryList = null;
		awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);
		
		List<AwardItem> companyCodeList = awardItemService.listCode("C00015");
		List<AwardItem> awardKindList = awardItemService.listCode("C00009");
		List<AwardItem> awardGradeList = awardItemService.listCode("C00011");
		List<AwardItem> storageLocCdList = awardItemService.listCode("C00012");
		List<AwardItem> awardMaterialList = awardItemService.listCode("C00017");
		ModelAndView modelAndView = new ModelAndView().addObject("award", award).addObject("user", user)
				.addObject("awardCode", new BoardCode()).addObject("userConfig", userConfig)
				.addObject("itemId", itemId).addObject("permission", awardPermission)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", tempPopupYn).addObject("awardItemCategoryList", awardItemCategoryList)
				.addObject("awardAdminRole", awardAdminRole)
				.addObject("companyCodeList", companyCodeList)
				.addObject("awardKindList", awardKindList)
				.addObject("awardGradeList", awardGradeList)
				.addObject("storageLocCdList", storageLocCdList)
				.addObject("awardMaterialList", awardMaterialList)
				.addObject("userAuthMgntYn", awardItemService.userAuthMgntYn(user.getEmpNo()));
 
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/deleteAwardItemListView")
	public ModelAndView deleteAwardItemListView(
			 AwardItemSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode
	) {
		User user = this.readUser();
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin) searchCondition.setAdmin(isSystemAdmin);

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		

		Portal portal = this.readPortal();

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

		Boolean createUserConfig = (userConfig != null);

		if (SearchCondition.ACTION_PAGE_PER_RECORD_CHANAGE.equals(searchCondition.getActionType())) {
			if (createUserConfig) {
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				this.userConfigService.updateUserConfig(userConfig);

			} else {
				userConfig = new UserConfig();

				// 개인화 설정 정보를 저장한다.
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 게시판 권한 정보를 설정한다.
		SearchResult<AwardItem> searchResult =null;
		searchResult = this.awardItemService.deleteListAwardItemBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView().addObject("user", user)
				.addObject("userConfig", userConfig)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString);
 
		return modelAndView;
	}

	
	@RequestMapping(value = "/listReaderView")
	public ModelAndView listReaderView(AwardItemReaderSearchCondition searchCondition,
			@RequestParam(value = "awardItemId", required = false) String awardItemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString){
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardItemId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}
		SearchResult<AwardItemReader> searchResult = this.awardItemReaderService.listReaderBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("awardCode", new BoardCode());
		
		return modelAndView;
	}	
	
	@RequestMapping(value = "/listAwardView")
	public ModelAndView listAwardView(AwardItemReaderSearchCondition searchCondition,
			@RequestParam(value = "awardItemId", required = false) String awardItemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString){
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardItemId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}
		SearchResult<AwardItemReader> searchResult = this.awardItemReaderService.listAwardBySearchCondition(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView()
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition())
		.addObject("searchConditionString", tempSearchConditionString)
		.addObject("awardCode", new BoardCode());
		
		return modelAndView;
	}	
			
			
			
	/**
	 * 게시글 다른 파트에서 사용하게 위해서 awardId를 받지 않는 URL
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param popupYn the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readAwardItemLinkView")
	public ModelAndView readAwardItemLinkView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0") String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {

		Boolean temPopupYn = Boolean.TRUE;

		User user = this.readUser();

		AwardItem awardItem = this.awardItemService.readAwardItemMasterInfo(itemId);

		if (awardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.deletedItem");
		}

		Boolean hasAdminPermission = this.aclService.hasSystemPermission(Award.AWARD_PERMISSION_CLASS_NAME, "MANAGE",
				Award.AWARD_RESOURCE_NAME, user.getUserId());

		Boolean hasAwardPermission = this.aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, new String[] {
				"READ", "WRITE", "MANAGE" }, awardItem.getAwardId(), user.getUserId());

		AccessingResult accessResult = new AccessingResult();

		if (hasAdminPermission || hasAwardPermission) {
			accessResult.setResult(Boolean.TRUE);
		} else {
			accessResult.setResult(Boolean.FALSE);
		}

		ModelAndView modelAndView = this.readAwardItemView(awardItem.getAwardId(), accessResult, itemId, layoutType,
				null, temPopupYn, portletYn, viewMode);

		return modelAndView;
	}

	/**
	 * 게시글 상세조회 화면 컨트롤 메서드
	 * 
	 * @param itemId 상세조회 대상 게시글 ID
	 * @param layoutType the layout type
	 * @param popupYn the doc popup
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readAwardItemView")
	public ModelAndView readAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AwardItem awardItem = this.awardItemService.readAwardItem(itemId);

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());
		boolean awardAdminRole = false;
		if(award.getAdminPermission()==0){
			awardAdminRole = aclService.hasSystemPermission(Award.AWARD_ACL_CLASS_NAME, "ADMIN", award.getAwardId(), user.getUserId());
		}
		//System.out.println("##############################"+award.getContentsReadPermission());

		String readerView ="";
		if(award.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			AwardItemReader awardItemReader =new AwardItemReader();
			awardItemReader.setAwardItemId(awardItem.getItemId());
			awardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.awardItemReaderService.selectReaderCount(awardItemReader));
			if(this.awardItemReaderService.selectReaderCount(awardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.awardItemReaderService.readerFlag(awardItemReader));
				if( ( this.awardItemReaderService.readerFlag(awardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(awardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		// 조회 카운트를 증가 시킨다.
		AwardReference awardReference = new AwardReference();

		awardReference.setRegisterId(user.getUserId());
		awardReference.setItemId(itemId);

		this.awardItemService.updateHitCount(awardReference);
		
		

		if (awardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.deletedItem");
		}

		// 삭제 게시물에 대한 처리
		if (awardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.deletedItem");
		}

		// 게시기간이 지난 아이템의 처리
		if (getDayDiff(awardItem.getEndDate(), DateUtil.getToday()) < 0 && !this.isSystemAdmin(user)
				&& !awardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.expiredItem");
		}

		// 게시기간이 시작되지 않은 아이템의 처리
		if (getDayDiff(awardItem.getStartDate(), DateUtil.getToday()) > 0 && !this.isSystemAdmin(user)
				&& !awardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.preItem");
		}



		// 이동할 뷰를 선택한다.
		String viewName = "";

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "lightpack/award/awardItem/readAwardItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "lightpack/award/awardItem/readAwardItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "lightpack/award/awardItem/readAwardItemView";

		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		boolean ecmrole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(map1);
		if(ecmRole > 0){
			ecmrole = true;
		}
		List<AwardItem> awardMaterialList = awardItemService.listCode("C00017");
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("awardItem", awardItem)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("viewMode", viewMode).addObject("isFavorite", isFavorite)
				.addObject("mntrole", mntrole)
				.addObject("ecmrole", ecmrole)
				.addObject("awardAdminRole", awardAdminRole)
				.addObject("awardMaterialList", awardMaterialList)
				.addObject("userAuthMgntYn", awardItemService.userAuthMgntYn(user.getEmpNo()));

		if (popupYn) {
			modelAndView.setViewName("lightpack/award/awardItem/readAwardItemPopupView");
		}

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/readDeleteAwardItemView")
	public ModelAndView readDeleteAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AwardItem awardItem = this.awardItemService.readAwardItem(itemId);

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());
		
		//System.out.println("##############################"+award.getContentsReadPermission());

		String readerView ="";
		if(award.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			AwardItemReader awardItemReader =new AwardItemReader();
			awardItemReader.setAwardItemId(awardItem.getItemId());
			awardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.awardItemReaderService.selectReaderCount(awardItemReader));
			if(this.awardItemReaderService.selectReaderCount(awardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.awardItemReaderService.readerFlag(awardItemReader));
				if( ( this.awardItemReaderService.readerFlag(awardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(awardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		// 조회 카운트를 증가 시킨다.
		AwardReference awardReference = new AwardReference();

		awardReference.setRegisterId(user.getUserId());
		awardReference.setItemId(itemId);

		this.awardItemService.updateHitCount(awardReference);
		
		

		if (awardItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.deletedItem");
		}

		// 삭제 게시물에 대한 처리
		if (awardItem.getItemDelete() > 0 && !this.isSystemAdmin(user)) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.deletedItem");
		}

		// 게시기간이 지난 아이템의 처리
		if (getDayDiff(awardItem.getEndDate(), DateUtil.getToday()) < 0 && !this.isSystemAdmin(user)
				&& !awardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.expiredItem");
		}

		// 게시기간이 시작되지 않은 아이템의 처리
		if (getDayDiff(awardItem.getStartDate(), DateUtil.getToday()) > 0 && !this.isSystemAdmin(user)
				&& !awardItem.getRegisterId().equals(user.getUserId())) {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.awardItem.preItem");
		}



		// 이동할 뷰를 선택한다.
		String viewName = "lightpack/award/awardItem/readDeleteAwardItemView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		boolean ecmrole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(map1);
		if(ecmRole > 0){
			ecmrole = true;
		}
		
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("awardItem", awardItem)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("viewMode", viewMode).addObject("isFavorite", isFavorite)
				.addObject("mntrole", mntrole)
				.addObject("ecmrole", ecmrole);


		return modelAndView;
	}
	
	@RequestMapping(value = "/readAwardItemPrintView")
	public ModelAndView readAwardItemPrintView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"READ", "WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam(value = "portletYn", defaultValue = "false", required = false) Boolean portletYn,
			@RequestParam(value = "viewMode", defaultValue = "0", required = false) String viewMode)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		
		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		AwardItem awardItem = this.awardItemService.readAwardItem(itemId);

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());
		
		//System.out.println("##############################"+award.getContentsReadPermission());

		String readerView ="";
		if(award.getContentsReadPermission()==1){//독서자 설정 게시판이고 
			AwardItemReader awardItemReader =new AwardItemReader();
			awardItemReader.setAwardItemId(awardItem.getItemId());
			awardItemReader.setReaderId(user.getUserId());
			//System.out.println("##############################selectReaderCount:"+this.awardItemReaderService.selectReaderCount(awardItemReader));
			if(this.awardItemReaderService.selectReaderCount(awardItemReader)==0){//독서자 설정이 없으면 독서자 체크하지 않는다.
				readerView="hidden";
			}else{
				//System.out.println("##############################readerFlag:"+this.awardItemReaderService.readerFlag(awardItemReader));
				if( ( this.awardItemReaderService.readerFlag(awardItemReader)==0 ) && ( !isSystemAdmin ) && ( !(awardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 어드민, 등록자가 아니면
	
					throw new IKEP4AuthorizedException();
				}
			}
		}
		
		



		// 이동할 뷰를 선택한다.
		String viewName = "";

		viewName = "lightpack/award/awardItem/readAwardItemPrintView";

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		
		boolean mntrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "MNT");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			mntrole = true;
		}

		
		ModelAndView modelAndView = new ModelAndView(viewName).addObject("awardItem", awardItem)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("layoutType", layoutType).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("portletYn", portletYn).addObject("fileDataListJson", fileDataListJson)
				.addObject("readerView", readerView)
				.addObject("mntrole", mntrole);

		if (popupYn) {
			modelAndView.setViewName("lightpack/award/awardItem/readAwardItemPopupView");
		}

		return modelAndView;
	}


	/**
	 * 게시글 생성 화면 컨트롤 메서드
	 * 
	 * @param awardId 게시판 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createAwardItemView")
	public ModelAndView createAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn)
			throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		AwardItem awardItem = null;

		if (this.getModelAttribute("awardItem") == null) {
			Date date = Calendar.getInstance().getTime();

			awardItem = new AwardItem();
			awardItem.setAwardId(awardId);
			awardItem.setStartDate(date);
			awardItem.setToDate(date);

			Properties awardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

			// #게시기간 단위 설정 (year,month,day)
			String itemPeriodType = awardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
			// #게시기간 설정
			int itemPeriod = Integer.valueOf(awardprop.getProperty("lightpack.board.boardItem.itemPeriod"));

			if ("year".equals(itemPeriodType)) {
				awardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));
			} else if ("month".equals(itemPeriodType)) {
				awardItem.setEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			} else if ("day".equals(itemPeriodType)) {
				awardItem.setEndDate(DateUtils.addDays(date, itemPeriod));
			} else {
				awardItem.setEndDate(DateUtils.addYears(date, 1));
			}
			
			awardItem.setDisStartDate(date);
			awardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			

		} else {
			awardItem = (AwardItem) this.getModelAttribute("awardItem");
		}

		Award award = this.awardAdminService.readAward(awardId);

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}	

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(awardId);
		
		boolean ecmrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ECM");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			ecmrole = true;
		}
		String viewName = "";

		if(ecmrole){
			viewName = "lightpack/award/awardItem/createAwardEcmItemView";
		}else{
			viewName = "lightpack/award/awardItem/createAwardDextItemView";
		}
		
		List<AwardItem> companyCodeList = awardItemService.listCode("C00015");
		List<AwardItem> awardKindList = awardItemService.listCode("C00009");
		List<AwardItem> awardGradeList = awardItemService.listCode("C00011");
		List<AwardItem> storageLocCdList = awardItemService.listCode("C00012");
		List<AwardItem> awardMaterialList = awardItemService.listCode("C00017");
		
		List<AwardItemCategory> awardItemCategoryList = null;
			awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);
		return this.bindResult(new ModelAndView(viewName).addObject("awardItem", awardItem).addObject("user", user)
				.addObject("permission", this.initPermission(user, award))
				.addObject("fileDataListJson", fileDataListJson)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("ecmrole", ecmrole)
				.addObject("companyCodeList", companyCodeList)
				.addObject("awardKindList", awardKindList)
				.addObject("awardGradeList", awardGradeList)
				.addObject("storageLocCdList", storageLocCdList)
				.addObject("awardMaterialList", awardMaterialList)
				.addObject("useActiveX", useActiveX).addObject("award", award).addObject("awardItemCategoryList",awardItemCategoryList));
				//.addObject("useActiveX", useActiveX).addObject("award", award).addObject("awardItemCategoryList",awardItemCategoryList));
	}

	/**
	 * 게시글 수정 화면 컨트롤 메서드
	 * 
	 * @param itemId 수정대상 게시글 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateAwardItemView")
	public ModelAndView updateAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		Properties awardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

		// #게시기간 단위 설정 (year,month,day)
		String itemPeriodType = awardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
		// #게시기간 설정
		int itemPeriod = Integer.valueOf(awardprop.getProperty("lightpack.board.boardItem.itemPeriod"));
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		AwardItem awardItem = null;

		if (this.getModelAttribute("award") == null) {
			awardItem = this.awardItemService.readAwardItem(itemId);

		} else {
			awardItem = (AwardItem) this.getModelAttribute("awardItem");
		}
		List<String> materialList = null;
		materialList = awardItemService.materialList(itemId);
		Date date = Calendar.getInstance().getTime();
		awardItem.setToDate(date);
		
		if(awardItem.getDisEndDate() == null || awardItem.getItemDisplay() == 0){
			awardItem.setDisStartDate(date);
			awardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
		}

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(awardId);
		
		List<AwardItemCategory> awardItemCategoryList = null;
			awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);
			
			boolean ecmrole = false;
			Map<String, String> emap = new HashMap<String, String>();
			emap.put("userId", user.getUserId());
			emap.put("roleName", "ECM");
			int ecmRole = userDao.getUserRoleCheck(emap);
			if(ecmRole > 0){
				ecmrole = true;
			}
		
		String viewName = "";

		if(ecmrole){
			viewName = "lightpack/award/awardItem/updateAwardEcmItemView";
		}else{
			viewName = "lightpack/award/awardItem/updateAwardDextItemView";
		}
	
		List<AwardItem> companyCodeList = awardItemService.listCode("C00015");
		List<AwardItem> awardKindList = awardItemService.listCode("C00009");
		List<AwardItem> awardGradeList = awardItemService.listCode("C00011");
		List<AwardItem> storageLocCdList = awardItemService.listCode("C00012");
		List<AwardItem> awardMaterialList = awardItemService.listCode("C00017");
		
		return this.bindResult(new ModelAndView(viewName).addObject("awardItem", awardItem).addObject("user", user)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson)
				.addObject("awardItemCategoryList",awardItemCategoryList))
				.addObject("companyCodeList", companyCodeList)
				.addObject("awardKindList", awardKindList)
				.addObject("awardGradeList", awardGradeList)
				.addObject("storageLocCdList", storageLocCdList)
				.addObject("awardMaterialList", awardMaterialList)
				.addObject("ecmrole", ecmrole).addObject("materialList", materialList)
				.addObject("userAuthMgntYn", awardItemService.userAuthMgntYn(user.getEmpNo()));
	}

	
	@RequestMapping(value = "/updateDeleteAwardItemView")
	public ModelAndView updateDeleteAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		Properties awardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

		// #게시기간 단위 설정 (year,month,day)
		String itemPeriodType = awardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
		// #게시기간 설정
		int itemPeriod = Integer.valueOf(awardprop.getProperty("lightpack.board.boardItem.itemPeriod"));
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		AwardItem awardItem = null;

		if (this.getModelAttribute("award") == null) {
			awardItem = this.awardItemService.readAwardItem(itemId);

		} else {
			awardItem = (AwardItem) this.getModelAttribute("awardItem");
		}
		Date date = Calendar.getInstance().getTime();
		awardItem.setToDate(date);
		
		if(awardItem.getDisEndDate() == null || awardItem.getItemDisplay() == 0){
			awardItem.setDisStartDate(date);
			awardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
		}
		
		awardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		AwardItemCategory categoryAwardId = new AwardItemCategory();
		categoryAwardId.setAwardId(awardId);
		
		List<AwardItemCategory> awardItemCategoryList = null;
			awardItemCategoryList = this.awardItemCategoryService.listCategoryAwardItem(categoryAwardId);
			
			boolean ecmrole = false;
			Map<String, String> emap = new HashMap<String, String>();
			emap.put("userId", user.getUserId());
			emap.put("roleName", "ECM");
			int ecmRole = userDao.getUserRoleCheck(emap);
			if(ecmRole > 0){
				ecmrole = true;
			}
		
		return this.bindResult(new ModelAndView().addObject("awardItem", awardItem).addObject("user", user)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson)
				.addObject("awardItemCategoryList",awardItemCategoryList))
				.addObject("ecmrole", ecmrole);
	}
	/**
	 * 답글 생성 화면 컨트롤 메서드
	 * 
	 * @param itemId 원본 게시글 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createReplyAwardItemView")
	public ModelAndView createReplyAwardItemView(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		AwardItem awardItem = null;

		if (this.getModelAttribute("awardItem") == null) {
			awardItem = this.awardItemService.readAwardItem(itemId);
			awardItem.setFileLinkList(null);
			awardItem.setEditorFileLinkList(null);
			awardItem.setFileDataList(null);
			awardItem.setEditorFileDataList(null);

		} else {
			awardItem = (AwardItem) this.getModelAttribute("awardItem");

		}

		Award award = this.awardAdminService.readAward(awardItem.getAwardId());

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (awardItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(awardItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return this.bindResult(new ModelAndView().addObject("awardItem", awardItem).addObject("user", user)
				.addObject("award", award).addObject("permission", this.initPermission(user, award))
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson));
	}

	
	
	 
	  @RequestMapping("/getMailControl.do")
	  public @ResponseBody Map<String, Object> 
	getWaitTime() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int mailWaitTime = -1;
			int mailWaitCount = this.awardItemService.getMailWaitCount();
			if(mailWaitCount > 0){
				mailWaitTime = this.awardItemService.getMailWaitTime();
			}
			result.put("mailWaitTime", mailWaitTime);
			result.put("success", "success");
		} catch(Exception ex) {
			result.put("success", "fail");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	  
	
	/**
	 * 게시글 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param awardItem 게시글 생성 화면에서 전달된 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	
	@RequestMapping(value = "/createAwardItem")
	public ModelAndView createAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @ValidEx(groups = { CreateItemGroup.class }) AwardItem awardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("awardItem", awardItem, result);
			return new ModelAndView("forward:/lightpack/award/awardItem/createAwardItemView.do?awardId="
					+ awardItem.getAwardId());
		}

		User user = this.readUser();

		awardItem.setPortalId(user.getPortalId());

		if(awardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), awardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), user.getUserName());
		}
		// 타임존 처리
		//Date startDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getStartDate());
		//Date endDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getEndDate());

		//endDate = DateUtils.addDays(endDate, 1);
		//endDate = DateUtils.addSeconds(endDate, -1);

		//awardItem.setStartDate(startDate);
		
		//if(awardItem.getItemForever()==1){
		//	String[] patterns= {"yyyyMMdd"};
		try {
			Date date = Calendar.getInstance().getTime();
			awardItem.setStartDate(date);
			awardItem.setItemForever(1);
			String[] patterns= {"yyyyMMdd"};
			awardItem.setEndDate(DateUtils.parseDate("99991231",patterns ));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}else{
		//	awardItem.setEndDate(endDate);
		//}
		if(awardItem.getDisStartDate()!=null){
			Date disStartDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getDisStartDate());
			Date disEndDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getDisEndDate());
			awardItem.setDisStartDate(disStartDate);
			awardItem.setDisEndDate(disEndDate);
		}

		awardItem.setItemDelete(AwardItem.STATUS_SERVICING);
		String wordId   = awardItem.getWordId();
		String wordName = awardItem.getWordName();

		awardItem.setWordId(wordId);
		awardItem.setWordName(wordName);
		awardItem.setDisplayName(awardItem.getRegisterName());
		
		String itemId = this.awardItemService.createAwardItem(awardItem, user);
		
		awardItem.setItemId(itemId);
		awardItemService.saveAwardItemMaterial(awardItem);
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");

		status.setComplete();

		String viewName = null;

		if (awardItem.getPopupYn()) {
			viewName = "redirect:/lightpack/award/awardItem/listAwardItemView.do";
			if("1".equals(awardItem.getTempSave())) {
				viewName = "redirect:/basicpack/award/awardItem/listTempSaveItemView.do";
			}

		} else {
			viewName = "redirect:/lightpack/award/awardItem/readAwardItemView.do";
		}

		return new ModelAndView(viewName).addObject("awardId", awardItem.getAwardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", awardItem.getSearchConditionString())
				.addObject("popupYn", awardItem.getPopupYn());
	}

	
	
	@RequestMapping(value = "/listTempSaveItemView")
	public ModelAndView listTempSaveItemView(@RequestParam(value = "searchConditionString", required = false) String searchConditionString, 	@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn, AwardItemSearchCondition searchCondition) {
		
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardId",
					"pagePerRecord", "pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();
		Portal portal = this.readPortal();
		
		searchCondition.setUserId(user.getUserId());
		Boolean tempPopupYn = Boolean.FALSE;

		if (BooleanUtils.isTrue(popupYn)) {
			tempPopupYn = Boolean.TRUE;

		} else {
			tempPopupYn = Boolean.FALSE;
		}

		searchCondition.setPopupYn(tempPopupYn);
	
		SearchResult<AwardItem> searchResult = this.awardItemService.listTempSaveItemBySearchCondition(searchCondition);

		return new ModelAndView().addObject("searchResult", searchResult)
								 .addObject("searchCondition", searchResult.getSearchCondition())
								 .addObject("searchConditionString", tempSearchConditionString)
								 .addObject("popupYn", tempPopupYn)
								 .addObject("awardCode", new BoardCode());
	}
	
	
	/**
	 * 답글 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param awardItem 답변 생성 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param awardItem the award item
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createReplyAwardItem")
	public ModelAndView createReplyAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @ValidEx(groups = { CreateReplyItemGroup.class }) AwardItem awardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("awardItem", awardItem, result);
			return new ModelAndView("forward:/lightpack/award/awardItem/createReplyAwardItemView.do?itemId="
					+ awardItem.getItemParentId());
		}

		User user = this.readUser();

		if(awardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), awardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), user.getUserName());
		}

		AwardItem parent = this.awardItemService.readAwardItemMasterInfo(awardItem.getItemParentId());

		awardItem.setStartDate(parent.getStartDate());
		awardItem.setEndDate(parent.getEndDate());
		awardItem.setItemDisplay(parent.getItemDisplay());
		awardItem.setItemDelete(AwardItem.STATUS_SERVICING);

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		awardItem.setPortalId(user.getPortalId());
		awardItem.setDisplayName(awardItem.getRegisterName());

		String itemId = this.awardItemService.createReplyAwardItem(awardItem, user);
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardItem/readAwardItemView.do")
				.addObject("awardId", awardItem.getAwardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", awardItem.getSearchConditionString())
				.addObject("popupYn", awardItem.getPopupYn());
	}

	/**
	 * 게시글 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param awardItem 게시글 수정 화면에서 전달된 답변 게시글 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateAwardItem")
	public ModelAndView updateAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @ValidEx(groups = { ModifyItemGroup.class }) AwardItem awardItem,
			AccessingResult accessResult, BindingResult result, SessionStatus status) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			this.setErrorAttribute("awardItem", awardItem, result);
			return new ModelAndView("forward:/lightpack/award/awardItem/updateAwardItemView.do?itemId="
					+ awardItem.getItemId());
		}

		User user = this.readUser();

		if(awardItem.getAnonymous()==1){
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), awardItem.getRegisterName());
		}else{
			ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), user.getUserName());
		}
		
		if(StringUtil.isEmpty(awardItem.getItemParentId())){
			// 타임존 처리
			//Date startDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getStartDate());
			//Date endDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getEndDate());
	
			//endDate = DateUtils.addDays(endDate, 1);
			//endDate = DateUtils.addSeconds(endDate, -1);
	
			
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+awardItem.getTempSave());
			
			
			//awardItem.setStartDate(startDate);
			//if(awardItem.getItemForever()==1){
				String[] patterns= {"yyyyMMdd"};
				try {
					Date date = Calendar.getInstance().getTime();
					awardItem.setStartDate(date);
					awardItem.setItemForever(1);
					awardItem.setEndDate(DateUtils.parseDate("99991231",patterns ));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}else{
			//	awardItem.setEndDate(endDate);
			//}
		}
		if(awardItem.getDisStartDate()!=null){
			Date disStartDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getDisStartDate());
			Date disEndDate = this.timeZoneSupportService.convertServerTimeZone(awardItem.getDisEndDate());
			awardItem.setDisStartDate(disStartDate);
			awardItem.setDisEndDate(disEndDate);
		}
		
		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		awardItem.setPortalId(user.getPortalId());
		
		String wordId   = awardItem.getWordId();
		String wordName = awardItem.getWordName();

		awardItem.setWordId(wordId);
		awardItem.setWordName(wordName);
		
		awardItem.setDisplayName(awardItem.getUpdaterName());

		this.awardItemService.updateAwardItem(awardItem, user);
		
		awardItemService.saveAwardItemMaterial(awardItem);
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");

		status.setComplete();

		return new ModelAndView("redirect:/lightpack/award/awardItem/readAwardItemView.do")
				.addObject("awardId", awardItem.getAwardId()).addObject("itemId", awardItem.getItemId())
				.addObject("searchConditionString", awardItem.getSearchConditionString())
				.addObject("popupYn", awardItem.getPopupYn());
	}

	/**
	 * 게시글 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param itemId 삭제 대상 게시글 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userDeleteAwardItem")
	public ModelAndView userDeleteAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = {
							"WRITE", "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		User user = this.readUser();

		AwardItem awardItem = this.awardItemService.readAwardItemMasterInfo(itemId);

		ModelBeanUtil.bindRegisterInfo(awardItem, user.getUserId(), user.getUserName());

		this.awardItemService.userDeleteAwardItem(awardItem);

		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");
		
		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/award/awardItem/awardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/award/awardItem/listAwardItemView.do";
		}

		return new ModelAndView(viewName).addObject("awardId", awardItem.getAwardId())
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 일괄삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param itemIds 삭제대상 게시글 ID 배열
	 */
	@RequestMapping(value = "/adminMultiDeleteAwardItem")
	public @ResponseBody
	void adminMultiDeleteAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {

		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		try {
			this.awardItemService.adminMultiDeleteAwardItem(itemIds);
			
			// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	/**
	 * 게시글 관리자모드 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param itemId the item id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/adminDeleteAwardItem")
	public ModelAndView adminDeleteAwardItem(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			@RequestParam("itemId") String itemId, AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", required = false) Boolean popupYn) {
		// 게시글 조회의 권한이 있는지 없는지 확인
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		AwardItem awardItem = this.awardItemService.readAwardItem(itemId);

		this.awardItemService.adminDeleteAwardItem(awardItem);
		
		// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-recentAward-portlet");

		String viewName = null;

		if (popupYn) {
			viewName = "redirect:/lightpack/award/awardItem/awardItemResult.do";

		} else {
			viewName = "redirect:/lightpack/award/awardItem/listAwardItemView.do";
		}

		return new ModelAndView(viewName).addObject("awardId", awardItem.getAwardId())
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 삭제의 경우 팝업 모드일 경우 삭제 후 부모 창에 있는 게시글목록을 갱신해야 하는데 사용하는 URL
	 * 
	 * @param itemId the item id
	 * @return ModelAndView
	 */
	
	@RequestMapping(value = "/mailSending")
	public @ResponseBody
	Integer mailSending(@RequestParam("itemId") String itemId) {
		Integer mailWaitYn = null;

		try {
			mailWaitYn = this.awardItemService.getMailWaitYn(itemId);
			if(mailWaitYn > 0){
				awardItemService.updateMailWaitYn(itemId);
				try {
					Date jobTime = new Date();
					
					if(this.fileUrl == null) {
						Properties prop = loadProperties("/configuration/common-conf.properties", Thread.currentThread().getContextClassLoader());
						Properties prop2 = loadProperties("/configuration/fileupload-conf.properties", Thread.currentThread().getContextClassLoader());
						this.fileUrl = prop.getProperty("ikep4.baseUrl") + prop2.getProperty("ikep4.support.fileupload.downloadurl");
					}
					//awardItemService.doAwardItemNotiJobSchedule(jobTime, interval, fileUrl);
					awardItemService.doAwardItemNotiInstant(itemId);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage());
					throw new IKEP4ApplicationException("", e);
				}
			}
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return mailWaitYn;

	}
	
	
	@RequestMapping(value = "/awardItemResult")
	public ModelAndView awardItemResult(@RequestParam("awardId") String awardId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn) {

		return new ModelAndView().addObject("awardId", awardId)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn);
	}

	/**
	 * 게시글 추천 비동기 컨트롤 메서드
	 * 
	 * @param itemId 추천 게시글 ID
	 * @return the integer
	 */
	@RequestMapping(value = "/updateRecommendCount")
	public @ResponseBody
	Integer updateRecommendCount(@RequestParam("itemId") String itemId) {
		Integer currentRecommendCount = null;

		try {
			User user = (User) this.getRequestAttribute("ikep.user");

			// 게시물 추천을 한다.
			AwardRecommend awardRecommend = new AwardRecommend();

			awardRecommend.setItemId(itemId);
			awardRecommend.setRegisterId(user.getUserId());

			// 이미 존재하면 -1를 리턴한다.
			if (this.awardItemService.exsitRecommend(awardRecommend)) {
				currentRecommendCount = -1;
			} else {

				this.awardItemService.updateRecommendCount(awardRecommend);

				currentRecommendCount = this.awardItemService.readAwardItem(itemId).getRecommendCount();
			}
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return currentRecommendCount;

	}

	/**
	 * 개인화 설정 레이아웃 모드 설정
	 * 
	 * @param awardId 게시판 ID
	 * @param layout 레이아웃
	 */
	@RequestMapping(value = "/updateUserConfigLayout")
	public @ResponseBody
	void updateUserConfigLayout(@RequestParam("awardId") String awardId, @RequestParam("layoutType") String layoutType) {

		User user = this.readUser();

		Portal portal = this.readPortal();

		try {
			// 개인화 설정 정보를 불러온다.
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
					IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

			if (userConfig == null) {
				userConfig = new UserConfig();
				// 개인화 설정 정보를 저장한다.
				userConfig.setLayout(layoutType);
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);
			} else {
				userConfig.setLayout(layoutType);
				this.userConfigService.updateUserConfig(userConfig);

			}

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	/**
	 * 개인화 설정 레이아웃 모드 설정
	 * 
	 * @param awardId 게시판 ID
	 * @param layout 레이아웃
	 */
	@RequestMapping(value = "/listRecentAwardItem")
	public @ResponseBody
	List<AwardItem> updateUserConfigLayout(@RequestParam("awardId") String awardId, @RequestParam("count") Integer count) {
		List<AwardItem> awardItemList = null;

		try {
			User user = (User)getRequestAttribute("ikep.user");		
			
			AwardItemSearchCondition searchCondition = new AwardItemSearchCondition();

			searchCondition.setUserId(user.getUserId());
			searchCondition.setAwardId(awardId);
			searchCondition.setRecordCount(count);
			
			Award award = this.awardAdminService.readAward(searchCondition.getAwardId());
			
			AwardPermission awardPermission = this.initPermission(user, award);
		
			searchCondition.setAdmin(awardPermission.getIsSystemAdmin());		
			
			awardItemList = this.awardItemService.listRecentAwardItem(searchCondition);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return awardItemList;
	}

	/**
	 * 메일 뷰 개수 업데이트
	 * 
	 * @param itemId Idea ID
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMailCount(@RequestParam("itemId") String itemId) {

		try {
			awardItemService.updateMailCount(itemId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 블로그 뷰 개수 업데이트
	 * 
	 * @param itemId Idea ID
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMblogCount(@RequestParam("itemId") String itemId) {

		try {
			awardItemService.updateMblogCount(itemId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	private static long MILLSECS_PER_DAY = 1000 * 60 * 60 * 24;

	public static int getDayDiff(Date a, Date b) {
		long d = DateUtils.truncate(a, Calendar.DATE).getTime() - DateUtils.truncate(b, Calendar.DATE).getTime();
		return (int) (d / MILLSECS_PER_DAY);
	}
	
	/**
	 * 게시물 이동시 게시판 Tree 팝업
	 * 
	 * @param workspaceId
	 * @param orgAwardId
	 * @return
	 */
	@RequestMapping(value = "/viewAwardTree")
	public ModelAndView viewAwardTree(@RequestParam("awardParentId") String awardParentId,
			@RequestParam("orgAwardId") String orgAwardId) {
		String awardTreeJson = awardTreeJson();

		ModelAndView model = new ModelAndView();
		model.setViewName("lightpack/award/awardItem/viewAwardTree");
		model.addObject("orgAwardId", orgAwardId);
		model.addObject("awardTreeJson", awardTreeJson);
		return model;

	}
	
	/**
	 * 게시판 Tree Json
	 * 
	 * @param workspaceId
	 * @return
	 */
	private String awardTreeJson() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("awardRootId", DEFAULT_AWARD_ROOT_ID);

		List<Award> awardList = awardAdminService.listByAwardRootIdMap(map);
		return TreeMaker.init(awardList, "awardId", "awardParentId", "awardName").create().toJsonString();

	}
	
	/* 5/17 WS 추가 */
	/**
	 * 게시물 이동처리
	 */
	@RequestMapping(value = "/moveAwardItem")
	public @ResponseBody
	void moveAwardItem(@RequestParam("orgAwardId") String orgAwardId,
			@RequestParam("targetAwardId") String targetAwardId, @RequestParam("itemIds") String[] itemIds) {
		User user = (User) getRequestAttribute("ikep.user");

		try {
			this.awardItemService.moveAwardItem(orgAwardId, targetAwardId, itemIds, user);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}
	
	private Properties loadProperties(String nameParam, ClassLoader loaderParam) {

		String name = nameParam;
		ClassLoader loader = loaderParam;

		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}

		Properties result = null;

		InputStream in = null;

		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}

			name = name.replace('.', '/');

			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}

			in = loader.getResourceAsStream(name);

			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
					// 예외 무시
					log.error("InputStream of Property close error", ignore);
				}
			}
		}

		if (result == null) {
			throw new IllegalArgumentException("Could not load [" + name + "] as a classloader resource");
		}

		return result;
	}
	
	private String getAwardTypeFlag(String tempSave, Date startDate) {
		String AwardTypeFlag = "";
		
		if("1".equals(tempSave)) {
			AwardTypeFlag = "T";
		} 
//		  else if(startDate != null){
//			Date todayDate = new Date();
//			
//			String todayDateString = com.lgcns.ikep4.common.util.lang.DateUtil.getFmtDateString(todayDate, "yyyyMMddHHmmss");
//			String startDateString = com.lgcns.ikep4.common.util.lang.DateUtil.getFmtDateString(startDate, "yyyyMMddHHmmss");
//			
//			if(Long.parseLong(startDateString) > Long.parseLong(todayDateString)) {
//				AwardTypeFlag = "R";
//			}
//		}
		
		return AwardTypeFlag;
	}
	
	/**
	 * 모바일 게시판 메뉴 관리
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAwardMenuList")
	public ModelAndView getAwardMenuList(
			 AwardItemSearchCondition searchCondition,
			 @RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false") Boolean popupYn,
			@RequestParam(value = "isSystemAdmin", defaultValue = "false") Boolean isSystemAdmin,
			@CookieValue(value = "viewMode", defaultValue = "") String viewMode

	) {
		
		if(!"".equals(viewMode)) searchCondition.setViewMode(viewMode);
		String tempSearchConditionString = null;
		if(isSystemAdmin) searchCondition.setAdmin(isSystemAdmin);

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition, "awardId",
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType", "viewMode", "popupYn", "wordId", "admin");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}

		User user = this.readUser();

		Portal portal = this.readPortal();

		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				IKepConstant.ITEM_TYPE_CODE_BBS, portal.getPortalId());

		Boolean createUserConfig = (userConfig != null);

		if (SearchCondition.ACTION_PAGE_PER_RECORD_CHANAGE.equals(searchCondition.getActionType())) {
			if (createUserConfig) {
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				this.userConfigService.updateUserConfig(userConfig);

			} else {
				userConfig = new UserConfig();

				// 개인화 설정 정보를 저장한다.
				userConfig.setPageCount(searchCondition.getPagePerRecord());
				userConfig.setPortalId(this.readPortal().getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId(IKepConstant.ITEM_TYPE_CODE_BBS);

				this.userConfigService.createUserConfig(userConfig);

				createUserConfig = Boolean.TRUE;
			}
		}

		// 게시판 권한 정보를 설정한다.
		if(searchCondition.getAdmin()==null) searchCondition.setAdmin(false);
		
		AwardPermission awardPermission = new AwardPermission(searchCondition.getAdmin(), true, true);
		searchCondition.setUserId(user.getUserId());

		SearchResult<AwardItem> searchResult =null;
		searchResult = this.awardItemService.getAwardMenuList(searchCondition);
		
		
		// #########################임시로........... 해논 구간.#########################
		List<Award> awardList = this.awardAdminService.listByAwardRootIdPermission("0", this.readPortal().getPortalId(),user.getUserId());
		List<AwardItem> awardItemList = new ArrayList<AwardItem>();
		for(Award entity : awardList) {
			AwardItem awardItem = new AwardItem();
			if(!"2".equals(entity.getAwardType())) {
				awardItem.setAwardId(entity.getAwardId());
				awardItem.setAwardName(entity.getAwardName());
				awardItemList.add(awardItem);
			} 
		}
		List<AwardItem> presentAwardItemList = this.awardItemDao.getPresentAwardMenuList(searchCondition.getUserId());
		for(AwardItem awardItem : awardItemList) {
			for(AwardItem awardItem1 : presentAwardItemList) {
				if(awardItem.getAwardId().equals(awardItem1.getAwardId())) {
					awardItem.setTempSave("1");
				}
			}
		}
		searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		//######################### 여기까지 #########################
		
		
		ModelAndView modelAndView = new ModelAndView().addObject("user", user)
				.addObject("awardCode", new BoardCode()).addObject("userConfig", userConfig)
				.addObject("itemId", itemId).addObject("permission", awardPermission)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult).addObject("searchConditionString", tempSearchConditionString)
				.addObject("popupYn", Boolean.FALSE);
 
		return modelAndView;
	}
	
	/**
	 * 모바일 게시판 메뉴 관리 UPDATE
	 * 
	 * @param searchCondition 게시글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 */
	@RequestMapping(value = "/updateAwardMenuList")
	public @ResponseBody
	void updateAwardMenuList(
			@IsAccess({
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_PERMISSION_CLASS_NAME, operationName = { "MANAGE" }, resourceName = Award.AWARD_RESOURCE_NAME),
					@Attribute(type = AccessType.SYSTEM, className = Award.AWARD_ACL_CLASS_NAME, operationName = { "MANAGE" }, resourceName = "awardId") }) @RequestParam("awardId") String awardId,
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {
		
		User user = this.readUser();
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user.getUserId());
		
		List<String> itemList = new ArrayList<String>();
		for(String item : itemIds) {
			itemList.add(item);
		}
		param.put("itemIds",itemList );
		this.awardItemService.updateAwardMenuList(param);
	}

}
