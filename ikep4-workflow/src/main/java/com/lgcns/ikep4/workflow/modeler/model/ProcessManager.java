/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.model;

import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IKEP4_WF_PARTITION, IKEP4_WF_PARTIION_PROCESS, IKEP4_WF_PROCESS_MODEL 3개의 관계
 * 테이블을 엮어서 전체 리스트로 반환하는 VO.
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessManager.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ProcessManager extends BaseObject {

	// 기본 모델 형태.
	private Partition partition;

	private PartitionProcess partitionProcess;

	private ProcessModel processModel;

	// List Type의 형태.
	private List<Partition> partitionList;

	private List<PartitionProcess> partitionProcessList;

	private List<ProcessModel> processModelList;

	/**
	 * @return the partition
	 */
	public Partition getPartition() {
		return partition;
	}

	/**
	 * @param partition the partition to set
	 */
	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	/**
	 * @return the partitionProcess
	 */
	public PartitionProcess getPartitionProcess() {
		return partitionProcess;
	}

	/**
	 * @param partitionProcess the partitionProcess to set
	 */
	public void setPartitionProcess(PartitionProcess partitionProcess) {
		this.partitionProcess = partitionProcess;
	}

	/**
	 * @return the processModel
	 */
	public ProcessModel getProcessModel() {
		return processModel;
	}

	/**
	 * @param processModel the processModel to set
	 */
	public void setProcessModel(ProcessModel processModel) {
		this.processModel = processModel;
	}

	/**
	 * @return the partitionList
	 */
	public List<Partition> getPartitionList() {
		return partitionList;
	}

	/**
	 * @param partitionList the partitionList to set
	 */
	public void setPartitionList(List<Partition> partitionList) {
		this.partitionList = partitionList;
	}

	/**
	 * @return the partitionProcessList
	 */
	public List<PartitionProcess> getPartitionProcessList() {
		return partitionProcessList;
	}

	/**
	 * @param partitionProcessList the partitionProcessList to set
	 */
	public void setPartitionProcessList(List<PartitionProcess> partitionProcessList) {
		this.partitionProcessList = partitionProcessList;
	}

	/**
	 * @return the processModelList
	 */
	public List<ProcessModel> getProcessModelList() {
		return processModelList;
	}

	/**
	 * @param processModelList the processModelList to set
	 */
	public void setProcessModelList(List<ProcessModel> processModelList) {
		this.processModelList = processModelList;
	}

}
