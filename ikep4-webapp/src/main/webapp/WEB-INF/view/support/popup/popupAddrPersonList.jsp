<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.popup.addressbookheader" /> 
<c:set var="preList"    value="ui.support.addressbook.popup.addressbook.list" />
<c:set var="preDetail"  value="ui.support.popup.user.detail" />
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="preMessage" value="ui.support.popup.message" />
<c:set var="preSearch"  value="ui.support.addressbook.export.list.column" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">

(function($) {
	
	$jq(document).ready(function() { 
		
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=chk]:not(checked)').attr("checked", true);
			}else{
		   		$jq('input[name=chk]:checked').attr("checked", false);
		    }

	    });


		$jq('#personName').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		}); 
		
	});
	
})(jQuery); 
 
 </script>
 
					<form id="searchForm" method="post" action="" onsubmit="return false">  
									
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="listInfo"></div>
						<div class="tableSearch"> 	
							<spring:bind path="PersonSearch.personName">  					
								<ikep4j:message pre='${preSearch}' key='${status.expression}'/> : <input name="personName" id="personName" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>"  size="20" />
							</spring:bind>
 
						</div>		
						<div class="clear"></div>
						<div class="tableTop_bgR"></div>
					</div>
					<!--//tableTop End-->
					
					<table summary="연락처팝업">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%">
									<c:choose>
					    				<c:when test="${isMulti == 'Y'}">
					    					<input type="checkbox" name="checkedAll" id="checkedAll" />
										</c:when>
					    			</c:choose>
								</th>
								<th scope="col" width="15%">
									<ikep4j:message pre='${preList}' key='personName' />
								</th>
								<th scope="col" width="40%">
									<ikep4j:message pre='${preList}' key='companyName' />
								</th>
								<th scope="col" width="40%">
									<ikep4j:message pre='${preList}' key='jobRankName' />
								</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="person" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td>
											<c:choose>
							    				<c:when test="${isMulti == 'Y'}">
							    					<input type="checkbox" name="chk" value="${person.personId}"/>
												</c:when>
							    				<c:otherwise>
							    					<input type="radio" name="chk" value="${person.personId}"/>
							    				</c:otherwise>
							    			</c:choose>
							    			<input type="hidden" name="chk01" value="${person.personId}"/>
							    			<input type="hidden" name="chk02" value="${person.personName}"/>
							    			<input type="hidden" name="chk03" value="${person.jobRankName}"/>
							    			<input type="hidden" name="chk04" value="${person.teamName}"/>
							    			<input type="hidden" name="chk05" value="${person.companyName}"/>
							    			<input type="hidden" name="chk06" value="${person.mailAddress}"/>
							    			<input type="hidden" name="chk07" value="${person.mobilePhoneno}"/>
										</td>	
										<td>${person.personName}</td>			
										<td class="textLeft">${person.companyName}</td>
										<td class="textLeft">${person.jobRankName}</td>
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  																																																					
						</tbody>
					</table>	
					</form>		
				
				
