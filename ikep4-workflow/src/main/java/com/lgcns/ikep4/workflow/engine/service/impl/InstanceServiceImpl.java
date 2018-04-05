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
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.service.AdminEmailService;
import com.lgcns.ikep4.workflow.engine.dao.InstanceDao;
import com.lgcns.ikep4.workflow.engine.dao.InstanceLogDao;
import com.lgcns.ikep4.workflow.engine.dao.InstanceVarDao;
import com.lgcns.ikep4.workflow.engine.dao.ProcessDao;
import com.lgcns.ikep4.workflow.engine.dao.ProcessStepDao;
import com.lgcns.ikep4.workflow.engine.dao.ServiceDao;
import com.lgcns.ikep4.workflow.engine.dao.WorkListDao;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.model.InstanceLogBean;
import com.lgcns.ikep4.workflow.engine.model.InstanceVarBean;
import com.lgcns.ikep4.workflow.engine.model.NotifyBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessReqBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessStepBean;
import com.lgcns.ikep4.workflow.engine.model.ServiceBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.NotifyService;
import com.lgcns.ikep4.workflow.engine.service.SplitTrackService;
import com.lgcns.ikep4.workflow.engine.util.DomUtil;
import com.lgcns.ikep4.workflow.engine.util.XmlService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InstanceServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $ InstanceServiceImpl.java 오후 7:08:43
 */
@Service("instanceService")
public class InstanceServiceImpl implements InstanceService {
	
	Log log = LogFactory.getLog(InstanceServiceImpl.class);

	@Autowired
	private ServiceDao serviceDao;	
	
	@Autowired
	private InstanceDao instanceDao;	
	
	@Autowired
	private InstanceLogDao instanceLogDao;	
	
	@Autowired
	private ProcessStepDao processStepDao;	
	
	@Autowired
	private InstanceVarDao instanceVarDao;	
	
	@Autowired
	private WorkListDao workListDao;	
	
	@Autowired
	private ProcessDao processDao;		
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private NotifyService notifyService;
	
//	@Autowired
//	private SmsServiceImpl SmsServiceImpl;
	
	@Autowired
	private AdminEmailService adminEmailService;
	
	@Autowired
	private SplitTrackService splitTrackService;	
	

	 /* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(WorkItemBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	public WorkItemBean read(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.Serializable)
	 */
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(WorkItemBean object) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	public WorkItemBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 프로세스를 시작한다. 
	 */
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh) {
		Date todayDate			= new Date();
		return startProcess(processId, "1.0", title, userId, null, paramHsh, todayDate, null);
	}
	
	/**
	 * 프로세스를 시작한다. 
	 */
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh, Date openDate) {
		Date todayDate			= new Date();
		return startProcess(processId, "1.0", title, userId, null, paramHsh, todayDate, openDate);
	}	
	
	/**
	 * 프로세스를 시작한다. 
	 */
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh, Date executeDate, Date openDate) {
		Date todayDate			= new Date();
		return startProcess(processId, "1.0", title, userId, null, paramHsh, todayDate, openDate);
	}
	
	/**
	 * 프로세스를 시작한다. 
	 */
	public InstanceBean startProcess(String processId, String processVer, String title, String userId, Map<String, Object> paramHsh, Date executeDate) {
		Date todayDate			= new Date();
		return startProcess(processId, processVer, title, userId, null, paramHsh, todayDate, null);
	}
	
	
	/**
	 * 프로세스를 시작한다. 
	 */
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> appKeyHsh, Map<String, Object> paramHsh, Date executeDate) {
		Date todayDate			= new Date();		
		return startProcess(processId, "1.0", title, userId, appKeyHsh, paramHsh, todayDate, null);
	}
	
	 /* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.service.InstanceService#startProcess(java.lang.String, java.lang.String, java.lang.String)
	 */
	public InstanceBean startProcess(String processId, String processVer, String title, String userId, Map<String, Object> appKeyHsh, Map<String, Object> paramHsh, Date executeDate, Date openDate) {
		// TODO Auto-generated method stub
		String msg	= 	"\n################################################################################" +
						"\n# Start startProcess.. " +
						"\n################################################################################";
		log.info(msg);			
		
		// 프로세스 버전문제로 서비스할 프로세스를 찾는다. 
		ServiceBean serviceBean			= new ServiceBean();
		serviceBean.setServiceId(processId);
		List<ServiceBean> serviceList	= serviceDao.selectStartSerice(serviceBean);
		
		String instanceId 				= "";
		String instanceLogId 			= "";		
		InstanceBean instanceBean		= new InstanceBean();
		
		if(0 < serviceList.size()) {
			serviceBean					= serviceList.get(0);
			processId					= serviceBean.getProcessId();
			processVer					= serviceBean.getProcessVer();

			instanceBean				= new InstanceBean();
			try {
				instanceId 				= idgenService.getNextId();
				
				Calendar cal			= Calendar.getInstance();
				long millisTime			= cal.getTimeInMillis();		
				String timeHex			= Long.toHexString(millisTime).toUpperCase();
				instanceId				= timeHex + instanceId;
				log.info("# instanceId : " + instanceId);
				
				instanceBean.setCommand("StartProcess");
				instanceBean.setInstanceId(instanceId);
				instanceBean.setProcessId(processId);
				instanceBean.setProcessVer(processVer);
				instanceBean.setTitle(title);
				instanceBean.setPerformerId(userId);
				instanceBean.setCreateDate(executeDate);
				instanceBean.setState("RUNNING");
				if(openDate != null) {
					instanceBean.setOpenDate(openDate);
				} else {
					Date openMaxDate	= new Date();
					openMaxDate.setYear(9999);
					openMaxDate.setMonth(12);
					openMaxDate.setDate(31);
				}
				
				log.info("#### appKeyHsh : " + appKeyHsh);
				
				if(appKeyHsh != null) {
					if(appKeyHsh.containsKey("appKey01")) {
						instanceBean.setAppKey01(appKeyHsh.get("appKey01").toString());
					} else if(appKeyHsh.containsKey("appKey02")) {
						instanceBean.setAppKey02(appKeyHsh.get("appKey02").toString());
					} else if(appKeyHsh.containsKey("appKey03")) {
						instanceBean.setAppKey03(appKeyHsh.get("appKey03").toString());
					} else if(appKeyHsh.containsKey("appKey04")) {
						instanceBean.setAppKey04(appKeyHsh.get("appKey04").toString());
					} else if(appKeyHsh.containsKey("appKey05")) {
						instanceBean.setAppKey05(appKeyHsh.get("appKey05").toString());
					} else if(appKeyHsh.containsKey("appKey06")) {
						instanceBean.setAppKey06(appKeyHsh.get("appKey06").toString());
					} else if(appKeyHsh.containsKey("appKey07")) {
						instanceBean.setAppKey07(appKeyHsh.get("appKey07").toString());
					} else if(appKeyHsh.containsKey("appKey08")) {
						instanceBean.setAppKey08(appKeyHsh.get("appKey08").toString());
					} else if(appKeyHsh.containsKey("appKey09")) {
						instanceBean.setAppKey09(appKeyHsh.get("appKey09").toString());
					} else if(appKeyHsh.containsKey("appKey10")) {
						instanceBean.setAppKey10(appKeyHsh.get("appKey10").toString());
					}					
				}
				
				instanceDao.startProcess(instanceBean);
				InstanceBean instanceBeanTemp	= instanceDao.select(instanceBean);
				instanceBean.setProcessId(instanceBeanTemp.getProcessId());
				instanceBean.setTitle(instanceBeanTemp.getTitle());				
				
				instanceLogId		= workProcessing(instanceBean, paramHsh, executeDate);
				instanceBean.setInstanceLogId(instanceLogId);
			} catch(Exception e) {
				log.info("# InstanceServiceImpl.startProcess : ", e);
			}
		//} else {
			// 서비스할 프로세스가 없음...
		}
		msg	= 	"\n################################################################################" +
				"\n# End startProcess.. instanceLogId = " + instanceLogId + 
				"\n################################################################################";
		log.info(msg);			
		return instanceBean;
	}
	
	/**
	 * 
	 */
	public void completeWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh) {
		Date todayDate			= new Date();
		completeWorkItem(processInsId, activityId, insLogId, userId, paramHsh, todayDate); 
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param processId
	 * @param activityId
	 * @param userId
	 * @param appKeyMap
	 * @param paramHsh
	 * @param executeDate
	 */
	public void completeWorkItem(String processId, String activityId, String userId, Map<String, Object> appKeyMap, Map<String, Object> paramHsh, Date executeDate) {
		String msg	= 	"\n################################################################################" +
						"\n# Start Interface CompleteWorkItem.. " +
						"\n################################################################################";
		log.info(msg);
		
		if(executeDate == null || executeDate.equals("")) {
			executeDate				= new Date();
		}
		String processInsId			= "";
		String insLogId				= "";
		
		// 프로세스 버전문제로 서비스할 프로세스를 찾는다. 
		ServiceBean serviceBean			= new ServiceBean();
		serviceBean.setServiceId(processId);
		if(appKeyMap.containsKey("appKey01")) {
			serviceBean.setAppKey01(appKeyMap.get("appKey01").toString());
		} else if(appKeyMap.containsKey("appKey02")) {
			serviceBean.setAppKey02(appKeyMap.get("appKey02").toString());
		} else if(appKeyMap.containsKey("appKey03")) {
			serviceBean.setAppKey03(appKeyMap.get("appKey03").toString());
		} else if(appKeyMap.containsKey("appKey04")) {
			serviceBean.setAppKey04(appKeyMap.get("appKey04").toString());
		} else if(appKeyMap.containsKey("appKey05")) {
			serviceBean.setAppKey05(appKeyMap.get("appKey05").toString());
		} else if(appKeyMap.containsKey("appKey06")) {
			serviceBean.setAppKey06(appKeyMap.get("appKey06").toString());
		} else if(appKeyMap.containsKey("appKey07")) {
			serviceBean.setAppKey07(appKeyMap.get("appKey07").toString());
		} else if(appKeyMap.containsKey("appKey08")) {
			serviceBean.setAppKey08(appKeyMap.get("appKey08").toString());
		} else if(appKeyMap.containsKey("appKey09")) {
			serviceBean.setAppKey09(appKeyMap.get("appKey09").toString());
		} else if(appKeyMap.containsKey("appKey10")) {
			serviceBean.setAppKey10(appKeyMap.get("appKey10").toString());
		}
		List<ServiceBean> serviceList	= serviceDao.completeStartSerice(serviceBean);
		
		if(1 == serviceList.size()) {
			serviceBean					= serviceList.get(0);
			processInsId				= serviceBean.getInstanceId();
			insLogId					= serviceBean.getLogId();
			completeWorkItem(processInsId, activityId, insLogId, userId, paramHsh, executeDate); 
		//} else if(1<serviceList.size()) {
			// 에러로그 처리 : 애매모호한 경우 
		}
		msg	= 		"\n################################################################################" +
					"\n# End Interface CompleteWorkItem.. " +
					"\n################################################################################";
		log.info(msg);		
	}	
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 */
	public void completeWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh, Date executeDate) {
		String msg	= 	"\n################################################################################" +
						"\n# Start completeWorkItem.. " +
						"\n################################################################################";
		log.info(msg);		
		InstanceBean instanceBean		= new InstanceBean();
		instanceBean.setCommand("CompleteWorkItem");
		instanceBean.setInstanceId(processInsId);
		instanceBean.setActivityId(activityId);
		instanceBean.setInstanceLogId(insLogId);
		instanceBean.setPerformerId(userId);

		InstanceBean instanceBeanTemp	= instanceDao.select(instanceBean);
		instanceBean.setProcessId(instanceBeanTemp.getProcessId());
		instanceBean.setTitle(instanceBeanTemp.getTitle());	
		instanceBean.setProcessVer(instanceBeanTemp.getProcessVer());
		instanceBean = saveVarSchema(instanceBean, paramHsh);
		
		workProcessing(instanceBean, paramHsh, executeDate);
		 msg	= 	"\n################################################################################" +
					"\n# End completeWorkItem.. " +
					"\n################################################################################";
		 log.info(msg);			
	}
	
	/**
	 * 
	 */
	public void deleteInstance(String instanceId) {
		// TODO Auto-generated method stub
		InstanceBean instanceBean		= new InstanceBean();
		instanceBean.setInstanceId(instanceId);
		instanceDao.deleteInstance(instanceBean);
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 * @param paramHsh
	 * @return
	 */
	public InstanceBean saveVarSchema(InstanceBean instanceBean, Map<String, Object> paramHsh) {
		log.info("### saveVarSchema : " + instanceBean.getInstanceLogId());
		List<WorkItemBean> list		= workListDao.select(instanceBean.getInstanceLogId());
		

		String varSchema			= "";
		
		
		if(0 < list.size()) {
			// 검색된 데이터가 있는경우
			WorkItemBean workItemBean	= list.get(0);  
			instanceBean.setProcessId(workItemBean.getProcessId());
			
			if(!workItemBean.getVarSchema().equals("") ) { 
				instanceBean.setVarSchema(workItemBean.getVarSchema());
				varSchema				= generatorVarSchema(instanceBean, paramHsh, "CompleteWorkItem");
				instanceBean.setVarSchema(varSchema);
				instanceDao.updateVarSchema(instanceBean);				
			}
		} else {
			// 검색된 데이터가 없는 경우
			ProcessBean processBean				= new ProcessBean();
			processBean.setProcessId(instanceBean.getProcessId());
			List<ProcessBean> processList		= processDao.select(processBean);
			processBean							= processList.get(0);
			instanceBean.setVarSchema(processBean.getVarDefine());

			varSchema						= generatorVarSchema(instanceBean, paramHsh, "StartProcess");
			instanceBean.setVarSchema(varSchema);
			instanceDao.updateVarSchema(instanceBean);
		}
		log.info("# ******************************************************");
		return instanceBean;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 * @param paramHsh
	 * @return
	 */
	public String generatorVarSchema(InstanceBean instanceBean, Map<String, Object> paramHsh, String processingType) {
		String varSchema		= "";
		Date todayDate			= new Date();
		
		XmlService xmlServiceInsVar	= new XmlService();
		xmlServiceInsVar.loadingXML(instanceBean.getVarSchema());
		if(paramHsh != null) {
			Iterator<String> iteratorParam	= paramHsh.keySet().iterator();
			
			InstanceVarBean instanceVarBean		= new InstanceVarBean();
			instanceVarBean.setProcessId(instanceBean.getProcessId());
			instanceVarBean.setProcessVer(instanceBean.getProcessVer());
			instanceVarBean.setInstanceId(instanceBean.getInstanceId());
			
			// ********************************************************************************************
			// Process Data를  처리하여 XML스키마 형태로 만든다.  
			// ********************************************************************************************			
			while(iteratorParam.hasNext()) {
				String paramKey			= iteratorParam.next().toString();
				Object paramObj			= paramHsh.get(paramKey);
				
				if(paramObj.getClass().getName().equalsIgnoreCase("java.util.HashMap")||paramObj.getClass().getName().equalsIgnoreCase("java.util.Map")) {
					HashMap<String, Object> arrayHsh	= (HashMap) paramHsh.get(paramKey);
					if(0 < arrayHsh.size()) {
	
						for(int i=0;i<arrayHsh.size();i++) {
							String arrayValue		= "";			
							if(arrayHsh.containsKey(""+i)) {
								if(xmlServiceInsVar.isExistNode("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue/Item[@Index='"+i+"']")) {
									// 배열 데이터가 있는 경우 데이터를 업데이트 한다.
									arrayValue	= arrayHsh.get("" + i).toString();
									if(!arrayValue.equals("") && arrayValue != null) {
										xmlServiceInsVar.setNodeValue("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue/Item[@Index='"+i+"']", arrayValue);
									}
								} else {
									// 배열 데이터가 없는 경우 데이터를 생성한다. 
									arrayValue	= arrayHsh.get("" + i).toString();
									if(!arrayValue.equals("") && arrayValue != null) {
										xmlServiceInsVar.appendElement("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue", "Item", arrayValue, "Index", (""+i));
									}
								}
							}else {
								// 배열 데이터가 없는 경우 데이터를 생성한다.
								xmlServiceInsVar.appendElement("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue", "Item", "", "Index", (""+i));
							}
						}
					}
				} else {
					String paramValue	= paramHsh.get(paramKey).toString();
	
					if(xmlServiceInsVar.isExistNode("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue")) {
						// 데이터가 있는 경우 데이터를 업데이트 한다. 
						if(!paramValue.equals("") && paramValue != null) {
							xmlServiceInsVar.setNodeValue("DataFields/DataField[@Id='"+ paramKey + "']/CurrentValue", paramValue);
						}
					} else {
						// 데이터가 있는 경우 데이터를 생성 한다.
						if(!paramValue.equals("") && paramValue != null) {
							xmlServiceInsVar.appendElement("DataFields/DataField[@Id='"+ paramKey + "']", "CurrentValue", paramValue);
						}					
					}
				}
			}
			DomUtil domUtil 			= new DomUtil();
			varSchema					= domUtil.generationDoc(xmlServiceInsVar.getDocument());	
			xmlServiceInsVar.loadingXML(varSchema);
			
			instanceBean.setVarSchema(varSchema);
//			log.info("#################################################################################");
//			log.info("# Generator VarSchema.... : " + instanceBean);
//			log.info("# paramHsh.... : " + paramHsh);
//			log.info("#################################################################################");
			// ********************************************************************************************
			// Process Data를  처리하여 DB에 반영한다. 
			// ********************************************************************************************			
			
			NodeList schemaNodeList		= xmlServiceInsVar.getNodeList("DataFields/DataField");
			for(int i=0;i<schemaNodeList.getLength();i++) {
				Node dataNode			= schemaNodeList.item(i);
				String nodeId			= xmlServiceInsVar.getAttribute(dataNode, "Id");
				String isArray			= xmlServiceInsVar.getAttribute(dataNode, "IsArray");
				String nodeType			= xmlServiceInsVar.getAttribute(dataNode, "DataType/BasicType", "Type");
	
				if(isArray.equalsIgnoreCase("true")) {
					// 배열변수인 경우
					instanceVarBean.setVarType(nodeType);
					NodeList itemNodeList		= xmlServiceInsVar.getNodeList(dataNode, "CurrentValue/Item");
					instanceVarBean.setInstanceId(instanceBean.getInstanceId());
					instanceVarBean.setVarId(nodeId);
					instanceVarDao.delete(instanceVarBean);
					
					if(0<itemNodeList.getLength()) {
						for(int j=0;j<itemNodeList.getLength();j++) {
							Node itemNode			= itemNodeList.item(j);
							String itemIndex		= xmlServiceInsVar.getAttribute(itemNode, "Index");
							String itemValue		= xmlServiceInsVar.getAttrValue(itemNode.getParentNode(), "Item[@Index='" + j + "']");
							
							instanceVarBean.setVarId(nodeId);
							instanceVarBean.setVarKey(itemIndex);
							instanceVarBean.setVarValue(itemValue);
	
							if(processingType.equalsIgnoreCase("StartProcess")) {
								String seqId = idgenService.getNextId();
								instanceVarBean.setSeqId(seqId);
								instanceVarBean.setCreateDate(todayDate);
								instanceVarDao.create(instanceVarBean);					
							} else {
								String seqId = idgenService.getNextId();
								instanceVarBean.setSeqId(seqId);							
								instanceVarBean.setCreateDate(todayDate);
								instanceVarDao.create(instanceVarBean);
							}					
						}
					} else {
						String seqId = idgenService.getNextId();
						instanceVarBean.setSeqId(seqId);					
						instanceVarBean.setVarId(nodeId);
						instanceVarBean.setVarKey("");
						instanceVarBean.setVarValue("");					
						instanceVarBean.setCreateDate(todayDate);
						instanceVarDao.create(instanceVarBean);						
					}
				} else {
					// String 변수인 경우
					String currentValue		= xmlServiceInsVar.getAttrValue(dataNode, "CurrentValue");
					instanceVarBean.setVarType(nodeType);
					instanceVarBean.setVarId(nodeId);
					instanceVarBean.setVarValue(currentValue);					
					if(processingType.equalsIgnoreCase("StartProcess")) {
						String seqId = idgenService.getNextId();
						instanceVarBean.setSeqId(seqId);
						instanceVarBean.setVarKey("");
						instanceVarBean.setCreateDate(todayDate);
						instanceVarDao.create(instanceVarBean);					
					} else {
						instanceVarBean.setUpdateDate(todayDate);
						instanceVarBean.setVarKey("");
						instanceVarDao.update(instanceVarBean);
					}
				}
			}
			
			// ********************************************************************************************
			// ProcessKeyData 데이터 처리
			// ********************************************************************************************			
			NodeList processKeyNodeList		= xmlServiceInsVar.getNodeList("DataFields/ProcessKeyDatas/ProcessKeyData");
			for(int i=0;i<processKeyNodeList.getLength();i++) {
				Node processKeyNode			= processKeyNodeList.item(i);
				Node expressionNode			= xmlServiceInsVar.getSingleNode(processKeyNode, "Expression");
				String processKeyCount		= xmlServiceInsVar.getAttribute(processKeyNode, "Index");
				String expression			= xmlServiceInsVar.getAttrValue(expressionNode);
	
				StringTokenizer tokenList 	= new StringTokenizer(expression);
				String keyDataValue			= "";
				if(tokenList.countTokens() == 0) {
					keyDataValue			= "";
				} else if(tokenList.countTokens() == 1) {
					//========================================================= 
					// ProcessKeyData Expression 파싱 처리 부분 (배열변수 처리 보강 필요 : array[count] 경우 
					//=========================================================						
					String token			= tokenList.nextToken();
					if(xmlServiceInsVar.isExistNode("DataFields/DataField[@Id='"+token+"']")) {
						keyDataValue		= xmlServiceInsVar.getAttrValue("DataFields/DataField[@Id='"+token+"']/CurrentValue");
					} else {
						keyDataValue		= "";
					}
//				} else {
					//while(tokenList.hasMoreTokens()) {
						//String token			= tokenList.nextToken();
						//========================================================= 
						// ProcessKeyData Expression 파싱 처리 부분	
						//=========================================================					
					//}
				}
				
				if(processKeyCount.equalsIgnoreCase("1")) {
					instanceBean.setAppKey01(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("2")) {
					instanceBean.setAppKey02(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("3")) {
					instanceBean.setAppKey03(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("4")) {
					instanceBean.setAppKey04(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("5")) {
					instanceBean.setAppKey05(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("6")) {
					instanceBean.setAppKey06(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("7")) {
					instanceBean.setAppKey07(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("8")) {
					instanceBean.setAppKey08(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("9")) {
					instanceBean.setAppKey09(keyDataValue);
				} else if(processKeyCount.equalsIgnoreCase("10")) {
					instanceBean.setAppKey10(keyDataValue);
				}
				instanceDao.updateKeyData(instanceBean);
			}
			varSchema					= domUtil.generationDoc(xmlServiceInsVar.getDocument());
		}
		return varSchema;
	}
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 */
	public String workProcessing(InstanceBean instanceBean, Map<String, Object> paramHsh, Date executeDate) {

		boolean isExecute = false;
		String logId 			= ""; 
		//InstanceBean instanceBeanTemp	= instanceDao.select(instanceBean);
		String command			= instanceBean.getCommand();
		String activityId		= instanceBean.getActivityId();
		String processId		= instanceBean.getProcessId();
		String processVer		= instanceBean.getProcessVer();
		String title			= instanceBean.getTitle();

		ProcessReqBean processReqBean	= new ProcessReqBean();
		processReqBean.setProcessId(processId);
		processReqBean.setProcessVer(processVer);
		processReqBean.setActivityId(activityId);
		List<ProcessStepBean> processStepList = null;
		instanceBean.setProcessId(processId);
		instanceBean.setTitle(title);

		if(command.equalsIgnoreCase("StartProcess")) {
			processStepList		= processStepDao.selectStart(processReqBean);
		} else {
			processStepList		= processStepDao.select(processReqBean);
		}
		int count				= 0;
		String msg = 	"\n########################################################################" +
						"\n# 받은 instanceBean 데이터 " +	command + 
						"\n########################################################################" +		
						"\n# instanceBean.getInstanceLogId() 	: " + instanceBean.getInstanceLogId() +
						"\n# instanceBean.getInstanceId() 		: " + instanceBean.getInstanceId() +
						"\n# instanceBean.getProcessId()		: " + instanceBean.getProcessId() +
						"\n# instanceBean.getProcessVer()		: " + instanceBean.getProcessVer() +
						"\n# instanceBean.getActivityId()		: " + instanceBean.getActivityId() +
						"\n# instanceBean.getTitle()			: " + instanceBean.getTitle() +
						"\n# instanceBean.getPerformerId()		: " + instanceBean.getPerformerId() +
						"\n# processStepList.size()		: " + processStepList.size() +		
						"\n########################################################################";
		
		msg			+=	"\n파라메터 데이터 : " + paramHsh;
		
		log.info(msg);
		
		for(int i=0;i<processStepList.size();i++) {
			ProcessStepBean processStepBean		= processStepList.get(i);
			msg = 		"\n########################################################################" + 
						"\n# 진행할 단계" + 
						"\n########################################################################" +
						"\n# processStepBean.getActId() : " + processStepBean.getActId() +
						"\n# processStepBean.getActName() : " + processStepBean.getActName() + 
						"\n# processStepBean.getActType() : " + processStepBean.getActType()  + 
						"\n# processStepBean.getConditionExpression() : " + processStepBean.getConditionExpression() + 
						"\n# processStepBean.getConditionType() : " + processStepBean.getConditionType() + 
						"\n# processStepBean.getNextActId() : " + processStepBean.getNextActId() + 
						"\n# processStepBean.getNextActName() : " + processStepBean.getNextActName() + 
						"\n# processStepBean.getNextActType() : " + processStepBean.getNextActType() + 
						"\n# processStepBean.getNextSubType() : " + processStepBean.getNextSubType() + 
						"\n# processStepBean.getProcessId() : " + processStepBean.getProcessId() + 			
						"\n# processStepBean.getSubType() : " + processStepBean.getSubType() + 
						"\n########################################################################";							
			log.info(msg);
			String nextSubType					= 	processStepBean.getNextSubType();
			String nextActType					= 	processStepBean.getNextActType();

			if(count == 0) {
				InstanceLogBean instanceLogBean	= new InstanceLogBean();
				if(command.equalsIgnoreCase("StartProcess")) {
					// 로그 ID를 발급 받는다.
					logId 						= idgenService.getNextId();			
					
					// 현재 instance log 저장하고...
					instanceLogBean.setInstanceLogId(logId);
					instanceLogBean.setActivityId(processStepBean.getActId());
					instanceLogBean.setProcessId(instanceBean.getProcessId());
					instanceLogBean.setProcessVer(instanceBean.getProcessVer());
					instanceLogBean.setInstanceId(instanceBean.getInstanceId());
					instanceLogBean.setPerformerId(instanceBean.getPerformerId());
					instanceLogBean.setMiSeq("0");
					instanceLogBean.setCreateDate(executeDate);

					if(processStepBean.getSubType().equalsIgnoreCase("StartEvent")) {
						instanceLogBean.setState("COMPLETE");
					} else {
						instanceLogBean.setState("ASSIGN");
					}

					instanceLogDao.insert(instanceLogBean);
					
					// 인스턴스 초기 변수값 스키마를 저장한다. 
					instanceBean.setInstanceLogId(logId);
					instanceBean.setVarSchema(processStepBean.getVarDefine());
					instanceBean = saveVarSchema(instanceBean, paramHsh);

				} else if(command.equalsIgnoreCase("CompleteWorkItem")) {
					logId		= instanceBean.getInstanceLogId();
					instanceLogBean.setInstanceLogId(instanceBean.getInstanceLogId());
					instanceLogBean.setPerformerId(instanceBean.getPerformerId());
					instanceLogBean.setState("COMPLETE");
					instanceLogDao.update(instanceLogBean);	
					// Notify가 있을경우 SMS/Email 형태로 처리한다. (완료시:FinishedNotify)
					mailSend(instanceBean, "FinishNotify");
					
					
					// 이전 ToDo 리스트 데이터를 삭제한다. 
					WorkItemBean workItemDelBean		= new WorkItemBean();
					workItemDelBean.setInstanceId(instanceBean.getInstanceId());
					workItemDelBean.setInsLogId(instanceBean.getInstanceLogId());
					workItemDelBean.setActivityId(instanceBean.getActivityId());
					workListDao.delete(workItemDelBean);
				}
			}
			
			if(nextSubType.equalsIgnoreCase("ACTIVITY") && (nextActType == null || nextActType.equals(""))) {
				log.info("# Activity 처리.....");
				//if(!isExecute) {
					Map<String, Object> returnHash = conditionProcessing("Activity", isExecute, instanceBean, processStepBean, executeDate, paramHsh);
					
					if(returnHash.containsKey("isExecute")) {
						String strExecute	= returnHash.get("isExecute").toString();
						if(strExecute.equalsIgnoreCase("TRUE")) {
							isExecute = true;
						} else {
							isExecute = false;
						}
					}
					if(returnHash.containsKey("logId")) {
						logId				= returnHash.get("logId").toString();
					}
				//}
			} else if(nextSubType.equalsIgnoreCase("ENDEVENT") && nextActType == null) {
				log.info("# EndEvent 처리.....");
				// 로그 ID를 발급 받는다. 
				if(!isExecute) {
					String logNextId = idgenService.getNextId();
					// 다음 instance log 저장...
					InstanceLogBean instanceLogNextBean		= new InstanceLogBean();
					instanceLogNextBean.setInstanceLogId(logNextId);
					instanceLogNextBean.setActivityId(processStepBean.getNextActId());
					instanceLogNextBean.setProcessId(instanceBean.getProcessId());
					instanceLogNextBean.setProcessVer(instanceBean.getProcessVer());
					instanceLogNextBean.setInstanceId(instanceBean.getInstanceId());
					instanceLogNextBean.setPerformerId(instanceBean.getPerformerId());
					instanceLogNextBean.setCreateDate(executeDate);
					instanceLogNextBean.setMiSeq("0");
					instanceLogNextBean.setState("COMPLETE");
					instanceLogDao.insert(instanceLogNextBean);
					
					instanceBean.setState("COMPLETE");
					instanceBean.setFinishedDate(executeDate);
					instanceDao.update(instanceBean);
				}
				
			} else {
				log.info("# Split/Join 처리.....");
				//if(!isExecute) {
					Map<String, Object> returnHash = conditionProcessing("NotActivity", isExecute, instanceBean, processStepBean, executeDate, paramHsh);
					
					if(returnHash.containsKey("isExecute")) {
						String strExecute	= returnHash.get("isExecute").toString();
						if(strExecute.equalsIgnoreCase("TRUE")) {
							isExecute = true;
						} else {
							isExecute = false;
						}
					}
					if(returnHash.containsKey("logId")) {
						logId				= returnHash.get("logId").toString();
					}
				//}
			}
			count++;
		}
		return logId;
	}

	/**
	 * 분기조건을 처리한다. 
	 * @param command
	 * @param isExecute
	 * @param instanceBean
	 * @param processStepBean
	 * @param executeDate
	 * @param paramHsh
	 * @return
	 */
	public Map<String, Object> conditionProcessing(String command, boolean isExecute, InstanceBean instanceBean, ProcessStepBean processStepBean, Date executeDate, Map<String, Object> paramHsh) {
		HashMap<String, Object> returnHash			= new HashMap<String, Object>();
		String logId		= instanceBean.getInstanceLogId();
		boolean isReturn	= isExecute;
		String msg		= "";
		// 조건 판단을 한다. 
		msg	= 	"\n################################################################################" +
				"\n# Start Condition Processing.. " +
				"\n################################################################################" +
				"\n# processStepBean.getConditionType() : " + processStepBean.getConditionType() +
				"\n# processStepBean.getConditionExpression() : " + processStepBean.getConditionExpression() +
				"\n# processStepBean.getGatewayType() : " + processStepBean.getGatewayType() +
				"\n################################################################################";
		log.info(msg);
		
		if(processStepBean.getConditionType() != null) {
			if(processStepBean.getConditionType().equalsIgnoreCase("CONDITION")) {
				// Condition Flow
				if(processStepBean.getConditionExpression() != null) {
					String conditionExpression = processStepBean.getConditionExpression();
					String result	= "";
					if(processStepBean.getGatewayType().equalsIgnoreCase("AND")) {
						result 		= parseExpression(instanceBean, conditionExpression, "AND");
						if(command.equalsIgnoreCase("Activity")) {
							logId = runActivity(instanceBean, processStepBean, executeDate);
						} else {
							logId = returnCall(instanceBean, processStepBean, executeDate, paramHsh);
						}
					} else if(processStepBean.getGatewayType().equalsIgnoreCase("OR")) {
						result 		= parseExpression(instanceBean, conditionExpression, "OR");
						if(result.equalsIgnoreCase("TRUE")) {
							if(command.equalsIgnoreCase("Activity")) {
								logId = runActivity(instanceBean, processStepBean, executeDate);
							} else {
								logId = returnCall(instanceBean, processStepBean, executeDate, paramHsh);
							}
							isReturn	= true;
						}								
					} else if(processStepBean.getGatewayType().equalsIgnoreCase("XOR")) {
						result 		= parseExpression(instanceBean, conditionExpression, "XOR");
						if(result.equalsIgnoreCase("TRUE")) {
							if(command.equalsIgnoreCase("Activity")) {
								logId = runActivity(instanceBean, processStepBean, executeDate);
							} else {
								logId = returnCall(instanceBean, processStepBean, executeDate, paramHsh);
							}
							isReturn	= true;
						}
					}		
					log.info("### CONDITION : " + result);
				}
			} else {
				if(processStepBean.getGatewayType().equalsIgnoreCase("AND")) {
					log.info("## AND OTHERWISE");
				} else if(processStepBean.getGatewayType().equalsIgnoreCase("OR")) {
					log.info("## OR OTHERWISE");
				} else if(processStepBean.getGatewayType().equalsIgnoreCase("XOR")) {
					log.info("## XOR OTHERWISE : " + isReturn + ", command : " + command);
					if(!isReturn) {
						if(command.equalsIgnoreCase("Activity")) {
							logId = runActivity(instanceBean, processStepBean, executeDate);
						} else {
							logId = returnCall(instanceBean, processStepBean, executeDate, paramHsh);
						}
					}
				}							
			}
		} else {
			// Normal Flow
			if(command.equalsIgnoreCase("Activity")) {
				logId = runActivity(instanceBean, processStepBean, executeDate);
			} else {
				logId = returnCall(instanceBean, processStepBean, executeDate, paramHsh);
			}
		}
		returnHash.put("isExecute", isReturn);
		returnHash.put("logId", logId);
		msg	= 	"\n################################################################################" +
				"\n# End Contition Processing.. " + returnHash + 
				"\n################################################################################";
		log.info(msg);
		
		return returnHash;
	}
	
	
	/**
	 * Activity 데이터를 생성시킨다. 
	 * @param instanceBean
	 * @param processStepBean
	 * @param executeDate
	 */
	public String runActivity(InstanceBean instanceBean, ProcessStepBean processStepBean, Date executeDate) {
		// 로그 ID를 발급 받는다. 
		String logNextId = idgenService.getNextId();
		
		// 다음 instance log 저장...
		InstanceLogBean instanceLogNextBean		= new InstanceLogBean();
		instanceLogNextBean.setInstanceLogId(logNextId);
		instanceLogNextBean.setActivityId(processStepBean.getNextActId());
		instanceLogNextBean.setProcessId(instanceBean.getProcessId());
		instanceLogNextBean.setProcessVer(instanceBean.getProcessVer());
		instanceLogNextBean.setInstanceId(instanceBean.getInstanceId());
		instanceLogNextBean.setPerformerId("");
		instanceLogNextBean.setCreateDate(executeDate);
		instanceLogNextBean.setState("ASSIGN");
		instanceLogNextBean.setMiSeq("0");
		instanceLogDao.insert(instanceLogNextBean);
		// MI를 처리해야한다. 
		
		// TODO 리스트에 저장
		WorkItemBean workItemBean			= new WorkItemBean();
		workItemBean.setInsLogId(logNextId);
		workItemBean.setInstanceId(instanceBean.getInstanceId());
		workItemBean.setProcessId(instanceBean.getProcessId());
		workItemBean.setProcessVer(instanceBean.getProcessVer());
		workItemBean.setActivityId(processStepBean.getNextActId());
		workItemBean.setActivityName(processStepBean.getNextActName());
		workItemBean.setTitle(instanceBean.getTitle());
		workItemBean.setCreateDate(executeDate);
		workItemBean.setState("ASSIGN");
		workItemBean.setMiSeq("0");
		workListDao.insert(workItemBean);
		// MI를 처리해야한다. 
		
		//if(processStepBean.g)
		
		
		
		
		
		// Notify가 있을경우 SMS/Email 형태로 처리한다. (완료시:AssignNotify)
		mailSend(instanceBean, "AssignNotify");
		
		return logNextId;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 * @param processStepBean
	 * @param executeDate
	 * @param paramHsh
	 */
	public String returnCall(InstanceBean instanceBean, ProcessStepBean processStepBean, Date executeDate, Map<String, Object> paramHsh) {
		log.info("# workProcessing : 재귀호출 시작 ======================================================");
		
		// 현재상태의 로그를 저장한다. 
		String logId		= "";
		String logNextId 	= idgenService.getNextId();
		
		
		// 다음 instance log 저장 (split, join)...
		InstanceLogBean instanceLogNextBean		= new InstanceLogBean();
		instanceLogNextBean.setInstanceLogId(logNextId);
		instanceLogNextBean.setActivityId(processStepBean.getNextActId());
		instanceLogNextBean.setProcessId(instanceBean.getProcessId());
		instanceLogNextBean.setProcessVer(instanceBean.getProcessVer());
		instanceLogNextBean.setInstanceId(instanceBean.getInstanceId());
		instanceLogNextBean.setPerformerId(instanceBean.getPerformerId());
		instanceLogNextBean.setCreateDate(executeDate);
		instanceLogNextBean.setState("COMPLETE");
		instanceLogNextBean.setMiSeq("0");
		instanceLogDao.insert(instanceLogNextBean);		
		
		log.info("#*****************************************************");
		log.info("# processStepBean : " + processStepBean);
		log.info("# processStepBean.getActType() : " + processStepBean.getActType());
		log.info("# processStepBean.getNextActId() : " + processStepBean.getNextActId());
		log.info("# processStepBean.getNextActType() : " + processStepBean.getNextActType());
		log.info("#*****************************************************");
		
//		if(processStepBean.getActType().equalsIgnoreCase("SPLIT")) {
//			String splitSeq 					= idgenService.getNextId();
//			SplitTrackBean splitTrackBean		= new SplitTrackBean();
//			splitTrackBean.setSplitSeq(splitSeq);
//			splitTrackBean.setInstanceId(instanceBean.getInstanceId());
//			splitTrackBean.setLogId(logNextId);
//			splitTrackBean.setSplitId(processStepBean.getActId());
//			splitTrackService.insertSplitTrack(splitTrackBean);
//		}
		
		// 다음 단계로 재귀호출하기 위한 준비 단계. workProcessing....
		InstanceBean instanceBeanTemp	= new InstanceBean();
		instanceBeanTemp.setCommand(instanceBean.getCommand());
		instanceBeanTemp.setProcessId(instanceBean.getProcessId());
		instanceBeanTemp.setProcessVer(instanceBean.getProcessVer());
		instanceBeanTemp.setTitle(instanceBean.getTitle());
		instanceBeanTemp.setInstanceId(instanceBean.getInstanceId());
		instanceBeanTemp.setPerformerId(instanceBean.getPerformerId());
		instanceBeanTemp.setVarSchema(instanceBean.getVarSchema());
		instanceBeanTemp.setCreateDate(executeDate);

		instanceBeanTemp.setActivityId(processStepBean.getNextActId());				
		logId				= workProcessing(instanceBeanTemp, paramHsh, executeDate);
		log.info("# workProcessing : 재귀호출 종료======================================================");
		return logId;
	}
	
	/**
	 * Expression을 justice 메소드가 판단할 수 있는 형태로 정제시킨다. 
	 * @param instanceBean
	 * @param conditionExpression
	 * @param gatewayType
	 * @return
	 */
	public String parseExpression(InstanceBean instanceBean, String conditionExpression, String gatewayType) {
		String result			= "";
		String frontExpression	= "";
		String backExpression	= "";
		if(conditionExpression != null && !conditionExpression.equals("")) {
			backExpression		= conditionExpression.trim();
		}

		if(-1 < conditionExpression.indexOf('(')) {
			while(0 <= backExpression.indexOf('(')) {
				int openPos				= backExpression.indexOf('(');
				frontExpression	= backExpression.substring(0, openPos);
				backExpression	= backExpression.substring(openPos+1, backExpression.length());
				if(-1 == backExpression.indexOf('(') || backExpression.indexOf(')') < backExpression.indexOf('(')) {
					int cmpltClosePos	= backExpression.indexOf(')');
					String expression	= backExpression.substring(0, cmpltClosePos);
					expression			= justice(instanceBean, expression);
					backExpression		= backExpression.substring(cmpltClosePos + 1, backExpression.length());
					backExpression	= frontExpression + expression + backExpression;
					backExpression	= backExpression.trim();
					if(backExpression.equalsIgnoreCase("true") || backExpression.equalsIgnoreCase("false")) {
						result			= backExpression;
						break;
					} else {
						backExpression	= parseExpression(instanceBean, backExpression, gatewayType);
					}
				}
			}
		} else {
			result	= justice(instanceBean, backExpression);
		}
		return result;
	}
	
	
	/**
	 * 조건문을 판단한다. 
	 * @param instanceBean
	 * @param conditionExpression
	 * @return
	 */
	public String justice(InstanceBean instanceBean, String conditionExpression) {
		log.info("### justice conditionExpression : " + conditionExpression);
		boolean isBeforeResult 	= false; 
		boolean isResult 		= false;
		StringTokenizer tokenList = new StringTokenizer(conditionExpression);
		XmlService justService	= new XmlService();
		justService.loadingXML(instanceBean.getVarSchema());
		
		String tokenValue		= "";
		String beforeTokenValue	= "";
		String operator			= "";
		String compare			= "";
		int compareCount		= 0;
		
		while(tokenList.hasMoreElements()) {
			String token	= tokenList.nextToken();
			if(token != null && !token.equals("")) {
				token = token.trim();
			}
			if(token.startsWith("$")) {
				token = token.substring(1, token.length());
			}
			
			if(token.equalsIgnoreCase("TRUE") || token.equalsIgnoreCase("FALSE") ) {
				if(token.equalsIgnoreCase("TRUE")) {
					isResult	= true;
				} else if(token.equalsIgnoreCase("FALSE")) {
					isResult	= false;
				}	
				if(0<compareCount) {
					if(compare.equalsIgnoreCase("&&")) {
						isResult 	= isBeforeResult && isResult;
					} else {
						isResult	= isBeforeResult || isResult;
					}
					compareCount = 0;
				}		
				isBeforeResult	= isResult;
			} else if(token.equalsIgnoreCase("==") || token.equalsIgnoreCase("!=") || token.equalsIgnoreCase("=") ||
					token.equalsIgnoreCase("<>") || token.equalsIgnoreCase("<=") || token.equalsIgnoreCase(">=") ||
					token.equalsIgnoreCase(">") || token.equalsIgnoreCase("<") ) {
				operator = token;
			} else if(token.equalsIgnoreCase("&&") || token.equalsIgnoreCase("||") || token.equalsIgnoreCase("and") || token.equalsIgnoreCase("or")) {
				if(token.equalsIgnoreCase("and")) {
					compare = "&&";
				} else if(token.equalsIgnoreCase("or")) {
					compare = "||";
				} else {
					compare = token;
				}
				compareCount++;
			} else {
				if(0<token.indexOf('[')) {
					String index	= "0";
					index		= token.substring((token.indexOf('[')+1), (token.length()-1));
					token		= token.substring(0, token.indexOf('['));
					tokenValue	= justService.getAttrValue("DataFields/DataField[@Id='"+ token + "']/CurrentValue/Item[@Index='"+index+"']");
				} else if(0<token.indexOf(".length")) {
					token		= token.substring(0, token.indexOf(".length"));
					tokenValue	= justService.nodeCount("DataFields/DataField[@Id='"+ token + "']/CurrentValue/Item");
				} else {
					if(token.startsWith("\'") || token.startsWith("\"") ) {
						token	= token.substring(1, (token.length()-1));
						tokenValue = token;
					} else {
						if(justService.isExistNode("DataFields/DataField[@Id='"+ token + "']/CurrentValue")) {
							tokenValue	= justService.getAttrValue("DataFields/DataField[@Id='"+ token + "']/CurrentValue");
						}
					}
				}	
				log.info("### token : " + token + ", beforeTokenValue : " + beforeTokenValue + ", operator : " + operator + ", tokenValue : " + tokenValue);
				
				if(tokenValue == null || tokenValue.equals("")) {
					String msg = 	"\n#################################################################" +
									"\n# ERROR : Not Exist Condition Value(" + token +")" +
									"\n#################################################################";
					log.info(msg);
					InstanceBean resultInstanceBean		= new InstanceBean();
					resultInstanceBean.setInstanceId(instanceBean.getInstanceId());
					resultInstanceBean.setResult("ERROR");
					resultInstanceBean.setResultMsg("Not Exist Condition Value(" + token +")");
					resultInstanceBean.setUpdateDate(instanceBean.getCreateDate());
					instanceDao.updateResult(resultInstanceBean);
				}
				
				if(!beforeTokenValue.equals("")) {
					if(!operator.equals("")) {
						if(operator.equalsIgnoreCase("==")) {
							if(beforeTokenValue.equals(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase("!=")) {
							if(!beforeTokenValue.equals(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase("=")) {
							if(beforeTokenValue.equals(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase("<>")) {
							if(!beforeTokenValue.equals(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase("<=")) {
							if(Integer.parseInt(beforeTokenValue) <= Integer.parseInt(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase(">=")) {
							if(Integer.parseInt(beforeTokenValue) >= Integer.parseInt(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase(">")) {
							if(Integer.parseInt(beforeTokenValue) > Integer.parseInt(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						} else if(operator.equalsIgnoreCase("<")) {
							if(Integer.parseInt(beforeTokenValue) < Integer.parseInt(tokenValue)) {
								isResult = true;
							} else {
								isResult = false;
							}
						}
						if(0<compareCount) {
							if(compare.equalsIgnoreCase("&&")) {
								isResult 	= isBeforeResult && isResult;
							} else {
								isResult	= isBeforeResult || isResult;
							}
							compareCount = 0;
						}
						isBeforeResult	= isResult;
						operator = "";
					}
				}
			}
			beforeTokenValue = tokenValue;
		}
		return (isResult + "");
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param insLogId
	 * @param userId
	 * @param delegatorId
	 * @param message
	 * @return
	 */
	public boolean setDelegate(String instanceLogId, String userId, String delegatorId, String message) {
		boolean isSuccess	= false;
		InstanceLogBean instanceLogBean		= new InstanceLogBean();
		instanceLogBean.setInstanceLogId(instanceLogId);
		instanceLogBean.setPerformerId(delegatorId);
		instanceLogBean.setDelegatorId(userId);
		WorkItemBean workItemBean			= new WorkItemBean();
		workItemBean.setInsLogId(instanceLogId);
		workItemBean.setUserId(delegatorId);
		workItemBean.setDelegatorId(userId);
		if(workListDao.updateDelegate(workItemBean)) {
			if(instanceLogDao.updateDelegate(instanceLogBean)) {
				isSuccess	= true;
			}
		}
		return isSuccess;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 * @param notifyPoint
	 */
	public void mailSend(InstanceBean instanceBean, String notifyPoint) {
		// Notify가 있을경우 SMS/Email 형태로 처리한다. (완료시:FinishedNotify)
		NotifyBean notifyBean	= new NotifyBean();
		notifyBean.setProcessId(instanceBean.getProcessId());
		notifyBean.setProcessVer(instanceBean.getProcessVer());
		notifyBean.setActivityId(instanceBean.getActivityId());
		notifyBean.setNotifyPoint(notifyPoint);
		
		List<NotifyBean> notifyList	= notifyService.selectNotify(notifyBean);
		
		for(int m=0;m<notifyList.size();m++) {
			notifyBean			= notifyList.get(m);
			if(notifyBean.getNotifyType().equalsIgnoreCase("EMAIL")) {
				User user = new User();
				user.setUserId("spy28");
				user.setUserPassword("daum28");
				user.setMail("spy28@hanmail.net");
				user.setUserName("박철순");									
				
				// 메일 내용 설정
				AdminEmailLog em = new AdminEmailLog();
				em.setSenderEmail("spy28@hanmail.net");
				em.setSenderId("spy28");
				em.setReceiver("sniper28");
				if(notifyBean.getEmailType().equalsIgnoreCase("TO")) {
					em.setReceiverEmail(notifyBean.getEmailAddress());
				} else if(notifyBean.getEmailType().equalsIgnoreCase("CC")) {
					em.setCcEmail(notifyBean.getEmailAddress());
				} else if(notifyBean.getEmailType().equalsIgnoreCase("BCC")) {
					em.setBccEmail(notifyBean.getEmailAddress());
				}
				em.setEmailTitle(notifyBean.getNotifyTitle());
				em.setEmailContent(notifyBean.getNotifyContent());
				adminEmailService.sendAdminEmail(em, user);
//			} else {
//				Sms sms = new Sms();
//				sms.setReceiverId("user1");
//				sms.setReceiverPhoneno(notifyBean.getMobileNumber());
//				sms.setContents(notifyBean.getNotifyContent());
//				SmsServiceImpl.create(sms);
			}
		}		
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param instanceBean
	 * @return
	 */
	public InstanceBean select(InstanceBean instanceBean) {
		return instanceDao.select(instanceBean);
	}
	
	
	
	/**
	 * 
	 */
	public void selectWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh) {
		InstanceLogBean instanceLogBean		= new InstanceLogBean();
		//instanceLogBean.setInstanceId(instanceId)
		
		instanceLogDao.updateSelectWorkItem(instanceLogBean);
	}
	
}
