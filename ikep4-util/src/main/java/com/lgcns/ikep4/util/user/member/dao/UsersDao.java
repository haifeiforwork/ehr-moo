/* 
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.user.member.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.util.user.member.model.User;


/**
 * 사용자 관리 DAO 정의
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserDao.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
public interface UsersDao extends GenericDao<User, String> {

	public User get(String id);
}
