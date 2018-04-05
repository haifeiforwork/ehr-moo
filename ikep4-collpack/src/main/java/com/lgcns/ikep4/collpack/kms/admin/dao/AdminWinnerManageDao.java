package com.lgcns.ikep4.collpack.kms.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface AdminWinnerManageDao extends GenericDao<AdminWinner, String> {

	Integer countBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countPeopleBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countTeamBySearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countAwardBySearchCondition(KmsAdminSearchCondition searchCondition);

	List<AdminWinner> getWinnerList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerPeopleList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerTeamList(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerAwardList(KmsAdminSearchCondition searchCondition);

	void deleteWinner(AdminWinner adminWinner);
	
	void deleteWinnerPeople(AdminWinner adminWinner);
	
	void deleteWinnerTeam(AdminWinner adminWinner);
	
	void deleteWinnerAward(AdminWinner adminWinner);

	void displayWinner(String isMonth);

	String getDisplay();

	void saveWinner(AdminWinner adminWinner);
	
	void saveWinnerPeople(AdminWinner adminWinner);
	
	void saveWinnerTeam(AdminWinner adminWinner);
	
	void saveWinnerAward(AdminWinner adminWinner);

	Object getAssessStandard();

	void saveAssessStandard(Map<Integer, Integer> paramMap);

	Integer countByPortletSearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countPeopleByPortletSearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countTeamByPortletSearchCondition(KmsAdminSearchCondition searchCondition);
	
	Integer countAwardByPortletSearchCondition(KmsAdminSearchCondition searchCondition);

	List<AdminWinner> getWinnerListByPortlet(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerPeopleListByPortlet(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerTeamListByPortlet(KmsAdminSearchCondition searchCondition);
	
	List<AdminWinner> getWinnerAwardListByPortlet(KmsAdminSearchCondition searchCondition);
	
	void updateWinnerPeople(AdminWinner adminWinner);
	
	void updateWinnerTeam(AdminWinner adminWinner);
	
	void updateWinnerAward(AdminWinner adminWinner);
	
	AdminWinner getWinnerPeople(String itemSeq);

	AdminWinner getWinnerTeam(String itemSeq);
	
	AdminWinner getWinnerAward(String itemSeq);
	

}
