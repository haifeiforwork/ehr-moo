/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.collpack.qna.service.QnaExpertService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


/**
 * Qna controller
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaController.java 16971 2011-11-03 06:50:28Z yu_hs $$
 */
@Controller
@SessionAttributes("qnaForm")
public class QnaController extends QnaBaseController {

	@Autowired
	private QnaService qnaService;

	@Autowired
	private QnaExpertService qnaExpertService;

	@Autowired
	private TagService tagService;

	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	@Autowired
	private FileService fileService; 

	/**
	 * 등록폼
	 * 
	 * @param qnaSearch Qna search 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/formQna.do", method = RequestMethod.GET)
	public String getForm(Qna qnaSearch, Model model, @ModelAttribute("isAdmin") boolean isAdmin)
			throws JsonGenerationException, JsonMappingException, IOException {

		Qna qna = new Qna();

		if (StringUtil.isEmpty(qnaSearch.getQnaId())) { // 질문 등록이면

			qna.setQnaType(QnaConstants.IS_QNA);

			if (qnaSearch.getUrgent() != null) {
				qna.setUrgent(qnaSearch.getUrgent());
			}
			qna.setCategoryId(qnaSearch.getCategoryId());

			if (!StringUtil.isEmpty(qnaSearch.getListType())
					&& qnaSearch.getListType().equals(QnaConstants.LIST_TYPE_URGENT)) {
				qna.setUrgent(QnaConstants.URGENT_OK);
			}

		} else { // 수정이면
			qna = qnaService.read(qnaSearch.getQnaId());

			// 권한체크
			accessCheck(isAdmin, qna.getRegisterId());

			// 전문가 정보 가져오기
			List<QnaExpert> expertList = qnaExpertService.list(qnaSearch.getQnaId());
			model.addAttribute("expertList", expertList);

			if (expertList.size() > 0) {
				model.addAttribute("expertChannel", expertList.get(0).getRequestChannel());
				qna.setExpertChannel(expertList.get(0).getRequestChannel());
			}

			// 첨부파일 가져오기
			ObjectMapper mapper = new ObjectMapper();
			String fileDataListJson = mapper.writeValueAsString(qna.getFileDataList());
			model.addAttribute("fileDataListJson", fileDataListJson);

			// 태그 가져오기
			List<Tag> tagList = tagService.listTagByItemId(qnaSearch.getQnaId(), TagConstants.ITEM_TYPE_QNA);
			model.addAttribute("tagList", tagList);
		}

		// 전페이지를 가기위한 파라미터
		qna.setListType(StringUtil.nvl(qnaSearch.getListType(), QnaConstants.LIST_TYPE_SEARCH));
		qna.setListTab(qnaSearch.getListTab());

		model.addAttribute("qnaForm", qna);
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		model.addAttribute("useActiveX", useActiveX);
		

		return "collpack/qna/qnaForm";
	}

	/**
	 * 전문가를 선택하여 질문했을 때
	 * 
	 * @param qnaExpert 전문가 객체
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/formExpertQna.do", method = RequestMethod.GET)
	public String postForm(QnaExpert qnaExpert, Model model) {

		Qna qna = new Qna();

		qna.setQnaType(QnaConstants.IS_QNA);
		qna.setExpertId(qnaExpert.getExpertId());
		qna.setExpertChannel(qnaExpert.getRequestChannel());

		// 전페이지를 가기위한 파라미터
		qna.setListType(QnaConstants.LIST_TYPE_SEARCH);

		model.addAttribute("qnaForm", qna);

		qnaExpert.setUserEnglishName(qnaExpert.getUserName());
		qnaExpert.setJobTitleEnglishName(qnaExpert.getJobTitleName());
		qnaExpert.setTeamEnglishName(qnaExpert.getTeamName());

		List<QnaExpert> expertList = new ArrayList<QnaExpert>();

		expertList.add(qnaExpert);

		model.addAttribute("expertList", expertList);
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		model.addAttribute("useActiveX", useActiveX);

		return "collpack/qna/qnaForm";
	}

	/**
	 * 답글 쓰기 폼
	 * 
	 * @param qnaSearch Qna search 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/formAnswer.do", method = RequestMethod.GET)
	public String getReplayForm(Qna qnaSearch, Model model, @ModelAttribute("isAdmin") boolean isAdmin)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		Qna parent = qnaService.read(qnaSearch.getQnaGroupId());

		// 첨부파일 가져오기
		parent.setFileDataListJson(mapper.writeValueAsString(parent.getFileDataList()));

		model.addAttribute("parent", parent); // 질문 게시글

		Qna qna = new Qna();

		if (StringUtil.isEmpty(qnaSearch.getQnaId())) { // 등록이면

			qna.setQnaGroupId(parent.getQnaGroupId());
			qna.setQnaType(QnaConstants.IS_REPLY);
			qna.setCategoryId(parent.getCategoryId());
			qna.setAnswerAdopt(QnaConstants.ADOPT_NO); // 정렬을 위해 질문에는 디폴트 값을
														// 넣는다.

		} else { // 수정이면
			qna = qnaService.read(qnaSearch.getQnaId());

			// 권한체크
			accessCheck(isAdmin, qna.getRegisterId());

			// 첨부파일 가져오기
			String fileDataListJson = mapper.writeValueAsString(qna.getFileDataList());
			model.addAttribute("fileDataListJson", fileDataListJson);

			// 태그 가져오기
			List<Tag> tagList = tagService.listTagByItemId(qnaSearch.getQnaId(), TagConstants.ITEM_TYPE_QNA);
			model.addAttribute("tagList", tagList);

		}

		// 전페이지를 가기위한 파라미터
		qna.setListType(qnaSearch.getListType());
		qna.setListTab(qnaSearch.getListTab());

		model.addAttribute("qnaForm", qna); // 답변 기본 값
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		model.addAttribute("useActiveX", useActiveX);

		return "collpack/qna/qnaAnswerForm";
	}

	/**
	 * Qna 등록
	 * 
	 * @param qna Qna 객체
	 * @param result
	 * @param status
	 * @param model
	 * @param isAdmin
	 * @param request
	 * @param categoryList
	 * @return
	 */
	@RequestMapping(value = "/createQna.do", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute("qnaForm") Qna qnaForm, BindingResult result, SessionStatus status,
			ModelMap model, @ModelAttribute("isAdmin") boolean isAdmin, HttpServletRequest request,
			@ModelAttribute("categoryList") List<QnaCategory> categoryList) {

		ModelAndView mav = new ModelAndView("collpack/qna/qnaForm");

		// 유효성 검사
		if (!isValid(result, qnaForm, null)) {

			mav.addObject("qnaForm", qnaForm);

			String[] tagNames = qnaForm.getTag().split(",");
			List<Tag> tagList = new ArrayList<Tag>();
			for (String tagName : tagNames) {
				Tag tag = new Tag();
				tag.setTagName(tagName);
				tagList.add(tag);
			}
			mav.addObject("categoryList", categoryList);
			mav.addObject("tagList", tagList);

			if (qnaForm.getQnaType() == 0) { // 질문이면
				mav.setViewName("collpack/qna/qnaForm");
			} else {
				Qna parent = qnaService.read(qnaForm.getQnaGroupId());
				mav.addObject("parent", parent);
				mav.setViewName("collpack/qna/qnaAnswerForm");
			}

			return mav;
		}

		String qnaId = qnaForm.getQnaId();

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		qnaForm.setUpdaterId(user.getUserId());
		qnaForm.setUpdaterName(user.getUserName());
		qnaForm.setPortalId(portal.getPortalId());

		if (qnaForm.getUrgent() == null) {
			qnaForm.setUrgent(0);
		}

		char mblogChannel = '0'; // 마이크로블로깅 전문가 답변요청이 있는지 검사
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX) && qnaForm.getMsie() == 1) {
			
			//사용자 브라우저가 IE인 경우
				try {	
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(qnaForm.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						qnaForm.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					qnaForm.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
		}
		
		//빈값을 null로 변경 - mssql 
		if(qnaForm.getCategoryId() != null && qnaForm.getCategoryId().length() == 0){
			qnaForm.setCategoryId(null);
		}
		
		String pk = "";
		if (StringUtil.isEmpty(qnaId)) { // 등록일때

			qnaForm.setRegisterId(user.getUserId());
			qnaForm.setRegisterName(user.getUserName());

			String url = request.getRequestURL().toString();

			qnaForm.setQnaPathUrl(url.substring(0, url.lastIndexOf("/")));

			pk = qnaService.create(qnaForm, user);

		} else { // 수정일때

			// 권한체크
			accessCheck(isAdmin, qnaForm.getRegisterId());

			qnaService.update(qnaForm, user);
		}

		// getView.do에서 mblog 호출하기 위한 변수 처리
		try {
			mblogChannel = qnaForm.getExpertChannel().charAt(QnaConstants.EXPERT_CHANNEL_MBLOG);
		} catch (Exception e) {
		}

		// 그룹아이디로 상세화면 이동
		String groupId = qnaForm.getQnaGroupId();

		if (StringUtil.isEmpty(groupId)) {
			groupId = pk;
		}

		StringBuffer param = new StringBuffer();
		;

		param = getParam(qnaForm, param);

		model.clear(); // modelAttribute url에서 제거

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();

		mav.setViewName("redirect:getQna.do?qnaId=" + groupId + "&listType=" + qnaForm.getListType() + param.toString()
				+ "&mblogChannel=" + mblogChannel);

		return mav;
	}

	/**
	 * 파라미터 구하기
	 * 
	 * @param qna
	 * @param param
	 * @return
	 */
	private StringBuffer getParam(Qna qna, StringBuffer param) {

		if (!StringUtil.isEmpty(qna.getCategoryId())) {
			String cateId = "&categoryId=" + qna.getCategoryId();
			param.append(cateId);
		}
		if (qna.getListTab() != null) {
			String listTab = "&listTab=" + qna.getListTab();
			param.append(listTab);
		}
		String qnaType = "&qnaType=" + qna.getQnaType();
		param.append(qnaType);

		return param;
	}

	/**
	 * Qna 상세조회 - Qna 상세 질문부터 답변까지 다 가져옴
	 * 
	 * @param qnaId Qna id
	 * @param listType 리스트 타입
	 * @param docPopup 팝업 모드인지 구분
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getQna.do", method = RequestMethod.GET)
	public ModelAndView getQna(@RequestParam(value = "qnaId") String qnaId,
			@RequestParam(value = "listType", required = false) String listType,
			@RequestParam(value = "docPopup", required = false, defaultValue = "false") String docPopup,
			@ModelAttribute("categoryList") List<QnaCategory> categoryList) throws JsonGenerationException,
			JsonMappingException, IOException {

		String view = (docPopup.equals("false")) ? "collpack/qna/qnaView" : "collpack/qna/qnaViewNotTile";

		ModelAndView mav = new ModelAndView();
		User user = (User) getRequestAttribute("ikep.user");

		List<Qna> qnaGroupList = qnaService.readGroup(qnaId, user.getUserId());

		// 태그 관련 qna 가져오기
		if (qnaGroupList != null && qnaGroupList.size() > 0) {

			mav = listRelationQnaAction(qnaGroupList.get(0).getQnaId(), 1, view);

		} else {
				throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}

		// 첨부파일 가져오기
		if (qnaGroupList.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();

			for (Qna qnaFile : qnaGroupList) {
				String fileDataListJson = mapper.writeValueAsString(qnaFile.getFileDataList());

				qnaFile.setFileDataListJson(fileDataListJson);

			}
		}

		mav.addObject("qnaGroupList", qnaGroupList);

		String lType = listType;
		if (StringUtil.isEmpty(listType)) {
			lType = QnaConstants.LIST_TYPE_SEARCH;
		}

		mav.addObject("listType", lType);

		// 전문가 정보 가져오기
		List<QnaExpert> expertList = qnaExpertService.list(qnaId);
		mav.addObject("expertList", expertList);
		mav.addObject("expertListCount", expertList.size());

		// 사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(qnaId, user.getUserId());
		mav.addObject("isFavorite", isFavorite);

		// 카테고리 이름 가져오기
		String categoryName = "";
		if (qnaGroupList.size() > 0 && categoryList.size() > 0) {
			for (QnaCategory category : categoryList) {
				if (category.getCategoryId().equals(qnaGroupList.get(0).getCategoryId())) {
					categoryName = category.getCategoryName();
				}
			}
		}
		mav.addObject("categoryName", categoryName);

		return mav;
	}

	/**
	 * 관련 QNA 리스트 가져오기
	 * 
	 * @param qnaGroupId 그룹 ID
	 * @param pageIndex 페이지 index
	 * @return
	 */
	@RequestMapping("/listRelationQna.do")
	public ModelAndView listRelationQna(String qnaGroupId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex) {

		ModelAndView mav = new ModelAndView();
		mav = listRelationQnaAction(qnaGroupId, pageIndex, "collpack/qna/qnaRelationList");
		return mav;
	}

	/**
	 * ajax이용한 관련 QNA 리스트 가져오기
	 * 
	 * @param qnaGroupId 그룹 ID
	 * @param pageIndex 페이지 index
	 * @return
	 */
	@RequestMapping(value = "/listRelationQnaAjax.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listRelationQnaAjax(String qnaGroupId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex) {

		ModelAndView mav = new ModelAndView();

		try {

			mav = listRelationQnaAction(qnaGroupId, pageIndex, "collpack/qna/qnaRelationList");

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);

		}

		return mav;
	}

	/**
	 * 공통 qna relation list
	 * 
	 * @param pageIndex TODO
	 * @param returnUrl
	 * @param qnaId
	 * @return
	 */
	private ModelAndView listRelationQnaAction(String qnaGroupId, int pageIndex, String returnUrl) {

		ModelAndView mav = new ModelAndView(returnUrl);

		// 페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");
		int pagePer = Integer.parseInt(prop.getProperty("qna.relation.list.pagePer"));

		Tag tagSearch = new Tag();
		tagSearch.setTagItemId(qnaGroupId);
		tagSearch.setTagItemType(TagConstants.ITEM_TYPE_QNA);
		tagSearch.setTagItemSubType(QnaConstants.ITEM_SUB_TYPE_QNA);
		tagSearch.setPageIndex(pageIndex);
		tagSearch.setPagePer(pagePer);

		Map<String, Object> qnaIdMap = tagService.listItemId(tagSearch);

		List<String> qnaIdList = (List<String>) qnaIdMap.get("list");
		int qnaIdCount = (Integer) qnaIdMap.get("count");
		mav.addObject("relationListCount", qnaIdCount);

		if (qnaIdCount > 0) {

			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			Qna qnaSearch = new Qna();

			qnaSearch.setQnaId(qnaGroupId);
			qnaSearch.setQnaIdList(qnaIdList);
			qnaSearch.setPortalId(portal.getPortalId());
			qnaSearch.setQnaType(QnaConstants.IS_QNA);

			SearchCondition searchCondition = new SearchCondition();
			searchCondition.setPageIndex(pageIndex);
			searchCondition.setPagePerRecord(pagePer);

			// 카운트
			int count = qnaIdCount;
			searchCondition.terminateSearchCondition(count);

			mav.addObject("relationSearchCondition", searchCondition);

			// 게시물 리스트 가져오기
			List<Qna> qnaRelationList = qnaService.listRelation(qnaSearch);

			mav.addObject("qnaRelationList", qnaRelationList);
		}

		return mav;

	}

	/**
	 * QNA 검색 리스트
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping(value = "/listSearchQna.do", method = RequestMethod.GET)
	public ModelAndView listSearch(Qna qnaSearch) {

		qnaSearch.setQnaType(QnaConstants.IS_QNA);

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_SEARCH);
		return mav;
	}

	/**
	 * QNA 검색 리스트 - Ajax
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping("/listSearchQnaAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listSearchAjax(Qna qnaSearch) {

		ModelAndView mav = new ModelAndView();

		try {

			qnaSearch.setQnaType(QnaConstants.IS_QNA);

			mav = listAction(qnaSearch, "collpack/qna/qnaMoreList");
			mav.addObject("listType", QnaConstants.LIST_TYPE_SEARCH);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	/**
	 * 분야별 QNA
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping(value = "/listCategoryQna.do", method = RequestMethod.GET)
	public ModelAndView listCategoryQna(Qna qnaSearch, @ModelAttribute("categoryList") List<QnaCategory> categoryList) {

		qnaSearch.setQnaType(QnaConstants.IS_QNA);

		// 탭타입에 맞게 검색
		if (qnaSearch.getListTab() != null && qnaSearch.getListTab() == QnaConstants.TAP_TYPE_IS_NOT_ADOPT) { // 미채택
			qnaSearch.setIsNotAdopt(QnaConstants.IS_NOT_ADOPT);
		} else if (qnaSearch.getListTab() != null && qnaSearch.getListTab().equals(QnaConstants.TAP_TYPE_BEST)) {
			qnaSearch.setBestFlag(QnaConstants.BEST_QNA_TYPE);
		}

		if (qnaSearch.getCategoryId() == null || StringUtil.isEmpty(qnaSearch.getCategoryId())) {
			qnaSearch.setIsNullCategory("true");
		}

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_CATEGORY);
		mav.addObject("categoryId", qnaSearch.getCategoryId());
		mav.addObject("isNotAdopt", qnaSearch.getIsNotAdopt());
		mav.addObject("bestFlag", qnaSearch.getBestFlag());
		mav.addObject("qnaType", qnaSearch.getQnaType());
		mav.addObject("listTab", qnaSearch.getListTab());

		// 카테고리 이름 가져오기
		String categoryName = "";
		if (categoryList.size() > 0) {
			for (QnaCategory category : categoryList) {
				if (category.getCategoryId().equals(qnaSearch.getCategoryId())) {
					categoryName = category.getCategoryName();
				}
			}
		}
		mav.addObject("categoryName", categoryName);

		return mav;
	}

	/**
	 * MY QnA 리스트
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping(value = "/listMyQna.do", method = RequestMethod.GET)
	public ModelAndView listMyQna(Qna qnaSearch) {

		User user = (User) getRequestAttribute("ikep.user");

		qnaSearch.setRegisterId(user.getUserId());
		if (qnaSearch.getQnaType() == null) {
			qnaSearch.setQnaType(QnaConstants.IS_QNA);
		}
		if (qnaSearch.getListTab() == null) {
			qnaSearch.setListTab(QnaConstants.IS_QNA);
		}
		
		if(qnaSearch.getListTab() != null && qnaSearch.getListTab() == QnaConstants.IS_REPLY){
			qnaSearch.setQnaType(QnaConstants.IS_REPLY);
			qnaSearch.setListTab(QnaConstants.IS_REPLY);
		}

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_MY);
		mav.addObject("qnaType", qnaSearch.getQnaType());
		mav.addObject("listTab", qnaSearch.getListTab());

		return mav;
	}

	/**
	 * 긴급 리스트
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping(value = "/listUrgentQna.do", method = RequestMethod.GET)
	public ModelAndView listUrgent(Qna qnaSearch) {

		qnaSearch.setQnaType(QnaConstants.IS_QNA);
		qnaSearch.setUrgent(QnaConstants.URGENT_OK);

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_URGENT);
		mav.addObject("urgent", qnaSearch.getUrgent());
		return mav;
	}

	/**
	 * 미해결질문
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping(value = "/listNotAdoptQna.do", method = RequestMethod.GET)
	public ModelAndView listNotAdopt(Qna qnaSearch) {

		qnaSearch.setQnaType(QnaConstants.IS_QNA);
		qnaSearch.setIsNotAdopt(QnaConstants.IS_NOT_ADOPT);

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_NOTADOPT);
		mav.addObject("isNotAdopt", qnaSearch.getIsNotAdopt());
		mav.addObject("qnaType", QnaConstants.IS_QNA);
		return mav;
	}

	/**
	 * Best질문
	 * 
	 * @param pageIndex
	 * @param searchSelect
	 * @param searchWord
	 * @return
	 */
	@RequestMapping(value = "/listBestQna.do", method = RequestMethod.GET)
	public ModelAndView listBest(Qna qnaSearch) {

		if (qnaSearch.getListTab() != null && qnaSearch.getListTab().equals(QnaConstants.IS_REPLY)) { // 답변이면
			qnaSearch.setAnswerBestFlag(QnaConstants.BEST_QNA_TYPE);
		} else { // 질문이면
			qnaSearch.setBestFlag(QnaConstants.BEST_QNA_TYPE);
		}

		qnaSearch.setQnaType(QnaConstants.IS_QNA);

		ModelAndView mav = listAction(qnaSearch, "collpack/qna/qnaList");
		mav.addObject("listType", QnaConstants.LIST_TYPE_BEST);
		mav.addObject("bestFlag", qnaSearch.getBestFlag());
		mav.addObject("qnaType", qnaSearch.getQnaType());
		return mav;
	}

	/**
	 * 공통 리스트 액션
	 * 
	 * @param qnaSearch
	 * @param returnUrl
	 * @return
	 */
	private ModelAndView listAction(Qna qnaSearch, String returnUrl) {

		ModelAndView mav = new ModelAndView(returnUrl);

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		qnaSearch.setPortalId(portal.getPortalId());

		// 페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");
		int pagePer = Integer.parseInt(prop.getProperty("qna.default.list.pagePer"));

		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(qnaSearch.getPageIndex());
		searchCondition.setPagePerRecord(pagePer);

		// 카운트
		int count = qnaService.listCount(qnaSearch);
		mav.addObject("count", count);

		searchCondition.terminateSearchCondition(count);

		mav.addObject("searchCondition", searchCondition);

		// 게시물 리스트 가져오기
		qnaSearch.setEndRowIndex(searchCondition.getEndRowIndex());
		qnaSearch.setStartRowIndex(searchCondition.getStartRowIndex());

		List<Qna> list = qnaService.list(qnaSearch);

		mav.addObject("qnaList", list);

		return mav;
	}

	/**
	 * Qna main
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main.do")
	public ModelAndView viewMain() {

		ModelAndView mav = new ModelAndView("collpack/qna/main");

		Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// Best Qna
		String bestLine = prop.getProperty("qna.main.bestList.pagePer");
		String bestLimitDate = prop.getProperty("qna.main.bestList.day");

		User user = (User) getRequestAttribute("ikep.user");
		
		String bestQnaId = qnaService.readBestQnaId(QnaConstants.IS_QNA, portal.getPortalId(), bestLimitDate, Integer.parseInt(bestLine));
		List<Qna> bestQnaList = qnaService.readGroup(bestQnaId, user.getUserId());
		mav.addObject("bestQnaList", bestQnaList);

		//최신 qna
		Qna newQnaSearch = new Qna();

		String newPageLine = prop.getProperty("qna.main.newList.pagePer");

		newQnaSearch.setEndRowIndex(Integer.parseInt(newPageLine));
		newQnaSearch.setStartRowIndex(0);
		newQnaSearch.setPortalId(portal.getPortalId());
		newQnaSearch.setQnaType(QnaConstants.IS_QNA);
		newQnaSearch.setItemDelete(QnaConstants.ITEM_DELETE_NO); // 삭제되지 않은 자료만

		List<Qna> newList = qnaService.list(newQnaSearch);

		mav.addObject("newList", newList);

		// 전문가 random list
		List<QnaExpert> qnaExpertList = qnaService.listMainExpert(portal.getPortalId());
		mav.addObject("qnaExpertList", qnaExpertList);

		return mav;
	}

	/**
	 * ajax이용한 리스트 가져오기
	 * 
	 * @param qnaSearch Qna search 객체
	 * @return
	 */
	@RequestMapping("/listMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listMore(Qna qnaSearch) {

		User user = (User) getRequestAttribute("ikep.user");

		if (qnaSearch.getListType() != null && qnaSearch.getListType().equals(QnaConstants.LIST_TYPE_MY)) {
			qnaSearch.setRegisterId(user.getUserId());
		}

		if (qnaSearch.getListType() != null && qnaSearch.getListType().equals(QnaConstants.LIST_TYPE_CATEGORY)
				&& StringUtil.isEmpty(qnaSearch.getCategoryId())) {
			qnaSearch.setIsNullCategory("true");
		}
		
		if (qnaSearch.getListType() != null && qnaSearch.getListType().equals(QnaConstants.LIST_TYPE_BEST)) {
			
			if (qnaSearch.getListTab() != null && qnaSearch.getListTab().equals(QnaConstants.IS_REPLY)) { // 답변이면
				qnaSearch.setAnswerBestFlag(QnaConstants.BEST_QNA_TYPE);
			} else { // 질문이면
				qnaSearch.setBestFlag(QnaConstants.BEST_QNA_TYPE);
			}
			
			qnaSearch.setQnaType(QnaConstants.IS_QNA);
		}


		ModelAndView mav = new ModelAndView();

		try {
			mav = listAction(qnaSearch, "collpack/qna/qnaMoreList");
			mav.addObject("qnaType", qnaSearch.getQnaType());
			mav.addObject("listType", qnaSearch.getListType());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);

		}

		return mav;
	}

	/**
	 * 임시삭제
	 * 
	 * @param qnaSearch Qna search 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteItemQna.do", method = RequestMethod.GET)
	public String deleteItemQna(Qna qnaSearch, ModelMap model, @ModelAttribute("isAdmin") boolean isAdmin) {

		Qna qna = qnaService.read(qnaSearch.getQnaId());

		// 권한체크
		accessCheck(isAdmin, qna.getRegisterId());

		if (isAdmin) { // 어드민이면 영구 삭제
			qnaService.delete(qnaSearch.getQnaId());
		} else {
			qnaService.approveDeleteItem(qnaSearch.getQnaId());
		}

		StringBuffer param = new StringBuffer();

		if (!StringUtil.isEmpty(qnaSearch.getCategoryId())) {
			String categoryId = "&categoryId=" + qnaSearch.getCategoryId();
			param.append(categoryId);
		}
		if (qnaSearch.getListTab() != null) {
			String listTab = "&listTab=" + qnaSearch.getListTab();
			param.append(listTab);
		}

		// 삭제할때 오류 발생으로 인해 해당 파라미터가 없으면 넘기지 않도록 수정
		if(qnaSearch.getQnaType() != null && "".equals(qnaSearch.getQnaType())) {
			String qnaType = "&qnaType=" + qnaSearch.getQnaType();
			param.append(qnaType);
		}

		String paramToString = "";
		if (param.length() > 0) {
			paramToString = "?" + param.toString().substring(1, param.toString().length());
		}

		String action;

		model.clear();

		if (StringUtil.isEmpty(qnaSearch.getListType())) {
			qnaSearch.setListType(QnaConstants.LIST_TYPE_MAIN);
		}

		if (qna.getQnaType() == QnaConstants.IS_QNA) { // 질문이면 리스트로 이동, 답변이면
														// 상세보기

			if (qnaSearch.getListType().equals(QnaConstants.LIST_TYPE_MAIN)) {
				action = "redirect:main.do";
			} else {
				action = "redirect:list" + qnaSearch.getListType() + "Qna.do" + paramToString;
			}
		} else {
			action = "redirect:getQna.do?qnaId=" + qna.getQnaGroupId() + "&listType=" + qnaSearch.getListType()
					+ param.toString();
		}
		return action;
	}

	/**
	 * 임시삭제 취소
	 * 
	 * @param qnaId Qna Id
	 * @return
	 */
	@RequestMapping(value = "/aliveItemQna.do", method = RequestMethod.GET)
	public String updateItemDeleteNo(@RequestParam("qnaId") String qnaId, @ModelAttribute("isAdmin") boolean isAdmin) {

		Qna qna = qnaService.read(qnaId);

		// 권한체크
		accessCheck(isAdmin, qna.getRegisterId());

		qnaService.cancleDeleteItem(qnaId);

		String action;

		if (qna.getQnaType() == QnaConstants.IS_QNA) { // 질문면 리스트로 이동, 답변이면 상세보기
			action = "redirect:listQna.do";
		} else {
			action = "redirect:getQna.do?qnaId=" + qna.getQnaGroupId();
		}
		return action;
	}

	/**
	 * ajax를 이용한 답글 승인
	 * 
	 * @param qnaId Qna Id
	 * @param groupId 그룹 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/adoptOk.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String adoptOk(@RequestParam("qnaId") String qnaId, @RequestParam("groupId") String groupId,
			@ModelAttribute("isAdmin") boolean isAdmin) {

		Qna prantQna = qnaService.read(groupId);

		// 권한체크
		accessCheck(isAdmin, prantQna.getRegisterId());

		try {
			qnaService.approveAdopt(qnaId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);

		}

		return "success";

	}

	/**
	 * ajax를 이용한 승인 취소
	 * 
	 * @param qnaId Qna Id
	 * @param groupId 그룹 ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/adoptNo.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String adoptNo(@RequestParam("qnaId") String qnaId, @RequestParam("groupId") String groupId,
			@ModelAttribute("isAdmin") boolean isAdmin) {

		Qna prantQna = qnaService.read(groupId);

		// 권한체크
		accessCheck(isAdmin, prantQna.getRegisterId());

		try {
			qnaService.cancelAdopt(qnaId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);

		}

		return "success";
	}

	/**
	 * 즐겨찾기
	 * 
	 * @param qnaGroupNo 그룹 ID
	 * @return
	 */
	@RequestMapping("/addFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String addFavorite(@RequestParam("qnaGroupNo") String qnaGroupNo) {

		try {
			qnaService.updateFavorite(qnaGroupNo, QnaConstants.NUM_INCRASE);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 즐겨찾기 삭제
	 * 
	 * @param qnaGroupNo 그룹 ID
	 * @return
	 */
	@RequestMapping("/delFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String delFavorite(@RequestParam("qnaGroupNo") String qnaGroupNo) {

		try {
			qnaService.updateFavorite(qnaGroupNo, QnaConstants.NUM_DECREASE);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 메일 뷰 개수 업데이트
	 * 
	 * @param qnaId Qna Id
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMailCount(@RequestParam("qnaId") String qnaId) {

		try {
			qnaService.updateMailCount(qnaId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 블로그 뷰 개수 업데이트
	 * 
	 * @param qnaId
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateMblogCount(@RequestParam("qnaId") String qnaId) {

		try {
			qnaService.updateMblogCount(qnaId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * qna 태그 리스트 - xml 형식
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping("/qnaTag.do")
	public ModelAndView tagList(@RequestParam("type") String type) {

		Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		Tag tag = new Tag();
		tag.setTagItemType(TagConstants.ITEM_TYPE_QNA);
		tag.setStartRowIndex(0);
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
		tag.setPortalId(portal.getPortalId());

		List<Tag> tagList = new ArrayList<Tag>();
		String itemType = "";
		String subItemType = "";

		if (type.equals(TagConstants.ITEM_TYPE_PROFILE_PRO)) { // 전문가 tag
			int proTagPagePer = Integer.parseInt(prop.getProperty("qna.main.expertTag.pagePer"));

			tag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
			tag.setTagItemSubType(null);
			tag.setEndRowIndex(proTagPagePer);

			tagList = tagService.listTagByItemType(tag);
			itemType = TagConstants.ITEM_TYPE_PROFILE_PRO;

		} else if (type.equals(QnaConstants.ITEM_SUB_TYPE_ANSWER)) { // 답변 tag

			int answerTagPagePer = Integer.parseInt(prop.getProperty("qna.main.answerTag.pagePer"));

			tag.setTagItemSubType(QnaConstants.ITEM_SUB_TYPE_ANSWER);
			tag.setEndRowIndex(answerTagPagePer);

			tagList = tagService.listTagByItemType(tag);
			itemType = TagConstants.ITEM_TYPE_QNA;
			subItemType = QnaConstants.ITEM_SUB_TYPE_ANSWER;

		} else if (type.equals(QnaConstants.ITEM_SUB_TYPE_QNA)) { // 질문 tag

			int qnaTagPagePer = Integer.parseInt(prop.getProperty("qna.main.qnaTag.pagePer"));

			tag.setTagItemSubType(QnaConstants.ITEM_SUB_TYPE_QNA);
			tag.setEndRowIndex(qnaTagPagePer);

			tagList = tagService.listTagByItemType(tag);
			itemType = TagConstants.ITEM_TYPE_QNA;
			subItemType = QnaConstants.ITEM_SUB_TYPE_QNA;
		}

		return new ModelAndView("support/tagging/tagXml").addObject("tagList", tagList)
				.addObject("itemType", itemType).addObject("subItemType", subItemType);

	}
}
