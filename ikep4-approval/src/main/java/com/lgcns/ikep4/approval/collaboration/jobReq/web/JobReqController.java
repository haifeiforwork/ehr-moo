/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.jobReq.dao.JobReqDao;
import com.lgcns.ikep4.approval.collaboration.jobReq.service.JobReqService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.tagfree.util.MimeUtil;


/**
 * 
 * @brief  : 법무자료관리
 * @detail : 법무자료관리 메인페이지
 * @Method : jobReq
 * @author : 신정수
 * @since  : 2018.02.06
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/approval/collaboration/jobReq")
public class JobReqController extends BaseController {

	@Autowired
	JobReqService jobReqService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CommonCodeDao commonCodeDao;
	
	@Autowired
	private JobReqDao jobReqDao;
	
    @Autowired
    CustomerService customerService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    CustomerCounselHistoryService counselHistoryService;
    
    @Autowired
	private CollaboCommonService collaboCommonService;
    
	/** The file service. */
	@Autowired
	private FileService fileService;
	
	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}
	
	/**
	 * 계약서 요청 리스트 화면
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/jobReqList.do")
	public ModelAndView apprJobReqList(JobReqListSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString) throws Exception {
		System.out.println("======== 업무 협의 요청서 목록 ========");
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqList");
		try {
			String today = getToday("yyyy.MM.dd");
			
			if(StringUtil.isEmpty(searchCondition.getSearchStartRegDate1()) 
					|| StringUtil.isEmpty(searchCondition.getSearchEndRegDate1())) {
				
				String [] todays = StringUtils.split(today, ".");
				String year = todays[0];
				String month = todays[1];
				
				searchCondition.setSearchStartRegDate1(year+"."+month+"."+"01");
				searchCondition.setSearchEndRegDate1(today);
			}
			
			//화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
		    String tempSearchConditionString = null;
		    if ( StringUtils.isEmpty( searchConditionString ) ) {
		        tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition,
		                "pageIndex", "searchColumn", "sortColumn", "sortType" );
		    } else {
		        ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
		        tempSearchConditionString = searchConditionString;
		    }
		    
		    //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)

		    if ( searchCondition.getPagePerRecord().equals( "" ) ) {
		        searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );

		    }
			
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 관리자 여부 체크
//			if(!this.isSystemAdmin(user)){
//				throw new IKEP4AuthorizedException();
//			}
			
			searchCondition.setLoginEmpNo(user.getEmpNo()); //로그인아이디 세팅
			SearchResult<JobReq> resultList = jobReqService.jobReqList(searchCondition);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("grpCd", "C00008");
			
			List<CommonCode> codeList = commonCodeDao.getCommonCodeList(map);
			
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("jobReqResultList", resultList);
			mav.addObject("codeList", codeList);
			mav.addObject("isSystemAdmin", 		this.isSystemAdmin(user));
			mav.addObject( "searchConditionString", tempSearchConditionString );
		} catch(Exception ex) {
			ex.printStackTrace();       
		}
		return mav;
	}
	
	/**
     * 업무의뢰 신규 등록 화면 불러오기 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/jobReqCreate.do")
    public ModelAndView jobReqCreate() throws Exception{
    	System.out.println("======== 신규 ========");
    	// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqCreate");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, Object> lederInfo = new HashMap<String, Object>();
			
			lederInfo = commonCodeDao.getTeamLeaderInfo(user.getEmpNo());
			mav.addObject("user", 		user);
			mav.addObject("lederInfo", lederInfo);
			mav.addObject("ecmRoll", collaboCommonService.isEcmUser( user)); //ecm 사용자 여부
	    } catch(Exception ex) {
			ex.printStackTrace();
		}
		return mav;
    }
    
    /**
	 * Default 계약서 신규 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqSave(@Valid JobReq jobReq) {
		System.out.println("======== 저장 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String today = getToday("yyyy.MM.dd");
			String [] todays = StringUtils.split(today, ".");
			String year = todays[0];
			String month = todays[1];
			String dateNo = year + "-" + month;
			
			jobReq.setDateNo(dateNo);
			
			jobReqService.jobReqSave(jobReq); //저장
			
			result.put("jobReqMgntNo", jobReq.getJobReqMgntNo());
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 상세화면
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/jobReqDetail.do")
	public ModelAndView jobReqDetail(JobReq jobReq, @RequestParam(value="id")String jobReqMgntNo, @RequestParam(value="processStatus")String processStatus) {
		System.out.println("========  상세정보화면 ========");
	
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqDetail");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 관리자 여부 체크
//			if(!this.isSystemAdmin(user)){
//				throw new IKEP4AuthorizedException();
//			}
			
//			String processStatus = jobReq.getProcessStatus();
			
			
			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("jobReqMgntNo", jobReqMgntNo);
			paramMap.put("processStatus", processStatus);
			
			Map<String, String> resultMap = jobReqDao.getApprLv(paramMap);
			
			paramMap.put("jobReqMgntNo", jobReqMgntNo);
			paramMap.put("empNo", user.getEmpNo());
			paramMap.put("apprLv", resultMap.get("apprLv")); //승인 단계 세팅
			
			Map<String, String> resultDetail = jobReqService.jobReqDetail(paramMap); //상세정보
			
			// 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty((String) resultDetail.get("fileItemId") )) {
				
				fileDataList = this.fileService.getItemFile( (String) resultDetail.get("fileItemId"), jobReq.ITEM_FILE_TYPE);
			}
			jobReq.setFileDataList(fileDataList);
			
			//기안부서 기안자 여부
			String reqDraftYn = "N";
			if(user.getEmpNo().equals((String) resultDetail.get("reqDraftEmpNo"))){
				reqDraftYn = "Y";
			}
			
			//주관부서 기안자 여부
			String rcvDraftYn = "N";
			if(user.getEmpNo().equals((String) resultDetail.get("rcvDraftEmpNo"))){
				rcvDraftYn = "Y";
			} 
			
			//ecm 사용자 여부
			jobReq.setEcmRoll( collaboCommonService.isEcmUser( user));
			
			List<JobReq> resultList = jobReqService.jobReqHistoryList(jobReqMgntNo);
			jobReq.setFileItemId((String)resultDetail.get("fileItemId"));
			
			mav.addObject("jobReq", jobReq);
			mav.addObject("resultDetail", resultDetail);
			mav.addObject("resultList", resultList);
			mav.addObject("apprLv", resultMap.get("apprLv"));
			mav.addObject("saveYn", resultDetail.get("saveYn"));
			mav.addObject("reqYn", resultDetail.get("reqYn"));
			mav.addObject("agreementEmpNoYn", resultDetail.get("agreementEmpNoYn"));
			mav.addObject("empNoYn", resultDetail.get("empNoYn"));
			mav.addObject("reqDraftYn", reqDraftYn);
			mav.addObject("rcvDraftYn", rcvDraftYn);
			mav.addObject("ecmRoll", jobReq.isEcmRoll());
			mav.addObject("userAuthMgntYn", jobReqService.userAuthMgntYn(user.getEmpNo()));
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( jobReq.getFileDataList()));
			
		} catch(Exception ex) {
			ex.getStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * 이벤트후 상세화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqDetailRe.do")
	public ModelAndView jobReqDetailRe(JobReq jobReq) {
		System.out.println("========  리로드 이후 상세정보화면 ========");
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqDetail");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 관리자 여부 체크
//			if(!this.isSystemAdmin(user)){
//				throw new IKEP4AuthorizedException();
//			}
			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("jobReqMgntNo", jobReq.getJobReqMgntNo());
			paramMap.put("empNo", user.getEmpNo());
			paramMap.put("processStatus", jobReq.getProcessStatus());
			Map<String, String> resultMap = jobReqDao.getApprLv(paramMap);
			
			paramMap.put("apprLv", resultMap.get("apprLv")); //승인 단계 세팅
			//
			
			Map<String, String> resultDetail = jobReqService.jobReqDetail(paramMap);
			
			//기안부서 기안자 여부
			String reqDraftYn = "N";
			if(user.getEmpNo().equals((String) resultDetail.get("reqDraftEmpNo"))){
				reqDraftYn = "Y";
			}
			
			//주관부서 기안자 여부
			String rcvDraftYn = "N";
			if(user.getEmpNo().equals((String) resultDetail.get("rcvDraftEmpNo"))){
				rcvDraftYn = "Y";
			} 
			
			//ecm 사용자 여부
			jobReq.setEcmRoll( collaboCommonService.isEcmUser( user));
			
			List<JobReq> resultList = jobReqService.jobReqHistoryList(jobReq.getJobReqMgntNo());
			
			// 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty(resultDetail.get("fileItemId") )) {
				
				fileDataList = this.fileService.getItemFile( resultDetail.get("fileItemId"), jobReq.ITEM_FILE_TYPE);
			}
			jobReq.setFileDataList(fileDataList);
			
			mav.addObject("jobReq", jobReq);
			mav.addObject("resultDetail", resultDetail);
			mav.addObject("resultList", resultList);
			mav.addObject("apprLv", jobReq.getApprLv());
			mav.addObject("saveYn", resultDetail.get("saveYn"));
			mav.addObject("reqYn", resultDetail.get("reqYn"));
			mav.addObject("agreementEmpNoYn", resultDetail.get("agreementEmpNoYn"));
			mav.addObject("empNoYn", resultDetail.get("empNoYn"));
			mav.addObject("reqDraftYn", reqDraftYn);
			mav.addObject("rcvDraftYn", rcvDraftYn);
//			mav.addObject("rcvUseYn", rcvUseYn);
			mav.addObject("ecmRoll", jobReq.isEcmRoll());
			mav.addObject("userAuthMgntYn", jobReqService.userAuthMgntYn(user.getEmpNo()));
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( jobReq.getFileDataList()));
			
			
		} catch(Exception ex) {
			ex.getStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * Default 수정
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqModify.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqModify(@Valid JobReq jobReq) {
		System.out.println("======== 수정 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			jobReqService.jobReqModify(jobReq); //저장
			
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			ex.printStackTrace();
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 삭제
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqDelete.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqDelete(@Valid JobReq jobReq) {
		System.out.println("======== 삭제 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			jobReqService.jobReqDelete((String) jobReq.getJobReqMgntNo()); //저장
			
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			ex.printStackTrace();
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 검토 이력 등록 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqHistoryPopUp.do") 
	public ModelAndView jobReqHistoryPopUp(@Valid JobReq jobReq) throws Exception {
		System.out.println("========  검토 이력 등록 팝업창 ========");
		
		String ecmYn = jobReq.getEcmYn();
		String url = "";
		if(ecmYn.equals("Y")) {
			url = "/approval/collaboration/jobReq/editFilePopHistoryViewEcm";
		} else {
			url = "/approval/collaboration/jobReq/editFilePopHistoryViewActiveX";
		}
		
		// tiles
		ModelAndView mav = new ModelAndView(url);
		try {
			User user = getSessionUser();
			JobReq jobReq2 = jobReqService.getFileObject( jobReq, user);
			
			mav.addObject("jobReqMgntNo", jobReq.getJobReqMgntNo());
			mav.addObject( "jobReq", jobReq2);
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( jobReq.getFileDataList()));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mav;
	}
	
	/**
	 * Default 검토 이력 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqHistorySave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqHistorySave(@Valid JobReq jobReq) {
		System.out.println("======== 검토 이력 저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			jobReq.setWriteEmpNo(user.getEmpNo()); //작성사번 세팅
			jobReqService.jobReqHistorySave(jobReq, user); //승인
			
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			result.put("success", "N");
			ex.getStackTrace();
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 검토이력 상세 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqHistoryDetailPopUp.do")
	public ModelAndView apprHistoryDetailPopUp(@Valid JobReq jobReq) {
		System.out.println("========  검토이력 상세 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqHistoryDetailPopUp");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("jobReqMgntNo", jobReq.getJobReqMgntNo());
			paramMap.put("historySeqno", jobReq.getHistorySeqno());
			
			JobReq resultDetail = jobReqDao.jobReqHistoryDetail(paramMap);
			
			User user = getSessionUser();
			JobReq jobReq2 = jobReqService.getFileObject( resultDetail, user);
			
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( jobReq2.getFileDataList()));
			mav.addObject("resultDetail", resultDetail);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * Default 승인
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqAppr.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqAppr(@Valid JobReq jobReq) {
		System.out.println("======== 승인 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		try {
			jobReq.setProcessStatusOld(jobReq.getProcessStatus()); //품의 진행상태값 세팅전 임시 변수에 담아두기(승인 서비스impl에서 필요)
			Map<String, Object> stats = getProcessStatus(jobReq.getProcessStatus(), jobReq);
			String apprLv = (String) stats.get("apprLv");
			
			jobReq.setProcessStatus((String) stats.get("processStatus"));
			jobReq.setApprStsCd((String) stats.get("apprStsCd"));
			jobReq.setApprLv(apprLv);
			if(apprLv.equals("6")) { //최종승인자가 승인시 확정일자 세팅
				jobReq.setApprDate(date);
			}
			
			jobReqService.jobReqAppr(jobReq); //승인
			
			//단계세팅
			String lv = jobReq.getApprLv();
			if(lv.equals("1")) {
				jobReq.setApprLv("2");
			} else if(lv.equals("4")) {
				jobReq.setApprLv("5");
			}
//			result.put("jobReqMgntNo", jobReq.getjobReqMgntNo());
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			ex.printStackTrace();
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 반려사유 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqRejectPopUp.do")
	public ModelAndView jobReqRejectPopUp(JobReq jobReq) {
		System.out.println("========  반려팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqRejectPopUp");
		try {
			
			String jobReqMgntNo = jobReq.getJobReqMgntNo();
			String apprStaCd = jobReq.getApprStsCd();
			String rejectReason = "";
			
			if(apprStaCd.equals("04")) {
				rejectReason = jobReqDao.getReject(jobReqMgntNo).get("rejectReason");
			}
			
			mav.addObject("jobReqMgntNo", jobReqMgntNo);
			mav.addObject("apprLv", jobReq.getApprLv());
			mav.addObject("apprStsCd", apprStaCd);
			mav.addObject("rejectReason", rejectReason);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * Default 반려저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqRejectSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqRejectSave(@Valid JobReq jobReq) {
		System.out.println("======== 반려저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		try {
			String apprLv = jobReq.getApprLv();
			if(apprLv.equals("1") || apprLv.equals("2") | apprLv.equals("3")) {
				jobReq.setProcessStatus("13"); //기안부 반려
			} else if(apprLv.equals("4") || apprLv.equals("5") | apprLv.equals("6")) {
				jobReq.setProcessStatus("23"); //법무부 반려
			} else {
				jobReq.setProcessStatus("99"); //종결
			}
			
			jobReq.setApprStsCd("04"); //반려
			jobReq.setApprDate(date); //품의확정일자세팅
			jobReqService.jobReqAppr(jobReq); //승인
			
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 종결처리
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqHistoryEnd.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqHistoryEnd(@Valid JobReq jobReq) {
		System.out.println("======== 종결처리 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
//			Map<String, Object> stats = getProcessStatus(jobReq.getProcessStatus(), jobReq);
			jobReq.setProcessStatus("99");
			jobReq.setApprStsCd(null);
			jobReq.setApprLv("7");
			jobReqService.jobReqAppr(jobReq); //종결처리
			
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			ex.printStackTrace();
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	 /**
	 * Default 기안부서 접수
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqReqReceipt.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqReqReceipt(@Valid JobReq jobReq) {
		System.out.println("======== 기안부서 접수 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, Object> lederInfo = new HashMap<String, Object>();
			lederInfo = commonCodeDao.getTeamLeaderInfo(user.getEmpNo());
			
			jobReqService.jobReqReqReceipt(jobReq); //저장
			
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 법무 결재선 접수
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqRcvReceipt.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqRcvReceipt(@Valid JobReq jobReq) {
		System.out.println("======== 법무부서 접수 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, Object> lederInfo = new HashMap<String, Object>();
			lederInfo = commonCodeDao.getTeamLeaderInfo(user.getEmpNo());
			
			jobReq.setProcessStatus("20"); //법무결재 작성
			jobReq.setRcvDraftStsCd("01"); //주관부서 기안자 작성
			jobReq.setRcvDraftEmpNo(user.getEmpNo());
			jobReq.setRcvApprEmpNo((String) lederInfo.get("empNo"));
			
			jobReqService.jobReqRcvReceipt(jobReq); //저장
			
			
			result.put("success", "Y");
			result.put("lederInfo", lederInfo);
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 검토 결과 등록 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqResultPopUp.do")
	public ModelAndView jobReqResultPopUp(JobReq jobReq) {
		System.out.println("========  검토 결과 등록 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqResultPopUp");
		try {
//			User user = (User) getRequestAttribute("ikep.user");
//			String today = getToday("yyyy.MM.dd");
			Date today = new Date();
			
			mav.addObject("jobReq", jobReq);
			mav.addObject("today", today);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * Default 검토 결과 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqResultSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqResultSave(JobReq jobReq) {
		System.out.println("======== 검토 결과 저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			jobReqService.jobReqResultSave(jobReq);
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 검토 결과 수정
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxJobReqResultModify.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxJobReqResultModify(JobReq jobReq) {
		System.out.println("======== 검토 결과 수정  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			jobReqService.jobReqResultModify(jobReq);
			result.put("success", "Y");
			result.put("jobReq", jobReq);
		} catch(Exception ex) {
			result.put("success", "N");
		}
		return result;
	}
	
	/**
	 * 검토 결과 상세 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jobReqResultDetailPopUp.do")
	public ModelAndView jobReqResultDetailPopUp(JobReq jobReq) {
		System.out.println("========  검토 결과 상세 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/jobReq/jobReqResultDetailPopUp");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("jobReqMgntNo", jobReq.getJobReqMgntNo());
			
			JobReq resultDetail = jobReqDao.jobReqResultDetail(paramMap);
			
			mav.addObject("resultDetail", resultDetail);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	
	//날짜형식 포멧및 세팅
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}
	
	/**
	 * 진행상태와 품의상태값 세팅
	 * @param 
	 * @param stats, jobReq
	 * @return result
	 */
	public Map<String, Object> getProcessStatus(String stats, JobReq jobReq) {
		// session
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			User user = (User) getRequestAttribute("ikep.user");
			String userId =  user.getEmpNo();
			
			if(stats.equals("10")) {
				result.put("processStatus", "11");
				result.put("apprStsCd", "02");
				result.put("apprLv", "1");
			} else if(stats.equals("11")) {
				if(userId.equals(jobReq.getReqApprEmpNo())) { //기안부서 승인자
					result.put("processStatus", "12");
					result.put("apprStsCd", "02");
					result.put("apprLv", "3");
				} else {
					result.put("processStatus", "11");
					result.put("apprStsCd", "02");
					result.put("apprLv", "2");
				}
			} else if(stats.equals("12") || stats.equals("20")) {
				result.put("processStatus", "21");
				result.put("apprStsCd", "02");
				result.put("apprLv", "4");
			} else if(stats.equals("21")) {
				if(userId.equals(jobReq.getRcvApprEmpNo())) {
					result.put("processStatus", "22");
					result.put("apprStsCd", "03");
					result.put("apprLv", "6");
				} else {
					result.put("processStatus", "21");
					result.put("apprStsCd", "02");
					result.put("apprLv", "5");
				}
			} else {
				result.put("processStatus", "99");
				result.put("apprStsCd", "03");
				result.put("apprLv", "6");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 파일 등록/수정 화면으로 이동 ActiveX
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewActiveX")
	public ModelAndView editFilePopViewActiveX( JobReq jobReq, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/jobReq/editFilePopViewActiveX");
		try {
			
			User user = getSessionUser();
			JobReq jobReq2 = jobReqService.getFileObject( jobReq, user);
			
			modelAndView.addObject( "jobReq", jobReq2);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( jobReq.getFileDataList()));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	/**
	 * 파일 등록/수정 화면으로 이동 Ecm
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewEcm")
	public ModelAndView editFilePopViewEcm( JobReq jobReq,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception{
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/jobReq/editFilePopViewEcm");
		try {
			
			User user = getSessionUser();
			JobReq jobReq2 = jobReqService.getFileObject( jobReq, user);
			
			modelAndView.addObject( "jobReq", jobReq2);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User getSessionUser() {
		
		return (User)this.getRequestAttribute("ikep.user");
	}
	
	/**
	 * 파일 등록/수정
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxUdateFile")
	public @ResponseBody String ajaxUdateFile( JobReq jobReq, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			
			fileItemId = jobReqService.ajaxUdateFile( jobReq, getSessionUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fileItemId;
	}
	/**
	 * 파일 등록/수정(검토이력등록용)
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxUdateHistoryFile")
	public @ResponseBody String ajaxUdateHistoryFile( JobReq jobReq, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			fileItemId = jobReqService.ajaxUdateFile( jobReq, getSessionUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fileItemId;
	}
	
	/**
	 * Ubi Report
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ubiReport")
	public String openUbiReport( @RequestParam(value = "id") String id, @RequestParam(value = "type") String type) throws Exception  {
		
		return "redirect:/ubi4/ubihtml.jsp?pkNo="+ id + "&reportType=" + type;
	}
	
	
}
