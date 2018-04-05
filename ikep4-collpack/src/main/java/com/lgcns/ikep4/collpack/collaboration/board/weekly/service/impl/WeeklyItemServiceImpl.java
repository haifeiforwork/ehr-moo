package com.lgcns.ikep4.collpack.collaboration.board.weekly.service.impl;

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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.WeeklyItemDao;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


@Service
//@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class WeeklyItemServiceImpl implements WeeklyItemService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private WeeklyItemDao weeklyItemDao;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;

	/**
	 * 주간보고 목록
	 */
	public SearchResult<WeeklyItem> listWeeklyItemBySearchCondition(WeeklyItemSearchCondition searchCondition) {

		Integer count = this.weeklyItemDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<WeeklyItem> searchResult = null;
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<WeeklyItem>(searchCondition);
		} else {
			List<WeeklyItem> weeklyItemList = this.weeklyItemDao.listBySearchCondition(searchCondition);
			// List<FileData> fileDataList = null;

			int summaryCount = 0;
			for (WeeklyItem weeklyItem : weeklyItemList) {
				if (weeklyItem.getIsSummary() == 1) {
					summaryCount = 1;
				}
				/*
				 * if(weeklyItem.getAttachFileCount()!= null &&
				 * weeklyItem.getAttachFileCount()!=0 ){ fileDataList =
				 * this.fileService.getItemFile(weeklyItem.getItemId(),
				 * WeeklyItem.ITEM_TYPE_CODE);
				 * weeklyItem.setFileDataList(fileDataList); }
				 */
			}

			searchCondition.setSummaryCount(summaryCount);
			searchResult = new SearchResult<WeeklyItem>(weeklyItemList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * 하위Coll. 취합주간보고 게시물 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService
	 * #listLowRankWeeklyItemBySearchCondition
	 * (com.lgcns.ikep4.collpack.collaboration.
	 * board.weekly.search.WeeklyItemSearchCondition)
	 */
	public SearchResult<WeeklyItem> listLowRankWeeklyItemBySearchCondition(WeeklyItemSearchCondition searchCondition) {

		Integer count = this.weeklyItemDao.countLowRankItemBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<WeeklyItem> searchResult = null;
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<WeeklyItem>(searchCondition);
		} else {
			List<WeeklyItem> weeklyItemList = this.weeklyItemDao.listLowRankBySearchCondition(searchCondition);
			List<FileData> fileDataList = null;

			for (WeeklyItem weeklyItem : weeklyItemList) {
				if (weeklyItem.getAttachFileCount() != null && weeklyItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(weeklyItem.getItemId(), WeeklyItem.ITEM_FILE_TYPE);
					weeklyItem.setFileDataList(fileDataList);
				}
			}

			searchResult = new SearchResult<WeeklyItem>(weeklyItemList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * 주간보고 게시물 등록
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService
	 * #createWeeklyItem
	 * (com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem)
	 */
	public String createWeeklyItem(WeeklyItem weeklyItem, User user) {

		String id = idgenService.getNextId();
		weeklyItem.setItemId(id);

		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(weeklyItem.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(weeklyItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						weeklyItem.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					weeklyItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}	
				
		int attachCnt = 0;
		// 첨부파일 업데이트
		if (weeklyItem.getFileLinkList() != null) {
			weeklyItem.setAttachFileCount(weeklyItem.getFileLinkList().size());
			this.fileService.saveFileLink(weeklyItem.getFileLinkList(), weeklyItem.getItemId(),
					AnnounceItem.ITEM_FILE_TYPE, user);
			attachCnt = weeklyItem.getFileLinkList().size();
		}

		// 해당기간 이전 취합본 삭제
		if (weeklyItem.getIsSummary() == 1) {
			List<WeeklyItem> temp = new ArrayList<WeeklyItem>();
			String workspaceId = weeklyItem.getWorkspaceId();
			String weeklyTerm = weeklyItem.getWeeklyTerm();

			temp = weeklyItemDao.getSummaryWeeklyItems(workspaceId, weeklyTerm);
			if (temp != null) {
				for (WeeklyItem idx : temp) {
					this.deleteWeeklyItem(idx);
				}
			}
		}
		
		String [] ecmFileIds = StringUtils.split(weeklyItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(weeklyItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(weeklyItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(weeklyItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(weeklyItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(weeklyItem.getEcmFileId() != null) {
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
				fileLink.setItemId(weeklyItem.getItemId());
				fileLink.setItemTypeCode(AnnounceItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(weeklyItem.getItemId());
				fileLink.setItemTypeCode(AnnounceItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			weeklyItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(weeklyItem.getEcmFileId() != null) {
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

		String itemId = weeklyItemDao.create(weeklyItem);
		

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (weeklyItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(weeklyItem.getEditorFileLinkList(), weeklyItem.getItemId(),	WeeklyItem.ITEM_FILE_TYPE, user);
		}		
		
		// ActivityStream
		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(weeklyItem.getWorkspaceId());
		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, weeklyItem.getItemId(), weeklyItem.getTitle(), "WEEKLY",
				workspace.getWorkspaceId(), workspace.getWorkspaceName());
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
	 * 주간보고 게시물 읽기
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService
	 * #readWeeklyItem(java.lang.String, java.lang.String, java.lang.String)
	 */
	public WeeklyItem readWeeklyItem(String userId, String itemId, String workspaceId) {

		WeeklyItem weeklyItem = new WeeklyItem();

		// 해당 게시물 정보
		weeklyItem = weeklyItemDao.getWeeklyItem(itemId, workspaceId);
		if (weeklyItem != null) {
			List<FileData> fileDataList = this.fileService.getItemFile(itemId, WeeklyItem.ITEM_FILE_TYPE);

			if (fileDataList.size() > 0) {
				weeklyItem.setFileDataList(fileDataList);
			}

			updateWeeklyHitCount(userId, itemId);
		}
		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, WeeklyItem.EDITOR_FILE);
		weeklyItem.setEditorFileDataList(editorFileDataList);
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, WeeklyItem.ITEM_FILE_TYPE);
		weeklyItem.setEcmFileDataList(ecmFileDataList);
		
		return weeklyItem;
	}

	/**
	 * 주간보고 삭제
	 */
	public void deleteWeeklyItem(WeeklyItem weeklyItem) {
		// Reference삭제
		weeklyItemDao.removeWeeklyReference(weeklyItem.getItemId());
		// Item삭제
		weeklyItemDao.removeWeeklyItem(weeklyItem.getItemId());
		// 전체 파일 삭제
		this.fileService.removeItemFile(weeklyItem.getItemId());
		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(weeklyItem.getWorkspaceId());

		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE, weeklyItem.getItemId(), weeklyItem.getTitle(), "WEEKLY",
				workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/**
	 * 주간보고 수정
	 */
	public void updateWeeklyItem(WeeklyItem weeklyItem, User user) {
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(weeklyItem.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(weeklyItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(weeklyItem.getEditorFileLinkList() != null){
							for (int i = 0; i < weeklyItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) weeklyItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						weeklyItem.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					weeklyItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}	
		
		
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(weeklyItem.getItemId(), WeeklyItem.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(weeklyItem.getItemId(), WeeklyItem.ITEM_FILE_TYPE);
		// 첨부파일 업데이트
		if (weeklyItem.getFileLinkList() != null) {

			this.fileService.saveFileLink(weeklyItem.getFileLinkList(), weeklyItem.getItemId(),
					WeeklyItem.ITEM_FILE_TYPE, user);
			int index = 0;
			for (FileLink tempFile : weeklyItem.getFileLinkList()) {
				if (tempFile.getFlag().equals("del")) {
					index++;
					attachCnt++;
					
				}
			}
			if (index != 0) {
				weeklyItem.setAttachFileCount(weeklyItem.getFileLinkList().size() - index);
				attachCnt = weeklyItem.getFileLinkList().size() - index;
			} else {
				/*weeklyItem.setAttachFileCount(weeklyItem.getFileLinkList().size());
				attachCnt = weeklyItem.getFileLinkList().size();*/
				if (tempFileDataList != null) {
					int fileCount = tempFileDataList.size();
					weeklyItem.setAttachFileCount(fileCount);

				}else{
					attachCnt = ecmFileDataList.size();
					weeklyItem.setAttachFileCount(attachCnt);
				}
			}
		}
		
		// 이미지 파일 업데이트
		if (weeklyItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(weeklyItem.getEditorFileLinkList(), weeklyItem.getItemId(),	WeeklyItem.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split(weeklyItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(weeklyItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(weeklyItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(weeklyItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(weeklyItem.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId(weeklyItem.getItemId());
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
				fileLink.setItemId(weeklyItem.getItemId());
				fileLink.setItemTypeCode(WeeklyItem.ITEM_FILE_TYPE);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(weeklyItem.getItemId());
				fileLink.setItemTypeCode(WeeklyItem.ITEM_FILE_TYPE);
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
		
		weeklyItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}

		weeklyItemDao.update(weeklyItem);
		// ActivityStream
		Workspace workspace = new Workspace();
		workspace = workspaceService.getWorkspace(weeklyItem.getWorkspaceId());

		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT, weeklyItem.getItemId(), weeklyItem.getTitle(), "WEEKLY",
				workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/**
	 * 주간보고 조회수 수정
	 * 
	 * @param userId
	 * @param itemId
	 */
	public void updateWeeklyHitCount(String userId, String itemId) {
		// 조회수
		int refCount = this.weeklyItemDao.getWeeklyReference(userId, itemId);
		if (refCount == 0) {
			this.weeklyItemDao.updateWeeklyHitCount(itemId);
			this.weeklyItemDao.createWeeklyItemReference(userId, itemId);
		}
	}

	/**
	 * 주간보고 본문내용 조회
	 */
	public List<WeeklyItem> readWeeklyItemContents(WeeklyItem weeklyItem) {
		return weeklyItemDao.getWeeklyItemContents(weeklyItem);
	}

	/**
	 * WS 정보 조회
	 */
	public Workspace getWorkspaceInfo(String portalId, String workspaceId) {
		Workspace workspace = new Workspace();
		workspace.setWorkspaceId(workspaceId);
		workspace.setPortalId(portalId);
		workspace = workspaceService.read(workspace);

		return workspace;
	}

	/**
	 * 주간보고 기간 게시물 존재유무 확인
	 */
	public String checkWeeklyTerm(Map<String, String> map) {
		return this.weeklyItemDao.checkWeeklyTerm(map);
	}

	/**
	 * Workspace 삭제 배치 실행 - 주간보고중 게시물에 첨부가 존재하는 게시물 목록
	 */
	public List<WeeklyItem> listDeleteWeeklyItemByWorkspace(String workspaceId) {
		return weeklyItemDao.listDeleteWeeklyItemByWorkspace(workspaceId);
	}

}
