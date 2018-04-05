package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.util.model.PartnerBuyingInfo;
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
public class EpPartnerBuyingInfoRcvRFC {

    protected final Log log = LogFactory.getLog(this.getClass());
    
    private String functionName = "ZEP_BASICINFO_BUYING";
    
    public List<PartnerBuyingInfo> EpPartnerBuyingRcvRFC(HttpServletRequest request){
        
    	Connection connect  = null;
        List<PartnerBuyingInfo> partnerBuyingInfoList = new ArrayList<PartnerBuyingInfo>();
       
        
        try{
            
            connect = new Connection(request);
            JCoFunction function  = connect.getSAPFunction( functionName );
            connect.executeSAP( function );
            
            if(function.getTableParameterList() != null){
                JCoTable tb = function.getTableParameterList().getTable( "ZLES1048" );
                
                
                for(int i = 0; i<tb.getNumRows(); i++, tb.nextRow()){
                    
                    
                    if(!"".equals( tb.getString( "KUNNR" ) )){
                        PartnerBuyingInfo partnerBuyingInfo = new PartnerBuyingInfo();
                        
                        partnerBuyingInfo.setSapId( tb.getString( "KUNNR" ) );
                        partnerBuyingInfo.setSellingGroup( tb.getString( "VKORGT" ) );
                        partnerBuyingInfo.setChannel( tb.getString( "VTWEGT" ) );
                        partnerBuyingInfo.setProductLine( tb.getString( "SPARTT" ) );
                        partnerBuyingInfo.setPartnerGroup2( tb.getString( "KVGR2T" ) );
                        partnerBuyingInfo.setPartnerGroup3( tb.getString( "KVGR3T" ) );
                        partnerBuyingInfo.setBusinessEmployee( tb.getString( "PERNR" ).replaceAll(" ","") );
                        
                        partnerBuyingInfoList.add( partnerBuyingInfo );
                        
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
        return partnerBuyingInfoList;
        
    }
}
