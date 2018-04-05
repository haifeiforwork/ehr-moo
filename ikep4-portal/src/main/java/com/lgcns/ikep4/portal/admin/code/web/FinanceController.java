package com.lgcns.ikep4.portal.admin.code.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.customer.model.Finance;
import com.lgcns.ikep4.support.customer.service.CustomerFinanceService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 국가 코드 관리 컨트롤러
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: NationController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/finance")
@SessionAttributes("nation")
public class FinanceController extends BaseController {

	@Autowired
    CustomerFinanceService customerFinanceService;

	
	@RequestMapping(value = "/editFinance")
	public ModelAndView editCategory() {

		String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("startDt", startDt);
		model.addObject("endDt", endDt);
		return model;
	}
	
	@RequestMapping(value = "/createFinance")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createFinance(Finance finance) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		finance.setUpdaterId(user.getUserId());
		finance.setUpdaterName(user.getUserName());
		
		this.customerFinanceService.saveFinanceYearInfo(finance);
		
		return "success";
	}

}
