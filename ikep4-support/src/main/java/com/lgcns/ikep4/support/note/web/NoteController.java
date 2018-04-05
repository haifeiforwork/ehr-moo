/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.model.NoteShare;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.service.NoteFolderService;
import com.lgcns.ikep4.support.note.service.NoteService;
import com.lgcns.ikep4.support.note.service.NoteShareService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.web.TreeNode;

/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageController.java 17335 2012-02-22 05:52:20Z yruyo $
 */
@Controller
@RequestMapping(value = "/support/note")
public class NoteController extends BaseController {

	@Autowired
	private ACLService aclService;

	@Autowired
	private UserService userService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteFolderService noteFolderService;

	@Autowired
	private NoteShareService noteShareService;

	@Autowired
	private LocaleCodeService lcodeService;

	@Autowired
	private UserConfigService userConfigService;

	public static final int NOTE_DEFALUT_PAGE_PER_RECORD = 10;

	public static final int PORTAL_NOTE_DEFALUT_PAGE_PER_RECORD = 10000;
	
	/**
	 * 노트 목록 조회 화면 컨트롤 메서드
	 *
	 * @param searchCondition 노트 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/noteView")
	public ModelAndView noteView(
			@RequestParam(value="folderId", required=false) String folderId,
			@RequestParam(value="noteId",  required=false) String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString
	) {
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		List<NoteFolder> noteUserFolder = noteFolderService.listByFolderRootId(portalId, user.getUserId());

		List<NoteFolder> noteSharedFolder = noteFolderService.listBySharedFolder(user, portalId);

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		searchCondition.setPortalId(portalId);
		searchCondition.setUserId(user.getUserId());
		// 모든 노트 목록 수
		searchCondition.setFolderId("A");
		SearchResult<Note> searchResult1 = this.noteService.listNoteBySearchCondition(searchCondition);
		int folderACnt = searchResult1.getRecordCount();
		// 중요 노트 목록 수
		searchCondition.setFolderId("I");
		SearchResult<Note> searchResult2 = this.noteService.listNoteBySearchCondition(searchCondition);
		int folderICnt = searchResult2.getRecordCount();

		ModelAndView modelAndView = new ModelAndView()
		.addObject("userFolderList", noteUserFolder)
		.addObject("sharedFolderList", noteSharedFolder)
		.addObject("folderId", folderId)
		.addObject("noteId", noteId)
		.addObject("folderACnt", folderACnt)
		.addObject("folderICnt", folderICnt)
		.addObject("user", user)
		.addObject("searchConditionString", searchConditionString);

		return modelAndView;
	}

	/**
	 * 노트 목록 조회 화면 컨트롤 메서드
	 * 
	 * @param searchCondition 노트 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listNoteView")
	public ModelAndView listNoteView(
			NoteSearchCondition searchCondition,
			AccessingResult accessResult, 
			@RequestParam(value = "paramFolderId", required = false) String paramFolderId,
			@RequestParam(value = "noteId", required = false) String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			HttpServletRequest request
	) {
		Long past = System.currentTimeMillis();
	
		String tempSearchConditionString = null;

		if (StringUtils.isEmpty(searchConditionString)) {
			tempSearchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition,
					"pageIndex", "searchColumn", "searchWord", "sortColumn", "sortType");
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition);
			tempSearchConditionString = searchConditionString;
		}
		
		if (paramFolderId != null || !StringUtils.isEmpty(paramFolderId)) {
			// 특정 폴더가 선택되어 왔을 경우 세팅
			searchCondition.setFolderId(paramFolderId);
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 폴더 관리 정보를 가져온다.
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		searchCondition.setPagePerRecord(NOTE_DEFALUT_PAGE_PER_RECORD);
		
		// 개인화 설정 정보를 불러온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
				"NO", portalId);
		
		Boolean createUserConfig = (userConfig != null);
		
		// 개인화 설정이 없으면 게시판설정의 페이지 정보를 저장한다.
		if (createUserConfig) {
			searchCondition.setLayoutType(userConfig.getLayout());
		}

		if (searchCondition.getLayoutType() == null) {
			// 설정 정보 없을 경우 갤러리형  뷰모드 설정
			searchCondition.setLayoutType("layoutVertical");
		}

		if (searchCondition.getSortColumn() == null) {
			// 정렬 컬럼 정보 없을 경우 등록일로 설정
			searchCondition.setSortColumn("REGIST_DATE");
		}
			
		if (searchCondition.getSortType() == null) {
			// 정렬 유형 정보 없을 경우 최신글로 설정
			searchCondition.setSortType("DESC");
		}

		searchCondition.setSortWord(searchCondition.getSortColumn() + ":" + searchCondition.getSortType());
		
		searchCondition.setUserId(user.getUserId());
		SearchResult<Note> searchResult = this.noteService.listNoteBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView()
				.addObject("noteFolder", noteFolder)
				.addObject("user", user)
				.addObject("userConfig", userConfig)
				.addObject("noteId", noteId)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult)
				.addObject("searchConditionString", tempSearchConditionString);

		Long present = System.currentTimeMillis();

		this.log.info("목록 갯수" + searchResult.getSearchCondition().getPagePerRecord() + "소모시간 : " + (present - past));

		return modelAndView;
	}

	/**
	 * ajax이용한 리스트 가져오기
	 * 
	 * @param searchCondition NoteSearchCondition 객체
	 * @return
	 */
	@RequestMapping("/listMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listMore(NoteSearchCondition searchCondition) {

		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setPagePerRecord(NOTE_DEFALUT_PAGE_PER_RECORD);
		if (searchCondition.getSortColumn() == null) {
			// 정렬 컬럼 정보 없을 경우 등록일로 설정
			searchCondition.setSortColumn("REGIST_DATE");
		}
			
		if (searchCondition.getSortType() == null) {
			// 정렬 유형 정보 없을 경우 최신글로 설정
			searchCondition.setSortType("DESC");
		}

		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setUserId(user.getUserId());

		// 폴더 관리 정보를 가져온다.
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		// 노트목록 정보를 가져온다.
		SearchResult<Note> searchResult = this.noteService.listNoteBySearchCondition(searchCondition);

		// 이동할 뷰를 선택한다.
		String viewName = "";
		if ("layoutHorizental".equals(searchCondition.getLayoutType())) {
			viewName = "support/note/listTypeNoteView";
		} else if ("layoutVertical".equals(searchCondition.getLayoutType())) {
			viewName = "support/note/summaryTypeNoteView";
		}
		ModelAndView mav = new ModelAndView(viewName);		
		try {
			mav.addObject("noteFolder", noteFolder);
			mav.addObject("searchResult", searchResult);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	/**
	 * 노트 상세조회 화면 컨트롤 메서드
	 * 
	 * @param noteId 상세조회 대상 노트 ID
	 * @param layoutType the layout type
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/readNoteView")
	public ModelAndView readNoteView(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal", required = false) String layoutType,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = (User) getRequestAttribute("ikep.user");

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		Note note = this.noteService.readNote(noteId);

		if (note == null) {			
			throw new IKEP4AuthorizedException(messageSource, "message.support.common.note.deleteContents");
		}

		List<NoteFolder> noteUserFolderList = noteFolderService.listByMoveUserFolder(user.getPortalId(), user.getUserId(), note.getFolderId());

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		// 이동할 뷰를 선택한다.
		String viewName = "";
		if ("layoutHorizental".equals(layoutType)) {
			viewName = "support/note/readNoteFrameView";
		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "support/note/readNoteFrameView";
		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "support/note/readNoteView";
		}
		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}
		
		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("note", note)
				.addObject("noteFolder", noteFolder)
				.addObject("folderList", noteUserFolderList)
				.addObject("layoutType", layoutType)
				.addObject("user", user)
				.addObject("searchConditionString", searchConditionString)
				.addObject("fileDataListJson", fileDataListJson);

		return modelAndView;
	}

	/**
	 * 노트 생성 화면 컨트롤 메서드
	 * 
	 * @param folderId 폴더 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createNoteView")
	public ModelAndView createNoteView(
			@RequestParam("folderId") String folderId,
			@RequestParam("groupType") String groupType) {

		User user = (User) getRequestAttribute("ikep.user");

		Note note = new Note();
		note.setFolderId(folderId);

		List<NoteFolder> noteUserFolderList = noteFolderService.listByUserFolder(user.getPortalId(), user.getUserId());

		if (groupType.equals("S")) {
			folderId = "A";
		}
		
		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView modelAndView = new ModelAndView()
				.addObject("noteFolder", noteFolder)
				.addObject("note", note)
				.addObject("user", user)
				.addObject("useActiveX", useActiveX)
				.addObject("folderList", noteUserFolderList);

		return modelAndView;
	}

	/**
	 * 노트 생성 처리 동기 컨트롤 메서드
	 * 
	 * @param note 노트 생성 화면에서 전달된 노트 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createNote")
	public ModelAndView createNote(
			@RequestParam("selFolderId") String selFolderId,
			Note note,
			AccessingResult accessResult, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");

		if (result.hasErrors()) {
			this.setErrorAttribute("note", note, result);
			return new ModelAndView("forward:/support/note/createNoteView.do?noteId="
					+ note.getNoteId());
		}

		note.setPortalId(user.getPortalId());

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		String noteId = this.noteService.createNote(note, user);
		
		status.setComplete();

		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId="+selFolderId+"&noteId="+noteId+"';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listNoteView.do")
				.addObject("folderId", selFolderId)
				.addObject("noteId", noteId);		
	}

	/**
	 * TODO 노트 생성 처리 AJAX Controller
	 * 
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "/createNoteAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createNoteAjax(Note note) {

		User user = (User) getRequestAttribute("ikep.user");
		
		note.setPortalId(user.getPortalId());
		note.setUserId(user.getUserId());
		
		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		String noteId = "";
		if (note.getNoteId()==null || StringUtil.isEmpty(note.getNoteId())) {
			noteId = this.noteService.createNote(note, user);
		} else {
			this.noteService.updateNote(note, user);
			noteId = note.getNoteId();
		}
		
		return noteId;
	}
	
	/**
	 * 노트 수정 화면 컨트롤 메서드
	 * 
	 * @param noteId 수정대상 노트 ID
	 * @return ModelAndView
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/updateNoteView")
	public ModelAndView updateNoteView(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {

		User user = (User) getRequestAttribute("ikep.user");

		Note note = this.noteService.readNote(noteId);

		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView modelAndView = new ModelAndView()
				.addObject("noteFolder", noteFolder)
				.addObject("note", note)
				.addObject("user", user)
				.addObject("searchConditionString", searchConditionString)
				.addObject("useActiveX", useActiveX)
				.addObject("fileDataListJson", fileDataListJson)
				.addObject("folderList", noteUserFolderList);

		return modelAndView;
	}

	/**
	 * 노트 수정 처리 동기 컨트롤 메서드
	 * 
	 * @param note 노트 수정 화면에서 전달된 노트 객체 모델 클래스
	 * @param result the result
	 * @param status the status
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateNote")
	public ModelAndView updateNote(
			@RequestParam("selFolderId") String selFolderId,
			Note note,
			AccessingResult accessResult, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");

		if (result.hasErrors()) {
			this.setErrorAttribute("note", note, result);
			return new ModelAndView("forward:/support/note/updateNoteView.do?noteId="
					+ note.getNoteId());
		}

		// 포탈아이디 넣기 TagService에서 필요로 함
		note.setPortalId(user.getPortalId());
		note.setUserId(user.getUserId());

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.updateNote(note, user);
		
		status.setComplete();

		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId="+selFolderId+"&noteId="+note.getNoteId()+"&searchConditionString="+note.getSearchConditionString()+"';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listNoteView.do")
				.addObject("folderId", selFolderId)
				.addObject("noteId", note.getNoteId())
				.addObject("searchConditionString", note.getSearchConditionString());
	}

	/**
	 * TODO 선택한 노트 삭제 처리 AJAX Controller
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "logicalDeleteNoteAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String logicalDeleteNoteAjax(@RequestParam("noteId") String noteId) {

		User user = (User) getRequestAttribute("ikep.user");
		
		Note note = this.noteService.readNoteMasterInfo(noteId);

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.logicalDeleteNote(note);

		return "success";
	}
	
	/**
	 * 노트 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param noteId 삭제 대상 노트ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/logicalDeleteNote")
	public ModelAndView logicalDeleteNote(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");
		
		Note note = this.noteService.readNoteMasterInfo(noteId);

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.logicalDeleteNote(note);

		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId="+folderId+"&searchConditionString="+note.getSearchConditionString()+"';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listNoteView.do")
				.addObject("folderId", folderId)
				.addObject("searchConditionString", searchConditionString);
	}

	/**
	 * TODO 선택한 노트 복원 처리 AJAX Controller
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "restorationNoteAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String restorationNoteAjax(@RequestParam("noteId") String noteId) {

		User user = (User) getRequestAttribute("ikep.user");

		Note note = this.noteService.readNoteMasterInfo(noteId);

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.restorationNote(note, user);

		return "success";
	}
	
	/**
	 * 노트 삭제 복원 처리 동기 컨트롤 메서드
	 * 
	 * @param noteId 복원 대상 노트ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/restorationNote")
	public ModelAndView restorationNote(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");
		
		Note note = this.noteService.readNoteMasterInfo(noteId);

		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.restorationNote(note, user);

		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId="+folderId+"&searchConditionString="+searchConditionString+"';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listNoteView.do")
				.addObject("folderId", folderId)
				.addObject("searchConditionString", searchConditionString);
	}

	/**
	 * TODO 선택한 노트 완전 삭제 처리 AJAX Controller
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "physicalDeleteNoteAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String physicalDeleteNoteAjax(@RequestParam("noteId") String noteId) {

		Note note = this.noteService.readNoteMasterInfo(noteId);

		this.noteService.physicalDeleteNote(note);

		return "success";
	}
	
	/**
	 * 노트 완전 삭제 처리 동기 컨트롤 메서드
	 * 
	 * @param noteId 삭제 대상노트ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/physicalDeleteNote")
	public ModelAndView physicalDeleteNote(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			HttpServletRequest request, HttpServletResponse response) {
		
		Note note = this.noteService.readNoteMasterInfo(noteId);

		this.noteService.physicalDeleteNote(note);

		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId="+folderId+"&searchConditionString="+searchConditionString+"';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listNoteView.do")
				.addObject("folderId", folderId)
				.addObject("searchConditionString", searchConditionString);
	}

	/**
	 * TODO 선택한 노트 중요 처리 AJAX Controller
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "/moveFolderAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String moveFolderAjax(
			@RequestParam("folderId") String folderId,
			@RequestParam("noteId") String noteId) {

		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다.
		Note note = this.noteService.readNote(noteId);
		if (note == null) {			
			throw new IKEP4AuthorizedException(messageSource, "message.support.common.note.deleteContents");
		}

		note.setFolderId(folderId);
		note.setUserId(user.getUserId());
		
		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.moveFolder(note);
		
		return "success";
	}
	
	/**
	 * TODO 선택한 노트 중요 처리 AJAX Controller
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "checkImportant")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String checkImportant(@RequestParam("noteId") String noteId) {

		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다.
		Note note = this.noteService.readNote(noteId);
		if (note == null) {			
			throw new IKEP4AuthorizedException(messageSource, "message.support.common.note.deleteContents");
		}

		note.setUserId(user.getUserId());
		
		ModelBeanUtil.bindRegisterInfo(note, user.getUserId(), user.getUserName());

		this.noteService.checkPriority(note);
		
		return "success";
	}

	/**
	 * 노트 공유 화면
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/shareNoteForm")
	public ModelAndView shareNoteForm(HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		Map<String, String> parameterMap = new HashMap<String, String>();

		for (Object key : request.getParameterMap().keySet()) {
			parameterMap.put(String.valueOf(key), request.getParameter(String.valueOf(key)));
		}

		String shareType = String.valueOf(parameterMap.get("shareType"));

		List<NoteShare> workspaceList = new ArrayList<NoteShare>();
		List<NoteShare> boardList = new ArrayList<NoteShare>();
		// 공유 대상이 '게시판'일 경우
		if (shareType.equals("B")) {
			String boardRootId = "0";
			Boolean isSystemAdmin = this.isSystemAdmin("BBS", user);			
			
			if(isSystemAdmin) {
				List<NoteShare> _boardList = this.noteShareService.boardListByBBS(boardRootId, portalId);
				
				for(NoteShare board : _boardList) {
					if("0".equals(board.getBoardType())){
						boardList.add(board);
					}
				}
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("portalId", portalId);
				param.put("boardRootId", boardRootId);
				param.put("userId", user.getUserId());
				param.put("className", "BBS-Board");
				param.put("operationName", "WRITE");
				
				boardList = this.noteShareService.boardListByBBSPer(param);
			}

		// 공유 대상이 '워크스페이스'일 경우
		} else if (shareType.equals("W")) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				map.put("portalId", portalId);
				map.put("memberId", user.getUserId());
				
				workspaceList = noteShareService.myCollaborationListByWorkspace(map);
			} catch (Exception ex) {
				throw new IKEP4AjaxException("code", ex);
			}

		// 공유 대상이 '전사공유지식'일 경우
		} else if (shareType.equals("K")) {
			String boardRootId = "0";
			Boolean isSystemAdmin = this.isSystemAdmin("CK", user);
						
			if(isSystemAdmin) {
				List<NoteShare> _boardList = this.noteShareService.boardListByKnowledge(boardRootId, portalId);
				
				for(NoteShare board : _boardList) {
					if("0".equals(board.getBoardType())){
						boardList.add(board);
					}
				}
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("portalId", portalId);
				param.put("boardRootId", boardRootId);
				param.put("userId", user.getUserId());
				param.put("className", "CK");
				param.put("operationName", "WRITE");
				
				boardList = this.noteShareService.boardListByKnowledgePer(param);
			}

		}
		
		ModelAndView mav = new ModelAndView("support/note/shareNoteForm");

		mav.addObject("parameterMap", parameterMap);
		mav.addObject("shareType", shareType);
		mav.addObject("workspaceList", workspaceList);
		mav.addObject("boardList", boardList);

		return mav;
	}

	/**
	 * ajax이용해 Workspage에 대항하는 Board 리스트 가져오기
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping("/shareNoteBoardList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<NoteShare> shareNoteBoardList(String workspaceId) {

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		String boardRootId = "0";
		Boolean isSystemAdmin = this.isSystemAdmin("Collaboration", user);

		List<NoteShare> boardList = new ArrayList<NoteShare>();
		if(isSystemAdmin) {
			List<NoteShare> _boardList = this.noteShareService.boardListByWorkspace(boardRootId, portalId, workspaceId);
			
			for(NoteShare board : _boardList) {
				if("0".equals(board.getBoardType())){
					boardList.add(board);
				}
			}
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("portalId", portalId);
			param.put("boardRootId", boardRootId);
			param.put("userId", user.getUserId());
			param.put("workspaceId", workspaceId);
			param.put("className", "Coll-BD");
			param.put("operationName", "WRITE");
			
			boardList = this.noteShareService.boardListByWorkspacePer(param);
		}
		
		return boardList;
	}

	/**
	 * TODO 노트 공유 처리 AJAX Controller
	 * 
	 * @param model
	 * @param request
	 * @return @
	 */
	@RequestMapping("/shareNoteAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String shareNote(String targetId, HttpServletRequest request) {
		
		User user = (User) getRequestAttribute("ikep.user");

		String itemId = "";
		
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();

			for (Object key : request.getParameterMap().keySet()) {
				parameterMap.put(String.valueOf(key), request.getParameter(String.valueOf(key)));
			}

			String shareType = String.valueOf(parameterMap.get("shareType"));
			String noteId = String.valueOf(parameterMap.get("noteId"));
			
			// 노트 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
			Note note = this.noteService.readNote(noteId);

			NoteShare noteShare = new NoteShare();

			noteShare.setPortalId(user.getPortalId());
			noteShare.setBoardId(String.valueOf(parameterMap.get("boardId")));
			noteShare.setTag(String.valueOf(parameterMap.get("tag")));

			ModelBeanUtil.bindRegisterInfo(noteShare, user.getUserId(), user.getUserName());

			// 공유 대상이 '게시판'일 경우
			if (shareType.equals("B")) {

				// 타임존 처리
				Date clientNow = timeZoneSupportService.convertTimeZone();
				Date today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Date startDate = today;
				Date endDate = DateUtils.addYears(today, 1);
				
				startDate = this.timeZoneSupportService.convertServerTimeZone(startDate);
				endDate = this.timeZoneSupportService.convertServerTimeZone(endDate);

				noteShare.setStartDate(startDate);
				noteShare.setEndDate(endDate);

				itemId = this.noteShareService.createBoardItemForBBS(note, noteShare, user);							
				
			// 공유 대상이 '워크스페이스'일 경우
			} else if (shareType.equals("W")) {
				noteShare.setWorkspaceId(String.valueOf(parameterMap.get("workspaceId")));
				noteShare.setWorkspaceName(String.valueOf(parameterMap.get("workspaceName")));
				noteShare.setItemType("0");
				
				itemId = this.noteShareService.createBoardItemForWS(note, noteShare, user);

			// 공유 대상이 '전사공유지식'일 경우
			} else if (shareType.equals("K")) {

				itemId = this.noteShareService.createBoardItemForCK(note, noteShare, user);
				
			}
		} catch (Exception e) {
			
			return itemId;
		}

		return itemId;
	}

	/**
	 * 개인보관함 트리 조회
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listFolderView")
	public ModelAndView listFolderView() {
		
		User user = (User) getRequestAttribute("ikep.user");

		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		return new ModelAndView()
					.addObject("folderList", noteUserFolderList)
					.addObject("viewCode", "F");
	}

	/**
	 * 폴더 목록 조회 화면 컨트롤 메서드
	 *
	 * @param folderId 폴더 Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listChildrenFolder")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildrenFolder( @RequestParam(value="folderId") String folderId) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		User user = (User) getRequestAttribute("ikep.user");

		try {
			List<NoteFolder> folderList = this.noteFolderService.listChildrenFolder(folderId, user.getPortalId(), user.getUserId());
			
			for(NoteFolder foteFolder : folderList) {
								
				treeNodeList.add( new TreeNode(foteFolder.getFolderId(), null, foteFolder.getFolderName(), "", (foteFolder.getHasChildren() > 0 ? "closed" : "leaf"), foteFolder));
			}

		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}


		return treeNodeList;
	}

	/**
	 * 폴더 상세조회 화면 컨트롤 메서드
	 *
	 * @param folderId 폴더 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readFolderView")
	public ModelAndView readFolderView(
			@RequestParam(value="folderId") String folderId) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);
		return new ModelAndView().addObject("noteFolder", noteFolder);
	}

	/**
	 * 폴더 생성 화면 컨트롤 메서드
	 *
	 * @param folderParentId 부모 게시판 ID
	 * @param folderType 폴터 타입 ( 0 : 폴더 , 1 : 폴더 카테고리 구분용 Dummy 폴더)
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/createFolderView")
	public ModelAndView createFolderView(
			@RequestParam(value="folderParentId") String folderParentId,
			@RequestParam(value="folderType") Integer folderType) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteFolder noteFolder = new NoteFolder();

		noteFolder.setFolderType(folderType);
		noteFolder.setFolderParentId(folderParentId);
		noteFolder.setPortalId(user.getPortalId());

		return new ModelAndView().addObject("noteFolder", noteFolder);
	}

	/**
	 * 폴더 수정 화면 컨트롤 메서드
	 *
	 * @param folderId 폴더 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateFolderView")
	public ModelAndView updateBoardView(@RequestParam(value="folderId") String folderId) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);

		return new ModelAndView().addObject("noteFolder", noteFolder);
	}

	@RequestMapping(value = "/existFolderName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean existFolderName(SessionStatus status
												, @RequestParam(value = "folderName", required = false) String folderName
												, @RequestParam(value = "folderId", required = false) String folderId) {

		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("folderName", folderName);
		map.put("folderId", folderId);
		map.put("userId", user.getUserId());

		boolean existDoc = false;
		try {
			existDoc = noteFolderService.existFolderName(map);
			
			status.setComplete();	
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return existDoc;
	}

	@RequestMapping(value = "/createFolder")
	public ModelAndView createFolder(NoteFolder noteFolder, HttpServletRequest request, HttpServletResponse response) {

		User user = (User) getRequestAttribute("ikep.user");

		noteFolder.setPortalId(user.getPortalId());
		noteFolder.setUserId(user.getUserId());

		ModelBeanUtil.bindRegisterInfo(noteFolder, user.getUserId(), user.getUserName());

		if (StringUtil.isEmpty(noteFolder.getFolderId())) {
			this.noteFolderService.createFolder(noteFolder);			
		} else {
			this.noteFolderService.updateFolder(noteFolder);
		}
		
		try {
			String contextRoot = request.getContextPath();
			response.setContentType("text/html");
			response.getWriter().print("<script type='text/javascript'>parent.location.href = '"+contextRoot+"/support/note/noteView.do?folderId=F';</script>");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/support/note/listFolderView.do");
	}

	/**
	 * TODO 폴더 생성 처리 AJAX Controller
	 * 
	 * @param noteFolder
	 * @return
	 */
	@RequestMapping(value = "/createFolderAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createFolderAjax(NoteFolder noteFolder) {

		User user = (User) getRequestAttribute("ikep.user");

		noteFolder.setPortalId(user.getPortalId());
		noteFolder.setUserId(user.getUserId());

		ModelBeanUtil.bindRegisterInfo(noteFolder, user.getUserId(), user.getUserName());

		if (StringUtil.isEmpty(noteFolder.getFolderId())) {
			this.noteFolderService.createFolder(noteFolder);			
		} else {
			this.noteFolderService.updateFolder(noteFolder);
		}

		return "success";
	}

	/**
	 * 폴더 위치 이동 처리 비동기 컨트롤 메서드
	 *
	 * @param folderId 이동 폴더 ID
	 * @param folderParentId 이동 폴더의 부모 ID
	 * @param sortOrder 이동 위치
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateMoveFolder")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateMoveBoard(
			@RequestParam(value="folderId") String folderId, @RequestParam(value="folderParentId", required=false) String folderParentId, @RequestParam(value="sortOrder") Integer sortOrder) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteFolder noteFolder = new NoteFolder();

		noteFolder.setFolderId(folderId);
		noteFolder.setSortOrder(sortOrder);
		noteFolder.setFolderParentId(folderParentId);
		noteFolder.setPortalId(user.getPortalId());

		this.noteFolderService.updateFolderMove(noteFolder);
	}

	/**
	 * 폴더 이름 변경 수정 처리 비동기 컨트롤 메서드
	 *
	 * @param folderId 수정대상 폴더 ID
	 * @param folderName 수정대상 폴더 이름
	 */
	@RequestMapping(value = "/updateFolderName")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateFolderName(
			@RequestParam(value="folderId") String folderId, @RequestParam(value="folderName") String folderName) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);
			
		noteFolder.setFolderName(folderName);
		
		this.noteFolderService.updateFolder(noteFolder);
	}

	/**
	 * TODO 선택한  폴더 삭제 처리 AJAX Controller
	 * 
	 * @param folderId
	 * @return
	 */
	@RequestMapping(value = "deleteFolderAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String deleteFolderAjax(@RequestParam("folderId") String folderId) {

		// 폴더 삭제
		this.noteFolderService.physicalDeleteFolder(folderId);		

		return "success";
	}
	
	@RequestMapping(value = "/deleteFolder.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView deleteFolder(@RequestParam(value = "folderId", required = false) String folderId, SessionStatus status) {

		// 폴더 삭제
		this.noteFolderService.physicalDeleteFolder(folderId);		

		return new ModelAndView("redirect:/support/note/listFolderView.do");
	}

	/**
	 * 개인화 설정 레이아웃 모드 설정
	 * 
	 * @param noteId 노트 ID
	 * @param layout 레이아웃
	 */
	@RequestMapping(value = "/updateUserConfigLayout")
	public @ResponseBody
	void updateUserConfigLayout(@RequestParam("folderId") String folderId, @RequestParam("layoutType") String layoutType) {

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		try {
			// 개인화 설정 정보를 불러온다.
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(),
					"NO", portalId);

			if (userConfig == null) {
				userConfig = new UserConfig();
				// 개인화 설정 정보를 저장한다.
				userConfig.setLayout(layoutType);
				userConfig.setPortalId(portalId);
				userConfig.setUserId(user.getUserId());
				userConfig.setModuleId("NO");

				this.userConfigService.createUserConfig(userConfig);
			} else {
				userConfig.setLayout(layoutType);
				
				this.userConfigService.updateUserConfig(userConfig);
			}
			
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
	}

	// 노트 링크
	@RequestMapping(value = "/portalNote.do")
	public ModelAndView portalNote() {
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		List<NoteFolder> noteUserFolder = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

//		List<NoteFolder> noteSharedFolder = noteFolderService.listBySharedFolder(user, portalId);

		ModelAndView mav = new ModelAndView("support/note/portalNote");
		mav.addObject("userFolderList", noteUserFolder);
//		mav.addObject("sharedFolderList", noteSharedFolder);
		
		return mav;
	}

	// 노트 목록
	@RequestMapping(value = "/portalNoteList.do")
	public ModelAndView portalNoteSearch(String folderId, String searchType, String searchText, String sortColumn, String sortType) {

		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
				
		searchCondition.setPagePerRecord(PORTAL_NOTE_DEFALUT_PAGE_PER_RECORD);
		if (sortColumn == null || StringUtil.isEmpty(sortColumn)) {
			// 정렬 컬럼 정보 없을 경우 등록일로 설정
			searchCondition.setSortColumn("REGIST_DATE");
		} else {
			searchCondition.setSortColumn(sortColumn);
		}			
		if (sortType == null || StringUtil.isEmpty(sortType)) {
			// 정렬 유형 정보 없을 경우 최신글로 설정
			searchCondition.setSortType("DESC");
		} else {
			searchCondition.setSortType(sortType);
		}

		searchCondition.setSortWord(searchCondition.getSortColumn() + ":" + searchCondition.getSortType());
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setUserId(user.getUserId());
		searchCondition.setFolderId(folderId);
		searchCondition.setSearchType(searchType);
		searchCondition.setSearchWord(searchText);

		// 폴더 관리 정보를 가져온다.
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);
		
		// 노트 목록을 가져온다.
		SearchResult<Note> searchResult = this.noteService.listNoteBySearchCondition(searchCondition);


		ModelAndView mav = new ModelAndView("support/note/portalNoteList");		
		mav.addObject("noteFolder", noteFolder);
		mav.addObject("portalNoteList", searchResult.getEntity());
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		return mav;
	}

	// 노트 상세
	@RequestMapping(value = "/portalNoteView.do")
	public ModelAndView portalNoteView(String noteId)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		Note note = this.noteService.readNote(noteId);

		List<NoteFolder> noteUserFolderList = noteFolderService.listByMoveUserFolder(user.getPortalId(), user.getUserId(), note.getFolderId());

		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}
		
		ModelAndView mav = new ModelAndView("support/note/portalNoteView");
		mav.addObject("note", note);
		mav.addObject("folderList", noteUserFolderList);
		mav.addObject("fileDataListJson", fileDataListJson);

		return mav;
	}

	// 노트 등록
	@RequestMapping(value = "/portalNoteCreate.do")
	public ModelAndView portalNoteCreate(String folderId)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setUserId(user.getUserId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);
		
		// 노트 정보를 가져온다.
		Note note = new Note();
		note.setFolderId(folderId);
		note.setFolderName(noteFolder.getFolderName());

		// 폴더 정보를 가져온다.
		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView mav = new ModelAndView("support/note/portalNoteCreate");
		mav.addObject("viewMode", "C");
		mav.addObject("note", note);
		mav.addObject("user", user);
		mav.addObject("useActiveX", useActiveX);
		mav.addObject("folderList", noteUserFolderList);

		return mav;
	}
	
	// 노트 수정
	@RequestMapping(value = "/portalNoteUpdate.do")
	public ModelAndView portalNoteUpdate(String noteId)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		Note note = this.noteService.readNote(noteId);

		// 폴더 정보를 가져온다.
		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView mav = new ModelAndView("support/note/portalNoteCreate");
		mav.addObject("viewMode", "U");
		mav.addObject("note", note);
		mav.addObject("user", user);
		mav.addObject("useActiveX", useActiveX);
		mav.addObject("fileDataListJson", fileDataListJson);
		mav.addObject("folderList", noteUserFolderList);

		return mav;
	}

	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(String type, User user) {
		return this.aclService.isSystemAdmin(type, user.getUserId());
	}
	
	// 노트 상세
	@RequestMapping(value = "/webDiaryNoteView.do")
	public ModelAndView webDiaryNoteView(String noteId)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		Note note = this.noteService.readNote(noteId);

		List<NoteFolder> noteUserFolderList = noteFolderService.listByMoveUserFolder(user.getPortalId(), user.getUserId(), note.getFolderId());

		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}
		
		ModelAndView mav = new ModelAndView("lightpack/planner/webdiary/webDiaryNoteView");
		mav.addObject("note", note);
		mav.addObject("folderList", noteUserFolderList);
		mav.addObject("fileDataListJson", fileDataListJson);

		return mav;
	}

	// 노트 등록
	@RequestMapping(value = "/webDiaryNoteCreate.do")
	public ModelAndView webDiaryNoteCreate(String folderId)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = (User) getRequestAttribute("ikep.user");

		NoteSearchCondition searchCondition = new NoteSearchCondition();
		searchCondition.setPortalId(user.getPortalId());
		searchCondition.setUserId(user.getUserId());
		searchCondition.setFolderId(folderId);
		
		NoteFolder noteFolder = this.noteFolderService.readFolder(searchCondition);
		
		// 노트 정보를 가져온다.
		Note note = new Note();
		note.setFolderId(folderId);
		note.setFolderName(noteFolder.getFolderName());

		// 폴더 정보를 가져온다.
		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView mav = new ModelAndView("lightpack/planner/webdiary/webDiaryNoteCreate");
		mav.addObject("viewMode", "C");
		mav.addObject("note", note);
		mav.addObject("user", user);
		mav.addObject("useActiveX", useActiveX);
		mav.addObject("folderList", noteUserFolderList);

		return mav;
	}
	
	// 노트 수정
	@RequestMapping(value = "/webDiaryNoteUpdate.do")
	public ModelAndView webDiaryNoteUpdate(String noteId)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = (User) getRequestAttribute("ikep.user");

		// 노트 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
		Note note = this.noteService.readNote(noteId);

		// 폴더 정보를 가져온다.
		List<NoteFolder> noteUserFolderList = noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());

		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = null;

		if (note.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(note.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		ModelAndView mav = new ModelAndView("lightpack/planner/webdiary/webDiaryNoteCreate");
		mav.addObject("viewMode", "U");
		mav.addObject("note", note);
		mav.addObject("user", user);
		mav.addObject("useActiveX", useActiveX);
		mav.addObject("fileDataListJson", fileDataListJson);
		mav.addObject("folderList", noteUserFolderList);

		return mav;
	}

	
}
