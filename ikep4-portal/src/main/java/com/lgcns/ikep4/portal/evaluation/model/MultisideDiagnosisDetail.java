package com.lgcns.ikep4.portal.evaluation.model;

public class MultisideDiagnosisDetail {

	private String mrgrp;
	private String seqnr;
	private String mroty;
	private String mrobj;
	private String motyp;
	private String mobjd;
	private String mstext;
	private String mdtail;
	private String mrslt;

	public String getMrgrp() {
		return mrgrp;
	}

	public void setMrgrp(String mrgrp) {
		this.mrgrp = mrgrp;
	}

	public String getSeqnr() {
		return seqnr;
	}

	public void setSeqnr(String seqnr) {
		this.seqnr = seqnr;
	}

	public String getMroty() {
		return mroty;
	}

	public void setMroty(String mroty) {
		this.mroty = mroty;
	}

	public String getMrobj() {
		return mrobj;
	}

	public void setMrobj(String mrobj) {
		this.mrobj = mrobj;
	}

	public String getMotyp() {
		return motyp;
	}

	public void setMotyp(String motyp) {
		this.motyp = motyp;
	}

	public String getMobjd() {
		return mobjd;
	}

	public void setMobjd(String mobjd) {
		this.mobjd = mobjd;
	}

	public String getMstext() {
		return mstext;
	}

	public void setMstext(String mstext) {
		this.mstext = mstext;
	}

	public String getMdtail() {
		return mdtail;
	}

	public void setMdtail(String mdtail) {
		this.mdtail = mdtail;
	}

	public String getMrslt() {
		return mrslt;
	}

	public void setMrslt(String mrslt) {
		this.mrslt = mrslt;
	}

	@Override
	public String toString() {
		return "ItDetail [mrgrp=" + mrgrp + ", seqnr=" + seqnr + ", mroty=" + mroty + ", mrobj=" + mrobj
				+ ", motyp=" + motyp + ", mobjd=" + mobjd + ", mstext=" + mstext + ", mdtail=" + mdtail + ", mrslt="
				+ mrslt + "]";
	}

}
