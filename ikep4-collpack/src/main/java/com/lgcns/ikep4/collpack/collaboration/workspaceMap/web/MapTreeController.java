/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.WorkspaceMapAdminService;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.tree.TreeNodeAttrWorkspaceMap;
import com.lgcns.ikep4.collpack.common.tree.TreeManager;
import com.lgcns.ikep4.collpack.common.tree.TreeNode;
import com.lgcns.ikep4.collpack.common.tree.TreeNodeData;
import com.lgcns.ikep4.framework.web.BaseController;
//import com.lgcns.ikep4.collpack.common.tree.TreeNodeAttr;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map 에서 사용하는 Custom Controller
 * (좌측 트리메뉴에서 사용하는 모듈)
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: MapTreeController.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class MapTreeController extends BaseController {


	@Autowired
	protected WorkspaceMapAdminService workspaceMapAdminService;

	/**
	 * 사용자 세션  정보
	 * @return
	 */
	protected User getSessionUser() {
		return (User)this.getRequestAttribute("ikep.user");
	}

	/**
	 * Root Category 반환
	 * @param user
	 * @return
	 */
	protected WorkspaceMap getRootCategory(User user) {
		// 만약 Root Category가 없을시 자동생성하므로 세션정보를 같이 넘겨야 한다.
		WorkspaceMap workspaceMap = new WorkspaceMap();
		workspaceMap.setRegisterId(user.getUserId());
		workspaceMap.setRegisterName(user.getUserName());

		// Root Category
		WorkspaceMap rootCategory = null;//workspaceMapAdminService.readCreateRootCategory(workspaceMap);

		return rootCategory;
	}

	/**
	 * 트리에서 사용하는 노드 JSON
	 * (최상위 카테고리들)
	 * @param user
	 * @return
	 */
	protected String getMenuLevelOneCategorysJSON(User user) {
		// Root Category
		//WorkspaceMap workspaceMap= getRootCategory(user);

		// 카테고리 root 자식들
		//List<WorkspaceMap> workspaceMapList = workspaceMapAdminService.listAndChildCountByCategoryParentId(workspaceMap.getMapId());
		//List<TreeNode> treeNodeList = listMenuCategory2TreeNodes(workspaceMapList);

		//String categoryJSON = TreeManager.getJSON(treeNodeList);
		return TreeManager.getJSON(null);

	}

	/**
	 * Category 데이터를 트리에 보내는 자료로 변경
	 * @param categoryList
	 * @return
	 */
	protected List<TreeNode> listMenuCategory2TreeNodes(List<WorkspaceMap> workspaceMapList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		//for (WorkspaceMap item : workspaceMapList ) {
			//treeNodeList.add(menuCategory2TreeNode(item));
		//}
	
		return treeNodeList;
	}

	/**
	 * 카테고리 데이터를 트리 테이터로 변경
	 * @param category
	 * @return
	 */
	/**private TreeNode makeMenuTreeNode(WorkspaceMap workspaceMap) {
		// Attr 생성
		TreeNodeAttr tna = new TreeNodeAttr();
		tna.setId(workspaceMap.getWorkspaceId());

		// Data 생성
		TreeNodeData tnd = new TreeNodeData();
		tnd.setTitle(workspaceMap.getMapName());
		tnd.setIcon("dept");

		// Node 생성
		TreeNode tn = new TreeNode();
		tn.setAttr(tna);
		tn.setData(tnd);
		if (0 < workspaceMap.getChildCount()) {
			tn.setState("closed");
		}

		return tn;
	}**/


	/**
	 * 카테고리 데이터를 트리 테이터로 변경
	 * @param category
	 * @return
	 */
	protected TreeNode makeTreeNode(WorkspaceMap workspaceMap) {
		// Attr 생성
		TreeNodeAttrWorkspaceMap tna = new TreeNodeAttrWorkspaceMap();
		tna.setId(workspaceMap.getMapId());
		tna.setPid(workspaceMap.getMapParentId());
		tna.setMapDescription(workspaceMap.getMapDescription());
		tna.setOrder(workspaceMap.getSortOrder());
		tna.setTags(workspaceMap.getTags());
		tna.setWorkspaceId(workspaceMap.getWorkspaceId());
		// Data 생성
		TreeNodeData tnd = new TreeNodeData();
		tnd.setTitle(workspaceMap.getMapName());
		tnd.setIcon("dept");

		// Node 생성
		TreeNode tn = new TreeNode();
		tn.setAttr(tna);
		tn.setData(tnd);
		
		if (workspaceMap.getChildCount()!=null && 0 < workspaceMap.getChildCount()) {
			tn.setState("closed");
		}

		return tn;
	}

	/**
	 * Map 데이터를 트리에 보내는 자료로 변경
	 * @param categoryList
	 * @return
	 */
	protected List<TreeNode> makeListMapTreeNodes(List<WorkspaceMap> workspaceMapList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (WorkspaceMap item : workspaceMapList ) {
			treeNodeList.add(makeTreeNode(item));
		}
	
		return treeNodeList;
	}

}
