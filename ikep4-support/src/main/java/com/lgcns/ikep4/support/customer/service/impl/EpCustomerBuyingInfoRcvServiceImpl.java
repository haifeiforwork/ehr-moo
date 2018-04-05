package com.lgcns.ikep4.support.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.customer.service.EpCustomerBuyingInfoRcvService;
import com.lgcns.ikep4.util.jco.Connection;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Service
@Transactional
public class EpCustomerBuyingInfoRcvServiceImpl extends GenericServiceImpl<CustomerBuyingInfo, String> implements EpCustomerBuyingInfoRcvService                               {

 protected final Log log = LogFactory.getLog(this.getClass());
    
    private String functionName = "ZEP_BASICINFO_BUYING";
    
    public List<CustomerBuyingInfo> EpCustomerBuyingRcv(HttpServletRequest request){
        
    	Connection connect  = null;
        List<CustomerBuyingInfo> customerBuyingInfoList = new ArrayList<CustomerBuyingInfo>();
       
        
        try{
            
            connect = new Connection(request);
            JCoFunction function  = connect.getSAPFunction( functionName );
            connect.executeSAP( function );
            
            if(function.getTableParameterList() != null){
                JCoTable tb = function.getTableParameterList().getTable( "ZLES1048" );
                
                
                for(int i = 0; i<tb.getNumRows(); i++, tb.nextRow()){
                    
                    
                    if(!"".equals( tb.getString( "KUNNR" ) )){
                        CustomerBuyingInfo customerBuyingInfo = new CustomerBuyingInfo();
                        
                        customerBuyingInfo.setSapId( tb.getString( "KUNNR" ) );
                        customerBuyingInfo.setSellingGroup( tb.getString( "VKORGT" ) );
                        customerBuyingInfo.setChannel( tb.getString( "VTWEGT" ) );
                        customerBuyingInfo.setProductLine( tb.getString( "SPARTT" ) );
                        customerBuyingInfo.setCustomerGroup2( tb.getString( "KVGR2T" ) );
                        customerBuyingInfo.setCustomerGroup3( tb.getString( "KVGR3T" ) );
                        customerBuyingInfo.setBusinessEmployee( tb.getString( "PERNR" ).replaceAll(" ","") );
                        
                        customerBuyingInfoList.add( customerBuyingInfo );
                        
                    }
                    
                }
                
                
                
                
            }
            
            
            
        }catch(JCoException e){
            log.debug("e.getMessage():"+e.getMessage());        
            log.debug("e.getGroup():"+e.getGroup());
            log.debug("e.getKey():"+e.getKey());
            e.printStackTrace();
            
        }finally{
            
        }
        return customerBuyingInfoList;
        
    }

    public String create( CustomerBuyingInfo arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public CustomerBuyingInfo read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( CustomerBuyingInfo arg0 ) {
        // TODO Auto-generated method stub
        
    }
    
    
}
