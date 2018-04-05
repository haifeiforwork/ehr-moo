/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

/**
 * Knowledge Monitor KnowledgeMonitorAccumulation model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorAccumulation.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorAccumulation extends KnowledgeMonitorAccumulationChart {

	/**
	 *
	 */
	private static final long serialVersionUID = 4740877693494446409L;

	/**
	 * 공개 항목수 (Social Blog)
	 */
	private int sbPublicDocCount;

	/**
	 * 전체 항목수 (Social Blog)
	 */
	private int sbAllDocCount;

	/**
	 * 공개 항목수 (WorkSpace)
	 */
	private int wsPublicDocCount;

	/**
	 * 전체 항목수 (WorkSpace)
	 */
	private int wsAllDocCount;

	/**
	 * 공개 항목수 (Q&A)
	 */
	private int qaPublicDocCount;

	/**
	 * 전체 항목수 (Q&A)
	 */
	private int qaAllDocCount;

	/**
	 * 공개 항목수 (Coporate Voca)
	 */
	private int cvPublicDocCount;

	/**
	 * 전체 항목수 (Coporate Voca)
	 */
	private int cvAllDocCount;

	/**
	 * 공개 항목수 (Work Manual)
	 */
	private int wmPublicDocCount;

	/**
	 * 전체 항목수 (Work Manual)
	 */
	private int wmAllDocCount;

	/**
	 * 공개 항목수 (Cafe)
	 */
	private int cfPublicDocCount;

	/**
	 * 전체 항목수 (Cafe)
	 */
	private int cfAllDocCount;

	/**
	 * 공개 항목수 (Forum)
	 */
	private int frPublicDocCount;

	/**
	 * 전체 항목수 (Forum)
	 */
	private int frAllDocCount;

	/**
	 * 공개 항목수 (Ideation)
	 */
	private int idPublicDocCount;

	/**
	 * 전체 항목수 (Ideation)
	 */
	private int idAllDocCount;

	/**
	 * 공개 항목수 (BBS)
	 */
	private int bdPublicDocCount;

	/**
	 * 전체 항목수 (BBS)
	 */
	private int bdAllDocCount;


	/**
	 * @return the sbPublicDocCount
	 */
	public int getSbPublicDocCount() {
		return sbPublicDocCount;
	}

	/**
	 * @param sbPublicDocCount the sbPublicDocCount to set
	 */
	public void setSbPublicDocCount(int sbPublicDocCount) {
		this.sbPublicDocCount = sbPublicDocCount;
	}

	/**
	 * @return the sbAllDocCount
	 */
	public int getSbAllDocCount() {
		return sbAllDocCount;
	}

	/**
	 * @param sbAllDocCount the sbAllDocCount to set
	 */
	public void setSbAllDocCount(int sbAllDocCount) {
		this.sbAllDocCount = sbAllDocCount;
	}

	/**
	 * @return the wsPublicDocCount
	 */
	public int getWsPublicDocCount() {
		return wsPublicDocCount;
	}

	/**
	 * @param wsPublicDocCount the wsPublicDocCount to set
	 */
	public void setWsPublicDocCount(int wsPublicDocCount) {
		this.wsPublicDocCount = wsPublicDocCount;
	}

	/**
	 * @return the wsAllDocCount
	 */
	public int getWsAllDocCount() {
		return wsAllDocCount;
	}

	/**
	 * @param wsAllDocCount the wsAllDocCount to set
	 */
	public void setWsAllDocCount(int wsAllDocCount) {
		this.wsAllDocCount = wsAllDocCount;
	}

	/**
	 * @return the qaPublicDocCount
	 */
	public int getQaPublicDocCount() {
		return qaPublicDocCount;
	}

	/**
	 * @param qaPublicDocCount the qaPublicDocCount to set
	 */
	public void setQaPublicDocCount(int qaPublicDocCount) {
		this.qaPublicDocCount = qaPublicDocCount;
	}

	/**
	 * @return the qaAllDocCount
	 */
	public int getQaAllDocCount() {
		return qaAllDocCount;
	}

	/**
	 * @param qaAllDocCount the qaAllDocCount to set
	 */
	public void setQaAllDocCount(int qaAllDocCount) {
		this.qaAllDocCount = qaAllDocCount;
	}

	/**
	 * @return the cvPublicDocCount
	 */
	public int getCvPublicDocCount() {
		return cvPublicDocCount;
	}

	/**
	 * @param cvPublicDocCount the cvPublicDocCount to set
	 */
	public void setCvPublicDocCount(int cvPublicDocCount) {
		this.cvPublicDocCount = cvPublicDocCount;
	}

	/**
	 * @return the cvAllDocCount
	 */
	public int getCvAllDocCount() {
		return cvAllDocCount;
	}

	/**
	 * @param cvAllDocCount the cvAllDocCount to set
	 */
	public void setCvAllDocCount(int cvAllDocCount) {
		this.cvAllDocCount = cvAllDocCount;
	}

	/**
	 * @return the wmPublicDocCount
	 */
	public int getWmPublicDocCount() {
		return wmPublicDocCount;
	}

	/**
	 * @param wmPublicDocCount the wmPublicDocCount to set
	 */
	public void setWmPublicDocCount(int wmPublicDocCount) {
		this.wmPublicDocCount = wmPublicDocCount;
	}

	/**
	 * @return the wmAllDocCount
	 */
	public int getWmAllDocCount() {
		return wmAllDocCount;
	}

	/**
	 * @param wmAllDocCount the wmAllDocCount to set
	 */
	public void setWmAllDocCount(int wmAllDocCount) {
		this.wmAllDocCount = wmAllDocCount;
	}

	/**
	 * @return the cfPublicDocCount
	 */
	public int getCfPublicDocCount() {
		return cfPublicDocCount;
	}

	/**
	 * @param cfPublicDocCount the cfPublicDocCount to set
	 */
	public void setCfPublicDocCount(int cfPublicDocCount) {
		this.cfPublicDocCount = cfPublicDocCount;
	}

	/**
	 * @return the cfAllDocCount
	 */
	public int getCfAllDocCount() {
		return cfAllDocCount;
	}

	/**
	 * @param cfAllDocCount the cfAllDocCount to set
	 */
	public void setCfAllDocCount(int cfAllDocCount) {
		this.cfAllDocCount = cfAllDocCount;
	}

	/**
	 * @return the frPublicDocCount
	 */
	public int getFrPublicDocCount() {
		return frPublicDocCount;
	}

	/**
	 * @param frPublicDocCount the frPublicDocCount to set
	 */
	public void setFrPublicDocCount(int frPublicDocCount) {
		this.frPublicDocCount = frPublicDocCount;
	}

	/**
	 * @return the frAllDocCount
	 */
	public int getFrAllDocCount() {
		return frAllDocCount;
	}

	/**
	 * @param frAllDocCount the frAllDocCount to set
	 */
	public void setFrAllDocCount(int frAllDocCount) {
		this.frAllDocCount = frAllDocCount;
	}

	/**
	 * @return the idPublicDocCount
	 */
	public int getIdPublicDocCount() {
		return idPublicDocCount;
	}

	/**
	 * @param idPublicDocCount the idPublicDocCount to set
	 */
	public void setIdPublicDocCount(int idPublicDocCount) {
		this.idPublicDocCount = idPublicDocCount;
	}

	/**
	 * @return the idAllDocCount
	 */
	public int getIdAllDocCount() {
		return idAllDocCount;
	}

	/**
	 * @param idAllDocCount the idAllDocCount to set
	 */
	public void setIdAllDocCount(int idAllDocCount) {
		this.idAllDocCount = idAllDocCount;
	}

	/**
	 * @return the bdPublicDocCount
	 */
	public int getBdPublicDocCount() {
		return bdPublicDocCount;
	}

	/**
	 * @param bdPublicDocCount the bdPublicDocCount to set
	 */
	public void setBdPublicDocCount(int bdPublicDocCount) {
		this.bdPublicDocCount = bdPublicDocCount;
	}

	/**
	 * @return the bdAllDocCount
	 */
	public int getBdAllDocCount() {
		return bdAllDocCount;
	}

	/**
	 * @param bdAllDocCount the bdAllDocCount to set
	 */
	public void setBdAllDocCount(int bdAllDocCount) {
		this.bdAllDocCount = bdAllDocCount;
	}
}
