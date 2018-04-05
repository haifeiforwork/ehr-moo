/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService;
import com.lgcns.ikep4.collpack.knowledgehub.tree.TreeNodeAttrKnowledgeMap;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.base.tree.TreeManager;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.base.tree.TreeNodeAttr;
import com.lgcns.ikep4.support.base.tree.TreeNodeData;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map 에서 사용하는 Custom Controller
 * (좌측 트리메뉴에서 사용하는 모듈)
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: CustomTreeController.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class CustomTreeController extends BaseController {

	/**
	 * 세션에 저장된 유저 정보 Attribute ID
	 */
	private static final String SESSION_ATTRIBUTE_USER = "ikep.user";

	/**
	 * 세션에 저장된 포털 정보 Attribute ID
	 */
	private static final String SESSION_ATTRIBUTE_PORTAL = "ikep.portal";

	/**
	 * 관리자 권한 리소스명칭
	 */
	protected static final String SYSTEM_NAME = "KMap";

	/**
	 * 사용자 정보
	 * @return User
	 */
	protected User getSessionUser() {
		return (User)this.getRequestAttribute(SESSION_ATTRIBUTE_USER);
	}

	/**
	 * 포털 정보
	 * @return Portal
	 */
	protected Portal getSessionPortal() {
		return (Portal)this.getRequestAttribute(SESSION_ATTRIBUTE_PORTAL);
	}

	@Autowired
	private ACLService aclService;

	/**
	 * 관리자 권한 (모듈 Knowledge Map의 관리자 권한)
	 * @param userId
	 * @return boolean
	 */
	protected boolean isAdmin(String userId) {
		return aclService.isSystemAdmin(SYSTEM_NAME, userId);
	}

	/**
	 * 권한체크<br/>
	 * 관리자 권한이 없으면 예외발생 (IKEP4AjaxException)
	 */
	protected void checkAdmin() {
		if (!isAdmin(getSessionUser().getUserId())) {
			throw new IKEP4AjaxException("Access Denied", new Exception());
		}
	}

	/**
	 * 권한체크<br/>
	 * 관리자 권한이 없으면 예외발생 (IKEP4AjaxException)
	 * @param userId
	 */
	protected void checkAdmin(String userId) {
		if (!isAdmin(userId)) {
			throw new IKEP4AjaxException("Access Denied", new Exception());
		}
	}

	/**
	 * 리소스번들에서 메시지 값 조회 <br/>
	 * 현재 세션의 Locale 을 기준으로 조회
	 * @param code - 리소스번들의 코드값
	 * @return 코드값에 해당하는 리소스 값 
	 */
	protected String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

	/**
	 * 리소스번들에서 메시지 값 조회 <br/>
	 * 현재 세션의 Locale 을 기준으로 조회
	 * @param code - 리소스번들의 코드값
	 * @param args - 파라미터 배열
	 * @return 코드값에 해당하는 리소스 값
	 */
	protected String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}

	/**
	 * 로케일 상태
	 * @return boolean
	 */
	protected boolean isSameLocale() {
		return getSessionPortal().getDefaultLocaleCode().equals(getSessionUser().getLocaleCode());
	}


	@Autowired
	protected KnowledgeMapCategoryService categoryService;

	/**
	 * Root Category 반환
	 * @param user
	 * @return KnowledgeMapCategory
	 */
	protected KnowledgeMapCategory getRootCategory(User user) {
		// 만약 Root Category가 없을시 자동생성하므로 세션정보를 같이 넘겨야 한다.
		KnowledgeMapCategory category = new KnowledgeMapCategory();
		category.setPortalId(user.getPortalId());
		category.setRegisterId(user.getUserId());
		category.setRegisterName(user.getUserName());

		// Root Category
		KnowledgeMapCategory rootCategory = categoryService.readCreateRootCategory(category);

		return rootCategory;
	}

	/**
	 * 트리에서 사용하는 노드 JSON
	 * (최상위 카테고리들)
	 * @param user
	 * @return String
	 */
	protected String getMenuLevelOneCategorysJSON(User user) {
		// Root Category
		KnowledgeMapCategory rootCategory = getRootCategory(user);

		// 카테고리 root 자식들
		List<KnowledgeMapCategory> categoryList = categoryService.listAndChildCountByCategoryParentId(rootCategory.getCategoryId());
		List<TreeNode> treeNodeList = listMenuCategory2TreeNodes(categoryList);

		String categoryJSON = TreeManager.getJSON(treeNodeList);

		return categoryJSON;
	}

	/**
	 * Category 데이터를 트리에 보내는 자료로 변경
	 * @param categoryList
	 * @return List<TreeNode>
	 */
	protected List<TreeNode> listMenuCategory2TreeNodes(List<KnowledgeMapCategory> categoryList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (KnowledgeMapCategory item : categoryList ) {
			treeNodeList.add(menuCategory2TreeNode(item));
		}
	
		return treeNodeList;
	}

	/**
	 * 카테고리 데이터를 트리 테이터로 변경
	 * @param category
	 * @return TreeNode
	 */
	private TreeNode menuCategory2TreeNode(KnowledgeMapCategory category) {
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

	/**
	 * 카테고리 데이터를 트리 테이터로 변경
	 * @param category
	 * @return TreeNode
	 */
	protected TreeNode category2TreeNode(KnowledgeMapCategory category) {
		// Attr 생성
		TreeNodeAttrKnowledgeMap tna = new TreeNodeAttrKnowledgeMap();
		tna.setId(category.getCategoryId());
		tna.setPid(category.getCategoryParentId());
		tna.setOrder(category.getSortOrder());
		tna.setIsBlog(category.getIsBlog());
		tna.setIsColl(category.getIsColl());
		tna.setIsQna(category.getIsQna());
		tna.setIsDic(category.getIsDic());
		tna.setIsCafe(category.getIsCafe());
		tna.setIsForum(category.getIsForum());
		tna.setIsIdea(category.getIsIdea());
		tna.setIsBbs(category.getIsBbs());
		tna.setIsManual(category.getIsManual());
		tna.setTags(category.getTags());

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

	/**
	 * Category 데이터를 트리에 보내는 자료로 변경
	 * @param categoryList
	 * @return List<TreeNode>
	 */
	protected List<TreeNode> listCategory2TreeNodes(List<KnowledgeMapCategory> categoryList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (KnowledgeMapCategory item : categoryList ) {
			treeNodeList.add(category2TreeNode(item));
		}
	
		return treeNodeList;
	}

	/**
	 * 지식지도에서 조회되는 모든 모듈 구하기
	 * @return String - 콤마(,)로 분리된 문자열
	 */
	protected String getUsableModules() {
		StringBuffer sb = new StringBuffer();
		sb.append(TagConstants.ITEM_TYPE_BBS);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_CAFE);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_CONPORATE_VOCA);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_FORUM);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_IDEATION);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_QNA);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_SOCIAL_BLOG);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_WORK_MANUAL);
		sb.append(CommonConstant.COMMA_SEPARATER);
		sb.append(TagConstants.ITEM_TYPE_WORKSPACE);

		return sb.toString();
	}
}