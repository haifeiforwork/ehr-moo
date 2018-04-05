/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeLayoutService;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletLayoutService;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafePortletService;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * CafePortletController
 * 
 * @author 유승목
 * @version $Id: CafePortletController.java 16573 2011-09-21 02:06:10Z giljae $
 */
@Controller
@RequestMapping(value = "/lightpack/cafe/cafelayout")
@SessionAttributes("cafe")
public class CafePortletController extends BaseController {

	@Autowired
	private CafeService cafeService;

	@Autowired
	private CafePortletService cafePortletService;

	@Autowired
	private CafePortletLayoutService cafePortletLayoutService;

	@Autowired
	private CafeLayoutService cafeLayoutService;

	@Autowired
	private ACLService aclService;

	/**
	 * WS 전체관리자 여부 권한 확인
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(
				IKepConstant.ITEM_TYPE_CODE_CAFE, user.getUserId());
		// isSystemAdmin = true;
		return isSystemAdmin;
	}

	/**
	 * cafe 포틀릿 설정 페이지
	 * 
	 * @param cafeId
	 * @return
	 */
	@RequestMapping(value = "/layoutManageView.do", method = RequestMethod.GET)
	public ModelAndView layoutManageView(
			@RequestParam(value = "cafeId", required = false) String cafeId) {

		ModelAndView mav = new ModelAndView(
				"lightpack/cafe/cafelayout/layoutManageView");

		Cafe cafe = new Cafe();
		cafe.setCafeId(cafeId);
		Cafe recafe = cafeService.read(cafeId);

		// 포틀릿 전체 리스트
		List<CafePortlet> cafePortletList = cafePortletService
				.listAllCafePortlet(cafeId);
		mav.addObject("cafePortletList", cafePortletList);

		// 내가 저장하고 있는 Portlet Layout
		List<CafePortletLayout> cafePortletLayoutList = cafePortletLayoutService
				.listLayoutByCafe(cafeId);
		mav.addObject("cafePortletLayoutList", cafePortletLayoutList);
		mav.addObject("cafeId", cafeId);

		// 레이아웃 관련 전부 가져 오는 부분
		List<CafeLayout> cafeLayoutList = cafeLayoutService.listCafeLayoutAll();
		mav.addObject("cafeLayoutList", cafeLayoutList);

		// 내가 사용 하는 Layout
		CafeLayout myCafeLayout = cafeLayoutService.cafeLayoutByOwnerId(recafe);
		mav.addObject("myCafeLayout", myCafeLayout);
		mav.addObject("myCafeLayoutColumn",
				myCafeLayout.getCafeLayoutColumnList());

		mav.addObject("cafe", recafe);

		return mav;
	}

	/**
	 * 레이아웃 정보 저장
	 * 
	 * @param CafeId
	 * @param portletId
	 * @param rowIndex
	 */
	@RequestMapping(value = "/saveLayoutManage.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void saveLayoutManage(
			@RequestParam(value = "cafeId", required = false) String cafeId,
			@RequestParam(value = "layoutId", required = false) String layoutId,
			@RequestParam(value = "portletId", required = false) String[] portletId,
			@RequestParam(value = "rowIndex", required = false) String[] rowIndex) {

		try {

			User user = (User) this.getRequestAttribute("ikep.user");

			List<CafePortletLayout> cafePortletLayoutList = new ArrayList<CafePortletLayout>();

			int colIndex1 = 0;
			if (portletId != null) {
				for (int i = 0; i < portletId.length; i++) {

					CafePortletLayout cafePortletLayout = new CafePortletLayout();

					List<String> portletIds = StringUtil.getTokens(
							portletId[i], "@");// portletId/boardId

					cafePortletLayout.setPortletId(portletIds.get(0));

					if (portletIds.size() > 1 && portletIds.get(1) != null
							&& !portletIds.get(1).equals("")) {
						cafePortletLayout.setBoardId(portletIds.get(1));
						cafePortletLayout.setIsBoardPortlet(1);
					} else {
						cafePortletLayout.setIsBoardPortlet(0);
					}
					if (Integer.parseInt(rowIndex[i]) == 0) {
						colIndex1++;
					}

					cafePortletLayout.setColIndex(colIndex1);
					cafePortletLayout
							.setRowIndex(Integer.parseInt(rowIndex[i]) + 1);

					cafePortletLayoutList.add(cafePortletLayout);
				}
			}
			
			Cafe cafe = new Cafe();
			cafe.setCafeId(cafeId);
			cafe.setLayoutId(layoutId);
			cafe.setUpdaterId(user.getUserId());
			cafe.setUpdaterName(user.getUserName());

			cafePortletLayoutService.saveCafePortletLayout(cafe,
					cafePortletLayoutList);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);

		}

	}

}
