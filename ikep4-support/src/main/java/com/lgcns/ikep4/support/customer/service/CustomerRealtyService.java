package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.Realty;
import com.lgcns.ikep4.support.customer.search.RealtySearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 고객별 상담내역 서비스 클래스
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface CustomerRealtyService {
    /**
     * 상담이력 리스팅 
     * @param searchCondition
     * @return
     */
    SearchResult<Realty> listRealtyBySearchCondition(RealtySearchCondition searchCondition);
    
    public List<Realty> selectRealty();

   
    /**
     * 상담이력 상세정보 조회 
     * @param itemId
     * @return
     */
    Realty getRealty( int itemId );


    /**
     * 상담이력 등록 
     * @param realty
     * @param user
     * @param fileList
     * @return
     */
    int createRealty( Realty realty, User user, List<MultipartFile> fileList );


    /**
     * 상담이력 수정
     * @param realty
     * @param fileList
     * @param user
     * @param seqNum
     */
    void updateRealty( Realty realty, List<MultipartFile> fileList, User user, int seqNum );


    /**
     * 상담이력 삭제
     * @param realty
     */
    void deleteRealty( Realty realty );
    
    void syncCustomerName(Realty realty);

}
