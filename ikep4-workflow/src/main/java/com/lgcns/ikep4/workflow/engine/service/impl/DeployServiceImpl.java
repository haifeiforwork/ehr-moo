/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.engine.dao.DeployActivityDao;
import com.lgcns.ikep4.workflow.engine.dao.DeployProcessDao;
import com.lgcns.ikep4.workflow.engine.dao.DeployTransitionDao;
import com.lgcns.ikep4.workflow.engine.dao.ParticipantDao;
import com.lgcns.ikep4.workflow.engine.dao.ProcessDao;
import com.lgcns.ikep4.workflow.engine.dao.ServiceDao;
import com.lgcns.ikep4.workflow.engine.model.ActivityBean;
import com.lgcns.ikep4.workflow.engine.model.DatafieldBean;
import com.lgcns.ikep4.workflow.engine.model.NotifyBean;
import com.lgcns.ikep4.workflow.engine.model.ParticipantBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.ServiceBean;
import com.lgcns.ikep4.workflow.engine.model.TransitionBean;
import com.lgcns.ikep4.workflow.engine.service.DeployService;
import com.lgcns.ikep4.workflow.engine.service.NotifyService;
import com.lgcns.ikep4.workflow.engine.util.DomUtil;
import com.lgcns.ikep4.workflow.engine.util.XmlService;


/**
 * BoardService 구현 클래스
 * 
 * @author 박철순(sniper28@naver.com)
 * @version $Id: DeployServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("deployService")
public class DeployServiceImpl implements DeployService {
	
	Log log = LogFactory.getLog(DeployServiceImpl.class);
	
	@Autowired
	private ServiceDao serviceDao;	

	@Autowired
	private ProcessDao processDao;	
	
	@Autowired
	private DeployProcessDao deployProcessDao;

	@Autowired
	private DeployActivityDao deployActivityDao;
	
	@Autowired
	private DeployTransitionDao deployTransitionDao;	
	
	@Autowired
	private ParticipantDao participantDao;		
	
	@Autowired
	private IdgenService idgenService;	
	
	@Autowired
	private NotifyService notifyService;		

	/**
	 * 프로세스를 Deploy 처리한다. 
	 * @param xpdlStream
	 * @param userId
	 * @return
	 */
	public boolean deployProcess(String xpdlStream, String userId) {
		Date todayDate			= new Date();
		return deployProcess(xpdlStream, userId, "", "WORKFLOW", todayDate);
	}
	
	/**
	 * 프로세스를 Deploy 처리한다. 
	 * @param xpdlStream
	 * @param userId
	 * @param partitionId
	 * @return
	 */
	public boolean deployProcess(String xpdlStream, String userId, String partitionId) {
		Date todayDate			= new Date();
		return deployProcess(xpdlStream, userId, partitionId, "WORKFLOW", todayDate);
	}
	
	/**
	 * 프로세스를 Deploy 처리한다. 
	 * @param xpdlStream
	 * @param userId
	 * @param partitionId
	 * @param processType
	 * @return
	 */
	public boolean deployProcess(String xpdlStream, String userId, String partitionId, String processType) {
		Date todayDate			= new Date();
		return deployProcess(xpdlStream, userId, partitionId, processType, todayDate);
	}	
	

	/**
	 * 프로세스를 Deploy 처리한다. 
	 * @param xpdlStream
	 * @return
	 */
	public boolean deployProcess(String xpdlStream, String userId, String partitionId, String processType, Date executeDate) {
		boolean isSuccess = false;
		XmlService xmlService = new XmlService();
		xmlService.loadingXML(xpdlStream);
		HashMap<String, ActivityBean> activityHsh 	= new HashMap<String, ActivityBean>();
		HashMap<String, TransitionBean> transitionHsh 	= new HashMap<String, TransitionBean>();
		HashMap<String, ParticipantBean> participantHsh 	= new HashMap<String, ParticipantBean>();
		String processId		= "";
		String processName		= "";
		String processVer		= "1.0";
		String vendor			= "tmaxsoft";
		String processDescription = "";
		String deployLog		= "";
		

		ProcessBean processBean = new ProcessBean();
		try {
			
			deployLog	= 	"\n#######################################################################" + 
							"\nSTART DEPLOY XPDL..." + 
							"\n#######################################################################";
			processId = xmlService.getAttribute("/Package/WorkflowProcesses/WorkflowProcess", "Id");
			processName = xmlService.getAttribute("/Package/WorkflowProcesses/WorkflowProcess", "Name");
			processVer = xmlService.getNodeText("/Package/WorkflowProcesses/WorkflowProcess/RedefinableHeader/Version");
			processDescription = xmlService.getNodeText("/Package/WorkflowProcesses/WorkflowProcess/ProcessHeader/Description");
			
			processBean.setPartitionId(partitionId);
			processBean.setProcessId(processId);
			processBean.setProcessName(processName);
			processBean.setProcessVer(processVer);
			processBean.setAuthorId(userId);
			processBean.setVendor(vendor);
			processBean.setProcessType(processType);
			processBean.setProcessState("ACTIVE");
			processBean.setDescription(processDescription);
			processBean.setCreateDate(executeDate);

			deployLog	+= 	"\n# processId : " + processId + ", processName : " + processName;
			deployLog	+= 	"\n# processDescription : " + processDescription;
			
			// ********************************************************************************************
			// Participant 처리 
			// ********************************************************************************************
			deployLog	+= 	"\n\n# processing Participant..... ";
			
			NodeList participantNodeList = xmlService.getNodeList("/Package/WorkflowProcesses/WorkflowProcess/Participants/Participant");
			for (int i = 0; i < participantNodeList.getLength(); i++) {
				if(vendor.equalsIgnoreCase("tmaxsoft")) {
					Node participantNode		= participantNodeList.item(i);
					String participantId		= xmlService.getAttribute(participantNode, "Id");
					String participantType		= xmlService.getAttribute(participantNode, "ParticipantType", "Type");
					String participantValue		= "";
					String contraintValue		= "";
					String useIndex				= "";
					ParticipantBean participantBean = new ParticipantBean();
					
					if(xmlService.isExistNode(participantNode, "ExtendedAttributes/ExtendedAttribute[@Name='ParticipantExtendedAttribute']/ParticipantExtendedAttribute/Value")) {
						Node participantValueNode	= xmlService.getSingleNode(participantNode, "ExtendedAttributes/ExtendedAttribute[@Name='ParticipantExtendedAttribute']/ParticipantExtendedAttribute/Value");
						participantValue			= participantValueNode.getTextContent();
						if(participantValue.startsWith("$")) {
							participantValue		= participantValue.substring(1, participantValue.length());
						}
						
						// 배열변수에서 Index 사용여부를 체크하여 Participant를 Deploy한다. 
						if(xmlService.isExistNode("Package/WorkflowProcesses/WorkflowProcess/DataFields/DataField[@Id='" + participantValue + "']")) {
							Node dataFieldNode		= xmlService.getSingleNode("/Package/WorkflowProcesses/WorkflowProcess/DataFields/DataField[@Id='" + participantValue + "']");
							if(xmlService.isAttribute(dataFieldNode, "IsArray")) {
								if(xmlService.isAttribute(dataFieldNode, "lgcns:UseIndex")) {
									useIndex		= xmlService.getAttribute(dataFieldNode, "lgcns:UseIndex");
									if(useIndex.equalsIgnoreCase("TRUE")) {
										participantBean.setIndexVar(participantValue + "_Key");
									}
								}
							}
						}
					}
					// 제약사항을 처리한다. 
					if(xmlService.isExistNode(participantNode, "ExtendedAttributes/ExtendedAttribute[@Name='ParticipantExtendedAttribute']/ParticipantExtendedAttribute/UserConstraints")) {
						Node contraintNode			= xmlService.getSingleNode(participantNode, "ExtendedAttributes/ExtendedAttribute[@Name='ParticipantExtendedAttribute']/ParticipantExtendedAttribute/UserConstraints");
						contraintValue				= contraintNode.getTextContent(); 
					}
					
					participantBean.setParticipantId(participantId);
					participantBean.setParticipantType(participantType);
					participantBean.setPerformerId(participantValue);
					participantBean.setRuleContraint(contraintValue);
					participantHsh.put(participantId, participantBean);
					
					deployLog	+= 	"\n# participant Id=" + participantId + ", Type=" + participantType + ", performerId=" + participantValue + ", UseIndex=" + useIndex;
				}
			}

			// ********************************************************************************************
			// DataField 처리
			// ********************************************************************************************
			deployLog	+= 	"\n\n# processing Datafield..... ";

			HashMap<String, String> hshAttr = new HashMap<String, String>();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance(); // DocumentBuilderFactory
																								// 의
																								// 객체
																								// 생성
			docBuilderFactory.setCoalescing(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document datafieldDoc = docBuilder.newDocument();
			DomUtil domUtil = new DomUtil();
			Element elmDatafields = domUtil.createElementReturn(datafieldDoc, null, "DataFields", "", null);

			NodeList dataFieldNodeList = xmlService.getNodeList("/Package/WorkflowProcesses/WorkflowProcess/DataFields/DataField");
			for (int i = 0; i < dataFieldNodeList.getLength(); i++) {
				Node dataFieldNode = dataFieldNodeList.item(i);
				String datafieldId = xmlService.getAttribute(dataFieldNode, "Id");
				String datafieldName = xmlService.getAttribute(dataFieldNode, "Name");
				String arrayType = xmlService.getAttribute(dataFieldNode, "IsArray").equals("") ? "false" : xmlService.getAttribute(dataFieldNode, "IsArray");
				String useIndex = xmlService.getAttribute(dataFieldNode, "lgcns:UseIndex").equals("") ? "FALSE" : xmlService.getAttribute(dataFieldNode, "lgcns:UseIndex");
				String dataType = xmlService.getAttribute(dataFieldNode, "DataType/BasicType", "Type");
				String initValue = xmlService.getAttrValue(dataFieldNode, "DataType/BasicType/InitialValue");
				
				deployLog	+= 	"\n# datafieldName=" + datafieldName + ", dataType=" + dataType + ", arrayType=" + arrayType + ", useIndex=" + useIndex + ", initValue=" + initValue;

				DatafieldBean datafieldBean = new DatafieldBean();
				datafieldBean.setDatafieldId(datafieldId);
				datafieldBean.setDatafieldName(datafieldName);
				datafieldBean.setArrayType(arrayType);
				datafieldBean.setUseIndex(useIndex);
				datafieldBean.setDatafieldValue(initValue);

				processBean.addDatafieldBean(datafieldId, datafieldBean);
				
				// 배열변수이면서 Index를 사용할 경우 Index_Key Datafield를 생성시킨다.   
				if(arrayType.equalsIgnoreCase("TRUE")) {
					if(useIndex.equalsIgnoreCase("TRUE")) {
						DatafieldBean datafieldBeanKey = new DatafieldBean();
						datafieldBeanKey.setDatafieldId(datafieldId + "_Key");
						datafieldBeanKey.setDatafieldName(datafieldName + "(Index)");
						datafieldBeanKey.setArrayType("FALSE");
						datafieldBeanKey.setUseIndex("FALSE");
						processBean.addDatafieldBean(datafieldId+"_Key", datafieldBeanKey);
					}
				}

				hshAttr.clear();
				hshAttr.put("Id", datafieldId);
				hshAttr.put("Name", datafieldName);
				hshAttr.put("IsArray", arrayType);
				hshAttr.put("UseIndex", useIndex);
				Element elmDatafield = domUtil.createElementReturn(datafieldDoc, elmDatafields, "DataField", "", hshAttr);
				Element elmDataType = domUtil.createElementReturn(datafieldDoc, elmDatafield, "DataType", "", null);

				hshAttr.clear();
				hshAttr.put("Type", dataType);
				domUtil.createElement(datafieldDoc, elmDataType, "BasicType", "", hshAttr);
				domUtil.createElement(datafieldDoc, elmDatafield, "InitialValue", initValue, null);
				domUtil.createElement(datafieldDoc, elmDatafield, "CurrentValue", initValue, null);
				
				// 배열변수이면서 Index를 사용할 경우 Index_Key 변수 Element를 XML에 생성시킨다.  
				if(arrayType.equalsIgnoreCase("TRUE")) {
					if(useIndex.equalsIgnoreCase("TRUE")) {
						hshAttr.clear();
						hshAttr.put("Id", datafieldId + "_Key");
						hshAttr.put("Name", datafieldName + "(Index)");
						hshAttr.put("IsArray", "FALSE");
						hshAttr.put("UseIndex", "FALSE");
						Element elmDatafieldKey = domUtil.createElementReturn(datafieldDoc, elmDatafields, "DataField", "", hshAttr);
						Element elmDataTypeKey = domUtil.createElementReturn(datafieldDoc, elmDatafieldKey, "DataType", "", null);
	
						hshAttr.clear();
						hshAttr.put("Type", dataType);
						domUtil.createElement(datafieldDoc, elmDataTypeKey, "BasicType", "", hshAttr);
						domUtil.createElement(datafieldDoc, elmDatafieldKey, "InitialValue", initValue, null);
						domUtil.createElement(datafieldDoc, elmDatafieldKey, "CurrentValue", initValue, null);
					}
				}
			}
			// ********************************************************************************************
			// ProcessKeyData 처리
			// ********************************************************************************************	
			deployLog	+= 	"\n\n# processing ProcessKeyData..... ";
			NodeList processKeyNodeList = xmlService.getNodeList("/Package/WorkflowProcesses/WorkflowProcess/ExtendedAttributes/ExtendedAttribute/ProcessExtendedAttribute/ProcessKeyData");
			Element elmProcessKeyDatas = domUtil.createElementReturn(datafieldDoc, elmDatafields, "ProcessKeyDatas", "", null);
			for(int i=0;i<processKeyNodeList.getLength();i++) {
				Node processKeyNode = processKeyNodeList.item(i);
				Node expressionNode = xmlService.getSingleNode(processKeyNode, "Expression");
				String expression	= xmlService.getAttrValue(expressionNode);
				expression			= expression.replaceAll("\\$", "");

				deployLog	+= 	"\n# ProcessKeyData[" + i + "] : " + expression + ", expressionNode : " + expressionNode + ", expressionNode.getTextContent() : " + expressionNode.getTextContent();
				
				if(expression != null && !expression.equals("")) {
					hshAttr.clear();
					hshAttr.put("Index", (""+(i+1)));					
					Element elmProcessKeyData = domUtil.createElementReturn(datafieldDoc, elmProcessKeyDatas, "ProcessKeyData", "", hshAttr);
					domUtil.createElementReturn(datafieldDoc, elmProcessKeyData, "Expression", expression, null);
				}
			}

			String varDefine = domUtil.generationDoc(datafieldDoc);
			processBean.setVarDefine(varDefine);

			
			// ********************************************************************************************
			// Activity 처리
			// ********************************************************************************************
			deployLog	+= 	"\n\n# processing Activity..... ";

			NodeList activityNodeList = xmlService.getNodeList("/Package/WorkflowProcesses/WorkflowProcess/Activities/Activity");
			for (int i = 0; i < activityNodeList.getLength(); i++) {
				Node activityNode = activityNodeList.item(i);
				String activityId = xmlService.getAttribute(activityNode, "Id");
				String activityName = xmlService.getAttribute(activityNode, "Name");
				String activityType = "";
				String subType = "Activity";
				String gatewayType = "";
				String subProcessId = "";
				if (xmlService.isExistNode(activityNode, "Event/StartEvent")) {
					subType = "StartEvent";
				} else if (xmlService.isExistNode(activityNode, "Event/EndEvent")) {
					subType = "EndEvent";
				}
				if (xmlService.isExistNode(activityNode, "Implementation/SubFlow")) {
					subProcessId = xmlService.getAttribute(activityNode, "Implementation/SubFlow", "Id");
				}

				if (xmlService.isExistNode(activityNode, "TransitionRestrictions/TransitionRestriction")) {
					Node transitionRestrictionNode = xmlService.getSingleNode(activityNode,
							"TransitionRestrictions/TransitionRestriction");
					Node gatewayNode = null;
					if (xmlService.isExistNode(transitionRestrictionNode, "Split")) {
						gatewayNode = xmlService.getSingleNode(transitionRestrictionNode, "Split");
						activityType = "Split";
						gatewayType = xmlService.getAttribute(gatewayNode, "Type");
					} else if (xmlService.isExistNode(transitionRestrictionNode, "Join")) {
						gatewayNode = xmlService.getSingleNode(transitionRestrictionNode, "Join");
						activityType = "Join";
						gatewayType = xmlService.getAttribute(gatewayNode, "Type");
					}
					if (gatewayNode != null) {
						NodeList transitionRefNodeList = xmlService.getNodeList(gatewayNode,
								"TransitionRefs/TransitionRef");
						for (int j = 0; j < transitionRefNodeList.getLength(); j++) {

						}
					}
				}

				ActivityBean activityBean = new ActivityBean();
				activityBean.setActivityId(activityId);
				activityBean.setActivityName(activityName);
				activityBean.setActivityType(activityType);
				activityBean.setSubType(subType);
				activityBean.setGatewayType(gatewayType);
				activityBean.setSubProcessId(subProcessId);
				
				activityBean.setProcessId(processBean.getProcessId());
				activityBean.setProcessVer(processBean.getProcessVer());
				
				// Activity 퍼포머 정보를 담는다. 
				NodeList partNodeList			= xmlService.getNodeList(activityNode, "Implementation/Task/TaskManual/Performers/Performer");
				for(int j=0;j<partNodeList.getLength();j++) {
					Node participantNode		= partNodeList.item(j);
					activityBean.setParticipant(participantNode.getTextContent());
				}
				
				
				// InputSet, OutputSet을 처리한다.
				DocumentBuilderFactory docDataFactory = DocumentBuilderFactory.newInstance(); // DocumentBuilderFactory 의 객체 생성
				docDataFactory.setCoalescing(true);
				DocumentBuilder docData = docDataFactory.newDocumentBuilder();
				Document dataSetDoc = docData.newDocument();

				Element elmDataSet = domUtil.createElementReturn(dataSetDoc, null, "DataSet", "", null);
				

				// Activity의 InputSet, OutputSet을 처리한다.
				Element elmInputSet = domUtil.createElementReturn(dataSetDoc, elmDataSet, "InputSet", "", null);
				if (xmlService.isExistNode(activityNode, "InputSets/InputSet/Input")) {
					NodeList nodeInputList = xmlService.getNodeList(activityNode, "InputSets/InputSet/Input");
					for (int j = 0; j < nodeInputList.getLength(); j++) {
						Node inputNode = nodeInputList.item(j);
						String artifactId = xmlService.getAttribute(inputNode, "ArtifactId");
						activityBean.addInputSet(artifactId, processBean.getDatafieldBean(artifactId));
						
						DatafieldBean datafieldBean			= processBean.getDatafieldBean(artifactId);
						hshAttr.clear();
						hshAttr.put("Id", artifactId);
						hshAttr.put("Name", datafieldBean.getDatafieldName());
						hshAttr.put("Type", datafieldBean.getDatafieldType());
						hshAttr.put("IsArray", datafieldBean.getArrayType());
						hshAttr.put("UseIndex", datafieldBean.getUseIndex());
						domUtil.createElement(dataSetDoc, elmInputSet, "Input", "", hshAttr);						
						
					}
				}
				
				Element elmOutputSet = domUtil.createElementReturn(dataSetDoc, elmDataSet, "OutputSet", "", null);
				if (xmlService.isExistNode(activityNode, "OutputSets/OutputSet/Output")) {
					NodeList nodeOutputList = xmlService.getNodeList(activityNode, "OutputSets/OutputSet/Output");
					for (int j = 0; j < nodeOutputList.getLength(); j++) {
						Node outputNode = nodeOutputList.item(j);
						String artifactId = xmlService.getAttribute(outputNode, "ArtifactId");
						activityBean.addOutputSet(artifactId, processBean.getDatafieldBean(artifactId));
						
						DatafieldBean datafieldBean			= processBean.getDatafieldBean(artifactId);
						hshAttr.clear();
						hshAttr.put("Id", artifactId);
						hshAttr.put("Name", datafieldBean.getDatafieldName());
						hshAttr.put("Type", datafieldBean.getDatafieldType());
						hshAttr.put("IsArray", datafieldBean.getArrayType());
						hshAttr.put("UseIndex", datafieldBean.getUseIndex());
						domUtil.createElement(dataSetDoc, elmOutputSet, "Output", "", hshAttr);						
					}
				}
				// Loop 설정을 처리한다. 
				if(xmlService.isExistNode(activityNode, "Loop")) {
					Node loopNode	= xmlService.getSingleNode(activityNode, "Loop");
					String loopType	= xmlService.getAttribute(loopNode, "LoopType");
					
					if(loopType.equalsIgnoreCase("MultiInstance")) {
						if(xmlService.isExistNode(loopNode, "LoopMultiInstance")) {
							Node loopMultiNode		= xmlService.getSingleNode(loopNode, "LoopMultiInstance");
							String miOrdering		= xmlService.getAttribute(loopMultiNode, "MI_Ordering");
							String miFlowCondition	= xmlService.getAttribute(loopMultiNode, "MI_FlowCondition");
							if(miOrdering.equalsIgnoreCase("Parallel")) {
								if(miFlowCondition.equalsIgnoreCase("ALL")) {
									activityBean.setLoopType("MULTI_PARALLEL_ALL");
								} else {
									activityBean.setLoopType("MULTI_PARALLEL");
								}
							}
						}
					}
				}
				
				String varDefineAct 	= domUtil.generationDoc(dataSetDoc);
				activityBean.setVarDefine(varDefineAct);	
				
				activityBean	= setupNotify(activityBean, activityNode, "AssignNotify");
				activityBean	= setupNotify(activityBean, activityNode, "FinishNotify");
				activityBean	= setupNotify(activityBean, activityNode, "BeforeNotify");
				activityBean	= setupNotify(activityBean, activityNode, "AfterNotify");
				
				processBean.addActivityBean(activityId, activityBean);
				activityHsh.put(activityId, activityBean);
				deployLog	+= 	"\n# activityId : " + activityId + ", activityName : " + activityName;
			}

			// ********************************************************************************************
			// Transition
			// ********************************************************************************************
			deployLog		+= 	"\n\n# processing Transition.....\n";
			
			NodeList transitionNodeList = xmlService.getNodeList("/Package/WorkflowProcesses/WorkflowProcess/Transitions/Transition");
			for (int i = 0; i < transitionNodeList.getLength(); i++) {
				Node transitionNode = transitionNodeList.item(i);
				String transitionId = xmlService.getAttribute(transitionNode, "Id");
				String transitionName = xmlService.getAttribute(transitionNode, "Name");
				String transitionFrom = xmlService.getAttribute(transitionNode, "From");
				String transitionTo = xmlService.getAttribute(transitionNode, "To");
				String conditionType = "";
				String conditionExpression = "";

				if (xmlService.isExistNode(transitionNode, "Condition")) {
					conditionType = xmlService.getAttribute(transitionNode, "Condition", "Type");
					conditionExpression = xmlService.getAttrValue(transitionNode, "Condition/Expression");
				}

				TransitionBean transitionBean = new TransitionBean();
				transitionBean.setTransitionId(transitionId);
				transitionBean.setTransitionName(transitionName);
				transitionBean.setTransitionFrom(transitionFrom);
				transitionBean.setTransitionTo(transitionTo);
				transitionBean.setConditionType(conditionType);
				transitionBean.setConditionExpression(conditionExpression);
				
				transitionBean.setProcessId(processBean.getProcessId());
				transitionBean.setProcessVer(processBean.getProcessVer());
				transitionBean.setCreateDate(executeDate);

				processBean.addTransitionBean(transitionId, transitionBean);
				transitionHsh.put(transitionId, transitionBean);

				deployLog	+= 	"\n# transitionId : " + transitionId + ", transitionName : " + transitionName + ", transitionFrom : " + transitionFrom + ", transitionTo : " + transitionTo;
			}
			deployLog	+= 	"\n#######################################################################";
			deployLog	+= 	"\n FINISHED DEPLOY XPDL...";
			deployLog	+= 	"\n#######################################################################";

			deployLog	+= 	"\n# processBean.getProcessId() : " + processBean.getProcessId();
			deployLog	+= 	"\n# processBean.getProcessVer() : " + processBean.getProcessVer();
			
			List<ProcessBean> processList	= processDao.select(processBean);
			
			
			deployLog	+= 	"\n# processList.size() : " + processList.size();
			
			if(0<processList.size()) {
				// 프로세스를 Hot-Deploy 처리한다. 
				deployProcessDao.updateProcess(processBean);
			} else {
				// 프로세스를 Deploy 처리한다.  
				deployProcessDao.create(processBean);
				ServiceBean serviceBean 	= new ServiceBean();
				serviceBean.setServiceId(processId);
				serviceBean.setProcessId(processId);
				serviceBean.setProcessVer(processVer);
				serviceBean.setBaseCheck("N");
				serviceBean.setCreateDate(executeDate);
				serviceBean.setApplyStartDate(executeDate);
				
				Calendar calEndDate	= Calendar.getInstance();
				calEndDate.set(9999, 11, 30);
				
				serviceBean.setApplyEndDate(calEndDate.getTime());
				serviceDao.create(serviceBean);	
			}

			Iterator<String> iteratorAct		= activityHsh.keySet().iterator();
			
			while (iteratorAct.hasNext()) {
				String paramKey = iteratorAct.next().toString();
				ActivityBean actBean = (ActivityBean) activityHsh.get(paramKey);
				actBean.setCreateDate(executeDate);
				String actId = actBean.getActivityId();
				// Activity 데이터를 저장한다. 
				deployActivityDao.insertActivity(actBean);

				// Participant 데이터를 저장한다. 
				List participantList	= actBean.getParticipantList();
				for(int j=0;j<participantList.size();j++) {
					String participant				= participantList.get(j).toString();
					ParticipantBean participantBean	= (ParticipantBean) participantHsh.get(participant);
					if(participantBean != null) {
						String participantSeq = idgenService.getNextId();		
						participantBean.setParticipantSeq(participantSeq);
						participantBean.setProcessId(processId);
						participantBean.setProcessVer(processVer);
						participantBean.setActivityId(actId);
						participantBean.setCreateDate(executeDate);
						participantDao.insert(participantBean);
					}
				}
				// Notify 데이터를 저장한다.
				List notifyList	= actBean.getNotifyList();
				for(int j=0;j<notifyList.size();j++) {
					String notifySeq 		= idgenService.getNextId();
					NotifyBean	notifyBean	= (NotifyBean) notifyList.get(j);
					notifyBean.setNotifySeq(notifySeq);
					notifyBean.setCreateDate(com.lgcns.ikep4.support.searchpreprocessor.util.DateUtil.getToday());
					notifyService.insertNotify(notifyBean);
				}
			}
			
			Iterator iteratorTrans		= transitionHsh.keySet().iterator();
			while (iteratorTrans.hasNext()) {
				String paramKey = iteratorTrans.next().toString();
				TransitionBean transBean = (TransitionBean) transitionHsh.get(paramKey);
				deployTransitionDao.insertTransition(transBean);
			}			
			isSuccess = true;
			
			log.info(deployLog);
		} catch (Exception e) {
			isSuccess = false;
			log.info(deployLog);
			log.info("# DeployServiceImpl.deployProcess : ", e);
		}

		return isSuccess;
	}

	/**
	 * @param processId
	 * @return
	 */
	public boolean undeployProcess(String processId) {
		boolean isSuccess = false;

		return isSuccess;
	}

	/**
	 * 프로세스에 관련한  모두 데이터를 삭제한다.
	 * @param processId
	 * @return
	 */
	public boolean deleteProcess(String processId) {
		ProcessBean processBean = new ProcessBean();
		processBean.setProcessId(processId);
		return deployProcessDao.remove(processBean);
	}
	
	/**
	 * 프로세스에 관련한  모두 데이터를 삭제한다.
	 * @param processId
	 * @return
	 */
	public boolean deleteProcess(String processId, String processVer) {
		ProcessBean processBean = new ProcessBean();
		processBean.setProcessId(processId);
		if(processVer != null && !processVer.equals("")) {
			processBean.setProcessVer(processVer);
		}
		return deployProcessDao.remove(processBean);
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param activityBean
	 * @param activityNode
	 * @return
	 */
	public ActivityBean setupNotify(ActivityBean activityBean, Node activityNode, String notifyPoint) {
		// Assign Notify를 처리한다. 
		XmlService xmlService				= new XmlService();
		if (xmlService.isExistNode(activityNode, "ExtendedAttributes/ExtendedAttribute/HumanActivityExtendedAttribute/" + notifyPoint)) {
			NodeList nodeNotifyList 	= xmlService.getNodeList(activityNode, "ExtendedAttributes/ExtendedAttribute/HumanActivityExtendedAttribute/" + notifyPoint);
			Node nodeNotifyPoint			= nodeNotifyList.item(0);
			
			// Email을 처리한다. 
			if(xmlService.isAttribute(nodeNotifyPoint, "useEmail")) {
				String subject						= "";
				String content						= "";
				if(xmlService.isExistNode(nodeNotifyPoint, "Subject")) {
					NodeList nodeEmailSubejctList	= xmlService.getNodeList(nodeNotifyPoint, "Subject");
					Node nodeSubject				= nodeEmailSubejctList.item(0);
					subject							= xmlService.getAttrValue(nodeSubject);
				}
				if(xmlService.isExistNode(nodeNotifyPoint, "Contents")) {
					NodeList nodeEmailContentList	= xmlService.getNodeList(nodeNotifyPoint, "Contents");
					Node nodeContent				= nodeEmailContentList.item(0);
					content							= xmlService.getAttrValue(nodeContent);
				}
				
				if(xmlService.isExistNode(nodeNotifyPoint, "To/EmailAddress")) {
					NotifyBean notifyBean		= new NotifyBean();
					NodeList nodeToEmailList	= xmlService.getNodeList(nodeNotifyPoint, "To/EmailAddress");
					for(int k=0;k<nodeToEmailList.getLength();k++) {
						Node nodeToAddress		= nodeToEmailList.item(k);
						String toAddress		= xmlService.getAttrValue(nodeToAddress); 
						notifyBean.setProcessId(activityBean.getProcessId());
						notifyBean.setProcessVer(activityBean.getProcessVer());
						notifyBean.setActivityId(activityBean.getActivityId());
						notifyBean.setNotifyPoint(notifyPoint);
						notifyBean.setNotifyType("EMAIL");
						notifyBean.setEmailType("TO");
						notifyBean.setEmailAddress(toAddress);
						notifyBean.setNotifyTitle(subject);
						notifyBean.setNotifyContent(content);
						activityBean.addNotify(notifyBean);
					}
				}
				if(xmlService.isExistNode(nodeNotifyPoint, "Cc/EmailAddress")) {
					NotifyBean notifyBean		= new NotifyBean();
					NodeList nodeCcEmailList	= xmlService.getNodeList(nodeNotifyPoint, "Cc/EmailAddress");
					for(int k=0;k<nodeCcEmailList.getLength();k++) {
						Node nodeCcAddress		= nodeCcEmailList.item(k);
						String ccAddress		= xmlService.getAttrValue(nodeCcAddress); 
						notifyBean.setProcessId(activityBean.getProcessId());
						notifyBean.setProcessVer(activityBean.getProcessVer());
						notifyBean.setActivityId(activityBean.getActivityId());
						notifyBean.setNotifyPoint(notifyPoint);
						notifyBean.setNotifyType("EMAIL");
						notifyBean.setEmailType("CC");
						notifyBean.setEmailAddress(ccAddress);
						notifyBean.setNotifyTitle(subject);
						notifyBean.setNotifyContent(content);	
						activityBean.addNotify(notifyBean);
					}
				}
				if(xmlService.isExistNode(nodeNotifyPoint, "Bcc/EmailAddress")) {
					NotifyBean notifyBean		= new NotifyBean();
					NodeList nodeBccEmailList	= xmlService.getNodeList(nodeNotifyPoint, "Bcc/EmailAddress");
					for(int k=0;k<nodeBccEmailList.getLength();k++) {
						Node nodeBccAddress		= nodeBccEmailList.item(k);
						String bccAddress		= xmlService.getAttrValue(nodeBccAddress);
						notifyBean.setProcessId(activityBean.getProcessId());
						notifyBean.setProcessVer(activityBean.getProcessVer());
						notifyBean.setActivityId(activityBean.getActivityId());
						notifyBean.setNotifyPoint(notifyPoint);
						notifyBean.setNotifyType("EMAIL");
						notifyBean.setEmailType("BCC");
						notifyBean.setEmailAddress(bccAddress);
						notifyBean.setNotifyTitle(subject);
						notifyBean.setNotifyContent(content);	
						activityBean.addNotify(notifyBean);
					}
				}
			}
			
			// SMS을 처리한다. 
			if(xmlService.isAttribute(nodeNotifyPoint, "useSms")) {
				String notifyContent				= "";
				if(xmlService.isExistNode(nodeNotifyPoint, "SmsContents")) {
					NodeList nodeSmsContentsList	= xmlService.getNodeList(nodeNotifyPoint, "SmsContents");
					Node nodeSmsContents			= nodeSmsContentsList.item(0);
					notifyContent					= xmlService.getAttrValue(nodeSmsContents);
				}	
				
				if(xmlService.isExistNode(nodeNotifyPoint, "SmsRecipient/MobileNumber")) {
					NotifyBean notifyBean		= new NotifyBean();
					NodeList nodeMobileList	= xmlService.getNodeList(nodeNotifyPoint, "SmsRecipient/MobileNumber");
					for(int k=0;k<nodeMobileList.getLength();k++) {
						Node nodeMobileNumber		= nodeMobileList.item(k);
						String mobileNumber			= xmlService.getAttrValue(nodeMobileNumber);
						notifyBean.setProcessId(activityBean.getProcessId());
						notifyBean.setProcessVer(activityBean.getProcessVer());
						notifyBean.setActivityId(activityBean.getActivityId());	
						notifyBean.setNotifyPoint(notifyPoint);
						notifyBean.setNotifyType("SMS");
						notifyBean.setMobileNumber(mobileNumber);
						notifyBean.setNotifyContent(notifyContent);
						activityBean.addNotify(notifyBean);
					}
				}						
			}
		}
		return activityBean;
	}
}
