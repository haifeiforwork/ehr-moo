<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prePopup"  value="message.collpack.kms.admin.winner.list" />
<c:set var="preMessage"  		value="message.collpack.kms.admin.winner.message" />

<script type="text/javascript" language="javascript">

(function($) {
	

	$jq(document).ready(function() { 
		
		if("${param.gubun}" == "Y"){			
			alert("<ikep4j:message key='message.collpack.kms.admin.permission.user.popup.myCnt.modify' />");
			parent.opener.location.reload();
		}
		
		//입력값 확인
		$("#updateWinnerForm").validate({ 
			submitHandler: function(form) {
                  form.submit();
                  return true;
            },
            rules : {
    			
    			mark : {
    				required : true,
    				min : 1,
    				number : true
    			},
    			    			
    			conversionMark : {
    				required : true,
    				min : 1,
    				number : true
    			}
    		},
    		
    		messages : {
    			
    			mark : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				number : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			
    			conversionMark : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				number : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			}
    		}
		}); 
		
		
		$("#applyBtn").click(function() {			
			$("#updateWinnerForm").submit(); 
			return false;
	    });
		
		
		$jq("#cancelBtn").click(function() {			
			iKEP.closePop();			
		});
		
	});
	
})(jQuery);
	
</script>

<!--popup Start-->
<form id="updateWinnerForm" method="post" onsubmit="return false" action="<c:url value='/collpack/kms/admin/winner/updateWinnerPopup.do' />">
<input type="hidden" name="winnerId" value="${adminWinner.winnerId}"/>
<input type="hidden" name="itemSeq" value="${adminWinner.itemSeq}"/>
<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1><ikep4j:message key='message.collpack.kms.admin.winner.modify.title' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message key='ui.support.popup.button.close' /></span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->		
	
	<!--popup_contents Start-->
	<div id="popup_contents_2">
	
		<!--blockDetail Start-->
	    <div class="blockDetail">
	        <table summary="new group">
	            <colgroup>
	                <col width="30%"/>
	                <col width="70%"/>
	            </colgroup>
	            <tbody>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="targetTime" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.winYear">
                              	<select title="${status.expression}" name="${status.expression}">
                               		<c:forEach var="year" begin="2012" end="${nowYear}" step="1">
                                       <option value="${year}"  <c:if test="${year eq status.value}">selected="selected"</c:if>>${year}</option>
                                   </c:forEach>
                                  </select>
                              </spring:bind>
                              
                              <spring:bind path="adminWinner.winGb">
                                  <select title="${status.expression}" name="${status.expression}">
                                   <c:forEach var="month" begin="1" end="12" step="1">
                                       <option value="${month}"  <c:if test="${month eq status.value}">selected="selected"</c:if>>${month}</option>
                                   </c:forEach>
                                  </select>
                              </spring:bind>
	                    </td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="sort" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.sort">
                          		<select title="${status.expression}" name="${status.expression}">
                          			<c:forEach var="index" begin="1" end="10" step="1">
                          				<option value="${index}" <c:if test="${index eq status.value}">selected="selected"</c:if> >${index}</option>
                          			</c:forEach>
                          		</select>
                          	</spring:bind>
	                    </td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="isMonth" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.isMonth">
                           		<select title=${status.expression} name="${status.expression}">
                                   <option value="0" <c:if test="${'0' eq status.value}">selected="selected"</c:if>><ikep4j:message key="message.collpack.kms.admin.winner.condition.month" /></option>
                                   <option value="1" <c:if test="${'1' eq status.value}">selected="selected"</c:if>><ikep4j:message key="message.collpack.kms.admin.winner.condition.quarter" /></option>
                                   <option value="2" <c:if test="${'2' eq status.value}">selected="selected"</c:if>><ikep4j:message key="message.collpack.kms.admin.winner.condition.year" /></option>
                               </select>
                            </spring:bind>
	                    </td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="name" /></th>
	                    <td>${adminWinner.userName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="id" /></th>
	                    <td>${adminWinner.winnerId}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="jobTitle" /></th>
	                    <td>${adminWinner.jobTitleName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="teamName" /></th>
	                    <td>${adminWinner.teamName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="workPlaceName" /></th>
	                    <td>${adminWinner.workPlaceName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="mark" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.mark">
                            	<input name="${status.expression}" id="${status.expression}" value="${status.value}" class="inputbox" type="text" size="10"  maxlength="4"/>
                            </spring:bind>
	                    </td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="conversionMark" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.conversionMark">
                             	<input id="${status.expression}" name="${status.expression}" value="${status.value}" class="inputbox" type="text" size="10" maxlength="4"/>
                            </spring:bind>
	                    </td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="regCnt" /></th>
	                    <td>
	                    	<spring:bind path="adminWinner.regCnt">
                             	<input id="${status.expression}" name="${status.expression}" value="${status.value}" class="inputbox" type="text" size="10" maxlength="4"/>
                            </spring:bind>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
	    <!--//blockDetail End-->	
		
		<!--blockButton Start-->
		<div class="blockButton"> 			
			<ul>
	            <li><a class="button" href="#a" id="applyBtn" name="applyBtn"><span><ikep4j:message key='ui.support.popup.button.modify' /></span></a></li>
	            <li><a class="button" href="#a" id="cancelBtn" name="cancelBtn"><span><ikep4j:message key='ui.support.popup.button.close' /></span></a></li>&nbsp;
	        </ul>
		</div>
		<!--//blockButton End-->
		<div class="blockBlank_5px"></div>	
	
	<!--//popup_contents End-->
</div>
<!--//popup End-->
</form>