/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 로그인 히스토리 service
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuService.java 17353 2012-03-05 01:17:40Z unshj $
 */
@Transactional
public interface UtMenuService extends GenericService<UtMenu,String> {
	/**
	 * 모든 menu 저장및 수정
	 * @param utConfigList
	 */
	public void saveOrUpdate(List<UtMenu> utMenuList);
	

	
	/**
	 * 메뉴 수정 및 메시지 수정 처리 
	 */
	public void updateAndMessaging(UtMenu utMenu);
	
	/**
	 * 메뉴 추가 및 메시지 처리
	 */
	public void createAndMessaging(UtMenu utMenu);
	
	/**
	 * 선택된것 삭제
	 * @param menuIds
	 */
	public void removeCheckedAll(String[] menuIds);
	
	/**
	 * 메뉴리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtMenu> listBySearchCondition(UtSearchCondition searchCondition) ;
	
	/**
	 * 메뉴콤보
	 * @param utMenu
	 * @return
	 */
	List<UtMenu> comboList(UtMenu utMenu);
	
	List<UtMenu> mobileComboList(UtMenu utMenu);
	
	List<UtMenu> mobileSubComboList(UtMenu utMenu);
	
	/**
	 * 메뉴 intercepter
	 * @param utMenu
	 * @return
	 */
	public UtMenu getMenu(UtMenu utMenu); 
	
	/**
	 * 메뉴 intercepter
	 * @param utMenu
	 * @return
	 */
	public List<UtMenu> getMenuList(UtMenu utMenu); 
	
	
	
	/**
	 * URL존재여부
	 * @param menuURL
	 */
	public boolean existsURL(UtMenu utMenu);
	
	
}
