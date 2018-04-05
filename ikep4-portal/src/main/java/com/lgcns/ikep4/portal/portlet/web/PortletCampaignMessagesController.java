package com.lgcns.ikep4.portal.portlet.web;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.PortletCampaignMessages;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;
import com.lgcns.ikep4.portal.portlet.service.PortletCampaignMessagesService;
import com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * Portlet Campaign Messages Controller
 *
 * @author 최재영
 * @version $Id: PortletCampaignMessagesController.java 11714 2011-05-18 05:49:19Z $
 */
@Controller
@RequestMapping(value = "/portal/portlet/campaignmessages")
public class PortletCampaignMessagesController extends BaseController{

	@Autowired
	private PortletCampaignMessagesService portletCampaignMessagesService;
	
	@Autowired
	private PortletWiseSayingService portletWiseSayingService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * ManagerMessage 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/campaignmessages/normalView");
		
		String campaignmessagesImageYn = "N";
		
		String managementPolicyImageYn = "N";
		
		try{
			//캠페인을 로딩한다
			PortletCampaignMessages portletCampaignMessages = portletCampaignMessagesService.readPortletCampaignMessages();

			if(portletCampaignMessages != null) {
				
				if(!StringUtil.isEmpty(portletCampaignMessages.getSummary())) {
					
					String summary = portletCampaignMessages.getSummary().replaceAll("\r\n", "<br/>");
				
					portletCampaignMessages.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(portletCampaignMessages.getImageFileId())) {
				
					FileData fileData = fileService.read(portletCampaignMessages.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							campaignmessagesImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("portletCampaignMessages", portletCampaignMessages);
			mav.addObject("campaignmessagesImageYn", campaignmessagesImageYn);
			
			PortletCampaignMessages portletManagementPolicy = portletCampaignMessagesService.readPortletManagementPolicy();

			if(portletManagementPolicy != null) {
				
				if(!StringUtil.isEmpty(portletManagementPolicy.getSummary())) {
					
					String summary = portletManagementPolicy.getSummary().replaceAll("\r\n", "<br/>");
				
					portletManagementPolicy.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(portletManagementPolicy.getImageFileId())) {
				
					FileData fileData = fileService.read(portletManagementPolicy.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							managementPolicyImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("portletManagementPolicy", portletManagementPolicy);
			mav.addObject("managementPolicyImageYn", managementPolicyImageYn);
						
			//오늘의 명언 캐시에서 조회
			/*PortletWiseSaying portletWiseSaying = (PortletWiseSaying) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
			
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
				
			}*/
			
			//mav.addObject("portletWiseSaying", portletWiseSaying);
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * ManagerMessage 포틀릿 설정(configView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/campaignmessages/configView");
		
		PortletCampaignMessages portletCampaignMessages = portletCampaignMessagesService.readPortletCampaignMessages();
		
		if(portletCampaignMessages == null) {
			portletCampaignMessages = new PortletCampaignMessages();
		}
		
		mav.addObject("portletCampaignMessages", portletCampaignMessages);
		mav.addObject("portletConfigId", portletConfigId);
		
		PortletCampaignMessages portletManagementPolicy = portletCampaignMessagesService.readPortletManagementPolicy();
		
		if(portletManagementPolicy == null) {
			portletManagementPolicy = new PortletCampaignMessages();
		}
		
		mav.addObject("portletManagementPolicy", portletManagementPolicy);
		//mav.addObject("portletConfigId", portletConfigId);
		
		
		/*PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
		
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
		
		mav.addObject("portletWiseSaying", portletWiseSaying);*/
		//mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}
	
	/**
	 * ManagerMessage 포틀릿 등록
	 * @param portletCampaignMessages Portlet ManagerMessage Model
	 * @param editorAttach 에디터 여부(1 : 에디터, 0: 에디터 아님)
	 * @param request Request 객체
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createPortletCampaignMessages.do")
	public @ResponseBody String createPortletCampaignMessages(@ModelAttribute PortletCampaignMessages portletCampaignMessages, String editorAttach, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			portletCampaignMessagesService.createPortletCampaignMessages(portletCampaignMessages, fileList, editorAttach, user);
		} catch (Exception e) {
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/createPortletManagementPolicy.do")
	public @ResponseBody String createPortletManagementPolicy(@ModelAttribute PortletCampaignMessages portletCampaignMessages, String editorAttach, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			portletCampaignMessagesService.createPortletManagementPolicy(portletCampaignMessages, fileList, editorAttach, user);
		} catch (Exception e) {
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}
		}
		
		return "success";
	}
	
}