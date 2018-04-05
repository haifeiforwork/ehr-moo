/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.web;

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

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrLinereplyService;
import com.lgcns.ikep4.collpack.forum.service.FrRecommendService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: FrLinereplyController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
public class FrLinereplyController extends ForumBaseController{

	@Autowired
	private FrLinereplyService frLinereplyService;
	
	@Autowired
	private FrRecommendService frRecommendService;
	
	/**
	 * 댓글 등록
	 * @param frLinereply	댓글 객체
	 * @param result
	 * @param isAdmin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createLinereply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String onSubmitReply(@ValidEx FrLinereply frLinereply, BindingResult result, @ModelAttribute("isAdmin") boolean isAdmin, ModelMap model) {
		
		if(result.hasErrors()) {
	        throw new IKEP4AjaxValidationException(result, messageSource);
	    }
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			frLinereply.setUpdaterId(user.getUserId());
			frLinereply.setUpdaterName(user.getUserName());
			
			//수정이면
			if (!StringUtil.isEmpty(frLinereply.getLinereplyId())) {
				
				FrLinereply linereply = frLinereplyService.get(frLinereply.getLinereplyId());
				//권한체크
				accessCheck(isAdmin, linereply.getRegisterId());
				
				frLinereplyService.update(frLinereply);
				
			} else {	//등록이면
				
				//부모 ID가 있으면
				if (!StringUtil.isEmpty(frLinereply.getLinereplyParentId())) {
					
					FrLinereply parentLinereply = frLinereplyService.get(frLinereply.getLinereplyParentId());
					
					frLinereply.setLinereplyGroupId(parentLinereply.getLinereplyGroupId());
					frLinereply.setStep(parentLinereply.getStep()+1);
					frLinereply.setIndentation(parentLinereply.getIndentation()+1);
					
					if(frLinereply.getIndentation() >= ForumConstants.LINEREPLY_INDENTATION_LIMITE){
						throw new IKEP4AjaxException("indentation over", null);
					}
					
					
				} else {
					frLinereply.setStep(0);
					frLinereply.setIndentation(0);
				}
				
				frLinereply.setRegisterId(user.getUserId());
				frLinereply.setRegisterName(user.getUserName());
				
				Portal portal = (Portal) getRequestAttribute("ikep.portal");
				frLinereply.setPortalId(portal.getPortalId());
				
				
				frLinereplyService.create(frLinereply);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		model.clear();
		
		return "redirect:listReply.do?itemId="+frLinereply.getItemId();
	}


	/**
	 * 댓글 리스트
	 * @param itemId	토론글 ID
	 * @param pageIndex	페이지 index
	 * @return
	 */
	@RequestMapping("/listReply.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView list(@RequestParam("itemId") String itemId
							,@RequestParam(value = "pageIndex", required = false, defaultValue="1") int pageIndex) {

		ModelAndView mav = new ModelAndView("collpack/forum/linereplyListMore");
		
		try {
		
			FrSearch frSearch = new FrSearch();
			
			frSearch.setItemId(itemId);
			
			//게시물 삭제플래그 제외 카운트
			int count = frLinereplyService.getCountList(frSearch);
			mav.addObject("totalCount", count);
			
			//페이징조건
			Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
			int pagePer = Integer.parseInt(prop.getProperty("forum.view.linereply.list.pagePer"));		//라인수
			
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setPageIndex(pageIndex);
			searchCondition.setPagePerRecord(pagePer);
			 
			searchCondition.terminateSearchCondition(count); 
			 
			mav.addObject("linereplyCondition", searchCondition);
			
			//게시물 리스트 가져오기
			frSearch.setEndRowIndex(searchCondition.getEndRowIndex());
			frSearch.setStartRowIndex(searchCondition.getStartRowIndex());
			 
			 List<FrLinereply> list = frLinereplyService.list(frSearch);
			 mav.addObject("linereplyList", list);
			 
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return mav;
	}
	
	
	/**
	 *  댓글 삭제하기
	 * @param linereplyId	댓글 Id
	 * @param itemId		토론글 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String removeItem(@RequestParam("linereplyId") String linereplyId
							, @RequestParam("itemId") String itemId
							, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		FrLinereply  frLinereply = frLinereplyService.get(linereplyId);
		
		//권한체크
		accessCheck(isAdmin, frLinereply.getRegisterId());
		
		try {
			
			if(isAdmin){	//관리자면 영구 삭제
				frLinereplyService.remove(linereplyId);
			} else {
				frLinereplyService.updateDeleteFlagTure(linereplyId, itemId);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "redirect:listReply.do?itemId="+itemId;
	}
	
	/**
	 * 댓글 되살리기
	 * @param linereplyId	댓글 ID
	 * @param itemId		토론글 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/aliveItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody  String aliveItem(@RequestParam("linereplyId") String linereplyId
						, @RequestParam("itemId") String itemId
						, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		FrLinereply  frLinereply = frLinereplyService.get(linereplyId);
		
		//권한체크
		accessCheck(isAdmin, frLinereply.getRegisterId());

		try {
			frLinereplyService.updateDeleteFlagFalse(linereplyId, itemId);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	
	/**
	 * 추천하기
	 * @param linereplyId	댓글 ID
	 * @param discussionId	토론 주제 ID
	 * @return
	 */
	@RequestMapping("/addRecommend.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String addRecommend(@RequestParam("linereplyId") String linereplyId
											, @RequestParam("discussionId") String discussionId) {
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			String userId =  user.getUserId();
			
			boolean feedback = frRecommendService.exists(linereplyId, userId);
			
			if(feedback){		//존재하면
				return "exists";
			}
			frRecommendService.create(linereplyId, userId, discussionId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}


}
