package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.util.model.UserDetail;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * EpPersonDataRcvRFC.java
 * 부서정보를 가져오는 RFC를 호출하는 Class
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("epPersonRcvNew")
@Transactional
public class EpPersonDataRcvNewRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
    private String functionName = "Z_HR_EP_PERSON_DATA_SEND2";

    /**
     * 사원정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public List<UserDetail> EpPersonDataRcvNewRFC(HttpServletRequest request )  {
        
    	Connection connect = null;
    	List<UserDetail> userDetailList = new ArrayList<UserDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		connect.executeSAP(function);
		
		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		if(!"".equals(tb.getString("USRID_LONG") ))
        	  {
        		UserDetail userDetailData = new UserDetail();
        		
                String USRID_LONG = tb.getString("USRID_LONG").toLowerCase();
        		
        		if(USRID_LONG.indexOf("@")>0)
        		{
        			String[] userIdArr = USRID_LONG.split("@");        		
        			userDetailData.setUserId(userIdArr[0]);
        			userDetailData.setMail(USRID_LONG);
        		}
        		else
        		{        				
        			userDetailData.setUserId(USRID_LONG);
        			userDetailData.setMail("");
        		}        		     		
        		userDetailData.setUserStatus(tb.getString("STAT2"));
        		userDetailData.setEmpNo(tb.getString("PERNR"));
        	    userDetailData.setUserName(tb.getString("SNAME"));
        		userDetailData.setUserEnglishName(tb.getString("ZENAM"));
        		userDetailData.setTeamCode(tb.getString("ORGEH"));
        		userDetailData.setTeamName(tb.getString("ORGTX"));
        		userDetailData.setMobile(tb.getString("USRID2"));
        		userDetailData.setOfficePhoneNo(tb.getString("USRID3"));
        		userDetailData.setBirthday (tb.getString("GBDAT"));
        		userDetailData.setLunisolar(tb.getString("ZZBIR"));
        		userDetailData.setLeader(tb.getString("ZRSIGN"));
        		userDetailData.setJobDutyCode(tb.getString("ZZJIK"));
        		userDetailData.setJobDutyName(tb.getString("COTXT"));
        		userDetailData.setJobPositionCode(tb.getString("TRFGR"));
        		userDetailData.setJobPositionName(tb.getString("JIKWI"));
        		userDetailData.setTeamEnglishName(tb.getString("ZORGTX"));
        		userDetailData.setProfilePicturePath (tb.getString("PPATH"));
        		userDetailData.setBwId (tb.getString("USRID4"));
        		userDetailData.setSapId(tb.getString("USRID5"));
        		userDetailData.setJobClassCode(tb.getString("PERSK"));
        		userDetailData.setJobClassName(tb.getString("PTEXT"));
        		userDetailData.setWorkPlaceCode(tb.getString("BTRTL"));
        		userDetailData.setWorkPlaceName(tb.getString("BTEXT"));
        		userDetailData.setHanziName(tb.getString("ZHNAM"));
        		userDetailData.setModifiedDate(tb.getString("ZAEDT"));
        		userDetailData.setCompanyName (tb.getString("BUTXT"));
        		userDetailData.setCompanyCode (tb.getString("BUKRS"));
        		userDetailData.setEssAuthCode(tb.getString("EROLE"));
        		userDetailData.setMssAuthCode(tb.getString("MROLE"));
        		userDetailData.setErpPositionName(tb.getString("PLSTX"));
        		userDetailData.setErpPositionCode(tb.getString("PLANS"));
        		userDetailData.setScheduleManager(tb.getString("SACHZ"));
        		userDetailData.setExecutiveId(tb.getString("EXECUTIVE_ID"));
        		userDetailData.setEntryDate(tb.getString("ENTRY_DATE"));
        		userDetailData.setLeavingDate(tb.getString("LEAVING_DATE"));
        		userDetailData.setRetireFlag(tb.getString("RETIRE_FLAG"));
        		
        		
        		
        		userDetailList.add(userDetailData);
        	  }
        		
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return userDetailList;
    }
    
    public List<UserDetail> EpPersonDataRFC(HttpServletRequest request, String jobCondition )  {
        
    	Connection connect = null;
    	List<UserDetail> userDetailList = new ArrayList<UserDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		function.getImportParameterList().setValue("I_DATE", jobCondition);
		connect.executeSAP(function);
		
		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		if(!"".equals(tb.getString("USRID_LONG") ))
        	  {
        		UserDetail userDetailData = new UserDetail();
        		
                String USRID_LONG = tb.getString("USRID_LONG").toLowerCase();
        		
        		if(USRID_LONG.indexOf("@")>0)
        		{
        			String[] userIdArr = USRID_LONG.split("@");        		
        			userDetailData.setUserId(userIdArr[0]);
        			userDetailData.setMail(USRID_LONG);
        		}
        		else
        		{        				
        			userDetailData.setUserId(USRID_LONG);
        			userDetailData.setMail("");
        		}        		     		
        		userDetailData.setUserStatus(tb.getString("STAT2"));
        		userDetailData.setEmpNo(tb.getString("PERNR"));
        	    userDetailData.setUserName(tb.getString("SNAME"));
        		userDetailData.setUserEnglishName(tb.getString("ZENAM"));
        		userDetailData.setTeamCode(tb.getString("ORGEH"));
        		userDetailData.setTeamName(tb.getString("ORGTX"));
        		userDetailData.setMobile(tb.getString("USRID2"));
        		userDetailData.setOfficePhoneNo(tb.getString("USRID3"));
        		userDetailData.setBirthday (tb.getString("GBDAT"));
        		userDetailData.setLunisolar(tb.getString("ZZBIR"));
        		userDetailData.setLeader(tb.getString("ZRSIGN"));
        		userDetailData.setJobDutyCode(tb.getString("ZZJIK"));
        		userDetailData.setJobDutyName(tb.getString("COTXT"));
        		userDetailData.setJobPositionCode(tb.getString("TRFGR"));
        		userDetailData.setJobPositionName(tb.getString("JIKWI"));
        		userDetailData.setTeamEnglishName(tb.getString("ZORGTX"));
        		userDetailData.setProfilePicturePath (tb.getString("PPATH"));
        		userDetailData.setBwId (tb.getString("USRID4"));
        		userDetailData.setSapId(tb.getString("USRID5"));
        		userDetailData.setJobClassCode(tb.getString("PERSK"));
        		userDetailData.setJobClassName(tb.getString("PTEXT"));
        		userDetailData.setWorkPlaceCode(tb.getString("BTRTL"));
        		userDetailData.setWorkPlaceName(tb.getString("BTEXT"));
        		userDetailData.setHanziName(tb.getString("ZHNAM"));
        		userDetailData.setModifiedDate(tb.getString("ZAEDT"));
        		userDetailData.setCompanyName (tb.getString("BUTXT"));
        		userDetailData.setCompanyCode (tb.getString("BUKRS"));
        		userDetailData.setEssAuthCode(tb.getString("EROLE"));
        		userDetailData.setMssAuthCode(tb.getString("MROLE"));
        		userDetailData.setErpPositionName(tb.getString("PLSTX"));
        		userDetailData.setErpPositionCode(tb.getString("PLANS"));
        		userDetailData.setScheduleManager(tb.getString("SACHZ"));
        		userDetailData.setExecutiveId(tb.getString("EXECUTIVE_ID"));
        		userDetailData.setEntryDate(tb.getString("ENTRY_DATE"));
        		userDetailData.setLeavingDate(tb.getString("LEAVING_DATE"));
        		userDetailData.setRetireFlag(tb.getString("RETIRE_FLAG"));
        		
        		
        		userDetailList.add(userDetailData);
        	  }
        		
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return userDetailList;
    }
    
public List<UserDetail> EpPersonTempDataRcvRFC(HttpServletRequest request )  {
        
		Connection connect = null;
    	List<UserDetail> userDetailList = new ArrayList<UserDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		connect.executeSAP(function);
		
		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		if(!"".equals(tb.getString("USRID_LONG") ))
        	  {
        		UserDetail userDetailData = new UserDetail();
        		
                String USRID_LONG = tb.getString("USRID_LONG").toLowerCase();
        		
        		if(USRID_LONG.indexOf("@")>0)
        		{
        			String[] userIdArr = USRID_LONG.split("@");        		
        			userDetailData.setUserId(userIdArr[0]);
        			userDetailData.setMail(USRID_LONG);
        		}
        		else
        		{        				
        			userDetailData.setUserId(USRID_LONG);
        			userDetailData.setMail("");
        		}        		     		
        		userDetailData.setUserStatus(tb.getString("STAT2"));
        		userDetailData.setEmpNo(tb.getString("PERNR"));
        	    userDetailData.setUserName(tb.getString("SNAME"));
        		userDetailData.setUserEnglishName(tb.getString("ZENAM"));
        		userDetailData.setTeamCode(tb.getString("ORGEH"));
        		userDetailData.setTeamName(tb.getString("ORGTX"));
        		userDetailData.setMobile(tb.getString("USRID2"));
        		userDetailData.setOfficePhoneNo(tb.getString("USRID3"));
        		userDetailData.setBirthday (tb.getString("GBDAT"));
        		userDetailData.setLunisolar(tb.getString("ZZBIR"));
        		userDetailData.setLeader(tb.getString("ZRSIGN"));
        		userDetailData.setJobDutyCode(tb.getString("ZZJIK"));
        		userDetailData.setJobDutyName(tb.getString("COTXT"));
        		userDetailData.setJobPositionCode(tb.getString("TRFGR"));
        		userDetailData.setJobPositionName(tb.getString("JIKWI"));
        		userDetailData.setTeamEnglishName(tb.getString("ZORGTX"));
        		userDetailData.setProfilePicturePath (tb.getString("PPATH"));
        		userDetailData.setBwId (tb.getString("USRID4"));
        		userDetailData.setSapId(tb.getString("USRID5"));
        		userDetailData.setJobClassCode(tb.getString("PERSK"));
        		userDetailData.setJobClassName(tb.getString("PTEXT"));
        		userDetailData.setWorkPlaceCode(tb.getString("BTRTL"));
        		userDetailData.setWorkPlaceName(tb.getString("BTEXT"));
        		userDetailData.setHanziName(tb.getString("ZHNAM"));
        		userDetailData.setModifiedDate(tb.getString("ZAEDT"));
        		userDetailData.setCompanyName (tb.getString("BUTXT"));
        		userDetailData.setCompanyCode (tb.getString("BUKRS"));
        		userDetailData.setEssAuthCode(tb.getString("EROLE"));
        		userDetailData.setMssAuthCode(tb.getString("MROLE"));
        		userDetailData.setErpPositionName(tb.getString("PLSTX"));
        		userDetailData.setErpPositionCode(tb.getString("PLANS"));
        		userDetailData.setScheduleManager(tb.getString("SACHZ"));
        		userDetailData.setExecutiveId(tb.getString("EXECUTIVE_ID"));
        		userDetailData.setEntryDate(tb.getString("ENTRY_DATE"));
        		
        		
        		
        		userDetailList.add(userDetailData);
        	  }
        		
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return userDetailList;
    }
    
    /**
     * 사원정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public List<UserDetail> EpAllPersonDataRcvRFC(HttpServletRequest request )  {
        
    	Connection connect = null;
    	List<UserDetail> userDetailList = new ArrayList<UserDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		//String n = function.toXML();			
	    function.getImportParameterList().setValue("UPDATE_ALL", "X");
		connect.executeSAP(function);
		
		if (function.getTableParameterList() != null) {
			
        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
        	        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        	  if(!"".equals(tb.getString("USRID_LONG") ))
        	  {
        		UserDetail userDetailData = new UserDetail();
        		
        		String USRID_LONG = tb.getString("USRID_LONG").toLowerCase();
        		
        		if(USRID_LONG.indexOf("@")>0)
        		{
        			String[] userIdArr = USRID_LONG.split("@");        		
        			userDetailData.setUserId(userIdArr[0]);
        			userDetailData.setMail(USRID_LONG);
        		}
        		else
        		{        				
        			userDetailData.setUserId(USRID_LONG);
        			userDetailData.setMail("");
        		}
        		
        		userDetailData.setUserStatus(tb.getString("STAT2"));
        		userDetailData.setEmpNo(tb.getString("PERNR"));
        		userDetailData.setUserName(tb.getString("SNAME"));
        		userDetailData.setUserEnglishName(tb.getString("ZENAM"));
        		userDetailData.setTeamCode(tb.getString("ORGEH"));
        		userDetailData.setTeamName(tb.getString("ORGTX"));
        		userDetailData.setMobile(tb.getString("USRID2"));
        		userDetailData.setOfficePhoneNo(tb.getString("USRID3"));
        		userDetailData.setBirthday (tb.getString("GBDAT"));
        		userDetailData.setLunisolar(tb.getString("ZZBIR"));
        		userDetailData.setLeader(tb.getString("ZRSIGN"));
        		userDetailData.setJobDutyCode(tb.getString("ZZJIK"));
        		userDetailData.setJobDutyName(tb.getString("COTXT"));
        		userDetailData.setJobPositionCode(tb.getString("TRFGR"));
        		userDetailData.setJobPositionName(tb.getString("JIKWI"));
        		userDetailData.setTeamEnglishName(tb.getString("ZORGTX"));
        		userDetailData.setProfilePicturePath (tb.getString("PPATH"));
        		userDetailData.setBwId (tb.getString("USRID4"));
        		userDetailData.setSapId(tb.getString("USRID5"));
        		userDetailData.setJobClassCode(tb.getString("PERSK"));
        		userDetailData.setJobClassName(tb.getString("PTEXT"));
        		userDetailData.setWorkPlaceCode(tb.getString("BTRTL"));
        		userDetailData.setWorkPlaceName(tb.getString("BTEXT"));
        		userDetailData.setHanziName(tb.getString("ZHNAM"));
        		userDetailData.setModifiedDate(tb.getString("ZAEDT"));
        		userDetailData.setCompanyName (tb.getString("BUTXT"));
        		userDetailData.setCompanyCode (tb.getString("BUKRS"));
        		userDetailData.setEssAuthCode(tb.getString("EROLE"));
        		userDetailData.setMssAuthCode(tb.getString("MROLE"));
        		userDetailData.setErpPositionName(tb.getString("PLSTX"));
        		userDetailData.setErpPositionCode(tb.getString("PLANS"));
        		userDetailData.setScheduleManager(tb.getString("SACHZ"));
        		userDetailData.setExecutiveId(tb.getString("EXECUTIVE_ID"));
        		       		
        		userDetailList.add(userDetailData);
        	  }
        		
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return userDetailList;
    }
    
    /**
     * 사원정보를  가져오는 RFC를 호출하는 Method
     * @param java.lang.String 사번
     * @return java.util.Vector
     * @throws JCoException 
     * @exception com.sns.jdf.GeneralException
     */
    public UserDetail EpPersonDataRcvRFC(HttpServletRequest request, String empNo )  {
        
    	Connection connect = null;
    	UserDetail userDetailData = new UserDetail();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction(functionName);
		function.getImportParameterList().setValue("I_PERNR", empNo);
		//String n = function.toXML();			
		
		connect.executeSAP(function);

		if (function.getExportParameterList() != null) 
		{
			
        	    JCoTable tb = function.getTableParameterList().getTable("ITAP");      	
				tb.nextRow();

        		userDetailData.setUserId(tb.getString("USRID_LONG"));
        		userDetailData.setUserStatus(tb.getString("STAT2"));
        		userDetailData.setEmpNo(tb.getString("PERNR"));
        		userDetailData.setMail(tb.getString("USRID"));
        		userDetailData.setUserName(tb.getString("SNAME"));
        		userDetailData.setUserEnglishName(tb.getString("ZENAM"));
        		userDetailData.setTeamCode(tb.getString("ORGEH"));
        		userDetailData.setTeamName(tb.getString("ORGTX"));
        		userDetailData.setMobile(tb.getString("USRID2"));
        		userDetailData.setOfficePhoneNo(tb.getString("USRID3"));
        		userDetailData.setBirthday (tb.getString("GBDAT"));
        		userDetailData.setLunisolar(tb.getString("ZZBIR"));
        		userDetailData.setLeader(tb.getString("ZRSIGN"));
        		userDetailData.setJobDutyCode(tb.getString("ZZJIK"));
        		userDetailData.setJobDutyName(tb.getString("COTXT"));
        		userDetailData.setJobPositionCode(tb.getString("TRFGR"));
        		userDetailData.setJobPositionName(tb.getString("JIKWI"));
        		userDetailData.setTeamEnglishName(tb.getString("ZORGTX"));
        		userDetailData.setProfilePicturePath (tb.getString("PPATH"));
        		userDetailData.setBwId (tb.getString("USRID4"));
        		userDetailData.setSapId(tb.getString("USRID5"));
        		userDetailData.setJobClassCode(tb.getString("PERSK"));
        		userDetailData.setJobClassName(tb.getString("PTEXT"));
        		userDetailData.setWorkPlaceCode(tb.getString("BTRTL"));
        		userDetailData.setWorkPlaceName(tb.getString("BTEXT"));
        		userDetailData.setHanziName(tb.getString("ZHNAM"));
        		userDetailData.setModifiedDate(tb.getString("ZAEDT"));
        		userDetailData.setCompanyName (tb.getString("BUTXT"));
        		userDetailData.setCompanyCode (tb.getString("BUKRS"));
        		userDetailData.setEssAuthCode(tb.getString("EROLE"));
        		userDetailData.setMssAuthCode(tb.getString("MROLE"));
        		userDetailData.setErpPositionName(tb.getString("PLSTX"));
        		userDetailData.setErpPositionCode(tb.getString("PLANS"));
        		userDetailData.setScheduleManager(tb.getString("SACHZ"));
        		userDetailData.setExecutiveId(tb.getString("EXECUTIVE_ID"));
				// log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return userDetailData;
    }



 
}

