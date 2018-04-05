package com.lgcns.ikep4.support.rss.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;


/**
 * Channel Item 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItemService.java 16274 2011-08-18 07:08:23Z giljae $
 */
@Transactional
public interface ChannelItemService extends GenericService<ChannelItem, String> {

	/**
	 * 채널 아이템 조회
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ChannelItem> getAll(ChannelSearchCondition searchCondition);

}
