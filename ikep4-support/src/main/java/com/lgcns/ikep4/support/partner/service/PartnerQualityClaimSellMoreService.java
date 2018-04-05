package com.lgcns.ikep4.support.partner.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface PartnerQualityClaimSellMoreService extends GenericService<PartnerQualitySub, String>{

   
    /**
     * 댓글 등록
     * @param partnerQualityClaimSellMore
     */
    String createQualityClaimSellMore(PartnerQualitySub partnerQualitySub);

    /**
     * 댓글 상세조회
     * @param linereplyParentId
     * @return
     */
    public SearchResult<PartnerQualitySub> getPartnerQualityClaimSellMore( PartnerQualityClaimSellMoreSearchCondition searchCondtion );

    public SearchResult<PartnerQualitySub> getPartnerQualityClaimSellMorePrint( int itemId );
    /**
     * 댓글의 댓글 등록
     * @param partnerQualityClaimSellMore
     */
    String createReplyQualityClaimSellMore( PartnerQualitySub partnerQualitySub );

    /**
     * 댓글을 조회한다.
     * @param linereplyId 댓글 ID
     * @return 조회된 댓글 모델 객체
     */
    PartnerQualitySub readPartnerQualityClaimSellMore( String linereplyId );

    /**
     * 댓글의 답변을 수정한다.
     * @param readPartnerQualityClaimSellMore 수정해야 되는 댓글 모델 객체 
     */
    void updatePartnerQualityClaimSellMore( PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void sendMail(User user, PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void sendCommentMail(User user, PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void sendCommentMailSub(User user, PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void updatePartnerTlComment( PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void updatePartnerOfComment( PartnerQualitySub readPartnerQualityClaimSellMore );
    
    void updatePartnerQualityClaimSellMaster( PartnerQualitySub readPartnerQualityClaimSellMore );

    /**
     * 사용자모드로 댓글을 삭제한다.
     * 삭제대상의 하위가 존재하는 경우 삭제플러그를 "1"로 바꾸고
     * 하위가 없으면 물리적으로 DELETE 한다.
     * @param partnerQualityClaimSellMore  삭제 대상 댓글 
     */
    void userDeleteQualityClaimSellMore( PartnerQualitySub partnerQualitySub );
    
    /**
     * 관리자모드로 댓글을 삭제한다.
     * 댓글의 하위 쓰레드를 모두 삭제한다.
     *
     * @param id  댓글 ID
     */
    void adminDeleteQualityClaimSellMore(String itemId, String linereplyId, String itemType);
    
    

}
