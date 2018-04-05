<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprDefLine.createView"/>
				
<c:set var="preHeader"	value="ui.approval.admin.apprDefLine.createView.header"/>
<c:set var="prePreView"	value="ui.approval.admin.apprDefLine.createView.preview"/>
<c:set var="preView"	value="ui.approval.admin.apprDefLine.createView.view"/>
<c:set var="preSearch"	value="ui.approval.admin.apprDefLine.createView.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprDefLine.createView.button"/>

<c:set var="preCommon"	value="ui.approval.common.apprDefLine.code"/>
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprDefLine.js"/>"></script>
<script type="text/javascript"> 
var groupTree;
var $groupTree;
var $groupLeaderTree;

var jobList;
var $jobList;

var curItem;

var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
								"<ikep4j:message pre='${preCommon}' key='agree' />",
								"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
								"<ikep4j:message pre='${preCommon}' key='receive' />");

// script Message
var	Message_Delete_Line	=	"<ikep4j:message pre='${preMessage}' key='js.deleteLine' />";
var	Message_Search_Key	=	"<ikep4j:message pre='${preMessage}' key='js.searchKey' />";
var	Message_Leader		=	"<ikep4j:message pre='${preMessage}' key='js.leader' />";
var	Message_Dept_Line	=	"<ikep4j:message pre='${preMessage}' key='js.noDeptLine' />";
var	Message_None_Dept	=	"<ikep4j:message pre='${preMessage}' key='js.noneDept' />";
var	Message_No_Add_Dept	=	"<ikep4j:message pre='${preMessage}' key='js.noAddDept' />";
var	Message_No_Add_User	=	"<ikep4j:message pre='${preMessage}' key='js.noAddUser' />";
var	Message_No_Add_Line	=	"<ikep4j:message pre='${preMessage}' key='js.noAddLine' />";
var	Message_No_Dept_Usr	=	"<ikep4j:message pre='${preMessage}' key='js.noDeptUsr' />";
var	Message_Btn_Add		=	"<ikep4j:message pre='${preMessage}' key='js.btnAdd' />";
var	Message_Select_Item	=	"<ikep4j:message pre='${preMessage}' key='js.selectItem' />";

(function($) {
	fnCaller = function (params, dialog) {
		
		dialogWindow = dialog;
		$("#btnClose").click(function() {
			dialogWindow.close();
		
		});
	};
	initItemSet	=	function($result){
		// 결재문서의 결재선 정보 초기 설정
		var type	=	"";
		var orgName	=	"";
		var name	=	"";
		
		<c:if test="${!empty apprDefLine.apprDefLineInfo}">
		<c:forEach var="item" items="${apprDefLine.apprDefLineInfo}">
		// 사용자
		if(${item.approverType}==0){
			type	=	"user";
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
					name	=	"${item.userName} ${item.approverJobTitleName} ${item.teamName}";
					orgName	=	"${item.userName} ${item.approverJobTitleName}";
				</c:when>
				<c:otherwise> 
					name	=	"${item.userEnglishName} ${item.approverJobTitleEnglishName} ${item.teamEnglishName}";
					orgName	=	"${item.userEnglishName} ${item.approverJobTitleEnglishName}";
				</c:otherwise>
			</c:choose>				

		
		} else if(${item.approverType}==1){ // 부서
			type	=	"group";
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
					name	=	"${item.groupName}";
					orgName	=	"${item.groupName}";
				</c:when>
				<c:otherwise> 
					name	=	"${item.groupEnglishName}";
					orgName	=	"${item.groupEnglishName}";
				</c:otherwise>
			</c:choose>					

		} else if(${item.approverType}==3){ // 리더
			type	=	"group";
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
					name	=	"${item.groupName} <ikep4j:message pre='${preView}' key='leader' />";
					orgName	=	"${item.groupName} <ikep4j:message pre='${preView}' key='leader' />";
				</c:when>
				<c:otherwise> 
					name	=	"${item.groupEnglishName} <ikep4j:message pre='${preView}' key='leader' />";
					orgName	=	"${item.groupEnglishName} <ikep4j:message pre='${preView}' key='leader' />";
				</c:otherwise>
			</c:choose>					

		} else {
			type	=	"user";
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
					name	=	"${item.jobDutyName}";
					orgName	=	"${item.jobDutyName}";
				</c:when>
				<c:otherwise> 
					name	=	"${item.jobDutyEnglishName}";
					orgName	=	"${item.jobDutyEnglishName}";
				</c:otherwise>
			</c:choose>				
	
		}
		var approverId ="";
		if(type=='group' && ${item.approverType}==3) {
			approverId ="${item.approverId}:LD";
		} else {
			approverId ="${item.approverId}";
		}
 
		items={// 공통필수
			type			:	type,
			apprType		:	"${item.apprType}",	
			approverId		:	approverId,
			group			:	${item.approverWay}==1?"jobDuty":"",
			name			:	apprType[${item.apprType}]+" "+name,
			orgName			:	orgName,	
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
					teamName		:	"${item.teamName}",
					jobTitle		:	"${item.approverJobTitleName}",	
				</c:when>
				<c:otherwise> 
					teamName		:	"${item.teamEnglishName}",
					jobTitle		:	"${item.approverJobTitleEnglishName}",	
				</c:otherwise>
			</c:choose>									
			jobType					:	${item.jobType},	
			approverJobTitle		:	"${item.approverJobTitle}",	
			approverJobTitleName	:	"${item.approverJobTitleName}",								
			approverWay				:	${item.approverWay},
			approverType			:	"${item.approverType}",				
			lineModifyAuth			:	${item.lineModifyAuth},
			docModifyAuth			:	${item.docModifyAuth},	
			readModifyAuth			:	${item.readModifyAuth}
		};	
		if(type!='group') {
			$.tmpl("tmplResultUserItem", items).appendTo($result).data("data", items);	
		}else {
			$.tmpl("tmplResultGroupItem", items).appendTo($result).data("data", items);		
		}
		</c:forEach>

		</c:if>	
	}
		
	$(document).ready(function() {	
		fnCaller = function (params, dialog) {
		
			dialogWindow 	= dialog;
		};
		$("#btnClose").click(function() {
			dialogWindow.close();
		});	
		// Tab
		tab = $("#divTabs").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tab-orggroup" : 
						$('input[name=leaderType]:hidden').val('0');
						createGroupTree();
						break;
					case "tab-jobgroup" :
						$('input[name=leaderType]:hidden').val('0');
						createJobDutyList();
						break;
					case "tab-leadergroup" : 
						$('input[name=leaderType]:hidden').val('1');
						createLeaderList();
						break;					
					//case "tab-orggroup" : createGroupTree();
					//	break;
					//case "tab-jobgroup" : createJobDutyList();
					//	break;
				}
			}
		});			
		// init Setting
		createGroupTree();	
				
		var $result = $("#ulResult");
		
		// 기존 결재선 정보 초기화 (DB)
		//initItemSet($result);		
		
		// Click Event
		$("#btnList").click(function(s) {
			location.href="<c:url value="/approval/admin/apprDefLine/listView.do"/>";
		});
				
		//Default 결재선 등록
		$("#btnSave").click(function() {
			var cntParDefLineWay=0;
			
			var $resultItems = $("#ulResult").children();
					
			if($resultItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noDefault' />");
				return false;
			}

			var agreeIdx=0;
			// 내부 결재시 합의 위치 체크
			if($('input[name=defLineType]:checked').val()==0){
				// 합의 Index 
				$.each($resultItems, function(idx) {
					var data = $.extend({}, $.data(this, "data"));
					if(data.apprType==1 || data.apprType==2){
						agreeIdx = idx+1;
					}
				});
				// 내부 결재시 체크사항 ( 합의가 마지막에 올수 없음)
				if($resultItems.length==agreeIdx) {
					alert("<ikep4j:message pre='${preMessage}' key='alertLastAgree' />");
					return;				
				}				
			}
			
			agreeIdx		=	0;
			var approvalIdx	=	0;
			var receiveStartIdx	=	'';
			var receiveEndIdx	=	'';
						
			// 합의,수신 체크
			// 협조공문시 체크사항 ( 수신선택은 여러개 지정가능, 수신으로 지정된 부분이 마지막에만 가능 )
			if($('input[name=defLineType]:checked').val()==1) {
				
				$.each($resultItems, function(idx) {
					var data = $.extend({}, $.data(this, "data"));
					if(data.apprType==1 || data.apprType==2){
						agreeIdx = idx+1;
					}
					if(data.apprType==0){
						approvalIdx = idx+1;
					}
					if(data.apprType==3) {
						if(receiveStartIdx=='')
							receiveStartIdx=idx+1;
						else
							receiveEndIdx=idx+1;						
					}
					if(receiveEndIdx=='')
						receiveEndIdx=receiveStartIdx;
				});

				if($resultItems.length==agreeIdx) {
					alert("<ikep4j:message pre='${preMessage}' key='alertLastAgree' />");
					return;				
				}				
				if(agreeIdx > approvalIdx){
					alert('<ikep4j:message pre='${preMessage}' key='alertLastAgree1' />');
					return;	
				}
				if(receiveStartIdx < approvalIdx || receiveStartIdx < agreeIdx ){ // || approvalIdx > receiveEndIdx){
					alert("<ikep4j:message pre='${preMessage}' key='alertLastReceive' />");
					return;	
				}				
			}
			
			var idx=0;			
			
			$("#defLineInfoDiv").empty();

			var checkAppr =0;
			$.each($resultItems, function() {		
				var data = $.extend({}, $.data(this, "data"));	
				if(data.apprType!=3)
					checkAppr++;
			});
			
			if(checkAppr<1){
				alert("<ikep4j:message pre='${preMessage}' key='errorReceive' />");
				return;
			}
						
			$.each($resultItems, function() {
				$jq("#approverJobTitle").empty();			
				var approverId="";				
				var data = $.extend({}, $.data(this, "data"));			
						
				if(data.type=='group'){	
					var str = data.approverId.split(":");					
					if(str.length==1) {	
						approverId		=	str[0]; //data.code;
					} else {					// 리더 선택시
						approverId		=	str[0]; //data.code;					
					}						
				} else if(data.type=='user') {
					if(data.group=='jobDuty'){
						data.approverJobTitle='';	
					}
					approverId		=	data.approverId;
				}
							
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].approverWay' 		value='"+ data.approverWay +"' />").appendTo( "#defLineInfoDiv" );  
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].approverType'		value='"+ data.approverType +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].apprType'			value='"+ data.apprType +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].approverId'			value='"+ approverId +"' />").appendTo( "#defLineInfoDiv" ); 
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].apprOrder'			value='"+ idx +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].jobType'				value='"+ data.jobType +"' />").appendTo( "#defLineInfoDiv" );				
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].approverJobTitle'	value='"+ data.approverJobTitle +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].lineModifyAuth'		value='"+ data.lineModifyAuth +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].docModifyAuth'		value='"+ data.docModifyAuth +"' />").appendTo( "#defLineInfoDiv" );
				$("<input type='hidden' name='apprDefLineInfo[" + idx + "].readModifyAuth'		value='"+ data.readModifyAuth +"' />").appendTo( "#defLineInfoDiv" );				
								
				idx++;
							
			});
			
			if(!confirm('<ikep4j:message pre='${preMessage}' key='confirmSave' />')) {
				return;
			}			
			$("#mainForm").submit(); 		
			
			return false; 
		});
	
				
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"defLineName" : {
					required : true,
					maxlength : 100
				}				
			},
			messages : {
				"defLineName" : {
					required : "<ikep4j:message key='NotEmpty.apprDefLine.defLineName' />",
					maxlength : "<ikep4j:message key='Size.apprDefLine.defLineName' />"
				}
			},
			submitHandler : function() {				
				$jq.ajax({
					url : '<c:url value='/approval/admin/apprDefLine/ajaxCreatePopup.do'/>',
					data : $jq("#mainForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");

						try {
							//$("#btnList").click();
							//$("#btnClose").click();
							dialogWindow.callback(result);
							dialogWindow.close();								
						} catch(e) {}
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#mainForm", validOptions);
	
		/**
		 * Validation Logic End
		 */		

		
	});		// document.ready End

	//User JobTitle/JobDuty Info
	changeUserInfo = function(userId) {		
        $jq.get('<c:url value="/approval/admin/apprDefLine/getUserInfo.do"/>',
			{userId:userId},
			function(data) {
				$jq("#approverJobTitle").empty();				
		
				if(data.jobPositionCode != null) {
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
							$jq("<option/>").attr("value",data.jobTitleCode).text(data.jobTitleName).appendTo("#approverJobTitle");
						</c:when>
						<c:otherwise> 
							$jq("<option/>").attr("value",data.jobTitleCode).text(data.jobTitleEnglishName).appendTo("#approverJobTitle");
						</c:otherwise>
					</c:choose>				
					//$jq("<option/>").attr("value",data.jobTitleCode).text(data.jobTitleName).appendTo("#approverJobTitle");
				}
				if(data.jobDutyCode != null) {
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
							$jq("<option/>").attr("value",data.jobDutyCode).text(data.jobDutyName).appendTo("#approverJobTitle");
						</c:when>
						<c:otherwise> 
							$jq("<option/>").attr("value",data.jobDutyCode).text(data.jobDutyEnglishName).appendTo("#approverJobTitle");
						</c:otherwise>
					</c:choose>				
					//$jq("<option/>").attr("value",data.jobDutyCode).text(data.jobDutyName).appendTo("#approverJobTitle");
				}
				if(curItem.approverJobTitle == undefined || curItem.approverJobTitle == ""){
					$("#approverJobTitle > option[value='"+data.jobTitleCode+"']").attr("selected",true);
					curItem.approverJobTitle=data.jobTitleCode;
					curItem.jobType=0;				
				} else if(curItem.approverJobTitle == data.jobTitleCode ) {
					$("#approverJobTitle > option[value='"+curItem.approverJobTitle+"']").attr("selected",true);
					curItem.approverJobTitle=data.jobTitleCode;
					curItem.jobType=0;			
				} else if(curItem.approverJobTitle == data.jobDutyCode ) {
					$("#approverJobTitle > option[value='"+curItem.approverJobTitle+"']").attr("selected",true);
					curItem.approverJobTitle=data.jobDutyCode;
					curItem.jobType=1;			
				}					
			}
		)
		return false; 
	};
	// 조직도 팝업에서 그룹과 사용자  트리 조회
	var initTreeItem = "";
	
	if(${selectType=='GROUP'}) {
		initTreeItem = "#treeItem_${user.groupId}";
	}
	else {
		initTreeItem = "#treeItem_${user.userId}";
	}
	var createGroupTree = function() {
		
		if(!$groupTree) {
			$("#treeDept").bind("loaded.jstree", function (event, data) {
				var $selectItem = $(initTreeItem);
				while(true) {
					$selectItem = $selectItem.parents("li:first", this);
					if($selectItem.length > 0) {
						$("#treeDept").jstree("open_node", $selectItem[0], false);
					} else {break;}
				}				
				//var $TopItem = $("ul > li:first", this);
				//$("#treeDept").jstree("open_node", $TopItem.children("a")[0], false);
	        });
			
			$groupTree = $("#treeDept").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					//data : iKEP.arrayToTreeData(deptItems.items),
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {			
							return iKEP.arrayToTreeData(data.items, null, true);
						}
					}
				},
				core:{animation:100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDept").jstree("open_node", this, false, true); 
				//$("#producttree").jstree("toggle_node", this); 
	        });
		}
	};	
	// 직책
	var createJobDutyList = function() {
		$("#divJobResult").jstree("destroy").empty();
		$("#divJobResult").unbind("dblclick");			
		$.post("<c:url value='/approval/admin/apprDefLine/requestJobDutyList.do'/>")
			.success(function(result) { 
				if(result.items.length > 0) {
					$("#divJobResult").jstree({
							plugins : [ "themes", "json_data", "ui" ],
							json_data : { data : iKEP.arrayToTreeData(result.items) },
							themes : {dots:false}
					});				
					$("#divJobResult").bind("dblclick", applyItem);						
				}
			})
			.error(function(event, request, settings) { alert("error"); });
	};

	// 리더
	var initLeaderTreeItem = "#treeItem_${user.groupId}";
	var createLeaderList = function() {
		if(!$groupLeaderTree) {
			$("#treeDeptLeader").bind("loaded.jstree", function (event, data) {
				var $selectItem = $(initLeaderTreeItem);
				while(true) {
					$selectItem = $selectItem.parents("li:first", this);
					if($selectItem.length > 0) {
						$("#treeDeptLeader").jstree("open_node", $selectItem[0], false);
					} else {break;}
				}				
	        });
			
			$groupLeaderTree = $("#treeDeptLeader").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					ajax : {
						url : "<c:url value='/approval/admin/apprDefLine/requestLeaderList.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {			
							return iKEP.arrayToTreeData(data.items, null, true);
						}
					}
				},
				core:{animation:100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDeptLeader").jstree("open_node", this, false, true); 
	        });
		}
	};		
	$.template("tmplResultGroupItem", '<li class="ui-state-default">\${name}</li>');
	$.template("tmplResultUserItem", '<li class="ui-state-default">\${name} ${teamName}</li>');

})(jQuery);

</script>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='pageTitle' /></h2>
</div>
<!--//pageTitle End-->

<!--blockListTable Start-->
<div class="blockListTable" style="min-width:900px">


	<!--popup_contents Start-->
	<div id="popup_contents" style="margin:0px">
 
		<!--tab Start-->		
		<div id="divTabs" class="iKEP_tab">
			<ul>
				<li><a href="#tab-orggroup"><ikep4j:message pre='${preView}' key='tab.group' /></a></li>
				<li><a href="#tab-searchgroup"><ikep4j:message pre='${preView}' key='tab.search' /></a></li>
				<li><a href="#tab-jobgroup"><ikep4j:message pre='${preView}' key='tab.duty' /></a></li>
				<li><a href="#tab-leadergroup"><ikep4j:message pre='${preView}' key='tab.leader' /></a></li>
			</ul>				
			<!--//tab End-->

			<div class="tab_con">		
				<div id="tabs-1">
					<form id="mainForm" method="post">
					<input name="controlTabType" title="" type="hidden" value="1:0:0:0" />
					<input name="controlType" title="" type="hidden" value="ORG" />
					<input name="selectType" title="" type="hidden" value="ALL" />
					<input name="leaderType" title="" type="hidden" value="0" />
					<input name="defLineId" title="" type="hidden" value="${apprDefLine.defLineId}" />
					<div id="defLineInfoDiv"></div>
					<div class="shuttletab_ins">
						<label></label>
					</div>
									
					<div class="blockShuttle">
						<div class="shuttle_l" style="width:30%; ">
								
							<div id="tab-orggroup" >
								<div class="sbox" style="height:400px; overflow:auto;">					
									<div class="shuttleTree">
										<div id="treeDept"></div>
									</div>
								</div>
							</div>
				
							<div id="tab-searchgroup">
								<div class="sbox" style="height:400px; overflow:auto;">	
									<div class="shuttleSearch" style="padding:10px 0 10px 15px ;">
										<input type="text" title="<ikep4j:message pre='${preView}' key='name' />" class="inputbox" id="schKeyword" value="" size="20" />
										<a id="btnSearch" class="ic_search" href="#"><span><ikep4j:message pre='${preView}' key='search' /></span></a> 
										&nbsp;&nbsp;&nbsp;
									</div>
									<div id="divSearchResult"></div>
								</div>
							</div>
								
							<div id="tab-jobgroup">
								<div class="sbox" style="height:400px; overflow:auto;">	
									<div class="" style="padding:10px 0 10px 15px ;">
										<div id="divJobResult"></div>
									</div>
								</div>
							</div>
							
							<div id="tab-leadergroup">
								<div class="sbox" style="height:400px; overflow:auto;">	
									<div class="" style="padding:10px 0 10px 15px ;">
										<div id="treeDeptLeader"></div>
									</div>
								</div>
							</div>															
						</div>

						<div class="shuttle_m" style="margin-top:140px;min-width:120px;width:16%;">
							<div class="centerBox"> 
								<ul id="apprType">
									<li><strong><ikep4j:message pre='${preView}' key='approvalType' /></strong></li>				
									<li class="textLeft"><span><input type="radio" class="radio" title="" name="apprType" value="0" /><ikep4j:message pre='${preCommon}' key='appr' /></span></li>
									<li class="textLeft"><span><input type="radio" class="radio" title="" name="apprType" value="1" /><ikep4j:message pre='${preCommon}' key='agree' /></span></li>
									<li class="textLeft"><span><input type="radio" class="radio" title="" name="apprType" value="2" /><ikep4j:message pre='${preCommon}' key='choiceAgree' /></span></li>
									<li class="textLeft"><span><input type="radio" class="radio" title="" name="apprType" value="3" /><ikep4j:message pre='${preCommon}' key='receive' /></span></li>
									<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="Add" /></a></li>
									<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="Delete" /></a></li>
									<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="Reset" /></a></li>
								</ul>				
							</div>									
						</div>


						<div class="blockRight" style="margin-top:10px;width:50%; border:2px solid #eee; padding:10px 10px 0 10px;">				
							<div class="sbox">				
								<div class="subTitle_2 noline">
								<h3><ikep4j:message pre='${preView}' key='lineInfo' /></h3>
								</div>
							
								
								<!--blockSearch Start-->
								<div class="blockDetail">
									<table summary="<ikep4j:message pre='${preView}' key='defaultInfo' />">
									<caption></caption>
									<tbody>
									<tr>
										<th scope="row" width="10%"><ikep4j:message pre='${preView}' key='defLineType' /></th>
										<td width="65%" id="defLineType" class="textLeft">
										<c:if test="${defLineType==0}">
										<input type="radio" class="radio" name="defLineType" title="<ikep4j:message pre='${preView}' key='defLineType' />" value="0" checked="checked" /> <ikep4j:message pre='${preCommon}' key='innerAppr' />
										</c:if> 
										<c:if test="${defLineType==1}">
										<input type="radio" class="radio" name="defLineType" title="<ikep4j:message pre='${preView}' key='defLineType' />" value="1" checked="checked" /> <ikep4j:message pre='${preCommon}' key='collAppr' />
										</c:if> 
										</td>	
				
									</tr>	
									<tr>
										<spring:bind path="apprDefLine.systemId">
										<th scope="row" width="10%"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
										<td width="65%" id="systemId" class="textLeft">
										<select name="${status.expression}" title='<ikep4j:message pre='${preView}' key='${status.expression}' />'>
											<c:forEach var="apprSystemList" items="${apprSystemList}">
											<option value="${apprSystemList.systemId}" <c:if test="${apprSystemList.systemId eq systemId}">selected="selected"</c:if> >${apprSystemList.systemName}</option>
											</c:forEach> 
										</select>											
										</td>	
										</spring:bind>							
									</tr>										
									
									<tr>
										<spring:bind path="apprDefLine.defLineName">
										<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
										<td width="65%" class="textLeft"><input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" size="50" value="${status.value}" /></td>
										</spring:bind>							
									</tr>
									</tbody>
									</table>

								</div>	
								<!--//blockSearch End-->
	
				
								<div class="" style="height:300px;">
								
									<div class="blockLeft" style="width:60%">
									<ul id="ulResult" class="list-selectable" style="width:100%;"></ul>
									</div>
									
									<div class="blockRight" style="width:35%">
										
										<div id="apprTypeDiv" class="none" style="padding:3px 3px 10px 10px ;border:1px solid #e0e0e0">
										<dt><ikep4j:message pre='${preView}' key='changeLine' />
										<dl>
											<select title="<ikep4j:message pre='${preView}' key='changeLine' />" id="apprTypeChange" style="width:80%;">

											</select>											
										</dl>	
										</dt>											
										</div>
										<div class="clear" style="height:10px"></div>
										
					
										<div id="jobTitle" style="padding:3px 3px 10px 10px ;border:1px solid #e0e0e0">
										<dt><ikep4j:message pre='${preView}' key='selectDuty' />
										<dl>
											<select class="multi" title="<ikep4j:message pre='${preView}' key='selectDuty' />" size="2" id="approverJobTitle" name="approverJobTitle" style="width:80%;">
								
											</select>											
										</dl>	
										</dt>											
										</div>

										<div class="clear" style="height:10px"></div>	
										
										<div id="setAuth" style="padding:3px 3px 10px 10px ;border:1px solid #e0e0e0">
										<dt><ikep4j:message pre='${preView}' key='auth' />
										<dl><input type="checkbox" class="checkbox" id="lineModifyAuth" name="lineModifyAuth" title="<ikep4j:message pre='${preView}' key='auth.line' />" /> <ikep4j:message pre='${preView}' key='auth.line' /></dl>
										<dl><input type="checkbox" class="checkbox" id="docModifyAuth" name="docModifyAuth" title="<ikep4j:message pre='${preView}' key='auth.doc' />" /> <ikep4j:message pre='${preView}' key='auth.doc' /></dl>
										<dl><input type="checkbox" class="checkbox" id="readModifyAuth" name="readModifyAuth" title="<ikep4j:message pre='${preView}' key='auth.read' />" /> <ikep4j:message pre='${preView}' key='auth.read' /></dl>

										</dt>
										</div>
										<div class="clear" style="height:10px"></div>
										
										<spring:bind path="apprDefLine.defLineWay">
										<div id="${status.expression}" style="padding:3px 3px 10px 10px ;border:1px solid #e0e0e0">
										<dt><ikep4j:message pre='${preView}' key='agree' />
										<dl><input type="radio" class="radio" name="${status.expression}" title="<ikep4j:message pre='${preView}' key='agree.seq' />" value="0" <c:if test="${status.value==0}">checked="checked"</c:if> /> <ikep4j:message pre='${preView}' key='agree.seq' /></dl>
										<dl><input type="radio" class="radio" name="${status.expression}" title="<ikep4j:message pre='${preView}' key='agree.par' />" value="1" <c:if test="${status.value==1}">checked="checked"</c:if> /> <ikep4j:message pre='${preView}' key='agree.par' /></dl>
										</dt>
										</div>
										</spring:bind>	

									</div>
								</div>
							</div>

						</div>
							<div class="clear"></div>						
					</div>
					<!--//blockShuttle End-->

					<!--blockButton Start-->
					<div class="blockButton suttle_btn">
						<ul>
							<li><a id="btnPreview" class="button"><span><ikep4j:message pre='${preButton}' key='preView' /></span></a></li>
							<li><a id="btnSave" class="button"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
							<li><a id="btnClose" class="button"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
						</ul>
					</div>
					</form>
				</div>
			</div>
				
		</div>
		<!--//popup_contents End-->
		
		
<div id="previewDetailDiv" class="none">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>Preview</h2>
</div>
<!--//pageTitle End-->

<!--blockListTable Start-->
<div class="blockDetail">	
	
	<table summary="<ikep4j:message pre='${prePreView}' key='summary' />">
		<caption></caption>
		<!--col style="width: 10%;"/-->
		<col style="width: 25%;"/>
		<col style="width: 40%;"/>		
		<col />
		<thead>
			<tr>				
				<!--th scope="col"><ikep4j:message pre='${prePreView}' key='no' /></th-->
				<th class="textCenter" scope="col"><ikep4j:message pre='${prePreView}' key='approver' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${prePreView}' key='dept' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${prePreView}' key='apprType' /></th>
			</tr>
		</thead>
		<tbody  id="divPreviewResult">
		</tbody>
	</table>

</div>
<!--//blockListTable End-->	
</div>		
		
	</div> 
	


</div> 
<!--//popup End-->
 

