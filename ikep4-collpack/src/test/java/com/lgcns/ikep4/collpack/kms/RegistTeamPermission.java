package com.lgcns.ikep4.collpack.kms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 *  IKEP4_KMS_DIVISION_PERMISSION 초기데이터를 입력을 위한 클래스
 *  */
public class RegistTeamPermission extends BaseKmsTestCase {
	
	@Autowired
	AdminPermissionDao adminPermissionDao;
	
	
	
	
	@Test
	@Rollback(false)
	public void registTeamPermission() {
		
		List<String> leaderList = adminPermissionDao.getLeaderIdList();
		
		for(String ids : leaderList ){
			
			List<String> temp = StringUtil.getTokens(ids, "|");
			String userId = temp.get(0);
			String groupId = temp.get(1);

			
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





}
