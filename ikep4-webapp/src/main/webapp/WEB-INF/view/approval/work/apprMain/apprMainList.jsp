<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.apprMain.header" />
<c:set var="preForm"  	value="ui.approval.work.apprMain" />
<c:set var="preValidation" value="ui.approval.work.apprMain.validation" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
			 
<script type="text/javascript">
<!-- 

(function($) {
	
	getApprDetail = function(apprId, listType, entrustUserId) {
		if(entrustUserId == undefined || entrustUserId == '') {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
		}
		else {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType+"&entrustUserId="+entrustUserId;
		}
	};

    createApprDocForm = function(formId){
        $("#listForm input[name=formId]").val(formId);
		$("#listForm input[name=templateType]").val();
        $("#listForm").attr("action", "<c:url value="/approval/work/apprWorkDoc/createApprDocForm.do"/>");
        //$("#listForm").submit();
        document.listForm.submit();
		    
	};
	
	goFavoriteFormList = function(sortColumn){
		
		$("#listForm input[name=sortColumn]").val(sortColumn); 
		readFormList();
		if($("#formListDiv").css("display")=="none"){
		    $("#mainTabDiv").tabs({disabled:[0]});
		    $("#mainTabDiv").tabs({selected:[1]});
        }
		
	};	
	
	readFormList = function(){
		
	    $.ajax({
			url : "<c:url value="/approval/work/apprWorkForm/ajaxListApprForm.do"/>",
			type : "post",
			loadingElement : {container:"#formListDiv"},
			data : $("#listForm").serialize(),
			success : function(result) {
			    
				$('#formListDiv').html(result);
			    pageEvent();
			},
			error : function(xhr, exMessage) {
			
				var errorItems = $.parseJSON(xhr.responseText).exception;
				validator.showErrors(errorItems);
			}
		});		
	};
	
	addFavorite = function(t, formId){
		var mode = $(t).hasClass("select")?"remove":"add";
		
		$.post(iKEP.getContextRoot()+"/approval/work/apprWorkForm/ajaxSetApprFavorite.do", {"formId" : formId, "mode" : mode})
		.success(function(data) {
			if(mode=="remove"){
				$(t).removeClass("select");
			}else{
				$(t).addClass("select");
			}
		})
		.error(function(event, request, settings) { alert("error"); });	
	};
	
	pageEvent = function(){
	    $("#pageIndex").click(function() {
			readFormList();
		});
	};
	
	sort = function(sortColumn, sortType) { 
		$("#listForm input[name=actionType]").val("sort");
		$("#listForm input[name=sortColumn]").val(sortColumn); 
		$("#listForm input[name=sortType]").val(sortType == "ASC" ? "DESC" : "ASC");	 
		readFormList();
	}; 
	
	sortFavorite = function(t){
		if(t.checked){
			sort("FAVORITE_ID", "DESC");
		}else{
			sort("FORM_PARENT_ID", "DESC");
		}
	};
	
	checkKeyevent = function(event){
        if (event.keyCode == '13'){
            $("#searchbtn").click();
        }
    };

	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
	    $jq("#mainTabDiv").tabs({ disabled:[1]});
	     
	    // 메뉴선택
	    $("#apprMainOfLeft").click();
		
		$("#searchbtn").click(function() {
			readFormList();
			if($("#formListDiv").css("display")=="none"){
			    $("#mainTabDiv").tabs({disabled:[0]});
			    $("#mainTabDiv").tabs({selected:[1]});
	        }
			
		});
		
	});
})(jQuery);  
//-->
</script>

<h1 class="none">컨텐츠영역</h1>		

<!--search Start-->
<form id="listForm" name="listForm" method="post" onsubmit="return false;">	
<div class="qna_search">
	<div class="qna_appr">
		<h2><ikep4j:message pre="${preHeader}" key="searchForm" /></h2>
		<!-- <p>이곳에 설명이 나올 수 있습니다.</p> -->
		<div class="qna_search_form_1">
			<div class="qna_search_input_1">							
				<div class="qna_search_inputbox" >
					<table summary="<ikep4j:message pre="${preHeader}" key="searchForm" />" width="100%">
						<caption></caption>
						<tbody>
							<tr>
								<td width="*">
								    <spring:bind path="searchCondition.topFormParentId">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
								    <spring:bind path="searchCondition.formParentId">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
                                    <spring:bind path="searchCondition.formId">
                                    <input type="hidden" name="${status.expression}" value="" />
                                    </spring:bind>
                                    <spring:bind path="searchCondition.actionType">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
                                    <spring:bind path="searchCondition.sortColumn">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
                                    <spring:bind path="searchCondition.sortType">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
                                    <spring:bind path="searchCondition.templateType">
                                    <input type="hidden" name="${status.expression}" value="${status.value}" />
                                    </spring:bind>
								    <spring:bind path="searchCondition.searchWord">
									<input name="${status.expression}" title="<ikep4j:message pre="${preHeader}" key="searchForm" />" class="inputbox"  type="text" onkeypress="checkKeyevent(event);" />
									</spring:bind>
								</td>
							</tr>
						</tbody>
					</table>
				</div>								
				<span><a id="searchbtn" class="qna_btn_1" href="#a"><span><ikep4j:message pre="${preHeader}" key="search" /></span></a></span>	
			</div>							
		</div>

	</div>
</div>
<!--//search End-->	
<div id="mainTabDiv" class="iKEP_tab">
    <ul>
        <li><a href="#docListDiv">결재현황</a></li>
        <li title="검색을 해주세요"><a href="#formListDiv">검색결과</a></li>
    </ul>
    <div class="tab_con">		
        <div id="formListDiv"></div>			
        <!--pr_sub Start-->
        <div class="pr_sub" id="docListDiv">
        <div class="blockBlank_10px"></div>
        	<!--pr_bl Start-->
        	<div class="pr_bl">
        		
        		<!--결재할 문서-->
        		<div class="tableList_2 mb15">
        			<!--subTitle_1 Start-->
        			<div class="subTitle_1">
        				<h3><img src="<c:url value='/base/images/icon/ic_appr01.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="listApprTodo" /></h3>
        				<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprTodo.do"/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        			</div>
        			<!--//subTitle_1 End-->	
        			<table summary="<ikep4j:message pre="${preHeader}" key="listApprTodo" />">
        				<caption></caption>
        				<colgroup>
        					<col width="*"/>
        					<col width="60"/>
        					<col width="60"/>
        				</colgroup>
        				<tbody>
        					<c:if test="${!myTodoResult.emptyRecord}">
        						<c:forEach var="aplist" items="${myTodoResult.entity}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        							<tr>
        								<th class="ellipsis" width="*" scope="row">
        									<c:if test="${aplist.approvalId ne user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprTodo','${aplist.approvalId}');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
	        								<c:if test="${aplist.approvalId eq user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprTodo','');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
        								</th>
        								<td class="textCenter ellipsis" width="60" title="${aplist.registerName}"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
        								<td class="textRight" width="60" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>">
        									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.apprReqDate}"/></span>
        								</td>
        							</tr>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${myTodoResult.emptyRecord}">
        					<tr>
        						<td class="textCenter" colspan="3"><ikep4j:message pre="${preSearch}" key="emptyRecord" /></td>
        					</tr>				
        					</c:if>					
        								
        				</tbody>
        			</table>	
        		</div>					
        		<!--//결재할 문서-->
        		
        		<!--기안한 문서-->
        		<div class="tableList_2 mb15">
        			<!--subTitle_1 Start-->
        			<div class="subTitle_1">
        				<h3><img src="<c:url value='/base/images/icon/ic_appr02.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="myRequestList" /></h3>
        				<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprMyRequest.do"/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        			</div>
        			<!--//subTitle_1 End-->	
        			<table summary="<ikep4j:message pre="${preHeader}" key="myRequestList" />">
        				<caption></caption>
        				<colgroup>
        					<col width="*"/>
        					<col width="60"/>
        					<col width="60"/>
        				</colgroup>
        				<tbody>
        					<c:if test="${!myRequestResult.emptyRecord}">
        						<c:forEach var="aplist" items="${myRequestResult.entity}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        							<tr>
        								<th class="ellipsis" width="*" scope="row">
        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','myRequestList');">
        										${aplist.apprTitle}
        									</a>
        								</th>
        								<td class="textCenter" width="60">
        									<span class="name">
        										<c:if test="${aplist.apprDocStatus == '1'}">
        											<ikep4j:message pre="${preForm}" key="progress" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '2'}">
        											<ikep4j:message pre="${preForm}" key="complete" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '3'}">
        											<ikep4j:message pre="${preForm}" key="reject" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '4'}">
        											<ikep4j:message pre="${preForm}" key="return" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '5'}">
        											<ikep4j:message pre="${preForm}" key="error" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '6'}">
        											<ikep4j:message pre="${preForm}" key="wait" />
        										</c:if>
        									</span>
        								</td>
        								<td class="textRight" width="60" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>">
        									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.apprReqDate}"/></span>
        								</td>
        							</tr>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${myRequestResult.emptyRecord}">
        					<tr>
        						<td class="textCenter" colspan="3"><ikep4j:message pre="${preSearch}" key="emptyRecord" /></td>
        					</tr>				
        					</c:if>					
        								
        				</tbody>
        			</table>	
        		</div>					
        		<!--//기안한 문서-->
        		
        		<!--반려된 문서-->
        		<div class="tableList_2 mb15">
        			<!--subTitle_1 Start-->
        			<div class="subTitle_1">
        				<h3><img src="<c:url value='/base/images/icon/ic_appr03.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="rejectList" /></h3>
        				<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprReject.do"/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        			</div>
        			<!--//subTitle_1 End-->	
        			<table summary="<ikep4j:message pre="${preHeader}" key="rejectList" />">
        				<caption></caption>
        				<colgroup>
        					<col width="*"/>
        					<col width="60"/>
        					<col width="60"/>
        				</colgroup>
        				<tbody>
        					<c:if test="${!myRejectResult.emptyRecord}">
        						<c:forEach var="aplist" items="${myRejectResult.entity}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        							<tr>
        								<th class="ellipsis" width="*" scope="row">
        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','rejectList');">
        										${aplist.apprTitle}
        									</a>
        								</th>
        								<td class="textRight" width="60" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>">
        									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.apprReqDate}"/></span>
        								</td>
        							</tr>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${myRejectResult.emptyRecord}">
        					<tr>
        						<td class="textCenter" colspan="3"><ikep4j:message pre="${preSearch}" key="emptyRecord" /></td>
        					</tr>				
        					</c:if>					
        								
        				</tbody>
        			</table>	
        		</div>					
        		<!--//반려된 문서-->
        		
        		<!--부서수신함 문서-->
        		<div class="tableList_2 mb15">
        			<!--subTitle_1 Start-->
        			<div class="subTitle_1">
        				<h3><img src="<c:url value='/base/images/icon/ic_appr04.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="listApprDeptRec" /></h3>
        				<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprDeptRec.do"/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        			</div>
        			<!--//subTitle_1 End-->	
        			<table summary="<ikep4j:message pre="${preHeader}" key="listApprDeptRec" />">
        				<caption></caption>
        				<colgroup>
        					<col width="*"/>
        					<col width="60"/>
        					<col width="60"/>
        				</colgroup>
        				<tbody>
        					<c:if test="${!deptRecResult.emptyRecord}">
        						<c:forEach var="aplist" items="${deptRecResult.entity}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        							<tr>
        								<th class="ellipsis" width="*" scope="row">
        									<c:if test="${aplist.approvalId ne user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprDeptRec','${aplist.approvalId}');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
	        								<c:if test="${aplist.approvalId eq user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprDeptRec','');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
        								</th>
        								<td class="textCenter ellipsis" width="60" title="${aplist.registerName}"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
        								<td class="textRight" width="60" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>">
        									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.apprReqDate}"/></span>
        								</td>
        							</tr>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${deptRecResult.emptyRecord}">
        					<tr>
        						<td class="textCenter" colspan="3"><ikep4j:message pre="${preSearch}" key="emptyRecord" /></td>
        					</tr>				
        					</c:if>					
        								
        				</tbody>
        			</table>	
        		</div>					
        		<!--//부서수신함 문서-->
        		
        		<!--개인수신함 문서-->
        		<div class="tableList_2 mb15">
        			<!--subTitle_1 Start-->
        			<div class="subTitle_1">
        				<h3><img src="<c:url value='/base/images/icon/ic_appr04.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="listApprUserRec" /></h3>
        				<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprUserRec.do"/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        			</div>
        			<!--//subTitle_1 End-->	
        			<table summary="<ikep4j:message pre="${preHeader}" key="listApprUserRec" />">
        				<caption></caption>
        				<colgroup>
        					<col width="*"/>
        					<col width="60"/>
        					<col width="60"/>
        				</colgroup>
        				<tbody>
        					<c:if test="${!userRecResult.emptyRecord}">
        						<c:forEach var="aplist" items="${userRecResult.entity}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        							<tr>
        								<th class="ellipsis" width="*" scope="row">
        									<c:if test="${aplist.approvalId ne user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprUserRec','${aplist.approvalId}');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
	        								<c:if test="${aplist.approvalId eq user.userId}">
	        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprUserRec','');">
	        										${aplist.apprTitle}
	        									</a>
	        								</c:if>
        								</th>
        								<td class="textCenter ellipsis" width="60" title="${aplist.registerName}"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
        								<td class="textRight" width="60" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>">
        									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.apprReqDate}"/></span>
        								</td>
        							</tr>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${userRecResult.emptyRecord}">
        					<tr>
        						<td class="textCenter" colspan="3"><ikep4j:message pre="${preSearch}" key="emptyRecord" /></td>
        					</tr>				
        					</c:if>					
        								
        				</tbody>
        			</table>	
        		</div>					
        		<!--//개인수신함 문서-->
        		
        	</div>
        	<!--//pr_bl End-->
        	
        	<!--pr_br Start-->
        	<div class="pr_br">		
        		
        		<!--최근 기안한 양식-->				
        		<div class="pr_schedule">
        			<div class="pr_schedule_t">
        				<h3><img src="<c:url value='/base/images/icon/ic_recent.png'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="recentForm" /></h3>
        				<div class="clear"></div>
        			</div>
        			<div class="pr_schedule_c">
        				<ul class="mt0">	
        					
        					<c:if test="${!empty recentFormList}">
        						<c:forEach var="form" items="${recentFormList}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        								<li title="${form.formName}"><span><img src="<c:url value="/base/images/icon/ic_document.png"/>" alt="more" /></span> 
        									<a href="#a" onclick="createApprDocForm('${form.formId}');">${form.formName}</a>
        								</li>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${empty recentFormList}">
        						<li>&nbsp;<ikep4j:message pre="${preSearch}" key="emptyRecord" /></li>
        					</c:if>			
        								
        				</ul>
        			</div>
        		</div>					
        		<!--//최근 기안한 양식-->
        		
        		<!--즐겨찾기한 양식-->				
        		<div class="pr_schedule">
        			<div class="pr_schedule_t">
        				<h3><img src="<c:url value='/base/images/icon/ic_favorite.gif'/>" alt="approval icon" /><ikep4j:message pre="${preHeader}" key="favoriteForm" /></h3>
        				<div class="more"><a href="#a" onclick="goFavoriteFormList('FAVORITE_ID');"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
        				<div class="clear"></div>
        			</div>
        			<div class="pr_schedule_c">
        				<ul class="mt0">	
        					
        					<c:if test="${!empty favoriteFormList}">
        						<c:forEach var="form" items="${favoriteFormList}"  varStatus="status">
        							<c:if test="${status.count <= 5}">
        								<li title="${form.formName}"><span><img src="<c:url value="/base/images/icon/ic_document.png"/>" alt="more" /></span> 
        									<a href="#a" onclick="createApprDocForm('${form.formId}');">${form.formName}</a>
        								</li>
        							</c:if>
        						</c:forEach>
        					</c:if>
        					<c:if test="${empty favoriteFormList}">
        						<li>&nbsp;<ikep4j:message pre="${preSearch}" key="emptyRecord" /></li>
        					</c:if>			
        								
        				</ul>
        			</div>
        		</div>					
        		<!--//즐겨찾기한 양식-->
        
        	</div>
        	<!--//pr_br End-->					
        </div>
    </div>
</div>
</form>
<!--//pr_sub End-->			