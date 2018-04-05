package com.lgcns.ikep4.support.rss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.rss.dao.ChannelDao;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;


/**
 * Channel DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class ChannelDaoImpl extends GenericDaoSqlmap<Channel, String> implements ChannelDao {
	
	/**
	 * 채널 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.rss.channel.";
	
	/**
	 * 메타 정보 쿼리 네임 스페이스
	 */
	private static final String NAMESPACE_META = "support.rss.meta.";
	
	/**
	 * 채널 조회
	 */
	public Channel get(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE + "select", id);
	}
	
	/**
	 * 채널 체크
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 채널 생성
	 */
	public String create(Channel channel) {
		return (String) sqlInsert(NAMESPACE + "insert", channel);
	}
	
	/**
	 * 채널 수정
	 */
	public void update(Channel channel) {
		sqlUpdate(NAMESPACE + "update", channel);
	}
	
	/**
	 * 채널 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}
	
	/**
	 * 채널  삭제
	 */
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "deleteByCategoryId", categoryId);
	}
	
	/**
	 * 채널 리스트 조회
	 */
	public List<Channel> getAll(ChannelSearchCondition searchCondition) {
		return (List<Channel>) sqlSelectForList(NAMESPACE + "selectAll", searchCondition);
	}
	
	/**
	 * 정렬 타겟 조회
	 */
	public Channel selectSortTarget(Map<String, String> map) {
		return (Channel) sqlSelectForObject(NAMESPACE + "selectSortTarget", map);
	}
	
	/**
	 * 정렬 순서 수정
	 */
	public void updateSortOrder(Channel channel) {
		sqlUpdate(NAMESPACE + "updateSortOrder", channel);
	}
	
	/**
	 * 정렬 순서 얻기
	 */
	public String getSortOrder(Map<String, String> map) {
		return (String) sqlSelectForObject(NAMESPACE + "selectSortOrder", map);
	}
	
	/**
	 * 채널 체크
	 */
	public boolean checkChannel(Map<String, String> map) {
		boolean check = false;
		Channel channel = (Channel) sqlSelectForObject(NAMESPACE + "exists", map);
		if (channel != null) {
			check = true;
		}
		return check;
	}
	
	/**
	 * 채널 리스트 갯수
	 */
	public Integer countBySearchCondition(ChannelSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	/**
	 * 게시판 타이틀 얻기
	 */
	public Channel getBoard(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE_META + "getBoard", id);
	}
	
	/**
	 * Qna 타이틀 얻기
	 */
	public Channel getQna(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE_META + "getQna", id);
	}
	
	/**
	 * 워크스페이스 타이틀 얻기
	 */
	public Channel getWorkspace(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE_META + "getWorkspace", id);
	}
	
	/**
	 * 블로그 타이틀 얻기
	 */
	public Channel getBlog(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE_META + "getBlog", id);
	}
	
	/**
	 * 카페 게시판 타이틀 얻기
	 */
	public Channel getCafeBoard(String id) {
		return (Channel) sqlSelectForObject(NAMESPACE_META + "getCafeBoard", id);
	}

}
