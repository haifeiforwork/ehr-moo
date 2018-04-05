package com.lgcns.ikep4.servicepack.usagetracker.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 쿼리 자동생성툴
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: Criteria.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class Criteria {
	protected List<String> criteriaWithoutValue;

	protected List<Map<String, Object>> criteriaWithSingleValue;

	protected List<Map<String, Object>> criteriaWithListValue;

	protected List<Map<String, Object>> criteriaWithBetweenValue;

	public Criteria() {
		super();
		criteriaWithoutValue = new ArrayList<String>();
		criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
		criteriaWithListValue = new ArrayList<Map<String, Object>>();
		criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
	}

	public boolean isValid() {
		return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0
				|| criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
	}

	public List<String> getCriteriaWithoutValue() {
		return criteriaWithoutValue;
	}

	public List<Map<String, Object>> getCriteriaWithSingleValue() {
		return criteriaWithSingleValue;
	}

	public List<Map<String, Object>> getCriteriaWithListValue() {
		return criteriaWithListValue;
	}

	public List<Map<String, Object>> getCriteriaWithBetweenValue() {
		return criteriaWithBetweenValue;
	}

	public Criteria addCriterion(String condition) {
		if (condition == null) {
			throw new CriteriaException("Value for condition cannot be null");
		}
		criteriaWithoutValue.add(condition);
		return this;
	}

	public Criteria addCriterion(String condition, Object value, String property) {
		if (value == null) {
			throw new CriteriaException("Value for " + property + " cannot be null");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("value", value);
		criteriaWithSingleValue.add(map);
		
		return this;
	}

	public Criteria addCriterion(String condition, List<? extends Object> values, String property) {
		if (values == null || values.size() == 0) {
			throw new CriteriaException("Value list for " + property + " cannot be null or empty");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("values", values);
		criteriaWithListValue.add(map);
		return this;
	}

	public Criteria addCriterion(String condition, Object value1, Object value2, String property) {
		if (value1 == null || value2 == null) {
			throw new CriteriaException("Between values for " + property + " cannot be null");
		}
		List<Object> list = new ArrayList<Object>();
		list.add(value1);
		list.add(value2);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("values", list);
		criteriaWithBetweenValue.add(map);
		return this;
	}

	public Criteria addCriterionForJDBCDate(String condition, Date value, String property) {
		addCriterion(condition, new java.sql.Date(value.getTime()), property);
		return this;
	}

	public Criteria addCriterionForJDBCDate(String condition, List<Date> values, String property) {
		if (values == null || values.size() == 0) {
			throw new CriteriaException("Value list for " + property + " cannot be null or empty");
		}
		List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
		Iterator<Date> iter = values.iterator();
		while (iter.hasNext()) {
			dateList.add(new java.sql.Date(iter.next().getTime()));
		}
		addCriterion(condition, dateList, property);
		return this;
	}

	public Criteria addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
		if (value1 == null || value2 == null) {
			throw new CriteriaException("Between values for " + property + " cannot be null");
		}
		addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
		return this;
	}
}
