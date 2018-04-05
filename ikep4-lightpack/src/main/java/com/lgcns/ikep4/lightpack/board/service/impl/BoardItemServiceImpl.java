/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
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
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardRecommendDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardBatchService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemReaderService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
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
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * BoardItemService 구현체 클래스
 */
@Service
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
	private BoardItemReaderService boardItemReaderService;
	
	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private BoardBatchService boardBatchService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	protected final org.apache.commons.logging.Log clog = LogFactory.getLog(getClass());
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService#doAlarmJobSchedule(java.util.Date, int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doBoardItemNotiJobSchedule(Date jobTime, int interval, String fileUrl) throws ParseException {
		List<BoardItemReader> boardItemList = this.boardItemReaderService.listBoardItemAllNoti();
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		Properties imageprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		Set alarmIds = new HashSet();
		//clog.debug("@@@@@@@@@@@@@@@boardItemList.size():"+boardItemList.size());
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String downUrl ="/support/fileupload/downloadFile.do?fileId=";
		String imageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_image");
		String newImageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_profile_image");

		for (BoardItemReader boardItem : boardItemList) {
			User user = this.userService.read(boardItem.getReaderId());
			List<BoardItemReader> boardItemReaderList =this.boardItemReaderService.listReaderAllMail(boardItem.getBoardItemId());
			List<FileData> contentsAddAtts = fileService.getItemFile(boardItem.getBoardItemId(), "N");//첨부된 파일 리스트를 가져온다.
			List<FileData> contentsAddImgs = fileService.getItemFileAll(boardItem.getBoardItemId(), "Y");//해당 글에 에디터로 첨부된 이미지리스트를 가져온다. 
			String contents ="";
			String filename = "";
			
			
			
			for (FileData addAtt : contentsAddAtts) {
				filename =filename+"<br><img src='" + baseUrl + "/base/images/icon/ic_file_"+addAtt.getFileExtension()+".gif' /><a title='"+addAtt.getFileRealName()+"' style='color:black;' href='"+baseUrl+downUrl+addAtt.getFileId()+"'> "+addAtt.getFileRealName()+"</a>";
			}
			
			String tmpContents = "";
			if(contentsAddImgs.size() > 0){
				tmpContents = boardItem.getContents().replaceAll("/support/fileupload/downloadFile.do[?]fileId=", "/mailimage/");
			}else{
				tmpContents = boardItem.getContents();
			}
			
			//String tmpContents2 = "";
			for (FileData addImg : contentsAddImgs) {
				try{
					addImg.setFilePhysicalPath(imageUrl+ addImg.getFilePath()+ File.separatorChar+ addImg.getFileName());
					File srcFile = new File(addImg.getFilePhysicalPath());
					FileUtils.copyFile(srcFile, new File(newImageUrl+"/mailimage/",addImg.getFileName()));
					tmpContents = tmpContents.replaceAll(addImg.getFileId(), addImg.getFileName());
				}
				catch(Exception e) {
				}
				
			}
			contents = tmpContents+filename;

			for (BoardItemReader boardItemReader : boardItemReaderList) {
				if(StringUtil.isEmpty(boardItemReader.getReaderMail())){//메일주소 없는 사용자는 메일 보내지 않음.
				}else{
			
					if(!StringUtil.isEmpty(boardItemReader.getReaderPassword())){//패스워드 없는 자는 퇴사자 일것입니다.
						HashMap<String, String> recip= new HashMap<String, String>();
						recip.put("email", boardItemReader.getReaderMail());
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ boardItemReader.getReaderMail():"+boardItemReader.getReaderMail());
						
						
						Mail mail = new Mail();
						mail.setMailType(MailConstant.MAIL_TYPE_HTML);
						if(!StringUtil.isEmpty(boardItem.getWordName())){
							mail.setTitle("["+boardItem.getWordName()+"] 공지사항이 등록되었습니다.");
						}else{
							mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");
						}
							
						
						mail.setContent(contents);
						
						
						List recipients = new ArrayList();
						recipients.add(recip);
						
						/*
						Mail mail = new Mail();
						mail.setMailType(MailConstant.MAIL_TYPE_HTML);
						mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");

						String linkUrl;
						try {
							linkUrl = commonprop.getProperty("ikep4.baseUrl")+"/messengerLogin.do?gubun=2&itemId="+boardItem.getBoardItemId()+"&j_username="+boardItemReader.getReaderId()+"&j_password="+URLEncoder.encode(boardItemReader.getReaderPassword(), "UTF-8");
							mail.setContent("<br><br>아래의 DocLink Icon을 Double Click하면 회람 문서로 이동합니다.<br><br><a href=\""+linkUrl+"\"><img src=\""+baseUrl+"/base/images/icon/ic_note_b.png\" border=\"0\"></a>");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						*/
						
						if(recipients.size() > 0) {
							mail.setToEmailList(recipients);
							Map dataMap = new HashMap();
							mailSendService.sendMailNotice(mail, dataMap, user);

						}
						
					}
				}
			}
			/*
			if(recipients.size() > 0) {
				mail.setToEmailList(recipients);
				Map dataMap = new HashMap();
				mailSendService.sendMailNotice(mail, dataMap, user);
			}
			*/
			
			alarmIds.add(boardItem.getBoardItemId());
			 
		}
		
		if(alarmIds.size()>0) {
			boardItemDao.updateMailSent(alarmIds);
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doBoardItemNotiInstant(String itemId) throws ParseException {
		List<BoardItemReader> boardItemList = this.boardItemReaderService.listBoardItemAllNotiInstant(itemId);
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		Properties imageprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String downUrl ="/support/fileupload/downloadFile.do?fileId=";
		String imageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_image");
		String newImageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_profile_image");

		for (BoardItemReader boardItem : boardItemList) {
			User user = this.userService.read(boardItem.getReaderId());
			List<BoardItemReader> boardItemReaderList =this.boardItemReaderService.listReaderAllMail(boardItem.getBoardItemId());
			List<FileData> contentsAddAtts = fileService.getItemFile(boardItem.getBoardItemId(), "N");//첨부된 파일 리스트를 가져온다.
			List<FileData> contentsAddImgs = fileService.getItemFileAll(boardItem.getBoardItemId(), "Y");//해당 글에 에디터로 첨부된 이미지리스트를 가져온다. 
			String contents ="";
			String filename = "";
			
			
			
			for (FileData addAtt : contentsAddAtts) {
				filename =filename+"<br><img src='" + baseUrl + "/base/images/icon/ic_file_"+addAtt.getFileExtension()+".gif' /><a title='"+addAtt.getFileRealName()+"' style='color:black;' href='"+baseUrl+downUrl+addAtt.getFileId()+"'> "+addAtt.getFileRealName()+"</a>";
			}
			
			String tmpContents = "";
			if(contentsAddImgs.size() > 0){
				tmpContents = boardItem.getContents().replaceAll("/support/fileupload/downloadFile.do[?]fileId=", "/mailimage/");
			}else{
				tmpContents = boardItem.getContents();
			}
			
			//String tmpContents2 = "";
			for (FileData addImg : contentsAddImgs) {
				try{
					addImg.setFilePhysicalPath(imageUrl+ addImg.getFilePath()+ File.separatorChar+ addImg.getFileName());
					File srcFile = new File(addImg.getFilePhysicalPath());
					FileUtils.copyFile(srcFile, new File(newImageUrl+"/mailimage/",addImg.getFileName()));
					tmpContents = tmpContents.replaceAll(addImg.getFileId(), addImg.getFileName());
				}
				catch(Exception e) {
				}
				
			}
			contents = tmpContents+filename;

			boardBatchService.sendUserMail( boardItemReaderList, contents,user,boardItem.getWordName());
			
			 
		}
		
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listBoardItemBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)
	 */
	public SearchResult<BoardItem> listBoardItemBySearchCondition(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition(searchCondition);

			if(boardItemList != null && boardItemList.size() > 0) {
				for(BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					boardItem.setTagList(tagList);
					//boardItem.setUser(this.userService.read(boardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;


	}
	
	public SearchResult<BoardItem> listBoardItemBySearchCondition3(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countBySearchCondition3(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition3(searchCondition);

			if(boardItemList != null && boardItemList.size() > 0) {
				for(BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					boardItem.setTagList(tagList);
					//boardItem.setUser(this.userService.read(boardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;


	}
	
	public SearchResult<BoardItem> deleteListBoardItemBySearchCondition(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.deleteCountBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.deleteListBySearchCondition(searchCondition);

			if(boardItemList != null && boardItemList.size() > 0) {
				for(BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					boardItem.setTagList(tagList);
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;


	}
	public SearchResult<BoardItem> listBoardItemBySearchCondition2(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countBySearchCondition2(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listBySearchCondition2(searchCondition);
			/*
			if(boardItemList != null && boardItemList.size() > 0) {
				for(BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					boardItem.setTagList(tagList);
					//boardItem.setUser(this.userService.read(boardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}
			*/
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
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, BoardItem.ATTECHED_FILE);
		boardItem.setEcmFileDataList(ecmFileDataList);

		//CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, BoardItem.EDITOR_FILE);
		boardItem.setEditorFileDataList(editorFileDataList);

		//태그 목록을 조회하고 게시물에 넣는다
		List<Tag> tagList = this.tagService.listTagByItemId(itemId,  TagConstants.ITEM_TYPE_BBS);
		boardItem.setTagList(tagList);

		//태그 문자열을 만든다.
		if(tagList != null && tagList.size() > 0) {
			StringBuffer tagBuffer = new StringBuffer();

			for(Tag tag : tagList) {
				tagBuffer.append(tag.getTagName());
				tagBuffer.append(", ");
			}

			boardItem.setTag(StringUtils.substringBeforeLast(tagBuffer.toString(), ","));
		}

		
		
		List<BoardItemReader> boardItemReaderList = this.boardItemReaderService.listBoardItemReader(itemId);//독서자를 가져온다.
		boardItem.setBoardItemReaderList(boardItemReaderList);
		
		
		return boardItem;
	}
	
	public List<String> workplaceList(String itemId){
		List<String> workplaceList = boardItemDao.workplaceList(itemId);
		return workplaceList;
	}

	/**
	 * 태그들을 저장한다.
	 * 태그는 화면에서 문자열 형태로 넘어온다. (ex: Tag-A, Tag-B, Tag-C)
	 *
	 * @param boardItem the board item
	 */
	private void saveTags(BoardItem boardItem) {
		//태그저장
		if(StringUtils.isEmpty(boardItem.getTag())) {
			return;
		} else {
			Tag tag = new Tag();

			tag.setTagName(boardItem.getTag());
			tag.setTagItemId(boardItem.getItemId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_BBS);
			tag.setTagItemName(boardItem.getTitle());
			tag.setTagItemContents(boardItem.getContents());
			tag.setTagItemUrl("/lightpack/board/boardItem/readBoardItemView.do?&docPopup=true&boardId=" + boardItem.getBoardId()+ "&itemId="+boardItem.getItemId());
			tag.setRegisterId(boardItem.getRegisterId());
			tag.setRegisterName(boardItem.getRegisterName());
			tag.setPortalId(boardItem.getPortalId());

			this.tagService.create(tag);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#createBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
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

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();
		
		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		boardItem.setItemId(id);
		boardItem.setItemGroupId(id);
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(boardItem.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기

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

		int attachCnt = 0;
		//게시물의 첨부파일 갯수를 업데이트 한다.
		if(boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
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
				fileLink.setItemTypeCode(BoardItem.ITEM_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(boardItem.getItemId());
				fileLink.setItemTypeCode(BoardItem.ITEM_TYPE);
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

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if(boardItem.getEditorFileLinkList() != null && boardItem.getEditorFileLinkList().size()>0) {
			if(boardItem.getEditorFileLinkList().get(0) != null){
				boardItem.setImageFileId(boardItem.getEditorFileLinkList().get(0).getFileId());
			}
		}
		
		//게시물을 생성한다.
		String boardItemId = this.boardItemDao.create(boardItem);

		//게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if(boardItem.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(boardItem.getEditorFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}

		//태그저장
		this.saveTags(boardItem);

		//Activity Stream 게시글 생성
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_POST, boardItemId, boardItem.getTitle());
		
		//독서자 지정
		readerSetting(boardItem.getItemId(), boardItem);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getReaderMailFlag():"+boardItem.getReaderMailFlag());

		return boardItemId;
	
		

	}
	
	public void saveBoardItemWorkplace(BoardItem boardItem){
		boardItemDao.deleteBoardItemWorkplace(boardItem.getItemId());
		String workplaces[] = boardItem.getWorkplaces().split(",");
		for(int i=0;i<workplaces.length;i++){
			boardItem.setWorkplace(workplaces[i]);
			boardItemDao.saveBoardItemWorkplace(boardItem);
		}
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

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#createReplyBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	@SuppressWarnings("unchecked")
	public String createReplyBoardItem(BoardItem boardItem, User user) {
		boardItem.setHitCount(0);
		boardItem.setRecommendCount(0);
		boardItem.setReplyCount(0);
		boardItem.setLinereplyCount(0);
		boardItem.setAttachFileCount(0);
		boardItem.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다.
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
		if(boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		}
		
		//게시글들의 스탭을 업데이트 한다.(같은 Level에 존재하는 글에 대한 순서이다.)
		this.boardItemDao.updateStep(boardItem);

		// 답글 게시글을 생성한다.
		String boardItemId = this.boardItemDao.create(boardItem);

		//첨부파일 업데이트
		if(boardItem.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}

		//이미지 파일  업데이트
		if(boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(boardItem.getEditorFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}

		//태그저장
		this.saveTags(boardItem);

		//Activity Stream 게시글 생성
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_POST, boardItemId, boardItem.getTitle());

		//답글 갯수를 업데이트 한다.
		this.boardItemDao.updateReplyCount(boardItem.getItemParentId());


		
		List<BoardItemReader> boardItemReaderList = this.boardItemReaderService.listBoardItemReader(boardItem.getItemParentId());//부모글의 독서자를 가져온다.
		for (int i = 0; i < boardItemReaderList.size(); i++) {
			BoardItemReader boardItemReader = (BoardItemReader) boardItemReaderList.get(i);
			boardItemReader.setBoardItemId(boardItemId);//답글의 독서자로 셋팅.
			this.boardItemReaderService.createBoardItemReader(boardItemReader);
		}
		
		return boardItemId;
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void updateBoardItem(BoardItem boardItem, User user) {
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(boardItem.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기

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
		
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(boardItem.getItemId(), BoardItem.ATTECHED_FILE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.ATTECHED_FILE);
		//게시물의 첨부파일 갯수를 업데이트 한다.
		if(boardItem.getFileLinkList() != null) {
			
			int fileCount = 0;
			for(FileLink fileLink :boardItem.getFileLinkList()){
				if(!fileLink.getFlag().equals("del")){
					++fileCount;
					attachCnt++;
				}
			}
			
			boardItem.setAttachFileCount(fileCount);
			
			this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				boardItem.setAttachFileCount(fileCount);

			}else{
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
				fileLink.setItemTypeCode(BoardItem.ITEM_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(boardItem.getItemId());
				fileLink.setItemTypeCode(BoardItem.ITEM_TYPE);
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
		

		//이미지 파일  업데이트
		if(boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(boardItem.getEditorFileLinkList(), boardItem.getItemId(), BoardItem.ITEM_TYPE, user);
		}

		List<FileData> fileDateList = this.fileService.getItemFile(boardItem.getItemId(), BoardItem.EDITOR_FILE);

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if(fileDateList != null && fileDateList.size() > 0) {
			boardItem.setImageFileId(fileDateList.get(0).getFileId() );
		}
		
		this.boardItemDao.update(boardItem);
		

		
		BoardItem masterInfo = this.boardItemDao.get(boardItem.getItemId());

		//답글이 존재하면 답변글의 날짜도 변경한다.
		if(masterInfo.getReplyCount() > 0) {
			//본인 포함 하위 게시글을 가져온다.
			List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(boardItem.getItemId());

			for(BoardItem looping : boardItemList) {
				looping.setStartDate(boardItem.getStartDate());
				looping.setEndDate(boardItem.getEndDate());
				looping.setItemDisplay(boardItem.getItemDisplay());//부모글을 공지로 수정하면 함께 공지로 해준다.
				//if("100000793116".equals(boardItem.getCompanyNoticeBoardId())){
				//looping.setCompanyNoticeBoardId("100000793116");//부모글을 전사공지로 수정하면 함께 전사공지로 해준다.
				//}
				this.boardItemDao.update(looping);
				
				if(StringUtil.isEmpty(boardItem.getItemParentId())){//최상위 글이면
					readerSetting(looping.getItemId(), boardItem);//자식 독서자를 최상위 글과 동일하게 설정해줌. //독서자 메일 발송여부는 각각이므로 제외.
				}//그외는 이미 답변글의 그대로 유지.
			}
		}else{
			if(StringUtil.isEmpty(boardItem.getItemParentId())){//최상위 글이면
				readerSetting(boardItem.getItemId(), boardItem);
			}
		}
		

		//태그저장
		this.saveTags(boardItem);


		//Activity Stream 게시글 수정
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_EDIT, boardItem.getItemId(), boardItem.getTitle());
		

	}

	public void readerSetting(String itemId, BoardItem boardItem){
		this.boardItemReaderService.deleteReader(itemId);//기존 독서자 지운다.
		//독서자 지정
		if (boardItem.getReaderList() != null) {
			for (String readerId : boardItem.getReaderList()) {

					String reader = readerId.substring(readerId.lastIndexOf(":") + 1,
							readerId.length());
					BoardItemReader boardItemReader  = new BoardItemReader();
					boardItemReader.setBoardItemId(itemId);
					boardItemReader.setReaderId(reader);
					boardItemReader.setReaderType(readerId.substring(0, 1));//G 또는 U 또는 C 로 들어간다.
					this.boardItemReaderService.createBoardItemReader(boardItemReader);

			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#adminDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void adminDeleteBoardItem(BoardItem boardItem) {

		BoardItem persisted = this.boardItemDao.get(boardItem.getItemId());


		if(persisted.getReplyCount() == 0) {
			this.updateItemDeleteField(boardItem.getItemId(), BoardItem.STATUS_DELETE_WAITING);
			
			//태그를 삭제한다.
			this.tagService.delete(boardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

			//Activity Stream 게시물 삭제 등록
			this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_DELETE, boardItem.getItemId(), boardItem.getTitle());

		} else {
			//본인 포함 하위 게시글을 가져온다.
			List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(boardItem.getItemId());

			for(BoardItem loopingItem : boardItemList) {
				this.updateItemDeleteField(loopingItem.getItemId(), BoardItem.STATUS_DELETE_WAITING);
				
				//태그를 삭제한다.
				this.tagService.delete(boardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

				//Activity Stream 게시물 삭제 등록
				this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_DELETE, loopingItem.getItemId(), loopingItem.getTitle());
			}
		}
		
		

		//부모글의 답글 갯수를 업데이트 한다.
		if(boardItem.getItemParentId() != null){
			this.boardItemDao.updateReplyCount(boardItem.getItemParentId());
		}
	}




	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#userDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void userDeleteBoardItem(BoardItem boardItem) {
		BoardItem persisted = this.boardItemDao.get(boardItem.getItemId());

		if(persisted.getReplyCount() == 0) {
			this.adminDeleteBoardItem(boardItem);

		} else {

			this.boardItemDao.logicalDelete(boardItem);
			//태그를 삭제한다.
			this.tagService.delete(boardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(BoardRecommend boardRecommend) {
		//이미 추천을 했다면
		if(!this.boardRecommendDao.exists(boardRecommend)) {
			this.boardItemDao.updateRecommendCount(boardRecommend.getItemId());
			this.boardRecommendDao.create(boardRecommend);
		}

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
			if(boardItem == null || boardItem.getItemDelete() == BoardItem.STATUS_DELETE_WAITING) {
				continue;
			}

			this.adminDeleteBoardItem(boardItem);
		}
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#exsitRecommend(com.lgcns.ikep4.lightpack.board.model.BoardRecommend)
	 */
	public Boolean exsitRecommend(BoardRecommend boardRecommend) {
		return this.boardRecommendDao.exists(boardRecommend);

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
	public List<BoardItem> listRecentBoardItem(BoardItemSearchCondition searchCondition) {
					
		Board board = this.boardDao.get(searchCondition.getBoardId());

		List<BoardItem> boardItemList = null;
		if("100000793108".equals(board.getBoardId())||"100000793116".equals(board.getBoardId())){//공문공지이거나 일반공지 이면
			boardItemList = this.boardItemDao.listRecentBoardItem2(searchCondition);		
		}else{
			boardItemList = this.boardItemDao.listRecentBoardItem(searchCondition);		
		}
		
		//게시글의 게시판 정보를 넣는다.
		for(BoardItem boardItem : boardItemList) {
	
			boardItem.setAnonymous(board == null ? 0 : board.getAnonymous());

			if(boardItem.getAnonymous() == 1) {
				
				User tmpUser = new User();				
				tmpUser.setUserName(boardItem.getUpdaterName());		
				
				boardItem.setRegisterId(null);
				boardItem.setRegisterName(null);
				boardItem.setUpdaterId(null);
				boardItem.setUpdaterName(null);
				boardItem.setUser(tmpUser);
			}
		}

		return boardItemList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#updateItemDelelteField(java.lang.String, java.lang.Integer)
	 */
	public void updateItemDeleteField(String itemId, Integer status) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("itemId", itemId);
		parameter.put("status", status);

		BoardItem boardItem = this.readBoardItemMasterInfo(itemId);
		this.boardItemDao.updateItemDeleteField(parameter);

		if(status == BoardItem.STATUS_DELETE_WAITING) {
			
			//Activity Stream 게시물 삭제 등록
			this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_DELETE, boardItem.getItemId(), boardItem.getTitle());

			//부모글의 답글 갯수를 업데이트 한다.
			this.boardItemDao.updateReplyCount(boardItem.getItemParentId());
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
			this.updateItemDeleteField(loopingItem.getItemId(), BoardItem.STATUS_DELETE_WAITING);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#deleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)
	 */
	public void deleteBoardItemThread(BoardItem boardItem) {
		//본인 포함 하위 게시글을 가져온다.
		List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(boardItem.getItemId());

		for(BoardItem loopingItem : boardItemList) {
			//태그를 삭제한다.
			this.tagService.delete(loopingItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

			//전체 파일 삭제
			this.fileService.removeItemFile(loopingItem.getItemId());

			//댓글 쓰레드를 삭제한다.
			this.boardLinereplyDao.physicalDeleteThreadByItemThread(loopingItem.getItemId());

			//게시글 추천 정보를 삭제한다.
			this.boardRecommendDao.removeByItemId(loopingItem.getItemId());

			//게시글 조회 정보를 삭제한다.
			this.boardReferenceDao.removeByItemId(loopingItem.getItemId());

			//게시글을 삭제한다.
			this.boardItemDao.physicalDelete(loopingItem.getItemId());
		}

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardItemService#listReplayItemThreadByItemId(java.lang.String)
	 */
	public List<BoardItem> listReplayItemThreadByItemId(String itemGroupId) {
		//게시물과 연관되는 답글 Thread를 가져와 게시물에 넣는다
		return this.boardItemDao.listReplayItemThreadByItemId(itemGroupId);
	}
	
	

	public void updateMailCount(String itemId) {
		
		this.boardItemDao.updateMailCount(itemId);
		
	}

	public void updateMblogCount(String itemId) {
		this.boardItemDao.updateMblogCount(itemId);
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

	public List<BoardItem> listRecentBoardItemPortlet(String boardId,  Integer count) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", boardId);
		parameter.put("count", count == null ? 10 : count);

		return this.boardItemDao.listRecentBoardItemPortlet(parameter);
	}
	
	/**
	 * 임시 저장 게시물 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<BoardItem> listTempSaveItemBySearchCondition(BoardItemSearchCondition searchCondition) {
		Integer count = this.boardItemDao.countTempSaveBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.listTempSaveBySearchCondition(searchCondition);

			if(boardItemList != null && boardItemList.size() > 0) {
				for(BoardItem boardItem : boardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(boardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					boardItem.setTagList(tagList);
					
					boardItem.setBoard(this.boardDao.get(boardItem.getBoardId()));
				}
			}

			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;
	}
	
	public int getMailWaitYn(String itemId){
		int mailWaitYn = this.boardItemDao.getMailWaitYn(itemId);
		return mailWaitYn;
	}
	
	public int getBoardItemCount(BoardItem boardItem){
		int itemCnt = this.boardItemDao.getBoardItemCount(boardItem);
		return itemCnt;
	}
	
	public int getHappyBoardItemCount(BoardItem boardItem){
		int itemCnt = this.boardItemDao.getHappyBoardItemCount(boardItem);
		return itemCnt;
	}
	
	public void updateMailWaitYn(String itemId) {
		
		this.boardItemDao.updateMailWaitYn(itemId);
		
	}
	
	public int getMailWaitTime(){
		int mailWaitTime = this.boardItemDao.getMailWaitTime();
		return mailWaitTime;
	}
	
	public int getMailWaitCount(){
		int mailWaitCount = this.boardItemDao.getMailWaitCount();
		return mailWaitCount;
	}
	
	/**
	 * 이전 게시글의 정보를 조회한다.
	 * 
	 * @param String boardId , String itemId
	 */
	public BoardItem readPrevBoardItem(String boardId, String itemId) {
		//게시물을 가져온다.
		String readflag = "prev";
		BoardItem boardItem = this.boardItemDao.getPrevItem(boardId, itemId, readflag);

		return boardItem;
	}
	
	/**
	 * 다음 게시글의 정보를 조회한다.
	 * 
	 * @param String boardId , String itemId
	 */
	public BoardItem readNextBoardItem(String boardId, String itemId) {
		//게시물을 가져온다.
		String readflag = "next";
		BoardItem boardItem = this.boardItemDao.getNextItem(boardId, itemId, readflag);

		return boardItem;
	}
	public SearchResult<BoardItem> getBoardMenuList(BoardItemSearchCondition searchCondition) {
		// TODO Auto-generated method stub
		Integer count = this.boardItemDao.countBoardMenuList(searchCondition);
		
		searchCondition.terminateSearchCondition(count);

		SearchResult<BoardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BoardItem>(searchCondition);

		} else {

			List<BoardItem> boardItemList = this.boardItemDao.getBoardMenuList(searchCondition);
			List<BoardItem> presentBoardItemList = this.boardItemDao.getPresentBoardMenuList(searchCondition.getUserId());
			
			for(BoardItem boardItem : boardItemList) {
				for(BoardItem boardItem1 : presentBoardItemList) {
					if(boardItem.getBoardId().equals(boardItem1.getBoardId())) {
						boardItem.setTempSave("1");
					}
				}
			}
			
			searchResult = new SearchResult<BoardItem>(boardItemList, searchCondition);
		}

		return searchResult;
	}
	
	public void updateBoardMenuList(Map<String, Object> param) {
		
		//지우고,
		this.boardItemDao.deleteBoardMenuList((String)param.get("userId"));
		
		//집어넣는다.
		for(int i=0; i<((List<String>)param.get("itemIds")).size(); i++){
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("userId", param.get("userId"));
			param1.put("itemId", ((List<String>)param.get("itemIds")).get(i));
			this.boardItemDao.insertBoardMenuList(param1);
		}
	}
}
