/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.util.MessageConstance;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemCategoryDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardItemCategoryService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * BoardItemService 구현체 클래스
 */
@Service
public class BoardItemCategoryServiceImpl implements BoardItemCategoryService {
	/** The board item dao. */
	@Autowired
	private BoardItemCategoryDao boardItemCategoryDao;
	
	@Autowired
	private IdgenService idgenService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listRecentBoardItem(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<BoardItemCategory> listCategoryBoardItem(BoardItemCategory categoryBoardId) {
		return this.boardItemCategoryDao.listCategoryBoardItem(categoryBoardId);
	}
	
	public void insertCategoryNm(List<BoardItemCategory> receiveCategoryNmList) {
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			BoardItemCategory boardItemCategory = receiveCategoryNmList.get(i);
			String addNameList = boardItemCategory.getAddNameList();
			String delIdList   = boardItemCategory.getDelIdList();
			String updateIdList   = boardItemCategory.getUpdateIdList();
			String updateNameList = boardItemCategory.getUpdateNameList();
			String boardId = boardItemCategory.getBoardId();
			String alignList   = boardItemCategory.getAlignList();
			
			String[] arrayReceive;
			arrayReceive  = addNameList.split(",");
			
			String[] arrayModifyId;
			arrayModifyId = updateIdList.split(",");
			
			String[] arrayModifyNm;
			arrayModifyNm = updateNameList.split(",");
			
			String[] arrayDelId;
			arrayDelId    = delIdList.split(",");

			
			
			String[] arrayAlignName;
			arrayAlignName    = alignList.split(",");
			
			
			if(!"".equals(addNameList) ){
				for (int j = 0; j < arrayReceive.length; j++) {		
					boardItemCategory.setCategoryId(idgenService.getNextId());
					boardItemCategory.setAddNameList(arrayReceive[j]);
					boardItemCategory.setBoardId(boardId);
					this.boardItemCategoryDao.createCategoryNm(boardItemCategory);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					boardItemCategory.setUpdateIdList(arrayModifyId[j]);
					boardItemCategory.setUpdateNameList(arrayModifyNm[j]);
					boardItemCategory.setBoardId(boardId);
					this.boardItemCategoryDao.updateCategoryNm(boardItemCategory);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					boardItemCategory.setDelIdList(arrayDelId[j]);
					boardItemCategory.setBoardId(boardId);
					this.boardItemCategoryDao.deleteCategoryNm(boardItemCategory);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					boardItemCategory.setCategorySeqId(""+j);
					boardItemCategory.setAlignList(arrayAlignName[j]);
					boardItemCategory.setBoardId(boardId);
					this.boardItemCategoryDao.updateCategoryAlign(boardItemCategory);
				}	
			}
			
		}
	}
}
