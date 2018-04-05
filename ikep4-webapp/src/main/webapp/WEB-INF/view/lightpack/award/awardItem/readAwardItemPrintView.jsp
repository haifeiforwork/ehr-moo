<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.readAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardItem.readAwardView.detailAwardItem" />
<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>  
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>
<script type="text/javascript">
<!--  

window.onload = function()
{
	printWindow();
}

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 4.23 //왼쪽 여백
	factory.printing.topMargin = 4.23 //위쪽 여백
	factory.printing.rightMargin = 4.23 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
	factory.printing.Preview();
	//setTimeout("window.close()", 100);
	setTimeout("javascript:iKEP.closePop()", 100);

}		
		
(function($){	 
	
	
	$(document).ready(function() { 
		
	});    

})(jQuery); 


//-->
</script> 
<div class="blockTableReadWrap">
		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="awardItem.title">
				<p id="awardItemTitle"> 
				<c:if test="${awardItem.itemDelete eq 1}"><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span></c:if>
				<c:if test="${award.wordHead == 1}"><c:if test="${!empty awardItem.wordName}">[${awardItem.wordName}]</c:if></c:if>${awardItem.awardKindTxt} | [${awardItem.companyCodeTxt}] ${awardItem.awardTitle} | 등급 ${awardItem.awardGrade}		
				</p> 	
				</spring:bind> 
				<div class="summaryViewInfo">
					<c:choose>
						<c:when test="${award.anonymous eq 1}">
							<span class="summaryViewInfo_name"><!--<ikep4j:message pre='${preDetail}' key='anonymous'/>-->${awardItem.displayName}
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							</span>
						</c:when>  
						<c:otherwise>
							<span class="summaryViewInfo_name">
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userName}<!-- ${awardItem.user.jobTitleName}  --></a>
										</c:if>
										<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:when>
									<c:otherwise> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userEnglishName}<!-- ${awardItem.user.jobTitleEnglishName}--></a>
										</c:if>
										<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:otherwise>
								</c:choose>
							</span>
						</c:otherwise> 
					</c:choose>
					<span class="blockCommentInfo_name">
						<ikep4j:timezone date="${awardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				    </span>
					<spring:bind path="awardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					</spring:bind>  
					<span>
			</div> 
			<div class="blockDetail">
        	<table summary="">
            	<caption></caption>
                    <colgroup>
                    	<col width="10%"/>
                        <col width="23%"/>
                        <col width="10%"/>
                        <col width="23%"/>
                        <col width="10%"/>
                        <col width="24%"/>
                    </colgroup>
                    <tbody>
                        <tr>
                        	<th class="textRight">날짜</th>
                        	<td>${awardItem.awardDate}</td>
                            <th class="textRight">발행처(대상)</th>
                            <td>${awardItem.publisher}</td>
                            <th class="textRight">자료</th>
                            <td id="awardMaterialTd"></td>
                        </tr>
                        <tr>
                        	<th class="textRight">보관위치</th>
                        	<td>${awardItem.storageLocTxt} ${awardItem.storageLocDetail}</td>
                            <th class="textRight">주무부서</th>
                            <td>${awardItem.resDeptId}</td>
                            <th class="textRight">내역</th>
                            <td>${awardItem.awardTxt}</td>
                        </tr>
                        </tbody>
                        </table>
                        </div>
			
			<DIV class="none" id="awardItemContent2"><ikep4j:extractTextBody text="${awardItem.contents}"/></div>
			<div class="blockTableRead_c" id="awardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="awardItem.contents">
						<p >${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
		        <div class="clear"></div>  
			</div>  
			<c:if test="${awardItem.attachFileCount > 0}">
					<div id="fileDownload"></div>
			</c:if> 
			<!--//blockListTable End-->	  
			<!--tableBottom Start--> 
			<div class="tableBottom" style="margin-top: 6px;"> 
				<!--//blockButton End--> 
			</div> 
			<!--//tableBottom End--> 
		</div>   	
	</div> 

