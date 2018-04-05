package com.lgcns.ikep4.collpack.kms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.support.customer.dao.CustomerLinereplyDao;
import com.lgcns.ikep4.support.customer.model.CustomerLinereply;
import com.lgcns.ikep4.support.customer.service.CustomerLinereplyService;


public class InsertCustomerLineReplyFromCustom  extends BaseKmsTestCase{
    
    @Autowired
    private CustomerLinereplyService CustomerLinereplyServiceImpl;
    
    @Autowired
    private CustomerLinereplyDao customerLineReplyDao;
    
    
    
    
    @Test
    @Rollback(false)
    public void testCreate() {
        
        List<HashMap<String, String>> listHistorysMemo = (List<HashMap<String, String>>)customerLineReplyDao.listHistorysMemo();
        
        CustomerLinereply customerLinereply = null;
        
        for(HashMap resultData : listHistorysMemo){
            customerLinereply = setCustomerLinereply(resultData);
            CustomerLinereplyServiceImpl.createLinereply( customerLinereply );
        }
        
    }


/*
 * #linereplyId#,
        #itemType#,
        #itemId#,
        #linereplyGroupId#,
        #linereplyParentId#,
        #step#,
        #indentation#,
        #contents#,
        0,
        #linereplyDelete#,
        #updaterId#,
        #updaterName#,
        CURRENT_TIMESTAMP,
        #registerId#,
        #registerName#,
        CURRENT_TIMESTAMP
 * */
    private CustomerLinereply setCustomerLinereply( HashMap<String, String> resultData ) {
        
        CustomerLinereply customerLinereply = new CustomerLinereply();
        
        customerLinereply.setItemType( resultData.get("FLAG") );
        customerLinereply.setItemId( resultData.get( "SEQNUM" ) );
        customerLinereply.setLinereplyParentId( null );
        customerLinereply.setStep( 0 );
        customerLinereply.setIndentation( 0 );
        customerLinereply.setContents( resultData.get( "MEMO_CONTENT" ) );
        customerLinereply.setLinereplyDelete( 0 );
        customerLinereply.setUpdaterId( resultData.get("USER_ID") );
        customerLinereply.setUpdaterName( resultData.get("USER_NAME") );
        customerLinereply.setRegisterId( resultData.get("USER_ID") );
        customerLinereply.setRegisterName( resultData.get("USER_NAME") );
        customerLinereply.setUpdateDate( makeDate(resultData.get("CREATEDATE"))  );
        customerLinereply.setRegistDate( makeDate(resultData.get("CREATEDATE"))  );
        
        return customerLinereply;
    }


private Date makeDate( String createDate ) {
    Date date = null;
    try {
        
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        date = format.parse( createDate );
    } catch ( Exception e ) {
        // TODO: handle exception
    }
    return date;
}

}
