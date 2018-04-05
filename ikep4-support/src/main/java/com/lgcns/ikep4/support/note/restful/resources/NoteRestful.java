package com.lgcns.ikep4.support.note.restful.resources;


import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.restful.model.Root;
import com.lgcns.ikep4.support.note.restful.model.NoteParam;
import com.lgcns.ikep4.support.note.restful.model.UserFolderList;
import com.lgcns.ikep4.support.note.restful.model.UserFolderListBody;
import com.lgcns.ikep4.support.note.restful.model.UserFolderListReturnData;
import com.lgcns.ikep4.support.note.restful.model.ShareFolderList;
import com.lgcns.ikep4.support.note.restful.model.ShareFolderListBody;
import com.lgcns.ikep4.support.note.restful.model.ShareFolderListReturnData;
import com.lgcns.ikep4.support.note.restful.model.NoteList;
import com.lgcns.ikep4.support.note.restful.model.NoteListBody;
import com.lgcns.ikep4.support.note.restful.model.NoteListReturnData0;
import com.lgcns.ikep4.support.note.restful.model.NoteListReturnData1;
import com.lgcns.ikep4.support.note.restful.model.NoteView;
import com.lgcns.ikep4.support.note.restful.model.NoteViewBody;
import com.lgcns.ikep4.support.note.restful.model.NoteViewReturnData0;
import com.lgcns.ikep4.support.note.restful.model.NoteViewReturnData1;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.service.NoteService;
import com.lgcns.ikep4.support.note.service.NoteFolderService;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.MultiPart;
import java.net.URLDecoder;

@Path("/note")
@Component
public class NoteRestful extends BaseController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private NoteFolderService noteFolderService;

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private CacheService cacheService;

	/**
	 * 01.개인폴더 리스트
	 */
	@POST 
	@Path("/retrieveUserFolderList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserFolderList retrieveUserFolderList(NoteParam noteParam) {

		UserFolderList result = new UserFolderList();		
		
		try {

			List<NoteFolder> sourceList = noteFolderService.listByFolderRootId(noteParam.getPortalId(), noteParam.getUserId());

			List<UserFolderListReturnData> returnDataList = new ArrayList<UserFolderListReturnData>();
			
			if (sourceList != null){
				for ( NoteFolder noteFolder : sourceList ) {
					UserFolderListReturnData returnData = new UserFolderListReturnData();
					
					returnData.setFolderId			(noteFolder.getFolderId());
					returnData.setFolderName		(noteFolder.getFolderName());
					returnData.setFolderColor		(noteFolder.getColor());
					returnData.setSiblingOrder		(noteFolder.getSortOrder());
	
					returnDataList.add(returnData);
				}
			}
			
			
			Head head = new Head(0, Response.Status.OK.toString());
			UserFolderListBody body = new UserFolderListBody(returnDataList);
			result = new UserFolderList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new UserFolderList(head, null);
		}
				
		return result;
	}

	/**
	 * 02.공유폴더 리스트
	 */
	@POST 
	@Path("/retrieveShareFolderList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ShareFolderList retrieveShareFolderList(NoteParam noteParam) {

		User user = userDao.get(noteParam.getUserId());
		
		ShareFolderList result = new ShareFolderList();	
		
		try {

			List<NoteFolder> sourceList = noteFolderService.listBySharedFolder(user, noteParam.getPortalId());

			List<ShareFolderListReturnData> returnDataList = new ArrayList<ShareFolderListReturnData>();
			
			if (sourceList != null){
				for ( NoteFolder noteFolder : sourceList ) {
					ShareFolderListReturnData returnData = new ShareFolderListReturnData();
					
					returnData.setFolderId			(noteFolder.getFolderId());
					returnData.setFolderName		(noteFolder.getFolderName());
					returnData.setSiblingOrder		(noteFolder.getSortOrder());
					returnData.setShareUserId		(noteFolder.getUserId());
					returnData.setShareUserName		(noteFolder.getUserName());
					returnData.setShareUserDept		(noteFolder.getUserTeamName());
					returnData.setShareUserTitle	(noteFolder.getUserTitleName());
	
					returnDataList.add(returnData);
				}
			}
			
			
			Head head = new Head(0, Response.Status.OK.toString());
			ShareFolderListBody body = new ShareFolderListBody(returnDataList);
			result = new ShareFolderList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new ShareFolderList(head, null);
		}
				
		return result;
	}
	
	/**
	 * 03.노트 리스트
	 */
	@POST 
	@Path("/retrieveNoteList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public NoteList retrieveNoteList( NoteParam noteParam ) {
		
		NoteList result = new NoteList();
				
		try {
			
			NoteSearchCondition searchCondition = new NoteSearchCondition();
			
			searchCondition.setPortalId(noteParam.getPortalId());
			searchCondition.setUserId(noteParam.getUserId());		
			searchCondition.setFolderId(noteParam.getFolderId());
			searchCondition.setPagePerRecord(noteParam.getListSize());
			searchCondition.setPageIndex(noteParam.getPage());
			searchCondition.setStartRowIndex(noteParam.getPage()-1);
			if ( !StringUtil.isEmpty(noteParam.getSearchWord()) ) {
				searchCondition.setSearchWord(noteParam.getSearchWord());
			}
			
			if ( !StringUtil.isEmpty(noteParam.getSearchType()) ) {
				searchCondition.setSearchType(noteParam.getSearchType());
			}
			
			// 0. 폴더 정보 가져오기
			if (!noteParam.getFolderId().equals("A") && !noteParam.getFolderId().equals("I")) {
				// 전체 폴더 리스트 중 첫번째 폴더를 가져온다.
				if ( StringUtil.isEmpty(noteParam.getFolderId()) ) { 
					List<NoteFolder> fList = noteFolderService.listByFolderRootId(noteParam.getPortalId(), noteParam.getUserId());
					if( fList != null){
						for ( NoteFolder n : fList ) {
							if ( "0".equals(n.getFolderType()) ) {
								searchCondition.setFolderId(n.getFolderId());
								break;
							}
						}
					}
				} else {
					searchCondition.setFolderId(noteParam.getFolderId());
				}
			}

			if (searchCondition.getSortColumn() == null) {
				// 정렬 컬럼 정보 없을 경우 등록일로 설정
				searchCondition.setSortColumn("REGIST_DATE");
			}
				
			if (searchCondition.getSortType() == null) {
				// 정렬 유형 정보 없을 경우 최신글로 설정
				searchCondition.setSortType("DESC");
			}

			// 1. 노트 목록 정보 가져오기
			SearchResult<Note> searchResult = this.noteService.listNoteBySearchCondition(searchCondition);

			// 3. 데이터 가공 및 JAXB 처리
			List<NoteListReturnData0> returnData0 = new ArrayList<NoteListReturnData0>();
			
			if(searchResult.getEntity() != null){
				for ( Note note : searchResult.getEntity() ) {
					
					NoteListReturnData0 returnItem = new NoteListReturnData0();

					returnItem.setFolderId(StringUtil.nvl(note.getFolderId(), ""));
					returnItem.setFolderName(StringUtil.nvl(note.getFolderName(), ""));
					returnItem.setNoteId(StringUtil.nvl(note.getNoteId(), ""));
					returnItem.setNoteTitle(StringUtil.nvl(note.getTitle(), ""));
					returnItem.setNoteContent(StringUtil.nvl(note.getContents().toString(), ""));
					if (note.getAttachFileCount() > 0 ) {
						returnItem.setIsAttach("1");
					} else {
						returnItem.setIsAttach("0");
					}
					returnItem.setIsImportant(StringUtil.nvl(note.getPriority().toString(), ""));
					returnItem.setRegUserId(StringUtil.nvl(note.getRegisterId(), ""));
					returnItem.setRegUserName(StringUtil.nvl(note.getRegisterName(), ""));
					returnItem.setRegUserDept(StringUtil.nvl(note.getUser().getTeamName(), "")); 
					returnItem.setRegUserTitle(StringUtil.nvl(note.getUser().getJobTitleName(), ""));
					returnItem.setRegDate(DateUtil.getFmtDateString(note.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
				
					returnData0.add(returnItem);
				}
			}

			// 1. 검색된 페이지 정보
			int pageCount = 0;
			if (searchCondition.getPageCount()!=null) {
				pageCount = searchCondition.getPageCount();
			}
			int recordCount = 0;
			if (searchResult.getRecordCount()!=null) {
				recordCount = searchResult.getRecordCount();
			}
			
			NoteListReturnData1 returnData1 = new NoteListReturnData1(pageCount, recordCount);
			
			Head head = new Head(0, Response.Status.OK.toString());
			NoteListBody body = new NoteListBody(returnData0, returnData1);
			result = new NoteList(head, body);
		
		} catch(Exception e) {//e.printStackTrace();
			Head head = new Head(1, "Error");
			result = new NoteList(head, null);
		}
		
		return result;
		
	}

	/**
	 * 04.노트 상세조회
	 */
	@POST 
	@Path("/retrieveNoteDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public NoteView retrieveNoteDetail( NoteParam noteParam ) {
		
		NoteView result = new NoteView();
		
		try {
			
			// 1. 데이터 가져오기
			
			// 노트 정보를 가져온다.
			Note note = this.noteService.readNote(noteParam.getNoteId());

			if (note == null || note.getNoteDelete() == 1) {
				Head head = new Head(1, Response.Status.NO_CONTENT.toString());
				result = new NoteView(head, null);
			} else {

				User user = userDao.get(noteParam.getUserId());
				RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
					
				//노트상세정보
				NoteViewReturnData0 returnData0 = new NoteViewReturnData0();
				returnData0.setFolderId(StringUtil.nvl(note.getFolderId(), ""));
				returnData0.setFolderName(StringUtil.nvl(note.getFolderName(), ""));
				returnData0.setNoteId(StringUtil.nvl(note.getNoteId(), ""));
				returnData0.setNoteTitle(StringUtil.nvl(note.getTitle(), ""));
				returnData0.setNoteContent(StringUtil.nvl(note.getContents().toString(), ""));
				returnData0.setIsImportant(StringUtil.nvl(note.getPriority().toString(), ""));
				returnData0.setRegUserId(StringUtil.nvl(note.getRegisterId(), ""));
				returnData0.setRegUserName(StringUtil.nvl(note.getRegisterName(), ""));
				returnData0.setRegUserDept(StringUtil.nvl(note.getUser().getTeamName(), "")); 
				returnData0.setRegUserTitle(StringUtil.nvl(note.getUser().getJobTitleName(), ""));
				returnData0.setRegDate(DateUtil.getFmtDateString(note.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
			
				
				List<NoteViewReturnData1> returnData1 = new ArrayList<NoteViewReturnData1>();
				if (note.getFileDataList() != null) {
					
					//properties파일에 설정되어 있는 정보로 첨부파일 다운로드 URL 생성
					String propUrl = "";
					Properties commonProp = PropertyLoader.loadProperties("/configuration/common-conf.properties");
					propUrl = commonProp.getProperty("ikep4.baseUrl");
					
					Properties fileprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
					propUrl += fileprop.getProperty("ikep4.support.fileupload.downloadurl");
				
					List<FileData> FileList = note.getFileDataList();
					
					if(FileList != null){
						for(FileData file : FileList){
							FileData fileData = fileService.read(file.getFileid());
							String fileUrl = propUrl + fileData.getFileId();
							
							NoteViewReturnData1 returnAttachData = new NoteViewReturnData1();
							returnAttachData.setAttachFileId(fileData.getFileId());
							returnAttachData.setAttachFileName(fileData.getFileRealName());
							returnAttachData.setAttachFileSize(fileData.getFileSize()+"");
							returnAttachData.setAttachFilePath(fileData.getFilePath());
							returnAttachData.setAttachFileUrl(fileUrl);
							returnAttachData.setAttachFileStoredName(fileData.getFileName());
							returnAttachData.setAttachTargetUrl(fileUrl);
							
							returnData1.add(returnAttachData);
						}
					}
				}
				
				Head head = new Head(0, Response.Status.OK.toString());
				NoteViewBody body = new NoteViewBody(returnData0, returnData1);
				result = new NoteView(head, body);
			}
		
		} catch(Exception e) {//e.printStackTrace();
			Head head = new Head(1, "Error");
			result = new NoteView(head, null);
		}
		
		return result;
		
	}

	/**
	 * 05.노트 등록 (첨부파일 포함)
	 */
	@POST 
	@Path("/insertNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root insertNote(MultiPart multipart) throws Exception {
		
		Root result = new Root();
		Note note = new Note();
		List<FileData> uploadList = new ArrayList<FileData>();
		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		
		try{
			List<BodyPart> bodypart = multipart.getBodyParts();

			//User 정보를 우선으로 설정해준다.
			for(BodyPart bp : bodypart) {
				
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				if("userId".equals(fdbodypart.getName())){
					user = userDao.get(fdbodypart.getValue());
			    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			    	
			    	break;
				}
			}
			
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
				
				//파일명이 없는것은 Content 로 간주 한다.
				if(disposition.getFileName() == null){
					String name = fdbodypart.getName();
				    String value = fdbodypart.getValue();

				    if("portalId".equals(name)) note.setPortalId(value);
				    if("userId".equals(name)) note.setUserId(value);
				    if("folderId".equals(name)) note.setFolderId(value);
				    if("noteTitle".equals(name)) note.setTitle(value);
				    if("noteContent".equals(name)) note.setContents(value);
				    if("isImportant".equals(name)) note.setPriority(Integer.parseInt(value));
				    
				//첨부파일 업로드
				}else{
					String fileName = URLDecoder.decode( new String(disposition.getFileName().getBytes("8859_1"),"utf-8"), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(fileService.uploadFile(fileName, is, user));
				    }
				}
			}

			note.setUser(user);
			note.setNoteDelete(0);
			
			if (uploadList != null && uploadList.size() > 0) {
				note.setFileDataList(uploadList);
				
				//FileLinkList 생성
				for(FileData fileData : uploadList){
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileData.getFileId());
					fileLink.setFileSize(fileData.getFileSize()+"");
					fileLink.setRegisterId(fileData.getRegisterId());
					fileLink.setRegisterName(fileData.getRegisterName());
					fileLink.setUpdaterId(fileData.getUpdaterId());
					fileLink.setUpdaterName(fileData.getUpdaterName());
					fileLink.setFlag("add");
					fileLinkList.add(fileLink);
				}
				note.setFileLinkList(fileLinkList);
			}
			
			ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());
			
			String noteId = null; 
			noteId = noteService.createNote(note,user);
			
			// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");
						
			if ( null != noteId && !StringUtil.isEmpty(noteId) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : Note create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {//e.printStackTrace();
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;

    }

	/**
	 * 06.노트 수정
	 */
	@POST 
	@Path("/updateNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root updateNote(MultiPart multipart) throws Exception {

		Root result = new Root();
		Note note = new Note();
		//List<FileData> uploadList = new ArrayList<FileData>();
		//List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		
		String noteId = null;
		try{
			List<BodyPart> bodypart = multipart.getBodyParts();

			//User 정보와 BoardItemId를 우선으로 설정해준다.
			for(BodyPart bp : bodypart) {
				
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				if("userId".equals(fdbodypart.getName())){
					user = userDao.get(fdbodypart.getValue());
			    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
				}
				
				if("noteId".equals(fdbodypart.getName())){
					note.setNoteId(fdbodypart.getValue()); 
					noteId = fdbodypart.getValue();
					
					if(!"".equals(noteId) && noteId != null){
						//이전의 첨부파일 삭제
						//this.fileService.removeItemFile(boardItemId);//Skip 처리
					}
				}
			}
			
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
				
				//파일명이 없는것은 Content 로 간주 한다.
				if(disposition.getFileName() == null){
					String name = fdbodypart.getName();
				    String value = fdbodypart.getValue();

				    if("portalId".equals(name)) note.setPortalId(value);
				    if("userId".equals(name)) note.setUserId(value);
				    if("folderId".equals(name)) note.setFolderId(value);
				    if("noteTitle".equals(name)) note.setTitle(value);
				    if("noteContent".equals(name)) note.setContents(value);
				    if("isImportant".equals(name)) note.setPriority(Integer.parseInt(value));
				    				    
				//첨부파일 업로드
				}else{
					//모바일용 파일처리 SKIP
					/*String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(this.fileService.uploadFile(fileName, is, user));
				    }*/
				}
			}

			note.setUser(user);
			note.setNoteDelete(0);
						
			//모바일용 파일처리 SKIP
			/*if (uploadList != null && uploadList.size() > 0) {
				note.setFileDataList(uploadList);
				
				//FileLinkList 생성
				for(FileData fileData : uploadList){
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileData.getFileId());
					fileLink.setFileSize(fileData.getFileSize()+"");
					fileLink.setRegisterId(fileData.getRegisterId());
					fileLink.setRegisterName(fileData.getRegisterName());
					fileLink.setUpdaterId(fileData.getUpdaterId());
					fileLink.setUpdaterName(fileData.getUpdaterName());
					fileLink.setFlag("add");
					fileLinkList.add(fileLink);
				}
				note.setFileLinkList(fileLinkList);
			}*/

			ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());
			
			this.noteService.updateNote(note, user);
			
			if ( null != noteId && !StringUtil.isEmpty(noteId) ) {
				Head head = new Head(0, "success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : noteId update failure.(NoteId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {//e.printStackTrace();
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;

    }

	/**
	 * 07.노트 삭제
	 */
	@POST 
	@Path("/deleteNote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteNote( NoteParam noteParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(noteParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			Note note = new Note();
			
			note.setPortalId(noteParam.getPortalId());
			note.setNoteId(noteParam.getNoteId());
			note.setUpdaterId(noteParam.getUserId());
				
			ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

			this.noteService.logicalDeleteNote(note);
			
			if ( null != noteParam.getNoteId() && !StringUtil.isEmpty(noteParam.getNoteId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : note delete failure.(NoteId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}

	/**
	 * 08.노트 중요여부 처리
	 */
	@POST 
	@Path("/updateNoteStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateNoteStatus( NoteParam noteParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(noteParam.getUserId());
		
		try {
		
			Note note = new Note();
			
			note.setPortalId(noteParam.getPortalId());
			note.setUserId(noteParam.getUserId());
			note.setNoteId(noteParam.getNoteId());
				
			ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

			if (noteParam.getIsImportant().equals("0")) {
				note.setPriority(1);
			} else if (noteParam.getIsImportant().equals("1")){
				note.setPriority(0);
			} else {
				Head head = new Head(1, Response.Status.NO_CONTENT.toString());
				result = new Root(head, null);
				
				return result;
			}			

			this.noteService.checkPriority(note);

			Head head = new Head(0, Response.Status.OK.toString());
			result = new Root(head, null);
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		return result;		
	}
	
}