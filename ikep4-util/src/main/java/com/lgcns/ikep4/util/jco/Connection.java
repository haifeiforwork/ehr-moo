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
public class Connection {

	String SAP_SERVER ="";
    String BW_SERVER ="";
    String APO_SERVER ="";

	private JCoRepository SAP_repos;
	private JCoDestination SAP_dest;
	private Properties SAP_properties;

	private JCoRepository BW_repos;
	private JCoDestination BW_dest;
	private Properties BW_properties;

	private JCoRepository APO_repos;
	private JCoDestination APO_dest;
	private Properties APO_properties;

	protected final Log log = LogFactory.getLog(this.getClass());

	public Connection(HttpServletRequest request) {

		SAP_properties = new Properties();
		BW_properties = new Properties();
		APO_properties = new Properties();

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
			if(SAP_properties==null){
				//System.out.println("##########################SAP_properties is null");
			}
			if(BW_properties==null){
				//System.out.println("##########################BW_properties is null");
			}
			if(APO_properties==null){
				//System.out.println("##########################BW_properties is null");
			}
			myProvider.changePropertiesForABAP_AS(SAP_properties);
			myProvider.changePropertiesForABAP_AS(BW_properties);
			//myProvider.changePropertiesForABAP_AS(APO_properties);


		}catch(Exception e){
			e.printStackTrace();
		}

		try {


			SAP_dest = JCoDestinationManager.getDestination(SAP_SERVER);
			//System.out.println("111111111111111111111111111111111");
	       // System.out.println(SAP_dest.getAttributes());
	        SAP_repos = SAP_dest.getRepository();
	        SAP_dest.ping();

	        BW_dest = JCoDestinationManager.getDestination(BW_SERVER);
	        //System.out.println("22222222222222222222222222222222");
	        //System.out.println(BW_dest.getAttributes());
	        //System.out.println(BW_dest.getRepository());
	        BW_repos = BW_dest.getRepository();
	        BW_dest.ping();

	        //APO_dest = JCoDestinationManager.getDestination(APO_SERVER);
	        //System.out.println("33333333333333333333333333333333");
	        //System.out.println(APO_dest.getRepository());
	        //System.out.println(APO_dest.getAttributes());
	       // APO_repos = APO_dest.getRepository();

	        //APO_dest.ping();


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
	public void executeSAP(JCoFunction function) throws JCoException {
		// modified by hosung from EACC.
		JCoContext.begin(SAP_dest);
		try {
			function.execute(SAP_dest);
			//this.log.debug("data: " +function.toXML());

			//LLog.debug.write(function.toXML());
//			function.writeXML("C:\\function.xml");

		//} catch (JCoException e) {
		//	this.log.debug("Error on SAP JCO : "+function.toXML() + e);
		//	e.printStackTrace();

		} finally {
			this.log.debug("SAP RFC END");
			JCoContext.end(SAP_dest);
		}
	}
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
