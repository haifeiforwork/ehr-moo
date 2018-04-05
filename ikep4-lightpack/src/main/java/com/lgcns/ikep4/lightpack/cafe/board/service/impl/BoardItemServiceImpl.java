/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.tagfree.util.MimeUtil;

/**
 * BoardItemService 구현체 클래스
 */
@Service("cfBoardItemServiceImpl")
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
	private BoardRecommendDao boardRecommendDao;

	@Autowired
	private BoardReferenceDao boardReferenceDao;

	@Autowired
	private TagService tagService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	ACLService aclService;

	@Autowired
	CafeService cafeService;

	@Autowired
	private BoardDao boardDao;

	/**
	 * 게시물 목록
	 */
	public SearchResult<BoardItem> listBoardItemBySearchCondition(
			BoardItemSearchCondition searchCondtion) {
		Integer count = this.boardItemDao
				.countBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if (searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondtion);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao
					.listBySearchCondition(searchCondtion);

			if (boardItemList != null && boardItemList.size() > 0) {
				for (BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(
							boardItem.getItemId(), TagConstants.ITEM_TYPE_CAFE);
					boardItem.setTagList(tagList);
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList,
					searchCondtion);
		}

		return searchResult;

	}

	/**
	 * 게시물 정보 조회
	 */
	public BoardItem readBoardItem(String itemId) {

		// 게시물을 가져온다.
		BoardItem boardItem = this.boardItemDao.get(itemId);

		List<FileData> fileDataList = null;

		fileDataList = this.fileService.getItemFile(itemId,
				BoardItem.ATTECHED_FILE);

		boardItem.setFileDataList(fileDataList);

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(
				itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

		// 게시물과 연관되는 답글 Thread를 가져와 게시물에 넣는다
		List<BoardItem> boardItemList = this.boardItemDao
				.listReplayItemThreadByItemId(boardItem.getItemGroupId());
		boardItem.setReplyItemThreadList(boardItemList);

		// 태그 목록을 조회하고 게시물에 넣는다
		List<Tag> tagList = this.tagService.listTagByItemId(itemId,
				TagConstants.ITEM_TYPE_CAFE);
		boardItem.setTagList(tagList);

		// 태그 문자열을 만든다.
		if (tagList != null && tagList.size() > 0) {
			StringBuffer tagBuffer = new StringBuffer();

			for (Tag tag : tagList) {
				tagBuffer.append(tag.getTagName());
				tagBuffer.append(", ");
			}

			boardItem.setTag(StringUtils.substringBeforeLast(
					tagBuffer.toString(), ","));
		}

		return boardItem;
	}

	/**
	 * 태그들을 저장한다. 태그는 화면에서 문자열 형태로 넘어온다. (ex: Tag-A, Tag-B, Tag-C)
	 * 
	 * @param boardItem
	 *            the board item
	 */
	private void saveTags(BoardItem boardItem) {
		// 태그저장
		if (StringUtils.isEmpty(boardItem.getTag())) {
			return;
		} else {
			Tag tag = new Tag();

			tag.setTagName(boardItem.getTag());
			tag.setTagItemId(boardItem.getItemId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_CAFE);
			tag.setTagItemSubType(boardItem.getBoardId());
			tag.setTagItemName(boardItem.getTitle());
			tag.setTagItemContents(boardItem.getContents());
			tag.setTagItemUrl("/lightpack/cafe/board/boardItem/readBoardItemView.do?docPopup=true&boardId="
					+ boardItem.getBoardId()
					+ "&itemId="
					+ boardItem.getItemId());
			tag.setRegisterId(boardItem.getRegisterId());
			tag.setRegisterName(boardItem.getRegisterName());
			tag.setPortalId(boardItem.getPortalId());

			this.tagService.create(tag);
		}

	}

	/**
	 * 게시물 등록
	 */
	@SuppressWarnings("unchecked")
	public String createBoardItem(BoardItem boardItem, User user) {
		boardItem.setStep(0);
		boardItem.setIndentation(0);
		boardItem.setHitCount(0);
		boardItem.setRecommendCount(0);
		boardItem.setReplyCount(0);
		boardItem.setLinereplyCount(0);
		boardItem.setAttachFileCount(0);
		boardItem.setItemDelete(0);

		// 신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		// 아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		boardItem.setItemId(id);
		boardItem.setItemGroupId(id);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader
				.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (boardItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder
							.currentRequestAttributes().getAttribute(
									"ikep.portal",
									RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop
							.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService,
							portal.getPortalHost(), port);
					util.setMimeValue(boardItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null
							&& util.getFileLinkList().size() > 0) {
						boardItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					boardItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		}

		// CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if (boardItem.getEditorFileLinkList() != null
				&& boardItem.getEditorFileLinkList().get(0) != null) {
			boardItem.setImageFileId(boardItem.getEditorFileLinkList().get(0)
					.getFileId());
		}

		// 게시물을 생성한다.
		String boardItemId = this.boardItemDao.create(boardItem);

		// 게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if (boardItem.getFileLinkList() != null) {
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(),
					boardItem.getItemId(), IKepConstant.ITEM_TYPE_CODE_CAFE,
					user);
		}

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_CAFE, user);
		}

		// 태그저장
		this.saveTags(boardItem);

		// Activity Stream 게시글 생성
		// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
		// IKepConstant.ACTIVITY_CODE_DOC_POST,
		// boardItemId, boardItem.getTitle());

		Board board = this.boardDao.get(boardItem.getBoardId());
		Cafe cafe = this.cafeService.read(board.getCafeId());
		this.activityStreamService.createForCollavoration(
				IKepConstant.ITEM_TYPE_CODE_CAFE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, boardItemId,
				boardItem.getTitle(), "BBS", cafe.getCafeId(),
				cafe.getCafeName());

		return boardItemId;
	}

	/**
	 * 게시물 답변 등록
	 */
	@SuppressWarnings("unchecked")
	public String createReplyBoardItem(BoardItem boardItem, User user) {

		boardItem.setHitCount(0);
		boardItem.setRecommendCount(0);
		boardItem.setReplyCount(0);
		boardItem.setLinereplyCount(0);
		boardItem.setAttachFileCount(0);
		boardItem.setItemDelete(0);
		boardItem.setItemDisplay(0);

		// 신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		// 아이디를 설정한다.
		boardItem.setItemId(id);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader
				.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (boardItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder
							.currentRequestAttributes().getAttribute(
									"ikep.portal",
									RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop
							.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService,
							portal.getPortalHost(), port);
					util.setMimeValue(boardItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null
							&& util.getFileLinkList().size() > 0) {
						boardItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					boardItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		}

		// 게시글들의 스탭을 업데이트 한다.(같은 Level에 존재하는 글에 대한 순서이다.)
		this.boardItemDao.updateStep(boardItem);

		// 답글 게시글을 생성한다.
		String boardItemId = this.boardItemDao.create(boardItem);

		// 첨부파일 업데이트
		if (boardItem.getFileLinkList() != null) {
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(),
					boardItem.getItemId(), IKepConstant.ITEM_TYPE_CODE_CAFE,
					user);
		}

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_CAFE, user);
		}

		// 태그저장
		this.saveTags(boardItem);

		// 답글 갯수를 업데이트 한다.
		this.boardItemDao.updateReplyCount(boardItem.getItemParentId());

		// Activity Stream 등록(삭제)
		// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
		// IKepConstant.ACTIVITY_CODE_DOC_POST,
		// boardItem.getItemId(), boardItem.getTitle());

		Board board = this.boardDao.get(boardItem.getBoardId());
		Cafe cafe = this.cafeService.read(board.getCafeId());
		this.activityStreamService.createForCollavoration(
				IKepConstant.ITEM_TYPE_CODE_CAFE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, boardItemId,
				boardItem.getTitle(), "BBS", cafe.getCafeId(),
				cafe.getCafeName());

		return boardItemId;
	}

	/**
	 * 게시물 수정
	 */
	@SuppressWarnings("unused")
	public void updateBoardItem(BoardItem boardItem, User user) {

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader
				.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (boardItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder
							.currentRequestAttributes().getAttribute(
									"ikep.portal",
									RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop
							.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService,
							portal.getPortalHost(), port);
					util.setMimeValue(boardItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null
							&& util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (boardItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < boardItem
									.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) boardItem
										.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util
									.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						boardItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					boardItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : boardItem.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
				}
			}

			boardItem.setAttachFileCount(fileCount);

			this.fileService.saveFileLink(boardItem.getFileLinkList(),
					boardItem.getItemId(), IKepConstant.ITEM_TYPE_CODE_CAFE,
					user);

		} else {
			boardItem.setAttachFileCount(0);
		}

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_CAFE, user);
		}

		List<FileData> fileDateList = this.fileService.getItemFile(
				boardItem.getItemId(), BoardItem.EDITOR_FILE);

		// CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if (fileDateList != null && fileDateList.size() > 0) {
			boardItem.setImageFileId(fileDateList.get(0).getFileId());
		}

		// 수정전 게시물 정보
		BoardItem orgBoardItem = this.boardItemDao.get(boardItem.getItemId());

		this.boardItemDao.update(boardItem);

		// 태그저장
		this.saveTags(boardItem);

		// Activity Stream 수정
		// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
		// IKepConstant.ACTIVITY_CODE_DOC_EDIT,
		// boardItem.getItemId(), boardItem.getTitle());

		Board board = this.boardDao.get(boardItem.getBoardId());
		Cafe cafe = this.cafeService.read(board.getCafeId());
		this.activityStreamService.createForCollavoration(
				IKepConstant.ITEM_TYPE_CODE_CAFE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT,
				boardItem.getItemId(), boardItem.getTitle(), "BBS",
				cafe.getCafeId(), cafe.getCafeName());

	}

	/**
	 * 게시물 삭제
	 */
	public void adminDeleteBoardItem(BoardItem boardItem) {
		// 본인 포함 하위 게시글을 가져온다.
		List<BoardItem> boardItemList = this.boardItemDao
				.listLowerItemThread(boardItem.getItemId());

		// //Indentation이 높은 것이 우선적으로 오게 정렬한다.
		// Collections.sort(boardItemList, new Comparator<BoardItem>(){
		// public int compare(BoardItem boardItem1, BoardItem boardItem2) {
		// return (boardItem1.getIndentation() - boardItem2.getIndentation()) *
		// -1;
		// }
		// });

		for (BoardItem loopingItem : boardItemList) {
			// Activity Stream 등록(삭제)
			// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
			// IKepConstant.ACTIVITY_CODE_DOC_DELETE, boardItem.getItemId(),
			// boardItem.getTitle());

			// 태그를 삭제한다.
			this.tagService.delete(loopingItem.getItemId(),
					TagConstants.ITEM_TYPE_CAFE);

			// 전체 파일 삭제
			this.fileService.removeItemFile(loopingItem.getItemId());

			// 댓글 쓰레드를 삭제한다.
			this.boardLinereplyDao.physicalDeleteThreadByItemThread(loopingItem
					.getItemId());

			// 게시글 추천 정보를 삭제한다.
			this.boardRecommendDao.removeByItemId(loopingItem.getItemId());

			// 게시글 조회 정보를 삭제한다.
			this.boardReferenceDao.removeByItemId(loopingItem.getItemId());

			// 게시글을 삭제한다.
			this.boardItemDao.physicalDelete(loopingItem.getItemId());
		}

		// 부모글의 답글 갯수를 업데이트 한다.
		this.boardItemDao.updateReplyCount(boardItem.getItemParentId());
	}

	/**
	 * 게시물 삭제
	 */
	public void userDeleteBoardItem(BoardItem boardItem) {
		Integer count = this.boardItemDao.countChildren(boardItem.getItemId());

		if (count == 0) {
			this.adminDeleteBoardItem(boardItem);

		} else {
			this.boardItemDao.logicalDelete(boardItem);
			// 태그를 삭제한다.
			this.tagService.delete(boardItem.getItemId(),
					TagConstants.ITEM_TYPE_CAFE);
		}
	}

	/**
	 * 추천수 수정 및 추천 정보 등록
	 */
	public void updateRecommendCount(BoardRecommend boardRecommend) {
		// 이미 추천을 했다면
		if (!this.boardRecommendDao.exists(boardRecommend)) {
			this.boardItemDao.updateRecommendCount(boardRecommend.getItemId());
			this.boardRecommendDao.create(boardRecommend);
		}

	}

	/**
	 * 조회수 수정 및 조회 정보 등록
	 */
	public void updateHitCount(BoardReference boardReference) {

		// 이미 조회를 했다면 등록 일자만 업데이트 해준다.
		if (this.boardReferenceDao.exists(boardReference)) {
			this.boardReferenceDao.update(boardReference);

			// 조회를 하지 않았다면 조회 데이터를 생성한다.
		} else {
			this.boardItemDao.updateHitCount(boardReference.getItemId());
			this.boardReferenceDao.create(boardReference);
		}
	}

	/**
	 * 게시물 목록
	 */
	public SearchResult<BoardItem> listBoardItemNoThreadBySearchCondition(
			BoardItemSearchCondition searchCondtion) {
		Integer count = this.boardItemDao
				.countNoThreadBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if (searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondtion);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao
					.listNoThreadBySearchCondition(searchCondtion);

			if (boardItemList != null && boardItemList.size() > 0) {
				for (BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(
							boardItem.getItemId(), TagConstants.ITEM_TYPE_CAFE);
					boardItem.setTagList(tagList);
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList,
					searchCondtion);
		}

		return searchResult;
	}

	/**
	 * 게시물 다중 삭제
	 */
	public void adminMultiDeleteBoardItem(String[] itemIds) {
		BoardItem boardItem = null;

		for (String itemId : itemIds) {
			boardItem = this.boardItemDao.get(itemId);

			// 이미 삭제가 되었다면 continue
			if (boardItem == null) {
				continue;
			}

			this.adminDeleteBoardItem(boardItem);
		}
	}

	/**
	 * 게시판 내의 게시물 삭제
	 */
	public void adminDeleteBoardItemInBoard(String boardId) {
		List<BoardItem> boardItemList = this.boardItemDao
				.listByBoardIdForBoardDelete(boardId);

		if (boardItemList != null) {
			for (BoardItem boardItem : boardItemList) {
				this.adminDeleteBoardItem(boardItem);

			}
		}
	}

	/**
	 * 추천정보 존재유무
	 */
	public Boolean exsitRecommend(BoardRecommend boardRecommend) {
		return this.boardRecommendDao.exists(boardRecommend);

	}

	/**
	 * 게시물 정보조회
	 */
	public BoardItem readBoardItemMasterInfo(String itemId) {
		return this.boardItemDao.get(itemId);
	}

	private final static Integer DEFAULT_RECENT_ITEM_COUNT = 10;

	/**
	 * 최근 게시물 목록
	 */
	public List<BoardItem> listRecentBoardItem(String boardId, Integer count) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", boardId);
		parameter.put("count", count == null ? DEFAULT_RECENT_ITEM_COUNT
				: count);

		return this.boardItemDao.listRecentBoardItem(parameter);
	}

	/**
	 * 카페 최근 게시물 목록
	 */
	public SearchResult<BoardItem> listRecentBoardItemForCafe(
			BoardItemSearchCondition searchCondition) {

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<BoardItem> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount()
				+ searchCondition.getPagePerRecord());

		List<BoardItem> list = boardItemDao
				.listRecentBoardItemForCafe(searchCondition);

		if (list == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount()
					+ searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (BoardItem boardItem : list) {

				if (!user.getLocaleCode().equals("ko")) {
					boardItem.setRegisterName(boardItem.getUser()
							.getUserEnglishName());
				} else {
					boardItem
							.setRegisterName(boardItem.getUser().getUserName());
				}
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount()
					+ searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(list.size());
		}

		searchResult = new SearchResult<BoardItem>(list, searchCondition);

		return searchResult;

	}

	/* WS 추가된 내역 05/17 */

	/**
	 * 게시물 본문의 html 부분 제거
	 */
	public static String getText(String content) {

		Pattern scripts = Pattern.compile(
				"<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		Pattern style = Pattern.compile("<style[^>]*>.*</style>",
				Pattern.DOTALL);
		Pattern tags = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		Pattern nTags = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		Pattern entityRefs = Pattern.compile("&[^;]+;");
		Pattern whiteSpace = Pattern.compile("\\s\\s+");

		Matcher m;

		String convertedContents = content;

		m = scripts.matcher(content);
		convertedContents = m.replaceAll("");

		m = style.matcher(content);
		convertedContents = m.replaceAll("");

		m = tags.matcher(content);
		convertedContents = m.replaceAll("");

		m = nTags.matcher(content);
		convertedContents = m.replaceAll("");

		m = entityRefs.matcher(content);
		convertedContents = m.replaceAll("");

		m = whiteSpace.matcher(content);
		convertedContents = m.replaceAll(" ");

		convertedContents = replace(content, "\n", "<br>");
		return convertedContents;

	}

	/**
	 * replace
	 * 
	 * @param str
	 * @param sourceStr
	 * @param targetStr
	 * @return
	 */
	public static String replace(String str, String sourceStr, String targetStr) {

		if (str == null || sourceStr == null || targetStr == null
				|| str.length() == 0 || sourceStr.length() == 0) {
			return str;
		}

		int position = 0;
		int sourceStrLength = sourceStr.length();
		int targetStrLength = targetStr.length();
		String convertedStr = str;

		while (true) {
			if ((position = convertedStr.indexOf(sourceStr, position)) != -1) {
				if ((position + sourceStrLength) < convertedStr.length()) {
					convertedStr = convertedStr.substring(0, position)
							+ targetStr
							+ convertedStr
									.substring(position + sourceStrLength);
				} else {
					convertedStr = convertedStr.substring(0, position)
							+ targetStr;
				}

				position = position + targetStrLength;

				if (position > convertedStr.length()) {
					position = convertedStr.length();
				}
			} else {
				break;
			}
		}

		return convertedStr;
	}

	/**
	 * 개별 Cafe 게시판 최근 목록
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String, Object> map) {
		return this.boardItemDao.listBoardItemByPortlet(map);
	}

	/**
	 * 해당 게시판의 게시물중 첨부파일이 있는 게시물 조회 (삭제 배치중 )
	 */
	public List<BoardItem> listDeleteBoardItem(String boardId) {
		return this.boardItemDao.listDeleteBoardItem(boardId);
	}

	/**
	 * 게시물 이동
	 */
	public void moveBoardItem(String orgBoardId, String targetBoardId,
			String[] itemIds, User user) {

		try {
			Map<String, String> boardItemMap = new HashMap<String, String>();
			boardItemMap.put("orgBoardId", orgBoardId);
			boardItemMap.put("targetBoardId", targetBoardId);
			boardItemMap.put("updaterId", user.getUserId());
			boardItemMap.put("updaterName", user.getUserName());

			// List<String> itemIdList = StringUtil.getTokens(itemIds[0], ",");

			for (String itemId : itemIds) {

				boardItemMap.put("itemId", itemId);

				this.boardItemDao.updateBoardItemMove(boardItemMap);
				// 로그
				if (this.log.isDebugEnabled()) {
					this.log.debug("게시물 이동 : orgBoardId[" + orgBoardId
							+ "]targetBoardId[" + targetBoardId + "]itemId["
							+ itemId + "]");
				}

			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nCafe 게시물 이동 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(
				name, RequestAttributes.SCOPE_SESSION);
	}

	public void updateMailCount(String itemId) {

		this.boardItemDao.updateMailCount(itemId);

	}

	public void updateMblogCount(String itemId) {
		this.boardItemDao.updateMblogCount(itemId);
	}

}
