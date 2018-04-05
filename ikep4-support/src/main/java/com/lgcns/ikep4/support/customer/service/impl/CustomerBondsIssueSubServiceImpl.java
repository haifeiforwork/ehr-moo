package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueDao;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueSubDao;
import com.lgcns.ikep4.support.customer.dao.CustomerCounselHistoryDao;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.model.CustomerBondsIssueSub;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSubSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueSubService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service
@Transactional
public class CustomerBondsIssueSubServiceImpl extends GenericServiceImpl<CustomerBondsIssueSub, String>implements CustomerBondsIssueSubService {

    @Autowired
    private  IdgenService idgenService;
    
    @Autowired
    private CustomerBondsIssueSubDao customerBondsIssueSubDao;
    
    @Autowired
    private CustomerBondsIssueDao customerBondsIssueDao;
    
    @Autowired
    private CustomerCounselHistoryDao customerCounselHistoryDao;
    

    public String createBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub ) {
        
        final String generatedId = this.idgenService.getNextId();
        customerBondsIssueSub.setBondsIssueSubId( generatedId );
        customerBondsIssueSub.setBondsIssueSubGroupId( generatedId );
        
        final String insertedId = this.customerBondsIssueSubDao.create( customerBondsIssueSub );
        
        if(customerBondsIssueSub.getItemType().equals( "BI" )){
            //this.customerBondsIssueDao.updateBondsIssueSubCount(customerBondsIssueSub.getItemId());
        	BondsIssueHistory bondsIssue = new BondsIssueHistory();
        	bondsIssue.setSeqNum(Integer.parseInt(customerBondsIssueSub.getItemId()));
        	bondsIssue.setItemId(customerBondsIssueSub.getItemId());
        	bondsIssue.setSubId(customerBondsIssueSub.getLinereplyId());
        	this.customerBondsIssueDao.updateBondsIssueSubInfo(bondsIssue);
        }
        
        return insertedId;
      
    }

    public SearchResult<CustomerBondsIssueSub> getCustomerBondsIssueSub(CustomerBondsIssueSubSearchCondition searchCondition ){
        Integer count = this.customerBondsIssueSubDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CustomerBondsIssueSub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<CustomerBondsIssueSub>( searchCondition );
        }else{
            List<CustomerBondsIssueSub> customerBondsIssueSubList = this.customerBondsIssueSubDao.listBySearchCondition(searchCondition);
            result = new SearchResult<CustomerBondsIssueSub>( customerBondsIssueSubList , searchCondition );
        }
        
        return result;
        
    }
    
    public SearchResult<CustomerBondsIssueSub> getCustomerBondsIssueSubPrint(int itemId){
    	
    	CustomerBondsIssueSubSearchCondition searchCondition = new CustomerBondsIssueSubSearchCondition();
    	searchCondition.setItemId(Integer.toString(itemId));
    	searchCondition.setItemType("BI");
        Integer count = this.customerBondsIssueSubDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CustomerBondsIssueSub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<CustomerBondsIssueSub>( searchCondition );
        }else{
            List<CustomerBondsIssueSub> customerBondsIssueSubList = this.customerBondsIssueSubDao.listBySearchCondition(searchCondition);
            result = new SearchResult<CustomerBondsIssueSub>( customerBondsIssueSubList , searchCondition );
        }
        
        return result;
        
    }
    
    
    public String createReplyBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub ){
        
    	CustomerBondsIssueSub parent = this.customerBondsIssueSubDao.get( customerBondsIssueSub.getBondsIssueSubParentId() );
        
    	customerBondsIssueSub.setItemId( parent.getItemId() );
    	customerBondsIssueSub.setBondsIssueSubGroupId( parent.getBondsIssueSubGroupId() );
    	customerBondsIssueSub.setIndentation( parent.getIndentation() + 1 );
    	customerBondsIssueSub.setBondsIssueSubDelete( 0 );
    	customerBondsIssueSub.setStep( parent.getStep()+1 );

        final String generatedId = this.idgenService.getNextId();
        
        customerBondsIssueSub.setBondsIssueSubId( generatedId );
        
        //스텝값을 업데이트한다.(기존에 있던 데이터)
        this.customerBondsIssueSubDao.updateStep(customerBondsIssueSub);
        
        final String insertedId = this.customerBondsIssueSubDao.create( customerBondsIssueSub );
        
        if(customerBondsIssueSub.getItemType().equals( "BI" )){
            this.customerBondsIssueDao.updateBondsIssueSubCount(customerBondsIssueSub.getItemId());
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

    public CustomerBondsIssueSub read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( CustomerBondsIssueSub arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public CustomerBondsIssueSub readCustomerBondsIssueSub( String linereplyId ) {
    	CustomerBondsIssueSub customerBondsIssueSub = this.customerBondsIssueSubDao.get( linereplyId );
        return customerBondsIssueSub;
    }

    public void updateCustomerBondsIssueSub( CustomerBondsIssueSub readCustomerBondsIssueSub ) {
        this.customerBondsIssueSubDao.update( readCustomerBondsIssueSub );
    }
    
    public void updateCustomerBondsIssueSubMaster( CustomerBondsIssueSub readCustomerBondsIssueSub ) {
        this.customerBondsIssueSubDao.updateSubMaster( readCustomerBondsIssueSub );
    }

    public void userDeleteBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub ){
        //하위 댓글 개수를 구함
        Integer count = this.customerBondsIssueSubDao.countChildren(customerBondsIssueSub.getBondsIssueSubId());
        
        //하위 댓글이 존재하지 않으면
        if(count == 0 ){
            this.adminDeleteBondsIssueSub(customerBondsIssueSub.getItemId(), customerBondsIssueSub.getBondsIssueSubId(),customerBondsIssueSub.getItemType() );
        }else{ //하위 댓글이 존재하면
            //삭제 여부 필드를 "1"로 업데이트 한다.
            this.customerBondsIssueSubDao.logicalDelete(customerBondsIssueSub);
        }  
    }
    
    /*
     * (non-Javadoc)
     * @see com.lgcns.ikep4.lightpack.board.service.BoardBondsIssueSubService#
     * adminDeleteBoardBondsIssueSub(java.lang.String, java.lang.String)
     */
    public void adminDeleteBondsIssueSub(String itemId, String linereplyId, String itemType) {
        

        Integer childrenCount = this.customerBondsIssueSubDao.countChildren(linereplyId);

        if (childrenCount == 0) {
            this.customerBondsIssueSubDao.physicalDelete(linereplyId);

        } else {
            this.customerBondsIssueSubDao.physicalDeleteThreadByBondsIssueSubId(linereplyId);
        }

        if(itemType.equals( "BI" )){
        // 게시글의 댓글 갯수를 업데이트한다.
            this.customerBondsIssueDao.updateBondsIssueSubCount(itemId);
        }
        

    }

    
}
