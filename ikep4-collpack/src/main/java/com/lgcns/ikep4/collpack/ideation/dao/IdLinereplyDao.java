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
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyDao.java 12098 2011-05-19 09:46:17Z loverfairy $
 */
public interface IdLinereplyDao extends GenericDao<IdLinereply, String>  {
	
	/**
	 * 자식글 있는지 조회
	 * @param linereplyParentId
	 * @return
	 */
	public int getCountByParentId(String linereplyParentId);
	
	/**
	 * 사용자가 쓴 의견수, 베스트 의견수, 타인이 등록한 추천수
	 * @param registerId
	 * @return
	 */
	public IdLinereply getCountes(IdSearch idSearch);
	
	/**
	 * 토론글에 해당하는 의견 등록자 ID
	 * @param itemId
	 * @return
	 */
	public List<String> listUserId(String itemId);
	
	
	/**
	 * 토론의견 목록 조회
	 * @param idSearch
	 * @return
	 */
	public List<IdLinereply> list(IdSearch idSearch);
	
	/**
	 * 토론 의견 목록 수
	 * @param idSearch
	 * @return
	 */
	public int getCountList(IdSearch idSearch);
	
	/**
	 * step 수정
	 * @param linereplyGroupId
	 * @param step
	 */
	public void updateStep(String linereplyGroupId, int step);
	
	/**
	 * 토론의견 delete 필드 수정
	 * @param linereplyId
	 * @param linereplyDelete
	 */
	public void updateLinereplyDelete(String linereplyId, int linereplyDelete);
	
	/**
	 * 채택 의견
	 * @param linereplyId
	 */
	public void updateAdoptLinereply(String linereplyId);
	
	
	/**
	 * 추천수 수정
	 * @param linereplyId
	 */
	public void updateRecommendCount(String linereplyId);
	
	
	
	
	/**
	 * itemid별 목록 모두 삭제
	 * @param itemId
	 */
	public void removeByItemId(String itemId);
	
}
