package com.lgcns.ikep4.approval.collaboration.testreq.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaborationException;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequestPermission;
import com.lgcns.ikep4.approval.collaboration.testreq.search.TestRequestSearchCondition;
import com.lgcns.ikep4.approval.collaboration.testreq.service.TestRequestService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 시험의뢰서 Controller
 * 
 * @author pjh
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/collaboration/testreq")
public class TestRequestController extends BaseController {
	
	/** The CommonCode service. */
	@Autowired
	private CommonCodeService commonCodeService;
	
	/** The TestRequest service. */
	@Autowired
	private TestRequestService testRequestService;
	
	
	/**
	 * 시험의뢰서 목록조회 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/listTestRequestView.do")
	public ModelAndView listTestRequestView( TestRequestSearchCondition searchCondition,
												@RequestParam(value = "searchConditionString", required = false) String searchConditionString
												) throws Exception{
		
		//화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        SearchResult<TestRequest> searchResult= null;
        ModelAndView modelAndView = new ModelAndView("/approval/collaboration/testreq/listTestRequestView");
        try {
			
        	if ( StringUtils.isEmpty( searchConditionString ) ) {
        		tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
        				"searchColumn", "searchWord", "sortColumn", "sortType", "searchStartWriteDate", "searchEndWriteDate", 
        				"searchReqEmpNo","searchReqEmpNm", "searchRcvEmpNo", "searchRcvEmpNm", "searchProcessStatus", "searchItemKindCd", "searchTestReqTitle", "searchCompanyChkVal");
        	} else {
        		ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
        		tempSearchConditionString = searchConditionString;
        	}
        	
        	// 목록 데이터 조회
        	searchResult = testRequestService.getTestRequestList( searchCondition, getSessionUser());
        	
        	// 공통코드 조회
        	List<CommonCode> procStatusList = commonCodeService.getCommonCodeList( "C00014");
        	List<CommonCode> c00016List = commonCodeService.getCommonCodeList( "C00016");
        	
        	modelAndView.addObject( "searchResult", searchResult);
        	modelAndView.addObject( "c00016List", c00016List);
        	modelAndView.addObject( "procStatusList", procStatusList);
        	modelAndView.addObject( "searchCondition", searchResult.getSearchCondition());
        	modelAndView.addObject( "searchConditionString", tempSearchConditionString);
        	modelAndView.addObject( "boardCode", new BoardCode());
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		return modelAndView;
	}
	
	/**
	 * 시험의뢰서 등록/수정 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/editTestRequestView.do")
	public ModelAndView editTestRequestView( TestRequestSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
			) throws Exception{
		
		TestRequest testRequest = null;
		TestRequestPermission testRequestPermission = null;
		List<CommonCode> c00005List = null;
		List<CommonCode> c00013List = null;
		List<CommonCode> c00016List = null;
		Map<String, Object> resultMap = null;
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/testreq/editTestRequestView");
		try {
			
			User user = getSessionUser();
			resultMap = testRequestService.getTestRequestObject( searchCondition, user);
			
			c00005List = commonCodeService.getCommonCodeList( "c00005");
			c00013List = commonCodeService.getCommonCodeList( "C00013");
			c00016List = commonCodeService.getCommonCodeList( "C00016");
        	
        	
			testRequest  = (TestRequest) resultMap.get("testRequest");
			testRequestPermission = (TestRequestPermission) resultMap.get("testRequestPermission");
        	
    		
			modelAndView.addObject( "c00005List", c00005List);
    		modelAndView.addObject( "c00013List", c00013List);
    		modelAndView.addObject( "c00016List", c00016List);
    		modelAndView.addObject( "searchConditionString", searchConditionString);
    		modelAndView.addObject( "testRequest", testRequest);
    		modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( testRequest.getFileDataList()));
    		modelAndView.addObject( "viewMode", searchCondition.getViewMode());
    		modelAndView.addObject( "permission", testRequestPermission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		
		return modelAndView;
	}
	
	/**
	 * Ubi Report
	 * @param proposal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ubiReport")
	public String openUbiReport( TestRequest testRequest, HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		testRequestService.checkPermission( testRequest, getSessionUser(), CbrConstants.STAT_DEFAULT);
		
		return "redirect:/ubi4/ubihtml.jsp?pkNo="+ testRequest.getTrMgntNo() + "&reportType=testReq";
	}
	
	/**
	 * 파일 등록/수정 화면으로 이동 ActiveX
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewActiveX")
	public ModelAndView editFilePopViewActiveX( TestRequest testRequest, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/testreq/editFilePopViewActiveX");
		try {
			
			User user = getSessionUser();
			TestRequest testRequest2 = testRequestService.getFileObject( testRequest, user);
			
			modelAndView.addObject( "testRequest", testRequest2);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( testRequest.getFileDataList()));
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
	public ModelAndView editFilePopViewEcm( TestRequest testRequest,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception{
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/testreq/editFilePopViewEcm");
		try {
			
			User user = getSessionUser();
			TestRequest testRequest2 = testRequestService.getFileObject( testRequest, user);
			
			modelAndView.addObject( "testRequest", testRequest2);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 부결팝업 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/rejectTestRequestView")
	public ModelAndView rejectTestRequestView( TestRequestSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/testreq/rejectTestRequestView");
		Map<String, Object> resultMap = null;
		try {
			
			
			User user = getSessionUser();
			
			searchCondition.setViewMode("modify");
			resultMap = testRequestService.getTestRequestObject( searchCondition, user);
			TestRequest testRequest  = (TestRequest) resultMap.get("testRequest");
			TestRequestPermission permission = (TestRequestPermission) resultMap.get("testRequestPermission");
			
        	modelAndView.addObject( "testRequest", testRequest);
        	modelAndView.addObject( "permission", permission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 부결팝업 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/addWriteDetailTestRequestPopView")
	public ModelAndView addWriteDetailTestRequestPopView( TestRequestSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/testreq/addWriteDetailTestRequestPopView");
		Map<String, Object> resultMap = null;
		try {
			
			
			User user = getSessionUser();
			
			searchCondition.setViewMode("modify");
			resultMap = testRequestService.getTestRequestObject( searchCondition, user);
			TestRequest testRequest  = (TestRequest) resultMap.get("testRequest");
			TestRequestPermission permission = (TestRequestPermission) resultMap.get("testRequestPermission");
			
			modelAndView.addObject( "testRequest", testRequest);
			modelAndView.addObject( "permission", permission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 시험의뢰서 등록
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createTestRequest")
	public String createTestRequest( TestRequest testRequest, 
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString ) throws Exception  {
		
		try {
			
			testRequestService.createTestRequest( testRequest, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "trMgntNo=" +testRequest.getTrMgntNo() + "&searchConditionString=" + searchConditionString+"&viewMode=modify";
		
		return "redirect:/approval/collaboration/testreq/editTestRequestView.do?"+ param;
	}
	
	/**
	 * 시험의뢰서 수정
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTestRequest")
	public String updateTestRequest( TestRequest testRequest, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		try {
			
			testRequestService.updateTestRequest( testRequest, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "trMgntNo=" +testRequest.getTrMgntNo() + "&searchConditionString=" + searchConditionString+"&viewMode=modify";
		
		return "redirect:/approval/collaboration/testreq/editTestRequestView.do?"+ param;
	}
	
	/**
	 * 파일 등록/수정
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxUdateFile")
	public @ResponseBody String ajaxUdateFile( TestRequest testRequest, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			
			fileItemId = testRequestService.ajaxUdateFile( testRequest, getSessionUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fileItemId;
	}
	
	/**
	 * 삭제
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxDeletTestRequest", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxDeleteNewProductDev( TestRequest testRequest){
		
		try {
			
			testRequestService.deleteTestRequest( testRequest, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 승인
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxApproveTestRequest", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void approveTestRequest( TestRequest testRequest){
		
		try {
			
			testRequest.setApproveStsCd( CbrConstants.STATUS_CD_03);
			testRequestService.approveTestRequest(testRequest, getSessionUser());
			
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 승인
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithApproveTestRequest", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateWithApprove( TestRequest testRequest){
		
		try {
			
			testRequest.setApproveStsCd( CbrConstants.STATUS_CD_03);
			// [통합테스트] 상신시 저장하도록 변경 
			testRequestService.updateWithApprove( testRequest, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 반려
	 * @param TestRequest
	 * @return
	 */
	@RequestMapping(value = "/ajaxRejectTestRequest", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxRejectTestRequest( TestRequest testRequest){
		
		try {
			testRequest.setApproveStsCd( CbrConstants.STATUS_CD_04);
			testRequestService.approveTestRequest( testRequest, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 초기화
	 * @param testRequest
	 */
	@RequestMapping(value = "/ajaxInitTestRequest", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxInitTestRequest( TestRequest testRequest){
		
		try {
			testRequestService.initTestRequest( testRequest, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 추가사항 수정
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddWriteDetail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxAddWriteDetail( TestRequest testRequest){
		
		try {
			
			testRequestService.updateAddWriteDetail( testRequest, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User getSessionUser() {
		
		return (User)this.getRequestAttribute("ikep.user");
	}
	
}
