<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.kms.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 검색정보 선언 Start --%> 
<c:set var="isDebug" value="false" />
<c:set var="query" value="" />
<%-- 검색정보 선언 End --%>
	
<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/search/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/search/js/jquery-ui.min.js"></script>
<script type="text/javascript"> 
(function($) {

	$jq(document).ready(function() {	
		getPopkeyword();
	
		
		getPortletLeftView();
		getPortletRightView();
		getPortletCenterView();
		
		iKEP.iFrameContentResize(); 
	});
	
	
	showKmsFrameDialog = function(url, title, width, height, collback) {
		
		var mainFrameDialog = null;
		mainFrameDialog = new iKEP.Dialog({
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true,
			collback : collback
		});
	};
	
	getPortletLeftView = function() {
		<c:forEach var="layoutList" items="${kmsPortletLayoutList}" varStatus="status" >
		<c:if test="${layoutList.colIndex==1}">
			
			<c:if test="${!empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>		
			$jq("<div/>").load(url)			
	    	.error(function(event, request, settings) { alert("error"); })
	    	.appendTo("#portlet_left<c:out value='${layoutList.rowIndex}'/>");		

		</c:if>
		</c:forEach>
	};
	getPortletRightView = function() {
		
		<c:forEach var="layoutList" items="${kmsPortletLayoutList}"  varStatus="status">
		<c:if test="${layoutList.colIndex==2}">
			<c:if test="${!empty layoutList.boardId}">
			var url="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			$jq("<div/>").load(url)
    		.error(function(event, request, settings) { alert("error"); })
    		.appendTo("#portlet_right_<c:out value='${layoutList.rowIndex}'/>");
			
		</c:if>
		</c:forEach>	
	};
	
	getPortletCenterView = function() {			
			
		$.post("<c:url value='/collpack/kms/main/portlet/searchTab.do'/>", {"portletLayoutId" : "5"}) 
		.success(function(data) {
			$addFullHtml = $("#center").append
			("<div class='kmbox' style='padding-top:0px'>						"
				+"	<div id='blockCenter' style='padding-top:15px'>	                "
				+ data
				+"	</div>                                  "
				+"</div>                                    "
				+"<div class='kmboxline'></div>             "					
			);			
		})	
	};
	
	searchPerformanceBody = function(searchYear, searchMonth){
		$("#chart1").html("");
		
		var url="<c:url value='/collpack/kms/main/portlet/performanceSub.do'/>?searchYear="+searchYear+"&searchMonth="+searchMonth;
		$jq("<div/>").load(url)
		.error(function(event, request, settings) { alert("error"); })
		.appendTo("#chart1");

	};
	
	function getPopkeyword() {
		
		var str = "<div style='position:relative; z-index:999; padding-top:0px; text-align:center; !important '><img src='${pageContext.request.contextPath}/base/images/icon/ic_favorite.gif' alt='' />검색범위는 지식광장내의 자료를 대상으로 합니다.(네오넷 전체 검색은 <b>Search</b> 메뉴를 이용해 주십시오)";
		$("#popword").html(str);

		/*
		var target		= "popword";
		var range		= "W";
		var collection  = "_ALL_";
	    var datatype   = "xml";
		$.ajax({
		  type: "POST",
		  url: "<c:url value='/search/popword/popword.jsp'/>", 
		  dataType: "xml",
		  data: { "target" : target, "range" : range, "collection" : collection , "datatype" : datatype },
		  success: function(xml) {
			var str = "<div style='position:relative; z-index:999; padding-top:0px; text-align:left;'><span class='search_infoTxt'><img src='${pageContext.request.contextPath}/base/images/icon/ic_favorite.gif' alt='' /> 인기검색어 :  &nbsp;검색범위는 지식광장내의 자료를 대상으로 합니다.(네오넷 전체 검색은 Search 메뉴를 이용해 주십시오.)검색범위는 지식광장내의 자료를 대상으로 합니다.(네오넷 전체 검색은 Search 메뉴를 이용해 주십시오.)</span>";
			str += "<span>";
				
			$(xml).find("Query").each(function(){
	 			str += "<a href='#' onClick=\"javascript:doFowardPop('" + $(this).text() + "');\">" + $(this).text() + "</a>";
	 			str += "&nbsp;&nbsp;";
	 			if ($(this).attr("id") == 6) {
	 				return false;
	 			}
			});
			str += "</span><div>";

			$("#popword").html(str);
		  }
		});
		*/

	}
	
})(jQuery);



function doFoward(query) {
	
	document.search.query.value = document.search.query.value;
	document.search.submit();
}

function doFowardPop( query ) {
	
	document.search.query.value = query;
	document.search.submit();
}


function pressCheck() {   
	if (event.keyCode == 13) {
		return doFoward();
	}else{
		return false;
	}
}

</script>

<h1 class="none"><ikep4j:message pre="${prefix}" key="contents.pageTitle" /></h1>
<div class="clear"></div>
<!--blockSearch Start-->
   <div>
		<div class="corner_RoundBox03a_2" style="height: 65px;">
			<form name="search" id="search" action="${pageContext.request.contextPath}/search/kmsSearchResult.jsp" method="POST">
			<!--conSearch Start-->
				<div class="kmSearch_1">
					<%-- <div class="kmSearch_title_1" style="text-align: left;">
       					<img src="<c:url value='/base/images/common/ic_title_03_1.gif'/>" /><span style="font-size: 20px;vertical-align: top; font-weight: bold; ">지식광장 검색</span>
					</div> --%>
   					<div class="kmSearch_input_1" >
       					<input name="query" id="query" title="검색창" style="width:370px" type="text" value="" onKeypress="javascript:pressCheck(this.value);" autocomplete="off"/>
					</div>	
                  	<div class="clear" ></div>
					<div><a class="sel_btn" href="#" onClick="javascript:doFoward();" style="right:40px;" ></a></div>	
				</div>
				<div style="position:relative; margin:0 auto; top:50px;text-align: center;">- 검색범위는 지식광장내의 자료를 대상으로 합니다. (네오넷 전체 검색은 Search 메뉴를 이용해 주십시오)</div>				
              </form>
          	   
		</div>	
	</div>
	<div class="clear"></div>
	<div style="height:10px"></div>
	<c:if test="${!empty announceItem1.itemId}">
		<div class='kmbox' style="padding:5px 5px 5px 5px !important">									
			<div id='blockTop' >	    
			            
				<marquee direction="left" scrolldelay="300">
					<img src="<c:url value='/base/images/icon/ic_notice.gif'/>" /><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/announce/readAnnounceItemView.do?itemId=${announceItem1.itemId}'/>&docPopup=true', '${ikep4j:replaceQuot(announceItem1.title)}', 800, 600);" title="${announceItem1.title}">${announceItem1.title}</a>
					<br/>
				</marquee>
			
			</div>                                  
		</div>                                    
		<div class='kmboxline'></div>             	                                   
	</c:if>

<!--blockLeft Start-->
<div class="blockLeft" id="blockLeft">
	<c:forEach var="layoutList" items="${kmsPortletLayoutList}"   varStatus="status">	
	<c:if test="${layoutList.colIndex==1 && layoutList.isBoardPortlet==1}">	
		<div id="portlet_left<c:out value='${layoutList.rowIndex}'/>"></div>
	</c:if>
	</c:forEach>	
</div>

<!--//blockLeft End-->

<!--blockRight Start-->
<div id="blockRight" class="blockRight">
	<c:forEach var="layoutList" items="${kmsPortletLayoutList}"  varStatus="status">
	<c:if test="${layoutList.colIndex==2 && layoutList.isBoardPortlet==1}">	
		<div id="portlet_right_<c:out value='${layoutList.rowIndex}'/>"></div>
	</c:if>
	</c:forEach>
</div>

<div class="clear"></div>
<!--//blockRight End-->

<div id="center" style="height:240px"></div>


