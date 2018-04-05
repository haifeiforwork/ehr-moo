package com.lgcns.ikep4.collpack.kms.notice.service.impl;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.lgcns.ikep4.collpack.kms.notice.dao.NoticeItemDao;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeConfig;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeItem;
import com.lgcns.ikep4.collpack.kms.notice.search.NoticeItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.notice.service.NoticeItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


@Service("kmsNoticeItemService")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class NoticeItemServiceImpl implements NoticeItemService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private NoticeItemDao noticeItemDao;

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

	/*
	 * 공지사항 게시물 조회리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #
	 * listNoticeItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .notice.search.NoticeItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<NoticeItem> listNoticeItemBySearchCondition(
			NoticeItemSearchCondition noticeItemSearchCondition) {
		Integer count = this.noticeItemDao.countBySearchCondition(noticeItemSearchCondition);

		noticeItemSearchCondition.terminateSearchCondition(count);

		SearchResult<NoticeItem> searchResult = null;
		if (noticeItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<NoticeItem>(noticeItemSearchCondition);
		} else {
			List<NoticeItem> noticeItemList = this.noticeItemDao
					.listBySearchCondition(noticeItemSearchCondition);

			List<FileData> fileDataList = null;

			for (NoticeItem noticeItem : noticeItemList) {
				if (noticeItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(noticeItem.getItemId(), NoticeItem.ITEM_FILE_TYPE);
					noticeItem.setFileDataList(fileDataList);
				}

				List<Tag> tagList = tagService.listTagByItemId(noticeItem.getItemId(), NoticeItem.ITEM_TYPE_CODE);
				noticeItem.setTagList(tagList);
			}

			searchResult = new SearchResult<NoticeItem>(noticeItemList, noticeItemSearchCondition);
		}

		return searchResult;
	}

	/*
	 * 공지사항 게시물 읽기
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #readNoticeItem(java.lang.String, java.lang.String)
	 */
	@Transactional
//	public NoticeItem readNoticeItem(String userId, String itemId, String workspaceId) {
	public NoticeItem readNoticeItem(String userId, String itemId) {

		NoticeItem noticeItem = new NoticeItem();
//		noticeItem = noticeItemDao.getNotice(itemId, workspaceId);
		noticeItem = noticeItemDao.getNotice(itemId);
		if (noticeItem != null) {
			updateHitCount(userId, itemId);

			// 첨부파일 정보
			if (noticeItem.getAttachFileCount() > 0) {
				List<FileData> fileDataList = this.fileService.getItemFile(itemId, NoticeItem.ATTECHED_FILE);
				noticeItem.setFileDataList(fileDataList);
			}

			List<Tag> tagList = tagService.listTagByItemId(noticeItem.getItemId(), NoticeItem.ITEM_TYPE_CODE);
			noticeItem.setTagList(tagList);
		}

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, NoticeItem.EDITOR_FILE);
		noticeItem.setEditorFileDataList(editorFileDataList);
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, NoticeItem.ITEM_FILE_TYPE);
		noticeItem.setEcmFileDataList(ecmFileDataList);

		return noticeItem;
	}
	
	public NoticeConfig readNoticeconfig(){
		NoticeConfig noticeConfig = new NoticeConfig();
		noticeConfig = noticeItemDao.readNoticeconfig();
		return noticeConfig;
	}
	
	public void createNoticeConfig(NoticeConfig noticeConfig, List<MultipartFile> fileList, String editorAttach, User user){
		//기존 설정 정보 조회
		NoticeConfig oldNoticeConfig = noticeItemDao.readNoticeconfig();
		
		if(oldNoticeConfig == null) {
			String dreamTogetherId = idgenService.getNextId();
			noticeConfig.setPortletConfigId(dreamTogetherId);
		} else {
			noticeConfig.setPortletConfigId(oldNoticeConfig.getPortletConfigId());
		}
		
		if(oldNoticeConfig != null && !StringUtil.isEmpty(oldNoticeConfig.getImageFileId())) {
			fileService.removeFile(oldNoticeConfig.getImageFileId());
		}
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, noticeConfig.getPortletConfigId(), "KN", user);
		}
		
		noticeConfig.setImageFileId(fileId);
		noticeConfig.setRegisterId(user.getUserId());
		noticeConfig.setRegisterName(user.getUserName());
		noticeConfig.setUpdaterId(user.getUserId());
		noticeConfig.setUpdaterName(user.getUserName());
		
		if(oldNoticeConfig == null) {
			//없으면 인서트
			noticeItemDao.createNoticeConfig(noticeConfig);
		} else {
			//있으면 업데이트
			noticeItemDao.updateNoticeConfig(noticeConfig);
		}
	}

	/*
	 * 공지사항 게시물 등록
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #
	 * createNoticeItem(com.lgcns.ikep4.collpack.collaboration.board.notice.model
	 * .NoticeItem)
	 */
	public String createNoticeItem(NoticeItem noticeItem, User user) {
		String id = idgenService.getNextId();
		noticeItem.setItemId(id);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (noticeItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(noticeItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						noticeItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					noticeItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		// 첨부파일 업데이트
		if (noticeItem.getFileLinkList() != null) {
			noticeItem.setAttachFileCount(noticeItem.getFileLinkList().size());
			this.fileService.saveFileLink(noticeItem.getFileLinkList(), noticeItem.getItemId(),
					NoticeItem.ITEM_FILE_TYPE, user);
			attachCnt = noticeItem.getFileLinkList().size();
		}

		// Tag 등록
		if (noticeItem.getTag() != null) {
			createTag(noticeItem, user);
		}
		String [] ecmFileIds = StringUtils.split(noticeItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(noticeItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(noticeItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(noticeItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(noticeItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(noticeItem.getEcmFileId() != null) {
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
				fileLink.setItemId(noticeItem.getItemId());
				fileLink.setItemTypeCode(noticeItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(noticeItem.getItemId());
				fileLink.setItemTypeCode(noticeItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			noticeItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(noticeItem.getEcmFileId() != null) {
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
		String itemId = noticeItemDao.create(noticeItem);

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (noticeItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(noticeItem.getEditorFileLinkList(), noticeItem.getItemId(),
					NoticeItem.ITEM_FILE_TYPE, user);
		}

//		if (!StringUtil.isEmpty(itemId)) {
//			// 링크 INSERT
//			noticeItem.setIsOwner("1");
//			noticeItemDao.createLinkNotice(noticeItem);
//		}

//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(noticeItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, noticeItem.getItemId(), noticeItem.getTitle(),
//				"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());

		return itemId;
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
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #
	 * adminDeleteNoticeItem(com.lgcns.ikep4.collpack.collaboration.board.notice.
	 * model.NoticeItem)
	 */
	public void adminDeleteNoticeItem(NoticeItem noticeItem) {
//		if (!StringUtil.isEmpty(noticeItem.getIsOwner()) && noticeItem.getIsOwner().equals("1")) {
			// Link삭제
//			noticeItemDao.removeNoticeLink(noticeItem.getItemId(), null);
//			noticeItemDao.removeNoticeLink(noticeItem.getItemId());
			// Reference삭제
			noticeItemDao.removeNoticeReference(noticeItem.getItemId());
			// Item삭제
			noticeItemDao.removeNoticeItem(noticeItem.getItemId());
			// 전체 파일 삭제
			this.fileService.removeItemFile(noticeItem.getItemId());
			// Tag삭제
			// Tag 삭제
			tagService.delete(noticeItem.getItemId(), NoticeItem.ITEM_TYPE_CODE);
			// ActivityStream
//			Workspace workspace = new Workspace();
//			workspace = workspaceService.getWorkspace(noticeItem.getWorkspaceId());
//			activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//					IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE, noticeItem.getItemId(), noticeItem.getTitle(),
//					"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
//		} else if (!StringUtil.isEmpty(noticeItem.getIsOwner()) && noticeItem.getIsOwner().equals("0")) {
//			// Link삭제
//			noticeItemDao.removeNoticeLink(noticeItem.getItemId(), noticeItem.getWorkspaceId());
//			noticeItemDao.removeNoticeLink(noticeItem.getItemId());
//		}
	}

	/*
	 * 공지사항 게시물 다중삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #adminMultiDeleteNoticeItem(java.util.List, java.lang.String)
	 */
//	public void adminMultiDeleteNoticeItem(List<String> itemIds, String workspaceId, String portalId) {
	public void adminMultiDeleteNoticeItem(List<String> itemIds, String portalId) {
		NoticeItem noticeItem = new NoticeItem();

		for (String itemId : itemIds) {
//			noticeItem = this.noticeItemDao.getNotice(itemId, workspaceId);
			noticeItem = this.noticeItemDao.getNotice(itemId);

			// 이미 삭제가 되었다면 continue
			if (noticeItem == null) {
				continue;
			}

//			if ("0".equals(noticeItem.getIsOwner())) {
//				 하위 운영자에 의한 Link삭제만
//				noticeItemDao.removeNoticeLink(noticeItem.getItemId(), workspaceId);
//				noticeItemDao.removeNoticeLink(noticeItem.getItemId());
//			} else {
				adminDeleteNoticeItem(noticeItem);// is_Owner :1
//			}
		}
	}

	/*
	 * 하위부서 중 개설 WORKSPACE 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #
	 * listChildWorkspaceInfoBySearchCondition(com.lgcns.ikep4.collpack.collaboration
	 * .board.notice.search.NoticeItemSearchCondition)
	 */
//	public SearchResult<NoticeItem> listChildWorkspaceInfoBySearchCondition(
//			NoticeItemSearchCondition searchCondition) {
//
//		Workspace workspace = new Workspace();
//		workspace = getWorkspaceInfo(searchCondition.getPortalId(), searchCondition.getWorkspaceId());
//
//		SearchResult<NoticeItem> searchResult = null;
//
//		searchCondition.setTeamId(workspace.getTeamId());
//		Integer count = this.noticeItemDao.countChildWorkspaceBySearchCondition(searchCondition);
//		searchCondition.terminateSearchCondition(count);
//		if (searchCondition.isEmptyRecord()) {
//			searchResult = new SearchResult<NoticeItem>(searchCondition);
//
//		} else {
//
//			List<NoticeItem> noticeWorkspceList = this.noticeItemDao
//					.listChildWorkspaceBySearchCondition(searchCondition);
//			searchResult = new SearchResult<NoticeItem>(noticeWorkspceList, searchCondition);
//		}
//
//		return searchResult;
//	}

	/*
//	 * 공지사항 게시물 하위부서 공유
//	 * @see
//	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
//	 * #createNoticeLinkItem(java.lang.String, java.util.List)
//	 */
//	public void createNoticeLinkItem(String noticeItemId, List<String> workspaceIds) {
//		// DELETE RESET
//		noticeItemDao.removeNoticeShareLink(noticeItemId);
//		if (workspaceIds != null) {
//			for (int j = 0; j < workspaceIds.size(); j++) {
//				String workspaceId = workspaceIds.get(j);
//				// insert
//				NoticeItem noticeItem = new NoticeItem();
//				noticeItem.setItemId(noticeItemId.trim());
//				noticeItem.setWorkspaceId(workspaceId);
//				noticeItem.setIsOwner("0");
//				noticeItemDao.createLinkNotice(noticeItem);
//			}
//		}
//	}

	/*
	 * 공지사항게시물 업데이트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #
	 * updateNoticeItem(com.lgcns.ikep4.collpack.collaboration.board.notice.model
	 * .NoticeItem)
	 */
	public void updateNoticeItem(NoticeItem noticeItem, User user) {
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (noticeItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(noticeItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (noticeItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < noticeItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) noticeItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						noticeItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					noticeItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(noticeItem.getItemId(), NoticeItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(noticeItem.getItemId(), NoticeItem.ITEM_FILE_TYPE);
		// 첨부파일 업데이트
		if (noticeItem.getFileLinkList() != null) {
			this.fileService.saveFileLink(noticeItem.getFileLinkList(), noticeItem.getItemId(),
					NoticeItem.ITEM_FILE_TYPE, user);

			int index = 0;
			for (FileLink tempFile : noticeItem.getFileLinkList()) {
				if (tempFile.getFlag().equals("del")) {
					index++;
					attachCnt++;
				}
			}
			if (index != 0) {
				noticeItem.setAttachFileCount(noticeItem.getFileLinkList().size() - index);
			} else {
				//noticeItem.setAttachFileCount(noticeItem.getFileLinkList().size());
				if (tempFileDataList != null) {
					int fileCount = tempFileDataList.size();
					noticeItem.setAttachFileCount(fileCount);

				}else{
					attachCnt = ecmFileDataList.size();
					noticeItem.setAttachFileCount(attachCnt);
				}
			}

		}

		// Tag 삭제
		tagService.delete(noticeItem.getItemId(), NoticeItem.ITEM_TYPE_CODE);

		// Tag 등록
		if (noticeItem.getTag() != null) {
			createTag(noticeItem, user);
		}

		// 이미지 파일 업데이트
		if (noticeItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(noticeItem.getEditorFileLinkList(), noticeItem.getItemId(),
					NoticeItem.ITEM_FILE_TYPE, user);
		}
		String [] ecmFileIds = StringUtils.split(noticeItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(noticeItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(noticeItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(noticeItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(noticeItem.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId(noticeItem.getItemId());
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
				fileLink.setItemId(noticeItem.getItemId());
				fileLink.setItemTypeCode(NoticeItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(noticeItem.getItemId());
				fileLink.setItemTypeCode(NoticeItem.ITEM_FILE_TYPE);
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
		
		noticeItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}

		noticeItemDao.update(noticeItem);

//		// ActivityStream
//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(noticeItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT, noticeItem.getItemId(), noticeItem.getTitle(),
//				"NOTICE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/*
	 * 조회수 UPDATE
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.notice.service.NoticeItemService
	 * #updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String userId, String itemId) {
		int refCount = this.noticeItemDao.countNoticeReference(userId, itemId);
		if (refCount == 0) {
			this.noticeItemDao.updateNoticeHitCount(itemId);
			this.noticeItemDao.createNoticeItemReference(userId, itemId);
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
	public List<NoticeItem> listNoticeItemByPortlet(Map<String, String> map) {
		return this.noticeItemDao.listNoticeItemByPortlet(map);
	}

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<NoticeItem> listDeleteNoticeByWorkspace(String workspaceId) {
	public List<NoticeItem> listDeleteNoticeByWorkspace() {		
		return this.noticeItemDao.listDeleteNoticeByWorkspace();
	}

	/**
	 * 태그작성
	 * 
	 * @param workspace
	 * @param user
	 */
	private void createTag(NoticeItem noticeItem, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(noticeItem.getTag())) {
			Tag tag = new Tag();

			// tagService.create(tag);

			tag.setTagName(noticeItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(noticeItem.getItemId()); // 게시물 ID
			tag.setTagItemType(NoticeItem.ITEM_TYPE_CODE); // 모듈 타입 정의되어 있음.
			tag.setTagItemSubType(""); // 모듈 서브 타입 -
//			tag.setTagItemSubType(noticeItem.getWorkspaceId()); // 모듈 서브 타입 -
																	// 있을때만 넣기
			tag.setTagItemName(noticeItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(noticeItem.getContents()); // WS 소개글
			tag.setTagItemUrl("/collpack/kms/notice/readNoticeItemView.do?itemId="
					+ noticeItem.getItemId()); // WS 팝업창 url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(noticeItem.getPortalId());

			tagService.create(tag);
		}
	}
}
