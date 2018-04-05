package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.util.model.CustomerBuyingInfo;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;



/**
 * 
 * (고객 정보) 구매정보를 가져오는 RFC를 호출하는 Class
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class EpCustomerBuyingInfoRcvRFC {

    protected final Log log = LogFactory.getLog(this.getClass());
    
    private String functionName = "ZEP_BASICINFO_BUYING";
    
    public List<CustomerBuyingInfo> EpCustomerBuyingRcvRFC(HttpServletRequest request){
        
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
}
