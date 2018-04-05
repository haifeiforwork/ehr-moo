/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.externalSNS.base.Constant;
import com.lgcns.ikep4.support.externalSNS.model.TwitterModel;
import com.lgcns.ikep4.support.externalSNS.service.TwitterService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Twitter 연동 및 조회 입력을 위한 Controller
 * 
 * 연동 API 다운로드
 * http://twitter4j.org/en/index.jsp
 * 
 * @author 홍정관
 * @version $Id: TwitterController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/externalSNS")
public class TwitterController extends BaseController {
	
	/**
	 * User 정보 컨트롤용 Service
	 */
	@Autowired
	private UserService userService;

	/**
	 * 트위터  정보 컨트롤용 Service
	 */
	@Autowired
	private TwitterService twitterService;


	/**
	 * 트위터 연동 요청 
	 * @param model model View 정보 객체
	 * @return redirect URL
	 */
	@RequestMapping(value = "/TwitterMain.do", method = RequestMethod.GET)
	public String twitterMain(Model model) {

		/*** REALSOURCE START ***/
		return "redirect:/support/externalSNS/TwitterAuth.do";
		/*** REALSOURCE END ****/
	}

	/**
	 * 페이스 북 인증  연동 처리 요청 
	 * @param poolIdx 부모창 갱신시 사용하는 팝업 pool Id
	 * @return 연동 요청 결과 
	 */
	@RequestMapping(value = "/TwitterAuth.do", method = RequestMethod.GET)
	public ModelAndView twitterAuth(@RequestParam("poolIdx") String poolIdx){

		ModelAndView mav = new ModelAndView("/support/externalSNS/TwitterMain");
		setRequestAttribute("poolIdx", poolIdx);
		
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.twitter.key");
		String secret = prop.getProperty("externalsns.twitter.secret");
		
		/*** REALSOURCE START ***/
		Twitter twitter = new Twitter();
		RequestToken requestToken = null;
		twitter.setOAuthConsumer( key, secret);

		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		if( StringUtil.isEmpty(user.getTwitterAccount()) && StringUtil.isEmpty(user.getTwitterAuthCode()) ){			
			try{
				
				requestToken = twitter.getOAuthRequestToken();
				if (!"".equals(requestToken) && requestToken != null) {
					if( user.getLocaleCode().equals("ko") ){
						mav.addObject("authenticationURL", requestToken.getAuthenticationURL());
					}else{
						mav.addObject("authenticationURL", requestToken.getAuthorizationURL());
					}
		
					setRequestAttribute("token", requestToken.getToken());
					setRequestAttribute("tokenSecret", requestToken.getTokenSecret());
							
					mav.addObject("authFlag", "NeedAuth");
				}
			} catch (TwitterException twe) {
				mav.addObject("authFlag", "AuthFalure");
			}

		}else{
			mav.addObject("authFlag", "AlreadyAuth");
		}

		return mav; 
	}

	/**
	 * 트위터 연동 결과 인증값 처리 
	 * @param oauth_token 트위터 인증 토큰 값
	 * @param oauth_verifier 트위터 연동 인증값
	 * @param model model View 정보 객체
	 * @return 인증 결과 처리 View
	 */
	@RequestMapping(value = "/TwitterAuthSuccess.do", method = RequestMethod.GET)
	public ModelAndView twitterAuthSuccess(@RequestParam String oauth_token
										, String oauth_verifier, Model model) {

		ModelAndView mav = new ModelAndView("/support/externalSNS/TwitterAuthSuccess");
		mav.addObject("poolIdx", getRequestAttribute("poolIdx") );
		
		/*** REALSOURCE START ****/
		try {
			Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
			String key = prop.getProperty("externalsns.twitter.key");
			String secret = prop.getProperty("externalsns.twitter.secret");
			
			Twitter twitter = new Twitter();
			twitter.setOAuthConsumer(key, secret);
			User user = (User) getRequestAttribute("ikep.user");
			
			RequestToken requestToken = twitter.getOAuthRequestToken();
			//String token = requestToken.getToken();
			String tokenSecret = requestToken.getTokenSecret();
			
			if( StringUtil.isEmpty(user.getTwitterAccount()) && StringUtil.isEmpty(user.getTwitterAuthCode()) ){
				
				AccessToken accessToken = null;
				accessToken = twitter.getOAuthAccessToken(oauth_token, tokenSecret);
	
				// Twitter 계정 정보 조회용 Start ******************************************
				//System.out.println( "######################################## : " + twitter.getUserId() );
				//System.out.println( "######################################## : " + twitter.getUserAgent() );
				//System.out.println( "######################################## : " + twitter.getUserDetail(twitter.getUserId()).toString() );
				// Twitter 계정 정보 조회용 End ******************************************
				
				// accessTokenValue DB Insert
				User twitterAuthUser = new User();
				twitterAuthUser.setTwitterAccount(accessToken.getToken());
				twitterAuthUser.setTwitterAuthCode(accessToken.getTokenSecret());
				twitterAuthUser.setUpdaterId(user.getUserId());
				twitterAuthUser.setUpdaterName(user.getUserName());
				twitterAuthUser.setUserId(user.getUserId());
				userService.updateTwitterInfo(twitterAuthUser);
	
				// Session(ikep.user) 재설정
				user.setTwitterAccount(accessToken.getToken());
				user.setTwitterAuthCode(accessToken.getTokenSecret());
				setRequestAttribute("ikep.user", user);
				
				mav.addObject("authFlag", "SuccessAuth");
				
			}else{
				
				AccessToken accessToken = twitter.getOAuthAccessToken(oauth_token, tokenSecret);
				if( !(accessToken.getToken().equals(user.getTwitterAccount()) && accessToken.getTokenSecret().equals(user.getTwitterAuthCode()) )){
				
					// accessTokenValue DB Insert
					User twitterAuthUser = new User();
					twitterAuthUser.setTwitterAccount(accessToken.getToken());
					twitterAuthUser.setTwitterAuthCode(accessToken.getTokenSecret());
					twitterAuthUser.setUpdaterId(user.getUserId());
					twitterAuthUser.setUpdaterName(user.getUserName());
					twitterAuthUser.setUserId(user.getUserId());
					userService.updateTwitterInfo(twitterAuthUser);
		
					// Session(ikep.user) 재설정
					user.setTwitterAccount(accessToken.getToken());
					user.setTwitterAuthCode(accessToken.getTokenSecret());
					setRequestAttribute("ikep.user", user);
					
					mav.addObject("authFlag", "SuccessAuth");
					
				}else{
					mav.addObject("authFlag", "AlreadyAuth");
				}
			}
			
		} catch (TwitterException tew) {
			mav.addObject("authFlag", "FalureAuth");
		}
		/*** REALSOURCE END ****/
		return mav ;
	}

	@RequestMapping(value = "/TwitterList.do")
	public ModelAndView twitterList(@RequestParam(value = "sectionFlag", required = false) String paramSectionFlag,
			@RequestParam(value = "twitterCount", required = false) int paramTwitterCount,
			@RequestParam(value = "twitterPage" , required = false) int paramTwitterPage,
			Model model) {

		String sectionFlag = paramSectionFlag;
		int twitterCount = paramTwitterCount;
		int twitterPage = paramTwitterPage;
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		/*** REALSOURCE START ****/
		ModelAndView mvn = new ModelAndView("/support/externalSNS/twitterTimelineList");
		try {
			List<Status> statuses = null;
			
			Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
			String key = prop.getProperty("externalsns.twitter.key");
			String secret = prop.getProperty("externalsns.twitter.secret");
			
			Twitter twitter = new Twitter();
			twitter.setOAuthConsumer(key, secret);
			/**
			 * 인증된 사용자에게 부여되는 AccessToken 값으로 지속적으로 접근 가능
			 */
			String accessToken = "";
			String accessTokenSecret = "";
			
			// Session Select
			accessToken = user.getTwitterAccount();
			accessTokenSecret = user.getTwitterAuthCode();

			// 접속 요청을 위해 SET
			twitter.setOAuthAccessToken(accessToken, accessTokenSecret);

			if (StringUtil.isEmpty(sectionFlag)) {
				sectionFlag = "homeTimeLine";
			}

			if (0 == twitterCount) {
				twitterCount = Constant.TWITTER_COUNT_DEFAULT;
			}

			if (0 == twitterPage) {
				twitterPage = 1;
			}

//			if (log.isDebugEnabled()) {
//				log.debug("twitterCount:" + twitterCount);
//				log.debug("twitterPage:" + twitterPage);
//			}
			// 보여질 페이지 개수 처리
			Paging page = new Paging();

			page.count(twitterCount);
			page.setPage(twitterPage);

			// 카테고리 선택(기본 홈타임라인)
			if (sectionFlag.equals("homeTimeLine")) {
				statuses = twitter.getHomeTimeline(page); // 홈 타임라인
			} else if (sectionFlag.equals("mentions")) {
				statuses = twitter.getMentions(page);// Mention
			} else if (sectionFlag.equals("retweet")) {
				statuses = twitter.getRetweetedToMe(page);// ReTweet(세부종류 다양)
			}

			// statuses = twitter.getReplies();//Reply

			List<TwitterModel> info = new ArrayList<TwitterModel>();
			for (Status status : statuses) {
				TwitterModel vo = new TwitterModel();
				vo.setId(status.getId());
				vo.setName(status.getUser().getName());
				vo.setScreenName(status.getUser().getScreenName());
				vo.setUrl(status.getUser().getURL());
				vo.setText(status.getText());
				vo.setCreateAt(status.getCreatedAt());
				vo.setProfileImage(status.getUser().getProfileImageURL());
				vo.setSource(status.getSource());
				info.add(vo);
				if (log.isDebugEnabled()) {
					log.debug("vo.toString():" + vo.toString());
				}
			}

			mvn.addObject("info", info);
			mvn.addObject("sectionFlag", sectionFlag);
		} catch (TwitterException tew) {

			// accessTokenValue DB Insert
			User twitterAuthUser = new User();
			twitterAuthUser.setTwitterAccount("");
			twitterAuthUser.setTwitterAuthCode("");
			twitterAuthUser.setUpdaterId(user.getUserId());
			twitterAuthUser.setUpdaterName(user.getUserName());
			twitterAuthUser.setUserId(user.getUserId());
			
			userService.updateTwitterInfo(twitterAuthUser);

			// Session(ikep.user) 재설정
			user.setTwitterAccount("");
			user.setTwitterAuthCode("");
			setRequestAttribute("ikep.user", user);
			
			mvn.addObject("info", "TWITTER_ERROR");
		} catch (Exception e) {

			// accessTokenValue DB Insert
			User twitterAuthUser = new User();
			twitterAuthUser.setTwitterAccount("");
			twitterAuthUser.setTwitterAuthCode("");
			twitterAuthUser.setUpdaterId(user.getUserId());
			twitterAuthUser.setUpdaterName(user.getUserName());
			twitterAuthUser.setUserId(user.getUserId());
			
			userService.updateTwitterInfo(twitterAuthUser);

			// Session(ikep.user) 재설정
			user.setTwitterAccount("");
			user.setTwitterAuthCode("");
			setRequestAttribute("ikep.user", user);
			
			mvn.addObject("info", "TWITTER_ERROR");
		}
		/*** REALSOURCE END ****/
		return mvn;
	}

	@RequestMapping(value = "/TwitterUp.do", method = RequestMethod.POST)
	public String twitterUp(@RequestParam(value = "content", required = true) String content) throws TwitterException {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		String accessToken = user.getTwitterAccount();
		String accessTokenSecret = user.getTwitterAuthCode();
		
		twitterService.twitterUp(accessToken, accessTokenSecret, content);
		
//		Status status = null;
//		//Twitter twitter = new Twitter();
//		//twitter.setOAuthConsumer(Constant.CONSUMER_KEY, Constant.CONSUMER_SECRET);
//		String accessToken = "";
//		String accessTokenSecret = "";
//		try {
//
//			if (StringUtil.isEmpty(user.getTwitterAccount()) && StringUtil.isEmpty(user.getTwitterAuthCode())) {
//				// DB select
//			} else {
//				// get Session
//				accessToken = user.getTwitterAccount();
//				accessTokenSecret = user.getTwitterAuthCode();
//			}
//
//			twitter.setOAuthConsumer(Constant.CONSUMER_KEY, Constant.CONSUMER_SECRET);
//			twitter.setOAuthAccessToken(accessToken, accessTokenSecret);
//			status = twitter.updateStatus(content);
//		} catch (TwitterException twe) {
//
//		}
		return "redirect:/support/externalSNS/TwitterList.do?sectionFlag=homeTimeLine";
	}
}
