package com.lgcns.ikep4.support.rss.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.rss.dao.ChannelItemDao;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;


/**
 * Channel Item DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItemDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class ChannelItemDaoImpl extends GenericDaoSqlmap<ChannelItem, String> implements ChannelItemDao {

	final String CHANNEL_ITEM = "channelItem";
	
	/**
	 * 채널 아이템 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.rss.channelItem.";

	/**
	 * 메타 정보 쿼리 네임 스페이스
	 */
	private static final String NAMESPACE_META = "support.rss.meta.";

	/**
	 * 채널 아이템 얻기
	 */
	public ChannelItem get(String id) {
		return (ChannelItem) sqlSelectForObject(NAMESPACE + "select", id);
	}

	/**
	 * 체크 체널 아이템
	 */
	public boolean checkItem(Map<String, String> map) {
		boolean check = false;
		ChannelItem channelItem = (ChannelItem) sqlSelectForObject(NAMESPACE + "exists", map);
		if (channelItem == null) {
			check = false;
		} else {
			check = true;
		}

		return check;
	}

	/**
	 * 채널 아이템 생성
	 */
	public String create(ChannelItem channelItem) {
		return (String) sqlInsert(NAMESPACE + "insert", channelItem);
	}
	
	/**
	 * 채널 아이템 생성
	 * 
	 * @param channelItemList
	 * ITEM_DESCRIPTION 은 입력하지 않게 수정함. - 어떤 RSS 사이트는 4000byte 이상되는 내용이 존재하여 에러가남.
	 */
	public void channelItemInsert(List<ChannelItem> channelItemList){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		try {
			
			if (channelItemList != null){				
				// Batch 처리
				getSqlMapClientTemplate().getSqlMapClient().startBatch();
				
				for(ChannelItem channelItem : channelItemList){
					//parameterMap.put(CHANNEL_ITEM, channelItem);
					//sqlInsert(NAMESPACE + "insert", parameterMap);
					sqlInsert(NAMESPACE + "insert", channelItem);
				}
				getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			}
			
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
		
	}

	/**
	 * 채널 아이템 수정
	 */
	public void update(ChannelItem channelItem) {

	}

	/**
	 * 채널 아이템 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}
	
	/**
	 * 채널 아이템 삭제
	 */
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "deleteByCategoryId", categoryId);
	}
	
	/**
	 * 채널 아이템 일괄 정리
	 */
	public void cleanup(ChannelSearchCondition searchCondition) {
		sqlDelete(NAMESPACE + "cleanup", searchCondition);
	}

	/**
	 * 채널 아이템 목록 조회
	 */
	public List<ChannelItem> getAll(ChannelSearchCondition searchCondition) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE + "selectAll", searchCondition);
	}

	/**
	 * 체널 아이템 목록 갯수
	 */
	public Integer countBySearchCondition(ChannelSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	/**
	 * 채널 아이템 체크
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 게시물 목록 조회
	 */
	public List<ChannelItem> getBoardItemList(Map<String, String> map) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE_META + "getBoardItemList", map);
	}

	/**
	 * Qna 게시물 목록 조회
	 */
	public List<ChannelItem> getQnaItemList(Map<String, String> map) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE_META + "getQnaItemList", map);
	}

	/**
	 * 워크스페이스 게시물 목록 조회
	 */
	public List<ChannelItem> getWorkspaceItemList(Map<String, String> map) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE_META + "getWorkspaceItemList", map);
	}

	/**
	 * 블로그 게시물 목록 조회
	 */
	public List<ChannelItem> getBlogItemList(Map<String, String> map) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE_META + "getBlogItemList", map);
	}
	
	/**
	 * 카페 게시물 목록 조회
	 */
	public List<ChannelItem> getCafeBoardItemList(Map<String, String> map) {
		return (List<ChannelItem>) sqlSelectForList(NAMESPACE_META + "getCafeBoardItemList", map);
	}

}
