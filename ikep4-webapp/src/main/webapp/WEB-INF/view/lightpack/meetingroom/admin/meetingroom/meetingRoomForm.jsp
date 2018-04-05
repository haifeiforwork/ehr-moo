<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!-- 
(function($) {
	
	goList = function() {
		
		document.location.href = "<c:url value='/lightpack/meetingroom/meetingroom/meetingRoomMain.do'/>";
			
	};
	
	setAddress = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#managerName").val(managerName);
			$jq("#managerId").val(userId);
		});
	};
	
	setSubAddress = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#subManagerName").val(managerName);
			$jq("#subManagerId").val(userId);
		});
	};
	
	setLastAddress = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#lastManagerName").val(managerName);
			$jq("#lastManagerId").val(userId);
		});
	};

	setUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#managerName").val(managerName);
			$jq("#managerId").val(userId);
		});
	};
	
	setSubUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#subManagerName").val(managerName);
			$jq("#subManagerId").val(userId);
		});
	};
	
	setLastUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#lastManagerName").val(managerName);
			$jq("#lastManagerId").val(userId);
		});
	};
	
	fileUploadForMeeting = function(userId, targetId, imgSrc, callback) { 

		var options = {
				
				title : "Image Upload",
				url : iKEP.getContextRoot() + '/support/fileupload/uploadFormForMeetingRoom.do?userId='+userId+'&targetId='+targetId+"&editorAttachYn=0&imageYn=1",
				width : 550,
				height : 400,
				modal : true,
				resizable : false,
				scroll : "no",
				params : { 
					
					imgSrc : imgSrc 
				},
				callback: function(result) {
					
					callback(result.status, result.fileId, result.fileName, result.messgage, result.gubun);
				}
			};
		iKEP.showDialog(options);
	};
	
	function afterFileUpload(status, fileId, fileName, message, gubun){
		
		$jq('#fileImage').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=' />" + fileId) ;
		$jq("#fileId").val(fileId);
	};
	
	function resetFileUpload() {
		
		$jq("#fileImage").attr("src", "<c:url value='/base/images/common/noimage_170x170.gif'/>") ;
		$jq("#fileId").val("");
	}
	
	var validOptions = {
			
	    rules : {
	    	
	    	meetingRoomName : {
	    		
	    		required : true,
				rangelength : [0, 30]
	        },
	        meetingRoomEnglishName : {
	    		
	        	required : true,
				terminology : true,
				rangelength : [0, 100]
	        },
			phone : {
		    	
		    	numberHyphen : true,
				rangelength : [0, 30]
		    }
	    },
	    messages : {
	    	
	    	meetingRoomName : {
	    		
	            direction : "bottom",
	            required : '<ikep4j:message key="NotNull.MeetingRoom.meetingRoomName"/>',
				rangelength : '<ikep4j:message key="Size.MeetingRoom.meetingRoomName"/>'
	        },
			meetingRoomEnglishName : {
	    		
	            direction : "bottom",
	            required : '<ikep4j:message key="NotNull.MeetingRoom.meetingRoomEnglishName"/>',
				terminology : '<ikep4j:message key="Terminology.MeetingRoom.meetingRoomEnglishName"/>',
				rangelength : '<ikep4j:message key="Size.MeetingRoom.meetingRoomEnglishName"/>'
	        },
	        phone : {
	        	
	        	numberHyphen : '<ikep4j:message key="NumberHyphen.MeetingRoom.phone"/>',
	    		rangelength : '<ikep4j:message key="Size.MeetingRoom.phone"/>'
	        }
	    },
	    submitHandler : function(form) {
			
	    	if(confirm("<ikep4j:message pre='${preMessage}' key='save' />")) {
	    	
		    	$jq.ajax({     
					url : '<c:url value="/lightpack/meetingroom/meetingroom/createMeetingRoom.do" />',     
					data : $jq("#meetingRoomForm").serialize(),     
					type : "post",     
					success : function(result) {     
						
						var buildingId = $jq("#buildingId").val();
						var floorId = $jq("#floorId").val();
						
						var url = "<c:url value='/lightpack/meetingroom/meetingroom/meetingRoomMain.do'/>";
						var param = "buildingId=" + buildingId + "&floorId=" + floorId;
						
						document.location.href = url + "?" + param;
					},
					error : function(event, request, settings) {
						
						 alert("error");
					}
				});
	    	
	    	}
	    }
	 };
	
	changeRoomFloorSelect = function() {
		
		jQuery.ajax({     
			url : '<c:url value="/lightpack/meetingroom/buildingfloor/getFloorList.do" />',     
			data : {
				
				buildingId : $("#buildingId").val()
			},     
			type : "post",     
			success : function(data) {
				
				//jQuery("#floorId").find("option").remove().end().append("<option value=\"\"></option>");
				jQuery("#floorId").find("option").remove();
				
				if(data.length > 0) {
					
					$jq.each(data, function(i){
						
						var useFlagValue = "";
						
						if(this.useFlag == "0") {
							
							useFlagValue = "[<ikep4j:message pre='${preDetail}' key='useFlagN' />]";
						}
						
						jQuery("#floorId").append("<option value=\""+ this.buildingFloorId +"\">"+ this.buildingFloorName + useFlagValue + "</option>");
					});
				} else {
					
					jQuery("#floorId").find("option").remove().end().append("<option value=\"\">===============</option>");
				}
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
		jQuery.ajax({     
			url : '<c:url value="/lightpack/meetingroom/buildingfloor/getToolList.do" />',     
			data : {
				
				buildingId : $("#buildingId").val()
			},     
			type : "post",     
			success : function(data) {
				jQuery("#toolsSub").remove();
				jQuery("#toolsDiv").append('<span id="toolsSub"></span>');
				if(data.length > 0) {
					
					$jq.each(data, function(i){
						jQuery("#toolsSub").append('<input name="carTool" type="checkbox" value="'+this.cartooletcId+'"/>'+ this.cartooletcName+'&nbsp;');
					});
				} 
			},
			error : function(event, request, settings) { alert("error"); }
		});  
	};


	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#meetingRoomForm", validOptions);
		
		$jq("#btnSave").click(function() {  
			
			if($jq("#buildingId").val() == "") {
				
				alert("<ikep4j:message pre='${preMessage}' key='noBuilding' />");
				
				$jq("#buildingId").focus();
				
				return;
			}
			
			if($jq("#floorId").val() == "") {
				
				alert("<ikep4j:message pre='${preMessage}' key='noFloor' />");
				
				$jq("#floorId").focus();
				
				return;
			}
			var tmpCarTool = "";
			$("#meetingRoomForm input[name=carTool]:checked").each(function(index) {
				if(tmpCarTool == ""){
					tmpCarTool = $(this).val();
				}else{
					tmpCarTool = tmpCarTool+"/"+$(this).val();
				}
				
				$jq("#carTools").val(tmpCarTool);
			});
			
			$jq("#meetingRoomForm").submit();
		});   
		
		$jq("#btnList").click(function() {  
			
			document.location.href = '<c:url value="/lightpack/meetingroom/meetingroom/meetingRoomMain.do" />';
		});   
		
		$jq("#btnDelete").click(function() {  
			
				if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
					
					$jq.ajax({     
						url : '<c:url value="/lightpack/meetingroom/meetingroom/deleteMeetingRoom.do" />',     
						data :  {
							
							meetingRoomIds : '${meetingRoom.meetingRoomId}'
						},     
						type : "post",     
						success : function(result) {      
							
							goList();
						},
						error : function(event, request, settings){
							
							 alert("error");
						}
					});
				}  
		});   
		
		$jq('#addrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#managerName').trigger("keypress");
		});
		
		$('#addrBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$('#subAddrBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setSubAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$('#lastAddrBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setLastAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#managerName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('#subManagerName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setSubUser);			
		});
		
		$jq('#lastManagerName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setLastUser);			
		});
		
		$jq('#btnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#managerName').val('');
			$jq('#managerId').val('');
		});
		
		$jq('#subBtnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#subManagerName').val('');
			$jq('#subManagerId').val('');
		});
		
		$jq('#lastBtnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#lastManagerName').val('');
			$jq('#lastManagerId').val('');
		});
		
		$jq("#btnFileUpload").click(function(){
			
			fileUploadForMeeting('','', '', afterFileUpload);
		});
		
		$jq("#btnFileReset").click(function(){
			
			resetFileUpload();
		});
		
		$('#buildingId').change( function() { 
			
			changeRoomFloorSelect();
		});
		
	});
	
})(jQuery);  


//-->	
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="meetingRoom" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="meetingRoom" /></h2> 
</div>
<!--//pageTitle End-->

<!--blockDetail Start-->
<div class="blockDetail">

<form id="meetingRoomForm" method="post" action="">
	<input name="carTools" id="carTools" type="hidden" value=""/>
	<spring:bind path="meetingRoom.meetingRoomId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
	</spring:bind> 

	<table summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />">
		<caption></caption>
		<colgroup>
			<col width="15%"/>
			<col width="10%"/>
			<col width="70%"/>
		</colgroup>	

		<tbody>
		
			<tr> 
				<th scope="row" rowspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='meetingRoomName' />
				</th>
				<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name' /></th>
				<td width="85%">
					<div>
					<input type="text" name="meetingRoomName" id="meetingRoomName" value="${meetingRoom.meetingRoomName}" size="50" class="inputbox" title="<ikep4j:message pre='${preDetail}' key='meetingRoomName' />"/>
					</div>
				</td> 
			</tr>	
			<tr>
				<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName' /></th>
				<td>
					<div>
						<input type="text" name="meetingRoomEnglishName" id="meetingRoomEnglishName" value="${meetingRoom.meetingRoomEnglishName}" size="50" class="inputbox" />
					</div>
				</td>
			</tr>
			
			<spring:bind path="meetingRoom.buildingId">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='building' />
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='building' />" >
						<c:if test="${!empty buildingList}">
						<c:forEach var="building" items="${buildingList}" >
							<option value="${building.buildingFloorId}" <c:if test="${building.buildingFloorId==status.value}">selected</c:if>>
								${building.buildingFloorName}
								<c:if test="${building.useFlag == '0'}">[<ikep4j:message pre='${preDetail}' key='useFlagN' />]</c:if>
							</option>
						</c:forEach>
						</c:if>
						<c:if test="${empty buildingList}">
							<option value="">===============</option>
						</c:if>
					</select>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.floorId">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='floor' />
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='floor' />" >
						<c:if test="${!empty floorList}">
						<c:forEach var="floor" items="${floorList}" >
							<option value="${floor.buildingFloorId}" <c:if test="${floor.buildingFloorId == status.value}">selected</c:if>>
								${floor.buildingFloorName}
							</option>
						</c:forEach>
						</c:if>
						<c:if test="${empty floorList}">
							<option value="">===============</option>
						</c:if>
					</select>
					</div>
				</td> 
			</tr>		
			</spring:bind>
			<spring:bind path="meetingRoom.toolId">
			<tr> 
				<th scope="row" colspan="2">
					회의실 전용 기자재
				</th>
				<td>
					<div id="toolsDiv">
						<span id="toolsSub">
					<c:if test="${!empty toolList}">
						<c:forEach var="tool" items="${toolList}" >
							<input name="carTool" type="checkbox" value="${tool.cartooletcId}" <c:if test="${tool.toolUseFlag != '0'}">checked=""checked"</c:if>/>${tool.cartooletcName}
						</c:forEach>
						</c:if>
						</span>
					</div>
				</td> 
			</tr>			
			</spring:bind>
			
			<spring:bind path="meetingRoom.managerId">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />1
				</th>
				<td>
					<div>
					<label><input name="autoApprove" type="checkbox" value="Y" <c:if test="${meetingRoom.autoApprove != 'N'}">checked="checked"</c:if>/> <ikep4j:message pre='${preDetail}' key='autoApprove' /></label><br/>
					<input name="${status.expression}"  id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
					<input type="text" class="inputbox" id="managerName" name="managerName" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" value="${managerName}" size="30" />
					<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
						<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search'/></span>
					</a>
					<a id="addrBtn" href="#a" class="button_ic">
						<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address'/></span>
					</a>
					<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
						<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete'/></span>
					</a>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.subManagerId">
			<tr> 
				<th scope="row" colspan="2">
					담당자2
				</th>
				<td>
					<div>
					<input name="${status.expression}"  id="${status.expression}" type="hidden" value="${status.value}" />
					<input type="text" class="inputbox" id="subManagerName" name="subManagerName"  value="${subManagerName}" size="30" />
					<a name="subAddrSearchBtn" id="subAddrSearchBtn" class="button_ic" href="#a">
						<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search'/></span>
					</a>
					<a id="subAddrBtn" href="#a" class="button_ic">
						<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address'/></span>
					</a>
					<a class="button_ic valign_bottom" href="#a" id="subBtnDeleteControl">
						<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete'/></span>
					</a>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.lastManagerId">
			<tr> 
				<th scope="row" colspan="2">
					담당자3
				</th>
				<td>
					<div>
					<input name="${status.expression}"  id="${status.expression}" type="hidden" value="${status.value}" />
					<input type="text" class="inputbox" id="lastManagerName" name="lastManagerName"  value="${lastManagerName}" size="30" />
					<a name="lastAddrSearchBtn" id="lastAddrSearchBtn" class="button_ic" href="#a">
						<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search'/></span>
					</a>
					<a id="lastAddrBtn" href="#a" class="button_ic">
						<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address'/></span>
					</a>
					<a class="button_ic valign_bottom" href="#a" id="lastBtnDeleteControl">
						<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete'/></span>
					</a>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.capacity">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="20" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.area">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="20" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.phone">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="20" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/> 예) 010-2525-1004
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.equipment">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="50" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/> &nbsp; 쉼표로 구분해서 입력. 예)프로젝터, 노트북, 생수기, 프린터
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.description">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<textarea name="${status.expression}" class="tabletext" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" cols="300" rows="7">${status.value}</textarea>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.useFlag">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" >
						<option value="1" <c:if test="${'1'==meetingRoom.useFlag}">selected</c:if>>
								<ikep4j:message pre='${preDetail}' key='useFlagY' />
						</option>
						<option value="0" <c:if test="${'0'==meetingRoom.useFlag}">selected</c:if>>
								<ikep4j:message pre='${preDetail}' key='useFlagN' />
						</option>
					</select>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="meetingRoom.fileId">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<a id="btnFileUpload" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='fileUpload' /></span></a>
					<a id="btnFileReset" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reset' /></span></a>
					<div style="text-align:center; vertical-align:middle;">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
					<img id="fileImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${status.value}' />"  alt="<ikep4j:message pre='${preForm}' key='signImage' />" onerror="this.src='<c:url value="/base/images/common/noimage_170x170.gif"/>'" />
					</div>
				</td> 
			</tr>				
			</spring:bind>
					
		</tbody>					
	</table>
</form>

</div>
<!--blockDetail End-->				
					
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="btnSave" class="button" href="#a" ><span>등록</span></a></li>
		
		<c:if test="${!empty meetingRoom.meetingRoomId}">
			<li><a id="btnDelete" class="button" href="#a" ><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</c:if>
		
		<li><a id="btnList" class="button" href="#a" ><span><ikep4j:message pre='${preButton}' key='list' /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
					
				