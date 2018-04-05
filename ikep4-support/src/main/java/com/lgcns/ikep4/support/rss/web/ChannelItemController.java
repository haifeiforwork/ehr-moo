package com.lgcns.ikep4.support.rss.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelItemService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Channel Item 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItemController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/rss/channelitem")
public class ChannelItemController extends BaseController {
	
	/**
	 * 채널 아이템 서비스
	 */
	@Autowired
	private ChannelItemService channelItemService;

	public ModelAndView getView(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 아이템 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getChannelItemList.do")
	public ModelAndView getChannelItemList(ChannelSearchCondition searchCondition, String channelId) {

		ModelAndView mav = new ModelAndView("/support/rss/channelitem/channelItemList");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setChannelId(channelId);
			searchCondition.setOwnerId(user.getUserId());
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ITEM_PUBLISH_DATE");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("DESC");
			}

			SearchResult<ChannelItem> searchResult = channelItemService.getAll(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 아이템 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getList.do")
	public ModelAndView getList() {

		ModelAndView mav = new ModelAndView("support/rss/channelitem/list");

		return mav;
	}

	/**
	 * 아이템 리스트 검색(요약 리스트)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getChannelItemSummary.do")
	public ModelAndView getChannelItemSummary() {

		ModelAndView mav = new ModelAndView("/support/rss/channelitem/channelItemSummary");

		try {
			ChannelSearchCondition searchCondition = new ChannelSearchCondition();

			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setPagePerRecord(10);
			searchCondition.setOwnerId(user.getUserId());
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ITEM_PUBLISH_DATE");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("DESC");
			}

			SearchResult<ChannelItem> searchResult = channelItemService.getAll(searchCondition);

			mav.addObject("searchResult", searchResult);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	/**
	 * 아이템 요약 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSummary.do")
	public ModelAndView getSummary() {

		ModelAndView mav = new ModelAndView("support/rss/channelitem/summary");

		return mav;
	}

	/**
	 * 아이템 삭제
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/removeChannelItem.do")
	public String removeChannelItem(String itemId) {

		try {
			channelItemService.delete(itemId);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return "redirect:/support/rss/channelitem/getChannelItemList.do";
	}
}
