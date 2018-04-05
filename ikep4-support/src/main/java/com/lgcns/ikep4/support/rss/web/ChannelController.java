package com.lgcns.ikep4.support.rss.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelCategoryService;
import com.lgcns.ikep4.support.rss.service.ChannelService;
import com.lgcns.ikep4.support.rss.util.RssUtil;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Channel 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelController.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Controller
@RequestMapping(value = "/support/rss/channel")
@SessionAttributes("channel")
public class ChannelController extends BaseController {
	
	/**
	 * 채널 서비스
	 */
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelCategoryService channelCategoryService;

	/**
	 * 채널 등록 폼
	 * 
	 * @param channelId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createChannel.do")
	public String getForm(String channelId, Model model) {

		try {
			Channel channel = null;
			if (channelId != null) {
				channel = channelService.read(channelId);
			} else {
				if (log.isDebugEnabled()) {
					log.debug("If channelId is NULL, create a new channel");
				}
				channel = new Channel();
			}
			
			User user = (User) getRequestAttribute("ikep.user");
			List<ChannelCategory> categoryList = channelCategoryService.list(user.getUserId());
			
			model.addAttribute("channel", channel);
			model.addAttribute("categoryList", categoryList);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return "/support/rss/channel/form";
	}

	/**
	 * 채널 저장
	 * 
	 * @param channel
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createChannel.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(@ValidEx Channel channel, BindingResult result, SessionStatus status) {

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			if (result.hasErrors()) {
				throw new IKEP4AjaxValidationException(result, messageSource);
			}

			User user = (User) getRequestAttribute("ikep.user");

			String channelId = channel.getChannelId();

			channel.setOwnerId(user.getUserId());
			channel.setRegisterId(user.getUserId());
			channel.setRegisterName(user.getUserName());
			channel.setUpdaterId(user.getUserId());
			channel.setUpdaterName(user.getUserName());

			Map<String, String> map = new HashMap<String, String>();
			map.put("channelUrl", channel.getChannelUrl());
			map.put("ownerId", channel.getOwnerId());
			map.put("categoryId", channel.getCategoryId());

			if (!channelService.checkChannel(map)) {
				channelId = channelService.create(channel);
			} else {
				channelService.update(channel);
			}

			if (channelId.equals("maxError")) {
				reMap.put("status", "maxError");
				reMap.put("message",
						messageSource.getMessage("ui.support.rss.message.maxSize", null, new Locale(user.getLocaleCode())));
			} else if (channelId.equals("dupError")) {
				reMap.put("status", "dupError");
				reMap.put(
						"message",
						messageSource.getMessage("ui.support.rss.message.channelName.duplicated", null,
								new Locale(user.getLocaleCode())));
			} else {
				reMap.put("status", "success");
				reMap.put(
						"message",
						messageSource.getMessage("ui.support.rss.message.channel.insert.success", null,
								new Locale(user.getLocaleCode())));
			}

			return objectMapper.writeValueAsString(reMap);

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

	}

	/**
	 * 채널 등록 폼 (게시판 링크용)
	 * 
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createChannelForm.do")
	public String createChannelForm(String feedType, String metaType, String metaId, Model model,
			HttpServletRequest request) {

		try {
			Channel channel = new Channel();

			String channelUrl = RssUtil.makeChannelUrl(feedType, metaType, metaId, request);
			String channelTitle = channelService.getMetaTitle(messageSource, metaType, metaId);

			channel.setChannelUrl(channelUrl);
			channel.setChannelTitle(channelTitle + " (" + feedType.toUpperCase() + ")");

			model.addAttribute("channel", channel);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return "support/rss/channel/createForm";
	}

	/**
	 * 채널 정보 검색
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getChannel.do")
	public ModelAndView getView(String channelId) {

		ModelAndView mav = new ModelAndView("/support/rss/channel/view");

		try {
			if (channelId != null) {
				Channel channel = channelService.read(channelId);
				mav.addObject("channel", channel);
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 채널 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/getChannelList.do")
	public ModelAndView getChannelList(ChannelSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/rss/channel/channelList");

		try {

			Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");
			String maxSize = prop.getProperty("ikep4.support.rss.channel.maxSize");

			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setOwnerId(user.getUserId());
			searchCondition.setPagePerRecord(Integer.parseInt(maxSize));
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			SearchResult<Channel> searchResult = channelService.getAllChannel(searchCondition);

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
	 * 채널 리스트 (요약리스트)
	 * 
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/getChannelSummary.do")
	public ModelAndView getChannelSummary(ChannelSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/rss/channelitem/channelSummary");

		try {

			Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");
			String maxSize = prop.getProperty("ikep4.support.rss.channel.maxSize");

			User user = (User) getRequestAttribute("ikep.user");		
			
			searchCondition.setOwnerId(user.getUserId());
			searchCondition.setPagePerRecord(Integer.parseInt(maxSize));
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}
			searchCondition.setPagePerRecord(100);

			SearchResult<ChannelCategory> searchResult = channelCategoryService.getAll(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("categoryList", null);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	/**
	 * 채널 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getList.do")
	public ModelAndView getList() {

		ModelAndView mav = new ModelAndView("support/rss/channel/list");

		return mav;
	}

	/**
	 * 채널 삭제
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/removeChannel.do")
	public String removeChannel(String channelId) {

		try {
			channelService.delete(channelId);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return "redirect:/support/rss/channel/createChannel.do";
	}	

	/**
	 * 채널 이름 가져오기
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getChannelTitle.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Channel getChannelTitle(String channelId) {

		Channel channel;
		try {

			channel = channelService.read(channelId);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return channel;
	}

	/**
	 * 채널 정렬 순서 변경 (UP)
	 * 
	 * @param channelId
	 * @param sortOrder
	 * @return
	 */
	@RequestMapping(value = "/changeSort.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String changeSort(String channelId, String sortOrder) {

		try {
			User user = (User) getRequestAttribute("ikep.user");

			Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");
			String maxSize = prop.getProperty("ikep4.support.rss.channel.maxSize");

			ChannelSearchCondition searchCondition = new ChannelSearchCondition();

			searchCondition.setOwnerId(user.getUserId());
			searchCondition.setPagePerRecord(Integer.parseInt(maxSize));
			searchCondition.setNotInChannelId(channelId);
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}
			searchCondition.setPagePerRecord(100);

			SearchResult<ChannelCategory> searchResult = channelCategoryService.getAll(searchCondition);
//TODO:복원 해야 함
			//channelService.changeSort(searchResult.getEntity(), channelId, Integer.parseInt(sortOrder),
			//		user.getUserId());

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 채널 URL 유효성 체크
	 * 
	 * @param channelUrl
	 * @return
	 */
	@RequestMapping(value = "/checkChannel.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView checkChannel(String channelUrl) {

		ModelAndView mav = new ModelAndView("/support/rss/channel/output");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			Map<String, String> map = new HashMap<String, String>();
			map.put("ownerId", user.getUserId());
			map.put("channelUrl", channelUrl);
		
			//boolean check = false;
			boolean check = channelService.checkChannel(map);

			if (check) {
				mav.addObject("reStr", "dupError");
			} else {

				Channel readChannel = null;
				try {
					readChannel = RssUtil.readRss(channelUrl);
					if (readChannel.getChannelItemList() == null) {
						mav.addObject("reStr", "urlError");
					} else {
						mav.addObject("reStr", readChannel.getChannelTitle());
					}
				} catch (Exception e) {
					mav.addObject("reStr", "urlError");
				}
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
	
	/**
	 * 채널 URL 유효성 체크
	 * 
	 * @param channelUrl
	 * @return
	 */
	@RequestMapping(value = "/checkChannelExist.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView checkChannel(String channelUrl, String channelId) {

		ModelAndView mav = new ModelAndView("/support/rss/channel/output");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			Map<String, String> map = new HashMap<String, String>();
			map.put("ownerId", user.getUserId());
			map.put("channelUrl", channelUrl);
			map.put("channelId", channelId);

			//boolean check = false;
			boolean check = channelService.checkChannel(map);

			if (check) {
				mav.addObject("reStr", "dupError");
			} else {

				Channel readChannel = null;
				try {
					readChannel = RssUtil.readRss(channelUrl);
					if (readChannel.getChannelItemList() == null) {
						mav.addObject("reStr", "urlError");
					} else {
						mav.addObject("reStr", readChannel.getChannelTitle());
					}
				} catch (Exception e) {
					mav.addObject("reStr", "urlError");
				}
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	/**
	 * 채널 URL 중복 등록 체크
	 * 
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @return
	 */
	@RequestMapping(value = "/checkDupChannel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String checkDupChannel(String feedType, String metaType, String metaId, HttpServletRequest request) {

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			User user = (User) getRequestAttribute("ikep.user");

			String channelUrl = RssUtil.makeChannelUrl(feedType, metaType, metaId, request);

			Map<String, String> map = new HashMap<String, String>();
			map.put("ownerId", user.getUserId());
			map.put("channelUrl", channelUrl);

			boolean check = channelService.checkChannel(map);

			if (check) {
				reMap.put("status", "dupError");
				reMap.put(
						"message",
						messageSource.getMessage("ui.support.rss.message.channelCheck.duplicated", null,
								new Locale(user.getLocaleCode())));
				reMap.put("title",
						messageSource.getMessage("ui.support.rss.channel.detail.main.title", null, new Locale(user.getLocaleCode())));
			} else {
				reMap.put("status", "success");
				reMap.put(
						"message",
						messageSource.getMessage("ui.support.rss.message.channelCheck.success", null,
								new Locale(user.getLocaleCode())));
				reMap.put("title",
						messageSource.getMessage("ui.support.rss.channel.detail.main.title", null, new Locale(user.getLocaleCode())));
			}

			return objectMapper.writeValueAsString(reMap);

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
	}

	/**
	 * 채널 이름 중복 등록 체크
	 * 
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @return
	 */
	@RequestMapping(value = "/checkDupChannelName.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String checkDupChannelName(Channel channel) {

		try {
			User user = (User) getRequestAttribute("ikep.user");

			Map<String, String> map = new HashMap<String, String>();
			map.put("ownerId", user.getUserId());
			map.put("channelTitle", channel.getChannelTitle());

			boolean check = channelService.checkChannel(map);

			if (check) {
				return "dupError";
			} else {
				return "success";
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
	}

	/**
	 * RSS/ATOM 채널 및 아이템 정보 읽기
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/readChannel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String readChannel(String channelId, String categoryId, HttpServletRequest request) {

		try {
			setRequestAttribute("rssReading", "Y");

			ChannelSearchCondition searchCondition = new ChannelSearchCondition();
			
			if(!StringUtil.isEmpty(channelId))
				searchCondition.setChannelId(channelId);
			if(!StringUtil.isEmpty(categoryId))
				searchCondition.setCategoryId(categoryId);

			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setPagePerRecord(1000);
			searchCondition.setOwnerId(user.getUserId());

			channelService.readChannel(searchCondition, request);

			setRequestAttribute("rssReading", "N");
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * RSS/ATOM 채널 및 아이템 정보 읽기 중지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/stopChannel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String stopChannel() {

		try {
			setRequestAttribute("rssReading", "N");
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return "success";
	}

	/**
	 * RSS/ATOM 채널 및 아이템 정보 발행
	 * 
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @return
	 */
	@RequestMapping(value = "/sendChannel.do")
	public ModelAndView sendChannel(String feedType, String metaType, String metaId, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/support/rss/channel/output");

		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			String rssXml = channelService.sendChannel(messageSource, feedType, metaType, metaId, request, user);

			mav.addObject("reStr", rssXml);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
}
