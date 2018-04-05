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
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;


/**
 * Channel DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelDao.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface ChannelDao extends GenericDao<Channel, String> {

	/**
	 * 채널 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	public List<Channel> getAll(ChannelSearchCondition searchCondition);

	/**
	 * 채널 목록 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(ChannelSearchCondition searchCondition);

	/**
	 * 순서를 바꿀 채널 조회
	 * 
	 * @param map
	 * @return
	 */
	public Channel selectSortTarget(Map<String, String> map);

	/**
	 * 채널 순서 변경
	 * 
	 * @param map
	 * @return
	 */
	public void updateSortOrder(Channel channel);

	/**
	 * 순번 구하기
	 * 
	 * @param map
	 * @return
	 */
	public String getSortOrder(Map<String, String> map);

	/**
	 * 채널 유효성 체크
	 * 
	 * @param map
	 * @return
	 */
	public boolean checkChannel(Map<String, String> map);

	/**
	 * 게시판 타이틀 얻기
	 * 
	 * @param id
	 * @return
	 */
	public Channel getBoard(String id);

	/**
	 * Qna 타이틀 얻기
	 * 
	 * @param id
	 * @return
	 */
	public Channel getQna(String id);

	/**
	 * 워크스페이스 타이틀 얻기
	 * 
	 * @param id
	 * @return
	 */
	public Channel getWorkspace(String id);

	/**
	 * 블로그 타이틀 얻기
	 * 
	 * @param id
	 * @return
	 */
	public Channel getBlog(String id);
	
	/**
	 * 카페 게시판 타이틀 얻기
	 * 
	 * @param id
	 * @return
	 */
	public Channel getCafeBoard(String id);
	
	/**
	 * 채널  삭제
	 */
	public void removeByCategoryId(String categoryId);

}
