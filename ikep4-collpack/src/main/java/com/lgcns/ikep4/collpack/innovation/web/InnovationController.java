/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.innovation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Qna controller
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: InnovationController.java 16647 2011-09-26 02:16:42Z giljae $$
 */
@Controller
public class InnovationController extends InnovationBaseController { 

	/**
	 * 메인
	 * @return
	 */
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main()  {
		
		
		return "collpack/innovation/main";
	}

	/**
	 * 서브
	 * @return
	 */
	@RequestMapping(value = "/subMain.do", method = RequestMethod.GET)
	public String subBody()  {
		
		
		return "collpack/innovation/subMain";
	}

	/**
	 * 서브
	 * @return
	 */
	@RequestMapping(value = "/activityList.do", method = RequestMethod.GET)
	public String activityList()  {
		
		
		return "collpack/innovation/activityList";
	}
	
}
