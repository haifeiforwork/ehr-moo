/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

/**
 * 
 * 사용량통계 사용자 rank
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtLoginRank.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtLoginRank extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -6946590163204483968L;

	/**
    USER_ID[사용자 ID]
     */
    private String userId;

    /**
    MONTH1[1월 누적 로그인]
     */
    private Integer month1;

    /**
    MONTH2[2월 누적 로그인]
     */
    private Integer month2;

    /**
    MONTH3[3월 누적 로그인]
     */
    private Integer month3;

    /**
    MONTH4[4월 누적 로그인]
     */
    private Integer month4;

    /**
    MONTH5[5월 누적 로그인]
     */
    private Integer month5;

    /**
    MONTH6[6월 누적 로그인]
     */
    private Integer month6;

    /**
    MONTH7[7월 누적 로그인]
     */
    private Integer month7;

    /**
    MONTH8[8월 누적 로그인]
     */
    private Integer month8;

    /**
    MONTH9[9월 누적 로그인]
     */
    private Integer month9;

    /**
    MONTH10[10월 누적 로그인]
     */
    private Integer month10;

    /**
    MONTH11[11월 누적 로그인]
     */
    private Integer month11;

    /**
    MONTH12[12월 누적 로그인]
     */
    private Integer month12;

    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getMonth1() {
        return month1;
    }

    public void setMonth1(Integer month1) {
        this.month1 = month1;
    }

    public Integer getMonth2() {
        return month2;
    }

    public void setMonth2(Integer month2) {
        this.month2 = month2;
    }

    public Integer getMonth3() {
        return month3;
    }

    public void setMonth3(Integer month3) {
        this.month3 = month3;
    }

    public Integer getMonth4() {
        return month4;
    }

    public void setMonth4(Integer month4) {
        this.month4 = month4;
    }

    public Integer getMonth5() {
        return month5;
    }

    public void setMonth5(Integer month5) {
        this.month5 = month5;
    }

    public Integer getMonth6() {
        return month6;
    }

    public void setMonth6(Integer month6) {
        this.month6 = month6;
    }

    public Integer getMonth7() {
        return month7;
    }

    public void setMonth7(Integer month7) {
        this.month7 = month7;
    }

    public Integer getMonth8() {
        return month8;
    }

    public void setMonth8(Integer month8) {
        this.month8 = month8;
    }

    public Integer getMonth9() {
        return month9;
    }

    public void setMonth9(Integer month9) {
        this.month9 = month9;
    }

    public Integer getMonth10() {
        return month10;
    }

    public void setMonth10(Integer month10) {
        this.month10 = month10;
    }

    public Integer getMonth11() {
        return month11;
    }

    public void setMonth11(Integer month11) {
        this.month11 = month11;
    }

    public Integer getMonth12() {
        return month12;
    }

    public void setMonth12(Integer month12) {
        this.month12 = month12;
    }
}