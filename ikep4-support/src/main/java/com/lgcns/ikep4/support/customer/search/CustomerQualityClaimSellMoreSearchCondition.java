
package com.lgcns.ikep4.support.customer.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 댓글 검색조건 모델 클래스
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class CustomerQualityClaimSellMoreSearchCondition extends SearchCondition {

    /**
     *
     */
    private static final long serialVersionUID = 4477255360301680988L;

    /** 게시물 ID */
    private String itemId;

    /** 메뉴 타입 */
    private String itemType;

    public String getItemId() {
        return itemId;
    }

    public void setItemId( String itemId ) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType( String itemType ) {
        this.itemType = itemType;
    }

}
