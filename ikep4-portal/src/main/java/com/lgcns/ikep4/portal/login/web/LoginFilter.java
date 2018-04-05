/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.portal.front.service.UserCountService;
import com.lgcns.ikep4.portal.front.service.impl.UserCountServiceImpl;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * Login Handler 구현 (스프링 시큐리티를 제거하고 같은 기능을 수행하는 서블릿)
 * 
 * @author 주길재
 * @version $Id: LoginFilter.java 17380 2012-03-07 10:35:33Z arthes $
 */
@Service(value="loginFilter")
public class LoginFilter extends GenericFilterBean {
	private static final int AJAX_HTTP_TIMEOUTME = 511;

	private static final String SESSION_EXPIRE_URI = "/loginForm.do?error=2";

	private static final String LOGIN_FORM_URI = "/loginForm.do";

	private List<String> urlList;
	
	private final String IKEP4LICENSE_USERCOUNT = "ikep4license.usercount";

	protected FilterConfig filterConfig;
	
	@Autowired
	private UserCountService userCountService; 

	/**
	 * init() 메소드가 호출되면 필터가 생성된다.
	 */
	public void initFilterBean() throws ServletException {
		this.filterConfig = super.getFilterConfig();
		this.urlList = new ArrayList<String>();

		// properties에서 none_filter_urls 정보를 가져온다.
		Properties prop = PropertyLoader.loadProperties("/configuration/authentication.properties");
		String url = prop.getProperty("none_filter_urls").replaceAll(" ", "");
		String[] urls = url.split(",");

		this.urlList = Arrays.asList(urls);
	}
	
	public void destroy() {
		this.filterConfig = null;
	}
	
	/*
	 * 라이센스에서 사용하기 위한 session 정보
	 */
	private void setLicenseSession() {
		String readUserCount = (String) RequestContextHolder.currentRequestAttributes().getAttribute(this.IKEP4LICENSE_USERCOUNT,RequestAttributes.SCOPE_SESSION);
		
		if(readUserCount == null) {
			if(userCountService == null) {
				ServletContext context = super.getServletContext();
				ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
				userCountService = (UserCountService)ac.getBean("userCountService");
			}

			String userCount = userCountService.userCount();
			RequestContextHolder.currentRequestAttributes().setAttribute(this.IKEP4LICENSE_USERCOUNT, userCount, RequestAttributes.SCOPE_SESSION);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		try {
			HttpServletRequest req = (HttpServletRequest) request;

			HttpServletResponse res = (HttpServletResponse) response;

			HttpSession session = ((HttpServletRequest) request).getSession();

			String url = req.getServletPath();

			String redirectUri = LOGIN_FORM_URI;
			
			// 라이센스 세션 생성
			setLicenseSession();
			
			// 제외 대상 Uri일 경우 체크
			if (urlList.contains(url)) {
				//System.out.println("##################url:"+url);
				chain.doFilter(request, response);
				return;
			}

			// session expire 처리 및 메인 화면 Ajax call skip 처리
			if (req.getRequestedSessionId() != null && !req.isRequestedSessionIdValid()) {
				String ajaxFlag = req.getHeader("x-requested-with");

				// 헤더 interval일 경우, loginForm redirect 수행 안함
				if ("XMLHttpRequest".equals(ajaxFlag)) {
					res.sendError(AJAX_HTTP_TIMEOUTME, "sessiontimeout");
					return;
				}

				// session expire uri 대입
				String preRedirectUri = getRequestUriWithParameter(req);
				session.setAttribute("ikep.preRedirectUri", preRedirectUri);
				redirectUri = SESSION_EXPIRE_URI;
			} else if (request instanceof HttpServletRequest) {
				// authentication check
				if (session != null) {
					User user = (User) session.getAttribute("ikep.user");

					if (user != null) {
						/*
						// 인증된 사용자면 next filter들을 수행한다.
						chain.doFilter(request, response);
						return;
						*/
						
						//세션 타임아웃 설정을 한다.(24시간)
						if(session.getAttribute("ikep.loginTime")!=null){
							
							long loginTime = (Long)session.getAttribute("ikep.loginTime");
							long curTime = System.currentTimeMillis();
							
							long mills=curTime-loginTime;							
							long hour=mills/60000/60;
							
							
							if(hour>=24){
								//String preRedirectUri = getRequestUriWithParameter(req);
								session.setAttribute("ikep.user", null);
								redirectUri = SESSION_EXPIRE_URI;
								
							}else{								 
								chain.doFilter(request, response);
								return;
							}								
						}
						
					} else {
						// Request URI를 세션에 임시 저장한다.
						String preRedirectUri = getRequestUriWithParameter(req);
						session.setAttribute("ikep.preRedirectUri", preRedirectUri);
					}
				}
			}

			ServletContext context = super.getServletContext();
			context.getRequestDispatcher(redirectUri).forward(request, response);
			// res.sendRedirect(req.getContextPath()+redirectUri);
		} catch (IOException ie) {
			throw new IKEP4ApplicationException("", ie);
		} catch (ServletException se) {
			throw new IKEP4ApplicationException("", se);
		}
	}

	/**
	 * Request url 및 parameter 정보를 리턴한다.
	 * 
	 * @param request
	 * @return
	 */
	private String getRequestUriWithParameter(HttpServletRequest request) {
		String queryString = request.getQueryString();
		String paramUri = request.getServletPath();
		
		if(paramUri.indexOf("portalMain.do") == -1) {
			paramUri = "/portal/main/portalMain.do?mainFrameUrl=" + paramUri;
		}

		if (queryString != null) {
			paramUri += "?" + request.getQueryString();
		}

		return paramUri;
	}
}
