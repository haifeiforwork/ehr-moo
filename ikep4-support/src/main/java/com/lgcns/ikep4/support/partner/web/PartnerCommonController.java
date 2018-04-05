/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

package com.lgcns.ikep4.support.partner.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerBasicInfoService;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellService;
import com.lgcns.ikep4.support.partner.service.PartnerService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 
 * 인물 정보 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping( value = "/support/partner/partnerCommon" )

public class PartnerCommonController extends BaseController {

    @Autowired
    private PartnerService partnerService;
    
    @Autowired
    PartnerBasicInfoService partnerBasicInfoService;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private PartnerQualityClaimSellService partnerQualityClaimSellService;
    
    @Autowired
    private IdgenService idgenService;
    
    @Autowired
	private RoleService roleService;
   
    
    /**
     * 고객정보 메뉴화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/menuView.do" )
    public ModelAndView menuView() {
    	
    	try {
			
    		User user = (User) getRequestAttribute("ikep.user");
    		
    		String roleId = (String)userDao.getRoleId(user.getUserId());
    		boolean cradminrole = false;
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("userId", user.getUserId());
    		map.put("roleName", "CRADM");
    		int cradminRole = userDao.getUserRoleCheck(map);
    		if(cradminRole > 0){
    			cradminrole = true;
    		}
    		
    		
    		ModelAndView modelAndView = new ModelAndView("support/partner/partnerCommon/menuView");
    		modelAndView.addObject("roleId", roleId);
    		modelAndView.addObject("cradminrole", cradminrole);
    		return modelAndView;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

    }
    
    /**
     * 인물정보 리스트 보여주기
     * @return
     */
    @RequestMapping( value = "/manInfo.do" )
    public ModelAndView ManInfo( PartnerManInfoItemSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString,
            @RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName ) {
    	User user = (User) getRequestAttribute("ikep.user");
        
        if(sendName !=null){
            searchCondition.setSearchWord( id );
            searchCondition.setSearchColumn( "id" );
        }


        //화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }

        //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)
        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );
        }
        List<String> tempRolePurposeList = new ArrayList<String>();
        /*boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
			tempRolePurposeList.add("CR01");
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
			tempRolePurposeList.add("CR02");
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
			tempRolePurposeList.add("CR03");
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
			tempRolePurposeList.add("CR04");
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
			tempRolePurposeList.add("CR05");
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
			tempRolePurposeList.add("CR06");
		}*/
        
        tempRolePurposeList.add("CR01");
        tempRolePurposeList.add("CR02");
        tempRolePurposeList.add("CR03");
        tempRolePurposeList.add("CR04");
        tempRolePurposeList.add("CR05");
        tempRolePurposeList.add("CR06");
        tempRolePurposeList.add("CR07");
		searchCondition.setRolePurposeList(tempRolePurposeList);
		

        SearchResult<PartnerManInfoItem> searchResult = this.partnerService
                .listManInfoItemBySearchCondition( searchCondition );

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                /*.addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )*/
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
    }
    
    @RequestMapping( value = "/regInfo.do" )
    public ModelAndView regInfo( PartnerManInfoItemSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString,
            @RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName ) {
        
        if(sendName !=null){
            searchCondition.setSearchWord( id );
            searchCondition.setSearchColumn( "id" );
        }

        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );
        }

        SearchResult<PartnerManInfoItem> searchResult = this.partnerService.listRegInfoItemBySearchCondition( searchCondition );

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
    }
    
    @RequestMapping( value = "/regInfoExceldown.do" )
    public ModelAndView regInfoExceldown( PartnerManInfoItemSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString,
            @RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName ) {
        
    	ExcelViewModel excel = new ExcelViewModel();  
    	
        if(sendName !=null){
            searchCondition.setSearchWord( id );
            searchCondition.setSearchColumn( "id" );
        }

        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );
        }
        
        searchCondition.setPagePerRecord(10000);
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(99999);

        SearchResult<PartnerManInfoItem> searchResult = this.partnerService.listRegInfoItemBySearchCondition( searchCondition );
        
        
        String fileName = "ContactReport_"
			 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

			
		excel.setFileName(fileName);   
	    excel.setSheetName("Sheet");   
	    
	    excel.setTitle("ContactReport_"+ DateUtil.getTodayDateTime("yyyyMMdd"));  
	    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
	    
	    excel.addColumn("이름", "user.userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
	    excel.addColumn("직위", "user.jobTitleName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("부서", "user.teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("거래처등록수", "regCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("상담등록수", "regSubCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("팀장코멘트", "comCnt1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("임원코멘트", "comCnt2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		
		excel.setDataList(searchResult.getEntity()); 
				


        return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
    }
    
    
    @RequestMapping(value = "/roleList.do")
	public ModelAndView roleList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult) {

		ModelAndView mav = new ModelAndView("support/partner/partnerCommon/roleList");

		try {
			User sessionUser = (User) getRequestAttribute("ikep.user");

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ROLE_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			searchCondition.setFieldName("roleName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId("P000001");
			searchCondition.setSearchColumn("title");
			searchCondition.setSearchWord("CR");

			SearchResult<Role> searchResult = roleService.list(searchCondition);
			
			searchCondition.setSearchWord("");

			List<RoleType> roleTypeList = roleService.selectTypeId(sessionUser.getLocaleCode());

			BoardCode boardCode = new BoardCode();

			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("roleTypeList", roleTypeList);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
    
    @RequestMapping(value = "/roleOnSubmit.do")
	public String roleOnSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid Role role,
			BindingResult result, SessionStatus status, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		/*if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}*/

		User sessionUser = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = roleService.exists(role.getRoleId());

		// 그룹의 등록/수정을 위해 필요한 기본 정보 추가
		List<Group> tempGroupList = role.getGroupList();

		if (tempGroupList != null) {
			for (int i = 0; i < tempGroupList.size(); i++) {
				Group tempGroup = tempGroupList.get(i);
				tempGroup.setRoleId(role.getRoleId());
				tempGroup.setRegisterId(sessionUser.getUserId());
				tempGroup.setRegisterName(sessionUser.getUserName());
				tempGroup.setUpdaterId(sessionUser.getUserId());
				tempGroup.setUpdaterName(sessionUser.getUserName());
			}
		}

		// 사용자의 등록/수정을 위해 필요한 기본 정보 추가
		List<User> tempUserList = role.getUserList();

		if (tempUserList != null) {
			for (int i = 0; i < tempUserList.size(); i++) {
				User tempUser = tempUserList.get(i);
				tempUser.setRoleId(role.getRoleId());
				tempUser.setRegisterId(sessionUser.getUserId());
				tempUser.setRegisterName(sessionUser.getUserName());
				tempUser.setUpdaterId(sessionUser.getUserId());
				tempUser.setUpdaterName(sessionUser.getUserName());
			}
		}

		if (isCodeExist) {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId("P000001");
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.update(role);
		} else {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId("P000001");
			role.setRegisterId(sessionUser.getUserId());
			role.setRegisterName(sessionUser.getUserName());
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.create(role);
		}

		status.setComplete();

		return "redirect:/support/partner/partnerCommon/roleList.do";
	}
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/roleForm.do", method = RequestMethod.POST)
	public ModelAndView roleForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam(value = "roleId") String roleId,
			HttpServletRequest request, AccessingResult accessResult) {


		ModelAndView mav = new ModelAndView();

		Role role = null;

		String id = request.getParameter("roleId");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();
		List<RoleType> roleTypeList = roleService.selectTypeId(userLocaleCode);

		if (id != null && !"".equals(id)) {
			role = roleService.read(id);

			List userList = role.getUserList();

			// 해당 역할이 부여된 그룹의 리스트를 가져온다.
			List<Role> roleGroupList = roleService.selectRoleGroupList(id);

			// 해당 역할이 부여된 사용자의 리스트를 가져온다.
			List<Map<String, String>> roleUserList = roleService.selectRoleUserList(id);

			role.setCodeCheck("modify");

			mav.addObject("currRoleId", role.getRoleId());
			mav.addObject("userList", userList);
			mav.addObject("roleGroupList", roleGroupList);
			mav.addObject("roleUserList", roleUserList);
		} else {
			role = new Role();
			role.setCodeCheck("new");
		}

		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("role", role);
		mav.addObject("roleTypeList", roleTypeList);

		mav.setViewName("support/partner/partnerCommon/roleForm");

		return mav;
	}

    /**
     * 인물정보 상세조회
     * 	 * @return
     */
    @RequestMapping( value = "/detailManInfo.do" )
    public ModelAndView DetailManInfo( @RequestParam( "seqNum" )int seqNum, 
    @RequestParam( value = "searchConditionString", required = false )String searchConditionString ) throws JsonGenerationException, JsonMappingException, IOException {
        
        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        /*if(!isAdmin){
        
            boolean result = partnerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }*/
        
        //인물정보를 가져온다.

        //String itemId = seqNum.toString();
        PartnerManInfoItem manInfoItem = partnerService.readManInfoItem( seqNum );

        String itemId = Integer.toString( seqNum );
        List<PartnerManInfoItem> manFamily = partnerService.readManFamily( itemId );
        List<PartnerManInfoItem> manCareer = partnerService.readManCareer( itemId );
        
       /* BasicInfo info = new BasicInfo();
        info.setId(itemId);
        info.setDivCode("PI");
        info.setRegisterId(user.getUserId());
        partnerBasicInfoService.updateHitCount(info);*/
        
        ModelAndView modelAndView = new ModelAndView().addObject( "manInfoItem", manInfoItem )
                .addObject( "manFamily", manFamily ).addObject( "manCareer", manCareer ).addObject( "isAdmin", isAdmin );
        return modelAndView;
    }
    
    @RequestMapping( value = "/detailManInfoPrint.do" )
    public ModelAndView DetailManInfoPrint( @RequestParam( "seqNum" )int seqNum, 
    @RequestParam( value = "searchConditionString", required = false )String searchConditionString ) throws JsonGenerationException, JsonMappingException, IOException {
        
        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        /*if(!isAdmin){
        
            boolean result = partnerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }*/
        
        //인물정보를 가져온다.

        //String itemId = seqNum.toString();
        PartnerManInfoItem manInfoItem = partnerService.readManInfoItem( seqNum );

        String itemId = Integer.toString( seqNum );
        List<PartnerManInfoItem> manFamily = partnerService.readManFamily( itemId );
        List<PartnerManInfoItem> manCareer = partnerService.readManCareer( itemId );
        
       /* BasicInfo info = new BasicInfo();
        info.setId(itemId);
        info.setDivCode("PI");
        info.setRegisterId(user.getUserId());
        partnerBasicInfoService.updateHitCount(info);*/
        
        ModelAndView modelAndView = new ModelAndView().addObject( "manInfoItem", manInfoItem )
                .addObject( "manFamily", manFamily ).addObject( "manCareer", manCareer ).addObject( "isAdmin", isAdmin );
        return modelAndView;
    }
    
    /**
     * 인물정보 생성 화면 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/createManInfoView.do" )
    public ModelAndView createManInfoView()throws JsonGenerationException, JsonMappingException, IOException {

    	User user = (User) getRequestAttribute("ikep.user");
    	PartnerManInfoItem manInfoItem = new PartnerManInfoItem();
        
    	boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
		}
		
		boolean crRole7 = false;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("userId", user.getUserId());
		map7.put("roleName", "CR07");
		int tempCrRole7 = userDao.getUserRoleCheck(map7);
		if(tempCrRole7 > 0){
			crRole7 = true;
		}
      
        return this.bindResult( new ModelAndView().addObject( "manInfoItem",manInfoItem )
        		.addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )
                .addObject( "crRole7", crRole7 ));
    }
    
    /**
     * 인물정보 생성 처리 동기 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/createManInfo.do" )
    public String createManInfo(String userId, PartnerManInfoItem manInfoItem,int familyTrTemp, int careerTrTemp
            ,HttpServletRequest request )throws JsonGenerationException, JsonMappingException, IOException {
        
        User user = (User) getRequestAttribute("ikep.user");

        MultipartRequest multipartRequest = (MultipartRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        
        
        manInfoItem.setDeleteFlag( "N" );
        manInfoItem.setOversea( "" );
        manInfoItem.setRegisterId( userId );
        manInfoItem.setUpdaterId( userId );
        manInfoItem.setPartnerId(idgenService.getNextId());
       
        int manId = partnerService.createManInfoMaster( manInfoItem, user);
        manInfoItem.setSeqNum(manId);
        partnerService.createManInfoItem( manInfoItem , familyTrTemp , careerTrTemp, fileList ,user);
        
        
        return "redirect:/support/partner/partnerCommon/detailManInfo.do?seqNum=" + manId;
    }
    
 
    
    
    /**
     * 인물정보 수정 화면 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/modifyManInfoView.do" )
    public ModelAndView modifyManInfoView(@RequestParam( "seqNum" )int seqNum ) throws JsonGenerationException, JsonMappingException, IOException {
   
    	User user = (User) getRequestAttribute("ikep.user");
    	PartnerManInfoItem manInfoItem = partnerService.readManInfoItem( seqNum );

        String itemId = Integer.toString( seqNum );
        List<PartnerManInfoItem> manFamily = partnerService.readManFamily( itemId );
        List<PartnerManInfoItem> manCareer = partnerService.readManCareer( itemId );
        
        boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
		}
		
		boolean crRole7 = false;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("userId", user.getUserId());
		map7.put("roleName", "CR07");
		int tempCrRole7 = userDao.getUserRoleCheck(map7);
		if(tempCrRole7 > 0){
			crRole7 = true;
		}

        ModelAndView modelAndView = new ModelAndView().addObject( "manInfoItem", manInfoItem )
                .addObject( "manFamily", manFamily ).addObject( "manCareer", manCareer )
                .addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )
                .addObject( "crRole7", crRole7 );
      
        return modelAndView;    
      
    }
    
    /**
     * 인물정보 수정 처리 동기 컨트롤 메서드
     * @return
     */
    @RequestMapping( value = "/modifyManInfo.do" )
    public String modifyManInfo(@RequestParam( "seqNum" )int seqNum,
            @RequestParam( "registerId" )String registerId, PartnerManInfoItem manInfoItem, PartnerManInfoItem manFamily,int familyTrTemp, int careerTrTemp
            ,HttpServletRequest request){
        
        User user = (User) getRequestAttribute("ikep.user");

        MultipartRequest multipartRequest = (MultipartRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        
        partnerService.updateManInfoMaster( manInfoItem ,user);
       
        partnerService.updateManInfo( manInfoItem , familyTrTemp , careerTrTemp,fileList ,user);
        
        
        return "redirect:/support/partner/partnerCommon/detailManInfo.do?seqNum=" + seqNum;
  
    }
    
    /**
     * 인물 정보 삭제 (deleteFlag = Y)
     * @param seqNum
     * @return
     */
    @RequestMapping( value = "/deleteManInfo.do" )
    public String deleteManInfo(@RequestParam("seqNum")int seqNum,@RequestParam("registerId")String registerId){
        
        User user = (User) getRequestAttribute("ikep.user");
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        PartnerManInfoItem manInfoItem = new PartnerManInfoItem();
        manInfoItem.setSeqNum( seqNum );
        manInfoItem.setUpdaterId( user.getUpdaterId() );
        manInfoItem.setUpdaterName( user.getUserName() );
        
        //if(isAdmin || user.getUserId().equals( registerId) ){
        partnerService.deleteManInfo(manInfoItem);
        //}
                
        return "redirect:/support/partner/partnerCommon/manInfo.do";
    }

    @RequestMapping(value="/popupPartner.do")
    public ModelAndView popupPartner(@RequestParam("partner") String partner){
  
        ModelAndView mav = new ModelAndView();
        List<PartnerManInfoItem> partnerItem = new ArrayList<PartnerManInfoItem>();
        //partner = "교원";
        partnerItem = partnerService.getPartnerId(partner);
        mav.addObject( "partner", partnerItem );
        
        return mav;
    }

}
