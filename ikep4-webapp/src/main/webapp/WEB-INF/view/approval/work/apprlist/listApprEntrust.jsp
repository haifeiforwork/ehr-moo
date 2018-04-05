<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
<c:set var="preApCommList"    		value="ui.approval.work.apprlist" />
<c:set var="preForm"  				value="ui.approval.work.entrust.form" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprEntrust'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprEntrust.do" />
	</c:when>	
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
	
	//문서 결재 정보 조회
	getViewApprLine = function(apprId) {
		
		var $divContext = $jq("#"+apprId+"_${user.userId}"),
			$trRow = $jq("#apprLineTr"+apprId+"_${user.userId}");
		
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]").each(function() { 
				var rowApprid = $(this).find("div").attr("id");
				
				$jq("#"+rowApprid).empty();
				$jq("#apprLineTr"+rowApprid).css("display","block");
				$jq("#"+rowApprid).hide();
				$jq("#apprLineTr"+rowApprid).hide();
			});
			$.post("<c:url value='/approval/work/apprLine/listApprLineInfo.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$divContext.append(result);
					$trRow.css("display","");
					$divContext.show();
					$jq("#listTable").ajaxLoadComplete();
				})
				.error(function(event, request, settings) { alert("error");$jq("#listTable").ajaxLoadComplete(); });
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
	};
	
	//문서 상세 정보
	getApprDetail = function(apprId,entrustUserId,entrustId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm input[name=entrustUserId]:hidden").val(entrustUserId);
		$("#searchForm input[name=entrustId]:hidden").val(entrustId);
		$("#searchForm input[name=entrustType]:hidden").val('${entrustType}');
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
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
		
	});
})(jQuery);  
//-->
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->	
	
	<!--popup_contents Start-->
	<div id="popup_contents">

		<!--blockSearch Start-->
		<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
			<spring:bind path="searchCondition.apprId">
				<input name="${status.expression}" type="hidden" value="" title="hidden" />
			</spring:bind>
			<spring:bind path="searchCondition.listType">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
			</spring:bind>
			<spring:bind path="searchCondition.entrustUserId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
			</spring:bind>
			<spring:bind path="searchCondition.entrustType">
				<input name="${status.expression}" type="hidden" value="${entrustType}" title="hidden" />
			</spring:bind>
			<spring:bind path="searchCondition.entrustId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
			</spring:bind>
			<input name="apprIds" type="hidden" value="" title="hidden" />
			<input name="popupYn" type="hidden" value="true" title="hidden" />
			<spring:bind path="searchCondition.sortColumn">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 		
			<spring:bind path="searchCondition.sortType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
		
		<div class="blockSearch">
			<div class="corner_RoundBox03">
				<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />">
					<caption></caption>
					<tbody>
						<c:choose>
							<c:when test="${entrustType eq 'E'}">
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${preForm}' key='signUserName'/></th>
									<td>
										${entrust.signUserName}&nbsp;
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${preForm}' key='userName'/></th>
									<td>
										${entrust.userName}&nbsp;
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr>					
							<th scope="row" width="5%"><ikep4j:message pre='${preForm}' key='delegateperiod'/></th>
							<td>
								<ikep4j:timezone pattern="yyyy.MM.dd" date="${entrust.startDate}"/> ~ 
								<ikep4j:timezone pattern="yyyy.MM.dd" date="${entrust.endDate}"/>							
							</td>
						</tr>
						<tr>					
							<th scope="row" width="5%"><ikep4j:message pre='${preForm}' key='reason'/></th>
							<td>
								${entrust.reason}					
							</td>
						</tr>
					</tbody>
				</table>
				<div class="searchBtn">
				</div>
				
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>
			</div>
		</div>	
		<!--//blockSearch End-->
		
		<h1 class="none">
		  <ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />
		</h1>
		
		<!--blockListTable Start-->
		<div class="blockListTable">
		
			<!--tableTop Start-->
			<div class="tableTop">
			<div class="tableTop_bgR"></div> 
				<div class="listInfo">
					<spring:bind path="searchCondition.pagePerRecord">
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<c:forEach var="commonCode" items="${commonCode}">
							<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
						</c:forEach> 
					</select> 
					</spring:bind>
					<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
					<div align="right">
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
						<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
					</div>			
				</div>		
				<div class="clear"></div>
			</div>
			<!--//tableTop End-->
			<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
				<caption></caption>
				<colgroup>
				<col width="5%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="40%"/>
				<col width="10%"/>
				<col width="15%"/>
				<col width="10%"/>
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='number' /></th>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='apprDocType' /></th>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='codeName' /></th>
						<th scope="col">
							<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
							
						</th>
						<th scope="col"><ikep4j:message pre='${preSearch}' key='searchReqUserId' /></th>
						<th scope="col">
							<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
						</th>
						<th scope="col">
						    <ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />			
						</th>
					</tr>
				</thead> 
				<tbody>	
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
							</tr>
					    </c:when>
					    <c:otherwise>
							<c:forEach var="aplist" items="${searchResult.entity}" varStatus="i">
								<c:choose>
						 			<c:when test="${i.count % 2 == 0}">
						 				<c:set var="className" value="bgWhite"/>
						 			</c:when>
						 			<c:otherwise>
						 				<c:set var="className" value="bgSelected"/>
						 			</c:otherwise>
						 		</c:choose>  
								<tr class="${className}">
									<td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
									<td>
										<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
										<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
									</td>
									<td>${aplist.codeName}</td>
									<td class="textLeft">
										<div class="ellipsis">
											<c:choose>
												<c:when test="${entrustType eq 'E'}">
													<a href="#a" onclick="getApprDetail('${aplist.apprId}','${entrust.signUserId}','${aplist.entrustId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
												</c:when>
												<c:otherwise>
													<a href="#a" onclick="getApprDetail('${aplist.apprId}','${aplist.entrustUserId}','${aplist.entrustId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>		
												</c:otherwise>
											</c:choose>
										</div>
									</td>
									<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
									<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/></td>
									<td>
										<c:choose>
											<c:when test="${aplist.apprDocStatus == '1'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '2'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '3'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /></a>
											</c:when>
										</c:choose>	
									</td>
								</tr>
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
									<td colspan="7">
										<div id="${aplist.apprId}_${user.userId}" style="display:none;">
										</div>							
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
	
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->

</div>
<!--//popup End-->