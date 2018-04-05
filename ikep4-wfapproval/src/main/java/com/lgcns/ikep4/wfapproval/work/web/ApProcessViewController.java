/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.wfapproval.work.service.ApProcessViewService;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;


/**
 * 결재 양식 선택 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>양식 목록 조회</li>
 * <li>양식 선택</li>
 * </pre>
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApProcessViewController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/work")
//@SessionAttributes("aplist")
public class ApProcessViewController extends BaseController {

	@Autowired
	private ApProcessViewService apProcessViewService;

	/**
	 * Approval Process View XML.
	 * 
	 * @param 
	 * @param 
	 * @return redirect
	 * @throws IOException
	 */
	@RequestMapping(value = "/apProcessViewXml", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void apProcessViewXml( ProcessModel processModel,
			                      HttpServletRequest req,
			                      HttpServletResponse res) throws IOException {
		
		String apprId = req.getParameter("apprId");
		
		String apProcessViewXml = (apprId==null)?"":apProcessViewService.getApProcessXMLData(apprId);
		
		if (log.isDebugEnabled()) {
			log.debug("apProcessViewXml : "+apProcessViewXml);
		}
		
		res.getOutputStream().write(apProcessViewXml.getBytes());
		res.setContentType("text/xml;charset=utf-8");
		res.setHeader("Cache-Control", "no-cache");
	}	

}
