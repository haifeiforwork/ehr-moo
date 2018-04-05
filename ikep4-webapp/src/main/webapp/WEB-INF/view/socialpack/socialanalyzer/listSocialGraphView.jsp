<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.socialpack.socialanalyzer.listSocialGraphView</c:set>
<c:set var="messagePrefix">ui.socialpack.socialanalyzer.message</c:set>
<c:set var="buttonPrefix">ui.socialpack.socialanalyzer.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
var xmlPath = "<c:url value='/socialpack/socialanalyzer/tab${tabIndex}XML.do'/>";
var firstID = "${searchId}";
var rootPath = "<c:url value='/base/swf/socialmap/'/>";
var groupName = "Direct Network||Communication Network||Fellowship Network||Expertise Network";
function getXML_PATH() {return xmlPath;  }
function getFIRST_ID() {return firstID;  }
function getGROUP()    {return groupName;}
function getROOT_PATH(){return rootPath; }

(function($) {
	$jq(document).ready(function(){		
		//사용자 클릭
		$jq('#userName').keypress( function(event) {
			iKEP.searchUser(event, "N", setUser);
		});
		//조회 버튼 클릭
		$jq('#searchButton').click( function() { 
			$jq('#userName').trigger("keypress");
		});

		//탭 클릭
		$("#divTab1").tabs({     
			selected : ${tabIndex},
			select : function(event, ui) {
				location.href = "<c:url value='/socialpack/socialanalyzer/listSocialGraphView.do?searchId=${searchId}&tabIndex='/>" + ui.index;
			}
		});  		

	});	

	//사용자  등록
	setUser = function(data) {
		if(data == "") {
			alert("<ikep4j:message pre='${messagePrefix}' key='user.nothing'/>");
			$jq('#userName').val("");
			return;
		}
		
		$jq(data).each(function(index) {
			if(index == 0) {
				$jq('#userName').val(this.userName);
				$jq('#searchId').val(this.id);
				$jq('#searchText').val(this.userName);
				
				$jq("#form").submit();
			}
		});	
	}

})(jQuery);
</script>

				<h1 class="none"></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
				</div>
				<!--//subTitle_2 End-->
				
				<!--tab Start-->		
				<div id="divTab1" class="iKEP_tab">
					<ul>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab1.title"/></a></li>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab2.title"/></a></li>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab3.title"/></a></li>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab4.title"/></a></li>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab5.title"/></a></li>
						<li><a href="#divPerson"><ikep4j:message pre="${prefix}" key="tab6.title"/></a></li>	
					</ul>    
					<div class="clear"></div>
					
					<form id="form" action="<c:url value="/socialpack/socialanalyzer/listSocialGraphView.do"/>" method="post">										
						<input type="hidden" id="tabIndex" name="tabIndex" value="${tabIndex}"/>											
						<input type="hidden" id="searchId" name="searchId" value="${searchId}"/>											
						<input type="hidden" id="searchText" name="searchText" value="${searchText}"/>	
					</form>
					
					<div class="mt10" style="position:relative;">
						<div style="position:absolute; top:3px; left:0;">
							<c:choose>
							    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${userInfo.userName} ${userInfo.jobTitleName} ${userInfo.teamName}</c:when>
							    <c:otherwise>${userInfo.userEnglishName} ${userInfo.jobTitleEnglishName} ${userInfo.teamEnglishName}</c:otherwise>
						    </c:choose>
							</div>
						<div class="tableSearch">
							<ikep4j:message pre='${prefix}' key='search.searchType'/>					
							<input id="userName" name="userName" value="" type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='search.searchType'/>" size="20" />
							<a href="#a" id="searchButton" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>
						</div>
						<div class="clear"></div>
					</div>
					
					
					<!--tab Start-->	
					<div id="divPerson">
						<div class="corner_RoundBox02">					
							<div class="Analyzer_flash textCenter" id="socialmapSwf">							
								<c:choose>
								    <c:when test="${tabIndex == 5}"><script>iKEP.createSWFEmbedObject("#socialmapSwf","/ikep4-webapp/base/swf/socialmap/GNBGroup.swf", 712, 498);</script></c:when>
								    <c:otherwise><script>iKEP.createSWFEmbedObject("#socialmapSwf","<c:url value='/base/swf/socialmap/GNB.swf'/>", 712, 498);</script></c:otherwise>
							    </c:choose>
							</div>
							<div class="clear"></div>
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>
						</div>
						
						<div class="blockBlank_20px"></div>
						
						<c:if test="${tabIndex != 5}"> 
						<!--blockListTable Start-->
						<div class="blockListTable">
							<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="5%"><ikep4j:message pre='${prefix}' key='table.column1'/></th>
										<th scope="col" width="20%"><ikep4j:message pre='${prefix}' key='table.column2'/></th>
										<th scope="col" width="*"><ikep4j:message pre='${prefix}' key='table.column3'/></th>
										<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${socialityList}" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td><c:choose>
											    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.userName} ${item.jobTitleName}</c:when>
											    <c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise>
										    </c:choose>
										</td>
										<td><c:choose>
											    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when>
											    <c:otherwise>${item.teamEnglishName}</c:otherwise>
										    </c:choose>
										</td>
										<td>${item.point}</td>
									</tr>
									</c:forEach>																																																																																														
								</tbody>
							</table>			
						</div>
						<!--//blockListTable End-->	
						</c:if>
					</div>					
					<!--//tab End-->

					
				</div>		
				<!--//tab End-->