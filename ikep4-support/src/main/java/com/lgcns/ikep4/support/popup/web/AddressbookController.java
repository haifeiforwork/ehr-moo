package com.lgcns.ikep4.support.popup.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.base.Constant;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.addressbook.service.PersonService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.support.recent.service.RecentService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

import com.lgcns.ikep4.support.user.code.service.JobTitleService;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;

/**
 * 조직도 팝업을 위한 Controller
 * 
 * @author 이형운
 * @version $Id: AddressbookController.java 18372 2012-04-27 01:24:37Z yu_hs $
 */
@Controller
@RequestMapping(value = "/support/popup")
@SessionAttributes("addressbook")
public class AddressbookController extends BaseController {

	/**
	 * 사용자 User 정보 컨트롤용 Service
	 */
	@Autowired
	private UserService userService;

	/**
	 * 사용자 Group 정보 컨트롤용 Service
	 */
	@Autowired
	private GroupService groupService;

	/**
	 * 사용자 Group 정보 컨트롤용 Service
	 */
	@Autowired
	private JobTitleService jobTitleService;

	/**
	 * 사용자 Group 정보 컨트롤용 Service
	 */
	@Autowired
	private JobDutyService jobDutyService;
	
	/**
	 * 주소록 그룹 컨트롤용 Service
	 */
	@Autowired
	private AddrgroupService addrgroupService;

	/**
	 * 주소록 Person 컨드롤용 Service
	 */
	@Autowired
	private PersonService personService;

	/**
	 * 포탈 Favorite 컨드롤용 Service
	 */
	@Autowired
	private PortalFavoriteService portalFavoriteService;

	/**
	 * 포탈 Recent 컨트롤용 Service
	 */
	@Autowired
	private RecentService recentService;
	
	/**
	 * 주소록 팝업을 호출 하는 메서드
	 * 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @param controlTabType : PUB:PRI:COL:SNS 순 0(선택안함) 1(선택함)으로 표기 <br/>
	 *            예) PUB COL 선택시 ) 1:0:1:0
	 * @param controlType : ORG : 조직도, PUB:공용주소록 PRI: 개인 주소록 COL: Collaboration,
	 *            SNS : SNS Follow
	 * @param selectMaxCnt 최대 선택 가능수
	 * @param searchSubFlag 하위 부서 포함 선택 유무
	 * @return 조직도 조회용 팝업페이지 View
	 */
	@RequestMapping("/addresbookPopup.do")
	public ModelAndView addresbookPopup(
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType,
			@RequestParam(value = "controlTabType", required = true, defaultValue = "0:0:0:0:1:1") String controlTabType,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectMaxCnt", required = true, defaultValue = "500") String selectMaxCnt,
			@RequestParam(value = "searchSubFlag", required = true, defaultValue = "false") String searchSubFlag) {
		ModelAndView mav = new ModelAndView("/support/popup/addresscontrolpopup");

		// 첫 팝업 호출시 ORG 타입으로 호출
		List<String> controlTabTypeList = StringUtil.getTokens(controlTabType, ":");
		
		if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT) || CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_BASIC)) {
			controlTabTypeList.set(2, "0");	// collaboration disable
			controlTabTypeList.set(3, "0");	// sns disable
		}

		int i = 0;
		if (!(selectType.equals("GROUP"))) {
			for (String tabType : controlTabTypeList) {

				if (i == Constant.ADDRE_PUB_TAB_INDEX) {
					mav.addObject("dispPubTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_PRI_TAB_INDEX) {
					mav.addObject("dispPriTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_COL_TAB_INDEX) {
					mav.addObject("dispColTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_SNS_TAB_INDEX) {
					mav.addObject("dispSnsTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_ORG_TAB_INDEX) {
					mav.addObject("dispOrgTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_SCH_TAB_INDEX) {
					mav.addObject("dispSchTabFlag", dispTabFlag(tabType));
				}
				i++;
			}
		} else {
			mav.addObject("dispPubTabFlag", "false");
			mav.addObject("dispPriTabFlag", "false");
			mav.addObject("dispColTabFlag", "false");
			mav.addObject("dispSnsTabFlag", "false");
		}

		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("searchSubFlag", searchSubFlag);

		return mav;
	}
	
	@RequestMapping("/addresbookReaderPopup.do")
	public ModelAndView addresbookReaderPopup(
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType,
			@RequestParam(value = "controlTabType", required = true, defaultValue = "0:0:0:0:1:1") String controlTabType,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectMaxCnt", required = true, defaultValue = "500") String selectMaxCnt,
			@RequestParam(value = "searchSubFlag", required = true, defaultValue = "false") String searchSubFlag) {
		ModelAndView mav = new ModelAndView("/support/popup/addresscontrolReaderpopup");

		// 첫 팝업 호출시 ORG 타입으로 호출
		List<String> controlTabTypeList = StringUtil.getTokens(controlTabType, ":");
		
		if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT) || CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_BASIC)) {
			controlTabTypeList.set(2, "0");	// collaboration disable
			controlTabTypeList.set(3, "0");	// sns disable
		}

		int i = 0;
		if (!(selectType.equals("GROUP"))) {
			for (String tabType : controlTabTypeList) {

				if (i == Constant.ADDRE_PUB_TAB_INDEX) {
					mav.addObject("dispPubTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_PRI_TAB_INDEX) {
					mav.addObject("dispPriTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_COL_TAB_INDEX) {
					mav.addObject("dispColTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_SNS_TAB_INDEX) {
					mav.addObject("dispSnsTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_ORG_TAB_INDEX) {
					mav.addObject("dispOrgTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_SCH_TAB_INDEX) {
					mav.addObject("dispSchTabFlag", dispTabFlag(tabType));
				}
				i++;
			}
		} else {
			mav.addObject("dispPubTabFlag", "false");
			mav.addObject("dispPriTabFlag", "false");
			mav.addObject("dispColTabFlag", "false");
			mav.addObject("dispSnsTabFlag", "false");
		}

		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("searchSubFlag", searchSubFlag);

		return mav;
	}
	
	@RequestMapping("/addresbookSurveyPopup.do")
	public ModelAndView addresbookSurveyPopup(
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType,
			@RequestParam(value = "controlTabType", required = true, defaultValue = "1:1:0:0:1:1") String controlTabType,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectMaxCnt", required = true, defaultValue = "500") String selectMaxCnt,
			@RequestParam(value = "searchSubFlag", required = true, defaultValue = "false") String searchSubFlag) {
		ModelAndView mav = new ModelAndView("/support/popup/addresscontrolpopup");

		// 첫 팝업 호출시 ORG 타입으로 호출
		List<String> controlTabTypeList = StringUtil.getTokens(controlTabType, ":");
		
		if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_LIGHT) || CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_BASIC)) {
			controlTabTypeList.set(2, "0");	// collaboration disable
			controlTabTypeList.set(3, "0");	// sns disable
		}

		int i = 0;
		if (!(selectType.equals("GROUP"))) {
			for (String tabType : controlTabTypeList) {

				if (i == Constant.ADDRE_PUB_TAB_INDEX) {
					mav.addObject("dispPubTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_PRI_TAB_INDEX) {
					mav.addObject("dispPriTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_COL_TAB_INDEX) {
					mav.addObject("dispColTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_SNS_TAB_INDEX) {
					mav.addObject("dispSnsTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_ORG_TAB_INDEX) {
					mav.addObject("dispOrgTabFlag", dispTabFlag(tabType));
				}else if (i == Constant.ADDRE_SCH_TAB_INDEX) {
					mav.addObject("dispSchTabFlag", dispTabFlag(tabType));
				}
				i++;
			}
		} else {
			mav.addObject("dispPubTabFlag", "true");
			mav.addObject("dispPriTabFlag", "false");
			mav.addObject("dispColTabFlag", "false");
			mav.addObject("dispSnsTabFlag", "false");
		}

		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("searchSubFlag", searchSubFlag);

		return mav;
	}

	@RequestMapping("/addresbookMailPopup.do")
	public ModelAndView addresbookMailPopup(
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType,
			@RequestParam(value = "controlTabType", required = true, defaultValue = "0:0:0:0") String controlTabType,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectMaxCnt", required = true, defaultValue = "500") String selectMaxCnt,
			@RequestParam(value = "searchSubFlag", required = true, defaultValue = "false") String searchSubFlag) {
		ModelAndView mav = new ModelAndView("/support/popup/addresscontrolmailpopup");

		// 첫 팝업 호출시 ORG 타입으로 호출
		List<String> controlTabTypeList = StringUtil.getTokens(controlTabType, ":");

		int i = 0;
		if (!(selectType.equals("GROUP"))) {
			for (String tabType : controlTabTypeList) {

				if (i == Constant.ADDRE_PUB_TAB_INDEX) {
					mav.addObject("dispPubTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_PRI_TAB_INDEX) {
					mav.addObject("dispPriTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_COL_TAB_INDEX) {
					mav.addObject("dispColTabFlag", dispTabFlag(tabType));
				} else if (i == Constant.ADDRE_SNS_TAB_INDEX) {
					mav.addObject("dispSnsTabFlag", dispTabFlag(tabType));
				}
				i++;
			}
		} else {
			mav.addObject("dispPubTabFlag", "false");
			mav.addObject("dispPriTabFlag", "false");
			mav.addObject("dispColTabFlag", "false");
			mav.addObject("dispSnsTabFlag", "false");
		}
		
		Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
		
		String strMailServer = prop.getProperty("ikep4.support.mail.externalserver");

		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("searchSubFlag", searchSubFlag);
		mav.addObject("mailserver", strMailServer);

		return mav;
	}
	/**
	 * 조직도 팝업에서 Tab 을 표시 할건지 유무를 반환 한다.
	 * 
	 * @param tabType 조직도 탭의 Display Index 값
	 * @return 디스플레이 유무
	 */
	public String dispTabFlag(String tabType) {
		String returnFlag = "true";
		if (tabType.equals(Constant.ADDRE_TAB_DISP_FLAG)) {
			returnFlag = "true";
		} else {
			returnFlag = "false";
		}
		return returnFlag;
	}

	/**
	 * 조직도의 그룹을 조회 하는 메서드 <br/>
	 * 기준 GroupId를 기준으로 하위의 조직도를 데이타를 조회
	 * 
	 * @param groupId 조회할 그룹 ID
	 * @param userId 조회할 사용자 ID
	 * @param controlType 조회할 조직도 탭 타입
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	@RequestMapping("/requestGroupChildren.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestGroupChildren(@RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "controlType", required = true, defaultValue = "ORG") String controlType,
			@RequestParam(value = "selectType", required = true, defaultValue = "GROUP") String selectType) {

		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			if (controlType.equals("ORG")) {
				list = getOrgGroupAndUser(groupId, userId, selectType);
			} else if (controlType.equals("ORG22")) {
				list =  getOrgGroupAndUser2(groupId, userId, selectType);
			}  else if (controlType.equals("PUB")) {
				//list = getPublicGroupAndUser(selectType);
				list = getPublicGroupAndUser(selectType,groupId);
			} else if (controlType.equals("PRI")) {
				list = getPrivateGroupAndUser(selectType);
			} else if (controlType.equals("COL")) {
				list = getCollaborationGroupAndUser(selectType);
			} else if (controlType.equals("SNS")) {
				list = getSnsGroupAndUser(selectType);
			} else if (controlType.equals("JBT")) {
				list = getJobTitleGroupAndUser(groupId, userId);
			} else if (controlType.equals("JBD")) {
				list = getJobDutyGroupAndUser(groupId, userId);
			}

			item.put("items", list);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;
	}

	/**
	 * 검색 을 통해 조직도와 사용자를 조회 하는 메서드
	 * 
	 * @param keyword 조직도 조회 검색어
	 * @param selectType 조직도 검색 타입 (이름/부서)
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	@RequestMapping("/requestSearchGroup.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestSearchGroup(@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "selectType", required = true) String selectType) {

		// searchType
		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			list = searchGroupAndUser(keyword, selectType);
			item.put("items", list);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;

	}
	
	/**
	 * 개인 주소록 Person 정보 입력 및 수정 FORM 등록 페이지를 반환 한다.
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @param personId 주소록 개인 ID
	 * @return Person 정보 입력 및 수정 FORM View
	 */
	@RequestMapping(value = "/addPersonPopup.do")	
	public ModelAndView addPersonToAddressbook(@RequestParam(value = "userAddr", required = false) String userAddr, 
			@RequestParam(value = "userName", required = false) String userName) {

		ModelAndView mav = new ModelAndView("/support/popup/addressbookpersonformpopup");

		User user = (User) getRequestAttribute("ikep.user");		
		
		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		// 그룹 정보 조회
		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		//추가를 위해 검색하는 주소록은 개인 주소록으로 한정한다
		addrgroupSearch.setGroupType("P");

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		
		mav.addObject("addrgroupList", addrgroupList);
		
		//현재 도메인을 가져온다.
		
		Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");		
		
		String strDomain = prop.getProperty("ikep4.support.mail.domain");
		
				
		// 사용자 정보 조회
		Person personInfo = new Person();	
		
		//저장할 이메일 주소를 파싱하여 처리한다.
		if(userAddr.indexOf("@") > 0)
		{
			String[] userMailAddrInfo = userAddr.split("@");
			//내부직원 여부 확인
			if(strDomain.equals(userMailAddrInfo[1]))
			{
				Person tmpPerson = new Person();				
				tmpPerson.setPersonId(userMailAddrInfo[0]);	
				personInfo = personService.read(tmpPerson);
				//만일 입력하려는 사용자 정보가 주소록에 없는 경우에는
				if(personInfo == null)
				{
					
					personInfo = new Person();
					
					//사용자를  검색해 정보를 가져온다.
					UserSearchCondition searchCondition = new UserSearchCondition();
					searchCondition.setSearchColumn("id");
					searchCondition.setSearchWord(userMailAddrInfo[0]);				
					
					SearchResult<User> userList = userService.list(searchCondition);
					//보낸 주소는 무림 메일이지만 사용자 가 검색되지 않는 경우를 대비
					if(!userList.isEmptyRecord())
					{
						User tmpUser = userList.getEntity().get(0);
						personInfo.setPersonName(tmpUser.getUserName());
						personInfo.setCompanyName(tmpUser.getCompanyCodeName());					
						personInfo.setTeamName(tmpUser.getTeamName());
						personInfo.setJobRankName(tmpUser.getJobTitleName());
						personInfo.setMailAddress(tmpUser.getMail());	
						personInfo.setOfficePhoneno(tmpUser.getOfficePhoneNo());
						personInfo.setOfficePhoneno(tmpUser.getMobile());
						personInfo.setRegisterId(user.getUserId());
					}
					else
					{
						personInfo.setMailAddress(userAddr); //주석제거 2012.11.14 by tseliot 김철민과장님 요청
						personInfo.setRegisterId(user.getUserId());
					}
				}
				personInfo.setUserType("I");
			}
			else
			{
				PersonSearchCondition personSearch = new PersonSearchCondition();				
				personSearch.setMailAddress(userAddr);
				personSearch.setRegisterId(user.getUserId());								
				personInfo = personService.selectPersonByMail(personSearch);
				if(personInfo == null)
				{
					personInfo = new Person();
					personInfo.setMailAddress(userAddr);
					personInfo.setRegisterId(user.getUserId());	
				}
				personInfo.setUserType("O");
				
			}		
		}
		personInfo.setPersonName(StringUtil.nvl(userName, personInfo.getPersonName()));
		mav.addObject("person", personInfo);

		return mav;
	}


	/**
	 * 조직도와 사용자 조회를 하기 위한 메서드
	 * 
	 * @param groupId 조직도에서 조회하고자 하는 그룹 ID
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");

				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}
				
				map.put("name", user.getUserName() + " " + strJobTitle);
				map.put("userName", user.getUserName());
				map.put("jobTitleName", strJobTitle);
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * 공용 그룹의 조직도표를 조회 하는 메서드
	 * 
	 * @param groupId 조회하고자 하는 그룹 ID
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getPublicGroupAndUser(String selectType, String groupId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(sessionuser.getUserId());
		addrgroupSearch.setPortalId(sessionuser.getPortalId());
		addrgroupSearch.setGroupType("O");
		addrgroupSearch.setAddrgroupId(groupId);

		SearchResult<Addrgroup> searchResult = addrgroupService.listPubCount(addrgroupSearch);
		
		//공용 그룹은 그룹만을 가져오고 그룹 하위의 사용자는 가져오지 못하도록 수정 

		// 공용그룹
		if (searchResult.getEntity() != null) {
			List<Addrgroup> addrgroupList = searchResult.getEntity();
			for (Addrgroup addrgroup : addrgroupList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "common");
				map.put("id", addrgroup.getGroupType());
				map.put("parent", "pub");
				if(groupId != ""){
					map.put("name", addrgroup.getAddrgroupName());
					map.put("code", addrgroup.getAddrgroupId());
					map.put("hasChild", "0");
				}else{
					map.put("name", addrgroup.getCategoryName());
					map.put("code", addrgroup.getCategoryId());
					map.put("hasChild", addrgroup.getAddrgroupUserCnt());
				}
				//공용그룹의 하위 클릭을 방지하기 위해 숫자를 0으로 고정
				//map.put("hasChild", addrgroup.getAddrgroupUserCnt());
				list.add(map);
			}
		}
		//그룹 하위의 사용자를 가져오지 못하도록 주석처리
		// 사용자 추가
		/*if (!(selectType.equals("GROUP"))) {

			PersonSearchCondition personSearch = new PersonSearchCondition();
			personSearch.setGroupType("O");
			personSearch.setRegisterId(sessionuser.getUserId());
			personSearch.setFieldName("jobTitleName");
			personSearch.setUserLocaleCode(sessionuser.getLocaleCode());

			SearchResult<Person> searchResultPerson = personService.listAllWithoutCount(personSearch);

			if (searchResultPerson.getEntity() != null) {

				List<Person> personList = searchResultPerson.getEntity();*/

		        //공용 그룹의 하위의 사용자를 가져오기 위해 주석처리를 해제해도 미분류 부분의 주석처리는 남겨두어야 한다
				// 미분류그룹
				/*
				 * int cnt = 0; for (Person person : personList) {
				 * if(StringUtil.isEmpty(person.getAddrgroupId())) cnt++; }
				 * Map<String, Object> mainaddrgroup = new HashMap<String,
				 * Object>(); mainaddrgroup.put("type", "group");
				 * mainaddrgroup.put( "name", messageSource.getMessage(
				 * "ui.support.addressbook.addrgroup.nogroup", null, new
				 * Locale(sessionuser.getLocaleCode())));
				 * mainaddrgroup.put("code", "99999999999999999999");
				 * mainaddrgroup.put("groupTypeId", "O");
				 * mainaddrgroup.put("parent", "pub");
				 * mainaddrgroup.put("hasChild", cnt); list.add(mainaddrgroup);
				 */

				// 사용자 추가
				/*for (Person person : personList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "user");
					if (sessionuser.getLocaleCode().equals("ko")) {
						map.put("userName", person.getPersonName());
						map.put("name", person.getPersonName() + " " + person.getJobRankName());
						map.put("jobTitleName", person.getJobRankName());
						map.put("teamName", person.getTeamName());
					} else {
						map.put("userName", person.getPersonEnglishName());
						map.put("name", person.getPersonEnglishName() + " " + person.getJobRankEnglishName());
						map.put("jobTitleName", person.getJobRankEnglishName());
						map.put("teamName", person.getTeamEnglishName());
					}
					map.put("code", "");
					if (StringUtil.isEmpty(person.getAddrgroupId())) {
						map.put("parent", "99999999999999999999");
					} else {
						map.put("parent", person.getAddrgroupId());
					}
					map.put("id", person.getPersonId());
					map.put("empNo", "");
					map.put("email", person.getMailAddress());
					map.put("mobile", person.getMobilePhoneno());

					list.add(map);
				}
			}
		}*/

		return list;
	}

	/**
	 * 개인 그룹의 조직도표를 조회 하는 메서드
	 * 
	 * @param groupId 조직도에서 조회 하고자 하는 그룹 ID
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getPrivateGroupAndUser(String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(sessionuser.getUserId());
		addrgroupSearch.setPortalId(sessionuser.getPortalId());
		addrgroupSearch.setGroupType("P");

		SearchResult<Addrgroup> searchResult = addrgroupService.listWithoutCount(addrgroupSearch);

		// 그룹
		if (searchResult.getEntity() != null) {
			List<Addrgroup> addrgroupList = searchResult.getEntity();
			for (Addrgroup addrgroup : addrgroupList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "private");
				map.put("name", addrgroup.getAddrgroupName());
				map.put("code", addrgroup.getAddrgroupId());
				map.put("groupTypeId", addrgroup.getGroupType());
				map.put("parent", "pri");
				map.put("hasChild", addrgroup.getAddrgroupUserCnt());
				list.add(map);
			}
		}
		addrgroupSearch.setGroupType("G");
		SearchResult<Addrgroup> searchResult1 = addrgroupService.listWithoutCount(addrgroupSearch);
		
		if (searchResult1.getEntity() != null) {
			List<Addrgroup> addrgroupList1 = searchResult1.getEntity();
			for (Addrgroup addrgroup1 : addrgroupList1) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "private");
				map.put("name", addrgroup1.getAddrgroupName());
				map.put("code", addrgroup1.getAddrgroupId());
				map.put("groupTypeId", addrgroup1.getGroupType());
				map.put("parent", "pri");
				map.put("hasChild", addrgroup1.getAddrgroupUserCnt());
				list.add(map);
			}
		}
		
		addrgroupSearch.setGroupType("P");

		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			PersonSearchCondition personSearch = new PersonSearchCondition();
			personSearch.setGroupType("P");
			personSearch.setRegisterId(sessionuser.getUserId());
			personSearch.setFieldName("jobTitleName");
			personSearch.setUserLocaleCode(sessionuser.getLocaleCode());
			//personSearch.setUserTypeIn("Y");

			SearchResult<Person> searchResultPerson = personService.listAllWithoutCount(personSearch);

			if (searchResultPerson.getEntity() != null) {

				List<Person> personList = searchResultPerson.getEntity();

				// 미분류그룹
				
				int cnt = 0;
				for (Person person : personList) {
					if (StringUtil.isEmpty(person.getAddrgroupId())) {
						cnt++;
					}
				}
				Map<String, Object> mainaddrgroup = new HashMap<String, Object>();
				mainaddrgroup.put("type", "private");
				mainaddrgroup.put("name", messageSource.getMessage("ui.support.addressbook.addrgroup.nogroup", null,
						new Locale(sessionuser.getLocaleCode())));
				mainaddrgroup.put("code", "99999999999999999999");
				mainaddrgroup.put("groupTypeId", "P");
				mainaddrgroup.put("parent", "pri");
				mainaddrgroup.put("hasChild", cnt);
				list.add(mainaddrgroup);
				

				// 사용자추가
				for (Person person : personList) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (!(StringUtil.isEmpty(person.getUserType())) && person.getUserType().equals("I")) {
						map.put("type", "user");
						if (sessionuser.getLocaleCode().equals("ko")) {
							map.put("userName", person.getPersonName());
							map.put("name", person.getPersonName() + " " + person.getJobRankName());
							map.put("jobTitleName", person.getJobRankName());
							map.put("teamName", person.getTeamName());
						} else {
							map.put("userName", person.getPersonEnglishName());
							map.put("name", person.getPersonEnglishName() + " " + person.getJobRankEnglishName());
							map.put("jobTitleName", person.getJobRankEnglishName());
							map.put("teamName", person.getTeamEnglishName());
						}
					} else {
						map.put("type", "addruser");
						map.put("userName", person.getPersonName());
						map.put("name", person.getPersonName() + " " + person.getJobRankName() + " " + person.getTeamName() + " " + person.getCompanyName());
						map.put("jobTitleName", person.getJobRankName());
						map.put("teamName", person.getTeamName() + " " + person.getCompanyName());
					}
					
					map.put("code", "");
					if (StringUtil.isEmpty(person.getAddrgroupId())) {
						map.put("parent", "99999999999999999999");
					} else {
						map.put("parent", person.getAddrgroupId());
					}
					map.put("id", person.getPersonId());
					map.put("empNo", "");
					map.put("email", person.getMailAddress());
					map.put("mobile", person.getMobilePhoneno());
					list.add(map);
				}
			}
			personSearch.setGroupType("G");
			SearchResult<Person> searchResultPerson1 = personService.listAllWithoutCount(personSearch);

			if (searchResultPerson1.getEntity() != null) {

				List<Person> personList1 = searchResultPerson1.getEntity();

				

				// 사용자추가
				for (Person person : personList1) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (!(StringUtil.isEmpty(person.getUserType())) && person.getUserType().equals("I")) {
						map.put("type", "user");
						if (sessionuser.getLocaleCode().equals("ko")) {
							map.put("userName", person.getPersonName());
							map.put("name", person.getPersonName() + " " + person.getJobRankName());
							map.put("jobTitleName", person.getJobRankName());
							map.put("teamName", person.getTeamName());
						} else {
							map.put("userName", person.getPersonEnglishName());
							map.put("name", person.getPersonEnglishName() + " " + person.getJobRankEnglishName());
							map.put("jobTitleName", person.getJobRankEnglishName());
							map.put("teamName", person.getTeamEnglishName());
						}
					} else {
						map.put("type", "addruser");
						map.put("userName", person.getPersonName());
						map.put("name", person.getPersonName() + " " + person.getJobRankName() + " " + person.getTeamName() + " " + person.getCompanyName());
						map.put("jobTitleName", person.getJobRankName());
						map.put("teamName", person.getTeamName() + " " + person.getCompanyName());
					}
					
					map.put("code", "");
					if (StringUtil.isEmpty(person.getAddrgroupId())) {
						map.put("parent", "99999999999999999999");
					} else {
						map.put("parent", person.getAddrgroupId());
					}
					map.put("id", person.getPersonId());
					map.put("empNo", "");
					map.put("email", person.getMailAddress());
					map.put("mobile", person.getMobilePhoneno());
					list.add(map);
				}
			}
		}

		return list;
	}

	/**
	 * Collaboration 의 Group 과 User를 조회 하는 메서드
	 * 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getCollaborationGroupAndUser(String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		/*
		 * Map<String, Object> rootMap = new HashMap<String, Object>();
		 * rootMap.put("type", "group"); rootMap.put("name", "Collaboration");
		 * rootMap.put("code", "col"); rootMap.put("groupTypeId", "");
		 * rootMap.put("parent", ""); rootMap.put("hasChild", 1);
		 * list.add(rootMap);
		 */

		Map<String, String> paramap = new HashMap<String, String>();
		paramap.put("userId", sessionuser.getUserId());
		List<Recent> colList = recentService.selectCollaboration(paramap);

		RecentSearchCondition searchCondition = new RecentSearchCondition();
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setRegisterId(sessionuser.getUserId());
		searchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_FETCH_SIZE);

		SearchResult<Recent> searchResult = recentService.selectCollaborationMember(searchCondition);

		// 그룹
		if (colList != null) {

			for (Recent recent : colList) {
				Map<String, Object> groupMap = new HashMap<String, Object>();
				groupMap.put("type", "group");
				groupMap.put("name", recent.getTitle());
				groupMap.put("code", recent.getTargetId());
				groupMap.put("groupTypeId", "");
				groupMap.put("parent", "col");

				int cnt = 0;
				for (Recent user : searchResult.getEntity()) {
					if (user.getFollowId().equals(recent.getTargetId())) {
						cnt++;
					}
				}
				groupMap.put("hasChild", cnt);
				list.add(groupMap);
			}
		}

		// 사용자 추가
		if (!(selectType.equals("GROUP")) && searchResult.getEntity() != null) {

			for (Recent user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("userName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", user.getFollowId());
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}

		}

		return list;
	}

	/**
	 * Follower 이거나 Following User 를 조회 하기 위한 메서드
	 * 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getSnsGroupAndUser(String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		/*
		 * Map<String, Object> rootMap = new HashMap<String, Object>();
		 * rootMap.put("type", "group"); rootMap.put("name", "SNS");
		 * rootMap.put("code", "sns"); rootMap.put("groupTypeId", "");
		 * rootMap.put("parent", ""); rootMap.put("hasChild", 5);
		 * list.add(rootMap);
		 */

		// Favorite
		if (!(selectType.equals("GROUP"))) {
			setFavorite(list, sessionuser);
		}

		// Contact History
		if (!(selectType.equals("GROUP"))) {
			setContactHistory(list, sessionuser);
		}

		// Following
		if (!(selectType.equals("GROUP"))) {
			setFollowing(list, sessionuser);
		}

		// Follower
		if (!(selectType.equals("GROUP"))) {
			setFollower(list, sessionuser);
		}

		// Intimate
		if (!(selectType.equals("GROUP"))) {
			setIntimate(list, sessionuser);
		}

		return list;
	}

	/**
	 * Favorite 리스트 셋팅
	 * 
	 * @param list
	 * @param sessionuser
	 */
	private void setFavorite(List<Map<String, Object>> list, User sessionuser) {

		PortalFavoriteSearchCondition favoriteSearchCondition = new PortalFavoriteSearchCondition();
		favoriteSearchCondition.setFieldName("jobTitleName");
		favoriteSearchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		favoriteSearchCondition.setRegisterId(sessionuser.getUserId());
		favoriteSearchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE);

		SearchResult<PortalFavorite> searchResult = portalFavoriteService.getAllForPeople(favoriteSearchCondition);

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("type", "group");
		groupMap.put("name", "Favorite");
		groupMap.put("code", "favorite");
		groupMap.put("groupTypeId", "");
		groupMap.put("parent", "sns");
		if (searchResult.getEntity() != null) {
			groupMap.put("hasChild", searchResult.getEntity().size());
		} else {
			groupMap.put("hasChild", 0);
		}
		list.add(groupMap);

		if (searchResult.getEntity() != null) {
			for (PortalFavorite user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("userName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", "favorite");
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}
		}
	}

	/**
	 * Contact History 리스트 셋팅
	 * 
	 * @param list
	 * @param sessionuser
	 */
	private void setContactHistory(List<Map<String, Object>> list, User sessionuser) {

		RecentSearchCondition searchCondition = new RecentSearchCondition();
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setRegisterId(sessionuser.getUserId());
		searchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE);

		SearchResult<Recent> searchResult = recentService.getAllForPeople(searchCondition);

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("type", "group");
		groupMap.put("name", "Contact History");
		groupMap.put("code", "contact");
		groupMap.put("groupTypeId", "");
		groupMap.put("parent", "sns");
		if (searchResult.getEntity() != null) {
			groupMap.put("hasChild", searchResult.getEntity().size());
		} else {
			groupMap.put("hasChild", 0);
		}
		list.add(groupMap);

		if (searchResult.getEntity() != null) {
			for (Recent user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("userName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", "contact");
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}
		}
	}

	/**
	 * Following 리스트 셋팅
	 * 
	 * @param list
	 * @param sessionuser
	 */
	private void setFollowing(List<Map<String, Object>> list, User sessionuser) {

		RecentSearchCondition searchCondition = new RecentSearchCondition();
		searchCondition.setRegisterId(sessionuser.getUpdaterId());
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setRegisterId(sessionuser.getUserId());
		searchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE);

		SearchResult<Recent> searchResult = recentService.selectFollowing(searchCondition);

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("type", "group");
		groupMap.put("name", "Following");
		groupMap.put("code", "following");
		groupMap.put("groupTypeId", "");
		groupMap.put("parent", "sns");
		if (searchResult.getEntity() != null) {
			groupMap.put("hasChild", searchResult.getEntity().size());
		} else {
			groupMap.put("hasChild", 0);
		}
		list.add(groupMap);

		if (searchResult.getEntity() != null) {
			for (Recent user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("userName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", "following");
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}
		}
	}

	/**
	 * Follower 리스트 셋팅
	 * 
	 * @param list
	 * @param sessionuser
	 */
	private void setFollower(List<Map<String, Object>> list, User sessionuser) {

		RecentSearchCondition searchCondition = new RecentSearchCondition();
		searchCondition.setRegisterId(sessionuser.getUpdaterId());
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setRegisterId(sessionuser.getUserId());
		searchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE);

		SearchResult<Recent> searchResult = recentService.selectFollower(searchCondition);

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("type", "group");
		groupMap.put("name", "Follower");
		groupMap.put("code", "follower");
		groupMap.put("groupTypeId", "");
		groupMap.put("parent", "sns");
		if (searchResult.getEntity() != null) {
			groupMap.put("hasChild", searchResult.getEntity().size());
		} else {
			groupMap.put("hasChild", 0);
		}
		list.add(groupMap);

		if (searchResult.getEntity() != null) {
			for (Recent user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("nameName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", "follower");
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}
		}
	}

	/**
	 * Initimate 리스트 셋팅
	 * 
	 * @param list
	 * @param sessionuser
	 */
	private void setIntimate(List<Map<String, Object>> list, User sessionuser) {

		RecentSearchCondition searchCondition = new RecentSearchCondition();
		searchCondition.setRegisterId(sessionuser.getUpdaterId());
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setRegisterId(sessionuser.getUserId());
		searchCondition.setPagePerRecord(Constant.DEFAULT_ADDR_POPUP_GROUP_FETCH_SIZE);

		SearchResult<Recent> searchResult = recentService.selectIntimate(searchCondition);

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("type", "group");
		groupMap.put("name", "Initimate");
		groupMap.put("code", "initimate");
		groupMap.put("groupTypeId", "");
		groupMap.put("parent", "sns");
		if (searchResult.getEntity() != null) {
			groupMap.put("hasChild", searchResult.getEntity().size());
		} else {
			groupMap.put("hasChild", 0);
		}
		list.add(groupMap);

		if (searchResult.getEntity() != null) {
			for (Recent user : searchResult.getEntity()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("userName", user.getTitle());
					map.put("name", user.getTitle() + " " + user.getJobTitleName());
					map.put("jobTitleName", user.getJobTitleName());
					map.put("teamName", user.getTeamName());
				} else {
					map.put("userName", user.getEnglishTitle());
					map.put("name", user.getEnglishTitle() + " " + user.getJobTitleEnglishName());
					map.put("jobTitleName", user.getJobTitleEnglishName());
					map.put("teamName", user.getTeamEnglishName());
				}
				map.put("code", "");
				map.put("parent", "initimate");
				map.put("id", user.getTargetId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				list.add(map);
			}
		}
	}

	/**
	 * 그룹과 사용자 별로 조직도 내의 검색 결과를 조회 하는 메서드
	 * 
	 * @param keyword 조직도 조회 검색어
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> searchGroupAndUser(String keyword, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectSearchForTree(sessionuser.getLocaleCode(), keyword, sessionuser.getUserId());
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();				
				
				if (!user.getGroupId().equals("OUTSIDE")){
					
					map.put("type", "user");	
					String strJobTitle = user.getJobTitleName();
					if(user.getJobDutyName() != null)
					{
						strJobTitle = user.getJobDutyName();
					}
					map.put("userName", user.getUserName());
					map.put("name", user.getUserName() + " " + strJobTitle + " " + user.getTeamName());		
					map.put("jobTitleName", strJobTitle);
					
				} else {
					map.put("type", "addruser");
					map.put("userName", user.getUserName());
					map.put("name", user.getUserName() + " " + user.getJobTitleName() + " " + user.getTeamName());
					map.put("jobTitleName", user.getJobTitleName());
				}				
				
				map.put("teamName", user.getTeamName());
				map.put("code", user.getUserId());
				map.put("parent", user.getGroupId());	// 그룹 코드 추가
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);			
				
			}
			
		}

		if (!(selectType.equals("USER"))) {

			List<Group> groupList = groupService.selectGroupSearch(sessionuser.getLocaleCode(), keyword);
			for (Group group : groupList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "group");
				if (sessionuser.getLocaleCode().equals("ko")) {
					map.put("name", group.getGroupName());
				} else {
					map.put("name", group.getGroupEnglishName());
				}
				map.put("code", group.getGroupId());
				map.put("groupTypeId", group.getGroupTypeId());
				map.put("parent", "");
				map.put("hasChild", 0);
				list.add(map);
			}

		}

		return list;
	}
	

	/**
	 * 직급을 통한 조직도 트리를 그리기 위해 리스트를 조회해옴
	 * 
	 * @param 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getJobTitleGroupAndUser(String jobTitleCode, String userId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = sessionuser.getPortalId();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if(jobTitleCode==null){

			List<JobTitle> jobTitleList = jobTitleService.selectJobTitleList(portalId);
			
			for (JobTitle jobTitle : jobTitleList) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "group");
				map.put("name", jobTitle.getJobTitleName());
				map.put("code", jobTitle.getJobTitleCode());
				map.put("groupTypeId", "");
				map.put("parent", "pri");
				map.put("hasChild", jobTitle.getNum());
				list.add(map);
			}	
		}		

		List<User> userList = userService.selectJobTitleUserForTree(sessionuser.getLocaleCode(), jobTitleCode, userId);
		for (User user : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "user");


			String strJobTitle = user.getJobTitleName();
			if(!StringUtil.isEmpty(user.getJobDutyName()))
			{
				strJobTitle = user.getJobDutyName();
			}
			if("1".equals(user.getJobTitleCode()) || "2".equals(user.getJobTitleCode())){
				map.put("name", user.getUserName() + " " + strJobTitle);	
			}
			else
			{
				map.put("name", user.getUserName() + " " + strJobTitle + "/" + user.getTeamName());	
			}
			map.put("jobTitle", strJobTitle);
			map.put("teamName", user.getTeamName());
			
			
			map.put("code", "");
			map.put("parent", user.getGroupId());
			map.put("id", user.getUserId());
			map.put("empNo", user.getEmpNo());
			map.put("email", user.getMail());
			map.put("mobile", user.getMobile());
			map.put("leader", user.getLeader());
			list.add(map);
		}
		
		return list;
	}


	/**
	 * 직급을 통한 조직도 트리를 그리기 위해 리스트를 조회해옴
	 * 
	 * @param 
	 * @param selectType GROUP : 조직도만 조회, USER : 사용자 조회, ALL : 전체 조회
	 * @return 조회된 그룹과 사용자 리스트 Map
	 */
	private List<Map<String, Object>> getJobDutyGroupAndUser(String jobDutyCode, String userId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = sessionuser.getPortalId();
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if(jobDutyCode==null){

			List<JobDuty> jobDutyList = jobDutyService.selectJobDutyList(portalId);
			
			for (JobDuty jobDuty : jobDutyList) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "group");
				map.put("name", jobDuty.getJobDutyName());
				map.put("code", jobDuty.getJobDutyCode());
				map.put("groupTypeId", "");
				map.put("parent", "pri");
				map.put("hasChild", jobDuty.getNum());
				list.add(map);
			}	
		}	
		
		//2012-12-07 최재영
		//회장님을 별도로 보여드리기 위한 노력...
		if("10".equals(jobDutyCode) || "11".equals(jobDutyCode))
		{
			List<User> userList = userService.selectJobDutyUserForTree(sessionuser.getLocaleCode(), "11", userId);
			
			for (User user : userList) {
					
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}			
						
				map.put("name", user.getUserName() + " " + strJobTitle);// + "/" + user.getTeamName());		
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				
				if("10".equals(jobDutyCode) && "1".equals(user.getJobTitleCode()))
				{						
					list.add(map);
				}
				
				if("11".equals(jobDutyCode) && "2".equals(user.getJobTitleCode()))
				{						
					list.add(map);
				}	
			}			
			
		}		
		else
		{
			List<User> userList = userService.selectJobDutyUserForTree(sessionuser.getLocaleCode(), jobDutyCode, userId);
			
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}
				map.put("name", user.getUserName() + " " + strJobTitle + "/" + user.getTeamName());		
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
		}
		
		return list;
	}
	

	private List<Map<String, Object>> getOrgGroupAndUser2(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		
		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);
			
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}
		
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				
				if((sessionuser.getScheduleManager()).equals(user.getScheduleManager())){//동일한 스케줄 인원이면 더한다. 즉 같은 반장 소속이면.
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "user");

	
					String strJobTitle = user.getJobTitleName();
					if(!StringUtil.isEmpty(user.getJobDutyName()))
					{
						strJobTitle = user.getJobDutyName();
					}
					
					map.put("name", user.getUserName() + " " + strJobTitle);
					map.put("jobTitle", strJobTitle);
					map.put("teamName", user.getTeamName());

					map.put("code", "");
					map.put("parent", user.getGroupId());
					map.put("id", user.getUserId());
					map.put("empNo", user.getEmpNo());
					map.put("email", user.getMail());
					map.put("mobile", user.getMobile());
					map.put("leader", user.getLeader());
					list.add(map);
				}
			}
		}

		return list;
	}
}
