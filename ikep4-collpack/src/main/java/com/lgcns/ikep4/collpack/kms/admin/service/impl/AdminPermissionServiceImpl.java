package com.lgcns.ikep4.collpack.kms.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.model.CompulsionTime;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Service("AdminPermissionService")
public class AdminPermissionServiceImpl  extends GenericServiceImpl<Object, String> implements AdminPermissionService {
	
	@Autowired
	private AdminPermissionDao adminPermissionDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 사원정보 목록
	 * @param AdminPermission 조건문
	 */
	public SearchResult<AdminPermission> getUserList(KmsAdminSearchCondition searchCondition){
		
		Integer count = adminPermissionDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminPermission> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminPermission>(searchCondition);
		}else{
			List<AdminPermission> userList = adminPermissionDao.getUserList(searchCondition);
			searchResult = new SearchResult<AdminPermission>(userList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminPermission> getUserRecommendReplyList(KmsAdminSearchCondition searchCondition){
		
		Integer count = adminPermissionDao.countByRecommendReplySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminPermission> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminPermission>(searchCondition);
		}else{
			List<AdminPermission> userList = adminPermissionDao.getUserRecommendReplyList(searchCondition);
			searchResult = new SearchResult<AdminPermission>(userList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminPermission> getCompulsionTimeLogList(KmsAdminSearchCondition searchCondition){
		Integer count = adminPermissionDao.countByCompulsionTimeLogSearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminPermission> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminPermission>(searchCondition);
		}else{
			List<AdminPermission> userList = adminPermissionDao.getCompulsionTimeLogList(searchCondition);
			searchResult = new SearchResult<AdminPermission>(userList, searchCondition);
		}
		
		return searchResult;
	}
	
	public List<AdminPermission> listCategory(AdminPermission categoryBoardId) {
		return adminPermissionDao.listCategory(categoryBoardId);
	}
	
	public List<AdminPermission> listCategoryUser(AdminPermission categoryBoardId) {
		return adminPermissionDao.listCategoryUser(categoryBoardId);
	}
	
	public List<AdminPermission> listSpecialUser() {
		return adminPermissionDao.listSpecialUser();
	}
	
	public List<AdminPermission> listKeyInfoPermission() {
		return adminPermissionDao.listKeyInfoPermission();
	}
	
	public void insertSpecialUser(AdminPermission specialUser){
		adminPermissionDao.deleteSpecialUser(specialUser);
		adminPermissionDao.insertSpecialUser(specialUser);
	}
	
	public void insertKeyInfoPermission(AdminPermission keyInfoPermission){
		adminPermissionDao.deleteKeyInfoPermission(keyInfoPermission);
		adminPermissionDao.insertKeyInfoPermission(keyInfoPermission);
	}
	
	public void insertNewKeyInfoPermission(){
		adminPermissionDao.insertNewKeyInfoPermission();
	}
	
	public void deleteSpecialUser(String userIds,String categoryIds) {
		
		userIds = StringUtil.nvl(userIds, "");
		categoryIds = StringUtil.nvl(categoryIds, "");
		
		String[] tempUserIds = userIds.split(",");
		String[] tempCategoryIds = categoryIds.split(",");
		if(tempUserIds!=null){
			for(int i=0;i<tempUserIds.length;i++){
				AdminPermission specialUser = new AdminPermission();
				specialUser.setUserId(tempUserIds[i]);
				specialUser.setCategoryId(tempCategoryIds[i]);
				adminPermissionDao.deleteSpecialUser(specialUser);
			}
		}
	}
	
	public void deleteKeyInfoPermission(String userIds) {
		
		userIds = StringUtil.nvl(userIds, "");
		
		String[] tempUserIds = userIds.split(",");
		if(tempUserIds!=null){
			for(int i=0;i<tempUserIds.length;i++){
				AdminPermission keyInfoPermission = new AdminPermission();
				keyInfoPermission.setUserId(tempUserIds[i]);
				adminPermissionDao.deleteKeyInfoPermission(keyInfoPermission);
			}
		}
	}
	
	public SearchResult<AdminPermission> getLeaderPermissionList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminPermissionDao.countLeaderPermBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminPermission> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminPermission>(searchCondition);
		}else{
			List<AdminPermission> leaderList = adminPermissionDao.getLeaderPermissionList(searchCondition);
			searchResult = new SearchResult<AdminPermission>(leaderList, searchCondition);
		}
		
		return searchResult;
	}

	public AdminPermission getUser(String empNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(AdminPermission adminPermission) {
		// TODO Auto-generated method stub
		
	}

	public List getWorkPlaceNameList() {
		return sort(adminPermissionDao.getWorkPlaceNameList());
		
	}
	
	public List<AdminPermission> getPeriodList() {
		List<AdminPermission> list = adminPermissionDao.getPeriodList();
		return list;
	}
	
	private List<String> sort(List workPlaceNameList){
		String workPlaceHead = messageSource.getMessage("message.collpack.kms.admin.permission.user.workPlaceName.head", null, Locale.getDefault());
		int head_quarter = workPlaceNameList.indexOf(workPlaceHead);
		
		if(head_quarter > -1){
			int pos = workPlaceNameList.size();
			
			workPlaceNameList.add(pos-1, workPlaceNameList.get(0));
			if(head_quarter > 1){
				workPlaceNameList.add(pos, workPlaceNameList.get(1));
				workPlaceNameList.set(1, workPlaceHead);
				workPlaceNameList.remove(head_quarter);
			}
			workPlaceNameList.set(0, "ALL");
		}
		return workPlaceNameList;
	}
	
	

	public AdminPermission getUserPermPopup(KmsAdminSearchCondition searchCondition) {
		AdminPermission adminPermission = adminPermissionDao.getUserPermInfo(searchCondition);
		
		if(adminPermission == null){
			adminPermissionDao.insertUserCnt(searchCondition);
			adminPermission = adminPermissionDao.getUserPermInfo(searchCondition);
		}
		/**
		searchCondition.setTeamName(adminPermission.getTeamName());
		searchCondition.setWorkPlaceCode(adminPermission.getWorkPlaceCode());
		String teamCnt = adminPermissionDao.getObliCntByTeam(searchCondition);
		adminPermission.setTeamObligationCnt(teamCnt);
		*/
		return adminPermission;
	}
	
	public AdminPermission getSpecialUserInfo(KmsAdminSearchCondition searchCondition){
		AdminPermission adminPermission = adminPermissionDao.getSpecialUserInfo(searchCondition);
		return adminPermission;
	}
	
	public AdminPermission getKeyInfoPermissionUser(KmsAdminSearchCondition searchCondition){
		AdminPermission adminPermission = adminPermissionDao.getKeyInfoPermissionUser(searchCondition);
		return adminPermission;
	}

	public void updateUserCnt(Map<String, String> paramMap) {
		
		adminPermissionDao.updateUserCnt(paramMap);
		
		
		
	}


	public SearchResult<AdminTeamLeader> getTeamByLeaderPermissionList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminPermissionDao.countTeamByLeaderPermBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		SearchResult<AdminTeamLeader> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminTeamLeader>(searchCondition);
		}else{
			List teamList = adminPermissionDao.getTeamByLeaderPermList(searchCondition);
			searchResult = new SearchResult<AdminTeamLeader>(teamList, searchCondition);
		}
		
		return searchResult;
	}
	
	public void insertCategoryNm(List<AdminPermission> receiveCategoryNmList) {
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			AdminPermission category = receiveCategoryNmList.get(i);
			String addNameList = category.getAddNameList();
			String delIdList   = category.getDelIdList();
			String updateIdList   = category.getUpdateIdList();
			String updateNameList = category.getUpdateNameList();
			String boardId = category.getBoardId();
			String alignList   = category.getAlignList();
			
			String[] arrayReceive;
			arrayReceive  = addNameList.split(",");
			
			String[] arrayModifyId;
			arrayModifyId = updateIdList.split(",");
			
			String[] arrayModifyNm;
			arrayModifyNm = updateNameList.split(",");
			
			String[] arrayDelId;
			arrayDelId    = delIdList.split(",");

			
			
			String[] arrayAlignName;
			arrayAlignName    = alignList.split(",");
			
			
			if(!"".equals(addNameList) ){
				for (int j = 0; j < arrayReceive.length; j++) {		
					category.setCategoryId(idgenService.getNextId());
					category.setAddNameList(arrayReceive[j]);
					category.setBoardId(boardId);
					this.adminPermissionDao.createCategoryNm(category);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					category.setUpdateIdList(arrayModifyId[j]);
					category.setUpdateNameList(arrayModifyNm[j]);
					category.setBoardId(boardId);
					this.adminPermissionDao.updateCategoryNm(category);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					category.setDelIdList(arrayDelId[j]);
					category.setBoardId(boardId);
					this.adminPermissionDao.deleteCategoryNm(category);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					category.setCategorySeqId(""+j);
					category.setAlignList(arrayAlignName[j]);
					category.setBoardId(boardId);
					this.adminPermissionDao.updateCategoryAlign(category);
				}	
			}
			
		}
	}
	
	public void insertCategoryUsers(String categoryUsers, User user) {
		String [] categoryUsers1 = StringUtils.split(categoryUsers, "^");
		String [] categoryUsers2 = null;
		this.adminPermissionDao.deleteCategoryUsers();
		AdminPermission category = new AdminPermission();
		for(int i=0; i<categoryUsers1.length; i++){
			categoryUsers2 = StringUtils.split(categoryUsers1[i], "_");
			category.setCategoryId(categoryUsers2[0]);
			category.setUserId(categoryUsers2[1]);
			category.setRegisterId(user.getUserId());
			category.setRegisterName(user.getUserName());
			this.adminPermissionDao.createCategoryUsers(category);
		}
		
	}
	
	public void insertMessage(AdminPermission category) {
		this.adminPermissionDao.deleteMessage();
		this.adminPermissionDao.createMessage(category);
		
	}
	
	public void saveCompulsionTimeSetting(CompulsionTime compulsionTime){
		this.adminPermissionDao.saveCompulsionTimeSetting(compulsionTime);
	}
	
	public void compulsionTimeClickSave(CompulsionTime compulsionTime){
		this.adminPermissionDao.compulsionTimeClickSave(compulsionTime);
	}

	public CompulsionTime selectCompulsionTimeSetting(){
		CompulsionTime compulsionTime = adminPermissionDao.selectCompulsionTimeSetting();
		return compulsionTime;
	}
	
	public Integer selectCompulsionTimeClickCheck(String checkFlg, String userId){
		CompulsionTime compulsionTime = new CompulsionTime();
		compulsionTime.setUserId(userId);
		compulsionTime.setCompulsionTimeClickFlg(checkFlg);
		int compulsionTimeClick = adminPermissionDao.selectCompulsionTimeClickCheck(compulsionTime);
		return compulsionTimeClick;
	}

	public void deleteTeamByLeader(String userId, String teamCodes) {
		
		teamCodes = StringUtil.nvl(teamCodes, "");
		AdminTeamLeader adminTeamLeader = new AdminTeamLeader();
		adminTeamLeader.setUserId(userId);
		adminTeamLeader.setTeamCodes(teamCodes);
		adminPermissionDao.deleteTeamByLeader(adminTeamLeader);
		
	}


	public String listTeamCodes(String workPlaceName, String isTitle) {
		List<AdminTeamLeader> teamList = adminPermissionDao.listTeamCodes(workPlaceName);
		String prefix = "<option value=\"";
		String suffix = "</option>";
		StringBuffer strBuf = new StringBuffer();
		
		if(isTitle.equals("0")){
			strBuf.append(prefix).append("").append("\">")
			.append( messageSource.getMessage("message.collpack.kms.admin.permission.team.leader.info.add.teamCode", null, Locale.getDefault())).append(suffix).append("\r\n");
		}
		
		for(AdminTeamLeader teamInfo : teamList){
			
			strBuf.append(prefix).append(teamInfo.getTeamCode()).append("\">")
				.append(teamInfo.getTeamName()).append(suffix).append("\r\n");
		}
		return strBuf.toString();
	}


	public SearchResult<AdminTeamLeader> getTeamCntByWorkPlaceList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminPermissionDao.countTeamCntByWorkPlacBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminTeamLeader> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminTeamLeader>(searchCondition);
		}else{
			List teamList = adminPermissionDao.getTeamCntByWorkPlaceList(searchCondition);
			searchResult = new SearchResult<AdminTeamLeader>(teamList, searchCondition);
		}
		
		return searchResult;
	}


	public void addTeamByLeader(AdminTeamLeader adminTeamLeader) {
		adminPermissionDao.insertTeamByLeader(adminTeamLeader);
		
	}
	
	/**
	 * Batch에서 DIVISION_PERMISSION테이블에 신규 추가된 리더를 가져와서 insert해준다
	 * */
	public void registTeamPermission() {
		
		List<HashMap<String, String>> leaderList = adminPermissionDao.getUnregistTeamLeader();
		
		for(HashMap<String, String> data : leaderList ){
			
			String userId = data.get("LEADER_ID");
			String groupId = data.get("TEAM_CODE");
			
			List<String> teamList = adminPermissionDao.getDivisionTree(groupId);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userId", userId);
			for(String teamCode : teamList){
				paramMap.put("teamCode", teamCode);
				Integer cnt = adminPermissionDao.getLeaderAndTeam(paramMap);
				if(cnt == 0)
					adminPermissionDao.insertTeamLeader(paramMap);				
			}
			
		}	
		
	}
	
	/**
	 * Batch에서 그달의 지식광장 의무건수를 insert해준다
	 * */
	public void registObligationCnt() {
		adminPermissionDao.insertObligationCnt();				
	}

	

	
	/**
	 * 사원정보
	 * @param userId
	 */
	//public AdminPermission getUser(String userId);
	
	
	/**
	 * 사원정보 수정
	 * @param userId
	 */
	//public void updateUser(AdminPermission adminPermission);

}
