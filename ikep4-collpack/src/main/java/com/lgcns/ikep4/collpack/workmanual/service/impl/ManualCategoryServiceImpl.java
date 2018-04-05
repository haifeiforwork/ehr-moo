/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalUserDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualCategoryDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ReadGroupDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ReadUserDao;
import com.lgcns.ikep4.collpack.workmanual.dao.WriteUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.collpack.workmanual.model.ReadGroup;
import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.collpack.workmanual.service.ManualCategoryService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.base.tree.TreeManager;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.base.tree.TreeNodeAttr;
import com.lgcns.ikep4.support.base.tree.TreeNodeData;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualCategoryServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class ManualCategoryServiceImpl extends GenericServiceImpl<ManualCategory, String> implements ManualCategoryService {
	@Autowired
	private ManualCategoryDao manualCategoryDao;
	@Autowired
	private ReadUserDao readUserDao;
	@Autowired
	private ReadGroupDao readGroupDao;
	@Autowired
	private ApprovalUserDao approvalUserDao;
	@Autowired
	private WriteUserDao writeUserDao;
	

	@Autowired
	private IdgenService idgenService;
	
	public String create(ManualCategory manualCategory) {
		return manualCategoryDao.create(manualCategory);
	}

	public boolean exists(String categoryId) {
		return manualCategoryDao.exists(categoryId);
	}

	public ManualCategory read(String categoryId) {
		return manualCategoryDao.get(categoryId);
	}

	public void delete(String categoryId) {
		manualCategoryDao.remove(categoryId);
	}

	public void update(ManualCategory manualCategory) {
		manualCategoryDao.update(manualCategory);
	}
	////////////////////////////////////
	
	//Portal 포함하여 조회
	public ManualCategory getManualCategory(String categoryId, String portalId) {
		ManualCategory manualCategory = new ManualCategory();
		manualCategory.setCategoryId(categoryId);
		manualCategory.setPortalId(portalId);
		return manualCategoryDao.getManualCategory(manualCategory);
	}
	//Portal로 조회
	public List<ManualCategory> listManualCategoryByPortalId(String portalId) {
		return manualCategoryDao.listManualCategoryByPortalId(portalId);
	}
	//수정
	public void updateManualCategory(ManualCategory manualCategory, String readUsers, String readGroups, String writeUsers, String approvalUsers) {		
		//카테고리 수정
		manualCategoryDao.update(manualCategory);

		//기존 권한 정보 삭제
		readUserDao.removeByCategoryId(manualCategory.getCategoryId());
		readGroupDao.removeByCategoryId(manualCategory.getCategoryId());
		approvalUserDao.removeByCategoryId(manualCategory.getCategoryId());
		writeUserDao.removeByCategoryId(manualCategory.getCategoryId());
		
		if(manualCategory.getReadPermission() == 0) { //비공개이면
			//문서 조회 사용자 저장		
			if(!StringUtils.isEmpty(readUsers)) {	
				String[] readUserArr = readUsers.split(",");
				for(int i=0; i<readUserArr.length; i++) {
					ReadUser readUser = new ReadUser();
					readUser.setCategoryId(manualCategory.getCategoryId());
					readUser.setReadUserId(readUserArr[i]);
					readUserDao.create(readUser);
				}
			}
			
			//문서 조회 조직 저장			
			if(!StringUtils.isEmpty(readGroups)) {
				String[] readGroupArr = readGroups.split(",");
				for(int i=0; i<readGroupArr.length; i++) {
					ReadGroup readGroup = new ReadGroup();
					readGroup.setCategoryId(manualCategory.getCategoryId());
					readGroup.setReadGroupId(readGroupArr[i]);
					readGroupDao.create(readGroup);
				}
			}
		}
		
		//문서 결재자 저장			
		if(!StringUtils.isEmpty(approvalUsers)) {	
			String[] approvalUserArr = approvalUsers.split(",");
			for(int i=0; i<approvalUserArr.length; i++) {
				ApprovalUser approvalUser = new ApprovalUser();
				approvalUser.setCategoryId(manualCategory.getCategoryId());
				approvalUser.setApprovalUserId(approvalUserArr[i]);
				approvalUser.setApprovalLine(i+1);
				approvalUserDao.create(approvalUser);
			}
		}
		
		//문서 담당자 저장		
		String[] writeUserArr = writeUsers.split(",");
		for(int i=0; i<writeUserArr.length; i++) {
			WriteUser writeUser = new WriteUser();
			writeUser.setCategoryId(manualCategory.getCategoryId());
			writeUser.setWriteUserId(writeUserArr[i]);
			writeUserDao.create(writeUser);
		}
	}
	//삭제
	public void deleteManualCategory(String categoryId, String portalId) {
		ManualCategory manualCategory = new ManualCategory();
		manualCategory.setCategoryId(categoryId);
		manualCategory.setPortalId(portalId);
		
		//매뉴얼 결재자 정보 삭제
		manualCategoryDao.removeApprovalLineByCategoryId(manualCategory);
		//매뉴얼 결재정보 삭제
		manualCategoryDao.removeApprovalByCategoryId(manualCategory);
		//댓글 정보 삭제
		manualCategoryDao.removeLinereplyByCategoryId(manualCategory);
		//조회 정보 삭제
		manualCategoryDao.removeReferenceByCategoryId(manualCategory);
		//버젼 정보 삭제
		manualCategoryDao.removeManualVersionByCategoryId(manualCategory);
		//매뉴얼 정보 삭제
		manualCategoryDao.removeManualByCategoryId(manualCategory);
		//조회 사용자 정보 삭제
		manualCategoryDao.removeReadUserByCategoryId(manualCategory);
		//조회 조직 삭제
		manualCategoryDao.removeReadGroupByCategoryId(manualCategory);
		//문서 결재자 삭제
		manualCategoryDao.removeApprovalUserByCategoryId(manualCategory);
		//문서 담당자 삭제
		manualCategoryDao.removeWriteUserByCategoryId(manualCategory);
		//카테고리 삭제
		manualCategoryDao.removeManualCategoryByCategoryId(manualCategory);
	}
	
	
	
	//Category 데이터를 트리에 보내는 자료로 변경
	private List<TreeNode> listCategory2TreeNodes(List<ManualCategory> categoryList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (ManualCategory item : categoryList ) {
			treeNodeList.add(category2TreeNode(item));
		}
	
		return treeNodeList;
	}
	//카테고리 데이터를 트리 테이터로 변경
	private TreeNode category2TreeNode(ManualCategory category) {
		// Attr 생성
		TreeNodeAttr tna = new TreeNodeAttr();
		tna.setId(category.getCategoryId());

		// Data 생성
		TreeNodeData tnd = new TreeNodeData();
		tnd.setTitle(category.getCategoryName());
		tnd.setIcon("dept");

		// Node 생성
		TreeNode tn = new TreeNode();
		tn.setAttr(tna);
		tn.setData(tnd);

		if (0 < category.getChildCount()) {
			tn.setState("closed");
		}

		return tn;
	}
	
	//트리에서 사용하는 노드 JSON - 최상위 카테고리 조회
	public String listTopCategory(User user) {
		ManualCategory manualCategory = manualCategoryDao.getRootCategory(user.getPortalId());
		if (manualCategory == null) {
			manualCategory = new ManualCategory();
			manualCategory.setCategoryId("ROOT_CATEGORY");
			manualCategory.setCategoryName("ROOT_CATEGORY");
			manualCategory.setCategoryParentId("ROOT_CATEGORY");
			manualCategory.setPortalId(user.getPortalId());
			manualCategory.setRegisterId(user.getUserId());
			manualCategory.setRegisterName(user.getUserName());
			manualCategoryDao.create(manualCategory);
		}
		
		List<ManualCategory> manualCategoryList = manualCategoryDao.listChildCategory(manualCategory.getCategoryId());
		List<TreeNode> treeNodeList = listCategory2TreeNodes(manualCategoryList);

		return TreeManager.getJSON(treeNodeList);
	}
	//하위 카테고리 노드 조회
	public List<TreeNode> listChildCategory(String categoryParentId) {
		List<ManualCategory> manualCategoryList = manualCategoryDao.listChildCategory(categoryParentId);
		return listCategory2TreeNodes(manualCategoryList);
	}
	//신규 생성
	public String createManualCategory(ManualCategory manualCategory, String readUsers, String readGroups, String writeUsers, String approvalUsers) {
		String categoryId = idgenService.getNextId();
		
		//카테고리 저장
		manualCategory.setCategoryId(categoryId);
		if(StringUtils.isEmpty(manualCategory.getCategoryParentId())) {
			manualCategory.setSortOrder(1);
		} else {
			int count = manualCategoryDao.countChildren(manualCategory.getCategoryParentId());
			manualCategory.setSortOrder(count+1);
		}
		manualCategoryDao.create(manualCategory);
		
		if(manualCategory.getReadPermission() == 0) { //비공개이면
			//문서 조회 사용자 저장		
			if(!StringUtils.isEmpty(readUsers)) {
				String[] readUserArr = readUsers.split(",");
				for(int i=0; i<readUserArr.length; i++) {
					ReadUser readUser = new ReadUser();
					readUser.setCategoryId(categoryId);
					readUser.setReadUserId(readUserArr[i]);
					readUserDao.create(readUser);
				}
			}
			
			//문서 조회 조직 저장			
			if(!StringUtils.isEmpty(readGroups)) {
				String[] readGroupArr = readGroups.split(",");
				for(int i=0; i<readGroupArr.length; i++) {
					ReadGroup readGroup = new ReadGroup();
					readGroup.setCategoryId(categoryId);
					readGroup.setReadGroupId(readGroupArr[i]);
					readGroupDao.create(readGroup);
				}
			}
		}
		
		//문서 결재자 저장			
		if(!StringUtils.isEmpty(approvalUsers)) {
			String[] approvalUserArr = approvalUsers.split(",");
			for(int i=0; i<approvalUserArr.length; i++) {
				ApprovalUser approvalUser = new ApprovalUser();
				approvalUser.setCategoryId(categoryId);
				approvalUser.setApprovalUserId(approvalUserArr[i]);
				approvalUser.setApprovalLine(i+1);
				approvalUserDao.create(approvalUser);
			}
		}
		
		//문서 담당자 저장	
		String[] writeUserArr = writeUsers.split(",");
		for(int i=0; i<writeUserArr.length; i++) {
			WriteUser writeUser = new WriteUser();
			writeUser.setCategoryId(categoryId);
			writeUser.setWriteUserId(writeUserArr[i]);
			writeUserDao.create(writeUser);
		}		
		
		return TreeManager.getJSON(category2TreeNode(manualCategory));
	}
	//트리 이동
	public void moveCategory(String sourceId, String targetParentId, String targetSortOrder) {
		ManualCategory manualCategory = manualCategoryDao.get(sourceId);
		
		//원본 순번 바꾸기
		Map<String, String> map = new HashMap<String, String>();
		map.put("categoryParentId", manualCategory.getCategoryParentId());
		map.put("sortOrder", manualCategory.getSortOrder().toString());
		manualCategoryDao.updateMoveCategoryInSource(map);
		
		//타겟 순번 바꾸기
		map.put("categoryParentId", targetParentId);
		map.put("sortOrder", targetSortOrder);
		manualCategoryDao.updateMoveCategoryInTarget(map);
		
		//이동 카테고리 변경
		map.put("categoryId", sourceId);
		manualCategoryDao.updateMoveCategoryInMine(map);
	}
}
