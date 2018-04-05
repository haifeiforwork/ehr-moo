package com.lgcns.ikep4.portal.admin.pw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.poll.model.BoardCode;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;



@Controller
@RequestMapping(value = "/portal/admin/pw")
@SessionAttributes("nation")
public class PwController extends BaseController {

	@Autowired
    UserService userService;
	
	@Autowired
    UserDao userDao;
	
	 @RequestMapping( value = "/menuView.do" )
	    public ModelAndView menuView() {
	    	
	    	try {
				
	    		User user = (User) getRequestAttribute("ikep.user");
	    		
	    		
	    		ModelAndView modelAndView = new ModelAndView("portal/admin/pw/menuView");
	    		return modelAndView;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;

	    }
	
	@RequestMapping(value = "/pwUpdateList")
	public ModelAndView pwUpdateList(UserSearchCondition searchCondition) {

		
		ModelAndView model = new ModelAndView();
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		List<String> workPlaceList = userService.getWorkPlaceNameList();
		
		List<User> teamList = null;
		
		if(searchCondition.getWorkPlaceName() == null ){
			searchCondition.setWorkPlaceName("본사");
		}
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = userDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}
		
		SearchResult<User> searchResult = userService.selectUserPwUpdateList(searchCondition);
		
		model.addObject("userList", searchResult);
		model.addObject("searchCondition", searchCondition);
		model.addObject("searchResult", searchResult);
		model.addObject("announceCode", new BoardCode());
		model.addObject("workPlaceList", workPlaceList);
		model.addObject("teamList", teamList);
		return model;
	}
	
}
