/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

import java.util.HashMap;
import java.util.List;
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

import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.collpack.qna.service.QnaRecommendService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaRecommendController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
@RequestMapping(value = "/collpack/qna")
//@SessionAttributes("qnaRecommend")
public class QnaRecommendController extends BaseController {

	@Autowired
	private QnaRecommendService qnaRecommendService;

	/**
	 * 추천 등록
	 * @param qnaId QNA ID
	 * @return
	 */
	@RequestMapping(value = "/createRecommend.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, String> onSubmitRecommend(@RequestParam("qnaId") String qnaId) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
		
			User user = (User) getRequestAttribute("ikep.user");
			
			QnaRecommend qnaRecommend = new QnaRecommend();
			
			qnaRecommend.setQnaId(qnaId);
			qnaRecommend.setRegisterId(user.getUserId());
			
			QnaRecommend testRecommend = qnaRecommendService.read(qnaId, user.getUserId());
			
			if(testRecommend == null){
				qnaRecommendService.create(qnaRecommend);
				resultMap.put("stat", "success");
				
			} else {
				resultMap.put("stat", "error");
				resultMap.put("message", "exist");
			}
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return resultMap;
		
	}

	/**
	 * 추천 리스트
	 * @param qnaId
	 * @return
	 */
	@RequestMapping("/listRecommend.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView list(@RequestParam("qnaId") String qnaId) {
		
		ModelAndView mav = new ModelAndView("collpack/qna/recommendList");
		try {
		
			QnaRecommend qnaRecommend = new QnaRecommend();
			qnaRecommend.setQnaId(qnaId);
			
			List<QnaRecommend> list = qnaRecommendService.list(qnaId);
			
			mav.addObject("recommendList", list);
			mav.addObject("size", list.size());
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		return mav;
	}
	
	/**
	 * 추천 삭제
	 * @param qnaId
	 * @return
	 */
	@RequestMapping(value = "/deleteRecommend.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, String> remove(@RequestParam("qnaId") String qnaId) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			qnaRecommendService.delete(qnaId, user.getUserId());
			
			resultMap.put("stat", "success");
			
		} catch(Exception ex) {
			resultMap.put("stat", "error");
			resultMap.put("message", "exception");
			throw new IKEP4AjaxException("", ex);
		} 
		return resultMap;
	}
	

}
