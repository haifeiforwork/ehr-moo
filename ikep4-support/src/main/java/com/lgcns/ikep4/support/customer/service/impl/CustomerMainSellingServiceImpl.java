package com.lgcns.ikep4.support.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerMainSellingDao;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.search.MainSellingSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerMainSellingService;
import com.lgcns.ikep4.support.user.member.model.User;

@Service
@Transactional
public class CustomerMainSellingServiceImpl extends GenericServiceImpl<MainSelling, String> implements CustomerMainSellingService{

    @Autowired
    private CustomerMainSellingDao customerMainSellingDao;
    
    public SearchResult<MainSelling> listMainSellingBySearchCondition( MainSellingSearchCondition searchCondition ) {
        
        Integer count = this.customerMainSellingDao.countBySearchCondition(searchCondition);
        
       searchCondition.terminateSearchCondition( count );
       
       SearchResult<MainSelling> searchResult = null;
       if(searchCondition.isEmptyRecord()){
           searchResult = new SearchResult<MainSelling>( searchCondition );
       }else{
           List<MainSelling> mainSellingList = this.customerMainSellingDao.listMainSellingBySearchCondition(searchCondition);
           searchResult = new SearchResult<MainSelling>( mainSellingList,searchCondition );
       }
        
        return searchResult;
    }

    public List<MainSelling> listMainSellingDetail( String customerId ) {
       
        List<MainSelling> mainSellingDetailList = new ArrayList<MainSelling>();
        mainSellingDetailList = customerMainSellingDao.listMainSellingDetail( customerId );
        
        return mainSellingDetailList;
    }

    public void createMainSelling( MainSelling mainSelling) {
       try{
           if(mainSelling.getSellingId() == 1){      
               customerMainSellingDao.createMainSelling( mainSelling );
           }else{
               int sellingId = customerMainSellingDao.getSellingId( mainSelling.getCustomerId() );           
               mainSelling.setSellingId( sellingId + 1);
               customerMainSellingDao.createMainSelling( mainSelling );
               customerMainSellingDao.updateRegistInfo( mainSelling );
           }
       }catch(Exception e){
           throw new IKEP4ApplicationException( "DB 업데이트 실패", e );
       }
        
    }
    
   

    public void modifyMainSelling( MainSelling mainSelling ) {
        try{
            customerMainSellingDao.modifyMainSelling( mainSelling );
           
        }catch(Exception e){
            
            throw new IKEP4ApplicationException( "DB 업데이트 실패", e );
        }
        
        
        
    }

    public MainSelling getMainSellingItem( int seqNum ) {
        
        return customerMainSellingDao.getMainSellingItem( seqNum);
    }

    public void deleteMainSelling( MainSelling mainSelling ) {
       customerMainSellingDao.deleteMainSelling(mainSelling);
        
    }

    public int checkMainSelling( String customerId ) {
        
        return customerMainSellingDao.checkMainSelling(customerId);
    }

    public void syncCustomerName( MainSelling mainSelling ) {
        customerMainSellingDao.syncCustomerName( mainSelling);
        
    }





    
    
    
    
    
}
