/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereplyRecommend;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyRecommendDao.java 12098 2011-05-19 09:46:17Z loverfairy $
 */
public interface IdLinereplyRecommendDao extends GenericDao<IdLinereplyRecommend, String>  {
	
	/**
	 * 추천 등록
	 * @param linereplyId
	 * @param registerId
	 */
	public void create(String linereplyId,  String registerId);
	
	/**
	 * 사용자가 추천 했는지
	 * @param linereplyId
	 * @param registerId
	 * @return
	 */
	public boolean exists(String linereplyId, String registerId);
	
	/**
	 * 사용자에 해당하는 추천 개수
	 * @param linereplyId
	 * @param registerId
	 * @return
	 */;
	public int getCountByUserId(String registerId);
	
	/**
	 * 토론의견에 해당하는 추천등록자 리스트
	 * @param linereplyId
	 * @return
	 */
	public List<String> listUserIdByLinereplyId(String linereplyId);
	
	/**
	 * 토론글에 해당하는 추천등록자 리스트
	 * @param itemId
	 * @return
	 */
	public List<String> listUserIdByItemId(String itemId);
	
	
	/**
	 * 의견 추천 리스트
	 * @param linereplyId
	 * @return
	 */
	public List<IdLinereplyRecommend> list(String linereplyId);
	
	
	/**
	 * 사용자 추천 삭제
	 * @param linereplyId
	 * @param registerId
	 */
	public void remove(String linereplyId, String registerId);
	
	/**
	 * 의견에 해당하는 모든 추천 삭제
	 * @param linereplyId
	 */
	public void removeByLinereplyId(String linereplyId);
	
	/**
	 * 토론글ID에 해당하는 모든 추천 삭제
	 * @param itemId
	 */
	public void removeByItemId(String itemId);
	
	
}
