package com.lgcns.ikep4.support.customer.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.CustomerBondsIssueSub;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSubSearchCondition;


@Transactional
public interface CustomerBondsIssueSubService extends GenericService<CustomerBondsIssueSub, String>{

   
    /**
     * 댓글 등록
     * @param customerBondsIssueSub
     */
    String createBondsIssueSub(CustomerBondsIssueSub customerBondsIssueSub);

    /**
     * 댓글 상세조회
     * @param linereplyParentId
     * @return
     */
    public SearchResult<CustomerBondsIssueSub> getCustomerBondsIssueSub( CustomerBondsIssueSubSearchCondition searchCondtion );

    public SearchResult<CustomerBondsIssueSub> getCustomerBondsIssueSubPrint(int itemId);

    /**
     * 댓글의 댓글 등록
     * @param customerBondsIssueSub
     */
    String createReplyBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub );

    /**
     * 댓글을 조회한다.
     * @param linereplyId 댓글 ID
     * @return 조회된 댓글 모델 객체
     */
    CustomerBondsIssueSub readCustomerBondsIssueSub( String linereplyId );

    /**
     * 댓글의 답변을 수정한다.
     * @param readCustomerBondsIssueSub 수정해야 되는 댓글 모델 객체 
     */
    void updateCustomerBondsIssueSub( CustomerBondsIssueSub readCustomerBondsIssueSub );
    
    void updateCustomerBondsIssueSubMaster( CustomerBondsIssueSub readCustomerBondsIssueSub );

    /**
     * 사용자모드로 댓글을 삭제한다.
     * 삭제대상의 하위가 존재하는 경우 삭제플러그를 "1"로 바꾸고
     * 하위가 없으면 물리적으로 DELETE 한다.
     * @param customerBondsIssueSub  삭제 대상 댓글 
     */
    void userDeleteBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub );
    
    /**
     * 관리자모드로 댓글을 삭제한다.
     * 댓글의 하위 쓰레드를 모두 삭제한다.
     *
     * @param id  댓글 ID
     */
    void adminDeleteBondsIssueSub(String itemId, String linereplyId, String itemType);
    
    

}
