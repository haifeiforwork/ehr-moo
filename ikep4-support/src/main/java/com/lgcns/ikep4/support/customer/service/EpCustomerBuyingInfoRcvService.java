package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;

@Transactional
public interface EpCustomerBuyingInfoRcvService extends GenericService<CustomerBuyingInfo, String> {

    
    public List<CustomerBuyingInfo> EpCustomerBuyingRcv(HttpServletRequest request);
    
}
