package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.Customer;

public interface CustomerCommonDao extends GenericDao<Customer, String>{
    

    /**
     * 고객 ID를 가져온다.
     * @param customerName
     * @return
     */
    List<Customer> getCustomerId( String customerName);

}
