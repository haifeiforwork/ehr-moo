package com.lgcns.ikep4.util.jco;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;


/**
 * EpGetHRWFCountRFC.java
 * ***을 가져오는  Class
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("epStatementRcv1")
@Transactional
public class EpGetStatementTestRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
    private String functionName = "ZFB_IF_EP_TEST";

    /**
     * 부서 정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public String EpGetStatementTestRFC(HttpServletRequest request )  {
        
    	Connection connect = null;
    	String strResult = "0";
    	//List<GroupDetail> GroupDetailList = new ArrayList<GroupDetail>();
    					
		try {
		connect = new Connection(request);	
		JCoFunction function = connect.getSAPFunction(functionName);
		String[] itBukrs = new String[2];
		//String[] itDt = new String[2];
		itBukrs[0] = "m100";
		itBukrs[1] = "p100";
		//itDt[0] = "201306";
		//itDt[1] = "201307";
		
		//function.getImportParameterList().getTable("IT_BUKRS").setValue("BUKRS", itBukrs);
		
		//function.getImportParameterList().getTable("IT_DT").setValue("FR_DT", "6");
		
		//function.getImportParameterList().getTable("IT_DT").setValue("TO_DT", "7");
		
		Hashtable hm = new Hashtable();
		
		hm.put("fr_dt", "6");
		hm.put("to_dt", "7");
		
//		JCoTable tb1 = function.getTableParameterList().getTable("IT_DT"); 
//		System.out.println("sap변수셋팅1!");
//		tb1.setValue("fr_dt", "6");
//		System.out.println("sap변수셋팅2!");
//		tb1.setValue("to_dt", "7");
		System.out.println("셋팅 전이야!~");
		
		function.getImportParameterList().setValue("IT_DT", hm);
		System.out.println("sap변수셋팅3!");
		
		/*TestTable test = new TestTable();
		test.setFR_DT("6");
		test.setTO_DT("7");
		function.getTableParameterList().setValue("IT_DT", test);*/
		
		//function.getImportParameterList().setValue("IT_DT", itDt);
		System.out.println("sap호출전!");
		//String n = function.toXML();			
		connect.executeSAP(function);
System.out.println("sap호출!");
System.out.println("if문 전");
		if (function.getExportParameterList() != null) {
			System.out.println("if문 진입");
			strResult = function.getExportParameterList().getString("O_RESULT");   
			System.out.println("strResult+++++++++++++++++++++++++++++++++++++"+strResult);
		}
		System.out.println("if문 후");
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

