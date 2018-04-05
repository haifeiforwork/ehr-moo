package com.lgcns.ikep4.portal.portlet.web;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;
import com.lgcns.ikep4.portal.portlet.service.PortletInnovationService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 Innovation Controller
 *
 * @author 임종상
 * @version $Id: PortletInnovationController.java 19565 2012-07-02 00:27:14Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/portlet/innovation")
public class PortletInnovationController extends BaseController{

	@Autowired
	private PortletInnovationService portletInnovationService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * Innovation 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/innovation/normalView");
		
		String innovationImageYn = "N";
		
		//이노베이션 캐시에서 조회
		PortletInnovation portletInnovation = (PortletInnovation) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		//캐시가 없는 경우
		if(portletInnovation == null) {

			//저장된 데이터 가져옴
			portletInnovation = portletInnovationService.readPortletInnovation(portletConfigId);
			
			//저장데이터 있고 이미지파일도 있는 경우
			if(portletInnovation != null && !StringUtil.isEmpty(portletInnovation.getImageFileId())) {

				FileData fileData = fileService.read(portletInnovation.getImageFileId());
				
				if(fileData != null) {
					Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
					String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
					File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
					if (file.exists()) {
						innovationImageYn = "Y";
						portletInnovation.setInnovationImageYn(true);
					}
				}

			// 저장데이터가 없거나, 있으나 이미지파일은 없는 경우
			} else {
				portletInnovation = new PortletInnovation();
				portletInnovation.setInnovationImageYn(false);
			}
				
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, portletInnovation);
		
		//캐시 있고 이미지파일이 저장되어 있는 경우
		} else if(portletInnovation.getInnovationImageYn()) {
			innovationImageYn = "Y";
		}

		mav.addObject("portletInnovation", portletInnovation);
		mav.addObject("innovationImageYn", innovationImageYn);
		
		return mav;
	}
	
	/**
	 * Innovation 포틀릿 설정(configView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/innovation/configView");
		
		PortletInnovation portletInnovation = portletInnovationService.readPortletInnovation(portletConfigId);
		
		if(portletInnovation == null) {
			portletInnovation = new PortletInnovation();
			portletInnovation.setPortletConfigId(portletConfigId);
		}
		
		mav.addObject("portletInnovation", portletInnovation);
		
		return mav;
	}
	
	/**
	 * Innovation 포틀릿 등록
	 * @param portletInnovation Portlet Innovation Model
	 * @param editorAttach 에디터 여부(1 : 에디터, 0: 에디터 아님)
	 * @param request Request 객체
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createPortletInnovation.do")
	public @ResponseBody String createPortletInnovation(@ModelAttribute PortletInnovation portletInnovation, String editorAttach, HttpServletRequest request
			) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {

			portletInnovationService.createPortletInnovation(portletInnovation, editorAttach, user);

			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-innovation-portlet");
			
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