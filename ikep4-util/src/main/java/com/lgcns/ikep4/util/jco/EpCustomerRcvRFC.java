package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * 
 * 고객정보를 가져오는 RFC를 호출하는 Class
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class EpCustomerRcvRFC {
    
    protected final Log log = LogFactory.getLog(this.getClass());
    
    private String functionName = "ZEP_BASICINFO_MASTER";
    
    public List<CustomerBasicInfo> EpCustomerRcvRFC(HttpServletRequest request){
        
    	Connection connect  = null;
        List<CustomerBasicInfo> customerBasicInfoList = new ArrayList<CustomerBasicInfo>();
        
        try{
            
            connect = new Connection(request);  
            
            JCoFunction function = connect.getSAPFunction( functionName );
            connect.executeSAP( function );
            
            if(function.getTableParameterList()  != null){
                
                JCoTable tb = function.getTableParameterList().getTable( "ZLES1047" );
                
                for(int i=0; i<tb.getNumRows() ;i++, tb.nextRow())
                {
                    if(!"".equals( tb.getString( "KUNNR" ) ))
                    {
                        CustomerBasicInfo customerBasicInfo = new CustomerBasicInfo();
                        
                        customerBasicInfo.setSapId( tb.getString( "KUNNR" ) );
                        customerBasicInfo.setName( tb.getString( "NAME1" ) );
                        customerBasicInfo.setRegno( tb.getString( "STCD2" ) );
                        customerBasicInfo.setCorporationNo( tb.getString( "STCD1" ) );
                        customerBasicInfo.setDirector( tb.getString( "J_1KFREPRE" ) );
                        customerBasicInfo.setType( tb.getString( "J_1KFTBUS" ) );
                        customerBasicInfo.setJijongType( tb.getString( "J_1KFTIND" ) );
                        customerBasicInfo.setPostNo( tb.getString( "PSTLZ" ) );
                        customerBasicInfo.setAddress2( tb.getString( "STRAS" ) );
                        customerBasicInfo.setAddress1( tb.getString( "ORT01" ) );
                        customerBasicInfo.setTelNo( tb.getString( "TELF1" ) );
                        customerBasicInfo.setFax( tb.getString( "TELFX" ) );
                        customerBasicInfo.seteMail( tb.getString( "SMTP_ADDR" ) );
                        
                        customerBasicInfoList.add( customerBasicInfo );
 
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
        
        return customerBasicInfoList;
        
        
    }
}
