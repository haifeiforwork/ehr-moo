/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service.impl;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardAssessItemDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemVersionDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemReader;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemTarget;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemVersion;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.model.BoardReference;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemBatchService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.kms.main.service.KmsService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;
import com.tagfree.util.MimeUtil;
/**
 * BoardItemService 구현체 클래스
 */
@Service("kmsBoardItemServiceImpl")
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
	private BoardDao boardDao;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private BoardItemVersionDao boardItemVersionDao;

	@Autowired
	ACLService aclService;

	@Autowired
	WorkspaceService workspaceService;

	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private BoardAssessItemDao boardAssessItemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardItemBatchService boardBatchItemService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;

	private static final String CLASS_NAME = "Coll-BD-Item";
	
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	@Autowired
	private KmsService kmsService;

	/**
	 * 게시물 목록
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> listBoardItemBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.countBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
//	public SearchResult<BoardItem> listBoardItemBySearchCondition(
//			BoardItemSearchCondition searchCondtion) {
//		
//		Integer count = this.boardItemDao.countBySearchCondition(searchCondtion);
//
//		searchCondtion.terminateSearchCondition(count);
//
//		SearchResult<BoardItem> searchResult = null;
//		if (searchCondtion.isEmptyRecord()) {
//			searchResult = new SearchResult<BoardItem>(searchCondtion);
//
//		} else {
//
//			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition(searchCondtion);
//
//			if (boardItemList != null && boardItemList.size() > 0) {
//				for (BoardItem boardItem : boardItemList) {
//					List<Tag> tagList = this.tagService.listTagByItemId(
//							boardItem.getItemId(),
//							TagConstants.ITEM_TYPE_KMS);
//					boardItem.setTagList(tagList);
//				}
//			}
//
//			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondtion);
//		}
//
//		return searchResult;
//
//	}

	/**
	 * 게시물 정보 조회
	 */
	public BoardItem readBoardItem(String itemId) {

		// 게시물을 가져온다.
		BoardItem boardItem = this.boardItemDao.get(itemId);

		// 게시판 정보 조회
//		Board board = this.boardDao.get(boardItem.getBoardId());

		List<FileData> fileDataList = new ArrayList<FileData>();
		// 버전 정보 미사용시 itemId로 첨부 조회
//		if (board.getVersionManage() == 0) {
			// 첨부 파일 리스트를 가져와 게시물에 넣는다
			fileDataList = this.fileService.getItemFile(itemId,
					BoardItem.ATTECHED_FILE);
//		} else {
//			BoardItemVersion boardItemVersion = this.boardItemVersionDao.getMaxVersionId(itemId);
//			if (boardItemVersion != null) {
//
//				// 첨부 파일 리스트를 가져와 게시물에 넣는다
//				fileDataList = this.fileService.getItemFile(
//						boardItemVersion.getVersionId(),
//						BoardItem.ATTECHED_FILE);
//			}
//		}
			
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, BoardItem.ITEM_FILE_TYPE);
		boardItem.setEcmFileDataList(ecmFileDataList);

		boardItem.setFileDataList(fileDataList);

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(
				itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

//		// 게시물과 연관되는 답글 Thread를 가져와 게시물에 넣는다
//		List<BoardItem> boardItemList = this.boardItemDao.listReplayItemThreadByItemId(boardItem.getItemGroupId());
//		boardItem.setReplyItemThreadList(boardItemList);
//
		// 태그 목록을 조회하고 게시물에 넣는다
		List<Tag> tagList = this.tagService.listTagByItemId(itemId,
				TagConstants.ITEM_TYPE_KMS);
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
		
//		// Read 하위 그룹 체크여부
//		int searchSubFlag = 0;
//		// 개별 설정 그룹/사용자 정보 Load
//		if (boardItem.getReadPermission().equals("4")) {
//			ACLResourcePermission aclResourcePermission = this.aclService
//					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
//							"READ");
//			if (aclResourcePermission != null) {
//				aclResourcePermission = this.aclService
//						.listDetailPermission(aclResourcePermission);
//
//				if (aclResourcePermission.getGroupPermissionList() != null) {
//					for (ACLGroupPermission aclGroupPerm : aclResourcePermission
//							.getGroupPermissionList()) {
//						searchSubFlag = aclGroupPerm.getHierarchyPermission();
//						if (searchSubFlag > 0) {
//							boardItem.setSearchSubFlag(searchSubFlag);
//							break;
//						}
//					}
//				}
//			}
//			boardItem.setAclReadPermissionList(aclResourcePermission);
//		}
		List<BoardItemTarget> targetList1 = new ArrayList<BoardItemTarget>();
		
		List<BoardItem> list1 = this.boardItemDao.selectTargetGroup(itemId);
		if (list1 != null && list1.size() > 0) {
			for (BoardItem target1 : list1) {
				BoardItemTarget tempTarget = new BoardItemTarget();
				tempTarget.setTargetGroupId(target1.getTargetGroupId());
				tempTarget.setTargetGroupName(target1.getTargetGroupName());
				targetList1.add(tempTarget);
			}
		}
		boardItem.setTargetGroup(targetList1);
		
		List<BoardItemTarget> targetList2 = new ArrayList<BoardItemTarget>();
		
		List<BoardItem> list2 = this.boardItemDao.selectTargetUser(itemId);
		if (list2 != null && list2.size() > 0) {
			for (BoardItem target2 : list2) {
				BoardItemTarget tempTarget = new BoardItemTarget();
				tempTarget.setTargetGroupId(target2.getTargetGroupId());
				tempTarget.setTargetGroupName(target2.getTargetGroupName());
				targetList2.add(tempTarget);
			}
		}
		boardItem.setTargetUser(targetList2);
		
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

			Board board = boardDao.get(boardItem.getBoardId());

			tag.setTagName(boardItem.getTag());
			tag.setTagItemId(boardItem.getItemId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_KMS);
			// tag.setTagItemSubType(boardItem.getBoardId());
//			tag.setTagItemSubType(board.getWorkspaceId());
			tag.setTagItemName(boardItem.getTitle());
			tag.setTagItemContents(boardItem.getContents());
			tag.setTagItemUrl("/collpack/kms/board/boardItem/readBoardItemView.do?&docPopup=true&boardId="
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
		// if (boardItem.getEditorFileLinkList() != null &&
		// boardItem.getEditorFileLinkList().get(0) != null) {
		// boardItem.setImageFileId(boardItem.getEditorFileLinkList().get(0).getFileId());
		// }
		// CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if (boardItem.getEditorFileLinkList() != null
				&& boardItem.getEditorFileLinkList().size() > 0) {
			if (boardItem.getEditorFileLinkList().get(0) != null) {
				boardItem.setImageFileId(boardItem.getEditorFileLinkList()
						.get(0).getFileId());
			}
		}
		
		//등록자 그룹세팅
		HashMap<String, String> userInfo = (HashMap<String, String>) this.userDao.getKmsUserGroup(user.getUserId());
		boardItem.setGroupId(userInfo.get("GROUP_ID"));
		boardItem.setGroupName(userInfo.get("GROUP_NAME"));
		
		

		int attachCnt = 0;
		// 게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if (boardItem.getFileLinkList() != null) {
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(),
					boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE, user);
			attachCnt = boardItem.getFileLinkList().size();
		}
		
		String [] ecmFileIds = StringUtils.split(boardItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(boardItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(boardItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(boardItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(boardItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(boardItem.getEcmFileId() != null) {
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				attachCnt++;
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
				String fileId = StringUtil.replaceQuot(EncryptUtil.encryptText(idgenService.getNextId()));
				FileData fileData = new FileData();
				fileData.setFileId(fileId);
				fileData.setFilePath(ecmFilePaths[i]);
				fileData.setFileUrl1(ecmFileUrl1s[i]);
				fileData.setFileUrl2(ecmFileUrl2s[i]);
				fileData.setFileRealName(ecmFileNames[i]);
				fileData.setFileSize(0);
				fileData.setFileName(ecmFileIds[i]);
				fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
				fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
				fileData.setEditorAttach(0);
				fileData.setRegisterId(user.getUserId());
				fileData.setRegisterName(user.getUserName());
				fileData.setUpdaterId(user.getUserId());
				fileData.setUpdaterName(user.getUserName());
				this.fileDao.createEcmFile(fileData);
				
				fileData.setFileId(fileId);
				if(checkImageFile(ecmFileNames[i])){
					fileData.setFilePath(uploadFolderForImage);
				}else{
					fileData.setFilePath(uploadFolderForFile);
				}
				fileData.setFileRealName(ecmFileNames[i]);
				fileData.setFileSize(0);
				fileData.setFileName(ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExtsSize]);
				fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
				fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
				fileData.setEditorAttach(0);
				fileData.setRegisterId(user.getUserId());
				fileData.setRegisterName(user.getUserName());
				fileData.setUpdaterId(user.getUserId());
				fileData.setUpdaterName(user.getUserName());
				this.fileDao.create(fileData);
				
				//ECM 파일 링크 정보 넣기
				FileLink fileLink = new FileLink();
				String fileLinkId = idgenService.getNextId();
				fileLink.setFileId(fileId);
				fileLink.setItemId(boardItem.getItemId());
				fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(boardItem.getItemId());
				fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			boardItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(boardItem.getEcmFileId() != null) {
				for( int i = 0 ; i < ecmFileIds.length ; i++ ){
					if(checkImageFile(ecmFileNames[i])){
						File folder = new File(tempUploadRootImage);
						if (!folder.exists()) {
							folder.mkdirs();
						}
						String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
						URL url = new URL(ecmFileUrl2s[i]);
						File srcFile2 = new File(tempUploadRootImage+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
						FileUtils.copyURLToFile(url, srcFile2);
					}else{
						File folder = new File(tempUploadRoot);
						if (!folder.exists()) {
							folder.mkdirs();
						}
						String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
						URL url = new URL(ecmFileUrl2s[i]);
						File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
						FileUtils.copyURLToFile(url, srcFile2);
					}
				}
			}
			
		}
			catch(Exception e) {
		}

		
			// 게시물을 생성한다.
			String boardItemId = this.boardItemDao.create(boardItem);
		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					BoardItem.ITEM_FILE_TYPE, user);
		}

		// 태그저장
		this.saveTags(boardItem);
		
		// 활용정보 저장
		this.saveRef(boardItem);

		// Activity Stream 게시글 생성
		// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS,
		// IKepConstant.ACTIVITY_CODE_DOC_POST, boardItemId,
		// boardItem.getTitle());

		/** WS 추가 **/

		// 게시판의 버전정보 확인
		/**
		 * Map<String, String> boardMap = new HashMap<String, String>();
		 * boardMap.put("workspaceId", boardItem.getWorkspaceId());
		 * boardMap.put("boardId", boardItem.getBoardId()); Board board =
		 * boardDao.get(boardMap);
		 **/
		Board board = this.boardDao.get(boardItem.getBoardId());

//		Workspace workspace = this.workspaceService.getWorkspace(board
//				.getWorkspaceId());
//		
//		// Activity Stream 등록
//		this.activityStreamService.createForCollavoration(
//				IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST,
//				boardItem.getItemId(), 
//				boardItem.getTitle(), 
//				"BBS",
//				workspace.getWorkspaceId(), 
//				workspace.getWorkspaceName()
//				);
//
//		// 별도 공유범위 설정시 권한 리소스 등록
//		if (boardItem.getFollowBoardPermission() == 0) {
//			this.addAclResourcePermission(boardItem, "READ", user,
//					workspace.getWorkspaceId());
//		}

		// 사용
		if (board.getVersionManage() == 1) {
			// 현재수정본 버전정보에 등록

			BoardItemVersion itemVersion = new BoardItemVersion();
			itemVersion.setVersionId(boardItem.getItemId());
			// itemVersion.setVersion(1.0);
			itemVersion.setItemId(boardItem.getItemId());
			itemVersion.setItemType(boardItem.getItemType());
			itemVersion.setItemDisplay(boardItem.getItemDisplay());
			itemVersion.setTitle(boardItem.getTitle());
			itemVersion.setContents(boardItem.getContents());
			itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
			itemVersion.setStartDate(boardItem.getStartDate());
			itemVersion.setEndDate(boardItem.getEndDate());
			itemVersion.setRegisterId(user.getUserId());
			itemVersion.setRegisterName(user.getUserName());

			this.boardItemVersionDao.create(itemVersion);
		}
		
		//평가항목저장
		BoardAssessItem boardAssessItem = new BoardAssessItem();
		for(int i = 1 ; i <= 9 ; i++){
			boardAssessItem.setItemId(id);
			boardAssessItem.setAssessItem(i);
			boardAssessItemDao.createAssessItem(boardAssessItem);
		}
		 
		String[] refIds = boardItem.getRefItemIds();
		
		if(refIds != null){
			/*
			int cnt = refIds.length;
			BoardAssessItem boardAssessItem1 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			for(int i=0; i<cnt ; i++){
				boardAssessItem1.setItemId(refIds[i]);
				boardAssessItem1.setAssessItem(3);
				boardAssessItem1.setItemDisplay(1);
				ordBoardItem = this.readBoardItem(refIds[i]);
				this.boardAssessItemDao.updateAssessItem( boardAssessItem1);
				this.boardItemDao.updateMark(refIds[i]);
				
				prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
				serverIp = prop.getProperty("messenger.server.ip");
				serverPort = prop.getProperty("messenger.server.port");
				AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));

				String title 	=ordBoardItem.getRegistDate()+"에 등록한 정보에 추가 정보가 등록되었습니다.";
				String contents = "[등록정보 - "+ordBoardItem.getTitle()+"]<br>[추가정보 - "+boardItem.getTitle()+"]<br>[추가정보 작성자 - "+boardItem.getRegisterName()+"]";

				//메시지 보내기
				atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), "", title);
				atmc.send();
			}	*/
			
			BoardAssessItem boardAssessItem3 = new BoardAssessItem();
			boardAssessItem3.setItemId(id);
			boardAssessItem3.setAssessItem(3);
			boardAssessItem3.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem3);
			
		}else if(boardItem.getRefItemId() != null && !boardItem.getRefItemId().equals("")){
			/*BoardAssessItem boardAssessItem2 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			boardAssessItem2.setItemId(boardItem.getRefItemId());
			boardAssessItem2.setAssessItem(3);
			boardAssessItem2.setItemDisplay(1);
			ordBoardItem = this.readBoardItem(boardItem.getRefItemId());
			this.boardAssessItemDao.updateAssessItem( boardAssessItem2);
			this.boardItemDao.updateMark(boardItem.getRefItemId());*/
			
			BoardAssessItem boardAssessItem4 = new BoardAssessItem();
			boardAssessItem4.setItemId(id);
			boardAssessItem4.setAssessItem(3);
			boardAssessItem4.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem4);
			
			/*prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(ordBoardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 추가 정보가 등록되었습니다.";
			String contents = "[등록정보 - "+ordBoardItem.getTitle()+"]<br>[추가정보 - "+boardItem.getTitle()+"]<br>[추가정보 작성자 - "+boardItem.getRegisterName()+"]";

			//메시지 보내기
			atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), "", title);
			atmc.send();*/
		}
		
		List<String> targetGroupIds = boardItem.getTargetGroupList();
		if( targetGroupIds != null ){
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetGroupIds.get(i));
				boardItem.setIsGroup("Y");
				this.boardItemDao.insertTargetGroup(boardItem);
			}

		}
		
		List<String> targetUserIds = boardItem.getTargetList();
		
		if( targetUserIds != null ){
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetUserIds.get(i));
				boardItem.setIsGroup("N");
				this.boardItemDao.insertTargetGroup(boardItem);
			}
		}

		return boardItemId;
	}
	
	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader
				.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop
				.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
	public String getFilePath(String path) {

		String date = getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * 게시물 답변 등록
	 */
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
					boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE, user);
		}

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					BoardItem.ITEM_TYPE, user);
		}

		// 태그저장
		this.saveTags(boardItem);

		// 답글 갯수를 업데이트 한다.
		this.boardItemDao.updateReplyCount(boardItem.getItemParentId());

		/** WS 추가 **/

		Board board = this.boardDao.get(boardItem.getBoardId());
		Workspace workspace = this.workspaceService.getWorkspace(board
				.getWorkspaceId());

		// Activity Stream 등록(삭제)
//		this.activityStreamService.createForCollavoration(
//				IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST,
//				boardItem.getItemId(), boardItem.getTitle(), "BBS",
//				workspace.getWorkspaceId(), workspace.getWorkspaceName());

		// 별도 공유범위 설정시 권한 리소스 등록
		if (boardItem.getFollowBoardPermission() == 0) {
			this.addAclResourcePermission(boardItem, "READ", user,
					workspace.getWorkspaceId());
		}

		// 사용
		if (board.getVersionManage() == 1) {
			// 현재수정본 버전정보에 등록

			BoardItemVersion itemVersion = new BoardItemVersion();
			itemVersion.setVersionId(boardItem.getItemId());
			// itemVersion.setVersion(1.0);
			itemVersion.setItemId(boardItem.getItemId());
			itemVersion.setItemType(boardItem.getItemType());
			itemVersion.setItemDisplay(boardItem.getItemDisplay());
			itemVersion.setTitle(boardItem.getTitle());
			itemVersion.setContents(boardItem.getContents());
			itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
			itemVersion.setStartDate(boardItem.getStartDate());
			itemVersion.setEndDate(boardItem.getEndDate());
			itemVersion.setRegisterId(user.getUserId());
			itemVersion.setRegisterName(user.getUserName());

			this.boardItemVersionDao.create(itemVersion);
		}

		return boardItemId;
	}

	/**
	 * 게시물 수정
	 */
	public void updateBoardItem(BoardItem boardItem, User user, boolean isAssess) {

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
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : boardItem.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			boardItem.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				boardItem.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				boardItem.setAttachFileCount(attachCnt);
			}
		}
		
		
		
		String [] ecmFileIds = StringUtils.split(boardItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(boardItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(boardItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(boardItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(boardItem.getEcmFileUrl2(), "|");
		String [] ecmOldFiles = new String[ecmFileDataList.size()];
		String [] uploadFlgs = new String[ecmFileIds.length];
		int ecmOldCnt = 0;
		 
		for(FileData ecmFiles0 : ecmFileDataList) {
			ecmOldFiles[ecmOldCnt] = ecmFiles0.getFileName();
			ecmOldCnt++;
		}
		
		//첨부파일명 갱신
		FileData tempFileData = new FileData();
		for(FileData tempFiles : ecmFileDataList) {
			for(int i=0;i<ecmFileIds.length;i++){
				if(tempFiles.getFileName().equals(ecmFileIds[i])){
					tempFileData.setFileId(tempFiles.getFileId());
					tempFileData.setFileRealName(ecmFileNames[i]);
					this.fileDao.updateEcmFileName(tempFileData);
					this.fileDao.updateFileName(tempFileData);
					attachCnt = i+1;
				}
			}
		}
		
		for(int j = 0 ; j < ecmOldFiles.length ; j++) {
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				if(ecmOldFiles[j].equals(ecmFileIds[i])){
					ecmOldFiles[j] = "";
				}
			}
		}
		
		//String uploadFlg = "";
		for( int i = 0 ; i < ecmFileIds.length ; i++ ) {
			for(FileData ecmFiles2 : ecmFileDataList){
				if(ecmFiles2.getFileName().equals(ecmFileIds[i])){
					uploadFlgs[i] = "N";
				}
			}
		}
		FileLink fileLink1 = new FileLink();
		
		for(FileData ecmFiles3 : ecmFileDataList){
			for( int i = 0 ; i < ecmOldFiles.length ; i++ ) {
				if(ecmFiles3.getFileName().equals(ecmOldFiles[i])){
					fileLink1.setFileId(ecmFiles3.getFileid());
					fileLink1.setItemId(boardItem.getItemId());
					this.fileLinkDao.removeFileLink(fileLink1);
					this.fileLinkDao.removeEcmFileLink(fileLink1);
				}
			}
		}
		
		try{
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
		
		if(ecmFileIds.length > 0){
			for(int i = 0 ; i < ecmFileIds.length ; i++) {
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
				if(uploadFlgs[i] != "N"){
					
					/*File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}*/
					
					attachCnt++;
					
					String fileId = StringUtil.replaceQuot(EncryptUtil.encryptText(idgenService.getNextId()));
					FileData fileData = new FileData();
					fileData.setFileId(fileId);
					fileData.setFilePath(ecmFilePaths[i]);
					fileData.setFileUrl1(ecmFileUrl1s[i]);
					fileData.setFileUrl2(ecmFileUrl2s[i]);
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.createEcmFile(fileData);
					
					fileData.setFileId(fileId);
					if(checkImageFile(ecmFileNames[i])){
						fileData.setFilePath(uploadFolderForImage);
					}else{
						fileData.setFilePath(uploadFolderForFile);
					}
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.create(fileData);
					
					//ECM 파일 링크 정보 넣기
					FileLink fileLink = new FileLink();
					String fileLinkId = idgenService.getNextId();
					fileLink.setFileId(fileId);
					fileLink.setItemId(boardItem.getItemId());
					fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.createEcmFileLink(fileLink);
					
					fileLink.setFileId(fileId);
					fileLink.setItemId(boardItem.getItemId());
					fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.create(fileLink);
					
					//URL url = new URL(ecmFileUrl2s[i]);
					//File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					//FileUtils.copyURLToFile(url, srcFile2);
					
					
				}
				if(checkImageFile(ecmFileNames[i])){
					File folder = new File(tempUploadRootImage);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRootImage+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}else{
					File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}
			}
			
			boardItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}
		// 첨부파일 업데이트
		// if (boardItem.getFileLinkList() != null) {
		// this.fileService.saveFileLink(boardItem.getFileLinkList(),
		// boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		// }

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					BoardItem.ITEM_FILE_TYPE, user);
		}

		List<FileData> fileDateList = this.fileService.getItemFile(
				boardItem.getItemId(), BoardItem.EDITOR_FILE);

		// CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if (fileDateList != null && fileDateList.size() > 0) {
			boardItem.setImageFileId(fileDateList.get(0).getFileId());
		}

		// 수정전 게시물 정보
		BoardItem orgBoardItem = this.boardItemDao.get(boardItem.getItemId());
		orgBoardItem.setItemType(boardItem.getItemType());
		
		//등록자 정보
		boardItem.setRegisterId(orgBoardItem.getRegisterId());
		boardItem.setRegisterName(orgBoardItem.getRegisterName());
		
		// 수정자 정보
		boardItem.setUpdaterId(user.getUserId());
		boardItem.setUpdaterName(user.getUserName());
		
		if(orgBoardItem.getStatus() < 3 && boardItem.getStatus() > 2){
			// 평가자 정보
			boardItem.setAssessorId(user.getUserId());
			boardItem.setAssessorName(user.getUserName());
		}else if(orgBoardItem.getStatus() == 3 && boardItem.getStatus() == 3){
			boardItem.setAssessorId("");
			boardItem.setAssessorName("");
		}
		
		this.boardItemDao.update(boardItem);

		// 태그저장
		this.saveTags(boardItem);

		/** WS 추가 **/

		Board board = this.boardDao.get(boardItem.getBoardId());
		Workspace workspace = this.workspaceService.getWorkspace(board
				.getWorkspaceId());

		// Activity Stream 수정
//		this.activityStreamService.createForCollavoration(
//				IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT,
//				boardItem.getItemId(), boardItem.getTitle(), "BBS",
//				workspace.getWorkspaceId(), workspace.getWorkspaceName());

		// 별도 공유범위 설정시 권한 리소스 등록
		if (boardItem.getFollowBoardPermission() == 0) {
			this.addAclResourcePermission(boardItem, "READ", user,
					workspace.getWorkspaceId());
		} else {
			// 게시판과 공유범위 동일시 기존 리소스 삭제
			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission hasAclResourcePermission = this.aclService
					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
							"READ");

			if (hasAclResourcePermission != null) {
				this.aclService.deleteContentPermission(CLASS_NAME,
						boardItem.getItemId());
			}
		}

		// 첨부파일 업데이트 - 버전 미사용시
		if (board.getVersionManage() == 0) {
			if (boardItem.getFileLinkList() != null) {
				this.fileService.saveFileLink(boardItem.getFileLinkList(),
						boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE, user);
			}
		}
		// 게시물 버전관리 사용시
		else if (board.getVersionManage() == 1) {

			// 게시물 버전정보 존재여부
			boolean isVersion = this.boardItemVersionDao.exists(boardItem
					.getItemId());

			// 이전버전이 없으면 ( 버전 미사용->사용으로 변경된 것일 경우 수정전의 데이터 버전등록)
			if (!isVersion) {
				// 이전버전 1.0 등록
				// String versionId = idgenService.getNextId();

				BoardItemVersion oldItemVersion = new BoardItemVersion();
				oldItemVersion.setVersionId(orgBoardItem.getItemId());
				// itemVersion.setVersion(1.0);
				oldItemVersion.setItemId(orgBoardItem.getItemId());
				oldItemVersion.setItemType(orgBoardItem.getItemType());
				oldItemVersion.setItemDisplay(orgBoardItem.getItemDisplay());
				oldItemVersion.setTitle(orgBoardItem.getTitle());
				oldItemVersion.setContents(orgBoardItem.getContents());
				oldItemVersion.setAttachFileCount(orgBoardItem
						.getAttachFileCount());
				oldItemVersion.setStartDate(orgBoardItem.getStartDate());
				oldItemVersion.setEndDate(orgBoardItem.getEndDate());
				oldItemVersion.setRegisterId(user.getUserId());
				oldItemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(oldItemVersion);

				// 현재수정본 버전정보에 등록
				String versionId = this.idgenService.getNextId();

				BoardItemVersion itemVersion = new BoardItemVersion();
				itemVersion.setVersionId(versionId);
				// itemVersion.setVersion(1.0);
				itemVersion.setItemId(boardItem.getItemId());
				itemVersion.setItemType(boardItem.getItemType());
				itemVersion.setItemDisplay(boardItem.getItemDisplay());
				itemVersion.setTitle(boardItem.getTitle());
				itemVersion.setContents(boardItem.getContents());
				itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
				itemVersion.setStartDate(boardItem.getStartDate());
				itemVersion.setEndDate(boardItem.getEndDate());
				itemVersion.setRegisterId(user.getUserId());
				itemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(itemVersion);

				// 첨부파일 등록
				if (boardItem.getFileLinkList() != null) {
					this.fileService.saveFileLink(boardItem.getFileLinkList(),
							boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE,
							user);
				}
			} else {

				// 현재수정본 버전정보에 등록
				String versionId = this.idgenService.getNextId();

				BoardItemVersion itemVersion = new BoardItemVersion();
				itemVersion.setVersionId(versionId);
				// itemVersion.setVersion(1.0);
				itemVersion.setItemId(boardItem.getItemId());
				itemVersion.setItemType(boardItem.getItemType());
				itemVersion.setItemDisplay(boardItem.getItemDisplay());
				itemVersion.setTitle(boardItem.getTitle());
				itemVersion.setContents(boardItem.getContents());
				itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
				itemVersion.setStartDate(boardItem.getStartDate());
				itemVersion.setEndDate(boardItem.getEndDate());
				itemVersion.setRegisterId(user.getUserId());
				itemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(itemVersion);

				// 첨부파일 등록
				/**
				 * if (boardItem.getFileLinkList() != null) { List<String>
				 * fileIdList = new ArrayList<String>(); for(FileLink
				 * fileLink:boardItem.getFileLinkList()) {
				 * fileIdList.add(fileLink.getFileId()); }
				 * this.fileService.copyForTransferVersion(fileIdList,
				 * itemVersion.getVersionId(), BoardItem.ITEM_TYPE, user); }
				 **/
				this.fileService.copyByFileLinkListVersion(
						boardItem.getFileLinkList(),
						itemVersion.getVersionId(), BoardItem.ITEM_FILE_TYPE,
						user);
			}
			// 수정전 게시물 버전정보에 등록
			// 첨부파일 처리
		}
		
		// 활용정보 저장
		this.saveRef(boardItem);
		
		
		
		if(isAssess){
			//평가정보Update
			if(boardItem.getStatus() > 2 || boardItem.getStatus() == 1){
				this.updateAssessItem(boardItem);	
			}
		}
		
		String[] refIds = boardItem.getRefItemIds();
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		
		if(refIds != null && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")){
			int cnt = refIds.length;
			BoardAssessItem boardAssessItem1 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			for(int i=0; i<cnt ; i++){
				boardAssessItem1.setItemId(refIds[i]);
				boardAssessItem1.setAssessItem(3);
				boardAssessItem1.setItemDisplay(1);
				ordBoardItem = this.readBoardItem(refIds[i]);
				this.boardAssessItemDao.updateAssessItem( boardAssessItem1);
				this.boardItemDao.updateMark(refIds[i]);
				
				prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
				serverIp = prop.getProperty("messenger.server.ip");
				serverPort = prop.getProperty("messenger.server.port");
				AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));

				String title 	=ordBoardItem.getRegistDate()+"에 등록한 정보에 추가 정보가 등록되었습니다.";
				String contents = "";
				if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
					contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]<br>[추가정보 작성자: "+boardItem.getRegisterName()+"]";
				}else{
					contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]";
				}
				url += ordBoardItem.getRegisterId();
				//메시지 보내기
				//atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
				atmc.addMessage(ordBoardItem.getRegisterId(), "", contents.toString(), url, title,"smspop");
				if(StringUtils.isNotEmpty(boardItem.getAssessorId())){
					atmc.send();
				}
				
			}
			
			
			BoardAssessItem boardAssessItem3 = new BoardAssessItem();
			boardAssessItem3.setItemId(boardItem.getItemId());
			boardAssessItem3.setAssessItem(3);
			boardAssessItem3.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem3);
			
		}
		if(boardItem.getRefItemId() != null && boardItem.getRefItemId() != "" && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")){
			BoardAssessItem boardAssessItem2 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			boardAssessItem2.setItemId(boardItem.getRefItemId());
			boardAssessItem2.setAssessItem(3);
			boardAssessItem2.setItemDisplay(1);
			ordBoardItem = this.readBoardItem(boardItem.getRefItemId());
			this.boardAssessItemDao.updateAssessItem( boardAssessItem2);
			this.boardItemDao.updateMark(boardItem.getRefItemId());
			
			BoardAssessItem boardAssessItem4 = new BoardAssessItem();
			boardAssessItem4.setItemId(boardItem.getItemId());
			boardAssessItem4.setAssessItem(3);
			boardAssessItem4.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem4);
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(ordBoardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 추가 정보가 등록되었습니다.";
			String contents = "";
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]<br>[추가정보 작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]";
			}
			url += ordBoardItem.getRegisterId();
			//메시지 보내기
			//atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
			atmc.addMessage(ordBoardItem.getRegisterId(), "", contents.toString(), url, title,"smspop");
			if(StringUtils.isNotEmpty(boardItem.getAssessorId())){
				atmc.send();
			}
		}
		
		
		List<HashMap<String, String>> keywordList = kmsService.listKeywordAll();
		
		if(StringUtils.isNotEmpty(boardItem.getAssessorId()) && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String contents = ""; 
			
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[관심지식 작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			
			
			//boardBatchItemService.messageSendKms(user.getUserName(),keywordList,contents.toString(),url,boardItem.getTitle());
			if(StringUtils.isEmpty(orgBoardItem.getAssessorId()) && boardItem.getStatus() == 3){
				boardBatchItemService.messageSendKms("",keywordList,contents.toString(),url,boardItem.getTitle());
			}
		}
		
		this.boardItemDao.deleteTargetGroup(boardItem.getItemId());
		
		sendMail(user,boardItem,orgBoardItem);
		/*List<String> targetGroupIds = boardItem.getTargetGroupList();
		if( targetGroupIds != null){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String title = boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = "";
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetGroupIds.get(i));
				boardItem.setIsGroup("Y");
				this.boardItemDao.insertTargetGroup(boardItem);
				List<BoardItem> targetUserList = this.boardItemDao.selectTargetUserList(targetGroupIds.get(i));
				
				String[] recipientId = new String[targetUserList.size()];
				for(int ti = 0; ti < targetUserList.size(); ti++){
					recipientId[ti] = targetUserList.get(ti).getTargetUserId();
				}
				if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
					//boardBatchItemService.messageSend(user.getUserName(),recipientId,contents.toString(),url,title);
					if(StringUtils.isEmpty(orgBoardItem.getAssessorId()) && boardItem.getStatus() == 3){
						boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
					}
				}
				
			}

		}
		
		List<String> targetUserIds = boardItem.getTargetList();
		
		if( targetUserIds != null){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String title = boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = "";
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			String[] recipientId = new String[targetUserIds.size()];
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetUserIds.get(i));
				boardItem.setIsGroup("N");
				this.boardItemDao.insertTargetGroup(boardItem);
				recipientId[i] = targetUserIds.get(i);
			}
			if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
				//boardBatchItemService.messageSend(user.getUserName(),recipientId,contents.toString(),url,title);
				if(StringUtils.isEmpty(orgBoardItem.getAssessorId()) && boardItem.getStatus() == 3){
					boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
				}
			}
		}*/

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendMail(User user, BoardItem boardItem, BoardItem orgBoardItem ) {
    	List<User> recipientList = new ArrayList<User>();
    	
    	List<String> targetGroupIds = boardItem.getTargetGroupList();
		if( targetGroupIds != null){
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetGroupIds.get(i));
				boardItem.setIsGroup("Y");
				this.boardItemDao.insertTargetGroup(boardItem);
				List<BoardItem> targetUserList = this.boardItemDao.selectTargetUserList(targetGroupIds.get(i));
				List<String> targetUserIdList = new ArrayList<String>();
				for(int ti = 0; ti < targetUserList.size(); ti++){
					targetUserIdList.add(ti, targetUserList.get(ti).getTargetUserId());
				}
				List<User> targetUsers = userDao.listUserInfo(targetUserIdList);
				
				Mail mail = new Mail();

				mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
				mail.setMailTemplatePath("simpleMailTemplate.vm");
				
				int len = targetUsers.size();
				List recipients = new ArrayList();
				HashMap<String, String> recip;
				for (int j = 0; j < len; j++) {
					User rm = (User) targetUsers.get(j);
					recip = new HashMap<String, String>();

					recip.put("email", rm.getMail());
					recip.put("name", rm.getUserName());

					recipients.add(recip);
				}
				if(len > 0){
					mail.setToEmailList(recipients);
					
					Map dataMap = new HashMap();
					
					
					
					Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
			        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
					String url = "";
					url = baseUrl+"/collpack/kms/board/boardItem/readSearchItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow();
					
					dataMap.put("regdate", getToday("yyyy-MM-dd"));
					if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
						mail.setTitle(boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다");
						dataMap.put("register", boardItem.getRegisterName());
					}else{
						mail.setTitle("지식광장 글이 공유되었습니다");
						dataMap.put("register", "익명");
					}
					
					dataMap.put("title", boardItem.getTitle());
					dataMap.put("url", url);
					
					User sender = user;
					
					if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
						if(StringUtils.isEmpty(orgBoardItem.getAssessorId()) && boardItem.getStatus() == 3){
							mailSendService.sendMail(mail, dataMap, sender);
						}
					}
				}
			}	
		}
		
		List<String> targetUserIds = boardItem.getTargetList();
		
		if( targetUserIds != null){
			String[] recipientId = new String[targetUserIds.size()];
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetUserIds.get(i));
				boardItem.setIsGroup("N");
				this.boardItemDao.insertTargetGroup(boardItem);
				recipientId[i] = targetUserIds.get(i);
			}
			
			List<User> targetUsers = userDao.listUserInfo(targetUserIds);
			Mail mail = new Mail();

			mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			mail.setMailTemplatePath("simpleMailTemplate.vm");
			
			int len = targetUsers.size();
			List recipients = new ArrayList();
			HashMap<String, String> recip;
			for (int j = 0; j < len; j++) {
				User rm = (User) targetUsers.get(j);
				recip = new HashMap<String, String>();

				recip.put("email", rm.getMail());
				recip.put("name", rm.getUserName());

				recipients.add(recip);
			}
			if(len > 0){
				mail.setToEmailList(recipients);
				
				Map dataMap = new HashMap();
				
				
				Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
				String url = "";
				url = baseUrl+"/collpack/kms/board/boardItem/readSearchItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow();
				
				dataMap.put("regdate", getToday("yyyy-MM-dd"));
				if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
					mail.setTitle(boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다");
					dataMap.put("register", boardItem.getRegisterName());
				}else{
					mail.setTitle("지식광장 글이 공유되었습니다");
					dataMap.put("register", "익명");
				}
				dataMap.put("title", boardItem.getTitle());
				dataMap.put("url", url);
				
				User sender = user;

				if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
					if(StringUtils.isEmpty(orgBoardItem.getAssessorId()) && boardItem.getStatus() == 3){
						mailSendService.sendMail(mail, dataMap, sender);
					}
				}
			}
		}
		
	}
	
	public void updateTempBoardItem(BoardItem boardItem, User user, boolean isAssess) {

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
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : boardItem.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			boardItem.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				boardItem.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				boardItem.setAttachFileCount(attachCnt);
			}
		}
		
		
		
		String [] ecmFileIds = StringUtils.split(boardItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(boardItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(boardItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(boardItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(boardItem.getEcmFileUrl2(), "|");
		String [] ecmOldFiles = new String[ecmFileDataList.size()];
		String [] uploadFlgs = new String[ecmFileIds.length];
		int ecmOldCnt = 0;
		 
		for(FileData ecmFiles0 : ecmFileDataList) {
			ecmOldFiles[ecmOldCnt] = ecmFiles0.getFileName();
			ecmOldCnt++;
		}
		
		//첨부파일명 갱신
		FileData tempFileData = new FileData();
		for(FileData tempFiles : ecmFileDataList) {
			for(int i=0;i<ecmFileIds.length;i++){
				if(tempFiles.getFileName().equals(ecmFileIds[i])){
					tempFileData.setFileId(tempFiles.getFileId());
					tempFileData.setFileRealName(ecmFileNames[i]);
					this.fileDao.updateEcmFileName(tempFileData);
					this.fileDao.updateFileName(tempFileData);
					attachCnt = i+1;
				}
			}
		}
		
		for(int j = 0 ; j < ecmOldFiles.length ; j++) {
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				if(ecmOldFiles[j].equals(ecmFileIds[i])){
					ecmOldFiles[j] = "";
				}
			}
		}
		
		//String uploadFlg = "";
		for( int i = 0 ; i < ecmFileIds.length ; i++ ) {
			for(FileData ecmFiles2 : ecmFileDataList){
				if(ecmFiles2.getFileName().equals(ecmFileIds[i])){
					uploadFlgs[i] = "N";
				}
			}
		}
		FileLink fileLink1 = new FileLink();
		
		for(FileData ecmFiles3 : ecmFileDataList){
			for( int i = 0 ; i < ecmOldFiles.length ; i++ ) {
				if(ecmFiles3.getFileName().equals(ecmOldFiles[i])){
					fileLink1.setFileId(ecmFiles3.getFileid());
					fileLink1.setItemId(boardItem.getItemId());
					this.fileLinkDao.removeFileLink(fileLink1);
					this.fileLinkDao.removeEcmFileLink(fileLink1);
				}
			}
		}
		
		try{
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
		
		if(ecmFileIds.length > 0){
			for(int i = 0 ; i < ecmFileIds.length ; i++) {
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
				if(uploadFlgs[i] != "N"){
					
					/*File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}*/
					
					attachCnt++;
					
					String fileId = StringUtil.replaceQuot(EncryptUtil.encryptText(idgenService.getNextId()));
					FileData fileData = new FileData();
					fileData.setFileId(fileId);
					fileData.setFilePath(ecmFilePaths[i]);
					fileData.setFileUrl1(ecmFileUrl1s[i]);
					fileData.setFileUrl2(ecmFileUrl2s[i]);
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.createEcmFile(fileData);
					
					fileData.setFileId(fileId);
					if(checkImageFile(ecmFileNames[i])){
						fileData.setFilePath(uploadFolderForImage);
					}else{
						fileData.setFilePath(uploadFolderForFile);
					}
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.create(fileData);
					
					//ECM 파일 링크 정보 넣기
					FileLink fileLink = new FileLink();
					String fileLinkId = idgenService.getNextId();
					fileLink.setFileId(fileId);
					fileLink.setItemId(boardItem.getItemId());
					fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.createEcmFileLink(fileLink);
					
					fileLink.setFileId(fileId);
					fileLink.setItemId(boardItem.getItemId());
					fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.create(fileLink);
					
					//URL url = new URL(ecmFileUrl2s[i]);
					//File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					//FileUtils.copyURLToFile(url, srcFile2);
					
					
				}
				if(checkImageFile(ecmFileNames[i])){
					File folder = new File(tempUploadRootImage);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRootImage+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}else{
					File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}
			}
			
			boardItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}
		// 첨부파일 업데이트
		// if (boardItem.getFileLinkList() != null) {
		// this.fileService.saveFileLink(boardItem.getFileLinkList(),
		// boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		// }

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					BoardItem.ITEM_FILE_TYPE, user);
		}

		List<FileData> fileDateList = this.fileService.getItemFile(
				boardItem.getItemId(), BoardItem.EDITOR_FILE);

		// CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if (fileDateList != null && fileDateList.size() > 0) {
			boardItem.setImageFileId(fileDateList.get(0).getFileId());
		}

		// 수정전 게시물 정보
		BoardItem orgBoardItem = this.boardItemDao.get(boardItem.getItemId());
		orgBoardItem.setItemType(boardItem.getItemType());
		
		//등록자 정보
		boardItem.setRegisterId(orgBoardItem.getRegisterId());
		boardItem.setRegisterName(orgBoardItem.getRegisterName());
		
		// 수정자 정보
		boardItem.setUpdaterId(user.getUserId());
		boardItem.setUpdaterName(user.getUserName());
		
		if(orgBoardItem.getStatus() < 3 && boardItem.getStatus() > 2){
			// 평가자 정보
			boardItem.setAssessorId(user.getUserId());
			boardItem.setAssessorName(user.getUserName());
		}
		
		this.boardItemDao.updateTempItem(boardItem);

		// 태그저장
		this.saveTags(boardItem);

		/** WS 추가 **/

		Board board = this.boardDao.get(boardItem.getBoardId());
		Workspace workspace = this.workspaceService.getWorkspace(board
				.getWorkspaceId());

		// Activity Stream 수정
//		this.activityStreamService.createForCollavoration(
//				IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT,
//				boardItem.getItemId(), boardItem.getTitle(), "BBS",
//				workspace.getWorkspaceId(), workspace.getWorkspaceName());

		// 별도 공유범위 설정시 권한 리소스 등록
		if (boardItem.getFollowBoardPermission() == 0) {
			this.addAclResourcePermission(boardItem, "READ", user,
					workspace.getWorkspaceId());
		} else {
			// 게시판과 공유범위 동일시 기존 리소스 삭제
			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission hasAclResourcePermission = this.aclService
					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
							"READ");

			if (hasAclResourcePermission != null) {
				this.aclService.deleteContentPermission(CLASS_NAME,
						boardItem.getItemId());
			}
		}

		// 첨부파일 업데이트 - 버전 미사용시
		if (board.getVersionManage() == 0) {
			if (boardItem.getFileLinkList() != null) {
				this.fileService.saveFileLink(boardItem.getFileLinkList(),
						boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE, user);
			}
		}
		// 게시물 버전관리 사용시
		else if (board.getVersionManage() == 1) {

			// 게시물 버전정보 존재여부
			boolean isVersion = this.boardItemVersionDao.exists(boardItem
					.getItemId());

			// 이전버전이 없으면 ( 버전 미사용->사용으로 변경된 것일 경우 수정전의 데이터 버전등록)
			if (!isVersion) {
				// 이전버전 1.0 등록
				// String versionId = idgenService.getNextId();

				BoardItemVersion oldItemVersion = new BoardItemVersion();
				oldItemVersion.setVersionId(orgBoardItem.getItemId());
				// itemVersion.setVersion(1.0);
				oldItemVersion.setItemId(orgBoardItem.getItemId());
				oldItemVersion.setItemType(orgBoardItem.getItemType());
				oldItemVersion.setItemDisplay(orgBoardItem.getItemDisplay());
				oldItemVersion.setTitle(orgBoardItem.getTitle());
				oldItemVersion.setContents(orgBoardItem.getContents());
				oldItemVersion.setAttachFileCount(orgBoardItem
						.getAttachFileCount());
				oldItemVersion.setStartDate(orgBoardItem.getStartDate());
				oldItemVersion.setEndDate(orgBoardItem.getEndDate());
				oldItemVersion.setRegisterId(user.getUserId());
				oldItemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(oldItemVersion);

				// 현재수정본 버전정보에 등록
				String versionId = this.idgenService.getNextId();

				BoardItemVersion itemVersion = new BoardItemVersion();
				itemVersion.setVersionId(versionId);
				// itemVersion.setVersion(1.0);
				itemVersion.setItemId(boardItem.getItemId());
				itemVersion.setItemType(boardItem.getItemType());
				itemVersion.setItemDisplay(boardItem.getItemDisplay());
				itemVersion.setTitle(boardItem.getTitle());
				itemVersion.setContents(boardItem.getContents());
				itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
				itemVersion.setStartDate(boardItem.getStartDate());
				itemVersion.setEndDate(boardItem.getEndDate());
				itemVersion.setRegisterId(user.getUserId());
				itemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(itemVersion);

				// 첨부파일 등록
				if (boardItem.getFileLinkList() != null) {
					this.fileService.saveFileLink(boardItem.getFileLinkList(),
							boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE,
							user);
				}
			} else {

				// 현재수정본 버전정보에 등록
				String versionId = this.idgenService.getNextId();

				BoardItemVersion itemVersion = new BoardItemVersion();
				itemVersion.setVersionId(versionId);
				// itemVersion.setVersion(1.0);
				itemVersion.setItemId(boardItem.getItemId());
				itemVersion.setItemType(boardItem.getItemType());
				itemVersion.setItemDisplay(boardItem.getItemDisplay());
				itemVersion.setTitle(boardItem.getTitle());
				itemVersion.setContents(boardItem.getContents());
				itemVersion.setAttachFileCount(boardItem.getAttachFileCount());
				itemVersion.setStartDate(boardItem.getStartDate());
				itemVersion.setEndDate(boardItem.getEndDate());
				itemVersion.setRegisterId(user.getUserId());
				itemVersion.setRegisterName(user.getUserName());

				this.boardItemVersionDao.create(itemVersion);

				// 첨부파일 등록
				/**
				 * if (boardItem.getFileLinkList() != null) { List<String>
				 * fileIdList = new ArrayList<String>(); for(FileLink
				 * fileLink:boardItem.getFileLinkList()) {
				 * fileIdList.add(fileLink.getFileId()); }
				 * this.fileService.copyForTransferVersion(fileIdList,
				 * itemVersion.getVersionId(), BoardItem.ITEM_TYPE, user); }
				 **/
				this.fileService.copyByFileLinkListVersion(
						boardItem.getFileLinkList(),
						itemVersion.getVersionId(), BoardItem.ITEM_FILE_TYPE,
						user);
			}
			// 수정전 게시물 버전정보에 등록
			// 첨부파일 처리
		}
		
		// 활용정보 저장
		this.saveRef(boardItem);
		
		
		
		if(isAssess){
			//평가정보Update
			if(boardItem.getStatus() > 2 || boardItem.getStatus() == 1){
				this.updateAssessItem(boardItem);	
			}
		}
		
		String[] refIds = boardItem.getRefItemIds();
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		
		if(refIds != null && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")){
			int cnt = refIds.length;
			BoardAssessItem boardAssessItem1 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			for(int i=0; i<cnt ; i++){
				boardAssessItem1.setItemId(refIds[i]);
				boardAssessItem1.setAssessItem(3);
				boardAssessItem1.setItemDisplay(1);
				ordBoardItem = this.readBoardItem(refIds[i]);
				this.boardAssessItemDao.updateAssessItem( boardAssessItem1);
				this.boardItemDao.updateMark(refIds[i]);
				
				prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
				serverIp = prop.getProperty("messenger.server.ip");
				serverPort = prop.getProperty("messenger.server.port");
				AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));

				String title 	=ordBoardItem.getRegistDate()+"에 등록한 정보에 추가 정보가 등록되었습니다.";
				String contents = "";
				if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
					contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]<br>[추가정보 작성자: "+boardItem.getRegisterName()+"]";
				}else{
					contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]";
				}
				url += ordBoardItem.getRegisterId();
				//메시지 보내기
				//atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
				atmc.addMessage(ordBoardItem.getRegisterId(), "", contents.toString(), url, title,"smspop");
				if(StringUtils.isNotEmpty(boardItem.getAssessorId())){
					atmc.send();
				}
				
			}
			
			
			BoardAssessItem boardAssessItem3 = new BoardAssessItem();
			boardAssessItem3.setItemId(boardItem.getItemId());
			boardAssessItem3.setAssessItem(3);
			boardAssessItem3.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem3);
			
		}
		if(boardItem.getRefItemId() != null && boardItem.getRefItemId() != "" && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")){
			BoardAssessItem boardAssessItem2 = new BoardAssessItem();
			BoardItem ordBoardItem = new BoardItem();
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			boardAssessItem2.setItemId(boardItem.getRefItemId());
			boardAssessItem2.setAssessItem(3);
			boardAssessItem2.setItemDisplay(1);
			ordBoardItem = this.readBoardItem(boardItem.getRefItemId());
			this.boardAssessItemDao.updateAssessItem( boardAssessItem2);
			this.boardItemDao.updateMark(boardItem.getRefItemId());
			
			BoardAssessItem boardAssessItem4 = new BoardAssessItem();
			boardAssessItem4.setItemId(boardItem.getItemId());
			boardAssessItem4.setAssessItem(3);
			boardAssessItem4.setItemDisplay(1);
			this.boardAssessItemDao.updateAssessItem( boardAssessItem4);
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	=DateUtil.getFmtDateString(ordBoardItem.getRegistDate(), "yyyy-MM-dd")+"에 등록한 정보에 추가 정보가 등록되었습니다.";
			String contents = "";
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]<br>[추가정보 작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+ordBoardItem.getTitle()+"]<br>[추가정보 제목: "+boardItem.getTitle()+"]";
			}
			url += ordBoardItem.getRegisterId();
			//메시지 보내기
			//atmc.addMessage(ordBoardItem.getRegisterId(), user.getUserName(), contents.toString(), url, title);
			atmc.addMessage(ordBoardItem.getRegisterId(), "", contents.toString(), url, title,"smspop");
			if(StringUtils.isNotEmpty(boardItem.getAssessorId())){
				atmc.send();
			}
		}
		
		
		List<HashMap<String, String>> keywordList = kmsService.listKeywordAll();
		
		if(StringUtils.isNotEmpty(boardItem.getAssessorId()) && boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String contents = ""; 
			
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[관심지식 작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			
			
			//boardBatchItemService.messageSendKms(user.getUserName(),keywordList,contents.toString(),url,boardItem.getTitle());
			boardBatchItemService.messageSendKms("",keywordList,contents.toString(),url,boardItem.getTitle());
		}
		
		this.boardItemDao.deleteTargetGroup(boardItem.getItemId());
		List<String> targetGroupIds = boardItem.getTargetGroupList();
		if( targetGroupIds != null){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String title = boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = ""; 
			
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetGroupIds.get(i));
				boardItem.setIsGroup("Y");
				this.boardItemDao.insertTargetGroup(boardItem);
				List<BoardItem> targetUserList = this.boardItemDao.selectTargetUserList(targetGroupIds.get(i));
				
				String[] recipientId = new String[targetUserList.size()];
				for(int ti = 0; ti < targetUserList.size(); ti++){
					recipientId[ti] = targetUserList.get(ti).getTargetUserId();
				}
				if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
					//boardBatchItemService.messageSend(user.getUserName(),recipientId,contents.toString(),url,title);
					boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
				}
				
			}

		}
		
		List<String> targetUserIds = boardItem.getTargetList();
		
		if( targetUserIds != null){
			url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
			String title = boardItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = "";
			if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성자: "+boardItem.getRegisterName()+"]";
			}else{
				contents = "[등록정보 제목: "+boardItem.getTitle()+"]";
			}
			String[] recipientId = new String[targetUserIds.size()];
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				boardItem.setTargetGroupId(targetUserIds.get(i));
				boardItem.setIsGroup("N");
				this.boardItemDao.insertTargetGroup(boardItem);
				recipientId[i] = targetUserIds.get(i);
			}
			if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")&& !boardItem.getInfoGrade().equals("A")&& !boardItem.getInfoGrade().equals("B")){
				//boardBatchItemService.messageSend(user.getUserName(),recipientId,contents.toString(),url,title);
				boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
			}
		}

	}

	/**
	 * 게시물 삭제
	 */
	public void adminDeleteBoardItem(BoardItem boardItem, Boolean batch) {
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
			if (!batch) {
				Board board = this.boardDao.get(boardItem.getBoardId());
//				Workspace workspace = this.workspaceService.getWorkspace(board
//						.getWorkspaceId());

				// Activity Stream 등록(삭제)
//				this.activityStreamService.createForCollavoration(
//						IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//						IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE,
//						boardItem.getItemId(), boardItem.getTitle(), "BBS",
//						workspace.getWorkspaceId(),
//						workspace.getWorkspaceName());

				// Activity Stream 게시물 삭제 등록
				// this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS,
				// IKepConstant.ACTIVITY_CODE_DOC_DELETE,
				// loopingItem.getItemId(), boardItem.getTitle());
			}
			// 태그를 삭제한다.
			this.tagService.delete(loopingItem.getItemId(),TagConstants.ITEM_TYPE_KMS);

			// 전체 파일 삭제
			this.fileService.removeItemFile(loopingItem.getItemId());

			// 댓글 쓰레드를 삭제한다.
			this.boardLinereplyDao.physicalDeleteThreadByItemThread(loopingItem
					.getItemId());

			// 게시글 추천 정보를 삭제한다.
			this.boardRecommendDao.removeByItemId(loopingItem.getItemId());

			// 게시글 조회 정보를 삭제한다.
			//this.boardReferenceDao.removeByItemId(loopingItem.getItemId());
			
			// 활용 정보를 삭제한다.
			this.boardItemDao.deleteRefInfo(loopingItem.getItemId());

			// 게시글을 삭제한다.
			this.boardItemDao.physicalDelete(loopingItem.getItemId());
			
			// 평가항목 삭제한다.
			this.boardAssessItemDao.deleteAssessItem(loopingItem.getItemId());
		}

		if (!batch) {
			// 부모글의 답글 갯수를 업데이트 한다.
			this.boardItemDao.updateReplyCount(boardItem.getItemParentId());
		}
	}

	/**
	 * 게시물 삭제
	 */
	public void userDeleteBoardItem(BoardItem boardItem) {
		Integer count = this.boardItemDao.countChildren(boardItem.getItemId());

		if (count == 0) {
			this.adminDeleteBoardItem(boardItem, Boolean.FALSE);

		} else {
			this.boardItemDao.logicalDelete(boardItem);
			// 태그를 삭제한다.
			this.tagService.delete(boardItem.getItemId(), TagConstants.ITEM_TYPE_KMS);
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
	
	public void updateScore(BoardRecommend boardRecommend) {
		if (!this.boardRecommendDao.scoreExists(boardRecommend)) {
			this.boardRecommendDao.insertScore(boardRecommend);
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
							boardItem.getItemId(),
							TagConstants.ITEM_TYPE_WORKSPACE);
					boardItem.setTagList(tagList);
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList,
					searchCondtion);
		}

		return searchResult;
	}
	
	public List<BoardItem> getRecommendDetailList(BoardItemSearchCondition searchCondition){
		List<BoardItem> list = boardItemDao.getRecommendDetailList(searchCondition);
		return list;
	}
	
	public List<BoardItem> getRecommendReceiveDetailList(BoardItemSearchCondition searchCondition){
		List<BoardItem> list = boardItemDao.getRecommendReceiveDetailList(searchCondition);
		return list;
	}
	
	public List<BoardItem> getReplyDetailList(BoardItemSearchCondition searchCondition){
		List<BoardItem> list = boardItemDao.getReplyDetailList(searchCondition);
		return list;
	}
	
	public List<BoardItem> getReplyReceiveDetailList(BoardItemSearchCondition searchCondition){
		List<BoardItem> list = boardItemDao.getReplyReceiveDetailList(searchCondition);
		return list;
	}

	/**
	 * 게시물 다중 삭제
	 */
	public void adminMultiDeleteBoardItem(List<String> itemIds) {
		BoardItem boardItem = null;

		for (String itemId : itemIds) {
			boardItem = this.boardItemDao.get(itemId);

			// 이미 삭제가 되었다면 continue
			if (boardItem == null) {
				continue;
			}

			this.adminDeleteBoardItem(boardItem, Boolean.FALSE);
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
				this.adminDeleteBoardItem(boardItem, Boolean.FALSE);

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
	
	public BoardItem readCaution() {
		return this.boardItemDao.getCaution();
	}

	private final static Integer DEFAULT_RECENT_ITEM_COUNT = 10;

	/**
	 * 최근 게시물 목록
	 */
//	public List<BoardItem> listRecentBoardItem(String boardId, Integer count) {
//		Map<String, Object> parameter = new HashMap<String, Object>();
//
//		parameter.put("boardId", boardId);
//		parameter.put("count", count == null ? DEFAULT_RECENT_ITEM_COUNT
//				: count);
//
//		List<BoardItem> boardItemList = this.boardItemDao
//				.listRecentBoardItem(parameter);
//
//		Board board = null;
//
//		// 게시글의 게시판 정보를 넣는다.
//		for (BoardItem boardItem : boardItemList) {
//			board = this.boardDao.get(boardItem.getBoardId());
//			boardItem.setAnonymous(board == null ? Board.ANONYMOUS_BOARD
//					: board.getAnonymous());
//
//			if (boardItem.getAnonymous() == 1) {
//				boardItem.setRegisterId(null);
//				boardItem.setRegisterName(null);
//				boardItem.setUpdaterId(null);
//				boardItem.setUpdaterName(null);
//				boardItem.setUser(null);
//			}
//		}
//
//		return this.boardItemDao.listRecentBoardItem(parameter);
//	}

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

		m = scripts.matcher(convertedContents);
		convertedContents = m.replaceAll("");

		m = style.matcher(convertedContents);
		convertedContents = m.replaceAll("");

		m = tags.matcher(convertedContents);
		convertedContents = m.replaceAll("");

		m = nTags.matcher(convertedContents);
		convertedContents = m.replaceAll("");

		m = entityRefs.matcher(convertedContents);
		convertedContents = m.replaceAll("");

		m = whiteSpace.matcher(convertedContents);
		convertedContents = m.replaceAll(" ");

		convertedContents = replace(convertedContents, "\n", "<br>");
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
	 * Collaboration 메인 화면에서 나의 Workspace 중의 최근 게시물 조회
	 */
	public SearchResult<BoardItem> listMyCollBoardItemBySearchCondition(
			BoardItemSearchCondition searchCondition, User user) {

		Integer count = (searchCondition.getPageIndex() + 1) * 10;
		searchCondition.terminateSearchCondition(count);
		SearchResult<BoardItem> searchResult = null;

		List<BoardItem> boardItemList = this.boardItemDao
				.listMyCollBySearchCondition(searchCondition);
		for (BoardItem boardItem : boardItemList) {
			List<Tag> tagList = this.tagService.listTagByItemId(
					boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
			boardItem.setTagList(tagList);
			// List<FileData> fileDataList = null;
			if (boardItem.getContents() != null) {
				boardItem.setContents(getText(boardItem.getContents()));
			}
			// if (boardItem.getAttachFileCount() > 0) {
			// fileDataList =
			// this.fileService.getItemFile(boardItem.getItemId(),
			// BoardItem.ITEM_FILE_TYPE);
			// boardItem.setFileDataList(fileDataList);
			// }
		}
		searchResult = new SearchResult<BoardItem>(boardItemList,
				searchCondition);
		return searchResult;
	}

	/**
	 * 개별 WOrkspace 게시판 최근 목록
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String, String> map) {
		return this.boardItemDao.listBoardItemByPortlet(map);
	}

	/**
	 * 태그 등록
	 * 
	 * @param
	 */
	/**
	 * private void createTag(BoardItem boardItem,User user,String workspaceId){
	 * //태그 등록 - 태그 있을때 등록 if(!StringUtil.isEmpty(boardItem.getTag())){ Tag tag
	 * = new Tag(); String tagSubType = ""; tag.setTagName(boardItem.getTag());
	 * //사용자가 작성한 tag tag.setTagItemId(boardItem.getItemId()); //Item ID
	 * tag.setTagItemType(WorkspaceConstants.ITEM_TYPE_NAME); //모듈 타입 정의되어 있음.
	 * //tag.setTagItemSubType(tagSubType); //모듈 서브 타입 - 있을때만 넣기
	 * tag.setTagItemSubType(boardItem.getBoardId()); //모듈 서브 타입 - 있을때만 넣기
	 * tag.setTagItemName(boardItem.getTitle()); // 게시물 제목
	 * tag.setTagItemContents(boardItem.getTitle()); //게시물 내용 소개글
	 * tag.setTagItemUrl(
	 * "/collpack/kms/board/boardItem/readBoardItemView.do?docPopup=true&workspaceId="
	 * +workspaceId+"&itemId="+boardItem.getItemId()); //게시물 팝업창 url
	 * tag.setRegisterId(user.getUserId());
	 * tag.setRegisterName(user.getUserName());
	 * tag.setPortalId(user.getPortalId()); tagService.create(tag); } }
	 **/
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
			buffer.append("\r\nWorkspace 게시물 이동 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

	}

	/*
	 * public void createBoardItemReference(BoardItemReference
	 * boardItemReference) { try {
	 * boardItemDao.createBoardItemReference(boardItemReference); } catch
	 * (Exception e) { e.printStackTrace(); } } public void
	 * createBoardItemRecommend(BoardItemRecommend boardItemRecommend) { try {
	 * boardItemDao.createBoardItemRecommend(boardItemRecommend); } catch
	 * (Exception e) { e.printStackTrace(); } } public boolean
	 * existsBoardItemReference(BoardItemReference boardItemReference) { return
	 * boardItemDao.existsBoardItemReference(boardItemReference); } public
	 * boolean existsBoardItemRecommend(BoardItemRecommend boardItemRecommend) {
	 * return boardItemDao.existsBoardItemRecommend(boardItemRecommend); }
	 */
	/**
	 * 게시물 삭제
	 */
	public void adminDeleteBoardItem(BoardItem boardItem) {
		try {

			/**
			 * 1. 추천 관련 데이터 삭제 2. 조회 관련 데이터 삭제 3. 댓글 삭제 4.
			 */
			/**
			 * Map<String, String> boardMap = new HashMap<String, String>();
			 * boardMap.put("workspaceId", boardItem.getWorkspaceId());
			 * boardMap.put("boardId", boardItem.getBoardId()); Board board =
			 * boardDao.get(boardMap); //itemId로 해서 하위추천 전체 삭제
			 * if(boardItem.getRecommendCount()>0)
			 * boardItemDao.physicalDeleteRecommend(boardItem.getItemId());
			 * //itemId로 해서 하위 카운트 전체 삭제 if(boardItem.getHitCount()>0)
			 * boardItemDao.physicalDeleteReference(boardItem.getItemId());
			 * //TODO: itemId로 파일 삭제 if(boardItem.getLinereplyCount()>0)
			 * this.boardLinereplyDao
			 * .physicalDeleteThreadByItemThread(boardItem.getItemId()); // 버전정보
			 * 하위 게시물 포함 삭제 if(board.getVersionManage()==1)
			 * this.boardItemVersionDao
			 * .physicalDeleteThread(boardItem.getItemId());
			 **/

			// itemId로 해서 하위 댓글 전체 삭제
			this.boardItemDao.physicalDeleteThread(boardItem.getItemId());

			// 전체 파일 삭제
			if (boardItem.getAttachFileCount() > 0) {
				this.fileService.removeItemFile(boardItem.getItemId());
			}
			// 부모글의 답글 갯수를 업데이트 한다.
			if (boardItem.getItemParentId() != null) {
				this.boardItemDao.updateReplyCount(boardItem.getItemParentId());
			}
			// 권한 삭제
			ACLResourcePermission hasReadAclResourcePermission = this.aclService
					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
							"READ");
			ACLResourcePermission hasWriteAclResourcePermission = this.aclService
					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
							"WRITE");
			if (hasReadAclResourcePermission != null
					|| hasWriteAclResourcePermission != null) {
				this.aclService.deleteContentPermission(CLASS_NAME,
						boardItem.getItemId());
			}
			
			// 활용 정보를 삭제한다.
			this.boardItemDao.deleteRefInfo(boardItem.getItemId());
			
			// 평가항목 삭제한다.
			this.boardAssessItemDao.deleteAssessItem(boardItem.getItemId());
			// 게시판정보 확인
			// Map<String, String> boardMap = new HashMap<String, String>();
			// boardMap.put("workspaceId", boardItem.getWorkspaceId());
			// boardMap.put("boardId", boardItem.getBoardId());

			// Board board = boardDao.get(boardMap);
//			Board board = this.boardDao.get(boardItem.getBoardId());
//			Workspace workspace = this.workspaceService.getWorkspace(board
//					.getWorkspaceId());

			// Activity Stream 등록(삭제)
//			this.activityStreamService.createForCollavoration(
//					IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//					IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE,
//					boardItem.getItemId(), boardItem.getTitle(), "BBS",
//					workspace.getWorkspaceId(), workspace.getWorkspaceName());

			// Activity Stream 등록(삭제)
			// activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
			// IKepConstant.ACTIVITY_CODE_DOC_DELETE, boardItem.getItemId(),
			// boardItem.getTitle(), "BBS");

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 게시물 삭제 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

	}

	/*
	 * public void updateRecommendCount(String itemId) {
	 * this.boardItemDao.updateRecommendCount(itemId); } public void
	 * updateHitCount(String itemId) { this.boardItemDao.updateHitCount(itemId);
	 * }
	 */

	/**
	 * 게시물 권한 등록/수정
	 */
	public void addAclResourcePermission(BoardItem boardItem,
			String operationName, User user, String workspaceId) {

		try {
			// 계층 권한 지원 여부 입력 (1:지원, 0: 비지원)
			if (boardItem.getSearchSubFlag() == null) {
				boardItem.setSearchSubFlag(0);
			}
			// 리소스/퍼미션 오픈여부
			int openReadFlag = 0;

			// 전사오픈 1
			// 정/준회원 2 인경우 정/준/운영진 그룹 설정
			// 정회원 3 인경우 정/운영진 그룹 설정
			// 개별설정 4인 경우 해당 그룹/사용자 설정
			List<Group> groupList = new ArrayList<Group>();

			Group group = new Group();
			group.setGroupId(workspaceId);
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);

			List<String> readPermissionList = new ArrayList<String>();

			// 정/준, 정회원 체크시 Coll ACL 그룹 가져오기 ( 정/준/운영진 그룹정보)
			if (boardItem.getReadPermission().equals("2")
					|| boardItem.getReadPermission().equals("3")) {
				groupList = this.groupDao.selectOrgGroupByGroupTypeId(group);
			}
			// Read Perm
			if (boardItem.getReadPermission().equals("1")) {// 전사오픈
				openReadFlag = 1;
			} else if (boardItem.getReadPermission().equals("2")) {// 정/준회원 이상

				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else {
						readPermissionList.add("G:" + group1.getGroupId());
					}
				}
				boardItem.setReadPermissionList(readPermissionList);

			} else if (boardItem.getReadPermission().equals("3")) {// 정회원 이상

				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else if (group1.getGroupName().equals(
							WorkspaceConstants.WS_PERMISSION_GROUP_ASSOCIATE)) {
						continue;
					} else {
						readPermissionList.add("G:" + group1.getGroupId());
					}
				}
				boardItem.setReadPermissionList(readPermissionList);
			}

			ACLResourcePermission aclResourcePermission = new ACLResourcePermission();

			/*
			 * ============================================= 리소스 & 기본 권한
			 * =============================================
			 */
			aclResourcePermission.setClassName(CLASS_NAME);
			aclResourcePermission.setResourceName(boardItem.getItemId());
			aclResourcePermission.setResourceDescription(boardItem.getTitle());
			// 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈
			aclResourcePermission.setOpen(openReadFlag);

			aclResourcePermission.setUserId(user.getUserId());
			aclResourcePermission.setUserName(user.getUserName());
			aclResourcePermission.setOperationName(operationName);

			aclResourcePermission.setPermissionName(boardItem.getTitle()
					+ aclResourcePermission.getOperationName());
			aclResourcePermission.setPermissionDescription(boardItem.getTitle()
					+ aclResourcePermission.getOperationName());

			if (boardItem.getReadPermissionList() != null) {
				for (String id : boardItem.getReadPermissionList()) {
					// String id = permissionList.get(i);

					if (id.substring(0, 1).equals("G")) {
						String groupId = id.substring(id.lastIndexOf(":") + 1,
								id.length());

						aclResourcePermission.addGroupPermission(groupId,
								boardItem.getSearchSubFlag());
					} else {
						String userId = id.substring(id.lastIndexOf(":") + 1,
								id.length());
						aclResourcePermission.addAssignUserId(userId);
					}
				}
			}

			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission hasAclResourcePermission = this.aclService
					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
							operationName);

			if (hasAclResourcePermission != null) {
				this.aclService.updateContentPermission(aclResourcePermission);
			} else {
				this.aclService.createContentPermission(aclResourcePermission);
			}
		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 게시물 권한 등록/수정 ...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	public void updateMailCount(String itemId) {

		this.boardItemDao.updateMailCount(itemId);

	}

	public void updateMblogCount(String itemId) {
		this.boardItemDao.updateMblogCount(itemId);
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 최근정보 게시물 조회리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * listBoardItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> latestListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.latestCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.latestListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	@Transactional(readOnly = true)
	public SearchResult<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition searchCondtion) {
		
		Integer count = this.boardItemDao.listReaderCountBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<BoardItemReader> searchResult = null;
		if(searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItemReader>(searchCondtion);

		} else {

			List<BoardItemReader> boardItemReaderList = this.boardItemDao.listReaderBySearchCondition(searchCondtion);
			searchResult = new SearchResult<BoardItemReader>(boardItemReaderList, searchCondtion);
		}

		return searchResult;
	}
	
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> hotissueListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.hotissueCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.hotissueListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/*
	 * 우수정보 Board 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * listBoardItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> excellenceListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.excellenceCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.excellenceListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 게시물 정보 조회
	 */
	public BoardItem readBoardItem1(String itemId) {

		// 게시물을 가져온다.
		BoardItem boardItem = this.boardItemDao.get(itemId);

		// 게시판 정보 조회
//		Board board = this.boardDao.get(boardItem.getBoardId());

		List<FileData> fileDataList = new ArrayList<FileData>();
		// 버전 정보 미사용시 itemId로 첨부 조회
//		if (board.getVersionManage() == 0) {
			// 첨부 파일 리스트를 가져와 게시물에 넣는다
			fileDataList = this.fileService.getItemFile(itemId,
					BoardItem.ATTECHED_FILE);
//		} else {
//			BoardItemVersion boardItemVersion = this.boardItemVersionDao.getMaxVersionId(itemId);
//			if (boardItemVersion != null) {
//
//				// 첨부 파일 리스트를 가져와 게시물에 넣는다
//				fileDataList = this.fileService.getItemFile(
//						boardItemVersion.getVersionId(),
//						BoardItem.ATTECHED_FILE);
//			}
//		}

		boardItem.setFileDataList(fileDataList);

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(
				itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

//		// 게시물과 연관되는 답글 Thread를 가져와 게시물에 넣는다
//		List<BoardItem> boardItemList = this.boardItemDao.listReplayItemThreadByItemId(boardItem.getItemGroupId());
//		boardItem.setReplyItemThreadList(boardItemList);
//
//		// 태그 목록을 조회하고 게시물에 넣는다
//		List<Tag> tagList = this.tagService.listTagByItemId(itemId,
//				TagConstants.ITEM_TYPE_WORKSPACE);
//		boardItem.setTagList(tagList);
//
//		// 태그 문자열을 만든다.
//		if (tagList != null && tagList.size() > 0) {
//			StringBuffer tagBuffer = new StringBuffer();
//
//			for (Tag tag : tagList) {
//				tagBuffer.append(tag.getTagName());
//				tagBuffer.append(", ");
//			}
//
//			boardItem.setTag(StringUtils.substringBeforeLast(
//					tagBuffer.toString(), ","));
//		}
		
//		// Read 하위 그룹 체크여부
//		int searchSubFlag = 0;
//		// 개별 설정 그룹/사용자 정보 Load
//		if (boardItem.getReadPermission().equals("4")) {
//			ACLResourcePermission aclResourcePermission = this.aclService
//					.getContentPermission(CLASS_NAME, boardItem.getItemId(),
//							"READ");
//			if (aclResourcePermission != null) {
//				aclResourcePermission = this.aclService
//						.listDetailPermission(aclResourcePermission);
//
//				if (aclResourcePermission.getGroupPermissionList() != null) {
//					for (ACLGroupPermission aclGroupPerm : aclResourcePermission
//							.getGroupPermissionList()) {
//						searchSubFlag = aclGroupPerm.getHierarchyPermission();
//						if (searchSubFlag > 0) {
//							boardItem.setSearchSubFlag(searchSubFlag);
//							break;
//						}
//					}
//				}
//			}
//			boardItem.setAclReadPermissionList(aclResourcePermission);
//		}
		return boardItem;
	}
	
	/*
	 * 조회수 UPDATE
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #updateHitCount(java.lang.String)
	 */
public void updateHitCount(String userId, String itemId) {
		
		try {
			int refCount = this.boardItemDao.countBoardReference(userId, itemId);
			if (refCount == 0) {
				this.boardItemDao.updateBoardHitCount(itemId);
				this.boardItemDao.createBoardItemReference(userId, itemId);
			}else{
				this.boardItemDao.updateBoardItemReference(userId, itemId);
			}
			
			//등록자 그룹세팅
			HashMap<String, String> userInfo = (HashMap<String, String>) this.userDao.getKmsUserGroup(userId);
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("userId", userId);
			map.put("itemId", itemId);
			map.put("groupId", userInfo.get("GROUP_ID"));
			map.put("groupName", userInfo.get("GROUP_NAME"));
			
			//조회수 누적데이터
			int refTotCnt = this.boardItemDao.countBoardRefCumulative(map);
			if (refTotCnt == 0)
				this.boardItemDao.createBoardItemRefCumulative(map);
			else
				this.boardItemDao.updateBoardItemRefCumulative(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}	
	
	/*
	 * 활용정보 INSERT
	 * @see
	 */
	public void saveRef(BoardItem boardItem) {
		
		//활용정보 삭제
		this.boardItemDao.deleteRefInfo(boardItem.getItemId());
		
		String[] refItemIds = boardItem.getRefItemIds();
		
		if(refItemIds != null){
			
			Map<String, String> map = new HashMap<String, String>();

			int cnt = refItemIds.length;
			
			for(int i=0; i<cnt ; i++){
				map.put("itemId", boardItem.getItemId());
				map.put("refItemId", boardItem.getRefItemIds()[i]);
				//활용정보 중복검사
				int k = this.boardItemDao.countCreateRefInfo(map);
				if(k == 0)
					this.boardItemDao.createRefInfo(map);
			}			
			
		}else if(boardItem.getRefItemId() != null){
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("itemId", boardItem.getItemId());
			map.put("refItemId", boardItem.getRefItemId());
			
			//활용정보 중복검사
			int k = this.boardItemDao.countCreateRefInfo(map);
			if(k == 0)
				this.boardItemDao.createRefInfo(map);
		}
	}
	
	/*
	 * Ref 게시물 조회리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * refListBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> refListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.refCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.refListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/*
	 * 임시저장 Board 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * temporaryListBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> temporaryListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.temporaryCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.temporaryListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> targetListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.targetCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.targetListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> keyInfoListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.keyInfoCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.keyInfoListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/*
	 * 전문가 Board 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * listBoardItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> assessListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.assessCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.assessListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 전문가설정
	 */
	public void assessMove(Map<String, String> map) {
		this.boardItemDao.assessMove(map);
	}

	public List<BoardAssessItem> listAssessItem(String boardId) {
		
		return this.boardAssessItemDao.listAssessItem(boardId);
	}
	
	public void updateAssessItem(BoardItem boardItem){
		Properties commonprop = PropertyLoader
		.loadProperties("/configuration/common-conf.properties");
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		BoardAssessItem boardAssessItem = new BoardAssessItem();
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(1);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_1()));
		if(Integer.parseInt(boardItem.getItemDisplays_1())>0){
			if(boardItem.getInfoGrade() != null && boardItem.getInfoGrade() != "" && !boardItem.getInfoGrade().equals("E")){
				url = baseUrl+"/collpack/kms/board/boardItem/directReadItemView.do?itemId="+boardItem.getItemId()+"&isKnowhow="+boardItem.getIsKnowhow()+"&suserId=";
				String title = "경영진 보고정보가 등록되었습니다";
				String contents = "";
				if(boardItem.getIsKnowhow().equals("0") || boardItem.getIsKnowhow().equals("3")){
					contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성자: "+boardItem.getRegisterName()+"]<br>[작성일시:"+DateUtil.getFmtDateString(boardItem.getOriRegistDate(), "yyyy-MM-dd")+"]";
				}else{
					contents = "[등록정보 제목: "+boardItem.getTitle()+"]<br>[작성일시:"+DateUtil.getFmtDateString(boardItem.getOriRegistDate(), "yyyy-MM-dd")+"]";
				}
			
				List<BoardItem> targetUserList = this.boardItemDao.selectKeyInfoUserList();
				
				String[] recipientId = new String[targetUserList.size()];
				for(int ti = 0; ti < targetUserList.size(); ti++){
					recipientId[ti] = targetUserList.get(ti).getTargetUserId();
				}
				//boardBatchItemService.messageSend(boardItem.getRegisterName(),recipientId,contents.toString(),url,title);
				boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
				
			}
				
		}
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(2);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_2()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(3);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_3()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(4);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_4()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(5);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_5()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(6);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_6()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(7);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_7()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(8);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_8()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(9);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_9()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		/*boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(10);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_10()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(11);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_11()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(12);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_12()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(13);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_13()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(14);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_14()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(15);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_15()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(16);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_16()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
		
		boardAssessItem.setItemId(boardItem.getItemId());
		boardAssessItem.setAssessItem(17);
		boardAssessItem.setItemDisplay(Integer.parseInt(boardItem.getItemDisplays_17()));
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);*/
	}
	
	/**
	 * 전문가평가 사용자인지 체크
	 * 
	 * @param sysName
	 * @param userId
	 * @return
	 */
	public boolean isAssessor(String userId) {
		
		// 전문가평가 항목이 있는지 조회
		int count = this.boardAssessItemDao.countIsAssessor(userId);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (count < 1) {
			return Boolean.FALSE;
		}else{
			return Boolean.TRUE;
		}
	}
	
	public boolean isKeyInfoAssessor(String userId) {
		
		// 전문가평가 항목이 있는지 조회
		int count = this.boardAssessItemDao.countIsKeyInfoAssessor(userId);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (count < 1) {
			return Boolean.FALSE;
		}else{
			return Boolean.TRUE;
		}
	}
	
	/*
	 * 지식조회 Board 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * listBoardItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> searchListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.searchCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.searchListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 게시물 정보 조회
	 */
	public BoardItem readBoardItemPrint(String itemId) {

		// 게시물을 가져온다.
		//BoardItem boardItem = this.boardItemDao.getBoardItemPrint(itemId);//등록자 정보 없을시 에러
		BoardItem boardItem = this.boardItemDao.get(itemId);
		List<FileData> fileDataList = new ArrayList<FileData>();
		// 버전 정보 미사용시 itemId로 첨부 조회
		fileDataList = this.fileService.getItemFile(itemId,
					BoardItem.ATTECHED_FILE);

		boardItem.setFileDataList(fileDataList);

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(
				itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

		// 태그 목록을 조회하고 게시물에 넣는다
		List<Tag> tagList = this.tagService.listTagByItemId(itemId,
				TagConstants.ITEM_TYPE_KMS);
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
	
	/*
	 * E등급지식 Board 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService
	 * #
	 * temporaryListBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .board.search.BoardItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<BoardItem> einfogradeListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		
		Integer count = this.boardItemDao.einfogradeCountBySearchCondition(boardItemSearchCondition);

		boardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		
		if (boardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(boardItemSearchCondition);
		} else {
			List<BoardItem> boardItemList = this.boardItemDao.einfogradeListBySearchCondition(boardItemSearchCondition);

			List<FileData> fileDataList = null;

			for (BoardItem boardItem : boardItemList) {
				if (boardItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE);
					boardItem.setFileDataList(fileDataList);
				}

				//List<Tag> tagList = tagService.listTagByItemId(boardItem.getItemId(), BoardItem.ITEM_TYPE_CODE);
				//boardItem.setTagList(tagList);
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, boardItemSearchCondition);
		}

		return searchResult;
	}

	public void updateBoardItem(BoardItem boardItem, User user) {
		// TODO Auto-generated method stub
		updateBoardItem(boardItem, user, false);
	}
	
	public void updateTempBoardItem(BoardItem boardItem, User user) {
		// TODO Auto-generated method stub
		updateTempBoardItem(boardItem, user, false);
	}
	
	public List<BoardItem> expertList() {
		return this.boardItemDao.expertList();
	}
}
