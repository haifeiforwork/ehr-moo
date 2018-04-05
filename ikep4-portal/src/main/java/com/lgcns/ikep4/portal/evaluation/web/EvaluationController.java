package com.lgcns.ikep4.portal.evaluation.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value="/portal/evaluation")
public class EvaluationController extends BaseController {

	@Autowired
	private ACLService aclService;
	
	@Autowired
    private UserDao userDao;

	//Log
	protected final Log log = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimEss", user.getUserId());

	}

	public String getLinkPath(HttpServletRequest request) {

		String serverLinkUrl = "";

		if((request.getServerName().toLowerCase()).indexOf("eptest.moorim.co.kr")>-1) {
			serverLinkUrl ="http://smrmeqas.moorim.co.kr:8000/sap/bc/bsp/sap/";
		} else {
			serverLinkUrl ="http://smrmep02.moorim.co.kr:8000/sap/bc/bsp/sap/";
		}
		return serverLinkUrl;

	}

	@RequestMapping(value = "/evaluationMain.do")
	public ModelAndView evaluationMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/main");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMain");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/performance.do")
	public ModelAndView performance(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/performance");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		

		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "performance");
		mav.addObject("serverLinkUrl", getLinkPath(request));
//
		return mav;
	}

	@RequestMapping(value = "/competence.do")
	public ModelAndView competence(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/competence");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "competence");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/multiside.do")
	public ModelAndView multiside(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/multiside");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "multiside");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/idp.do")
	public ModelAndView idp(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/idp");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "idp");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/promotion.do")
	public ModelAndView promotion(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/promotion");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "promotion");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/technical.do")
	public ModelAndView technical(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/technical");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "technical");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/privilege.do")
	public ModelAndView privilege(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/privilege");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "privilege");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/position.do")
	public ModelAndView position(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/position");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "position");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

	@RequestMapping(value = "/etc.do")
	public ModelAndView etc(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/portal/evaluation/etc");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "etc");
		mav.addObject("serverLinkUrl", getLinkPath(request));

		return mav;
	}

}
