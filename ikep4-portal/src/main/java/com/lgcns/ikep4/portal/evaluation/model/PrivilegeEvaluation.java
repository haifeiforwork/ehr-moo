package com.lgcns.ikep4.portal.evaluation.model;

public class PrivilegeEvaluation {

	private String zyear;
	private String pernr;
	private String otype;
	private String objid;
	private String stext;
	private String dftxt;
	private String bhidx;
	private String seqnr;
	private String nxgrd;
	private String mark;

	public String getZyear() {
		return zyear;
	}

	public void setZyear(String zyear) {
		this.zyear = zyear;
	}

	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
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

	public String getNxgrd() {
		return nxgrd;
	}

	public void setNxgrd(String nxgrd) {
		this.nxgrd = nxgrd;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "PrivilegeEvaluation [zyear=" + zyear + ", pernr=" + pernr + ", otype=" + otype + ", objid=" + objid
				+ ", stext=" + stext + ", dftxt=" + dftxt + ", bhidx=" + bhidx + ", seqnr=" + seqnr + ", nxgrd=" + nxgrd
				+ ", mark=" + mark + "]";
	}

}
