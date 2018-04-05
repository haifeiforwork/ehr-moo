<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listCategoryManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<c:set var="preSearch">ui.collpack.workmanual.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		//보기 모드 설정
		if('${manualSearchCondition.viewMode}' == 'A')
			$jq("#viewMode1>img").attr("src", "<c:url value='/base/images/icon/ic_view_list_on.gif'/>");
		else
			$jq("#viewMode2>img").attr("src", "<c:url value='/base/images/icon/ic_view_summary_on.gif'/>");
		
		//보기 모드 클릭
		$jq("#viewMode1").click(function() {
			$jq("#manualForm>input[name=viewMode]").val("A");
			$jq("#searchpagingButton").click();
		});
		$jq("#viewMode2").click(function() {
			$jq("#manualForm>input[name=viewMode]").val("B");
			$jq("#searchpagingButton").click();
		});

		//페이징 클릭
		$jq("#pagePerRecord").change(function() {
			$jq("#searchpagingButton").click();
        });
		
		//조회 버튼 클릭
		$jq("#searchpagingButton").click(function() {
			$jq("#manualForm").submit();
		});
		
	});

	goManual = function(manualId) {
		var readAuthority = "${readAuthority}";
		if(readAuthority == "Y") {
			location.href = "<c:url value='/collpack/workmanual/readManualView.do'/>" + "?manualId=" + manualId + "&pathStep=B";
		} else {
			alert("<ikep4j:message pre='${messagePrefix}' key='manual.readAuthority'/>");
		}
	}
	
})(jQuery);
//]]>
</script>

<div id="tagResult">
				<h1 class="none"></h1>
				
				<form id="manualForm" action="<c:url value='/collpack/workmanual/listCategoryManualView.do'/>" method="post">
					<spring:bind path="manualSearchCondition.categoryId">
						<input type="hidden" name="${status.expression}" value="${status.value}" />
					</spring:bind>
					<spring:bind path="manualSearchCondition.viewMode">
						<input type="hidden" name="${status.expression}" value="${status.value}" />
					</spring:bind>
				<!--tableTop Start-->
				<div class="tableTop">	
					<div class="tableTop_bgR"></div>	
					<h2>${manualCategory.categoryName}</h2>
					<div class="listInfo">
						<spring:bind path="manualSearchCondition.pagePerRecord">
						<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
								<option <c:if test="${manualSearchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 40}">selected="selected"</c:if> value="40">40</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
						</select>
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
					</div>	
					<div class="listView_1">							
						<div class="none"></div>
						<ul>
							<li><a href="#none" id="viewMode1"><img src="<c:url value="/base/images/icon/ic_view_list.gif"/>" alt="<ikep4j:message pre="${prefix}" key="search.viewMode1"/>" title="<ikep4j:message pre="${prefix}" key="search.viewMode1"/>" /></a></li>
							<li><a href="#none" id="viewMode2"><img src="<c:url value="/base/images/icon/ic_view_summary.gif"/>" alt="<ikep4j:message pre="${prefix}" key="search.viewMode2"/>" title="<ikep4j:message pre="${prefix}" key="search.viewMode2"/>" /></a></li>
						</ul>
					</div>	
					<div class="tableSearch">
						<spring:bind path="manualSearchCondition.searchType">
						<select title="<ikep4j:message pre="${prefix}" key="search.searchType"/>" name="${status.expression}">
							<option <c:if test="${status.value == 'A'}">selected="selected"</c:if> value="A"><ikep4j:message pre="${prefix}" key="search.searchType1"/></option>
							<option <c:if test="${status.value == 'B'}">selected="selected"</c:if> value="B"><ikep4j:message pre="${prefix}" key="search.searchType2"/></option>
						</select>													
						</spring:bind>
						<spring:bind path="manualSearchCondition.searchText">
						<input type="text" class="inputbox" title="<ikep4j:message pre="${prefix}" key="search.searchText"/>" name="${status.expression}" value="${status.value}" size="20" />
						</spring:bind>
						<a href="#a" id="searchpagingButton" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>
					</div>			
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->

				<!--blockListTable Start-->
				<c:if test="${manualSearchCondition.viewMode == 'A'}">
				<div class="blockListTable">
				</c:if>
				<c:if test="${manualSearchCondition.viewMode == 'B'}">
				<div class="blockListTable summaryView">
				</c:if>
				
					<c:if test="${manualSearchCondition.viewMode == 'A'}">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column1"/></th>
								<th scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
								<th scope="col" width="*"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
								<th scope="col" width="16%"><ikep4j:message pre="${prefix}" key="table.column6"/></th>
								<th scope="col" width="7%"><ikep4j:message pre="${prefix}" key="table.column7"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(searchResult.entity) == 0}">
									<tr><td colspan="7"><ikep4j:message pre="${prefix}" key="table.data.nothing"/></td></tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${searchResult.entity}">
										<tr>
											<td>${item.indexRowNum}</td>
											<td><c:if test="${item.attachCount > 0}"><img src="<c:url value="/base/images/icon/ic_attach.gif"/>" alt="<ikep4j:message pre='${buttonPrefix}' key='attachFile'/>" /></c:if></td>
											<td><c:if test="${item.manualType == 'A'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit'/></c:if>
												<c:if test="${item.manualType == 'B'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/></c:if>
												<c:if test="${item.manualType == 'C'}"><ikep4j:message pre='${messagePrefix}' key='manual.disuse'/></c:if>
											</td>
											<td class="textLeft" width="47%"><a href="#a" onclick="goManual('${item.manualId}')"><div class="ellipsis">${item.title}</div></a></td>
											<td><c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.updaterName}</c:when>
													<c:otherwise>${item.updaterEnglishName}</c:otherwise>
												</c:choose>
											</td>
											<td><ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd"/></td>
											<td class="tdLast">${item.version}</td>
										</tr>	
									</c:forEach>
								</c:otherwise>
							</c:choose>																																																																																													
						</tbody>
					</table>
					</c:if>
					<c:if test="${manualSearchCondition.viewMode == 'B'}">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>						
						<tbody>
							<c:choose>
								<c:when test="${fn:length(searchResult.entity) == 0}">
									<tr><td><ikep4j:message pre="${prefix}" key="table.data.nothing"/></td></tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${searchResult.entity}">
									<tr>
										<td>
											<div class="summaryViewTitle">
												<c:if test="${item.manualType == 'A'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>]</c:if>
												<c:if test="${item.manualType == 'B'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>]</c:if>
												<c:if test="${item.manualType == 'C'}">[<ikep4j:message pre='${messagePrefix}' key='manual.disuse'/>]</c:if>
												 &nbsp;<a href="#a" onclick="goManual('${item.manualId}')">${item.title}</a>&nbsp;(<ikep4j:message pre='${prefix}' key='table.version'/>:${item.version})
											</div>
											<div class="summaryViewInfo">
												<span class="summaryViewInfo_name">
													<c:choose>
														<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.updaterName}&nbsp;${item.jobTitleName}</c:when>
														<c:otherwise>${item.updaterEnglishName}&nbsp;${item.jobTitleEnglishName}</c:otherwise>
													</c:choose>
												</span>
												<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
												<span><ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd HH:mm"/></span>
												<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
												<span><ikep4j:message pre="${prefix}" key="table.search"/> <strong>${item.hitCount}</strong></span>
											</div>
											<div class="summaryViewCon">${item.contents}</div>
											<div class="summaryViewTag"><span class="ic_tag"><span><ikep4j:message pre="${prefix}" key="table.tag"/></span></span>  
												<c:forEach var="tag" items="${item.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
											</div>
										</td>
									</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>																																																		
						</tbody>
					</table>	
					</c:if>
					
					<!--Page Numbur Start-->
					<c:if test="${searchResult.recordCount > 0}">
						<spring:bind path="manualSearchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${manualSearchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
					</c:if>
					<!--//Page Numbur End-->			
				</div>
				<!--//blockListTable End-->
				</form>
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><c:if test="${writeUserYn == 'Y'}"><a class="button" href="<c:url value='/collpack/workmanual/createManualView.do?categoryId=${manualCategory.categoryId}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='new'/></span></a></c:if></li>
					</ul>
				</div>
				<!--//blockButton End-->
				
				<!--담당자,결재자 Start-->
				<div>
					<ul>
						<li>
							<span>
								<ikep4j:message pre="${prefix}" key="writeUser"/>:&nbsp;
								<c:forEach var="item" items="${writeUserList}" varStatus="status">  	
									<c:if test="${!status.first}">,&nbsp;</c:if>
									<c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.writeUserTeamName}&nbsp;${item.writeUserName}&nbsp;${item.writeUserJobTitleName}</c:when>
										<c:otherwise>${item.writeUserTeamEnglishName}&nbsp;${item.writeUserEnglishName}&nbsp;${item.writeUserJobTitleEnglishName}</c:otherwise>
									</c:choose>
								</c:forEach>
							</span>
						</li>
						<li>
							<span>
								<ikep4j:message pre="${prefix}" key="approvalUser"/>:&nbsp;
								<c:if test="${fn:length(approvalUserList) != 0}">
									<c:forEach var="item" items="${approvalUserList}" varStatus="status">
										<c:if test="${!status.first}">,&nbsp;</c:if>
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.approvalUserTeamName}&nbsp;${item.approvalUserName}&nbsp;${item.approvalUserJobTitleName}</c:when>
											<c:otherwise>${item.approvalUserTeamEnglishName}&nbsp;${item.approvalUserEnglishName}&nbsp;${item.approvalUserJobTitleEnglishName}</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(approvalUserList) == 0}">
									<span class="colorPoint"><ikep4j:message pre="${prefix}" key="approvalUserEmpty"/></span>
								</c:if>
							</span>
						</li>
					</ul>
				</div>
				<!--//담당자,결재자 End-->
</div>				