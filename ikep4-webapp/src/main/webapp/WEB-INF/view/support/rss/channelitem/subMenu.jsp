<%@ page contentType="text/html; charset=UTF-8"%>
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

<script type="text/javascript">
//<!--
(function($) {
	
	getChannelSummary = function() {
		
		$jq.ajax({     
			url : '<c:url value="/support/rss/channel/getChannelSummary.do" />',     
			data : "",     
			type : "post",     
			success : function(result) {       
				$jq("#channelSummaryDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});   
		
	};	
	
	goEdit = function() {
	   
		if($jq("input[name=catCount]").val() == 1)
		{
			var url = '<c:url value="/support/rss/channel/getList.do"/>';
			var title = '<ikep4j:message pre='ui.support.rss.channel.list' key='main.title' />'
			var width = 780;
			var height = 607;
	
			iKEP.showDialog({ 
				title: title,
				url: url,
				width:width,
				height:height,
				modal: true,
				scroll: "no",
				beforeClose:function(result) {
					//$jq("#RSStitle").click();
					getChannelSummary();
                }
			});
		}
		else
		{
		     alert("<ikep4j:message pre='ui.support.rss.message' key='NotEmptyCategory' />")
		}
		
	};
	
	$(document).ready(function() {	
		// left menu setting
		iKEP.setLeftMenu();		
		getChannelSummary();
		
	});
})(jQuery);
//-->
</script>

<!--leftMenu Start-->
<h1 class="none">RSS</h1>	
<h2 class="han"><a id="RSStitle" href="<c:url value='/support/rss/channelitem/getList.do'/>"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_home_rss.gif'/>"/></span>
</a><a class="none" href="#a" onclick="javascript:goEdit()"><img src="<c:url value="/base/images/icon/ic_edit.gif"/>" alt="edit" /></a></h2>


<!--leftMenu Start-->
<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<div class="left_fixed" >
	<ul>
	<li id="channelSummaryDiv" class="liFirst opened">
	</li>	
     <li class="opened"><a href="#a" title="<ikep4j:message pre='${preList}' key='administration'/>"><ikep4j:message pre='${preList}' key='administration'/></a>
         <ul style="padding-top: 2px; padding-bottom: 2px;">
            <li class="no_child" ><a href="#a" onclick="javascript:getCategoryList('')" title="<ikep4j:message pre='${preList}' key='itemCategoryAdmin'/>"><ikep4j:message pre='${preList}' key='itemCategoryAdmin'/></a></li>
         </ul>
         <ul style="padding-top: 2px; padding-bottom: 2px;">
            <li class="no_child" ><a href="#a" onclick="javascript:goEdit()" title="<ikep4j:message pre='${preList}' key='itemChannelAdmin'/>"><ikep4j:message pre='${preList}' key='itemChannelAdmin'/></a></li>
         </ul>
    </li>   
	</ul>	
</div>
<input type='hidden' id='catCount' name='catCount' value=''/>
<!--//leftMenu End-->
