package com.lgcns.ikep4.servicepack.seamless.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageAdmin;

public interface SeamlessmessageAdminDao extends GenericDao<SeamlessmessageAdmin,String> {
	/**TODO 삭제 Batch 
	 * 
	 */
	public void removeBatch();
}
