/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtMenuDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 메뉴 daoimpl
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuDaoImpl.java 17353 2012-03-05 01:17:40Z unshj $
 */
@Repository
public class UtMenuDaoImpl extends GenericDaoSqlmap<UtMenu,String>  implements UtMenuDao {

private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utMenu.";
	
	/**
	 * 읽기
	 */
	public UtMenu get(String menuId) {
		return (UtMenu) sqlSelectForObject(NAMESPACE+"get", menuId);
	}
	
	/**
	 * 읽기
	 */
	public List<UtMenu> getMenuWithLocaleList(String menuId) {
		return sqlSelectForList(NAMESPACE+"getMenuWithLocaleList", menuId);
	}
	
	/**
	 * 메뉴 intercepter
	 */
	public UtMenu getMenu(UtMenu utMenu) {
		return (UtMenu) sqlSelectForObject(NAMESPACE+"getMenu", utMenu);
	}
	
	
	/**
	 * 메뉴 intercepter
	 */
	public List<UtMenu> getMenuList(UtMenu utMenu) {
		return sqlSelectForList(NAMESPACE+"getMenuList", utMenu);
		
	}
	
	

	/**
	 * 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 생성
	 */
	public String create(UtMenu utMenu) {
		sqlInsert(NAMESPACE+"create", utMenu);
		
		return utMenu.getMenuId();
	}

	/**
	 * 수정
	 */
	public void update(UtMenu UtMenu) {
		sqlUpdate(NAMESPACE+"update", UtMenu);
	}

	/**
	 * 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 통계 메뉴리스트
	 */
	public List<UtMenu> listBySearchCondition(UtSearchCondition searchCondition) { 
		return (List<UtMenu>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/**
	 * 통계 메뉴카운트
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	} 
	
	/**
	 * 메뉴콤보
	 */
	public List<UtMenu> comboList(UtMenu utMenu){
		return (List<UtMenu>)this.sqlSelectForList(NAMESPACE + "comboList", utMenu);
	}
	
	public List<UtMenu> mobileComboList(UtMenu utMenu){
		return (List<UtMenu>)this.sqlSelectForList(NAMESPACE + "mobileComboList", utMenu);
	}
	
	public List<UtMenu> mobileSubComboList(UtMenu utMenu){
		return (List<UtMenu>)this.sqlSelectForList(NAMESPACE + "mobileSubComboList", utMenu);
	}
	
	/**
	 * URL존재여부
	 */
	public boolean existsURL(String menuUrl) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"existsURL", menuUrl);
		return count > 0;
	}
	
	
}