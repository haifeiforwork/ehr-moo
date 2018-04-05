<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.wfapproval.work.aplist" />
<c:set var="preList"    			value="ui.wfapproval.work.aplist.listTodo" />
<c:set var="preButton"  			value="ui.wfapproval.common.button" />
<c:set var="preSearch"  			value="ui.wfapproval.common.searchCondition" />
<c:set var="preWorkplace"     value="ui.workflow.workplace.worklist" />
<c:set var="preWorkplaceCommon"  value="ui.workflow.workplace.common" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:choose>
	<c:when test="${listType eq 'myRequestList'}">
		<c:set var="formActUrl"     value="/wfapproval/work/aplist/listApMyRequest.do" />
	</c:when>
	<c:when test="${listType eq 'completeList'}">
		<c:set var="formActUrl"     value="/wfapproval/work/apdoc/listApComplete.do" />
	</c:when>
	<c:when test="${listType eq 'todoList'}">
		<c:set var="formActUrl"     value="/wfapproval/work/aplist/listApTodo.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/wfapproval/work/aplist/listApTodo.do" />
	</c:otherwise>
</c:choose>	
			 
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
	 var resultData = new Array();
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
	
	var cnt=0;
	//미사용
	f_getProcessInfo = function (pId, pName, pType,form){
		
		resultData[cnt] = {
			id		: pId,
			name	: pName,
			type	: pType
		};		
		cnt++;
		
		//iKEP.debug(resultDataSave);
		//상세화면 프로세스명 설정.
		//$("#selectProcess").text(">> "+pName);
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() { 
		
		fnCaller = function (params, dialog) {
			if(params) {
				//alert(params.id);
				//resultData = params;
			}
			
			dialogWindow 	= dialog;
		};
		
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		//alert(document.searchForm.apprId100000741565.value) ;
		//document.searchForm.apprId100000741565.checked =true;
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
		
		
		
		/**
		    * ApForm 버튼영역 실행
		    */
			$("#searchApFormProcessButton a").click(function(){
	            switch (this.id) {
	                case "applyProcessButton":
	                	
	                	var idlist = [];
						var itemstr ="";
 						$('input[name=apprId]:checked').each(function(){idlist.push(this.value)});

 						if(idlist.length == 0){
							alert("선택된 문서가 없습니다.");
	                		return false;
						}else{
							$.each(idlist,function(index, item){
								  itemstr += item + ',';
								  var index = item.indexOf("^");
								  resultData[cnt] = {
											id		: item.substring(0,index),
											name	: item.substring(index+1),
											type	: ""
								  };
								  //iKEP.debug(resultData[cnt]);
								  cnt++;
							 });	
	            		
	            		}
	                	
	                	if (!resultData) {
							
							alert("선택한 결재선이 없습니다.");
							return false;
						}
						
						dialogWindow.callback(resultData);
						dialogWindow.close();
						break;
	                case "cancelProcessButton":
	                    dialogWindow.close();
	                    break;
	                default:
	                    break;
	            }
	        });
		
		/**
 		 * 체크박스 ALL 
 		 */
		$("#checkboxAllApForm").click(function() { 
			$("input[name=apprId]").attr("checked", $(this).is(":checked"));  
		}); 
		
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='${listType}' /></h1>

<!--pageTitle Start-->

<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="searchForm" name="searchForm" method="post" action="<c:url value='${formActUrl}' />">

	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${listType}' />">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="searchCondition.apprTypeCd">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<option value=""><ikep4j:message pre="${preSearch}" key="selectAll"/></option>
							<c:forEach var="apCode" items="${listFormTypeCd}">
								<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
				<c:if test="${listType ne 'myRequestList'}">
					<spring:bind path="searchCondition.requestorId">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="userName" id="userName" value="<c:out value="${pUserName}"/>" size="10" />&nbsp;
						<a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" name="userSearchBtn" id="userSearchBtn" alt="" />Search</span></a>
					  &nbsp;
						<a class="button_ic valign_bottom" href="#"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_delete.gif"  name="userRemoveBtn" id="userRemoveBtn" alt="" />Delete</span></a>
						<input type="hidden" name="userNameIsMulti" id="userNameIsMulti" value="N" />
						<input type="hidden" name="requestorId" id="requestorId" value="${status.value}" />
					</td>
					</spring:bind>
				</c:if>
					<spring:bind path="searchCondition.apprDocState">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td <c:choose><c:when test="${listType eq 'myRequestList'}">colspan=3</c:when><c:otherwise>width="25%"</c:otherwise></c:choose>>
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<option value=""><ikep4j:message pre="${preSearch}" key="selectAll"/></option>
							<c:forEach var="apCode" items="${listApprDocState}">
								<option value="${apCode.codeValue}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.codeName}</option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
				</tr>
				<tr>					
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
					<td width="25%">
						<spring:bind path="searchCondition.startPeriod">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
						</spring:bind>
						<spring:bind path="searchCondition.endPeriod">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
						</spring:bind>							
					</td>
					<spring:bind path="searchCondition.searchcondition">
    			<input type="hidden" title="<ikep4j:message pre='${preWorkplaceCommon}' key='searchcondition'/>" name="${status.expression}" value="TITLE" />
					</spring:bind>
					<spring:bind path="searchCondition.searchkeyword">
					<th scope="row"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
					<td colspan="3">								
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preWorkplaceCommon}' key='searchkeyword'/>" name="${status.expression}" value="${status.value}" size="35" />
					</td>		
					</spring:bind>		
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
  <ikep4j:message pre='${preApCommList}.pageTitle' key='${listType}' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preWorkplaceCommon}' key='${status.expression}' />'>
				<c:forEach var="code" items="${workplaceCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			<div class="totalNum">${apList.pageIndex}/ ${apList.pageCount} <ikep4j:message pre='${preWorkplaceCommon}' key='page' /> ( <ikep4j:message pre='${preWorkplaceCommon}' key='pageAll' /><span> ${apList.recordCount}</span>)</div>
			<div align="right">
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.stop'/>" /><ikep4j:message pre='${preApCommList}' key='display.stop'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.error'/>" /><ikep4j:message pre='${preApCommList}' key='display.error'/>
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${listType}' />>
		<caption></caption>
		<col style="width: 5%;"/>
		<col style="width: 7%;"/>
		<col style="width: 10%;"/>
		<col/>
		<c:if test="${listType ne 'myRequestList'}"><col style="width: 10%;"/></c:if>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAllApForm" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprTypeCd' />
				</th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
					<a onclick="f_Sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
				<c:if test="${listType ne 'myRequestList'}">
				<th scope="col">
					<ikep4j:message pre='${preApCommList}' key='author' />
				</th>
				</c:if>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
				    <c:choose>
				 			<c:when test="${listType ne 'myRequestList'}">
								<a onclick="f_Sort('procStartDate', '<c:if test="${searchCondition.sortColumn eq 'procStartDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
				 			</c:when>
				 			<c:otherwise>
								<a onclick="f_Sort('createDate', '<c:if test="${searchCondition.sortColumn eq 'createDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
				 			</c:otherwise>
						</c:choose>					
						<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="오름차순"/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="내림차순"/>
					</a>
				</th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprDocState' />
				</th>
			 	<th scope="col">
					<ikep4j:message pre='${preApCommList}' key='procStateName' />
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${apList.emptyRecord}">
					<tr>
			    <c:choose>
			 			<c:when test="${listType ne 'myRequestList'}">
							<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
			 			</c:when>
			 			<c:otherwise>
							<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
			 			</c:otherwise>
					</c:choose>					
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
							<td><!--  <input 	id="apprId" name="apprId" onClick="f_getProcessInfo('${aplist.appKey01}','${aplist.title}','3',this.form)" class="checkbox" title="checkbox" type="checkbox" value="${aplist.appKey01}"/></td>-->
				<input 	id="apprId" name="apprId" class="checkbox" title="checkbox" type="checkbox" value="${aplist.appKey01}^${aplist.title}"/></td>			
							<td>${aplist.formTypeName}</td>
							<td class="textLeft">${aplist.title}</td>
					    <c:choose>
					 			<c:when test="${listType ne 'myRequestList'}">
							    <td>${aplist.userName}</td>
							    <td><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${aplist.procStartDate}"/></td>
					 			</c:when>
					 			<c:otherwise>
							     <td><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${aplist.createDate}"/></td>
					 			</c:otherwise>
							</c:choose>
							
							<td>
								<c:choose>
									<c:when test="${aplist.state == 'RUNNING'}">
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${adminMessage}' key='display.progress'/>" />
									</c:when>
									<c:when test="${aplist.state == 'COMPLETE'}">
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_02.png'/>" alt="<ikep4j:message pre='${adminMessage}' key='display.complete'/>" />
									</c:when>
									<c:when test="${aplist.state == 'SUSPEND'}">
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${adminMessage}' key='display.stop'/>" />
									</c:when>
									<c:otherwise>
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_03.png'/>" alt="<ikep4j:message pre='${adminMessage}' key='display.error'/>" />
									</c:otherwise>
								</c:choose>
							</td>							
							
							
							<td>
							<a class="button_ic valign_bottom" href="javaScript:f_ViewApProcessState('${aplist.appKey01}','${aplist.instanceId}','${aplist.processId}');"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" alt="" />View</span></a>
							</td>
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

<div id="searchApFormProcessButton" class="blockButton" style="text-align:center;"> 
		<ul>
			<li><a id="applyProcessButton"   	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
			<li><a id="cancelProcessButton" 	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
</div>
<!--blockButton Start-->

<%//@ include file="/WEB-INF/view/support/popup/includePopupUser.jsp"%>
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
						$jq("#requestorId").val(data[index].userId);
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
						$jq("#requestorId").val("");
				});
		    
		    /*
				$("#datepicker").datepicker({
						inline: true,
						onSelect: function(date) { alert(date); }
				});
				
				$jq("input.datepicker").datepicker({
						onSelect: function(date, event) {
								var arrDate = date.split("-");
								var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
								event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
						}
				});

				$("#endPeriod" ).datepicker({
					showOn: "button",
					buttonImage: "/ikep4-webapp/base/images/icon/ic_cal.gif",
					buttonImageOnly: true
				}); */
		    

				$jq("#startPeriod").datepicker()
					.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
				
				$jq("#endPeriod").datepicker()
				.next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

				$jq('#startPeriod').change( function() {
					  if( $jq('#endPeriod').val() != '' ) {
						    if( $jq('#startPeriod').val().replaceAll('-','') > $jq('#endPeriod').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preApCommList}.messages" key="dateFault" />");
						    	  $jq('#startPeriod').val("");
						    } 
					  } else {
						    $jq('#endPeriod').val($jq('#startPeriod').val());
					  }
				});
				
				$jq('#endPeriod').change( function() { 
					  if( $jq('#startPeriod').val() != '' ) {
						    if( $jq('#startPeriod').val().replaceAll('-','') > $jq('#endPeriod').val().replaceAll('-','') ) {
						    	  alert("<ikep4j:message pre="${preApCommList}.messages" key="dateFault" />");
						    	  $jq('#endPeriod').val("");
						    }
					  } else {
						  	$jq('#startPeriod').val($jq('#endPeriod').val());
					  }
				});

		});
		
		/**
		 * 프로세스 진행현황 화면 오픈.
		 */
		f_ViewApProcessState = function(apprId, instanceId, processId) {
			var mode = "runtime";//"model";//"design";
			viewApProcessStateDialog = new iKEP.Dialog({     
				title 		: "결재진행현황",
				url 			: "/ikep4-webapp/workflow/modeler/prism.do?mode="+mode+"&apprId="+apprId+"&processId="+processId+"&version=&instanceId="+instanceId+"&onlyProcessView=true&processType=approval",
				modal 		: true,
				width 		: 950, //1200,
				height 		: 450,
				params 		: null,
				callback	: null,
				buttons: { '창닫기': function() {
                      $(this).dialog('close');
                     }
                 },
        close: function() {}
			});
		}		

})(jQuery);

//window.open('www.naver.com', 'test', 'top=100px, left=100px, width=600, height=400, menubar=no');


</script>

<!--//blockButton End-->