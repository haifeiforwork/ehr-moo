<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"    value="ui.wfapproval.work.aplist" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
<!-- 
(function($) {

	//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
	
	/**
	 * 정렬 조건 변경 함수
	 * 
	 * @param {Object} sortColumn
	 * @param {Object} sortType
	 */
	f_Sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == '') sortType = 'ASC';
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#searchApListButton").click();
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() { 
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$("#searchForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
            $("#pageIndex").click();
        });
		
 
		$("#listApButton").click(function() {   
			location.href='listApTempSearch.do';
		});
		
		$("#createApButton").click(function() {   
			location.href='createApForm.do';
		});
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre="${preList}.pageTitle" key="${listType}" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preList}.pageTitle" key="${listType}" /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst">Home</li>
			<li>Approval</li>
			<li><ikep4j:message pre='ui.wfapproval.work.aplist.subtitle' key='reference' /></li>
			<li class="liLast"><ikep4j:message pre='${preList}.pageTitle' key='${listType}' /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='/wfapproval/work/aplist/listApRef.do' />">

	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="검색">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="searchCondition.apprTypeCd">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="15%">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<option value=""><ikep4j:message pre="${preSearch}" key="selectAll"/></option>
							<c:forEach var="apCode" items="${listFormTypeCd}">
								<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
					<spring:bind path="searchCondition.registUserId">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="35%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="userName" id="userName" value="<c:out value="${pUserName}"/>" size="10" />&nbsp;
						<a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" name="userSearchBtn" id="userSearchBtn" alt="" />Search</span></a>
					  &nbsp;
					  <a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_delete.gif"  name="userRemoveBtn" id="userRemoveBtn" alt="" />Delete</span></a>
						<input type="hidden" name="userNameIsMulti" id="userNameIsMulti" value="N" />
						<input type="hidden" name="registUserId" id="registUserId" value="${status.value}" />
					</td>
					</spring:bind>
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
					<td width="35%">
						<spring:bind path="searchCondition.searchStartDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
						</spring:bind>
						<spring:bind path="searchCondition.searchEndDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
						</spring:bind>							
					</td>
				</tr>
				<tr>
					<spring:bind path="searchCondition.apprTitle">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td colspan=3>
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="60" />
					</td>
					</spring:bind>
					<input name="apprDocState" value="0" type="hidden" title='<ikep4j:message pre='${preSearch}' key='apprDocState' />' />
				</tr>								
			</tbody>
		</table>
		<div class="searchBtn">
			<a id="searchApListButton" name="searchApListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
		</div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>
	</div>
</div>	
<!--//blockSearch End-->

<h1 class="none">
	<ikep4j:message pre='${preList}.pageTitle' key='${listType}' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${commonCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum">${apList.pageIndex}/ ${apList.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${apList.recordCount}</span>)</div>
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	
	<table summary="<ikep4j:message pre='${preList}.pageTitle' key='${listType}' />">
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>
		<col/>
		<col style="width: 15%;"/>
		<col style="width: 20%;"/>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprTypeCd' />
				</th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
					<a onclick="f_Sort('APPR_TITLE', '<c:if test="${searchCondition.sortColumn eq 'APPR_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
				<th scope="col">
					<ikep4j:message pre='${preList}' key='author' />
				</th>				
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprDocState' />
				</th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
					<a onclick="f_Sort('APPR_REQ_DATE', '<c:if test="${searchCondition.sortColumn eq 'APPR_REQ_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${apList.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="aplist" items="${apList.entity}" varStatus="i">
						<c:choose>
				 			<c:when test="${i.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>
						<tr class="${className}">
							<td>${(apList.recordCount-(searchCondition.pagePerRecord*(apList.pageIndex-1))-i.count)+1}</td>
							<td>${aplist.apprTypeName}</td>
							<td class="textLeft"><a href="<c:url value="/wfapproval/work/apdoc/readApDoc.do?apprId="/>${aplist.apprId}">${aplist.apprTitle}</a></td>
							<td>${aplist.registUserName}</td>
							<td>${aplist.apprDocStateNm}</td>
							<td><ikep4j:timezone date="${aplist.apprReqDate}"/></td>
						</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->
		
</div>
</form>
<!--//blockListTable End-->	

<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<li><a id="listApButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	</ul>
</div>
<script type="text/javascript" language="javascript">
(function($) {
		//검색되거나 선택된 값이 있을경우 실행됨
		//함수명은 동일하게, 함수안의 내용은 각자에 맞게 코딩함
		//data : 선택된 값을 배열로 리턴함
		//targetId : 처음 이벤트가 발생한 객체의 ID, 입력박스가 여려개일 경우 구분자로 활용함
		setUser = function(data) {
				$jq(data).each(function(index) {
						//userId, userName, mail, teamName, jobTitleName, empNo, mobile
						$jq("#userName").val(data[index].userName);
						$jq("#registUserId").val(data[index].userId);
				});
				//$jq("#userDiv").html(str);
		};

		//검색되거나 선택된 값이 없을경우 실행됨
		//함수명은 동일하게, 함수의 내용은 각자에 맞게 코딩함
		clearUser = function(targetId) {  //alert(iKEP.debug($jq("#userName").val()));
				//$jq("#userName").val("");
				//$jq("#requestorId").val("");
		};

		$jq(document).ready(function() {
       
			//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
				$jq('#userName').keypress( function(event) {
						iKEP.searchUser(event, $jq('#userNameIsMulti').val(), setUser);
				});

        //검색버튼을 클릭했을때 이벤트 바인딩
				$jq('#userSearchBtn').click( function() { 
						$jq('#userName').trigger("keypress");
				});
        
		    //삭제 버튼을 클릭했을때 이벤트 바인딩
				$jq('#userRemoveBtn').click(function(event) {
						$jq("#userName").val("");
						$jq("#registUserId").val("");
				});

		    /*
				$("#datepicker").datepicker({
					inline: true,
					onSelect: function(date) { alert(date); }
				});

				$("input.datepicker").datepicker({
					onSelect: function(date, event) {
							var arrDate = date.split("-");
							var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
							event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
					}
				});*/
			
				$("#searchStartDate").datepicker()
					.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			
				$("#searchEndDate").datepicker()
					.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

				$jq('#searchStartDate').change( function() {
					  if( $jq('#searchEndDate').val() != '' ) {
						    if( $jq('#searchStartDate').val().replaceAll('-','') > $jq('#searchEndDate').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preList}.messages" key="dateFault" />");
						    	  $jq('#searchStartDate').val("");
						    } 
					  } else {
						    $jq('#searchEndDate').val($jq('#searchStartDate').val());
					  }
				});
				
				$jq('#searchEndDate').change( function() { 
					  if( $jq('#searchStartDate').val() != '' ) {
						    if( $jq('#searchStartDate').val().replaceAll('-','') > $jq('#searchEndDate').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preList}.messages" key="dateFault" />");
						    	  $jq('#searchEndDate').val("");
						    }
					  } else {
						  	$jq('#searchStartDate').val($jq('#searchEndDate').val());
					  }
				});
				
		});

})(jQuery);  
</script>
<!--//blockButton End-->