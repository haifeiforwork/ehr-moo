package com.lgcns.ikep4.util.model;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.web.SearchCondition;


public final class ModelBeanUtil {
	
	private ModelBeanUtil() {
		throw new AssertionError();
	}

	public final static void bindRegisterInfo(BaseObject baseObject, String id, String name) {
		try {
			for (Method method : baseObject.getClass().getMethods()) {
				if (method.getName().equals("setRegisterId")) {
					PropertyUtils.setProperty(baseObject, "registerId", id);
				}

				if (method.getName().equals("setRegisterName")) {
					PropertyUtils.setProperty(baseObject, "registerName", name);
				}

				if (method.getName().equals("setUpdaterId")) {
					PropertyUtils.setProperty(baseObject, "updaterId", id);
				}

				if (method.getName().equals("setUpdaterName")) {
					PropertyUtils.setProperty(baseObject, "updaterName", name);
				}

				if (method.getReturnType().equals(List.class)) {

					@SuppressWarnings("rawtypes")
					List baseObjectList = (List) MethodUtils.invokeMethod(baseObject, method.getName(), null);

					if (baseObjectList != null) {
						for (Object object : baseObjectList) {

							for (Method nestedMethod : object.getClass().getMethods()) {
								if (nestedMethod.getName().equals("setRegisterId")) {
									PropertyUtils.setProperty(object, "registerId", id);
								}

								if (nestedMethod.getName().equals("setRegisterName")) {
									PropertyUtils.setProperty(object, "registerName", name);
								}

								if (nestedMethod.getName().equals("setUpdaterId")) {
									PropertyUtils.setProperty(object, "updaterId", id);
								}

								if (nestedMethod.getName().equals("setUpdaterName")) {
									PropertyUtils.setProperty(object, "updaterName", name);
								}
							}

						}
					}
				}
			}

		} catch (Exception exception) {
			throw new IKEP4ApplicationException(exception.getMessage(), exception);
		}
	}

	public final static void bindUpdaterInfo(BaseObject baseObject, String id, String name) {

		try {

			for (Method method : baseObject.getClass().getMethods()) {
				if (method.getName().equals("setUpdaterId")) {
					PropertyUtils.setProperty(baseObject, "updaterId", id);
				}

				if (method.getName().equals("setUpdaterName")) {
					PropertyUtils.setProperty(baseObject, "updaterName", name);
				}

				if (method.getReturnType().equals(List.class)) {

					@SuppressWarnings("rawtypes")
					List baseObjectList = (List) MethodUtils.invokeMethod(baseObject, method.getName(), null);
					if (baseObjectList != null) {
						for (Object object : baseObjectList) {

							for (Method nestedMethod : object.getClass().getMethods()) {
								if (nestedMethod.getName().equals("setUpdaterId")) {
									PropertyUtils.setProperty(object, "updaterId", id);
								}

								if (nestedMethod.getName().equals("setUpdaterName")) {
									PropertyUtils.setProperty(object, "updaterName", name);
								}
							}

						}
					}
				}
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}


	public static String addTreeRequiredField(Object object, String idFieldName, String parentFieldName, String nameFieldName) {
		String genrenatedJsonString = "";
		try {

			//String code = String.valueOf(PropertyUtils.getProperty(object, idFieldName));
			//String parent = String.valueOf(PropertyUtils.getProperty(object, parentFieldName));
			//String name = String.valueOf(PropertyUtils.getProperty(object, nameFieldName));

			ObjectMapper mapper = new ObjectMapper();

			genrenatedJsonString = mapper.writeValueAsString(object);

			genrenatedJsonString = StringUtils.replace(genrenatedJsonString, "\"" + idFieldName + "\":", "\"code\":");
			genrenatedJsonString = StringUtils.replace(genrenatedJsonString, "\"" + parentFieldName + "\":", "\"parent\":");
			genrenatedJsonString = StringUtils.replace(genrenatedJsonString, "\"" + nameFieldName + "\":", "\"name\":");


		} catch (Exception ex) {
			throw new IKEP4ApplicationException(ex.getMessage(), ex);
		}

		return genrenatedJsonString;
	}


	public static String makeSearchConditionString(SearchCondition searchCondition, String... attributes) {
		String searchConditionString = null;

		StringBuffer buffer = new StringBuffer();


		try {
			for(String attribute : Arrays.asList(attributes)) {

				Object result = MethodUtils.invokeExactMethod(searchCondition, "get" + StringUtils.upperCase(StringUtils.left(attribute, 1)) + StringUtils.substring(attribute, 1)  , null);


				if(result == null) {
					continue;
				}

				buffer.append(attribute);
				buffer.append("^");

				if(result instanceof Date ) {
					buffer.append(DateFormatUtils.format((Date)result, "yyyyMMddHHmmss"));

				} else {
					if(!StringUtils.isEmpty(String.valueOf(result))) {
						buffer.append(String.valueOf(result));
					}
				}


				buffer.append("~");
			}

			if(buffer.length() > 1) {
				searchConditionString = buffer.delete(buffer.length() - 1, buffer.length()).toString();
			}

		} catch(Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return searchConditionString;
	}

	public static void makeSearchCondition(String searchConditionString, SearchCondition searchCondition) {

		List<String> paramSet = Arrays.asList(StringUtils.split(searchConditionString, "~"));

		String key   = null;
		String value = null;
		String methodName = null;
		Date dateValue = null;

		for(String param : paramSet) {

			if(!(StringUtils.split(param, "^").length == 2)) {
				continue;
			}
			key   = StringUtils.split(param, "^")[0];
			value = StringUtils.split(param, "^")[1];
			methodName = "get" + StringUtils.upperCase(StringUtils.left(key, 1)) + StringUtils.substring(key, 1);

			try {
				for(Method method : searchCondition.getClass().getMethods()) {

					if(methodName.equals(method.getName())) {
						if(method.getReturnType().getSimpleName().equals("Date")) {
							dateValue = DateUtils.parseDate(value, new String[]{"yyyyMMddHHmmss"});
							BeanUtils.setProperty(searchCondition, key, dateValue);
						} else {
							BeanUtils.setProperty(searchCondition, key, value);
						}
						break;
					}

				}

			} catch(Exception ex) {
				throw new IKEP4ApplicationException("", ex);

			}
		}
	}

}
