/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.facebookapi.Attachment;
import com.google.code.facebookapi.AttachmentMediaImage;
import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.externalSNS.base.Constant;
import com.lgcns.ikep4.support.externalSNS.model.FacebookModel;
import com.lgcns.ikep4.support.externalSNS.service.FacebookService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Facebook 인증  및 페이스북 정보 조회 등록을 위한 Controller
 * 
 * API 다운로드 사이트 
 * http://code.google.com/p/facebook-java-api/ 
 * 
 * @author 홍정관
 * @version $Id: FacebookController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/externalSNS")
public class FacebookController extends BaseController {
	
	/**
	 * User 정보 컨트롤용 Service
	 */
	@Autowired
	private UserService userService;

	/**
	 * Facebook 정보 컨트롤용 Service
	 */
	@Autowired
	private FacebookService facebookService;
	
	//private static final String[] perms = new String[] { "publish_stream", "email", "read_stream", ",offline_access" };

	/**
	 * Facebook 인증용 리턴 URL 생성 호출
	 * @param serverUri 인증 용 리턴 URL
	 * @param poolIdx 부모창 갱신시 사용하는 팝업 pool Id
	 * @return 인증 redirect url
	 */
	public static String getLoginRedirectURL(String serverUri, String poolIdx) {

		String returnUrl = serverUri + Constant.REDIRECT_URL + "?poolIdx=" + poolIdx;

		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.facebook.key");
		
		StringBuffer forwardUri = new StringBuffer();
		forwardUri.append(Constant.FB_AUTH_URL).append(Constant.FB_AUTH_PARAM1).append(key).append(Constant.FB_AUTH_PARAM2)
				.append(returnUrl).append(Constant.FB_AUTH_PARAM3).append(returnUrl)
				.append(Constant.FB_AUTH_PARAM4)
				.append(Constant.FB_AUTH_PARAM5);

		return forwardUri.toString();
	}

	/**
	 * 페이스 북 인증 요청 페이지
	 * @param poolIdx 부모창 갱신시 사용하는 팝업 pool Id
	 * @param model model View 정보 객체
	 * @param request HttpServlet Request 요청 객체
	 * @return
	 */
	@RequestMapping(value = "/FacebookMain.do", method = RequestMethod.GET)
	public String goFacebookMain(@RequestParam("poolIdx") String poolIdx, Model model, HttpServletRequest request ) {
		
		//setRequestAttribute("poolIdx", poolIdx);
		
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String id = prop.getProperty("externalsns.facebook.id");
		String key = prop.getProperty("externalsns.facebook.key");
		String secret = prop.getProperty("externalsns.facebook.secret");
		
		model.addAttribute("appId", id);
		model.addAttribute("appKey", key);
		model.addAttribute("appSecret", secret);

		String serverUri = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getServletPath()));
		
		model.addAttribute("facebookAuthUrl", getLoginRedirectURL(serverUri, poolIdx));
		
		User user = (User) getRequestAttribute("ikep.user");
		if( StringUtil.isEmpty(user.getFacebookAuthCode()) ){
			model.addAttribute("authFlag", "NeedAuth");
		}else{
			model.addAttribute("authFlag", "AlreadyAuth");
		}
		
		return "/support/externalSNS/FacebookMain";
	}

	/**
	 * 페이스북 인증 결과 처리 메서드
	 * @param sessionStr 페이스북 인증 결과 값
	 * @param poolIdx 부모창 갱신시 사용하는 팝업 pool Id
	 * @return 인증 결과 popup 페이지 View
	 */
	@RequestMapping(value = "/FacebookAuth.do", method = RequestMethod.GET)
	public ModelAndView facebookAuth(@RequestParam(value = "session", required = false, defaultValue = "") String sessionStr
									, @RequestParam("poolIdx") String poolIdx){

		ModelAndView mav = new ModelAndView("/support/externalSNS/FacebookAuth");
		mav.addObject("poolIdx", poolIdx);
		
		try{
			JSONObject json = new JSONObject(sessionStr);
			String sessionKey = json.getString("session_key");
			//System.out.println( "######################################## : " + sessionKey );
			
			if (!StringUtil.isEmpty(sessionKey)) {
				User user = (User) getRequestAttribute("ikep.user");
			
				if (StringUtil.isEmpty(user.getFacebookAuthCode()) || !user.getFacebookAuthCode().equals(sessionKey)) {
					// update new sessionKey to DB & ikep.user
	
					User facebookAuthUser = new User();
					facebookAuthUser.setFacebookAccount("");
					facebookAuthUser.setFacebookAuthCode(sessionKey);
					facebookAuthUser.setUpdaterId(user.getUserId());
					facebookAuthUser.setUpdaterName(user.getUserName());
					facebookAuthUser.setUserId(user.getUserId());
					userService.updateFacebookInfo(facebookAuthUser);
	
					user.setFacebookAuthCode(sessionKey);
					setRequestAttribute("ikep.user", user);
					
					mav.addObject("authFlag", "SuccessAuth");
					
					// Facebook 계정 정보 조회 용 Start *********************************
					//FacebookJsonRestClient facebook = new FacebookJsonRestClient(Constant.API_KEY, Constant.APP_SECRET, sessionKey);
					//FacebookJsonRestClient facebookClient = (FacebookJsonRestClient) facebook;
					//System.out.println( "#######################################11# : " +facebook.getCacheUserId() );
					//System.out.println( "#######################################22# : " +facebook.getApiKey() );
					//System.out.println( "#######################################232323# : " + facebook.users_getLoggedInUser());
					//System.out.println( "#######################################33# : " + facebookClient.getCacheAppUser());
					//System.out.println( "#######################################44# : " + facebookClient.getCacheUserId());
					
					//List<Long> longUsers = new ArrayList<Long>();
					//longUsers.add(facebook.users_getLoggedInUser());
					//JSONArray json2 = facebookClient.users_getInfo(longUsers,
			        //        EnumSet.of(ProfileField.UID, ProfileField.EMAIL_HASHES, ProfileField.PROXIED_EMAIL, ProfileField.NAME, ProfileField.SEX, ProfileField.BIRTHDAY));
					//System.out.println( "#######################################44# : " + json2.toString());
					// Facebook 계정 정보 조회 용 End *********************************
					
					
				}else{
					mav.addObject("authFlag", "AlreadyAuth");
				}
			}else{
				mav.addObject("authFlag", "FalureAuth");
			}
		} catch(Exception ex) {
			//throw new IKEP4AjaxException("code", ex);
			mav.addObject("authFlag", "FalureAuth");
		}
		
		return mav;
	}

	// @RequestMapping(value = "/FacebookList.do", method = RequestMethod.GET)
	// public String facebookList(Model model){
	//
	// return "/support/externalSNS/FacebookList";
	// }

	@RequestMapping(value = "/FacebookList.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView facebookList()  {

		/*** REALSOURCE START ****/
		ModelAndView mvn = new ModelAndView("/support/externalSNS/facebookTimelineList");

		User user = (User) getRequestAttribute("ikep.user");
		String sessionKey = "";

		if (!StringUtil.isEmpty(user.getFacebookAuthCode())) {
			sessionKey = user.getFacebookAuthCode();

			Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
			String key = prop.getProperty("externalsns.facebook.key");
			String secret = prop.getProperty("externalsns.facebook.secret");
			
			
			FacebookJsonRestClient facebook = new FacebookJsonRestClient(key, secret, sessionKey);
			FacebookJsonRestClient facebookClient = (FacebookJsonRestClient) facebook;
			
			try {

				// 글 등록
				// facebookClient.stream_publish(message, null, null, null,
				// null);

				// 목록 테스트
				long uid = facebookClient.users_getLoggedInUser();
				JSONArray ex = facebook.stream_getFilters(uid);

				JSONObject job = ex.getJSONObject(0);

				// JSONObject mod1 = ex.getJSONObject(1);
				// JSONObject mod2 = ex.getJSONObject(2);
				// JSONObject mod3 = ex.getJSONObject(3);
				// JSONObject mod4 = ex.getJSONObject(4);
				// JSONObject mod5 = ex.getJSONObject(5);
				// JSONObject mod6 = ex.getJSONObject(6);
				String filterKey = (String) job.get(Constant.API_CONSTANT_FILTER_KEY);//

				// 조회 기간은 오늘부터 한달 전 이고 50건으로 제한한다.
				Date start = null;
				Date end = null;
				
				Date date = new Date();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				end = cal.getTime();

				cal.add(Calendar.MONTH, -1 );
				start = cal.getTime();


				if (log.isDebugEnabled()) {
					log.debug("end:" + end);
					log.debug("start:" + start);
				}
				
				// facebook.stream_get(viewerId, sourceIds, start, end, limit,
				// filterKey, metadata)
				JSONObject items = facebook.stream_get(uid, null, start, end, Constant.FACEBOOK_LIMIT, filterKey, null);

				List<FacebookModel> info = new ArrayList<FacebookModel>();

				JSONArray jsonarray = items.getJSONArray("posts");
				for (int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonobject = jsonarray.getJSONObject(i);

					FacebookModel vo = new FacebookModel();
					vo.setActorId(jsonobject.getString("actor_id").toString());
					vo.setSourceId(jsonobject.getString("source_id").toString());
					vo.setPrivacy(jsonobject.getString("privacy").toString());
					vo.setAttachment(jsonobject.getString("attachment").toString());
					vo.setPostId(jsonobject.getString("post_id").toString());
					vo.setType(jsonobject.getString("type").toString());
					vo.setUpdatedTime(new Date(new Long(jsonobject.getString("updated_time")) * Constant.COUNT_FOR_MILLISECOND));
					vo.setActionLinks(jsonobject.getString("action_links").toString());
					vo.setTargetId(jsonobject.getString("target_id").toString());
					vo.setMessage(jsonobject.getString("message").toString());
					vo.setTaggedIds(jsonobject.getString("tagged_ids").toString());
					vo.setPermalink(jsonobject.getString("permalink").toString());
					vo.setIsHidden(jsonobject.getString("is_hidden").toString());
					vo.setLikes(jsonobject.getString("likes").toString());
					vo.setAppData(jsonobject.getString("app_data").toString());
					vo.setAppId(jsonobject.getString("app_id").toString());
					vo.setViewerId(jsonobject.getString("viewer_id").toString());
					vo.setCreatedTime(new Date(new Long(jsonobject.getString("created_time")) * Constant.COUNT_FOR_MILLISECOND));
					vo.setComments(jsonobject.getString("comments").toString());
					vo.setAttribution(jsonobject.getString("attribution").toString());

					info.add(vo);

					if (log.isDebugEnabled()) {
						log.debug("vo.toString():" + vo.toString());
					}
				}

				mvn.addObject("info", info);

			} catch (FacebookException e) {
				mvn.addObject("info", "FACEBOOK_ERROR");

				User facebookAuthUser = new User();
				facebookAuthUser.setFacebookAccount("");
				facebookAuthUser.setFacebookAuthCode("");
				facebookAuthUser.setUpdaterId(user.getUserId());
				facebookAuthUser.setUpdaterName(user.getUserName());
				facebookAuthUser.setUserId(user.getUserId());
				
				userService.updateFacebookInfo(facebookAuthUser);

				user.setFacebookAuthCode("");
				setRequestAttribute("ikep.user", user);
				
				//e.printStackTrace();
			} catch (Exception e) {
				mvn.addObject("info", "FACEBOOK_ERROR");

				User facebookAuthUser = new User();
				facebookAuthUser.setFacebookAccount("");
				facebookAuthUser.setFacebookAuthCode("");
				facebookAuthUser.setUpdaterId(user.getUserId());
				facebookAuthUser.setUpdaterName(user.getUserName());
				facebookAuthUser.setUserId(user.getUserId());
				
				userService.updateFacebookInfo(facebookAuthUser);

				user.setFacebookAuthCode("");
				setRequestAttribute("ikep.user", user);
				
				//e.printStackTrace();
			}
		} else {
			mvn.addObject("info", "FACEBOOK_ERROR");

			User facebookAuthUser = new User();
			facebookAuthUser.setFacebookAccount("");
			facebookAuthUser.setFacebookAuthCode("");
			facebookAuthUser.setUpdaterId(user.getUserId());
			facebookAuthUser.setUpdaterName(user.getUserName());
			facebookAuthUser.setUserId(user.getUserId());
			
			userService.updateFacebookInfo(facebookAuthUser);

			user.setFacebookAuthCode("");
			setRequestAttribute("ikep.user", user);
			
			//e.printStackTrace();
			//throw new UnsupportedOperationException();
		}

		return mvn;

		/*** REALSOURCE END ****/
	}

	@RequestMapping(value = "/facebookCreatePost.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void facebookCreatePost(String message, Model model) throws JSONException, FacebookException {

		User user = (User) getRequestAttribute("ikep.user");
		String sessionKey = user.getFacebookAuthCode();

		facebookService.createFacebook(sessionKey,  message);

		/*** REALSOURCE END ****/
	}

	// 샘플코드
	public static void setPost(String sessionKey, String content, String img) throws FacebookException {
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.facebook.key");
		String secret = prop.getProperty("externalsns.facebook.secret");
		
		FacebookJsonRestClient client = new FacebookJsonRestClient(key, secret, sessionKey);
		Attachment attachment = new Attachment();

		if (!StringUtil.isEmpty(img)) {
			attachment.addMedia(new AttachmentMediaImage(img, img));
		}
		client.stream_publish(content, attachment, null, null, null);
	}

	public static JSONArray getFriends(String sessionKey) throws FacebookException {
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.facebook.key");
		String secret = prop.getProperty("externalsns.facebook.secret");
		
		FacebookJsonRestClient client = new FacebookJsonRestClient(key, secret, sessionKey);
		return client.friends_get();
	}

	public static void statusShare(String sessionKey, String content) throws FacebookException {
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.facebook.key");
		String secret = prop.getProperty("externalsns.facebook.secret");
		
		FacebookJsonRestClient client = new FacebookJsonRestClient(key, secret, sessionKey);
		client.users_setStatus(content);
	}
}
