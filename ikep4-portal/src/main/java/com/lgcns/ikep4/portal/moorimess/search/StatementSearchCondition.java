package com.lgcns.ikep4.portal.moorimess.search;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 
 * 전표결제 검색조건
 *
 * @author 
 * @version $Id$
 */
public class StatementSearchCondition extends SearchCondition {
	
    /** 무림P&P */
	private String ip100;
	
	/** 무림페이퍼 */
	private String is100;

	/** 무림에스피 */
	private String im100;
	
	/** 시작년월 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private String ifspmon;
	
	/** 종료년월 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private String itspmon;
	
	/** 계정과목 */
	private String iracct;
	
	/** 사용자 마스터 레코드의 사용자 이름 */
	private String iuname;
	
	/** 네오넷 주요 예산계정 */
	private String ineonetacc;
	
	/** 검색 조건 분기 */
	private String quarter;
	
	/** 전표상태 -전표승인 */
	private String ireqs;
	
	/** 전표상태 -전표미요청 */
	private String ireqr;
	
	/** 전표상태 -전표반려*/
	private String ireqd;
	
	/** 전표상태 -전표미생성 */
	private String ireqn;
	
	/** 카드승인일-FROM */
	private String iconffdate;
	
	/** 카드승인일-TO */
	private String iconftdate;
	
	/** 승인/반려일-FROM */
	private String iapprfdate;
	
	/** 승인/반려일-TO */
	private String iapprtdate;
	
	/** 최초 로딩 여부 */
	private String isInit = "yes";
	
	/** 카드번호 */
	private String cardno;
	
	/** sort컬럼 */
	private String ifname;
	
	/** sort방식 */
	private String istate;
	
	/** 검색 조건 기간 */
	private String period;
	
	private String kostl;
	
	private String zzkostl;
		
	public String getIp100() {
		return ip100;
	}

	public void setIp100(String ip100) {
		this.ip100 = ip100;
	}

	public String getIs100() {
		return is100;
	}

	public void setIs100(String is100) {
		this.is100 = is100;
	}

	public String getIm100() {
		return im100;
	}

	public void setIm100(String im100) {
		this.im100 = im100;
	}

	public String getIfspmon() {
		return ifspmon;
	}

	public void setIfspmon(String ifspmon) {
		this.ifspmon = ifspmon;
	}

	public String getItspmon() {
		return itspmon;
	}

	public void setItspmon(String itspmon) {
		this.itspmon = itspmon;
	}

	public String getIracct() {
		return iracct;
	}

	public void setIracct(String iracct) {
		this.iracct = iracct;
	}

	public String getIuname() {
		return iuname;
	}

	public void setIuname(String iuname) {
		this.iuname = iuname;
	}

	public String getIneonetacc() {
		return ineonetacc;
	}

	public void setIneonetacc(String ineonetacc) {
		this.ineonetacc = ineonetacc;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getIreqs() {
		return ireqs;
	}

	public void setIreqs(String ireqs) {
		this.ireqs = ireqs;
	}

	public String getIreqr() {
		return ireqr;
	}

	public void setIreqr(String ireqr) {
		this.ireqr = ireqr;
	}

	public String getIreqd() {
		return ireqd;
	}

	public void setIreqd(String ireqd) {
		this.ireqd = ireqd;
	}

	public String getIreqn() {
		return ireqn;
	}

	public void setIreqn(String ireqn) {
		this.ireqn = ireqn;
	}

	public String getIconffdate() {
		return iconffdate;
	}

	public void setIconffdate(String iconffdate) {
		this.iconffdate = iconffdate;
	}

	public String getIconftdate() {
		return iconftdate;
	}

	public void setIconftdate(String iconftdate) {
		this.iconftdate = iconftdate;
	}

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getIfname() {
		return ifname;
	}

	public void setIfname(String ifname) {
		this.ifname = ifname;
	}

	public String getIstate() {
		return istate;
	}

	public void setIstate(String istate) {
		this.istate = istate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getKostl() {
		return kostl;
	}

	public void setKostl(String kostl) {
		this.kostl = kostl;
	}

	public String getIapprfdate() {
		return iapprfdate;
	}

	public void setIapprfdate(String iapprfdate) {
		this.iapprfdate = iapprfdate;
	}

	public String getIapprtdate() {
		return iapprtdate;
	}

	public void setIapprtdate(String iapprtdate) {
		this.iapprtdate = iapprtdate;
	}

	public String getZzkostl() {
		return zzkostl;
	}

	public void setZzkostl(String zzkostl) {
		this.zzkostl = zzkostl;
	}
    
}
