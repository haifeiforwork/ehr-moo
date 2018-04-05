package com.lgcns.ikep4.collpack.kms.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

public interface AdminWinnerManageService extends GenericService<AdminWinner, String> {

	SearchResult<AdminWinner> getWinnerList(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerPeopleList(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerTeamList(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerAwardList(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerListByPortlet(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerPeopleListByPortlet(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerTeamListByPortlet(KmsAdminSearchCondition searchCondition);
	
	SearchResult<AdminWinner> getWinnerAwardListByPortlet(KmsAdminSearchCondition searchCondition);

	void deleteWinner(String itemSeqs);
	
	void deleteWinnerPeople(String itemSeqs);
	
	void deleteWinnerTeam(String itemSeqs);
	
	void deleteWinnerAward(String itemSeqs);

	void displayWinner(String winGb);

	String getDisplay();

	List<AdminWinner> getAllWinnerList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getAllWinnerPeopleList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getAllWinnerTeamList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getAllWinnerAwardList(KmsAdminSearchCondition searchCondition);

	void saveWinners(AdminWinner adminWinner);
	
	void saveWinnerPeople(AdminWinner adminWinner);
	
	void saveWinnerTeam(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user);
	
	void saveWinnerAward(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user);

	Object getAssessStandard();

	void saveAssessStandard(Map<Integer, Integer> paramMap);
	
	void updateWinnerPeople(AdminWinner adminWinner);
	
	void updateWinnerTeam(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user);
	
	void updateWinnerAward(AdminWinner adminWinner,List<MultipartFile> fileList, String editorAttach, User user);
	
	AdminWinner readWinnerPeople(String itemSeq);
	
	AdminWinner readWinnerTeam(String itemSeq);
	
	AdminWinner readWinnerAward(String itemSeq);

}
