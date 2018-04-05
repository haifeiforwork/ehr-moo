<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    		value="ui.wfapproval.work.aplist" />
<c:set var="preList"    			value="ui.wfapproval.work.aplist.listTodo" />
<c:set var="preButton"  			value="ui.wfapproval.common.button" />
<c:set var="preSearch"  			value="ui.wfapproval.common.searchCondition" />
<c:set var="preWorkplace"     		value="ui.workflow.workplace.worklist" />
<c:set var="preWorkplaceCommon"  	value="ui.workflow.workplace.common" />
<c:set var="formActUrl"     		value="/wfapproval/work/portlet/recentApDoc/maxView.do" />

<script type="text/javascript">
<!-- 
(function($) {
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() { 
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex_${portletConfigId}").click(function() {
			
			var pagePerRecord = $("#pagePerRecord_${portletConfigId}").val();
			var pageIndex = $("#pageIndex_${portletConfigId}").val();
			
			PortletSimple.load('${portletConfigId}', '<c:url value="/wfapproval/work/portlet/recentApDoc/maxView.do"/>?pagePerRecord='+pagePerRecord+'&pageIndex='+pageIndex,'에러 메세지');
		});
		
		$("#pagePerRecord_${portletConfigId}").change(function(){
            $("#pageIndex_${portletConfigId}").click();
        });
 	});
})(jQuery);  

function popupDetail_${portletConfigId}(apprId, instanceId, insLogId) {
	var top; 
	var left; 
	var width = 850;
	var height = 700;
	
	left = (screen.width - width) / 2; 
	top = (screen.height - height) / 2;		
	var param = "apprId="+apprId+"&instanceId="+instanceId+"&insLogId="+insLogId+"&linkType=mywork";
	var contextUrl	= "<c:url value='/wfapproval/work/apdoc/readApDoc.do'/>?"+param;
	window.open(contextUrl, "", "top=" + top + ", left=" + left +", width="+ width + ", height="+height+", resizable=yes,scrollbars=yes");
}
//-->
</script>

<div id="${portletConfigId}">
	 <%-- 메시지 관련 Prefix 선언 End --%>
	<div class="portletUnit_c">
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="listInfo">
				<select id="pagePerRecord_${portletConfigId}" title='<ikep4j:message pre='${preWorkplaceCommon}' key='${status.expression}' />'>
					<c:forEach var="code" items="${workplaceCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq searchCondition.pagePerRecord}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
				</select> 
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
	
		<!--blockListTable Start-->
		<div class="blockListTable msgTable" style="margin-bottom:0px;">
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
						<th scope="col"><ikep4j:message pre='${preWorkplace}' key='number' /></th>
						<th scope="col">
							<ikep4j:message pre='${preSearch}' key='apprTypeCd' />
						</th>
						<th scope="col">
							<ikep4j:message pre='${preSearch}' key='apprTitle' />
						</th>
						<c:if test="${listType ne 'myRequestList'}">
						<th scope="col">
							<ikep4j:message pre='${preApCommList}' key='author' />
						</th>
						</c:if>
						<th scope="col">
							<ikep4j:message pre='${preSearch}' key='apprReqDate' />
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
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
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
									<td>${aplist.formTypeName}</td>
									<td class="textLeft">
										<a href="javascript:popupDetail_${portletConfigId}('${aplist.appKey01}','${aplist.instanceId}','${aplist.insLogId}')">${aplist.title}</a>
									</td>
							    	<c:choose>
							 			<c:when test="${listType ne 'myRequestList'}">
									    <c:choose>
										    <c:when test="${listType eq 'todoList'}">
										    	<td>${aplist.instanceAuthorName}</td>
										    	<td><ikep4j:timezone date="${aplist.createDate}" pattern="yyyy.MM.dd HH:mm"/></td>
										    </c:when>
										    <c:otherwise>
										    	<td>${aplist.userName}</td>							    
										    	<td><ikep4j:timezone date="${aplist.procStartDate}" pattern="yyyy.MM.dd HH:mm"/></td>
										    </c:otherwise>
									    </c:choose>
							 			</c:when>
							 			<c:otherwise>
									     <td><ikep4j:timezone date="${aplist.createDate}" pattern="yyyy.MM.dd HH:mm"/></td>
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
			<ikep4j:pagination searchButtonId="pageIndex_${portletConfigId}" pageIndexInput="pageIndex_${portletConfigId}" searchCondition="${searchCondition}" />
			<input  id="pageIndex_${portletConfigId}" type="hidden" value="${searchCondition.pageIndex}" />
			<!--//Page Numbur End-->			
		</div>
		<!--//blockListTable End-->	
	</div>
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
				height 		: 500,
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

</script>