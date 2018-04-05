/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.legal.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.apache.commons.lang.StringUtils;
import org.apache.velocity.runtime.parser.node.GetExecutor;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.legal.dao.ApprLegalDao;
import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.approval.collaboration.legal.search.ApprContractListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.legal.service.ApprLegalService;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
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
 * @Method : apprLegal
 * @author : 신정수
 * @since  : 2018.02.06
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/approval/collaboration/legal")
public class ApprLegalController extends BaseController {

	@Autowired
	ApprLegalService apprLegalService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CommonCodeDao commonCodeDao;
	
	@Autowired
	private ApprLegalDao apprLegalDao;
	
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
	@RequestMapping(value = "/apprContractList.do")
	public ModelAndView apprContractList(ApprContractListSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString) throws Exception {
		System.out.println("======== 계약서 검토 요청서 목록 ========");
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractList");
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
			SearchResult<ApprLegal> resultList = apprLegalService.apprContractList(searchCondition);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("grpCd", "C00008");
			
			List<CommonCode> codeList = commonCodeDao.getCommonCodeList(map);
			
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("legalResultList", resultList);
			mav.addObject("codeList", codeList);
			mav.addObject("isSystemAdmin", 		this.isSystemAdmin(user));
			mav.addObject( "searchConditionString", tempSearchConditionString );
		} catch(Exception ex) {
			ex.printStackTrace();       
		}
		return mav;
	}
	
	/**
	 * 상세화면
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/apprContractDetail.do")
	public ModelAndView apprContractDetail(ApprLegal apprLegal, @RequestParam(value="id")String contractMgntNo, @RequestParam(value="processStatus")String processStatus) {
		System.out.println("========  상세정보화면 ========");
	
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractDetail");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 관리자 여부 체크
//			if(!this.isSystemAdmin(user)){
//				throw new IKEP4AuthorizedException();
//			}
			
			String id = apprLegal.getContractMgntNo();
//			String processStatus = apprLegal.getProcessStatus();
			
			
			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("contractMgntNo", contractMgntNo);
			paramMap.put("processStatus", processStatus);
			
			Map<String, String> resultMap = apprLegalDao.getApprLv(paramMap);
			
			paramMap.put("contractMgntNo", contractMgntNo);
			paramMap.put("empNo", user.getEmpNo());
			paramMap.put("apprLv", resultMap.get("apprLv")); //승인 단계 세팅
			
			
			Map<String, String> resultDetail = apprLegalService.apprContractDetail(paramMap); //상세정보
			
			// 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty(resultDetail.get("fileItemId") )) {
				
				fileDataList = this.fileService.getItemFile( resultDetail.get("fileItemId"), apprLegal.ITEM_FILE_TYPE);
			}
			apprLegal.setFileDataList(fileDataList);
			
			
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
			apprLegal.setEcmRoll( collaboCommonService.isEcmUser( user));
			
			List<ApprLegal> resultList = apprLegalService.apprContractHistoryList(contractMgntNo);
			apprLegal.setFileItemId((String)resultDetail.get("fileItemId"));
			
			mav.addObject("apprLegal", apprLegal);
			mav.addObject("resultDetail", resultDetail);
			mav.addObject("resultList", resultList);
			mav.addObject("apprLv", resultMap.get("apprLv"));
			mav.addObject("saveYn", resultDetail.get("saveYn"));
			mav.addObject("reqYn", resultDetail.get("reqYn"));
			mav.addObject("agreementEmpNoYn", resultDetail.get("agreementEmpNoYn"));
			mav.addObject("empNoYn", resultDetail.get("empNoYn"));
			mav.addObject("reqDraftYn", reqDraftYn);
			mav.addObject("rcvDraftYn", rcvDraftYn);
			mav.addObject("ecmRoll", apprLegal.isEcmRoll());
			mav.addObject("userAuthMgntYn", apprLegalService.userAuthMgntYn(user.getEmpNo()));
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( apprLegal.getFileDataList()));
			
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
	@RequestMapping(value = "/apprContractDetailRe.do")
	public ModelAndView apprContractDetailRe(ApprLegal apprLegal) {
		System.out.println("========  리로드 이후 상세정보화면 ========");
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractDetail");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 관리자 여부 체크
//			if(!this.isSystemAdmin(user)){
//				throw new IKEP4AuthorizedException();
//			}
			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("contractMgntNo", apprLegal.getContractMgntNo());
			paramMap.put("empNo", user.getEmpNo());
			paramMap.put("processStatus", apprLegal.getProcessStatus());
			Map<String, String> resultMap = apprLegalDao.getApprLv(paramMap);
			
			paramMap.put("apprLv", resultMap.get("apprLv")); //승인 단계 세팅
			//
			
			Map<String, String> resultDetail = apprLegalService.apprContractDetail(paramMap);
			
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
			apprLegal.setEcmRoll( collaboCommonService.isEcmUser( user));
			
			List<ApprLegal> resultList = apprLegalService.apprContractHistoryList(apprLegal.getContractMgntNo());
			// 파일정보 가져오기
			List<FileData> fileDataList = new ArrayList<FileData>();
			if( StringUtils.isNotEmpty(resultDetail.get("fileItemId") )) {
				
				fileDataList = this.fileService.getItemFile( resultDetail.get("fileItemId"), apprLegal.ITEM_FILE_TYPE);
			}
			apprLegal.setFileDataList(fileDataList);
			
			mav.addObject("apprLegal", apprLegal);
			mav.addObject("resultDetail", resultDetail);
			mav.addObject("resultList", resultList);
			mav.addObject("apprLv", apprLegal.getApprLv());
			mav.addObject("saveYn", resultDetail.get("saveYn"));
			mav.addObject("reqYn", resultDetail.get("reqYn"));
			mav.addObject("agreementEmpNoYn", resultDetail.get("agreementEmpNoYn"));
			mav.addObject("empNoYn", resultDetail.get("empNoYn"));
			mav.addObject("reqDraftYn", reqDraftYn);
			mav.addObject("rcvDraftYn", rcvDraftYn);
//			mav.addObject("rcvUseYn", rcvUseYn);
			mav.addObject("ecmRoll", apprLegal.isEcmRoll());
			mav.addObject("userAuthMgntYn", apprLegalService.userAuthMgntYn(user.getEmpNo()));
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( apprLegal.getFileDataList()));
			
			
		} catch(Exception ex) {
			ex.getStackTrace();
		}
		
		return mav;
	}
	
	/**
	 * 반려사유 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprRejectPopUp.do")
	public ModelAndView apprRejectPopUp(ApprLegal apprLegal) {
		System.out.println("========  반려팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprRejectPopUp");
		try {
			String contractMgntNo = apprLegal.getContractMgntNo();
			String apprStaCd = apprLegal.getApprStsCd();
			String rejectReason = "";
			
			if(apprStaCd.equals("04")) {
				rejectReason = apprLegalDao.getReject(contractMgntNo).get("rejectReason");
			}
			
			mav.addObject("contractMgntNo", contractMgntNo);
			mav.addObject("apprLv", apprLegal.getApprLv());
			mav.addObject("apprStsCd", apprLegal.getApprStsCd());
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
	@RequestMapping(value = "/ajaxContractRejectSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractRejectSave(@Valid ApprLegal apprLegal) {
		System.out.println("======== 반려저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		try {
			String apprLv = apprLegal.getApprLv();
			if(apprLv.equals("1") || apprLv.equals("2") | apprLv.equals("3")) {
				apprLegal.setProcessStatus("13"); //기안부 반려
			} else if(apprLv.equals("4") || apprLv.equals("5") | apprLv.equals("6")) {
				apprLegal.setProcessStatus("23"); //법무부 반려
			} else {
				apprLegal.setProcessStatus("99"); //종결
			}
			
			apprLegal.setApprStsCd("04"); //반려
			apprLegal.setApprDate(date); //품의확정일자
			apprLegalService.contractAppr(apprLegal); //승인
			
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 검토 이력 등록 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprHistoryPopUp.do")
	public ModelAndView apprHistoryPopUp(@Valid ApprLegal apprLegal) throws Exception {
		System.out.println("========  검토 이력 등록 팝업창 ========");
		String ecmYn = apprLegal.getEcmYn();
		String url = "";
		if(ecmYn.equals("Y")) {
			url = "/approval/collaboration/legal/editFilePopHistoryViewEcm";
		} else {
			url = "/approval/collaboration/legal/editFilePopHistoryViewActiveX";
		}
		
		// tiles
		ModelAndView mav = new ModelAndView(url);
		
		try {
			
			User user = getSessionUser();
			ApprLegal apprLegal2 = apprLegalService.getFileObject( apprLegal, user);
			
			mav.addObject("contractMgntNo", apprLegal.getContractMgntNo());
			mav.addObject( "apprLegal", apprLegal2);
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( apprLegal.getFileDataList()));
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
	@RequestMapping(value = "/ajaxContractHistorySave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractHistorySave(@Valid ApprLegal apprLegal) {
		System.out.println("======== 검토 이력 저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			apprLegal.setWriteEmpNo(user.getEmpNo()); //작성사번 세팅
			apprLegalService.historySave(apprLegal, user); //승인
			
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
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
	@RequestMapping(value = "/apprHistoryDetailPopUp.do")
	public ModelAndView apprHistoryDetailPopUp(@Valid ApprLegal apprLegal) {
		System.out.println("========  검토이력 상세 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprHistoryDetailPopUp");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("contractMgntNo", apprLegal.getContractMgntNo());
			paramMap.put("historySeqno", apprLegal.getHistorySeqno());
			
			ApprLegal resultDetail = apprLegalDao.apprContractHistoryDetail(paramMap);
			
			User user = getSessionUser();
			ApprLegal apprLegal2 = apprLegalService.getFileObject( resultDetail, user);
			
			mav.addObject( "fileDataListJson", CollaboUtils.convertJsonString( apprLegal2.getFileDataList()));
			mav.addObject("resultDetail", resultDetail);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 검토 결과 등록 팝업 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprContractResultPopUp.do")
	public ModelAndView apprReviewResultPopUp(ApprLegal apprLegal) {
		System.out.println("========  검토 결과 등록 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractResultPopUp");
		try {
			String contents = "<HTML><HEAD>";
				   contents += "<META content=IE=5 http-equiv=X-UA-Compatible>";
				   contents += "<META content='text/html; charset=utf-8' http-equiv=Content-Type>";
				   contents += "<META name=GENERATOR content='TAGFREE Active Designer'>";
				   contents += "<STYLE title=__tagfree_default>P {";
				   contents += "MARGIN-BOTTOM: 2px; MARGIN-TOP: 0pt; LINE-HEIGHT: 130%";
				   contents += "}";
				   contents += "BLOCKQUOTE {";
				   contents += "MARGIN-BOTTOM: 0pt; MARGIN-TOP: 0pt";
				   contents += "}";
				   contents += "TD {";
				   contents += "WORD-BREAK: break-all";
				   contents += "}";
				   contents += "</STYLE>";
				   contents += "</HEAD>";
				   contents += "<BODY style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>1. 계약목적</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>2.계약서 초안 검토</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>";
				   contents += "<TABLE style='FONT-SIZE: 9pt; BORDER-TOP: medium none; BORDER-RIGHT: medium none; BORDER-COLLAPSE: collapse; BORDER-BOTTOM: medium none; BORDER-LEFT: medium none' borderColor=#6e6e6e cellSpacing=0 cellPadding=0 width=722 bgColor=#ffffff border=1>";
				   contents += "<TBODY>";
				   contents += "<TR>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; WIDTH: 237px; BORDER-BOTTOM: #000000 1pt solid; TEXT-ALIGN: center; BORDER-LEFT: #000000 1pt solid; BACKGROUND-COLOR: #ffff00' bgColor=#ffff00 height=37 width=240>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕; TEXT-ALIGN: center'>초안</P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; WIDTH: 237px; BORDER-BOTTOM: #000000 1pt solid; TEXT-ALIGN: center; BORDER-LEFT: #000000 1pt solid; BACKGROUND-COLOR: #ffff00' bgColor=#ffff00 height=37 width=240>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕; TEXT-ALIGN: center'>검토안</P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; WIDTH: 239px; BORDER-BOTTOM: #000000 1pt solid; TEXT-ALIGN: center; BORDER-LEFT: #000000 1pt solid; BACKGROUND-COLOR: #ffff00' bgColor=#ffff00 height=37 width=242>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕; TEXT-ALIGN: center'>검토사유</P>";
				   contents += "</TD>";
				   contents += "</TR>";
				   contents += "<TR>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=240>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=240>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=242>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>&nbsp;</P>";
				   contents += "</TD>";
				   contents += "</TR>";
				   contents += "<TR>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=240>";
				   contents += "<P></P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=240>";
				   contents += "<P>&nbsp;</P>";
				   contents += "</TD>";
				   contents += "<TD style='BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid; BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid' height=37 width=242>";
				   contents += "<P>&nbsp;</P>";
				   contents += "</TD>";
				   contents += "</TR>";
				   contents += "</TBODY>";
				   contents += "</TABLE>";
				   contents += "</P>";
				   contents += "<P style='FONT-SIZE: 9pt; FONT-FAMILY: 맑은 고딕'>";
				   contents += "</P>";
				   contents += "</BODY>";
				   
			mav.addObject("apprLegal", apprLegal);
			mav.addObject("contents", contents);
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
	@RequestMapping(value = "/ajaxContractResultSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractResultSave(ApprLegal apprLegal) {
		System.out.println("======== 검토 결과 저장  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			apprLegalService.apprContractResultSave(apprLegal);
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
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
	@RequestMapping(value = "/ajaxContractResultModify.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractResultModify(ApprLegal apprLegal) {
		System.out.println("======== 검토 결과 수정  ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			apprLegalService.apprContractResultModify(apprLegal);
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
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
	@RequestMapping(value = "/apprContractResultDetailPopUp.do")
	public ModelAndView apprReviewResultDetailPopUp(ApprLegal apprLegal) {
		System.out.println("========  검토 결과 상세 팝업창 ========");
		// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractResultDetailPopUp");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("contractMgntNo", apprLegal.getContractMgntNo());
			paramMap.put("reqDeptId", apprLegal.getReqDeptId());
			
			ApprLegal resultDetail = apprLegalDao.apprContractResultDetail(paramMap);
			
			mav.addObject("resultDetail", resultDetail);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
    /**
     * 계약서 신규 등록 화면 불러오기 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/apprContractCreate.do")
    public ModelAndView createCounselHistoryView() throws Exception{
    	System.out.println("======== 신규 ========");
    	// tiles
		ModelAndView mav = new ModelAndView("/approval/collaboration/legal/apprContractCreate");
		
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
	 * Default 기안부서 접수
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxContractReqReceipt.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractReqReceipt(@Valid ApprLegal apprLegal) {
		System.out.println("======== 기안부서 접수 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, Object> lederInfo = new HashMap<String, Object>();
			lederInfo = commonCodeDao.getTeamLeaderInfo(user.getEmpNo());
			
			apprLegalService.contractReqReceipt(apprLegal); //저장
			
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
	@RequestMapping(value = "/ajaxContractRcvReceipt.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractRcvReceipt(@Valid ApprLegal apprLegal) {
		System.out.println("======== 법무부서 접수 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, Object> lederInfo = new HashMap<String, Object>();
			lederInfo = commonCodeDao.getTeamLeaderInfo(user.getEmpNo());
			
			apprLegal.setProcessStatus("20"); //법무결재 작성
			apprLegal.setRcvDraftStsCd("01"); //주관부서 기안자 작성
			apprLegal.setRcvDraftEmpNo(user.getEmpNo());
			apprLegal.setRcvApprEmpNo((String) lederInfo.get("empNo"));
			
			apprLegalService.contractRcvReceipt(apprLegal); //저장
			
			
			result.put("success", "Y");
			result.put("lederInfo", lederInfo);
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 계약서 신규 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxContractSave.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractSave(@Valid ApprLegal apprLegal) {
		System.out.println("======== 저장 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String today = getToday("yyyy.MM.dd");
			String [] todays = StringUtils.split(today, ".");
			String year = todays[0];
			String month = todays[1];
			String dateNo = year + "-" + month;
			
			apprLegal.setDateNo(dateNo);
			
			apprLegalService.contractSave(apprLegal); //저장
			
			result.put("contractMgntNo", apprLegal.getContractMgntNo());
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Default 수정
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxContractModify.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractModify(@Valid ApprLegal apprLegal) {
		System.out.println("======== 수정 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			apprLegalService.contractModify(apprLegal); //저장
			
//			result.put("contractMgntNo", apprLegal.getContractMgntNo());
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
	@RequestMapping(value = "/ajaxContractDelete.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractDelete(@Valid ApprLegal apprLegal) {
		System.out.println("======== 삭제 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			apprLegalService.contractDelete((String) apprLegal.getContractMgntNo()); //저장
			
//			result.put("contractMgntNo", apprLegal.getContractMgntNo());
			result.put("success", "Y");
		} catch(Exception ex) {
			result.put("success", "N");
			ex.printStackTrace();
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	/**
	 * Default 승인
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxContractAppr.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractAppr(@Valid ApprLegal apprLegal) {
		System.out.println("======== 승인 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Date date = new Date();
			apprLegal.setProcessStatusOld(apprLegal.getProcessStatus()); //품의 진행상태값 세팅전 임시 변수에 담아두기(승인 서비스impl에서 필요)
			Map<String, Object> stats = getProcessStatus(apprLegal.getProcessStatus(), apprLegal);
			String apprLv = (String) stats.get("apprLv");
			
			
			apprLegal.setProcessStatus((String) stats.get("processStatus"));
			apprLegal.setApprStsCd((String) stats.get("apprStsCd"));
			apprLegal.setApprLv(apprLv);
			if(apprLv.equals("6")) { //최종승인자가 승인시 확정일자 세팅
				apprLegal.setApprDate(date);
			}
			
			apprLegalService.contractAppr(apprLegal); //승인
			
			//단계세팅
			String lv = apprLegal.getApprLv();
			if(lv.equals("1")) {
				apprLegal.setApprLv("2");
			} else if(lv.equals("4")) {
				apprLegal.setApprLv("5");
			}
//			result.put("contractMgntNo", apprLegal.getContractMgntNo());
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
		} catch(Exception ex) {
			ex.printStackTrace();
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
	@RequestMapping(value = "/ajaxContractHistoryEnd.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxContractHistoryEnd(@Valid ApprLegal apprLegal) {
		System.out.println("======== 종결처리 ========");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
//			Map<String, Object> stats = getProcessStatus(apprLegal.getProcessStatus(), apprLegal);
			apprLegal.setProcessStatus("99");
			apprLegal.setApprStsCd(null);
			apprLegal.setApprLv("7");
			apprLegalService.contractAppr(apprLegal); //종결처리
			
			result.put("success", "Y");
			result.put("apprLegal", apprLegal);
		} catch(Exception ex) {
			ex.printStackTrace();
			result.put("success", "N");
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 진행상태와 품의상태값 세팅
	 * @param 
	 * @param stats, apprLegal
	 * @return result
	 */
	public Map<String, Object> getProcessStatus(String stats, ApprLegal apprLegal) {
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
				if(userId.equals(apprLegal.getReqApprEmpNo())) { //기안부서 승인자
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
				if(userId.equals(apprLegal.getRcvApprEmpNo())) {
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
	 * 파일 등록/수정 화면으로 이동 ActiveX
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewActiveX")
	public ModelAndView editFilePopViewActiveX( ApprLegal apprLegal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/legal/editFilePopViewActiveX");
		try {
			
			User user = getSessionUser();
			ApprLegal apprLegal2 = apprLegalService.getFileObject( apprLegal, user);
			
			modelAndView.addObject( "apprLegal", apprLegal2);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( apprLegal.getFileDataList()));
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
	public ModelAndView editFilePopViewEcm( ApprLegal apprLegal,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception{
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/legal/editFilePopViewEcm");
		try {
			
			User user = getSessionUser();
			ApprLegal apprLegal2 = apprLegalService.getFileObject( apprLegal, user);
			
			modelAndView.addObject( "apprLegal", apprLegal2);
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
	public @ResponseBody String ajaxUdateFile( ApprLegal apprLegal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			
			fileItemId = apprLegalService.ajaxUdateFile( apprLegal, getSessionUser());
			
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
	public @ResponseBody String ajaxUdateHistoryFile( ApprLegal apprLegal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			fileItemId = apprLegalService.ajaxUdateFile( apprLegal, getSessionUser());
			
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
	@RequestMapping(value = "/ubiReport.do")
	public String openUbiReport( @RequestParam(value = "id") String id, @RequestParam(value = "type") String type) throws Exception  {
		
		return "redirect:/ubi4/ubihtml.jsp?pkNo="+ id + "&reportType=" + type ;
	}
	
}
