package com.lgcns.ikep4.collpack.kms.admin.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceCode;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminWinnerManageService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.lang.DateUtil;

/**
 * AdminWinnerManage Controller
 * 
 * @author 정애란(tseliot@lgcns.com)
 * @version 
 */

@Controller
@RequestMapping(value = "/collpack/kms/admin/winner")
public class AdminWinnerManageController extends BaseController {
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private AdminWinnerManageService adminWinnerManageService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		
		return (User) this.getRequestAttribute("ikep.user");
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
	 * 우수지식인 목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listWinners.do")	
	public ModelAndView listWinners(KmsAdminSearchCondition searchCondition) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/listWinners");
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminWinner> searchResult = adminWinnerManageService.getWinnerList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());		
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));

		return modelAndView;
	}
	
	@RequestMapping(value = "/listHandsomePeople.do")	
	public ModelAndView listHandsomePeople(KmsAdminSearchCondition searchCondition) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/listHandsomePeople");
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminWinner> searchResult = adminWinnerManageService.getWinnerPeopleList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());		
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));

		return modelAndView;
	}
	
	@RequestMapping(value = "/listHandsomeTeam.do")	
	public ModelAndView listHandsomeTeam(KmsAdminSearchCondition searchCondition) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/listHandsomeTeam");
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminWinner> searchResult = adminWinnerManageService.getWinnerTeamList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());		
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));

		return modelAndView;
	}
	
	@RequestMapping(value = "/listAward.do")	
	public ModelAndView listAward(KmsAdminSearchCondition searchCondition) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/listAward");
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		SearchResult<AdminWinner> searchResult = adminWinnerManageService.getWinnerAwardList(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode());		
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));

		return modelAndView;
	}
	
	/**
	 * 우수지식인 일괄삭제 처리 비동기 컨트롤 메서드
	 * 
	 * @param teamCodes 삭제대상 부서코드
	 */
	@RequestMapping(value = "/deleteWinner.do")
	public @ResponseBody String deleteWinner(@RequestParam(value="itemSeqs") String itemSeqs) {

		try {			
			adminWinnerManageService.deleteWinner(itemSeqs);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	@RequestMapping(value = "/deleteWinnerPeople.do")
	public @ResponseBody String deleteWinnerPeople(@RequestParam(value="itemSeqs") String itemSeqs) {

		try {			
			adminWinnerManageService.deleteWinnerPeople(itemSeqs);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	@RequestMapping(value = "/deleteWinnerTeam.do")
	public @ResponseBody String deleteWinnerTeam(@RequestParam(value="itemSeqs") String itemSeqs) {

		try {			
			adminWinnerManageService.deleteWinnerTeam(itemSeqs);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	@RequestMapping(value = "/deleteWinnerAward.do")
	public @ResponseBody String deleteWinnerAward(@RequestParam(value="itemSeqs") String itemSeqs) {

		try {			
			adminWinnerManageService.deleteWinnerAward(itemSeqs);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	/**
	 * 우수지식인 게시형태 처리 비동기 컨트롤 메서드
	 * 
	 * @param isMonth 게시형태 (0:달, 1:분기, 2:연간)
	 */
	@RequestMapping(value = "/displayWinner.do")
	public @ResponseBody String displayWinner(@RequestParam(value="isMonthDisplay") String isMonth) {

		try {			
			adminWinnerManageService.displayWinner(isMonth);

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}	
	
	
	/**
	 * 우수지식인 목록 엑셀다운로드
	 * 
	 */
	@RequestMapping(value = "/downloadExcelWinners.do")
	public void downloadExcelWinners( KmsAdminSearchCondition searchCondition, HttpServletResponse response) {
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<AdminWinner> allWinnerList = adminWinnerManageService.getAllWinnerList(searchCondition);
		
		String fileName = messageSource.getMessage("message.collpack.kms.admin.winner.message.excel.fileName", null, Locale.getDefault())
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		
		if( allWinnerList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();			
			
			for (AdminWinner adminWinner : allWinnerList) {
				excelDownloadList.add(adminWinner);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();

			titleMap.put("rowNum", getMessage("itemSeqId"));
			titleMap.put("winYear", getMessage("winYear"));
			titleMap.put("winGb", getMessage("winGb"));
			titleMap.put("isMonthValue", getMessage("isMonth"));
			titleMap.put("sort", getMessage("sort"));
			titleMap.put("userName", getMessage("name"));
			titleMap.put("jobTitleName", getMessage("jobTitle"));
			titleMap.put("winnerId", getMessage("id"));
			titleMap.put("workPlaceName", getMessage("workPlaceName"));
			titleMap.put("teamName", getMessage("teamName"));
			titleMap.put("regCnt", getMessage("regCnt"));
			titleMap.put("mark", getMessage("mark"));
			titleMap.put("conversionMark", getMessage("conversionMark"));
			titleMap.put("registDate", getMessage("registDate"));

			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response);
		}
	}	
	
	@RequestMapping(value = "/downloadExcelWinnerPeople.do")
	public void downloadExcelWinnerPeople( KmsAdminSearchCondition searchCondition, HttpServletResponse response) {
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<AdminWinner> allWinnerList = adminWinnerManageService.getAllWinnerPeopleList(searchCondition);
		
		String fileName = messageSource.getMessage("message.collpack.kms.admin.winner.message.excel.fileName", null, Locale.getDefault())
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		
		if( allWinnerList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();			
			
			for (AdminWinner adminWinner : allWinnerList) {
				excelDownloadList.add(adminWinner);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();

			titleMap.put("rowNum", getMessage("itemSeqId"));
			titleMap.put("winYear", getMessage("winYear"));
			titleMap.put("winGb", getMessage("winGb"));
			titleMap.put("isMonthValue", getMessage("isMonth"));
			titleMap.put("sort", getMessage("sort"));
			titleMap.put("userName", getMessage("name"));
			titleMap.put("jobTitleName", getMessage("jobTitle"));
			titleMap.put("winnerId", getMessage("id"));
			titleMap.put("workPlaceName", getMessage("workPlaceName"));
			titleMap.put("teamName", getMessage("teamName"));
			titleMap.put("registDate", getMessage("registDate"));

			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response);
		}
	}	
	
	
	@RequestMapping(value = "/downloadExcelWinnerTeam.do")
	public void downloadExcelWinnerTeam( KmsAdminSearchCondition searchCondition, HttpServletResponse response) {
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<AdminWinner> allWinnerList = adminWinnerManageService.getAllWinnerTeamList(searchCondition);
		
		String fileName = messageSource.getMessage("message.collpack.kms.admin.winner.message.excel.fileName", null, Locale.getDefault())
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		
		if( allWinnerList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();			
			
			for (AdminWinner adminWinner : allWinnerList) {
				excelDownloadList.add(adminWinner);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();

			titleMap.put("rowNum", getMessage("itemSeqId"));
			titleMap.put("winYear", getMessage("winYear"));
			titleMap.put("winGb", getMessage("winGb"));
			titleMap.put("isMonthValue", getMessage("isMonth"));
			titleMap.put("sort", getMessage("sort"));
			titleMap.put("winnerId", getMessage("id"));
			titleMap.put("teamName", getMessage("teamName"));
			titleMap.put("registDate", getMessage("registDate"));

			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response);
		}
	}	
	
	@RequestMapping(value = "/downloadExcelWinnerAward.do")
	public void downloadExcelWinnerAward( KmsAdminSearchCondition searchCondition, HttpServletResponse response) {
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<AdminWinner> allWinnerList = adminWinnerManageService.getAllWinnerAwardList(searchCondition);
		
		String fileName = messageSource.getMessage("message.collpack.kms.admin.winner.message.excel.fileName", null, Locale.getDefault())
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		
		if( allWinnerList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();			
			
			for (AdminWinner adminWinner : allWinnerList) {
				excelDownloadList.add(adminWinner);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();

			titleMap.put("rowNum", getMessage("itemSeqId"));
			titleMap.put("winYear", getMessage("winYear"));
			titleMap.put("winGb", getMessage("winGb"));
			titleMap.put("isMonthValue", getMessage("isMonth"));
			titleMap.put("sort", getMessage("sort"));
			titleMap.put("winnerId", getMessage("id"));
			titleMap.put("winnerName", "포상품");
			titleMap.put("registDate", getMessage("registDate"));

			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response);
		}
	}	
	
	private String getMessage(String key){
		String msg =  messageSource.getMessage("message.collpack.kms.admin.winner.list."+ key, null, Locale.getDefault());		
		return msg;
	}
	
	
	
	
	/**
	 * 우수지식인 등록UI 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/registerWinners.do")	
	public ModelAndView registerWinners(AdminWinner adminWinner) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		String isMonth = adminWinnerManageService.getDisplay();
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/registerWinners");
		modelAndView.addObject("adminWinner", adminWinner).addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM")).addObject("isMonthDisplay", isMonth);
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/registerWinnerPeople.do")	
	public ModelAndView registerWinnerPeople(AdminWinner adminWinner) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		String isMonth = adminWinnerManageService.getDisplay();
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/registerWinnerPeople");
		modelAndView.addObject("adminWinner", adminWinner).addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM")).addObject("isMonthDisplay", isMonth);
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/registerWinnerTeam.do")	
	public ModelAndView registerWinnerTeam(AdminWinner adminWinner) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		String isMonth = adminWinnerManageService.getDisplay();
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/registerWinnerTeam");
		modelAndView.addObject("adminWinner", adminWinner).addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM")).addObject("isMonthDisplay", isMonth);
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/registerWinnerAward.do")	
	public ModelAndView registerWinnerAward(AdminWinner adminWinner) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		String isMonth = adminWinnerManageService.getDisplay();
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/registerWinnerAward");
		modelAndView.addObject("adminWinner", adminWinner).addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM")).addObject("isMonthDisplay", isMonth);
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/periodManage.do")	
	public ModelAndView periodManage(AdminWinner adminWinner) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		String isMonth = adminWinnerManageService.getDisplay();
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/periodManage");
		modelAndView.addObject("adminWinner", adminWinner).addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM")).addObject("isMonthDisplay", isMonth);
		
		
		return modelAndView;
	}
	
	
	/**
	 * 우수지식인 DB등록 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/saveWinners.do")	
	public ModelAndView saveWinners(AdminWinner adminWinner, BindingResult bindResult) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		boolean isSuccess = true;
		
		if(bindResult.hasErrors()){
			isSuccess = false;
		}	
		
		
		if(isSuccess){
			try {
				adminWinnerManageService.saveWinners(adminWinner);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}
		
		ModelAndView modelAndView = null;
		
		if(isSuccess){
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/listWinners.do");
		}else{
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/registerWinners.do?error=Y").addObject("adminWinner", adminWinner);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveWinnerPeople.do")	
	public ModelAndView saveWinnerPeople(AdminWinner adminWinner, BindingResult bindResult) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		boolean isSuccess = true;
		
		if(bindResult.hasErrors()){
			isSuccess = false;
		}	
		
		
		if(isSuccess){
			try {
				adminWinnerManageService.saveWinnerPeople(adminWinner);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}
		
		ModelAndView modelAndView = null;
		
		if(isSuccess){
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/listHandsomePeople.do");
		}else{
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/registerWinnerPeople.do?error=Y").addObject("adminWinner", adminWinner);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveWinnerTeam.do")	
	public ModelAndView saveWinnerTeam(AdminWinner adminWinner, BindingResult bindResult,String editorAttach, HttpServletRequest request) {
		 		
		User user = (User) getRequestAttribute("ikep.user");
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		boolean isSuccess = true;
		
		if(bindResult.hasErrors()){
			isSuccess = false;
		}	
		
		
		if(isSuccess){
			try {
				MultipartRequest multipartRequest = (MultipartRequest) request;
				List<MultipartFile> fileList = multipartRequest.getFiles("file");
				adminWinnerManageService.saveWinnerTeam(adminWinner,fileList, editorAttach, user);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}
		
		ModelAndView modelAndView = null;
		
		if(isSuccess){
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/listHandsomeTeam.do");
		}else{
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/registerWinnerTeam.do?error=Y").addObject("adminWinner", adminWinner);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveWinnerAward.do")	
	public ModelAndView saveWinnerAward(AdminWinner adminWinner, BindingResult bindResult,String editorAttach, HttpServletRequest request) {
		 		
		User user = (User) getRequestAttribute("ikep.user");
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		boolean isSuccess = true;
		
		if(bindResult.hasErrors()){
			isSuccess = false;
		}	
		
		
		if(isSuccess){
			try {
				MultipartRequest multipartRequest = (MultipartRequest) request;
				List<MultipartFile> fileList = multipartRequest.getFiles("file");
				adminWinnerManageService.saveWinnerAward(adminWinner,fileList, editorAttach, user);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}
		
		ModelAndView modelAndView = null;
		
		if(isSuccess){
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/listAward.do");
		}else{
			modelAndView = new ModelAndView("redirect:/collpack/kms/admin/winner/registerWinnerAward.do?error=Y").addObject("adminWinner", adminWinner);
		}
		return modelAndView;
	}
	
	
	/**
	 * 우수지식인 조회/수정 팝업 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/getWinnerPopup.do")	
	public ModelAndView getWinnerPopup(@RequestParam("itemSeq") String itemSeq) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/getWinnerPopup");
		AdminWinner adminWinner = adminWinnerManageService.read(itemSeq);
		modelAndView.addObject("adminWinner", adminWinner);
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getWinnerPeoplePopup.do")	
	public ModelAndView getWinnerPeoplePopup(@RequestParam("itemSeq") String itemSeq) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/getWinnerPeoplePopup");
		AdminWinner adminWinner = adminWinnerManageService.readWinnerPeople(itemSeq);
		modelAndView.addObject("adminWinner", adminWinner);
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getWinnerTeamPopup.do")	
	public ModelAndView getWinnerTeamPopup(@RequestParam("itemSeq") String itemSeq) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/getWinnerTeamPopup");
		AdminWinner adminWinner = adminWinnerManageService.readWinnerTeam(itemSeq);
		modelAndView.addObject("adminWinner", adminWinner);
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getWinnerAwardPopup.do")	
	public ModelAndView getWinnerAwardPopup(@RequestParam("itemSeq") String itemSeq) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/getWinnerAwardPopup");
		AdminWinner adminWinner = adminWinnerManageService.readWinnerAward(itemSeq);
		modelAndView.addObject("adminWinner", adminWinner);
		modelAndView.addObject("nowYear", DateUtil.getToday("yyyy"));
		
		return modelAndView;
	}
	
			
	
	/**
	 * 우수지식인 수정 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/updateWinnerPopup.do")	
	public String updateWinnerPopup(AdminWinner adminWinner) {		
		
		adminWinnerManageService.update(adminWinner);		
		return "redirect:/collpack/kms/admin/winner/getWinnerPopup.do?itemSeq="+ adminWinner.getItemSeq() + "&gubun=Y";
	}
	
	@RequestMapping(value = "/updateWinnerPeoplePopup.do")	
	public String updateWinnerPeoplePopup(AdminWinner adminWinner) {		
		
		adminWinnerManageService.updateWinnerPeople(adminWinner);		
		return "redirect:/collpack/kms/admin/winner/getWinnerPeoplePopup.do?itemSeq="+ adminWinner.getItemSeq() + "&gubun=Y";
	}
	
	@RequestMapping(value = "/updateWinnerTeamPopup.do")	
	public String updateWinnerTeamPopup(AdminWinner adminWinner,String editorAttach, HttpServletRequest request) {		
		User user = (User) getRequestAttribute("ikep.user");
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		List<MultipartFile> fileList = multipartRequest.getFiles("file");
		
		adminWinnerManageService.updateWinnerTeam(adminWinner,fileList, editorAttach, user);		
		return "redirect:/collpack/kms/admin/winner/getWinnerTeamPopup.do?itemSeq="+ adminWinner.getItemSeq() + "&gubun=Y";
	}
	
	@RequestMapping(value = "/updateWinnerAwardPopup.do")	
	public String updateWinnerAwardPopup(AdminWinner adminWinner,String editorAttach, HttpServletRequest request) {		
		User user = (User) getRequestAttribute("ikep.user");
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		List<MultipartFile> fileList = multipartRequest.getFiles("file");
		
		adminWinnerManageService.updateWinnerAward(adminWinner,fileList, editorAttach, user);		
		return "redirect:/collpack/kms/admin/winner/getWinnerAwardPopup.do?itemSeq="+ adminWinner.getItemSeq() + "&gubun=Y";
	}
	
	
	/**
	 * 평가기준관리 조회 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/getAssessStandard.do")	
	public ModelAndView getAssessStandard() {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/admin/winner/getAssessStandard");
		Map<String, String> resultMap = (Map<String, String>)adminWinnerManageService.getAssessStandard();
		modelAndView.addObject("assess", resultMap);
		return modelAndView;
	}
	
	
	
	/**
	 * 평가기준관리 변경 
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/saveAssessStandard.do")	
	public String saveAssessStandard(@RequestParam Map<Integer, Integer> paramMap) {
		 		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		adminWinnerManageService.saveAssessStandard(paramMap);
		
		return "redirect:/collpack/kms/admin/winner/getAssessStandard.do?gubun=Y"; 
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
