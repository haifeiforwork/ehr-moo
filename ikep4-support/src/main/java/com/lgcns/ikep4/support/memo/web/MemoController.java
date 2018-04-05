/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.memo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.memo.model.Memo;
import com.lgcns.ikep4.support.memo.search.MemoSearchCondition;
import com.lgcns.ikep4.support.memo.service.MemoService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 배성훤(sunghwonbae@gmail.com)
 * @version $Id: MemoController.java 7746 2011-04-26 03:44:44Z combinet $
 */
@Controller
@RequestMapping(value = "support/memo")
public class MemoController extends BaseController {

	@Autowired
	private MemoService memoService;
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;
	

	@Autowired
	private UserService userService; 
	

	@RequestMapping(value = "/memoList.do")
	public ModelAndView memoListView(MemoSearchCondition memoSearchCondition, 
			@RequestParam(value="searchConditionString", required=false) String searchConditionString,
			@RequestParam(value="returnMsg", required=false, defaultValue="NEW") String returnMsg) {
		
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(memoSearchCondition,
					"pageIndex",
					"pagePerRecord",
					"searchWord",
					"registerId"
			); 
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, memoSearchCondition); 
		}

		User user = this.getUser();
		memoSearchCondition.setRegisterId(user.getUserId());
		if(memoSearchCondition.getPagePerRecord()==10){
			memoSearchCondition.setPagePerRecord(15);
		}
		
		SearchResult<Memo> memoList = this.memoService.getMemoList(memoSearchCondition);
		List<Memo> listMemo = new ArrayList<Memo>();
		SearchResult<Memo> memoResult = null;
		if(memoList.getEntity() != null){
			for (Memo memoData : memoList.getEntity()) {   
				 memoData.setContents(StringUtil.cutString(StringUtil.extractTextFormHTML(memoData.getContents()), 40, "..."));
				 listMemo.add(memoData);
			}
			memoResult = new SearchResult(listMemo,memoSearchCondition);
		}else{
			memoResult = memoList;
		}
		

		ModelAndView modelAndView = new ModelAndView("support/memo/memoList")

		.addObject("memoList", memoResult)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString)
		.addObject("returnMsg",returnMsg)
		.addObject("searchCondition", memoResult.getSearchCondition());

		return modelAndView;
	}

	
	private User getUser() {
		return (User)getRequestAttribute("ikep.user"); 
	}
	

	@RequestMapping(value = "/memoView.do")
	public ModelAndView getView(@RequestParam(value = "itemId", required = false) String itemId, 
								@RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		Memo memo = this.memoService.get(itemId);

		ModelAndView modelAndView = new ModelAndView()
		.addObject("memo", memo)
		.addObject("searchConditionString", searchConditionString);

		return modelAndView;
	}
	
	@RequestMapping(value = "/memoUpdateView.do")
	public ModelAndView getUpdateView(@RequestParam(value = "itemId", required = false) String itemId, 
								@RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		Memo memo = this.memoService.get(itemId);
		memo.setContents(StringUtil.replace(memo.getContents(),  "<br>", "\n"));
		
		ModelAndView modelAndView = new ModelAndView("support/memo/memoUpdateView")
		.addObject("memo", memo)
		.addObject("searchConditionString", searchConditionString);

		return modelAndView;
	}
	
	@RequestMapping(value = "/inputMemo.do")
	public ModelAndView inputMemo( @RequestParam(value="returnMsg", required=false, defaultValue="NEW") String returnMsg,
										@RequestParam(value="searchConditionString", required=false) String searchConditionString ){
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		

		Memo memo = new Memo();
		ModelAndView mav = new ModelAndView();
		mav.addObject("memo", memo);
		mav.addObject("returnMsg",returnMsg);
		mav.addObject("searchConditionString", searchConditionString);

		return mav;
	}

	@RequestMapping(value = "/multiDeleteMemo")
	public @ResponseBody void multiDelete(@RequestParam("itemId") String[] itemIds) {

		try {
			this.memoService.multiDelete(itemIds);

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}
	

	
	@RequestMapping(value = "/memoSave.do")
	public String memoSave(Model model, @Valid Memo memo, BindingResult result, SessionStatus status, HttpServletRequest req) {
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "memo", result);
			return "forward:/portal/memo/inputMemo.do";
		}
		User regUser = this.getUser();
		String portalId = (String)getRequestAttribute("ikep.portalId");
		
		memo.setPortalId(portalId);
		memo.setRegisterId(regUser.getUserId());
		memo.setRegisterName(regUser.getUserName());
		memo.setItemId(this.idgenService.getNextId());
		memo.setContents(StringUtil.replaceHtmlString(memo.getContents()));
		this.memoService.create(memo);
				
		return "redirect:/support/memo/memoList.do?returnMsg=SAVE";
	}
	
	
	@RequestMapping(value = "/memoModify.do")
	public String memoModify(Model model, @Valid  Memo memo, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "memo", result);
			return "forward:/support/memo/updateMemoView.do";
		}


		memo.setContents(StringUtil.replaceHtmlString(memo.getContents()));
		this.memoService.update(memo);
		
		
		return "redirect:/support/memo/memoList.do?returnMsg=MODIFY";
	}
	
}
