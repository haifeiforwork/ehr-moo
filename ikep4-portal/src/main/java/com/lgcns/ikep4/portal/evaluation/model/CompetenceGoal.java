package com.lgcns.ikep4.portal.evaluation.model;

import java.util.List;

public class CompetenceGoal {

	private String ayear;
	private String aprse;
	private String seqno;
	private String qitem;
	private String stext;
	private String dftxt;
	private String bhidx;
	private String apgrd1;
	private String apsco1;
	private String apgrd2;
	private String apsco2;
	private String ptsum;

	private String rjtext;

	private String button;

	private List<CompetenceGoal> goals;
	private List<CompetenceGoal> cgoals;
	private List<CompetenceGoal> sgoals;

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

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getQitem() {
		return qitem;
	}

	public void setQitem(String qitem) {
		this.qitem = qitem;
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

	public String getApgrd1() {
		return apgrd1;
	}

	public void setApgrd1(String apgrd1) {
		this.apgrd1 = apgrd1;
	}

	public String getApsco1() {
		return apsco1;
	}

	public void setApsco1(String apsco1) {
		this.apsco1 = apsco1;
	}

	public String getApgrd2() {
		return apgrd2;
	}

	public void setApgrd2(String apgrd2) {
		this.apgrd2 = apgrd2;
	}

	public String getApsco2() {
		return apsco2;
	}

	public void setApsco2(String apsco2) {
		this.apsco2 = apsco2;
	}

	public String getPtsum() {
		return ptsum;
	}

	public void setPtsum(String ptsum) {
		this.ptsum = ptsum;
	}

	public String getRjtext() {
		return rjtext;
	}

	public void setRjtext(String rjtext) {
		this.rjtext = rjtext;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public List<CompetenceGoal> getGoals() {
		return goals;
	}

	public void setGoals(List<CompetenceGoal> goals) {
		this.goals = goals;
	}

	public List<CompetenceGoal> getCgoals() {
		return cgoals;
	}

	public void setCgoals(List<CompetenceGoal> cgoals) {
		this.cgoals = cgoals;
	}

	public List<CompetenceGoal> getSgoals() {
		return sgoals;
	}

	public void setSgoals(List<CompetenceGoal> sgoals) {
		this.sgoals = sgoals;
	}

	@Override
	public String toString() {
		return "CompetenceGoal [ayear=" + ayear + ", aprse=" + aprse + ", seqno=" + seqno + ", qitem=" + qitem
				+ ", stext=" + stext + ", dftxt=" + dftxt + ", bhidx=" + bhidx + ", apgrd1=" + apgrd1 + ", apsco1="
				+ apsco1 + ", apgrd2=" + apgrd2 + ", apsco2=" + apsco2 + ", ptsum=" + ptsum + ", rjtext=" + rjtext
				+ ", button=" + button + "]";
	}

}
