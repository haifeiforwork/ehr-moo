/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 포탈 권한 관리
 *
 * @author 한승환
 * @version $Id: PortalSecurityServiceImpl.java 17346 2012-02-28 08:43:22Z yruyo $
 */
@Service("portalSecurityService")
public class PortalSecurityServiceImpl extends GenericServiceImpl<PortalSecurity, String> implements PortalSecurityService {
	
	@Autowired
	ACLService aclService;
	
	public void createSystemPermission(PortalSecurity security){
		/* 권한 등록 관련 */
		ACLResourcePermission aclResourcePermissionRead = security.getAclResourcePermissionRead();
		// 리소스의 타입을 입력한다.
		aclResourcePermissionRead.setClassName(security.getClassName());
		// 리소스 이름을 입력한다.
		aclResourcePermissionRead.setResourceName(security.getResourceName());
		// 리소스에 대한 설명을 입력한다.
		aclResourcePermissionRead.setResourceDescription(security.getResourceDescription());

		// 오퍼레이션 이름을 입력한다.
		aclResourcePermissionRead.setOperationName(security.getOperationName());

		// 퍼미션 이름 & 설명을 입력한다.
		// 만약에 퍼미션 이름 & 설명을 등록하지 않으면 리소스의 이름 & 설명으로 대체한다.
		aclResourcePermissionRead.setPermissionName(security.getPermissionName());
		aclResourcePermissionRead.setPermissionDescription(security.getPermissionDescription());
		
		// Owner 정보 셋팅
		aclResourcePermissionRead.setUserId(security.getOwnerId());
		aclResourcePermissionRead.setUserName(security.getOwnerName());

		// 사용자 권한
		if(!StringUtil.isEmpty(security.getAddrUserListAll())) {
			for (String item : security.getAddrUserListAll().split("\\^")) {
				aclResourcePermissionRead.addAssignUserId(item);
			}
		}

		// 그룹 권한
		if(!StringUtil.isEmpty(security.getAddrGroupTypeListAll())) {
			for (String item : security.getAddrGroupTypeListAll().split("\\^")) {	
				JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(item));
				JSONObject obj = (JSONObject) jsonArray.get(0);
				
				aclResourcePermissionRead.addGroupPermission(obj.getString("groupId"), obj.getInt("hierarchyPermission"));
			}
		}

		// 역할 권한
		if(!StringUtil.isEmpty(security.getAddrRoleListAll())) {
			for(String item : security.getAddrRoleListAll().split("\\^")) {
				aclResourcePermissionRead.addRoleId(item);
			}
		}

		// 고용형태 권한
		if(!StringUtil.isEmpty(security.getAddrJobClassListAll())) {
			for(String item : security.getAddrJobClassListAll().split("\\^")) {
				aclResourcePermissionRead.addJobClassCode(item);
			}
		}

		// 직책 권한
		if(!StringUtil.isEmpty(security.getAddrJobDutyListAll())) {
			for(String item : security.getAddrJobDutyListAll().split("\\^")) {
				aclResourcePermissionRead.addJobDutyCode(item);
			}
		}
		
		// 사용자 예외 권한
		if(!StringUtil.isEmpty(security.getAddrExpUserListAll())) {
			for (String item : security.getAddrExpUserListAll().split("\\^")) {
				aclResourcePermissionRead.addExceptUserId(item);
			}
		}

		// 권한 업데이트
		aclService.createSystemPermission(aclResourcePermissionRead);
	}
	
	public void updateSystemPermissionAndResource(PortalSecurity security){
		/* 권한 수정 관련 */
		ACLResourcePermission aclResourcePermissionRead = security.getAclResourcePermissionRead();
		aclResourcePermissionRead.setClassName(security.getClassName());
		aclResourcePermissionRead.setResourceName(security.getResourceName());
		aclResourcePermissionRead.setResourceDescription(security.getResourceDescription());
		aclResourcePermissionRead.setOperationName(security.getOperationName());
		aclResourcePermissionRead.setPermissionDescription(security.getPermissionDescription());
		aclResourcePermissionRead.setPermissionName(security.getPermissionName());
		
		// 퍼미션 이름 & 설명을 입력한다.
		// 만약에 퍼미션 이름 & 설명을 등록하지 않으면 리소스의 이름 & 설명으로 대체한다.
		aclResourcePermissionRead.setPermissionName(security.getPermissionName());
		aclResourcePermissionRead.setPermissionDescription(security.getPermissionDescription());
		
		// Owner 정보 셋팅
		aclResourcePermissionRead.setUserId(security.getOwnerId());
		aclResourcePermissionRead.setUserName(security.getOwnerName());

		// 사용자 권한
		if(!StringUtil.isEmpty(security.getAddrUserListAll())) {
			for (String item : security.getAddrUserListAll().split("\\^")) {
				aclResourcePermissionRead.addAssignUserId(item);
			}
		}

		// 그룹 권한
		if(!StringUtil.isEmpty(security.getAddrGroupTypeListAll())) {
			for (String item : security.getAddrGroupTypeListAll().split("\\^")) {	
				JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(item));
				JSONObject obj = (JSONObject) jsonArray.get(0);
				
				aclResourcePermissionRead.addGroupPermission(obj.getString("groupId"), obj.getInt("hierarchyPermission"));
			}
		}

		// 역할 권한
		if(!StringUtil.isEmpty(security.getAddrRoleListAll())) {
			for(String item : security.getAddrRoleListAll().split("\\^")) {
				aclResourcePermissionRead.addRoleId(item);
			}
		}

		// 고용형태 권한
		if(!StringUtil.isEmpty(security.getAddrJobClassListAll())) {
			for(String item : security.getAddrJobClassListAll().split("\\^")) {
				aclResourcePermissionRead.addJobClassCode(item);
			}
		}

		// 직책 권한
		if(!StringUtil.isEmpty(security.getAddrJobDutyListAll())) {
			for(String item : security.getAddrJobDutyListAll().split("\\^")) {
				aclResourcePermissionRead.addJobDutyCode(item);
			}
		}
		
		// 사용자 예외 권한
		if(!StringUtil.isEmpty(security.getAddrExpUserListAll())) {
			for (String item : security.getAddrExpUserListAll().split("\\^")) {
				aclResourcePermissionRead.addExceptUserId(item);
			}
		}

		// 권한 업데이트
		aclService.deleteSystemPermission(security.getClassName(), security.getResourceName());
		aclService.createSystemPermission(aclResourcePermissionRead);
	}
	
	public void updateSystemPermission(PortalSecurity security, String portalId){
		/* 권한 수정 관련 */
		ACLResourcePermission aclResourcePermissionRead = security.getAclResourcePermissionRead();
		aclResourcePermissionRead.setClassName(security.getClassName());
		aclResourcePermissionRead.setResourceName(security.getResourceName());
		aclResourcePermissionRead.setResourceDescription(security.getResourceDescription());
		aclResourcePermissionRead.setOperationName(security.getOperationName());
		aclResourcePermissionRead.setPermissionDescription(security.getPermissionDescription());
		aclResourcePermissionRead.setPermissionName(security.getPermissionName());
		
		// 퍼미션 이름 & 설명을 입력한다.
		// 만약에 퍼미션 이름 & 설명을 등록하지 않으면 리소스의 이름 & 설명으로 대체한다.
		aclResourcePermissionRead.setPermissionName(security.getPermissionName());
		aclResourcePermissionRead.setPermissionDescription(security.getPermissionDescription());
		
		// Owner 정보 셋팅
		aclResourcePermissionRead.setUserId(security.getOwnerId());
		aclResourcePermissionRead.setUserName(security.getOwnerName());

		// 사용자 권한
		if(!StringUtil.isEmpty(security.getAddrUserListAll())) {
			for (String item : security.getAddrUserListAll().split("\\^")) {
				aclResourcePermissionRead.addAssignUserId(item);
			}
		}

		// 그룹 권한
		if(!StringUtil.isEmpty(security.getAddrGroupTypeListAll())) {
			for (String item : security.getAddrGroupTypeListAll().split("\\^")) {	
				JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(item));
				JSONObject obj = (JSONObject) jsonArray.get(0);
				
				aclResourcePermissionRead.addGroupPermission(obj.getString("groupId"), obj.getInt("hierarchyPermission"));
			}
		}

		// 역할 권한
		if(!StringUtil.isEmpty(security.getAddrRoleListAll())) {
			for(String item : security.getAddrRoleListAll().split("\\^")) {
				aclResourcePermissionRead.addRoleId(item);
			}
		}

		// 고용형태 권한
		if(!StringUtil.isEmpty(security.getAddrJobClassListAll())) {
			for(String item : security.getAddrJobClassListAll().split("\\^")) {
				aclResourcePermissionRead.addJobClassCode(item);
			}
		}

		// 직책 권한
		if(!StringUtil.isEmpty(security.getAddrJobDutyListAll())) {
			for(String item : security.getAddrJobDutyListAll().split("\\^")) {
				aclResourcePermissionRead.addJobDutyCode(item);
			}
		}
		
		// 사용자 예외 권한
		if(!StringUtil.isEmpty(security.getAddrExpUserListAll())) {
			for (String item : security.getAddrExpUserListAll().split("\\^")) {
				aclResourcePermissionRead.addExceptUserId(item);
			}
		}

		// 권한 업데이트
		aclService.updateSystemPermission(aclResourcePermissionRead, portalId);
	}

	public void deleteSystemPermission(String className, String resourceName){
		aclService.deleteSystemPermission(className, resourceName);
	}
}
