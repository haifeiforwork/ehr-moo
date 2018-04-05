/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardGroup;
import com.lgcns.ikep4.lightpack.award.service.AwardAdminService;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * AwardAdminService 구현체 클래스
 */
@Service
public class AwardAdminServiceImpl implements AwardAdminService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());


	/** The award dao. */
	@Autowired
	private AwardDao awardDao;

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	@SuppressWarnings("unused")
	@Autowired
	private AwardItemService awardItemService;

	@Autowired
	private ACLService aclService;

	/** 위지윅에디터에서 업로드 가능한 이미지 미디어타입 목록 */
	private final List<String> supportedImageMediaTypeList = Arrays.asList("image/gif", "image/jpeg", "image/png",
			"image/swf", "image/psd", "image/bmp", "image/tiff_ii", "image/tiff_mm", "image/jpc", "image/jp2",
			"image/jpx", "image/jb2", "image/swc", "image/iff", "image/wbmp", "image/xbm");

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardAdminService#
	 * checkSupportedImageMediaType(java.lang.String)
	 */
	public Boolean checkSupportedImageMediaType(String imageType) {

		for(String target : this.supportedImageMediaTypeList) {
			if(target.equals(imageType)) {
				return true;
			}
		}
		return false;
		//return Arrays.binarySearch(this.supportedImageMediaTypeList.toArray(new String[0]), imageType) > 0;
	}
	
	/**
	 * 게시판 목록
	 */
	public List<Award> listByAwardRootIdMap(Map<String, String> map) {
		List<Award> awardList = this.awardDao.listByAwardRootIdMap(map);
		return awardList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#listByAwardRootId
	 * (java.lang.String)
	 */
	public List<Award> listByAwardRootIdPermission(String awardRootId, String portalId,String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("awardRootId", awardRootId);
		parameter.put("portalId", portalId);
		parameter.put("userId", userId);

		List<Award> awardList = this.awardDao.listByAwardRootIdPermission(parameter);

		return awardList;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#listByAwardRootId
	 * (java.lang.String)
	 */
	public List<Award> listByAwardRootId(String awardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("awardRootId", awardRootId);
		parameter.put("portalId", portalId);


		List<Award> awardList = this.awardDao.listByAwardRootId(parameter);

		return awardList;
	}
	
	public List<Award> listByAwardRootIdMobile(String awardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("awardRootId", awardRootId);
		parameter.put("portalId", portalId);


		List<Award> awardList = this.awardDao.listByAwardRootIdMobile(parameter);

		return awardList;
	}
	
	public List<Award> listByAwardRootIdPer(Map<String, Object> param) {
		List<Award> awardList = this.awardDao.listByAwardRootIdPer(param);

		return awardList;
	}
	
	public List<Award> listByAwardRootIdPerMobile(Map<String, Object> param) {
		List<Award> awardList = this.awardDao.listByAwardRootIdPerMobile(param);

		return awardList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#readAward(java
	 * .lang.String)
	 */
	public Award readAward(String awardId) {
		Award award = this.awardDao.get(awardId);

		// 게시판의 조상목록을 조회한다.
		award.setParentList(this.awardDao.getParents(awardId));

		// 게시판의 손자목록을 조회한다.
		award.setChildList(this.awardDao.getChildren(awardId));

		//게시판 읽기 권한을 세팅
		this.setupAwardReadPermission(award);

		//게시판 쓰기 권한을 세팅
		this.setupAwardWritePermission(award);
		
		this.setupAwardAdminPermission(award);

		return award;
	}
	public Award readAward2(String awardId) {
		Award award = this.awardDao.get(awardId);

		return award;
	}
	/**
	 * 게시판 읽기 권한을 세팅하는 메서드
	 *
	 * @param award 게시판 모델 클래스
	 */
	private void setupAwardReadPermission(Award award) {
		//읽기 권한 정보를 가져온다.
		ACLResourcePermission readPermission = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "READ");
		if(readPermission != null) {
			readPermission = this.aclService.listDetailPermission(readPermission);
			List<User> readUserList = readPermission.getAssignUserDetailList();
			List<Group> readGroupList = readPermission.getGroupDetailList();
			List<Role> readRoleList = readPermission.getRoleDetailList();

			List<ACLGroupPermission> readPermissionList = readPermission.getGroupPermissionList();

			if(readGroupList != null) {
				List <AwardGroup> readAwardGroupList = new ArrayList<AwardGroup>();

				AwardGroup awardGroup = null;

				for(Group group : readGroupList) {

					awardGroup = new AwardGroup();
					awardGroup.setGroupId(group.getGroupId());
					awardGroup.setGroupName(group.getGroupName());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@:group.getGroupId():"+group.getGroupId()+":group.getGroupName():"+group.getGroupName());
					for(ACLGroupPermission aclGroupPermission : readPermissionList) {
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@:aclGroupPermission.getGroupId():"+aclGroupPermission.getGroupId());
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@:aclGroupPermission.getHierarchyPermission():"+aclGroupPermission.getHierarchyPermission());
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							awardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
						}
					}

					readAwardGroupList.add(awardGroup);
				}

				award.setReadGroupList(readAwardGroupList);
			}

			award.setReadUserList(readUserList);
			award.setReadRoleList(readRoleList);
			
			award.setReadPermission(readPermission.getOpen());

		}
	}

	/**
	 * 게시판 쓰기 권한을 세팅하는 메서드
	 *
	 * @param award 게시판 모델 클래스
	 */
	private void setupAwardWritePermission(Award award) {
		//쓰기 권한 정보를 가져온다.
		ACLResourcePermission writePermission = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "WRITE");

		if(writePermission != null) {
			writePermission = this.aclService.listDetailPermission(writePermission);

			List<Group> writeGroupList = writePermission.getGroupDetailList();

			List<ACLGroupPermission> wirtePermissionList = writePermission.getGroupPermissionList();

			if(writeGroupList != null) {
				List<AwardGroup> writeAwardGroupList = new ArrayList<AwardGroup>();

				AwardGroup awardGroup = null;

				for(Group group : writeGroupList) {
					awardGroup = new AwardGroup();
					awardGroup.setGroupId(group.getGroupId());
					awardGroup.setGroupName(group.getGroupName());

					for(ACLGroupPermission aclGroupPermission : wirtePermissionList) {
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							awardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
							continue;
						}
					}

					writeAwardGroupList.add(awardGroup);
				}

				award.setWriteGroupList(writeAwardGroupList);
			}
			
			award.setWritePermission(writePermission.getOpen());

			List<User> writeUserList = writePermission.getAssignUserDetailList();
			award.setWriteUserList(writeUserList);
			List<Role> writeRoleList = writePermission.getRoleDetailList();
			award.setWriteRoleList(writeRoleList);
		}
	}
	
	private void setupAwardAdminPermission(Award award) {
		//쓰기 권한 정보를 가져온다.
		ACLResourcePermission adminPermission = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "ADMIN");

		if(adminPermission != null) {
			adminPermission = this.aclService.listDetailPermission(adminPermission);

			List<Group> adminGroupList = adminPermission.getGroupDetailList();

			List<ACLGroupPermission> wirtePermissionList = adminPermission.getGroupPermissionList();

			if(adminGroupList != null) {
				List<AwardGroup> adminAwardGroupList = new ArrayList<AwardGroup>();

				AwardGroup awardGroup = null;

				for(Group group : adminGroupList) {
					awardGroup = new AwardGroup();
					awardGroup.setGroupId(group.getGroupId());
					awardGroup.setGroupName(group.getGroupName());

					for(ACLGroupPermission aclGroupPermission : wirtePermissionList) {
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							awardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
							continue;
						}
					}

					adminAwardGroupList.add(awardGroup);
				}

				award.setAdminGroupList(adminAwardGroupList);
			}
			
			award.setAdminPermission(adminPermission.getOpen());

			List<User> adminUserList = adminPermission.getAssignUserDetailList();
			award.setAdminUserList(adminUserList);
			List<Role> adminRoleList = adminPermission.getRoleDetailList();
			award.setAdminRoleList(adminRoleList);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#createAward
	 * (com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public String createAward(Award award) {
		final String generatedId = this.idgenService.getNextId();

		award.setAwardId(generatedId);
		
		//1Level Award 생성시 Foreign key 제약조건 오류 방지
		if("".equals(award.getAwardParentId()))
		{
			award.setAwardParentId(null);
		}
		String awardId = this.awardDao.create(award);

		//게시판인경우 권한 정보를 저장한다.
		if("0".equals(award.getAwardType())) {
			this.createWritePermission(award);
			this.createReadPermission(award);
		}
		
		//게시판인경우 권한 정보를 저장한다.
		if("2".equals(award.getAwardType())) {
			this.createReadPermission(award);
		}

		return awardId;

	}

	/**
	 * 쓰기 권한을 수정한다.
	 *
	 * @param award 게시판 모델 객체
	 */
	private void updateWritePermission(Award award) {
		ACLResourcePermission writePermission = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "WRITE");
		writePermission.setAssignUserIdList(null);
		writePermission.setGroupPermissionList(null);
		writePermission.setOpen(award.getWritePermission());

		if(award.getWritePermission() == 0) {
			//쓰기 권한 사용자 정보를 넣는다.
			if(award.getWriteUserList() != null) {
				for(User user : award.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());

				}
			}

			//쓰기 권한 관리자 정보를 넣는다.
			if(award.getWriteGroupList() != null) {
				for(AwardGroup group : award.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(award.getWriteRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getWriteRoleList()) {
					writePermission.addRoleId(role.getRoleId());
				}
			}
		}
		
		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		
		this.aclService.updateSystemPermission(writePermission,user.getPortalId());
	}
	
	private void updateAdminPermission(Award award) {
		ACLResourcePermission adminPermission = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "ADMIN");
		adminPermission.setAssignUserIdList(null);
		adminPermission.setGroupPermissionList(null);
		adminPermission.setOpen(award.getAdminPermission());

		if(award.getAdminPermission() == 0) {
			//쓰기 권한 사용자 정보를 넣는다.
			if(award.getAdminUserList() != null) {
				for(User user : award.getAdminUserList()) {
					adminPermission.addAssignUserId(user.getUserId());

				}
			}

			//쓰기 권한 관리자 정보를 넣는다.
			if(award.getAdminGroupList() != null) {
				for(AwardGroup group : award.getAdminGroupList()) {
					adminPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(award.getAdminRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getAdminRoleList()) {
					adminPermission.addRoleId(role.getRoleId());
				}
			}
		}
		
		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		
		this.aclService.updateSystemPermission(adminPermission,user.getPortalId());
	}

	/**
	 * 읽기 권한을 수정한다.
	 *
	 * @param award 게시판 모델 객체
	 */
	private void updateReadPermission(Award award) {
		ACLResourcePermission readPermission  = this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "READ");
		readPermission.setAssignUserIdList(null);
		readPermission.setGroupPermissionList(null);
		readPermission.setRoleIdList(null);
		readPermission.setOpen(award.getReadPermission());
		
		if(award.getReadPermission() == 0) {
			//읽기 권한 사용자 정보를 넣는다.
			if(award.getReadUserList() != null) {
				for(User user : award.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			//읽기 권한 관리자 정보를 넣는다.
			if(award.getReadGroupList() != null) {
				for(AwardGroup group : award.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(award.getReadRoleList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getReadRoleList()) {
					readPermission.addRoleId(role.getRoleId());
				}
			}
		}
	
		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		
		this.aclService.updateSystemPermission(readPermission, user.getPortalId());
	}

	/**
	 * 쓰기권한을 생성한다.
	 *
	 * @param award 게시판 모델 객체
	 */
	private void createWritePermission(Award award) {
		ACLResourcePermission writePermission  = new ACLResourcePermission();

		// 시스템 리소스의 타입을 입력한다.
		writePermission.setClassName(Award.AWARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		writePermission.setResourceName(award.getAwardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		writePermission.setResourceDescription(award.getAwardName());

		// 사용자 아이디를 입력한다.
		writePermission.setUserId(award.getUpdaterId());
		// 사용자 이름을 입력한다.
		writePermission.setUserName(award.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		writePermission.setOperationName("WRITE");
		writePermission.setOpen(award.getWritePermission());

		if(award.getWritePermission() == 0) {
			// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

			if(award.getWriteUserList() != null) {
				//쓰기 권한 사용자 정보를 넣는다.
				for(User user : award.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());
				}
			}

			if(award.getWriteGroupList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				for(AwardGroup group : award.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(award.getWriteRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getWriteRoleList()) {
					writePermission.addRoleId(role.getRoleId());
				}
			}
			
		}
		this.aclService.createSystemPermission(writePermission);
	}
	
	private void createAdminPermission(Award award) {
		ACLResourcePermission adminPermission  = new ACLResourcePermission();

		// 시스템 리소스의 타입을 입력한다.
		adminPermission.setClassName(Award.AWARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		adminPermission.setResourceName(award.getAwardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		adminPermission.setResourceDescription(award.getAwardName());

		// 사용자 아이디를 입력한다.
		adminPermission.setUserId(award.getUpdaterId());
		// 사용자 이름을 입력한다.
		adminPermission.setUserName(award.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		adminPermission.setOperationName("ADMIN");
		adminPermission.setOpen(award.getAdminPermission());

		if(award.getAdminPermission() == 0) {
			// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

			if(award.getAdminUserList() != null) {
				//쓰기 권한 사용자 정보를 넣는다.
				for(User user : award.getAdminUserList()) {
					adminPermission.addAssignUserId(user.getUserId());
				}
			}

			if(award.getAdminGroupList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				for(AwardGroup group : award.getAdminGroupList()) {
					adminPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(award.getAdminRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getAdminRoleList()) {
					adminPermission.addRoleId(role.getRoleId());
				}
			}
			
		}
		this.aclService.createSystemPermission(adminPermission);
	}

	/**
	 * 읽기 권한을 생성한다.
	 *
	 * @param award 게시판 모델 객체
	 */
	private void createReadPermission(Award award) {
		ACLResourcePermission readPermission  = new ACLResourcePermission();
		// 시스템 리소스의 타입을 입력한다.
		readPermission.setClassName(Award.AWARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		readPermission.setResourceName(award.getAwardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		readPermission.setResourceDescription(award.getAwardName());
		// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈
		readPermission.setOpen(award.getReadPermission());
		// 사용자 아이디를 입력한다.
		readPermission.setUserId(award.getUpdaterId());
		// 사용자 이름을 입력한다.
		readPermission.setUserName(award.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		readPermission.setOperationName("READ");

		if(award.getReadPermission() == 0) {
			if( award.getReadUserList() != null) {
				//읽기 권한 사용자 정보를 넣는다.
				for(User user : award.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			if( award.getReadGroupList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				for(AwardGroup group : award.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			if(award.getReadRoleList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				
				for(Role role : award.getReadRoleList()) {
					readPermission.addRoleId(role.getRoleId());
				}
			}
		}

		this.aclService.createSystemPermission(readPermission);

	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#updateAward
	 * (com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public void updateAward(Award award) {
		
		//1Level Award 생성시 Foreign key 제약조건 오류 방지
		if("".equals(award.getAwardParentId()))
		{
			award.setAwardParentId(null);
		}
		
		this.awardDao.update(award);
		
		//게시판인 경우 권한 정보를 저장한다.
		if("0".equals(award.getAwardType())) {

			//권한이 생성되어 있는지 확인하고 생성되어 있으면 업데이트 아니면 생성을 한다.
			if(this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "READ") == null) {
				this.createReadPermission(award);
			} else {
				this.updateReadPermission(award);

			}
			if(this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "WRITE") == null) {
				this.createWritePermission(award);
			} else {
				this.updateWritePermission(award);
			}
			if(this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "ADMIN") == null) {
				this.createAdminPermission(award);
			} else {
				this.updateAdminPermission(award);
			}
		}
		
		//카테고리인 경우 읽기 권한 정보를 저장한다.
		if("2".equals(award.getAwardType())) {
			//권한이 생성되어 있는지 확인하고 생성되어 있으면 업데이트 아니면 생성을 한다.
			if(this.aclService.getSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId(), "READ") == null) {
				this.createReadPermission(award);
				
			} else {
				this.updateReadPermission(award);
				

			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#physicalDeleteAward
	 * (java.lang.String)
	 */
	public void physicalDeleteAward(String awardId) {
		/*
		//게시판 정보를 조회한다.
		Award award = this.awardDao.get(awardId);

		//권한을 삭제한다.
		this.aclService.deleteSystemPermission(Award.AWARD_ACL_CLASS_NAME, award.getAwardId());

		// 게시판에 게시글과 관련 정보를 모두 삭제한다.
		this.awardItemService.adminDeleteAwardItemInAward(awardId);

		// 게시판을 삭제한다.
		this.awardDao.physicalDelete(awardId);
		 */


		this.updateAwardDeleteField(awardId, Award.AWARD_DELETE_WAIT);


		List<Award> awardList = this.awardDao.listChildrenAward(awardId);

		for(Award award : awardList) {
			this.updateAwardDeleteField(award.getAwardId(), Award.AWARD_DELETE_WAIT);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#listParentAward
	 * (java.lang.String)
	 */
	public List<Award> listParentAward(String awardId) {
		return this.awardDao.getParents(awardId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#updateAwardMove
	 * (com.lgcns.ikep4.lightpack.award.model.Award)
	 */
	public void updateAwardMove(Award after) {

		Award before = this.awardDao.get(after.getAwardId());

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		this.awardDao.updateSortOderDecrease(before);

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		this.awardDao.updateSortOderIncrease(after);
		
		//1Level Award 생성시 Foreign key 제약조건 오류 방지
		if("".equals(after.getAwardParentId()))
		{
			after.setAwardParentId(null);
		}

		this.awardDao.updateMove(after);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardAdminService#
	 * readHasPermissionAward(java.lang.String, java.lang.String)
	 */
	public Award readHasPermissionAward(String userId, String awardRootId) {
		return this.awardDao.readHasPermissionAward(userId, awardRootId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#listChildrenAward
	 * (java.lang.String)
	 */
	public List<Award> listChildrenAward(String awardId) {
		return this.awardDao.listChildrenAward(awardId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.award.service.AwardAdminService#listChildrenAward
	 * (java.lang.String)
	 */
	public List<Award> listChildrenAward(String awardId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("awardId", awardId);
		parameter.put("portalId", portalId);
		
		return this.awardDao.listChildrenAward(parameter);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardAdminService#updateAwardDeleteField(java.lang.Integer)
	 */
	public void updateAwardDeleteField(String awardId, Integer status) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("awardId", awardId);
		parameter.put("status", status);

		this.awardDao.updateAwardDeleteField(parameter);

	}
	
	public void updateAwardMenuType(boolean isTree) {
		awardDao.updateAwardMenuType(isTree ? "TREE" : "MENU");
	}
	
	public boolean isAwardMenuTree() {
		boolean isTree = false;
		String type = awardDao.selectAwardMenuType();
		
		if(type.equalsIgnoreCase("tree"))
			isTree =  true;
		
		return isTree;
	}

	public List<Award> getAwardMenuList() {
		// TODO Auto-generated method stub
		return this.awardDao.getAwardMenuList();
	}

}
