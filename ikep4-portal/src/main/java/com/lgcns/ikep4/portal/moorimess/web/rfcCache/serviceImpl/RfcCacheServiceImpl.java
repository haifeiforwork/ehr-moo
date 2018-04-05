package com.lgcns.ikep4.portal.moorimess.web.rfcCache.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.lgcns.ikep4.portal.moorimess.web.rfcCache.service.RfcCacheService;
import com.lgcns.ikep4.util.jco.WebESSRcvRFC;

@Service(value = "rfcCacheService")
public class RfcCacheServiceImpl implements RfcCacheService{
	
	@Autowired
	private WebESSRcvRFC webEssRcv;

	@Cacheable(cacheName = "rfcCache")
	public Map<?, ?> callRfcFunctionSetCache(String functionName, HashMap<String, Object> params, HashMap<?, ?> tableParams) {
		return webEssRcv.callRfcFunction(functionName, params, tableParams, null);
	}	
}
