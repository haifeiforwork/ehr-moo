<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="preHeader"  		value="message.collpack.kms.admin.permission.user.header" /> 
<c:set var="preList"    		value="message.collpack.kms.admin.permission.list" />
<c:set var="preAdminSearch"  	value="message.collpack.kms.admin.permission.user.searchCondition" />

<c:set var="preSearch"  		value="ui.ikep4.common.searchCondition" />
<c:set var="prePrivate"    		value="message.collpack.kms.perform.private" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
		    //- 버튼영역 실행  
		    
			$jq("#saveButton").click(function() {   
	    		
	    		$.ajax({
					url : "<c:url value='/portal/admin/code/finance/createFinance.do' />",
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
		    
			sort = function(sortColumn, sortType) { 
				$("#itemForm input[name=actionType]").val("sort");
				$("#itemForm input[name=sortColumn]").val(sortColumn); 
				$("#itemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
				$("#searchUserPermButton").click();
			}; 
		    
			$("#searchUserPermButton").click(function() { 
				
				$("#itemForm input[name=actionType]").val("search");			
				$("#itemForm").submit();
				
				return false; 
			});
			
			$("#itemForm select[name=pagePerRecord]").change(function() { 
				$("#itemForm input[name=actionType]").val("pagePerRecord"); 
				$("#itemForm").submit(); 
				return false;
			});  
			
			
			$("#itemForm select[name=workPlaceName]").change(function() { 
				var workPlaceName = $("#itemForm select[name=workPlaceName]").val();
				
				if(workPlaceName == ""){
					$("#teamCode").empty();
					var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
					$("#teamCode").append(teamCode);
				}else{
					$("#itemForm").submit(); 
					return false;
				}
			}); 
			
			$("#itemForm select[name=teamCode]").change(function() { 
				$("#teamName").val($("#teamCode option:selected").text());
				$("#itemForm").submit(); 
				return false;
			}); 
			
			$("#itemForm input:radio[name=period]").click(function() { 
				
				$("#itemForm").submit(); 
				return false;
			}); 
            
        });
        
        
		
	})(jQuery);  

//-->  
</script>

<!--popup Start-->
<form id="itemForm" name="itemForm" method="post" action="<c:url value='/portal/admin/pw/pwUpdateList.do' />" >	

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>변경 대상 조회</h2>
	<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${announceCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>
	<div class="clear"></div>
</div>
	<!--popup_contents Start-->
	
	
	<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.actionType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.teamName">
<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>

<div class="blockSearch">
       	<div class="corner_RoundBox03">
		<table summary="tableSearch">
		<tbody>
                   <tr>
                       <th scope="row" width="5%"><ikep4j:message pre="${preAdminSearch}" key="workPlaceName" /></th>
                       <td width="30%">	
                       <spring:bind path="searchCondition.workPlaceName">                             			
                                 	<select title="${status.expression}" name="${status.expression}">
                                      <option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.company' /></option>	                                        
                                      <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
                                      	<option value="${workPlace}" <c:if test="${searchCondition.workPlaceName eq workPlace}">selected="selected"</c:if>>${workPlace}</option>
                                      </c:forEach>
                                  </select>	                                    
                              </spring:bind>
                              <spring:bind path="searchCondition.teamCode">
                                  <select title="teamCode" name="${status.expression}" id="${status.expression}">
                                  	<option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.teamCode' /></option>
                                      <c:forEach var="teamInfo" items="${teamList}">
                                      	<option value="${teamInfo.teamCode}" <c:if test="${searchCondition.teamCode eq teamInfo.teamCode}">selected="selected"</c:if>>${teamInfo.teamName}</option>
                                      </c:forEach>	                                        
                                  </select>
						</spring:bind>	
                       </td>
                       <th scope="row" width="5%">변경예외</th>
                       <td width="10%">
                       <spring:bind path="searchCondition.passwordChangeYn">  
                       		<select title="${status.expression}" name="${status.expression}">
                       			<option value="" <c:if test="${'' eq status.value}">selected="selected"</c:if>>전체</option>
                       			<option value="1" <c:if test="${'1' eq status.value}">selected="selected"</c:if>>Y</option>
                               	<option value="0" <c:if test="${'0' eq status.value}">selected="selected"</c:if>>N</option>
                               
                           </select>	
                       	</spring:bind>
                       </td>
                       <td width="20%">
                       <spring:bind path="searchCondition.period">  
                       	<input name="${status.expression}" class="radio" title="radio" type="radio" value="" style="vertical-align: middle;" <c:if test="${empty searchCondition.period}">checked="checked"</c:if>/>전체
                       	<input name="${status.expression}" class="radio" title="radio" type="radio" value="4" style="vertical-align: middle;" <c:if test="${searchCondition.period eq '4'}">checked="checked"</c:if>/>D+
                       	<input name="${status.expression}" class="radio" title="radio" type="radio" value="0" style="vertical-align: middle;" <c:if test="${searchCondition.period eq '0'}">checked="checked"</c:if>/>D-day
                       	<input name="${status.expression}" class="radio" title="radio" type="radio" value="1" style="vertical-align: middle;" <c:if test="${searchCondition.period eq '1'}">checked="checked"</c:if>/>D-1
                       	<input name="${status.expression}" class="radio" title="radio" type="radio" value="3" style="vertical-align: middle;" <c:if test="${searchCondition.period eq '3'}">checked="checked"</c:if>/>D-3
                       	</spring:bind>
                       </td>
                       
                       <td width="20%">		
                       <spring:bind path="searchCondition.searchColumn">						
                           <select title="${status.expression}" name="${status.expression}">
                               <option value="userName" <c:if test="${'userName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preAdminSearch}" key="gubun.name" /></option>
                           </select>															
                           <spring:bind path="searchCondition.searchWord">  					
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='' size="20"/>
					</spring:bind>
                       </td>
                       </spring:bind>							
                   </tr>														
               </tbody>
           	</table>
           	<div class="searchBtn"><a id="searchUserPermButton" name="searchUserPermButton" href="#a"><span>Search</span></a></div> 
           </div>
</div>
   		<!--blockDetail Start-->
		<div class="blockListTable">
				<!--Layout Start-->
				<div id="container">
					<!--List Layout Start-->
					<div id="listDiv"> 
					<table summary="<ikep4j:message pre='${preList}' key='summary' />">
						<caption></caption>
						<thead>
						<tr>							
							<th scope="col" width="10%">
								<a onclick="sort('USER_NAME', '<c:if test="${searchCondition.sortColumn eq 'USER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='name' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'USER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'USER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>		
							</th>
							<th scope="col" width="10%">
								<a onclick="sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
							<th scope="col" width="10%">
								<a onclick="sort('UPDATE_PASSWORD_DATE', '<c:if test="${searchCondition.sortColumn eq 'UPDATE_PASSWORD_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									최근 변경일
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'UPDATE_PASSWORD_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'UPDATE_PASSWORD_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
							<th scope="col" width="10%">
								<a onclick="sort('CHANGE_PASSWORD_DATE', '<c:if test="${searchCondition.sortColumn eq 'CHANGE_PASSWORD_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									변경 예정일
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'CHANGE_PASSWORD_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'CHANGE_PASSWORD_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
							<th scope="col" width="10%">
								<a onclick="sort('PASSWORD_CHANGE_YN', '<c:if test="${searchCondition.sortColumn eq 'PASSWORD_CHANGE_YN'}">${searchCondition.sortType}</c:if>');"  href="#a">
									변경 예외
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'PASSWORD_CHANGE_YN' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'PASSWORD_CHANGE_YN' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="userInfo" items="${searchResult.entity}">
									<tr>												
										<td>										
											${userInfo.userName} <c:if test="${!empty userInfo.jobDutyName}">${userInfo.jobDutyName}</c:if> 
											<c:if test="${empty userInfo.jobDutyName}">${userInfo.jobTitleName}</c:if>
										</td>
										<td>
											${userInfo.teamName}								
										</td>
										<td>
											${userInfo.updatePasswordDate}
										</td>
										<td>
											${userInfo.expectedUpdatePasswordDate}
										</td>
										<td>
											<c:if test="${userInfo.passwordChangeYn == '1'}">Y</c:if>
											<c:if test="${userInfo.passwordChangeYn != '1'}">N</c:if>
										</td>
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
						</tbody>				
					</div>
					<!--//blockListTable End-->
					</table>
					
				
					
					<!--Layout Start-->
					<div id="container">
						<!--List Layout Start-->
						<div id="listDiv"> 						
							
							<!--Page Number Start-->
							<spring:bind path="searchCondition.pageIndex">
								<ikep4j:pagination searchButtonId="searchUserPermButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
								<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
							</spring:bind>
							<!--//Page Number End-->
							</div>
						</div>
					</div>
					
				</div>
			</div>
		<!--//blockDetail End-->	 
																																			
		
		
		<!--blockButton Start-->
		
	</form>

<!--//popup End-->