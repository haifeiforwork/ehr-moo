/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.util;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.servicepack.usagetracker.model.UtBaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 쿼리자동빌터툴
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SearchUtil.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SearchUtil extends UtBaseObject{
	/**
	 *
	 */
	private static final long serialVersionUID = 1916841494978810752L;
	protected String orderByClause;
	private Integer startIndex;
	private Integer endIndex;
	private String category;
	private User user;
	private int viewOption;
	
	
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

	public int getViewOption() {
		return viewOption;
	}

	public void setViewOption(int viewOption) {
		this.viewOption = viewOption;
	}
}