package com.lgcns.ikep4.support.user.restful.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.restful.model.FavoriteList;
import com.lgcns.ikep4.support.user.restful.model.FavoriteListBody;
import com.lgcns.ikep4.support.user.restful.model.FavoriteListReturnData;
import com.lgcns.ikep4.support.user.restful.model.DeptList;
import com.lgcns.ikep4.support.user.restful.model.DeptListBody;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData0;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData1;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData2;
import com.lgcns.ikep4.support.user.restful.model.DeptListReturnData3;
import com.lgcns.ikep4.support.user.restful.model.PeopleList;
import com.lgcns.ikep4.support.user.restful.model.PeopleListBody;
import com.lgcns.ikep4.support.user.restful.model.PeopleListReturnData0;
import com.lgcns.ikep4.support.user.restful.model.PeopleListReturnData1;
import com.lgcns.ikep4.support.user.restful.model.ProfileInfo;
import com.lgcns.ikep4.support.user.restful.model.ProfileInfoBody;
import com.lgcns.ikep4.support.user.restful.model.ProfileInfoReturnData;
import com.lgcns.ikep4.support.user.restful.model.PeopleDetail;
import com.lgcns.ikep4.support.user.restful.model.PeopleDetailBody;
import com.lgcns.ikep4.support.user.restful.model.PeopleDetailReturnData0;
import com.lgcns.ikep4.support.user.restful.model.AddressInfoParam;
import com.lgcns.ikep4.support.user.restful.model.Vcard;
import com.lgcns.ikep4.support.user.restful.model.VcardFolderList;
import com.lgcns.ikep4.support.user.restful.model.VcardFolderListBody;
import com.lgcns.ikep4.support.user.restful.model.VcardFolderListReturnData;
import com.lgcns.ikep4.support.user.restful.model.VcardList;
import com.lgcns.ikep4.support.user.restful.model.VcardListBody;
import com.lgcns.ikep4.support.user.restful.model.VcardListReturnData;
import com.lgcns.ikep4.support.user.restful.model.VcardSearchCondition;
import com.lgcns.ikep4.support.user.util.UserConstant;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.favorite.util.FavoriteConstant;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.support.recent.service.RecentService;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.restful.model.Root;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.member.service.VcardFolderService;
import com.lgcns.ikep4.support.user.member.service.VcardService;
import com.lgcns.ikep4.support.user.restful.model.VcardFolder;
import com.lgcns.ikep4.util.lang.StringUtil;

@Path("/address")
@Component
public class AddressRestful {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PortalFavoriteService favoriteService;
	
	@Autowired
	private RecentService recentService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private VcardService vcardService;
	
	@Autowired
	private VcardFolderService vcardFolderService;

	/**
	 * 세션에 portal Locale 정보 저장
	 */
	public void setRequestAttributes(User user) {
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 세션에 portal Locale 정보 삭제
	 */
	public void removeRequestAttributes() {
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
	}
	
	
	/**
	 * 주소록-01.조직도
	 */
	@POST 
	@Path("/retrieveOrgList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DeptList getRetrieveOrgList(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		//세션 정보 저장
		this.setRequestAttributes(user);
		
		if ( null == addressInfoParam.getDeptId() || StringUtil.isEmpty(addressInfoParam.getDeptId()) ) {
			addressInfoParam.setDeptId(user.getGroupId());
		}
		
		DeptList result = new DeptList();
		try {
			UserSearchCondition searchCondition = new UserSearchCondition();
			
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(addressInfoParam.getPortalId());
			searchCondition.setUserId(addressInfoParam.getUserId());
			searchCondition.setGroupId(addressInfoParam.getDeptId());
			searchCondition.setPagePerRecord(UserConstant.MOBILE_MAX_PAGE_SIZE);

			// 0. 해당 조직원 리스트
			SearchResult<User> searchResult0 = userService.listForMobile(searchCondition);
			List<DeptListReturnData0> returnDataList0 = new ArrayList<DeptListReturnData0>();		
			if (searchResult0.getEntity() != null){
				for ( User userInfo : searchResult0.getEntity() ) {
					DeptListReturnData0 returnData0 = new DeptListReturnData0();
					
					returnData0.setUserId			(userInfo.getUserId());
					returnData0.setUserName			(userInfo.getUserName());
					returnData0.setUserDept			(userInfo.getTeamName());
					returnData0.setUserTitle		(userInfo.getJobTitleName());
					returnData0.setMail				(userInfo.getMail());
					returnData0.setOfficePhoneNo	(userInfo.getOfficePhoneNo());
					returnData0.setMobile			(userInfo.getMobile());
					
					if ( null != userInfo.getFavoriteId() && !StringUtil.isEmpty(userInfo.getFavoriteId()) ) {
						returnData0.setIsFavorite("1");
					} else {
						returnData0.setIsFavorite("0");
					}
		
					returnDataList0.add(returnData0);
				}
			}

			// 1. 조회 요청이 들어온 조직 하위 레벨에 있는 조직들의 리스트
			UserSearchCondition searchCondition1 = new UserSearchCondition();
			
			searchCondition1.setUserLocaleCode(user.getLocaleCode());
			searchCondition1.setPortalId(addressInfoParam.getPortalId());
			searchCondition1.setUserId(addressInfoParam.getUserId());
			searchCondition1.setGroupId(addressInfoParam.getDeptId());
			searchCondition1.setPagePerRecord(UserConstant.MOBILE_MAX_PAGE_SIZE);			
			searchCondition1.setSearchColumn("child_group_id");
			
			SearchResult<User> searchResult1 = userService.groupListMobile(searchCondition1);

			List<DeptListReturnData1> returnDataList1 = new ArrayList<DeptListReturnData1>();
			if (searchResult1.getEntity() != null){
				for ( User userInfo : searchResult1.getEntity() ) {
					DeptListReturnData1 returnData1 = new DeptListReturnData1();
					
					returnData1.setDeptId			(userInfo.getGroupId());
					returnData1.setDeptName			(userInfo.getTeamName());
	
					returnDataList1.add(returnData1);
				}
			}

			// 2. 조회 요청이 들어온 조직 상위 레벨에 있는 조직들의 리스트
			UserSearchCondition searchCondition2 = new UserSearchCondition();
			
			searchCondition2.setUserLocaleCode(user.getLocaleCode());
			searchCondition2.setPortalId(addressInfoParam.getPortalId());
			searchCondition2.setUserId(addressInfoParam.getUserId());
			searchCondition2.setGroupId(addressInfoParam.getDeptId());
			searchCondition2.setPagePerRecord(UserConstant.MOBILE_MAX_PAGE_SIZE);	
			searchCondition2.setSearchColumn("parent_group_id");			

			SearchResult<User> searchResult2 = userService.groupListForTreeMobile(searchCondition2);

			List<DeptListReturnData2> returnDataList2 = new ArrayList<DeptListReturnData2>();

			// 조직도에 최상위 조직이 다수로 들어가는 경우에 대한 처리를 위해 임시로 사용함.(2013-03-10)
			if (!StringUtil.isEmpty(addressInfoParam.getDeptId()) && !addressInfoParam.getDeptId().equals("0")) {
				DeptListReturnData2 returnData2 = new DeptListReturnData2();
				returnData2.setParentDeptId		("0");
				returnData2.setParentDeptName	("Organization");
				returnData2.setParentDeptLevel	("1");	
				returnDataList2.add(returnData2);
			}
			
			if (searchResult2.getEntity() != null){
				for ( User userInfo : searchResult2.getEntity() ) {
					DeptListReturnData2 returnData2 = new DeptListReturnData2();
					
					returnData2.setParentDeptId			(userInfo.getGroupId());
					returnData2.setParentDeptName		(userInfo.getTeamName());
					returnData2.setParentDeptLevel		(userInfo.getGroupLevel());
	
					returnDataList2.add(returnData2);
				}
			}

			// 3. 조회 요청이 들어온 조직 정보
			UserSearchCondition searchCondition3 = new UserSearchCondition();
			
			searchCondition3.setUserLocaleCode(user.getLocaleCode());
			searchCondition3.setPortalId(addressInfoParam.getPortalId());
			searchCondition3.setUserId(addressInfoParam.getUserId());
			searchCondition3.setGroupId(addressInfoParam.getDeptId());
			searchCondition3.setPagePerRecord(UserConstant.MOBILE_MAX_PAGE_SIZE);	
			searchCondition3.setSearchColumn("group_id");

			SearchResult<User> searchResult3 = userService.groupListForTreeMobile(searchCondition3);

			DeptListReturnData3 returnData3 = new DeptListReturnData3();
			if (searchResult3.getEntity() != null){
				for ( User userInfo : searchResult3.getEntity() ) {					
					returnData3.setSelectedDeptId		(userInfo.getGroupId());
					returnData3.setSelectedDeptName		(userInfo.getTeamName());
					returnData3.setSelectedDeptLevel	(userInfo.getGroupLevel());	
				}
			}
			
			// 조직도에 최상위 조직이 다수로 들어가는 경우에 대한 처리를 위해 임시로 사용함.(2013-03-10)
			if (addressInfoParam.getDeptId().equals("0")) {
				returnData3.setSelectedDeptId		("0");
				returnData3.setSelectedDeptName		("Organization");
				returnData3.setSelectedDeptLevel	("1");	
			}
			
			Head head = new Head(0, Response.Status.OK.toString());
			DeptListBody body = new DeptListBody(returnDataList0, returnDataList1, returnDataList2, returnData3);
			result = new DeptList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new DeptList(head, null);
		}
		
		//세션 정보 삭제
		this.removeRequestAttributes();
				
		return result;
	}

	/**
	 * 주소록-02.주소록 검색(조직도)
	 */
	@POST 
	@Path("/retrieveUserList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PeopleList getRetrieveUserList(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		//세션 정보 저장
		this.setRequestAttributes(user);
		
		PeopleList result = new PeopleList();
		try {
			UserSearchCondition searchCondition = new UserSearchCondition();
			
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(addressInfoParam.getPortalId());
			searchCondition.setUserId(addressInfoParam.getUserId());
			searchCondition.setSearchWord(addressInfoParam.getSearchWord());
			searchCondition.setUserFlag(addressInfoParam.getUserFlag());

			searchCondition.setPagePerRecord(Integer.parseInt(addressInfoParam.getListSize()));
			searchCondition.setPageIndex(Integer.parseInt(addressInfoParam.getPage()));
			searchCondition.setStartRowIndex(Integer.parseInt(addressInfoParam.getPage())-1);
			
			// 0. 검색된 사용자 리스트
			SearchResult<User> searchResult = userService.listAll(searchCondition);

			List<PeopleListReturnData0> returnDataList = new ArrayList<PeopleListReturnData0>();
			if (searchResult.getEntity()!=null) {
				for ( User userInfo : searchResult.getEntity() ) {
	
					PeopleListReturnData0 returnData = new PeopleListReturnData0();
					
					returnData.setUserId			(userInfo.getUserId());
					returnData.setUserName			(userInfo.getUserName());
					returnData.setUserDept			(userInfo.getTeamName());
					//직책정보가 있으면 직책정보를 디스플레이
					if(userInfo.getJobDutyCode() != null)
					{
						returnData.setUserTitle(userInfo.getJobDutyName());
					}else{
						returnData.setUserTitle(userInfo.getJobTitleName());
					}
					
					returnData.setMail				(userInfo.getMail());
					returnData.setOfficePhoneNo		(userInfo.getOfficePhoneNo());
					returnData.setMobile			(userInfo.getMobile());

					if ( null != userInfo.getFavoriteId() && !StringUtil.isEmpty(userInfo.getFavoriteId()) ) {
						returnData.setIsFavorite	("1");
					} else {
						returnData.setIsFavorite	("0");
					}
					returnData.setUserFlag			("I");
					
					returnDataList.add(returnData);
				}
			}
			
			// 1. 검색된 페이지 정보
			int pageCount = 0;
			if (searchCondition.getPageCount()!=null) {
				pageCount = searchCondition.getPageCount();
			}
			int recordCount = 0;
			if (searchResult.getRecordCount()!=null) {
				recordCount = searchResult.getRecordCount();
			}
			
			PeopleListReturnData1 returnData = new PeopleListReturnData1(pageCount, recordCount);
			
			Head head = new Head(0, Response.Status.OK.toString());
			PeopleListBody body = new PeopleListBody(returnDataList, returnData);
			result = new PeopleList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new PeopleList(head, null);
		}
		
		//세션 정보 삭제
		this.removeRequestAttributes();
		
		return result;
	}	

	/**
	 * 주소록-03. 주소록 상세조회(조직도)
	 */
	@POST 
	@Path("/retrieveUserDetail")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ProfileInfo getDetailForPeoplePOST(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		//세션 정보 저장
		this.setRequestAttributes(user);
		
		ProfileInfo result = new ProfileInfo();

		if (null == addressInfoParam.getSearchUserId() || StringUtil.isEmpty(addressInfoParam.getSearchUserId())) {
			Head head = new Head(1, Response.Status.NO_CONTENT.toString());
			result = new ProfileInfo(head, null);
			return result;
		}
		
		try {
			UserSearchCondition searchCondition = new UserSearchCondition();	
			
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(addressInfoParam.getPortalId());		
			searchCondition.setUserId(addressInfoParam.getUserId());		
			searchCondition.setSearchUserId(addressInfoParam.getSearchUserId());
			
			// 0. 검색된 사용자 정보
			SearchResult<User> searchResult = userService.listAll(searchCondition);

			ProfileInfoReturnData returnData = new ProfileInfoReturnData();
			if (searchResult.getEntity() != null) {
				for ( User userInfo : searchResult.getEntity() ) {											
					returnData.setUserId			(userInfo.getUserId());
					returnData.setUserName			(userInfo.getUserName());
					returnData.setUserDept			(userInfo.getTeamName());
					//직책정보가 있으면 직책정보를 디스플레이
					if(userInfo.getJobDutyCode() != null)
					{
						returnData.setUserTitle(userInfo.getJobDutyName());
					}else{
						returnData.setUserTitle(userInfo.getJobTitleName());
					}
					returnData.setMail				(userInfo.getMail());
					returnData.setOfficePhoneNo		(userInfo.getOfficePhoneNo());
					returnData.setMobile			(userInfo.getMobile());					
					returnData.setProfilePicturePath(userInfo.getProfilePicturePath());
					if ( null != userInfo.getFavoriteId() && !StringUtil.isEmpty(userInfo.getFavoriteId()) ) {
						returnData.setIsFavorite	("1");
					} else {
						returnData.setIsFavorite	("0");
					}
					returnData.setUserFlag			("I");

				}
			}
			
			Head head = new Head(0, Response.Status.OK.toString());
			ProfileInfoBody body = new ProfileInfoBody(returnData);
			result = new ProfileInfo(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new ProfileInfo(head, null);
		}

		//세션 정보 삭제
		this.removeRequestAttributes();
		
		return result;
	}
	
	/**
	 * 주소록-04.즐겨찾기 리스트
	 */
	@POST 
	@Path("/retrieveFavoriteList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FavoriteList getRetrieveFavoriteList(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		//세션 정보 저장
		this.setRequestAttributes(user);

		FavoriteList result = new FavoriteList();
		try {
			// 0. 최근 Contact List 
			RecentSearchCondition searchCondition0 = new RecentSearchCondition();

			if (!StringUtil.isEmpty(user.getUserId())) {
				searchCondition0.setUserLocaleCode(user.getLocaleCode());
				searchCondition0.setRegisterId(user.getUserId());
			}
			searchCondition0.setPagePerRecord(FavoriteConstant.MOBILE_PAGE_SIZE);
			searchCondition0.setSortColumn("USER_NAME");
			searchCondition0.setSortType("ASC");
			
			SearchResult<Recent> searchResult0 = recentService.getAllForPeople(searchCondition0);

			List<FavoriteListReturnData> returnDataList0 = new ArrayList<FavoriteListReturnData>();		
			if (searchResult0.getEntity() != null){		
				for ( Recent recent : searchResult0.getEntity() ) {
					
					FavoriteListReturnData returnData0 = new FavoriteListReturnData();
					
					returnData0.setUserId			(recent.getTargetId());
					returnData0.setUserName			(recent.getTitle());
					returnData0.setUserDept			(recent.getTeamName());
					returnData0.setUserTitle		(recent.getJobTitleName());
					returnData0.setMail				(recent.getMail());
					returnData0.setOfficePhoneNo	(recent.getOfficePhoneNo());
					returnData0.setMobile			(recent.getMobile());
					if ( null != recent.getFavoriteId() && !StringUtil.isEmpty(recent.getFavoriteId()) ) {
						returnData0.setIsFavorite	("1");
					} else {
						returnData0.setIsFavorite	("0");
					}
		
					returnDataList0.add(returnData0);
				}
			}

			// 1. Favorite List
			PortalFavoriteSearchCondition searchCondition1 = new PortalFavoriteSearchCondition();

			if (!StringUtil.isEmpty(addressInfoParam.getUserId())) {
				searchCondition1.setUserLocaleCode(user.getLocaleCode());
				searchCondition1.setRegisterId(addressInfoParam.getUserId());
			}
			searchCondition1.setPagePerRecord(FavoriteConstant.MOBILE_PAGE_SIZE);
			searchCondition1.setSortColumn("USER_NAME");
			searchCondition1.setSortType("ASC");

			SearchResult<PortalFavorite> searchResult1 = favoriteService.getAllForPeople(searchCondition1);
			
			List<FavoriteListReturnData> returnDataList1 = new ArrayList<FavoriteListReturnData>();			
			if (searchResult1.getEntity() != null){
				for ( PortalFavorite portalFavorite : searchResult1.getEntity() ) {
					FavoriteListReturnData returnData1 = new FavoriteListReturnData();
					
					returnData1.setUserId			(portalFavorite.getTargetId());
					returnData1.setUserName			(portalFavorite.getTitle());
					returnData1.setUserDept			(portalFavorite.getTeamName());
					returnData1.setUserTitle		(portalFavorite.getJobTitleName());
					returnData1.setMail				(portalFavorite.getMail());
					returnData1.setOfficePhoneNo	(portalFavorite.getOfficePhoneNo());
					returnData1.setMobile			(portalFavorite.getMobile());
					if ( null != portalFavorite.getFavoriteId() && !StringUtil.isEmpty(portalFavorite.getFavoriteId()) ) {
						returnData1.setIsFavorite	("1");
					} else {
						returnData1.setIsFavorite	("0");
					}
					returnData1.setUserFlag			("I");
		
					returnDataList1.add(returnData1);
				}
			}

			Head head = new Head(0, Response.Status.OK.toString());
			FavoriteListBody body = new FavoriteListBody(returnDataList1);
			result = new FavoriteList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new FavoriteList(head, null);
		}
		
		//세션 정보 삭제
		this.removeRequestAttributes();
		
		return result;
	}	

	/**
	 * 주소록-05. 즐겨찾기 변경
	 */
	@POST 
	@Path("/updateFavoriteUser")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateFavoriteUser(AddressInfoParam addressInfoParam) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		// addrUserId로 사용자 정보를 가져온다.
		User addrUser = userDao.get(addressInfoParam.getAddrUserId());
		
		try {
			PortalFavorite favorite = new PortalFavorite();
			favorite.setType("PEOPLE");
			favorite.setItemTypeCode("PF");
			favorite.setTargetId(addrUser.getUserId());
			favorite.setTargetTitle(addrUser.getUserName());
			favorite.setRegisterId(user.getUserId());
			favorite.setRegisterName(user.getUserName());
			favorite.setUpdaterId(user.getUserId());
			favorite.setUpdaterName(user.getUserName());

			String favoriteId = "";
			if (favoriteService.exists(favorite)) {						
				favoriteId = favoriteService.getFavoriteId(favorite);
			}
			
			if (addressInfoParam.getIsFavorite().equals("0")) {
				if (null != favoriteId && !favoriteId.isEmpty()) {
					try {
						favorite.setFavoriteId(favoriteId);
						favorite.setTargetId(addrUser.getUserId());
						favorite.setRegisterId(user.getUserId());

						favoriteService.delete(favorite);

						Head head = new Head(0, Response.Status.OK.toString());
						result = new Root(head, null);
					} catch (Exception ex) {
						Head head = new Head(1, "Error");
						result = new Root(head, null);
					}
				} else {
					// 존재하는 정보가 없으므로 정상처리로 Return
					Head head = new Head(0, Response.Status.OK.toString());
					result = new Root(head, null);
				}
			} else if (addressInfoParam.getIsFavorite().equals("1")){
				if (null != favoriteId && !favoriteId.isEmpty()) {
					// 이미 정보가 존재하므로  정상처리로 Return
					Head header = new Head(0, Response.Status.OK.toString());
					result = new Root(header, null);
				} else {
					try {

						favoriteId = favoriteService.create(favorite);

						Head head = new Head(0, Response.Status.CREATED.toString());
						result = new Root(head, null);
					} catch (Exception ex) {
						Head head = new Head(1, "Error");
						result = new Root(head, null);
					}
				}
			} else {
				Head head = new Head(1, Response.Status.NO_CONTENT.toString());
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;		
	}

	/**
	 * 주소록-인원 상세조회 화면
	 */
	@POST 
	@Path("/detailForPeople")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PeopleDetail getDetailForPeoplePOST(@FormParam("portalId") String portalId, 
											   	@FormParam("userId") String userId,
											   	@FormParam("searchUserId") String searchUserId,
											   	@FormParam("date") String date ) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(userId);
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);

		PeopleDetail result = new PeopleDetail();
		try {
			// 0. 검색된 사용자 정보
			User profileInfo = userService.read(searchUserId);

			PeopleDetailReturnData0 returnData0 = new PeopleDetailReturnData0();
		
			returnData0.setUserId			(profileInfo.getUserId());
			returnData0.setUserName			(profileInfo.getUserName());
			returnData0.setTeamName			(profileInfo.getTeamName());
			returnData0.setMobile			(profileInfo.getMobile());
			returnData0.setMail				(profileInfo.getMail());
			returnData0.setOfficePhoneNo	(profileInfo.getOfficePhoneNo());
			returnData0.setJobTitleName		(profileInfo.getJobTitleName());
			returnData0.setProfilePicturePath(profileInfo.getProfilePicturePath());
			
			if ( null != profileInfo.getFavoriteId() && !StringUtil.isEmpty(profileInfo.getFavoriteId()) ) {
				returnData0.setFavoriteFlag	("Y");
			} else {
				returnData0.setFavoriteFlag	("N");
			}
	
			Head head = new Head(0, Response.Status.OK.toString());
			PeopleDetailBody body = new PeopleDetailBody(returnData0);
			result = new PeopleDetail(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new PeopleDetail(head, null);
		}
				
		return result;
	}	
	
	/**
	 * 주소록-06.명함그룹 리스트
	 */
	@POST 
	@Path("/retrieveVcardGroupList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public VcardFolderList getRetrieveVcardGroupList(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);

		VcardFolderList result = new VcardFolderList();
		try {
			// 0. 명함그룹 List
			
			List<VcardFolder> vcardFolderList = vcardFolderService.listUserFolderTree(addressInfoParam.getUserId(), addressInfoParam.getPortalId());
			
			List<VcardFolderListReturnData> returnDataList = new ArrayList<VcardFolderListReturnData>();	
			if(vcardFolderList != null){				
				for ( VcardFolder vcardFolder : vcardFolderList ) {				
					VcardFolderListReturnData returnData = new VcardFolderListReturnData();
					
					returnData.setFolderId			(vcardFolder.getFolderId());
					returnData.setLevel				(1);
					returnData.setIsHasChildren		("0");
					returnData.setFolderParentId	("0");
					returnData.setFolderName		(vcardFolder.getFolderName());
//					returnData.setSiblingOrder			(vcardFolder.getSortOrder()+1);
					returnData.setRootId		("0");
					
					returnDataList.add(returnData);
				}
			}

			Head head = new Head(0, Response.Status.OK.toString());
			VcardFolderListBody body = new VcardFolderListBody(returnDataList);
			result = new VcardFolderList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new VcardFolderList(head, null);
		}
				
		return result;
	}	

	/**
	 * 주소록-07.명함연락처 리스트
	 */
	@POST 
	@Path("/retrieveVcardUserList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public VcardList getRetrieveVcardUserList(AddressInfoParam addressInfoParam) {

		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(addressInfoParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);

		VcardList result = new VcardList();
		try {
			// 0. 명함정보
			VcardSearchCondition searchCondition = new VcardSearchCondition();
			searchCondition.setUserId(addressInfoParam.getUserId());
			searchCondition.setFolderId(addressInfoParam.getFolderId());

			List<Vcard> vcardList = vcardService.listUserVcardCheckFolder(searchCondition);
			
			List<VcardListReturnData> returnDataList = new ArrayList<VcardListReturnData>();	

			if(vcardList != null){		
				for ( Vcard vcard : vcardList ) {
					VcardListReturnData returnData = new VcardListReturnData();
					
					returnData.setUserId			(vcard.getVisitingcardId());
					returnData.setUserName			(vcard.getLastName());
					returnData.setCompanyName		(vcard.getCompanyName());
					returnData.setUserTitle			(vcard.getJobRankName());
					returnData.setMail				(vcard.getMail());
					returnData.setOfficePhoneNo		(vcard.getOfficePhoneno());
					returnData.setMobile			(vcard.getMobile());
	
					returnDataList.add(returnData);
				}
			}

			Head head = new Head(0, Response.Status.OK.toString());
			VcardListBody body = new VcardListBody(returnDataList);
			result = new VcardList(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new VcardList(head, null);
		}
				
		return result;
	}	

}