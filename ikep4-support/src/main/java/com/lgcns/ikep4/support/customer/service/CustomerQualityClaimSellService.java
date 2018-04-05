package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 품질/클레임 상담이력 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Transactional
public interface CustomerQualityClaimSellService {

    /**
     * 품질/클레임 상담이력리스트를 조회
     * @param searchCondition
     * @return
     */
    SearchResult<QualityClaimSellHistory> listQualityClaimSellBySearchCondition(CustomerQualityClaimSellSearchCondition searchCondition);

    /**
     * 품질/클레임 상담이력 상세조회
     * @param itemId
     * @return
     */
    QualityClaimSellHistory getQualityClaimSell ( int itemId);

    /**
     * 품질/클레임 등록 
     * @param qualityClaimSell
     * @param user
     * @param fileList
     * @return
     */
    int createQualityClaimSell( QualityClaimSellHistory qualityClaimSell, User user, List<MultipartFile> fileList );

    /**
     * 품질/클레임 수정
     * @param qualityClaimSell
     * @param fileList
     * @param user
     */
    void updateQualityClaimSell( QualityClaimSellHistory qualityClaimSell, List<MultipartFile> fileList, User user, int seqNum );
    
    void updateQualityClaimSellMaster( QualityClaimSellHistory qualityClaimSell, User user, int seqNum );

    /**
     * 품질/클레임 작성자/관리자 모드 삭제
     * 삭제여부만 업데이트 함 (게시물, 첨부파일, 댓글 모두 남아있음 )
     * @param seqNum
     */
    void deleteQualityClaimSell( QualityClaimSellHistory qualityClaimSell  );
    
    
    
}
