package com.lgcns.ikep4.wfapproval.work.web;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 결제 카운트 Controller
 * 
 * @author 이재경
 * @version $Id: ApCountController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/wfapproval")
public class ApCountController extends BaseController {
	
	@Autowired
	private WorkplaceListService workplaceListService;
	
	// userId를 parameter로 받아 필요한 개수를 얻어 json 타입으로 return.
	// 메인화면의 퀵메뉴의 미결함 수를 가져오는 메써드
	@RequestMapping(value="/getApprovalCount.do", method = RequestMethod.POST)
	public @ResponseBody String getApprovalCount() {
		
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition(); 
		workplaceSearchCondition.setQueryId("todoList");
		
		User user = (User)this.getRequestAttribute("ikep.user");
		workplaceSearchCondition.setUserId(user.getUserId());
		
		int count = workplaceListService.listCount(workplaceSearchCondition);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);
		
		return jsonObj.toString();	// {"count":개수} 형태의 json 타입으로 return 함.
	}
}