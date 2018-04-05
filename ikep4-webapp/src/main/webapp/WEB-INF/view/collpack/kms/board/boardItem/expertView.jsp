<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>
<c:forEach var="statement1" items="${statementList1}" varStatus="status">
<c:set var="statementCnt1" value="${status.count}" />
</c:forEach>
<c:forEach var="statement2" items="${statementList2}" varStatus="status">
<c:set var="statementCnt2" value="${status.count}" />
</c:forEach>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">
function customerGradePopup(){
	 var url = "<c:url value='/support/customer/customerFinance/popupCustomerGrade.do'/>";
	 window.open(url,'customerGrade','width=800,height=490,scrollbars=1,toolbar=0'); 
	
}

function goDelete(){
	
	if(confirm("고객정보를 삭제하시겠습니까?")){
		location.href = "<c:url value='/support/customer/customerFinance/deleteFinance.do?id=${finance.id}'/>";
	}
}

(function($) {
	$(document).ready(function() {
		var tmpCategoryId = "";
		$("#applyBtn").click(function() {
			$("input[name=userId]:checked").each(function(index) { 
				tmpCategoryId = $(this).val();
			});	
			if(tmpCategoryId == ""){
				alert("전문가를 선택하세요.");
				return;
			}else{
				opener.setExpert($("#assessorId_"+tmpCategoryId).val(),$("#assessorName_"+tmpCategoryId).val(),$("#assessorTeam_"+tmpCategoryId).val(),$("#assessorJob_"+tmpCategoryId).val());
				window.close();
			}
	    });


	});
})(jQuery);
</script>

				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>전문가 조회</h2>		
					<div class="clear"></div>
				</div>
	 			<!--//tableTop End-->
				<div class="blockButton"> 
                <ul>           
				</ul>
				</div>
				<div class="clear"></div> 

                
                <div class="blockBlank_10px"></div>		
				
				
				<!--//subTitle_2 End-->	
				
				<div class="blockDetail">
						    <table style="font-size:13px">
						        <caption></caption>
						        <colgroup>
						            <col width="45%"/>
						            <col width="55%"/>
						        </colgroup>
						        <tbody>
						        	<c:forEach var="code" items="${expertList}">
					    	      		<tr>
					    	      			<th style="text-align:left;"><input type="radio" name="userId" value="${code.categoryId}"/>${code.categoryName}</th>
					    	      			<td style="text-align:left;">${code.userName} ${code.jobTitleName} ${code.teamName}
				                               	<input id="assessorId_${code.categoryId}" name="assessorId_${code.categoryId}" value="${code.userId}" type="hidden"/>
				                               	<input id="assessorName_${code.categoryId}" name="assessorName_${code.categoryId}" value="${code.userName}" type="hidden"/>
				                               	<input id="assessorTeam_${code.categoryId}" name="assessorName_${code.categoryId}" value="${code.teamName}" type="hidden"/>
				                               	<input id="assessorJob_${code.categoryId}" name="assessorName_${code.categoryId}" value="${code.jobTitleName}" type="hidden"/>
					    	      			</td>
					    	      		</tr>
				    	      		</c:forEach>
						        </tbody>
						    </table>
						</div>
                <div class="blockButton"> 
			<ul>
				<li><a class="button" href="#" id="applyBtn"><span>확인</span></a></li>
			</ul>
		</div>
				<!--//blockDetail End-->
				
						</div>
                
                        		