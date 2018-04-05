package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueDao;
import com.lgcns.ikep4.support.customer.dao.CustomerCounselHistoryDao;
import com.lgcns.ikep4.support.customer.dao.CustomerLinereplyDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellDao;
import com.lgcns.ikep4.support.customer.model.CustomerLinereply;
import com.lgcns.ikep4.support.customer.search.CustomerLinereplySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerLinereplyService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service
@Transactional
public class CustomerLinereplyServiceImpl extends GenericServiceImpl<CustomerLinereply, String>implements CustomerLinereplyService {

    @Autowired
    private  IdgenService idgenService;
    
    @Autowired
    private CustomerLinereplyDao customerLinereplyDao;
    
    @Autowired
    private CustomerQualityClaimDao customerQualityClaimDao;
    
    @Autowired
    private CustomerQualityClaimSellDao customerQualityClaimSellDao;
    
    @Autowired
    private CustomerCounselHistoryDao customerCounselHistoryDao;
    
    @Autowired
    private CustomerBondsIssueDao customerBondsIssueDao;

    public String createLinereply( CustomerLinereply customerLinereply ) {
        
        final String generatedId = this.idgenService.getNextId();
        customerLinereply.setLinereplyId( generatedId );
        customerLinereply.setLinereplyGroupId( generatedId );
        
        final String insertedId = this.customerLinereplyDao.create( customerLinereply );
        
        if(customerLinereply.getItemType().equals( "QC" )){
            this.customerQualityClaimDao.updateLinereplyCount(customerLinereply.getItemId());
        }else if(customerLinereply.getItemType().equals("CH")){
            this.customerCounselHistoryDao.updateLinereplyCount( customerLinereply.getItemId() );
        }else if(customerLinereply.getItemType().equals("BI")){
            this.customerBondsIssueDao.updateLinereplyCount( customerLinereply.getItemId() );
        }else if(customerLinereply.getItemType().equals("CL")){
        	this.customerQualityClaimSellDao.updateLinereplyCount(customerLinereply.getItemId());
        }
        
        return insertedId;
      
    }

    public SearchResult<CustomerLinereply> getCustomerLinereply(CustomerLinereplySearchCondition searchCondition ){
        Integer count = this.customerLinereplyDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CustomerLinereply> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<CustomerLinereply>( searchCondition );
        }else{
            List<CustomerLinereply> customerLinereplyList = this.customerLinereplyDao.listBySearchCondition(searchCondition);
            result = new SearchResult<CustomerLinereply>( customerLinereplyList , searchCondition );
        }
        
        return result;
        
    }
    
    
    public String createReplyLinereply( CustomerLinereply customerLinereply ){
        
        CustomerLinereply parent = this.customerLinereplyDao.get( customerLinereply.getLinereplyParentId() );
        
        customerLinereply.setItemId( parent.getItemId() );
        customerLinereply.setLinereplyGroupId( parent.getLinereplyGroupId() );
        customerLinereply.setIndentation( parent.getIndentation() + 1 );
        customerLinereply.setLinereplyDelete( 0 );
        customerLinereply.setStep( parent.getStep()+1 );

        final String generatedId = this.idgenService.getNextId();
        
        customerLinereply.setLinereplyId( generatedId );
        
        //스텝값을 업데이트한다.(기존에 있던 데이터)
        this.customerLinereplyDao.updateStep(customerLinereply);
        
        final String insertedId = this.customerLinereplyDao.create( customerLinereply );
        
        if(customerLinereply.getItemType().equals( "QC" )){
            this.customerQualityClaimDao.updateLinereplyCount(customerLinereply.getItemId());
        }else if(customerLinereply.getItemType().equals( "BI" )){
            this.customerBondsIssueDao.updateLinereplyCount( customerLinereply.getItemId() );
        }else if(customerLinereply.getItemType().equals( "CH" )){
            this.customerCounselHistoryDao.updateLinereplyCount( customerLinereply.getItemId() );
        }else if(customerLinereply.getItemType().equals( "CL" )){
        	this.customerQualityClaimDao.updateLinereplyCount(customerLinereply.getItemId());
        }
        
        return insertedId;
    }
    
    public void delete( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public CustomerLinereply read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( CustomerLinereply arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public CustomerLinereply readCustomerLinereply( String linereplyId ) {
        CustomerLinereply customerLinereply = this.customerLinereplyDao.get( linereplyId );
        return customerLinereply;
    }

    public void updateCustomerLinereply( CustomerLinereply readCustomerLinereply ) {
        this.customerLinereplyDao.update( readCustomerLinereply );
        
        
    }

    public void userDeleteLinereply( CustomerLinereply customerLinereply ){
        //하위 댓글 개수를 구함
        Integer count = this.customerLinereplyDao.countChildren(customerLinereply.getLinereplyId());
        
        //하위 댓글이 존재하지 않으면
        if(count == 0 ){
            this.adminDeleteLinereply(customerLinereply.getItemId(), customerLinereply.getLinereplyId(),customerLinereply.getItemType() );
        }else{ //하위 댓글이 존재하면
            //삭제 여부 필드를 "1"로 업데이트 한다.
            this.customerLinereplyDao.logicalDelete(customerLinereply);
        }  
    }
    
    /*
     * (non-Javadoc)
     * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
     * adminDeleteBoardLinereply(java.lang.String, java.lang.String)
     */
    public void adminDeleteLinereply(String itemId, String linereplyId, String itemType) {
        

        Integer childrenCount = this.customerLinereplyDao.countChildren(linereplyId);

        if (childrenCount == 0) {
            this.customerLinereplyDao.physicalDelete(linereplyId);

        } else {
            this.customerLinereplyDao.physicalDeleteThreadByLinereplyId(linereplyId);
        }

        if(itemType.equals( "QC" )){
        // 게시글의 댓글 갯수를 업데이트한다.
            this.customerQualityClaimDao.updateLinereplyCount(itemId);
        }else if(itemType.equals( "CH" )){
            this.customerCounselHistoryDao.updateLinereplyCount( itemId );
        }else if(itemType.equals( "BI" )){
            this.customerBondsIssueDao.updateLinereplyCount( itemId );
        }else if(itemType.equals( "CL" )){
        	this.customerQualityClaimDao.updateLinereplyCount(itemId);
        }
        

    }

    
}
