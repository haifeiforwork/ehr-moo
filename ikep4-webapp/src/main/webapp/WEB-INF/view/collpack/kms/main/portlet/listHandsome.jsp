<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.iFrameContentResize();  
		
		iKEP.showGallery($("a.image-gallery"));
	});
	
	<c:choose>
		<c:when test="${displayMode eq '0'}">
			$jq('.teamWinner').hide();
			$jq('.awardWinner').hide();
			$jq('.peopleTab').css("background-color","#EAEAEA");
		</c:when>
		<c:when test="${displayMode eq '1'}">
			$jq('.peopleWinner').hide();
			$jq('.awardWinner').hide();
			$jq('.teamTab').css("background-color","#EAEAEA");
		</c:when>
		<c:otherwise>
			$jq('.peopleWinner').hide();
			$jq('.teamWinner').hide();
			$jq('.awardTab').css("background-color","#EAEAEA");
		</c:otherwise>
	</c:choose>
	
	
	selectTab = function(tab) {
		if(tab == "0"){ 
			$jq('.peopleWinner').show();
			$jq('.teamWinner').hide();
			$jq('.awardWinner').hide();
			$jq('.peopleTab').css("background-color","#EAEAEA");
			$jq('.teamTab').css("background-color","#FFFFFF");
			$jq('.awardTab').css("background-color","#FFFFFF");
		}else if(tab == "1"){
			$jq('.teamWinner').show();
			$jq('.peopleWinner').hide();
			$jq('.awardWinner').hide();
			$jq('.teamTab').css("background-color","#EAEAEA");
			$jq('.peopleTab').css("background-color","#FFFFFF");
			$jq('.awardTab').css("background-color","#FFFFFF");
		}else{
			$jq('.awardWinner').show();
			$jq('.peopleWinner').hide();
			$jq('.teamWinner').hide();
			$jq('.awardTab').css("background-color","#EAEAEA");
			$jq('.peopleTab').css("background-color","#FFFFFF");
			$jq('.teamTab').css("background-color","#FFFFFF");
		}
	}
	
})(jQuery);
</script>
<div class="kmbox">
	<div class="tableList_3 mb15">
	<!--subTitle_1 Start-->
	<div class="subTitle_1a">
		<h3>
		${kmsPortletLayout.kmsPortlet.portletName}
		<span>
			<c:choose>
				<c:when test="${displayMode eq '0'}">
					(${monthlyYear}년 ${monthly}월)
				</c:when>
				<c:when test="${displayMode eq '1'}">
					(${quarterYear}년 ${quarter}분기)
				</c:when>
				<c:otherwise>
					(${year}년)
				</c:otherwise>
			</c:choose>
		</span>
		</h3>		
	</div>
	<table>
		<tr>
			<td onclick="selectTab(0);" class="peopleTab" width="38%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">Handsome한 무림인</span></td>
			<td onclick="selectTab(1);" class="teamTab" width="32%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">Handsome한 부서</span></td>
			<td onclick="selectTab(2);" class="awardTab" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">두근두근 포상품</span></td>
			<td width="*" style="border-bottom:#d4d4d4 1px solid;"></td>
		</tr>
	</table>
	<br/>
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty winnerPeopleItem}">
			<tr>
			<c:forEach var="adminWinner" items="${winnerPeopleItem}"  varStatus="status">
				<td class="peopleWinner">
					<table>
						<tr><td>${adminWinner.reason}</td></tr>
						<tr><td>
						<img src="<c:url value='${adminWinner.imageFileId}' />" alt="image" style="width:70px;height:90px;" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'"/>
						</td></tr>
						<tr><td>${adminWinner.userName}&nbsp;${adminWinner.teamName}</td></tr>
					</table>
				</td>
			</c:forEach>
			</tr>
			</c:if>
			<c:if test="${empty winnerPeopleItem}">
				<tr class="peopleWinner">
					<td class="textCenter">등록된 사용자가 없습니다</td>
				</tr>			
			</c:if>	
			<c:if test="${!empty winnerTeamItem}">
			<tr>
			<c:forEach var="adminWinner" items="${winnerTeamItem}"  varStatus="status">
			<td class="teamWinner">
					<table>
						<tr><td>${adminWinner.reason}</td></tr>
						<tr><td>
						<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${adminWinner.imageFileId}">
						<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${adminWinner.imageFileId}" width="100" height="107"/>
						</a>
						</td></tr>
						<tr><td>${adminWinner.teamName}</td></tr>
					</table>
				</td>
			</c:forEach>
			</tr>
			</c:if>
			<c:if test="${empty winnerTeamItem}">
				<tr class="teamWinner">
					<td class="textCenter">등록된 부서가 없습니다</td>
				</tr>			
			</c:if>	
			<c:if test="${!empty winnerAwardItem}">
			<tr>
			<c:forEach var="adminWinner" items="${winnerAwardItem}"  varStatus="status">
			<td class="awardWinner" >
					<table style="width:120;">
						<tr><td>
						<c:if test="${!empty adminWinner.winnerName}">
						<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${adminWinner.imageFileId}">
						<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${adminWinner.imageFileId}" width="100" height="107"/>
						</a>
						</td></tr>
						<tr><td>${adminWinner.winnerName}
						</c:if>
						</td></tr>
					</table>
				</td>
			</c:forEach>
			</tr>
			</c:if>
			<c:if test="${empty winnerAwardItem}">
				<tr class="awardWinner">
					<td class="textCenter">등록된 포상품이 없습니다</td>
				</tr>			
			</c:if>																																			
		</tbody>
	</table>	
</div>	
</div>				
<div class="kmboxline"></div>