package com.lgcns.ikep4.collpack.kms.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminWinnerManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminWinnerManageService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Service("AdminWinnerManageService")
public class AdminWinnerManageServiceImpl extends GenericServiceImpl<AdminWinner, String> implements AdminWinnerManageService {

	@Autowired
	private AdminWinnerManageDao adminWinnerManageDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private IdgenService idgenService;
	
	public String create(AdminWinner arg0) {
		// TODO Auto-generated method stub
		return null;
	} 

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public AdminWinner read(String itemSeq) {
		return adminWinnerManageDao.get(itemSeq);
	}
	
	public AdminWinner readWinnerPeople(String itemSeq) {
		return adminWinnerManageDao.getWinnerPeople(itemSeq);
	}
	
	public AdminWinner readWinnerTeam(String itemSeq) {
		return adminWinnerManageDao.getWinnerTeam(itemSeq);
	}
	
	public AdminWinner readWinnerAward(String itemSeq) {
		return adminWinnerManageDao.getWinnerAward(itemSeq);
	}

	public void update(AdminWinner adminWinner) {
		adminWinnerManageDao.update(adminWinner);

	}
	
	public void updateWinnerPeople(AdminWinner adminWinner) {
		adminWinnerManageDao.updateWinnerPeople(adminWinner);

	}
	
	public void updateWinnerTeam(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user) {
		
		AdminWinner tempAdminWinner = adminWinnerManageDao.getWinnerTeam(adminWinner.getItemSeq());
		
		if(tempAdminWinner != null && !StringUtil.isEmpty(tempAdminWinner.getImageFileId())) {
			fileService.removeFile(tempAdminWinner.getImageFileId());
		}
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "KMS_TM", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, adminWinner.getWinnerId(), "KMS_TM", user);
			
			adminWinner.setImageFileId(fileId);
		}
		adminWinnerManageDao.updateWinnerTeam(adminWinner);

	}
	
	public void updateWinnerAward(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user) {
		
		AdminWinner tempAdminWinner = adminWinnerManageDao.getWinnerAward(adminWinner.getItemSeq());
		
		if(tempAdminWinner != null && !StringUtil.isEmpty(tempAdminWinner.getImageFileId())) {
			fileService.removeFile(tempAdminWinner.getImageFileId());
		}
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "KMS_AW", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, adminWinner.getWinnerId(), "KMS_AW", user);
			
			adminWinner.setImageFileId(fileId);
		}
		adminWinnerManageDao.updateWinnerAward(adminWinner);

	}

	public SearchResult<AdminWinner> getWinnerList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerList(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerPeopleList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countPeopleBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerPeopleList(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerTeamList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countTeamBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerTeamList(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerAwardList(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countAwardBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerAwardList(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}

	public void deleteWinner(String itemSeqs) {
		
		itemSeqs = StringUtil.nvl(itemSeqs, "");
		AdminWinner adminWinner = new AdminWinner();
		adminWinner.setItemSeqs(itemSeqs);
		adminWinnerManageDao.deleteWinner(adminWinner);
	}
	
	public void deleteWinnerPeople(String itemSeqs) {
		
		itemSeqs = StringUtil.nvl(itemSeqs, "");
		
		String[] tempItemSeqs = itemSeqs.split(",");
		if(tempItemSeqs!=null){
			for(int i=0;i<tempItemSeqs.length;i++){
				AdminWinner adminWinner = new AdminWinner();
				adminWinner.setItemSeqs(tempItemSeqs[i]);
				adminWinnerManageDao.deleteWinnerPeople(adminWinner);
			}
		}
	}
	
	public void deleteWinnerTeam(String itemSeqs) {
		itemSeqs = StringUtil.nvl(itemSeqs, "");
		String[] tempItemSeqs = itemSeqs.split(",");
		if(tempItemSeqs!=null){
			for(int i=0;i<tempItemSeqs.length;i++){
				AdminWinner tempAdminWinner = adminWinnerManageDao.getWinnerTeam(tempItemSeqs[i]);
				
				if(tempAdminWinner != null && !StringUtil.isEmpty(tempAdminWinner.getImageFileId())) {
					fileService.removeFile(tempAdminWinner.getImageFileId());
				}
				AdminWinner adminWinner = new AdminWinner();
				adminWinner.setItemSeqs(itemSeqs);
				adminWinnerManageDao.deleteWinnerTeam(adminWinner);
			}
		}
		
	}
	
	public void deleteWinnerAward(String itemSeqs) {
		itemSeqs = StringUtil.nvl(itemSeqs, "");
		String[] tempItemSeqs = itemSeqs.split(",");
		if(tempItemSeqs!=null){
			for(int i=0;i<tempItemSeqs.length;i++){
				AdminWinner tempAdminWinner = adminWinnerManageDao.getWinnerAward(tempItemSeqs[i]);
				
				if(tempAdminWinner != null && !StringUtil.isEmpty(tempAdminWinner.getImageFileId())) {
					fileService.removeFile(tempAdminWinner.getImageFileId());
				}
				AdminWinner adminWinner = new AdminWinner();
				adminWinner.setItemSeqs(itemSeqs);
				adminWinnerManageDao.deleteWinnerAward(adminWinner);
			}
		}
		
	}

	public void displayWinner(String isMonth) {
		
		adminWinnerManageDao.displayWinner(isMonth);
		
	}

	public String getDisplay() {
		return adminWinnerManageDao.getDisplay();
	}

	public List<AdminWinner> getAllWinnerList(KmsAdminSearchCondition searchCondition) {
		
		Integer count = adminWinnerManageDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerList(searchCondition);
		return winnerList;
	}
	
	public List<AdminWinner> getAllWinnerPeopleList(KmsAdminSearchCondition searchCondition) {
		
		Integer count = adminWinnerManageDao.countPeopleBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerPeopleList(searchCondition);
		return winnerList;
	}
	
	public List<AdminWinner> getAllWinnerTeamList(KmsAdminSearchCondition searchCondition) {
		
		Integer count = adminWinnerManageDao.countTeamBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerTeamList(searchCondition);
		return winnerList;
	}
	
	public List<AdminWinner> getAllWinnerAwardList(KmsAdminSearchCondition searchCondition) {
		
		Integer count = adminWinnerManageDao.countAwardBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerAwardList(searchCondition);
		return winnerList;
	}

	public void saveWinners(AdminWinner adminWinner) {
		
		String[] winnerIds = adminWinner.getWinnerIds();
		
		if(winnerIds != null){
			
			AdminWinner setAdminWinner = new AdminWinner();
			int cnt = winnerIds.length;
			
			
			for(int i=0; i<cnt ; i++){
				setAdminWinner.setConversionMark(adminWinner.getConversionMarks()[i]);
				setAdminWinner.setIsMonth(adminWinner.getIsMonths()[i]);
				setAdminWinner.setMark(adminWinner.getMarks()[i]);
				setAdminWinner.setSort(adminWinner.getSorts()[i]);
				setAdminWinner.setWinGb(adminWinner.getWinGbs()[i]);
				setAdminWinner.setWinnerId(adminWinner.getWinnerIds()[i]);
				setAdminWinner.setWinYear(adminWinner.getWinYears()[i]);
				setAdminWinner.setRegCnt(adminWinner.getRegCnts()[i]);
				adminWinnerManageDao.saveWinner(setAdminWinner);
			}			
			
		}else if(adminWinner.getWinnerId() != null){
			adminWinnerManageDao.saveWinner(adminWinner);
		}
		
	}
	
	public void saveWinnerPeople(AdminWinner adminWinner) {
		
		String[] winnerIds = adminWinner.getWinnerIds();
		
		if(winnerIds != null){
			
			AdminWinner setAdminWinner = new AdminWinner();
			int cnt = winnerIds.length;
			
			
			for(int i=0; i<cnt ; i++){
				setAdminWinner.setIsMonth(adminWinner.getIsMonths()[i]);
				setAdminWinner.setSort(adminWinner.getSorts()[i]);
				setAdminWinner.setWinGb(adminWinner.getWinGbs()[i]);
				setAdminWinner.setWinnerId(adminWinner.getWinnerIds()[i]);
				setAdminWinner.setWinYear(adminWinner.getWinYears()[i]);
				setAdminWinner.setReason(adminWinner.getReasons()[i]);
				adminWinnerManageDao.saveWinnerPeople(setAdminWinner);
			}			
			
		}else if(adminWinner.getWinnerId() != null){
			adminWinnerManageDao.saveWinnerPeople(adminWinner);
		}
		
	}
	
	public void saveWinnerTeam(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user) {
		
		String[] winnerIds = adminWinner.getWinnerIds();
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "KMS_TM", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			if(winnerIds != null){
				fileService.createFileLink(fileId, winnerIds[0], "KMS_TM", user);
			}else if(adminWinner.getWinnerId() != null){
				fileService.createFileLink(fileId, adminWinner.getWinnerId(), "KMS_TM", user);
			}
			
		}
		
		if(winnerIds != null){
			
			AdminWinner setAdminWinner = new AdminWinner();
			int cnt = winnerIds.length;
			
			
			for(int i=0; i<cnt ; i++){
				setAdminWinner.setIsMonth(adminWinner.getIsMonths()[i]);
				setAdminWinner.setSort(adminWinner.getSorts()[i]);
				setAdminWinner.setWinGb(adminWinner.getWinGbs()[i]);
				setAdminWinner.setWinnerId(adminWinner.getWinnerIds()[i]);
				setAdminWinner.setWinYear(adminWinner.getWinYears()[i]);
				setAdminWinner.setImageFileId(fileId);
				adminWinnerManageDao.saveWinnerTeam(setAdminWinner);
			}			
			
		}else if(adminWinner.getWinnerId() != null){
			adminWinner.setImageFileId(fileId);
			adminWinnerManageDao.saveWinnerTeam(adminWinner);
		}
		
	}
	
	public void saveWinnerAward(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user) {
		
		
		String winnerId = this.idgenService.getNextId();
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "KMS_AW", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
				fileService.createFileLink(fileId,  winnerId, "KMS_AW", user);
			
		}
			adminWinner.setWinnerId(winnerId);
			adminWinner.setImageFileId(fileId);
			adminWinnerManageDao.saveWinnerAward(adminWinner);
		
	}

	public Object getAssessStandard() {
		return adminWinnerManageDao.getAssessStandard();
	}

	public void saveAssessStandard(Map<Integer, Integer> paramMap) {
		adminWinnerManageDao.saveAssessStandard(paramMap);
		
	}

	public SearchResult<AdminWinner> getWinnerListByPortlet(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countByPortletSearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerListByPortlet(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerPeopleListByPortlet(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countPeopleByPortletSearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerPeopleListByPortlet(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerTeamListByPortlet(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countTeamByPortletSearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerTeamListByPortlet(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<AdminWinner> getWinnerAwardListByPortlet(KmsAdminSearchCondition searchCondition) {
		Integer count = adminWinnerManageDao.countAwardByPortletSearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<AdminWinner> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<AdminWinner>(searchCondition);
		}else{
			List<AdminWinner> winnerList = adminWinnerManageDao.getWinnerAwardListByPortlet(searchCondition);
			searchResult = new SearchResult<AdminWinner>(winnerList, searchCondition);
		}
		
		return searchResult;
	}

}
