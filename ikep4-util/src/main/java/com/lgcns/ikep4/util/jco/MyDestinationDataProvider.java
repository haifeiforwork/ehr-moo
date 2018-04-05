package com.lgcns.ikep4.util.jco;

import java.util.Hashtable;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

/*
 * Provides the data connection to the SAP system
 * 
 */
public class MyDestinationDataProvider implements DestinationDataProvider {

	
	private DestinationDataEventListener eL;

    private Hashtable<String, Properties> propertiesTab = new Hashtable<String, Properties>();
    
    public Properties getDestinationProperties(String destinationName)
    {
         if(propertiesTab.containsKey(destinationName)){
              return propertiesTab.get(destinationName);
         }
        
        return null;
        //alternatively throw runtime exception
        //throw new RuntimeException("Destination " + destinationName + " is not available");
    }

    public void setDestinationDataEventListener(DestinationDataEventListener eventListener)
    {
        this.eL = eventListener;
    }

    public boolean supportsEvents()
    {
        return true;
    }
    
    void changePropertiesForABAP_AS(Properties pConProps)
    {
    	if(pConProps==null){
    		//System.out.println("##########################Properties pConProps is null");
    	}else{
	        if(pConProps.getProperty("ACTION").equalsIgnoreCase("CREATE")){
	             propertiesTab.put(pConProps.getProperty("jco.client.dest"), pConProps);
	             if(eL==null){
	            	// System.out.println("##########################DestinationDataEventListener eL is null");
	             }else{
	            	 eL.updated(pConProps.getProperty("jco.client.dest"));   
	             }
	        }
	        else if(pConProps.getProperty("ACTION").equalsIgnoreCase("DELETE")){
	             propertiesTab.remove(pConProps.getProperty("jco.client.dest"));
	             eL.deleted(pConProps.getProperty("jco.client.dest"));
	        }
    	}
    }
}
