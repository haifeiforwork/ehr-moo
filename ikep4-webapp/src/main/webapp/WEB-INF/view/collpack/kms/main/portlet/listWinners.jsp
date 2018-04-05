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
	});
	
	<c:choose>
		<c:when test="${displayMode eq '0'}">
			$jq('.quarterWinner').hide();
			$jq('.yearWinner').hide();
			$jq('.monthlyTab').css("background-color","#EAEAEA");
		</c:when>
		<c:when test="${displayMode eq '1'}">
			$jq('.monthlyWinner').hide();
			$jq('.yearWinner').hide();
			$jq('.quarterTab').css("background-color","#EAEAEA");
		</c:when>
		<c:otherwise>
			$jq('.monthlyWinner').hide();
			$jq('.quarterWinner').hide();
			$jq('.yearTab').css("background-color","#EAEAEA");
		</c:otherwise>
	</c:choose>
	
	
	selectTab = function(tab) {
		if(tab == "0"){ 
			$jq('.monthlyWinner').show();
			$jq('.quarterWinner').hide();
			$jq('.yearWinner').hide();
			$jq('.monthlyTab').css("background-color","#EAEAEA");
			$jq('.quarterTab').css("background-color","#FFFFFF");
			$jq('.yearTab').css("background-color","#FFFFFF");
		}else if(tab == "1"){
			$jq('.quarterWinner').show();
			$jq('.monthlyWinner').hide();
			$jq('.yearWinner').hide();
			$jq('.quarterTab').css("background-color","#EAEAEA");
			$jq('.monthlyTab').css("background-color","#FFFFFF");
			$jq('.yearTab').css("background-color","#FFFFFF");
		}else{
			$jq('.yearWinner').show();
			$jq('.monthlyWinner').hide();
			$jq('.quarterWinner').hide();
			$jq('.yearTab').css("background-color","#EAEAEA");
			$jq('.monthlyTab').css("background-color","#FFFFFF");
			$jq('.quarterTab').css("background-color","#FFFFFF");
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
		<span class="monthlyWinner">(${monthlyYear}년 ${monthly}월)</span>
		<span class="quarterWinner">(${quarterYear}년 ${quarter}분기)</span>
		<span class="yearWinner">(${year}년)</span>
		</h3>		
	</div>
	<table>
		<tr>
			<td onclick="selectTab(0);" class="monthlyTab" width="15%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">월간</span></td>
			<td onclick="selectTab(1);" class="quarterTab" width="15%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">분기</span></td>
			<td onclick="selectTab(2);" class="yearTab" width="15%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">연간</span></td>
			<td width="*" style="border-bottom:#d4d4d4 1px solid;"></td>
		</tr>
	</table>
	<br/>
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty winnerMonthlyItem}">
			<c:forEach var="adminWinner" items="${winnerMonthlyItem}"  varStatus="status">
			<tr class="monthlyWinner">
				<th class="ellipsis" width="40%" scope="row">					
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${adminWinner.winnerId}', 'bottom');iKEP.iFrameContentResize(); return false;">
					<img src="<c:url value='/base/images/icon/ic_no${adminWinner.sort}.gif'/>" alt="" /> ${adminWinner.userName}</a>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> ${adminWinner.teamName} 
				</th>
				<td class="textLeft" width="24%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.conversionMark" /> ${adminWinner.conversionMark}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.regCnt" /> ${adminWinner.regCnt}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.mark" /> ${adminWinner.mark}					
				</td>				
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty winnerMonthlyItem}">
				<tr class="monthlyWinner">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>	
			<c:if test="${!empty winnerQuarterItem}">
			<c:forEach var="adminWinner" items="${winnerQuarterItem}"  varStatus="status">
			<tr class="quarterWinner">
				<th class="ellipsis" width="40%" scope="row">					
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${adminWinner.winnerId}', 'bottom');iKEP.iFrameContentResize(); return false;">
					<img src="<c:url value='/base/images/icon/ic_no${adminWinner.sort}.gif'/>" alt="" /> ${adminWinner.userName}</a>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> ${adminWinner.teamName} 
				</th>
				<td class="textLeft" width="24%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.conversionMark" /> ${adminWinner.conversionMark}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.regCnt" /> ${adminWinner.regCnt}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.mark" /> ${adminWinner.mark}					
				</td>				
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty winnerQuarterItem}">
				<tr class="quarterWinner">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>	
			<c:if test="${!empty winnerYearItem}">
			<c:forEach var="adminWinner" items="${winnerYearItem}"  varStatus="status">
			<tr class="yearWinner">
				<th class="ellipsis" width="40%" scope="row">					
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${adminWinner.winnerId}', 'bottom');iKEP.iFrameContentResize(); return false;">
					<img src="<c:url value='/base/images/icon/ic_no${adminWinner.sort}.gif'/>" alt="" /> ${adminWinner.userName}</a>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> ${adminWinner.teamName} 
				</th>
				<td class="textLeft" width="24%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.conversionMark" /> ${adminWinner.conversionMark}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.regCnt" /> ${adminWinner.regCnt}					
				</td>
				<td class="textLeft" width="18%">
					<ikep4j:message key="message.collpack.kms.admin.winner.list.mark" /> ${adminWinner.mark}					
				</td>				
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty winnerYearItem}">
				<tr class="yearWinner">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>																																			
		</tbody>
	</table>	
</div>	
</div>				
<div class="kmboxline"></div>