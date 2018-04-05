/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.util;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 쿼리자동빌터툴
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SearchUtil.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class SearchUtil extends BaseObject{
    /**
	 *
	 */
	private static final long serialVersionUID = -4703008230503042293L;
	/**
	 * IKEP4_SP_DICTIONARY
	 */
	protected String orderByClause;
	private Integer startIndex;
	private Integer endIndex;
	private String category;
	private User user;
	private List<String> relationUserList;
	
	
	
	
	/**
	 * IKEP4_SP_DICTIONARY
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * @return  
	 * @param  
	 */
	public SearchUtil() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * @return  
	 * @param example
	 */
	protected SearchUtil(SearchUtil example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * @return  
	 * @param orderByClause
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * @return  String
	 * @param  
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * @return  List<Criteria>
	 * @param  
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * @return  
	 * @param criteria
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * @return  Criteria
	 * @param  
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * @return  Criteria
	 * @param  
	 */
	protected Criteria createCriteriaInternal() {
		return  new Criteria();
	}

	/**
	 * @return  
	 * @param  
	 */
	public void clear() {
		oredCriteria.clear();
	}
	
	
	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getRelationUserList() {
		return relationUserList;
	}

	public void setRelationUserList(List<String> relationUserList) {
		this.relationUserList = relationUserList;
	}
	
	
}