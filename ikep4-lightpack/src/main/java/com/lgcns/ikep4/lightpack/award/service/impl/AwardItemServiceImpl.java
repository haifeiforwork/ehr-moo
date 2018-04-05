/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

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
import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardRecommendDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardReferenceDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.model.AwardRecommend;
import com.lgcns.ikep4.lightpack.award.model.AwardReference;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardBatchService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemReaderService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
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
 * AwardItemService 구현체 클래스
 */
@Service
public class AwardItemServiceImpl implements AwardItemService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	/** The file service. */
	@Autowired
	private FileService fileService;

	/** The award item dao. */
	@Autowired
	private AwardItemDao awardItemDao;

	/** The award linereply dao. */
	@Autowired
	private AwardLinereplyDao awardLinereplyDao;

	@Autowired
	private AwardRecommendDao awardRecommendDao;

	@Autowired
	private AwardReferenceDao awardReferenceDao;

	@Autowired
	private TagService tagService;

	@Autowired
	private AwardDao awardDao;

	@Autowired
	private ActivityStreamService activityStreamService;
	

	@Autowired
	private AwardItemReaderService awardItemReaderService;
	
	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private AwardBatchService awardBatchService;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	protected final org.apache.commons.logging.Log clog = LogFactory.getLog(getClass());
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService#doAlarmJobSchedule(java.util.Date, int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doAwardItemNotiJobSchedule(Date jobTime, int interval, String fileUrl) throws ParseException {
		List<AwardItemReader> awardItemList = this.awardItemReaderService.listAwardItemAllNoti();
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		Properties imageprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		Set alarmIds = new HashSet();
		//clog.debug("@@@@@@@@@@@@@@@awardItemList.size():"+awardItemList.size());
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String downUrl ="/support/fileupload/downloadFile.do?fileId=";
		String imageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_image");
		String newImageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_profile_image");

		for (AwardItemReader awardItem : awardItemList) {
			User user = this.userService.read(awardItem.getReaderId());
			List<AwardItemReader> awardItemReaderList =this.awardItemReaderService.listReaderAllMail(awardItem.getAwardItemId());
			List<FileData> contentsAddAtts = fileService.getItemFile(awardItem.getAwardItemId(), "N");//첨부된 파일 리스트를 가져온다.
			List<FileData> contentsAddImgs = fileService.getItemFileAll(awardItem.getAwardItemId(), "Y");//해당 글에 에디터로 첨부된 이미지리스트를 가져온다. 
			String contents ="";
			String filename = "";
			
			
			
			for (FileData addAtt : contentsAddAtts) {
				filename =filename+"<br><img src='" + baseUrl + "/base/images/icon/ic_file_"+addAtt.getFileExtension()+".gif' /><a title='"+addAtt.getFileRealName()+"' style='color:black;' href='"+baseUrl+downUrl+addAtt.getFileId()+"'> "+addAtt.getFileRealName()+"</a>";
			}
			
			String tmpContents = "";
			if(contentsAddImgs.size() > 0){
				tmpContents = awardItem.getContents().replaceAll("/support/fileupload/downloadFile.do[?]fileId=", "/mailimage/");
			}else{
				tmpContents = awardItem.getContents();
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

			for (AwardItemReader awardItemReader : awardItemReaderList) {
				if(StringUtil.isEmpty(awardItemReader.getReaderMail())){//메일주소 없는 사용자는 메일 보내지 않음.
				}else{
			
					if(!StringUtil.isEmpty(awardItemReader.getReaderPassword())){//패스워드 없는 자는 퇴사자 일것입니다.
						HashMap<String, String> recip= new HashMap<String, String>();
						recip.put("email", awardItemReader.getReaderMail());
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ awardItemReader.getReaderMail():"+awardItemReader.getReaderMail());
						
						
						Mail mail = new Mail();
						mail.setMailType(MailConstant.MAIL_TYPE_HTML);
						if(!StringUtil.isEmpty(awardItem.getWordName())){
							mail.setTitle("["+awardItem.getWordName()+"] 공지사항이 등록되었습니다.");
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
							linkUrl = commonprop.getProperty("ikep4.baseUrl")+"/messengerLogin.do?gubun=2&itemId="+awardItem.getAwardItemId()+"&j_username="+awardItemReader.getReaderId()+"&j_password="+URLEncoder.encode(awardItemReader.getReaderPassword(), "UTF-8");
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
			
			alarmIds.add(awardItem.getAwardItemId());
			 
		}
		
		if(alarmIds.size()>0) {
			awardItemDao.updateMailSent(alarmIds);
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doAwardItemNotiInstant(String itemId) throws ParseException {
		List<AwardItemReader> awardItemList = this.awardItemReaderService.listAwardItemAllNotiInstant(itemId);
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		Properties imageprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String downUrl ="/support/fileupload/downloadFile.do?fileId=";
		String imageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_image");
		String newImageUrl =imageprop.getProperty("ikep4.support.fileupload.upload_root_profile_image");

		for (AwardItemReader awardItem : awardItemList) {
			User user = this.userService.read(awardItem.getReaderId());
			List<AwardItemReader> awardItemReaderList =this.awardItemReaderService.listReaderAllMail(awardItem.getAwardItemId());
			List<FileData> contentsAddAtts = fileService.getItemFile(awardItem.getAwardItemId(), "N");//첨부된 파일 리스트를 가져온다.
			List<FileData> contentsAddImgs = fileService.getItemFileAll(awardItem.getAwardItemId(), "Y");//해당 글에 에디터로 첨부된 이미지리스트를 가져온다. 
			String contents ="";
			String filename = "";
			
			
			
			for (FileData addAtt : contentsAddAtts) {
				filename =filename+"<br><img src='" + baseUrl + "/base/images/icon/ic_file_"+addAtt.getFileExtension()+".gif' /><a title='"+addAtt.getFileRealName()+"' style='color:black;' href='"+baseUrl+downUrl+addAtt.getFileId()+"'> "+addAtt.getFileRealName()+"</a>";
			}
			
			String tmpContents = "";
			if(contentsAddImgs.size() > 0){
				tmpContents = awardItem.getContents().replaceAll("/support/fileupload/downloadFile.do[?]fileId=", "/mailimage/");
			}else{
				tmpContents = awardItem.getContents();
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

			awardBatchService.sendUserMail( awardItemReaderList, contents,user,awardItem.getWordName());
			
			 
		}
		
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#listAwardItemBySearchCondition(com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition)
	 */
	public SearchResult<AwardItem> listAwardItemBySearchCondition(AwardItemSearchCondition searchCondition) {
		Integer count = this.awardItemDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.listBySearchCondition(searchCondition);

			if(awardItemList != null && awardItemList.size() > 0) {
				for(AwardItem awardItem : awardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(awardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					awardItem.setTagList(tagList);
					//awardItem.setUser(this.userService.read(awardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}

			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;


	}
	
	public SearchResult<AwardItem> listAwardItemBySearchCondition3(AwardItemSearchCondition searchCondition) {
		Integer count = this.awardItemDao.countBySearchCondition3(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.listBySearchCondition3(searchCondition);

			if(awardItemList != null && awardItemList.size() > 0) {
				for(AwardItem awardItem : awardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(awardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					awardItem.setTagList(tagList);
					//awardItem.setUser(this.userService.read(awardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}

			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;


	}
	
	public SearchResult<AwardItem> deleteListAwardItemBySearchCondition(AwardItemSearchCondition searchCondition) {
		Integer count = this.awardItemDao.deleteCountBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.deleteListBySearchCondition(searchCondition);

			if(awardItemList != null && awardItemList.size() > 0) {
				for(AwardItem awardItem : awardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(awardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					awardItem.setTagList(tagList);
				}
			}

			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;


	}
	public SearchResult<AwardItem> listAwardItemBySearchCondition2(AwardItemSearchCondition searchCondition) {
		Integer count = this.awardItemDao.countBySearchCondition2(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.listBySearchCondition2(searchCondition);
			/*
			if(awardItemList != null && awardItemList.size() > 0) {
				for(AwardItem awardItem : awardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(awardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					awardItem.setTagList(tagList);
					//awardItem.setUser(this.userService.read(awardItem.getRegisterId()));//등록자 정보를 셋팅
				}
			}
			*/
			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;


	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#readAwardItem(java.lang.String)
	 */
	public AwardItem readAwardItem(String itemId) {

		//게시물을 가져온다.
		AwardItem awardItem = this.awardItemDao.get(itemId);

		//첨부 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> fileDataList = this.fileService.getItemFile(itemId, AwardItem.ATTECHED_FILE);
		awardItem.setFileDataList(fileDataList);
		
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(itemId, AwardItem.ATTECHED_FILE);
		awardItem.setEcmFileDataList(ecmFileDataList);

		//CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, AwardItem.EDITOR_FILE);
		awardItem.setEditorFileDataList(editorFileDataList);

		//태그 목록을 조회하고 게시물에 넣는다
		List<Tag> tagList = this.tagService.listTagByItemId(itemId,  TagConstants.ITEM_TYPE_BBS);
		awardItem.setTagList(tagList);

		//태그 문자열을 만든다.
		if(tagList != null && tagList.size() > 0) {
			StringBuffer tagBuffer = new StringBuffer();

			for(Tag tag : tagList) {
				tagBuffer.append(tag.getTagName());
				tagBuffer.append(", ");
			}

			awardItem.setTag(StringUtils.substringBeforeLast(tagBuffer.toString(), ","));
		}

		
		
		List<AwardItemReader> awardItemReaderList = this.awardItemReaderService.listAwardItemReader(itemId);//독서자를 가져온다.
		awardItem.setAwardItemReaderList(awardItemReaderList);
		
		
		return awardItem;
	}
	
	public List<String> materialList(String itemId){
		List<String> materialList = awardItemDao.materialList(itemId);
		return materialList;
	}

	/**
	 * 태그들을 저장한다.
	 * 태그는 화면에서 문자열 형태로 넘어온다. (ex: Tag-A, Tag-B, Tag-C)
	 *
	 * @param awardItem the award item
	 */
	private void saveTags(AwardItem awardItem) {
		//태그저장
		if(StringUtils.isEmpty(awardItem.getTag())) {
			return;
		} else {
			Tag tag = new Tag();

			tag.setTagName(awardItem.getTag());
			tag.setTagItemId(awardItem.getItemId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_BBS);
			tag.setTagItemName(awardItem.getTitle());
			tag.setTagItemContents(awardItem.getContents());
			tag.setTagItemUrl("/lightpack/award/awardItem/readAwardItemView.do?&docPopup=true&awardId=" + awardItem.getAwardId()+ "&itemId="+awardItem.getItemId());
			tag.setRegisterId(awardItem.getRegisterId());
			tag.setRegisterName(awardItem.getRegisterName());
			tag.setPortalId(awardItem.getPortalId());

			this.tagService.create(tag);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#createAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	@SuppressWarnings("unchecked")
	public String createAwardItem(AwardItem awardItem, User user) {
		awardItem.setStep(0);
		awardItem.setIndentation(0);
		awardItem.setHitCount(0);
		awardItem.setRecommendCount(0);
		awardItem.setReplyCount(0);
		awardItem.setLinereplyCount(0);
		awardItem.setAttachFileCount(0);
		awardItem.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();
		
		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		awardItem.setItemId(id);
		awardItem.setItemGroupId(id);
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(awardItem.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기

					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(awardItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						awardItem.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					awardItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		int attachCnt = 0;
		//게시물의 첨부파일 갯수를 업데이트 한다.
		if(awardItem.getFileLinkList() != null) {
			awardItem.setAttachFileCount(awardItem.getFileLinkList().size());
			attachCnt = awardItem.getFileLinkList().size();
		}
		
		String [] ecmFileIds = StringUtils.split(awardItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(awardItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(awardItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(awardItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(awardItem.getEcmFileUrl2(), "|");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
			
		if(awardItem.getEcmFileId() != null) {
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
				fileLink.setItemId(awardItem.getItemId());
				fileLink.setItemTypeCode("AW");
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(awardItem.getItemId());
				fileLink.setItemTypeCode("AW");
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.create(fileLink);
				
			}
			awardItem.setAttachFileCount(attachCnt);
		}
		
		
		try{
			
			if(awardItem.getEcmFileId() != null) {
				
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
		if(awardItem.getEditorFileLinkList() != null && awardItem.getEditorFileLinkList().size()>0) {
			if(awardItem.getEditorFileLinkList().get(0) != null){
				awardItem.setImageFileId(awardItem.getEditorFileLinkList().get(0).getFileId());
			}
		}
		
		//게시물을 생성한다.
		String awardItemId = this.awardItemDao.create(awardItem);

		//게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if(awardItem.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(awardItem.getFileLinkList(), awardItem.getItemId(), "AW", user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(awardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(awardItem.getEditorFileLinkList(), awardItem.getItemId(), "AW", user);
		}

		//태그저장
		this.saveTags(awardItem);

		//Activity Stream 게시글 생성
		this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_POST, awardItemId, awardItem.getTitle());
		
		//독서자 지정
		readerSetting(awardItem.getItemId(), awardItem);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getReaderMailFlag():"+awardItem.getReaderMailFlag());

		return awardItemId;
	
		

	}
	
	public void saveAwardItemMaterial(AwardItem awardItem){
		awardItemDao.deleteAwardItemMaterial(awardItem.getItemId());
		String materials[] = awardItem.getAwardMaterial().split(",");
		for(int i=0;i<materials.length;i++){
			awardItem.setAwardMaterial(materials[i]);
			awardItemDao.saveAwardItemMaterial(awardItem);
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
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#createReplyAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	@SuppressWarnings("unchecked")
	public String createReplyAwardItem(AwardItem awardItem, User user) {
		awardItem.setHitCount(0);
		awardItem.setRecommendCount(0);
		awardItem.setReplyCount(0);
		awardItem.setLinereplyCount(0);
		awardItem.setAttachFileCount(0);
		awardItem.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다.
		awardItem.setItemId(id);
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(awardItem.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(awardItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						awardItem.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					awardItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		//게시물의 첨부파일 갯수를 업데이트 한다.
		if(awardItem.getFileLinkList() != null) {
			awardItem.setAttachFileCount(awardItem.getFileLinkList().size());
		}
		
		//게시글들의 스탭을 업데이트 한다.(같은 Level에 존재하는 글에 대한 순서이다.)
		this.awardItemDao.updateStep(awardItem);

		// 답글 게시글을 생성한다.
		String awardItemId = this.awardItemDao.create(awardItem);

		//첨부파일 업데이트
		if(awardItem.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(awardItem.getFileLinkList(), awardItem.getItemId(), "AW", user);
		}

		//이미지 파일  업데이트
		if(awardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(awardItem.getEditorFileLinkList(), awardItem.getItemId(), "AW", user);
		}

		//태그저장
		this.saveTags(awardItem);

		//Activity Stream 게시글 생성
		this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_POST, awardItemId, awardItem.getTitle());

		//답글 갯수를 업데이트 한다.
		this.awardItemDao.updateReplyCount(awardItem.getItemParentId());


		
		List<AwardItemReader> awardItemReaderList = this.awardItemReaderService.listAwardItemReader(awardItem.getItemParentId());//부모글의 독서자를 가져온다.
		for (int i = 0; i < awardItemReaderList.size(); i++) {
			AwardItemReader awardItemReader = (AwardItemReader) awardItemReaderList.get(i);
			awardItemReader.setAwardItemId(awardItemId);//답글의 독서자로 셋팅.
			this.awardItemReaderService.createAwardItemReader(awardItemReader);
		}
		
		return awardItemId;
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void updateAwardItem(AwardItem awardItem, User user) {
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(awardItem.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기

					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(awardItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(awardItem.getEditorFileLinkList() != null){
							for (int i = 0; i < awardItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) awardItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						awardItem.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					awardItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		
		int attachCnt = 0;
		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm(awardItem.getItemId(), AwardItem.ATTECHED_FILE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile(awardItem.getItemId(), AwardItem.ATTECHED_FILE);
		//게시물의 첨부파일 갯수를 업데이트 한다.
		if(awardItem.getFileLinkList() != null) {
			
			int fileCount = 0;
			for(FileLink fileLink :awardItem.getFileLinkList()){
				if(!fileLink.getFlag().equals("del")){
					++fileCount;
					attachCnt++;
				}
			}
			
			awardItem.setAttachFileCount(fileCount);
			
			this.fileService.saveFileLink(awardItem.getFileLinkList(), awardItem.getItemId(), "AW", user);
		}else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				awardItem.setAttachFileCount(fileCount);

			}else{
				attachCnt = ecmFileDataList.size();
				awardItem.setAttachFileCount(attachCnt);
			}
		}
		
		
		
		String [] ecmFileIds = StringUtils.split(awardItem.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split(awardItem.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split(awardItem.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split(awardItem.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split(awardItem.getEcmFileUrl2(), "|");
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
					fileLink1.setItemId(awardItem.getItemId());
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
				fileLink.setItemId(awardItem.getItemId());
				fileLink.setItemTypeCode("AW");
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				this.fileLinkDao.createEcmFileLink(fileLink);
				
				fileLink.setFileId(fileId);
				fileLink.setItemId(awardItem.getItemId());
				fileLink.setItemTypeCode("AW");
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
		
		awardItem.setAttachFileCount(attachCnt);
		}
		}
		catch(Exception e) {
		}
		

		//이미지 파일  업데이트
		if(awardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(awardItem.getEditorFileLinkList(), awardItem.getItemId(), "AW", user);
		}

		List<FileData> fileDateList = this.fileService.getItemFile(awardItem.getItemId(), AwardItem.EDITOR_FILE);

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if(fileDateList != null && fileDateList.size() > 0) {
			awardItem.setImageFileId(fileDateList.get(0).getFileId() );
		}
		
		this.awardItemDao.update(awardItem);
		

		
		AwardItem masterInfo = this.awardItemDao.get(awardItem.getItemId());

		//답글이 존재하면 답변글의 날짜도 변경한다.
		if(masterInfo.getReplyCount() > 0) {
			//본인 포함 하위 게시글을 가져온다.
			List<AwardItem> awardItemList = this.awardItemDao.listLowerItemThread(awardItem.getItemId());

			for(AwardItem looping : awardItemList) {
				looping.setStartDate(awardItem.getStartDate());
				looping.setEndDate(awardItem.getEndDate());
				looping.setItemDisplay(awardItem.getItemDisplay());//부모글을 공지로 수정하면 함께 공지로 해준다.
				//if("100000793116".equals(awardItem.getCompanyNoticeAwardId())){
				//looping.setCompanyNoticeAwardId("100000793116");//부모글을 전사공지로 수정하면 함께 전사공지로 해준다.
				//}
				this.awardItemDao.update(looping);
				
				if(StringUtil.isEmpty(awardItem.getItemParentId())){//최상위 글이면
					readerSetting(looping.getItemId(), awardItem);//자식 독서자를 최상위 글과 동일하게 설정해줌. //독서자 메일 발송여부는 각각이므로 제외.
				}//그외는 이미 답변글의 그대로 유지.
			}
		}else{
			if(StringUtil.isEmpty(awardItem.getItemParentId())){//최상위 글이면
				readerSetting(awardItem.getItemId(), awardItem);
			}
		}
		

		//태그저장
		this.saveTags(awardItem);


		//Activity Stream 게시글 수정
		this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_EDIT, awardItem.getItemId(), awardItem.getTitle());
		

	}

	public void readerSetting(String itemId, AwardItem awardItem){
		this.awardItemReaderService.deleteReader(itemId);//기존 독서자 지운다.
		//독서자 지정
		if (awardItem.getReaderList() != null) {
			for (String readerId : awardItem.getReaderList()) {

					String reader = readerId.substring(readerId.lastIndexOf(":") + 1,
							readerId.length());
					AwardItemReader awardItemReader  = new AwardItemReader();
					awardItemReader.setAwardItemId(itemId);
					awardItemReader.setReaderId(reader);
					awardItemReader.setReaderType(readerId.substring(0, 1));//G 또는 U 또는 C 로 들어간다.
					this.awardItemReaderService.createAwardItemReader(awardItemReader);

			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#adminDeleteAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void adminDeleteAwardItem(AwardItem awardItem) {

		AwardItem persisted = this.awardItemDao.get(awardItem.getItemId());


		if(persisted.getReplyCount() == 0) {
			this.updateItemDeleteField(awardItem.getItemId(), AwardItem.STATUS_DELETE_WAITING);
			
			//태그를 삭제한다.
			this.tagService.delete(awardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

			//Activity Stream 게시물 삭제 등록
			this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_DELETE, awardItem.getItemId(), awardItem.getTitle());

		} else {
			//본인 포함 하위 게시글을 가져온다.
			List<AwardItem> awardItemList = this.awardItemDao.listLowerItemThread(awardItem.getItemId());

			for(AwardItem loopingItem : awardItemList) {
				this.updateItemDeleteField(loopingItem.getItemId(), AwardItem.STATUS_DELETE_WAITING);
				
				//태그를 삭제한다.
				this.tagService.delete(awardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

				//Activity Stream 게시물 삭제 등록
				this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_DELETE, loopingItem.getItemId(), loopingItem.getTitle());
			}
		}
		
		

		//부모글의 답글 갯수를 업데이트 한다.
		if(awardItem.getItemParentId() != null){
			this.awardItemDao.updateReplyCount(awardItem.getItemParentId());
		}
	}




	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#userDeleteAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void userDeleteAwardItem(AwardItem awardItem) {
		AwardItem persisted = this.awardItemDao.get(awardItem.getItemId());

		if(persisted.getReplyCount() == 0) {
			this.adminDeleteAwardItem(awardItem);

		} else {

			this.awardItemDao.logicalDelete(awardItem);
			//태그를 삭제한다.
			this.tagService.delete(awardItem.getItemId(), TagConstants.ITEM_TYPE_BBS);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(AwardRecommend awardRecommend) {
		//이미 추천을 했다면
		if(!this.awardRecommendDao.exists(awardRecommend)) {
			this.awardItemDao.updateRecommendCount(awardRecommend.getItemId());
			this.awardRecommendDao.create(awardRecommend);
		}

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateHitCount(java.lang.String)
	 */
	public void updateHitCount(AwardReference awardReference) {

		//이미 조회를 했다면 등록 일자만 업데이트 해준다.
		if(this.awardReferenceDao.exists(awardReference)) {
			this.awardReferenceDao.update(awardReference);

			//조회를 하지 않았다면 조회 데이터를 생성한다.
		} else {
			this.awardItemDao.updateHitCount(awardReference.getItemId());
			this.awardReferenceDao.create(awardReference);
		}
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#adminMultiDeleteAwardItem(java.lang.String[])
	 */
	public void adminMultiDeleteAwardItem(String[] itemIds) {
		AwardItem awardItem = null;

		for(String itemId : itemIds) {
			awardItem = this.awardItemDao.get(itemId);

			//이미 삭제가 되었다면 continue
			if(awardItem == null || awardItem.getItemDelete() == AwardItem.STATUS_DELETE_WAITING) {
				continue;
			}

			this.adminDeleteAwardItem(awardItem);
		}
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#exsitRecommend(com.lgcns.ikep4.lightpack.award.model.AwardRecommend)
	 */
	public Boolean exsitRecommend(AwardRecommend awardRecommend) {
		return this.awardRecommendDao.exists(awardRecommend);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#readAwardItemMasterInfo(java.lang.String)
	 */
	public AwardItem readAwardItemMasterInfo(String itemId) {
		return this.awardItemDao.get(itemId);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#listRecentAwardItem(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<AwardItem> listRecentAwardItem(AwardItemSearchCondition searchCondition) {
					
		Award award = this.awardDao.get(searchCondition.getAwardId());

		List<AwardItem> awardItemList = null;
		if("100000793108".equals(award.getAwardId())||"100000793116".equals(award.getAwardId())){//공문공지이거나 일반공지 이면
			awardItemList = this.awardItemDao.listRecentAwardItem2(searchCondition);		
		}else{
			awardItemList = this.awardItemDao.listRecentAwardItem(searchCondition);		
		}
		
		//게시글의 게시판 정보를 넣는다.
		for(AwardItem awardItem : awardItemList) {
	
			awardItem.setAnonymous(award == null ? 0 : award.getAnonymous());

			if(awardItem.getAnonymous() == 1) {
				
				User tmpUser = new User();				
				tmpUser.setUserName(awardItem.getUpdaterName());		
				
				awardItem.setRegisterId(null);
				awardItem.setRegisterName(null);
				awardItem.setUpdaterId(null);
				awardItem.setUpdaterName(null);
				awardItem.setUser(tmpUser);
			}
		}

		return awardItemList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateItemDelelteField(java.lang.String, java.lang.Integer)
	 */
	public void updateItemDeleteField(String itemId, Integer status) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("itemId", itemId);
		parameter.put("status", status);

		AwardItem awardItem = this.readAwardItemMasterInfo(itemId);
		this.awardItemDao.updateItemDeleteField(parameter);

		if(status == AwardItem.STATUS_DELETE_WAITING) {
			
			//Activity Stream 게시물 삭제 등록
			this.activityStreamService.create("AW", IKepConstant.ACTIVITY_CODE_DOC_DELETE, awardItem.getItemId(), awardItem.getTitle());

			//부모글의 답글 갯수를 업데이트 한다.
			this.awardItemDao.updateReplyCount(awardItem.getItemParentId());
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateItemListDeleteField(java.util.List, java.lang.Integer)
	 */
	public void updateItemListDeleteField(List<String> awardItemIdList, Integer status) {

		for(String itemId : awardItemIdList) {
			this.updateItemThreadDeleteField(itemId, status);
		}


	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#updateItemThreadDeleteField(java.lang.String, java.lang.Integer)
	 */
	public void updateItemThreadDeleteField(String itemId, Integer status) {
		List<AwardItem> awardItemList = this.awardItemDao.listLowerItemThread(itemId);

		for(AwardItem loopingItem : awardItemList) {
			this.updateItemDeleteField(loopingItem.getItemId(), AwardItem.STATUS_DELETE_WAITING);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#deleteAwardItem(com.lgcns.ikep4.lightpack.award.model.AwardItem)
	 */
	public void deleteAwardItemThread(AwardItem awardItem) {
		//본인 포함 하위 게시글을 가져온다.
		List<AwardItem> awardItemList = this.awardItemDao.listLowerItemThread(awardItem.getItemId());

		for(AwardItem loopingItem : awardItemList) {
			//태그를 삭제한다.
			this.tagService.delete(loopingItem.getItemId(), TagConstants.ITEM_TYPE_BBS);

			//전체 파일 삭제
			this.fileService.removeItemFile(loopingItem.getItemId());

			//댓글 쓰레드를 삭제한다.
			this.awardLinereplyDao.physicalDeleteThreadByItemThread(loopingItem.getItemId());

			//게시글 추천 정보를 삭제한다.
			this.awardRecommendDao.removeByItemId(loopingItem.getItemId());

			//게시글 조회 정보를 삭제한다.
			this.awardReferenceDao.removeByItemId(loopingItem.getItemId());

			//게시글을 삭제한다.
			this.awardItemDao.physicalDelete(loopingItem.getItemId());
		}

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardItemService#listReplayItemThreadByItemId(java.lang.String)
	 */
	public List<AwardItem> listReplayItemThreadByItemId(String itemGroupId) {
		//게시물과 연관되는 답글 Thread를 가져와 게시물에 넣는다
		return this.awardItemDao.listReplayItemThreadByItemId(itemGroupId);
	}
	
	

	public void updateMailCount(String itemId) {
		
		this.awardItemDao.updateMailCount(itemId);
		
	}

	public void updateMblogCount(String itemId) {
		this.awardItemDao.updateMblogCount(itemId);
	}
	
	/**
	 * 게시물 이동
	 */
	public void moveAwardItem(String orgAwardId, String targetAwardId,
			String[] itemIds, User user) {

		try {
			Map<String, String> awardItemMap = new HashMap<String, String>();
			awardItemMap.put("orgAwardId", orgAwardId);
			awardItemMap.put("targetAwardId", targetAwardId);
			awardItemMap.put("updaterId", user.getUserId());
			awardItemMap.put("updaterName", user.getUserName());

			// List<String> itemIdList = StringUtil.getTokens(itemIds[0], ",");

			for (String itemId : itemIds) {

				awardItemMap.put("itemId", itemId);

				this.awardItemDao.updateAwardItemMove(awardItemMap);
				// 로그
				if (this.log.isDebugEnabled()) {
					this.log.debug("게시물 이동 : orgAwardId[" + orgAwardId
							+ "]targetAwardId[" + targetAwardId + "]itemId["
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

	public List<AwardItem> listRecentAwardItemPortlet(String awardId,  Integer count) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("awardId", awardId);
		parameter.put("count", count == null ? 10 : count);

		return this.awardItemDao.listRecentAwardItemPortlet(parameter);
	}
	
	/**
	 * 임시 저장 게시물 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<AwardItem> listTempSaveItemBySearchCondition(AwardItemSearchCondition searchCondition) {
		Integer count = this.awardItemDao.countTempSaveBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.listTempSaveBySearchCondition(searchCondition);

			if(awardItemList != null && awardItemList.size() > 0) {
				for(AwardItem awardItem : awardItemList) {
					List<Tag> tagList = this.tagService.listTagByItemId(awardItem.getItemId(),  TagConstants.ITEM_TYPE_BBS);
					awardItem.setTagList(tagList);
					
					awardItem.setAward(this.awardDao.get(awardItem.getAwardId()));
				}
			}

			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;
	}
	
	public int getMailWaitYn(String itemId){
		int mailWaitYn = this.awardItemDao.getMailWaitYn(itemId);
		return mailWaitYn;
	}
	
	public int getAwardItemCount(AwardItem awardItem){
		int itemCnt = this.awardItemDao.getAwardItemCount(awardItem);
		return itemCnt;
	}
	
	public int getHappyAwardItemCount(AwardItem awardItem){
		int itemCnt = this.awardItemDao.getHappyAwardItemCount(awardItem);
		return itemCnt;
	}
	
	public void updateMailWaitYn(String itemId) {
		
		this.awardItemDao.updateMailWaitYn(itemId);
		
	}
	
	public int getMailWaitTime(){
		int mailWaitTime = this.awardItemDao.getMailWaitTime();
		return mailWaitTime;
	}
	
	public int getMailWaitCount(){
		int mailWaitCount = this.awardItemDao.getMailWaitCount();
		return mailWaitCount;
	}
	
	/**
	 * 이전 게시글의 정보를 조회한다.
	 * 
	 * @param String awardId , String itemId
	 */
	public AwardItem readPrevAwardItem(String awardId, String itemId) {
		//게시물을 가져온다.
		String readflag = "prev";
		AwardItem awardItem = this.awardItemDao.getPrevItem(awardId, itemId, readflag);

		return awardItem;
	}
	
	/**
	 * 다음 게시글의 정보를 조회한다.
	 * 
	 * @param String awardId , String itemId
	 */
	public AwardItem readNextAwardItem(String awardId, String itemId) {
		//게시물을 가져온다.
		String readflag = "next";
		AwardItem awardItem = this.awardItemDao.getNextItem(awardId, itemId, readflag);

		return awardItem;
	}
	public SearchResult<AwardItem> getAwardMenuList(AwardItemSearchCondition searchCondition) {
		// TODO Auto-generated method stub
		Integer count = this.awardItemDao.countAwardMenuList(searchCondition);
		
		searchCondition.terminateSearchCondition(count);

		SearchResult<AwardItem> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AwardItem>(searchCondition);

		} else {

			List<AwardItem> awardItemList = this.awardItemDao.getAwardMenuList(searchCondition);
			List<AwardItem> presentAwardItemList = this.awardItemDao.getPresentAwardMenuList(searchCondition.getUserId());
			
			for(AwardItem awardItem : awardItemList) {
				for(AwardItem awardItem1 : presentAwardItemList) {
					if(awardItem.getAwardId().equals(awardItem1.getAwardId())) {
						awardItem.setTempSave("1");
					}
				}
			}
			
			searchResult = new SearchResult<AwardItem>(awardItemList, searchCondition);
		}

		return searchResult;
	}
	
	public void updateAwardMenuList(Map<String, Object> param) {
		
		//지우고,
		this.awardItemDao.deleteAwardMenuList((String)param.get("userId"));
		
		//집어넣는다.
		for(int i=0; i<((List<String>)param.get("itemIds")).size(); i++){
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("userId", param.get("userId"));
			param1.put("itemId", ((List<String>)param.get("itemIds")).get(i));
			this.awardItemDao.insertAwardMenuList(param1);
		}
	}
	
	public List<AwardItem> listCode(String grpCd){
		return awardItemDao.listCode(grpCd);
	}
	
	public boolean userAuthMgntYn(String userId) {
		int result = awardDao.userAuthMgntYn(userId);

		if (result > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
