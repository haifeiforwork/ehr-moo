package com.lgcns.ikep4.util.web;

import org.apache.commons.beanutils.PropertyUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class IconSelector extends BaseObject {  
	private static final long serialVersionUID = 4019326899615740609L;

	private String field;
	
	private Object compare; 
	
	private String icon;
	
	private Type type;
	
	private enum Type {
		EQUALS,
		NOT_EQUALS 
	}
	
	public IconSelector(Type type, String icon, String field, Object compare) {
		super();
		this.icon = icon;
		this.type = type;
		this.field = field;
		this.compare = compare;
	}
	
	public static IconSelector equals(String icon, String field, Object compare) {
		return new IconSelector(Type.EQUALS, icon, field, compare); 
	}
	
	public String execute(Object object) {
		String left = null;
		String result = null;
		
		try {
			left = String.valueOf(PropertyUtils.getProperty(object, field)); 
			
			if(this.type.equals(Type.EQUALS)) {  
				result = left.equals(String.valueOf(compare)) ? this.icon : null; 
				System.out.println(result + " == " + left + " equals " +  compare);
				return result;
				
			} else if(this.type.equals(Type.NOT_EQUALS)) { 
				result = left.equals(compare) ?  null : this.icon;  
				
				return result;
			}  
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		throw new RuntimeException();
	}
}
