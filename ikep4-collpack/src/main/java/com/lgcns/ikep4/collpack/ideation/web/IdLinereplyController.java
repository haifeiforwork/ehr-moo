/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.web;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyRecommendService;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: IdLinereplyController.java 15406 2011-06-22 07:22:25Z loverfairy $$
 */
@Controller
public class IdLinereplyController extends IdeationBaseController{

	@Autowired
	private IdLinereplyService idLinereplyService;
	
	@Autowired
	private IdLinereplyRecommendService idLinereplyRecommendService;
	
	/**
	 * 댓글 등록
	 * @param idLinereply	댓글 객체
	 * @param result
	 * @param isAdmin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createLinereply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String onSubmitReply(@ValidEx IdLinereply idLinereply, BindingResult result
								, @ModelAttribute("isAdmin") boolean isAdmin, ModelMap model) {
		
		if(result.hasErrors()) {
	        throw new IKEP4AjaxValidationException(result, messageSource);
	    }
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			idLinereply.setUpdaterId(user.getUserId());
			idLinereply.setUpdaterName(user.getUserName());
			
			//수정이면
			if (!StringUtil.isEmpty(idLinereply.getLinereplyId())) {
				
				IdLinereply linereply = idLinereplyService.get(idLinereply.getLinereplyId());
				//권한체크
				accessCheck(isAdmin, linereply.getRegisterId());
				
				idLinereplyService.update(idLinereply);
				
			} else {	//등록이면
				
				//부모 ID가 있으면
				if (!StringUtil.isEmpty(idLinereply.getLinereplyParentId())) {
					
					IdLinereply parentLinereply = idLinereplyService.get(idLinereply.getLinereplyParentId());
					
					idLinereply.setLinereplyGroupId(parentLinereply.getLinereplyGroupId());
					idLinereply.setStep(parentLinereply.getStep()+1);
					idLinereply.setIndentation(parentLinereply.getIndentation()+1);
					
					if(idLinereply.getIndentation() >= IdeationConstants.LINEREPLY_INDENTATION_LIMITE){
						throw new IKEP4AjaxException("indentation over", null);
					}
					
					
				} else {
					idLinereply.setStep(0);
					idLinereply.setIndentation(0);
				}
				
				idLinereply.setRegisterId(user.getUserId());
				idLinereply.setRegisterName(user.getUserName());
				
				Portal portal = (Portal) getRequestAttribute("ikep.portal");
				idLinereply.setPortalId(portal.getPortalId());
				
				
				idLinereplyService.create(idLinereply);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		model.clear();
	
		return "redirect:listReply.do?itemId="+idLinereply.getItemId();
	}


	/**
	 * 댓글 리스트
	 * @param itemId		Idea ID
	 * @param pageIndex		페이지 index
	 * @return
	 */
	@RequestMapping("/listReply.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView list(@RequestParam("itemId") String itemId
							,@RequestParam(value = "pageIndex", required = false, defaultValue="1") int pageIndex) {

		ModelAndView mav = new ModelAndView("collpack/ideation/linereplyListMore");
		
		try {
		
			IdSearch idSearch = new IdSearch();
			
			idSearch.setItemId(itemId);
			
			//게시물 삭제플래그 제외 카운트
			int count = idLinereplyService.getCountList(idSearch);
			mav.addObject("totalCount", count);
			
			//페이징조건
			Properties prop = PropertyLoader.loadProperties("/configuration/ideation-conf.properties");
			int pagePer = Integer.parseInt(prop.getProperty("ideation.view.linereply.list.pagePer"));		//라인수
			
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setPageIndex(pageIndex);
			searchCondition.setPagePerRecord(pagePer);
			 
			searchCondition.terminateSearchCondition(count); 
			 
			mav.addObject("pageCondition", searchCondition);
			
			//게시물 리스트 가져오기
			idSearch.setEndRowIndex(searchCondition.getEndRowIndex());
			idSearch.setStartRowIndex(searchCondition.getStartRowIndex());
			 
			 List<IdLinereply> list = idLinereplyService.list(idSearch);
			 mav.addObject("linereplyList", list);
			 
			 
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return mav;
	}
	
	
	/**
	 * 댓글 임시 삭제하기
	 * @param linereplyId 댓글 ID
	 * @param itemId	idea ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String removeItem(@RequestParam("linereplyId") String linereplyId
							, @RequestParam("itemId") String itemId
							, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		IdLinereply  idLinereply = idLinereplyService.get(linereplyId);
		
		//권한체크
		accessCheck(isAdmin, idLinereply.getRegisterId());
		
		try {
			
			if(isAdmin){	//관리자면 영구 삭제
				idLinereplyService.remove(linereplyId);
			} else {
				idLinereplyService.updateDeleteFlagTure(linereplyId, itemId);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "redirect:listReply.do?itemId="+itemId;
	}
	
	/**
	 * 댓글 되살리기
	 * @param linereplyId	댓글 ID
	 * @param itemId		아이디어 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/aliveItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody  String aliveItem(@RequestParam("linereplyId") String linereplyId
						, @RequestParam("itemId") String itemId
						, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		IdLinereply  idLinereply = idLinereplyService.get(linereplyId);
		
		//권한체크
		accessCheck(isAdmin, idLinereply.getRegisterId());

		try {
			idLinereplyService.updateDeleteFlagFalse(linereplyId, itemId);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	
	/**
	 * 추천하기
	 * @param linereplyId	댓글 ID
	 * @return
	 */
	@RequestMapping("/addLineRecommend.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String aaddRecommend(@RequestParam("linereplyId") String linereplyId) {
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			String userId =  user.getUserId();
			
			boolean feedback = idLinereplyRecommendService.exists(linereplyId, userId);
			
			if(feedback){		//존재하면
				return "exists";
			}
			idLinereplyRecommendService.create(linereplyId, userId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}


}
