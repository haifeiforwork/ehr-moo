package com.lgcns.ikep4.collpack.kms.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminWinnerManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminWinner;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


@Repository("AdminWinnerManageDao")
public class AdminWinnerManageDaoImpl extends GenericDaoSqlmap<AdminWinner, String> implements AdminWinnerManageDao {

	private static final String NAMESPACE = "collpack.kms.admin.dao.AdminWinnerManage.";
	
	public String create(AdminWinner arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public AdminWinner get(String itemSeq) {
		return (AdminWinner)this.sqlSelectForObject(NAMESPACE + "getWinner", itemSeq);
	}
	
	public AdminWinner getWinnerPeople(String itemSeq) {
		return (AdminWinner)this.sqlSelectForObject(NAMESPACE + "getWinnerPeople", itemSeq);
	}
	
	public AdminWinner getWinnerTeam(String itemSeq) {
		return (AdminWinner)this.sqlSelectForObject(NAMESPACE + "getWinnerTeam", itemSeq);
	}
	
	public AdminWinner getWinnerAward(String itemSeq) {
		return (AdminWinner)this.sqlSelectForObject(NAMESPACE + "getWinnerAward", itemSeq);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(AdminWinner adminWinner) {
		this.sqlUpdate(NAMESPACE  + "updateWinner" , adminWinner);
	}
	
	public void updateWinnerPeople(AdminWinner adminWinner) {
		this.sqlUpdate(NAMESPACE  + "updateWinnerPeople" , adminWinner);
	}
	
	public void updateWinnerTeam(AdminWinner adminWinner) {
		this.sqlUpdate(NAMESPACE  + "updateWinnerTeam" , adminWinner);
	}
	
	public void updateWinnerAward(AdminWinner adminWinner) {
		this.sqlUpdate(NAMESPACE  + "updateWinnerAward" , adminWinner);
	}

	public Integer countBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	public Integer countPeopleBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countPeopleBySearchCondition", searchCondition);
	}
	
	public Integer countTeamBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamBySearchCondition", searchCondition);
	}
	
	public Integer countAwardBySearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countAwardBySearchCondition", searchCondition);
	}

	public List<AdminWinner> getWinnerList(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerList", searchCondition);
	}
	
	public List<AdminWinner> getWinnerPeopleList(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerPeopleList", searchCondition);
	}
	
	public List<AdminWinner> getWinnerTeamList(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerTeamList", searchCondition);
	}
	
	public List<AdminWinner> getWinnerAwardList(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerAwardList", searchCondition);
	}

	public void deleteWinner(AdminWinner adminWinner) {
		this.sqlDelete(NAMESPACE +"deleteWinner", adminWinner);
		
	}
	
	public void deleteWinnerPeople(AdminWinner adminWinner) {
		this.sqlDelete(NAMESPACE +"deleteWinnerPeople", adminWinner);
		
	}
	
	public void deleteWinnerTeam(AdminWinner adminWinner) {
		this.sqlDelete(NAMESPACE +"deleteWinnerTeam", adminWinner);
		
	}
	
	public void deleteWinnerAward(AdminWinner adminWinner) {
		this.sqlDelete(NAMESPACE +"deleteWinnerAward", adminWinner);
		
	}

	public void displayWinner(String isMonth) {
		this.sqlUpdate(NAMESPACE +"displayWinner", isMonth);
		
	}

	public String getDisplay() {
		return (String)this.sqlSelectForObject(NAMESPACE+"getDisplay");
	}

	public void saveWinner(AdminWinner adminWinner) {
		this.sqlInsert(NAMESPACE + "saveWinner", adminWinner);
		
	}
	
	public void saveWinnerPeople(AdminWinner adminWinner) {
		this.sqlInsert(NAMESPACE + "saveWinnerPeople", adminWinner);
		
	}
	
	public void saveWinnerTeam(AdminWinner adminWinner) {
		this.sqlInsert(NAMESPACE + "saveWinnerTeam", adminWinner);
		
	}
	
	public void saveWinnerAward(AdminWinner adminWinner) {
		this.sqlInsert(NAMESPACE + "saveWinnerAward", adminWinner);
		
	}

	public Object getAssessStandard() {
		return this.sqlSelectForObject(NAMESPACE +"getAssessStandard");
	}

	public void saveAssessStandard(Map<Integer, Integer> paramMap) {
		this.sqlUpdate(NAMESPACE + "saveAssessStandard", paramMap);
		
	}

	public Integer countByPortletSearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByPortletSearchCondition", searchCondition);
	}
	
	public Integer countPeopleByPortletSearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countPeopleByPortletSearchCondition", searchCondition);
	}
	
	public Integer countTeamByPortletSearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamByPortletSearchCondition", searchCondition);
	}
	
	public Integer countAwardByPortletSearchCondition(KmsAdminSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countAwardByPortletSearchCondition", searchCondition);
	}

	public List<AdminWinner> getWinnerListByPortlet(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerListByPortlet", searchCondition);
	}
	
	public List<AdminWinner> getWinnerPeopleListByPortlet(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerPeopleListByPortlet", searchCondition);
	}
	
	public List<AdminWinner> getWinnerTeamListByPortlet(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerTeamListByPortlet", searchCondition);
	}
	
	public List<AdminWinner> getWinnerAwardListByPortlet(KmsAdminSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getWinnerAwardListByPortlet", searchCondition);
	}


}
