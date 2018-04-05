/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.socialblog.base.Constant;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 포틀릿 레이아웃 , 배경 설정, 통계 페이지등의 관리 페이지 Controller
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog/setting")
public class SocialBlogPortletController extends BaseController {

	/**
	 * 블로그 카테고리 관리 컨트롤용 Service
	 */
	@Autowired
	public SocialCategoryService socialCategoryService;
	
	/**
	 * 블로그 기본 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogService socialBlogService;
	
	/**
	 * 블로그 레이아웃 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogLayoutService socialBlogLayoutService;
	
	/**
	 * 블로그 포틀릿 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletService socialBlogPortletService;
	
	/**
	 * 블로그 포틀릿 레이아웃 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletLayoutService socialBlogPortletLayoutService;
	
	/**
	 * 블로그 방문자 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogVisitService socialBlogVisitService;
	
	/**
	 * 블로그 관리자 권한 체크 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;
	
	/**
	 * 블로그 첨부 파일 관리 컨트롤용 Service
	 */
	@Autowired
	private FileService fileService;
	
    /**
     * 소셜 블로그 포스팅에 대한 관리자 접근 권한 확인
     * @param blogOwnerId 블로그 소유자 ID
     * @return 블로그 관리 권한 Flag
     */
    public boolean isBlogAdmin(String blogOwnerId) { 
    	
    	boolean isBlogAdmin = false;
    	
    	User sessionUser = readSessionUser();
    	if( blogOwnerId.equals(sessionUser.getUserId())){
    		isBlogAdmin = true;
    	}
    	
        return isBlogAdmin;
    }
    
	/**
	 * 소셜 블로그 포스팅에 대한 시스템 관리자 접근 권한 확인
	 * 
	 * @param user 사용자 정보 객체
	 * @return 시스템 관리자 접근 권한 확인
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user.getUserId());
		//isSystemAdmin = true;
		return isSystemAdmin;
	}
    
    /**
     * 세션의 로그인 User 정보
     * @return 세션에서 받아온 User 정보 객체
     */
    private User readSessionUser() {  
    	return (User)getRequestAttribute("ikep.user"); 
    }
    
	/**
	 * 소셜 블로그 관리 팝업 메인 페이지 호출
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 블로그 관리 팝업 View
	 */
	@RequestMapping(value = "/socialblogSettingHome.do")
	public ModelAndView socialblogSettingHome(@RequestParam("blogOwnerId") String blogOwnerId) {
		
		User sessionUser = readSessionUser();
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(sessionUser) )){
			throw new IKEP4AjaxException("Access Error", null);
		}

		return new ModelAndView("/socialpack/socialblog/setting/socialblogSettingHome")
			.addObject("blogOwnerId", blogOwnerId)
			.addObject("user", sessionUser);
		
	}
	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 화면 Layout 관리 호출
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 화면 Layout 관리  View
	 */
	@RequestMapping(value = "/socialLayoutManage.do", method = RequestMethod.GET)
	public ModelAndView socialLayoutManage(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId) {
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/setting/socialLayoutManage");
		
		User sessionUser = readSessionUser();		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(sessionUser) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		//해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		
		// 포틀릿 전체 리스트 
		List<SocialBlogPortlet> socialBlogPortletList = socialBlogPortletService.listAllSocialBlogPortlet();
		mav.addObject("socialBlogPortletList", socialBlogPortletList);
		//mav.addObject("socialBlogPortletListSize", socialBlogPortletList.size());
		
		// 레이아웃 관련 전부 가져 오는 부분
		List<SocialBlogLayout> socialBlogLayoutList = socialBlogLayoutService.listSocialBlogLayoutAll();
		mav.addObject("socialBlogLayoutList", socialBlogLayoutList);
		//mav.addObject("socialBlogLayoutListSize", socialBlogLayoutList.size());
		
		// 내가 저장하고 있는 Portlet Layout
		List<SocialBlogPortletLayout> socialBlogPortletLayoutList = socialBlogPortletLayoutService.listSocialBlogPortletLayoutByBlogId(socialBlog);
		mav.addObject("socialBlogPortletLayoutList", socialBlogPortletLayoutList);
		
		// 내가 사용 하는 Layout
		SocialBlogLayout mySocialBlogLayout = socialBlogLayoutService.socialBlogLayoutByOwnerId(socialBlog);
		mav.addObject("mySocialBlogLayout", mySocialBlogLayout);
		mav.addObject("mySocialBlogLayoutColumn", mySocialBlogLayout.getSocialBlogLayoutColumnList());
		
		
		mav.addObject("socialBlog", socialBlog);
		
		return mav;
	}
	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 화면 Layout 관리 저장
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param layoutId 블로그 레이아웃 ID
	 * @param colIndex 포틀릿 위치값 배열
	 * @param portletId 포틀릿 ID 배열
	 * @param rowIndex 포틀릿 순번값 배열
	 */
	@RequestMapping(value = "/saveSocialLayoutManage.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void saveSocialLayoutManage(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId
													, @RequestParam(value = "layoutId", required = false) String layoutId
													, @RequestParam(value = "colIndex", required = false) String[] colIndex
													, @RequestParam(value = "portletId", required = false) String[] portletId
													, @RequestParam(value = "rowIndex", required = false) String[] rowIndex) {
		
		User sessionUser = readSessionUser();

		try {
			
			//권한 체크
			if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(sessionUser) )){
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			// 해당 블로그에 정보를 받아 온다.
			SocialBlog socialBlog = getBlogInfo(blogOwnerId);
			
			
			List<SocialBlogPortletLayout> socialBlogPortletLayoutList  = new ArrayList<SocialBlogPortletLayout>();
			for( int i = 0 ; i < portletId.length ; i ++ ){
				SocialBlogPortletLayout socialBlogPortletLayout = new SocialBlogPortletLayout();
				socialBlogPortletLayout.setPortletId(portletId[i]);
				socialBlogPortletLayout.setColIndex(Integer.parseInt(colIndex[i]));
				socialBlogPortletLayout.setRowIndex(Integer.parseInt(rowIndex[i]));
				socialBlogPortletLayoutList.add(socialBlogPortletLayout);
			}

			socialBlog.setLayoutId(layoutId);
			socialBlogPortletLayoutService.saveSocialBlogPortletLayout(socialBlog, socialBlogPortletLayoutList);
			

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
	}
	
	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 화면 배경 관리 페이지 호출
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 화면 배경 관리 페이지 View
	 */
	@RequestMapping(value = "/socialBackgroundManage.do", method = RequestMethod.GET)
	public ModelAndView socialBackgroundManage(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId ) {
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/setting/socialBackgroundManage");
		
		User sessionUser = readSessionUser();

		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(sessionUser) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		mav.addObject("socialBlog", getBlogInfo(blogOwnerId));
		
		return mav;
	}
	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 화면 배경 관리 수정 내용 저장
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param bgimage 배경 이미지 값
	 * @param editorAttachYn 첨부파일 유무
	 * @param request 첨부 파일 처리를 위한 HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/saveSocialBackgroundManage.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String saveSocialBackgroundManage(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId
														, @RequestParam(value = "bgimage", required = false) String bgimage
														, @RequestParam(value = "editorAttachYn", required = false) String editorAttachYn
														, HttpServletRequest request ) {
		
		User sessionUser = readSessionUser();
		
		try {
			
			// 해당 블로그에 정보를 받아 온다.
			SocialBlog socialBlog = getBlogInfo(blogOwnerId);
			
			List<String> fileIdList = new ArrayList<String>();
			
			if( !(StringUtil.isEmpty(bgimage)) && bgimage.equals("1") ){
				
				MultipartRequest multipartRequest = (MultipartRequest) request;
				List<MultipartFile> fileList = multipartRequest.getFiles("file");
				
				List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttachYn, sessionUser);
				String imageFileId = uploadList.get(0).getFileId();
				fileIdList.add(imageFileId);
	
				socialBlog.setImageFileId(imageFileId);
				socialBlog.setIsPrivateImage(1);
				socialBlog.setImageUrl("");

			}else{
				if( StringUtil.isEmpty(bgimage) ){
					socialBlog.setImageUrl("");
				}else{
					socialBlog.setImageUrl(bgimage);
				}
				socialBlog.setIsPrivateImage(0);
				socialBlog.setImageFileId("");
				
			}
			
			socialBlogService.updateBlogBgImage(socialBlog, fileIdList, sessionUser);

		} catch (Exception e) {  
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			} 
		}
		
		return "redirect:/socialpack/socialblog/setting/socialBackgroundManage.do?resultFlag=success&blogOwnerId="+ blogOwnerId;

	}

	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 방문자 통계 페이지 호출
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 방문자 통계 페이지  View
	 */
	@RequestMapping(value = "/socialVisitorManage.do", method = RequestMethod.GET)
	public ModelAndView socialVisitorManage(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId) {
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/setting/socialVisitorManage");
		
		User sessionUser = readSessionUser();
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(sessionUser) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		//해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		mav.addObject("socialBlog", socialBlog);
		
		SocialBlogVisit todaySocialBlogVisit = new SocialBlogVisit();
		todaySocialBlogVisit.setBlogId(socialBlog.getBlogId());
		
		// 그냥 전체 카운트
		Integer totalVisitCount = socialBlogVisitService.selectAllCountByblogId(todaySocialBlogVisit);
		mav.addObject("totalVisitCount", totalVisitCount);
		
		// 오늘 전체 카운트
		todaySocialBlogVisit.setVisitFlag("TODAY");
		Integer todayCount = socialBlogVisitService.selectAllCountByblogId(todaySocialBlogVisit);
		mav.addObject("todayCount", todayCount);
		
		// 어제 전체 카운트
		todaySocialBlogVisit.setVisitFlag("YESTERDAY");
		Integer yesterdayCount = socialBlogVisitService.selectAllCountByblogId(todaySocialBlogVisit);
		mav.addObject("yesterdayCount", yesterdayCount);
		
		// Last Week 전체 카운트
		todaySocialBlogVisit.setVisitFlag("LASTWEEK");
		Integer lastWeekCount = socialBlogVisitService.selectAllCountByblogId(todaySocialBlogVisit);
		mav.addObject("lastWeekCount", lastWeekCount);
		
		mav.addObject("weeklyTerm", makeDefaultWeeklyTermString());

		return mav;
	}
	
	/**
	 * 소셜 블로그 관리 >> 디자인설정 >> 화면 배경 관리 호출 >> 내부 차트 조회용
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param weeklyTerm 차트 조회 범위
	 * @param baseDateType 차트 조회 기본 값  DAILY : 일자별 조회, MONTH : 월별 조회
	 * @param dayFlag prev : 이전 next 이후 조회
	 * @return 방문 통계 차트 View
	 */
	@RequestMapping(value = "/socialVisitorManageForChart.do", method = RequestMethod.GET)
	public ModelAndView socialVisitorManageForChart(@RequestParam(value = "blogOwnerId", required = false) String blogOwnerId
													, @RequestParam(value = "weeklyTerm", required = false) String paramWeeklyTerm
													, @RequestParam(value = "baseDateType", required = false) String paramBaseDateType
													, @RequestParam(value = "dayFlag", required = false) String dayFlag
													) {
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/setting/socialVisitorManageForChart");
		List<SocialBlogVisit> socialBlogVisitList = null;
		
		//파라미터 null 처리를 위해 새 변수에 담음
		String baseDateType = paramBaseDateType;
		String weeklyTerm = paramWeeklyTerm;
		
		if( StringUtil.isEmpty(baseDateType) ){
			baseDateType = "DAILY";
		}
		mav.addObject("blogOwnerId", blogOwnerId );
		mav.addObject("baseDateType", baseDateType );
		
		String[] standardString = null;
		String startDateString = "";
		
		// 주차 계산전 기준 일자 용
		if(baseDateType.equals("DAILY")){
			if( StringUtil.isEmpty(weeklyTerm) ){
				weeklyTerm = makeDefaultWeeklyTermString();
	    	}
			standardString = weeklyTerm.split("~");
			startDateString = standardString[0].trim().replace(".", "");
	    	
		}else{
			if( StringUtil.isEmpty(weeklyTerm) ){
				weeklyTerm = DateUtil.getToday("yyyy.MM");
				startDateString = weeklyTerm;
			}else{
				startDateString = weeklyTerm.trim();
			}
		}

		if( !(StringUtil.isEmpty(dayFlag)) ){
			weeklyTerm = retrunPrevNextTerm(dayFlag, startDateString, baseDateType);
		}
    	mav.addObject("weeklyTerm", weeklyTerm );
    	
    	// 주차 및 월 계산 후에 다시 담는다.
    	String baseDate = makeBaseDateforChart(baseDateType, weeklyTerm);

		//해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		mav.addObject("socialBlog", socialBlog);
		
		SocialBlogVisit socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setBaseDate(baseDate);
		socialBlogVisit.setBaseDateType(baseDateType);
		socialBlogVisitList = socialBlogVisitService.getVisitorManage(socialBlogVisit);	
		mav.addObject("socialBlogVisitList", socialBlogVisitList);
		
		return mav;
	}
	
	
	/**
	 * 해당 사용자의 Blog 정보를 조회 하기 위한 메서드
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 블로그 기본 정보 객체
	 */
	public SocialBlog getBlogInfo(String blogOwnerId) {

		User sessionUser = readSessionUser();
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog searchSocialBlog = new SocialBlog();
		searchSocialBlog.setOwnerId(blogOwnerId);
		
		return socialBlogService.select(searchSocialBlog,sessionUser.getLocaleCode());
	}
	
	
    /**
     * 기본 주간 범위 값을 리턴 해준다.
     * @return 기본 주 간 범위 값
     */
    public String makeDefaultWeeklyTermString() {  
    	String weeklyTermString = "";
    	
    	String nowDate = DateUtil.getToday("yyyyMMdd");
    	String startDate = DateUtil.getWeekDay(nowDate, Constant.SOCIAL_BLOG_BASE_WEEK_DATE);
    	String endDate = DateUtil.getRelativeDateString(DateUtil.toDate(startDate), 0, 0, Constant.SOCIAL_BLOG_BASE_REALATIVE_DATE,"yyyyMMdd");
    	
    	startDate = DateUtil.getFmtDateString(startDate, "yyyy.MM.dd");
    	endDate = DateUtil.getFmtDateString(endDate, "yyyy.MM.dd");
    	
    	weeklyTermString=startDate+" ~ "+endDate;
    	
		return weeklyTermString;
	}
	

    /**
     *  기준 일자를 중심으로 이전달또는 이전주 이후달또는 이후 주 값을 계산해서 리턴한다.
     *  주와 월의 기준은 baseDateType 이 DAILY 인것을 기준으로 한다.
     * @param dayFlag prev : 이전 next 이후 조회
     * @param startDateString 기준 일자
     * @param baseDateType 차트 조회 기본 값  DAILY : 일자별 조회, MONTH : 월별 조회
     * @return 기준 일자를 중심으로 이전달또는 이전주 이후달또는 이후 주 값
     */
    public String retrunPrevNextTerm(String dayFlag, String startDateString, String baseDateType){
    	
    	String retWeeklyTerm = startDateString;
    	
    	if(baseDateType.equals("DAILY")){
	    	if(dayFlag.equals("prev")||"prev".equals(dayFlag)){
		    	
				String prevStartDateString = DateUtil.getPrevWeekDate(startDateString, 1, "yyyyMMdd");
		    	String prevEndDateString = DateUtil.getRelativeDateString(DateUtil.toDate(prevStartDateString), 0, 0,Constant.SOCIAL_BLOG_BASE_REALATIVE_DATE,"yyyyMMdd");
		    	
		    	prevStartDateString = DateUtil.getFmtDateString(prevStartDateString, "yyyy.MM.dd");
		    	prevEndDateString = DateUtil.getFmtDateString(prevEndDateString, "yyyy.MM.dd");
	    	
		    	retWeeklyTerm = prevStartDateString+" ~ "+prevEndDateString;
		    	
	    	}else if(dayFlag.equals("next")||"next".equals(dayFlag)){
	
	    		String nextStartDateString = DateUtil.getNextWeekDate(startDateString, 1, "yyyyMMdd");
	        	String nextEndDateString = DateUtil.getRelativeDateString(DateUtil.toDate(nextStartDateString), 0, 0,Constant.SOCIAL_BLOG_BASE_REALATIVE_DATE,"yyyyMMdd");
	        	
	        	nextStartDateString = DateUtil.getFmtDateString(nextStartDateString, "yyyy.MM.dd");
	        	nextEndDateString = DateUtil.getFmtDateString(nextEndDateString, "yyyy.MM.dd");
	        	
	        	retWeeklyTerm = nextStartDateString+" ~ "+nextEndDateString;
	    	}
		}else{
			
			if(dayFlag.equals("prev")||"prev".equals(dayFlag)){
		    	
				String prevMonthString = DateUtil.getPrevMonthDate(DateUtil.getFmtDateString(StringUtil.replace(startDateString+"01",".",""), "yyyyMMdd"), 1, "yyyyMMdd");
		    	prevMonthString = DateUtil.getFmtDateString(prevMonthString, "yyyy.MM.dd").substring(0,DateUtil.getFmtDateString(prevMonthString, "yyyy.MM.dd").lastIndexOf("."));
		    	retWeeklyTerm = prevMonthString;
		    	
	    	}else if(dayFlag.equals("next")||"next".equals(dayFlag)){

	    		String nextMonthString = DateUtil.getNextMonthDate(DateUtil.getFmtDateString(StringUtil.replace(startDateString+"01",".",""), "yyyyMMdd"), 1, "yyyyMMdd");
	        	nextMonthString = DateUtil.getFmtDateString(nextMonthString, "yyyy.MM.dd").substring(0,DateUtil.getFmtDateString(nextMonthString, "yyyy.MM.dd").lastIndexOf("."));
	        	retWeeklyTerm = nextMonthString;
	        	
	    	}

		}

    	return retWeeklyTerm;
    }
    
    /**
     * 이전달 이후달 또는 이전주 이후주 계산후 차트 조회용 파라미터가 되는 기준 BaseDate 를 추출 하여 반환한다.
     * 
     * @param baseDateType 차트 조회 기본 값  DAILY : 일자별 조회, MONTH : 월별 조회
     * @param weeklyTerm 차트 조회 범위
     * @return 차트 조회용 파라미터가 되는 기준 BaseDate 
     */
    public String makeBaseDateforChart(String baseDateType, String weeklyTerm){
    	
    	String returnBaseDate = "";
    	
    	if(baseDateType.equals("DAILY")){
	    	String[] standardString = weeklyTerm.split("~");
	    	if( StringUtil.isEmpty(standardString[1].trim()) ){
	    		returnBaseDate = DateUtil.getToday("yyyy.MM.dd");
	    	}else{
	    		returnBaseDate = standardString[1].trim();
	    	}

		}else{
			if( StringUtil.isEmpty(weeklyTerm) ){
				returnBaseDate = DateUtil.getToday("yyyy.MM");
			}else{
				returnBaseDate = weeklyTerm.trim();
			}
		}
    	
    	return returnBaseDate;
    }
    
}
