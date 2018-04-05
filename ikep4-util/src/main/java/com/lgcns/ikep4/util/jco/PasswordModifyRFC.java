package com.lgcns.ikep4.util.jco;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;




/**


 */
public class PasswordModifyRFC  {
	protected final Log log = LogFactory.getLog(this.getClass());


    /**
     */
    public int sapPasswordModify( String user_id , String old_pwd, String new_pwd, HttpServletRequest request )  {
        
    	Connection connect = null;
		connect = new Connection(request);	
		// 사진 정보 조회
		JCoFunction function = connect.getSAPFunction("SUSR_USER_CHANGE_PASSWORD_RFC");

		function.getImportParameterList().setValue("BNAME", user_id);
		function.getImportParameterList().setValue("PASSWORD", old_pwd);
		function.getImportParameterList().setValue("NEW_PASSWORD", new_pwd);
		int resultInt = 0;
		try {

			connect.executeSAP(function);	

		} catch (AbapException e) {

			log.debug("e.getMessage():"+e.getMessage());
			if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
				resultInt = 1;
			}
			if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
				resultInt = 4;
			}
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

			String x1 = function.toXML();
			log.debug(x1);
			log.debug("================ 사용자 비밀번호변경 연동 SAP=========================");
			return resultInt;
		}
    }
    
public int sapPasswordReset( String user_id, HttpServletRequest request )  {
        
    	Connection connect = null;
		connect = new Connection(request);	
		// 사진 정보 조회
		JCoFunction function = connect.getSAPFunction("Z_USER_PASSWORD_RESET");

		function.getImportParameterList().setValue("BNAME", user_id);
		
		int resultInt = 0;
		try {

			connect.executeSAP(function);	

		} catch (AbapException e) {
			log.debug("================ 사용자 비밀번호변경 연동 SAP 에러=========================");
			log.debug("e.getMessage():"+e.getMessage());
			if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
				resultInt = 1;
			}
			if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
				resultInt = 4;
			}
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

			String x1 = function.toXML();
			log.debug(x1);
			log.debug("================ 사용자 비밀번호변경 연동 SAP=========================");
			return resultInt;
		}
    }
    
	 public int bwPasswordModify( String user_id , String old_pwd, String new_pwd, HttpServletRequest request )  {
	        
	    	Connection connect = null;
			connect = new Connection(request);	
			// 사진 정보 조회
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-1");
			JCoFunction function = connect.getBWFunction("SUSR_USER_CHANGE_PASSWORD_RFC");
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-2:functionName:SUSR_USER_CHANGE_PASSWORD_RFC");
			function.getImportParameterList().setValue("BNAME", user_id);
			function.getImportParameterList().setValue("PASSWORD", old_pwd);
			function.getImportParameterList().setValue("NEW_PASSWORD", new_pwd);
			int resultInt = 0;
			try {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-3");
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("BNAME"));
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("PASSWORD"));
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("NEW_PASSWORD"));

				connect.executeBW(function);	

			} catch (AbapException e) {

				log.debug("e.getMessage():"+e.getMessage());
				if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 1;
				}
				if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 4;
				}
				log.debug("e.getGroup():"+e.getGroup());
				log.debug("e.getKey():"+e.getKey());
				e.printStackTrace();
			}finally{

				String x1 = function.toXML();
				log.debug(x1);
				log.debug("================ 사용자 비밀번호변경 연동 BW=========================");
				return resultInt;
			}
	    }
	 
	 public int apoPasswordModify( String user_id , String old_pwd, String new_pwd, HttpServletRequest request )  {
	        
	    	Connection connect = null;
			connect = new Connection(request);	
			// 사진 정보 조회
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-1");
			JCoFunction function = connect.getBWFunction("SUSR_USER_CHANGE_PASSWORD_RFC");
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-2:functionName:SUSR_USER_CHANGE_PASSWORD_RFC");
			function.getImportParameterList().setValue("BNAME", user_id);
			function.getImportParameterList().setValue("PASSWORD", old_pwd);
			function.getImportParameterList().setValue("NEW_PASSWORD", new_pwd);
			int resultInt = 0;
			try {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2-3");
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("BNAME"));
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("PASSWORD"));
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BNAME:"+function.getImportParameterList().getValue("NEW_PASSWORD"));

				connect.executeAPO(function);	

			} catch (AbapException e) {

				log.debug("e.getMessage():"+e.getMessage());
				if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 1;
				}
				if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 4;
				}
				log.debug("e.getGroup():"+e.getGroup());
				log.debug("e.getKey():"+e.getKey());
				e.printStackTrace();
			}finally{

				String x1 = function.toXML();
				log.debug(x1);
				log.debug("================ 사용자 비밀번호변경 연동 APO=========================");
				return resultInt;
			}
	    }
		    
	 public int bwPasswordReset( String user_id, HttpServletRequest request )  {
	        
	    	Connection connect = null;
			connect = new Connection(request);	
			// 사진 정보 조회
			JCoFunction function = connect.getBWFunction("Z_USER_PASSWORD_RESET");
			function.getImportParameterList().setValue("BNAME", user_id);
			int resultInt = 0;
			try {

				connect.executeBW(function);	

			} catch (AbapException e) {
				log.debug("================ 사용자 비밀번호변경 연동 BW 에러=========================");
				log.debug("e.getMessage():"+e.getMessage());
				if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 1;
				}
				if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 4;
				}
				log.debug("e.getGroup():"+e.getGroup());
				log.debug("e.getKey():"+e.getKey());
				e.printStackTrace();
			}finally{

				String x1 = function.toXML();
				log.debug(x1);
				log.debug("================ 사용자 비밀번호변경 연동 BW=========================");
				return resultInt;
			}
	    }
	 
	 	public int apoPasswordReset( String user_id, HttpServletRequest request )  {
	        
	    	Connection connect = null;
			connect = new Connection(request);	
			// 사진 정보 조회
			JCoFunction function = connect.getBWFunction("Z_USER_PASSWORD_RESET");
			function.getImportParameterList().setValue("BNAME", user_id);
			int resultInt = 0;
			try {

				connect.executeAPO(function);	

			} catch (AbapException e) {
				log.debug("================ 사용자 비밀번호변경 연동 APO 에러=========================");
				log.debug("e.getMessage():"+e.getMessage());
				if("CHANGE_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 1;
				}
				if("PASSWORD_NOT_ALLOWED".equals(e.getMessage())){
					resultInt = 4;
				}
				log.debug("e.getGroup():"+e.getGroup());
				log.debug("e.getKey():"+e.getKey());
				e.printStackTrace();
			}finally{

				String x1 = function.toXML();
				log.debug(x1);
				log.debug("================ 사용자 비밀번호변경 연동 APO=========================");
				return resultInt;
			}
	    }



}



