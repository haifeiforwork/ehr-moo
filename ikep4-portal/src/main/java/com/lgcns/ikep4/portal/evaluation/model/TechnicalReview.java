package com.lgcns.ikep4.portal.evaluation.model;

import java.util.List;

public class TechnicalReview {

	private String ayear;
	private String orgeh;
	private String orgtx;
	private String aprse;
	private String aprsenm;
	private String trfgr;
	private String fnsco;
	private String adjsc;
	private String cmqsc;
	private String rvsc1;
	private String rvsc2;
	private String rvwsc;
	private String revwr;
	private String revwrnm;
	private String rvsts;
	private String rvstsx;
	private String srtky;
	private String modif;

	private String button;

	private List<TechnicalReview> reviews;

	public String getAyear() {
		return ayear;
	}

	public void setAyear(String ayear) {
		this.ayear = ayear;
	}

	public String getOrgeh() {
		return orgeh;
	}

	public void setOrgeh(String orgeh) {
		this.orgeh = orgeh;
	}

	public String getOrgtx() {
		return orgtx;
	}

	public void setOrgtx(String orgtx) {
		this.orgtx = orgtx;
	}

	public String getAprse() {
		return aprse;
	}

	public void setAprse(String aprse) {
		this.aprse = aprse;
	}

	public String getAprsenm() {
		return aprsenm;
	}

	public void setAprsenm(String aprsenm) {
		this.aprsenm = aprsenm;
	}

	public String getTrfgr() {
		return trfgr;
	}

	public void setTrfgr(String trfgr) {
		this.trfgr = trfgr;
	}

	public String getFnsco() {
		return fnsco;
	}

	public void setFnsco(String fnsco) {
		this.fnsco = fnsco;
	}

	public String getAdjsc() {
		return adjsc;
	}

	public void setAdjsc(String adjsc) {
		this.adjsc = adjsc;
	}

	public String getCmqsc() {
		return cmqsc;
	}

	public void setCmqsc(String cmqsc) {
		this.cmqsc = cmqsc;
	}

	public String getRvsc1() {
		return rvsc1;
	}

	public void setRvsc1(String rvsc1) {
		this.rvsc1 = rvsc1;
	}

	public String getRvsc2() {
		return rvsc2;
	}

	public void setRvsc2(String rvsc2) {
		this.rvsc2 = rvsc2;
	}

	public String getRvwsc() {
		return rvwsc;
	}

	public void setRvwsc(String rvwsc) {
		this.rvwsc = rvwsc;
	}

	public String getRevwr() {
		return revwr;
	}

	public void setRevwr(String revwr) {
		this.revwr = revwr;
	}

	public String getRevwrnm() {
		return revwrnm;
	}

	public void setRevwrnm(String revwrnm) {
		this.revwrnm = revwrnm;
	}

	public String getRvsts() {
		return rvsts;
	}

	public void setRvsts(String rvsts) {
		this.rvsts = rvsts;
	}

	public String getRvstsx() {
		return rvstsx;
	}

	public void setRvstsx(String rvstsx) {
		this.rvstsx = rvstsx;
	}

	public String getSrtky() {
		return srtky;
	}

	public void setSrtky(String srtky) {
		this.srtky = srtky;
	}

	public String getModif() {
		return modif;
	}

	public void setModif(String modif) {
		this.modif = modif;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public List<TechnicalReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<TechnicalReview> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "TechnicalReview [ayear=" + ayear + ", orgeh=" + orgeh + ", orgtx=" + orgtx + ", aprse=" + aprse
				+ ", aprsenm=" + aprsenm + ", trfgr=" + trfgr + ", fnsco=" + fnsco + ", adjsc=" + adjsc + ", cmqsc="
				+ cmqsc + ", rvsc1=" + rvsc1 + ", rvsc2=" + rvsc2 + ", rvwsc=" + rvwsc + ", revwr=" + revwr
				+ ", revwrnm=" + revwrnm + ", rvsts=" + rvsts + ", rvstsx=" + rvstsx + ", srtky=" + srtky + ", modif="
				+ modif + ", button=" + button + ", reviews=" + reviews + "]";
	}

}
