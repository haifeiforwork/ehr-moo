package com.lgcns.ikep4.support.customer.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class BasicInfoReaderSearchCondition extends SearchCondition{

    /**
     *
     */
    private static final long serialVersionUID = 2868884324465935604L;


    private String id;

    private String searchColumn;

    private String searchWord;
    
    private String divCode;

    
    @Override
    public Integer getDefaultPagePerRecord(){
        return 10;
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSearchColumn() {
		return searchColumn;
	}


	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}


	public String getSearchWord() {
		return searchWord;
	}


	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}


	public String getDivCode() {
		return divCode;
	}


	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}
    
    


    
}
