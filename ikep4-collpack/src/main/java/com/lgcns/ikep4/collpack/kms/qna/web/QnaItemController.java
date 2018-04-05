package com.lgcns.ikep4.collpack.kms.qna.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.collpack.kms.board.model.BoardItem.CreateReplyItemGroup;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaCode;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaConfig;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaItem;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaRecommend;
import com.lgcns.ikep4.collpack.kms.qna.search.QnaItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.qna.service.QnaItemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.util.BrowserCheck;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


@Controller("kmsQnaItemController")
@RequestMapping(value = "/collpack/kms/qna")
@SessionAttributes("qnaItem")
public class QnaItemController extends BaseController {

//	private static final String COLLABORATION_MANAGER = "Collaboration";
	private static final String KMS_MANAGER = "Kms";

	@Autowired
	private QnaItemService qnaItemService;

//	@Autowired
//	private MemberService memberService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private UserThemeService userThemeService;

	/**
	 * Workspace 시스템 관리자 여부
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {

		return aclService.isSystemAdmin(KMS_MANAGER, user.getUserId());

	}

	/**
	 * 세션 사용자 정보
	 * 
	 * @return
	 */
	private User readUser() {
		return (User) getRequestAttribute("ikep.user");
	}

	/**
	 * NOTICE 게시물 리스트
	 * 
	 * @param workspaceId
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/listQnaItemView")
//	public ModelAndView listQnaItemView(String workspaceId, QnaItemSearchCondition searchCondition,
	public ModelAndView listQnaItemView(QnaItemSearchCondition searchCondition,
			BindingResult result, SessionStatus status) {
		User user = readUser();

//		if (searchCondition.getWorkspaceId() == null) {
//			searchCondition.setWorkspaceId(workspaceId);
//		}
		if (searchCondition.getViewMode() == null) {
			searchCondition.setViewMode("0");
		}
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}

		String viewName = null;
		SearchResult<QnaItem> searchResult = null;

		searchResult = this.qnaItemService.listQnaItemBySearchCondition(searchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("qnaCode", new QnaCode())
				.addObject("user", user)
//				.addObject("workspaceId", workspaceId)
//				.addObject("qnaPermission", getQnaPermission(user, workspaceId))
				.addObject("qnaPermission", getQnaPermission(user))
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult);

		return modelAndView;
	}
	
	@RequestMapping(value = "/createReplyQnaItemView")
	public ModelAndView createReplyQnaItemView(
			AccessingResult accessResult,
			@RequestParam(value = "searchConditionString", required = false) String searchConditionString,
			@RequestParam(value = "popupYn", defaultValue = "false", required = false) Boolean popupYn,
			@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {
		// 게시글 조회의 권한이 있는지 없는지 확인
		/*if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}*/

		User user = this.readUser();

		QnaItem qnaItem = null;

		if (this.getModelAttribute("qnaItem") == null) {
			qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId);
			qnaItem.setFileLinkList(null);
			qnaItem.setEditorFileLinkList(null);
			qnaItem.setFileDataList(null);
			qnaItem.setEditorFileDataList(null);

		} else {
			qnaItem = (QnaItem) this.getModelAttribute("qnaItem");

		}


		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (qnaItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(qnaItem.getFileDataList());
		}

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}

		return this.bindResult(new ModelAndView("collpack/kms/qna/createReplyQnaItemView").addObject("qnaItem", qnaItem).addObject("user", user)
				.addObject("searchConditionString", searchConditionString).addObject("popupYn", popupYn).addObject("ecmrole", ecmrole)
				.addObject("useActiveX", useActiveX).addObject("fileDataListJson", fileDataListJson));
	}
	
	@RequestMapping(value = "/createReplyQnaItem")
	public ModelAndView createReplyQnaItem(@ValidEx(groups = { CreateReplyItemGroup.class }) QnaItem boardItem,
			BindingResult result, SessionStatus status) {

		User user = this.readUser();
		
		ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

		QnaItem parent = this.qnaItemService.readQnaItem(user.getUserId(), boardItem.getItemParentId());

		// 포틀릿 아이디 넣기 TagService에서 필요로 함
		boardItem.setPortalId(user.getPortalId());

		String itemId = this.qnaItemService.createReplyBoardItem(boardItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/qna/readQnaItemView.do")
				.addObject("boardId", boardItem.getBoardId()).addObject("itemId", itemId)
				.addObject("searchConditionString", boardItem.getSearchConditionString())
				.addObject("popupYn", boardItem.getPopupYn());
	}
	
	@RequestMapping(value = "/qnaConfigView")
	public ModelAndView qnaConfigView( String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("collpack/kms/qna/qnaConfigView");
		
		//QnaConfig qnaconfig = qnaItemService.readQnaconfig();
		
		//if(qnaconfig == null) {
		//	qnaconfig = new QnaConfig();
		//}
		
		//mav.addObject("qnaconfig", qnaconfig);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}
	
	@RequestMapping(value = "/createQnaConfig.do")
	public @ResponseBody String createQnaConfig(@ModelAttribute QnaConfig qnaConfig, String editorAttach, HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			
			//qnaItemService.createQnaConfig(qnaConfig, fileList, editorAttach, user);
		} catch (Exception e) {
			if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}
		}
		
		return "success";
	}

	/**
	 * NOTICE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readQnaItemView")
//	public ModelAndView readQnaItemView(@RequestParam("itemId") String itemId,
//			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
//			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
//			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
//			JsonMappingException, IOException {
	public ModelAndView readQnaItemView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup) throws JsonGenerationException,
			JsonMappingException, IOException {		

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int qnaPermission = 0;
//		qnaPermission = getQnaPermission(user, workspaceId);
		qnaPermission = getQnaPermission(user);

		if (qnaPermission == 0) {
//			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId, workspaceId);
		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId);
 
		if (qnaItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.qnaItem.deletedItem");
		}
		
		SearchResult<QnaItem> searchResult = null;
		QnaItemSearchCondition searchCondition = new QnaItemSearchCondition();
		searchCondition.setItemId(itemId);
		searchResult = this.qnaItemService.listQnaSubItem(searchCondition);

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemView";
		}

//		if (StringUtil.isEmpty(workspaceId)) {
//			popup = true;
//		} else {
			popup = docPopup;
//		}

		if (popup) {
			viewName = "collpack/kms/qna/readQnaItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = qnaItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (qnaItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(qnaItem.getFileDataList());
		}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("qnaItem", qnaItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("qnaPermission", qnaPermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson)
				.addObject("searchResult", searchResult);;

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/updateRecommendCount")
	public @ResponseBody
	Integer updateRecommendCount(@RequestParam("itemId") String itemId) {
		Integer currentRecommendCount = null;

		try {
			User user = (User) this.getRequestAttribute("ikep.user");

			// 게시물 추천을 한다.
			QnaRecommend boardRecommend = new QnaRecommend();

			boardRecommend.setItemId(itemId);
			boardRecommend.setRegisterId(user.getUserId());

			// 이미 존재하면 -1를 리턴한다.
			if (this.qnaItemService.exsitRecommend(boardRecommend)) {
				currentRecommendCount = -1;
			} else {

				this.qnaItemService.updateRecommendCount(boardRecommend);
				
				QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId);

				currentRecommendCount = qnaItem.getRecommendCount();
				
				String title = qnaItem.getRegisterName() + " 님이 등록한 답글이 채택되었습니다.";
				String content = "<html><body>" + qnaItem.getRegisterName() + " 님이 등록한 답글이 채택되었습니다.<br/><br/>제목:"+qnaItem.getTitle()+"</body></html>";
				
				Mail mail = new Mail();
				Map dataMap = new HashMap();
				User userInfo = new User();
				List<HashMap> list = new ArrayList<HashMap>();

				userInfo = userService.read(qnaItem.getRegisterId());
				if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
					// 수신자
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", userInfo.getMail());
					map.put("name", userInfo.getUserName());
					list.add(map);
				}

				mail.setToEmailList(list);

				// 제목
				mail.setTitle(title);
				// 내용
				mail.setContent(content);
				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);

				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				String returnMsg = mailSendService.sendMail(mail, dataMap, user);
			}
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}

		return currentRecommendCount;

	}

	/**
	 * NOTICE 게시물 읽기
	 * 
	 * @param itemId
	 * @param layoutType
	 * @param docPopup
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/readQnaItemPopupView")
	public ModelAndView readQnaItemPopupView(@RequestParam("itemId") String itemId,
			@RequestParam(value = "layoutType", defaultValue = "layoutNormal") String layoutType,
			@RequestParam(value = "docPopup", defaultValue = "false") Boolean docPopup,
			@RequestParam(value = "workspaceId", required = false) String workspaceId) throws JsonGenerationException,
			JsonMappingException, IOException {

		User user = readUser();
		String viewName = "";

		Boolean popup = false;

		int qnaPermission = 0;
//		qnaPermission = getQnaPermission(user, workspaceId);
		qnaPermission = getQnaPermission(user);

		if (qnaPermission == 0) {
			throw new IKEP4AuthorizedException();
		}

		// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
//		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId, workspaceId);
		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId);

		if (qnaItem == null) {
			throw new IKEP4AuthorizedException(messageSource, "message.collpack.kms.common.boardItem.deletedItem");
		}

		if ("layoutHorizental".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemFrameView";

		} else if ("layoutVertical".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemFrameView";

		} else if ("layoutNormal".equals(layoutType)) {
			viewName = "collpack/kms/qna/readQnaItemView";
		}

		if (StringUtil.isEmpty(workspaceId)) {
			popup = true;
		} else {
			popup = docPopup;
		}

		if (popup) {
			viewName = "collpack/kms/qna/readQnaItemPopupView";
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();

//		Workspace workspace = new Workspace();
//		workspace = qnaItemService.getWorkspaceInfo(portalId, workspaceId);

		int isOrganization = 0;
//		if (workspace != null) {
//			isOrganization = workspace.getIsOrganization();
//		}

		ObjectMapper mapper = new ObjectMapper();

		// 파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if (qnaItem.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(qnaItem.getFileDataList());
		}
		

		boolean ecmrole = false;
				Map<String, String> emap = new HashMap<String, String>();
				emap.put("userId", user.getUserId());
				emap.put("roleName", "ECM");
				int ecmRole = userDao.getUserRoleCheck(emap);
				if(ecmRole > 0){
					ecmrole = true;
				}

		ModelAndView modelAndView = new ModelAndView(viewName).addObject("qnaItem", qnaItem)
				.addObject("layoutType", layoutType).addObject("docPopup", popup)
				.addObject("isOrganization", isOrganization).addObject("qnaPermission", qnaPermission)
				.addObject("user", user).addObject("fileDataListJson", fileDataListJson).addObject("ecmrole", ecmrole);

		return modelAndView;
	}

	/**
	 * NOTICE 게시물 등록 화면
	 * 
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/createQnaItemView")
	public ModelAndView createQnaItemView() {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		String portalId = portal.getPortalId();
//		Workspace workspace = new Workspace();
		QnaItem qnaItem = new QnaItem();

//		workspace = qnaItemService.getWorkspaceInfo(portalId, workspaceId);

//		qnaItem.setWorkspaceId(workspaceId);
//		qnaItem.setWorkspaceName(workspace.getWorkspaceName());

		User user = readUser();

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int qnaPermission = getQnaPermission(user, workspaceId);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		boolean ecmrole = false;
				Map<String, String> emap = new HashMap<String, String>();
				emap.put("userId", user.getUserId());
				emap.put("roleName", "ECM");
				int ecmRole = userDao.getUserRoleCheck(emap);
				if(ecmRole > 0){
					ecmrole = true;
				}

		return new ModelAndView().addObject("qnaItem", qnaItem).addObject("user", user)
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
	}

	/**
	 * NOTICE 게시물 수정 화면
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateQnaItemView")
	public ModelAndView updateQnaItemView(@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException,
			IOException {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int qnaPermission = getQnaPermission(user, workspaceId);
		User user = readUser();

//		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId, workspaceId);
		QnaItem qnaItem = this.qnaItemService.readQnaItem(user.getUserId(), itemId);

		ObjectMapper mapper = new ObjectMapper();

		String fileDataListJson = mapper.writeValueAsString(qnaItem.getFileDataList());
		String tagListJson = mapper.writeValueAsString(qnaItem.getTagList());

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		boolean ecmrole = false;
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			ecmrole = true;
		}


		return new ModelAndView().addObject("qnaItem", qnaItem).addObject("user", user)
				.addObject("tagListJson", tagListJson).addObject("fileDataListJson", fileDataListJson)
				.addObject("useActiveX", useActiveX).addObject("ecmrole", ecmrole);
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 등록
	 * 
	 * @param qnaItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createQnaItem")
	public ModelAndView createQnaItem(@Valid QnaItem qnaItem,
			QnaItemSearchCondition searchCondition, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int qnaPermission = getQnaPermission(user, workspaceId);
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		// if (log.isDebugEnabled()) {
		// log.debug(qnaItem.getEndDate());
		// }
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		qnaItem.setPortalId(portal.getPortalId());

		ModelBeanUtil.bindRegisterInfo(qnaItem, user.getUserId(), user.getUserName());

		String itemId = this.qnaItemService.createQnaItem(qnaItem, user);

		// QnaItemSearchCondition searchCondition =
		// (QnaItemSearchCondition)
		// this.getRequestAttribute("searchCondition");

		status.setComplete();

		// this.setRequestAttribute("searchCondition", searchCondition);

		return new ModelAndView("redirect:/collpack/kms/qna/readQnaItemView.do")
				.addObject("itemId", itemId).addObject("searchCondition", searchCondition)
//				.addObject("workspaceId", qnaItem.getWorkspaceId());
				.addObject("workspaceId", "");
	}

	/**
	 * NOTICE 게시물 수정모듈
	 * 
	 * @param qnaItem
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateQnaItem")
	public ModelAndView updateQnaItem(@Valid QnaItem qnaItem, BindingResult result, SessionStatus status) {

		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int qnaPermission = getQnaPermission(user, workspaceId);

		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); // BindingResult와
																			// BaseController의
																			// MessageSource를
																			// parameter로
																			// 전달해야
																			// 합니다.
		}

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		ModelBeanUtil.bindRegisterInfo(qnaItem, user.getUserId(), user.getUserName());
		qnaItem.setPortalId(portal.getPortalId());

		this.qnaItemService.updateQnaItem(qnaItem, user);

		status.setComplete();

		return new ModelAndView("redirect:/collpack/kms/qna/readQnaItemView.do").addObject(
				"itemId", qnaItem.getItemId());
//				.addObject("workspaceId", qnaItem.getWorkspaceId());
		
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 물리적 삭제 (다중삭제시)
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminMultiDeleteQnaItem")
	public @ResponseBody
	void adminMultiDeleteAnnouncItem(@RequestParam("qnaItemIds") List<String> qnaItemIds) {

		try {
			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
			// 권한 없을때 Forwarding 정책필요
			// int qnaPermission = getQnaPermission(user,
			// workspaceId);
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//			this.qnaItemService.adminMultiDeleteQnaItem(qnaItemIds, workspaceId, portal.getPortalId());
			this.qnaItemService.adminMultiDeleteQnaItem(qnaItemIds, portal.getPortalId());
		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}

	/**
	 * 관리자에 의한 NOTICE 게시물 물리적 삭제
	 * 
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/adminDeleteQnaItem")
	public ModelAndView adminDeleteQnaItem(@RequestParam("itemId") String itemId) {
		// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
		// 권한 없을때 Forwarding 정책필요
		// int qnaPermission = getQnaPermission(user, workspaceId);
		User user = readUser();
//		QnaItem qnaItem = qnaItemService.readQnaItem(user.getUserId(), itemId, workspaceId);
		QnaItem qnaItem = qnaItemService.readQnaItem(user.getUserId(), itemId);

		this.qnaItemService.adminDeleteQnaItem(qnaItem);

		return new ModelAndView("redirect:/collpack/kms/qna/listQnaItemView.do?workspaceId="
//				+ qnaItem.getWorkspaceId());
				+ "");
	}

//	/**
//	 * // * 하위부서중 개설된 WORKSPACE 리스트
//	 * 
//	 * @param qnaItemId
//	 * @param workspaceId
//	 * @param searchCondition
//	 * @param result
//	 * @param status
//	 * @return
//	 */
//	@RequestMapping(value = "/shareQnaItemPop")
//	public ModelAndView shareQnaItemPop(@RequestParam("itemId") String qnaItemId,
//			@RequestParam("workspaceId") String workspaceId, QnaItemSearchCondition searchCondition,
//			BindingResult result, SessionStatus status) {
//		try {
//			// 권한체크 0:비회원,1:회원,2:운영진,3:시스템/Coll관리자
//			// 권한 없을때 Forwarding 정책필요
//			// int qnaPermission = getQnaPermission(user,
//			// workspaceId);
//
//			Portal portal = (Portal) getRequestAttribute("ikep.portal");
//
//			SearchResult<QnaItem> searchResult = null;
//			searchCondition.setWorkspaceId(workspaceId);
//			searchCondition.setItemId(qnaItemId);
//			searchCondition.setPortalId(portal.getPortalId());
//			searchResult = qnaItemService.listChildWorkspaceInfoBySearchCondition(searchCondition);
//
//			return new ModelAndView("collpack/kms/qna/shareQnaItemPop")
//					.addObject("workspaceId", workspaceId)
//					.addObject("searchCondition", searchResult.getSearchCondition())
//					.addObject("searchResult", searchResult).addObject("itemId", qnaItemId);
//
//		} catch (Exception exception) {
//			throw new IKEP4AjaxException("code", exception);
//		}
//	}

//	/**
//	 * 선택된 공지사항 게시물 공유
//	 * 
//	 * @param qnaItemId
//	 * @param workspaceIds
//	 * @param workspaceId
//	 */
//	@RequestMapping(value = "/shareCollQnaItem")
//	public @ResponseBody
//	void shareCollQnaItem(@RequestParam("itemId") String qnaItemId,
//			@RequestParam(value = "workspaceIds", required = false) List<String> workspaceIds,
//			@RequestParam("workspaceId") String workspaceId) {
//		if (!StringUtil.isEmpty(qnaItemId)) {
//			qnaItemService.createQnaLinkItem(qnaItemId, workspaceIds);
//		}
//	}

	/**
	 * Workspace Qna 게시판 권한 가져오기 //권한체크
	 * 0:비회원,준회원,1:정회원,2:운영진,시샵,3:시스템,Coll관리자
	 * 
	 * @param userId
	 * @param workspaceId
	 * @return
	 */
//	public int getQnaPermission(User user, String workspaceId) {
	public int getQnaPermission(User user) {
		
		boolean isSystemAdmin = isSystemAdmin(user);

//		int qnaPermission = 0;
		int qnaPermission = 1;
		if (isSystemAdmin) {
			qnaPermission = 3;
		} 
//		else {
//			Member member = new Member();
//			member.setMemberId(user.getUserId());
//			member.setWorkspaceId(workspaceId);
//			member = memberService.read(member);
//
//			if (member != null) {
//				if ("1".equals(member.getMemberLevel()) || "2".equals(member.getMemberLevel())) {
//					qnaPermission = 2;
//				} else if ("3".equals(member.getMemberLevel())) {
//					qnaPermission = 1;
//				} else {
//					qnaPermission = 0;
//				}
//			}
//		}

		return qnaPermission;
	}
	
	@RequestMapping(value = "/qnaNormalView")
	public ModelAndView qnaNormalView(String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/kms/qna/qnaNormalView");
		
		String campaignmessagesImageYn = "N";
		
		//String managementPolicyImageYn = "N";
		
		try{
			
			/*QnaConfig qnaconfig = qnaItemService.readQnaconfig();

			if(qnaconfig != null) {
				
				if(!StringUtil.isEmpty(qnaconfig.getSummary())) {
					
					String summary = qnaconfig.getSummary().replaceAll("\r\n", "<br/>");
				
					qnaconfig.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(qnaconfig.getImageFileId())) {
				
					FileData fileData = fileService.read(qnaconfig.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							campaignmessagesImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("qnaconfig", qnaconfig);
			mav.addObject("campaignmessagesImageYn", campaignmessagesImageYn);*/
			
			/*PortletCampaignMessages portletManagementPolicy = portletCampaignMessagesService.readPortletManagementPolicy();

			if(portletManagementPolicy != null) {
				
				if(!StringUtil.isEmpty(portletManagementPolicy.getSummary())) {
					
					String summary = portletManagementPolicy.getSummary().replaceAll("\r\n", "<br/>");
				
					portletManagementPolicy.setSummary(summary);
			    }
			
				if(!StringUtil.isEmpty(portletManagementPolicy.getImageFileId())) {
				
					FileData fileData = fileService.read(portletManagementPolicy.getImageFileId());
			
					if(fileData != null) {
						Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
						String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root_image");
				
						File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
					
						if (file.exists()) {
							managementPolicyImageYn = "Y"; 
						}
					}
				}
			}
			
			mav.addObject("portletManagementPolicy", portletManagementPolicy);
			mav.addObject("managementPolicyImageYn", managementPolicyImageYn);*/
						
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	private void authenticationSuccess(HttpServletRequest request, String username) {
		HttpSession httpsession = request.getSession(true);
		String portalId = (String) httpsession.getAttribute("ikep.portalId");

		// 사용자 정보 조회
		User user = userService.read(username);

		// 사용자 테마 정보 조회
		UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());
		UserTheme userTheme = null;

		if (userThemeCheck == null) {
			// 사용자 테마 정보 없으면 기본 테마정보 조회
			userTheme = userThemeService.readDefaultTheme(portalId);
		} else {
			userTheme = userThemeService.readTheme(userThemeCheck.getThemeId(), portalId);
		}

		user.setUserTheme(userTheme);
		
		//세션 타임아웃 설정
		long loginTime=System.currentTimeMillis();
		httpsession.setAttribute("ikep.loginTime", loginTime);

		httpsession.setAttribute("ikep.user", user);
		httpsession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
				new Locale(user.getLocaleCode()));

		BrowserCheck browserCheck = new BrowserCheck();

		String userAgent = request.getHeader("user-agent");
		String browser = browserCheck.getInternetBrowser(userAgent.toLowerCase());

	}

}
