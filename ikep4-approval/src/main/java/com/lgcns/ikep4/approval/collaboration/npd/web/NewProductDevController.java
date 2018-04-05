package com.lgcns.ikep4.approval.collaboration.npd.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaborationException;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.npd.model.NpdPermission;
import com.lgcns.ikep4.approval.collaboration.npd.search.NewProductDevSearchCondition;
import com.lgcns.ikep4.approval.collaboration.npd.service.NewProductDevService;
import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 신제품 개발 Controller
 * 
 * @author pjh
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/collaboration/npd")
public class NewProductDevController extends BaseController {
	
	/** The NewProductDev service. */
	@Autowired
	private NewProductDevService newProductDevService;
	
	/** The CommonCode service. */
	@Autowired
	private CommonCodeService commonCodeService;
	
	/** The CollaboCommon service. */
	@Autowired
	private CollaboCommonService collaboCommonService;
	
	/**
	 * 사용자 권한 관리 목록조회 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/listNewProductDevView.do")
	public ModelAndView listNewProductDevView( NewProductDevSearchCondition searchCondition,
												@RequestParam(value = "searchConditionString", required = false) String searchConditionString
												) throws Exception{
		
		//화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        SearchResult<NewProductDev> searchResult= null;
        ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/listNewProductDevView");
        try {
			
        	if ( StringUtils.isEmpty( searchConditionString ) ) {
        		tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
        				"searchColumn", "searchWord", "sortColumn", "sortType", "searchReqEmpNo", "searchReqEmpNm", "searchTcsDraftEmpNo", "searchTcsDraftEmpNm",
        				"isAdmin", "searchProcessStatus", "searchStartReqDate", "searchEndReqDate", "searchProductName");
        	} else {
        		ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
        		tempSearchConditionString = searchConditionString;
        	}
        	
        	// 목록 데이터 조회
        	searchResult = newProductDevService.getNewProductDevList( searchCondition, getSessionUser());
        	
        	// 공통코드 조회
        	List<CommonCode> procStatusList = commonCodeService.getCommonCodeList( "C00002");
        	List<CommonCode> c00010List = commonCodeService.getCommonCodeList( "C00010");
        	
        	modelAndView.addObject( "searchResult", searchResult);
        	modelAndView.addObject( "c00010List", c00010List);
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
	 * 신제품 개발 등록/수정 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/editNewProductDevView.do")
	public ModelAndView editNewProductDevView( NewProductDevSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
			) throws Exception{
		
		NewProductDev newProductDev = null;
		List<CommonCode> c00003List = null;
		List<CommonCode> c00004List = null;
		List<CommonCode> c00005List = null;
		List<CommonCode> c00010List = null;
		Map<String, Object> resultMap = null;
		NpdPermission npdPermission = null;
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/editNewProductDevView");
		try {
			
			User user = getSessionUser();
			resultMap = newProductDevService.getNewProductObject( searchCondition, user);
			
			c00010List = commonCodeService.getCommonCodeList( "C00010");
        	c00003List = commonCodeService.getCommonCodeList( "C00003");
        	c00004List = commonCodeService.getCommonCodeList( "C00004");
        	c00005List = commonCodeService.getCommonCodeList( "C00005");	// 기안자품의상태코드
        	
        	
        	newProductDev  = (NewProductDev) resultMap.get("newProductDev");
        	npdPermission = (NpdPermission) resultMap.get("npdPermission");
        	
        	ObjectMapper mapper = new ObjectMapper();
        	// 파일 목록을 JSON으로 변환한다.
    		String fileDataListJson = null;
    		
    		if( newProductDev.getFileDataList() != null) {
    			fileDataListJson = mapper.writeValueAsString( newProductDev.getFileDataList());
    		}
    		
    		modelAndView.addObject( "c00003List", c00003List);
    		modelAndView.addObject( "c00004List", c00004List);
    		modelAndView.addObject( "c00005List", c00005List);
    		modelAndView.addObject( "c00010List", c00010List);
    		modelAndView.addObject( "searchConditionString", searchConditionString);
    		modelAndView.addObject( "newProductDev", newProductDev);
    		modelAndView.addObject( "ecmrole", collaboCommonService.isEcmUser( user));
    		modelAndView.addObject( "fileDataListJson", fileDataListJson);
    		modelAndView.addObject( "viewMode", searchCondition.getViewMode());
    		modelAndView.addObject( "npdPermission", npdPermission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		
		return modelAndView;
	}
	
	/**
	 * 반려팝업 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/rejectNewProductDevPopView.do")
	public ModelAndView rejectNewProductDevPopView( NewProductDevSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/rejectNewProductDevPopView");
		Map<String, Object> resultMap = null;
		try {
			searchCondition.setViewMode("modify");
			User user = getSessionUser();
			resultMap = newProductDevService.getNewProductDev( searchCondition, user);
			NewProductDev newProductDev  = (NewProductDev) resultMap.get("newProductDev");
			NpdPermission npdPermission = (NpdPermission) resultMap.get("npdPermission");
        	modelAndView.addObject( "newProductDev", newProductDev);
        	modelAndView.addObject( "npdPermission", npdPermission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 결과파일 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/rsltFileNewProductDevPopView.do")
	public ModelAndView rsltFileNewProductDevPopView( NewProductDevSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/rsltFileNewProductDevPopView");
		Map<String, Object> resultMap = null;
		try {
			
			User user = getSessionUser();
			searchCondition.setSearchFileType("rslt");
			resultMap = newProductDevService.getNewProductDev( searchCondition, user);
			NewProductDev newProductDev  = (NewProductDev) resultMap.get("newProductDev");
			NpdPermission npdPermission = (NpdPermission) resultMap.get("npdPermission");
			modelAndView.addObject( "newProductDev", newProductDev);
			modelAndView.addObject( "npdPermission", npdPermission);
			
			ObjectMapper mapper = new ObjectMapper();
        	// 파일 목록을 JSON으로 변환한다.
    		String fileDataListJson = null;
    		
    		if( newProductDev.getRsltFileDataList() != null) {
    			fileDataListJson = mapper.writeValueAsString( newProductDev.getRsltFileDataList());
    		}
    		
    		modelAndView.addObject( "ecmrole", collaboCommonService.isEcmUser( user));
    		modelAndView.addObject( "fileDataListJson", fileDataListJson);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 결과파일 수정 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/editRsltFileNewProductDevPopView.do")
	public ModelAndView editRsltFileNewProductDevPopView( NewProductDevSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/editRsltFileNewProductDevPopView");
		Map<String, Object> resultMap = null;
		try {
			
			User user = getSessionUser();
			searchCondition.setSearchFileType("rslt");
			resultMap = newProductDevService.getNewProductDev( searchCondition, user);
			NewProductDev newProductDev  = (NewProductDev) resultMap.get("newProductDev");
			NpdPermission npdPermission = (NpdPermission) resultMap.get("npdPermission");
			modelAndView.addObject( "newProductDev", newProductDev);
			modelAndView.addObject( "npdPermission", npdPermission);
			
			ObjectMapper mapper = new ObjectMapper();
			// 파일 목록을 JSON으로 변환한다.
			String fileDataListJson = null;
			
			if( newProductDev.getRsltFileDataList() != null) {
				fileDataListJson = mapper.writeValueAsString( newProductDev.getRsltFileDataList());
			}
			
			modelAndView.addObject( "ecmrole", collaboCommonService.isEcmUser( user));
			modelAndView.addObject( "fileDataListJson", fileDataListJson);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 요구일정 팝업화면으로 이동
	 * @param searchCondition
	 * @param searchConditionString
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reqScheduleCdPopView")
	public ModelAndView reqScheduleCdPopView( NewProductDevSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
		) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/npd/reqScheduleCdPopView");
		Map<String, Object> resultMap = null;
		try {
			
			List<CommonCode> c00004List = commonCodeService.getCommonCodeList( "C00004");
			searchCondition.setViewMode("modify");
			
			resultMap = newProductDevService.getNewProductDev( searchCondition, getSessionUser());
			NewProductDev newProductDev  = (NewProductDev) resultMap.get("newProductDev");
			NpdPermission npdPermission = (NpdPermission) resultMap.get("npdPermission");
        	
        	modelAndView.addObject( "newProductDev", newProductDev);
        	modelAndView.addObject( "npdPermission", npdPermission);
        	modelAndView.addObject( "c00004List", c00004List);
		
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 등록
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createNewProductDev")
	public String createNewProductDev( NewProductDev newProductDev,
				@RequestParam(value = "searchConditionString", required = false) String searchConditionString
			) throws Exception {
		
		try {
			
			newProductDevService.createNewProductDev(newProductDev, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "mgntNo=" +newProductDev.getMgntNo() + "&searchConditionString=" + searchConditionString+"&viewMode=modify";
		
		return "redirect:/approval/collaboration/npd/editNewProductDevView.do?"+ param;
	}
	
	/**
	 * 수정
	 * @param newProductDev
	 * @param searchConditionString
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNewProductDev")
	public String updateNewProductDev( NewProductDev newProductDev,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) throws Exception {
		
		try {
			
			newProductDevService.updateNewProductDev(newProductDev, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "mgntNo=" +newProductDev.getMgntNo() + "&searchConditionString=" + searchConditionString +"&viewMode=modify";
		
		return "redirect:/approval/collaboration/npd/editNewProductDevView.do?"+ param;
	}
	
	/**
	 * Ubi Report
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UbiReport")
	public String openUbiReport( NewProductDev newProductDev) throws Exception  {
		
		newProductDevService.checkPermission( newProductDev, getSessionUser(), CbrConstants.STAT_DEFAULT);
		
		return "redirect:/ubi4/ubihtml.jsp?pkNo="+ newProductDev.getMgntNo() + "&reportType=npd";
	}
	
	/**
	 * 파일 등록/수정 화면으로 이동 ActiveX
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewActiveX")
	public ModelAndView editFilePopViewActiveX( NewProductDev newProductDev, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/npd/editFilePopViewActiveX");
		try {
			
			newProductDevService.getFileObject(newProductDev, getSessionUser());
			
			modelAndView.addObject( "newProductDev", newProductDev);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( newProductDev.getFileDataList()));
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
	public ModelAndView editFilePopViewEcm( NewProductDev newProductDev,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception{
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/npd/editFilePopViewEcm");
		try {
			
			newProductDevService.getFileObject( newProductDev, getSessionUser());
			
			modelAndView.addObject( "newProductDev", newProductDev);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( newProductDev.getFileDataList()));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 파일 등록/수정
	 * @param newProductDev
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxUdateFile")
	public @ResponseBody String ajaxUdateFile( NewProductDev newProductDev, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			
			fileItemId = newProductDevService.ajaxUdateFile( newProductDev, getSessionUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fileItemId;
	}
	
	/**
	 * 접수
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxReceiptNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxReceiptNewProductDev( NewProductDev newProductDev){
		
		try {
			newProductDev.setApproveStsCd( CbrConstants.STATUS_CD_01);
			newProductDevService.approveNewProductDev(newProductDev, getSessionUser());
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
	@RequestMapping(value = "/ajaxApproveNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void approveNewProductDev( NewProductDev newProductDev){
		
		try {
			newProductDev.setApproveStsCd( CbrConstants.STATUS_CD_03);
			newProductDevService.approveNewProductDev(newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 승인/저장
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxApproveWithSave", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxApproveWithSave( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.approveWithSave( newProductDev, getSessionUser());
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
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxRejectNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxRejectNewProductDev( NewProductDev newProductDev){
		
		try {
			newProductDev.setApproveStsCd( CbrConstants.STATUS_CD_04);
			newProductDevService.approveNewProductDev(newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 결과파일 저장
	 * @param userAuthMgnt
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxRsltFileNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxRsltFileNewProductDev( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.updateRsltFile( newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 삭제
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxDeleteNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxDeleteNewProductDev( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.deleteNewProductDev( newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 반려문서 초기화
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxInitRejctNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxInitRejctNewProductDev( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.initRejctNewProductDev( newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 반려문서 초기화 - 재상신(접수단계로 변경)
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxChangeReceiptNewProductDev", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxChangeReceiptNewProductDev( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.changeReceiptNewProductDev( newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 요구일정 수정
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxUpdateReqScheduleCd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxUpdateReqScheduleCd( NewProductDev newProductDev){
		
		try {
			
			newProductDevService.updateReqScheduleCd( newProductDev, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 *
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User getSessionUser() {
		
		return (User)this.getRequestAttribute("ikep.user");
	}
	
}
