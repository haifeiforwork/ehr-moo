/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.collpack.knowledgehub.constant.KnowledgeMapConstant;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map KnowledgeMapRecommendList controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendListController.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub/personal")
public class KnowledgeMapRecommendListController extends CustomTreeController {

	@Autowired
	private KnowledgeMapRecommendListService knowledgeMapRecommendListService;

	@Autowired
	private TagService tagService;

	/**
	 * [Ajax]
	 * Knowledge Recommend List
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/knowledgeRecommendListViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView knowledgeRecommendListViewAjax(SessionStatus status) {

		try {
			// Session 정보
			// Session 정보
			User user = getSessionUser();
			String portalId = user.getPortalId();
			String userId = user.getUserId();
			
			Tag recommendTag = new Tag();
			recommendTag.setRegisterId(userId);
			recommendTag.setPortalId(portalId);
			recommendTag.setEndRowIndex(KnowledgeMapConstant.MAIN_RECOMMEND_COUNF_OF_PAGE);
			recommendTag.setTagItemType(getUsableModules());
			List<KnowledgeMapList> knowledgeRecommendList = knowledgeMapRecommendListService.listByUserIdPage(recommendTag);

			for (KnowledgeMapList item : knowledgeRecommendList) {
				item.setTagList(tagService.listTagByItemId(item.getItemId(), item.getItemType()));
			}

			// Locale 설정
			if (!isSameLocale()) {
				for (KnowledgeMapList item : knowledgeRecommendList) {
					item.setUserName(item.getUserEnglishName());
					item.setTeamName(item.getTeamEnglishName());
					item.setJobTitleName(item.getJobTitleEnglishName());
				}
			}

			// view 연결
			ModelAndView mav = new ModelAndView("collpack/knowledgehub/personal/knowledgeRecommendList");

			mav.addObject("knowledgeList", knowledgeRecommendList);

			return mav;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("knowledgeRecommendListViewAjax", ex);
    	}
	}

}
