package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.util.model.GroupDetail;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * EpPersonDataRcvRFC.java
 * 그룹을 가져오는 RFC를 호출하는 Class
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("epGrpRcv")
@Transactional
public class EpGroupDataRcvRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
    private String functionName = "Z_HR_EP_ORGANI_DATA_SEND";

    /**
     * 부서 정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    
    public List<GroupDetail> EpGroupDataRcvRFC(HttpServletRequest request )  {
        
    	Connection connect = null;
    	List<GroupDetail> GroupDetailList = new ArrayList<GroupDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		//String n = function.toXML();			
		
		connect.executeSAP(function);

		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		GroupDetail GroupDetailData = new GroupDetail();
        		
        		GroupDetailData.setGroupId(tb.getString("ORGEH"));
        		GroupDetailData.setGroupName(tb.getString("ORGTX"));
        		GroupDetailData.setParentGroupId(tb.getString("SOBID"));
        		GroupDetailData.setGroupEnglishName(tb.getString("ZORGTX"));
        		GroupDetailData.setLeaderId(tb.getString("USRID_LONG"));        	
        		GroupDetailData.setSortOrder(tb.getString("PRIOX"));
        		GroupDetailData.setGroupEdate(tb.getString("ENDDA"));
        		GroupDetailData.setIsUse(tb.getString("ORG_STR"));
        		
        		GroupDetailList.add(GroupDetailData);
        		
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
		
		return GroupDetailList;
    }
    
    public List<GroupDetail> EpGroupDataRFC(HttpServletRequest request, String jobCondition )  {
        
    	Connection connect = null;
    	List<GroupDetail> GroupDetailList = new ArrayList<GroupDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		//String n = function.toXML();			
		function.getImportParameterList().setValue("I_DATE", jobCondition);
		connect.executeSAP(function);

		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		GroupDetail GroupDetailData = new GroupDetail();
        		
        		GroupDetailData.setGroupId(tb.getString("ORGEH"));
        		GroupDetailData.setGroupName(tb.getString("ORGTX"));
        		GroupDetailData.setParentGroupId(tb.getString("SOBID"));
        		GroupDetailData.setGroupEnglishName(tb.getString("ZORGTX"));
        		GroupDetailData.setLeaderId(tb.getString("USRID_LONG"));        	
        		GroupDetailData.setSortOrder(tb.getString("PRIOX"));
        		GroupDetailData.setGroupEdate(tb.getString("ENDDA"));
        		GroupDetailData.setIsUse(tb.getString("ORG_STR"));
        		
        		GroupDetailList.add(GroupDetailData);
        		
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
		
		return GroupDetailList;
    }
    
    /**
     * 부서 정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String groupID
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public GroupDetail EpGroupDataRcvRFC(HttpServletRequest request, String groupID )  {
        
    	Connection connect = null;
    	GroupDetail GroupDetailData = new GroupDetail();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		function.getImportParameterList().setValue("rfc func 완성시 파라미터 설정", groupID);
		//String n = function.toXML();			
		
		connect.executeSAP(function);

		if (function.getExportParameterList() != null) 
		{
			
        	    JCoTable tb = function.getTableParameterList().getTable("ITAP");      	
				tb.nextRow();

				GroupDetailData.setGroupId(tb.getString("ORGEH"));
        		GroupDetailData.setGroupName(tb.getString("ORGTX"));
        		GroupDetailData.setParentGroupId(tb.getString("SOBID"));
        		GroupDetailData.setGroupEnglishName(tb.getString("ZORGTX"));
        		GroupDetailData.setLeaderId(tb.getString("USRID_LONG"));
        		GroupDetailData.setSortOrder(tb.getString("PRIOX"));
        		GroupDetailData.setGroupEdate(tb.getString("ENDDA"));
        		
				// log.debug(GroupDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return GroupDetailData;
    }



 
}

