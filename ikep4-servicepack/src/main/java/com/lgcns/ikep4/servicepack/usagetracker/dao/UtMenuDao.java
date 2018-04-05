/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 메뉴 dao
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuDao.java 17353 2012-03-05 01:17:40Z unshj $
 */
public interface UtMenuDao extends GenericDao<UtMenu,String> {
	/**
	 * 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtMenu> listBySearchCondition(UtSearchCondition searchCondition);
	/**
	 * 통계 메뉴 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UtSearchCondition searchCondition); 

	/**
	 * 메뉴 콤보
	 * @param utMenu
	 * @return
	 */
	List<UtMenu> comboList(UtMenu utMenu);
	
	List<UtMenu> mobileComboList(UtMenu utMenu);
	
	List<UtMenu> mobileSubComboList(UtMenu utMenu);
	
	/**
	 * 메뉴
	 * @param menuId
	 * @return
	 */
	public List<UtMenu> getMenuWithLocaleList(String menuId);
	
	/**
	 * 메뉴 intercepter
	 * @param utMenu
	 * @return
	 */
	public UtMenu getMenu(UtMenu utMenu); 
	
	
	/**
	 * 메뉴 intercepter
	 */
	public List<UtMenu> getMenuList(UtMenu utMenu);
	
	/**
	 * URL존재여부
	 * @param menuURL
	 */
	public boolean existsURL(String menuUrl);
	
}