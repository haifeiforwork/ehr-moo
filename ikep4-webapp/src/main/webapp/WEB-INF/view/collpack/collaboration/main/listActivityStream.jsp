<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main.activitystream" />
<%-- 메시지 관련 Prefix 선언 End --%>


<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>

<script type="text/javascript" language="javascript">

(function($) {

	getList = function(isScrollSet) {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		
		var msg = '<ikep4j:message pre='${prefix}' key='list.search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/main/listActivityStreamByWorkspace.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				$jq("#listDiv").append(result);
				var recordCount = eval($jq("#recordCount").val());
				iKEP.iFrameContentResize();  
			}, 
			error : function(event, request, settings) { alert("error"); }
		});  
		
		afterSearch(msg);
	};
	
	getListAndClear = function() {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		$jq("#pageIndex").val(1);
		
		$jq("#currentCount").val("0");
		
		var msg = '<ikep4j:message pre='${prefix}' key='list.search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/main/listActivityStreamByWorkspace.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				
				$jq("#listDiv").children().remove();
				$jq("#listDiv").append(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
		afterSearch(msg);
	};
	
	setCount = function() {
		
		var recordCount = eval($jq("#recordCount").val());
		var currentCount = eval($jq("#currentCount").val());
		
		var countStr = '<ikep4j:message pre='${prefix}' key='list.totalCount' /> ' + recordCount + '<ikep4j:message pre='${prefix}' key='list.count' /> ';
		countStr = countStr + ' (<ikep4j:message pre='${prefix}' key='list.nowCount' /> ' + currentCount + '<ikep4j:message pre='${prefix}' key='list.count' /> '+")";
		$jq("#countDiv").html(countStr);
		
		setMoreDiv();
	}
	
	var pageIndex=2;

	getMore = function() {
		
		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/main/listActivityStreamByWorkspace.do" />',     
			data :  {pageIndex:pageIndex,groupList:${groupList}},     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(data) {  
				
				$jq("#listDiv").append(data);
				pageIndex=pageIndex+1;

				iKEP.iFrameContentResize();
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getListAndClear();
			}
		}); 

	});
	
})(jQuery);  


function showDatail2(url, title) {
	
	url = iKEP.getContextRoot() + url;
	//iKEP.popupOpen(url, {width:800, height:600});
	
	var width = $jq(window).width()*0.8;
	var height = $jq(window).height()*0.8;
	
	parent.parent.showMainFrameDialog(url, title, width, height);
};

</script>

	<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='list.main.title' /></h2>
	</div>
	</div>	

	<form id="searchForm" method="post" action="" onsubmit="return false">  
	
	<div id="tabInputDiv">
		<input type="hidden" id="${group.groupId}" name="groupList"  value="${groupList}" />
		<input type="hidden" id="pageIndex" name="pageIndex"  value="${pageIndex+1}" />	
	</div>
			
	<!--Contents Start-->
	<!--blockListTable Start-->		
	<div class="expert_bl block mb10">	
		<div id="listDiv"></div>	
	</div>
	<!--//blockListTable End-->	
	
	<!--blockButton_3 Start-->
	<div id="moreDiv" class="blockButton_3" onclick="getMore()"> 
		<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='list.more' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt="" /></span></a>				
	</div>
	
	<div id="emptyDiv" class="blockButton_3" > 
		<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='list.empty' /> </span></a>				
	</div>
	<!--//blockButton_3 End-->	
	<!--//Contents End-->		
	
	</form>							
