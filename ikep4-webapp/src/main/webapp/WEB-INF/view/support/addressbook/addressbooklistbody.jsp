<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />
<c:set var="prePerList"  value="ui.support.addressbook.person.list" />
<c:set var="prePerDetail"  value="ui.support.addressbook.person.detail" />

<c:set var="preAddrInput"  value="ui.support.addressbook.addrgroup.input" /> 
<c:set var="preAddrButton"  value="ui.support.addressbook.addrgroup.button" /> 

<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="prePerMessage" value="message.support.addressbook.person" />


<script type="text/javascript">
<!--
(function($) {
	
	$jq(document).ready(function() {
		
		<c:if test="${groupType == 'P' && addrgroupId != 'NOGROUP' }">
			$jq('#userTypeIn').click(function(event) { 
				getPersonListByAddrView($jq('#searchForm').serialize());
			}); 
			
			$jq('#userTypeOut').click(function(event) { 
				getPersonListByAddrView($jq('#searchForm').serialize());
			}); 
		</c:if>
		
		$jq("select[name=pagePerRecord]").change(function() {
            //검색용 폼을 서브밋하는 코드 넣으시면 됩니다.
			getPersonListByAddrView($jq('#searchForm').serialize());
        });
		
		$jq('#searchKeyword').keyup( function(event) { 
            if (event.which == '13' || event.which === undefined) {
            	getPersonListByAddrView($jq('#searchForm').serialize());
            }
        });

	});
	
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />

			<form id="searchForm" action="" method="post" onsubmit="return false"> 
			<!--tableTop Start-->  
			<spring:bind path="personSearch.pageIndex"> 
				<input name="pageIndex" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<spring:bind path="personSearch.addrgroupId"> 
				<input name="addrgroupId" title="" type="hidden" value="${addrgroupId}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="personSearch.addrgroupName"> 
				<input name="addrgroupName" title="" type="hidden" value="${addrgroupName}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="personSearch.groupType"> 
				<input name="groupType" title="" type="hidden" value="${groupType}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="personSearch.indexSearchText"> 
				<input name="indexSearchText" title="" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="personSearch.indexSearchLocale"> 
				<input name="indexSearchLocale" title="" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>

			<div class="tableTop">  
				<div class="tableTop_bgR"></div>
				<div class="listInfo">  
					<spring:bind path="personSearch.pagePerRecord">  
						<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" >
						<c:forEach var="code" items="${boardCode.pageNumList}">
							<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
						</c:forEach> 
						</select> 
					</spring:bind>
					<div class="totalNum"><ikep4j:message pre='${prePerList}' key='totalCnt'/> <span>${personList.recordCount}</span></div>
				</div>	 
				<spring:bind path="personSearch.viewMode">
				<div class="listView_1"> 
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
					<div class="none"><ikep4j:message pre='${preSearch}' key='${status.expression}' post="webstandard"/></div>
					<ul> 
						<c:choose>
						    <c:when test="${status.value eq '0'}">       
						       <li><a name="viewModeTypeButton" onclick="changeViewMode('0');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.list.on' />" /></a></li>
						    </c:when>
						    <c:otherwise>
						      <li><a name="viewModeTypeButton" onclick="changeViewMode('0');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.list' />" /></a></li>
						    </c:otherwise> 
						</c:choose>			
						<c:choose>
						   <c:when test="${status.value eq '2'}">
						       <li><a name="viewModeTypeButton" onclick="changeViewMode('2');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.summary.on' />" /></a></li>
						    </c:when>
						    <c:otherwise>
						       <li><a name="viewModeTypeButton" onclick="changeViewMode('2');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.summary' />" /></a></li>
						    </c:otherwise> 
						</c:choose> 
					</ul> 
				</div>		
				</spring:bind>   
				<div class="tableSearch"> 
					
					<c:if test="${groupType == 'P' && addrgroupId != 'NOGROUP' }">
						<input type="checkbox" name="userTypeIn" id="userTypeIn" <c:if test="${personSearch.userTypeIn eq 'Y'}">checked="checked"</c:if>  value="Y"> 
						<ikep4j:message pre='${prePerDetail}' key='inCompany'/> 
						<input type="checkbox" name="userTypeOut" id="userTypeOut" <c:if test="${personSearch.userTypeOut eq 'Y'}">checked="checked"</c:if> value="Y"> 
						<ikep4j:message pre='${prePerDetail}' key='outCompany'/> 
						&nbsp;&nbsp;
					</c:if>
					
					<c:if test="${groupType != 'P' || addrgroupId == 'NOGROUP' }">
						<input type="hidden" name="userTypeIn" id="userTypeIn" <c:if test="${personSearch.userTypeIn eq 'Y'}">checked="checked"</c:if>  value="Y"> 
						<input type="hidden" name="userTypeOut" id="userTypeOut" <c:if test="${personSearch.userTypeOut eq 'Y'}">checked="checked"</c:if> value="Y"> 
					</c:if>
					
					<spring:bind path="personSearch.searchColumn">
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}'/>'>
							<option value="PNAME" <c:if test="${'PNAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prePerList}' key='personName'/></option>
							<option value="CNAME" <c:if test="${'CNAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prePerList}' key='companyName'/></option>
							<option value="HPNUM" <c:if test="${'HPNUM' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prePerList}' key='mobilePhoneno'/></option>
							<option value="OPNUM" <c:if test="${'OPNUM' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prePerList}' key='officePhoneno'/></option>
							<option value="MAILADR" <c:if test="${'MAILADR' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prePerList}' key='mailAddress'/></option>
							
						</select>	
					</spring:bind>		
					<spring:bind path="personSearch.searchKeyword">  					
						<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preAddrInput}' key='${status.expression}'/>'  size="20" /> 
					</spring:bind>
					<a class="ic_search" href="#a" id="searchBoardItemButton" name="searchBoardItemButton" onclick="javascript:getPersonListByAddrView($jq('#searchForm').serialize());">
						<span><ikep4j:message pre='${preAddrButton}' key='search'/></span>
					</a>
				</div>		
				<div class="clear"></div>	
			</div>
			</form>
			<!--//tableTop End-->

			<form id="personlistForm" action="" name="personlistForm" method="post" >
			<input name="addrgroupId" title="" type="hidden" value="${addrgroupId}" />
			<input name="groupType" title="" type="hidden" value="${groupType}" />
			
			<div class="namecard_wrap" >
			<ul>
			<c:choose>
				<c:when test="${personList.emptyRecord}">
					<li>
						<ikep4j:message pre='${preAddrMessage}' key='nodata'/>
					</li>			        
				</c:when>
				<c:otherwise>
					<c:forEach var="person" items="${personList.entity}" varStatus="status">
						<li>
							<div class="namecard_box">
								<div class="namecard_m">  
									<p class="namecard_title1">
										<c:if test="${groupType == 'T' or groupType == 'P' or (groupType == 'O' and publicManageFlag == 'true') }">
										<input name="personIdList" class="valign_middle" title="checkbox" type="checkbox" value="${person.personId}|${person.userType}" />
										</c:if>
										<b><a href="#a" onclick="Addressbook.editPerson('${addrgroupId}','${groupType}','${person.personId}');"><c:out value="${person.personName}"/></a></b> 
										<span class="namecard_title2">| 
											<c:choose>
												<c:when test="${person.userType == 'I'}">
													<ikep4j:message pre='${prePerDetail}' key='inCompany'/>
												</c:when>
												<c:otherwise>
													<ikep4j:message pre='${prePerDetail}' key='outCompany'/>
												</c:otherwise>
											</c:choose>
										</span>
										<span class="namecard_mail"><a href="#a"><c:out value="${person.mailAddress}"/></a></span>
									</p>
									<ul class="namecard_list">
										<li class="ellipsis"><c:out value="${person.companyName}"/> / <c:out value="${person.teamName}"/> / <c:out value="${person.jobRankName}"/></li>
										<li class="ellipsis"><b><ikep4j:message pre='${prePerList}' key='officePhoneno'/></b>&nbsp;&nbsp;<c:out value='${person.officePhoneno}'/>&nbsp;/&nbsp;<b><ikep4j:message pre='${prePerList}' key='mobilePhoneno'/></b>&nbsp;&nbsp;<c:out value='${person.mobilePhoneno}'/></li>
									</ul>
									<p class="namecard_memo ellipsis" title="<c:out value='${person.personMemo}'/>">
										<c:choose>
											<c:when test="${empty person.personMemo}">&nbsp;</c:when>
											<c:otherwise><c:out value="${person.personMemo}"/></c:otherwise>
										</c:choose>
									</p>
								</div>	
							</div>	
						</li>
					</c:forEach>
				</c:otherwise>
			</c:choose>																																																							
			</ul>
			</div>
			</form>	
			<!--//namecard End-->	
			<div class="border_t1"></div>
			
			<!--Page Numbur Start--> 
			<spring:bind path="personSearch.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getPersonListByAddrView" pageIndexInput="${status.expression}" searchCondition="${personSearch}" />
			 </spring:bind> 
			<!--//Page Numbur End-->


				
				