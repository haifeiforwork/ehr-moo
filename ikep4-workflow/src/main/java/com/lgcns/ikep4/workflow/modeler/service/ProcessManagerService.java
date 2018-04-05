/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.workflow.modeler.model.InstanceTrackingData;
import com.lgcns.ikep4.workflow.modeler.model.Partition;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;
import com.lgcns.ikep4.workflow.modeler.model.ProcessManager;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;


/**
 * ProcessManagerService로 IKEP4_WF_PARTITION, IKEP4_WF_PARTIION_PROCESS,
 * IKEP4_WF_PROCESS_MODEL 3개의 관계 테이블을 엮어서 수행하는 서비스
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessManagerService.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Transactional
public interface ProcessManagerService {
	/**
	 * 전체 리스트
	 * 
	 * @return
	 */
	public ProcessManager list();

	/**
	 * Partition 리스트
	 * 
	 * @return
	 */
	public ProcessManager listPartition();

	/**
	 * Process 리스트
	 * 
	 * @param processModel
	 * @return
	 */
	public ProcessManager listProcess(ProcessModel processModel);
	
	/**
	 * Process 리스트(ProcessId, ProcessVer)
	 * 
	 * @param processModel
	 * @param res
	 */
	public ProcessManager selectProcessModel(ProcessModel processModel);

	/**
	 * Partition 생성
	 * 
	 * @param partition
	 * @return
	 */
	public String createPartition(Partition partition);

	/**
	 * 프로세스 모델을 생성.
	 * 
	 * @param partitionProcess
	 * @param processModel
	 * @return
	 */
	public String createProcess(PartitionProcess partitionProcess, ProcessModel processModel);

	/**
	 * 프로세스 모델을 수정.
	 * 
	 * @param partitionProcess
	 * @param processModel
	 * @return
	 */
	public void updateProcess(PartitionProcess partitionProcess, ProcessModel processModel);

	/**
	 * 프로세스 모델 스크립트를 XML 형태로 반환.
	 * 
	 * @param processModel
	 * @param res
	 */
	public void selectModelXml(ProcessModel processModel, HttpServletResponse res);
	
	/**
	 * 프로세스 인스턴스 추적 데이터를 XML형태로 반환.
	 * 
	 * @param instanceId
	 * @param res
	 */
	public String getInstanceTrackingXMLData(String instanceId);
	
	/**
	 * 프로세스 Model View Script XML 데이터 반환.
	 * 
	 * @param instanceId
	 * @param res
	 */
	public String selectModelViewScript(ProcessModel processModel);
	
	/**
	 * 파티션 Id 유무 데이터 반환.
	 * 
	 * @param partitionId
	 * @param res
	 */
	public boolean getPartitionExists(Partition partition);
	
	/**
	 * 프로세스Id 유무 데이터 반환.
	 * 
	 * @param processId
	 * @param res
	 */
	public boolean getProcessExists(PartitionProcess partitionProcess, ProcessModel processModel);
	
	/**
	 * 프로세스 삭제
	 * 
	 * @param processId
	 * @param res
	 */
	public int deleteProcess(ProcessModel processModel);
	
	/**
	 * 파티션 프로세스 삭제
	 * 
	 * @param processId
	 * @param res
	 */
	public int deletePartitionProcess(PartitionProcess partitionProcess);
	
	/**
	 * 파티션 프로세스 반환
	 * 
	 * @param partitionProcess
	 * @param res
	 */
	public ProcessManager selectPartitionProcess(PartitionProcess partitionProcess);
	
	/**
	 * 파티션 삭제
	 * 
	 * @param partitionProcess
	 * @param res
	 */
	public void deletePartition(PartitionProcess partitionProcess);
}
