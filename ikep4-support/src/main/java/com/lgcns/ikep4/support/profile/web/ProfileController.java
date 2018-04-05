/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.support.guestbook.service.GuestbookService;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.support.personal.service.PersonalService;
import com.lgcns.ikep4.support.profile.model.Career;
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;
import com.lgcns.ikep4.support.profile.service.CareerService;
import com.lgcns.ikep4.support.profile.service.ProfileService;
import com.lgcns.ikep4.support.profile.service.ProfileVisitService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.model.Timezone;
import com.lgcns.ikep4.support.user.code.model.WorkPlace;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.code.service.NationService;
import com.lgcns.ikep4.support.user.code.service.TimezoneService;
import com.lgcns.ikep4.support.user.code.service.WorkPlaceService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.User.MyPsInfoUpdate;
import com.lgcns.ikep4.support.user.member.model.User.ProfileCurrentJobUpdate;
import com.lgcns.ikep4.support.user.member.model.User.ProfileExpertFieldUpdate;
import com.lgcns.ikep4.support.user.member.model.User.ProfileStatusUpdate;
import com.lgcns.ikep4.support.user.member.model.User.ProfileUpdate;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.ARIA;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileController.java 19571 2012-07-02 04:40:45Z malboru80 $
 */
@Controller
@RequestMapping(value = "/support/profile")
@SessionAttributes("profile")
//@SessionAttributes({"profile","guestbook"}) // 멀티 입력 테스트 용
public class ProfileController extends BaseController {

    /**
     * 프로파일에서 리스트 목록 형태로 조회시 사용되는 디폴트 Row 수
     */
    private static final Integer PROFILE_LIST_DEFAULT_SISE          = 10;
    
    @Autowired
    UserService userService;
    @Autowired
    CareerService careerService;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private GuestbookService guestbookService;
    @Autowired
    private TimezoneService timezoneService;
    @Autowired
    private LocaleCodeService localeCodeService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserThemeService userThemeService;
    
    
    @Autowired
    private PersonalService personalService;
    
    @Autowired
    private ActivityStreamService activityStreamService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private TimeZoneSupportService timeZoneSupportService;

    @Autowired
    private ProfileVisitService profileVisitService;
    
    @Autowired
    private NationService  nationService;
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private WorkPlaceService workPlaceService;
    
    @Autowired
    private ACLService aclService;
    
    /**
     * 프로파일의 메인을 가져 오기 위한 메서드
     * @param targetUserId 프로파일 대상자의 ID
     * @return 프로파일 메인 페이지
     */
    @RequestMapping(value = "/getProfile.do", method = RequestMethod.GET)
    public ModelAndView getView(@RequestParam(value="targetUserId", required=false ) String paramTargetUserId) {

        
        User sessionuser = (User) getRequestAttribute("ikep.user");
        
        
        String pageUrl = CommonConstant.IS_PROFILE_VIEW_SIMPLE ? "/support/profile/profileHomeSimple" : "/support/profile/profileHome";
        
        ModelAndView mav = new ModelAndView(pageUrl);
        String targetUserId = paramTargetUserId;
        
        if( StringUtil.isEmpty(targetUserId ) ){
            targetUserId = sessionuser.getUserId();
        }
        
        User profileInfo = userService.read(targetUserId);
        mav.addObject("profile", profileInfo);
        
        //메신저 ID 암호화 하기
        String encryptedId = "";
        if(paramTargetUserId != null){
            encryptedId = ARIA.getEncStr(paramTargetUserId);
        }
        mav.addObject( "encryptedId", encryptedId );
        String myId = sessionuser.getUserId();
        String encryptedMyId = ARIA.getEncStr(myId);
        mav.addObject( "encryptedMyId", encryptedMyId );
        
        if( !(sessionuser.getUserId().equals(targetUserId)) ){
            
            // Profile 방문자 기록 추가 
            ProfileVisit profileVisit = new ProfileVisit();
            profileVisit.setOwnerId(targetUserId);
            profileVisit.setVisitorId(sessionuser.getUserId());
            profileVisit.setVisitFlag("TODAY");
            profileVisitService.recordProfileVisitByDay(profileVisit);
            
            // Activity Stream 추가 
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_VISIT, targetUserId, profileInfo.getUserName(), targetUserId, 1);
            mav.addObject("editAuthFlag", "false");
        }else{
            mav.addObject("editAuthFlag", "true");
        }
        
        mav.addObject("isAdmin", aclService.isSystemAdmin("Portal", sessionuser.getUserId()));
        
        // 팀 조회
        Map<String, Object> groupmap = new HashMap<String, Object>();
        groupmap.put("userId", targetUserId);
        groupmap.put("localeCode", sessionuser.getLocaleCode());
        groupmap.put("isRoot", "true");
        List<Group> reportLineList = groupService.selectUserGroupWithHierarchy(groupmap);
        mav.addObject("reportLineList", reportLineList);
        mav.addObject("reportLineSize", reportLineList.size());
        mav.addObject("targetUserId", targetUserId);
        
        //12.09.24 최재영 무림의 경우 기본적으로 직급을 표시하고 직책이 있는 경우 직책을 표시하므로 직급이 이를 반영하도록 수정
        //12.09.24 최재영 페이지의 로직을 간소화하기 위해 수정한다

        if(!StringUtil.isEmpty(profileInfo.getJobDutyName()))
        {
            profileInfo.setJobTitleName(profileInfo.getJobDutyName());
            profileInfo.setJobTitleEnglishName(profileInfo.getJobDutyEnglishName());
        }
        
        
        boolean leaderYnFlag = false;
        String userTeamName = "";
        //12.09.24 최재영   보고라인은 targetUserId를 포함하면 안되므로 다름 로직을 폐기한다
        /*for(int i = 0 ; i < reportLineList.size();i++ ){
            if(targetUserId.equals(reportLineList.get(i).getLeaderId())){
                leaderYnFlag = true;
            }
        }*/
        //12.09.24 최재영   보고라인은 targetUserId를 포함하지 않도록 수정한다.
        
        if(reportLineList.size()> 0 && targetUserId.equals(reportLineList.get(0).getLeaderId())){
                reportLineList.remove(0);       
        }
        mav.addObject("userTeamName", userTeamName);
        
        if( "1".equals(profileInfo.getLeader()))
        {
            leaderYnFlag = true;
        }
        
        List<Group> userGroupOtherList = groupService.selectUserGroupOther(groupmap);
        for(int i = 0 ; i < userGroupOtherList.size();i++ ){
            if(targetUserId.equals(userGroupOtherList.get(i).getLeaderId())){
                leaderYnFlag = true;
            }
        }
        mav.addObject("leaderYnFlag", leaderYnFlag);
        
        if( sessionuser.getUserId().equals(targetUserId)){
            mav.addObject("editAuthFlag", "true");
        }else{
            mav.addObject("editAuthFlag", "false");
        }
        
        mav.addObject("microblogHomeUrl", "/socialpack/microblogging/privateHome.do");
        mav.addObject("socialblogHomeUrl", "/socialpack/socialblog/socialBlogHome.do");
        
        mav.addObject("myDocumentUrl", "/support/personal/getListForm.do?viewMode=document");
        mav.addObject("myFilesUrl", "/support/personal/getListForm.do?viewMode=file");
        mav.addObject("myImagesUrl", "/support/personal/getListForm.do?viewMode=image");
        mav.addObject("myCommentUrl", "/support/personal/getListForm.do?viewMode=comment");
        mav.addObject("myGalleryUrl", "/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId="+targetUserId);
        
        mav.addObject("smsUrl", "/support/sms/sms.do");
        mav.addObject("mailUrl", "/support/sms/sms.do");
        mav.addObject("messagerUrl", "/support/sms/sms.do");
        mav.addObject("messagUrl", "/support/message/messageNew.do");
        
        
        return mav;
    }
    
    /**
     * 프로파일  popup 용 보기
     * @param targetUserId 프로파일 대상자의 ID
     * @return 프로파일 팝업용 메인 페이지
     */
    @RequestMapping(value = "/getProfilePopup.do", method = RequestMethod.GET)
    public ModelAndView getProfilePopup(@RequestParam(value="targetUserId", required=false ) String paramTargetUserId) {
        
        User user = (User) getRequestAttribute("ikep.user");
        String targetUserId = paramTargetUserId;
        ModelAndView mav = new ModelAndView("/support/profile/profilepopup");
        
        if( StringUtil.isEmpty(targetUserId ) ){
            targetUserId = user.getUserId();
        }
        
        //메신저 ID 암호화 하기
        String encryptedId = "";
        if(paramTargetUserId != null){
            encryptedId = ARIA.getEncStr(paramTargetUserId);
        }
        mav.addObject( "encryptedId", encryptedId );
        String myId = user.getUserId();
        String encryptedMyId = ARIA.getEncStr(myId);
        mav.addObject( "encryptedMyId", encryptedMyId );
        
        if( !(user.getUserId().equals(targetUserId)) ){
            
            // Profile 방문자 기록 추가 
            ProfileVisit profileVisit = new ProfileVisit();
            profileVisit.setOwnerId(targetUserId);
            profileVisit.setVisitorId(user.getUserId());
            profileVisit.setVisitFlag("TODAY");
            profileVisitService.recordProfileVisitByDay(profileVisit);
            
            // Activity Stream 추가 
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_VISIT, targetUserId, user.getUserName(), user.getUserId(), 1);
        }
        
       
        User profile = userService.read(targetUserId);
        mav.addObject("profile", profile);
        
        // 팀 조회
        Map<String, Object> groupmap = new HashMap<String, Object>();
        groupmap.put("userId", targetUserId);
        groupmap.put("localeCode", user.getLocaleCode());
        groupmap.put("isRoot", "true");
        List<Group> reportLineList = groupService.selectUserGroupWithHierarchy(groupmap);
        mav.addObject("reportLineList", reportLineList);
        mav.addObject("reportLineSize", reportLineList.size());
        mav.addObject("targetUserId", targetUserId);
        
        if( user.getUserId().equals(targetUserId)){
            mav.addObject("editAuthFlag", "true");
        }else{
            mav.addObject("editAuthFlag", "false");
        }
        
        //mav.addObject("smsUrl", "/support/sms/sms.do");
        mav.addObject("mailUrl", "/support/sms/sms.do");
        mav.addObject("messagerUrl", "/support/sms/sms.do");
        mav.addObject("messagUrl", "/support/message/messageNew.do");

        return mav;
    }
    
    /**
     * 주요 프로파일 정보를 화면 조회페이지를  가져 오기 위한 메서드
     * @param targetUserId 프로파일 대상자의 ID
     * @return 개별 프로파일 정보 페이지
     */
    @RequestMapping(value = "/viewProfile.do")
    public ModelAndView viewProfile(@RequestParam("targetUserId") String targetUserId) {
        
        User user = (User) getRequestAttribute("ikep.user");
        //Portal portal = (Portal) getRequestAttribute("ikep.portal");
        
        ModelAndView mav = new ModelAndView("/support/profile/profileview");

        User profile = userService.read(targetUserId);      
        
        mav.addObject("profile", profile);
        mav.addObject("profileForm", profile);

        // 회사명 조회
        //Map<String, Object> commap = new HashMap<String, Object>();
        //commap.put("userId", targetUserId);
        //commap.put("localeCode", user.getLocaleCode());
        //Group group = groupService.selectUserRootGroup(commap);
        //if( group != null ){
        //  mav.addObject("userCompName", group.getRootGroupName());
        //}else{
        //  mav.addObject("userCompName", "");
        //}
        
        // 팀 조회
        Map<String, Object> groupmap = new HashMap<String, Object>();
        groupmap.put("userId", targetUserId);
        groupmap.put("localeCode", user.getLocaleCode());
        groupmap.put("isRoot", "false");
        if(user.getUserId().equals(targetUserId)){
        	mav.addObject("deviceList", getMobileDeviceList());
        }
        List<Group> reportLineList = groupService.selectUserGroupWithHierarchy(groupmap);
        mav.addObject("reportLine", reportLineList);
        String userTeamName = "";
        String userTeamEnglishName = "";
        
        
        //팀명 표시를 각각 나누어 처리. defaultLocaleCode 를 가져오는 로직을 추가하기 싫어 각각 설정하도록 처리
        //팀명 표시에서 사장님 회장님은 표시하지 않도록 하드 코딩...

        for(int i = (reportLineList.size()-1) ; i >= 0 ; i-- ){ 
            if("사장".equals(reportLineList.get(i).getGroupName()))
                    continue;
            
            userTeamName += reportLineList.get(i).getGroupName() ;          
            if( i != 0 ){
                userTeamName += " ";
            }
        }
        
        for(int i = 0 ; i < reportLineList.size();i++ ){
            if("사장".equals(reportLineList.get(i).getGroupName()))
                continue;
        
            userTeamEnglishName += reportLineList.get(i).getGroupEnglishName() ;            
            if( i != (reportLineList.size()-1)){
                userTeamEnglishName += " ";
            }
        }
        
        mav.addObject("userTeamName", userTeamName);
        mav.addObject("userTeamEnglishName", userTeamEnglishName);
        
        // 겸직 조직에 대한 정보를 표시한다.      
        List<Group> userGroupOtherList = groupService.selectUserGroupOther(groupmap);
        String userOtherTeamName = "";
        String userOtherTeamEnglishName = "";
        
        //겸직 정보는 현재 수정하지 않는다. 겸직에 대한 이야기가 나왔을때 데이터를 보고 처리하는게 좋을 듯하다

        for(int i = 0 ; i < userGroupOtherList.size();i++ ){
            userOtherTeamName += userGroupOtherList.get(i).getGroupName() ;
            userOtherTeamEnglishName += userGroupOtherList.get(i).getGroupEnglishName() ;

            if( i != (userGroupOtherList.size()-1)){
                userOtherTeamName += " ";
                userOtherTeamEnglishName += " ";
            }
            if( userGroupOtherList.get(i).getLevelNum().equals(1)){
                userOtherTeamName += "<br/>";
                userOtherTeamEnglishName += "<br/>";
            }
        }
        mav.addObject("userOtherTeamName", userOtherTeamName);
        mav.addObject("userOtherTeamEnglishName", userOtherTeamEnglishName);
        
        if( user.getUserId().equals(targetUserId)){
            mav.addObject("editAuthFlag", "true");
        }else{
            mav.addObject("editAuthFlag", "false");
        }

        return mav;
    }
    
    /**
	 * 사용자의 모바일 디바이스 목록을 가져옴 (모바일서버 restful)
	 * @return deviceList
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getMobileDeviceList() {
		
		Properties prop = PropertyLoader.loadProperties("/configuration/mobile-conf.properties");
		String mobileBaseUrl = prop.getProperty("ikep4.mobile.baseUrl");
		
		User user = (User) getRequestAttribute("ikep.user");
		Map<String, String> deviceList = new LinkedHashMap<String, String>();
		
		/*try {
			Client client = Client.create();
			WebResource webResource = client.resource(mobileBaseUrl+"/rest/device/retrieveDeviceList");
			
			client.setConnectTimeout(50); //timeout
			
			JSONObject input = new JSONObject();
			input.put("portalId", user.getPortalId());
			input.put("userId", user.getUserId());
			
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, input.toString());
				
			if (response.getStatus() != 200 && response.getStatus() != 201) {
				deviceList.put("error", response.getClientResponseStatus().getReasonPhrase()+" ("+response.getStatus()+")");
			} else {
				String output = response.getEntity(String.class);
				ObjectMapper mapper = new ObjectMapper();
				deviceList =  (LinkedHashMap<String, String>) mapper.readValue(output, Object.class); //json string을 LinkedHashMap으로 변환
			}
		} catch (Exception e) {
			deviceList.put("error",  e.getCause().getMessage());
		}*/
		
		return deviceList;
	}
    
	/**
	 * 모바일 단말기 Lock/Unlock 처리 (모바일서버 restful)
	 * @param uuid 
	 * @param isLock 0:lock처리, 1:unlock처리
	 * @return 결과값 String
	 */
	@RequestMapping(value = "/updateDeviceStatus.do")
	public @ResponseBody String updateDeviceStatus(@RequestParam("uuid") String uuid, @RequestParam("isLock") String isLock, SessionStatus status) {
		
		Properties prop = PropertyLoader.loadProperties("/configuration/mobile-conf.properties");
		String mobileBaseUrl = prop.getProperty("ikep4.mobile.baseUrl");
		
		User user = (User) getRequestAttribute("ikep.user");
		String returnValue = "";
		
		try {
		
			Client client = Client.create();
			WebResource webResource = client.resource(mobileBaseUrl+"/rest/device/updateDeviceStatus");
			
			client.setConnectTimeout(100); //timeout
			
			JSONObject input = new JSONObject();
			input.put("portalId", user.getPortalId());
			input.put("userId", user.getUserId());
			input.put("uuid", uuid);
			input.put("isLock", isLock);
			
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, input.toString());
			
			if (response.getStatus() != 200 && response.getStatus() != 201) {
				returnValue = "Error : " + response.getClientResponseStatus().getReasonPhrase()+" ("+response.getStatus()+")"; 
			} else {
				
				String output = response.getEntity(String.class);
				JSONObject jsonOutput = new JSONObject(output);
				
				if ( "0".equals(jsonOutput.getJSONObject("header").getString("returnCode")) ) {
					returnValue = "OK";	
				} else {
					returnValue = jsonOutput.getJSONObject("header").getString("returnDesc");
				}
				
			}
				
		} catch (Exception e) {
			returnValue = "Error : " + e.getCause().getMessage();
		}
		
		status.setComplete();
		
		return returnValue;
	}
	
	
	/**
	 * 모바일 단말기 초기화 처리 (모바일서버 restful)
	 * @param uuid 
	 * @return 결과값 String
	 */
	@RequestMapping(value = "/initializeDevice.do")
	public @ResponseBody String initializeDevice(@RequestParam("uuid") String uuid, SessionStatus status) {
		
		Properties prop = PropertyLoader.loadProperties("/configuration/mobile-conf.properties");
		String mobileBaseUrl = prop.getProperty("ikep4.mobile.baseUrl");
		
		User user = (User) getRequestAttribute("ikep.user");
		String returnValue = "";
		
		try {
		
			Client client = Client.create();
			WebResource webResource = client.resource(mobileBaseUrl+"/rest/device/initializeDevice");
			
			client.setConnectTimeout(100); //timeout

			JSONObject input = new JSONObject();
			input.put("portalId", user.getPortalId());
			input.put("userId", user.getUserId());
			input.put("uuid", uuid);
			
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, input.toString());
			
			if (response.getStatus() != 200 && response.getStatus() != 201) {
				returnValue = "Error : " + response.getClientResponseStatus().getReasonPhrase()+" ("+response.getStatus()+")"; 
			} else {
				
				String output = response.getEntity(String.class);
				JSONObject jsonOutput = new JSONObject(output);
				
				if ( "0".equals(jsonOutput.getJSONObject("header").getString("returnCode")) ) {
					returnValue = "OK";	
				} else {
					returnValue = jsonOutput.getJSONObject("header").getString("returnDesc");
				}
				
			}
				
		} catch (Exception e) {
			returnValue = "Error : " + e.getCause().getMessage();
		}
		
		status.setComplete();
		
		return returnValue;
	}
    /**
     * 주요 프로파일 정보를 수정하기 위한 입력 화면 페이지 조회용 메서드
     * @param targetUserId 프로파일 대상자의 ID
     * @return 개별 프로파일 수정 페이지
     */
    @RequestMapping(value = "/editProfile.do")
    public ModelAndView editProfile(@RequestParam("targetUserId") String targetUserId) {
        
        User user = (User) getRequestAttribute("ikep.user");
        
        ModelAndView mav = new ModelAndView("/support/profile/profileedit");

        // Combobox 조회용
        List<LocaleCode> localCodeList = localeCodeService.list(user.getLocaleCode());
        mav.addObject("localCodeList", localCodeList);
        
        //List<WorkPlace> workPlaceList = workPlaceService.list(user.getLocaleCode());
        //mav.addObject("workPlaceList", workPlaceList);        
        
        //List<Timezone> timezoneList = timezoneService.list();
        Map<String, String> param = new HashMap<String, String>();
        param.put("fieldName", "timezoneName");
        param.put("userLocaleCode", user.getLocaleCode());
        List<Timezone> timezoneList = timezoneService.list(param);
        
        mav.addObject("timezoneList", timezoneList);
        
        AdminSearchCondition searchCondition = new AdminSearchCondition();
        searchCondition.setFieldName("nationName");
        searchCondition.setUserLocaleCode(user.getLocaleCode());
        SearchResult<Nation> nationList = nationService.list(searchCondition);
        
        //mav.addObject("nationList", nationList.getEntity());
        
        User profile = userService.read(targetUserId);
                
        mav.addObject("profile", profile);
        mav.addObject("profileForm", profile);
        
        // 회사명 조회
        //Map<String, Object> commap = new HashMap<String, Object>();
        //commap.put("userId", targetUserId);
        //commap.put("localeCode", user.getLocaleCode());
        //Group group = groupService.selectUserRootGroup(commap);
        //if( group != null ){
        //  mav.addObject("userCompName", group.getRootGroupName());
        //}else{
        //  mav.addObject("userCompName", "");
        //}
        
        // 팀명 조회
        Map<String, Object> groupmap = new HashMap<String, Object>();
        groupmap.put("userId", targetUserId);
        groupmap.put("localeCode", user.getLocaleCode());
        groupmap.put("isRoot", "false");
        List<Group> reportLineList = groupService.selectUserGroupWithHierarchy(groupmap);
        mav.addObject("reportLine", reportLineList);
        String userTeamName = "";
        String userTeamEnglishName = "";
        
        for(int i = (reportLineList.size()-1) ; i >= 0 ; i-- ){ 
            if("사장".equals(reportLineList.get(i).getGroupName()))
                    continue;
            
            userTeamName += reportLineList.get(i).getGroupName() ;          
            if( i != 0 ){
                userTeamName += " ";
            }
        }
        
        for(int i = 0 ; i < reportLineList.size();i++ ){
            if("사장".equals(reportLineList.get(i).getGroupName()))
                continue;
        
            userTeamEnglishName += reportLineList.get(i).getGroupEnglishName() ;            
            if( i != (reportLineList.size()-1)){
                userTeamEnglishName += " ";
            }
        }
        mav.addObject("userTeamName", userTeamName);
        mav.addObject("userTeamEnglishName", userTeamEnglishName);
        
        List<Group> userGroupOtherList = groupService.selectUserGroupOther(groupmap);
        String userOtherTeamName = "";
        String userOtherTeamEnglishName = "";
        for(int i = 0 ; i < userGroupOtherList.size();i++ ){
            userOtherTeamName += userGroupOtherList.get(i).getGroupName() ;
            userOtherTeamEnglishName += userGroupOtherList.get(i).getGroupEnglishName() ;

            if( i != (userGroupOtherList.size()-1)){
                userOtherTeamName += " ";
                userOtherTeamEnglishName += " ";
            }
            if( userGroupOtherList.get(i).getLevelNum().equals(1)){
                userOtherTeamName += "<br/>";
                userOtherTeamEnglishName += "<br/>";
            }
        }
        mav.addObject("userOtherTeamName", userOtherTeamName);
        mav.addObject("userOtherTeamEnglishName", userOtherTeamEnglishName);

        return mav;
    }
    

    /**
         * 주요 프로파일 정보를 수정후 저장 하기 위한 저장용 메서드
         * @param user 프로파일 정보
         * @param result Validation 결과 값
         * @param status SessionStaus 값 
         * @param session httpSession (수정된 프로파일 정보를 세션에 담기 위한  session)
         * @return 저장 결과 메시지
         */
        @RequestMapping(value = "/updateProfile.do")
        public @ResponseBody String updateProfile( @ValidEx(groups={ProfileUpdate.class}) User user, BindingResult result, SessionStatus status, HttpSession session,
                HttpServletRequest request) {

            String sResult = "success";
            User sessionuser = (User) getRequestAttribute("ikep.user");
            
            if (result.hasErrors()) {
                sResult = "failure";
                throw new IKEP4AjaxValidationException(result, messageSource);
            }else{
                //String preUserPassword = request.getParameter("preMailPassword");
                //String mailPassword = user.getMailPassword();
                
                //if (!mailPassword.equals(preUserPassword)) {
                //  user.setMailPassword(EncryptUtil.encryptText(mailPassword));
                //}
                //120918 최재영
                //에디트 화면에서 로케일 입력을 막았기 때문에 오류 방지를 위해 추가함
                if(StringUtil.isEmpty(user.getLocaleCode()))
					user.setLocaleCode("ko");				
				if(StringUtil.isEmpty(user.getNationCode()))
					user.setNationCode("KOR");		
				
                userService.updateProfile(user);
                sResult = "success";
                
                User updateUser = userService.read(user.getUserId());
                
                //사용자 테마 정보 조회
                UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());
                UserTheme userTheme = null;
                
                if(userThemeCheck == null) {
                    //사용자 테마 정보 없으면 기본 테마정보 조회
                    userTheme = userThemeService.readDefaultTheme(updateUser.getPortalId());
                } else {
                    userTheme = userThemeService.readTheme(userThemeCheck.getThemeId(), updateUser.getPortalId());
                }
                updateUser.setUserTheme(userTheme);
                
                super.setRequestAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale(user.getLocaleCode()));
                super.setRequestAttribute("ikep.user", updateUser);
                
                // Activity Stream 기록
                updateProfileActivityStream(sessionuser, user);
                
                // 날씨 포틀릿 contents 캐시 Element 삭제
                cacheService.removeCacheElementPortletContent("Cachename-weather-portlet", "Cachemode-weather-portlet", "Elementkey-weather-portlet", updateUser.getUserId());
                
                // 메뉴 목록 캐시 Element 삭제
                cacheService.removeCacheElement("menu");
                
                // 포틀릿 목록 캐시 Element 삭제
                cacheService.removeCacheElement("portlet");
                
                // 포탈 공용 포틀릿 목록 Element 삭제
                cacheService.removeCacheElement("commonPortlet");
                
                // 포탈 사용자 포틀릿 목록 Element 삭제
                cacheService.removeCacheElement("userPortlet");
            }

            // 세션 상태를 complete하여 이중 전송 방지
            status.setComplete();
            
            return sResult;

        }
        
        /**
         * 주요 내 개인 정보 화면 조회페이지를  가져 오기 위한 메서드
         * @param targetUserId 프로파일 대상자의 ID
         * @return 개별 개인 정보 페이지
         */
        @RequestMapping(value = "/viewMyPsInfo.do")
        public ModelAndView viewMyPsInfo(@RequestParam("targetUserId") String targetUserId) {
            
            User sessionuser = (User) getRequestAttribute("ikep.user");
            
            ModelAndView mav = new ModelAndView("/support/profile/myPersonalInfoView");
            
            if( sessionuser.getUserId().equals(targetUserId)){
                User myPsInfo = userService.read(targetUserId);
                
                mav.addObject("editAuthFlag", "true");
                mav.addObject("myPsInfo", myPsInfo);
                mav.addObject("myPsInfoForm", myPsInfo);
            }else{
                mav.addObject("editAuthFlag", "false");
            }

            return mav;
        }
        
        
        /**
         * 주요 내 개인 정보를 수정하기 위한 입력 화면 페이지 조회용 메서드
         * @param targetUserId 프로파일 대상자의 ID
         * @return 개별 개인정보 수정 페이지
         */
        @RequestMapping(value = "/editMyPsInfo.do")
        public ModelAndView editMyPsInfo(@RequestParam("targetUserId") String targetUserId) {
            
            User sessionuser = (User) getRequestAttribute("ikep.user");
            
            ModelAndView mav = new ModelAndView("/support/profile/myPersonalInfoEdit");
            
            if( sessionuser.getUserId().equals(targetUserId)){
                User myPsInfo = userService.read(targetUserId);
                
                mav.addObject("editAuthFlag", "true");
                mav.addObject("myPsInfo", myPsInfo);
                mav.addObject("myPsInfoForm", myPsInfo);
            }else{
                mav.addObject("editAuthFlag", "false");
            }
            
            return mav;
        }
        
        
        /**
         * 주요 내 개인 정보를 수정후 저장 하기 위한 저장용 메서드
         * @param user 프로파일 정보
         * @param result Validation 결과 값
         * @param status SessionStaus 값 
         * @param session httpSession (수정된 프로파일 정보를 세션에 담기 위한  session)
         * @return 저장 결과 메시지
         */
        @RequestMapping(value = "/updateMyPsInfo.do")
        public @ResponseBody String updateMyPsInfo(@ValidEx(groups={MyPsInfoUpdate.class}) User user, BindingResult result, SessionStatus status, HttpSession session) {

            String sResult = "success";
            User sessionuser = (User) getRequestAttribute("ikep.user");

            // 입력된 password를 encrypt한다.
            //String encryptPassword = EncryptUtil.encryptSha(user.getUserId()+user.getUserPassword());//ikep 원래소스
            String encryptPassword = EncryptUtil.encryptText(user.getUserPassword());//무림제지 2012.08.31
            
            user.setUserPassword(encryptPassword);
            
            if (result.hasErrors()) {
                sResult = "failure";
                throw new IKEP4AjaxValidationException(result, messageSource);
            }else{
                userService.updateMyPsInfo(user);
                sResult = "success";
                
                // Activity Stream 기록
                updateProfileActivityStream(sessionuser, user);

            }

            
            // 세션 상태를 complete하여 이중 전송 방지
            status.setComplete();
            
            return sResult;

        }
    
    /**
     * 프로파일 대상자의 현재 Status 정보를  가지고 오기 위한 메서드
     * @param targetUserId 프로파일 대상자 ID
     * @param editflag view 화면 시 보기창 수정창 구분 Flag. view : 보기 화면, edit : 수정 화면
     * @return 현재 프로파일 대상자의 Status 페이지
     */
    @RequestMapping("/viewProfileStatus.do")
    public ModelAndView getProfilestatus(@RequestParam("targetUserId") String targetUserId, @RequestParam("editflag") String editflag){
        ModelAndView mav = new ModelAndView("/support/profile/profilestatus");
        
        User profile = userService.read(targetUserId);
        mav.addObject("profile", profile);
        mav.addObject("editflag", editflag);
        
        return mav;
    }
    
    /**
     * 자신의 현지 Profile Status 를 수정 저장 하기 위한 메서드
     * @param user 프로파일 정보
     * @param result Validation 결과 값
     * @param status SessionStaus 값 
     * @return Profile Status 저장 결과 메시지
     */
    @RequestMapping(value = "/updateProfilestatus.do")
    public @ResponseBody String updateProfilestatus(@ValidEx(groups={ProfileStatusUpdate.class}) User user, BindingResult result, SessionStatus status) {
        
        String sResult = "success";
        
        if (result.hasErrors()) {
            sResult = "failure";
            throw new IKEP4AjaxValidationException(result, messageSource);
        }else{
            userService.updateProfileStaus(user);
            // Activity Stream 추가  Service 에 Dependency 가 없음
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_STATUS_UPDATE, user.getUserId(),"Update Profile Status", user.getUserId(), 1);
            sResult = "success";
        }
        
        // 세션 상태를 complete하여 이중 전송 방지
        status.setComplete();

        return sResult;

    }
    
    /**
     * 프로파일 대상자의 현재 담당업무를 조회 하기 위한 메서드
     * @param targetUserId 프로파일 대상자 ID
     * @param editflag view 화면 시 보기창 수정창 구분 Flag. view : 보기 화면, edit : 수정 화면
     * @return 현재 프로파일 대상자의 현재 담당업무 페이지
     */
    @RequestMapping("/viewCurrentJobView.do")
    public ModelAndView getCurrentJob(@RequestParam("targetUserId") String targetUserId, @RequestParam("editflag") String editflag){
        ModelAndView mav = new ModelAndView("/support/profile/currentJob");
        
        User sessionuser = (User) getRequestAttribute("ikep.user");
        
        User profile = userService.read(targetUserId);
        mav.addObject("profile", profile);
        mav.addObject("editflag", editflag);
        
        if( sessionuser.getUserId().equals(targetUserId)){
            mav.addObject("editAuthFlag", "true");
        }else{
            mav.addObject("editAuthFlag", "false");
        }
        
        return mav;
    }
    
    /**
     * 프로파일 대상자의 현재 담당업무를 저장 하기 위한 메서드
     * @param user 프로파일 정보
     * @param result Validation 결과 값
     * @param status SessionStaus 값 
     * @return 현재 담당업무 저장 결과 메시지
     */
    @RequestMapping(value = "/updateCurrentJob.do")
    public @ResponseBody String updateCurrentJob(@ValidEx(groups={ProfileCurrentJobUpdate.class}) User user, BindingResult result, SessionStatus status) {
        
        String sResult = "success";
        
        if (result.hasErrors()) {
            sResult = "failure";
            throw new IKEP4AjaxValidationException(result, messageSource);
        }else{
            userService.updateCurrentJob(user);
            sResult = "success";
        }
        
        // 세션 상태를 complete하여 이중 전송 방지
        status.setComplete();

        return sResult;

    }
    

    /**
     * 프로파일 대상자의 전문가 정보를 조회 하기 위한 메서드
     * @param targetUserId 프로파일 대상자 ID
     * @param editflag view 화면 시 보기창 수정창 구분 Flag. view : 보기 화면, edit : 수정 화면
     * @return 현재 프로파일 대상자의 전문가 정보 페이지
     */
    @RequestMapping("/viewExpertFieldView.do")
    public ModelAndView getExpertField(@RequestParam("targetUserId") String targetUserId, @RequestParam("editflag") String editflag){
        ModelAndView mav = new ModelAndView("/support/profile/expertfield");
        
        User profile = userService.read(targetUserId);
        mav.addObject("profile", profile);
        mav.addObject("editflag", editflag);
        
        return mav;
    }
    
    /**
     * 프로파일 대상자의 전문가 정보를 저장 하기 위한 메서드
     * @param user 프로파일 정보
     * @param result Validation 결과 값
     * @param status SessionStaus 값 
     * @return 전문가 정보 저장 결과 메시지
     */
    @RequestMapping(value = "/updateExpertField.do")
    public @ResponseBody String updateExpertField(@ValidEx(groups={ProfileExpertFieldUpdate.class}) User user, BindingResult result, SessionStatus status) {
        
        String sResult = "success";
        
        if (result.hasErrors()) {
            sResult = "failure";
            throw new IKEP4AjaxValidationException(result, messageSource);
        }else{
            userService.updateExportField(user);
            sResult = "success";
        }
        
        // 세션 상태를 complete하여 이중 전송 방지
        status.setComplete();

        return sResult;

    }
    
    /**
     * 프로파일 대상자의 전문가 테그와 관심 테그 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 대상자의 전문가 테그 조회 페이지
     */
    @RequestMapping("/viewExpertTagView.do")
    public ModelAndView getExpertTagView(@RequestParam("targetUserId") String targetUserId){
        ModelAndView mav = new ModelAndView("/support/profile/experttag");
        
        List<Tag> expertTagList = tagService.listTagByItemId( targetUserId,  TagConstants.ITEM_TYPE_PROFILE_PRO);
        mav.addObject("expertTagList", expertTagList);
        List<Tag> intressTagList = tagService.listTagByItemId( targetUserId,  TagConstants.ITEM_TYPE_PROFILE_ATTENTION);
        mav.addObject("intressTagList", intressTagList);
        
        mav.addObject("targetUserId", targetUserId);
        
        return mav;
    }
    
    /**
     * 프로파일 대상자의 경력 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 경력정보 최근 5건 페이지
     */
    @RequestMapping("/getCareerR5View.do")
    public ModelAndView getCareerR5View(@RequestParam("targetUserId") String targetUserId){
        
        ModelAndView mav = new ModelAndView("/support/profile/careerview");

        Career career = new Career();
        career.setRegisterId(targetUserId);
        List<Career> careerList = careerService.listRecent5(career);
        
        mav.addObject("careerList", careerList);
        
        return mav;
    }
    
    
    /**
     * 프로파일 대상자의 일정 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 당일 일정 정보 페이지
     */
    @RequestMapping("/getMySchedule.do")
    public ModelAndView getMySchedule(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/myschedule");
        
        Date today = timeZoneSupportService.convertTimeZone();
        
        mav.addObject("today", today);
        mav.addObject("user", user);
        mav.addObject("targetUserId", targetUserId);
        
        return mav;
        
    }
    

    /**
     * 프로파일 대상자의 Follow User 정보를 가지고 온다. 
     * Following, Follower, Fellow Export Export
     * @param targetUserId 프로파일 대상자 ID
     * @return Follow User 정보 페이지
     */
    @RequestMapping("/getFollowList.do")
    public ModelAndView getFollowList(@RequestParam("targetUserId") String targetUserId){
        
        ModelAndView mav = new ModelAndView("/support/profile/myfollowlist");
        mav.addObject("targetUserId", targetUserId);
        
        
        List<ProfileExpertFellow> fellowExpertList = profileService.listProfileFellowExpert(targetUserId);
        mav.addObject("fellowExpertList", fellowExpertList);
        mav.addObject("fellowExpertListSize", fellowExpertList.size());

        return mav;
        
    }
    
    
    /**
     * 프로파일 대상자가 작성한 Document 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return Document 목록 페이지
     */
    @RequestMapping("/getMyDocment.do")
    public ModelAndView getMyDocmentList(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/mydocument");
        Integer pagePerRecord = PROFILE_LIST_DEFAULT_SISE;
        
        try {

            PersonalSearchCondition searchCondition = new PersonalSearchCondition();
            searchCondition.setUserLocaleCode(user.getLocaleCode());
            searchCondition.setRegisterId(targetUserId);
            searchCondition.setPagePerRecord(pagePerRecord);

            SearchResult<Personal> searchResult = personalService.getAllForDocument(searchCondition);

            mav.addObject("searchResult", searchResult);
            mav.addObject("searchCondition", searchResult.getSearchCondition());

        } catch (Exception ex) {
            throw new IKEP4AjaxException("", ex);
        }
        return mav;
        
    }

    /**
     * 프로파일 대상자가 작성한 Images 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 이미지 파일 목록
     */
    @RequestMapping("/getMyImages.do")
    public ModelAndView getMyImageList(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/myimages");
        Integer pagePerRecord = PROFILE_LIST_DEFAULT_SISE;
        
        try {

            PersonalSearchCondition searchCondition = new PersonalSearchCondition();
            searchCondition.setFileType("image");
            searchCondition.setUserLocaleCode(user.getLocaleCode());
            searchCondition.setRegisterId(targetUserId);
            searchCondition.setPagePerRecord(pagePerRecord);

            SearchResult<Personal> searchResult = personalService.getAllForFile(searchCondition);

            mav.addObject("searchResult", searchResult);
            mav.addObject("searchCondition", searchResult.getSearchCondition());

        } catch (Exception ex) {
            throw new IKEP4AjaxException("", ex);
        }
        return mav;
        
    }
    
    /**
     * 프로파일 대상자가 작성한 갤러리 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 이미지 파일 목록
     */
    @RequestMapping("/getMyGallery.do")
    public ModelAndView getMyGalleryList(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/myGallery");
        Integer pagePerRecord = PROFILE_LIST_DEFAULT_SISE;
        
        try {

            PersonalSearchCondition searchCondition = new PersonalSearchCondition();
            searchCondition.setFileType("image");
            searchCondition.setUserLocaleCode(user.getLocaleCode());
            searchCondition.setRegisterId(targetUserId);
            searchCondition.setPagePerRecord(pagePerRecord);

            //SearchResult<Personal> searchResult = personalService.getAllForFile(searchCondition);
            SearchResult<Personal> searchResult = personalService.getMyGallery(searchCondition,targetUserId);
            mav.addObject("searchResult", searchResult);
            mav.addObject("targetUserId", targetUserId);
            mav.addObject("searchCondition", searchResult.getSearchCondition());

        } catch (Exception ex) {
            throw new IKEP4AjaxException("", ex);
        }
        return mav;
        
    }   
    
    /**
     * 프로파일 대상자가 작성한 Comment 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 작성한 Comment 목록
     */
    @RequestMapping("/getMyComment.do")
    public ModelAndView getMyCommentList(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/mycomment");
        Integer pagePerRecord = PROFILE_LIST_DEFAULT_SISE;
        
        try {
            
            PersonalSearchCondition searchCondition = new PersonalSearchCondition();
            searchCondition.setCommentType("comment");
            searchCondition.setUserLocaleCode(user.getLocaleCode());
            searchCondition.setRegisterId(user.getUserId());
            searchCondition.setPagePerRecord(pagePerRecord);
            
            SearchResult<Personal> searchResult = personalService.getAllForComment(searchCondition);

            mav.addObject("searchResult", searchResult);
            mav.addObject("searchCondition", searchResult.getSearchCondition());

        } catch (Exception ex) {
            throw new IKEP4AjaxException("", ex);
        }
        
        return mav;
        
    }
    
    /**
     * 프로파일 대상자가 첨부한 Files 정보를 가지고 온다.
     * @param targetUserId 프로파일 대상자 ID
     * @return 첨부한 Files 목록
     */
    @RequestMapping("/getMyFiles.do")
    public ModelAndView getMyFileList(@RequestParam("targetUserId") String targetUserId){
        
        User user = (User) getRequestAttribute("ikep.user");
        ModelAndView mav = new ModelAndView("/support/profile/myfiles");
        Integer pagePerRecord = PROFILE_LIST_DEFAULT_SISE;
        
        try {

            PersonalSearchCondition searchCondition = new PersonalSearchCondition();
            searchCondition.setFileType("file");
            searchCondition.setUserLocaleCode(user.getLocaleCode());
            searchCondition.setRegisterId(targetUserId);
            searchCondition.setPagePerRecord(pagePerRecord);

            SearchResult<Personal> searchResult = personalService.getAllForFile(searchCondition);

            mav.addObject("searchResult", searchResult);
            mav.addObject("searchCondition", searchResult.getSearchCondition());

        } catch (Exception ex) {
            throw new IKEP4AjaxException("", ex);
        }
        return mav;
        
    }
    
    /**
     * 프로파일 대상자의 방명록 정보를 가져 온다.
     * @param guestbookSearch 방명록 조회 VO
     * @param targetUserId 프로파일 대상자 ID
     * @return
     */
    @RequestMapping(value = "/getGuestbookList.do")
    public ModelAndView getGuestbookList(GuestbookSearchCondition paramGuestbookSearch
                                        , @RequestParam("targetUserId") String targetUserId) {
        
        User sessionuser = (User) getRequestAttribute("ikep.user");
        GuestbookSearchCondition guestbookSearch = paramGuestbookSearch;
        if (guestbookSearch == null) {
            guestbookSearch = new GuestbookSearchCondition();
        }
        guestbookSearch.setTargetUserId(targetUserId);
        guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
        
        ModelAndView mav = new ModelAndView("/support/profile/guestbookview");

        SearchResult<Guestbook> searchResult = guestbookService.listGuestbook(guestbookSearch);
        
        mav.addObject("searchResult", searchResult);
        mav.addObject("targetUserId", targetUserId);
        mav.addObject("size", searchResult.getRecordCount());
        mav.addObject("guestbookSearch", guestbookSearch);
        
        return mav;
    }
    
    /**
     * 사용자 프로파일 정보 수정 저장시 수정 저장 기록을 Activity Stream 에 남기기 위한 메서드
     * @param sessionuser 현재 로그인 중인 사용자 세션 User 객체
     * @param user 업데이트를 위해 받아온 User 객체
     */
    public void updateProfileActivityStream(User sessionuser, User user){
        
        String sessionMobile = "";
        String userMobile = "";
        String sessionOfficePhoneNo = "";
        String userOfficePhoneNo = "";
        String sessionLocaleCode = "";
        String userLocaleCode = "";
        
        if( !(StringUtil.isEmpty(sessionuser.getMobile())) ){
            sessionMobile = sessionuser.getMobile();
        }
        if( !(StringUtil.isEmpty(user.getMobile())) ){
            userMobile = user.getMobile();
        }
        if( !(StringUtil.isEmpty(sessionuser.getOfficePhoneNo())) ){
            sessionOfficePhoneNo = sessionuser.getOfficePhoneNo();
        }
        if( !(StringUtil.isEmpty(user.getOfficePhoneNo())) ){
            userOfficePhoneNo = user.getOfficePhoneNo();
        }
        if( !(StringUtil.isEmpty(sessionuser.getLocaleCode())) ){
            sessionLocaleCode = sessionuser.getLocaleCode();
        }
        if( !(StringUtil.isEmpty(user.getLocaleCode())) ){
            userLocaleCode = user.getLocaleCode();
        }
        
        if( !(sessionMobile.equals(userMobile)) ){
            // Activity Stream 추가 
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_PHONENUMBER_UPDATE, user.getUserId(),sessionuser.getMobile()+"/"+user.getMobile(), user.getUserId(), 1);
        }

        if( !(sessionOfficePhoneNo.equals(userOfficePhoneNo)) ){
            // Activity Stream 추가 
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_STATUS_UPDATE, user.getUserId(),sessionuser.getOfficePhoneNo()+"/"+user.getOfficePhoneNo(), user.getUserId(), 1);
        }
        
        if( !(sessionLocaleCode.equals(userLocaleCode)) ){
            // Activity Stream 추가 
            activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_PROFILE, IKepConstant.ACTIVITY_CODE_PROFILE_STATUS_UPDATE, user.getUserId(),sessionuser.getLocaleCode()+"/"+user.getLocaleCode(), user.getUserId(), 1);
        }
    }

}
