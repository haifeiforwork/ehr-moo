/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagService.java 16489 2011-09-06 01:41:09Z giljae $
 */
@Transactional
public interface TagService extends GenericService<Tag, String> {

	/**
	 * 전체 리스트
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Tag> list(Tag tag);

	/**
	 * item 단위 리스트
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Tag> listTagByItemId(String tagItemId, String tagItemType);

	/**
	 * item type으로 태그 가져오기 - itemType 복수개 가능
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Tag> listTagByItemType(Tag tag);

	/**
	 * 태그 ID 나 이름으로 태그 검색
	 * 
	 * @param tagItemId
	 * @param tagItemType
	 * @param portalId
	 * @return
	 */
	public List<Tag> listTagSearch(Tag tag);

	/**
	 * 태그 ID 나 이름으로 태그 검색 개수
	 * 
	 * @param tag
	 * @return
	 */
	public int getCountSearch(Tag tag);

	/**
	 * 게시물 ID를 넘겨 item ID들을 가져온다.
	 * 
	 * @param tagItemId
	 * @param tagItemType
	 * @return
	 */
	public Map<String, Object> listItemId(String itemId, String itemType, int index, int pagePer);

	/**
	 * 게시물 ID와 태그 ID를 넘겨 item ID들을 가져온다.
	 * 
	 * @param tagId
	 * @param itemId
	 * @param itemType
	 * @param index
	 * @param pagePer
	 * @return
	 */
	public Map<String, Object> listItemId(String tagId, String itemId, String itemType, int index, int pagePer);

	/**
	 * 게시물 ID를 넘겨 item ID들을 가져온다.
	 * 
	 * @param tag
	 * @return
	 */
	public Map<String, Object> listItemId(Tag tag);

	/**
	 * 게시물 ID를 넘겨 item ID들을 가져온다.
	 * 
	 * @param tagItemId
	 * @param tagItemType
	 * @return
	 */
	public Map<String, Object> listItemIdByWorkspace(String workspaceId, String itemId, String itemType, int index,
			int pagePer);

	/**
	 * 태그 리스트로 게시물 ID 가져오기
	 * 
	 * @param tagList
	 * @param index
	 * @param pagePer
	 * @return
	 */
	public Map<String, Object> listItemByTagList(List<Tag> tagList, int index, int pagePer);

	/**
	 * 전체 개수
	 * 
	 * @param tag
	 * @return
	 */
	public int getCount(Tag tag);

	/**
	 * PK에 해당하는 테이블의 row를 삭제
	 * 
	 * @param categoryId 삭제하고자하는 row의 PK 값
	 */
	public void delete(String tagItemId, String tagItemType);

	/**
	 * 팀 목록
	 * 
	 * @param portalId
	 * @return
	 */
	public List<Tag> listTeamType(String portalId);

	/**
	 * 태그별 카테고리 개수
	 * 
	 * @param tag
	 * @return
	 */
	public List<Tag> listItemCount(String tagItemId, String tagItemType);

	/**
	 * 태그 id에 해당하는 item 정보 user정보와 같이 가져옴
	 * 
	 * @param tag
	 * @return
	 */
	public List<Tag> listItemAsUserByTagId(Tag tag);

	/**
	 * 태그 id에 해당하는 item 정보 개수
	 * 
	 * @param tag
	 * @return
	 */
	public int getCountListItemAsUserByTagId(Tag tag);

	/**
	 * 태그 Item search, 페이징을 처리하고 전체 개수 같이 줌.
	 * 
	 * @param tag
	 * @param index
	 * @param pagePer
	 * @return
	 */
	public Map<String, Object> listItemAsCount(Tag tag, int index, int pagePer);

	public List<String> listItemIdAsRandom(Tag tag);

	/**
	 * item types들이 있는지 계산
	 * 
	 * @param tag
	 * @return
	 */
	public Tag itemTypes(Tag tag);

}
