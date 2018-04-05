package com.lgcns.ikep4.util.model;

import java.util.Date;
import java.util.Properties;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.PropertyLoader;

/**
 * 
 * 사용자 정보 model
 *
 * @author 
 * @version 
 */
public class TestTable extends BaseObject {

	
	private String FR_DT;   
	
	private String TO_DT;

	public String getFR_DT() {
		return FR_DT;
	}

	public void setFR_DT(String fR_DT) {
		FR_DT = fR_DT;
	}

	public String getTO_DT() {
		return TO_DT;
	}

	public void setTO_DT(String tO_DT) {
		TO_DT = tO_DT;
	} 
	
	
	

}
