package com.lgcns.ikep4.lightpack.planner.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "lightpack/planner/mandate")
public class MandateController extends BaseController {
	private static final String SUCCESS = "success";
	private static final String FAIL 	= "fail";
	
	@Autowired
	private CalendarService service;
	
	@RequestMapping("/formView")
	public ModelAndView formView(@RequestParam(value = "mode", required = false) String mode) {
		Mandator mandator = service.getTrustee(getUser().getUserId());
		ModelAndView mav;
		if(!"iframe".equals(mode)) {			
			mav = new ModelAndView("lightpack/planner/mandate");
		} else {
			mav = new ModelAndView("lightpack/planner/iframe/mandate");
		}
		try {
			if(mandator != null) {
				mav.addObject(mandator);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@RequestMapping("/formView1")
	public ModelAndView formView1(@RequestParam(value = "mode", required = false) String mode) {
		Mandator mandator = service.getTrustee(getUser().getUserId());
		ModelAndView mav;
		if(!"iframe".equals(mode)) {			
			mav = new ModelAndView("lightpack/planner/mandate1");
		} else {
			mav = new ModelAndView("lightpack/planner/iframe/mandate");
		}
		try {
			if(mandator != null) {
				mav.addObject(mandator);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@RequestMapping("/editForm")
	public ModelAndView editForm(@RequestParam(value = "mode", required = false) String mode) {
		Mandator mandator = service.getTrustee(getUser().getUserId());
		ModelAndView mav;
		if(!"iframe".equals(mode)) {
			mav = new ModelAndView("lightpack/planner/mandateEditForm");
		} else {
			mav = new ModelAndView("lightpack/planner/iframe/mandateEditForm");
		}
		try {
			if(mandator != null) {
				mav.addObject(mandator);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(@ModelAttribute("mandator") Mandator mandator,
			@RequestParam(value = "mode", required = false) String mode) {
		User user = getUser();
		String portalId = user.getPortalId();
		String trusteeId = mandator.getTrusteeId();
		try {
			String mandatorId = mandator.getMandatorId();
			if(mandatorId != null) {
				service.deleteMandator(mandatorId);
			} 
			service.createMandator(user, portalId, trusteeId);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if(!"iframe".equals(mode)) {
			return new ModelAndView("redirect:/lightpack/planner/mandate/formView.do");
		} else {
			return new ModelAndView("redirect:/lightpack/planner/mandate/formView.do?mode=iframe");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getMyMandators")
	public @ResponseBody Map<String, Object>
	getMyMandators() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = getUser();
			
			List<Map> list = service.getMyMandators(user.getUserId());
			
			result.put("list", list);
			result.put("success", SUCCESS);
		} catch(Exception ex) {
			result.put("success", FAIL);
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delte(@RequestParam("mandatorId") String mandatorId,
			@RequestParam(value = "mode", required = false) String mode) {
		try {
			service.deleteMandator(mandatorId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!"iframe".equals(mode)) {
			return new ModelAndView("redirect:/lightpack/planner/mandate/formView.do");
		} else {
			return new ModelAndView("redirect:/lightpack/planner/mandate/formView.do?mode=iframe");
		}
	}
	
	private User getUser() {
		return (User)getRequestAttribute("ikep.user"); 
	}
}
