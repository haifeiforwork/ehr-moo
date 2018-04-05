package com.lgcns.ikep4.support.partner.search;

import java.util.List;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 
 * 게시글 검색조건 모델 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class PartnerQualityClaimSellSearchCondition extends SearchCondition {

    /**
     *
     */
    private static final long serialVersionUID = -4730893200422081171L;

    /** 게시판 ID. */
    private String boardId;

    /** 검색 컬럼. */
    private String searchColumn;

    /** 검색어. */
    private String searchWord;

    /** 보기 모드 (리스트, 요약, 간단, 갤러리). */
    private String viewMode;

    /** 레이아웃 (디폴트, 2Frame). */
    private String layoutType = "0";

    /** 레이아웃 (디폴트, 2Frame). */
    private Boolean docPopup = Boolean.FALSE;

    /** 관리자 여부. */
    private Boolean admin;

    /** 로그인 한 사용자 ID. */
    private String userId;
    
    private String searchPurpose;
    
    private List<String> rolePurposeList;
    
    private String searchYear;
    
    private String searchMonth;
    
    @Override
    public Integer getDefaultPagePerRecord(){
        return 20;
    }
    
    


    /**
     * Gets the board id.
     *
     * @return the board id
     */
    public String getBoardId() {
        return this.boardId;
    }

    /**
     * Sets the board id.
     *
     * @param boardId the new board id
     */
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    /**
     * Gets the search column.
     *
     * @return the search column
     */
    public String getSearchColumn() {
        return this.searchColumn;
    }

    /**
     * Sets the search column.
     *
     * @param searchColumn the new search column
     */
    public void setSearchColumn(String searchColumn) {
        this.searchColumn = searchColumn;
    }

    /**
     * Gets the search word.
     *
     * @return the search word
     */
    public String getSearchWord() {
        return this.searchWord;
    }

    /**
     * Sets the search word.
     *
     * @param searchWord the new search word
     */
    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    /**
     * Gets the view mode.
     *
     * @return the view mode
     */
    public String getViewMode() {
        return this.viewMode;
    }

    /**
     * Sets the view mode.
     *
     * @param viewMode the new view mode
     */
    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    /**
     * Gets the layout type.
     *
     * @return the layout type
     */
    public String getLayoutType() {
        return this.layoutType;
    }

    /**
     * Sets the layout type.
     *
     * @param layoutType the new layout type
     */
    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    /**
     * Gets the doc popup.
     *
     * @return the doc popup
     */
    public Boolean getDocPopup() {
        return this.docPopup;
    }

    /**
     * Sets the doc popup.
     *
     * @param docPopup the new doc popup
     */
    public void setDocPopup(Boolean docPopup) {
        this.docPopup = docPopup;
    }

    /**
     * Gets the admin.
     *
     * @return the admin
     */
    public Boolean getAdmin() {
        return this.admin;
    }

    /**
     * Sets the admin.
     *
     * @param admin the new admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }




	public String getSearchPurpose() {
		return searchPurpose;
	}




	public void setSearchPurpose(String searchPurpose) {
		this.searchPurpose = searchPurpose;
	}




	public List<String> getRolePurposeList() {
		return rolePurposeList;
	}




	public void setRolePurposeList(List<String> rolePurposeList) {
		this.rolePurposeList = rolePurposeList;
	}




	public String getSearchYear() {
		return searchYear;
	}




	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}




	public String getSearchMonth() {
		return searchMonth;
	}




	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
	}
    
    
}
