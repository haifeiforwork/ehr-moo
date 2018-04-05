package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletCategoryService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalThemeService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.dao.I18nMessageDao;
import com.lgcns.ikep4.support.user.code.dao.JobClassDao;
import com.lgcns.ikep4.support.user.code.dao.JobDutyDao;
import com.lgcns.ikep4.support.user.code.dao.JobPositionDao;
import com.lgcns.ikep4.support.user.code.dao.JobRankDao;
import com.lgcns.ikep4.support.user.code.dao.JobTitleDao;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.model.JobPosition;
import com.lgcns.ikep4.support.user.code.model.JobRank;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.dao.RoleDao;
import com.lgcns.ikep4.support.user.role.dao.RoleTypeDao;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleTypeService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 포탈 service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalServiceImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Service("portalService")
public class PortalServiceImpl extends GenericServiceImpl<Portal, String> implements PortalService {

	@Autowired
	private PortalDao portalDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private PortalPageService portalPageService;
	
	@Autowired
	private PortalSystemService portalSystemService;
	
	@Autowired
	private PortalSystemDao portalSystemDao;
	
	@Autowired
	private PortalThemeDao portalThemeDao;
	
	@Autowired
	private PortalThemeService portalThemeService;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	private PortalLayoutDao portalLayoutDao;
	
	@Autowired
	@Qualifier("aclSystemDao")
	private ACLDao aclSystemDao;
	
	@Autowired
	private RoleTypeDao roleTypeDao;
	
	@Autowired
	private RoleTypeService roleTypeService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private I18nMessageDao i18nMessageDao;
	
	@Autowired
	private JobTitleDao jobTitleDao;
	
	@Autowired
	private JobPositionDao jobPositionDao;
	
	@Autowired
	private JobRankDao jobRankDao;
	
	@Autowired
	private JobClassDao jobClassDao;
	
	@Autowired
	private JobDutyDao jobDutyDao;
	
	@Autowired
	private PortalQuickMenuDao portalQuickMenuDao;
	
	@Autowired
	private PortalQuickMenuService portalQuickMenuService;
	
	@Autowired
	private PortalMenuDao portalMenuDao;
	
	@Autowired
	private PortalMenuService portalMenuService;
	
	@Autowired
	private PortalPortletCategoryDao portalPortletCategoryDao;
	
	@Autowired
	private PortalPortletCategoryService portalPortletCategoryService;
	
	@Autowired
	private PortalPortletDao portalPortletDao;
	
	@Autowired
	private PortalPortletService portalPortletService;
	
	/**
	 * 포탈 로고 이미지 ID 조회
	 * @param portalId 포탈 ID
	 * @return 이미지 로고 ID
	 */
	public String readLogoImageId(String portalId) {
		
		Portal portal = portalDao.getPortal(portalId);
		String logoImageId = "";
		
		if(portal != null) {
			logoImageId = portal.getLogoImageId();
		}
		
		return logoImageId;
	}

	/**
	 * 포탈 생성
	 * @param portal 포탈 객체
	 */
	public void createPortal(Portal portal) {
		User sessionUser = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		//포탈 생성
		portal.setPortalId(idgenService.getNextId());
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(portal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(portal.getActive() == 0) {
			portal.setDefaultOption(0);
		}
		
		//선택된 포탈이 없을경우 
		if(StringUtil.isEmpty(portal.getSharePortalId())) {
			portal.setSharePortlet("N");
			portal.setShareMenu("N");
			portal.setShareQuickMenu("N");
			portal.setShareSystem("N");
			portal.setShareRole("N");
			portal.setShareRoleType("N");
			portal.setShareJobTitle("N");
			portal.setShareJobPosition("N");
			portal.setShareJobRank("N");
			portal.setShareJobClass("N");
			portal.setShareJobDuty("N");
		}
		
		//포탈 등록
		portalDao.createPortal(portal);
		
		//전체 로케일 리스트
		List<LocaleCode> LocaleCodeList = i18nMessageService.selectLocaleAll();
		
		//시스템 등록
		String systemCode = createSystem(portal, LocaleCodeList, sessionUser);

		//페이지 등록
		createPage(portal, LocaleCodeList, sessionUser, systemCode);
		
		//포틀릿 등록
		createPortlet(portal, systemCode);
		
		//메뉴 등록
		createMenu(portal, systemCode);
		
		//역할 타입 등록
		Map<String,String> roleTypeIds = createRoleType(portal);
		
		//역할 등록
		createRole(portal, roleTypeIds);
		
		//퀵메뉴 등록
		createQuickMenu(portal);
		
		//호칭 등록
		createJobTitle(portal);
		
		//직위 등록
		createJobPosition(portal);
		
		//직급 등록
		createJobRank(portal);
		
		//직군 등록
		createJobClass(portal);
		
		//직책 등록
		createJobDuty(portal);
		
		// 관리자 생성
		if(!StringUtil.isEmpty(portal.getAddAdmins())) {
			createPortalAdmin(sessionUser, portal.getPortalId(), portal.getAddAdmins());
		}
	}

	/**
	 * 포탈 조회
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal readPortal(String portalId) {
		return portalDao.getPortal(portalId);
	}
	
	/**
	 * 포탈 수정
	 * @param portal 포탈 객체
	 */
	public void updatePortal(Portal portal) {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(portal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(portal.getActive() == 0) {
			portal.setDefaultOption(0);
		}
		
		portalDao.updatePortal(portal);
		
		User sessionUser = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		// 관리자 수정
		if(!StringUtil.isEmpty(portal.getAdminUsers())) {
			
			updatePortalAdmin(sessionUser, portal.getPortalId(), portal.getAdminUsers());
		}
		
		// 관리자 생성
		if(!StringUtil.isEmpty(portal.getAddAdmins())) {
			
			createPortalAdmin(sessionUser, portal.getPortalId(), portal.getAddAdmins());
		}
		
		// 관리자 삭제
		if(!StringUtil.isEmpty(portal.getDeleteAdmins())) {
			
			deletePortalAdmin(portal.getPortalId(), portal.getDeleteAdmins());
		}
		
	}

	/**
	 * 포탈 리스트
	 * @param searchCondition 검색 조건
	 * @return SearchResult<Portal> 포탈 목록
	 */
	public SearchResult<Portal> listPortal(PortalSearchCondition searchCondition) {

		Integer count = portalDao.countListPortal(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Portal> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Portal>(searchCondition);

		} else {

			List<Portal> list = portalDao.listPortal(searchCondition);

			searchResult = new SearchResult<Portal>(list, searchCondition);
		}

		return searchResult;

	}

	/**
	 * 포탈 삭제
	 * @param portalId 포탈 ID
	 */
	public void removePortal(String portalId) {
		portalDao.removePortal(portalId);
	}

	/**
	 * 도메인으로 포탈 조회
	 * @param domain 도메인
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalDomain(String domain) {
		return portalDao.getPortalDomain(domain);
	}

	/**
	 * 디폴트 포탈 조회
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalDefault() {
		return portalDao.getPortalDefault();
	}

	/**
	 * ContextPath 로 포탈 조회
	 * @param contextPath 루트 경로
	 * @return Portal 포탈 정보
	 */
	public Portal readPortalContextPath(String contextPath) {
		return portalDao.getPortalContextPath(contextPath);
	}
	
	/**
	 * 포탈 관리자 권한 상세 정보를 조회
	 * @param aclResourcePermission
	 * @param portalId 포탈아이디
	 * @return aclResourcePermission 포탈 관리자 권한 상세 정보 리스트
	 */
	public ACLResourcePermission listPortalAdminPermission(ACLResourcePermission aclResourcePermission, String portalId) {
		
		// 사용자 권한 상세조회
		if (aclResourcePermission.getAssignUserIdList() != null && aclResourcePermission.getAssignUserIdList().size() > 0) {
			aclResourcePermission.setAssignUserDetailList(portalDao.listAdminUser(aclResourcePermission.getAssignUserIdList(), portalId));
		}

		return aclResourcePermission;
	}
	
	/**
	 * 포탈 관리자 정보 생성
	 * @param user 세션 사용자 정보
	 * @param portalId 포탈아이디
	 * @param addAdmins 생성할 관리자 정보
	 */
	private void createPortalAdmin(User user, String portalId, String addAdmins) {
		
		JSONArray addAdminArray = JSONArray.fromObject(JSONSerializer.toJSON(addAdmins));
		
		String className = "Portal";
		String resourceName = "Portal";
		String operationName = "MANAGE";
		
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setOperationName(operationName);
		
		List<String> assignUserIdList = new ArrayList<String>();
		
		for(int i=0; i<addAdminArray.size(); i++) {
			
			JSONObject obj = (JSONObject) addAdminArray.get(i);
			
			User addAdmin = new User();
			
			addAdmin.setUserId(obj.getString("userId"));
			//addAdmin.setUserPassword(EncryptUtil.encryptSha(obj.getString("userId") + obj.getString("userPassword")));//ikep 원래소스
			addAdmin.setUserPassword(EncryptUtil.encryptText( obj.getString("userPassword")));//무림제지 2012.08.31
			addAdmin.setUserName(obj.getString("userName"));
			addAdmin.setPortalId(portalId);
			addAdmin.setUserStatus("C");
			addAdmin.setUserEnglishName(addAdmin.getUserName());
			addAdmin.setTeamName("NONE");
			addAdmin.setTimezoneId(user.getTimezoneId());
			addAdmin.setLeader("0");
			addAdmin.setLocaleCode(user.getLocaleCode());			
			
			assignUserIdList.add(addAdmin.getUserId());
			
			ModelBeanUtil.bindRegisterInfo(addAdmin, user.getUserId(), user.getUserName());
			
			portalDao.createAdminUser(addAdmin);
			
		}
		
		aclResourcePermission.setAssignUserIdList(assignUserIdList);
		aclResourcePermission.setUserId(user.getUserId());
		aclResourcePermission.setUserName(user.getUserName());
		
		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
		|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (!isEmpty) {
			
			aclSystemDao.createUserPermission(aclResourcePermission, permissionId);
		}
	}
	
	/**
	 * 포탈 관리자 삭제
	 * @param portalId 포탈아이디
	 * @param deleteAdmins 삭제할 관리자 정보
	 */
	private void deletePortalAdmin(String portalId, String deleteAdmins) {
		
		JSONArray deleteAdminArray = JSONArray.fromObject(JSONSerializer.toJSON(deleteAdmins));
		
		for(int i=0; i<deleteAdminArray.size(); i++) {
			
			JSONObject obj = (JSONObject) deleteAdminArray.get(i);
			
			portalDao.deleteAdminUser(obj.getString("userId"), portalId);
		}
		
	}
	
	/**
	 * 포탈 관리자 정보 수정
	 * @param user 세션 사용자 정보
	 * @param portalId 포탈아이디
	 * @param adminUsers 수정할 관리자 정보
	 */
	private void updatePortalAdmin(User user, String portalId, String adminUsers) {
		
		JSONArray adminUserArray = JSONArray.fromObject(JSONSerializer.toJSON(adminUsers));
		
		for(int i=0; i<adminUserArray.size(); i++) {
			
			JSONObject obj = (JSONObject) adminUserArray.get(i);
			
			User adminUser = new User();
			adminUser.setUserId(obj.getString("userId"));
			adminUser.setUserPassword(obj.getString("userPassword"));
			adminUser.setUserName(obj.getString("userName"));
			
			if (!adminUser.getUserPassword().equals(obj.getString("preUserPassword"))) {
				//adminUser.setUserPassword(EncryptUtil.encryptSha(adminUser.getUserId() + adminUser.getUserPassword()));//ikep 원래소스
				adminUser.setUserPassword(EncryptUtil.encryptText(adminUser.getUserPassword()));//무림제지 2012.08.31
			}
			
			ModelBeanUtil.bindRegisterInfo(adminUser, user.getUserId(), user.getUserName());
			
			portalDao.updateAdminUser(user, portalId);
			
		}
	}

	public List<Portal> listPortalAll() {
		return portalDao.listPortalAll();
	}
	
	private String createSystem(Portal portal, List<LocaleCode> LocaleCodeList, User sessionUser) {
		String systemCode = "";
		
		// 권한 정보
		ACLResourcePermission aCLResourcePermissionReadSystem = new ACLResourcePermission();
		aCLResourcePermissionReadSystem.setOpen(1);
		
		PortalSecurity portalSecuritySystem = new PortalSecurity();
		portalSecuritySystem.setClassName("Portal-System");
		portalSecuritySystem.setOperationName("READ");
		portalSecuritySystem.setOwnerId(sessionUser.getUserId());
		portalSecuritySystem.setOwnerName(sessionUser.getUserName());
		portalSecuritySystem.setAclResourcePermissionRead(aCLResourcePermissionReadSystem);
		
		if("Y".equals(portal.getShareSystem())) {
			//시스템 목록 조회
			List<PortalSystem> listSystem = portalSystemDao.listSystemAll(portal.getSharePortalId());
			
			if(listSystem != null) {
				Map<String,String> systemCodes = new HashMap <String,String>();
				String orgSystemCode = "";
				PortalSystem portalSystem = null;
				
				//다국어 메세지 정보
				Map<String,String> systemMessageParam = new HashMap <String,String>();
				systemMessageParam.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				systemMessageParam.put("fieldName", "systemName");
				
				for(int i=0; i < listSystem.size();i++) {
					portalSystem = listSystem.get(i);
					
					orgSystemCode = portalSystem.getSystemCode(); 
					
					systemMessageParam.put("itemId", orgSystemCode);
					
					portalSystem.setSystemCode(idgenService.getNextId());
					
					if(systemCodes.get(portalSystem.getParentSystemCode()) != null) {
						portalSystem.setParentSystemCode(systemCodes.get(portalSystem.getParentSystemCode()));
					}

					portalSystem.setI18nMessageList(i18nMessageDao.selectMessagesByItemIdFieldName(systemMessageParam));
					portalSystem.setPortalId(portal.getPortalId());
					portalSystem.setRegisterId(sessionUser.getUserId());
					portalSystem.setRegisterName(sessionUser.getUserName());
					portalSystem.setUpdaterId(sessionUser.getUserId());
					portalSystem.setUpdaterName(sessionUser.getUserName());
					portalSystem.setSecurity(portalSecuritySystem);
					portalSystem.setAdminUserAddFlag("N");
					
					//시스템 생성
					portalSystemService.create(portalSystem);
					
					systemCodes.put(orgSystemCode, portalSystem.getSystemCode());
					
					if("Portal".equals(portalSystem.getSystemName())) {
						systemCode = portalSystem.getSystemCode();
					}
				}
			}
		} else {
			//시스템 생성시 다국어 등록
			List<I18nMessage> i18nMessageListSystem = new ArrayList<I18nMessage>();
			
			for(LocaleCode localeCode : LocaleCodeList) {
				I18nMessage i18nMessage = new I18nMessage();
				
				i18nMessage.setFieldName("systemName");
				i18nMessage.setLocaleCode(localeCode.getLocaleCode());
				i18nMessage.setItemMessage("Portal");
				
				i18nMessageListSystem.add(i18nMessage);
			}
			
			//시스템 Code
			systemCode = idgenService.getNextId();
			
			PortalSystem portalSystem = new PortalSystem();
			portalSystem.setPortalId(portal.getPortalId());
			portalSystem.setSystemCode(systemCode);
			portalSystem.setSystemName("Portal");
			portalSystem.setDescription("Portal");
			portalSystem.setSystemType("ITEM");
			portalSystem.setMainView(1);
			portalSystem.setUrlType("URL");
			portalSystem.setUrl("/portal/main/portalMain.do");
			portalSystem.setSortOrder("0000000000002");
			portalSystem.setTarget("INNER");
			portalSystem.setParentSystemCode("S000000");
			portalSystem.setOwnerId(sessionUser.getUserId());
			portalSystem.setRegisterId(sessionUser.getUserId());
			portalSystem.setRegisterName(sessionUser.getUserName());
			portalSystem.setUpdaterId(sessionUser.getUserId());
			portalSystem.setUpdaterName(sessionUser.getUserName());
			portalSystem.setI18nMessageList(i18nMessageListSystem);
			portalSystem.setSecurity(portalSecuritySystem);
			portalSystem.setAdminUserAddFlag("N");
			
			//시스템 생성
			portalSystemService.create(portalSystem);
		}
		
		return systemCode;
	}
	
	private void createPage(Portal portal, List<LocaleCode> LocaleCodeList, User sessionUser, String systemCode) {
		//페이지 생성시 다국어 등록
		List<I18nMessage> i18nMessageListPage = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : LocaleCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			
			i18nMessage.setFieldName("pageName");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setItemMessage("PortalPage");
			
			i18nMessageListPage.add(i18nMessage);
		}
		
		//페이지 ID
		String pageId = idgenService.getNextId();
		
		PortalPage portalPage = new PortalPage();
		portalPage.setPageId(pageId);
		portalPage.setPageName("PortalPage");
		portalPage.setCommon(1);
		portalPage.setOwnerId(sessionUser.getUserId());
		portalPage.setRegisterId(sessionUser.getUserId());
		portalPage.setRegisterName(sessionUser.getUserName());
		portalPage.setUpdaterId(sessionUser.getUserId());
		portalPage.setUpdaterName(sessionUser.getUserName());
		portalPage.setI18nMessageList(i18nMessageListPage);
		
		ACLResourcePermission aCLResourcePermissionReadPage = new ACLResourcePermission();
		aCLResourcePermissionReadPage.setOpen(1);
		
		PortalSecurity portalSecurityPage = new PortalSecurity();
		portalSecurityPage.setClassName("Portal-Page");
		portalSecurityPage.setOperationName("READ");
		portalSecurityPage.setOwnerId(sessionUser.getUserId());
		portalSecurityPage.setOwnerName(sessionUser.getUserName());
		portalSecurityPage.setAclResourcePermissionRead(aCLResourcePermissionReadPage);
		
		portalPage.setSecurity(portalSecurityPage);
		
		// 필수 입력 정보를 채운다 .
		List<I18nMessage> i18nMessageList = null;
		
		//VO에서 받아온 메세지를 사용
		i18nMessageList = i18nMessageService.fillMandatoryField(portalPage.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPage.getPageId());
		
		String systemUrlId = idgenService.getNextId();
		
		PortalSystemUrl portalSystemUrl = new PortalSystemUrl();
		portalSystemUrl.setSystemUrlId(systemUrlId);
		portalSystemUrl.setUrl("/portal/main/body.do?pageId="+portalPage.getPageId());
		portalSystemUrl.setSystemUrlName(portalPage.getPageName());
		portalSystemUrl.setRegisterId(sessionUser.getUserId());
		portalSystemUrl.setRegisterName(sessionUser.getUserName());
		portalSystemUrl.setUpdaterId(sessionUser.getUserId());
		portalSystemUrl.setUpdaterName(sessionUser.getUserName());
		
		// 필수 입력 정보를 채운다 .(시스템 URL)
		List<I18nMessage> i18nMessageSystemUrlList = new ArrayList<I18nMessage>();
		
		for(I18nMessage i18nMessage : portalPage.getI18nMessageList()) {
			I18nMessage i18nMessageSystemUrl = new I18nMessage();
			
			i18nMessageSystemUrl.setItemMessage(i18nMessage.getItemMessage());
			i18nMessageSystemUrl.setLocaleCode(i18nMessage.getLocaleCode());
			i18nMessageSystemUrl.setFieldName("systemUrlName");
			i18nMessageSystemUrl.setItemId(systemUrlId);
			i18nMessageSystemUrl.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_PORTAL);
			i18nMessageSystemUrl.setRegisterId(sessionUser.getUserId());
			i18nMessageSystemUrl.setRegisterName(sessionUser.getUserName());
			i18nMessageSystemUrl.setUpdaterId(sessionUser.getUserId());
			i18nMessageSystemUrl.setUpdaterName(sessionUser.getUserName());
			
			i18nMessageSystemUrlList.add(i18nMessageSystemUrl);	
		}
		
		//페이지 생성
		portalPage.setSystemCode(systemCode);
		portalSystemUrl.setPortalId(portal.getPortalId());
		
		portalPageService.createPage(i18nMessageList, portalPage, i18nMessageSystemUrlList, portalSystemUrl);
		
		//디폴트 테마 등록
		PortalTheme portalTheme = portalThemeDao.getDefaultTheme();
		portalTheme.setPortalId(portal.getPortalId());
		
		portalThemeService.createTheme(portalTheme);
		
		//공통 레이아웃 조회
		PortalLayout portalLayout = portalLayoutDao.getCommonLayout();

		Double width = 100.0;
		
		if("2".equals(portalLayout.getLayoutNum())) {
			width = 49.5;
		} else if("3".equals(portalLayout.getLayoutNum())) {
			width = 32.5;
		}
		
		//페이지 레이아웃 생성
		PortalPageLayout portalPageLayout = new PortalPageLayout();
		portalPageLayout.setPageId(portalPage.getPageId());
		portalPageLayout.setLayoutId(portalLayout.getLayoutId());
		portalPageLayout.setOwnerId(portal.getRegisterId());
		portalPageLayout.setRegisterId(portal.getRegisterId());
		portalPageLayout.setRegisterName(portal.getRegisterName());
		portalPageLayout.setUpdaterId(portal.getUpdaterId());
		portalPageLayout.setUpdaterName(portal.getUpdaterName());
		portalPageLayout.setWidth(width);
		
		for (int i = 0; i < Integer.parseInt(portalLayout.getLayoutNum()); i++) {
			portalPageLayout.setColIndex(i+1);
			portalPageLayout.setPageLayoutId(idgenService.getNextId());
			
			portalPageLayoutDao.createPageLayout(portalPageLayout);
		}
	}
	
	private void createPortlet(Portal portal, String systemCode) {
		if("Y".equals(portal.getSharePortlet())) {
			// 포탈시스템 포틀릿 카테고리 목록 조회
			List<PortalPortletCategory> portalPortletCategoryList = portalPortletCategoryDao.listPortalPortletCategoryAll(portal.getSharePortalId());
			Map<String,String> portalPortletCategoryIds = new HashMap <String,String>();
			
			if(portalPortletCategoryList != null) {
				PortalPortletCategory portalPortletCategory = null;
				String orgPortalPortletCategoryId = "";
				String portalPortletCategoryId = "";
				
				for(int i=0; i < portalPortletCategoryList.size();i++) {
					portalPortletCategory =  portalPortletCategoryList.get(i);
					
					portalPortletCategory.setSystemCode(systemCode);
					portalPortletCategory.setRegisterId(portal.getRegisterId());
					portalPortletCategory.setRegisterName(portal.getRegisterName());
					portalPortletCategory.setUpdaterId(portal.getUpdaterId());
					portalPortletCategory.setUpdaterName(portal.getUpdaterName());
					
					orgPortalPortletCategoryId = portalPortletCategory.getPortletCategoryId();
					
					//포틀릿 카테고리 생성
					portalPortletCategoryId = portalPortletCategoryService.createPortletCategory(portalPortletCategory);
					
					portalPortletCategoryIds.put(orgPortalPortletCategoryId, portalPortletCategoryId);
				}
			}
			
			// 포탈시스템 공유 포틀릿 목록 조회
			List<PortalPortlet> portalPortletList = portalPortletDao.listPortletShareAll(portal.getSharePortalId());
			
			if(portalPortletList != null) {
				// 권한 정보
				ACLResourcePermission aCLResourcePermissionReadPortlet = new ACLResourcePermission();
				aCLResourcePermissionReadPortlet.setOpen(1);
				
				PortalSecurity portalSecurityPortlet = new PortalSecurity();
				portalSecurityPortlet.setClassName("Portal-Portlet");
				portalSecurityPortlet.setOperationName("READ");
				portalSecurityPortlet.setOwnerId(portal.getRegisterId());
				portalSecurityPortlet.setOwnerName(portal.getRegisterName());
				portalSecurityPortlet.setAclResourcePermissionRead(aCLResourcePermissionReadPortlet);
				
				//다국어 메세지 정보
				Map<String,String> portletMessageParam = new HashMap <String,String>();
				portletMessageParam.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				portletMessageParam.put("fieldName", "portletName");
				
				Map<String,String> portletMessageParamDesc = new HashMap <String,String>();
				portletMessageParamDesc.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				portletMessageParamDesc.put("fieldName", "portletDesc");
				
				PortalPortlet portalPortlet = null;
				List<I18nMessage> i18nMessageList = null;
				List<I18nMessage> i18nMessagePortletNameList = null;
				List<I18nMessage> i18nMessagePortletDescList = null;
				
				for(int j=0; j < portalPortletList.size();j++) {
					portalPortlet = portalPortletList.get(j);
					
					portletMessageParam.put("itemId", portalPortlet.getPortletId());
					portletMessageParamDesc.put("itemId", portalPortlet.getPortletId());
					
					portalPortlet.setPortletId(idgenService.getNextId());
					portalPortlet.setSystemCode(systemCode);
					
					if(portalPortlet.getPortletCategoryId() != null && portalPortletCategoryIds.get(portalPortlet.getPortletCategoryId()) != null) {
						portalPortlet.setPortletCategoryId(portalPortletCategoryIds.get(portalPortlet.getPortletCategoryId()));
					}
					
					i18nMessageList = new ArrayList<I18nMessage>();
					
					i18nMessagePortletNameList = i18nMessageDao.selectMessagesByItemIdFieldName(portletMessageParam);
					
					for(I18nMessage i18nMessagePortletName : i18nMessagePortletNameList) {
						i18nMessageList.add(i18nMessagePortletName);
					}
					
					i18nMessagePortletDescList = i18nMessageDao.selectMessagesByItemIdFieldName(portletMessageParamDesc);
					
					for(I18nMessage i18nMessagePortletDesc : i18nMessagePortletDescList) {
						i18nMessageList.add(i18nMessagePortletDesc);
					}
					
					portalPortlet.setI18nMessageList(i18nMessageList);
					portalPortlet.setOwnerId(portal.getRegisterId());
					portalPortlet.setRegisterId(portal.getRegisterId());
					portalPortlet.setRegisterName(portal.getRegisterName());
					portalPortlet.setUpdaterId(portal.getUpdaterId());
					portalPortlet.setUpdaterName(portal.getUpdaterName());
					portalPortlet.setSecurity(portalSecurityPortlet);
					
					//포틀릿 생성
					portalPortletService.createPortalPortlet(portalPortlet);
				}
			}
		}
	}
	
	private void createMenu(Portal portal, String systemCode) {
		if("Y".equals(portal.getShareMenu())) {
			Map<String, String> menuParam = new HashMap<String, String>();
			menuParam.put("systemName", "Portal");
			menuParam.put("portalId", portal.getSharePortalId());
			
			// 포탈 메뉴 목록 조회
			List<PortalMenu> menuList = portalMenuDao.listMenuAll(menuParam);
			
			if(menuList != null) {
				// 권한 정보
				ACLResourcePermission aCLResourcePermissionReadMenu = new ACLResourcePermission();
				aCLResourcePermissionReadMenu.setOpen(1);
				
				PortalSecurity portalSecurityMenu = new PortalSecurity();
				portalSecurityMenu.setClassName("Portal-Menu");
				portalSecurityMenu.setOperationName("READ");
				portalSecurityMenu.setOwnerId(portal.getRegisterId());
				portalSecurityMenu.setOwnerName(portal.getRegisterName());
				portalSecurityMenu.setAclResourcePermissionRead(aCLResourcePermissionReadMenu);
				
				// 다국어 메세지 정보
				Map<String,String> menuMessageParam = new HashMap <String,String>();
				menuMessageParam.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				menuMessageParam.put("fieldName", "menuName");
				
				Map<String,String> menuIds = new HashMap <String,String>();
				PortalMenu portalMenu = null;
				String orgMenuId = "";
				
				for(int i=0; i < menuList.size();i++) {
					portalMenu = menuList.get(i);
					
					orgMenuId = portalMenu.getMenuId();
					
					menuMessageParam.put("itemId", orgMenuId);
					
					portalMenu.setMenuId(idgenService.getNextId());
					portalMenu.setSystemCode(systemCode);
					
					if(portalMenu.getParentMenuId() != null && menuIds.get(portalMenu.getParentMenuId()) != null) {
						portalMenu.setParentMenuId(menuIds.get(portalMenu.getParentMenuId()));
					}

					portalMenu.setI18nMessageList(i18nMessageDao.selectMessagesByItemIdFieldName(menuMessageParam));
					portalMenu.setRegisterId(portal.getRegisterId());
					portalMenu.setRegisterName(portal.getRegisterName());
					portalMenu.setUpdaterId(portal.getUpdaterId());
					portalMenu.setUpdaterName(portal.getUpdaterName());
					portalMenu.setSecurity(portalSecurityMenu);
					
					//메뉴 생성
					portalMenuService.create(portalMenu);
					
					menuIds.put(orgMenuId, portalMenu.getMenuId());
				}
			}
		}
	}
	
	private Map<String,String> createRoleType(Portal portal) {
		
		Map<String,String> roleTypeIds = new HashMap <String,String>();
		
		if("Y".equals(portal.getShareRoleType()) || "Y".equals(portal.getShareRole())) {
			// 역할타입 목록 조회
			List<RoleType> roleTypeList = roleTypeDao.listRoleTypePortal(portal.getSharePortalId());
			
			if(roleTypeList != null) {
				RoleType roleType = null;
				List<I18nMessage> roleTypeMessageList = null;
				String orgRoleTypeId = "";
				
				//다국어 메세지 정보
				Map<String,String> roleTypeMessageParam = new HashMap <String,String>();
				roleTypeMessageParam.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				roleTypeMessageParam.put("fieldName", "roleTypeName");
				
				for(int i=0; i < roleTypeList.size();i++) {
					roleType = roleTypeList.get(i);
					
					orgRoleTypeId = roleType.getRoleTypeId();
					
					roleTypeMessageParam.put("itemId", orgRoleTypeId);
					roleTypeMessageList = i18nMessageDao.selectMessagesByItemIdFieldName(roleTypeMessageParam);
					
					roleType.setRoleTypeId(idgenService.getNextId());
					
					roleTypeMessageList = i18nMessageService.fillMandatoryField(roleTypeMessageList, IKepConstant.ITEM_TYPE_CODE_PORTAL, roleType.getRoleTypeId());
					
					roleType.setRoleTypeName(roleType.getRoleTypeName());
					roleType.setPortalId(portal.getPortalId());
					roleType.setRegisterId(portal.getRegisterId());
					roleType.setRegisterName(portal.getRegisterName());
					roleType.setUpdaterId(portal.getUpdaterId());
					roleType.setUpdaterName(portal.getUpdaterName());
					roleType.setI18nMessageList(roleTypeMessageList);
					
					// 역할 타입 등록
					roleTypeService.create(roleType);
					
					roleTypeIds.put(orgRoleTypeId, roleType.getRoleTypeId());
				}
			}
		}
		
		return roleTypeIds;
	}
	
	private void createRole(Portal portal, Map<String,String> roleTypeIds) {
		if("Y".equals(portal.getShareRole())) {
			// 역할 목록 조회
			List<Role> listRole = roleDao.listRoleAll(portal.getSharePortalId());
			
			if(listRole != null) {
				Role role = null;
				
				for(int i=0; i < listRole.size();i++) {
					role = listRole.get(i);
					
					role.setRoleId(idgenService.getNextId());
					role.setRoleTypeId(roleTypeIds.get(role.getRoleTypeId()));
					role.setPortalId(portal.getPortalId());
					role.setRegisterId(portal.getRegisterId());
					role.setRegisterName(portal.getRegisterName());
					role.setUpdaterId(portal.getUpdaterId());
					role.setUpdaterName(portal.getUpdaterName());
					
					// 역할 생성
					roleDao.create(role);
				}
			}
		}
	}
	
	private void createQuickMenu(Portal portal) {
		if("Y".equals(portal.getShareQuickMenu())) {
			// 퀵메뉴 목록 조회
			List<PortalQuickMenu> quickMenuList = portalQuickMenuDao.listQuickMenu(portal.getSharePortalId());
			
			if(quickMenuList != null) {
				PortalQuickMenu portalQuickMenu = null;
				
				// 다국어 메세지 정보
				Map<String,String> quickMenuMessageParam = new HashMap <String,String>();
				quickMenuMessageParam.put("itemTypeCode", IKepConstant.ITEM_TYPE_CODE_PORTAL);
				quickMenuMessageParam.put("fieldName", "quickMenuName");
				
				// 권한 정보
				ACLResourcePermission aCLResourcePermissionReadQuickMenu = new ACLResourcePermission();
				aCLResourcePermissionReadQuickMenu.setOpen(1);
				
				PortalSecurity portalSecurityQuickMenu = new PortalSecurity();
				portalSecurityQuickMenu.setClassName("Portal-QuickMenu");
				portalSecurityQuickMenu.setOperationName("READ");
				portalSecurityQuickMenu.setOwnerId(portal.getRegisterId());
				portalSecurityQuickMenu.setOwnerName(portal.getRegisterName());
				portalSecurityQuickMenu.setAclResourcePermissionRead(aCLResourcePermissionReadQuickMenu);
				
				for(int i=0; i < quickMenuList.size();i++) {
					portalQuickMenu = quickMenuList.get(i);
					
					quickMenuMessageParam.put("itemId", portalQuickMenu.getQuickMenuId());
					
					portalQuickMenu.setI18nMessageList(i18nMessageDao.selectMessagesByItemIdFieldName(quickMenuMessageParam));
					portalQuickMenu.setQuickMenuId(idgenService.getNextId());
					portalQuickMenu.setPortalId(portal.getPortalId());
					portalQuickMenu.setRegisterId(portal.getRegisterId());
					portalQuickMenu.setRegisterName(portal.getRegisterName());
					portalQuickMenu.setUpdaterId(portal.getUpdaterId());
					portalQuickMenu.setUpdaterName(portal.getUpdaterName());
					portalQuickMenu.setSecurity(portalSecurityQuickMenu);
					
					// 퀵메뉴 생성
					portalQuickMenuService.create(portalQuickMenu);
				}
			}
		}
	}
	
	private void createJobTitle(Portal portal) {
		if("Y".equals(portal.getShareJobTitle())) {
			// 호칭 목록 조회
			List<JobTitle> jobTitleList = jobTitleDao.listJobTitleAll(portal.getSharePortalId());
			
			if(jobTitleList != null) {
				JobTitle jobTitle = null;
				
				for(int i=0; i < jobTitleList.size();i++) {
					jobTitle = jobTitleList.get(i);
					
					jobTitle.setJobTitleCode(idgenService.getNextId());
					jobTitle.setPortalId(portal.getPortalId());
					jobTitle.setRegisterId(portal.getRegisterId());
					jobTitle.setRegisterName(portal.getRegisterName());
					jobTitle.setUpdaterId(portal.getUpdaterId());
					jobTitle.setUpdaterName(portal.getUpdaterName());
					
					// 호칭 생성
					jobTitleDao.create(jobTitle);
				}
			}
		}
	}
	
	private void createJobPosition(Portal portal) {
		if("Y".equals(portal.getShareJobPosition())) {
			// 직위 목록 조회
			List<JobPosition> jobPositionList = jobPositionDao.listJobPositionAll(portal.getSharePortalId());
			
			if(jobPositionList != null) {
				JobPosition jobPosition = null;
				
				for(int i=0; i < jobPositionList.size();i++) {
					jobPosition = jobPositionList.get(i);
					
					jobPosition.setJobPositionCode(idgenService.getNextId());
					jobPosition.setPortalId(portal.getPortalId());
					jobPosition.setRegisterId(portal.getRegisterId());
					jobPosition.setRegisterName(portal.getRegisterName());
					jobPosition.setUpdaterId(portal.getUpdaterId());
					jobPosition.setUpdaterName(portal.getUpdaterName());
					
					// 직위 생성
					jobPositionDao.create(jobPosition);
				}
			}
		}
	}
	
	private void createJobRank(Portal portal) {
		if("Y".equals(portal.getShareJobRank())) {
			// 직급 목록 조회
			List<JobRank> jobRankList = jobRankDao.listJobRankAll(portal.getSharePortalId());
			
			if(jobRankList != null) {
				JobRank jobRank = null;
				
				for(int i=0; i < jobRankList.size();i++) {
					jobRank = jobRankList.get(i);
					
					jobRank.setJobRankCode(idgenService.getNextId());
					jobRank.setPortalId(portal.getPortalId());
					jobRank.setRegisterId(portal.getRegisterId());
					jobRank.setRegisterName(portal.getRegisterName());
					jobRank.setUpdaterId(portal.getUpdaterId());
					jobRank.setUpdaterName(portal.getUpdaterName());
					
					// 직급 생성
					jobRankDao.create(jobRank);
				}
			}
		}
	}
	
	private void createJobClass(Portal portal) {
		if("Y".equals(portal.getShareJobClass())) {
			// 직군 목록 조회
			List<JobClass> jobClassList = jobClassDao.listJobClassAll(portal.getSharePortalId());
			
			if(jobClassList != null) {
				JobClass jobClass = null;
				
				for(int i=0; i < jobClassList.size();i++) {
					jobClass = jobClassList.get(i);
					
					jobClass.setJobClassCode(idgenService.getNextId());
					jobClass.setPortalId(portal.getPortalId());
					jobClass.setRegisterId(portal.getRegisterId());
					jobClass.setRegisterName(portal.getRegisterName());
					jobClass.setUpdaterId(portal.getUpdaterId());
					jobClass.setUpdaterName(portal.getUpdaterName());
					
					// 직군 등록
					jobClassDao.create(jobClass);
				}
			}
		}
	}
	
	private void createJobDuty(Portal portal) {
		if("Y".equals(portal.getShareJobDuty())) {
			// 직책 목록 조회
			List<JobDuty> jobDutyList = jobDutyDao.listJobDutyAll(portal.getSharePortalId());
			
			if(jobDutyList != null) {
				JobDuty jobDuty = null;
				
				for(int i=0; i < jobDutyList.size();i++) {
					jobDuty = jobDutyList.get(i);
					
					jobDuty.setJobDutyCode(idgenService.getNextId());
					jobDuty.setPortalId(portal.getPortalId());
					jobDuty.setRegisterId(portal.getRegisterId());
					jobDuty.setRegisterName(portal.getRegisterName());
					jobDuty.setUpdaterId(portal.getUpdaterId());
					jobDuty.setUpdaterName(portal.getUpdaterName());
					
					// 직책 등록
					jobDutyDao.create(jobDuty);
				}
			}
		}
	}
}