/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.collpack.qna.service.QnaPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaPolicyController.java 16295 2011-08-19 07:49:49Z giljae $$
 */
@Controller
//@SessionAttributes("qnaPolicy")
public class QnaPolicyController extends QnaBaseController {

	@Autowired
	private QnaPolicyService qnaPolicyService;
	
	/**
	 * 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/formPolicy.do", method = RequestMethod.GET)
	public String getForm(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<QnaPolicy> list = qnaPolicyService.listPolicy(portal.getPortalId());
		
		model.addAttribute("listPolicy", list);
		
		return "collpack/qna/policyForm";
	}
	

	/**
	 * 정책 등록
	 * @param qnaHitWeight				조회 가중치
	 * @param qnaLinereplyWeight		댓글 가중치
	 * @param qnaAnswerWeight			답변 가중치
	 * @param qnaFavoriteWeight			즐겨찾기 가중치
	 * @param replyHitWeight			답변 조회수 가중치
	 * @param replyLinereplyWeight		답변 댓글 가중치	
	 * @param replyRecommendWeight		답변 추천 가중치
	 * @param replyAnswerAdoptWeight	답변 채택 가중치
	 * @param qnaPolicyId				정책 ID
	 * @param replyPolicyId				답변 정책 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createPolicy.do", method = RequestMethod.POST)
	public String onSubmit(@RequestParam("qnaHitWeight") 		int qnaHitWeight
						, @RequestParam("qnaLinereplyWeight") 	int qnaLinereplyWeight
						, @RequestParam("qnaAnswerWeight") 		int qnaAnswerWeight
						, @RequestParam("qnaFavoriteWeight") 	int qnaFavoriteWeight
						, @RequestParam("qnaAnswerSumWeight") 	int qnaAnswerSumWeight
						, @RequestParam("bestQnaBasisPoint") 	int bestQnaBasisPoint
						, @RequestParam("replyLinereplyWeight") int replyLinereplyWeight
						, @RequestParam("replyRecommendWeight") int replyRecommendWeight
						, @RequestParam("replyAnswerAdoptWeight") int replyAnswerAdoptWeight
						, @RequestParam("bestAnswerBasisPoint") int bestAnswerBasisPoint
						, @RequestParam(value = "qnaPolicyId", required = false) String qnaPolicyId
						, @RequestParam(value = "replyPolicyId", required = false) String replyPolicyId
						, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		//질문 정책
		QnaPolicy qnaPolicy = new QnaPolicy();
		qnaPolicy.setQnaType(QnaConstants.POLICY_QNA_TYPE);
		qnaPolicy.setPolicyId(qnaPolicyId);
		qnaPolicy.setHitWeight(qnaHitWeight);
		qnaPolicy.setLinereplyWeight(qnaLinereplyWeight);
		qnaPolicy.setAnswerWeight(qnaAnswerWeight);
		qnaPolicy.setFavoriteWeight(qnaFavoriteWeight);
		qnaPolicy.setAnswerSumWeight(qnaAnswerSumWeight);
		qnaPolicy.setbestQnaBasisPoint(bestQnaBasisPoint);
		qnaPolicy.setRegisterId(user.getUserId());
		qnaPolicy.setRegisterName(user.getUserName());
		qnaPolicy.setPortalId(portal.getPortalId());
		
		//답변 정책
		QnaPolicy replyPolicy = new QnaPolicy();
		replyPolicy.setQnaType(QnaConstants.POLICY_REPLY_TYPE);
		replyPolicy.setPolicyId(replyPolicyId);
		replyPolicy.setAnswerLinereplyWeight(replyLinereplyWeight);
		replyPolicy.setAnswerRecommendWeight(replyRecommendWeight);
		replyPolicy.setAnswerAdoptWeight(replyAnswerAdoptWeight);
		replyPolicy.setbestAnswerBasisPoint(bestAnswerBasisPoint);
		replyPolicy.setRegisterId(user.getUserId());
		replyPolicy.setRegisterName(user.getUserName());
		replyPolicy.setPortalId(portal.getPortalId());
		
		if(StringUtil.isEmpty(qnaPolicyId)){
			qnaPolicyService.create(qnaPolicy);
		} else {
			qnaPolicyService.update(qnaPolicy);
		}
		
		if(StringUtil.isEmpty(replyPolicyId)){
			qnaPolicyService.create(replyPolicy);
		} else {
			qnaPolicyService.update(replyPolicy);
		}
		
		return "redirect:formPolicy.do";
	}
	
	

}
