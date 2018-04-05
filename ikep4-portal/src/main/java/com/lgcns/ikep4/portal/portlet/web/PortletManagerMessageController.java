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

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;
import com.lgcns.ikep4.portal.portlet.service.PortletManagerMessageService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 ManagerMessage Controller
 *
 * @author 박순성
 * @version $Id: PortletManagerMessageController.java 11714 2011-05-18 05:49:19Z limjongsang $
 */
@Controller
@RequestMapping(value = "/portal/portlet/managermessage")
public class PortletManagerMessageController extends BaseController{

	@Autowired
	private PortletManagerMessageService portletManagerMessageService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * ManagerMessage 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/managermessage/normalView");
		
		String managermessageImageYn = "N";
		String managermessageTitleYn = "N";
		
		PortletManagerMessage portletManagerMessage = portletManagerMessageService.readPortletManagerMessage();

		if(portletManagerMessage != null) {
			if(!StringUtil.isEmpty(portletManagerMessage.getSummary())) {
				String summary = portletManagerMessage.getSummary().replaceAll("\r\n", "<br/>");
				
				portletManagerMessage.setSummary(summary);
			}
			
			if(!StringUtil.isEmpty(portletManagerMessage.getImageFileId())) {
				FileData fileData = fileService.read(portletManagerMessage.getImageFileId());
			
				if(fileData != null) {
					Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
					String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
					File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
					if (file.exists()) {
						managermessageImageYn = "Y"; 
					}
				}
			}
			
			if(!StringUtil.isEmpty(portletManagerMessage.getTitleFileId())) {
				FileData fileData = fileService.read(portletManagerMessage.getTitleFileId());
			
				if(fileData != null) {
					Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
					String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
					File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
					if (file.exists()) {
						managermessageTitleYn = "Y"; 
					}
				}
			}
		}
		
		mav.addObject("portletManagerMessage", portletManagerMessage);
		mav.addObject("managermessageImageYn", managermessageImageYn);
		mav.addObject("managermessageTitleYn", managermessageTitleYn);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}
	
	/**
	 * ManagerMessage 포틀릿 설정(configView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/managermessage/configView");
		
		PortletManagerMessage portletManagerMessage = portletManagerMessageService.readPortletManagerMessage();
		
		if(portletManagerMessage == null) {
			portletManagerMessage = new PortletManagerMessage();
		}
		
		mav.addObject("portletManagerMessage", portletManagerMessage);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}
	
	/**
	 * ManagerMessage 포틀릿 등록
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 * @param editorAttach 에디터 여부(1 : 에디터, 0: 에디터 아님)
	 * @param request Request 객체
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createPortletManagerMessage.do")
	public @ResponseBody String createPortletManagerMessage(@ModelAttribute PortletManagerMessage portletManagerMessage, String editorAttach, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			portletManagerMessageService.createPortletManagerMessage(portletManagerMessage, fileList, editorAttach, user);
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