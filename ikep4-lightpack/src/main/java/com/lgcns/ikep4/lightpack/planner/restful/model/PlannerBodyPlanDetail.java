package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyPlanDetail {
	@XmlElement(name="PlanDetail")
	private PlannerDataPlanDetail planDetail;
	
	@XmlElement(name="ShareList")
	private List<PlannerDataShare> shareList;
	
	@XmlElement(name="ReferenceList")
	private List<PlannerDataReference> referenceList;
	
	@XmlElement(name="FacilitiesList")
	private List<PlannerDataFacilities> facilitiesList;
	
	@XmlElement(name="AttachList")
	private List<PlannerDataAttach> attachList;
	
	@XmlElement(name="AlarmList")
	private List<PlannerDataAlarm> alarmList;
	
	@XmlElement(name="UnfixedPlanList")
	private List<PlannerDataUnfixedTime> unfixedPlanList;
	
	public PlannerBodyPlanDetail() {
		this.shareList = new ArrayList<PlannerDataShare>();
		this.referenceList = new ArrayList<PlannerDataReference>();
		this.facilitiesList = new ArrayList<PlannerDataFacilities>();
		this.attachList = new ArrayList<PlannerDataAttach>();
		this.alarmList = new ArrayList<PlannerDataAlarm>();
		this.unfixedPlanList = new ArrayList<PlannerDataUnfixedTime>();
	}
	
	public PlannerBodyPlanDetail(PlannerDataPlanDetail planDetail,
			List<PlannerDataShare> shareList, List<PlannerDataReference> referenceList,
			List<PlannerDataFacilities> facilitiesList, List<PlannerDataAttach> attachList,
			List<PlannerDataAlarm> alarmList, List<PlannerDataUnfixedTime> unfixedPlanList) {
		this.planDetail = planDetail;
		this.shareList = shareList;
		this.referenceList = referenceList;
		this.facilitiesList = facilitiesList;
		this.attachList = attachList;
		this.alarmList = alarmList;
		this.unfixedPlanList = unfixedPlanList;
	}
	
	public void addPlanDetail(PlannerDataPlanDetail planDetail) {
		this.planDetail = planDetail;
	}
	
	public void addShare(PlannerDataShare share) {
		this.shareList.add(share);
	}
	
	public void addReference(PlannerDataReference reference) {
		this.referenceList.add(reference);
	}
	
	public void addFacilities(PlannerDataFacilities facilities) {
		this.facilitiesList.add(facilities);
	}
	
	public void addAttach(PlannerDataAttach attach) {
		this.attachList.add(attach);
	}
	
	public void addAlarm(PlannerDataAlarm alarm) {
		this.alarmList.add(alarm);
	}
	
	public void addUnfixedTime(PlannerDataUnfixedTime unfixedTime) {
		this.unfixedPlanList.add(unfixedTime);
	}
}