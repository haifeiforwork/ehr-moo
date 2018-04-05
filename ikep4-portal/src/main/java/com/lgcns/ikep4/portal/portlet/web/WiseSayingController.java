package com.lgcns.ikep4.portal.portlet.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;
import com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 오늘의 명언 포틀릿 controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: WiseSayingController.java 19525 2012-06-27 05:34:59Z shinyong $
 */
@Controller
@RequestMapping(value = "/portal/portlet/wiseSaying")
public class WiseSayingController extends BaseController{

	/**
	 * 오늘의 명언 포틀릿 service
	 */
	@Autowired
	private PortletWiseSayingService portletWiseSayingService;
	
	/**
	 * 아이디 생성 service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 캐시 service
	 */
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 오늘의 명언 포틀릿 페이지
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/normalView");
		
		try {
			
			//오늘의 명언 캐시에서 조회
			PortletWiseSaying portletWiseSaying = (PortletWiseSaying) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
			
			if(portletWiseSaying == null) {
				
				//PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
				portletWiseSaying = portletWiseSayingService.getRandomWiseSaying();
				
				String wiseSayingContents = portletWiseSaying.getWiseSayingContents();
				String wiseSayingEnglishContents = portletWiseSaying.getWiseSayingEnglishContents();
				
				if(!StringUtil.isEmpty(wiseSayingContents)) {
					wiseSayingContents = wiseSayingContents.replace("\r\n", "<br/>");
					
					portletWiseSaying.setWiseSayingContents(wiseSayingContents);
				}
				
				if(!StringUtil.isEmpty(wiseSayingEnglishContents)) {
					wiseSayingEnglishContents = wiseSayingEnglishContents.replace("\r\n", "<br/>");
					
					portletWiseSaying.setWiseSayingEnglishContents(wiseSayingEnglishContents);
				}
				
				// 캐시에 저장
				cacheService.addCacheElementPortletContent(portletId, portletConfigId, portletWiseSaying);
				
			}
			
			mav.addObject("portletWiseSaying", portletWiseSaying);
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * 오늘의 명언 포틀릿 설정 페이지
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/configView");
		
		try {
			PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
			
			portletWiseSaying = portletWiseSayingService.getRandomWiseSaying();
			
			String wiseSayingContents = portletWiseSaying.getWiseSayingContents();
			String wiseSayingEnglishContents = portletWiseSaying.getWiseSayingEnglishContents();
			
			if(!StringUtil.isEmpty(wiseSayingContents)) {
				wiseSayingContents = wiseSayingContents.replace("\r\n", "<br/>");
				
				portletWiseSaying.setWiseSayingContents(wiseSayingContents);
			}
			
			if(!StringUtil.isEmpty(wiseSayingEnglishContents)) {
				wiseSayingEnglishContents = wiseSayingEnglishContents.replace("\r\n", "<br/>");
				
				portletWiseSaying.setWiseSayingEnglishContents(wiseSayingEnglishContents);
			}
			
			mav.addObject("portletWiseSaying", portletWiseSaying);
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 리스트 페이지
	 * @param portletWiseSaying 오늘의 명언 model
	 * @param searchCondition SearchCondition
	 * @param accessResult AccessingResult
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/popupPortletWiseSayingList.do")
	public ModelAndView getPopupPortletWiseSayingList(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortletWiseSaying portletWiseSaying, SearchCondition searchCondition, 
			AccessingResult accessResult, HttpServletRequest request) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/popupPortletWiseSayingList");
		
		try {
			PortalCode portalCode = new PortalCode();
			User user = (User) getRequestAttribute("ikep.user");
			
			SearchResult<PortletWiseSaying> searchResult = portletWiseSayingService.listBySearchCondition(searchCondition);
	
			String id = request.getParameter("tempId");
			
			PortletWiseSaying wiseSaying = new PortletWiseSaying();
			
			if(!StringUtil.isEmpty(id))
			{
				wiseSaying = portletWiseSayingService.get(id);
			}
			
			mav.addObject("userLocaleCode", user.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("portalCode", portalCode);
			mav.addObject("portletWiseSaying", wiseSaying);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * 오늘의 명언 수정 폼
	 * @param wiseSayingId 오늘의 명언 아이디
	 * @param portletWiseSaying 포틀릿 설정 아이디
	 * @param status SessionStatus
	 * @param accessResult AccessingResult
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/popupPortletWiseSayingForm.do")
	public ModelAndView getPopupPortletWiseSayingForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String wiseSayingId, PortletWiseSaying portletWiseSaying, 
			SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/popupPortletWiseSayingForm");
		
		String id = portletWiseSaying.getWiseSayingId();
		
		PortletWiseSaying wiseSaying = new PortletWiseSaying();
		
		if(!StringUtil.isEmpty(id))
		{
			wiseSaying = portletWiseSayingService.get(id);
		}
		
		mav.addObject("portletWiseSaying", wiseSaying);
		
		status.setComplete();
		
		return mav;
	}
	
	/**
	 * 오늘의 명언 등록
	 * @param portletWiseSaying 오늘의 명언 정보
	 * @param accessResult AccessingResult
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @return String 등록된 오늘의 명언 아이디
	 * @throws Exception
	 */
	@RequestMapping(value = "/popupPortletWiseSayingCreate.do", method = RequestMethod.POST)
	public @ResponseBody String popupPortletWiseSayingCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortletWiseSaying portletWiseSaying, AccessingResult accessResult, 
			BindingResult result, SessionStatus status, HttpServletRequest request) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{	
			try {
				String wiseSayingId = idgenService.getNextId();
				
				portletWiseSaying.setWiseSayingId(wiseSayingId);
				
				User user = (User) getRequestAttribute("ikep.user");
				
				ModelBeanUtil.bindRegisterInfo(portletWiseSaying, user.getUserId(), user.getUserName());
	
				portletWiseSayingService.createWiseSaying(portletWiseSaying);
				
				status.setComplete();
				
				returnValue = wiseSayingId;
				
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContentAll("Cachename-wiseSaying-portlet");
				
			} catch (Exception e) {
				throw new IKEP4AjaxException("", e);
			}
		}
		
		return returnValue;
	}
	
	/**
	 * 오늘의 명언 수정
	 * @param portletWiseSaying 오늘의 명언 정보
	 * @param accessResult AccessingResult
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @return String 수정된 오늘의 명언 아이디
	 * @throws Exception
	 */
	@RequestMapping(value = "/popupPortletWiseSayingUpdate.do", method = RequestMethod.POST)
	public @ResponseBody String popupPortletWiseSayingUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@Valid PortletWiseSaying portletWiseSaying, AccessingResult accessResult, 
			BindingResult result, SessionStatus status, HttpServletRequest request) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{		
			String wiseSayingId = portletWiseSaying.getWiseSayingId();
				
			User user = (User) getRequestAttribute("ikep.user");
			
			ModelBeanUtil.bindRegisterInfo(portletWiseSaying, user.getUserId(), user.getUserName());

			try {
				portletWiseSayingService.updateWiseSaying(portletWiseSaying);
			} catch(Exception exception) {
				throw new IKEP4AjaxException("code", exception);
			}
			
			status.setComplete();
			
			returnValue = wiseSayingId;
			
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-wiseSaying-portlet");
		}
		
		return returnValue;
	}
	
	/**
	 * 오늘의 명언 삭제
	 * @param portletWiseSaying 오늘의 명언 정보
	 * @param accessResult AccessingResult
	 * @return String
	 */
	@RequestMapping(value = "/popupPortletWiseSayingDelete.do", method = RequestMethod.POST)
	public @ResponseBody String popupPortletWiseSayingDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			PortletWiseSaying portletWiseSaying, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		String wiseSayingId = portletWiseSaying.getWiseSayingId();
		
		portletWiseSayingService.delete(wiseSayingId);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContentAll("Cachename-wiseSaying-portlet");
		
		return "";
	}
	
	/**
	 * 오늘의 명언 멀티 삭제
	 * @param accessResult AccessingResult
	 * @param itemIds 오늘의 명언 아이디
	 */
	@RequestMapping(value = "/popupPortletWiseSayingMultiDelete.do", method = RequestMethod.POST)
	public @ResponseBody void popupPortletWiseSayingMultiDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			AccessingResult accessResult, @RequestParam("itemId") String[] itemIds) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		try {
			portletWiseSayingService.multiDeleteWiseSaying(itemIds);
			
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-wiseSaying-portlet");
			
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}
	
	/**
	 * 오늘의 명언 엑셀 등록 폼
	 * @param accessResult AccessingResult
	 * @param req HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/excelPortletWiseSayingForm.do")
	public ModelAndView excelPortletWiseSayingForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			AccessingResult accessResult, HttpServletRequest req) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/excelPortletWiseSayingForm");

		// 더블 서밋방지 Token 셋팅
		String token = HttpUtil.setToken(req);
		mav.addObject("token", token);

		return mav;
	}

	/**
	 * 오늘의 명언 엑셀 업로드
	 * @param accessResult AccessingResult
	 * @param file 엑셀 파일
	 * @param req HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelPortletWiseSayingUpload.do", method = RequestMethod.POST)
	public ModelAndView excelPortletWiseSayingUpload(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			AccessingResult accessResult, @RequestParam("file") CommonsMultipartFile file, HttpServletRequest req) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/portlet/wiseSaying/excelPortletWiseSayingResult");

		try {

			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				InputStream inp = file.getInputStream();

				User user = (User) getRequestAttribute("ikep.user");

				String className = "com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying";

				List<Object> list = ExcelUtil.readExcel(className, inp, 1);

				int successCount = 0;
				int failCount = 0;
				
				List<PortletWiseSaying> wiseSayingList = new ArrayList<PortletWiseSaying>();

				for(Object obj:list) {
					try {
						PortletWiseSaying portletWiseSaying = (PortletWiseSaying) obj;
						
						String wiseSayingId = idgenService.getNextId();
						
						if(!portletWiseSayingService.existsWiseSaying(portletWiseSaying)) {
							portletWiseSaying.setWiseSayingId(wiseSayingId);
							portletWiseSaying.setRegisterId(user.getUserId());
							portletWiseSaying.setRegisterName(user.getUserName());
							portletWiseSaying.setUpdaterId(user.getUserId());
							portletWiseSaying.setUpdaterName(user.getUserName());
	
							portletWiseSayingService.create(portletWiseSaying);
						}

						successCount++;
					} catch (Exception e) {
						PortletWiseSaying portletWiseSaying = (PortletWiseSaying) obj;
						portletWiseSaying.setSuccessYn("N");
						portletWiseSaying.setErrMsg(e.getMessage());
						wiseSayingList.add(portletWiseSaying);
						
						failCount++;
					}
				}

				mav.addObject("wiseSayingList", wiseSayingList);
				mav.addObject("totalCount", list.size());
				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
				
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContentAll("Cachename-wiseSaying-portlet");
			}
		} catch (Exception e) {
			return mav;
		}

		return mav;
	}
}