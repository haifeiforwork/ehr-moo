package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.search.CounselHistorySearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 고객별 상담내역 서비스 클래스
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface CustomerCounselHistoryService {
    /**
     * 상담이력 리스팅 
     * @param searchCondition
     * @return
     */
    SearchResult<CounselHistory> listCounselHistoryBySearchCondition(CounselHistorySearchCondition searchCondition);

   
    /**
     * 상담이력 상세정보 조회 
     * @param itemId
     * @return
     */
    CounselHistory getCounselHistory( int itemId );


    /**
     * 상담이력 등록 
     * @param counselHistory
     * @param user
     * @param fileList
     * @return
     */
    int createCounselHistory( CounselHistory counselHistory, User user, List<MultipartFile> fileList );


    /**
     * 상담이력 수정
     * @param counselHistory
     * @param fileList
     * @param user
     * @param seqNum
     */
    void updateCounselHistory( CounselHistory counselHistory, List<MultipartFile> fileList, User user, int seqNum );


    /**
     * 상담이력 삭제
     * @param counselHistory
     */
    void deleteCounselHistory( CounselHistory counselHistory );
    
    void syncCustomerName(CounselHistory counselHistory);

}
