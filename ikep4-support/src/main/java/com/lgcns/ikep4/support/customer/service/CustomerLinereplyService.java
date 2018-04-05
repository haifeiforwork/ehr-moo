package com.lgcns.ikep4.support.customer.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.CustomerLinereply;
import com.lgcns.ikep4.support.customer.search.CustomerLinereplySearchCondition;


@Transactional
public interface CustomerLinereplyService extends GenericService<CustomerLinereply, String>{

   
    /**
     * 댓글 등록
     * @param customerLinereply
     */
    String createLinereply(CustomerLinereply customerLinereply);

    /**
     * 댓글 상세조회
     * @param linereplyParentId
     * @return
     */
    public SearchResult<CustomerLinereply> getCustomerLinereply( CustomerLinereplySearchCondition searchCondtion );

    
    /**
     * 댓글의 댓글 등록
     * @param customerLinereply
     */
    String createReplyLinereply( CustomerLinereply customerLinereply );

    /**
     * 댓글을 조회한다.
     * @param linereplyId 댓글 ID
     * @return 조회된 댓글 모델 객체
     */
    CustomerLinereply readCustomerLinereply( String linereplyId );

    /**
     * 댓글의 답변을 수정한다.
     * @param readCustomerLinereply 수정해야 되는 댓글 모델 객체 
     */
    void updateCustomerLinereply( CustomerLinereply readCustomerLinereply );

    /**
     * 사용자모드로 댓글을 삭제한다.
     * 삭제대상의 하위가 존재하는 경우 삭제플러그를 "1"로 바꾸고
     * 하위가 없으면 물리적으로 DELETE 한다.
     * @param customerLinereply  삭제 대상 댓글 
     */
    void userDeleteLinereply( CustomerLinereply customerLinereply );
    
    /**
     * 관리자모드로 댓글을 삭제한다.
     * 댓글의 하위 쓰레드를 모두 삭제한다.
     *
     * @param id  댓글 ID
     */
    void adminDeleteLinereply(String itemId, String linereplyId, String itemType);
    
    

}
