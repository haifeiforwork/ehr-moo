package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.util.model.VendorDetail;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("epVendorRcv")
@Transactional
public class EpVendorDataRcvRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	

	/**
	 * 승인 요청 내역 조회
	 */
    public List<VendorDetail> EpVendorDataRcvRFC(String name, HttpServletRequest request )  {
    	Connection connect = null;
    	List<VendorDetail> vendorDetailList = new ArrayList<VendorDetail>();
		try {
			
		connect = new Connection(request);	
		JCoFunction function = connect.getSAPFunction("ZEP_BASICINFO_VENDOR");
		function.getImportParameterList().setValue("L_NAME", name);
		connect.executeSAP(function);
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("ZLES1040_EP");     
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
        		VendorDetail vendorDetailData = new VendorDetail();
    			vendorDetailData.setSapId(tb.getString("LIFNR"));
    			vendorDetailData.setPartnerName(tb.getString("NAME1"));
    			vendorDetailData.setCategory(tb.getString("J_1KFTBUS"));
    			vendorDetailData.setSector(tb.getString("J_1KFTIND"));
    			vendorDetailData.setZipNo(tb.getString("PSTLZ"));
    			vendorDetailData.setAddress(tb.getString("STRAS"));
    			vendorDetailData.setMainPhone(tb.getString("TELF1"));
    			vendorDetailData.setEmail(tb.getString("SMTP_ADDR"));
    			vendorDetailData.setCeoName(tb.getString("STCD3"));
    			vendorDetailData.setBusinessNo(tb.getString("STCD2"));
    			vendorDetailData.setCorporationNo(tb.getString("STCD1"));
    			vendorDetailData.setKeyMan(tb.getString("VERKF"));
    			vendorDetailData.setContacts(tb.getString("TELF2"));
    			vendorDetailData.setFax(tb.getString("TELFX"));
    			vendorDetailList.add(vendorDetailData);
        	}
		}
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
			
		return vendorDetailList;
    }
    
}

