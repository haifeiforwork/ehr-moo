package com.lgcns.ikep4.support.rss.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Channel 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelService.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Transactional
public interface ChannelService extends GenericService<Channel, String> {

	/**
	 * 채널 조회
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Channel> getAllChannel(ChannelSearchCondition searchCondition);
	

	/**
	 * 채널 순서 정렬
	 * 
	 * @param channelList
	 * @param channelId
	 * @param sortOrder
	 * @param ownerId
	 */
	public void changeSort(List<Channel> channelList, String channelId, int sortOrder, String ownerId);

	/**
	 * 채널 유효성 체크
	 * 
	 * @param map
	 * @return
	 */
	public boolean checkChannel(Map<String, String> map);

	/**
	 * 채널 읽어오기
	 * 
	 * @param searchCondition
	 * @param request
	 */
	public void readChannel(ChannelSearchCondition searchCondition, HttpServletRequest request);

	/**
	 * 채널 발행하기
	 * 
	 * @param messageSource
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @param request
	 * @param user
	 * @return
	 */
	public String sendChannel(MessageSource messageSource, String feedType, String metaType, String metaId,
			HttpServletRequest request, User user);

	/**
	 * 메타 타이틀 가져오기
	 * 
	 * @param messageSource
	 * @param metaType
	 * @param metaId
	 * @return
	 */
	public String getMetaTitle(MessageSource messageSource, String metaType, String metaId);

	/**
	 * 채널 읽어오기(포틀릿용)
	 * 
	 * @param channelUrl
	 * @return
	 */
	public Channel readChannelForPortlet(String channelUrl);
}
