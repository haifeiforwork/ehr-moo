package com.lgcns.ikep4.support.rss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.dao.ChannelItemDao;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelItemService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Channel Item 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItemServiceImpl.java 1195 2011-02-24 00:44:16Z handul32
 *          $
 */
@Service
public class ChannelItemServiceImpl extends GenericServiceImpl<ChannelItem, String> implements ChannelItemService {
	
	/**
	 * 채널 아이템 Dao
	 */
	@Autowired
	private ChannelItemDao channelItemDao;
	
	/**
	 * 아이디 생성 Dao
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 채널 아이템 생성
	 */
	public String create(ChannelItem channelItem) {
		channelItem.setItemId(idgenService.getNextId());
		return channelItemDao.create(channelItem);
	}
	
	/**
	 * 채널 아이템 수정
	 */
	public void update(ChannelItem channelItem) {
		channelItemDao.update(channelItem);
	}
	
	/**
	 * 채널 아이템 삭제
	 */
	public void delete(String id) {
		channelItemDao.remove(id);
	}
	
	/**
	 * 채널 아이템 조회
	 */
	public ChannelItem read(String id) {
		return (ChannelItem) channelItemDao.get(id);
	}
	
	/**
	 * 채널 아이템 목록 조회
	 */
	public SearchResult<ChannelItem> getAll(ChannelSearchCondition searchCondition) {

		Integer count = channelItemDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ChannelItem> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ChannelItem>(searchCondition);

		} else {

			List<ChannelItem> channelItemList = channelItemDao.getAll(searchCondition);

			searchResult = new SearchResult<ChannelItem>(channelItemList, searchCondition);
		}

		return searchResult;

	}

}
