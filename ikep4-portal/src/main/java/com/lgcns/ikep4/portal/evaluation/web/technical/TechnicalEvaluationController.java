package com.lgcns.ikep4.portal.evaluation.web.technical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationExport;
import com.lgcns.ikep4.portal.evaluation.constant.EvaluationImport;
import com.lgcns.ikep4.portal.evaluation.constant.Technical;
import com.lgcns.ikep4.portal.evaluation.constant.TechnicalKeySet;
import com.lgcns.ikep4.portal.evaluation.constant.TechnicalRFC;
import com.lgcns.ikep4.portal.evaluation.model.TechnicalCommon;
import com.lgcns.ikep4.portal.evaluation.model.TechnicalEvaluation;
import com.lgcns.ikep4.portal.evaluation.model.TechnicalReview;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.portal.util.PagingUtils;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/evaluation/technical")
public class TechnicalEvaluationController extends BaseController {

	@Autowired
	private ACLService aclService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private MssTeamTreeSpecialService mssTeamTreeSpecialService;

	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Autowired
	private UserDao userDao;

	//Log
	protected final Log logger = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimMss", user.getUserId());

	}

	/**
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/technicalList.do")
	public ModelAndView technicalList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String action = (String) params.get("action");
		Technical technical = Technical.lookup(action.toUpperCase());

		ModelAndView mav = new ModelAndView(technical.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_APRSR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> tempList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		ArrayList<Map<?, ?>> etList = PagingUtils.setPagingParam(tempList, params);

		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/technicalCheckView.do")
	public @ResponseBody Map<?, ?> technicalCheckView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Technical technical = Technical.lookup((String)params.get("action"));

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		try {

			//RFC PARAMETER SETTING START
			for(String apprKey : TechnicalKeySet.COMMON.getKeys()){
				apprMap.put(apprKey, params.get(apprKey));
			}
			rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
			//RFC PARAMETER SETTING END

			Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

			result = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/technicalView.do")
	public ModelAndView technicalView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		String technicalGubun = (String) params.get("TECGB");
		Technical technical = Technical.lookup("EVALUATION_VIEW" + technicalGubun);

		ModelAndView mav = new ModelAndView(technical.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : TechnicalKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> etDetail1 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> etDetail2 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());
		ArrayList<Map<?, ?>> etScore = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_SCORE.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("esHeader", esHeader);
		mav.addObject("etDetail1", etDetail1);
		mav.addObject("etDetail2", etDetail2);
		mav.addObject("etScore", etScore);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * @param technicalCommon
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/technicalProc.do")
	public @ResponseBody Map<?, ?> technicalProc(TechnicalCommon technicalCommon, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		HashMap<String, Object> isApprMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> itDetail1List = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> itDetail2List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> deatil = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(String apprKey : TechnicalKeySet.COMMON.getKeys()) {
				methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
				isApprMap.put(apprKey, technicalCommon.getClass().getDeclaredMethod("get" + methodName).invoke(technicalCommon));
			}

			for(TechnicalEvaluation technicalEvaluation : technicalCommon.getDetails1()) {
				deatil = new HashMap<String, Object>();
				for(String apprKey : TechnicalKeySet.EVALUATION.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					deatil.put(apprKey, technicalEvaluation.getClass().getDeclaredMethod("get" + methodName).invoke(technicalEvaluation));
				}
				itDetail1List.add(deatil);
			}

			for(TechnicalEvaluation technicalEvaluation : technicalCommon.getDetails2()) {
				deatil = new HashMap<String, Object>();
				for(String apprKey : TechnicalKeySet.EVALUATION.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					deatil.put(apprKey, technicalEvaluation.getClass().getDeclaredMethod("get" + methodName).invoke(technicalEvaluation));
				}
				itDetail2List.add(deatil);
			}

			rfcParam.put(EvaluationImport.IV_APRSR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IV_BUTTON.name(), technicalCommon.getButton());
			rfcParam.put(EvaluationImport.IV_PERNR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IS_APPR.name(), isApprMap);
			rfcParam.put(EvaluationImport.IT_DETAIL1.name(), itDetail1List);
			rfcParam.put(EvaluationImport.IT_DETAIL2.name(), itDetail2List);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(TechnicalRFC.PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/technicalReviewList.do")
	public ModelAndView technicalReviewList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Technical technical = Technical.REVIEW;
		ModelAndView mav = new ModelAndView(technical.getView());

		User user = (User) getRequestAttribute("ikep.user");

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_REVWR.name(), user.getEmpNo());
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		String evRvsts = (String) export.get(EvaluationExport.EV_RVSTS.name());
		String evRvstsx = (String) export.get(EvaluationExport.EV_RVSTSX.name());
		Object evMrevw = export.get(EvaluationExport.EV_MREVW.name());
		Map<?, ?> esReturn = (Map<?, ?>) export.get(EvaluationExport.ES_RETURN.name());
		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		mav.addObject("evRvsts", evRvsts);
		mav.addObject("evRvstsx", evRvstsx);
		mav.addObject("evMrevw", evMrevw);
		mav.addObject("esReturn", esReturn);
		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	@RequestMapping(value = "/technicalReviewProc.do")
	public @ResponseBody Map<?, ?> technicalReviewProc(TechnicalReview technicalReview, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		List<HashMap<String, Object>> detailList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> detail = null;

		try {

			//RFC PARAMETER SETTING START
			String methodName = "";

			for(TechnicalReview review : technicalReview.getReviews()) {
				detail = new HashMap<String, Object>();
				for(String apprKey : TechnicalKeySet.REVIEW.getKeys()) {
					methodName = apprKey.replace(apprKey.substring(1), apprKey.substring(1).toLowerCase());
					detail.put(apprKey, review.getClass().getDeclaredMethod("get" + methodName).invoke(review));
				}
				detailList.add(detail);
			}

			rfcParam.put(EvaluationImport.IV_BUTTON.name(), technicalReview.getButton());
			rfcParam.put(EvaluationImport.IV_REVWR.name(), user.getEmpNo());
			rfcParam.put(EvaluationImport.IT_LIST.name(), detailList);

			result = webEssRcv.callRfcFunction(TechnicalRFC.REVIEW_PROCESS.getRfc(), rfcParam, null, request);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popTechnicalIndividualEvaluationList.do")
	public ModelAndView popTechnicalIndividualEvaluationList(@RequestParam(value="empno", required=true) String empno, @RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Technical technical = Technical.INDIVIDUAL_EVALUATION;
		ModelAndView mav = new ModelAndView(technical.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		rfcParam.put(EvaluationImport.IV_APRSR.name(), empno);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		ArrayList<Map<?, ?>> etList = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_LIST.name());

		mav.addObject("etList", etList);
		mav.addObject("params", params);

		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/popTechnicalIndividualEvaluationView.do")
	public ModelAndView popTechnicalIndividualEvaluationView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Technical technical = Technical.INDIVIDUAL_EVALUATION_VIEW;
		ModelAndView mav = new ModelAndView(technical.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : TechnicalKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> etDetail1 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> etDetail2 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("esHeader", esHeader);
		mav.addObject("etDetail1", etDetail1);
		mav.addObject("etDetail2", etDetail2);
		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 기능직 - 평가표조회(HSS)
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/technicalHSS.do")
	public ModelAndView technicalHSS(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Technical technical = Technical.HSS;
		ModelAndView mav = new ModelAndView(technical.getView());

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		/***********************************
		 * Teamview
		 **********************************/
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;

		if(isSystemAdmin){
			searchRootGroupId="";//전체인원
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}else{
			List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
			if(specialList!=null && specialList.size()>0){
				list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
			}else{
				searchRootGroupId=user.getGroupId();
				if("22".equals(user.getJobDutyCode())){//반장이면
					list = getOrgGroupAndUser2(searchRootGroupId, "", "USER");//소속 인원만.
				}else{
					list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
				}
			}
		}

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		mav.addObject("params", params);

		return mav;

	}

	/**
	 * 기증직 - 평가표조회(HSS) - 목록
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/technicalHSSList.do")
	public @ResponseBody Map<?, ?> technicalHSSList(@RequestParam HashMap<String, Object> params, HttpServletRequest request) {

		Map<?, ?> result = null;
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();

		try {

			String empNo = (String) params.get("emp_no");

			//RFC PARAMETER SETTING START
			rfcParam.put(EvaluationImport.IV_PERNR.name(), empNo);
			//RFC PARAMETER SETTING END

			result = webEssRcv.callRfcFunction(TechnicalRFC.HSS.getRfc(), rfcParam, null, request);

			Map userInfo = (HashMap<String, String>) this.userDao.empNoToUserId(empNo);
			result.putAll(userInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return result;

	}

	/**
	 * 기능직 - 평가표조회(HSS) - 상세
	 *
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/technicalHSSView.do")
	public ModelAndView technicalHSSView(@RequestParam HashMap<String, Object> params, HttpServletRequest request) throws Exception {

		Technical technical = Technical.HSS_VIEW;
		ModelAndView mav = new ModelAndView(technical.getView());

		//RFC PARAMETER SETTING START
		HashMap<String, Object> rfcParam = new HashMap<String, Object>();
		HashMap<String, Object> apprMap = new HashMap<String, Object>();

		for(String apprKey : TechnicalKeySet.COMMON.getKeys()){
			apprMap.put(apprKey, params.get(apprKey));
		}
		rfcParam.put(EvaluationImport.IS_APPR.name(), apprMap);
		//RFC PARAMETER SETTING END

		Map<?, ?> export = webEssRcv.callRfcFunction(technical.getTechnicalRFC().getRfc(), rfcParam, null, request);

		String evQunam1 = (String) export.get(EvaluationExport.EV_QUNAM1.name());
		String evQunam2 = (String) export.get(EvaluationExport.EV_QUNAM2.name());
		Map<?, ?> esHeader = (Map<?, ?>) export.get(EvaluationExport.ES_HEADER.name());
		ArrayList<Map<?, ?>> etDetail1 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL1.name());
		ArrayList<Map<?, ?>> etDetail2 = (ArrayList<Map<?, ?>>) export.get(EvaluationExport.ET_DETAIL2.name());

		mav.addObject("evQunam1", evQunam1);
		mav.addObject("evQunam2", evQunam2);
		mav.addObject("esHeader", esHeader);
		mav.addObject("etDetail1", etDetail1);
		mav.addObject("etDetail2", etDetail2);
		mav.addObject("params", params);

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

		return list;
	}

	/**
	 * @param groupId
	 * @param userId
	 * @param selectType
	 * @return
	 */
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
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sessionuser.getScheduleManager():"+sessionuser.getScheduleManager()+"@@");
		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ user.getScheduleManager():"+user.getScheduleManager()+"@@");

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

	/**
	 * @param specialGroupList
	 * @param userId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser3(List<MssTeamTreeSpecial> specialGroupList, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (MssTeamTreeSpecial specialGroup : specialGroupList) {
			Group searchgroup = new Group();
			searchgroup.setGroupId(specialGroup.getGroupId());
			searchgroup.setRegisterId(specialGroup.getUserId());


			Group specialRootGroup = groupService.read(specialGroup.getGroupId());

			Map<String, Object> smap = new HashMap<String, Object>();


			// 부서
			if(specialGroup.getChildGroupCnt() < 1){


				List<Group> groupList = groupService.selectOrgGroup(searchgroup);

				if(groupList.size() == 0){
					groupList = groupService.selectOrgGroupSp(searchgroup);
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
			}else{
				smap.put("type", "group");
				smap.put("name", specialRootGroup.getGroupName());
				smap.put("code", specialRootGroup.getGroupId());
				smap.put("groupTypeId", specialRootGroup.getGroupTypeId());
				smap.put("parent", specialRootGroup.getParentGroupId());
				smap.put("hasChild", specialRootGroup.getChildGroupCount());
				list.add(smap);

			}
		}

		return list;

	}

}
