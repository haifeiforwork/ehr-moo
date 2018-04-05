package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 채권 관련 이슈 상담이력 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Transactional
public interface CustomerBondsIssueService {

    /**
     * 채권 관련 이슈 상담이력리스트를 조회
     * @param searchCondition
     * @return
     */
    SearchResult<BondsIssueHistory> listBondsIssueBySearchCondition(CustomerBondsIssueSearchCondition searchCondition);

    /**
     * 채권 관련 이슈 상담이력 상세조회
     * @param itemId
     * @return
     */
    BondsIssueHistory getBondsIssue ( int itemId);

    /**
     * 채권 관련 이슈 등록 
     * @param bondsIssue
     * @param user
     * @param fileList
     * @return
     */
    int createBondsIssue( BondsIssueHistory bondsIssue, User user, List<MultipartFile> fileList );

    /**
     * 채권 관련 이슈 수정
     * @param bondsIssue
     * @param fileList
     * @param user
     */
    void updateBondsIssue( BondsIssueHistory bondsIssue, List<MultipartFile> fileList, User user, int seqNum );
    
    void updateBondsIssueMaster( BondsIssueHistory bondsIssue, User user, int seqNum );

    /**
     * 채권 관련 이슈 작성자/관리자 모드 삭제
     * 삭제여부만 업데이트 함 (게시물, 첨부파일, 댓글 모두 남아있음 )
     * @param seqNum
     */
    void deleteBondsIssue( BondsIssueHistory bondsIssue  );
    
    
    
}
