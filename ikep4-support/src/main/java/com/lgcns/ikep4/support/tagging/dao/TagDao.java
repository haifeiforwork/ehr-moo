/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface TagDao extends GenericDao<Tag, String>  {
	
	/**
	 *  리스트 가져오기 - Tag관리 리스트 전체에 사용
	 * @param tag
	 * @return
	 */
	public List<Tag> listCloud(Tag tag);
	
	/**
	 * itemid, itemType으로 리스트 가져오기 - itemId 전체가져옴
	 * @param tag
	 * @return
	 */
	public List<Tag> listByItem(String tagItemId, String tagItemType);
	
	/**
	 * 태그 검색 후 태그 리스트
	 * @param tag
	 * @return
	 */
	public List<Tag> listSearch(Tag tag);
	
	
	/**
	 * 조건에 맞는 전체 카운트 가져오기
	 * @param tag
	 */
	public int readCount(Tag tag);
	
	
	/**
	 * 조건에 맞는 전체 카운트 가져오기
	 * @param tag
	 */
	public int getCountSearch(Tag tag);
	
	
	/**
	 * 태그ITem ID들을 넘겨 item ID 리스트
	 * @param tag
	 * @return
	 */
	public List<String> listItemId(Tag tag);
	
	/**
	 *  태그ITem ID들을 넘겨 item ID 리스트  개수
	 * @param tag
	 * @return
	 */
	public int getCountListItemId(Tag tag);
	
	/**
	 * 팀 목록
	 * @param portalId
	 * @return
	 */
	public List<Tag> listTeamType(String portalId);
	
	/**
	 * 태그별 카테고리 개수
	 * @param tag
	 * @return
	 */
	public List<Tag> listItemCount(String tagItemId, String tagItemType);
	
	/**
	 * 태그 id에 해당하는 item 정보 user정보와 같이 가져옴
	 * - 사용않함. 각 모듈에서 user정보 직접 조인하여 필요한 정보 가져오도록 함.
	 * @param tag
	 * @return
	 */
	public List<Tag> listItemAsUserByTagId(Tag tag);
	
	/**
	 * 태그 id에 해당하는 item 정보 개수
	 * @param tag
	 * @return
	 */
	public int getCountListItemAsUserByTagId(Tag tag);

	/**
	 * itemId 리스트 랜덤하게 가져오기
	 * @param tag
	 * @return
	 */
	public List<String> listItemIdAsRandom(Tag tag);
	
	
}
