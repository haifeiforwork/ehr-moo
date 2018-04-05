package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.util.model.ScheduleDetail;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * EpPersonDataRcvRFC.java
 * 그룹을 가져오는 RFC를 호출하는 Class
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("scheduleSync")
@Transactional
public class ScheduleSyncDataRcvRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
    private String functionName = "Z_HR_EP_PT_DATA_SEND";

    /**
     * 부서 정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    
    public List<ScheduleDetail> ScheduleSyncDataRcvRFC(HttpServletRequest request )  {
        
    	Connection connect = null;
    	List<ScheduleDetail> ScheduleDetailList = new ArrayList<ScheduleDetail>();
    					
		try {
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		//String n = function.toXML();			
		
		connect.executeSAP(function);
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		ScheduleDetail ScheduleDetailData = new ScheduleDetail();
        		
        		ScheduleDetailData.setPernr(tb.getString("PERNR"));
        		ScheduleDetailData.setBegda(tb.getString("BEGDA"));
        		ScheduleDetailData.setEndda(tb.getString("ENDDA"));
        		ScheduleDetailData.setAtext(tb.getString("ATEXT"));
        		ScheduleDetailData.setAwart(tb.getString("AWART"));
        		ScheduleDetailData.setRequestReason(tb.getString("REQUEST_REASON")); 
        		if(tb.getString("BEGUZ").equals("24:00:00")){
        			ScheduleDetailData.setBeguz("23:55:00");
        		}else{
        			ScheduleDetailData.setBeguz(tb.getString("BEGUZ"));
        		}
        		if(tb.getString("ENDUZ").equals("24:00:00")){
        			ScheduleDetailData.setEnduz("23:55:00");
        		}else{
        			ScheduleDetailData.setEnduz(tb.getString("ENDUZ"));
        		}
        		
        		if(tb.getString("BEGUZ").equals("")||tb.getString("ENDUZ").equals("")){
        			ScheduleDetailData.setWhole("1");
        		}else{
        			ScheduleDetailData.setWhole("0");
        		}
        		ScheduleDetailList.add(ScheduleDetailData);
        		
        	}
        	//log.debug(GroupDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return ScheduleDetailList;
    }
}

