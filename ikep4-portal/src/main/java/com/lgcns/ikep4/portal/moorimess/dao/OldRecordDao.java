package com.lgcns.ikep4.portal.moorimess.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.moorimess.model.OldRecordHistory;
import com.lgcns.ikep4.portal.moorimess.search.OldRecordSearchCondition;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface OldRecordDao extends GenericDao<OldRecordHistory, String>{

    
    /**
     * 품질/클레임 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(OldRecordSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 리스트 출력
     * @param customerQualityCliamSearchCondition
     * @return
     */
    List<OldRecordHistory> listOldRecordBySearchCondition(OldRecordSearchCondition oldRecordSearchCondition);
    
    /**
     * 품질/클레임 상담이력 상세조회 
     * @param itemId
     * @return
     */
    OldRecordHistory getOldRecord (int itemId);
    
    /**
     * 품질/클레임 상담이력 생성
     * @param oldRecordItem
     * @return
     */
    int createOldRecord( OldRecordHistory oldRecordItem ) ;

    /**
     * 품질/클레임 상담이력 삭제
     * @param seqNum
     */
    void deleteOldRecord( OldRecordHistory oldRecord );

    
    /**
     * 품질/클레임 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );

    void updateOldRecordSubCount( String itemId );
    
    void updateOldRecordSubInfo(OldRecordHistory oldRecord);
    
    void updateMaster( OldRecordHistory oldRecord );
 
}
