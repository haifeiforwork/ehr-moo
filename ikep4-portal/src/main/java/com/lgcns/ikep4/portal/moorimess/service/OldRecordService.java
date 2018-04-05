package com.lgcns.ikep4.portal.moorimess.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.moorimess.model.OldRecordHistory;
import com.lgcns.ikep4.portal.moorimess.search.OldRecordSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 채권 관련 이슈 상담이력 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Transactional
public interface OldRecordService {

    /**
     * 채권 관련 이슈 상담이력리스트를 조회
     * @param searchCondition
     * @return
     */
    SearchResult<OldRecordHistory> listOldRecordBySearchCondition(OldRecordSearchCondition searchCondition);

    /**
     * 채권 관련 이슈 상담이력 상세조회
     * @param itemId
     * @return
     */
    OldRecordHistory getOldRecord ( int itemId);

    /**
     * 채권 관련 이슈 등록 
     * @param oldRecord
     * @param user
     * @param fileList
     * @return
     */
    int createOldRecord( OldRecordHistory oldRecord, User user, List<MultipartFile> fileList );

    /**
     * 채권 관련 이슈 수정
     * @param oldRecord
     * @param fileList
     * @param user
     */
    void updateOldRecord( OldRecordHistory oldRecord, List<MultipartFile> fileList, User user, int seqNum );
    
    void updateOldRecordMaster( OldRecordHistory oldRecord, User user, int seqNum );

    /**
     * 채권 관련 이슈 작성자/관리자 모드 삭제
     * 삭제여부만 업데이트 함 (게시물, 첨부파일, 댓글 모두 남아있음 )
     * @param seqNum
     */
    void deleteOldRecord( OldRecordHistory oldRecord  );
    
    
    
}
