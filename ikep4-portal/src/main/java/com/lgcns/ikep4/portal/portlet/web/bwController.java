package com.lgcns.ikep4.portal.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;

@Controller
@RequestMapping(value = "/portal/portlet/bw")
public class bwController extends BaseController{

	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	/**
	 * 환율 포틀릿 조회(normalView) 
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView() {
		ModelAndView mav = new ModelAndView( "portal/portlet/bw/normalView");
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		mav.addObject("bwId", user.getBwId());
		
		mav.addObject("bwPwd", getBWPassword(user, portalId));
		return mav;
	}
	
	public String getBWPassword(User user, String portalId){

		UserAccount userAccount = loginUserDetailsService.loadUserByUsername(user.getUserId(), portalId);
		

		String passwordStr ="";
		if(user.getBwId().toLowerCase().contains("epbwif")){//아이디는 대문자로 들어오고 EPBWIF01~... 
			passwordStr = user.getBwId().toLowerCase();//패스워드는 소문자로 넘긴다.
		}else{
			passwordStr = EncryptUtil.decryptText(userAccount.getPassword());
		}
		
		return passwordStr;
	}
}