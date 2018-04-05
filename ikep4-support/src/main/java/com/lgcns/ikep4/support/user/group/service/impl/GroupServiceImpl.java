/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.GroupDetail;
import com.lgcns.ikep4.util.model.UserDetail;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupServiceImpl.java 17883 2012-04-05 03:25:17Z arthes $
 */
@Service("groupService")
@Transactional
public class GroupServiceImpl extends GenericServiceImpl<Group, String> implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private IdgenService idGenService;

	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#list(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<Group> list(UserSearchCondition searchCondition) {

		Integer count = groupDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Group> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Group>(searchCondition);

		} else {

			List<Group> list = groupDao.selectAll(searchCondition);

			List<Group> returnList = new ArrayList<Group>();
			for (Group group : list) {
				if (!searchCondition.getUserLocaleCode().equals("ko")) {
					group.setGroupName(group.getGroupEnglishName());
					group.setGroupTypeName(group.getGroupTypeEnglishName());
				}
				returnList.add(group);
			}

			searchResult = new SearchResult<Group>(returnList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(Group group) {

		// groupId 생성해서 세팅
		String groupId = idGenService.getNextId();

		String leaderId = StringUtil.nvl(group.getLeaderId(), "");

		try {
			group.setGroupId(groupId);

			// Leader 정보 업데이트
			if (leaderId != null && !"".equals(leaderId)) {
				User leaderInfo = userService.read(leaderId);
				leaderInfo.setLeader("1");
				groupDao.updateLeaderInfo(leaderInfo);
			}

			// 1. Group 생성(Group 테이블에 group Insert)
			groupDao.create(group);
			// 2. pullPath 수정
			groupDao.updateFullPath(group);

			// 3. user_group 테이블에 매핑정보 Insert(Group과 User가 이미 생성되어 있어야 함)
			// 4. user 정보 update(Teamname)
			List<User> userList = group.getUserList();
			String isRepresentGroup = "0";
			if (userList != null && userList.size() != 0) {
				for (int i = 0; i < userList.size(); i++) {
					User user = userList.get(i);
					user.setGroupId(groupId);
					user.setIsRepresentGroup(isRepresentGroup);
					groupDao.addUserToGroup(user);
					groupDao.updateTeamName(user);
				}
			}

			// 5. Group 테이블에서  부모 Group의 childCount 업데이트
			groupDao.updateChildGroupCount(group.getParentGroupId(), "plus");

			return groupId;
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String createForExcel(Group group) {

		// groupId 생성해서 세팅
		String groupId = group.getGroupId();

		try {

			// 1. Group 생성(Group 테이블에 group Insert)
			groupDao.create(group);

			// 2. Group 테이블의 childCount 업데이트
			groupDao.updateChildGroupCount(group.getParentGroupId(), "plus");

			return groupId;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return groupDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#get(java
	 * .io.Serializable)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Group read(String id) {

		Group group = groupDao.get(id);
		if (group != null) {
			List groupUserList = groupDao.selectUserInGroup(id);
			group.setUserList(groupUserList);
		}

		return group;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(Group group) {

		String postParentGroupId = StringUtil.nvl(group.getParentGroupId(), "");
		String preParentGroupId = StringUtil.nvl(groupDao.get(group.getGroupId()).getParentGroupId(), "");

		String preGroupName = StringUtil.nvl(groupDao.get(group.getGroupId()).getGroupName(), "");
		String preGroupEnglishName = StringUtil.nvl(groupDao.get(group.getGroupId()).getGroupEnglishName(), "");
		String postGroupName = group.getGroupName();
		String postGroupEnglishName = group.getGroupEnglishName();

		String leaderId = group.getLeaderId();
		// Leader 정보 업데이트
		if (leaderId != null && !"".equals(leaderId)) {
			User leaderInfo = userService.read(leaderId);
			leaderInfo.setLeader("1");
			groupDao.updateLeaderInfo(leaderInfo);
		}

		// 1. Group 업데이트 전에 예전 userList, 바뀐 userList 뽑아놓기
		Group preGroup = read(group.getGroupId());
		List<User> preUserList = preGroup.getUserList();

		List<User> postUserList = group.getUserList();

		if (postUserList != null) {
			for (int i = 0; i < postUserList.size(); i++) {
				Map<String, String> userInfo = new HashMap<String, String>();
				userInfo.put("userId", postUserList.get(i).getUserId());
				userInfo.put("groupId", group.getGroupId());

				String isRepresentGroup = "0";
				
				User userTemp = new User();
				userTemp = groupDao.selectUserInfoInGroup(userInfo);
				
				if(userTemp != null) {
					isRepresentGroup = userTemp.getIsRepresentGroup();
				}
				
				postUserList.get(i).setIsRepresentGroup(isRepresentGroup);
			}
		}

		// 2. user_group 테이블의 매핑정보 업데이트, 기존 user들 삭제 후 새로 가져온 user를 insert
		// TODO 부모 그룹으로 옮기는 경우, 현재 사용자가 속한 그룹이 최상위 그룹인 경우 최상위 그룹의 부모그룹 ID는
		// null이기 때문에 sql error가 발생. 체크해서 처리하는 방안 마련해야 함
		String tempGroupId = groupDao.selectParentGroupId(group.getGroupId());

		if (tempGroupId != null && !"".equalsIgnoreCase(tempGroupId)) {
			// 새로 옮겨질 그룹 ID가 null, 공백이 아닌 경우는 그대로 진행
			if (preUserList != null) {
				groupDao.removeGroupFromUserGroup(group.getGroupId());
			}

			// 3. Group 정보 업데이트
			groupDao.update(group);

			if (postUserList != null) {
				for (int i = 0; i < postUserList.size(); i++) {
					User user = postUserList.get(i);
					groupDao.addUserToGroup(user);
				}
			}
		} else {
			// 새로 옮겨질 그룹 ID가 null, 공백인 경우는 삭제 후 재삽입
			if (preUserList != null) {
				groupDao.removeGroupFromUserGroup(group.getGroupId());
			}

			// 3. Group 정보 업데이트
			groupDao.update(group);

			if (postUserList != null) {
				for (int i = 0; i < postUserList.size(); i++) {
					User user = postUserList.get(i);
					groupDao.addUserToGroup(user);
				}
			}
		}

		// 4. childCount 업데이트(Bulk upload 시에 parent group을 변경할 수 있으므로)

		if (!preParentGroupId.equals(postParentGroupId)) {
			groupDao.updateChildGroupCount(preParentGroupId, "minus");
			groupDao.updateChildGroupCount(postParentGroupId, "plus");
		}

		/*
		if (!preGroupName.equals(postGroupName) || !preGroupEnglishName.equals(postGroupEnglishName)) {
			Map<String, String> groupInfo = new HashMap<String, String>();
			groupInfo.put("preGroupName", preGroupName);
			groupInfo.put("preGroupEnglishName", preGroupEnglishName);
			groupInfo.put("postGroupName", postGroupName);
			groupInfo.put("postGroupEnglishName", postGroupEnglishName);

			groupDao.updateUserTeamName(groupInfo);
		}
		*/
		if (!preGroupName.equals(postGroupName) || !preGroupEnglishName.equals(postGroupEnglishName)) {
			Map<String, String> groupInfo = new HashMap<String, String>();
			groupInfo.put("groupId", group.getGroupId());
			groupInfo.put("postGroupName", postGroupName);
			groupInfo.put("postGroupEnglishName", postGroupEnglishName);

			groupDao.updateUserRepresentTeamName(groupInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#updateForExcel
	 * (com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public void updateForExcel(Group group) {

		String postParentGroupId = StringUtil.nvl(group.getParentGroupId(), "");
		String preParentGroupId = StringUtil.nvl(groupDao.get(group.getGroupId()).getParentGroupId(), "");
		
		String preGroupName = StringUtil.nvl(groupDao.get(group.getGroupId()).getGroupName(), "");
		String preGroupEnglishName = StringUtil.nvl(groupDao.get(group.getGroupId()).getGroupEnglishName(), "");
		String postGroupName = group.getGroupName();
		String postGroupEnglishName = group.getGroupEnglishName();

		groupDao.update(group);

		if (!preParentGroupId.equals(postParentGroupId)) {
			groupDao.updateChildGroupCount(preParentGroupId, "minus");
			groupDao.updateChildGroupCount(postParentGroupId, "plus");
		}
		
		if (!preGroupName.equals(postGroupName) || !preGroupEnglishName.equals(postGroupEnglishName)) {
			Map<String, String> groupInfo = new HashMap<String, String>();
			groupInfo.put("groupId", group.getGroupId());
			groupInfo.put("postGroupName", postGroupName);
			groupInfo.put("postGroupEnglishName", postGroupEnglishName);

			groupDao.updateUserRepresentTeamName(groupInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#remove
	 * (java.io.Serializable)
	 */
	public void delete(Group group) {

		// 1. user_group 테이블 매핑정보 삭제(상위그룹으로 업데이트)
		// 2. user의 teamname 업데이트
		Group tempGroup = read(group.getGroupId());
		List<User> preUserList = tempGroup.getUserList();
		String tempParentGroupId = groupDao.selectParentGroupId(group.getGroupId());

		if (tempParentGroupId != null && !"".equalsIgnoreCase(tempParentGroupId)) {
			if (preUserList != null) {
				for (int i = 0; i < preUserList.size(); i++) {
					User user = preUserList.get(i);
					groupDao.moveUserToParentGroup(user);
					user.setGroupId(group.getParentGroupId());
					groupDao.updateTeamName(user);
				}
			}
		} else {
			if (preUserList != null) {
				// groupDao.removeGroupFromUserGroup(group.getGroupId());

				for (int i = 0; i < preUserList.size(); i++) {
					User user = preUserList.get(i);
					// groupId가 없으므로 쿼리에서 대표그룹으로 세팅한다.
					groupDao.updateTeamName(user);
				}
			}
		}

		// 관련 매핑 정보 삭제
		groupDao.removeGroupFromUserGroup(group.getGroupId());

		groupDao.deleteGroupFromRole(group.getGroupId());

		groupDao.deleteGroupFromSysPermission(group.getGroupId());

		groupDao.deleteGroupFromConPermission(group.getGroupId());

		// 3. Group 정보 삭제
		groupDao.remove(group.getGroupId());

		// 4. childcount 업데이트
		groupDao.updateChildGroupCount(group.getParentGroupId(), "minus");

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#updateGroupMove
	 * (java.lang.String, com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public void updateGroupMove(String prevSortOrder, String orgParentGroupId, Group currGroup) {
		
		

		// 1. sortorder 업데이트
		groupDao.updateSortOrder(prevSortOrder);

		// 2. 이동된 그룹 정보 업데이트(pullPath 수정 포함)
		Group tempGroup = read(currGroup.getGroupId());
		currGroup.setGroupTypeId(tempGroup.getGroupTypeId());
		
		groupDao.updateMove(currGroup);
		groupDao.updateFullPath(currGroup);
		
		
		// 3. 이동된 상위 그룹정보의  childCount Plus
		groupDao.updateChildGroupCount(currGroup.getParentGroupId(),"plus");
		
		// 4. 이동되기전  상위 그룹정보의  childCount Minus
		groupDao.updateChildGroupCount(orgParentGroupId,"minus");

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.service.GroupService#
	 * selectOrgGroupByGroupTypeId
	 * (com.lgcns.ikep4.support.user.group.model.Group)
	 */
	public List<Group> selectOrgGroupByGroupTypeId(Group group) {

		return groupDao.selectOrgGroupByGroupTypeId(group);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#selectOrgGroup(
	 * java.lang.Object)
	 */
	public List<Group> selectOrgGroup(Group group) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		group.setPortalId(portalId);
		return groupDao.selectOrgGroup(group);
	}
	
	public List<Group> selectOrgGroupSp(Group group) {

		String portalId = (String) getRequestAttribute("ikep.portalId");
		group.setPortalId(portalId);
		return groupDao.selectOrgGroupSp(group);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#
	 * selectCollaborationGroup(java.lang.Object)
	 */
	public List<Group> selectCollaborationGroup(String groupId) {
		return groupDao.selectCollaborationGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#selectSnsGroup(
	 * java.lang.Object)
	 */
	public List<Group> selectSnsGroup(String groupId) {
		return groupDao.selectSnsGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#selectGroupSearch
	 * (java.lang.String, java.lang.String)
	 */
	public List<Group> selectGroupSearch(String localeCode, String keyword) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupName", keyword);
		// if (localeCode.equals("ko")) {
		// param.put("groupName", keyword);
		// } else {
		// param.put("groupEnglishName", keyword);
		// }

		return groupDao.selectGroupSearch(param);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#selectUserRootGroup
	 * (java.util.Map)
	 */
	public Group selectUserRootGroup(Map<String, Object> map) {
		return groupDao.selectUserRootGroup(map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#selectUserGroup
	 * (java.util.Map)
	 */
	public List<Group> selectUserGroupWithHierarchy(Map<String, Object> map) {
		return groupDao.selectUserGroupWithHierarchy(map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#selectUserGroup2
	 * (java.util.Map)
	 */
	public List<Group> selectUserGroup(Map<String, Object> map) {
		return groupDao.selectUserGroup(map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return groupDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#goUp(java.util
	 * .Map)
	 */
	public void goUp(Map<String, String> map) {

		// 상위 노드를 curSortOrder로 업데이트
		Group prevGroup = groupDao.get((String) map.get("prevNodeGroupId")); // 상위
		if(Integer.parseInt(prevGroup.getSortOrder()) == Integer.parseInt(map.get("curSortOrder"))){
			if(map.get("curSortOrder").equals("0000000000001")){
				int tmpCurSortOrder = Integer.parseInt(map.get("curSortOrder"))+1;
				prevGroup.setSortOrder("000000000000"+Integer.toString(tmpCurSortOrder));
			}else{
				prevGroup.setSortOrder((String) map.get("curSortOrder"));
			}
		}else{
			prevGroup.setSortOrder((String) map.get("curSortOrder"));
		}
		

		groupDao.update(prevGroup);

		// 현재 노드를 prevSortOrder로 업데이트
		Group curGroup = groupDao.get((String) map.get("curNodeGroupId")); // 현재
																			// 그룹
		
		if(Integer.parseInt(curGroup.getSortOrder()) == Integer.parseInt(map.get("prevSortOrder"))){
			if(map.get("prevSortOrder").equals("0000000000001")){
				curGroup.setSortOrder((String) map.get("prevSortOrder"));
			}else{
				int tmpPrevSortOrder = Integer.parseInt(map.get("prevSortOrder"))-1;
				if(tmpPrevSortOrder > 9){
					curGroup.setSortOrder("00000000000"+Integer.toString(tmpPrevSortOrder));
				}else{
					curGroup.setSortOrder("000000000000"+Integer.toString(tmpPrevSortOrder));
				}
			}
		}else{
			curGroup.setSortOrder((String) map.get("prevSortOrder"));
		}
		
		groupDao.update(curGroup);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#goDown(java.util
	 * .Map)
	 */
	public void goDown(Map<String, String> map) {

		// 상위 노드를 curSortOrder로 업데이트
		Group nextGroup = groupDao.get((String) map.get("nextNodeGroupId")); // 상위
																				// 그룹
		
		if(Integer.parseInt(nextGroup.getSortOrder()) == Integer.parseInt(map.get("curSortOrder"))){
			if(map.get("curSortOrder").equals("0000000000001")){
				int tmpCurSortOrder = Integer.parseInt(map.get("curSortOrder"))+1;
				nextGroup.setSortOrder("000000000000"+Integer.toString(tmpCurSortOrder));
			}else{
				nextGroup.setSortOrder((String) map.get("curSortOrder"));
			}
		}else{
			nextGroup.setSortOrder((String) map.get("curSortOrder"));
		}

		groupDao.update(nextGroup);

		// 현재 노드를 prevSortOrder로 업데이트
		Group curGroup = groupDao.get((String) map.get("curNodeGroupId")); // 현재
																			// 그룹
		
		if(Integer.parseInt(curGroup.getSortOrder()) == Integer.parseInt(map.get("nextSortOrder"))){
			if(map.get("nextSortOrder").equals("0000000000001")){
				int tmpCurSortOrder = Integer.parseInt(map.get("nextSortOrder"))+1;
				curGroup.setSortOrder("000000000000"+Integer.toString(tmpCurSortOrder));
			}else{
				curGroup.setSortOrder((String) map.get("nextSortOrder"));
			}
		}else{
			curGroup.setSortOrder((String) map.get("nextSortOrder"));
		}

		groupDao.update(curGroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.service.GroupService#
	 * removeGroupFromUserGroup(java.lang.String)
	 */
	public void removeGroupFromUserGroup(String groupId) {

		groupDao.removeGroupFromUserGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.group.service.GroupService#selectUserGroupOther
	 * (java.util.Map)
	 */
	public List<Group> selectUserGroupOther(Map<String, Object> map) {
		return groupDao.selectUserGroupOther(map);
	}

	/*
	 * 사용자의 전체 그룹 경로 가져오기
	 * @param userInfo
	 * @return groupFullPath
	 */
	public String getGroupFullPath(Map<String, Object> userInfo) {
		return groupDao.selectGroupFullPath(userInfo);
	}
	
	/*
	 * 선택된 그룹 전체 경로 가져오기
	 * @param userInfo
	 * @return groupFullPath
	 */
	public String getGroupFullPathByGroup(Map<String, Object> groupInfo) {
		return groupDao.selectGroupFullPathByGroup(groupInfo);
	}
	
	/*
	 * 그룹 루트 갯수 구하기
	 * @param map
	 * @return rootGroupCount
	 */
	public int getRootGroupCount(Map<String, String> map) {
		return groupDao.getRootGroupCount(map);
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	public void updateSapGroup(List<GroupDetail> groupDetailList)
	{
		String tmp = "tmp";
		groupDao.deleteTmpGroup(tmp);
		
		for (GroupDetail groupDetail : groupDetailList) {
			groupDao.updateSapGroup(groupDetail);
	    }
		
	}
	
	public String updateEpTableFromTmpGroupTable()
	{
		return groupDao.updateEpTableFromTmpGroupTable();
	
	}

}
