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
		
		document.location.href = "<c:url value='/lightpack/meetingroom/cartooletc/cartooletcMain.do'/>";
			
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
		    	
		    	cartooletcName : {
		    		
		    		required : true,
					rangelength : [0, 30]
		        },
		        cartooletcEnglishName : {
		    		
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
		    	
		    	cartooletcName : {
		    		
		            direction : "bottom",
		            required : '<ikep4j:message key="NotNull.MeetingRoom.cartooletcName"/>',
					rangelength : '<ikep4j:message key="Size.MeetingRoom.cartooletcName"/>'
		        },
		        cartooletcEnglishName : {
		    		
		            direction : "bottom",
		            required : '<ikep4j:message key="NotNull.MeetingRoom.cartooletcEnglishName"/>',
					terminology : '<ikep4j:message key="Terminology.MeetingRoom.cartooletcEnglishName"/>',
					rangelength : '<ikep4j:message key="Size.MeetingRoom.cartooletcEnglishName"/>'
		        },
		        phone : {
		        	
		        	numberHyphen : '<ikep4j:message key="NumberHyphen.MeetingRoom.phone"/>',
		    		rangelength : '<ikep4j:message key="Size.MeetingRoom.phone"/>'
		        }
		    },
		    submitHandler : function(form) {
				
		    	if(confirm("<ikep4j:message pre='${preMessage}' key='save' />")) {
					var selIndex = $('#categoryId option').index($("#categoryId option:selected"));
					if(selIndex>1){//기존 분류 선택이면
						$("#categoryName").val($("#categoryId option:selected").text());
					}
					var selIndex2 = $('#regionId option').index($("#regionId option:selected"));
					if(selIndex2>1){//기존 위치 선택이면
						$("#regionName").val($("#regionId option:selected").text());
					}
					
			    	$jq.ajax({     
						url : '<c:url value="/lightpack/meetingroom/cartooletc/createCartooletc.do" />',     
						data : $jq("#cartooletcForm").serialize(),     
						type : "post",     
						success : function(result) {     
							
							var categoryId = $jq("#categoryId").val();
							var regionId = $jq("#regionId").val();
							
							var url = "<c:url value='/lightpack/meetingroom/cartooletc/cartooletcMain.do'/>";
							var param = "categoryId=" + categoryId + "&regionId=" + regionId;
							
							document.location.href = url + "?" + param;
						},
						error : function(event, request, settings) {
							
							 alert("error");
						}
					});
		    	
		    	}
		    }
		 };
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#cartooletcForm", validOptions);
		
		$jq("#btnList").click(function() {  
			
			document.location.href = "<c:url value='/lightpack/meetingroom/cartooletc/cartooletcMain.do'/>";
		});   
		
		$jq("#btnDelete").click(function() {  
			
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
				
				$jq.ajax({     
					url : '<c:url value="/lightpack/meetingroom/cartooletc/deleteCartooletc.do" />',     
					data :  {
						
						cartooletcIds : '${cartooletc.cartooletcId}'
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
		
		$jq("#btnSave").click(function() {  
			
			if($jq("#categoryId").val() == "") {
				
				alert("<ikep4j:message pre='${preMessage}' key='noCategory' />");
				
				$jq("#categoryId").focus();
				
				return;
			}
			
			if($jq("#regionId").val() == "") {
				
				alert("<ikep4j:message pre='${preMessage}' key='noRegion' />");
				
				$jq("#regionId").focus();
				
				return;
			}
			
			$jq("#cartooletcForm").submit();
		});   
		
		$jq('#addrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#managerName').trigger("keypress");
		});
		
		$jq('#subAddrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#subManagerName').trigger("keypress");
		});
		
		$jq('#lastAddrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#lastManagerName').trigger("keypress");
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
		
		$('#categoryId').change( function() { 
			
			var selIndex = $('#categoryId option').index($("#categoryId option:selected"));
			
			if(selIndex==1){//추가이면
				$("#addCategorySpan").show();
			}else{
				$("#categoryName").val("");//초기화
				$("#addCategorySpan").hide();
			}
		});
		$('#regionId').change( function() { 
			
			var selIndex = $('#regionId option').index($("#regionId option:selected"));
			
			if(selIndex==1){//추가이면
				$("#addRegionSpan").show();
			}else{
				$("#regionName").val("");//초기화
				$("#addRegionSpan").hide();
			}
		});
	});
})(jQuery);  

//-->
</script>



<h1 class="none"><ikep4j:message pre="${preHeader}" key="cartooletc" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="cartooletc" /></h2> 
</div>
<!--//pageTitle End-->

<!--blockDetail Start-->
<div class="blockDetail">

<form id="cartooletcForm" method="post" action="">
	


	<spring:bind path="cartooletc.cartooletcId">
		<input name="${status.expression}" type="hidden" value="${status.value}" />
	</spring:bind> 


	<table summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />">
		<caption></caption>
		<colgroup>
			<col width="15%"/>
			<col width="10%"/>
			<col width="70%"/>
		</colgroup>	

		<tbody>
		
			<tr> 
				<th scope="row" rowspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='cartooletcName' />
				</th>
				<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name' /></th>
				<td width="85%">
					<div>
					<input type="text" name="cartooletcName" id="cartooletcName" value="${cartooletc.cartooletcName}" size="50" class="inputbox" title="<ikep4j:message pre='${preDetail}' key='meetingRoomName' />"/>
					</div>
				</td> 
			</tr>	
			<tr>
				<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName' /></th>
				<td>
					<div>
						<input type="text" name="cartooletcEnglishName" id="cartooletcEnglishName" value="${cartooletc.cartooletcEnglishName}" size="50" class="inputbox" />
					</div>
				</td>
			</tr>
			
			<spring:bind path="cartooletc.regionId">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> 장소
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='region' />" >
						<option value="">--선택하세요--</option>
						<option value="new">--새로 추가--</option>
						<c:forEach var="region" items="${regionList}" varStatus="status">
							<option value="${region.regionId}" <c:if test="${region.regionId==cartooletc.regionId}">selected</c:if>>${region.regionName}</option>
						</c:forEach>
					</select>
					&nbsp;
					<span id="addRegionSpan" style="display:none"><input type="text" id="regionName" name="regionName" size="20" class="inputbox" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="cartooletc.categoryId">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='category' />
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='category' />" >
						<option value="">--선택하세요--</option>
						<option value="new">--새로 추가--</option>
						<c:forEach var="category" items="${categoryList}" varStatus="status">
							<option value="${category.categoryId}" <c:if test="${category.categoryId==cartooletc.categoryId}">selected</c:if>>${category.categoryName}</option>
						</c:forEach>
					</select>
					&nbsp;
					<span id="addCategorySpan" style="display:none"><input type="text" id="categoryName" name="categoryName" size="20" class="inputbox" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			
			<spring:bind path="cartooletc.managerId">
			<tr> 
				<th scope="row" colspan="2">
					1차 <ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<label><input name="autoApprove" type="checkbox" value="Y" <c:if test="${cartooletc.autoApprove != 'N'}">checked="checked"</c:if>/> <ikep4j:message pre='${preDetail}' key='autoApprove' /></label><br/>
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
			
			<spring:bind path="cartooletc.subManagerId">
			<tr> 
				<th scope="row" colspan="2">
					2차 담당자
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
			
			<spring:bind path="cartooletc.lastManagerId">
			<tr> 
				<th scope="row" colspan="2">
					3차 담당자
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
			
			
			
			<spring:bind path="cartooletc.phone">
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
			
			
			<spring:bind path="cartooletc.description">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='description2' /></th>
				<td>
					<div>
					<textarea name="${status.expression}" class="tabletext"  cols="300" rows="7">${status.value}</textarea>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="cartooletc.useFlag">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='${status.expression}' />
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" >
						<option value="1" <c:if test="${'1'==cartooletc.useFlag}">selected</c:if>>
								<ikep4j:message pre='${preDetail}' key='useFlagY' />
						</option>
						<option value="0" <c:if test="${'0'==cartooletc.useFlag}">selected</c:if>>
								<ikep4j:message pre='${preDetail}' key='useFlagN' />
						</option>
					</select>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			<spring:bind path="cartooletc.viewYn">
			<tr> 
				<th scope="row" colspan="2">
					<span class="colorPoint"></span> 공개여부
				</th>
				<td>
					<div>
					<select id="${status.expression}" name="${status.expression}" title="" >
						<option value="1" <c:if test="${'1'==cartooletc.viewYn}">selected</c:if>>
								공개
						</option>
						<option value="0" <c:if test="${'0'==cartooletc.viewYn}">selected</c:if>>
								비공개
						</option>
					</select>
					</div>
				</td> 
			</tr>				
			</spring:bind>
			<spring:bind path="cartooletc.fileId">
			<tr> 
				<th scope="row" colspan="2">
					<ikep4j:message pre='${preDetail}' key='fileId2' />
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
		
		<c:if test="${!empty cartooletc.cartooletcId}">
			<li><a id="btnDelete" class="button" href="#a" ><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</c:if>
		
		<li><a id="btnList" class="button" href="#a" ><span><ikep4j:message pre='${preButton}' key='list' /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
					
				