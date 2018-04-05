/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시판 배치 서비스 클래스
 *
 * @author 최현식
 * @version $Id: AwardBatchService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface AwardBatchService {

	/**
	 * 파라미터를 게시글의 쓰레드를 삭제한다.
	 * 
	 * @param itemId
	 */
	void adminDeleteAwardItemForBatch(String itemId);

	/**
	 * 전시 일자가 지난 게시물 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글 ID 목록
	 */
	List<AwardItem> listBatchDeletePassedAwardItem(Integer getCount);

	/**
	 * 게시물 상태가 삭제인  목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글 ID 목록
	 */
	List<AwardItem> listBatchDeleteStatusAwardItem(Integer getCount);

	/**
	 * 삭제 상태의 게시글을 가져온다.
	 * 
	 * @param awardId 게시판 Id
	 * @param repeadCount  가져와야 하는 삭제 목록
	 * @return
	 */
	List<AwardItem> listByAwardItemOfDeletedAward(String awardId, Integer getCount);

	/**
	 * 삭제되어야 할 게시판을 가져온다.
	 * 
	 * @return
	 */
	Award nextDeletedAward();

	/**
	 * 게시판을 삭제한다.
	 * 
	 * @param awardId
	 */
	void physicalAwardDelete(String awardId);
	
	void updateDisplayAwardParentItem();
	
	void updateDisplayAwardItem();
	
	void sendUserMail(List<AwardItemReader>  awardItemReaderList,String contents,User user,String wordName);
}
