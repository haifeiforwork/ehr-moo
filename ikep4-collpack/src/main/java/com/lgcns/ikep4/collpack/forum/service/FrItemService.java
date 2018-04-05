/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrItemService extends GenericService<FrItem, String>  {
	
	/**
	 * 상세 조회
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public FrItem get(String itemId, String registerId);
	
	/**
	 * 토론글 일반 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> list(FrSearch frSearch);
	
	/**
	 * 토론글 일반 목록 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listWithDiscussion(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글 리스트 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListWithDiscussion(FrSearch frSearch);
	
	/**
	 * 토론글 인기 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listPopular(FrSearch frSearch);
	
	/**
	 * 토론글 인기 리스트 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListPopular(FrSearch frSearch);
	
	/**
	 * 카테고리 랜덤 토론글 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listItemRandomCategory(FrSearch frSearch);
	
	/**
	 * 최신 주제 목록과 주제글
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listLastWithDiscussion(FrSearch frSearch);
	
	/**
	 *최신 주제 목록과 주제글 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListLastWithDiscussion(FrSearch frSearch);
	
	/**
	 * 즐겨찾기 개수 변경
	 * @param itemId
	 * @param favoriteCount
	 */
	public void updateFavoriteCount(String itemId, int favoriteCount);
	
	/**
	 * 삭제
	 */
	public void remove(String itemId);
	
	
	
}
