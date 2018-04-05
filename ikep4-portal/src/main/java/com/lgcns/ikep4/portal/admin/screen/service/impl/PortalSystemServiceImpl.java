/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.code.dao.ClassCodeDao;
import com.lgcns.ikep4.support.user.code.model.ClassCode;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 시스템 관리 Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemServiceImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Service("PortalSystemService")
public class PortalSystemServiceImpl extends GenericServiceImpl<PortalSystem, String> implements PortalSystemService {

	/**
	 * 포탈 시스템 dao
	 */
	@Autowired
	private PortalSystemDao portalSystemDao;
	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;
	
	/**
	 * 접근 관리 service
	 */
	@Autowired
	ACLService aclService;
	
	/**
	 * 관리자 권한 관리 service
	 */
	@Autowired
	private PortalSecurityService portalSecurityService;
	
	/**
	 * 클래스 관리 dao
	 */
	@Autowired
	private ClassCodeDao classCodeDao;
	
	/**
	 * 아이디 생성 service
	 */
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	@Qualifier("aclSystemDao")
	private ACLDao aclSystemDao;

	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<PortalSystem> portalSystemListView(String portalId) {
		return portalSystemDao.portalSystemListView(portalId);
	}


	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 관리 트리 목록
	 */
	public List<PortalSystem> treeList(Map<String, String> param) {

		List<PortalSystem> list = portalSystemDao.treeList(param);

		return list;

	}

	/**
	 * 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 * @param param Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 이름, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 */
	public List<PortalSystem> portalSystemLinkList(Map<String, String> param) {

		List<PortalSystem> list = portalSystemDao.linkList(param);
		
		return list;

	}

	/**
	 * 시스템 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드, systemCode:시스템 코드)
	 * @return PortalSystem 시스템 정보
	 */
	public PortalSystem read(Map<String, String> param) {

		PortalSystem portalSystem = portalSystemDao.get((String) param.get("systemCode"));
		
		if(portalSystem != null) {
			
			// 리소스에 대한 권한 정보를 읽어 온다.
			ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission("Portal-System", portalSystem.getSystemCode(), "READ");

			// 권한에 대한 상세정보를 조회 한다.
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);

			portalSystem.setAclResourcePermissionRead(aclResourcePermissionRead);
		}

		return portalSystem;

	}

	/**
	 * 시스템 정보 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	@Override
	public boolean exists(String systemCode) {

		boolean exist = portalSystemDao.exists(systemCode);

		return exist;

	}

	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param) {

		int count = portalSystemDao.getChildCount(param);

		return count;

	}
	
	/**
	 * 시스템 명 별 시스템 정보
	 * @param systemName 시스템 명
	 * @param portalId 포탈 아이디
	 * @return String 시스템 코드
	 */
	public String getSystemCode(String systemName, String portalId) {
		
		String systemCode = portalSystemDao.getSystemCode(systemName, portalId);
		
		return systemCode;
		
	}

	/**
	 * 시스템 생성
	 * @param portalSystem 시스템 정보
	 * @return String 생성된 시스템 코드
	 */
	public String create(PortalSystem portalSystem) {

		String id = portalSystem.getSystemCode();
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystem.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystem.getSystemCode());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		// 시스템 등록
		portalSystemDao.create(portalSystem);
		
		// Security Description  생성
		portalSystem.getSecurity().setResourceName(portalSystem.getSystemCode());
		portalSystem.getSecurity().setResourceDescription("시스템 권한");
		
		portalSecurityService.createSystemPermission(portalSystem.getSecurity());
		
		//시스템 등록시 시스템 관리자 등록
		if(!StringUtil.isEmpty(portalSystem.getClassName()) && !"N".equals(portalSystem.getAdminUserAddFlag())) {
			
			String className = portalSystem.getClassName();
			String resourceName = portalSystem.getClassName();
			String operationName = "MANAGE";
			
			boolean checkClassName = false;
			
			checkClassName = classCodeDao.checkName(className);
			
			if(!checkClassName) {
				createClassCode(className);
			}
				
			String resourceId = aclSystemDao.getResourceId(className, resourceName);

			if (resourceId == null || "".equals(resourceId)) {
				createResourceAndBasePermission(className, resourceName, operationName);
			}
				
			ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
			aclResourcePermission = aclService.getSystemPermission("Portal", "Portal", "MANAGE");
			
			if(aclResourcePermission != null) {
				createAdminPermission(aclResourcePermission, className, resourceName, operationName);
			}
		}

		return id;

	}

	/**
	 * 시스템 수정
	 * @param portalSystem 시스템 정보
	 */
	public void update(PortalSystem portalSystem) {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
	    //화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
	    List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystem.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystem.getSystemCode());

	    //다국어정보를 삭제합니다.
	    i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystem.getOldSystemCode());
	    
	    //다국어정보를 저장합니다.
	    i18nMessageService.create(i18nMessageList);
	    
	    // 시스템 수정
		portalSystemDao.update(portalSystem);

		// Security Description  삭제
		portalSecurityService.deleteSystemPermission("Portal-System", portalSystem.getOldSystemCode());
		
		// Security Description  생성
		portalSystem.getSecurity().setResourceName(portalSystem.getSystemCode());
		portalSystem.getSecurity().setResourceDescription("시스템 권한");
		
		portalSecurityService.createSystemPermission(portalSystem.getSecurity());
		
		String tempClassName = StringUtil.isEmpty(portalSystem.getClassName()) == true ? "" : portalSystem.getClassName();
		String tempOldClassName = StringUtil.isEmpty(portalSystem.getOldClassName()) == true ? "" : portalSystem.getOldClassName();
		  
		  //시스템 등록시 시스템 관리자 등록
		  if(!StringUtil.isEmpty(tempClassName) && !tempClassName.equals(tempOldClassName)) {
			
			String className = portalSystem.getClassName();
			String resourceName = portalSystem.getClassName();
			String operationName = "MANAGE";
			
			boolean checkClassName = false;
			
			checkClassName = classCodeDao.checkName(className);
			
			if(!checkClassName) {
				createClassCode(className);
			}
				
			String resourceId = aclSystemDao.getResourceId(className, resourceName);

			if (resourceId == null || "".equals(resourceId)) {
				createResourceAndBasePermission(className, resourceName, operationName);
			}
				
			ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
			aclResourcePermission = aclService.getSystemPermission("Portal", "Portal", "MANAGE");
			
			if(aclResourcePermission != null) {
				createAdminPermission(aclResourcePermission, className, resourceName, operationName);
			}
		}

	}

	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 */
	public void delete(String systemCode) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, systemCode);
		
		portalSystemDao.delete(systemCode);

		// Security Description  삭제
		portalSecurityService.deleteSystemPermission("Portal-System", systemCode);
	}

	/**
	 * 시스템 트리에서의 시스템 위치를 위로 이동
	 * @param map Map(prevSortOrder:트리 위치상 바로 위의 시스템의 순서, prevNodeId:트리 위치상 바로 위의 시스템 코드, curSortOrder:이동할 시스템의 순서, curNodeId:이동할 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveUpPortalSystem(Map<String, String> map) {

		PortalSystem prevPortalSystem = portalSystemDao.get((String) map.get("prevNodeId"));
		prevPortalSystem.setOldSystemCode(prevPortalSystem.getSystemCode());
		prevPortalSystem.setSortOrder((String) map.get("curSortOrder"));
		prevPortalSystem.setUpdaterId((String) map.get("updaterId"));
		prevPortalSystem.setUpdaterName((String) map.get("updaterName"));

		portalSystemDao.update(prevPortalSystem);

		PortalSystem curPortalSystem = portalSystemDao.get((String) map.get("curNodeId"));
		curPortalSystem.setOldSystemCode(curPortalSystem.getSystemCode());
		curPortalSystem.setSortOrder((String) map.get("prevSortOrder"));
		curPortalSystem.setUpdaterId((String) map.get("updaterId"));
		curPortalSystem.setUpdaterName((String) map.get("updaterName"));

		portalSystemDao.update(curPortalSystem);

	}

	/**
	 * 시스템 트리에서의 시스템 위치를 아래로 이동
	 * @param map Map(nextSortOrder:트리 위치상 바로 아래의 시스템의 순서, nextNodeId:트리 위치상 바로 아래의 시스템 코드, curSortOrder:이동할 시스템의 순서, curNodeId:이동할 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDownPortalSystem(Map<String, String> map) {

		PortalSystem nextPortalSystem = portalSystemDao.get((String) map.get("nextNodeId"));
		nextPortalSystem.setOldSystemCode(nextPortalSystem.getSystemCode());
		nextPortalSystem.setSortOrder((String) map.get("curSortOrder"));
		nextPortalSystem.setUpdaterId((String) map.get("updaterId"));
		nextPortalSystem.setUpdaterName((String) map.get("updaterName"));

		portalSystemDao.update(nextPortalSystem);

		PortalSystem curPortalSystem = portalSystemDao.get((String) map.get("curNodeId"));
		curPortalSystem.setOldSystemCode(curPortalSystem.getSystemCode());
		curPortalSystem.setSortOrder((String) map.get("nextSortOrder"));
		curPortalSystem.setUpdaterId((String) map.get("updaterId"));
		curPortalSystem.setUpdaterName((String) map.get("updaterName"));

		portalSystemDao.update(curPortalSystem);

	}

	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 시스템 위치를 트리 위치상 바로 위의 시스템의 하위로 이동
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndOneNode(Map<String, String> map) {

		PortalSystem portalSystem = portalSystemDao.get((String) map.get("systemCode"));
		portalSystem.setSortOrder((String) map.get("sortOrder"));
		portalSystem.setOldSystemCode((String) map.get("systemCode"));
		portalSystem.setParentSystemCode((String) map.get("parentSystemCode"));
		portalSystem.setUpdaterId((String) map.get("updaterId"));
		portalSystem.setUpdaterName((String) map.get("updaterName"));

		//String prevSortOrder = map.get("prevSortOrder");
		portalSystemDao.updateSortOrder(portalSystem);
		portalSystemDao.update(portalSystem);
		
	}

	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 이동 전 상위 시스템은 동일하면서 노드내에 순서만 변경
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, prevSystemCode:트리 위치상 바로 위의 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndSameNode(Map<String, String> map) {

		PortalSystem system = portalSystemDao.get((String) map.get("systemCode"));
		PortalSystem prevSystem = portalSystemDao.get((String) map.get("prevSystemCode"));

		system.setSortOrder((String) map.get("sortOrder"));
		system.setOldSystemCode((String) map.get("systemCode"));
		system.setParentSystemCode((String) map.get("parentSystemCode"));
		system.setUpdaterId((String) map.get("updaterId"));
		system.setUpdaterName((String) map.get("updaterName"));

		String prevSortOrder = map.get("prevSortOrder");
		portalSystemDao.updateSortOrder(system);

		if(system.getSortOrder().compareTo(prevSystem.getSortOrder()) < 0) {
			String systemSortOrder = system.getSortOrder();

			system.setSortOrder(prevSystem.getSortOrder());

			prevSystem.setOldSystemCode((String) map.get("prevSystemCode"));
			prevSystem.setUpdaterId((String) map.get("updaterId"));
			prevSystem.setUpdaterName((String) map.get("updaterName"));
			prevSystem.setSortOrder(systemSortOrder);

			portalSystemDao.update(prevSystem);
		}

		portalSystemDao.update(system);


	}

	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 시스템 위치를 트리 위치상 바로 위의 시스템의 하위로 이동하거나 이동 전 상위 시스템은 동일하면서 노드내에 순서만 변경하는 제외한 위치 이동
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndOtherNode(Map<String, String> map) {

		String newSortOrder = portalSystemDao.getMaxSortOrder();

		PortalSystem portalSystem = portalSystemDao.get((String) map.get("systemCode"));
		portalSystem.setOldSystemCode((String) map.get("systemCode"));
		portalSystem.setParentSystemCode((String) map.get("parentSystemCode"));
		portalSystem.setSortOrder(newSortOrder);
		portalSystem.setUpdaterId((String) map.get("updaterId"));
		portalSystem.setUpdaterName((String) map.get("updaterName"));


		String prevSortOrder = map.get("prevSortOrder");
		portalSystemDao.updateSortOrder(portalSystem);
		portalSystemDao.update(portalSystem);

	}
	
	/**
	 * 클래스 생성
	 * @param className 클래스명
	 */
	private void createClassCode(String className) {
		
		String classId = idgenService.getNextId();
		String description = className+" Class";
		
		ClassCode classCode = new ClassCode();
		classCode.setClassId(classId);
		classCode.setClassName(className);
		classCode.setDescription(description);
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		ModelBeanUtil.bindRegisterInfo(classCode, user.getUserId(), user.getUserName());
		
		// 클래스 생성
		classCodeDao.create(classCode);
	}
	
	/**
	 * 리소스 & 권한 기본 정보 생성
	 * @param className 클래스명
	 * @param resourceName 리소스명
	 * @param operationName 오퍼레이션명
	 */
	private void createResourceAndBasePermission(String className, String resourceName, String operationName) {
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setResourceDescription(resourceName);
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setOpen(1);
		aclResourcePermission.setUserId(user.getUserId());
		aclResourcePermission.setUserName(user.getUserName());
		aclResourcePermission.setOperationName(operationName);
		
		// Get the resource id.
		String resourceId = idgenService.getNextId();
		// 리소스 생성
		aclSystemDao.createResource(aclResourcePermission, resourceId);
		
		aclResourcePermission.setOpen(0);
		
		// Get the permission id.
		String permissionId = idgenService.getNextId();
		
		boolean isPermissionNameEmpty = (aclResourcePermission.getPermissionName() == null)
		|| ("".equals(aclResourcePermission.getPermissionName()));
		boolean isPermissionDescriptionEmpty = (aclResourcePermission.getPermissionDescription() == null)
				|| ("".equals(aclResourcePermission.getPermissionDescription()));
		
		// permissionName에 값이 없을 경우, resourceName으로 대체
		if (isPermissionNameEmpty) {
			aclResourcePermission.setPermissionName(aclResourcePermission.getResourceName());
		}
		// permissionDescription에 값이 없을 경우, resourceDescription으로 대체
		if (isPermissionDescriptionEmpty) {
			aclResourcePermission.setPermissionDescription(aclResourcePermission.getResourceDescription());
		}

		// 기본 퍼미션 생성
		aclSystemDao.createBasePermission(aclResourcePermission, resourceId, permissionId);
	}
	
	/**
	 * 관리자 권한 생성
	 * @param aclResourcePermission 권한 정보
	 */
	private void createAdminPermission(ACLResourcePermission aclResourcePermission, String className, String resourceName, String operationName) {
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		aclResourcePermission.setUserId(user.getUserId());
		aclResourcePermission.setUserName(user.getUserName());
		
		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
		|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (!isEmpty && !StringUtil.isEmpty(permissionId)) {	
			
			List<String> permissionIdList = new ArrayList<String>(); 
			permissionIdList.add(permissionId);
			
			aclSystemDao.removeUserPermission(permissionIdList);
			aclSystemDao.createUserPermission(aclResourcePermission, permissionId);
		}
	}
	
}