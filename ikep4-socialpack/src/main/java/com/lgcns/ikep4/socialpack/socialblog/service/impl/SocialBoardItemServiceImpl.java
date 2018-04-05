/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;

/**
 * 소셜블로그 게시글 관련 Service Implement Class
 * 
 * @author 이형운
 * @version $Id: SocialBoardItemServiceImpl.java 16246 2011-08-18 04:48:28Z
 *          giljae $
 */
@Service("socialBoardItemService")
public class SocialBoardItemServiceImpl extends
		GenericServiceImpl<SocialBoardItem, String> implements
		SocialBoardItemService {

	/**
	 * 블로그 블로깅 글 컨트롤용 Dao
	 */
	@Autowired
	private SocialBoardItemDao socialBoardItemDao;

	/**
	 * 첨부 파일 컨트롤용 Service
	 */
	@Autowired
	private FileService fileService;

	/**
	 * 태그 컨트롤용 Service
	 */
	@Autowired
	private TagService tagService;

	/**
	 * 키값 생성 관리 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * 블로그 댓글 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardLinereplyDao socialBoardLinereplyDao;

	/**
	 * Activity Stream 기록 용 Service
	 */
	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private SocialBoardItemRecommendDao socialBoardItemRecommendDao;

	@Autowired
	private SocialBoardItemReferenceDao socialBoardItemReferenceDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * listBySearchCondition
	 * (com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition
	 * )
	 */
	public SearchResult<SocialBoardItem> listSocialBoardItemBySearchCondition(
			SocialBoardItemSearchCondition socialBoardItemSearchCondition)
			throws JsonGenerationException, JsonMappingException, IOException {

		Integer count = this.socialBoardItemDao
				.countBySearchCondition(socialBoardItemSearchCondition);

		socialBoardItemSearchCondition.terminateSearchCondition(count);

		SearchResult<SocialBoardItem> searchResult = null;
		if (socialBoardItemSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<SocialBoardItem>(
					socialBoardItemSearchCondition);

		} else {

			List<SocialBoardItem> socialBoardItemList = this.socialBoardItemDao
					.listBySearchCondition(socialBoardItemSearchCondition);

			List<FileData> fileDataList = null;
			List<Tag> tagList = null;
			for (SocialBoardItem socialBoardItem : socialBoardItemList) {
				fileDataList = this.fileService.getItemFile(
						socialBoardItem.getItemId(),
						SocialBoardItem.ATTECHED_FILE);
				socialBoardItem.setFileDataList(fileDataList);

				tagList = tagService.listTagByItemId(
						socialBoardItem.getItemId(),
						TagConstants.ITEM_TYPE_SOCIAL_BLOG);
				socialBoardItem.setTagList(tagList);

				ObjectMapper mapper = new ObjectMapper();
				socialBoardItem.setFileDataListJson((String) mapper
						.writeValueAsString(socialBoardItem.getFileDataList()));
			}

			searchResult = new SearchResult<SocialBoardItem>(
					socialBoardItemList, socialBoardItemSearchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * countSocialBoardItemBySearchCondition
	 * (com.lgcns.ikep4.socialpack.socialblog
	 * .search.SocialBoardItemSearchCondition)
	 */
	public Integer countSocialBoardItemBySearchCondition(
			SocialBoardItemSearchCondition socialBoardItemSearchCondition) {
		return this.socialBoardItemDao
				.countBySearchCondition(socialBoardItemSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#readSocialBoardItem
	 * (java.io.Serializable,com.lgcns.ikep4.support.user.member.model.User)
	 */
	public SocialBoardItem readSocialBoardItem(String itemId, User sessionUser)
			throws JsonGenerationException, JsonMappingException, IOException {

		SocialBoardItem searchSocialBoardItem = new SocialBoardItem();
		searchSocialBoardItem.setItemId(itemId);
		searchSocialBoardItem.setSessUserLocale(sessionUser.getLocaleCode());

		// 게시물을 가져온다.
		SocialBoardItem socialBoardItem = socialBoardItemDao
				.get(searchSocialBoardItem);

		// 첨부 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> fileDataList = this.fileService.getItemFile(itemId,
				SocialBoardItem.ATTECHED_FILE);
		socialBoardItem.setFileDataList(fileDataList);

		// CKEDITOR내의 이미지 파일 리스트를 가져와 게시물에 넣는다
		List<FileData> editorFileDataList = this.fileService.getItemFile(
				itemId, SocialBoardItem.EDITOR_FILE);
		socialBoardItem.setEditorFileDataList(editorFileDataList);

		List<Tag> tagList = tagService
				.listTagByItemId(socialBoardItem.getItemId(),
						TagConstants.ITEM_TYPE_SOCIAL_BLOG);
		socialBoardItem.setTagList(tagList);
		String tagString = "";
		for (int i = 0; i < tagList.size(); i++) {
			if (i == (tagList.size() - 1)) {
				tagString = tagString + tagList.get(i).getTagName();
			} else {
				tagString = tagString + tagList.get(i).getTagName() + ",";
			}
		}
		socialBoardItem.setTag(tagString);

		ObjectMapper mapper = new ObjectMapper();
		socialBoardItem.setFileDataListJson((String) mapper
				.writeValueAsString(socialBoardItem.getFileDataList()));

		return socialBoardItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * createSocialBoardItem
	 * (com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem
	 * ,com.lgcns.ikep4.support.user.member.model.User)
	 */
	@SuppressWarnings("unchecked")
	public String createSocialBoardItem(SocialBoardItem socialBoardItem,
			User user) {
		// 신규 아이디를 받아온다.
		String itemId = idgenService.getNextId();

		// 아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		socialBoardItem.setItemId(itemId);

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader
				.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (socialBoardItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder
							.currentRequestAttributes().getAttribute(
									"ikep.portal",
									RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop
							.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService,
							portal.getPortalHost(), port);
					util.setMimeValue(socialBoardItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null
							&& util.getFileLinkList().size() > 0) {
						socialBoardItem.setEditorFileLinkList(util
								.getFileLinkList());
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					socialBoardItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		// 게시물을 생성한다.
		String socialBoardItemId = socialBoardItemDao.create(socialBoardItem);

		// 게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if (socialBoardItem.getFileLinkList() != null) {
			// 게시물의 첨부파일 갯수를 업데이트 한다.
			socialBoardItem.setAttachFileCount(socialBoardItem
					.getFileLinkList().size());
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(socialBoardItem.getFileLinkList(),
					socialBoardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user);

		}

		// CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if (socialBoardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					socialBoardItem.getEditorFileLinkList(),
					socialBoardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user);
		}

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(socialBoardItem.getTag())) {

			Tag tag = new Tag();
			tag.setTagName(socialBoardItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(socialBoardItem.getItemId()); // 게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_SOCIAL_BLOG); // 모듈 타입
																	// TagConstants에
																	// 정의되어 있음.
																	// 맡은 모듈에 맞게
																	// 골라 사용.
																	// -team
																	// coll은
																	// type ID를
																	// 넣으셔야 합니다.
			tag.setTagItemSubType(socialBoardItem.getRegisterId()); // 모듈 서브 타입
																	// - blog
																	// user id

			tag.setTagItemName(socialBoardItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(socialBoardItem.getContents()); // 게시물 내용
			tag.setTagItemUrl("/socialpack/socialblog//socialBlogPopup.do?blogOwnerId="
					+ socialBoardItem.getRegisterId()
					+ "&itemId="
					+ socialBoardItem.getItemId()); // 게시물 상세화면 URL - body 화면만
													// 나와야 함. 도메인과 contextPash는
													// 빼주시기 바랍니다.
			tag.setRegisterId(socialBoardItem.getRegisterId()); // 등록자 ID
			tag.setRegisterName(socialBoardItem.getRegisterName()); // 등록자 이름
			tag.setPortalId(user.getPortalId()); // portalID

			tagService.create(tag);
		}

		// Activity Stream 추가
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG,
				IKepConstant.ACTIVITY_CODE_DOC_POST,
				socialBoardItem.getItemId(), socialBoardItem.getTitle());

		return socialBoardItemId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#updateSocialBoardItem
	 * (
	 * com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem,com.lgcns.ikep4
	 * .support.user.member.model.User)
	 */
	public void updateSocialBoardItem(SocialBoardItem socialBoardItem, User user) {

		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader
				.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		// ActiveX Editor 사용 여부 확인
		if ("Y".equals(useActiveX)) {
			// 사용자 브라우저가 IE인 경우
			if (socialBoardItem.getMsie() == 1) {
				try {
					// 현재 포탈 도메인 가져오기
					Portal portal = (Portal) RequestContextHolder
							.currentRequestAttributes().getAttribute(
									"ikep.portal",
									RequestAttributes.SCOPE_SESSION);
					// 현재 포탈 포트 가져오기
					String port = commonprop
							.getProperty("ikep4.activex.editor.port");
					// Tagfree ActiveX Editor Util => FileService, domain, port
					// 생성자로 넘김
					MimeUtil util = new MimeUtil(fileService,
							portal.getPortalHost(), port);
					util.setMimeValue(socialBoardItem.getContents());
					// Mime 데이타 decoding
					util.processDecoding();
					// editor 첨부된 이미지 확인
					if (util.getFileLinkList() != null
							&& util.getFileLinkList().size() > 0) {
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						// 기존 등록된 파일 처리
						if (socialBoardItem.getEditorFileLinkList() != null) {
							for (int i = 0; i < socialBoardItem
									.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) socialBoardItem
										.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						// 새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink) util
									.getFileLinkList().get(i);
							newFileLinkList.add(fileLink);
						}

						socialBoardItem.setEditorFileLinkList(newFileLinkList);
					}
					// 내용 가져오기
					String content = util.getDecodedHtml(false);
					content = content.trim();
					// 내용세팅
					socialBoardItem.setContents(content);

				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		socialBoardItemDao.update(socialBoardItem);

		// 게시물에 등록하나 첨부파일의 링크 정보를 생성한다.
		if (socialBoardItem.getFileLinkList() != null) {
			// 게시물의 첨부파일 갯수를 업데이트 한다.
			socialBoardItem.setAttachFileCount(socialBoardItem
					.getFileLinkList().size());
			// 파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(socialBoardItem.getFileLinkList(),
					socialBoardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user);

		}

		// 이미지 파일 업데이트
		if (socialBoardItem.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(
					socialBoardItem.getEditorFileLinkList(),
					socialBoardItem.getItemId(),
					IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user);
		}

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(socialBoardItem.getTag())) {

			Tag tag = new Tag();
			tag.setTagName(socialBoardItem.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(socialBoardItem.getItemId()); // 게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_SOCIAL_BLOG); // 모듈 타입
																	// TagConstants에
																	// 정의되어 있음.
																	// 맡은 모듈에 맞게
																	// 골라 사용.
																	// -team
																	// coll은
																	// type ID를
																	// 넣으셔야 합니다.
			tag.setTagItemSubType(socialBoardItem.getRegisterId()); // 모듈 서브 타입
																	// - blog
																	// user id

			tag.setTagItemName(socialBoardItem.getTitle()); // 게시물 제목
			tag.setTagItemContents(socialBoardItem.getContents()); // 게시물 내용
			tag.setTagItemUrl("/socialpack/socialblog//socialBlogPopup.do?blogOwnerId="
					+ socialBoardItem.getRegisterId()
					+ "&itemId="
					+ socialBoardItem.getItemId()); // 게시물 상세화면 URL - body 화면만
													// 나와야 함. 도메인과 contextPash는
													// 빼주시기 바랍니다.
			tag.setRegisterId(socialBoardItem.getRegisterId()); // 등록자 ID
			tag.setRegisterName(socialBoardItem.getRegisterName()); // 등록자 이름
			tag.setPortalId(user.getPortalId()); // portalID

			tagService.create(tag);
		}

		// Activity Stream 추가
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG,
				IKepConstant.ACTIVITY_CODE_DOC_EDIT,
				socialBoardItem.getItemId(), socialBoardItem.getTitle());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * updateRecommendCount(java.lang.String)
	 */
	public void updateRecommendCount(String itemId) {
		socialBoardItemDao.updateRecommendCount(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * updateLinereplyCount(java.lang.String)
	 */
	public void updateLinereplyCount(String itemId) {
		socialBoardItemDao.updateLinereplyCount(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * updateMailCount(java.lang.String)
	 */
	public void updateMailCount(String itemId) {
		socialBoardItemDao.updateMailCount(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * updateMBlogCount(java.lang.String)
	 */
	public void updateMBlogCount(String itemId) {
		socialBoardItemDao.updateMBlogCount(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * updateHitCount(java.lang.String)
	 */
	public void updateHitCount(String itemId) {
		socialBoardItemDao.updateHitCount(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * adminDeleteSocialBoardItem
	 * (com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem)
	 */
	public void adminDeleteSocialBoardItem(SocialBoardItem socialBoardItem) {
		// itemId로 해서 하위 댓글 전체 삭제
		socialBoardLinereplyDao.physicalDeleteThreadByItemId(socialBoardItem
				.getItemId());

		// 전체 파일 삭제
		this.fileService.removeItemFile(socialBoardItem.getItemId());

		// 태그삭제
		tagService.delete(socialBoardItem.getItemId(),
				TagConstants.ITEM_TYPE_SOCIAL_BLOG);

		// / 게시글 추천 정보를 삭제한다.
		SocialBoardItemRecommend socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setItemId(socialBoardItem.getItemId());
		socialBoardItemRecommendDao.physicalDelete(socialBoardItemRecommend);

		// 게시글 조회 정보를 삭제한다.
		SocialBoardItemReference socialBoardItemReference = new SocialBoardItemReference();
		socialBoardItemReference.setItemId(socialBoardItem.getItemId());
		socialBoardItemReferenceDao.physicalDelete(socialBoardItemReference);

		// 글삭제
		socialBoardItemDao.physicalDelete(socialBoardItem.getItemId());

		// Activity Stream 추가
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG,
				IKepConstant.ACTIVITY_CODE_DOC_DELETE,
				socialBoardItem.getItemId(), socialBoardItem.getTitle());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * userDeleteSocialBoardItem
	 * (com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem)
	 */
	public void userDeleteSocialBoardItem(SocialBoardItem socialBoardItem) {

		// itemId로 해서 하위 댓글 전체 삭제
		socialBoardLinereplyDao.physicalDeleteThreadByItemId(socialBoardItem
				.getItemId());

		// 파일 삭제
		this.fileService.removeItemFile(socialBoardItem.getItemId());

		// 태그 삭제
		tagService.delete(socialBoardItem.getItemId(),
				TagConstants.ITEM_TYPE_SOCIAL_BLOG);

		// / 게시글 추천 정보를 삭제한다.
		SocialBoardItemRecommend socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setItemId(socialBoardItem.getItemId());
		socialBoardItemRecommendDao.physicalDelete(socialBoardItemRecommend);

		// 게시글 조회 정보를 삭제한다.
		SocialBoardItemReference socialBoardItemReference = new SocialBoardItemReference();
		socialBoardItemReference.setItemId(socialBoardItem.getItemId());
		socialBoardItemReferenceDao.physicalDelete(socialBoardItemReference);

		// 글 삭제
		socialBoardItemDao.physicalDelete(socialBoardItem.getItemId());

		// Activity Stream 추가
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG,
				IKepConstant.ACTIVITY_CODE_DOC_DELETE,
				socialBoardItem.getItemId(), socialBoardItem.getTitle());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#
	 * userDeleteSocialBoardItem
	 * (com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition
	 * )
	 */
	public List<String> selectPostingDate(
			SocialBoardItemSearchCondition socialBoardItemSearchCondition) {
		return socialBoardItemDao
				.selectPostingDate(socialBoardItemSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.
	 * Serializable)
	 */
	@Deprecated
	public SocialBoardItem read(String id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.
	 * Serializable)
	 */
	@Deprecated
	public void delete(String id) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * create(java.io.Serializable)
	 */
	@Deprecated
	public String create(SocialBoardItem socialBoardItem) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService#
	 * update(java.io.Serializable)
	 */
	@Deprecated
	public void update(SocialBoardItem socialBoardItem) {
	}

}
