/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.lightpack.gallery.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.gallery.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.gallery.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.gallery.model.BoardItem;
import com.lgcns.ikep4.lightpack.gallery.model.BoardReference;
import com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.gallery.service.BoardItemService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.tagfree.util.MimeUtil;

/**
 * BoardItemService 구현체 클래스
 */
@Service("pfBoardAdminServiceImpl")
public class BoardItemServiceImpl implements BoardItemService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	/** The file service. */
	@Autowired
	private FileService fileService;

	/** The board item dao. */
	@Autowired
	private BoardItemDao boardItemDao;

	/** The board linereply dao. */
	@Autowired
	private BoardLinereplyDao boardLinereplyDao;


	@Autowired
	private BoardReferenceDao boardReferenceDao;




	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listBoardItemBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public SearchResult<BoardItem> listBoardItemBySearchCondition(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countBySearchCondition(searchCondition);

		//searchCondition.setPagePerRecord(9);
		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;


	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listBoardItemBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public SearchResult<BoardItem> listBoardItemBySearchConditionView(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countBySearchConditionView(searchCondition);
		searchCondition.setPagePerRecord(9);
		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listBySearchConditionView(searchCondition);

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;


	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#readBoardItem(java.lang.String)
	 */
	public BoardItem readBoardItem(String itemId) {
		//게시물을 가져온다.
		BoardItem boardItem = this.boardItemDao.get(itemId);

		//첨부 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> fileDataList = this.fileService.getItemFile(itemId, BoardItem.ATTECHED_FILE);
		boardItem.setFileDataList(fileDataList);

		//CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

		return boardItem;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#createBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	@SuppressWarnings("unchecked")
	public String createBoardItem(BoardItem boardItem, User user,List<MultipartFile> fileList) {
		boardItem.setHitCount(0);
		boardItem.setLinereplyCount(0);
		boardItem.setAttachFileCount(0);
		boardItem.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		boardItem.setItemId(id);

		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(boardItem.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(boardItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						boardItem.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					boardItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		

		//게시물의 첨부파일 갯수를 업데이트 한다.
		//if(boardItem.getFileLinkList() != null) {
		///	boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		//}

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		//if(boardItem.getEditorFileLinkList() != null && boardItem.getEditorFileLinkList().size()>0) {
		//	if(boardItem.getEditorFileLinkList().get(0) != null){
		//		boardItem.setImageFileId(boardItem.getEditorFileLinkList().get(0).getFileId());
		//	}
		//}
		

		//파일 첨부
		String editorAttach="0";
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			boardItem.setAttachFileCount(1);
			
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			String fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
			boardItem.setImageFileId(fileId);
		}	

		// 첨부파일 링크 정보 생성 및 대표 썸네일 이미지 등록
		/**if(boardItem.getFileId()!=null && !boardItem.getFileId().equals("")) {
			boardItem.setAttachFileCount(1);
			fileService.createFileLink(boardItem.getFileId(), boardItem.getItemId(),BoardItem.ITEM_TYPE,user);
			boardItem.setImageFileId(boardItem.getFileId());
		}**/
		//게시물을 생성한다.
		String boardItemId = this.boardItemDao.create(boardItem);

		//게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		//if(boardItem.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
		//	this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		//}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(boardItem.getEditorFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}		

		return boardItemId;
	}



	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	@SuppressWarnings("unchecked")
	public void updateBoardItem(BoardItem boardItem, User user,List<MultipartFile> fileList) {
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(boardItem.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(boardItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(boardItem.getEditorFileLinkList() != null){
							for (int i = 0; i < boardItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) boardItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						boardItem.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					boardItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		
		
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			fileService.removeFile(boardItem.getOldFileId());
		}
		

		
		String fileId = "";
		String editorAttach="0";
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			boardItem.setAttachFileCount(1);
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
			boardItem.setImageFileId(fileId);
		}		
		
		
		// 첨부파일 링크 정보 생성 및 대표 썸네일 이미지 등록 - 기존이미지 삭제
		//if(boardItem.getOldFileId()!=null && boardItem.getFileId()!=null && !boardItem.getFileId().equals("")){
		//	fileService.removeFile(boardItem.getOldFileId());
		//}		
		// 첨부파일 링크 정보 생성 및 대표 썸네일 이미지 등록 - 신규 등록
		//if(boardItem.getFileId()!=null && !boardItem.getFileId().equals("")) {
		//	boardItem.setAttachFileCount(1);
		//	fileService.createFileLink(boardItem.getFileId(), boardItem.getItemId(),BoardItem.ITEM_TYPE,user);
		//	boardItem.setImageFileId(boardItem.getFileId());
		//}

		//게시물의 첨부파일 갯수를 업데이트 한다.
		//if(boardItem.getFileLinkList() != null) {
		//	boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		//}


		//첨부파일 업데이트
		//if(boardItem.getFileLinkList() != null) {
		//	this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		//}



		//List<FileData> fileDateList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.EDITOR_FILE);


		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		//if(fileDateList != null && fileDateList.size() > 0) {
		//	boardItem.setImageFileId(fileDateList.get(0).getFileId() );
		//}
		//이미지 파일  업데이트
		if(boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(boardItem.getEditorFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}
		this.boardItemDao.update(boardItem);

		BoardItem masterInfo = this.boardItemDao.get(boardItem.getItemId());

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#adminDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void adminDeleteBoardItem(BoardItem boardItem) {

		BoardItem persisted = this.boardItemDao.get(boardItem.getItemId());

		this.updateItemDeleteField(boardItem.getItemId(), BoardItem.STATUS_DELETED);

	}




	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#userDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void userDeleteBoardItem(BoardItem boardItem) {
		BoardItem persisted = this.boardItemDao.get(boardItem.getItemId());


		this.adminDeleteBoardItem(boardItem);

	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateHitCount(java.lang.String)
	 */
	public void updateHitCount(BoardReference boardReference) {

		//이미 조회를 했다면 등록 일자만 업데이트 해준다.
		if(this.boardReferenceDao.exists(boardReference)) {
			this.boardReferenceDao.update(boardReference);

			//조회를 하지 않았다면 조회 데이터를 생성한다.
		} else {
			this.boardItemDao.updateHitCount(boardReference.getItemId());
			this.boardReferenceDao.create(boardReference);
		}
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#adminMultiDeleteBoardItem(java.lang.String[])
	 */
	public void adminMultiDeleteBoardItem(String[] itemIds) {
		BoardItem boardItem = null;

		for(String itemId : itemIds) {
			boardItem = this.boardItemDao.get(itemId);

			//이미 삭제가 되었다면 continue
			if(boardItem.getItemDelete() == BoardItem.STATUS_DELETED) {
				continue;
			}

			this.adminDeleteBoardItem(boardItem);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#readBoardItemMasterInfo(java.lang.String)
	 */
	public BoardItem readBoardItemMasterInfo(String itemId) {
		return this.boardItemDao.get(itemId);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listRecentBoardItem(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<BoardItem> listRecentBoardItem(String boardId,  Integer count) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", boardId);
		parameter.put("count", count == null ? 10 : count);

		//List<BoardItem> boardItemList = this.boardItemDao.listRecentBoardItem(parameter);

		return this.boardItemDao.listRecentBoardItem(parameter);
	}

	public List<BoardItem> listRecentBoardItemPortlet(String boardId,  Integer count) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", boardId);
		parameter.put("count", count == null ? 10 : count);

		return this.boardItemDao.listRecentBoardItemPortlet(parameter);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateItemDelelteField(java.lang.String, java.lang.Integer)
	 */
	public void updateItemDeleteField(String itemId, Integer status) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("itemId", itemId);
		parameter.put("status", status);

		this.boardItemDao.updateItemDeleteField(parameter);

		if(status == BoardItem.STATUS_DELETED) {
			BoardItem boardItem = this.readBoardItemMasterInfo(itemId);
		
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateItemListDeleteField(java.util.List, java.lang.Integer)
	 */
	public void updateItemListDeleteField(List<String> boardItemIdList, Integer status) {

		for(String itemId : boardItemIdList) {
			this.updateItemThreadDeleteField(itemId, status);
		}


	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateItemThreadDeleteField(java.lang.String, java.lang.Integer)
	 */
	public void updateItemThreadDeleteField(String itemId, Integer status) {
		List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(itemId);

		for(BoardItem loopingItem : boardItemList) {
			this.updateItemDeleteField(loopingItem.getItemId(), BoardItem.STATUS_DELETED);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#deleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void deleteBoardItemThread(BoardItem boardItem) {
		//본인 포함 하위 게시글을 가져온다.
		List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(boardItem.getItemId());

		for(BoardItem loopingItem : boardItemList) {

			//전체 파일 삭제
			this.fileService.removeItemFile(loopingItem.getItemId());

			//댓글 쓰레드를 삭제한다.
			this.boardLinereplyDao.physicalDeleteThreadByItemThread(loopingItem.getItemId());

			//게시글 조회 정보를 삭제한다.
			this.boardReferenceDao.removeByItemId(loopingItem.getItemId());

			//게시글을 삭제한다.
			this.boardItemDao.physicalDelete(loopingItem.getItemId());
		}

	}

}
