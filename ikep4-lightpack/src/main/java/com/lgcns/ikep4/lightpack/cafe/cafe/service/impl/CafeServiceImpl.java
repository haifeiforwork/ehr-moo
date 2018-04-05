/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafePortletLayoutDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeConstants;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.member.dao.MemberDao;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.tagfree.util.MimeUtil;


/**
 * Cafe Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeServiceImpl.java 19561 2012-06-29 04:17:04Z malboru80 $
 */
@Service("cafeService")
public class CafeServiceImpl extends GenericServiceImpl<Cafe, String> implements CafeService {

	@Autowired
	private CafeDao cafeDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;

	@Autowired
	public CafePortletLayoutDao cafePortletLayoutDao;

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected BoardItemService boardItemService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#listBySearchCondition
	 * (com.lgcns.ikep4.kms.collaboration.search.CafeSearchCondition) Cafe 목록
	 */
	public SearchResult<Cafe> listBySearchCondition(CafeSearchCondition searchCondition) {

		User user = (User) this.getRequestAttribute("ikep.user");

		Integer count = this.cafeDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Cafe> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Cafe>(searchCondition);

		} else {

			List<Cafe> list = this.cafeDao.listBySearchCondition(searchCondition);

			for (Cafe cafe : list) {

				List<Tag> tagList = tagService.listTagByItemId(cafe.getCafeId(), IKepConstant.ITEM_TYPE_CODE_CAFE);
				if (tagList != null && tagList.size() > 0) {
					cafe.setTagList(tagList);
				}

				if (!user.getLocaleCode().equals("ko")) {
					cafe.setRegisterName(cafe.getRegisterEnglishName());
					cafe.setSysopName(cafe.getSysopEnglishName());
					cafe.setCategoryName(cafe.getCategoryEnglishName());
				}
			}
			searchResult = new SearchResult<Cafe>(list, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#listCafeByType(
	 * java.util.Map) 동맹 요청 목록
	 */
	public List<Cafe> listCafeByType(Map<String, String> map) {
		return cafeDao.listCafeByType(map);
	}

	public List<Cafe> listCafeByCategory(Map<String, String> map) {
		return cafeDao.listCafeByCategory(map);
	}

	public List<Cafe> listMyCafe(Map<String, String> map, boolean withBoardItem) {

		User user = (User) this.getRequestAttribute("ikep.user");

		List<Cafe> list = cafeDao.listMyCafe(map);

		for (Cafe cafe : list) {
			if (!user.getLocaleCode().equals("ko")) {
				cafe.setSysopName(cafe.getSysopEnglishName());
			}

			if (cafe.getMemberLevel().equals("1")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.1", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("2")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.2", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("3")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.3", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("4")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.4", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("5")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.5", null,
						Locale.getDefault()));
			}

			if (!user.getLocaleCode().equals("ko")) {
				cafe.setRegisterName(cafe.getRegisterEnglishName());
				cafe.setSysopName(cafe.getSysopEnglishName());
				cafe.setCategoryName(cafe.getCategoryEnglishName());
			}

			if (withBoardItem) {
				BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
				searchCondition.setCafeId(cafe.getCafeId());
				searchCondition.setPagePerRecord(3);
				SearchResult<BoardItem> searchResult = boardItemService.listRecentBoardItemForCafe(searchCondition);

				cafe.setBoardItemList(searchResult.getEntity());
			}
		}

		return list;
	}
	
	public List<Cafe> listMyCafePortlet(Map<String, String> map) {
		List<Cafe> list = cafeDao.listMyCafe(map);
		
		return list;
	}
	
	public List<Cafe> listMyCafeBoardItemPortlet(List<Cafe> list) {
		
		if(list != null) {
			for (Cafe cafe : list) {
				BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
				
				searchCondition.setCafeId(cafe.getCafeId());
				searchCondition.setPagePerRecord(3);
				SearchResult<BoardItem> searchResult = boardItemService.listRecentBoardItemForCafe(searchCondition);

				cafe.setBoardItemList(searchResult.getEntity());
			}
		}
		
		return list;
	}

	public List<Cafe> listCloseCafe(Map<String, String> map) {

		User user = (User) this.getRequestAttribute("ikep.user");

		List<Cafe> list = cafeDao.listCloseCafe(map);

		for (Cafe cafe : list) {
			if (!user.getLocaleCode().equals("ko")) {
				cafe.setSysopName(cafe.getSysopEnglishName());
			}

			if (cafe.getMemberLevel().equals("1")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.1", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("2")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.2", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("3")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.3", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("4")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.4", null,
						Locale.getDefault()));
			} else if (cafe.getMemberLevel().equals("5")) {
				cafe.setMemberLevelName(messageSource.getMessage("message.lightpack.cafe.common.member.5", null,
						Locale.getDefault()));
			}

			if (!user.getLocaleCode().equals("ko")) {
				cafe.setRegisterName(cafe.getRegisterEnglishName());
				cafe.setSysopName(cafe.getSysopEnglishName());
				cafe.setCategoryName(cafe.getCategoryEnglishName());
			}

		}

		return list;
	}

	public List<Cafe> listNewCafe(Map<String, String> map) {

		User user = (User) this.getRequestAttribute("ikep.user");

		List<Cafe> list = cafeDao.listNewCafe(map);

		for (Cafe cafe : list) {
			if (!user.getLocaleCode().equals("ko")) {
				cafe.setSysopName(cafe.getSysopEnglishName());
			}

			if (!user.getLocaleCode().equals("ko")) {
				cafe.setRegisterName(cafe.getRegisterEnglishName());
				cafe.setSysopName(cafe.getSysopEnglishName());
				cafe.setCategoryName(cafe.getCategoryEnglishName());
			}
		}

		return list;

	}

	public List<Cafe> listCafeManager(Map<String, String> map) {
		return cafeDao.listCafeManager(map);
	}

	/*
	 * public SearchResult<Cafe>
	 * listBySearchConditionGroupHierarchy(CafeSearchCondition searchCondition)
	 * { Integer count =
	 * this.cafeDao.countBySearchConditionGroupHierarchy(searchCondition);
	 * searchCondition.terminateSearchCondition(count); SearchResult<Cafe>
	 * searchResult = null; if(searchCondition.isEmptyRecord()) { searchResult =
	 * new SearchResult<Cafe>(searchCondition); } else { List<Cafe> list =
	 * this.cafeDao.listBySearchConditionGroupHierarchy(searchCondition);
	 * searchResult = new SearchResult<Cafe>(list, searchCondition); } return
	 * searchResult; } /* (non-Javadoc)
	 * @see com.lgcns.ikep4.kms.collaboration.service.CafeService#
	 * listBySearchConditionGroup
	 * (com.lgcns.ikep4.kms.collaboration.search.CafeSearchCondition) Cafe 미개설
	 * 조직정보리스트
	 */
	public SearchResult<Cafe> listBySearchConditionGroup(CafeSearchCondition searchCondition) {
		Integer count = this.cafeDao.countBySearchConditionGroup(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Cafe> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Cafe>(searchCondition);
		} else {

			List<Cafe> list = this.cafeDao.listBySearchConditionGroup(searchCondition);

			searchResult = new SearchResult<Cafe>(list, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#getGroupMemberIds
	 * (java.lang.String) 그룹 조직원 정보
	 */
	public List<Cafe> listGroupMembers(String groupId) {
		return cafeDao.listGroupMembers(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.kms.collaboration.service.CafeService#
	 * listBySearchConditionCloseGroup
	 * (com.lgcns.ikep4.kms.collaboration.search.CafeSearchCondition) Cafe 폐쇄대기
	 */
	public SearchResult<Cafe> listBySearchConditionCloseGroup(CafeSearchCondition searchCondition) {

		Integer count = this.cafeDao.countBySearchConditionCloseGroup(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Cafe> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Cafe>(searchCondition);
		} else {

			List<Cafe> list = this.cafeDao.listBySearchConditionCloseGroup(searchCondition);

			searchResult = new SearchResult<Cafe>(list, searchCondition);
		}
		return searchResult;
	}

	public List<Cafe> countCafeTypeOrg(Map<String, String> map) {
		return cafeDao.countCafeTypeOrg(map);
	}

	public void createCafeVisit(Map<String, String> map) {

		boolean hasVisit = this.cafeDao.existsCafeVisit(map);

		if (!hasVisit) {
			this.cafeDao.createCafeVisit(map);
		}
	}

	public String createCafe(String portalId, User user, Cafe cafe) {
		/**
		 * 1. Cafe 등록 2. 시샵 등록 3. 회원 등록 4. 권한 그룹(운영진,정회원,준회원) 5. 운영진,정회원 그룹에 멤버
		 * 등록 5. 기본 게시판 등록
		 */

		try {

			cafe.setCafeId(idgenService.getNextId());
			cafe.setCategoryId(cafe.getCategoryId());
			cafe.setRegisterId(user.getUserId());
			cafe.setRegisterName(user.getUserName());
			cafe.setUpdaterId(user.getUserId());
			cafe.setUpdaterName(user.getUserName());
			cafe.setPortalId(portalId);
			cafe.setCafeStatus(CafeConstants.CAFE_STATUS_OPEN);
			cafe.setLayoutId(this.cafeDao.getDefaultLayoutId());
			cafe.setImageId(cafe.getFileId());
			this.cafeDao.create(cafe);

			// Portlet 생성
			CafePortletLayout cafePortletLayout = new CafePortletLayout();
			for (int i = 1; i <= 2; i++) {
				cafePortletLayout.setPortletLayoutId(idgenService.getNextId());
				if (i <= 3) {
					cafePortletLayout.setColIndex(1);
					cafePortletLayout.setRowIndex(i); // 1,2,3
				} else {
					cafePortletLayout.setColIndex(2);
					cafePortletLayout.setRowIndex(i - 1); // 1,2
				}
				cafePortletLayout.setPortletId("CF_PORTLET_0" + i);
				cafePortletLayout.setCafeId(cafe.getCafeId());

				cafePortletLayout.setIsBoardPortlet(0);
				cafePortletLayout.setBoardId("");
				cafePortletLayoutDao.create(cafePortletLayout);
			}

			// 기본 게시판 생성
			Board board = setNoticeBoard(cafe.getCafeId(), portalId, user);
			boardAdminService.createBoard(board, user);

			// 기본 포틀릿 등록
			cafePortletLayout.setPortletLayoutId(idgenService.getNextId());
			cafePortletLayout.setColIndex(1);
			cafePortletLayout.setRowIndex(3);
			cafePortletLayout.setBoardId(board.getBoardId());
			cafePortletLayout.setIsBoardPortlet(1);
			cafePortletLayout.setPortletId("CF_PORTLET_09");
			cafePortletLayoutDao.create(cafePortletLayout);

			// 시샵 멤버 등록
			createSysopMember(cafe, user);

			// 개설시 추가회원 등록
			if (cafe.getMemberIds() != null) {
				createMember(cafe.getCafeId(), user, cafe.getMemberIds());
			}

			// 이미지 등록
			if (cafe.getFileId() != null && !cafe.getFileId().equals("")) {
				fileService.createFileLink(cafe.getFileId(), cafe.getCafeId(), IKepConstant.ITEM_TYPE_CODE_CAFE, user);
			}

			// Tag 등록
			if (cafe.getTag() != null && !cafe.getTag().equals("")) {
				createTag(cafe, user);
			}

			// createBoard(cafe.getCafeId(),user);

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nCafe 생성실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

		return cafe.getCafeId();
	}

	public void createSysopMember(Cafe cafe, User user) {

		// 개설자 시샵 등록처리
		Member member = new Member();

		member.setCafeId(cafe.getCafeId());
		member.setMemberId(user.getUserId());
		member.setMemberIntroduction(" ");
		member.setMemberLevel(CafeConstants.MEMBER_STATUS_SYSOP);
		member.setJoinType(CafeConstants.MEMBER_JOIN_TYPE_ADM);
		member.setRegisterId(user.getUserId());
		member.setRegisterName(user.getUserName());
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberDao.create(member);

	}

	private Board setNoticeBoard(String cafeId, String portalId, User user) {

		Board board = new Board();

		board.setBoardId(idgenService.getNextId());
		board.setBoardParentId("");
		board.setCafeId(cafeId);
		board.setSortOrder(1);
		board.setBoardType("0");
		board.setBoardName(messageSource.getMessage("message.lightpack.cafe.cafe.main.menu.detail.menu.announce", null,
				new Locale(user.getLocaleCode())));
		board.setDescription(messageSource.getMessage("message.lightpack.cafe.cafe.main.menu.detail.menu.announce",
				null, new Locale(user.getLocaleCode())));
		board.setListType("0");
		board.setRss(0);
		board.setAnonymous(0);
		board.setVersionManage(0);
		board.setWiki(0);
		board.setDocPopup(0);
		board.setPageNum(10);
		board.setReply(0);
		board.setLinereply(1);
		board.setMove(0);
		board.setFileSize(new Long(1048576));
		board.setImageFileSize(new Long(1024000));
		board.setImageWidth(300);
		board.setFileAttachOption(1);
		board.setAllowType("all");
		board.setReadPermission(3);
		board.setWritePermission(3);
		board.setRegisterId(user.getUserId());
		board.setRegisterName(user.getUserName());
		board.setUpdaterId(user.getUserId());
		board.setUpdaterName(user.getUserName());
		board.setBoardRootId("0");
		board.setPortalId(portalId);

		return board;

	}

	public void createMember(String cafeId, User user, List<String> memberIds) {

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("cafeId", cafeId);
		// map.put("groupName", "정회원");
		// 정회원 그룹 가져오기
		// String groupId=memberDao.getEvGroup(map);

		Member sysopMember = memberDao.getSysop(cafeId);

		for (String memberId : memberIds) {
			// 시샵인 경우 등록 제외
			if (memberId.equals(sysopMember.getMemberId())) {
				continue;
			}

			Member member = new Member();

			member.setCafeId(cafeId);
			member.setMemberId(memberId);

			member.setMemberIntroduction(" ");
			member.setMemberLevel(CafeConstants.MEMBER_STATUS_MEMBER);
			member.setJoinType(CafeConstants.MEMBER_JOIN_TYPE_ADM);

			member.setRegisterId(user.getUserId());
			member.setRegisterName(user.getUserName());
			member.setUpdaterId(user.getUserId());
			member.setUpdaterName(user.getUserName());

			memberDao.create(member);

		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#read(com.lgcns.
	 * ikep4.kms.collaboration.model.Cafe) Cafe 조회
	 */
	public Cafe read(String cafeId) {
		User user = (User) this.getRequestAttribute("ikep.user");

		Cafe cafe = cafeDao.get(cafeId);

		if (!user.getLocaleCode().equals("ko")) {
			cafe.setRegisterName(cafe.getRegisterEnglishName());
			cafe.setSysopName(cafe.getSysopEnglishName());
			cafe.setCategoryName(cafe.getCategoryEnglishName());
		}

		return cafe;
	}

	public Cafe getDefaultCafe(Map<String, String> map) {
		return cafeDao.getDefaultCafe(map);
	}

	public boolean checkCafeName(Map<String, String> map) {
		return cafeDao.checkCafeName(map);
	}

	public String checkCafeTeam(String teamId) {
		return cafeDao.checkCafeTeam(teamId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#getOrgGroup(java
	 * .lang.String) Cafe 조직도 정보 호출
	 */
	public List<Cafe> getOrgGroup(String groupId) {
		return cafeDao.getOrgGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#exists(com.lgcns
	 * .ikep4.kms.collaboration.model.Cafe) Cafe 존재유무
	 */
	public boolean exists(Cafe object) {
		return cafeDao.exists(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object) Cafe 정보 수정
	 */
	public void update(Cafe object) {
		cafeDao.update(object);
	}

	public void updateCafeInfo(Cafe cafe, User user) {

		cafe.setUpdaterId(user.getUserId());
		cafe.setUpdaterName(user.getUserName());
		cafe.setImageId(cafe.getFileId());

		cafeDao.update(cafe);

		// 이미지 등록
		if (cafe.getFileId() != null && !cafe.getFileId().equals("")) {

			if (!cafe.getFileId().equals(cafe.getImageIdPre())) {
				fileService.removeItemFile(cafe.getCafeId());
				fileService.createFileLink(cafe.getFileId(), cafe.getCafeId(), IKepConstant.ITEM_TYPE_CODE_CAFE, user);
			}
		}
		// Tag 등록
		if (cafe.getTag() != null)
			createTag(cafe, user);
	}

	public void updateCafeIntro(Cafe cafe, User user) {

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (cafe.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(cafe.getCafeIntroduction());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (cafe.getEditorFileLinkList() != null) {
							for (int i = 0; i < cafe.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) cafe.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						cafe.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					cafe.setCafeIntroduction(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		cafe.setUpdaterId(user.getUserId());
		cafe.setUpdaterName(user.getUserName());

		cafeDao.updateIntro(cafe);

	}

	public void updateCafeStatus(List<String> cafeIds, String cafeStatus, User user) {

		for (int i = 0; i < cafeIds.size(); i++) {
			Cafe cafe = new Cafe();

			String cafeId = cafeIds.get(i);

			cafe.setCafeId(cafeId);
			cafe.setCafeStatus(cafeStatus);

			cafe.setUpdaterId(user.getUserId());
			cafe.setUpdaterName(user.getUserName());

			cafeDao.updateStatus(cafe);
		}
		/**
		 * 멤버 권한 설정
		 */
	}

	public void updateCafeStatus(Portal portal, User user, List<String> cafeIds, String cafeStatus) {

		for (int i = 0; i < cafeIds.size(); i++) {

			Cafe cafe = new Cafe();

			String cafeId = cafeIds.get(i);

			cafe.setPortalId(portal.getPortalId());
			cafe.setCafeId(cafeId);
			cafe.setCafeStatus(cafeStatus);

			cafe.setUpdaterId(user.getUserId());
			cafe.setUpdaterName(user.getUserName());

			Cafe cafe1 = cafeDao.get(cafeId);

			if (cafe1.getCafeStatus().equals("WC") && cafeStatus.equals("O")) {
				cafe.setCafeStatus("Reject");// 폐쇄 반려처리
				cafeDao.updateStatus(cafe); // 폐쇄 반려처리
			} else {
				cafeDao.updateStatus(cafe);
			}
		}
		/**
		 * 멤버 권한 설정
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#updateStatus(com
	 * .lgcns.ikep4.kms.collaboration.model.Cafe) Cafe 상태 변경(개설,폐쇄신청,폐쇄,개설반려)
	 */
	public void updateStatus(Cafe object) {
		cafeDao.updateStatus(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.kms.collaboration.service.CafeService#physicalDelete(
	 * java.lang.String) Cafe 물리적 삭제
	 */
	public void physicalDelete(String cafeId) {

		/** 워크스페이스 멤버 삭제 **/
		memberService.physicalDeleteMemberByCafe(cafeId);

		cafeDao.physicalDelete(cafeId);

	}

	public void physicalDelete(List<String> cafeIds) {

		for (int i = 0; i < cafeIds.size(); i++) {

			String cafeId = cafeIds.get(i);

			/** 워크스페이스 멤버 삭제 + 권한 그룹 삭제 **/
			memberService.physicalDeleteMemberByCafe(cafeId);
			cafeDao.physicalDeleteCafeVisit(cafeId);
			cafeDao.physicalDelete(cafeId);

		}

	}

	/**
	 * 태그 등록
	 * 
	 * @param
	 */
	private void createTag(Cafe cafe, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(cafe.getTag())) {
			Tag tag = new Tag();
			String tagSubType = "";

			// tagSubType = "A";

			tag.setTagName(cafe.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(cafe.getCafeId()); // WS ID
			tag.setTagItemType(IKepConstant.ITEM_TYPE_CODE_CAFE); // 모듈 타입 정의되어
																	// 있음.
			tag.setTagItemSubType(tagSubType); // 모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemName(cafe.getCafeName()); // WS 이름
			tag.setTagItemContents(cafe.getDescription()); // WS 소개글
			tag.setTagItemUrl("/lightpack/cafe/main/cafe.do?docPopup=true&cafeId=" + cafe.getCafeId()); // WS
																										// 팝업창
																										// url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(user.getPortalId());

			tagService.create(tag);
		}
	}

	/**
	 * Cafe 삭제 목록
	 */
	public List<Cafe> listAllCafeDelete() {

		// Cafe 삭제 목록 정보
		return cafeDao.listAllCafeDelete();

	}

	public void syncTeamCafe(String userId) {
		cafeDao.syncTeamCafe(userId);
	}

	public void syncUserCafe(String userId) {
		cafeDao.syncUserCafe(userId);
	}

	public void deleteCafeBatch(String cafeId) {
		cafeDao.deleteCafeBatch(cafeId);
	}

	public List<Cafe> listGroupHierarchy(String groupId) {
		return cafeDao.listGroupHierarchy(groupId);
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public List<Cafe> getCafeDateCount(String cafeId) {
		return cafeDao.getCafeDateCount(cafeId);
	}

	public SearchResult<Cafe> getCafeImageFile(CafeSearchCondition searchCondition) {

		SearchResult<Cafe> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Cafe> list = cafeDao.getCafeImageFile(searchCondition);

		if (list == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(list.size());

			searchResult = new SearchResult<Cafe>(list, searchCondition);
		}

		return searchResult;

	}
}
