package com.lgcns.ikep4.collpack.kms.qna.service.impl;

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
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.qna.dao.QnaItemDao;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaConfig;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaItem;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaRecommendPK;
import com.lgcns.ikep4.collpack.kms.qna.search.QnaItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.qna.service.QnaItemService;
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
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


@Service("kmsQnaItemService")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class QnaItemServiceImpl implements QnaItemService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private QnaItemDao qnaItemDao;

//	@Autowired
//	private WorkspaceService workspaceService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private UserDao userDao;

	/*
	 * 공지사항 게시물 조회리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #
	 * listQnaItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .qna.search.QnaItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<QnaItem> listQnaItemBySearchCondition(
			QnaItemSearchCondition qnaItemSearchCondition) {
		Integer count = this.qnaItemDao.countBySearchCondition(qnaItemSearchCondition);

		qnaItemSearchCondition.terminateSearchCondition(count);

		SearchResult<QnaItem> searchResult = null;
		if (qnaItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<QnaItem>(qnaItemSearchCondition);
		} else {
			List<QnaItem> qnaItemList = this.qnaItemDao
					.listBySearchCondition(qnaItemSearchCondition);

			List<FileData> fileDataList = null;

			for (QnaItem qnaItem : qnaItemList) {
				if (qnaItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(qnaItem.getItemId(), QnaItem.ITEM_FILE_TYPE);
					qnaItem.setFileDataList(fileDataList);
				}

				List<Tag> tagList = tagService.listTagByItemId(qnaItem.getItemId(), QnaItem.ITEM_TYPE_CODE);
				qnaItem.setTagList(tagList);
			}

			searchResult = new SearchResult<QnaItem>(qnaItemList, qnaItemSearchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<QnaItem> listQnaSubItem(QnaItemSearchCondition qnaItemSearchCondition){
		Integer count = this.qnaItemDao.countQnaSubItem(qnaItemSearchCondition);

		qnaItemSearchCondition.terminateSearchCondition(count);

		SearchResult<QnaItem> searchResult = null;
		if (qnaItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<QnaItem>(qnaItemSearchCondition);
		} else {
			List<QnaItem> qnaItemList = this.qnaItemDao
					.listQnaSubItem(qnaItemSearchCondition);

			List<FileData> fileDataList = null;

			for (QnaItem qnaItem : qnaItemList) {
				if (qnaItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(qnaItem.getItemId(), QnaItem.ITEM_FILE_TYPE);
					qnaItem.setFileDataList(fileDataList);
				}

				List<Tag> tagList = tagService.listTagByItemId(qnaItem.getItemId(), QnaItem.ITEM_TYPE_CODE);
				qnaItem.setTagList(tagList);
			}

			searchResult = new SearchResult<QnaItem>(qnaItemList, qnaItemSearchCondition);
		}

		return searchResult;
	}
	
	public Boolean exsitRecommend(QnaRecommendPK boardRecommend) {
		return this.qnaItemDao.existsRecommend(boardRecommend);

	}
	
	public void updateRecommendCount(QnaRecommendPK boardRecommend) {
		//이미 추천을 했다면
		if(!this.qnaItemDao.existsRecommend(boardRecommend)) {
			this.qnaItemDao.updateRecommendCount(boardRecommend.getItemId());
			this.qnaItemDao.createRecommend(boardRecommend);
		}

	}

	/*
	 * 공지사항 게시물 읽기
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #readQnaItem(java.lang.String, java.lang.String)
	 */
	@Transactional
//	public QnaItem readQnaItem(String userId, String itemId, String workspaceId) {
	public QnaItem readQnaItem(String userId, String itemId) {

		QnaItem qnaItem = new QnaItem();
//		qnaItem = qnaItemDao.getQna(itemId, workspaceId);
		qnaItem = qnaItemDao.getQna(itemId);
		if (qnaItem != null) {
			updateHitCount(userId, itemId);

			// 첨부파일 정보
			if (qnaItem.getAttachFileCount() > 0) {
				List<FileData> fileDataList = this.fileService.getItemFile(itemId, QnaItem.ATTECHED_FILE);
				qnaItem.setFileDataList(fileDataList);
			}

			List<Tag> tagList = tagService.listTagByItemId(qnaItem.getItemId(), QnaItem.ITEM_TYPE_CODE);
			qnaItem.setTagList(tagList);
		}

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, QnaItem.EDITOR_FILE);
		qnaItem.setEditorFileDataList(editorFileDataList);
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, QnaItem.ITEM_FILE_TYPE);
		qnaItem.setEcmFileDataList(ecmFileDataList);

		return qnaItem;
	}
	
	public String createReplyBoardItem(QnaItem boardItem, User user) {

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
		int attachCnt = 0;
		// 첨부파일 업데이트
		if (boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
			this.fileService.saveFileLink(boardItem.getFileLinkList(), boardItem.getItemId(),
					QnaItem.ITEM_FILE_TYPE, user);
			attachCnt = boardItem.getFileLinkList().size();
		}

		// Tag 등록
		if (boardItem.getTag() != null) {
			createTag(boardItem, user);
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
				fileLink.setItemTypeCode(boardItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(boardItem.getItemId());
				fileLink.setItemTypeCode(boardItem.ITEM_FILE_TYPE);
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
		/*// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (boardItem.getFileLinkList() != null) {
			boardItem.setAttachFileCount(boardItem.getFileLinkList().size());
		}*/

		// 게시글들의 스탭을 업데이트 한다.(같은 Level에 존재하는 글에 대한 순서이다.)
		this.qnaItemDao.updateStep(boardItem);

		// 답글 게시글을 생성한다.
		String boardItemId = this.qnaItemDao.create(boardItem);

		/*// 첨부파일 업데이트
		if (boardItem.getFileLinkList() != null) {
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(boardItem.getFileLinkList(),
					boardItem.getItemId(), BoardItem.ITEM_FILE_TYPE, user);
		}*/

		// 이미지 파일 업데이트
		if (boardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					boardItem.getEditorFileLinkList(), boardItem.getItemId(),
					BoardItem.ITEM_TYPE, user);
		}


		// 답글 갯수를 업데이트 한다.
		this.qnaItemDao.updateReplyCount(boardItem.getItemParentId());


		return boardItemId;
	}
	
	
	public QnaConfig readQnaconfig(){
		QnaConfig qnaConfig = new QnaConfig();
		qnaConfig = qnaItemDao.readQnaconfig();
		return qnaConfig;
	}
	
	public void createQnaConfig(QnaConfig qnaConfig, List<MultipartFile> fileList, String editorAttach, User user){
		//기존 설정 정보 조회
		QnaConfig oldQnaConfig = qnaItemDao.readQnaconfig();
		
		if(oldQnaConfig == null) {
			String dreamTogetherId = idgenService.getNextId();
			qnaConfig.setPortletConfigId(dreamTogetherId);
		} else {
			qnaConfig.setPortletConfigId(oldQnaConfig.getPortletConfigId());
		}
		
		if(oldQnaConfig != null && !StringUtil.isEmpty(oldQnaConfig.getImageFileId())) {
			fileService.removeFile(oldQnaConfig.getImageFileId());
		}
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, qnaConfig.getPortletConfigId(), "KN", user);
		}
		
		qnaConfig.setImageFileId(fileId);
		qnaConfig.setRegisterId(user.getUserId());
		qnaConfig.setRegisterName(user.getUserName());
		qnaConfig.setUpdaterId(user.getUserId());
		qnaConfig.setUpdaterName(user.getUserName());
		
		if(oldQnaConfig == null) {
			//없으면 인서트
			qnaItemDao.createQnaConfig(qnaConfig);
		} else {
			//있으면 업데이트
			qnaItemDao.updateQnaConfig(qnaConfig);
		}
	}

	/*
	 * 공지사항 게시물 등록
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #
	 * createQnaItem(com.lgcns.ikep4.collpack.collaboration.board.qna.model
	 * .QnaItem)
	 */
	public String createQnaItem(QnaItem qnaItem, User user) {
		String id = idgenService.getNextId();
		qnaItem.setItemId(id);
		qnaItem.setStep(0);  
		qnaItem.setIndentation(0);
		qnaItem.setItemId(id);
		qnaItem.setItemGroupId(id);
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (qnaItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(qnaItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						qnaItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					qnaItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		// 첨부파일 업데이트
		if (qnaItem.getFileLinkList() != null) {
			qnaItem.setAttachFileCount(qnaItem.getFileLinkList().size());
			this.fileService.saveFileLink(qnaItem.getFileLinkList(), qnaItem.getItemId(),
					QnaItem.ITEM_FILE_TYPE, user);
			attachCnt = qnaItem.getFileLinkList().size();
		}

		// Tag 등록
		if (qnaItem.getTag() != null) {
			createTag(qnaItem, user);
		}
		String [] ecmFileIds = StringUtils.split(qnaItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(qnaItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(qnaItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(qnaItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(qnaItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(qnaItem.getEcmFileId() != null) {
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
				fileLink.setItemId(qnaItem.getItemId());
				fileLink.setItemTypeCode(qnaItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(qnaItem.getItemId());
				fileLink.setItemTypeCode(qnaItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			qnaItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(qnaItem.getEcmFileId() != null) {
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
		String itemId = qnaItemDao.create(qnaItem);

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (qnaItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(qnaItem.getEditorFileLinkList(), qnaItem.getItemId(),
					QnaItem.ITEM_FILE_TYPE, user);
		}

//		if (!StringUtil.isEmpty(itemId)) {
//			// 링크 INSERT
//			qnaItem.setIsOwner("1");
//			qnaItemDao.createLinkQna(qnaItem);
//		}

//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(qnaItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, qnaItem.getItemId(), qnaItem.getTitle(),
//				"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());

		/*List<String> targetGroupIds = qnaItem.getTargetGroupList();
		if( targetGroupIds != null ){
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetGroupIds.get(i));
				qnaItem.setIsGroup("Y");
				this.qnaItemDao.insertTargetGroup(qnaItem);
			}

		}
		
		List<String> targetUserIds = qnaItem.getTargetList();
		
		if( targetUserIds != null ){
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetUserIds.get(i));
				qnaItem.setIsGroup("N");
				this.qnaItemDao.insertTargetGroup(qnaItem);
			}
		}*/
		
		
		/*String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		List<String> targetGroupIds = qnaItem.getTargetGroupList();
		if( targetGroupIds != null){
			url = baseUrl+"/collpack/kms/board/qnaItem/directReadItemView.do?itemId="+qnaItem.getItemId()+"&isKnowhow=&suserId=";
			String title = qnaItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = "";
			contents = "[등록정보 제목: "+qnaItem.getTitle()+"]<br>[작성자: "+qnaItem.getRegisterName()+"]";
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetGroupIds.get(i));
				qnaItem.setIsGroup("Y");
				this.qnaItemDao.insertTargetGroup(qnaItem);
				List<QnaItem> targetUserList = this.qnaItemDao.selectTargetUserList(targetGroupIds.get(i));
				
				String[] recipientId = new String[targetUserList.size()];
				for(int ti = 0; ti < targetUserList.size(); ti++){
					recipientId[ti] = targetUserList.get(ti).getTargetUserId();
				}
				//boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
				
			}

		}
		
		List<String> targetUserIds = qnaItem.getTargetList();
		
		if( targetUserIds != null){
			url = baseUrl+"/collpack/kms/board/qnaItem/directReadItemView.do?itemId="+qnaItem.getItemId()+"&suserId=";
			String title = qnaItem.getRegisterName()+"님이 작성한 지식이 공유되었습니다";
			String contents = "";
			contents = "[등록정보 제목: "+qnaItem.getTitle()+"]<br>[작성자: "+qnaItem.getRegisterName()+"]";
			String[] recipientId = new String[targetUserIds.size()];
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetUserIds.get(i));
				qnaItem.setIsGroup("N");
				this.qnaItemDao.insertTargetGroup(qnaItem);
				recipientId[i] = targetUserIds.get(i);
			}
			//boardBatchItemService.messageSend("",recipientId,contents.toString(),url,title);
		}
		*/
		qnaItem.setItemId(itemId);
		sendQnaMail(user,qnaItem);
		
		return itemId;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendQnaMail(User user, QnaItem qnaItem ) {
    	List<User> recipientList = new ArrayList<User>();
    	
    	List<String> targetGroupIds = qnaItem.getTargetGroupList();
		if( targetGroupIds != null){
			for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetGroupIds.get(i));
				qnaItem.setIsGroup("Y");
				this.qnaItemDao.insertTargetGroup(qnaItem);
				List<QnaItem> targetUserList = this.qnaItemDao.selectTargetUserList(targetGroupIds.get(i));
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
					
					mail.setTitle("무림지식인(Q&A)가 등록되었습니다");
					
					Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
			        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
					String url = "";
					url = baseUrl+"/collpack/kms/qna/readQnaItemView.do?itemId="+qnaItem.getItemId();
					
					dataMap.put("regdate", getToday("yyyy-MM-dd"));
					dataMap.put("register", qnaItem.getRegisterName());
					dataMap.put("title", qnaItem.getTitle());
					dataMap.put("url", url);
					
					User sender = user;

					mailSendService.sendMail(mail, dataMap, sender);
				}
			}	
		}
		
		List<String> targetUserIds = qnaItem.getTargetList();
		
		if( targetUserIds != null){
			String[] recipientId = new String[targetUserIds.size()];
			for( int i = 0 ; i < targetUserIds.size() ; i++ ){
				qnaItem.setTargetGroupId(targetUserIds.get(i));
				qnaItem.setIsGroup("N");
				this.qnaItemDao.insertTargetGroup(qnaItem);
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
				
				mail.setTitle("무림지식인(Q&A)가 등록되었습니다");
				
				Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
				String url = "";
				url = baseUrl+"/collpack/kms/qna/readQnaItemView.do?itemId="+qnaItem.getItemId();
				
				dataMap.put("regdate", getToday("yyyy-MM-dd"));
				dataMap.put("register", qnaItem.getRegisterName());
				dataMap.put("title", qnaItem.getTitle());
				dataMap.put("url", url);
				
				User sender = user;

				mailSendService.sendMail(mail, dataMap, sender);
			}
		}
		
		/*recipientList = partnerQualityClaimSellMoreDao.getUserInfoList3(readPartnerQualityClaimSellMore.getLinereplyId());
		
		PartnerQualitySub tempPartnerQualitySub = this.partnerQualityClaimSellMoreDao.get( readPartnerQualityClaimSellMore.getLinereplyId() );
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("qnaMailTemplate.vm");
		

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		
		mail.setTitle("지식광장 Q&A가 등록되었습니다");
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		url = baseUrl+"/collpack/kms/board/qnaItem/directReadItemView.do?itemId="+qnaItem.getItemId()+"&suserId=";
		
		dataMap.put("regdate", tempPartnerQualitySub.getCounselDate());
		dataMap.put("register", tempPartnerQualitySub.getCounselor());
		dataMap.put("title", tempPartnerQualitySub.getCounselTitle());
		dataMap.put("url", url);
		
		User sender = user;

		mailSendService.sendMail(mail, dataMap, sender);*/
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
	/*
	 * 공유 공지사항 게시물 삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #
	 * adminDeleteQnaItem(com.lgcns.ikep4.collpack.collaboration.board.qna.
	 * model.QnaItem)
	 */
	public void adminDeleteQnaItem(QnaItem qnaItem) {
//		if (!StringUtil.isEmpty(qnaItem.getIsOwner()) && qnaItem.getIsOwner().equals("1")) {
			// Link삭제
//			qnaItemDao.removeQnaLink(qnaItem.getItemId(), null);
//			qnaItemDao.removeQnaLink(qnaItem.getItemId());
			// Reference삭제
			qnaItemDao.removeQnaReference(qnaItem.getItemId());
			// Item삭제
			qnaItemDao.removeQnaItem(qnaItem.getItemId());
			// 전체 파일 삭제
			this.fileService.removeItemFile(qnaItem.getItemId());
			// Tag삭제
			// Tag 삭제
			tagService.delete(qnaItem.getItemId(), QnaItem.ITEM_TYPE_CODE);
			// ActivityStream
//			Workspace workspace = new Workspace();
//			workspace = workspaceService.getWorkspace(qnaItem.getWorkspaceId());
//			activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//					IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE, qnaItem.getItemId(), qnaItem.getTitle(),
//					"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
//		} else if (!StringUtil.isEmpty(qnaItem.getIsOwner()) && qnaItem.getIsOwner().equals("0")) {
//			// Link삭제
//			qnaItemDao.removeQnaLink(qnaItem.getItemId(), qnaItem.getWorkspaceId());
//			qnaItemDao.removeQnaLink(qnaItem.getItemId());
//		}
	}

	/*
	 * 공지사항 게시물 다중삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #adminMultiDeleteQnaItem(java.util.List, java.lang.String)
	 */
//	public void adminMultiDeleteQnaItem(List<String> itemIds, String workspaceId, String portalId) {
	public void adminMultiDeleteQnaItem(List<String> itemIds, String portalId) {
		QnaItem qnaItem = new QnaItem();

		for (String itemId : itemIds) {
//			qnaItem = this.qnaItemDao.getQna(itemId, workspaceId);
			qnaItem = this.qnaItemDao.getQna(itemId);

			// 이미 삭제가 되었다면 continue
			if (qnaItem == null) {
				continue;
			}

//			if ("0".equals(qnaItem.getIsOwner())) {
//				 하위 운영자에 의한 Link삭제만
//				qnaItemDao.removeQnaLink(qnaItem.getItemId(), workspaceId);
//				qnaItemDao.removeQnaLink(qnaItem.getItemId());
//			} else {
				adminDeleteQnaItem(qnaItem);// is_Owner :1
//			}
		}
	}

	/*
	 * 하위부서 중 개설 WORKSPACE 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #
	 * listChildWorkspaceInfoBySearchCondition(com.lgcns.ikep4.collpack.collaboration
	 * .board.qna.search.QnaItemSearchCondition)
	 */
//	public SearchResult<QnaItem> listChildWorkspaceInfoBySearchCondition(
//			QnaItemSearchCondition searchCondition) {
//
//		Workspace workspace = new Workspace();
//		workspace = getWorkspaceInfo(searchCondition.getPortalId(), searchCondition.getWorkspaceId());
//
//		SearchResult<QnaItem> searchResult = null;
//
//		searchCondition.setTeamId(workspace.getTeamId());
//		Integer count = this.qnaItemDao.countChildWorkspaceBySearchCondition(searchCondition);
//		searchCondition.terminateSearchCondition(count);
//		if (searchCondition.isEmptyRecord()) {
//			searchResult = new SearchResult<QnaItem>(searchCondition);
//
//		} else {
//
//			List<QnaItem> qnaWorkspceList = this.qnaItemDao
//					.listChildWorkspaceBySearchCondition(searchCondition);
//			searchResult = new SearchResult<QnaItem>(qnaWorkspceList, searchCondition);
//		}
//
//		return searchResult;
//	}

	/*
//	 * 공지사항 게시물 하위부서 공유
//	 * @see
//	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
//	 * #createQnaLinkItem(java.lang.String, java.util.List)
//	 */
//	public void createQnaLinkItem(String qnaItemId, List<String> workspaceIds) {
//		// DELETE RESET
//		qnaItemDao.removeQnaShareLink(qnaItemId);
//		if (workspaceIds != null) {
//			for (int j = 0; j < workspaceIds.size(); j++) {
//				String workspaceId = workspaceIds.get(j);
//				// insert
//				QnaItem qnaItem = new QnaItem();
//				qnaItem.setItemId(qnaItemId.trim());
//				qnaItem.setWorkspaceId(workspaceId);
//				qnaItem.setIsOwner("0");
//				qnaItemDao.createLinkQna(qnaItem);
//			}
//		}
//	}

	/*
	 * 공지사항게시물 업데이트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #
	 * updateQnaItem(com.lgcns.ikep4.collpack.collaboration.board.qna.model
	 * .QnaItem)
	 */
	public void updateQnaItem(QnaItem qnaItem, User user) {
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (qnaItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(qnaItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (qnaItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < qnaItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) qnaItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						qnaItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					qnaItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(qnaItem.getItemId(), QnaItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(qnaItem.getItemId(), QnaItem.ITEM_FILE_TYPE);
		// 첨부파일 업데이트
		if (qnaItem.getFileLinkList() != null) {
			this.fileService.saveFileLink(qnaItem.getFileLinkList(), qnaItem.getItemId(),
					QnaItem.ITEM_FILE_TYPE, user);

			int index = 0;
			for (FileLink tempFile : qnaItem.getFileLinkList()) {
				if (tempFile.getFlag().equals("del")) {
					index++;
					attachCnt++;
				}
			}
			if (index != 0) {
				qnaItem.setAttachFileCount(qnaItem.getFileLinkList().size() - index);
			} else {
				//qnaItem.setAttachFileCount(qnaItem.getFileLinkList().size());
				if (tempFileDataList != null) {
					int fileCount = tempFileDataList.size();
					qnaItem.setAttachFileCount(fileCount);

				}else{
					attachCnt = ecmFileDataList.size();
					qnaItem.setAttachFileCount(attachCnt);
				}
			}

		}

		// Tag 삭제
		tagService.delete(qnaItem.getItemId(), QnaItem.ITEM_TYPE_CODE);

		// Tag 등록
		if (qnaItem.getTag() != null) {
			createTag(qnaItem, user);
		}

		// 이미지 파일 업데이트
		if (qnaItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(qnaItem.getEditorFileLinkList(), qnaItem.getItemId(),
					QnaItem.ITEM_FILE_TYPE, user);
		}
		String [] ecmFileIds = StringUtils.split(qnaItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(qnaItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(qnaItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(qnaItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(qnaItem.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId(qnaItem.getItemId());
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
				fileLink.setItemId(qnaItem.getItemId());
				fileLink.setItemTypeCode(QnaItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(qnaItem.getItemId());
				fileLink.setItemTypeCode(QnaItem.ITEM_FILE_TYPE);
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
		
		qnaItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}

		qnaItemDao.update(qnaItem);

//		// ActivityStream
//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(qnaItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT, qnaItem.getItemId(), qnaItem.getTitle(),
//				"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/*
	 * 조회수 UPDATE
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.qna.service.QnaItemService
	 * #updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String userId, String itemId) {
		int refCount = this.qnaItemDao.countQnaReference(userId, itemId);
		if (refCount == 0) {
			this.qnaItemDao.updateQnaHitCount(itemId);
			this.qnaItemDao.createQnaItemReference(userId, itemId);
		}
	}

	/**
	 * 워크스페이스 정보조회
	 * 
	 * @param portalId
	 * @param workspaceId
	 * @return
	 */
//	public Workspace getWorkspaceInfo(String portalId, String workspaceId) {
//		Workspace workspace = new Workspace();
//		workspace.setWorkspaceId(workspaceId);
//		workspace.setPortalId(portalId);
//		workspace = workspaceService.read(workspace);
//
//		return workspace;
//	}

	/**
	 * 공지사항 포틀릿 - 개별 Workspace
	 */
	public List<QnaItem> listQnaItemByPortlet(Map<String, String> map) {
		return this.qnaItemDao.listQnaItemByPortlet(map);
	}

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<QnaItem> listDeleteQnaByWorkspace(String workspaceId) {
	public List<QnaItem> listDeleteQnaByWorkspace() {		
		return this.qnaItemDao.listDeleteQnaByWorkspace();
	}

	/**
	 * 태그작성
	 * 
	 * @param workspace
	 * @param user
	 */
	private void createTag(QnaItem qnaItem, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(qnaItem.getTag())) {
			Tag tag = new Tag();

			// tagService.create(tag);

			tag.setTagName(qnaItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(qnaItem.getItemId()); // 게시물 ID
			tag.setTagItemType(QnaItem.ITEM_TYPE_CODE); // 모듈 타입 정의되어 있음.
			tag.setTagItemSubType(""); // 모듈 서브 타입 -
//			tag.setTagItemSubType(qnaItem.getWorkspaceId()); // 모듈 서브 타입 -
																	// 있을때만 넣기
			tag.setTagItemName(qnaItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(qnaItem.getContents()); // WS 소개글
			tag.setTagItemUrl("/collpack/kms/qna/readQnaItemView.do?itemId="
					+ qnaItem.getItemId()); // WS 팝업창 url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(qnaItem.getPortalId());

			tagService.create(tag);
		}
	}
}
