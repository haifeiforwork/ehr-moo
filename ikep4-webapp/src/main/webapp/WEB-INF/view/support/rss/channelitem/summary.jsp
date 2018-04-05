<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channelitem.header" /> 
<c:set var="preList"    value="ui.support.rss.channelitem.list" />
<c:set var="preDetail"  value="ui.support.rss.channelitem.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getChannelItemSummary.do" />',     
			data : "",     
			type : "post",     
			success : function(result) {         
				$jq("#listDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});     
		
	};
	
	goEdit = function() {
		
		var itemUrl = '<c:url value="/support/rss/channel/getList.do"/>';
		openPopupWindow(itemUrl, 1024, 600, "");
		
	};
	
	goMore = function() {
		
		document.location.href = '<c:url value="/support/rss/channelitem/getList.do"/>';
		
	};
	
	goRefresh = function() {
		
		$jq("#startRefresh").hide();
		$jq("#stopRefresh").show();
		
		$jq.ajax({     
			url : '<c:url value="/support/rss/channel/readChannel.do"/>',     
			data : "",     
			type : "post",     
			success : function(result) {         
				$jq("#startRefresh").show();
				$jq("#stopRefresh").hide();
				getList();
			},
			error : function(event, request, settings) { alert("error"); }
		});     
		
	};
	
	goStopRefresh = function() {
		
		$jq.ajax({     
			url : '<c:url value="/support/rss/channel/stopChannel.do"/>',     
			data : "",     
			type : "post",     
			success : function(result) {     
				$jq("#startRefresh").show();
				$jq("#stopRefresh").hide();
				getList();
			},
			error : function(event, request, settings) { alert("error"); }
		});   
		
	};
	
	getView = function(itemUrl) {
		
		openPopupWindow(itemUrl, 1024, 768, "");
		
	};
	
	openPopupWindow = function(url, winWidth, winHeight, winName) {
		
		var windowName = winName || "_blank";
		var width = winWidth || 200;
		var height = winHeight || 200;

		var windowName = window.open(
			url,
			windowName,
			"width=" + width + ", height=" + height + ", toolbar=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no"
		);
		
	};
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#stopRefresh").hide();
		getList();
		
	});
	
})(jQuery);  

//-->	
</script>


			<!--mainContents Start-->
			<div id="mainContents" >
			
				<!--pageTitle Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1><ikep4j:message pre='${preList}' key='main.title' /></h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//pageTitle End-->
				
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				
					
						
						<div id="listDiv"></div>
						
				
						
				</div>
				<!--//blockListTable End-->	
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="javascript:goEdit()"><span><ikep4j:message pre='${preButton}' key='edit' /></span></a></li>
						<li><a class="button" href="javascript:goMore()"><span><ikep4j:message pre='${preButton}' key='more' /></span></a></li>
						<li id="startRefresh"><a class="button" href="javascript:goRefresh()"><span><ikep4j:message pre='${preButton}' key='refresh' /></span></a></li>
						<li id="stopRefresh"><a class="button" href="javascript:goStopRefresh()"><span><ikep4j:message pre='${preButton}' key='stoprefresh' /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
	
								
				
			</div>
			<!--//mainContents End-->
			
		


	