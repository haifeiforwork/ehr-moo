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
public class BwConnection {
	
    String BW_SERVER ="";
    
	
	private JCoRepository BW_repos;
	private JCoDestination BW_dest;
	private Properties BW_properties;
	
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	public BwConnection(HttpServletRequest request) {
		
		BW_properties = new Properties();
		
		try{
			
			Properties prop  = PropertyLoader.loadProperties("/configuration/sap-conf.properties");

			
	        
	        BW_SERVER = prop.getProperty("jco.BWclient.destination");
	        
			String BW_HOST_NAME	= prop.getProperty("jco.BWclient.mshost");
			String BW_SYSTEM_NR	= prop.getProperty("jco.BWclient.sysnr");
	        String BW_CLIENT_NO	= prop.getProperty("jco.BWclient.client");			
	        String BW_USER_ID		= prop.getProperty("jco.BWclient.user");
	        String BW_PASSWORD		= prop.getProperty("jco.BWclient.passwd");	        
	        String BW_LANGUAGE		= prop.getProperty("jco.BWclient.lang");
	        String BW_POOL_CAPACITY = prop.getProperty("jco.destination.pool_capacity");
	        String BW_PEAK_LIMIT	= prop.getProperty("jco.destination.peak_limit");
	        String BW_R3_NAME		= prop.getProperty("jco.BWclient.r3name");	       
	        String BW_GROUP		= prop.getProperty("jco.BWclient.group");
	        
	        BW_properties.setProperty("ACTION", "CREATE");
	        BW_properties.setProperty(DestinationDataProvider.JCO_DEST ,BW_SERVER);
	        BW_properties.setProperty(DestinationDataProvider.JCO_ASHOST, BW_HOST_NAME);
	        BW_properties.setProperty(DestinationDataProvider.JCO_MSHOST, BW_HOST_NAME);
	        BW_properties.setProperty(DestinationDataProvider.JCO_SYSNR,  BW_SYSTEM_NR);
	        BW_properties.setProperty(DestinationDataProvider.JCO_CLIENT, BW_CLIENT_NO);
	        BW_properties.setProperty(DestinationDataProvider.JCO_USER,   BW_USER_ID);
	        BW_properties.setProperty(DestinationDataProvider.JCO_PASSWD, BW_PASSWORD);
	        BW_properties.setProperty(DestinationDataProvider.JCO_LANG,   BW_LANGUAGE);
	        BW_properties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,BW_POOL_CAPACITY);
	        BW_properties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    BW_PEAK_LIMIT);
	        BW_properties.setProperty(DestinationDataProvider.JCO_R3NAME,    BW_R3_NAME);  
	        BW_properties.setProperty(DestinationDataProvider.JCO_GROUP, BW_GROUP);
	        

			MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
			
			if (!com.sap.conn.jco.ext.Environment.isDestinationDataProviderRegistered()) {
				com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
			}else{
				//System.out.println("##########################isDestinationDataProviderRegistered");
			}
			if(BW_properties==null){
				//System.out.println("##########################BW_properties is null");
			}
			myProvider.changePropertiesForABAP_AS(BW_properties);

		
		}catch(Exception e){			
			e.printStackTrace();	
		}

		try {

			
	        BW_dest = JCoDestinationManager.getDestination(BW_SERVER);
	        BW_repos = BW_dest.getRepository();
	        BW_dest.ping();      
	        

	        
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
	public JCoFunction getBWFunction(String functionStr) {
		JCoFunction function = null;
		try {
			function = BW_repos.getFunction(functionStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problem retrieving BW JCO.Function object.");
		}
		if (function == null) {
			throw new RuntimeException("Not possible to receive BW function. ");
		}

		return function;
	}
	
	/**
	 * Method execute will call a function. The Caller of this function has
	 * already set all required parameters of the function
	 * @throws JCoException 
	 * 
	 */
	public void executeBW(JCoFunction function) throws JCoException {
		// modified by hosung from EACC.
		JCoContext.begin(BW_dest);
		try {
			function.execute(BW_dest);
			//this.log.debug("data: " +function.toXML());
		
			//LLog.debug.write(function.toXML());
//			function.writeXML("C:\\function.xml");
 
		//} catch (JCoException e) {
		//	this.log.debug("Error on SAP JCO : "+function.toXML() + e);
		//	e.printStackTrace();
			
		} finally {
			this.log.debug("BW RFC END");
			JCoContext.end(BW_dest);
		}
	}
	
}
