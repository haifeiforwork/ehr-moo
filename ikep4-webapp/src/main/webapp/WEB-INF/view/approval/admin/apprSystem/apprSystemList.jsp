<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 


<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprSystem.apprSystemList"/>

<c:set var="preHeader"	value="ui.approval.admin.apprSystem.apprSystemList.header"/>
<c:set var="preList"	value="ui.approval.admin.apprSystem.apprSystemList.list"/>
<c:set var="preView"	value="ui.approval.admin.apprSystem.apprSystemList.view"/>
<c:set var="preSearch"	value="ui.approval.admin.apprSystem.apprSystemList.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprSystem.apprSystemList.button"/>	
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
<!--
(function($){

	// 상단에 보이게 될 리스트를 가져오는 함수
	getList = function() {
		$jq("#searchForm").attr("action", "<c:url value='apprSystemList.do' />");
		$jq("#searchForm")[0].submit();
	};

	// 하단에 보이게 될 상세정보를 가져오는 함수
	getView = function(systemId, tr) {

		var selectClassName = "bgSelected";
		$jq(tr).parent().parent().children().removeClass(selectClassName).end()
				.end().end().addClass(selectClassName);

		$jq.ajax({
			url : '<c:url value="apprSystemForm.do" />',
			data : {
				systemId : systemId
			},
			type : "get",
			success : function(result) {
				$jq("#viewDiv").html(result);
			}
		});
	};

	// 코드 중복을 체크함
	checkSystemId = function() {

		if($jq("#systemId").val()=='') {
			alert("<ikep4j:message pre="${preMessage}" key="noSystemId" />");
		} else {
			$jq.ajax({
				url : '<c:url value="checkSystemId.do" />',     
				data : {systemId: $jq("#systemId").val()},     
				type : "post",
				success : function(result) {     
					
					if(result == 'duplicated') {
						alert("<ikep4j:message pre="${preMessage}" key="isDuplicated" />");
						$jq("#systemIdCheck").val("modify");
					} else {
						alert("<ikep4j:message pre="${preMessage}" key="isAvailable" />");
						$jq("#systemIdCheck").val("success");
					}
				}
			});
		}
	};
	
	// 코드를 삭제함
	deleteForm = function() {
		if ($jq("#systemId").val() == "") {
			alert("<ikep4j:message pre="${preMessage}" key="noDelete" />");
			return;
		}
		
		if(confirm("<ikep4j:message pre="${preMessage}" key="wannaDelete" />")) {
			$jq.ajax({
				url : '<c:url value="deleteApprSystem.do" />',
				data : $jq("#mainForm").serialize(),
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre="${preMessage}" key="deleteCompleted" />");
					getList();
				},
				error:function(){
					alert("<ikep4j:message pre="${preMessage}" key="failedToDelete" />");
				}
			});
		} else {
			return;
		}
	};
	
	// 순서를 위로
	setUp = function(systemId, systemOrder) {
		$jq.ajax({     
			url : '<c:url value="goUp.do" />',     
			data : {systemId: systemId, systemOrder: systemOrder},     
			type : "post",     
			success : function(result) {
				getList();
			} 
		});  
	}
	
	// 순서를 아래로
	setDown = function(systemId, systemOrder) {
		
		$jq.ajax({     
			url : '<c:url value="goDown.do" />',     
			data : {systemId: systemId, systemOrder: systemOrder},     
			type : "post",     
			success : function(result) {         
				getList();
			} 
		});  
	};
	
	// 소팅을 위한 함수
	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};


	// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
	$(document).ready(function() {
		
		$jq("#apprSystemListLinkOfLeft").click();
		
		$jq("#newFormButton").click(function() {
			$jq("#systemIdCheck").val("");
			getView();
		});
		
		$jq("#submitButton").click(function() {
			$jq("#mainForm").trigger("submit");
		});
		
		$jq("#deleteButton").click(function() {
			deleteForm();
		});
		
		// ID 중복을 체크함
		$jq("#checkSystemIdButton").click(function() {
			checkSystemId();
		});
		
		$jq("#searchButton").click(function() {   
			getList();
		});
		
		// code 값이 바뀌면 중복체크를 위해 systemIdCheck 값을 바꿔줌
		$jq("input[name=systemId]").change(function() {
			$jq("#systemIdCheck").val('changed');
		});
		
		// 백스페이스 방지(input, select 제외)
		$jq(document).keydown(function(e) {
			var element = e.target.nodeName.toLowerCase();
			if (element != 'input') {
				if (e.keyCode === 8) {
					return false;
				}
			}
		});
		
		var validOptions = {
				rules : {
					systemId : {
						required : true,
						rangelength : [0, 20]
					},
					systemName : {
						required : true,
						rangelength : [0, 20]
					},
					systemEnName : {
						required : true,
						englishName : true,
						rangelength : [0, 100]
					}
				},
				messages : {
					systemId : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemId" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemId" />"
					},
					systemName : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemName" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemName" />"
					},
					systemEnName : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemEnName" />",
						englishName : "<ikep4j:message key="EnglishName.apprSystem.systemEnName" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemEnName" />"
					}
				},
				submitHandler : function() {
					
					// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
					if (($jq("#systemIdCheck").val() == 'success') || 
							($jq("#systemIdCheck").val() == 'modify')) {
						$jq.ajax({
							url : '<c:url value="createApprSystem.do" />',
							data : $jq("#mainForm").serialize(),
							type : "post",
							success : function(result) {
								alert("<ikep4j:message pre="${preMessage}" key="saveCompleted" />");
								$jq("#tempCode").val(result);
								getList();
							},
							error : function(xhr, exMessage) {
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
							}
						});
					} else {
						alert("<ikep4j:message pre="${preMessage}" key="checkDuplicated" />");
						return;
					}
				}

		};

		var validator = new iKEP.Validator("#mainForm", validOptions);
		
	});


})(jQuery);
//-->
</script>

<h1 class="none"><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>

		

<!--blockListTable Start-->
<div class="blockListTable">
	<div id="listDiv">
		<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">

			<spring:bind path="searchCondition.sortColumn">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="searchCondition.sortType">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			
			<!--tableTop Start-->  
			<div class="tableTop">
				<div class="tableTop_bgR"></div>
				<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
				<div class="listInfo"> 
					<spring:bind path="searchCondition.pagePerRecord">  
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
							<c:forEach var="code" items="${boardCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
						</select> 
					</spring:bind>
					<div class="totalNum">
						${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
						( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
					</div>
				</div>
				<div class="tableSearch"> 
					<spring:bind path="searchCondition.searchColumn">  
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<option value="systemId" <c:if test="${'systemId' eq status.value}">selected="selected"</c:if> >
								<ikep4j:message pre="${preSearch}" key="systemId" />
							</option>
							<option value="systemName" <c:if test="${'systemName' eq status.value}">selected="selected"</c:if> >
								<ikep4j:message pre="${preSearch}" key="systemName" />
							</option>
						</select>	
					</spring:bind>		
					<spring:bind path="searchCondition.searchWord">  					
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
					</spring:bind>
					<a id="searchButton" name="searchButton" href="#a" class="ic_search">
						<span><ikep4j:message pre='${preSearch}' key='search' /></span>
					</a>
				</div>
				<div class="clear"></div>	
			</div>
			<!--//tableTop End-->	
			
			<table summary="<ikep4j:message pre="${preList}" key="tableSummary" />">
				<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="10%"/>
						<col width="20%"/>
						<col width="25%"/>
						<col width="25%"/>
						<col width="10%"/>
					</colgroup>
				<thead>
					<tr>
						<th scope="col">
							<a onclick="javascript:sort('SYSTEM_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre="${preList}" key="no" />
							</a>
						</th>
						<th scope="col">
							<a onclick="javascript:sort('SYSTEM_ID', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre="${preList}" key="systemId" />
							</a>
						</th>
						<th scope="col">
							<ikep4j:message pre="${preList}" key="systemType" />							
						</th>						
						<th scope="col">
							<a onclick="javascript:sort('SYSTEM_NAME', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre="${preList}" key="systemName" />
							</a>
						</th>
						<th scope="col">
							<a onclick="javascript:sort('SYSTEM_EN_NAME', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_EN_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre="${preList}" key="systemEnName" />
							</a>
						</th>
						<th scope="col"><ikep4j:message pre='${preList}' key='systemOrder' /></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='emptyRecord' /></td> 
							</tr>				        
						</c:when>
						<c:otherwise>
							<c:forEach var="systemList" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="textCenter">${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}</td>
									<td class="textCenter"><a href="#a" onclick="getView('${systemList.systemId}', this)">${systemList.systemId}</a></td>
									<td class="textCenter"><a href="#a" onclick="getView('${systemList.systemId}', this)">
									<c:choose>
									<c:when test="${systemList.systemType==0}">
									<ikep4j:message pre='${preList}' key='systemType.systemType0' />
									</c:when>
									<c:when test="${systemList.systemType==1}">
									<ikep4j:message pre='${preList}' key='systemType.systemType1' />
									</c:when>
									<c:when test="${systemList.systemType==2}">
									<ikep4j:message pre='${preList}' key='systemType.systemType2' />
									</c:when>
									<c:when test="${systemList.systemType==3}">
									<ikep4j:message pre='${preList}' key='systemType.systemType3' />
									</c:when>
									<c:when test="${systemList.systemType==4}">
									<ikep4j:message pre='${preList}' key='systemType.systemType4' />
									</c:when>
									</c:choose>							
									</a></td>
									<td class="textCenter"><a href="#a" onclick="getView('${systemList.systemId}', this)">${systemList.systemName}</a></td>
									<td class="textCenter"><a href="#a" onclick="getView('${systemList.systemId}', this)">${systemList.systemEnName}</a></td>
									<td class="textCenter">
										<a href="#" onclick="setUp('${systemList.systemId}', '${systemList.systemOrder}')">
											<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif' />" alt="UP" />
										</a>
										<a href="#" onclick="setDown('${systemList.systemId}', '${systemList.systemOrder}')">
											<img src="<c:url value='/base/images/icon/ic_tablesort_down.gif' />" alt="DOWN" />
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<!--Page Number Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Number End-->
			
			<input type="hidden" name="tempCode" id="tempCode" value=""	/>

		</form>
	</div>
</div>
<!--//blockListTable End-->

<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
<h3><ikep4j:message pre="${preList}" key="subTitle.detail" /></h3>
</div>
<!--//subTitle_2 End--> 

<!--blockDetail Start-->
<div class="blockDetail">
	<div id="viewDiv">
		<form id="mainForm" name="mainForm" method="post" onsubmit="return false;" action="">
		
			<input type="hidden" id="systemIdCheck" name="systemIdCheck" value="${apprSystem.systemIdCheck}"/>
		
			<table summary="<ikep4j:message pre="${preList}" key="tableSummary" />">
			<caption></caption>
				<colgroup>
					<col width="15%"/>
					<col width="10%"/>
					<col width="75%"/>
				</colgroup>								
			<tbody>
				<tr>
					<th scope="row" colspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemId" />
					</th>
					<td>
						<div>
							<input type="text" name="systemId" id="systemId" value="${apprSystem.systemId}" size="50" class="inputbox" />
							&nbsp;<a class="button_ic" id="checkSystemIdButton" href="#a"><span><ikep4j:message pre="${preList}" key="checkDuplicated" /></span></a>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" colspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemType" />
					</th>
					<td>
						<div>
							<select title="<ikep4j:message pre='${preList}' key='systemType' />" name="systemType" id="systemType">
							<option value="0"><ikep4j:message pre='${preList}' key='systemType.systemType0' /></option>
							<option value="1"><ikep4j:message pre='${preList}' key='systemType.systemType1' /></option>
							<option value="2"><ikep4j:message pre='${preList}' key='systemType.systemType2' /></option>
							<option value="3"><ikep4j:message pre='${preList}' key='systemType.systemType3' /></option>
							<option value="4"><ikep4j:message pre='${preList}' key='systemType.systemType4' /></option>
							</select>	
						</div>
					</td>
				</tr>				
				<tr>
					<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemName" /></th>
					<th><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemName" /></th>
					<td>
						<div>
							<input type="text" name="systemName" id="systemName" value="${apprSystem.systemName}" size="50" class="inputbox" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemEnName" /></th>
					<td>
						<div>
							<input type="text" name="systemEnName" id="systemEnName" value="${apprSystem.systemEnName}" size="50" class="inputbox" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" colspan="2"><ikep4j:message pre="${preList}" key="description" /></th>
					<td>
						<div>
							<textarea name="description" id="description" class="" style="width:80%;" title="<ikep4j:message pre='${preList}' key='description'/>">${apprSystem.description}</textarea>
						</div>
					</td>
				</tr>				
			</tbody>
			
			</table>
			
			<input type="hidden" id="systemOrder" name="systemOrder" value="${apprSystem.systemOrder}"/>
			<input type="hidden" id="oldSystemOrder" name="oldSystemOrder" value="${oldSystemOrder}"/>
			
		</form>
	</div>
</div>
<!--//blockDetail End--> 

<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre="${preButton}" key="new" /></span></a></li>
	<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
	<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
</ul>
</div>
<!--//blockButton End-->
