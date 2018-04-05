/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.collpack.qna.service.QnaLinereplyService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaLinereplyController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
@RequestMapping(value = "/collpack/qna")
public class QnaLinereplyController extends QnaBaseController{

	@Autowired
	private QnaLinereplyService qnaLinereplyService;
	
	@Autowired
	private QnaService qnaService;
	
	public interface Create {} // Create group을 선언
	public interface Update {} // Update group을 선언

	/**
	 * 댓글 등록
	 * @param qnaLinereply		댓글 객체
	 * @param result
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String onSubmitReply(@ValidEx QnaLinereply qnaLinereply, BindingResult result, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		if(result.hasErrors()) {
	        throw new IKEP4AjaxValidationException(result, messageSource);
	    }
		
		QnaLinereply parentLinereply = new QnaLinereply();
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			qnaLinereply.setUpdaterId(user.getUserId());
			qnaLinereply.setUpdaterName(user.getUserName());
			
			//수정이면
			if (!StringUtil.isEmpty(qnaLinereply.getLinereplyId())) {
				
				QnaLinereply linereply = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
				//권한체크
				accessCheck(isAdmin, linereply.getRegisterId());
				
				qnaLinereply.setLinereplyId(qnaLinereply.getLinereplyId());
				qnaLinereplyService.update(qnaLinereply);
				
			} else {	//등록이면
				
				//부모 ID가 있으면
				if (!StringUtil.isEmpty(qnaLinereply.getLinereplyParentId())) {
					parentLinereply = qnaLinereplyService.read(qnaLinereply.getLinereplyParentId());
					
					qnaLinereply.setLinereplyGroupId(parentLinereply.getLinereplyGroupId());
					qnaLinereply.setStep(parentLinereply.getStep()+1);
					qnaLinereply.setIndentation(parentLinereply.getIndentation()+1);
					
					if(qnaLinereply.getIndentation() >= QnaConstants.LINEREPLY_INDENTATION_LIMITE){
						throw new IKEP4AjaxException("indentation over", null);
					}
					
				} else {
					qnaLinereply.setStep(0);
					qnaLinereply.setIndentation(0);
				}
				
				qnaLinereply.setRegisterId(user.getUserId());
				qnaLinereply.setRegisterName(user.getUserName());
				
				qnaLinereplyService.create(qnaLinereply);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		
		return "redirect:listReply.do?qnaId="+qnaLinereply.getQnaId();
	}

	/**
	 * 댓글 리스트
	 * @param qnaId			Qna Id 
	 * @param pageIndex		페이지 index
	 * @return
	 */
	@RequestMapping("/listReply.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView list(@RequestParam("qnaId") String qnaId
							,@RequestParam(value = "pageIndex", required = false, defaultValue="1") int pageIndex) {

		ModelAndView mav = new ModelAndView("collpack/qna/linereplyList");
		
		try {
		
			Qna qnaSearch = new Qna();
			
			qnaSearch.setQnaId(qnaId);
			
			//게시물 삭제플래그 제외 카운트
			Qna qna = qnaService.read(qnaId);
			mav.addObject("count", qna.getLinereplyCount());
			
			//게시물 개수
			int count = qnaLinereplyService.getCount(qnaSearch);
			
			//페이징조건
			Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");
			int pagePer = Integer.parseInt(prop.getProperty("qna.linereply.list.pagePer"));		//라인수
			
			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setPageIndex(pageIndex);
			searchCondition.setPagePerRecord(pagePer);
			 
			searchCondition.terminateSearchCondition(count); 
			 
			mav.addObject("searchCondition", searchCondition);
			
			//게시물 리스트 가져오기
			qnaSearch.setEndRowIndex(searchCondition.getEndRowIndex());
			qnaSearch.setStartRowIndex(searchCondition.getStartRowIndex());
			 
			 List<QnaLinereply> list = qnaLinereplyService.list(qnaSearch);
			 mav.addObject("replyList", list);
			 
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return mav;
	}
	
	
	/**
	 * 한줄리플 임시 삭제하기
	 * @param replyNo
	 * @param qnaId
	 * @return
	 */
	@RequestMapping(value = "/deleteItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String removeItem(@RequestParam("replyNo") String replyNo
							, @RequestParam("qnaId") String qnaId
							, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		QnaLinereply  qnaLinereply = qnaLinereplyService.read(replyNo);
		
		//권한체크
		accessCheck(isAdmin, qnaLinereply.getRegisterId());
		
		try {
			
			if(isAdmin){	//관리자면 영구 삭제
				qnaLinereplyService.delete(replyNo);
			} else {
				qnaLinereplyService.deleteLinereplyFlag(replyNo);
			}

		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		
		return "redirect:listReply.do?qnaId="+qnaLinereply.getQnaId();
	}
	
	/**
	 * 한줄리플 되살리기
	 * @param replyNo
	 * @param qnaId
	 * @return
	 */
	@RequestMapping(value = "/aliveItemReply.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String aliveItem(@RequestParam("replyNo") String replyNo
						, @RequestParam("qnaId") String qnaId
						, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		QnaLinereply  qnaLinereply = qnaLinereplyService.read(replyNo);
		
		//권한체크
		accessCheck(isAdmin, qnaLinereply.getRegisterId());

		try {
			qnaLinereplyService.aliveLinereplyDeleteFlay(replyNo);
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "redirect:listReply.do?qnaId="+qnaLinereply.getQnaId();
	}
	

}
