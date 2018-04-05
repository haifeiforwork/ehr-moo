package com.lgcns.ikep4.collpack.kms.site.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.kms.site.dao.SiteItemDao;
import com.lgcns.ikep4.collpack.kms.site.model.SiteItem;
import com.lgcns.ikep4.collpack.kms.site.search.SiteItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.site.service.SiteItemService;
//import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
//import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.tagfree.util.MimeUtil;


@Service("kmsSiteItemService")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class SiteItemServiceImpl implements SiteItemService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private SiteItemDao siteItemDao;

//	@Autowired
//	private WorkspaceService workspaceService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;

	/*
	 * 공지사항 게시물 조회리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #
	 * listSiteItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board
	 * .site.search.SiteItemSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<SiteItem> listSiteItemBySearchCondition(
			SiteItemSearchCondition siteItemSearchCondition) {
		Integer count = this.siteItemDao.countBySearchCondition(siteItemSearchCondition);

		siteItemSearchCondition.terminateSearchCondition(count);

		SearchResult<SiteItem> searchResult = null;
		if (siteItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<SiteItem>(siteItemSearchCondition);
		} else {
			List<SiteItem> siteItemList = this.siteItemDao
					.listBySearchCondition(siteItemSearchCondition);

			List<FileData> fileDataList = null;

			for (SiteItem siteItem : siteItemList) {
				if (siteItem.getAttachFileCount() != 0) {
					fileDataList = this.fileService.getItemFile(siteItem.getItemId(), SiteItem.ITEM_FILE_TYPE);
					siteItem.setFileDataList(fileDataList);
				}

				List<Tag> tagList = tagService.listTagByItemId(siteItem.getItemId(), SiteItem.ITEM_TYPE_CODE);
				siteItem.setTagList(tagList);
			}

			searchResult = new SearchResult<SiteItem>(siteItemList, siteItemSearchCondition);
		}

		return searchResult;
	}

	/*
	 * 공지사항 게시물 읽기
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #readSiteItem(java.lang.String, java.lang.String)
	 */
	@Transactional
//	public SiteItem readSiteItem(String userId, String itemId, String workspaceId) {
	public SiteItem readSiteItem(String userId, String itemId) {

		SiteItem siteItem = new SiteItem();
//		siteItem = siteItemDao.getSite(itemId, workspaceId);
		siteItem = siteItemDao.getSite(itemId);
		if (siteItem != null) {
			updateHitCount(userId, itemId);

			// 첨부파일 정보
			if (siteItem.getAttachFileCount() > 0) {
				List<FileData> fileDataList = this.fileService.getItemFile(itemId, SiteItem.ATTECHED_FILE);
				siteItem.setFileDataList(fileDataList);
			}

			List<Tag> tagList = tagService.listTagByItemId(siteItem.getItemId(), SiteItem.ITEM_TYPE_CODE);
			siteItem.setTagList(tagList);
		}

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(itemId, SiteItem.EDITOR_FILE);
		siteItem.setEditorFileDataList(editorFileDataList);

		return siteItem;
	}

	/*
	 * 공지사항 게시물 등록
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #
	 * createSiteItem(com.lgcns.ikep4.collpack.collaboration.board.site.model
	 * .SiteItem)
	 */
	public String createSiteItem(SiteItem siteItem, User user) {
		String id = idgenService.getNextId();
		siteItem.setItemId(id);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (siteItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(siteItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						siteItem.setEditorFileLinkList(util.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					siteItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 첨부파일 업데이트
		if (siteItem.getFileLinkList() != null) {
			siteItem.setAttachFileCount(siteItem.getFileLinkList().size());
			this.fileService.saveFileLink(siteItem.getFileLinkList(), siteItem.getItemId(),
					SiteItem.ITEM_FILE_TYPE, user);
		}

		// Tag 등록
		if (siteItem.getTag() != null) {
			createTag(siteItem, user);
		}
		String itemId = siteItemDao.create(siteItem);

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (siteItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(siteItem.getEditorFileLinkList(), siteItem.getItemId(),
					SiteItem.ITEM_FILE_TYPE, user);
		}

//		if (!StringUtil.isEmpty(itemId)) {
//			// 링크 INSERT
//			siteItem.setIsOwner("1");
//			siteItemDao.createLinkSite(siteItem);
//		}

//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(siteItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST, siteItem.getItemId(), siteItem.getTitle(),
//				"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());

		return itemId;
	}

	/*
	 * 공유 공지사항 게시물 삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #
	 * adminDeleteSiteItem(com.lgcns.ikep4.collpack.collaboration.board.site.
	 * model.SiteItem)
	 */
	public void adminDeleteSiteItem(SiteItem siteItem) {
//		if (!StringUtil.isEmpty(siteItem.getIsOwner()) && siteItem.getIsOwner().equals("1")) {
			// Link삭제
//			siteItemDao.removeSiteLink(siteItem.getItemId(), null);
//			siteItemDao.removeSiteLink(siteItem.getItemId());
			// Reference삭제
			siteItemDao.removeSiteReference(siteItem.getItemId());
			// Item삭제
			siteItemDao.removeSiteItem(siteItem.getItemId());
			// 전체 파일 삭제
			this.fileService.removeItemFile(siteItem.getItemId());
			// Tag삭제
			// Tag 삭제
			tagService.delete(siteItem.getItemId(), SiteItem.ITEM_TYPE_CODE);
			// ActivityStream
//			Workspace workspace = new Workspace();
//			workspace = workspaceService.getWorkspace(siteItem.getWorkspaceId());
//			activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//					IKepConstant.ACTIVITY_CODE_COLL_DOC_DELETE, siteItem.getItemId(), siteItem.getTitle(),
//					"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
//		} else if (!StringUtil.isEmpty(siteItem.getIsOwner()) && siteItem.getIsOwner().equals("0")) {
//			// Link삭제
//			siteItemDao.removeSiteLink(siteItem.getItemId(), siteItem.getWorkspaceId());
//			siteItemDao.removeSiteLink(siteItem.getItemId());
//		}
	}

	/*
	 * 공지사항 게시물 다중삭제
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #adminMultiDeleteSiteItem(java.util.List, java.lang.String)
	 */
//	public void adminMultiDeleteSiteItem(List<String> itemIds, String workspaceId, String portalId) {
	public void adminMultiDeleteSiteItem(List<String> itemIds, String portalId) {
		SiteItem siteItem = new SiteItem();

		for (String itemId : itemIds) {
//			siteItem = this.siteItemDao.getSite(itemId, workspaceId);
			siteItem = this.siteItemDao.getSite(itemId);

			// 이미 삭제가 되었다면 continue
			if (siteItem == null) {
				continue;
			}

//			if ("0".equals(siteItem.getIsOwner())) {
//				 하위 운영자에 의한 Link삭제만
//				siteItemDao.removeSiteLink(siteItem.getItemId(), workspaceId);
//				siteItemDao.removeSiteLink(siteItem.getItemId());
//			} else {
				adminDeleteSiteItem(siteItem);// is_Owner :1
//			}
		}
	}

	/*
	 * 하위부서 중 개설 WORKSPACE 리스트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #
	 * listChildWorkspaceInfoBySearchCondition(com.lgcns.ikep4.collpack.collaboration
	 * .board.site.search.SiteItemSearchCondition)
	 */
//	public SearchResult<SiteItem> listChildWorkspaceInfoBySearchCondition(
//			SiteItemSearchCondition searchCondition) {
//
//		Workspace workspace = new Workspace();
//		workspace = getWorkspaceInfo(searchCondition.getPortalId(), searchCondition.getWorkspaceId());
//
//		SearchResult<SiteItem> searchResult = null;
//
//		searchCondition.setTeamId(workspace.getTeamId());
//		Integer count = this.siteItemDao.countChildWorkspaceBySearchCondition(searchCondition);
//		searchCondition.terminateSearchCondition(count);
//		if (searchCondition.isEmptyRecord()) {
//			searchResult = new SearchResult<SiteItem>(searchCondition);
//
//		} else {
//
//			List<SiteItem> siteWorkspceList = this.siteItemDao
//					.listChildWorkspaceBySearchCondition(searchCondition);
//			searchResult = new SearchResult<SiteItem>(siteWorkspceList, searchCondition);
//		}
//
//		return searchResult;
//	}

	/*
//	 * 공지사항 게시물 하위부서 공유
//	 * @see
//	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
//	 * #createSiteLinkItem(java.lang.String, java.util.List)
//	 */
//	public void createSiteLinkItem(String siteItemId, List<String> workspaceIds) {
//		// DELETE RESET
//		siteItemDao.removeSiteShareLink(siteItemId);
//		if (workspaceIds != null) {
//			for (int j = 0; j < workspaceIds.size(); j++) {
//				String workspaceId = workspaceIds.get(j);
//				// insert
//				SiteItem siteItem = new SiteItem();
//				siteItem.setItemId(siteItemId.trim());
//				siteItem.setWorkspaceId(workspaceId);
//				siteItem.setIsOwner("0");
//				siteItemDao.createLinkSite(siteItem);
//			}
//		}
//	}

	/*
	 * 공지사항게시물 업데이트
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #
	 * updateSiteItem(com.lgcns.ikep4.collpack.collaboration.board.site.model
	 * .SiteItem)
	 */
	public void updateSiteItem(SiteItem siteItem, User user) {
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (siteItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder.currentRequestAttributes().getAttribute(
							"ikep.portal", RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(siteItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null && util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (siteItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < siteItem.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) siteItem.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						siteItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					siteItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 첨부파일 업데이트
		if (siteItem.getFileLinkList() != null) {
			this.fileService.saveFileLink(siteItem.getFileLinkList(), siteItem.getItemId(),
					SiteItem.ITEM_FILE_TYPE, user);

			int index = 0;
			for (FileLink tempFile : siteItem.getFileLinkList()) {
				if (tempFile.getFlag().equals("del")) {
					index++;
				}
			}
			if (index != 0) {
				siteItem.setAttachFileCount(siteItem.getFileLinkList().size() - index);
			} else {
				siteItem.setAttachFileCount(siteItem.getFileLinkList().size());
			}

		}

		// Tag 삭제
		tagService.delete(siteItem.getItemId(), SiteItem.ITEM_TYPE_CODE);

		// Tag 등록
		if (siteItem.getTag() != null) {
			createTag(siteItem, user);
		}

		// 이미지 파일 업데이트
		if (siteItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(siteItem.getEditorFileLinkList(), siteItem.getItemId(),
					SiteItem.ITEM_FILE_TYPE, user);
		}

		siteItemDao.update(siteItem);

//		// ActivityStream
//		Workspace workspace = new Workspace();
//		workspace = workspaceService.getWorkspace(siteItem.getWorkspaceId());
//		activityStreamService.createForCollavoration(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
//				IKepConstant.ACTIVITY_CODE_COLL_DOC_EDIT, siteItem.getItemId(), siteItem.getTitle(),
//				"ANNOUNCE", workspace.getWorkspaceId(), workspace.getWorkspaceName());
	}

	/*
	 * 조회수 UPDATE
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.site.service.SiteItemService
	 * #updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String userId, String itemId) {
		int refCount = this.siteItemDao.countSiteReference(userId, itemId);
		if (refCount == 0) {
			this.siteItemDao.updateSiteHitCount(itemId);
			this.siteItemDao.createSiteItemReference(userId, itemId);
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
	public List<SiteItem> listSiteItemByPortlet(Map<String, String> map) {
		return this.siteItemDao.listSiteItemByPortlet(map);
	}

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<SiteItem> listDeleteSiteByWorkspace(String workspaceId) {
	public List<SiteItem> listDeleteSiteByWorkspace() {		
		return this.siteItemDao.listDeleteSiteByWorkspace();
	}

	/**
	 * 태그작성
	 * 
	 * @param workspace
	 * @param user
	 */
	private void createTag(SiteItem siteItem, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(siteItem.getTag())) {
			Tag tag = new Tag();

			// tagService.create(tag);

			tag.setTagName(siteItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(siteItem.getItemId()); // 게시물 ID
			tag.setTagItemType(SiteItem.ITEM_TYPE_CODE); // 모듈 타입 정의되어 있음.
			tag.setTagItemSubType(""); // 모듈 서브 타입 -
//			tag.setTagItemSubType(siteItem.getWorkspaceId()); // 모듈 서브 타입 -
																	// 있을때만 넣기
			tag.setTagItemName(siteItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(siteItem.getContents()); // WS 소개글
			tag.setTagItemUrl("/collpack/kms/site/readSiteItemView.do?itemId="
					+ siteItem.getItemId()); // WS 팝업창 url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(siteItem.getPortalId());

			tagService.create(tag);
		}
	}
}
