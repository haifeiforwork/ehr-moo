package com.lgcns.ikep4.support.customer.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellMoreSearchCondition;


@Transactional
public interface CustomerQualityClaimSellMoreService extends GenericService<CustomerQualitySub, String>{

   
    /**
     * 댓글 등록
     * @param customerQualityClaimSellMore
     */
    String createQualityClaimSellMore(CustomerQualitySub customerQualitySub);

    /**
     * 댓글 상세조회
     * @param linereplyParentId
     * @return
     */
    public SearchResult<CustomerQualitySub> getCustomerQualityClaimSellMore( CustomerQualityClaimSellMoreSearchCondition searchCondtion );

    public SearchResult<CustomerQualitySub> getCustomerQualityClaimSellMorePrint( int itemId );
    /**
     * 댓글의 댓글 등록
     * @param customerQualityClaimSellMore
     */
    String createReplyQualityClaimSellMore( CustomerQualitySub customerQualitySub );

    /**
     * 댓글을 조회한다.
     * @param linereplyId 댓글 ID
     * @return 조회된 댓글 모델 객체
     */
    CustomerQualitySub readCustomerQualityClaimSellMore( String linereplyId );

    /**
     * 댓글의 답변을 수정한다.
     * @param readCustomerQualityClaimSellMore 수정해야 되는 댓글 모델 객체 
     */
    void updateCustomerQualityClaimSellMore( CustomerQualitySub readCustomerQualityClaimSellMore );
    
    void updateCustomerQualityClaimSellMaster( CustomerQualitySub readCustomerQualityClaimSellMore );

    /**
     * 사용자모드로 댓글을 삭제한다.
     * 삭제대상의 하위가 존재하는 경우 삭제플러그를 "1"로 바꾸고
     * 하위가 없으면 물리적으로 DELETE 한다.
     * @param customerQualityClaimSellMore  삭제 대상 댓글 
     */
    void userDeleteQualityClaimSellMore( CustomerQualitySub customerQualitySub );
    
    /**
     * 관리자모드로 댓글을 삭제한다.
     * 댓글의 하위 쓰레드를 모두 삭제한다.
     *
     * @param id  댓글 ID
     */
    void adminDeleteQualityClaimSellMore(String itemId, String linereplyId, String itemType);
    
    

}
