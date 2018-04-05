/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardItemDao;
import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardItemVersionDao;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItemVersion;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemVersionSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemVersionService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author happyi1018
 * @version $Id: BoardItemVersionServiceImpl.java 9777 2011-05-05 07:10:37Z
 *          happyi1018 $
 */
@Service("wsBoardItemVersionServiceImpl")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BoardItemVersionServiceImpl implements BoardItemVersionService {

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BoardItemVersionDao boardItemVersionDao;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private FileService fileService;

	@Autowired
	private BoardDao boardDao;

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 게시물 버전 목록
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItemVersion> listBoardItemBySearchCondition(BoardItemVersionSearchCondition searchCondition) {

		Integer count = this.boardItemVersionDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItemVersion> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItemVersion>(searchCondition);

		} else {

			List<BoardItemVersion> boardItemVersionList = this.boardItemVersionDao
					.listBySearchCondition(searchCondition);

			List<FileData> fileDataList = null;
			int i = 0;
			for (BoardItemVersion boardItemVersion : boardItemVersionList) {

				if (boardItemVersion.getAttachFileCount() > 0) {
					// 첨부 파일 리스트를 가져와 게시물에 넣는다
					// fileDataList =
					// this.fileService.getItemFile(boardItemVersion.getVersionId(),
					// BoardItem.ATTECHED_FILE);
					fileDataList = this.fileService.getItemFile(boardItemVersion.getVersionId(),
							BoardItem.ITEM_TYPE_CODE);
					boardItemVersion.setFileDataList(fileDataList);

					ObjectMapper mapper = new ObjectMapper();
					// 파일 목록을 JSON으로 변환한다.

					if (fileDataList != null) {
						try {
							boardItemVersion.setFileDataListJson(mapper.writeValueAsString(fileDataList));
						} catch (JsonGenerationException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonGenerationException Error ");
							}
						} catch (JsonMappingException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonMappingException Error ");
							}
						} catch (IOException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson IOException Error ");
							}
						}
					}
				}
				boardItemVersionList.set(i, boardItemVersion);
				i++;
			}

			searchResult = new SearchResult<BoardItemVersion>(boardItemVersionList, searchCondition);
		}

		return searchResult;

	}

	/**
	 * 게시물 버전 비교
	 */
	public BoardItemVersion compareBoardItemVersion(List<String> versionIds) {

		BoardItemVersion boardItemVersion = new BoardItemVersion();
		BoardItemVersion boardItemVersion1 = new BoardItemVersion();
		for (int i = 0; i < versionIds.size(); i++) {

			String versionId = versionIds.get(i);
			if (i == 0) {

				boardItemVersion = this.boardItemVersionDao.getVersionItem(versionId);

				List<FileData> fileDataList = null;
				if (boardItemVersion.getAttachFileCount() > 0) {
					// 첨부 파일 리스트를 가져와 게시물에 넣는다
					// fileDataList =
					// this.fileService.getItemFile(boardItemVersion.getVersionId(),
					// BoardItem.ATTECHED_FILE);
					fileDataList = this.fileService.getItemFile(boardItemVersion.getVersionId(),
							BoardItem.ITEM_TYPE_CODE);
					boardItemVersion.setFileDataList(fileDataList);

					ObjectMapper mapper = new ObjectMapper();
					// 파일 목록을 JSON으로 변환한다.

					if (fileDataList != null) {
						try {
							boardItemVersion.setFileDataListJson(mapper.writeValueAsString(fileDataList));
						} catch (JsonGenerationException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonGenerationException Error ");
							}
						} catch (JsonMappingException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonMappingException Error ");
							}
						} catch (IOException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson IOException Error ");
							}
						}
					}
				}
			} else {
				boardItemVersion1 = this.boardItemVersionDao.getVersionItem(versionId);
				List<FileData> fileDataList = null;
				if (boardItemVersion1.getAttachFileCount() > 0) {
					// 첨부 파일 리스트를 가져와 게시물에 넣는다
					fileDataList = this.fileService.getItemFile(boardItemVersion1.getVersionId(),
							BoardItem.ITEM_TYPE_CODE);
					boardItemVersion1.setFileDataList(fileDataList);

					ObjectMapper mapper = new ObjectMapper();
					// 파일 목록을 JSON으로 변환한다.

					if (fileDataList != null) {
						try {
							boardItemVersion1.setFileDataListJson(mapper.writeValueAsString(fileDataList));
						} catch (JsonGenerationException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonGenerationException Error ");
							}
						} catch (JsonMappingException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson JsonMappingException Error ");
							}
						} catch (IOException e) {
							// 로그
							if (this.log.isDebugEnabled()) {
								this.log.debug("버전관리 목록 : fileDataListJson IOException Error ");
							}
						}
					}
				}
			}
		}
		boardItemVersion.setCompareBoardItem(boardItemVersion1);
		return boardItemVersion;
	}

	/**
	 * 게시물 되돌리기 - 선택한 버전 정보로 변경
	 */
	public int returnBoardItemVersion(String versionId, User user) {

		BoardItemVersion boardItemVersion = new BoardItemVersion();

		boardItemVersion = this.boardItemVersionDao.getVersionItem(versionId);

		BoardItem boardItem = new BoardItem();
		boardItem = boardItemDao.get(boardItemVersion.getItemId());

		boardItem.setTitle(boardItemVersion.getTitle());
		boardItem.setContents(boardItemVersion.getContents());
		boardItem.setStartDate(boardItemVersion.getStartDate());
		boardItem.setEndDate(boardItemVersion.getEndDate());
		boardItem.setAttachFileCount(boardItemVersion.getAttachFileCount());
		boardItem.setItemDisplay(boardItemVersion.getItemDisplay());
		boardItem.setUpdaterId(boardItemVersion.getRegisterId());
		boardItem.setUpdaterName(boardItemVersion.getRegisterName());

		boardItemDao.updateItem(boardItem);

		String newVersionId = idgenService.getNextId();
		// 되돌리기 버전 새버전으로 등록
		boardItemVersion.setVersionId(newVersionId);

		String versionId1 = boardItemVersionDao.create(boardItemVersion);

		// 첨부파일 처리
		// 게시판 정보 조회
		Board board = this.boardDao.get(boardItem.getBoardId());

		List<FileData> fileDataList = new ArrayList<FileData>();
		// 버전 정보 미사용시 itemId로 첨부 조회
		if (board.getVersionManage() == 0) {
			// 첨부 파일 리스트를 가져와 게시물에 넣는다
			fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ATTECHED_FILE);
		} else {
			// 첨부 파일 리스트를 가져와 게시물에 넣는다
			fileDataList = this.fileService.getItemFile(versionId, BoardItem.ATTECHED_FILE);
		}

		// 첨부파일 등록
		if (fileDataList != null) {
			List<String> fileIdList = new ArrayList<String>();
			for (FileData fileData : fileDataList) {
				fileIdList.add(fileData.getFileId());
			}
			this.fileService.copyForTransferVersion(fileIdList, newVersionId, BoardItem.ITEM_TYPE, user);
		}

		if (versionId1 != null) {
			return 1;
		} else {
			return 0;
		}
	}

}
