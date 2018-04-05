package com.lgcns.ikep4.portal.util;

public class LookupUtil {
	public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
		E result;
	    try {
	        result = Enum.valueOf(e, id);
	    } catch (IllegalArgumentException iae) {
	    	throw new RuntimeException("Invalid value for enum " + e.getSimpleName() + ": " + id);
	    }

	    return result;
	}
}
