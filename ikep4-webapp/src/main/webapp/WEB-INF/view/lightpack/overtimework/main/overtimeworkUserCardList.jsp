<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
		    //- 버튼영역 실행  
		    var today = iKEP.getCurTime();
			$jq("#saveButton").click(function() {   
				var tempStartDate = "";
				var tempEndDate = "";
				var tempYear = "";
				var tempMonth = "";
				var tempStatus = "";
				var startDate = "";
				var endDate = "";
				var year = "";
				var month = "";
				var status = "";
				for(i=1;i<13;i++){
					tempStartDate = $("#startDate"+i).val(); 
					if(tempStartDate == ""){
						tempStartDate = "N";
					}
					tempEndDate = $("#endDate"+i).val();  
					if(tempEndDate == ""){
						tempEndDate = "N";
					}
					tempStatus = $jq("input[name=statusCheck"+i+"]:checked").attr("value");
					tempMonth = i;
					if(i == 1){
						startDate = tempStartDate;
						endDate = tempEndDate;
						status = tempStatus;
						month = tempMonth;
					}else{
						startDate = startDate+"|"+tempStartDate;
						endDate = endDate+"|"+tempEndDate;
						status = status+"|"+tempStatus;
						month = month+"|"+tempMonth;
					}
				}
				$jq('input[name=startDate]').val(startDate); 
				$jq('input[name=endDate]').val(endDate);  
				$jq('input[name=status]').val(status);
				$jq('input[name=month]').val(month);
	    		$.ajax({
					url : "<c:url value='/lightpack/overtimework/savePeriod.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
			
			checkRadioBox = function(tmpMth){
				for(i=1;i<13;i++){
					$jq('input:radio[name=statusCheck'+i+']:input[value=N]').attr("checked", true);
				}
				$jq('input:radio[name=statusCheck'+tmpMth+']:input[value=Y]').attr("checked", true);
			};
			
			
			$("#itemForm select[name=workPlaceName]").change(function() { 
				var workPlaceName = $("#itemForm select[name=workPlaceName]").val();
				if($jq("input[name=cardIdCheckBox]").is(":checked")){
					$jq("#cardIdCheck").val("Y");
				}
				if(workPlaceName == ""){
					$("#teamCode").empty();
					var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
					$("#teamCode").append(teamCode);
				}else{
					$jq("#itemForm").attr("action", "<c:url value='/lightpack/overtimework/OvertimeworkUserCardList.do' />");
					$jq("#itemForm")[0].submit();
					return false;
				}
			}); 
			
			$("#itemForm select[name=teamCode]").change(function() { 
				$("#teamName").val($("#teamCode option:selected").text());
				if($jq("input[name=cardIdCheckBox]").is(":checked")){
					$jq("#cardIdCheck").val("Y");
				}
				$jq("#itemForm").attr("action", "<c:url value='/lightpack/overtimework/OvertimeworkUserCardList.do' />");
				$jq("#itemForm")[0].submit();
				return false;
			});
		    
			$jq("#searchBoardItemButton").click(function() {  
				
				getList();
			});
			
			$jq("#excelButton").click(function() { 
				if(${searchResult.emptyRecord}){
					alert("검색 결과가 없습니다.");
					return false;
				}
				$jq("#itemForm").attr("action","<c:url value='/lightpack/overtimework/downloadExcelOvertimeworkUserCardList.do'/>");
				$jq("#itemForm")[0].submit();
			return false; 
			});
        });
        
        
		
	})(jQuery);  

	function getList() {
	if($jq("input[name=cardIdCheckBox]").is(":checked")){
		$jq("#cardIdCheck").val("Y");
	}
	$jq("#itemForm").attr("action", "<c:url value='/lightpack/overtimework/OvertimeworkUserCardList.do' />");
	$jq("#itemForm")[0].submit();
	}
//-->
</script>

<!--popup Start-->
<form id="itemForm" name="itemForm" method="post" action="" onsubmit="getList(); return false;">	
<input type="hidden" id="cardIdCheck" name="cardIdCheck"/>
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>사용자 카드관리</h2>
	<spring:bind path="searchCondition.workPlaceName">                             			
      	<select title="${status.expression}" name="${status.expression}">
           <option value="">전체</option>	                                        
           <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
           	<option value="${workPlace}" <c:if test="${searchCondition.workPlaceName eq workPlace}">selected="selected"</c:if>>${workPlace}</option>
           </c:forEach>
       </select>	                                    
   </spring:bind>
   <spring:bind path="searchCondition.teamCode">
       <select title="teamCode" name="${status.expression}" id="${status.expression}">
       	<option value="">전체</option>
           <c:forEach var="teamInfo" items="${teamList}">
           	<option value="${teamInfo.teamCode}" <c:if test="${searchCondition.teamCode eq teamInfo.teamCode}">selected="selected"</c:if>>${teamInfo.teamName}</option>
           </c:forEach>
       </select>
	</spring:bind>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<spring:bind path="searchCondition.cardIdCheck">  
	<input name="${status.expression}Box" class="checkbox" title="checkbox" type="checkbox" value="" style="vertical-align:middle;"  <c:if test="${'Y' eq status.value}">checked="checked"</c:if> />CARD ID 미등록
	</spring:bind>
	<div class="tableSearch" > 
		<spring:bind path="searchCondition.searchColumn">  
            <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
                <option value="userName" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >성명</option>
                <option value="userId" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >ID</option>
                <option value="email" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >Email</option>
                <option value="cardId" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >CARD ID</option>
            </select>   
        </spring:bind>      
        <spring:bind path="searchCondition.searchWord">                     
            <input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
        </spring:bind>
	</div>
	<div class="clear"></div>
</div>
<div class="listInfo"> 
						<div class="totalNum">
							${searchResult.pageIndex}/ ${searchResult.pageCount} 페이지 
							( 전체<span> ${searchResult.recordCount}</span>)
						</div>
					</div>
	<!--popup_contents Start-->
	<div class="nblockButton"> 
							<a id='searchBoardItemButton' class='button_img01' href="#a"><span>조회</span></a>
							<a id='excelButton' class='button_img01' href="#a"><span><img src="<c:url value='/base/images/icon/ico_bullet_excel.png'/>" /> 엑셀다운로드</span></a>
					</div> 
	
   		<!--blockDetail Start-->
		<div class="blockListTable">
			<table>
				<caption></caption>
				<colgroup>
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="20%" />
					<col width="10%" />
					<col width="10%" />
					<col width="15%" />
					<col width="15%" />
				</colgroup>
				<tbody>   
    			    <tr>
    			    	<th style="text-align:center;border-left:1px solid #e0e0e0;">아이디</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">이름</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">직급/직책</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">Email</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">소속</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">사업장</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">부서</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;border-right:1px solid #e0e0e0;">CARD ID</th>
    				</tr>
    				<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="8" class="emptyRecord">
										검색된 사용자가 없습니다
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="card" items="${searchResult.entity}" varStatus="status">
    				<tr>
    					<td style="text-align:center;border-left:1px solid #e0e0e0;">
							${card.userId}
						</td>
    					<td style="text-align:center;border-left:1px solid #e0e0e0;">
    					<a href="<c:url value='/lightpack/overtimework/overtimeworkUserCardEditForm.do?userId=${card.userId}'/>" > ${card.userName}
    					</a>
    					</td>
    					<td style="text-align:center;border-left:1px solid #e0e0e0;">
    					<c:if test="${!empty card.jobDutyName}">${card.jobDutyName}</c:if><c:if test="${empty card.jobDutyName}">${card.jobTitleName}</c:if>
    					</td>
    					<td style="text-align:center;border-left:1px solid #e0e0e0;">
							${card.mail}
						</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;">
							${card.companyCodeName}
						</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;">
							${card.workPlaceName}
						</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;">${card.groupName}</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;border-right:1px solid #e0e0e0;">${card.cardId}</td>
    			    </tr>
    			    </c:forEach>
    			    </c:otherwise>
						</c:choose>
    			</tbody>
			</table>			
			<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> 
		</div>
		<!--//blockDetail End-->	
																																			
		
		
		<!--blockButton Start-->
		
	</form>
	<!--//popup_contents End-->

<!--//popup End-->