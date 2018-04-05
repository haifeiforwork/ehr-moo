package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.Customer;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 인물정보 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */


@Transactional
public interface CustomerService {
    
    /**
     * 고객정보의 관리자인지 아닌지 확인한다.
     * @param userId
     * @return
     */
    boolean checkAdmin(String userId);

	/**
	 * 검색조건을 이용해서 인물정보 목록을 조회한다.
	 * @param manInfoItemSearchCondition 인물정보 검색조건
	 * @return 인물정보 목록
	 */
	SearchResult<ManInfoItem> listManInfoItemBySearchCondition(ManInfoItemSearchCondition manInfoItemSearchCondition);
	
	/**
	 * 인물 상세정보를 조회한다.
	 * @param itemId 인물id
	 * @return 조회된 인물정보
	 */
	ManInfoItem readManInfoItem(int itemId);
	
	/**
	 * 인물 상세정보(가족정보)를 조회한다.
	 * @param itemId
	 * @return 조회된 가족정보
	 */
	List<ManInfoItem> readManFamily(String itemId);
	
	/**
	 * 인물 상세정보(경력정보)를 조회한다.
	 * @param itemId
	 * @return 조회된 경력정보
	 */
	List<ManInfoItem> readManCareer(String itemId);
	
	
	/**
	 * 인물 상세정보를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfo(ManInfoItem manInfoItem, int familyCnt, int careerCnt, List<MultipartFile> fileList, User user);
	
	/**
	 * 인물 상세정보를 등록한다.
	 * @param manInfoItem
	 */
	int createManInfoItem(ManInfoItem manInfoItem, int familyCnt, int careerCnt,  List<MultipartFile> fileList, User user);
	

	/**
	 * 인물 정보를 삭제한다. 
	 * @param seqNum
	 */
    void deleteManInfo( ManInfoItem manInfoItem );

    /**
     * 고객 ID를 검색한다.
     * @param customer
     * @return
     */
    List<Customer> getCustomerId( String customer );

    
    /**
     * sap의 NAMe으로 동기화
     * @param manInfoItem
     */
    void syncCustomerName( ManInfoItem manInfoItem );

    
    /**
     * 고객정보 조회 권한 있는지 체크 
     * @param userId
     * @return
     */
    boolean checkAccess( String userId );
	

	
}
