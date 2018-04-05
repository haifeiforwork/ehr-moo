package com.lgcns.ikep4.collpack.kms.announce.service.impl;

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

import com.lgcns.ikep4.collpack.kms.announce.dao.AnnounceItemDao;
import com.lgcns.ikep4.collpack.kms.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.kms.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.announce.service.AnnounceItemService;
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


@Service("kmsAnnounceItemService")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class AnnounceItemServiceImpl implements AnnounceItemService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private AnnounceItemDao announceItemDao;

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
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #
	 * listAnnounceItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .announce.search.AnnounceItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<AnnounceItem> listAnnounceItemBySearchCondition(
			AnnounceItemSearchCondition announceItemSearchCondition) {
		Integer count = this.announceItemDao.countBySearchCondition(announceItemSearchCondition);

		announceItemSearchCondition.terminateSearchCondition(count);

		SearchResult<AnnounceItem> searchResult = null;
		if (announceItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AnnounceItem>(announceItemSearchCondition);
		} else {
			List<AnnounceItem> announceItemList = this.announceItemDao
					.listBySearchCondition(announceItemSearchCondition);

			List<FileData> fileDataList = null;

			for (AnnounceItem announceItem : announceItemList) {
				if (announceItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(announceItem.getItemId(), AnnounceItem.ITEM_FILE_TYPE);
					announceItem.setFileDataList(fileDataList);
				}

				List<Tag> tagList = tagService.listTagByItemId(announceItem.getItemId(), AnnounceItem.ITEM_TYPE_CODE);
				announceItem.setTagList(tagList);
			}

			searchResult = new SearchResult<AnnounceItem>(announceItemList, announceItemSearchCondition);
		}

		return searchResult;
	}

	/*
	 * 공지사항 게시물 읽기
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #readAnnounceItem(java.lang.String, java.lang.String)
	 */
	@Transactional
//	public AnnounceItem readAnnounceItem(String userId, String itemId, String workspaceId) {
	public AnnounceItem readAnnounceItem(String userId, String itemId) {

		AnnounceItem announceItem = new AnnounceItem();
//		announceItem = announceItemDao.getAnnounce(itemId, workspaceId);
		announceItem = announceItemDao.getAnnounce(itemId);
		if (announceItem != null) {
			updateHitCount(userId, itemId);

			// 첨부파일 정보
			if (announceItem.getAttachFileCount() > 0) {
				List<FileData> fileDataList = this.fileService.getItemFile(itemId, AnnounceItem.ATTECHED_FILE);
				announceItem.setFileDataList(fileDataList);
			}

			List<Tag> tagList = tagService.listTagByItemId(announceItem.getItemId(), AnnounceItem.ITEM_TYPE_CODE);
			announceItem.setTagList(tagList);
		}

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, AnnounceItem.EDITOR_FILE);
		announceItem.setEditorFileDataList(editorFileDataList);
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, AnnounceItem.ITEM_FILE_TYPE);
		announceItem.setEcmFileDataList(ecmFileDataList);

		return announceItem;
	}

	/*
	 * 공지사항 게시물 등록
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #
	 * createAnnounceItem(com.lgcns.ikep4.collpack.collaboration.board.announce.model
	 * .AnnounceItem)
	 */
	public String createAnnounceItem(AnnounceItem announceItem, User user) {
		String id = idgenService.getNextId();
		announceItem.setItemId(id);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (announceItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(announceItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						announceItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					announceItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		// 첨부파일 업데이트
		if (announceItem.getFileLinkList() != null) {
			announceItem.setAttachFileCount(announceItem.getFileLinkList().size());
			this.fileService.saveFileLink(announceItem.getFileLinkList(), announceItem.getItemId(),
					AnnounceItem.ITEM_FILE_TYPE, user);
			attachCnt = announceItem.getFileLinkList().size();
		}

		// Tag 등록
		if (announceItem.getTag() != null) {
			createTag(announceItem, user);
		}
		String [] ecmFileIds = StringUtils.split(announceItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(announceItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(announceItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(announceItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(announceItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(announceItem.getEcmFileId() != null) {
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
				fileLink.setItemId(announceItem.getItemId());
				fileLink.setItemTypeCode(announceItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(announceItem.getItemId());
				fileLink.setItemTypeCode(announceItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			announceItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(announceItem.getEcmFileId() != null) {
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
		String itemId = announceItemDao.create(announceItem);

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (announceItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(announceItem.getEditorFileLinkList(), announceItem.getItemId(),
					AnnounceItem.ITEM_FILE_TYPE, user);
		}

//		if (!StringUtil.isEmpty(itemId)) {
//			// 링크 INSERT
//			announceItem.setIsOwner("1");
//			announceItemDao.createLinkAnnounce(announceItem);
//		}

//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(announceItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, announceItem.getItemId(), announceItem.getTitle(),
//				"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());

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
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #
	 * adminDeleteAnnounceItem(com.lgcns.ikep4.collpack.collaboration.board.announce.
	 * model.AnnounceItem)
	 */
	public void adminDeleteAnnounceItem(AnnounceItem announceItem) {
//		if (!StringUtil.isEmpty(announceItem.getIsOwner()) && announceItem.getIsOwner().equals("1")) {
			// Link삭제
//			announceItemDao.removeAnnounceLink(announceItem.getItemId(), null);
//			announceItemDao.removeAnnounceLink(announceItem.getItemId());
			// Reference삭제
			announceItemDao.removeAnnounceReference(announceItem.getItemId());
			// Item삭제
			announceItemDao.removeAnnounceItem(announceItem.getItemId());
			// 전체 파일 삭제
			this.fileService.removeItemFile(announceItem.getItemId());
			// Tag삭제
			// Tag 삭제
			tagService.delete(announceItem.getItemId(), AnnounceItem.ITEM_TYPE_CODE);
			// ActivityStream
//			Workspace workspace = new Workspace();
//			workspace = workspaceService.getWorkspace(announceItem.getWorkspaceId());
//			activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//					IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE, announceItem.getItemId(), announceItem.getTitle(),
//					"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
//		} else if (!StringUtil.isEmpty(announceItem.getIsOwner()) && announceItem.getIsOwner().equals("0")) {
//			// Link삭제
//			announceItemDao.removeAnnounceLink(announceItem.getItemId(), announceItem.getWorkspaceId());
//			announceItemDao.removeAnnounceLink(announceItem.getItemId());
//		}
	}

	/*
	 * 공지사항 게시물 다중삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #adminMultiDeleteAnnounceItem(java.util.List, java.lang.String)
	 */
//	public void adminMultiDeleteAnnounceItem(List<String> itemIds, String workspaceId, String portalId) {
	public void adminMultiDeleteAnnounceItem(List<String> itemIds, String portalId) {
		AnnounceItem announceItem = new AnnounceItem();

		for (String itemId : itemIds) {
//			announceItem = this.announceItemDao.getAnnounce(itemId, workspaceId);
			announceItem = this.announceItemDao.getAnnounce(itemId);

			// 이미 삭제가 되었다면 continue
			if (announceItem == null) {
				continue;
			}

//			if ("0".equals(announceItem.getIsOwner())) {
//				 하위 운영자에 의한 Link삭제만
//				announceItemDao.removeAnnounceLink(announceItem.getItemId(), workspaceId);
//				announceItemDao.removeAnnounceLink(announceItem.getItemId());
//			} else {
				adminDeleteAnnounceItem(announceItem);// is_Owner :1
//			}
		}
	}

	/*
	 * 하위부서 중 개설 WORKSPACE 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #
	 * listChildWorkspaceInfoBySearchCondition(com.lgcns.ikep4.collpack.collaboration
	 * .board.announce.search.AnnounceItemSearchCondition)
	 */
//	public SearchResult<AnnounceItem> listChildWorkspaceInfoBySearchCondition(
//			AnnounceItemSearchCondition searchCondition) {
//
//		Workspace workspace = new Workspace();
//		workspace = getWorkspaceInfo(searchCondition.getPortalId(), searchCondition.getWorkspaceId());
//
//		SearchResult<AnnounceItem> searchResult = null;
//
//		searchCondition.setTeamId(workspace.getTeamId());
//		Integer count = this.announceItemDao.countChildWorkspaceBySearchCondition(searchCondition);
//		searchCondition.terminateSearchCondition(count);
//		if (searchCondition.isEmptyRecord()) {
//			searchResult = new SearchResult<AnnounceItem>(searchCondition);
//
//		} else {
//
//			List<AnnounceItem> announceWorkspceList = this.announceItemDao
//					.listChildWorkspaceBySearchCondition(searchCondition);
//			searchResult = new SearchResult<AnnounceItem>(announceWorkspceList, searchCondition);
//		}
//
//		return searchResult;
//	}

	/*
//	 * 공지사항 게시물 하위부서 공유
//	 * @see
//	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
//	 * #createAnnounceLinkItem(java.lang.String, java.util.List)
//	 */
//	public void createAnnounceLinkItem(String announceItemId, List<String> workspaceIds) {
//		// DELETE RESET
//		announceItemDao.removeAnnounceShareLink(announceItemId);
//		if (workspaceIds != null) {
//			for (int j = 0; j < workspaceIds.size(); j++) {
//				String workspaceId = workspaceIds.get(j);
//				// insert
//				AnnounceItem announceItem = new AnnounceItem();
//				announceItem.setItemId(announceItemId.trim());
//				announceItem.setWorkspaceId(workspaceId);
//				announceItem.setIsOwner("0");
//				announceItemDao.createLinkAnnounce(announceItem);
//			}
//		}
//	}

	/*
	 * 공지사항게시물 업데이트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #
	 * updateAnnounceItem(com.lgcns.ikep4.collpack.collaboration.board.announce.model
	 * .AnnounceItem)
	 */
	public void updateAnnounceItem(AnnounceItem announceItem, User user) {
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (announceItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(announceItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (announceItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < announceItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) announceItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						announceItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					announceItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(announceItem.getItemId(), AnnounceItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(announceItem.getItemId(), AnnounceItem.ITEM_FILE_TYPE);
		// 첨부파일 업데이트
		if (announceItem.getFileLinkList() != null) {
			this.fileService.saveFileLink(announceItem.getFileLinkList(), announceItem.getItemId(),
					AnnounceItem.ITEM_FILE_TYPE, user);

			int index = 0;
			for (FileLink tempFile : announceItem.getFileLinkList()) {
				if (tempFile.getFlag().equals("del")) {
					index++;
					attachCnt++;
				}
			}
			if (index != 0) {
				announceItem.setAttachFileCount(announceItem.getFileLinkList().size() - index);
			} else {
				//announceItem.setAttachFileCount(announceItem.getFileLinkList().size());
				if (tempFileDataList != null) {
					int fileCount = tempFileDataList.size();
					announceItem.setAttachFileCount(fileCount);

				}else{
					attachCnt = ecmFileDataList.size();
					announceItem.setAttachFileCount(attachCnt);
				}
			}

		}

		// Tag 삭제
		tagService.delete(announceItem.getItemId(), AnnounceItem.ITEM_TYPE_CODE);

		// Tag 등록
		if (announceItem.getTag() != null) {
			createTag(announceItem, user);
		}

		// 이미지 파일 업데이트
		if (announceItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(announceItem.getEditorFileLinkList(), announceItem.getItemId(),
					AnnounceItem.ITEM_FILE_TYPE, user);
		}
		String [] ecmFileIds = StringUtils.split(announceItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(announceItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(announceItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(announceItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(announceItem.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId(announceItem.getItemId());
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
				fileLink.setItemId(announceItem.getItemId());
				fileLink.setItemTypeCode(AnnounceItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(announceItem.getItemId());
				fileLink.setItemTypeCode(AnnounceItem.ITEM_FILE_TYPE);
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
		
		announceItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}

		announceItemDao.update(announceItem);

//		// ActivityStream
//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(announceItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT, announceItem.getItemId(), announceItem.getTitle(),
//				"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/*
	 * 조회수 UPDATE
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService
	 * #updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String userId, String itemId) {
		int refCount = this.announceItemDao.countAnnounceReference(userId, itemId);
		if (refCount == 0) {
			this.announceItemDao.updateAnnounceHitCount(itemId);
			this.announceItemDao.createAnnounceItemReference(userId, itemId);
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
	public List<AnnounceItem> listAnnounceItemByPortlet(Map<String, String> map) {
		return this.announceItemDao.listAnnounceItemByPortlet(map);
	}

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<AnnounceItem> listDeleteAnnounceByWorkspace(String workspaceId) {
	public List<AnnounceItem> listDeleteAnnounceByWorkspace() {		
		return this.announceItemDao.listDeleteAnnounceByWorkspace();
	}

	/**
	 * 태그작성
	 * 
	 * @param workspace
	 * @param user
	 */
	private void createTag(AnnounceItem announceItem, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(announceItem.getTag())) {
			Tag tag = new Tag();

			// tagService.create(tag);

			tag.setTagName(announceItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(announceItem.getItemId()); // 게시물 ID
			tag.setTagItemType(AnnounceItem.ITEM_TYPE_CODE); // 모듈 타입 정의되어 있음.
			tag.setTagItemSubType(""); // 모듈 서브 타입 -
//			tag.setTagItemSubType(announceItem.getWorkspaceId()); // 모듈 서브 타입 -
																	// 있을때만 넣기
			tag.setTagItemName(announceItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(announceItem.getContents()); // WS 소개글
			tag.setTagItemUrl("/collpack/kms/announce/readAnnounceItemView.do?itemId="
					+ announceItem.getItemId()); // WS 팝업창 url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(announceItem.getPortalId());

			tagService.create(tag);
		}
	}
}
