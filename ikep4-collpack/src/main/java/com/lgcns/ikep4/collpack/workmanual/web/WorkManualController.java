/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ReadGroup;
import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.collpack.workmanual.search.ApprovalSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalLineService;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalService;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalUserService;
import com.lgcns.ikep4.collpack.workmanual.service.LineReplyService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualService;
import com.lgcns.ikep4.collpack.workmanual.service.ManualVersionService;
import com.lgcns.ikep4.collpack.workmanual.service.ReadGroupService;
import com.lgcns.ikep4.collpack.workmanual.service.ReadUserService;
import com.lgcns.ikep4.collpack.workmanual.service.WriteUserService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * Model Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: WorkManualController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/workmanual")
@SessionAttributes("workmanual")
public class WorkManualController extends BaseController {
	@Autowired
    ACLService aclService;
	@Autowired
	private UserService userService; 

	@Autowired
	private ManualCategoryService manualCategoryService;
	@Autowired
	private ManualService manualService;
	@Autowired
	private WriteUserService writeUserService;
	@Autowired
	private ApprovalUserService approvalUserService;
	@Autowired
	private ReadUserService readUserService;
	@Autowired
	private ReadGroupService readGroupService;
	@Autowired
	private ManualVersionService manualVersionService;
	@Autowired
	private LineReplyService lineReplyService;
	@Autowired
	private ApprovalLineService approvalLineService;
	@Autowired
	private ApprovalService approvalService;

	/**
	 * 관리자여부 - 메뉴용
	 * @param userId
	 * @return
	 */
	private String isAdminYn(String userId) {
		String adminYn = "N";
		if(aclService.isSystemAdmin("WorkManual", userId)) {
			adminYn = "Y";
		}
		return adminYn;
	}	
	/**
	 * 버젼이 제정,개정인지 판단
	 * @param version
	 * @return
	 */
	private boolean isReleaseVersion(float version) {
		float post = version - (int)version;
		return (post == (float)0);
	}
	
	/**
	 * 업무매뉴얼 메인 조회
	 * @return
	 */
	@RequestMapping(value = "/listWorkManualMainView")
	public ModelAndView listWorkManualMainView() {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listWorkManualMainView");

		//레프트 메뉴
		mav.addObject("categoryTreeJson", manualCategoryService.listTopCategory(user));
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		mav.addObject("writeUserYn", writeUserService.writeUserYn(user.getUserId()));
		//mav.addObject("approvalUserYn", approvalUserService.approvalUserYn(user.getUserId()));
		
		return mav;
	}

	/**
	 * 하위 카테고리 조회
	 * @param categoryParentId
	 * @return
	 */
	@RequestMapping(value = "/listChildCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildCategory(@RequestParam("categoryParentId") String categoryParentId) {
    	try {
    		return manualCategoryService.listChildCategory(categoryParentId);
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("listChildMenuCategorys", ex);
    	}
	}	
	
	/**
	 * 업무매뉴얼 조회
	 * @return
	 */
	@RequestMapping(value = "/listWorkManualView")
	public ModelAndView listWorkManualView() {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listWorkManualView");
		
		//조회
		List<Manual> manualList = manualService.listManual(user.getPortalId(), 5);
		mav.addObject("manualList", manualList);
		Integer maxRowNum = manualService.countManual(user.getPortalId());
		mav.addObject("maxRowNum", maxRowNum);
		mav.addObject("endRowNum", 5);

		//인기태그
		mav.addObject("popularTagList", manualService.listPopularTag(user.getPortalId()));
		
		//레프트 메뉴
		//getLeftMenuData(mav, user);
		
		return mav;
	}
	
	/**
	 * 업무매뉴얼 메인 조회 - AJAX
	 * @param endRowNum
	 * @return
	 */
	@RequestMapping(value = "/addManualPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView addManualPage(@RequestParam(value="endRowNum") Integer endRowNum) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/collpack/workmanual/addManualPage");
		
		//조회
		endRowNum += 5;
		List<Manual> manualList = manualService.listManual(user.getPortalId(), endRowNum);
		mav.addObject("manualList", manualList);
		Integer maxRowNum = manualService.countManual(user.getPortalId());
		mav.addObject("maxRowNum", maxRowNum);
		mav.addObject("endRowNum", endRowNum);
		
		return mav;
	}

	/**
	 * 업무 매뉴얼 조회
	 * @param manualId
	 * @param pathStep
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readManualView")
	public ModelAndView readManualView(@RequestParam("manualId") String manualId, @RequestParam("pathStep") String pathStep) throws JsonGenerationException, JsonMappingException, IOException {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/readManualView");
		
		//매뉴얼 조회
		Manual manual = manualService.readManual(manualId, user.getPortalId(), user.getUserId());
		mav.addObject("manual", manual);
		
		//매뉴얼 버젼 조회
		ManualVersion manualVersion = manualVersionService.getManualVersionBymanualId(manualId, user.getPortalId());
		mav.addObject("manualVersion", manualVersion);
		//파일 목록을 JSON으로 변환한다.
		if(manualVersion.getFileDataList()!= null ) {
			ObjectMapper mapper = new ObjectMapper();
			mav.addObject("fileDataListJson", mapper.writeValueAsString(manualVersion.getFileDataList()));
		}
		
		//버젼이 제정,개정인지 판단
		mav.addObject("isReleaseVersion", isReleaseVersion(manualVersion.getVersion()));
		
		//상신 중인 갯수
		int countSubmitting = manualVersionService.countSubmittingManualVersion(manualId, user.getPortalId());
		mav.addObject("countSubmitting", countSubmitting);
		
		//카테고리정보 조회
		ManualCategory manualCategory = manualCategoryService.getManualCategory(manual.getCategoryId(), user.getPortalId());
		mav.addObject("manualCategory", manualCategory);
		
		//결재자정보
		List<ApprovalLine> approvalLineList = approvalLineService.listApprovalLineByManualId(manual.getCategoryId(), user.getPortalId());
		mav.addObject("approvalLineList", approvalLineList);

		//담당자 여부
		WriteUser writeUser = new WriteUser();
		writeUser.setWriteUserId(user.getUserId());
		writeUser.setCategoryId(manual.getCategoryId());
		String writeAuthorityYn = "N";
		if(writeUserService.exists(writeUser)) {
			writeAuthorityYn = "Y";
		}
		mav.addObject("writeAuthorityYn", writeAuthorityYn);
		
		//문서 결재자 정보
		List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(manual.getCategoryId());
		mav.addObject("approvalUserList", approvalUserList);
		
		//경로정보
		mav.addObject("pathStep", pathStep);
		
		return mav;
	}

	/**
	 * 업무 매뉴얼 댓글 조회 - AJAX
	 * @param manualSearchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/addLinereplyPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView addLinereplyPage(ManualSearchCondition manualSearchCondition, BindingResult result, SessionStatus status) {
		ModelAndView mav = new ModelAndView("/collpack/workmanual/addLinereplyPage");

		//댓글 정보  조회                                                                              
		SearchResult<LineReply> searchResult = lineReplyService.listLineReply(manualSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		
		return mav;
	}

	/**
	 * 업무 매뉴얼 댓글 저장 - AJAX
	 * @param lineReply
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/createLinereply")
	@ResponseStatus(HttpStatus.OK)
	public void createLinereply(LineReply lineReply, BindingResult result, SessionStatus status) {
		User user = (User)getRequestAttribute("ikep.user");  
		
		ModelBeanUtil.bindRegisterInfo(lineReply, user.getUserId(), user.getUserName());
		lineReplyService.createLineReply(lineReply, user.getPortalId());
	}

	/**
	 * 업무 매뉴얼 댓글의 답글 저장 - AJAX
	 * @param lineReply
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/createReplyLinereply")
	@ResponseStatus(HttpStatus.OK)
	public void createReplyLinereply(LineReply lineReply, BindingResult result, SessionStatus status) {
		User user = (User)getRequestAttribute("ikep.user");  
			
		ModelBeanUtil.bindRegisterInfo(lineReply, user.getUserId(), user.getUserName());
		lineReplyService.createReplyLineReply(lineReply, user.getPortalId());
	}

	/**
	 * 업무 매뉴얼 댓글/답글 수정 - AJAX
	 * @param lineReply
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/updateLinereply")
	@ResponseStatus(HttpStatus.OK)
	public void updateLinereply(LineReply lineReply, BindingResult result, SessionStatus status) {
		User user = (User)getRequestAttribute("ikep.user"); 
		
		LineReply readLineReply = lineReplyService.read(lineReply.getLinereplyId());
		readLineReply.setLinereplyContents(lineReply.getLinereplyContents());
		ModelBeanUtil.bindRegisterInfo(readLineReply, user.getUserId(), user.getUserName());
	
		lineReplyService.update(readLineReply);
	}
	
	/**
	 * 작성자 모드로 글 삭제 - AJAX
	 * @param manualId
	 * @param linereplyId
	 */
	@RequestMapping(value = "/deleteLinereplyByUser")
	@ResponseStatus(HttpStatus.OK)
	public void deleteLinereplyByUser(@RequestParam("manualId") String manualId, @RequestParam("linereplyId") String linereplyId) {
		User user = (User)getRequestAttribute("ikep.user"); 
		
		lineReplyService.deleteLinereplyByUser(manualId, linereplyId, user.getUserId(), user.getUserName(), user.getPortalId());
	}

	/**
	 * 관리자 모드로 글 삭제 - AJAX
	 * @param manualId
	 * @param linereplyId
	 */
	@RequestMapping(value = "/deleteLinereplyByAdmin")
	@ResponseStatus(HttpStatus.OK)
	public void deleteLinereplyByAdmin(@RequestParam("manualId") String manualId, @RequestParam("linereplyId") String linereplyId) {
		User user = (User)getRequestAttribute("ikep.user"); 
		
		lineReplyService.deleteLinereplyByAdmin(manualId, linereplyId, user.getPortalId()); 
	}

	/**
	 * 결재함
	 * @param approvalSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/listApprovalView")
	public ModelAndView listApprovalView(ApprovalSearchCondition approvalSearchCondition) {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listApprovalView");
		
		//결재  조회         
		approvalSearchCondition.setUserId(user.getUserId());
		SearchResult<Approval> approvalResult = approvalService.listMyApproval(approvalSearchCondition);
		mav.addObject("searchResult", approvalResult);
		mav.addObject("approvalSearchCondition", approvalResult.getSearchCondition());
		
		return mav;
	}
	
	/**
	 * 결재 상세 조회
	 * @param approvalId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readApprovalView")
	public ModelAndView readApprovalView(String approvalId) throws JsonGenerationException, JsonMappingException, IOException {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/readApprovalView");
		
		//결재정보
		Approval approval = approvalService.read(approvalId);
		mav.addObject("approval", approval);
		
		//버젼정보
		ManualVersion manualVersion = manualVersionService.readManualVersion(approval.getVersionId(), user.getPortalId());
		mav.addObject("manualVersion", manualVersion);
		//파일 목록을 JSON으로 변환한다.
		if(manualVersion.getFileDataList()!= null ) {
			ObjectMapper mapper = new ObjectMapper();
			mav.addObject("fileDataListJson", mapper.writeValueAsString(manualVersion.getFileDataList()));
		}
		
		//결재자정보
		List<ApprovalLine> approvalLineList = approvalLineService.listApprovalLine(approval.getApprovalId());
		mav.addObject("approvalLineList", approvalLineList);
		for(int i=0; i<approvalLineList.size(); i++) {
			if(user.getUserId().equals(approvalLineList.get(i).getApprovalUserId())) {
				mav.addObject("approvalResult", approvalLineList.get(i).getApprovalResult());
			}
		}
		
		return mav;
	}

	/**
	 * 결재(승인/반려)
	 * @param approvalLine
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateApprovalLine")
	public ModelAndView updateApprovalLine(ApprovalLine approvalLine, HttpServletRequest req) {
		User user = (User) getRequestAttribute("ikep.user"); 
		
		approvalLineService.updateApprovalLine(approvalLine, user);
		
		//메일 전송
		if(approvalLine.getApprovalResult().equals("C")) { //승인
			approvalService.sendMail("C", approvalLine.getApprovalId(), user, HttpUtil.getWebAppUrl(req));
		} else {//반려
			approvalService.sendMail("D", approvalLine.getApprovalId(), user, HttpUtil.getWebAppUrl(req));
		}
		
		return new ModelAndView("redirect:/collpack/workmanual/listApprovalView.do");
	}

	/**
	 * 결재자 조회 - AJAX
	 * @param approvalId
	 * @return
	 */
	@RequestMapping(value = "/addApprovalLinePage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView addApprovalLinePage(@RequestParam("approvalId") String approvalId) {
		ModelAndView mav = new ModelAndView("/collpack/workmanual/addApprovalLinePage");
		User user = (User) getRequestAttribute("ikep.user"); 
		
		//결재자정보
		List<ApprovalLine> approvalLineList = approvalLineService.listApprovalLine(approvalId);
		mav.addObject("approvalLineList", approvalLineList);
		for(int i=0; i<approvalLineList.size(); i++) {
			if(user.getUserId().equals(approvalLineList.get(i).getApprovalUserId())) {
				mav.addObject("approvalResult", approvalLineList.get(i).getApprovalResult());
			}
		}
		
		return mav;
	}
	
	/**
	 * 결재자 추가 - AJAX
	 * @param approvalLine
	 */
	@RequestMapping(value = "/createApprovalUser")
	@ResponseStatus(HttpStatus.OK)
	public void createApprovalUser(ApprovalLine approvalLine) {
		approvalLineService.createApprovalUser(approvalLine);
	}
	
	/**
	 * 결재자 삭제 - AJAX
	 * @param approvalLinePk
	 */
	@RequestMapping(value = "/deleteApprovalUser")
	@ResponseStatus(HttpStatus.OK)
	public void deleteApprovalUser(ApprovalLinePk approvalLinePk) {
		approvalLineService.deleteApprovalUser(approvalLinePk);
	}

	/**
	 * 개인 업무 매뉴얼 조회
	 * @param manualSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/listMyManualView")
	public ModelAndView listMyManualView(ManualSearchCondition manualSearchCondition) {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listMyManualView");
		
		if(manualSearchCondition == null) {
			manualSearchCondition = new ManualSearchCondition();
		}
		
		//리스트 조회
		manualSearchCondition.setRegisterId(user.getUserId());
		manualSearchCondition.setPortalId(user.getPortalId());
		SearchResult<ManualVersion> searchResult = manualVersionService.listMyManualVersion(manualSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("manualSearchCondition", searchResult.getSearchCondition());
		
		return mav;
	}	

	/**
	 * 업무매뉴얼 조회-과거 버젼용
	 * @param versionId
	 * @param pathStep
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readManualVersionView")
	public ModelAndView readManualVersionView(@RequestParam("versionId") String versionId, @RequestParam("pathStep") String pathStep) throws JsonGenerationException, JsonMappingException, IOException {		
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/collpack/workmanual/readManualVersionView");

		//매뉴얼 버젼 정보
		ManualVersion manualVersion = manualVersionService.readManualVersion(versionId, user.getPortalId());
		mav.addObject("manualVersion", manualVersion);
		//파일 목록을 JSON으로 변환한다.
		if(manualVersion.getFileDataList()!= null ) {
			ObjectMapper mapper = new ObjectMapper();
			mav.addObject("fileDataListJson", mapper.writeValueAsString(manualVersion.getFileDataList()));
		}
		
		//버젼이 제정,개정인지 판단
		mav.addObject("isReleaseVersion", isReleaseVersion(manualVersion.getVersion()));
		
		//상신 중인 갯수
		int countSubmitting = manualVersionService.countSubmittingManualVersion(manualVersion.getManualId(), user.getPortalId());
		mav.addObject("countSubmitting", countSubmitting);
		
		//등록자 정보
		User registerUser = userService.read(manualVersion.getRegisterId());
		mav.addObject("registerUser", registerUser);
	
		//매뉴얼 결재자정보
		List<ApprovalLine> approvalLineList = approvalLineService.listApprovalLineByVersionId(manualVersion.getVersionId(), user.getPortalId());
		mav.addObject("approvalLineList", approvalLineList);

		//매뉴얼 정보
		Manual manual = manualService.readManual(manualVersion.getManualId(), user.getPortalId(), user.getUserId());
		mav.addObject("manual", manual);
		
		//표준 버젼
		float standardVersion = manual.getVersion();
		mav.addObject("standardVersion", (int) standardVersion);
		
		//담당자 여부
		WriteUser writeUser = new WriteUser();
		writeUser.setWriteUserId(user.getUserId());
		writeUser.setCategoryId(manual.getCategoryId());
		String writeAuthorityYn = "N";
		if(writeUserService.exists(writeUser)) {
			writeAuthorityYn = "Y";
		}
		mav.addObject("writeAuthorityYn", writeAuthorityYn);
		
		//문서 결재자 정보
		List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(manual.getCategoryId());
		mav.addObject("approvalUserList", approvalUserList);

		//경로정보
		mav.addObject("pathStep", pathStep);
	
		return mav;
	}		
	
	/**
	 * 매뉴얼 버젼 삭제
	 * @param manualId
	 * @param versionId
	 * @param pathStep
	 * @return
	 */
	@RequestMapping(value = "/deleteManualVersion")
	public ModelAndView deleteManualVersion(@RequestParam("manualId") String manualId, @RequestParam("versionId") String versionId, @RequestParam("pathStep") String pathStep) {
		User user = (User) getRequestAttribute("ikep.user"); 

		manualVersionService.deleteManualVersion(versionId, user.getPortalId(), user.getUserId(), user.getUserName());
		
		if(pathStep.equals("C")) {
			return new ModelAndView("redirect:/collpack/workmanual/listMyManualView.do");
		} else {
			return new ModelAndView("redirect:/collpack/workmanual/listHistoryView.do")
				.addObject("manualId", manualId)
				.addObject("pathStep", pathStep);
		}
	}	
	
	/**
	 * 상신
	 * @param approval
	 * @param pathStep
	 * @param pathStep2
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/createApproval")
	public ModelAndView createApproval(Approval approval, @RequestParam("pathStep") String pathStep, @RequestParam("pathStep2") String pathStep2, HttpServletRequest req) {
		User user = (User) getRequestAttribute("ikep.user"); 
		
		ModelBeanUtil.bindRegisterInfo(approval, user.getUserId(), user.getUserName());
		String approvalId = manualVersionService.createApproval(approval, user);
		
		//메일 전송
		approvalService.sendMail("A", approvalId, user, HttpUtil.getWebAppUrl(req));
		
		if(pathStep2.equals("A")) {
			return new ModelAndView("redirect:/collpack/workmanual/readManualView.do")
				.addObject("manualId", approval.getManualId())
				.addObject("pathStep", pathStep);
		} else {
			return new ModelAndView("redirect:/collpack/workmanual/readManualVersionView.do")
				.addObject("versionId", approval.getVersionId())
				.addObject("pathStep", pathStep);
		}
	}
	
	/**
	 * 상신 취소
	 * @param approval
	 * @param pathStep
	 * @param pathStep2
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cancelApproval")
	public ModelAndView cancelApproval(Approval approval, @RequestParam("pathStep") String pathStep, @RequestParam("pathStep2") String pathStep2, HttpServletRequest req) {
		User user = (User) getRequestAttribute("ikep.user"); 
		
		//메일 전송
		approvalService.sendMail("B", approvalService.readSubmittingApproval(approval.getVersionId()).getApprovalId(), user, HttpUtil.getWebAppUrl(req));
		
		manualVersionService.cancelApproval(approval.getVersionId(), user, approval.getManualType());

		if(pathStep2.equals("A")) {
			return new ModelAndView("redirect:/collpack/workmanual/readManualView.do")
				.addObject("manualId", approval.getManualId())
				.addObject("pathStep", pathStep);
		} else if(approval.getManualType().equals("C")) {
			return new ModelAndView("redirect:/collpack/workmanual/listMyManualView.do");
		} else {
			return new ModelAndView("redirect:/collpack/workmanual/listMyManualView.do")
			.addObject("versionId", approval.getVersionId())
			.addObject("pathStep", pathStep);
		}
	}
	
	/**
	 * 메뉴얼 버젼 수정           
	 * @param versionId
	 * @param pathStep
	 * @param pathStep2
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateManualVersionView")
	public ModelAndView updateManualVersionView(@RequestParam("versionId") String versionId, @RequestParam("pathStep") String pathStep, @RequestParam("pathStep2") String pathStep2) throws JsonGenerationException, JsonMappingException, IOException  {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/updateManualVersionView");
		
		//매뉴얼 버젼 정보
		ManualVersion manualVersion = manualVersionService.readManualVersion(versionId, user.getPortalId());
		mav.addObject("manualVersion", manualVersion);
		
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = mapper.writeValueAsString(manualVersion.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);

		//매뉴얼 정보
		Manual manual = manualService.readManual(manualVersion.getManualId(), user.getPortalId(), user.getUserId());
		mav.addObject("manual", manual);

		//경로정보
		mav.addObject("pathStep", pathStep);
		mav.addObject("pathStep2", pathStep2);
		
		return mav;
	}

	/**
	 * 메뉴얼 버젼 수정 저장
	 * @param manualVersion
	 * @param pathStep
	 * @param pathStep2
	 * @return
	 */
	@RequestMapping(value = "/updateManualVersion")
	public ModelAndView updateManualVersion(ManualVersion manualVersion, @RequestParam("pathStep") String pathStep, @RequestParam("pathStep2") String pathStep2) {
		User user = (User) getRequestAttribute("ikep.user"); 
		
		ModelBeanUtil.bindRegisterInfo(manualVersion, user.getUserId(), user.getUserName());
		manualVersion.setPortalId(user.getPortalId());
		manualVersionService.updateManualVersion(manualVersion, user);
		
		if(pathStep2.equals("A")) {
			return new ModelAndView("redirect:/collpack/workmanual/readManualView.do")
				.addObject("manualId", manualVersion.getManualId())
				.addObject("pathStep", pathStep);
		} else {
			return new ModelAndView("redirect:/collpack/workmanual/readManualVersionView.do")
				.addObject("versionId", manualVersion.getVersionId())
				.addObject("pathStep", pathStep);
		}
	}
	
	/**
	 * 카테고리별 목록 조회
	 * @param manualSearchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listCategoryManualView")
	public ModelAndView listCategoryManualView(ManualSearchCondition manualSearchCondition, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listCategoryManualView");

		//카테고리정보 조회
		manualSearchCondition.setPortalId(user.getPortalId());
		ManualCategory manualCategory = manualCategoryService.getManualCategory(manualSearchCondition.getCategoryId(), user.getPortalId());
		mav.addObject("manualCategory", manualCategory);
		
		//리스트 조회
		SearchResult<Manual> searchResult = manualService.listCategoryManual(manualSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("manualSearchCondition", searchResult.getSearchCondition());	
		
		//담당자 정보 가져오기
		List<WriteUser> writeUserList= writeUserService.listWriteUser(manualSearchCondition.getCategoryId());
		mav.addObject("writeUserList", writeUserList);

		//등록 버튼 활성화 여부 가져오기
		WriteUser writeUser = new WriteUser();
		writeUser.setCategoryId(manualSearchCondition.getCategoryId());
		writeUser.setWriteUserId(user.getUserId());
		String writeUserYn = "N";
		if(writeUserService.exists(writeUser)) {
			writeUserYn = "Y";
		}
		mav.addObject("writeUserYn", writeUserYn);
		
		//결재자 정보 가져오기
		List<ApprovalUser> approvalUserList= approvalUserService.listApprovalUser(manualSearchCondition.getCategoryId());
		mav.addObject("approvalUserList", approvalUserList);
		
		//상세 조회 권한 가져오기
		String readAuthority = "Y";
		if(manualCategory.getReadPermission() == 0) {
			ReadUser readUser = new ReadUser();
			readUser.setCategoryId(manualSearchCondition.getCategoryId());
			readUser.setReadUserId(user.getUserId());
			if(!readUserService.exists(readUser)) {
				ReadGroup readGroup = new ReadGroup();
				readGroup.setCategoryId(manualSearchCondition.getCategoryId());
				readGroup.setReadGroupId(user.getGroupId());
				if(!readGroupService.exists(readGroup)) {
					readAuthority = "N";
				}
			}		
		}
		mav.addObject("readAuthority", readAuthority);

		status.setComplete();
		
		return mav;
	}

	/**
	 * 매뉴얼 작성
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/createManualView")
	public ModelAndView createManualView(@RequestParam("categoryId") String categoryId) {
		ModelAndView mav = new ModelAndView("/collpack/workmanual/createManualView");	
		mav.addObject("categoryId", categoryId);
		
		return mav;
	}	

	/**
	 * 매뉴얼 저장
	 * @param manualVersion
	 * @param categoryId
	 * @param tags
	 * @return
	 */
	@RequestMapping(value = "/saveNewManual")
	public String saveNewManual(ManualVersion manualVersion, @RequestParam("categoryId") String categoryId, @RequestParam("tags") String tags) {
		User user = (User) getRequestAttribute("ikep.user");

		ModelBeanUtil.bindRegisterInfo(manualVersion, user.getUserId(), user.getUserName());
		manualVersion.setPortalId(user.getPortalId());
		manualVersionService.createManualVersion(manualVersion, categoryId, tags, user);
		
		return "redirect:/collpack/workmanual/listCategoryManualView.do?categoryId=" + categoryId;
	}

	/**
	 * 히스토리 조회
	 * @param manualSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/listHistoryView")
	public ModelAndView listHistoryView(ManualSearchCondition manualSearchCondition) {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listHistoryView");

		//리스트 조회
		manualSearchCondition.setPortalId(user.getPortalId());
		SearchResult<ManualVersion> searchResult = manualVersionService.listManualVersion(manualSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("manualSearchCondition", searchResult.getSearchCondition());		
		
		//매뉴얼 정보
		Manual manual = manualService.readManual(manualSearchCondition.getManualId(), user.getPortalId(), user.getUserId());
		mav.addObject("manual", manual);
		
		//표준 버젼
		float standardVersion = manual.getVersion();
		mav.addObject("standardVersion", (int) standardVersion);
		
		//담당자 여부
		WriteUser writeUser = new WriteUser();
		writeUser.setWriteUserId(user.getUserId());
		writeUser.setCategoryId(manual.getCategoryId());
		String redoAuthorityYn = "N";
		if(writeUserService.exists(writeUser)) {
			redoAuthorityYn = "Y";
		}
		mav.addObject("redoAuthorityYn", redoAuthorityYn);
		
		return mav;
	}
	
	/**
	 * 히스토리 되돌리기
	 * @param manualSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/redoManualVersion")
	public ModelAndView redoManualVersion(ManualSearchCondition manualSearchCondition) {
		User user = (User) getRequestAttribute("ikep.user");
		
		manualSearchCondition.setPortalId(user.getPortalId());
		manualVersionService.createRedoManualVersion(manualSearchCondition, user);
		
		return new ModelAndView("redirect:/collpack/workmanual/listHistoryView.do")
			.addObject("manualId", manualSearchCondition.getManualId())
			.addObject("pathStep", manualSearchCondition.getPathStep());
	}

	/**
	 * 매뉴얼 정보 팝업
	 * @param manualId
	 * @param manualSearchCondition
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readManualPopupView")
	public ModelAndView readManualPopupView(@RequestParam("manualId") String manualId, ManualSearchCondition manualSearchCondition) throws JsonGenerationException, JsonMappingException, IOException {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/readManualPopupView");
		
		//매뉴얼 조회
		Manual manual = manualService.readManual(manualId, user.getPortalId(), user.getUserId());
		mav.addObject("manual", manual);
		
		//매뉴얼 버젼 조회
		ManualVersion manualVersion = manualVersionService.getManualVersionBymanualId(manualId, user.getPortalId());
		mav.addObject("manualVersion", manualVersion);
		//파일 목록을 JSON으로 변환한다.
		if(manualVersion.getFileDataList()!= null ) {
			ObjectMapper mapper = new ObjectMapper();
			mav.addObject("fileDataListJson", mapper.writeValueAsString(manualVersion.getFileDataList()));
		}
		
		//버젼이 제정,개정인지 판단
		mav.addObject("isReleaseVersion", isReleaseVersion(manualVersion.getVersion()));
		
		//상신 중인 갯수
		int countSubmitting = manualVersionService.countSubmittingManualVersion(manualId, user.getPortalId());
		mav.addObject("countSubmitting", countSubmitting);
		
		//카테고리정보 조회
		ManualCategory manualCategory = manualCategoryService.getManualCategory(manual.getCategoryId(), user.getPortalId());
		mav.addObject("manualCategory", manualCategory);
		
		//결재자정보
		List<ApprovalLine> approvalLineList = approvalLineService.listApprovalLineByManualId(manual.getCategoryId(), user.getPortalId());
		mav.addObject("approvalLineList", approvalLineList);

		//담당자 여부
		WriteUser writeUser = new WriteUser();
		writeUser.setWriteUserId(user.getUserId());
		writeUser.setCategoryId(manual.getCategoryId());
		String writeAuthorityYn = "N";
		if(writeUserService.exists(writeUser)) {
			writeAuthorityYn = "Y";
		}
		mav.addObject("writeAuthorityYn", writeAuthorityYn);
		
		//문서 결재자 정보
		List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(manual.getCategoryId());
		mav.addObject("approvalUserList", approvalUserList);
		
		//댓글 정보  조회                                                                              
		SearchResult<LineReply> searchResult = lineReplyService.listLineReply(manualSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		
		return mav;
	}
	
	/**
	 * 카테고리 조회
	 * @return
	 */
	@RequestMapping(value = "/listCategoryView")
	public ModelAndView listCategoryView() {
		User user = (User) getRequestAttribute("ikep.user"); 
		ModelAndView mav = new ModelAndView("/collpack/workmanual/listCategoryView");
		
		mav.addObject("categoryTreeJson", manualCategoryService.listTopCategory(user));
		
		return mav;
	}
	
	/**
	 * 카테고리 상세 조회 - AJAX   R:읽기 전용    C:신규     U:수정
	 * @param mode
	 * @param categoryId
	 * @param upYn
	 * @return
	 */
	@RequestMapping(value = "/categoryPage")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView categoryPage(@RequestParam("mode") String mode, @RequestParam("categoryId") String categoryId, @RequestParam("upYn") String upYn) {
		User user = (User)getRequestAttribute("ikep.user"); 
		ModelAndView mav = null;
		
		if(mode.equals("R")) {
			mav = new ModelAndView("/collpack/workmanual/readCategoryPage");
			
			//카데고리 조회
			ManualCategory manualCategory = manualCategoryService.getManualCategory(categoryId, user.getPortalId());
			mav.addObject("manualCategory", manualCategory);
			
			//문서 결재자 정보
			List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(categoryId);
			mav.addObject("approvalUserList", approvalUserList);
			
			//문서 담당자 정보
			List<WriteUser> writeUserList = writeUserService.listWriteUser(categoryId);
			mav.addObject("writeUserList", writeUserList);
			
			//문서 조회 사용자 정보
			List<ReadUser> readUserList = readUserService.listReadUser(categoryId);
			mav.addObject("readUserList", readUserList);		
			
			//문서  조회 조직 정보
			List<ReadGroup> readGroupList = readGroupService.listReadGroup(categoryId, user.getPortalId());
			mav.addObject("readGroupList", readGroupList);
		} else if(mode.equals("C")) {
			mav = new ModelAndView("/collpack/workmanual/createCategoryPage");
			if(!StringUtils.isEmpty(categoryId)) {
				ManualCategory manualCategory = new ManualCategory();
				manualCategory.setCategoryParentId(categoryId);
				mav.addObject("manualCategory", manualCategory);
				
				if(upYn.equals("Y")) {
					//공개 여부 조회
					mav.addObject("readPermission", manualCategoryService.getManualCategory(categoryId, user.getPortalId()).getReadPermission());
					
					//문서 결재자 정보
					List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(categoryId);
					mav.addObject("approvalUserList", approvalUserList);
					
					//문서 담당자 정보
					List<WriteUser> writeUserList = writeUserService.listWriteUser(categoryId);
					mav.addObject("writeUserList", writeUserList);
					
					//문서 조회 사용자 정보
					List<ReadUser> readUserList = readUserService.listReadUser(categoryId);
					mav.addObject("readUserList", readUserList);		
					
					//문서  조회 조직 정보
					List<ReadGroup> readGroupList = readGroupService.listReadGroup(categoryId, user.getPortalId());
					mav.addObject("readGroupList", readGroupList);
				} else {
					//공개 여부 조회
					mav.addObject("readPermission", 1);
				}
			} else {
				//공개 여부 조회
				mav.addObject("readPermission", 1);
			}
		} else if(mode.equals("U")) {
			mav = new ModelAndView("/collpack/workmanual/createCategoryPage");

			//카데고리 조회
			ManualCategory manualCategory = manualCategoryService.getManualCategory(categoryId, user.getPortalId());
			mav.addObject("manualCategory", manualCategory);
			
			if(upYn.equals("Y")) {
				//공개 여부 조회
				mav.addObject("readPermission", manualCategoryService.getManualCategory(manualCategory.getCategoryParentId(), user.getPortalId()).getReadPermission());

				//문서 결재자 정보
				List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(manualCategory.getCategoryParentId());
				mav.addObject("approvalUserList", approvalUserList);
				
				//문서 담당자 정보
				List<WriteUser> writeUserList = writeUserService.listWriteUser(manualCategory.getCategoryParentId());
				mav.addObject("writeUserList", writeUserList);
				
				//문서 조회 사용자 정보
				List<ReadUser> readUserList = readUserService.listReadUser(manualCategory.getCategoryParentId());
				mav.addObject("readUserList", readUserList);		
				
				//문서  조회 조직 정보
				List<ReadGroup> readGroupList = readGroupService.listReadGroup(manualCategory.getCategoryParentId(), user.getPortalId());
				mav.addObject("readGroupList", readGroupList);
			} else {
				//공개 여부 조회
				mav.addObject("readPermission", manualCategory.getReadPermission());
				
				//문서 결재자 정보
				List<ApprovalUser> approvalUserList = approvalUserService.listApprovalUser(categoryId);
				mav.addObject("approvalUserList", approvalUserList);
				
				//문서 담당자 정보
				List<WriteUser> writeUserList = writeUserService.listWriteUser(categoryId);
				mav.addObject("writeUserList", writeUserList);
				
				//문서 조회 사용자 정보
				List<ReadUser> readUserList = readUserService.listReadUser(categoryId);
				mav.addObject("readUserList", readUserList);		
				
				//문서  조회 조직 정보
				List<ReadGroup> readGroupList = readGroupService.listReadGroup(categoryId, user.getPortalId());
				mav.addObject("readGroupList", readGroupList);
			}
		}

		//etc
		mav.addObject("mode", mode);
		mav.addObject("upYn", upYn);
		
		return mav;
	}
	
	/**
	 * 카테고리 상세 저장 - AJAX
	 * @param manualCategory
	 * @param writeUsers
	 * @param readUsers
	 * @param readGroups
	 * @param approvalUsers
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/saveCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String saveCategory(
			ManualCategory manualCategory,
			@RequestParam(value="writeUsers") String writeUsers,
			@RequestParam(value="readUsers", required=false) String readUsers, 
			@RequestParam(value="readGroups", required=false) String readGroups,
			@RequestParam(value="approvalUsers", required=false) String approvalUsers, 
			@RequestParam(value="mode") String mode) {
    	try {
    		User user = (User)getRequestAttribute("ikep.user");
    		manualCategory.setPortalId(user.getPortalId());
    		if(mode.equals("C")) {//신규
    			ModelBeanUtil.bindRegisterInfo(manualCategory, user.getUserId(), user.getUserName());
    			return manualCategoryService.createManualCategory(manualCategory, readUsers, readGroups, writeUsers, approvalUsers);
    		} else { //수정
    			manualCategory.getCategoryId();
    			manualCategoryService.updateManualCategory(manualCategory, readUsers, readGroups, writeUsers, approvalUsers);
        		return "";
    		}
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("saveCategory", ex);
    	}
	}
	
	/**
	 * 카테고리 상세 삭제 - AJAX
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView deleteCategory(@RequestParam("categoryId") String categoryId) {
		User user = (User)getRequestAttribute("ikep.user"); 
		
		manualCategoryService.deleteManualCategory(categoryId, user.getPortalId());

		return new ModelAndView("redirect:/collpack/workmanual/listCategoryView.do");
	}
	
	/**
	 * 카테고리 이동 - AJAX
	 * @param sourceId
	 * @param targetParentId
	 * @param targetSortOrder
	 */
	@RequestMapping(value = "/moveCategory")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void moveCategory(@RequestParam("sourceId") String sourceId, @RequestParam("targetParentId") String targetParentId, @RequestParam("targetSortOrder") String targetSortOrder) {
    	try {
    		manualCategoryService.moveCategory(sourceId, targetParentId, targetSortOrder);
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("saveCategory", ex);
    	}
	}
}
