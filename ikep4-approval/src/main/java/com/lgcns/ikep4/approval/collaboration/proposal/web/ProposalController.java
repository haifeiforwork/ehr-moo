package com.lgcns.ikep4.approval.collaboration.proposal.web;

import java.util.List;
import java.util.Map;

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
import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.proposal.model.ProposalPermission;
import com.lgcns.ikep4.approval.collaboration.proposal.search.ProposalSearchCondition;
import com.lgcns.ikep4.approval.collaboration.proposal.service.ProposalService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 개발제안서 Controller
 * 
 * @author pjh
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/collaboration/proposal")
public class ProposalController extends BaseController {
	
	/** The CommonCode service. */
	@Autowired
	private CommonCodeService commonCodeService;
	
	/** The ProposalDao service. */
	@Autowired
	private ProposalService proposalService;
	
	/**
	 * 개발제안서 목록조회 화면 이동
	 * @return
	 */
	@RequestMapping(value = "/listProposalView.do")
	public ModelAndView listProposalView( ProposalSearchCondition searchCondition, @RequestParam(value = "searchConditionString", required = false) String searchConditionString ) throws Exception{
		
		//화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        SearchResult<Proposal> searchResult= null;
        ModelAndView modelAndView = new ModelAndView("/approval/collaboration/proposal/listProposalView");
        try {
			
        	if ( StringUtils.isEmpty( searchConditionString ) ) {
        		tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
        				"searchColumn", "searchWord", "sortColumn", "sortType", "searchStartReqDate", "searchEndReqDate", 
        				"searchReqEmpNo", "searchTcsRcvEmpNo", "searchProductName");
        	} else {
        		ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
        		tempSearchConditionString = searchConditionString;
        	}
        	
        	// 목록 데이터 조회
        	searchResult = proposalService.getProposalList( searchCondition, getSessionUser());
        	
        	modelAndView.addObject( "searchResult", searchResult);
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
	 * 개발제안서 등록/수정 화면으로 이동
	 * @return
	 */
	@RequestMapping(value = "/editProposalView.do")
	public ModelAndView editProposalView( ProposalSearchCondition searchCondition,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
			) throws Exception{
		
		Proposal proposal = null;
		List<CommonCode> c00006List = null;
		List<CommonCode> c00007List = null;
		List<CommonCode> c00010List = null;
		List<CommonCode> c00018List = null;
		List<CommonCode> c00019List = null;
		Map<String, Object> resultMap = null;
		ProposalPermission permission = null;
		
		ModelAndView modelAndView = new ModelAndView("/approval/collaboration/proposal/editProposalView");
		try {
			
			User user = getSessionUser();
			resultMap = proposalService.getProposalObject( searchCondition, user);
			
			c00006List = commonCodeService.getCommonCodeList( "C00006");
			c00007List = commonCodeService.getCommonCodeList( "C00007");
			c00010List = commonCodeService.getCommonCodeList( "C00010");
			c00018List = commonCodeService.getCommonCodeList( "C00018");
			c00019List = commonCodeService.getCommonCodeList( "C00019");
        	
        	
			proposal  = (Proposal) resultMap.get("proposal");
			permission = (ProposalPermission) resultMap.get("permission");
        	
    		
    		modelAndView.addObject( "c00006List", c00006List);
    		modelAndView.addObject( "c00007List", c00007List);
    		modelAndView.addObject( "c00010List", c00010List);
    		modelAndView.addObject( "c00018List", c00018List);
    		modelAndView.addObject( "c00019List", c00019List);
    		modelAndView.addObject( "searchConditionString", searchConditionString);
    		modelAndView.addObject( "proposal", proposal);
    		modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( proposal.getFileDataList()));
    		modelAndView.addObject( "viewMode", searchCondition.getViewMode());
    		modelAndView.addObject( "permission", permission);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		
		return modelAndView;
	}
	
	/**
	 * 의견등록 화면으로 이동
	 * @return
	 */
	@RequestMapping(value = "/opinionProposalPopView")
	public ModelAndView opinionProposalPopView( Proposal proposal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/proposal/opinionProposalPopView");
		try {
			
			Proposal oldProposal = proposalService.getOpinionProposal(proposal, getSessionUser());
			modelAndView.addObject( "proposal", oldProposal);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		return modelAndView;
	}
	
	/**
	 * 파일 등록/수정 화면으로 이동 ActiveX
	 * @return
	 */
	@RequestMapping(value = "/editFilePopViewActiveX")
	public ModelAndView editFilePopViewActiveX( Proposal proposal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/proposal/editFilePopViewActiveX");
		try {
			
			User user = getSessionUser();
			Proposal proposal2 = proposalService.getFileObject( proposal, user);
			
			modelAndView.addObject( "proposal", proposal2);
			modelAndView.addObject( "fileDataListJson", CollaboUtils.convertJsonString( proposal.getFileDataList()));
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
	public ModelAndView editFilePopViewEcm( Proposal proposal,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception{
		
		ModelAndView modelAndView =  new ModelAndView( "/approval/collaboration/proposal/editFilePopViewEcm");
		try {
			
			User user = getSessionUser();
			Proposal proposal2 = proposalService.getFileObject( proposal, user);
			
			modelAndView.addObject( "proposal", proposal2);
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
	public @ResponseBody String ajaxUdateFile( Proposal proposal, @RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws Exception {
		
		String fileItemId = "";
		try {
			
			fileItemId = proposalService.ajaxUdateFile( proposal, getSessionUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return fileItemId;
	}
	
	/**
	 * 개발제안서 등록
	 * @param Proposal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createProposal")
	public String createProposal( Proposal proposal, 
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString ) throws Exception  {
		
		try {
			
			proposalService.createProposal( proposal, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "proposalNo=" +proposal.getProposalNo() + "&searchConditionString=" + searchConditionString+"&viewMode=modify";
		
		return "redirect:/approval/collaboration/proposal/editProposalView.do?"+ param;
	}
	
	/**
	 * 개발제안서 등록
	 * @param Proposal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProposal")
	public String updateProposal( Proposal proposal, 
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString ) throws Exception  {
		
		try {
			
			proposalService.updateProposal( proposal, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String param = "proposalNo=" +proposal.getProposalNo() + "&searchConditionString=" + searchConditionString+"&viewMode=modify";
		
		return "redirect:/approval/collaboration/proposal/editProposalView.do?"+ param;
	}
	
	/**
	 * Ubi Report
	 * @param proposal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ubiReport")
	public String openUbiReport( Proposal proposal) throws Exception  {
		
		proposalService.checkPermission( proposal, getSessionUser(), CbrConstants.STAT_DEFAULT);
		
		return "redirect:/ubi4/ubihtml.jsp?pkNo="+ proposal.getProposalNo() + "&reportType=proposal";
	}
	
	/**
	 * 개발제안서 삭제
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxDeletProposal", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxDeletProposal( Proposal proposal){
		
		try {
			
			proposalService.deleteProposal( proposal, getSessionUser());
		}catch(CollaborationException e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다."+ e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4AjaxValidationException( "", "오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 개발제안서 - 부서의견 수정
	 * @param newProductDev
	 */
	@RequestMapping(value = "/ajaxUpdateOpinion", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxUpdateOpinion( Proposal proposal){
		
		try {
			
			proposalService.UpdateOpinion( proposal, getSessionUser());
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
