package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.util.model.PartnerBasicInfo;
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
public class EpPartnerRcvRFC {
    
    protected final Log log = LogFactory.getLog(this.getClass());
    
    private String functionName = "ZEP_BASICINFO_MASTER";
    
    public List<PartnerBasicInfo> EpPartnerRcvRFC(HttpServletRequest request){
        
    	Connection connect  = null;
        List<PartnerBasicInfo> partnerBasicInfoList = new ArrayList<PartnerBasicInfo>();
        
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
                        PartnerBasicInfo partnerBasicInfo = new PartnerBasicInfo();
                        
                        partnerBasicInfo.setSapId( tb.getString( "KUNNR" ) );
                        partnerBasicInfo.setName( tb.getString( "NAME1" ) );
                        partnerBasicInfo.setRegno( tb.getString( "STCD2" ) );
                        partnerBasicInfo.setCorporationNo( tb.getString( "STCD1" ) );
                        partnerBasicInfo.setDirector( tb.getString( "J_1KFREPRE" ) );
                        partnerBasicInfo.setType( tb.getString( "J_1KFTBUS" ) );
                        partnerBasicInfo.setJijongType( tb.getString( "J_1KFTIND" ) );
                        partnerBasicInfo.setPostNo( tb.getString( "PSTLZ" ) );
                        partnerBasicInfo.setAddress2( tb.getString( "STRAS" ) );
                        partnerBasicInfo.setAddress1( tb.getString( "ORT01" ) );
                        partnerBasicInfo.setTelNo( tb.getString( "TELF1" ) );
                        partnerBasicInfo.setFax( tb.getString( "TELFX" ) );
                        partnerBasicInfo.seteMail( tb.getString( "SMTP_ADDR" ) );
                        
                        partnerBasicInfoList.add( partnerBasicInfo );
 
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
        
        return partnerBasicInfoList;
        
        
    }
}
