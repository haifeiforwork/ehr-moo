/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceCode;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * AdminPermission Main Controller
 * 
 * @author 정애란(tseliot@lgcns.com)
 * @version 
 */

@Controller
@RequestMapping(value = "/collpack/kms/admin")
public class AdminPermissionController extends BaseController {

	@Autowired
	private AdminPermissionService adminPermissionService;
	
	
	@Autowired
	private AdminPermissionDao adminPermissionDao;
	
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private BoardItemService boardItemService;
	

	/**
	 * 사용자 권한관리 목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listUserPermission.do")	
	public ModelAndView listUserPermission(KmsAdminSearchCondition searchCondition) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/listUserPermission");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminPermission> searchResult = adminPermissionService.getUserList(searchCondition);
		List<String> workPlaceNameList = adminPermissionService.getWorkPlaceNameList();
		
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workPlaceNameList", workPlaceNameList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/listRecommendReply.do")	
	public ModelAndView listRecommendReply(KmsAdminSearchCondition searchCondition) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/listRecommendReply");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminPermission> searchResult = adminPermissionService.getUserRecommendReplyList(searchCondition);
		List<String> workPlaceNameList = adminPermissionService.getWorkPlaceNameList();
		
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workPlaceNameList", workPlaceNameList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/compulsionTimeLogView.do")	
	public ModelAndView compulsionTimeLogView(KmsAdminSearchCondition searchCondition) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/compulsionTimeLogView");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminPermission> searchResult = adminPermissionService.getCompulsionTimeLogList(searchCondition);
		List<String> workPlaceNameList = adminPermissionService.getWorkPlaceNameList();
		
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("workPlaceNameList", workPlaceNameList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/listRecommendReplyExceldown.do")	
	public ModelAndView listRecommendReplyExceldown(KmsAdminSearchCondition searchCondition) {
		
		ExcelViewModel excel = new ExcelViewModel();  
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPagePerRecord(10000);
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(99999);
		
		SearchResult<AdminPermission> searchResult = adminPermissionService.getUserRecommendReplyList(searchCondition);
		

		String fileName = "kms_"
			 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

			
		excel.setFileName(fileName);   
	    excel.setSheetName("Sheet");   
	    
	    excel.setTitle("kms_"+ DateUtil.getTodayDateTime("yyyyMMdd"));  
	    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
	    
	    excel.addColumn("이름", "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
	    excel.addColumn("직책", "jobDutyName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
	    excel.addColumn("사번", "empNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
	    excel.addColumn("아이디", "userId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
	    excel.addColumn("사업장", "workPlaceName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("부서명", "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("추천수", "recommendCnt1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("댓글수", "replyCnt1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("추천받은수", "recommendCnt2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("댓글입력된수", "replyCnt2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		excel.addColumn("추천/댓글점수", "score", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		
		excel.setDataList(searchResult.getEntity()); 
				


       return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}
		
	
		@RequestMapping(value = "/compulsionTimeLogExceldown.do")	
		public ModelAndView compulsionTimeLogExceldown(KmsAdminSearchCondition searchCondition) {
			
			ExcelViewModel excel = new ExcelViewModel();  
			if(!isSystemAdmin()){
				throw new IKEP4AuthorizedException();
			}
			
			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}
			searchCondition.setPagePerRecord(10000);
			searchCondition.setStartRowIndex(0);
			searchCondition.setEndRowIndex(99999);
			
			SearchResult<AdminPermission> searchResult = adminPermissionService.getCompulsionTimeLogList(searchCondition);
			

			String fileName = "kms_"
				 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

				
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle("kms_"+ DateUtil.getTodayDateTime("yyyyMMdd"));  
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    
		    excel.addColumn("이름", "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("직책", "jobDutyName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("사번", "empNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("아이디", "userId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("사업장", "workPlaceName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
			excel.addColumn("부서명", "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
			excel.addColumn("시간", "clickTime", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
			excel.addColumn("구분", "clickFlg", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
			
			excel.setDataList(searchResult.getEntity()); 
					


	       return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
		}
	
	@RequestMapping(value = "/expertManagement.do")
	public ModelAndView expertManagement() {

		AdminPermission categoryBoardId = new AdminPermission();
		categoryBoardId.setBoardId("100000000001");

		List<AdminPermission> categoryList = null;
		categoryList = adminPermissionService.listCategoryUser(categoryBoardId);		
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/expertManagement");
		
		model.addObject("categoryList", categoryList);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/listSpecialUser.do")
	public ModelAndView listSpecialUser() {


		List<AdminPermission> specialUserList = null;
		specialUserList = adminPermissionService.listSpecialUser();		
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/listSpecialUser");
		
		model.addObject("specialUserList", specialUserList);
		return model;
	}
	
	@RequestMapping(value = "/createFormMessage.do")
	public ModelAndView createFormMessage() {


		BoardItem boardItem = this.boardItemService.readCaution();
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/createFormMessage");
		
		model.addObject("boardItem", boardItem);
		return model;
	}
	
	@RequestMapping(value = "/createMessage")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createMessage(AdminPermission category) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		category.setRegisterId(user.getUserId());
		
		this.adminPermissionService.insertMessage(category);
		
		return "success";
	}
	
	@RequestMapping(value = "/listKeyInfoPermission.do")	
	public ModelAndView listKeyInfoPermission(KmsAdminSearchCondition searchCondition) {
		 		
		adminPermissionService.insertNewKeyInfoPermission();
		
		List<AdminPermission> keyInfoPermissionUser = null;
		keyInfoPermissionUser = adminPermissionService.listKeyInfoPermission();		
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/listKeyInfoPermission");
		
		model.addObject("keyInfoPermissionUser", keyInfoPermissionUser);
		return model;
	}
	
	@RequestMapping(value = "/compulsionTimeSettingForm.do")	
	public ModelAndView compulsionTimeSettingForm() {
		 		
		//adminPermissionService.insertNewKeyInfoPermission();
		
		//List<AdminPermission> keyInfoPermissionUser = null;
		//keyInfoPermissionUser = adminPermissionService.listKeyInfoPermission();	
		
		CompulsionTime compulsionTime =  adminPermissionService.selectCompulsionTimeSetting();
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/compulsionTimeSettingForm");
		
		model.addObject("compulsionTime", compulsionTime);
		return model;
	}
	
	@RequestMapping(value = "/saveCompulsionTime.do")	
	public ModelAndView saveCompulsionTime(CompulsionTime compulsionTime) {
		 		
	
		adminPermissionService.saveCompulsionTimeSetting(compulsionTime);
		ModelAndView model = new ModelAndView("collpack/kms/admin/compulsionTimeSettingForm");
		

		return model;
	}
	
	@RequestMapping(value = "/registSpecialUserForm.do")
	public ModelAndView registSpecialUserForm() {

		ModelAndView model = new ModelAndView("collpack/kms/admin/registSpecialUserForm");
		
		return model;
	}
	
	@RequestMapping(value = "/registKeyInfoPermissionForm.do")
	public ModelAndView registKeyInfoPermissionForm() {

		ModelAndView model = new ModelAndView("collpack/kms/admin/registKeyInfoPermissionForm");
		
		return model;
	}
	
	@RequestMapping(value = "/deleteSpecialUser.do")
	public @ResponseBody String deleteWinnerPeople(@RequestParam(value="userIds") String userIds,
			@RequestParam(value="categoryIds") String categoryIds) {

		try {			
			adminPermissionService.deleteSpecialUser(userIds,categoryIds);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	@RequestMapping(value = "/deleteKeyInfoPermission.do")
	public @ResponseBody String deleteKeyInfoPermission(@RequestParam(value="userIds") String userIds) {

		try {			
			adminPermissionService.deleteKeyInfoPermission(userIds);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	@RequestMapping(value = "/registSpecialUser.do")
	public String registSpecialUser(AdminPermission specialUser) {

		User user = (User) getRequestAttribute("ikep.user");
		specialUser.setRegisterId(user.getUserId());
		specialUser.setRegisterName(user.getUserName());
		adminPermissionService.insertSpecialUser(specialUser);
		
		return "redirect:/collpack/kms/admin/listSpecialUser.do";
	}
	
	@RequestMapping(value = "/registKeyInfoPermission.do")
	public String registKeyInfoPermission(AdminPermission keyInfoPermission) {

		User user = (User) getRequestAttribute("ikep.user");
		keyInfoPermission.setRegisterId(user.getUserId());
		keyInfoPermission.setRegisterName(user.getUserName());
		adminPermissionService.insertKeyInfoPermission(keyInfoPermission);
		
		return "redirect:/collpack/kms/admin/listKeyInfoPermission.do";
	}
	
	@RequestMapping(value = "/expertFieldManagement.do")
	public ModelAndView expertFieldManagement() {

		AdminPermission categoryBoardId = new AdminPermission();
		categoryBoardId.setBoardId("100000000001");

		List<AdminPermission> categoryList = null;
		categoryList = adminPermissionService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView("collpack/kms/admin/expertFieldManagement");
		
		model.addObject("categoryList", categoryList);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/expertView.do")
	public ModelAndView expertView(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/collpack/kms/admin/expertView");
		
		User user = (User) getRequestAttribute("ikep.user");
		//Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		//mav.addObject("isSystemAdmin", isSystemAdmin);
		//mav.addObject("Bigmenu", "payDiligenceMng");
		//mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(AdminPermission category ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = category.getAddNameList();
		String delIdList = category.getDelIdList();
		String updateNameList = category.getUpdateNameList();
		String updateIdList = category.getUpdateIdList();

		AdminPermission receiveCategoryNmList = new AdminPermission();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(category.getAlignList());

		List<AdminPermission> list = new ArrayList<AdminPermission>();
		list.add(receiveCategoryNmList);
		this.adminPermissionService.insertCategoryNm(list);
		
		return "success";
	}
	
	@RequestMapping(value = "/createCategoryUsers")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryUsers(AdminPermission category) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		this.adminPermissionService.insertCategoryUsers(category.getCategoryUsers(),user);
		
		return "success";
	}
		
	
	
	/**
	 * 사용자 권한조회
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/getUserPermPopup.do")	
	public ModelAndView getUserPermPopup(@RequestParam(value="userId", required=true ) String userId,
			@RequestParam(value="gubun", required=false, defaultValue="N" ) String gubun) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/getUserPermPopup");
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		searchCondition.setUserId(userId);
		List<AdminPermission> periodList = adminPermissionService.getPeriodList();
		
		String thisYear = DateUtil.getToday("yyyy").toString();
		String thisMonth = DateUtil.getToday("MM").toString();
		searchCondition.setSyear(thisYear);
		searchCondition.setSmonth(thisMonth);
		AdminPermission userInfo = adminPermissionService.getUserPermPopup(searchCondition);
		
		modelAndView.addObject("adminPermission", userInfo);	
		modelAndView.addObject("periodList", periodList);
		modelAndView.addObject("gubun", gubun);

		return modelAndView;
	}
	
	@RequestMapping(value = "/modifySpecialUserForm.do")	
	public ModelAndView modifySpecialUserForm(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/registSpecialUserForm");
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		searchCondition.setUserId(userId);
		
		AdminPermission userInfo = adminPermissionService.getSpecialUserInfo(searchCondition);
		
		modelAndView.addObject("adminPermission", userInfo);	

		return modelAndView;
	}
	
	@RequestMapping(value = "/modifyKeyInfoPermissionForm.do")	
	public ModelAndView modifyKeyInfoPermissionForm(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/registKeyInfoPermissionForm");
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		searchCondition.setUserId(userId);
		
		AdminPermission userInfo = adminPermissionService.getKeyInfoPermissionUser(searchCondition);
		
		modelAndView.addObject("adminPermission", userInfo);	

		return modelAndView;
	}
	
	@RequestMapping(value = "/recommendDetailView.do")	
	public ModelAndView recommendDetailView(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/recommendReplyDetailView");
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setUserId(userId);
		List<BoardItem> recommendReplyDetailList = boardItemService.getRecommendDetailList(searchCondition);
		
		modelAndView.addObject("recommendReplyDetailList", recommendReplyDetailList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/replyDetailView.do")	
	public ModelAndView replyDetailView(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/recommendReplyDetailView");
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setUserId(userId);
		List<BoardItem> recommendReplyDetailList = boardItemService.getReplyDetailList(searchCondition);
		
		modelAndView.addObject("recommendReplyDetailList", recommendReplyDetailList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/recommendReceiveDetailView.do")	
	public ModelAndView recommendReceiveDetailView(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/recommendReceiveDetailView");
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setUserId(userId);
		List<BoardItem> recommendReplyDetailList = boardItemService.getRecommendReceiveDetailList(searchCondition);
		
		modelAndView.addObject("recommendReplyDetailList", recommendReplyDetailList);

		return modelAndView;
	}
	
	@RequestMapping(value = "/replyReceiveDetailView.do")	
	public ModelAndView replyReceiveDetailView(@RequestParam(value="userId", required=true ) String userId) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/replyReceiveDetailView");
		BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
		searchCondition.setUserId(userId);
		List<BoardItem> recommendReplyDetailList = boardItemService.getReplyReceiveDetailList(searchCondition);
		
		modelAndView.addObject("recommendReplyDetailList", recommendReplyDetailList);

		return modelAndView;
	}
		
	
	@RequestMapping(value = "/getUserPermission.do")	
	public ModelAndView getUserPermission(@RequestParam(value="userId", required=true ) String userId,
			@RequestParam(value="sYear" ) String searchYear,
			@RequestParam(value="sMonth" ) String searchMonth,
			@RequestParam(value="gubun", required=false, defaultValue="N" ) String gubun) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/getUserPermPopup");
		KmsAdminSearchCondition searchCondition = new KmsAdminSearchCondition();
		if(StringUtils.isEmpty(searchYear)){
			String thisYear = DateUtil.getToday("yyyy").toString();
			String thisMonth = DateUtil.getToday("MM").toString();
			searchCondition.setSyear(thisYear);
			searchCondition.setSmonth(thisMonth);
		}else{
			searchCondition.setSyear(searchYear);
			searchCondition.setSmonth(searchMonth);
		}
		
		
		
		searchCondition.setUserId(userId);
		List<AdminPermission> periodList = adminPermissionService.getPeriodList();
		AdminPermission userInfo = adminPermissionService.getUserPermPopup(searchCondition);
		
		
		modelAndView.addObject("adminPermission", userInfo);	
		modelAndView.addObject("periodList", periodList);
		modelAndView.addObject("gubun", gubun);
		modelAndView.addObject("searchYear", searchYear);
		modelAndView.addObject("searchMonth", searchMonth);

		return modelAndView;
	}
	
	
	/**
	 * 사용자 의무건수 수정
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/updateUserPermission.do")	
	public String updateUserPermission(@RequestParam(value="userId", required=true ) String userId, 
			@RequestParam(value="myCnt" ) String myCnt, 
			@RequestParam(value="searchYear" ) String searchYear,
			@RequestParam(value="searchMonth" ) String searchMonth,
			@RequestParam(value="infoGrade") String infoGrade) {		
		
		Map<String, String> paramMap = new HashMap<String, String>();		
		paramMap.put("userId", userId);
		paramMap.put("myCnt", myCnt);
		paramMap.put("infoGrade", infoGrade);
		paramMap.put("searchYear", searchYear);
		paramMap.put("searchMonth", searchMonth);
		
		adminPermissionService.updateUserCnt(paramMap);
		return "redirect:/collpack/kms/admin/getUserPermPopup.do?userId="+ userId + "&gubun=Y";
	}
	
	/**
	 * 팀리더 권한관리 목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listLeaderPermission.do")	
	public ModelAndView listLeaderPermission(KmsAdminSearchCondition searchCondition) {
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		 
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/listLeaderPermission");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		SearchResult<AdminPermission> searchResult = adminPermissionService.getLeaderPermissionList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());

		return modelAndView;
	}
	
	/**
	 * 리더별 관리 팀 목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listTeamByLeaderPermission.do")	
	public ModelAndView listTeamByLeaderPermission(KmsAdminSearchCondition searchCondition) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		 
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/listTeamByLeaderPermission");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		SearchResult<AdminTeamLeader> searchResult = adminPermissionService.getTeamByLeaderPermissionList(searchCondition);
		
		User user = readUser();		
		
		AdminPermission adminPermission = adminPermissionDao.getUserInfo(searchCondition.getUserId());	
		
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("adminPermission", adminPermission);		
		modelAndView.addObject("workPlaceList", workPlaceList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());		
		return modelAndView;
	}
	
	
	/**
	 * 팀리더가 관리하는 팀목록 일괄삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param teamCodes 삭제대상 부서코드
	 */
	@RequestMapping(value = "/deleteTeamByLeader.do")
	public @ResponseBody String deleteTeamByLeader(@RequestParam(value="userId", required=true) String userId, 
			@RequestParam(value="teamCodes") String teamCodes) {

		try {
			
			adminPermissionService.deleteTeamByLeader(userId, teamCodes);			

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}
	
	
	/**
	 * 사업장 선택시마다 해당 부서코드 목록조회
	 * 
	 * @param workPlaceName	사업장명(사업장마다 동일한 공장이 여러게 있기때문에 사업장명으로 가져온다)
	 */
	@RequestMapping(value = "/listTeamCodes.do")
	public @ResponseBody String listTeamCodes(@RequestParam(value="workPlaceName", required=true) String workPlaceName,
			@RequestParam(value="isTitle", required=false, defaultValue="1") String isTitle) {
		String htmlListTeamCodes = "";
		try {
			htmlListTeamCodes = adminPermissionService.listTeamCodes(workPlaceName, isTitle); 

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return htmlListTeamCodes;
	}
	
	
	/**
	 * 팀리더가 관리하는 팀추가 처리 비동기 컨트롤 메서드
	 * 
	 * @param teamCodes 추가대상 부서코드
	 */
	@RequestMapping(value = "/addTeamByLeader.do")
	public @ResponseBody String addTeamByLeader(AdminTeamLeader adminTeamLeader) {

		try {
			adminPermissionService.addTeamByLeader(adminTeamLeader);	

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	

	/**
	 * 사업장별 부서 의무건수 목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listTeamCntByWorkPlace.do")	
	public ModelAndView listTeamCntByWorkPlace(KmsAdminSearchCondition searchCondition) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/listTeamCntByWorkPlace");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		SearchResult<AdminTeamLeader> searchResult = adminPermissionService.getTeamCntByWorkPlaceList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());
		modelAndView.addObject("workPlaceList", workPlaceList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());

		return modelAndView;
	}
	
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin() {
		User user = this.readUser();
		return this.aclService.isSystemAdmin(Board.KMS_MANAGER, user.getUserId());
	}	
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		
		return (User) this.getRequestAttribute("ikep.user");
	}
	
	
}
