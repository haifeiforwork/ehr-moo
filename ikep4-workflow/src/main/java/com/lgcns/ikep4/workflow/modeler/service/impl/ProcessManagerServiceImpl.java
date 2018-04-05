/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.service.impl;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.modeler.dao.InstanceTrackingDataDao;
import com.lgcns.ikep4.workflow.modeler.dao.PartitionDao;
import com.lgcns.ikep4.workflow.modeler.dao.PartitionProcessDao;
import com.lgcns.ikep4.workflow.modeler.dao.ProcessModelDao;
import com.lgcns.ikep4.workflow.modeler.model.InstanceTrackingData;
import com.lgcns.ikep4.workflow.modeler.model.Partition;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;
import com.lgcns.ikep4.workflow.modeler.model.ProcessManager;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;
import com.lgcns.ikep4.workflow.modeler.service.ProcessManagerService;


/**
 * ProcessManagerServiceImpl로 IKEP4_WF_PARTITION, IKEP4_WF_PARTIION_PROCESS,
 * IKEP4_WF_PROCESS_MODEL 3개의 관계 테이블을 엮어서 수행하는 서비스
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessManagerServiceImpl.java 1941 2011-03-08 02:38:35Z smlee
 *          $
 */
@Service("processManagerService")
public class ProcessManagerServiceImpl implements ProcessManagerService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PartitionDao partitionDao;

	@Autowired
	private PartitionProcessDao partitionProcessDao;

	@Autowired
	private ProcessModelDao processModelDao;
	
	@Autowired
	private InstanceTrackingDataDao instanceTrackingDataDao;

	@Autowired
	private IdgenService idgenService;
	
	ProcessManager processManager;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public ProcessManager list() {
		processManager = new ProcessManager();
		processManager.setPartitionList(partitionDao.selectAll());
		processManager.setPartitionProcessList(partitionProcessDao.selectAll());
		processManager.setProcessModelList(processModelDao.selectAll());

		return processManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public ProcessManager listPartition() {
		processManager = new ProcessManager();
		processManager.setPartitionList(partitionDao.selectAll());

		return processManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public ProcessManager listProcess(ProcessModel processModel) {
		processManager = new ProcessManager();
		processManager.setProcessModel(processModelDao.get(processModel.getProcessId()));
		processManager.setPartitionProcess(partitionProcessDao.get(processModel.getProcessId()));
		processManager.setPartition(partitionDao.get(processManager.getPartitionProcess().getPartitionId()));

		return processManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public ProcessManager selectProcessModel(ProcessModel processModel) {
		processManager = new ProcessManager();
		processManager.setProcessModel(processModelDao.selectProcessModel(processModel));

		return processManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public String createPartition(Partition partition) {
		return partitionDao.create(partition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public String createProcess(PartitionProcess partitionProcess, ProcessModel processModel) {
		/** iKEP4 Common 으로 사용하는 Key(Sequences) 지정. */
		partitionProcess.setRelationId(idgenService.getNextId());
		String processId = processModelDao.create(processModel);
		partitionProcessDao.create(partitionProcess);

		return processId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public int deleteProcess(ProcessModel processModel) {
		return processModelDao.deleteProcess(processModel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public int deletePartitionProcess(PartitionProcess partitionProcess) {
		return partitionProcessDao.deletePartitionProcess(partitionProcess);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public void deletePartition(PartitionProcess partitionProcess) {
		String partitionId = partitionProcess.getPartitionId();
		partitionDao.remove(partitionId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public ProcessManager selectPartitionProcess(PartitionProcess partitionProcess) {
		processManager = new ProcessManager();
		processManager.setPartitionProcessList(partitionProcessDao.selectPartitionProcess(partitionProcess));

		return processManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public boolean getProcessExists(PartitionProcess partitionProcess, ProcessModel processModel) {
		boolean result = processModelDao.exists(processModel.getProcessId());
		return result;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public void updateProcess(PartitionProcess partitionProcess, ProcessModel processModel) {
		processModelDao.update(processModel);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public void selectModelXml(ProcessModel processModel, HttpServletResponse res) {}
	
	private enum InstanceState { 
		RUNNING, COMPLETE, SUSPEND, FAULT, NOVALUE;
		
		public static InstanceState fromString(String Str)
		{
			try {return valueOf(Str);}
			catch (Exception ex){return NOVALUE;}
		}

	}
	
	private enum ActivityState {
		ASSIGN, SELECT, COMPLETE, NOVALUE;
		
		public static ActivityState fromString(String Str)
		{
			try {return valueOf(Str);}
			catch (Exception ex){return NOVALUE;}
		}
	}
	
	private enum ActivitySubState {
		FAULT, OVERDUE, SKIP, NOVALUE;
		
		public static ActivitySubState fromString(String Str)
		{
			try {return valueOf(Str);}
			catch (Exception ex){return NOVALUE;}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public String getInstanceTrackingXMLData(String instanceId) {
		List<InstanceTrackingData> instanceTrackingDataList = instanceTrackingDataDao.listInstanceTrackingData(instanceId);
		StringBuffer sb = new StringBuffer();
		
		String instanceState = "";
		String instanceStateTxt = "";
		String activityState = "";
		String activityStateTxt = "";
		
		
		

		if(instanceTrackingDataList.size() > 0) {
			
			// tracker instance state (running : 1(녹색), suspend : 2(노랑), fault : 3(빨강), 기타 (회색))
			switch(InstanceState.fromString(instanceTrackingDataList.get(0).getState())) {
				case RUNNING : instanceState = "1"; instanceStateTxt = messageSource.getMessage("message.workflow.monitoring.instanceState.running", null, Locale.getDefault()); break;
				case SUSPEND : instanceState = "2"; instanceStateTxt = messageSource.getMessage("message.workflow.monitoring.instanceState.suspend", null, Locale.getDefault()); break;
				case FAULT : instanceState = "3"; instanceStateTxt = messageSource.getMessage("message.workflow.monitoring.instanceState.fault", null, Locale.getDefault()); break;
				case COMPLETE : instanceState = "5"; instanceStateTxt = messageSource.getMessage("message.workflow.monitoring.instanceState.complete", null, Locale.getDefault()); break;
				default : break;
			}
			
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
			sb.append("<process> \n");
    		sb.append("<cikey>"+instanceTrackingDataList.get(0).getInstanceId()+"</cikey>\n");
    		sb.append("<domain>"+instanceTrackingDataList.get(0).getPartitionId()+"</domain>\n");
    		sb.append("<process_id>"+instanceTrackingDataList.get(0).getProcessId()+"</process_id>\n");
    		sb.append("<process_name>"+instanceTrackingDataList.get(0).getProcessName()+"</process_name>\n");
    		sb.append("<revision_tag>"+instanceTrackingDataList.get(0).getInstanceId()+"</revision_tag>\n");
    		sb.append("<business_key></business_key>\n");
    		sb.append("<creation_date>"+instanceTrackingDataList.get(0).getCreateDate()+"</creation_date>\n");
    		sb.append("<modify_date>"+instanceTrackingDataList.get(0).getFinishedDate()+"</modify_date>\n");
    		sb.append("<state>"+instanceState+"</state>\n");
    		sb.append("<state_txt>"+instanceStateTxt+"</state_txt>\n");
    		sb.append("<title>"+instanceTrackingDataList.get(0).getTitle()+"</title>\n");
    		sb.append("<conversation_id></conversation_id>\n");
    		sb.append("<root_id></root_id>\n");
    		sb.append("<parent_id>"+instanceTrackingDataList.get(0).getParentInsId()+"</parent_id>\n");
    		sb.append("<metadata></metadata>\n");
    		sb.append("<index1></index1>\n");
    		sb.append("<index2></index2>\n");
    		sb.append("<index3></index3>\n");
    		sb.append("<index4></index4>\n");
    		sb.append("<index5></index5>\n");
    		sb.append("<index6></index6>\n");
    		sb.append("<webModelRepository></webModelRepository>\n");
    		sb.append("<activities>\n");
    		for(int i=0; i < instanceTrackingDataList.size(); i++) {
    			
    			// tracker activity state (activation, completion)
    			switch(ActivityState.fromString(instanceTrackingDataList.get(i).getAtState())) {
    				case ASSIGN : activityState = "activation"; activityStateTxt = messageSource.getMessage("message.workflow.monitoring.activityState.assign", null, Locale.getDefault()); break;
    				case SELECT : activityState = "activation"; activityStateTxt = messageSource.getMessage("message.workflow.monitoring.activityState.select", null, Locale.getDefault()); break;
    				case COMPLETE : activityState = "completion"; activityStateTxt = messageSource.getMessage("message.workflow.monitoring.activityState.complete", null, Locale.getDefault()); break;
    				default : break;
    			}
    			
    			switch(ActivitySubState.fromString(instanceTrackingDataList.get(i).getAtSubState())) {
    				case FAULT : activityStateTxt += "("+messageSource.getMessage("message.workflow.monitoring.activityState.fault", null, Locale.getDefault())+")"; break;
    				case OVERDUE : activityStateTxt += "("+messageSource.getMessage("message.workflow.monitoring.activityState.overdue", null, Locale.getDefault())+")"; break;
    				case SKIP : activityStateTxt += "("+messageSource.getMessage("message.workflow.monitoring.activityState.skip", null, Locale.getDefault())+")"; break;
    				default : break;
    			}
    			
    			sb.append("    <activity activity_id=\""+instanceTrackingDataList.get(i).getActivityId()+"\" activity_name=\""+instanceTrackingDataList.get(i).getActivityName()+"\">\n");
    			sb.append("        <wi_data>\n");
        		sb.append("		       <wi_creation_date>"+instanceTrackingDataList.get(i).getAtCreateDate()+"</wi_creation_date>\n");
        		sb.append("		       <wi_completion_date>"+instanceTrackingDataList.get(i).getAtFinishedDate()+"</wi_completion_date>\n");
        		sb.append("		       <wi_state>"+activityState+"</wi_state>\n");
        		sb.append("        </wi_data>\n");
        		sb.append("        <sensor_data>\n");
        		sb.append("		       <sensor_name></sensor_name>\n");
        		sb.append("		       <sensor_target></sensor_target>\n");
        		sb.append("		       <eval_point></eval_point>\n");
        		sb.append("        </sensor_data>\n");
        		sb.append("        <custom_data>\n");
        		sb.append("		       <activity_type>"+instanceTrackingDataList.get(i).getActivityType()+"</activity_type>\n");
        		sb.append("		       <activity_id>"+instanceTrackingDataList.get(i).getActivityId()+"</activity_id>\n");
        		sb.append("		       <activity_name>"+instanceTrackingDataList.get(i).getActivityName()+"</activity_name>\n");
        		sb.append("		       <group_processing_type></group_processing_type>\n");	//현재 미 구현 상태
        		sb.append("		       <creation_date>"+instanceTrackingDataList.get(i).getCreateDate()+"</creation_date>\n");
        		sb.append("		       <completion_date>"+instanceTrackingDataList.get(i).getFinishedDate()+"</completion_date>\n");
        		sb.append("		       <task_id></task_id>\n");
        		sb.append("		       <participants_id>"+instanceTrackingDataList.get(i).getPerformerId()+"</participants_id>\n");
        		sb.append("		       <participants></participants>\n");
        		sb.append("		       <participants_group_id>"+instanceTrackingDataList.get(i).getPerformerId()+"</participants_group_id>\n");	// 미규현 상태
        		sb.append("		       <participants_group></participants_group>\n");	// 미규현 상태
        		sb.append("		       <is_fault>"+("FAULT".equals(instanceTrackingDataList.get(i).getAtSubState()) ? "Y" : "N")+"</is_fault>\n");
        		sb.append("		       <is_overdue>"+("OVERDUE".equals(instanceTrackingDataList.get(i).getAtSubState()) ? "Y" : "N")+"</is_overdue>\n");
        		sb.append("		       <is_skip>"+("SKIP".equals(instanceTrackingDataList.get(i).getAtSubState()) ? "Y" : "N")+"</is_skip>\n");
        		sb.append("		       <status>"+activityStateTxt+"</status>\n");
        		sb.append("		       <outcome></outcome>\n");
        		sb.append("		       <lead_time>"+instanceTrackingDataList.get(i).getLeadTime()+"</lead_time>\n");
        		sb.append("		       <activity_lead_time>"+instanceTrackingDataList.get(i).getAtLeadTime()+"</activity_lead_time>\n");
        		sb.append("		       <retry_count></retry_count>\n");
        		sb.append("		       <is_participation></is_participation>\n");
        		sb.append("        </custom_data>\n");
        		sb.append("		</activity>\n");
    			
    		}
    		sb.append("</activities>\n");
    		sb.append("</process> \n");
		}
		
		if(log.isDebugEnabled()) {
			log.debug("instanceTrackingXMLData : "+sb.toString());
		}
		
		return sb.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public String selectModelViewScript(ProcessModel processModel) {
		processManager = new ProcessManager();
		processManager.setProcessModel(processModelDao.selectProcessModel(processModel));
		
		return processManager.getProcessModel().getViewScript();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.service.PartitionService#list()
	 */
	public boolean getPartitionExists(Partition partition) {
		return partitionDao.exists(partition.getPartitionId());
	}
}
