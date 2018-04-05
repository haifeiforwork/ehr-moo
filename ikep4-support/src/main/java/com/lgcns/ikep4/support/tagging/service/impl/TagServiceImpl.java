/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.dao.TagDao;
import com.lgcns.ikep4.support.tagging.dao.TagDictionaryDao;
import com.lgcns.ikep4.support.tagging.dao.TagItemDao;
import com.lgcns.ikep4.support.tagging.dao.TagLinkDao;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: TagServiceImpl.java 16910 2011-10-27 02:22:12Z giljae $
 */
@Service("tagService")
public class TagServiceImpl extends GenericServiceImpl<Tag, String> implements TagService {

	@Autowired
	private TagDao tagDao;

	@Autowired
	private TagDictionaryDao tagDictionaryDao;

	@Autowired
	private TagItemDao tagItemDao;

	@Autowired
	private TagLinkDao tagLinkDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Override
	public String create(Tag tag) {

		if (StringUtil.isEmpty(tag.getTagName())) { // 태그 없으면 null 리턴
			return null;
		}

		// 태그item 입력 - 등록되어있지 않으면 등록
		Tag tagItem = tagItemDao.read(tag.getTagItemId(), tag.getTagItemType());

		String contents = tag.getTagItemContents();

		// 요약보기
		contents = StringUtil.cutString(StringUtil.extractTextFormHTML(tag.getTagItemContents()),
				TagConstants.CONTENT_SUMMAR_COUNT, "");

		tag.setTagItemContents(contents);

		if (tagItem == null) {
			tagItemDao.create(tag);
		} else {
			tagItemDao.update(tag);

		}

		// 링크 삭제
		tagLinkDao.removeByItem(tag.getTagItemId(), tag.getTagItemType());

		String[] tags = tag.getTagName().split(",");

		// 중복 제거
		List<String> tagList = new ArrayList<String>();

		Properties prop = PropertyLoader.loadProperties("/configuration/tagging-conf.properties");
		int limitVal = Integer.parseInt(prop.getProperty("tag.create.limit.per")); // 태그
																					// 등록
																					// 허용수
		int j = 0;

		for (String tagName : tags) {

			if (j >= limitVal) {
				break;
			} // 허용 개수보다 많으면 리턴

			tagName = tagName.trim();

			if (!tagList.contains(tagName) && !StringUtil.isEmpty(tagName)) {
				tagList.add(tagName);
			}
			j++;
		}

		int index = 0;
		for (String tagName : tagList) { // 태그 수만큼

			tag.setTagName(tagName);

			// 태그 사전 입력 - 등록되어있지 않으면 등록
			Tag tagDicTionary = tagDictionaryDao.readByTagName(tag.getTagName(), tag.getPortalId());

			if (tagDicTionary == null) {
				tag.setTagId(idgenService.getNextId());
				tagDictionaryDao.create(tag);
			} else {
				tag.setTagId(tagDicTionary.getTagId());
			}

			// 태그 링크 입력
			tag.setSortOrder(index);
			tagLinkDao.create(tag);

			index++;

		}

		return null;
	}

	public List<Tag> list(Tag tag) {
		List<Tag> list = new ArrayList<Tag>();

		if (!(tag.getUserIdList() != null && tag.getUserIdList().size() == 0)) {
			list = tagDao.listCloud(itemTypes(tag));
		}

		return list;
	}

	public List<Tag> listTagByItemId(String tagItemId, String tagItemType) {
		return tagDao.listByItem(tagItemId, tagItemType);
	}

	public List<Tag> listTagSearch(Tag tag) {

		List<Tag> list = tagDao.listSearch(itemTypes(tag));

		return list;
	}

	public int getCountSearch(Tag tag) {

		int count = tagDao.getCountSearch(itemTypes(tag));

		return count;
	}

	public List<Tag> listTagByItemType(Tag tag) {

		tag.setStartRowIndex(0);

		List<Tag> list = tagDao.listCloud(itemTypes(tag));

		return list;
	}

	public Map<String, Object> listItemId(String itemId, String itemType, int pageIndex, int pagePer) {

		Tag tag = new Tag();
		tag.setTagItemId(itemId);
		tag.setTagItemType(itemType);
		tag.setPageIndex(pageIndex);
		tag.setPagePer(pagePer);

		return listItemId(tag);
	}

	public Map<String, Object> listItemIdByWorkspace(String workspaceId, String itemId, String itemType, int pageIndex,
			int pagePer) {

		Tag tag = new Tag();
		tag.setTagItemId(itemId);
		tag.setTagItemType(itemType);
		tag.setPageIndex(pageIndex);
		tag.setPagePer(pagePer);
		tag.setTagItemSubType(workspaceId);

		return listItemIdByWorkspace(tag);
	}

	public Map<String, Object> listItemIdByWorkspace(Tag tag) {

		int count = tagDao.getCountListItemId(tag);

		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(tag.getPageIndex());
		searchCondition.setPagePerRecord(tag.getPagePer());

		searchCondition.terminateSearchCondition(count);

		tag.setEndRowIndex(searchCondition.getEndRowIndex());
		tag.setStartRowIndex(searchCondition.getStartRowIndex());

		List<String> itemIdList = tagDao.listItemId(tag);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", itemIdList);
		map.put("pageCondition", searchCondition);

		return map;
	}

	public Map<String, Object> listItemId(String tagId, String itemId, String itemType, int pageIndex, int pagePer) {

		Tag tag = new Tag();
		tag.setTagItemId(itemId);
		tag.setTagItemType(itemType);
		tag.setTagId(tagId);
		tag.setPageIndex(pageIndex);
		tag.setPagePer(pagePer);

		return listItemId(tag);
	}

	public Map<String, Object> listItemId(Tag tag) {

		int count = tagDao.getCountListItemId(tag);

		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(tag.getPageIndex());
		searchCondition.setPagePerRecord(tag.getPagePer());

		searchCondition.terminateSearchCondition(count);

		tag.setEndRowIndex(searchCondition.getEndRowIndex());
		tag.setStartRowIndex(searchCondition.getStartRowIndex());

		List<String> itemIdList = tagDao.listItemId(tag);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", itemIdList);
		map.put("pageCondition", searchCondition);

		return map;
	}

	public Map<String, Object> listItemByTagList(List<Tag> tagList, int index, int pagePer) {

		List<String> tagIdList = new ArrayList<String>();
		for (Tag tag : tagList) {
			tagIdList.add(tag.getTagId());
		}

		Tag tag = new Tag();
		tag.setTagIdList(tagIdList);
		tag.setIsBbs(TagConstants.IS_BBS); // 게시판성 자료만 - 프로파일 자료 제외

		/*
		if(tagIdList.size() > 0){
			tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
		}
		 */
		int count = tagDao.getCountSearch(tag);

		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(index);
		searchCondition.setPagePerRecord(pagePer);

		searchCondition.terminateSearchCondition(count);

		tag.setEndRowIndex(searchCondition.getEndRowIndex());
		tag.setStartRowIndex(searchCondition.getStartRowIndex());

		List<Tag> tagItemList = tagDao.listSearch(tag);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", tagItemList);

		return map;
	}

	public int getCount(Tag tag) {

		int count = 0;

		if (!(tag.getUserIdList() != null && tag.getUserIdList().size() == 0)) {
			count = tagDao.readCount(itemTypes(tag));
		}

		return count;
	}

	public Map<String, Object> listItemAsCount(Tag tag, int index, int pagePer) {

		int count = tagDao.getCountSearch(tag);

		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(index);
		searchCondition.setPagePerRecord(pagePer);

		searchCondition.terminateSearchCondition(count);

		tag.setEndRowIndex(searchCondition.getEndRowIndex());
		tag.setStartRowIndex(searchCondition.getStartRowIndex());

		List<Tag> tagItemList = tagDao.listSearch(tag);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", tagItemList);

		return map;

	}

	@Override
	public void update(Tag object) {
		tagItemDao.update(object);
	}

	public void delete(String tagItemId, String tagItemType) {
		tagLinkDao.removeByItem(tagItemId, tagItemType);

		tagItemDao.remove(tagItemId, tagItemType);

	}

	/**
	 * item types들이 있는지 계산
	 * 
	 * @param tag
	 * @return
	 */
	public Tag itemTypes(Tag tag) {

		tag.setTagItemTypes(null);

		// itemType가 여러개 이면
		if (tag.getTagItemType() != null && tag.getTagItemType().indexOf(",") > -1) {

			String[] itemTypes = tag.getTagItemType().split(",");

			boolean isWork = false; // workspace가 있는지

			for (String itemType : itemTypes) {
				if (itemType.equals(TagConstants.ITEM_TYPE_WORKSPACE)) { // workspace가
																			// 있으면
																			// itemID
																			// 구해서
																			// 합쳐줌.

					List<Tag> teamList = listTeamType(tag.getPortalId());

					String[] listItemTypes = new String[teamList.size()];

					for (int i = 0; i < teamList.size(); i++) {
						Tag team = teamList.get(i);
						listItemTypes[i] = team.getTeamTypeId();
					}

					String[] tmpItemTypes = new String[teamList.size() + itemTypes.length];

					System.arraycopy(itemTypes, 0, tmpItemTypes, 0, itemTypes.length);
					System.arraycopy(listItemTypes, 0, tmpItemTypes, itemTypes.length, listItemTypes.length);

					tag.setTagItemTypes(tmpItemTypes);

					isWork = true;

				}
			}

			if (!isWork) { // 없으면 기존 item type만 넣음.
				tag.setTagItemTypes(itemTypes);
			}

		}

		// 오늘 날짜 조회
		if (tag.getLimitDate() != null && tag.getLimitDate().equals("1")) {
			Date date = timeZoneSupportService.convertTimeZone();
			tag.setTimeZoneDate(date);
		}

		return tag;
	}

	public List<Tag> listTeamType(String portalId) {
		return tagDao.listTeamType(portalId);
	}

	public List<Tag> listItemCount(String tagItemId, String tagItemType) {
		return tagDao.listItemCount(tagItemId, tagItemType);
	}

	public List<Tag> listItemAsUserByTagId(Tag tag) {
		return tagDao.listItemAsUserByTagId(tag);
	}

	public int getCountListItemAsUserByTagId(Tag tag) {
		return tagDao.getCountListItemAsUserByTagId(tag);
	}

	public List<String> listItemIdAsRandom(Tag tag) {
		return tagDao.listItemIdAsRandom(tag);
	}

}
