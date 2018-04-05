package com.lgcns.ikep4.util.jco;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;


/**
 * EpGetHRWFCountRFC.java
 * ***을 가져오는  Class
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
public class EpGetStatementCountRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
    private String functionName = "ZFB_IF_EP_CARD_CNT";

    /**
     * 부서 정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public String EpGetStatementCountRFC(HttpServletRequest request, String userID )  {
        
    	Connection connect = null;
    	String strResult = "0";
    	//List<GroupDetail> GroupDetailList = new ArrayList<GroupDetail>();
    					
		try {
		connect = new Connection(request);	
		JCoFunction function = connect.getSAPFunction(functionName);
		function.getImportParameterList().setValue("I_UNAME", userID);
		//String n = function.toXML();			
		connect.executeSAP(function);
		if (function.getExportParameterList() != null) {
			strResult = function.getExportParameterList().getString("EX_COUNT");   
		}
		//log.debug("");			

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{			
			
		}
		
	return strResult;
    } 
}

