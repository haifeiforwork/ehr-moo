package com.lgcns.ikep4.support.rss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.dao.ChannelCategoryDao;
import com.lgcns.ikep4.support.rss.dao.ChannelDao;
import com.lgcns.ikep4.support.rss.dao.ChannelItemDao;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelService;
import com.lgcns.ikep4.support.rss.util.RssConstant;
import com.lgcns.ikep4.support.rss.util.RssUtil;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Channel 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service
public class ChannelServiceImpl extends GenericServiceImpl<Channel, String> implements ChannelService {

	/**
	 * 채널 Dao
	 */
	@Autowired
	private ChannelDao channelDao;

	/**
	 * 채널 아이템 Dao
	 */
	@Autowired
	private ChannelItemDao channelItemDao;
	
	/**
	 * 채널 카테고리 Dao
	 */
	@Autowired
	private ChannelCategoryDao channelCategoryDao;

	/**
	 * 아이디생성 Dao
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * 채널 생성
	 */
	public String create(Channel channel) {

		try {
			Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");
			String maxSize = prop.getProperty("ikep4.support.rss.channel.maxSize");

			ChannelSearchCondition searchCondition = new ChannelSearchCondition();
			searchCondition.setOwnerId(channel.getOwnerId());
			Integer count = channelDao.countBySearchCondition(searchCondition);

			if (Integer.parseInt(maxSize) >= count) {

				Map<String, String> map = new HashMap<String, String>();
				map.put("ownerId", channel.getRegisterId());
				map.put("channelTitle", channel.getChannelTitle());

				if (channelDao.checkChannel(map)) {
					return "dupError";
				} else {

					channel.setChannelId(idgenService.getNextId());
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("ownerId", channel.getOwnerId());
					channel.setSortOrder(channelDao.getSortOrder(map2));
					channelDao.create(channel);

					return "ok";
				}
			} else {
				return "maxError";
			}

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
	}

	/**
	 * 채널 수정
	 */
	public void update(Channel channel) {
		channelDao.update(channel);
	}

	/**
	 * 채널 삭제
	 */
	public void delete(String id) {
		channelDao.remove(id);
	}

	/**
	 * 채널 검색
	 */
	public Channel read(String id) {
		return channelDao.get(id);
	}
	
	/**
	 * 채널 리스트 검색
	 */
	public SearchResult<Channel> getAllChannel(ChannelSearchCondition searchCondition) {

		Integer count = channelDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Channel> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Channel>(searchCondition);

		} else {

			List<Channel> channelList = channelDao.getAll(searchCondition);

			searchResult = new SearchResult<Channel>(channelList, searchCondition);
		}

		return searchResult;

	}
	
	/**
	 * 채널 정렬
	 */
	public void changeSort(List<Channel> channelList, String channelId, int sortOrder, String ownerId) {

		int i = 0;
		int newOrder = 100000;
		String preChannelId;

		try {

			if (channelList != null) {
				for (Channel channel : channelList) {

					preChannelId = channel.getChannelId();

					if (i == sortOrder) {
						channel.setChannelId(channelId);
						channel.setSortOrder(String.valueOf(newOrder));
						channelDao.updateSortOrder(channel);
						newOrder++;
					}

					channel.setChannelId(preChannelId);
					channel.setSortOrder(String.valueOf(newOrder));
					channelDao.updateSortOrder(channel);

					newOrder++;
					i++;
				}
			}

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

	}

	/**
	 * 채널 체크
	 */
	public boolean checkChannel(Map<String, String> map) {
		return channelDao.checkChannel(map);
	}

	/**
	 * 채널 읽어오기
	 */
	public void readChannel(ChannelSearchCondition searchCondition, HttpServletRequest request) {

		try {
			Integer count = channelDao.countBySearchCondition(searchCondition);

			searchCondition.terminateSearchCondition(count);

			List<Channel> channelList = channelDao.getAll(searchCondition);

			if (channelList != null) {
				for (Channel channel : channelList) {

					HttpSession session = request.getSession();
					String rssReading = (String) session.getAttribute("rssReading");

					if (rssReading.equals("Y")) {

						Channel readChannel = RssUtil.readRss(channel.getChannelUrl());

						if (readChannel.getChannelItemList() != null) {
							List<ChannelItem> channelItemList = new ArrayList<ChannelItem>();
							for (ChannelItem channelItem : readChannel.getChannelItemList()) {

								Map<String, String> map = new HashMap<String, String>();
								map.put("channelId", channel.getChannelId());
								map.put("itemUrl", channelItem.getItemUrl());
								
								if (!channelItemDao.checkItem(map)) {

									channelItem.setChannelId(channel.getChannelId());
									channelItem.setItemId(idgenService.getNextId());
									channelItem.setItemRegister("");
									
								
									//User user = (User) getRequestAttribute("ikep.user");
									channelItemList.add(channelItem);
									// channel Item건당 디비에 인서트하지 않고 일괄적으로 인서트하게 변경함으로 기존 한건당 입력건은 주석처리함.
									//channelItemDao.create(channelItem);
								}
							}
							// channel Item건당 디비에 인서트하지 않고 일괄적으로 인서트하는 구문 추가함.
							if(channelItemList.size()>0){
								channelItemDao.channelItemInsert(channelItemList);
							}
						}
					}
				}
			}
			
			//업데이트된 아이템의 갯수를 확인해 500건을 제외한 나머지를 모두 삭제한다
			/*int nTotalCount = channelItemDao.countBySearchCondition(searchCondition);
			
			if(nTotalCount > 500)
			{ 
				searchCondition.setStartRowIndex((nTotalCount - 500));				
				channelItemDao.cleanup(searchCondition);
			}*/
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
	}

	/**
	 * 채널 발행하기
	 */
	public String sendChannel(MessageSource messageSource, String feedType, String metaType, String metaId,
			HttpServletRequest request, User user) {

		String rssXml = "";

		try {

			Channel channel = null;

			// 전사 게시판
			if (metaType.equals(RssConstant.META_TYPE_BOARD)) {

				Channel board = channelDao.getBoard(metaId);

				Map<String, String> map = new HashMap<String, String>();
				map.put("boardId", metaId);
				List<ChannelItem> boardItemList = channelItemDao.getBoardItemList(map);

				if (boardItemList != null) {

					channel = RssUtil.getRssChannel(board, boardItemList, feedType, metaType, metaId, request,
							IKepConstant.ITEM_TYPE_CODE_BBS);
				}

				// QnA
			} else if (metaType.equals(RssConstant.META_TYPE_QNA100) || metaType.equals(RssConstant.META_TYPE_QNA200)
					|| metaType.equals(RssConstant.META_TYPE_QNA300) || metaType.equals(RssConstant.META_TYPE_QNA400)
					|| metaType.equals(RssConstant.META_TYPE_QNA500)) {

				Channel board = new Channel();

				Map<String, String> map = new HashMap<String, String>();
				map.put("categoryId", metaId);

				// 긴급 질문
				if (metaType.equals(RssConstant.META_TYPE_QNA100)) {
					board.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna100", null, new Locale(
							user.getLocaleCode())));
					map.put("urgent", "1");
					map.put("indentation", "0");

					// 미해결 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA200)) {
					board.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna200", null, new Locale(
							user.getLocaleCode())));
					map.put("isNotAdopt", "Y");
					map.put("indentation", "0");

					// 베스트 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA300)) {
					board.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna300", null, new Locale(
							user.getLocaleCode())));
					map.put("indentation", "0");
					map.put("bestQna", "1");

					// 베스트 답변
				} else if (metaType.equals(RssConstant.META_TYPE_QNA400)) {
					board.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna400", null, new Locale(
							user.getLocaleCode())));
					map.put("indentation", "1");
					map.put("bestQna", "2");

					// 카티고리별 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA500)) {
					board = channelDao.getQna(metaId);
					board.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna500", null, new Locale(
							user.getLocaleCode()))
							+ " : " + board.getChannelTitle());
					map.put("indentation", "0");
					map.put("categoryId", metaId);

				}

				List<ChannelItem> boardItemList = channelItemDao.getQnaItemList(map);

				if (boardItemList != null) {

					channel = RssUtil.getRssChannel(board, boardItemList, feedType, metaType, metaId, request,
							IKepConstant.ITEM_TYPE_CODE_QA);
				}

				// WORKSPACE
			} else if (metaType.equals(RssConstant.META_TYPE_WORKSPACE)) {
				Channel board = channelDao.getWorkspace(metaId);

				Map<String, String> map = new HashMap<String, String>();
				map.put("boardId", metaId);
				List<ChannelItem> boardItemList = channelItemDao.getWorkspaceItemList(map);

				if (boardItemList != null) {

					channel = RssUtil.getRssChannel(board, boardItemList, feedType, metaType, metaId, request,
							IKepConstant.ITEM_TYPE_CODE_WORK_SPACE);
				}
				// BLOG
			} else if (metaType.equals(RssConstant.META_TYPE_BLOG)) {
				Channel board = channelDao.getBlog(metaId);

				Map<String, String> map = new HashMap<String, String>();
				map.put("boardId", metaId);
				List<ChannelItem> boardItemList = channelItemDao.getBlogItemList(map);

				if (boardItemList != null) {

					channel = RssUtil.getRssChannel(board, boardItemList, feedType, metaType, metaId, request,
							IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG);
				}
				// CAFE)
			} else if (metaType.equals(RssConstant.META_TYPE_CAFE)) {

				Channel board = channelDao.getCafeBoard(metaId);

				Map<String, String>map = new HashMap<String, String>();
				map.put("boardId", metaId);
				List<ChannelItem> boardItemList = channelItemDao.getCafeBoardItemList(map);

				if (boardItemList != null) {

					channel = RssUtil.getRssChannel(board, boardItemList, feedType, metaType, metaId, request,
							IKepConstant.ITEM_TYPE_CODE_CAFE);
				}
			}

			rssXml = RssUtil.sendRss(channel);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return rssXml;
	}

	/**
	 * 메타 타이틀 얻어오기
	 */
	public String getMetaTitle(MessageSource messageSource, String metaType, String metaId) {

		String title = "";

		try {
			// 전사 게시판
			if (metaType.equals(RssConstant.META_TYPE_BOARD)) {

				Channel board = channelDao.getBoard(metaId);
				title = board.getChannelTitle();

				// QnA
			} else if (metaType.equals(RssConstant.META_TYPE_QNA100) || metaType.equals(RssConstant.META_TYPE_QNA200)
					|| metaType.equals(RssConstant.META_TYPE_QNA300) || metaType.equals(RssConstant.META_TYPE_QNA400)
					|| metaType.equals(RssConstant.META_TYPE_QNA500)) {

				// 긴급 질문
				if (metaType.equals(RssConstant.META_TYPE_QNA100)) {
					title = messageSource.getMessage("ui.support.rss.message.qna100", null, Locale.getDefault());

					// 미해결 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA200)) {
					title = messageSource.getMessage("ui.support.rss.message.qna200", null, Locale.getDefault());

					// 베스트 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA300)) {
					title = messageSource.getMessage("ui.support.rss.message.qna300", null, Locale.getDefault());

					// 베스트 답변
				} else if (metaType.equals(RssConstant.META_TYPE_QNA400)) {
					title = messageSource.getMessage("ui.support.rss.message.qna400", null, Locale.getDefault());

					// 카티고리별 질문
				} else if (metaType.equals(RssConstant.META_TYPE_QNA500)) {
					Channel qna = channelDao.getQna(metaId);
					qna.setChannelTitle(messageSource.getMessage("ui.support.rss.message.qna500", null,
							Locale.getDefault())
							+ " : " + qna.getChannelTitle());
					title = qna.getChannelTitle();
				}

				// WORKSPACE
			} else if (metaType.equals(RssConstant.META_TYPE_WORKSPACE)) {
				Channel board = channelDao.getWorkspace(metaId);
				title = board.getChannelTitle();
				// BLOG
			} else if (metaType.equals(RssConstant.META_TYPE_BLOG)) {
				Channel board = channelDao.getBlog(metaId);
				title = board.getChannelTitle();
				// CAFE)
			} else if (metaType.equals(RssConstant.META_TYPE_CAFE)) {
				Channel board = channelDao.getCafeBoard(metaId);
				title = board.getChannelTitle();
			}

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return title;
	}

	/**
	 * 채널읽기 (포틀릿용)
	 */
	public Channel readChannelForPortlet(String channelUrl) {

		Channel readChannel = null;

		try {
			readChannel = RssUtil.readRss(channelUrl);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return readChannel;
	}
}
