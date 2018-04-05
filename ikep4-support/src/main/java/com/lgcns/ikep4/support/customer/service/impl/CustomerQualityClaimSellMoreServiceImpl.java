package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueDao;
import com.lgcns.ikep4.support.customer.dao.CustomerCounselHistoryDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellMoreDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellDao;
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellMoreService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service
@Transactional
public class CustomerQualityClaimSellMoreServiceImpl extends GenericServiceImpl<CustomerQualitySub, String>implements CustomerQualityClaimSellMoreService {

    @Autowired
    private  IdgenService idgenService;
    
    @Autowired
    private CustomerQualityClaimSellMoreDao customerQualityClaimSellMoreDao;
    
    @Autowired
    private CustomerQualityClaimSellDao customerQualityClaimSellDao;
    
    @Autowired
    private CustomerCounselHistoryDao customerCounselHistoryDao;
    
    @Autowired
    private CustomerBondsIssueDao customerBondsIssueDao;

    public String createQualityClaimSellMore( CustomerQualitySub customerQualitySub ) {
        
        final String generatedId = this.idgenService.getNextId();
        customerQualitySub.setQualityClaimSellMoreId( generatedId );
        customerQualitySub.setQualityClaimSellMoreGroupId( generatedId );
        
        final String insertedId = this.customerQualityClaimSellMoreDao.create( customerQualitySub );
        
        if(customerQualitySub.getItemType().equals( "CL" )){
            //this.customerQualityClaimSellDao.updateQualityClaimSellMoreCount(customerQualitySub.getItemId());
        	QualityClaimSellHistory qualityClaimSell = new QualityClaimSellHistory();
        	qualityClaimSell.setSeqNum(Integer.parseInt(customerQualitySub.getItemId()));
        	qualityClaimSell.setItemId(customerQualitySub.getItemId());
        	qualityClaimSell.setSubId(customerQualitySub.getLinereplyId());
        	this.customerQualityClaimSellDao.updateQualityClaimSellMoreInfo(qualityClaimSell);
        }
        
        return insertedId;
      
    }

    public SearchResult<CustomerQualitySub> getCustomerQualityClaimSellMore(CustomerQualityClaimSellMoreSearchCondition searchCondition ){
        Integer count = this.customerQualityClaimSellMoreDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CustomerQualitySub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<CustomerQualitySub>( searchCondition );
        }else{
            List<CustomerQualitySub> customerQualityClaimSellMoreList = this.customerQualityClaimSellMoreDao.listBySearchCondition(searchCondition);
            result = new SearchResult<CustomerQualitySub>( customerQualityClaimSellMoreList , searchCondition );
        }
        
        return result;
        
    }
    
    public SearchResult<CustomerQualitySub> getCustomerQualityClaimSellMorePrint( int itemId ){
    	CustomerQualityClaimSellMoreSearchCondition searchCondition = new CustomerQualityClaimSellMoreSearchCondition();
    	searchCondition.setItemId(Integer.toString(itemId));
    	searchCondition.setItemType("CL");
        Integer count = this.customerQualityClaimSellMoreDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CustomerQualitySub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<CustomerQualitySub>( searchCondition );
        }else{
            List<CustomerQualitySub> customerQualityClaimSellMoreList = this.customerQualityClaimSellMoreDao.listBySearchCondition(searchCondition);
            result = new SearchResult<CustomerQualitySub>( customerQualityClaimSellMoreList , searchCondition );
        }
        
        return result;
        
    }
    
    
    public String createReplyQualityClaimSellMore( CustomerQualitySub customerQualitySub ){
        
    	CustomerQualitySub parent = this.customerQualityClaimSellMoreDao.get( customerQualitySub.getQualityClaimSellMoreParentId() );
        
    	customerQualitySub.setItemId( parent.getItemId() );
    	customerQualitySub.setQualityClaimSellMoreGroupId( parent.getQualityClaimSellMoreGroupId() );
    	customerQualitySub.setIndentation( parent.getIndentation() + 1 );
    	customerQualitySub.setQualityClaimSellMoreDelete( 0 );
    	customerQualitySub.setStep( parent.getStep()+1 );

        final String generatedId = this.idgenService.getNextId();
        
        customerQualitySub.setQualityClaimSellMoreId( generatedId );
        
        //스텝값을 업데이트한다.(기존에 있던 데이터)
        this.customerQualityClaimSellMoreDao.updateStep(customerQualitySub);
        
        final String insertedId = this.customerQualityClaimSellMoreDao.create( customerQualitySub );
        
        if(customerQualitySub.getItemType().equals( "CL" )){
            this.customerQualityClaimSellDao.updateQualityClaimSellMoreCount(customerQualitySub.getItemId());
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

    public CustomerQualitySub read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( CustomerQualitySub arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public CustomerQualitySub readCustomerQualityClaimSellMore( String linereplyId ) {
    	CustomerQualitySub customerQualitySub = this.customerQualityClaimSellMoreDao.get( linereplyId );
        return customerQualitySub;
    }

    public void updateCustomerQualityClaimSellMore( CustomerQualitySub readCustomerQualityClaimSellMore ) {
        this.customerQualityClaimSellMoreDao.update( readCustomerQualityClaimSellMore );
    }
    
    public void updateCustomerQualityClaimSellMaster( CustomerQualitySub readCustomerQualityClaimSellMore ) {
        this.customerQualityClaimSellMoreDao.updateSubMaster( readCustomerQualityClaimSellMore );
    }

    public void userDeleteQualityClaimSellMore( CustomerQualitySub customerQualitySub ){
        //하위 댓글 개수를 구함
        Integer count = this.customerQualityClaimSellMoreDao.countChildren(customerQualitySub.getQualityClaimSellMoreId());
        
        //하위 댓글이 존재하지 않으면
        if(count == 0 ){
            this.adminDeleteQualityClaimSellMore(customerQualitySub.getItemId(), customerQualitySub.getQualityClaimSellMoreId(),customerQualitySub.getItemType() );
        }else{ //하위 댓글이 존재하면
            //삭제 여부 필드를 "1"로 업데이트 한다.
            this.customerQualityClaimSellMoreDao.logicalDelete(customerQualitySub);
        }  
    }
    
    /*
     * (non-Javadoc)
     * @see com.lgcns.ikep4.lightpack.board.service.BoardQualityClaimSellMoreService#
     * adminDeleteBoardQualityClaimSellMore(java.lang.String, java.lang.String)
     */
    public void adminDeleteQualityClaimSellMore(String itemId, String linereplyId, String itemType) {
        

        Integer childrenCount = this.customerQualityClaimSellMoreDao.countChildren(linereplyId);

        if (childrenCount == 0) {
            this.customerQualityClaimSellMoreDao.physicalDelete(linereplyId);

        } else {
            this.customerQualityClaimSellMoreDao.physicalDeleteThreadByQualityClaimSellMoreId(linereplyId);
        }

        if(itemType.equals( "CL" )){
        // 게시글의 댓글 갯수를 업데이트한다.
            this.customerQualityClaimSellDao.updateQualityClaimSellMoreCount(itemId);
        }
        

    }

    
}
