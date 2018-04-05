package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.servicepack.survey.model.Code;
import com.lgcns.ikep4.servicepack.usagetracker.util.UsageTrackerConstance;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 사용량 통계 기본 base controller
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: BaseUsageTrackerController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@RequestMapping(value = "/servicepack/usagetrakcer")
public class BaseUsageTrackerController extends BaseController {

	@Autowired
	ACLService aclService;

	/**
	 * 어드민 유저 인지
	 * 
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin() {

		User user = (User) getRequestAttribute("ikep.user");

		String userId = user.getUserId();
		boolean isSystemAdmin = aclService.isSystemAdmin(UsageTrackerConstance.ITEM_TYPE_CODE, userId);

		return isSystemAdmin;
	}

	/**
	 * 페이지
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ModelAttribute("codeList")
	public List<Code<Integer>> getCodeList() {
		return Arrays.asList(new Code<Integer>(PageCons.PAGE_PER_ROW_10, "10"), new Code<Integer>(
				PageCons.PAGE_PER_ROW_15, "15"), new Code<Integer>(PageCons.PAGE_PER_ROW_20, "20"), new Code<Integer>(
				PageCons.PAGE_PER_ROW_30, "30"), new Code<Integer>(PageCons.PAGE_PER_ROW_40, "40"), new Code<Integer>(
				PageCons.PAGE_PER_ROW_50, "50"));
	}
}
