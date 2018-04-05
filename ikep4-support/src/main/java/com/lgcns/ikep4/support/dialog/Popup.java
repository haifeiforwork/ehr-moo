package com.lgcns.ikep4.support.dialog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.web.BaseController;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 유화선
 * @version $Id: Popup.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
public class Popup extends BaseController {

	@RequestMapping("support/dialog/dialog.do")
	public String dialog() {
		return "support/dialog/dialog";
	}
	
	@RequestMapping("support/popup/popupPortlet.do")
	public String popupPortlet() {
		return "support/popup/popupPortlet";
	}
}
