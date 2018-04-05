<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.work.apprLine.createApprReceiveLineView"/>
<c:set var="preComMessage"	value="message.approval.work.apprLine.common"/>

<c:set var="preHeader"	value="ui.approval.work.apprLine.createApprReceiveLineView.header"/>
<c:set var="prePreView"	value="ui.approval.work.apprLine.createApprReceiveLineView.preview"/>
<c:set var="preView"	value="ui.approval.work.apprLine.createApprReceiveLineView.view"/>
<c:set var="preSearch"	value="ui.approval.work.apprLine.createApprReceiveLineView.search"/>
<c:set var="preButton"	value="ui.approval.work.apprLine.createApprReceiveLineView.button"/>

<c:set var="preCommon"	value="ui.approval.common.apprDefLine.code"/>
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprReceiveLine.js"/>"></script>
<script type="text/javascript"> 

var	checkUserId='${user.userId}';

var groupTree;
var $groupTree;

var jobList;
var $jobList;

var dialogWindow = null;
var fnCaller;
var isLayerWindow = false;

var $curItem;
var curItem;

var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
								"<ikep4j:message pre='${preCommon}' key='agree' />",
								"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
								"<ikep4j:message pre='${preCommon}' key='receive' />");

// script Message
var	Message_Delete_Line	=	"<ikep4j:message pre='${preComMessage}' key='js.deleteLine' />";
var	Message_Search_Key	=	"<ikep4j:message pre='${preComMessage}' key='js.searchKey' />";
var	Message_Leader		=	"<ikep4j:message pre='${preComMessage}' key='js.leader' />";
var	Message_Dept_Line	=	"<ikep4j:message pre='${preComMessage}' key='js.noDeptLine' />";
var	Message_None_Dept	=	"<ikep4j:message pre='${preComMessage}' key='js.noneDept' />";
var	Message_No_Add_Dept	=	"<ikep4j:message pre='${preComMessage}' key='js.noAddDept' />";
var	Message_No_Add_User	=	"<ikep4j:message pre='${preComMessage}' key='js.noAddUser' />";
var	Message_No_Add_Line	=	"<ikep4j:message pre='${preComMessage}' key='js.noAddLine' />";
var	Message_No_Dept_Usr	=	"<ikep4j:message pre='${preComMessage}' key='js.noDeptUsr' />";
var	Message_Btn_Add		=	"<ikep4j:message pre='${preComMessage}' key='js.btnAdd' />";
var	Message_Select_Item	=	"<ikep4j:message pre='${preComMessage}' key='js.selectItem' />";
var	Message_Dup_Rcv		=	"<ikep4j:message pre='${preComMessage}' key='js.dupRcv' />";
var	Message_Dup_Line	=	"<ikep4j:message pre='${preComMessage}' key='js.dupLine' />";

// 결재선에 저장된 부서정보 중복 제거를 위한 Set
var $apprLine = "";
		
(function($) {	
	
	fnCaller = function (params, dialog) {
		if(params) {
			if(params.items) {
				appendItem(params.items);
			}
			if(params.search) {
				$("#treesch").val(params.search);
			}
		}
		
		dialogWindow = dialog;
		$("#btnClose").click(function() {
			dialogWindow.close();
		});
		
		isLayerWindow = true;
	};	
	
	$(document).ready(function() {	
		// 결재선에 저장된 부서정보 중복 제거를 위한 Set
		$apprLine = $jq.parseJSON($(opener.document).find("input[name=apprLine]").val());
		
		var param = iKEP.getPopupArgument();

		if(param && param.items) {
			if(param.items.length > 0) {
				var items = new Array();
				$.each(param.items, function() { items.push(this); });
				appendItem(items);
			}
			if(param.search) {
				$jq("#treesch").val(param.search);
			}
			$("#apprLineType input:radio").eq(param.apprLineType).attr('checked',true);
		}
		
		// Tab
		tab = $("#divTabs").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tab-orggroup" : createGroupTree();
						break;
				}
			}
		});
		
		// init Setting
		createGroupTree();	


		if(isLayerWindow == false) {
			$("#btnClose").click(function() {iKEP.closePop();});
		}		

		// 사용자 결재선 Select Box 변경시
		$("#userLineId").change(function() {
			// 해당 사용자 정의 결재선 정보 목록 표시
			if($("#userLineId option:selected").val()!="")
				getApprUserLineSub($("#userLineId option:selected").val());	
		}).trigger('change');		

		$("#btnRemove").click(function() {
			if($("#userLineId option:selected").val()!="")
			{
				var userLineId=$("#userLineId option:selected").val();
				$jq.ajax({
					url : '<c:url value='/approval/work/apprLine/ajaxDeleteApprUserLine.do'/>',
					data : {userLineId:userLineId},
					type : "get",
					success : function(result) {
						alert("<ikep4j:message pre='${preMessage}' key='deleteSuccess' />");
						
						// 사용자 정의 결재선 Select 추가
						changeApprUserLine(3);
						
						// Select Box Reload
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});			
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='noSelectApprUserLine'/>");
				return false;
			}					
			return false; 
		});

		
		// 결재선 지정 적용		
		$("#btnApply").click(function() {
			var result = [];
			var $resultItems = $("#ulResult").children();
			//opener.document.mainForm.apprLineType.value=$("#mainForm input[name=apprLineType]:checked").val();
			
			if($resultItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noApprLine'/>");
				return false;
			}

			$.each($resultItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').is(":checked");
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});

			$("#apprLineDiv").empty();
			// 결재선 변경시
			if('${apprLine.apprStatus}'=='1') {
				var idx=0;
				
				$.each(result, function() {

					var apprId = $("#mainForm input[name=apprId]").val();
					var modifyReason = $("#mainForm input[name=modifyReason]").val();
					
					var apprUserType = this.type=='group'?1:0;
					this.apprType=parseInt(this.apprType)
					this.apprDate = this.apprDate==undefined?"":this.apprDate;
					
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprId'				value='"+ apprId +"' />").appendTo( "#apprLineDiv" );	
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprType'			value='"+ this.apprType +"' />").appendTo( "#apprLineDiv" );	
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverId'			value='"+ this.id +"' />").appendTo( "#apprLineDiv" );  
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverName'		value='"+ this.userName +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverGroupName'	value='"+ this.teamName +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverJobTitle'	value='"+ this.jobTitleName +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverType'		value='"+ this.approverType +"' />").appendTo( "#apprLineDiv" );	
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprOrder'			value='"+ this.apprOrder +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].lineModifyAuth'		value='"+ this.lineModifyAuth +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].docModifyAuth'		value='"+ this.docModifyAuth +"' />").appendTo( "#apprLineDiv" );
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].readModifyAuth'		value='"+ this.readModifyAuth +"' />").appendTo( "#apprLineDiv" );	
					$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprDate'			value='"+ this.apprDate +"' />").appendTo( "#apprLineDiv" );
					idx++;
				}); 

				$jq.ajax({
					url : '<c:url value='/approval/work/apprLine/updateReceiverApprLine.do'/>',
					data : $jq("#mainForm").serialize(),
					type : "post",
					success : function(resultR) {
						alert("<ikep4j:message pre='${preMessage}' key='changeSuccess' />");
						
						if(dialogWindow != null) {
							try {	
								// callback function : dialog 생성시 callback handler 셋팅됨.
								dialogWindow.callback(result);
								dialogWindow.close();
							} catch(e) {}
						} else {
							iKEP.returnPopup(result);
						}				
						
					},
					error : function(xhr, exMessage) {
						//var errorItems = $.parseJSON(xhr.responseText).exception;
						//validator.showErrors(errorItems);
						alert('error1')
						return;
					}
				});
			
			} else {

				if(dialogWindow != null) {
					try {	
						// callback function : dialog 생성시 callback handler 셋팅됨.
						dialogWindow.callback(result);
						dialogWindow.close();
					} catch(e) {}
				} else {
					iKEP.returnPopup(result);
				}
			}
		});
				
		// 사용자 수신처 등록
		$("#btnSave").click(function() {
			
			var $resultItems = $("#ulResult").children();
					
			if($resultItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noApprLine' />");
				return false;
			}

			$("#apprUserLineDiv").empty();	
			var idx=0;
			$.each($resultItems, function() {
		
				//var apprUserType="0";	//사용자			
				var data = $.extend({}, $.data(this, "data"));
				var id = data.type=='group'?data.code:data.id;

				$("<input type='hidden' name='apprUserLineSub[" + idx + "].apprUserId'		value='"+ id +"' />").appendTo( "#apprUserLineDiv" ); 						
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].apprUserType' 	value='"+ data.approverType +"' />").appendTo( "#apprUserLineDiv" );  
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].apprType'		value='"+ data.apprType +"' />").appendTo( "#apprUserLineDiv" );
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].apprOrder'		value='"+ (idx+1) +"' />").appendTo( "#apprUserLineDiv" );// apprOrder 병렬시 합의는 통일.
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].apprJobTitle'	value='"+ data.jobTitleName +"' />").appendTo( "#apprUserLineDiv" );
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].lineModifyAuth'	value='"+ data.lineModifyAuth +"' />").appendTo( "#apprUserLineDiv" );
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].docModifyAuth'	value='"+ data.docModifyAuth +"' />").appendTo( "#apprUserLineDiv" );
				$("<input type='hidden' name='apprUserLineSub[" + idx + "].readModifyAuth'	value='"+ data.readModifyAuth +"' />").appendTo( "#apprUserLineDiv" );				
				idx++;
			});
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
					required : "<ikep4j:message key='NotEmpty.apprUserLine.userLineName' />",
					maxlength : "<ikep4j:message key='Size.apprUserLine.userLineName' />"
				}
			},
			submitHandler : function() {				
				$jq.ajax({
					url : '<c:url value='/approval/work/apprLine/ajaxSaveApprUserLine.do'/>',
					data : $jq("#mainForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
						$('input[name=userLineName]').val('');
						
						// 사용자 정의 수신처 Select 추가
						changeApprUserLine(3);
						
						// Select Box Reload
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

	// Appr User Line Select List
	changeApprUserLine = function(apprType) {		
        $jq.get('<c:url value="/approval/work/apprLine/getApprUserLine.do"/>',{apprType:apprType},
			function(data) {
				$jq("#userLineId option:eq(0)").nextAll().remove();	
				$jq.each(data, function() {	
					$jq("<option/>").attr("value",this.userLineId).text(this.userLineName).appendTo("#userLineId");
				});				
			}
		)
		return false; 
	};
	
	// Appr User Line Sub List
	getApprUserLineSub = function(userLineId) {		
        $jq.get('<c:url value="/approval/work/apprLine/getApprUserLineSub.do"/>',{userLineId:userLineId},
			function(data) {
	
				var items1=[];
				$("#ulResult>li").remove();
				
				$jq.each(data, function() {	
					
					var name = "";
					if(this.apprUserType==0)
						name = apprType[this.apprType] + " " + this.userName + " " + this.apprJobTitle + " " + this.teamName;
					else 
						name = apprType[this.apprType] + " " + this.teamName;
					
					items1.push({// 공통필수
						type			:	this.apprUserType==0?'user':'group',
						apprType		:	this.apprType,
						id				:	this.apprUserId,
						code			:	this.apprUserId,								
						userName		:	this.userName,
						teamName		:	this.teamName,
						jobTitleName	:	this.apprJobTitle,			
						approverType	:	this.apprUserType,//결재유형 사용자:0,부서:1
						apprOrder		:	this.apprOrder,				
						lineModifyAuth	:	this.lineModifyAuth,
						docModifyAuth	:	this.docModifyAuth,	
						readModifyAuth	:	this.readModifyAuth,	
						name			:	name
					});
				});	
				appendItem(items1);		
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
	
	$.template("tmplResultGroupItem", '<li class="ui-state-default">\${name}</li>');
	$.template("tmplResultUserItem", '<li class="ui-state-default">\${name} ${teamName}</li>');

})(jQuery);

</script>

<!--blockListTable Start-->
<div class="popup" style="min-width:900px">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->
	<!--popup_contents Start-->
	<div id="popup_contents" style="margin:0px">
 
		<!--tab Start-->		
		<div id="divTabs" class="iKEP_tab">
			<ul>
				<li><a href="#tab-orggroup"><ikep4j:message pre='${preView}' key='tab.group' /></a></li>
				<li><a href="#tab-searchgroup"><ikep4j:message pre='${preView}' key='tab.search' /></a></li>
			</ul>				
			<!--//tab End-->

			<div class="tab_con">		
				<div id="tabs-1">
					<form id="mainForm" method="post">
					<input name="controlTabType" title="" type="hidden" value="1:0:0:0" />
					<input name="controlType" title="" type="hidden" value="ORG" />
					<input name="selectType" title="" type="hidden" value="ALL" />
					<input name="apprId" title="" type="hidden" value="${apprDoc.apprId}" />
					<input name="userLineType" title="" type="hidden" value="1" />
					<div id="apprLineDiv"></div>
					<div id="apprUserLineDiv"></div>
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
						</div>

						<div class="shuttle_m" style="margin-top:140px;">
							<div class=""> 
								<ul id="apprType">
									<li><strong></strong></li>				
									<li class="textLeft"><span><input type="hidden" title="<ikep4j:message pre='${preCommon}' key='receive' />" name="apprType" value="3" /></span></li>
									<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="Add" /></a></li>
									<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="Delete" /></a></li>
									<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="Reset" /></a></li>
								</ul>				
							</div>									
						</div>


						<div class="blockRight" style="margin-top:10px;width:56%; border:2px solid #eee; padding:10px 10px 0 10px;">				
							<div class="sbox">				
								<div class="subTitle_2 noline">
								<h3><ikep4j:message pre='${preView}' key='receiveLineInfo' /></h3>
								</div>
							
								<!--blockSearch Start-->
								<div class="blockSearch">
									<div class="corner_RoundBox03">
									
									<table summary="<ikep4j:message pre='${preView}' key='userReceiveLineName' />">
									<caption></caption>
									<tbody>
							
									<tr>
										<th scope="row"><ikep4j:message pre='${preView}' key='userReceiveLineName' /></th>
										<td width="65%" class="textLeft">
											<select title="<ikep4j:message pre='${preView}' key='changeLine' />" id="userLineId" style="width:80%;">
											<option value=''><ikep4j:message pre='${preView}' key='select' /></option>
											<c:forEach var="apprUserLineList" items="${apprUserLineList}"> 
											<option value='${apprUserLineList.userLineId}'>${apprUserLineList.userLineName}</option>
											</c:forEach>											
											</select>												
										</td>
										<td><a id="btnRemove" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></td>
									</tr>
									</tbody>
									</table>

									</div>

								</div>	
								<!--//blockSearch End-->
				
								<div class="" style="height:330px;">								
									<div class="blockLeft" style="width:100%;height:315px;overflow-y:auto;">
									<ul id="ulResult" class="list-selectable" style="width:100%;"></ul>
									</div>						

								</div>
								
								<!--blockSearch Start-->
								<div class="blockSearch">
									<div class="corner_RoundBox03">
									<table summary="<ikep4j:message pre='${preView}' key='defaultInfo' />">
									<caption></caption>
									<tbody>
							
									<tr>
										<th scope="row"><ikep4j:message pre='${preView}' key='receiveLineName' /></th>
										<td width="65%" class="textLeft"><input type="text" class="inputbox" id="userLineName" name="userLineName" title="<ikep4j:message pre='${preView}' key='lineName' />" size="50" value="" /></td>
										<td><a id="btnSave" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></td>
									</tr>
									</tbody>
									</table>
									</div>	
								</div>	
								<!--//blockSearch End-->								
								
							</div>

						</div>
				
					</div>
					<!--//blockShuttle End-->
					<div class="clear"></div>
					<!--blockButton Start-->
					<div class="blockButton suttle_btn">
						<ul>
							<li><a id="btnApply" class="button"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
							<li><a id="btnClose" class="button"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
						</ul>
					</div>
					</form>
						
				</div>
			</div>
				
		</div>
		<!--//popup_contents End-->	
	
	</div>

</div> 
<!--//popup End-->
