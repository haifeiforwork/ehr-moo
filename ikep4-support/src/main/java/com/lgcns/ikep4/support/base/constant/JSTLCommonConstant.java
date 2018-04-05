package com.lgcns.ikep4.support.base.constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;


/**
 * IKEP 전역 상수 클래스를 JSP에서 EL로 사용하기 위해 HashMap에 저장하는 클래스
 * 
 * @author 유승목
 * @version $Id: JSTLCommonConstant.java 16239 2011-08-18 04:07:04Z giljae $
 */
@SuppressWarnings({ "serial", "unchecked" })
public class JSTLCommonConstant extends HashMap {

	private boolean initialised = false;

	private static final Class adaptee = CommonConstant.class;

	public JSTLCommonConstant() {
		Class c = adaptee;
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];
			int modifier = field.getModifiers();
			if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier)) {
				try {
					this.put(field.getName(), field.get(this));
				} catch (IllegalAccessException e) {
				}
			}
		}
		initialised = true;
	}

	public void clear() {
		if (!initialised) {
			super.clear();
		} else {
			throw new UnsupportedOperationException("Cannot modify this map");
		}
	}

	public Object put(Object key, Object value) {
		if (!initialised) {
			return super.put(key, value);
		} else {
			throw new UnsupportedOperationException("Cannot modify this map");
		}
	}

	public void putAll(Map m) {
		if (!initialised) {
			super.putAll(m);
		} else {
			throw new UnsupportedOperationException("Cannot modify this map");
		}
	}

	public Object remove(Object key) {
		if (!initialised) {
			return super.remove(key);
		} else {
			throw new UnsupportedOperationException("Cannot modify this map");
		}
	}
}
