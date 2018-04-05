package com.lgcns.ikep4.support.partner.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * 인물정보 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */


@Transactional
public interface PartnerService {
    
    /**
     * 고객정보의 관리자인지 아닌지 확인한다.
     * @param userId
     * @return
     */
    boolean checkAdmin(String userId);

    List<PartnerManInfoItem> getPartnerId( String partner );
    
    public Partner readPortalDefault();
	
    
    /**
     * 고객정보 조회 권한 있는지 체크 
     * @param userId
     * @return
     */
    boolean checkAccess( String userId );
    
    boolean checkRegInfo( PartnerClaimSellHistory qualityClaimSell );
    
    /**
	 * 검색조건을 이용해서 인물정보 목록을 조회한다.
	 * @param manInfoItemSearchCondition 인물정보 검색조건
	 * @return 인물정보 목록
	 */
	SearchResult<PartnerManInfoItem> listManInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition);
	
	SearchResult<PartnerManInfoItem> listRegInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition);
	
	/**
	 * 인물 상세정보를 조회한다.
	 * @param itemId 인물id
	 * @return 조회된 인물정보
	 */
	PartnerManInfoItem readManInfoItem(int itemId);
	
	/**
	 * 인물 상세정보(가족정보)를 조회한다.
	 * @param itemId
	 * @return 조회된 가족정보
	 */
	List<PartnerManInfoItem> readManFamily(String itemId);
	
	/**
	 * 인물 상세정보(경력정보)를 조회한다.
	 * @param itemId
	 * @return 조회된 경력정보
	 */
	List<PartnerManInfoItem> readManCareer(String itemId);
	
	
	/**
	 * 인물 상세정보를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfo(PartnerManInfoItem manInfoItem, int familyCnt, int careerCnt, List<MultipartFile> fileList, User user);
	
	void updateManInfoMaster(PartnerManInfoItem manInfoItem,User user);
	
	/**
	 * 인물 상세정보를 등록한다.
	 * @param manInfoItem
	 */
	void createManInfoItem(PartnerManInfoItem manInfoItem, int familyCnt, int careerCnt,  List<MultipartFile> fileList, User user);
	
	int createManInfoMaster(PartnerManInfoItem manInfoItem, User user);
	
	List<PartnerManInfoItem> listItemBySearchCondition(PartnerManInfoItemSearchCondition searchCondition);
	/**
	 * 인물 정보를 삭제한다. 
	 * @param seqNum
	 */
    void deleteManInfo( PartnerManInfoItem manInfoItem );
	

	
}
