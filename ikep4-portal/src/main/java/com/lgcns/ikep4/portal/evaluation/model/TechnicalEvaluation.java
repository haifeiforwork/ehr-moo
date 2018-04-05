package com.lgcns.ikep4.portal.evaluation.model;

public class TechnicalEvaluation {

	private String ayear;
	private String aprse;
	private String otype;
	private String objid;
	private String stext;
	private String dftxt;
	private String bhidx;
	private String seqnr;
	private String mtapr;
	private String mtsco;

	public String getAyear() {
		return ayear;
	}

	public void setAyear(String ayear) {
		this.ayear = ayear;
	}

	public String getAprse() {
		return aprse;
	}

	public void setAprse(String aprse) {
		this.aprse = aprse;
	}

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getStext() {
		return stext;
	}

	public void setStext(String stext) {
		this.stext = stext;
	}

	public String getDftxt() {
		return dftxt;
	}

	public void setDftxt(String dftxt) {
		this.dftxt = dftxt;
	}

	public String getBhidx() {
		return bhidx;
	}

	public void setBhidx(String bhidx) {
		this.bhidx = bhidx;
	}

	public String getSeqnr() {
		return seqnr;
	}

	public void setSeqnr(String seqnr) {
		this.seqnr = seqnr;
	}

	public String getMtapr() {
		return mtapr;
	}

	public void setMtapr(String mtapr) {
		this.mtapr = mtapr;
	}

	public String getMtsco() {
		return mtsco;
	}

	public void setMtsco(String mtsco) {
		this.mtsco = mtsco;
	}

	@Override
	public String toString() {
		return "TechnicalEvaluation [ayear=" + ayear + ", aprse=" + aprse + ", otype=" + otype + ", objid=" + objid
				+ ", stext=" + stext + ", dftxt=" + dftxt + ", bhidx=" + bhidx + ", seqnr=" + seqnr + ", mtapr=" + mtapr
				+ ", mtsco=" + mtsco + "]";
	}

}
