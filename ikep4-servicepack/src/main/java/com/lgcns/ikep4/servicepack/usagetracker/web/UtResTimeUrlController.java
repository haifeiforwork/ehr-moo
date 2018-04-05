package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeUrlService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value = "/servicepack/usagetracker/resTimeUrl")
public class UtResTimeUrlController extends BaseUsageTrackerController {
	
	@Autowired
	private UtResTimeUrlService utResTimeUrlService;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 응답시간 URL 리스트
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView getList(UtSearchCondition searchCondition) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setPortalId(portalId);
		
		SearchResult<UtResTimeUrl> searchResult = utResTimeUrlService.listBySearchCondition(searchCondition);
		
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/resTimeUrl/list");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		
		return mav;
	}
	
	/**
	 * 응답시간 URL 삭제
	 * @param resTimeUrlId
	 * @return
	 */
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public ModelAndView deleteAll(@RequestParam(value = "resTimeUrlId", required = true) String[] resTimeUrlId) {

		utResTimeUrlService.removeCheckedAll(resTimeUrlId);
		
		return new ModelAndView("redirect:/servicepack/usagetracker/resTimeUrl/list.do");
	}
	
	/**
	 * 응답시간 URL 등록폼
	 * @param utResTimeUrl
	 * @return
	 */
	@RequestMapping(value = "/createResTimeUrl", method = RequestMethod.GET)
	public ModelAndView createResTimeUrl(@RequestParam(value = "resTimeUrlId", required = false) String resTimeUrlId) {

		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/resTimeUrl/open");

		UtResTimeUrl utResTimeUrl = null;
		
		if(StringUtil.isEmpty(resTimeUrlId)) {
			utResTimeUrl = new UtResTimeUrl();
			utResTimeUrl.setUsage(0);
		} else {
			utResTimeUrl = utResTimeUrlService.read(resTimeUrlId);
		}
		
		mav.addObject("utResTimeUrl", utResTimeUrl);
		
		return mav;
	}
	
	/**
	 * 등록 or 수정
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createOrUpdateAjax", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map createOrUpdateAjax(UtResTimeUrl utResTimeUrl) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		int resultFlag = 0;
		
		try {
			utResTimeUrl.setPortalId(portalId);
			
			if (!StringUtil.isEmpty(utResTimeUrl.getResTimeUrlId())) {//수정모드
				utResTimeUrlService.update(utResTimeUrl);
			} else {//저장모드
				if(utResTimeUrlService.existsURL(utResTimeUrl.getResTimeUrl())){
					//저장할 url이 기존에 저장된 것일때
					resultFlag = 1;
				}else{
					utResTimeUrl.setResTimeUrlId(idgenService.getNextId());
					utResTimeUrl.setRegisterId(user.getUserId());
					utResTimeUrl.setRegisterName(user.getUserName());
					
					utResTimeUrlService.create(utResTimeUrl);
				}
			}
			
			result.put("resultFlag", resultFlag);
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return result;
	}
}