package com.lgcns.ikep4.support.addressbook.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 주소록 그룹 사용자 Service Interface
 * 
 * @author 이형운
 * @version $Id: AddrgroupService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface AddrgroupService extends GenericService<Addrgroup, String> {

	/**
	 * 모든 주소록 그룹 사용자 조회
	 * 
	 * @param addrgroupSearch 주소록 그룹 조회용 객체
	 * @return 주소록 그룹 목록
	 */
	public SearchResult<Addrgroup> list(AddrgroupSearchCondition addrgroupSearch);
	
	public List<Person> personList(String[] personNames);
	
	/**
	 * 모든 주소록 사용자를 Total Count 를 가지고 오지 않고 조회
	 * @param addrgroupSearch 주소록 그룹 조회용 객체
	 * @return 주소록 그룹 목록
	 */
	public SearchResult<Addrgroup> listWithoutCount(AddrgroupSearchCondition addrgroupSearch);
	
	public SearchResult<Addrgroup> listPubCount(AddrgroupSearchCondition addrgroupSearch);
	
	/**
	 * 모든 주소록 그룹  조회 수
	 * 
	 * @param addrgroupSearch 주소록 그룹 조회용 객체
	 * @return 주소록 그룹 수
	 */
	public Integer listCount (AddrgroupSearchCondition addrgroupSearch);
	
	/**
	 * 해당 주소록 사용자 조회
	 * @param addrgroup 주소록 그룹  객체
	 * @return 주소록 그룹  객체
	 */
	public Addrgroup read(Addrgroup addrgroup);

	/**
	 * 해당 주소록 존재 유무 
	 * @param addrgroup 주소록 그룹  객체
	 * @return 주소록 그룹  객체 존재 유무
	 */
	public boolean exists(Addrgroup addrgroup);

	/**
	 * 해당 주소록 삭제
	 * @param addrgroup 주소록 그룹  객체
	 */
	public void delete(Addrgroup addrgroup);
	
	/**
	 * 주소록 그룹을 저장및 수정시 주소록 그룹에 포함된 Person 정보도 함께 저장하는 서비스
	 * @param addrgroup 주소록 그룹  객체
	 * @param user 저장하는 User 정보
	 */
	public void saveAddrgroupList(Addrgroup addrgroup, User user);
	
	
	/**
	 * 주소록 그룹 삭제시 주소록 그룹에 포함된 Person2Group 테이블 정보도 함께 삭제 하는 서비스
	 * @param addrgroupId 주소록 그룹 ID
	 */
	public void deleteAddrgroupList(String addrgroupId);
}
