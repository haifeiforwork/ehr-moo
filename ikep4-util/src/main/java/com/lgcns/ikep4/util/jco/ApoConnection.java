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
public class ApoConnection {
	
    String APO_SERVER ="";
    
	
	private JCoRepository APO_repos;
	private JCoDestination APO_dest;
	private Properties APO_properties;
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	public ApoConnection(HttpServletRequest request) {
		
		APO_properties = new Properties();
		
		try{
			
			Properties prop  = PropertyLoader.loadProperties("/configuration/sap-conf.properties");

			
	        
	        APO_SERVER = prop.getProperty("jco.APOclient.destination");
	        
			String APO_HOST_NAME	= prop.getProperty("jco.APOclient.mshost");
			String APO_SYSTEM_NR	= prop.getProperty("jco.APOclient.sysnr");
	        String APO_CLIENT_NO	= prop.getProperty("jco.APOclient.client");			
	        String APO_USER_ID		= prop.getProperty("jco.APOclient.user");
	        String APO_PASSWORD		= prop.getProperty("jco.APOclient.passwd");	        
	        String APO_LANGUAGE		= prop.getProperty("jco.APOclient.lang");
	        String APO_POOL_CAPACITY = prop.getProperty("jco.destination.pool_capacity");
	        String APO_PEAK_LIMIT	= prop.getProperty("jco.destination.peak_limit");
	        String APO_R3_NAME		= prop.getProperty("jco.APOclient.r3name");	       
	        String APO_GROUP		= prop.getProperty("jco.APOclient.group");
	        
	        
	        APO_properties.setProperty("ACTION", "CREATE");
	        APO_properties.setProperty(DestinationDataProvider.JCO_DEST ,APO_SERVER);
	        APO_properties.setProperty(DestinationDataProvider.JCO_ASHOST, APO_HOST_NAME);
	        APO_properties.setProperty(DestinationDataProvider.JCO_MSHOST, APO_HOST_NAME);
	        APO_properties.setProperty(DestinationDataProvider.JCO_SYSNR,  APO_SYSTEM_NR);
	        APO_properties.setProperty(DestinationDataProvider.JCO_CLIENT, APO_CLIENT_NO);
	        APO_properties.setProperty(DestinationDataProvider.JCO_USER,   APO_USER_ID);
	        APO_properties.setProperty(DestinationDataProvider.JCO_PASSWD, APO_PASSWORD);
	        APO_properties.setProperty(DestinationDataProvider.JCO_LANG,   APO_LANGUAGE);
	        APO_properties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,APO_POOL_CAPACITY);
	        APO_properties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    APO_PEAK_LIMIT);
	        APO_properties.setProperty(DestinationDataProvider.JCO_R3NAME,    APO_R3_NAME);  
	        APO_properties.setProperty(DestinationDataProvider.JCO_GROUP, APO_GROUP);

			MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
			
			if (!com.sap.conn.jco.ext.Environment.isDestinationDataProviderRegistered()) {
				com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
			}else{
				//System.out.println("##########################isDestinationDataProviderRegistered");
			}
			if(APO_properties==null){
				//System.out.println("##########################BW_properties is null");
			}
			myProvider.changePropertiesForABAP_AS(APO_properties);

		
		}catch(Exception e){			
			e.printStackTrace();	
		}

		try {

	        APO_dest = JCoDestinationManager.getDestination(APO_SERVER);
	        APO_repos = APO_dest.getRepository();
	        
	        APO_dest.ping(); 

	        
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
	
	public JCoFunction getAPOFunction(String functionStr) {
		JCoFunction function = null;
		try {
			function = APO_repos.getFunction(functionStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problem retrieving APO JCO.Function object.");
		}
		if (function == null) {
			throw new RuntimeException("Not possible to receive APO function. ");
		}

		return function;
	}
	/**
	 * Method execute will call a function. The Caller of this function has
	 * already set all required parameters of the function
	 * @throws JCoException 
	 * 
	 */
	
	public void executeAPO(JCoFunction function) throws JCoException {
		// modified by hosung from EACC.
		JCoContext.begin(APO_dest);
		try {
			function.execute(APO_dest);
			//this.log.debug("data: " +function.toXML());
		
			//LLog.debug.write(function.toXML());
//			function.writeXML("C:\\function.xml");
 
		//} catch (JCoException e) {
		//	this.log.debug("Error on SAP JCO : "+function.toXML() + e);
		//	e.printStackTrace();
			
		} finally {
			this.log.debug("APO RFC END");
			JCoContext.end(APO_dest);
		}
	}
}
