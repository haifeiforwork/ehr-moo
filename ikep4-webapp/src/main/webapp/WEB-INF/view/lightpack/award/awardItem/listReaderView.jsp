<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript" language="javascript">
//<!--

		
	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		$jq("#searchForm").attr("action", "<c:url value='listReaderView.do' />");
		$jq("#searchForm")[0].submit();
	}


	
	// 소팅을 위한 함수
	function sort(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	(function($) {
		
	
	
		$jq(document).ready(function() {
			
			// 백스페이스 방지(input, select 제외)
			$jq(document).keydown(function(e) {
				
				var element = e.target.nodeName.toLowerCase();
				
				if (element != 'input' && element != 'textarea') {
					
				    if (e.keyCode === 8) {
				    	
				        return false;
				    }
				}
			});
			$("#searchReaderButton").click(function() {   
				getList();
				return false; 
			});
		});
	})(jQuery);
//-->
</script>
<!--popup Start-->
<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1>조회현황</h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close'/></span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents_2" style="margin-top:30px;">




<div class="contentIframe">

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
				<spring:bind path="searchCondition.awardItemId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>  	
						
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="listInfo"> 
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${awardCode.pageNumList}">
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
								<option value="READER_NAME" <c:if test="${'READER_NAME' eq status.value}">selected="selected"</c:if>>독서자</option>
								<option value="READER_TEAM_NAME" <c:if test="${'READER_TEAM_NAME' eq status.value}">selected="selected"</c:if>>소속부서</option>
							</select>		
						</spring:bind>		
						<spring:bind path="searchCondition.searchWord">  					
							<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
						</spring:bind>
						<a id="searchReaderButton" name="searchReaderButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a>
					</div>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				
				<table>
					<caption></caption>
						<colgroup>
							<col width="10%"/>
							<col width="20%"/>
							<col width="20%"/>
							<col width="*"/>

						</colgroup>
					<thead>
						<tr>
						 	<th scope="col">&nbsp;</th>
							<th scope="col">
								<a onclick="javascript:sort('READER_NAME', '<c:if test="${searchCondition.sortColumn eq 'READER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									이름
								</a>
							</th>

							<th scope="col">
								<a onclick="javascript:sort('READER_TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'READER_TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									소속부서
								</a>
							</th>
						
							<th scope="col">
								<a onclick="javascript:sort('READ_DATE', '<c:if test="${searchCondition.sortColumn eq 'READ_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									조회 시각
								</a>
						     </th>

						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="4" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="reader" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td class="textCenter">
											${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}
							    		</td>
										<td class="textCenter" >
											${reader.readerName}
										</td>
										<td class="textCenter">
											${reader.readerTeamName}
										</td>
										<td class="textCenter">
											 <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${reader.readDate}"/>
										</td>
									</tr>
								</c:forEach>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<!--Page Number Start--> 
				<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchReaderButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> 
				<!--//Page Number End-->
				
	
			</form>
		</div>
	</div>
	
</div>
	<!--//popup_contents End-->
</div>
			
</div>
<!--//popup End-->