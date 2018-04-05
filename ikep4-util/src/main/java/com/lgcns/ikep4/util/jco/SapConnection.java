package com.lgcns.ikep4.util.jco;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.util.PropertyLoader;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.ext.DestinationDataProvider;


/**
 * Connection allows to get and execute SAP functions. The constructor expect a
 * SapSystem and will save the connection data to a file. The connection will
 * also be automatically be established.
 */
public class SapConnection {
	
	String SAP_SERVER ="";
    
	private JCoRepository SAP_repos;
	private JCoDestination SAP_dest;
	private Properties SAP_properties;
	
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	public SapConnection(HttpServletRequest request) {
		
		SAP_properties = new Properties();
		
		try{
			
			Properties prop  = PropertyLoader.loadProperties("/configuration/sap-conf.properties");

			
			SAP_SERVER = prop.getProperty("jco.client.destination");
	        
			String SAP_HOST_NAME	= prop.getProperty("jco.client.mshost");
			String SAP_SYSTEM_NR	= prop.getProperty("jco.client.sysnr");
	        String SAP_CLIENT_NO	= prop.getProperty("jco.client.client");			
	        String SAP_USER_ID		= prop.getProperty("jco.client.user");
	        String SAP_PASSWORD		= prop.getProperty("jco.client.passwd");	        
	        String SAP_LANGUAGE		= prop.getProperty("jco.client.lang");
	        String SAP_POOL_CAPACITY = prop.getProperty("jco.destination.pool_capacity");
	        String SAP_PEAK_LIMIT	= prop.getProperty("jco.destination.peak_limit");
	        String SAP_R3_NAME		= prop.getProperty("jco.client.r3name");	       
	        String SAP_GROUP		= prop.getProperty("jco.client.group");
	        
	        
	        SAP_properties.setProperty("ACTION", "CREATE");
	        SAP_properties.setProperty(DestinationDataProvider.JCO_DEST ,SAP_SERVER);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_ASHOST, SAP_HOST_NAME);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_MSHOST, SAP_HOST_NAME);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_SYSNR,  SAP_SYSTEM_NR);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_CLIENT, SAP_CLIENT_NO);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_USER,   SAP_USER_ID);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_PASSWD, SAP_PASSWORD);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_LANG,   SAP_LANGUAGE);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, SAP_POOL_CAPACITY);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    SAP_PEAK_LIMIT);
	        SAP_properties.setProperty(DestinationDataProvider.JCO_R3NAME,    SAP_R3_NAME);  
	        SAP_properties.setProperty(DestinationDataProvider.JCO_GROUP, SAP_GROUP);
	        

			MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
			
			if (!com.sap.conn.jco.ext.Environment.isDestinationDataProviderRegistered()) {
				com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
			}else{
				//System.out.println("##########################isDestinationDataProviderRegistered");
			}
			if(SAP_properties==null){
				//System.out.println("##########################SAP_properties is null");
			}
			
			myProvider.changePropertiesForABAP_AS(SAP_properties);

		
		}catch(Exception e){			
			e.printStackTrace();	
		}

		try {

			
			SAP_dest = JCoDestinationManager.getDestination(SAP_SERVER);
	        SAP_repos = SAP_dest.getRepository();
	        SAP_dest.ping();
	        

	        
		} catch (JCoException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Method getFunction read a SAP Function and return it to the caller. The
	 * caller can then set parameters (import, export, tables) on this function
	 * and call later the method execute.
	 * 
	 * getFunction translates the JCo checked exceptions into a non-checked
	 * exceptions
	 */
	public JCoFunction getSAPFunction(String functionStr) {
		JCoFunction function = null;
		try {
			function = SAP_repos.getFunction(functionStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problem retrieving SAP JCO.Function object.");
		}
		if (function == null) {
			throw new RuntimeException("Not possible to receive SAP function. ");
		}

		return function;
	}
	
	/**
	 * Method execute will call a function. The Caller of this function has
	 * already set all required parameters of the function
	 * @throws JCoException 
	 * 
	 */
	public void executeSAP(JCoFunction function) throws JCoException {
		// modified by hosung from EACC.
		JCoContext.begin(SAP_dest);
		try {
			function.execute(SAP_dest);
			this.log.debug("data: " +function.toXML());
		
			//LLog.debug.write(function.toXML());
			//function.writeXML("C:\\function.xml");
 
		} catch (JCoException e) {
			this.log.debug("Error on SAP JCO : "+function.toXML() + e);
			e.printStackTrace();
			
		} finally {
			this.log.debug("SAP RFC END");
			JCoContext.end(SAP_dest);
		}
	}
}
