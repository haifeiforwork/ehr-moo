<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
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
var dialogWindow;

function fnCaller(param, dialog){
	dialogWindow = dialog;
}

(function($) {
	getList = function() {
		$.ajax({
			url : '<c:url value="/lightpack/meetingroom/cartooletc/cartooletcList.do" />',     
			data :  $("#searchForm").serialize(),     
			type : "post",     
			loadingElement : { container:"#listDiv" },
			success : function(result) {   
				$("#listDiv").html(result);
			},
			error : function(event, request, settings) { 
				alert("error"); 
			}
		});       
	};
	
	getView = function(cartooletcId) {
		document.location.href = '<c:url value="/lightpack/meetingroom/cartooletc/cartooletcForm.do?cartooletcId=" />' + cartooletcId;
	};
	
	changeSort = function(meetingRoomId, sortOrder) {
		$.ajax({
			url : '<c:url value="/lightpack/meetingroom/cartooletc/sortMeetingRoom.do" />',     
			data : {
				buildingId : $('#buildingId').val(),
				meetingRoomId : meetingRoomId, 
				sortOrder : sortOrder
			},     
			type : "post",     
			success : function(result) {   
				getList();
			},
			error : function(event, request, settings) { 
				alert("error"); 
			}
		});  
	};
	
	setRow = function(buildingId, obj) {
		$("#sortTable").children().each(function(index, item) { 
			$(item).removeClass();
		});
		
		var index = $(obj).parent().parent().index();
		var liObj = $("#sortTable").children().get(index);
		
		$(liObj).addClass("bgSelected");
		
		//getView(roomId);
	};
	

	
	$(document).ready(function() { 
		$("#btnNew").click(function() {  
			document.location.href = '<c:url value="/lightpack/meetingroom/cartooletc/cartooletcForm.do" />';
		});   
		
		$("#btnDelete").click(function() {
			var itemIds = new Array();
			$("#searchForm input[name=chkCartooletcId]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});	
			
			if(itemIds.length > 0) {
				if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
					$.ajax({
						url : '<c:url value="/lightpack/meetingroom/cartooletc/deleteCartooletc.do" />',     
						data : {
							cartooletcIds : itemIds.toString()
						},     
						type : "post",     
						success : function(result) {      
							getList();
						},
						error : function(event, request, settings){
							 alert("예약건이 있는 자원은 삭제할수 없습니다. 예약건부터 삭제하신후 작업하십시요.");
						}
					});
				}  
			} else {
				alert("<ikep4j:message pre="${preMessage}" key="selectDeleteItem" />");
			}
		});   
		
		$("#searchButton").click(function() {  
			getList();
		}); 
		
		$('#categoryId').change( function() { 
			getList();
		});
		
		$('#regionId').change( function() { 
			getList();
		});
		

		getList();

	});
})(jQuery);  
//-->	
</script>

<h1 class="none">자원관리</h1>		
	
<form id="searchForm" method="post" action="" onsubmit="return false">  
	
	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>자원관리</h2>
		
		<div class="listInfo">  
			
		</div>				
		<div class="tableSearch">
			<select name="regionId" id="regionId"  style="min-width:50px">
				<option value="">장소 <ikep4j:message pre="${preDetail}" key="all" /></option>
				<c:forEach var="region" items="${regionList}" varStatus="status">
					<option value="${region.regionId}">${region.regionName}</option>
				</c:forEach>
			</select> 
		
			<select name="categoryId" id="categoryId"  style="min-width:100px">
				<option value="">분류 <ikep4j:message pre="${preDetail}" key="all" /></option>
				<c:forEach var="category" items="${categoryList}" varStatus="status">
					<option value="${category.categoryId}">${category.categoryName}</option>
				</c:forEach>
			</select>
			


		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<!--blockListTable Start-->
	<div class="blockListTable" id="listDiv">


	</div>
	<!--//blockListTable End-->	

</form>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnNew" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='new' /></span></a></li>
			<li><a id="btnDelete" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
		
	