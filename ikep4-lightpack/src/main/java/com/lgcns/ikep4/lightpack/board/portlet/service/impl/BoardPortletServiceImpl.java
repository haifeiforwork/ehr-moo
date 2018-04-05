/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.portlet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;

/**
 * BBS 포틀릿용 서비스 구현 클래스
 *
 * @author 최현식
 * @version $Id: BoardPortletServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service
public class BoardPortletServiceImpl implements BoardPortletService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BoardItemDao boardItemDao;
	
	@Autowired
	private TagService tagService;	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService#listRecentBoard(java.lang.String, java.lang.Integer)
	 */
	public List<BoardItem> listRecentBoardItem(String userId, Integer count) {

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		parameterMap.put("userId", userId);
		parameterMap.put("count", count); 

		List<BoardItem> boardItemList = this.boardItemDao.listRecentAllBoardItem(parameterMap);
		
		
		if(boardItemList != null && boardItemList.size() > 0) {
			for(BoardItem boardItem : boardItemList) {
				List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
				boardItem.setTagList(tagList);
			}
		}
		
		return boardItemList;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService#listBySelectedBoardForPublicBoard()
	 */
	public List<Board> listBySelectedBoardForPublicBoard() { 
		return this.boardDao.listBySelectedBoardForPublicBoard();
	}
	
	public List<Board> listBySelectedBoardForMarkBoard(String registerId) { 
		return this.boardDao.listBySelectedBoardForMarkBoard(registerId);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService#updatePortlet(java.util.List)
	 */
	public void updatePortlet(List<String> boardIdList) {
		//this.boardDao.updateRemovePortlet();
		for(String boardId : boardIdList){
			boardDao.updateSetupPortlet(boardId);
		}
	}
	
	public void deletePortletMark(String registerId) {
		
		this.boardDao.deleteSetupPortletMark(registerId);
	}
	
	public void createPortletMark(List<String> boardIdList, String registerId,String portletConfigId,String viewCount ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("viewCount", viewCount);
		map.put("registerId", registerId);
		map.put("portletConfigId", portletConfigId);
		map.put("updaterId", registerId);    
		for(String boardId : boardIdList) {
			map.put("boardId", boardId); 
			this.boardDao.createSetupPortletMark(map);
		} 
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService#listBoardTypeBoard()
	 */
	public List<Board> listBoardTypeBoard() {  
		return this.boardDao.listBoardTypeBoard();
	} 
	public List<Board> listBoardTypeBoardmark(String portletConfigId, String userId, String portalId) { 
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", "0");
		parameter.put("portalId", portalId);
		parameter.put("userId", userId);
		parameter.put("portletConfigId", portletConfigId);

		return this.boardDao.listBoardTypeBoardmark(parameter);
	} 
}
