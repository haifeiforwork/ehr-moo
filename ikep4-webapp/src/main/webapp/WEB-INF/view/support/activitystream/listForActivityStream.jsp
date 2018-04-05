<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getList = function(isScrollSet) {
		
		$jq("#emptyDiv").hide();
		
		var msg = '<ikep4j:message pre='${preMessage}' key='search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/support/activitystream/getSubListForActivityStream.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(result) {  
				$jq("#listDiv").append(result);
				if(isScrollSet) setScroll();
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
		
		var msg = '<ikep4j:message pre='${preMessage}' key='search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/support/activitystream/getSubListForActivityStream.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#listDiv"}, 
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
		
		var countStr = '<ikep4j:message pre='${preList}' key='totalCount' /> ' + recordCount + '<ikep4j:message pre='${preList}' key='count' /> ';
		countStr = countStr + ' (<ikep4j:message pre='${preList}' key='nowCount' /> ' + currentCount + '<ikep4j:message pre='${preList}' key='count' /> '+")";
		$jq("#countDiv").html(countStr);
		
		setMoreDiv();
	}
	
	clickTab = function(obj) {
		
		$jq("#tabLiDiv").children().each(function(index, item) { 
			//$jq(item).removeClass();
		});
		
		$jq("#tabInputDiv").children().each(function(index, item) { 
			$jq(item).val("");
		})
		
		var index = $jq(obj).parent().index();
		
		//$jq(liObj).addClass("on");
		
		var liObj = $jq("#tabLiDiv").children().get(index);
		var inputObj = $jq("#tabInputDiv").children().get(index);
		
		if ($jq(inputObj).attr("id") == "isAll") {
			$jq("#tabInputDiv").children().each(function(index, item) { 
				$jq(item).val($jq(item).attr("id"));
			})
		}
		else {
			$jq(inputObj).val($jq(inputObj).attr("id"));
		}
		
		getListAndClear();
	}
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$("#divTab1").tabs();
		
		$jq("#listDiv").children().remove();
		getListAndClear();
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getListAndClear();
			}
		}); 
		
	});
	
})(jQuery);  

//-->
</script>

		
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preList}' key='main.title' /></h2>
				</div>
				<!--//pageTitle End-->
				
				<div class="clear"></div>	
				
				<form id="searchForm" method="post" action="" onsubmit="return false">  
				
				<spring:bind path="searchCondition.recordCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.currentCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.pageIndex">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>		
				
				<div id="tabInputDiv">
					<input type="hidden" id="isAll" name="isAll"  value="isAll" />
					
					<c:forEach var="group" items="${groupList}">
						<input type="hidden" id="${group.groupId}" name="groupList"  value="${group.groupId}" />
					</c:forEach>				
				
					<input type="hidden" id="isFollowing" name="isFollowing"  value="isFollowing" />
					<input type="hidden" id="isFavorite" name="isFavorite"  value="isFavorite" />
					<input type="hidden" id="isMy" name="isMy"  value="isMy" />
				</div>	
						
				<div id="divTab1" class="iKEP_tab">

						<ul id="tabLiDiv">
							
							<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='all' /></a></li>
							
							<c:forEach var="group" items="${groupList}">
								<li><a href="#tabs-1" onclick="clickTab(this)">${group.groupName}</a></li>
							</c:forEach>	
						
							<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='following' /></a></li>
							<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='favorite' /></a></li>
							<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='my' /></a></li>
							
						
						</ul>
						
							
					
					
				<div class="tab_con">
				<div id="tabs-1">
				<!--expert_bl Start-->
				<div class="expert_bl block mb10">

					
				
						<ul id="listDiv">
							
								<li></li>
						
						</ul>
						
						
						
				</div>
				<!--//expert_bl End-->	
				</div>
				</div>
				</div>
				
				<div class="clear"></div>
				
				
				<!--blockButton_3 Start-->
				<div id="moreDiv" class="blockButton_3" onclick="getMore()"> 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.more' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt="" /></span></a>				
				</div>
				
				<div id="emptyDiv" class="blockButton_3" > 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.empty' /> </span></a>				
				</div>
				<!--//blockButton_3 End-->	
				<!--//Contents End-->		
				
				</form>							
