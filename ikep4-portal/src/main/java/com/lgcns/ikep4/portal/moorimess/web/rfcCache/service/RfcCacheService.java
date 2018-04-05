package com.lgcns.ikep4.portal.moorimess.web.rfcCache.service;

import java.util.HashMap;
import java.util.Map;

public interface RfcCacheService {

	public Map<?, ?> callRfcFunctionSetCache(String functionName, HashMap<String, Object> params, HashMap<?, ?> tableParams);
}
