/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.rss.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;


/**
 * Channel Item DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItemDao.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface ChannelItemDao extends GenericDao<ChannelItem, String> {

	/**
	 * 아이템 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getAll(ChannelSearchCondition searchCondition);

	/**
	 * 아이템 목록 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(ChannelSearchCondition searchCondition);

	/**
	 * 중복 아이템 체크
	 * 
	 * @param map
	 * @return
	 */
	public boolean checkItem(Map<String, String> map);
	
	/**
	 * 게시물 리스트 얻기
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getBoardItemList(Map<String, String> map);
	
	/**
	 * Qna 게시물 리스트 얻기
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getQnaItemList(Map<String, String> map);
	
	/**
	 * 워크스페이스 게시물 리스트 얻기
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getWorkspaceItemList(Map<String, String> map);
	
	/**
	 * 블로그 게시물 리스트 얻기
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getBlogItemList(Map<String, String> map);
	
	/**
	 * 카페 게시물 리스트 얻기
	 * @param map
	 * @return
	 */
	public List<ChannelItem> getCafeBoardItemList(Map<String, String> map);
	
	/**
	 * 채널 아이템 생성
	 * 
	 * @param channelItemList
	 */
	public void channelItemInsert(List<ChannelItem> channelItemList);
	
	/**
	 * 채널 아이템 일괄 정리
	 * @param searchCondition
	 */
	public void cleanup(ChannelSearchCondition searchCondition);
	
	public void removeByCategoryId(String categoryId) ;

}
