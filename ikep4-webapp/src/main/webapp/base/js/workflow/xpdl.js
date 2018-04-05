/*
 * author : 김동후 (dhkim@built1.com)
 * version : 1.0
 * description : XPDL Utility
 * 
 * */
var xpdl;
var xmlUtil;
var xpdl_defaultNamespace = "http://www.wfmc.org/2004/XPDL2.1";
var cns_Namespace = "http://www.lgcns.com/workflow/2011/02/XPDL2.1/";
var sequence;
var edited;

(function($) {
	
	// XPDL 생성자 (xmlUtil 객체 생성)
	XPDL = function(xpdlDom) {
		xmlUtil = new XMLUtil(xpdlDom);
		edited = false;
	};
	
	// XPDL 객체 생성
	getXPDLInstance = function(xpdlXML) {
		return new XPDL(xmlDOM(xpdlXML)[0]);
	};
	
	// XPDL 객체 생성 (기존 XPDL DOM 을 통해 생성할 경우)
	createXPDLDom = function(xpdlDOM) {
		// create XPDL Contructor
		xpdl = new XPDL(xpdlDOM);
	};
	
	// XPDL 객체 생성 (신규 XPDL DOM 을 생성할 경우)
	createDefaultXPDLDom = function(processId, processName, version, Description) {
		// create xpdlDom
		
		var xpdlXML = "<Package xmlns=\"http://www.wfmc.org/2004/XPDL2.1\" xmlns:lgcns=\"http://www.lgcns.com/workflow/2011/02/XPDL2.1/\"/>";
		
		// create XPDL Contructor
		xpdl = this.getXPDLInstance(xpdlXML);
		
		xpdl.createPackageHeader();
		xpdl.createDefaultWorkflowProcesses(processId, processName, version, Description);
	};
	
	getInitXPDLDom = function(url) {
	  var xpdlDom = $.ajax({
		  url: url,
		  async: false,
		  success: function success() {},
		  error: function error() {}
		 }).responseXML;
	  
	  return xpdlDom;
	};
	
	// XPDL CLASS
	XPDL.prototype = {
			
		// XPDL DOM 반환
		getXPDLDom : function() {
			return xmlUtil.getDocument();
		},
		
		// XPDL 에서 임의 생성하는 아이디
		getGenerateId : function(prefix) {
			if(sequence == undefined)
				sequence = 0;
			
			sequence = sequence+1;
			
			// naming role : timestamp+seq
			return prefix + (new Date().getTime()) +"_"+sequence;
		},
		// 프로세스 최초 생성시 기본 <PackageHeader> 생성
		createPackageHeader : function() {
			 var packageHeaderEle = xmlUtil.createElement("PackageHeader",xpdl_defaultNamespace);
			 
			 var xpdlVersionEle = xmlUtil.createElement("XPDLVersion",xpdl_defaultNamespace);
			 xmlUtil.appendTextNode(xpdlVersionEle, "2.1");
			 
			 var vendorEle = xmlUtil.createElement("Vendor",xpdl_defaultNamespace);
			 xmlUtil.appendTextNode(vendorEle, "LGCNS iKEP 4.0 Workflow Modeler");
			 
			 var createdEle = xmlUtil.createElement("Created",xpdl_defaultNamespace);
			 xmlUtil.appendTextNode(createdEle, getISODateTime());
			 
			 xmlUtil.appendChildElement(packageHeaderEle, xpdlVersionEle);
			 xmlUtil.appendChildElement(packageHeaderEle, vendorEle);
			 xmlUtil.appendChildElement(packageHeaderEle, createdEle);
			 
			 //Append or Replace PackageHeader
			 if(xmlUtil.nodeCount("//xpdl:PackageHeader") > 0) {
			 	xmlUtil.replaceChildNode(packageHeaderEle, xmlUtil.selectSingleNode("//xpdl:PackageHeader"));
			 } else {
			 	// Append PackageHeader
			 	xmlUtil.appendChildElement(xmlUtil.selectSingleNode("/xpdl:Package"), packageHeaderEle);
			 }
		},
		// <Pools> 생성 : Deprecated
		createPools : function($defaultInfo) {
			var poolId = this.getGenerateId("pool");
			var laneId = this.getGenerateId("lane");
			var processName = $defaultInfo.find("span[id='processName']").text();
			//var processId = $defaultInfo.find("span[id='ProcessId']").text();
			//var version = $defaultInfo.find("span[id='versappendAndReplaceTransitionon']").text();
			//var description = $defaultInfo.find("span[id='description']").text();
			
			var poolsEle = xmlUtil.createElement("Pools", xpdl_defaultNamespace);
			var poolEle = xmlUtil.createElement("Pool", xpdl_defaultNamespace);
			poolEle = xmlUtil.createAttributeAndAppend(poolEle, "Id", poolId);					//Create Unique ID
			poolEle = xmlUtil.createAttributeAndAppend(poolEle, "Process", processName);
			poolEle = xmlUtil.createAttributeAndAppend(poolEle, "Orientation", "VERTICAL");
			
			var lanesEle = xmlUtil.createElement("Lanes", xpdl_defaultNamespace);
			var laneEle = xmlUtil.createElement("Lane", xpdl_defaultNamespace);
			laneEle = xmlUtil.createAttributeAndAppend(laneEle, "Id", laneId);
			laneEle = xmlUtil.createAttributeAndAppend(laneEle, "Name", "Lane");
			laneEle = xmlUtil.createAttributeAndAppend(laneEle, "parentPool", poolId);
			
			var nodeGraphicsInfos = xmlUtil.createElement("NodeGraphicsInfos", xpdl_defaultNamespace);
			var nodeGraphicsInfo = xmlUtil.createElement("NodeGraphicsInfo", xpdl_defaultNamespace);
			nodeGraphicsInfo = xmlUtil.createAttributeAndAppend(nodeGraphicsInfo, "Height", "");
			nodeGraphicsInfo = xmlUtil.createAttributeAndAppend(nodeGraphicsInfo, "Width", "");
			nodeGraphicsInfo = xmlUtil.createAttributeAndAppend(nodeGraphicsInfo, "BorderColor", "");
			
			xmlUtil.appendChildElement(xmlUtil.selectSingleNode("/xpdl:Package"),poolsEle);
			xmlUtil.appendChildElement(poolsEle,poolEle);
			xmlUtil.appendChildElement(poolEle,lanesEle);
			xmlUtil.appendChildElement(lanesEle,laneEle);
			xmlUtil.appendChildElement(laneEle,nodeGraphicsInfos);
			xmlUtil.appendChildElement(nodeGraphicsInfos,nodeGraphicsInfo);
		},
		// 프로세스 최초 생성시 기본 <WorkflowProcesses> 생성
		createDefaultWorkflowProcesses : function(processId, processName, version, description) {
			var workflowProcesses = xmlUtil.createElement("WorkflowProcesses",xpdl_defaultNamespace);
			 
			//Append or Replace WorkflorProcesses
			if(xmlUtil.nodeCount("//xpdl:WorkflorProcesses") > 0) {
				xmlUtil.replaceChildNode(workflowProcesses, xmlUtil.selectSingleNode("//xpdl:WorkflorProcesses"));
			} else {
				// Append PackageHeader
				xmlUtil.appendChildElement(xmlUtil.selectSingleNode("/xpdl:Package"), workflowProcesses);
			}
			
			// create <WorkflowProcess>
			var workflowProcess = xmlUtil.createElement("WorkflowProcess",xpdl_defaultNamespace);
			xmlUtil.createAttributeAndAppend(workflowProcess, "Id", processId);
			xmlUtil.createAttributeAndAppend(workflowProcess, "Name", processName);
			
			xmlUtil.appendChildElement(workflowProcesses, workflowProcess);
			
			// create <ProcessHeader>
			this.appendProcessHeader(description);
			
			// create <RedefinableHeader>
			this.appendRedefinableHeader(version);
			
			// create <FormalParameters>
			this.appendFormalParameter(null,null,null,null);
			
			// create <Participants>
			this.appendProcessParticipant(null);
			
			// create <DataFields>
			this.appendDataField(null,null,null,null);
			
			// create <Activities>
			this.appendActivity(null);
			
			// create <Transitions>
			this.appendTransition(null);
			
			// create <ExtendedAttributes>
			this.appendProcessKey(null);
		},
		// 프로세스 기본 정보 추가, 수정(이름과 설명)
		setProcessDefaultInfo : function(process, action) {
			result = new Array();
			try {
				var processId = process.id;;
				var processName = process.name;;
				var processVersion = process.version;;
				var processDescription = process.description;;
				
				this.setProcessName(processId, processName);
				
				if(action == 'append') {
					this.appendProcessHeader(processDescription);
				} else {
					this.replaceProcessHeader(processDescription);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setProcessDefaultInfo] "+e);
			}
		},
		// 프로세스 이름 변경 (프리즘에서 프로세스 이름 변경시 직접 호출됨)
		setProcessName : function(processId, processName) {
			try {
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess[@Id='"+processId+"']") > 0) {
					xmlUtil.setNodeValue("//xpdl:WorkflowProcess[@Id='"+processId+"']/@Name", processName);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setProcessDefaultInfo] "+e);
			}
		},
		// 프로세스 변수 설정
		setProcessVariable : function(processVariable, action) {
			// action : append, replace, remove
			try {
				var processVariableId = processVariable.id;
				var dataFieldCnt = xmlUtil.nodeCount("//xpdl:DataFields/xpdl:DataField[@Id='"+processVariableId+"']");
				var formalParameterCnt = xmlUtil.nodeCount("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+processVariableId+"']");
				var processKeyCnt = xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData/lgcns:Expression[text()='"+processVariableId+"']");
				
				var activityInputSetCnt = xmlUtil.nodeCount("//xpdl:InputSet/xpdl:Input[@ArtifactId='"+processVariableId+"']");
				var activityOutputSetCnt = xmlUtil.nodeCount("//xpdl:OutputSet/xpdl:Output[@ArtifactId='"+processVariableId+"']");
				
				// 단일 삭제
				if(action == "remove") {
					if(dataFieldCnt > 0)
						this.removeDataField(processVariableId);
					
					if(formalParameterCnt > 0)
						this.removeFormalParameter(processVariableId);
					
					if(processKeyCnt > 0)
						this.removeProcessKey(processVariableId);
					
					if(activityInputSetCnt > 0) {
						xmlUtil.removeNodes("//xpdl:InputSet/xpdl:Input[@ArtifactId='"+processVariableId+"']", true);
					}
					
					if(activityOutputSetCnt > 0) {
						xmlUtil.removeNodes("//xpdl:OutputSet/xpdl:Output[@ArtifactId='"+processVariableId+"']", true);
					}
					
				} else if(action == "append" || action == "replace"){
					if(action == "append") {
						if(dataFieldCnt > 0) {
							throw Error(processVariableId + " has already been added.");
						}
						
						this.appendDataField(processVariable);
						this.appendFormalParameter(processVariable);
						this.appendProcessKey(processVariable);
						
					} else {	// replace
						this.replaceDataField(processVariable);
						this.replaceFormalParameter(processVariable);
						this.replaceProcessKey(processVariable);
					}
					
				} else {
					throw Error("required action[append, replace, remove]");
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setProcessVariable] "+e);
			}
		},
		// 프로세스 참여자 설정 (action : append, replace, remove)
		setProcessParticipant : function(participant, action) {
			try {
				if(action == 'append')
					this.appendProcessParticipant(participant);
				else if(action == 'replace') 
					this.replaceProcessParticipant(participant);
				else
					this.removeProcessParticipant(participant.id);
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setProcessParticipant] "+e);
			}
			
		},
		// 액티비티 기본정보 설정
		setActivityDefaultInfo : function(activity, action) {
			//action (append, replace, remove)
			try {
				if(action == 'append') {
					this.appendActivity(activity);
				} else if(action == 'replace') {
					this.replaceActivity(activity);
				} else {	// remove
					this.removeActivity(activity.id);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityDefaultInfo] "+e);
			}
		},
		// 액티비티 이름 변경 (프리즘의 setComponentName(id, name) 메소드에서 호출됨)
		setActivityName : function(activityId, activityName) {
			try {
				if(xmlUtil.nodeCount("//xpdl:Activity[@Id='"+activityId+"']") > 0) {
					xmlUtil.setNodeValue("//xpdl:Activity[@Id='"+activityId+"']/@Name", activityName);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityName] "+e);
			}
		},
		// 액티비티 변수 설정
		setActivityVariables : function (mode, activityId, activityVariableIds, action) {
			
			try {
				if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
					throw Error(activityId + " Not Found!");
				}
				
				if(action == 'append') {
					for(var i=0; i < activityVariableIds.length; i++) {
						this.appendActivityVariable(mode, activityId, activityVariableIds[i]);
					}
				} else { // remove
					for(var i=0; i < activityVariableIds.length; i++) {
						this.removeActivityVariable(mode, activityId, activityVariableIds[i]);
					}
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityVariables] "+e);
			}
		},
		// 액티비티 고급 정보 설정
		setActivityAdvanceInfo : function(activityAdvance) {
			try {
				this.appendAndReplaceActivityAdvanceInfo(activityAdvance);
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityAdvanceInfo] "+e);
			}
		},
		// 액티비티 알림 정보 설정
		setActivityNotification : function(activityNotification, action) {
			try {
				if(action == 'append')
					this.appendActivityNotificationInfo(activityNotification);
				else if(action == 'replace')
					this.replaceActivityNotificationInfo(activityNotification);
				else // remove
					this.removeActivityNotificationInfo(activityNotification.activityId, activityNotification.id);
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityNotification] "+e);
			}
		},
		// 액티비티 수행자(Performer) 설정 및 제거
		setActivityPerformers : function(activityId, participantIds, action) {
			try {
				
				if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
					throw new Error(activityId + " does not exist.");
				}
				
				if(action == 'append') {
					for(var i=0; i < participantIds.length; i++) {
						if(participantIds[i] != '') {
							this.appendActivityPerformer(activityId, participantIds[i]);
						}
					}
				}
				else {  //remove
					for(var i=0; i < participantIds.length; i++) {
						if(participantIds[i] != '') {
							this.removeActivityPerformer(activityId, participantIds[i]);
						}
					}
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setActivityPerformers] "+e);
			}
		},
		// Transition 정보 설정
		setTransition : function(transition, action) {
			try {
				if(action == 'append') {
					this.appendTransition(transition);
				} else if(action == 'replace') {
					this.replaceTransition(transition);
				} else {	// drop
					this.removeTransition(transition.id);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setTransition] "+e);
			}
		},
		// Transition 이름 변경
		setTransitionName : function(transitionId, transitionName) {
			try {
				if(xmlUtil.nodeCount("//xpdl:Transition[@Id='"+transitionId+"']") > 0) {
					xmlUtil.setNodeValue("//xpdl:Transition[@Id='"+transitionId+"']/@Name", transitionName);
				}
				
				if(!edited)
					edited = true;
			} catch (e) {
				throw Error("[XPDL.setTransitionName] "+e);
			}
		},
		// 프로세스 헤더 정보 추가 (Description 만 반영됨).
		appendProcessHeader : function(processDescription) {
			try {
				if(processDescription == null) {
					processDescription = "";
				}
				
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ProcessHeader") > 0) {
					xmlUtil.setElementNodeValue(xmlUtil.selectSingleNode("//xpdl:ProcessHeader/xpdl:Description"), processDescription);
				} else {
					// create <ProcessHeader>
					var processHeader = xmlUtil.createElement("ProcessHeader", xpdl_defaultNamespace);
					var created = xmlUtil.createElement("Created", xpdl_defaultNamespace);
					xmlUtil.appendTextNode(created, getISODateTime());
					
					var descriptionEle = xmlUtil.createElement("Description", xpdl_defaultNamespace);
					xmlUtil.appendTextNode(descriptionEle, processDescription);
					
					xmlUtil.appendChildElement(processHeader, created);
					xmlUtil.appendChildElement(processHeader, descriptionEle);
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess"), processHeader);
				}
			} catch(e) {
				throw Error("[XPDL.appendProcessHeader] "+e);
			}
		},
		// 프로세스 헤더 정보 수정 (Description 만 해당됨)
		replaceProcessHeader : function(processDescription) {
			try {
				if(processDescription == null) {
					processDescription = "";
				}
				
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description") > 0) {
					xmlUtil.setNodeValue("//xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description", processDescription);
				}
			} catch(e) {
				throw Error("[XPDL.replaceProcessHeader] "+e);
			}
		},
		// 프로세스 재정의 헤더 정보 추가(버전만 해당됨)
		appendRedefinableHeader : function(processVersion) {
			try {
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:RedefinableHeader") > 0) {
					xmlUtil.setElementNodeValue(xmlUtil.selectSingleNode("//xpdl:RedefinableHeader/xpdl:Version"), processVersion);
				} else {
					// create <RedifinableHeader>
					var redefinableHeader = xmlUtil.createElement("RedefinableHeader", xpdl_defaultNamespace);
					var versionEle = xmlUtil.createElement("Version", xpdl_defaultNamespace);
					xmlUtil.appendTextNode(versionEle, processVersion);
					
					xmlUtil.appendChildElement(redefinableHeader, versionEle);
					$(redefinableHeader).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:ProcessHeader"));
				}
			} catch(e) {
				throw Error("[XPDL.appendRedefinableHeader] "+e);
			}
		},
		// 프로세스 Redefinable 헤더 정보 수정 (버전만 해당됨)
		replaceRedefinableHeader : function(processVersion) {
			try {
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version") > 0) {
					xmlUtil.setNodeValue("//xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version", processVersion);
				}
			} catch(e) {
				throw Error("[XPDL.replaceRedefinableHeader] "+e);
			}
		},
		// 프로세스 참여자 추가
		// participants : array(value,name,type)
		appendProcessParticipant : function(participant) {
			try {
				// <Participants> 없으면 추가
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:Participants") == 0) {
					var participantsEle = xmlUtil.createElement("Participants", xpdl_defaultNamespace);
					$(participantsEle).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:FormalParameters"));
				}
				
				if(participant != null) {
					var participantValueNodes = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
					
					var hasParticipant = false;
					
					alert(participant.id);
					
					var participantValue = participant.value;
					var participantName = participant.name;
					var participantType = participant.type;
					
					if(participantValueNodes.length != 0) {
						for(var j=0; j<participantValueNodes.length; j++) {
							if(participantValue == participantValueNodes[j].firstChild.nodeValue) {
								hasParticipant = true;
								break;	// end for loop
							}
						}
					}
					
					//기존에 추가된 사용자가 아니거나, 설정된 참여자가 없을 경우만 새로 추가한다. 존재할 경우 추가하지 않는다.
					if(!hasParticipant || participantValueNodes.length == 0) {
						var participantEle = xmlUtil.createElement("Participant", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(participantEle, "Id", this.getGenerateId("P"));
						xmlUtil.createAttributeAndAppend(participantEle, "Name", participantName);
						
						var participantTypeEle = xmlUtil.createElement("ParticipantType", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(participantTypeEle, "Type", participantType);
						
						var extendedAttributes = xmlUtil.createElement("ExtendedAttributes", xpdl_defaultNamespace);
						var extendedAttribute = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(extendedAttribute, "Name", "ParticipantExtendedAttribute");
						
						var participantExtendedAttribute = xmlUtil.createElement("lgcns:ParticipantExtendedAttribute", cns_Namespace);
						var value = xmlUtil.createElement("lgcns:Value", cns_Namespace);
						xmlUtil.appendTextNode(value, participantValue);
						
						xmlUtil.appendChildElement(participantEle, participantTypeEle);
						
						xmlUtil.appendChildElement(participantExtendedAttribute, value);
						xmlUtil.appendChildElement(extendedAttribute, participantExtendedAttribute);
						xmlUtil.appendChildElement(extendedAttributes, extendedAttribute);
						xmlUtil.appendChildElement(participantEle, extendedAttributes);
						
						xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:Participants"), participantEle);
					}
				}
			} catch (e) {
				throw Error("[XPDL.appendProcessParticipant] "+e);
			}
		},
		// 프로세스 참여자 수정
		// participants : array(id, value,name,type)
		replaceProcessParticipant : function(participant) {
			try {
				if(participant != null) {
					
					var participantId = participant.id;
					var participantValue = participant.value;
					var participantName = participant.name;
					var participantType = participant.type;
					
					var participantEle = xmlUtil.createElement("Participant", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(participantEle, "Id", participantId);
					xmlUtil.createAttributeAndAppend(participantEle, "Name", participantName);
					
					var participantTypeEle = xmlUtil.createElement("ParticipantType", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(participantTypeEle, "Type", participantType);
					
					var extendedAttributes = xmlUtil.createElement("ExtendedAttributes", xpdl_defaultNamespace);
					var extendedAttribute = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(extendedAttribute, "Name", "ParticipantExtendedAttribute");
					var participantExtendedAttribute = xmlUtil.createElement("lgcns:ParticipantExtendedAttribute", cns_Namespace);
					var value = xmlUtil.createElement("lgcns:Value", cns_Namespace);
					xmlUtil.appendTextNode(value, participantValue);
					
					xmlUtil.appendChildElement(participantEle, participantTypeEle);
					
					xmlUtil.appendChildElement(participantExtendedAttribute, value);
					xmlUtil.appendChildElement(extendedAttribute, participantExtendedAttribute);
					xmlUtil.appendChildElement(extendedAttributes, extendedAttribute);
					xmlUtil.appendChildElement(participantEle, extendedAttributes);
					
					//  존재하면 Replace
					if(xmlUtil.nodeCount("//xpdl:Participants/xpdl:Participants[@Id='"+participantId+"']") > 0) {
						xmlUtil.replaceChildNode(participantEle, xmlUtil.selectSingleNode("//xpdl:Participants/xpdl:Participants[@Id='"+participantId+"']"));
					} else {
						xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:Participants"), participantEle);
					}
				}
			} catch (e) {
				throw Error("[XPDL.replaceProcessParticipant] "+e);
			}
		},
		// 프로세스 참여자 삭제
		removeProcessParticipant : function(id) {
			try {
				// 참여자 삭제
				xmlUtil.removeSingleNode("//xpdl:Participants/xpdl:Participant[@Id='"+id+"']", false);
				
				// 액티비티 수행자 존재시 삭제
				xmlUtil.removeNodes("//xpdl:Performers/xpdl:Performer[text()='"+id+"']", false);
				
			} catch(e) {
				throw Error("[XPDL.removeProcessParticipant] "+e);
			}
		},
		// 프로세스 참여자 전체 삭제(type - p:group/user/role, v:variable)
		removeProcessParticipants : function(type) {
			try {
				if(type == 'p') { // group/user/role 삭제
					xmlUtil.removeNodes("//xpdl:Participants/xpdl:Participant[xpdl:ParticipantType[@Type='HUMAN']]", false);
					xmlUtil.removeNodes("//xpdl:Participants/xpdl:Participant[xpdl:ParticipantType[@Type='ORGANIZATIONAL_UNIT']]", false);
					xmlUtil.removeNodes("//xpdl:Participants/xpdl:Participant[xpdl:ParticipantType[@Type='ROLE']]", false);
				} else if (type == 'v')  // variable 삭제
					xmlUtil.removeNodes("//xpdl:Participants/xpdl:Participant[xpdl:ParticipantType[@Type='SYSTEM']]", false);
				  else  // 전체삭제
					xmlUtil.removeNodes("//xpdl:Participants/xpdl:Participant", false);
				
			} catch(e) {
				throw Error("[XPDL.removeProcessParticipants] "+e);
			}
		},
		// 현재 프로세스 참여자 목록에 존재하지 않는 잘못된 액티비티 수행자 제거
		removeInValidActivityPerformer : function() {
			try {
				var performerList = xmlUtil.selectNodes("//xpdl:Performers/xpdl:Performer");
				
				if(performerList.length > 0) {
					for(var i=0; i < performerList.length; i++) {
						var id = performerList[i].firstChild.nodeValue;
						
						if(xmlUtil.nodeCount("//xpdl:Participants/xpdl:Participant[@Id='"+id+"']") == 0) {
							// 참여자 목록에 존재하지 않는다면 제거한다.
							var parentNode = performerList[i].parentNode;
							
							parentNode.removeChild(performerList[i]);
						}
					}
				}
			} catch (e) {
				throw Error("[XPDL.removeInValidActivityPerformer] "+e);
			}
		},
		// 프로세스 변수중 In/Out/InOut 설정이 된 변수 추가
		appendFormalParameter : function(processVariable) {
			try {
				
				//<FormalParameters> 존재
				if(xmlUtil.nodeCount("//xpdl:FormalParameters") > 0) {
					var formalParameters = xmlUtil.selectSingleNode("//xpdl:FormalParameters");
				} else {
					// create <FormalParameters>
					var formalParameters = xmlUtil.createElement("FormalParameters", xpdl_defaultNamespace);
					$(formalParameters).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:RedefinableHeader"));
				}
				
				if(processVariable == null)
					return;
				
				var id = processVariable.id;
				var mode = processVariable.mode;
				var type = processVariable.type;
				var isArray = processVariable.isArray;
				
				if(mode != '') {	// Mode 가 존재하여야 FormalParameter에 추가한다.
					var formalParameter = xmlUtil.createElement("FormalParameter", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(formalParameter, "Id", id);
					xmlUtil.createAttributeAndAppend(formalParameter, "Mode", mode);
					xmlUtil.createAttributeAndAppend(formalParameter, "IsArray", isArray);
					
					var dataType = xmlUtil.createElement("DataType", xpdl_defaultNamespace);
					var basicType = xmlUtil.createElement("BasicType", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(basicType, "Type", type);
					
					xmlUtil.appendChildElement(dataType, basicType);
					xmlUtil.appendChildElement(formalParameter, dataType);
					
					xmlUtil.appendChildElement(formalParameters, formalParameter);
				}
				
			} catch(e) {
				throw Error("[XPDL.appendFormalParameter] "+e);
			}
		},
		// 프로세스 변수중 In/Out/InOut 설정이 된 변수 수정
		replaceFormalParameter : function(processVariable) {
			try {
				if(processVariable == null)
					  return;
				
				var id = processVariable.id;
				var mode = processVariable.mode;
				var type = processVariable.type;
				var isArray = processVariable.isArray;
				
				// mode 가 없으면 기존 FormalParameter를 삭제한다.
				if(mode == '') {
					if(xmlUtil.nodeCount("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+id+"']") > 0) {
						this.removeFormalParameter(id);
						return;
					}
				}
				
				var formalParameter = xmlUtil.createElement("FormalParameter", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(formalParameter, "Id", id);
				xmlUtil.createAttributeAndAppend(formalParameter, "Mode", mode);
				xmlUtil.createAttributeAndAppend(formalParameter, "IsArray", isArray);
				
				var dataType = xmlUtil.createElement("DataType", xpdl_defaultNamespace);
				var basicType = xmlUtil.createElement("BasicType", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(basicType, "Type", type);
				
				xmlUtil.appendChildElement(dataType, basicType);
				xmlUtil.appendChildElement(formalParameter, dataType);
				
				if(xmlUtil.nodeCount("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+id+"']") > 0) {
					xmlUtil.replaceChildNode(formalParameter, xmlUtil.selectSingleNode("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+id+"']"));
				} else {
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:FormalParameters"), formalParameter);
				}
				
			} catch(e) {
				throw Error("[XPDL.replaceFormalParameter] "+e);
			}
		},
		// 프로세스 변수중 In/Out/InOut 설정이 된 변수 삭제
		removeFormalParameter : function(id) {
			try {
				xmlUtil.removeSingleNode("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+id+"']", false);
			} catch(e) {
				throw Error("[XPDL.removeFormalParameter] "+e);
			}
		},
		// 프로세스 변수 추가
		appendDataField : function(processVariable) {
			try {
				//<DataFields> 존재
				if(xmlUtil.nodeCount("//xpdl:DataFields") > 0) {
					var dataFields = xmlUtil.selectSingleNode("//xpdl:DataFields");
				} else {
					// create <DataFields>
					var dataFields = xmlUtil.createElement("DataFields", xpdl_defaultNamespace);
					$(dataFields).insertAfter(xmlUtil.selectSingleNode("//xpdl:Participants"));
				}
				
				if(processVariable == null)
					  return;
				
				var id = processVariable.id;
				var name = processVariable.name;
				var type = processVariable.type;
				var isArray = processVariable.isArray;
				var useIndex = processVariable.useIndex;	// added by kdh (11.04.27)
				
				var dataField = xmlUtil.createElement("DataField", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(dataField, "Id", id);
				xmlUtil.createAttributeAndAppend(dataField, "Name", name);
				xmlUtil.createAttributeAndAppend(dataField, "IsArray", isArray);
				xmlUtil.createAttributeAndAppend(dataField, "lgcns:UseIndex", useIndex);
				
				var dataType = xmlUtil.createElement("DataType", xpdl_defaultNamespace);
				var basicType = xmlUtil.createElement("BasicType", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(basicType, "Type", type);
				
				xmlUtil.appendChildElement(dataType, basicType);
				xmlUtil.appendChildElement(dataField, dataType);
				
				xmlUtil.appendChildElement(dataFields, dataField);
			} catch (e) {
				throw Error("[XPDL.appendDataField] "+e);
			}
		},
		// 프로세스 변수 수정
		replaceDataField : function(processVariable) {
			try {
				
				if(processVariable == null)
					return;
				
				var id = processVariable.id;
				var name = processVariable.name;
				var type = processVariable.type;
				var isArray = processVariable.isArray;
				var useIndex = processVariable.useIndex;	// added by kdh (11.04.27)
				
				var dataField = xmlUtil.createElement("DataField", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(dataField, "Id", id);
				xmlUtil.createAttributeAndAppend(dataField, "Name", name);
				xmlUtil.createAttributeAndAppend(dataField, "IsArray", isArray);
				xmlUtil.createAttributeAndAppend(dataField, "lgcns:UseIndex", useIndex);
				
				var dataType = xmlUtil.createElement("DataType", xpdl_defaultNamespace);
				var basicType = xmlUtil.createElement("BasicType", xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(basicType, "Type", type);
				
				xmlUtil.appendChildElement(dataType, basicType);
				xmlUtil.appendChildElement(dataField, dataType);
				
				xmlUtil.replaceChildNode(dataField, xmlUtil.selectSingleNode("//xpdl:DataFields/xpdl:DataField[@Id='"+id+"']"));
				
			} catch (e) {
				throw Error("[XPDL.replaceDataField] "+e);
			}
		},
		// 프로세스 변수 삭제
		removeDataField : function(id) {
			try {
				xmlUtil.removeSingleNode("//xpdl:DataFields/xpdl:DataField[@Id='"+id+"']", false);
			} catch (e) {
				throw Error("[XPDL.removeDataField] "+e);
			}
		},
		// 프로세스 변수 다중 삭제 (DataField, FormalParameter 포함)
		removeProcessVariables : function(processVariableIds) {
			var dataFieldCnt;
			var formalParameterCnt;
			var processKeyCnt;
			var activityInputSetCnt;
			var activityOutputSetCnt;
			
			try {
				for(var i=0; i < processVariableIds.length; i++) {
					dataFieldCnt = xmlUtil.nodeCount("//xpdl:DataFields/xpdl:DataField[@Id='"+processVariableIds[i]+"']");
					formalParameterCnt = xmlUtil.nodeCount("//xpdl:FormalParameters/xpdl:FormalParameter[@Id='"+processVariableIds[i]+"']");
					processKeyCnt = xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData/lgcns:Expression[text()='"+processVariableIds[i]+"']");
					
					activityInputSetCnt = xmlUtil.nodeCount("//xpdl:InputSet/xpdl:Input[@ArtifactId='"+processVariableIds[i]+"']");
					activityOutputSetCnt = xmlUtil.nodeCount("//xpdl:OutputSet/xpdl:Output[@ArtifactId='"+processVariableIds[i]+"']");
					
					if(dataFieldCnt > 0) {
						xpdl.removeDataField(processVariableIds[i]);
					}
					
					if(formalParameterCnt > 0) {
						xpdl.removeFormalParameter(processVariableIds[i]);
					}
					
					if(processKeyCnt > 0) {
						this.removeProcessKey(processVariableIds[i]);
					}
					
					if(activityInputSetCnt > 0) {
						xmlUtil.removeNodes("//xpdl:InputSet/xpdl:Input[@ArtifactId='"+processVariableIds[i]+"']", true);
					}
					
					if(activityOutputSetCnt > 0) {
						xmlUtil.removeNodes("//xpdl:OutputSet/xpdl:Output[@ArtifactId='"+processVariableIds[i]+"']", true);
					}
				}
			} catch (e) {
				throw Error("[XPDL.removeProcessVariables] "+e);
			}
		},
		//프로세스 확장 속성 추가 (현재 프로세스 키 정보)
		appendProcessKey : function(processVariable) {
			try {
				//<ExtendedAttributes> 존재
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes") > 0) {
					var extendedAttributes = xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes");
				} else {
					// create <ExtendedAttributes>
					var extendedAttributes = xmlUtil.createElement("ExtendedAttributes", xpdl_defaultNamespace);
					$(extendedAttributes).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:Transitions"));
				}
				
				if(processVariable == null)
					return;
				
				var isProcessKey = processVariable.isKey;
				var processKey = processVariable.id;
				var processKeyName = processVariable.name;
				var index = processVariable.keyIndex;
				
				if(processKey == undefined || processKey == '')
					throw Error("required processKey variable!!");
				
				if(isProcessKey) {
					
					if(xmlUtil.nodeCount("//lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData[@Index='"+index+"']") > 0) {
						throw Error("Does not allow the same index..");
					}
					
					if(xmlUtil.nodeCount("//lgcns:ProcessExtendedAttribute") == 0) {
						var extendedAttribute = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(extendedAttribute, "Name", "ProcessExtendedAttribute");
						
						var processExtendedAttribute = xmlUtil.createElement("lgcns:ProcessExtendedAttribute", cns_Namespace);
						
						xmlUtil.appendChildElement(extendedAttribute, processExtendedAttribute);
						xmlUtil.appendChildElement(extendedAttributes, extendedAttribute);
						
					} else {
						var processExtendedAttribute = xmlUtil.selectSingleNode("//lgcns:ProcessExtendedAttribute");
					}
					
					var processKeyData = xmlUtil.createElement("lgcns:ProcessKeyData", cns_Namespace);
					xmlUtil.createAttributeAndAppend(processKeyData, "Index", index);
					
					var keyName = xmlUtil.createElement("lgcns:Name", cns_Namespace);
					xmlUtil.appendTextNode(keyName, processKeyName);
					
					var expression = xmlUtil.createElement("lgcns:Expression", cns_Namespace);
					xmlUtil.appendTextNode(expression, "$"+processKey);
					
					xmlUtil.appendChildElement(processKeyData, keyName);
					xmlUtil.appendChildElement(processKeyData, expression);
					xmlUtil.appendChildElement(processExtendedAttribute, processKeyData);
				}
			} catch (e) {
				throw Error("[XPDL.appendProcessKey] "+e);
			}
		},
		// 프로세스 키 정보 수정
		replaceProcessKey : function(processVariable) {
			try {
				//<ExtendedAttributes> 존재
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes") > 0) {
					var extendedAttributes = xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes");
				} else {
					// create <ExtendedAttributes>
					var extendedAttributes = xmlUtil.createElement("ExtendedAttributes", xpdl_defaultNamespace);
					$(extendedAttributes).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:Transitions"));
				}
				
				if(processVariable.id == undefined || processVariable.id == '')
					throw Error("required processKey variable!!");
				
				if(processVariable.isKey) {  // 존재하면 삭제후 추가
					
					if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData/lgcns:Expression[text()='$"+processVariable.id+"']") > 0) {
						this.removeProcessKey(processVariable.id);
					}
					
					this.appendProcessKey(processVariable);
				} else {  // 존재하면 삭제, 모두 삭제되면 상위 ExtendedAttribute 까지 삭제
					if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData/lgcns:Expression[text()='$"+processVariable.id+"']") > 0) {
						this.removeProcessKey(processVariable.id);
					}
				}
			} catch (e) {
				throw Error("[XPDL.replaceProcessKey] "+e);
			}
		},
		// 프로세스 키 정보 삭제
		removeProcessKey : function(processKey) {
			try {
				xmlUtil.removeSingleNode("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute/lgcns:ProcessKeyData[lgcns:Expression='$"+processKey+"']", true);
				
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute/lgcns:ProcessExtendedAttribute") == 0) {
					xmlUtil.removeSingleNode("//xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute", false);
				}
			} catch (e) {
				throw Error("[XPDL.removeProcessKey] "+e);
			}
		},
		// Activity 최초 추가 (프리즘의 setComponents(id,name,activityType,gatewayType) 함수에서 호출됨)
		appendActivity : function(activity) {
			try {
				// create <Activities>
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:Activities") == 0) {
					var activities = xmlUtil.createElement("Activities", xpdl_defaultNamespace);
					$(activities).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:DataFields"));
				}
				
				if(activity != null) {
					var activityId = activity.id;
					var activityName = activity.name;
					var activityType = activity.type;  // START, END, GATEWAY, HUMAN
					var gatewayType = activity.gatewayType;// AND, OR, XOR, COMPLEX
					var startMode = activity.startMode;
					var finishMode = activity.finishMode;
					
					if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
						var activity = xmlUtil.createElement("Activity", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(activity, "Id", activityId);
						xmlUtil.createAttributeAndAppend(activity, "Name", activityName);
						
						// activityType 이 MANUAL, AUTOMATIC일 경우만 StartMode 와 FinishMode 가 존재한다.
						if(activityType != 'START' && activityType != 'END' && activityType != 'GATEWAY') {
							xmlUtil.createAttributeAndAppend(activity, "StartMode", startMode);
							xmlUtil.createAttributeAndAppend(activity, "FinishMode", finishMode);
						}
						
						// Description Append
						var activityDescriptionEle = xmlUtil.createElement("Description", xpdl_defaultNamespace);
						xmlUtil.appendChildElement(activity, activityDescriptionEle);
						
						if(activityType == 'START') {
							// StartEvent Append
							var event = xmlUtil.createElement("Event", xpdl_defaultNamespace);
							var startEvent = xmlUtil.createElement("StartEvent", xpdl_defaultNamespace);
							xmlUtil.createAttributeAndAppend(startEvent, "Trigger", "NONE");  //hardcoding
							
							xmlUtil.appendChildElement(event, startEvent);
							xmlUtil.appendChildElement(activity, event);
							
						} else if (activityType == 'END') {
							// EndEvent Append
							var event = xmlUtil.createElement("Event", xpdl_defaultNamespace);
							var endEvent = xmlUtil.createElement("EndEvent", xpdl_defaultNamespace);
							xmlUtil.createAttributeAndAppend(endEvent, "Result", "NONE");  //hardcoding
							
							xmlUtil.appendChildElement(event, endEvent);
							xmlUtil.appendChildElement(activity, event);
							
						} else if (activityType == 'MANUAL') {
							var activityImplementationEle = xmlUtil.createElement("Implementation", xpdl_defaultNamespace);
							var activityTaskEle = xmlUtil.createElement("Task", xpdl_defaultNamespace);
							var activityTaskManualEle = xmlUtil.createElement("TaskManual", xpdl_defaultNamespace);
							xmlUtil.appendChildElement(activityTaskEle, activityTaskManualEle);
							xmlUtil.appendChildElement(activityImplementationEle, activityTaskEle);
							
							// Priority Append
							var activityPriorityEle = xmlUtil.createElement("Priority", xpdl_defaultNamespace);
							
							var extendedAttributesEle = xmlUtil.createElement("ExtendedAttributes", xpdl_defaultNamespace);
							var inputSetsEle = xmlUtil.createElement("InputSets", xpdl_defaultNamespace);
							var outputSetsEle = xmlUtil.createElement("OutputSets", xpdl_defaultNamespace);
							
							xmlUtil.appendChildElement(activity, activityImplementationEle);
							xmlUtil.appendChildElement(activity, activityPriorityEle);
							xmlUtil.appendChildElement(activity, extendedAttributesEle);
							xmlUtil.appendChildElement(activity, inputSetsEle);
							xmlUtil.appendChildElement(activity, outputSetsEle);
							
						} else if (activityType == 'GATEWAY') {
							var route = xmlUtil.createElement("Route", xpdl_defaultNamespace);
							xmlUtil.createAttributeAndAppend(route, "GatewayType", gatewayType);
							
							var transitionRestrictions = xmlUtil.createElement("TransitionRestrictions", xpdl_defaultNamespace);
							var transitionRestriction = xmlUtil.createElement("TransitionRestriction", xpdl_defaultNamespace);
							
							// Split 과 Join을 한쌍으로 사용하지 않음
							// Join 은 고려하지 않음.
							var split = xmlUtil.createElement("Split", xpdl_defaultNamespace);
							xmlUtil.createAttributeAndAppend(split, "Type", gatewayType);
							
							var transitionRefs = xmlUtil.createElement("TransitionRefs", xpdl_defaultNamespace);
							
							// Route Element Append
							xmlUtil.appendChildElement(activity, route);
							
							// TransitionRestrictions Element Append
							xmlUtil.appendChildElement(split, transitionRefs);
							xmlUtil.appendChildElement(transitionRestriction, split);
							xmlUtil.appendChildElement(transitionRestrictions, transitionRestriction);
							xmlUtil.appendChildElement(activity, transitionRestrictions);
						}
						xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activities"), activity);
						
					} else {
						throw Error(activityId + " has already been added.");
					}
				}
			} catch(e) {
				throw Error("[XPDL.appendActivity] "+e);
			}
		},
		// Activity 수정
		replaceActivity : function(activity) {
			try {
				if(activity != null) {
					
					var activityId = activity.id;
					var activityName = activity.name;
//					var activityType = activity.type;
//					var gatewayType = activity.gatewayType;
					var activityDescription = (activity.description == null ? "" : activity.description);
					var startMode = activity.startMode;
					var finishMode = activity.finishMode;
					var activityPriority = activity.priority;
					
					// update Activity Name
					xmlUtil.setNodeValue("//xpdl:Activity[@Id='"+activityId+"']/@Name", activityName);
					
					if(activityId.indexOf("MANUAL") != -1 || activityId.indexOf("AUTOMATIC") != -1) {
						// update Activity Priority
						if(activityPriority != null) {
							xmlUtil.setNodeValue("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Priority", activityPriority);
						}
						// update Activity Description
						if(activityDescription != null) {
							xmlUtil.setNodeValue("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Description", activityDescription);
						}
					}
				}
				
			} catch (e) {
				throw Error("[XPDL.replaceActivity] "+e);
			}
		},
		// 액티비티 삭제
		removeActivity : function (activityId) {
			try {
				xmlUtil.removeSingleNode("//xpdl:Activity[@Id='"+activityId+"']", false);
			} catch (e) {
				throw Error("[XPDL.removeActivity] "+e);
			}
		},
		// 액티비티 변수 추가
		appendActivityVariable : function(mode, activityId, variableId) {
			try {
				
				var modeElementName;
				if(mode == 'in')
					modeElementName = "Input";
				else if (mode == 'out')
					modeElementName = "Output";
				else
					throw Error("required mode[in,out]");
				
				if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:"+modeElementName+"Sets/xpdl:"+modeElementName+"Set") == 0) {
					// create InputSet/OutputSet
					var varSetEle = xmlUtil.createElement(modeElementName+"Set", xpdl_defaultNamespace);
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:"+modeElementName+"Sets"), varSetEle);
				}
				
				var modeEle = xmlUtil.createElement(modeElementName, xpdl_defaultNamespace);
				xmlUtil.createAttributeAndAppend(modeEle, "ArtifactId", variableId);
				xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:"+modeElementName+"Sets/xpdl:"+modeElementName+"Set"), modeEle);
				
			} catch (e) {
				throw Error("[XPDL.appendActivityVariable] "+e);
			}
		},
		// 액티비티 변수 삭제
		removeActivityVariable : function(mode, activityId, variableId) {
			try {
				var modeElementName;
				if(mode == 'in')
					modeElementName = "Input";
				else if (mode == 'out')
					modeElementName = "Output";
				else
					throw Error("required mode[in,out]");
				
				xmlUtil.removeSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:"+modeElementName+"Sets/xpdl:"+modeElementName+"Set/xpdl:"+modeElementName+"[@ArtifactId='"+variableId+"']", true);
				
			} catch (e) {
				throw Error("[XPDL.removeActivityVariable] "+e);
			}
		},
		// 액티비티 고급 정보 추가 및 수정
		appendAndReplaceActivityAdvanceInfo : function(activityAdvance) {
			try {
				var activityId = activityAdvance.activityId;
				var isDeadline = activityAdvance.isDeadline;
				var durationType = activityAdvance.durationType;
				
				if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
					throw Error(activityId + " Not Found!");
				}
				
				var activityDeadlineCnt = xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Deadline']");
				var activityUrlCnt = xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ActivityUrl']");
				
				if(activityDeadlineCnt > 0) {	// 존재하면
					xmlUtil.removeSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Deadline']", false);
				}
				
				if(activityUrlCnt > 0) {	// 존재하면
					xmlUtil.removeSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ActivityUrl']", false);
				}
				
				if(isDeadline) {
					var extendedAttributeEle = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(extendedAttributeEle, "Name", "Deadline");
					
					var deadlineEle = xmlUtil.createElement("lgcns:Deadline", cns_Namespace);
					xmlUtil.createAttributeAndAppend(deadlineEle, "base", "ACTIVITY_ASSIGN");		// HardCoding
					
					//duration Format : P1DT1H1M
					if(durationType == 'durationDay') {
						var day = activityAdvance.day;
						var hour = activityAdvance.hour;
						var minutes = activityAdvance.minutes;
						
						var durationTxt = getISODuration(day, hour, minutes);
						
						if(durationTxt != '') {
							var durationEle = xmlUtil.createElement("lgcns:Duration", cns_Namespace);
							xmlUtil.appendTextNode(durationEle, durationTxt);
							xmlUtil.appendChildElement(deadlineEle, durationEle);
						}
					} else {  // variable
						var durationVariable = activityAdvance.durationVariable;
						
						if(durationVariable != '') {
							var durationVariableEle = xmlUtil.createElement("lgcns:DurationVariable", cns_Namespace);
							xmlUtil.appendTextNode(durationVariableEle, durationVariable);
							xmlUtil.appendChildElement(deadlineEle, durationVariableEle);
						}
					}
					
					xmlUtil.appendChildElement(extendedAttributeEle, deadlineEle);
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes"), extendedAttributeEle);
				}
				
				if(activityAdvance.url != undefined && activityAdvance.url != '') {
					extendedAttributeEle = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(extendedAttributeEle, "Name", "ActivityUrl");
					
					var activityUrlEle = xmlUtil.createElement("lgcns:ActivityUrl", cns_Namespace);
					xmlUtil.appendTextNode(activityUrlEle, activityAdvance.url);
					
					xmlUtil.appendChildElement(extendedAttributeEle, activityUrlEle);
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes"), extendedAttributeEle);
					
				}
				
			} catch (e) {
				throw Error("[XPDL.appendAndReplaceActivityAdvanceInfo] "+e);
			}
		},
		// 액티비티 알림정보 추기
		appendActivityNotificationInfo : function(activityNotification) {
			
			try {
				var activityId = activityNotification.activityId;
			    var type = activityNotification.type;
			    var state = activityNotification.state;
			    var trigger = activityNotification.trigger;
			    var condition = activityNotification.condition;
			    
				var extendedAttributeEle;
				var extendedAttributesEle = xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes");
				
			    var durationTxt = getISODuration(activityNotification.day, activityNotification.hour, activityNotification.minutes);

			    if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
					throw Error(activityId + " Not Found!");
				}
			    
			    if(xmlUtil.nodeCount("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']/lgcns:Notification[@Type='"+type+"']/lgcns:Action[@Name='"+state+"'][@Trigger='"+trigger+"']") > 0) {
			    	throw Error("Does not allow the same Notification Info(type :" + type +", state : "+ state +", trigger : "+ trigger+")");
			    }
			    
			    if(xmlUtil.nodeCount("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']") == 0) {
			    	extendedAttributeEle = xmlUtil.createElement("ExtendedAttribute", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(extendedAttributeEle, "Name", "Notification");
			    } else {
			    	extendedAttributeEle = xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']");
			    }
			    
			    var notificationEle = xmlUtil.createElement("lgcns:Notification", cns_Namespace);
			    xmlUtil.createAttributeAndAppend(notificationEle, "Id", this.getGenerateId("N"));
			    xmlUtil.createAttributeAndAppend(notificationEle, "Type", type);
			    
			    var conditionEle = xmlUtil.createElement("lgcns:Condition", cns_Namespace);
			    if(condition != '' && condition != undefined) {
			    	var expressionEle = xmlUtil.createElement("lgcns:Expression", cns_Namespace);
				    xmlUtil.appendTextNode(expressionEle, condition);
				    
			    	xmlUtil.appendChildElement(conditionEle, expressionEle);
			    }
			    
			    var actionEle = xmlUtil.createElement("lgcns:Action", cns_Namespace);
			    xmlUtil.createAttributeAndAppend(actionEle, "Name", state);
			    xmlUtil.createAttributeAndAppend(actionEle, "Trigger", trigger);
			    xmlUtil.createAttributeAndAppend(actionEle, "Duration", durationTxt);
			    
			    var recipientsEle = xmlUtil.createElement("lgcns:Recipients", cns_Namespace);
			    var recipientEle;
			    
			    switch (type) {
				    case 'EMAIL' : if(activityNotification.to != '') {
					    	           var recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
					    	           xmlUtil.createAttributeAndAppend(recipientEle, "Name", "to");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.to_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.to);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
								   
				    			   if(activityNotification.cc != '') {
				    				   recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
									   xmlUtil.createAttributeAndAppend(recipientEle, "Name", "cc");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.cc_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.cc);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
				    			   
				    			   if(activityNotification.bcc != '') {
				    				   recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
									   xmlUtil.createAttributeAndAppend(recipientEle, "Name", "bcc");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.bcc_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.bcc);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
								   break;
				    case 'SMS' : if(activityNotification.from != '') {
				    		         recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
					    	         xmlUtil.createAttributeAndAppend(recipientEle, "Name", "from");
					                 xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.from_type);
						             xmlUtil.appendTextNode(recipientEle, activityNotification.from);
						             xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			 }
				    			 
				    			 if(activityNotification.to != '') {
				    				 recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
						             xmlUtil.createAttributeAndAppend(recipientEle, "Name", "to");
					                 xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.to_type);
						             xmlUtil.appendTextNode(recipientEle, activityNotification.to);
						             xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			 }
					             break;
			    }
			    
			    var messageEle = xmlUtil.createElement("lgcns:Message", cns_Namespace);
			    
			    xmlUtil.appendChildElement(actionEle, recipientsEle);
			    
			    if(type == 'EMAIL') {
			    	var messageTitle = xmlUtil.createElement("lgcns:Title", cns_Namespace);
				    xmlUtil.appendTextNode(messageTitle, activityNotification.title);
				    
				    xmlUtil.appendChildElement(messageEle, messageTitle);
			    }
			    
			    var messageBody = xmlUtil.createElement("lgcns:Body", cns_Namespace);
			    xmlUtil.appendTextNode(messageBody, activityNotification.body);
			    
			    xmlUtil.appendChildElement(messageEle, messageBody);
			    xmlUtil.appendChildElement(actionEle, messageEle);
			    xmlUtil.appendChildElement(notificationEle, conditionEle);
			    xmlUtil.appendChildElement(notificationEle, actionEle);
			    xmlUtil.appendChildElement(extendedAttributeEle, notificationEle);
			    xmlUtil.appendChildElement(extendedAttributesEle, extendedAttributeEle);
			} catch (e) {
				throw Error("[XPDL.appendActivityNotificationInfo] "+e);
			}
		},
		// 액티비티 알림 정보 수정
		replaceActivityNotificationInfo : function(activityNotification) {
			try {
				var id = activityNotification.id;
				var activityId = activityNotification.activityId;
			    var type = activityNotification.type;
			    var state = activityNotification.state;
			    var trigger = activityNotification.trigger;
			    var condition = activityNotification.condition;
			    
			    var durationTxt = getISODuration(activityNotification.day, activityNotification.hour, activityNotification.minutes);

			    if(xmlUtil.nodeCount("//xpdl:Activities/xpdl:Activity[@Id='"+activityId+"']") == 0) {
					throw Error(activityId + " Not Found!");
				}
			    
			    var conditionEle = xmlUtil.createElement("lgcns:Condition", cns_Namespace);
			    if(condition != '' && condition != undefined) {
			    	var expressionEle = xmlUtil.createElement("lgcns:Expression", cns_Namespace);
				    xmlUtil.appendTextNode(expressionEle, condition);
				    
			    	xmlUtil.appendChildElement(conditionEle, expressionEle);
			    }
			    
			    var actionEle = xmlUtil.createElement("lgcns:Action", cns_Namespace);
			    xmlUtil.createAttributeAndAppend(actionEle, "Name", state);
			    xmlUtil.createAttributeAndAppend(actionEle, "Trigger", trigger);
			    xmlUtil.createAttributeAndAppend(actionEle, "Duration", durationTxt);
			    
			    var recipientsEle = xmlUtil.createElement("lgcns:Recipients", cns_Namespace);
			    var recipientEle;
			    
			    switch (type) {
				    case 'EMAIL' : if(activityNotification.to != '') {
					    	           var recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
					    	           xmlUtil.createAttributeAndAppend(recipientEle, "Name", "to");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.to_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.to);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
								   
				    			   if(activityNotification.cc != '') {
				    				   recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
									   xmlUtil.createAttributeAndAppend(recipientEle, "Name", "cc");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.cc_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.cc);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
				    			   
				    			   if(activityNotification.bcc != '') {
				    				   recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
									   xmlUtil.createAttributeAndAppend(recipientEle, "Name", "bcc");
									   xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.bcc_type);
									   xmlUtil.appendTextNode(recipientEle, activityNotification.bcc);
									   xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			   }
								   break;
				    case 'SMS' : if(activityNotification.from != '') {
				    		         recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
					    	         xmlUtil.createAttributeAndAppend(recipientEle, "Name", "from");
					                 xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.from_type);
						             xmlUtil.appendTextNode(recipientEle, activityNotification.from);
						             xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			 }
				    			 
				    			 if(activityNotification.to != '') {
				    				 recipientEle = xmlUtil.createElement("lgcns:Recipient", cns_Namespace);
						             xmlUtil.createAttributeAndAppend(recipientEle, "Name", "to");
					                 xmlUtil.createAttributeAndAppend(recipientEle, "Type", activityNotification.to_type);
						             xmlUtil.appendTextNode(recipientEle, activityNotification.to);
						             xmlUtil.appendChildElement(recipientsEle, recipientEle);
				    			 }
					             break;
			    }
			    
			    var messageEle = xmlUtil.createElement("lgcns:Message", cns_Namespace);
			    
			    xmlUtil.appendChildElement(actionEle, recipientsEle);
			    
			    if(type == 'EMAIL') {
			    	var messageTitle = xmlUtil.createElement("lgcns:Title", cns_Namespace);
				    xmlUtil.appendTextNode(messageTitle, activityNotification.title);
				    
				    xmlUtil.appendChildElement(messageEle, messageTitle);
			    }
			    
			    var messageBody = xmlUtil.createElement("lgcns:Body", cns_Namespace);
			    xmlUtil.appendTextNode(messageBody, activityNotification.body);
			    
			    xmlUtil.appendChildElement(messageEle, messageBody);
			    xmlUtil.appendChildElement(actionEle, messageEle);
			    
			    xmlUtil.replaceChildNode(actionEle, xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']/lgcns:Notification[@Id='"+id+"']/lgcns:Action"));
			    xmlUtil.replaceChildNode(conditionEle, xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']/lgcns:Notification[@Id='"+id+"']/lgcns:Condition"));
			    
			} catch (e) {
				throw Error("[XPDL.replaceActivityNotificationInfo] "+e);
			}
		},
		// 액티비티 알림정보 삭제
		removeActivityNotificationInfo : function(activityId, id) {
			try {
				xmlUtil.removeSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='Notification']/lgcns:Notification[@Id='"+id+"']", true);
			} catch (e) {
				throw Error("[XPDL.removeActivityNotificationInfo] "+e);
			}
		},
		// 액티비티 수행자 추가
		appendActivityPerformer : function(activityId, participantId) {
			try {
				
				if(!xmlUtil.nodeCount("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Implementation/xpdl:Task/xpdl:TaskManual/xpdl:Performers[xpdl:Performer='"+participantId+"']") > 0) {
					var performersEle;
					// Performers 가 존재하지 않으면 새로 생성
					if(xmlUtil.nodeCount("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Implementation/xpdl:Task/xpdl:TaskManual/xpdl:Performers") == 0) {
						performersEle = xmlUtil.createElement("Performers", xpdl_defaultNamespace);
					} else {
						performersEle = xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Implementation/xpdl:Task/xpdl:TaskManual/xpdl:Performers");
					}
					
					var performerEle = performerEle = xmlUtil.createElement("Performer", xpdl_defaultNamespace);
					xmlUtil.appendTextNode(performerEle, participantId);
					
					xmlUtil.appendChildElement(performersEle, performerEle);
					
					if(performerEle != undefined)
						xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Implementation/xpdl:Task/xpdl:TaskManual"), performersEle);
				}
			} catch (e) {
				throw Error("[XPDL.appendActivityPerformer] "+e);
			}
		},
		// 액티비티 수행자 제거
		removeActivityPerformer : function(activityId, participantId) {
			try {
				xmlUtil.removeNodes("//xpdl:Activity[@Id='"+activityId+"']/xpdl:Implementation/xpdl:Task/xpdl:TaskManual/xpdl:Performers/xpdl:Performer[text()='"+participantId+"']", true);
			} catch (e) {
				throw Error("[XPDL.removeActivityPerformer] "+e);
			}
		},
		// 연결선 추가
		appendTransition : function (transition) {
			try {
				if(xmlUtil.nodeCount("//xpdl:WorkflowProcess/xpdl:Transitions") == 0) {
					var transitions = xmlUtil.createElement("Transitions", xpdl_defaultNamespace);
					$(transitions).insertAfter(xmlUtil.selectSingleNode("//xpdl:WorkflowProcess/xpdl:Activities"));
				}
				
				if(transition != null) {
					
					var transitionId = transition.id;
					var transitionConditionType = transition.conditionType;
					var transitionName = transition.name;
					var transitionConditionExpression = transition.conditionExpression;
					var transitionFrom = transition.from;
					var transitionTo = transition.to;
					var transitionDescription = transition.description;
					
					var transitionEle = xmlUtil.createElement("Transition", xpdl_defaultNamespace);
					xmlUtil.createAttributeAndAppend(transitionEle, "Id", transitionId);
					xmlUtil.createAttributeAndAppend(transitionEle, "Name", transitionName);
					xmlUtil.createAttributeAndAppend(transitionEle, "From", transitionFrom);
					xmlUtil.createAttributeAndAppend(transitionEle, "To", transitionTo);
					
					if(transitionConditionType == 'CONDITION' || transitionConditionType == 'OTHERWISE') {
						var conditionEle = xmlUtil.createElement("Condition", xpdl_defaultNamespace);
						xmlUtil.createAttributeAndAppend(conditionEle, "Type", transitionConditionType);
						
						if(transitionConditionType == 'CONDITION') {
							var expressioEle = xmlUtil.createElement("Expression", xpdl_defaultNamespace);
							xmlUtil.appendTextNode(expressioEle, transitionConditionExpression);
							
							xmlUtil.appendChildElement(conditionEle, expressioEle);
						}
						
						xmlUtil.appendChildElement(transitionEle, conditionEle);
					}
					
					var transitionDescriptionEle = xmlUtil.createElement("Description", xpdl_defaultNamespace);
					xmlUtil.appendTextNode(transitionDescriptionEle, transitionDescription);
					
					xmlUtil.appendChildElement(transitionEle, transitionDescriptionEle);
					xmlUtil.appendChildElement(xmlUtil.selectSingleNode("//xpdl:Transitions"), transitionEle);
					
				}
			} catch (e) {
				throw Error("[XPDL.appendTransition] "+e);
			}
		},
		// 연결선 수정
		replaceTransition : function (transition) {
			try {
				var transitionId = transition.id;
				
				// 기존 Transition 삭제
				this.removeTransition(transitionId);
				
				// 새로운 Transition 추가
				this.appendTransition(transition);
			} catch (e) {
				throw Error("[XPDL.replaceTransition] "+e);
			}
		},
		// 연결선 제거
		removeTransition : function (transitionId) {
			try {
				xmlUtil.removeSingleNode("//xpdl:Transition[@Id='"+transitionId+"']", false);
			} catch (e) {
				throw Error("[XPDL.removeTransition] "+e);
			}
		},
		// 액티비티, 게이트웨이, 연결선 다중 삭제
		removeProcessComponents : function (components) {
			try {
				
				for(var i=0; i < components.list.length; i++) {
					
					switch(components.list[i].type) {
						case "TRANSITION" : 
							xmlUtil.removeSingleNode("//xpdl:Transition[@Id='"+components.list[i].id+"']", false);
							break;
						default :
							xmlUtil.removeSingleNode("//xpdl:Activity[@Id='"+components.list[i].id+"']", false);
							break;
					}
				}
			} catch (e) {
				throw Error("[XPDL.removeProcessComponents] "+e);
			}
		}
	};
})(jQuery);


