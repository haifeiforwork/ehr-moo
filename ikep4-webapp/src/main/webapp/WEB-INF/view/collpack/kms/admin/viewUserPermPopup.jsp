<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.support.popup.user.header" /> 
<c:set var="preList"    value="ui.support.popup.user.list" />
<c:set var="preDetail"  value="ui.support.popup.user.detail" />
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="preMessage" value="ui.support.popup.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="prePopup"  value="message.collpack.kms.admin.permission.user.popup" />
 

<script type="text/javascript" language="javascript">

(function($) {
	
	periodControl = function(year, month){
		var tmpPeriodDiv1 = "";
		var tmpPeriodDiv2 = "";
		var tmpPeriodDiv3 = "";
		var tmpPeriodDiv4 = "";
		var tmpPeriodYear1 = "";
		var tmpPeriodYear2 = "";
		var tmpPeriodYear3 = "";
		var tmpPeriodYear4 = "";
		var tmpPeriodYear5 = "";
		var tmpPeriodMonth1 = "";
		
		if(year != "" && $("#sYear").val()==""){
			<c:forEach var="period" varStatus="pystatus" items="${periodList}">
				tmpPeriodDiv1 = "${period.period}";
				tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
				if(tmpPeriodYear1 != tmpPeriodYear2){
					tmpPeriodYear2 = tmpPeriodYear1;
					tmpPeriodDiv3 = tmpPeriodDiv3+"<option value=\""+tmpPeriodYear2+"\">"+tmpPeriodYear2+"</option>";
				}
				<c:if test="${pystatus.last}">
					tmpPeriodYear3 = tmpPeriodYear1;
				</c:if>
			</c:forEach>
			$("#periodYear").html(tmpPeriodDiv3);
			<c:forEach var="period" varStatus="pystatus" items="${periodList}">
				tmpPeriodDiv1 = "${period.period}";
				tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
				tmpPeriodYear3 = year;
				$("select[name=periodYear] option[value="+tmpPeriodYear3+"]").attr("selected",true);
			</c:forEach>
			<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
				tmpPeriodDiv2 = "${periodMonth.period}";
				tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
				if(tmpPeriodYear4 == tmpPeriodYear3){
					tmpPeriodMonth1 = tmpPeriodDiv2.substring(4,6);
					tmpPeriodDiv4 = tmpPeriodDiv4+"<option value=\""+tmpPeriodMonth1+"\">"+tmpPeriodMonth1+"</option>";
				}
			</c:forEach>
			$("#periodMonth").html(tmpPeriodDiv4);
			<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
				tmpPeriodDiv2 = "${periodMonth.period}";
				tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
				if(tmpPeriodYear4 == tmpPeriodYear3){
					tmpPeriodMonth1 = tmpPeriodDiv2.substring(4,6);
					<c:if test="${pmstatus.last}">
						$("select[name=periodMonth] option[value="+tmpPeriodMonth1+"]").attr("selected",true);
					</c:if>
				}
			</c:forEach>
			$("#sYear").val($("#periodYear option:selected").text());
			$("#sMonth").val($("#periodMonth option:selected").text());
			$("#updateUserOblCntForm").submit(); 
			return false; 
		}else{
			if($("#sYear").val() != ""){
				tmpPeriodDiv3 = "<select name=\"periodYear\" id=\"periodYear\">";
				<c:forEach var="period" varStatus="pystatus" items="${periodList}">
					tmpPeriodDiv1 = "${period.period}";
					tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
					if(tmpPeriodYear1 != tmpPeriodYear2){
						tmpPeriodYear2 = tmpPeriodYear1;
						tmpPeriodDiv3 = tmpPeriodDiv3+"<option value=\""+tmpPeriodYear2+"\">"+tmpPeriodYear2+"</option>";
					}
					<c:if test="${pystatus.last}">
						tmpPeriodYear3 = tmpPeriodYear1;
					</c:if>
				</c:forEach>
				tmpPeriodDiv3 = tmpPeriodDiv3+"</select>";
				$jq("#periodDiv1").append(tmpPeriodDiv3);
				<c:forEach var="period" varStatus="pystatus" items="${periodList}">
					tmpPeriodDiv1 = "${period.period}";
					tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
					if(tmpPeriodYear1 == $("#sYear").val()){
						tmpPeriodYear3 = tmpPeriodYear1;
						$("select[name=periodYear] option[value="+tmpPeriodYear3+"]").attr("selected",true);
					}
				</c:forEach>
				
				tmpPeriodDiv4 = "<select name=\"periodMonth\" id=\"periodMonth\">";
				<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
					tmpPeriodDiv2 = "${periodMonth.period}";
					tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
					if(tmpPeriodYear4 == tmpPeriodYear3){
						tmpPeriodMonth1 = tmpPeriodDiv2.substring(4,6);
						tmpPeriodDiv4 = tmpPeriodDiv4+"<option value=\""+tmpPeriodMonth1+"\">"+tmpPeriodMonth1+"</option>";
					}
				</c:forEach>
				tmpPeriodDiv4 = tmpPeriodDiv4+"</select>";
				$jq("#periodDiv2").append(tmpPeriodDiv4);
				<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
					tmpPeriodDiv2 = "${periodMonth.period}";
					tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
					if(tmpPeriodYear4 == tmpPeriodYear3){
						$("select[name=periodMonth] option[value="+$("#sMonth").val()+"]").attr("selected",true);
					}
				</c:forEach>
			}else{
				tmpPeriodDiv3 = "<select name=\"periodYear\" id=\"periodYear\">";
				<c:forEach var="period" varStatus="pystatus" items="${periodList}">
					tmpPeriodDiv1 = "${period.period}";
					tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
					if(tmpPeriodYear1 != tmpPeriodYear2){
						tmpPeriodYear2 = tmpPeriodYear1;
						tmpPeriodDiv3 = tmpPeriodDiv3+"<option value=\""+tmpPeriodYear2+"\">"+tmpPeriodYear2+"</option>";
					}
					<c:if test="${pystatus.last}">
						tmpPeriodYear3 = tmpPeriodYear1;
					</c:if>
				</c:forEach>
				tmpPeriodDiv3 = tmpPeriodDiv3+"</select>";
				$jq("#periodDiv1").append(tmpPeriodDiv3);
				<c:forEach var="period" varStatus="pystatus" items="${periodList}">
					tmpPeriodDiv1 = "${period.period}";
					tmpPeriodYear1 = tmpPeriodDiv1.substring(0,4);
					<c:if test="${pystatus.last}">
						tmpPeriodYear3 = tmpPeriodYear1;
						$("select[name=periodYear] option[value="+tmpPeriodYear3+"]").attr("selected",true);
					</c:if>
				</c:forEach>
				
				tmpPeriodDiv4 = "<select name=\"periodMonth\" id=\"periodMonth\">";
				<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
					tmpPeriodDiv2 = "${periodMonth.period}";
					tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
					if(tmpPeriodYear4 == tmpPeriodYear3){
						tmpPeriodMonth1 = tmpPeriodDiv2.substring(4,6);
						tmpPeriodDiv4 = tmpPeriodDiv4+"<option value=\""+tmpPeriodMonth1+"\">"+tmpPeriodMonth1+"</option>";
					}
				</c:forEach>
				tmpPeriodDiv4 = tmpPeriodDiv4+"</select>";
				$jq("#periodDiv2").append(tmpPeriodDiv4);
				<c:forEach var="periodMonth" varStatus="pmstatus" items="${periodList}">
					tmpPeriodDiv2 = "${periodMonth.period}";
					tmpPeriodYear4 = tmpPeriodDiv2.substring(0,4);
					if(tmpPeriodYear4 == tmpPeriodYear3){
						tmpPeriodMonth1 = tmpPeriodDiv2.substring(4,6);
						<c:if test="${pmstatus.last}">
							$("select[name=periodMonth] option[value="+tmpPeriodMonth1+"]").attr("selected",true);
						</c:if>
					}
				</c:forEach>
			}
			$("#sYear").val("");
			$("#sMonth").val("");
		}
	};

	$jq(document).ready(function() { 
		
		var gubun = $jq("input[name=gubun]").val();		
		if(gubun == "Y"){			
			alert('<ikep4j:message pre='${prePopup}' key='myCnt.modify' />');
			parent.opener.location.reload();
			iKEP.closePop();	
		}
		
		periodControl("${searchYear}","${searchMonth}");
		
		
		$("#updateUserPermCntForm select[name=periodYear]").change(function() { 
			periodControl($("#periodYear option:selected").text(),"");
		}); 
		
		$("#updateUserPermCntForm select[name=periodMonth]").change(function() { 
			$("#sYear").val($("#periodYear option:selected").text());
			$("#sMonth").val($("#periodMonth option:selected").text());
			$("#updateUserOblCntForm").submit(); 
			return false; 
			//periodControl($("#periodYear option:selected").text(),$("#periodMonth option:selected").text());
		}); 
				
		
		
		//입력값 확인
		$("#updateUserPermCntForm").validate({ 
			submitHandler: function(form) {                  
                  form.submit();
                  return true;
            },
            rules : {
    			myCnt : {
    				required : true,
    				min : 0,
    				number : true
    			}
    		},
    		
    		messages : {    			
    			
    			myCnt : {
    				required : "<ikep4j:message key='message.collpack.kms.admin.winner.message.digit' />",
    				min : "<ikep4j:message key='message.collpack.kms.admin.winner.message.digit' />",
    				number : "<ikep4j:message key='message.collpack.kms.admin.winner.message.digit' />"
    			}    			
    		}
		}); 
		
		$("#applyBtn").click(function() {		
			
			var nowCnt = ${adminPermission.obligationCnt};			
			var tobeCnt = $("#myCnt").val();
			
			var nowInfoGrade = "${adminPermission.infoGrade}";
			var tobeInfoGrade = $("#infoGrade option:selected").val();
			
			if( nowCnt == tobeCnt && nowInfoGrade == tobeInfoGrade ){
				alert('<ikep4j:message pre='${prePopup}' key='myCnt.alert' />');
				
			}else{				
				$("#searchYear").val($("#periodYear option:selected").text());
				$("#searchMonth").val($("#periodMonth option:selected").text());
				$("#updateUserPermCntForm").submit(); 
				return false; 
			}
		     
	    });
		
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();			
		});
		
	});
	
})(jQuery);
	
</script>

<form id="updateUserOblCntForm" method="post" action="<c:url value='/collpack/kms/admin/getUserPermission.do' />">
<input type="hidden" name="userId" value="${adminPermission.userId}"/>
<input type="hidden" name="sYear" id="sYear" value="${searchYear}"/>
<input type="hidden" name="sMonth" id="sMonth" value="${searchMonth}"/>
</form>

<!--popup Start-->
<form id="updateUserPermCntForm" method="post" action="<c:url value='/collpack/kms/admin/updateUserPermission.do' />">
<input type="hidden" name="userId" value="${adminPermission.userId}"/>
<input type="hidden" name="searchYear" id="searchYear" value=""/>
<input type="hidden" name="searchMonth" id="searchMonth" value=""/>
<input type="hidden" name="gubun" value="${gubun}"/>

<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1><ikep4j:message pre='${prePopup}' key='title' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
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
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="name" /></th>
	                    <td>${adminPermission.userName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="empNo" /></th>
	                    <td>${adminPermission.empNo}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="id" /></th>
	                    <td>${adminPermission.userId}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="workPlace" /></th>
	                    <td>${adminPermission.workPlaceName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="team" /></th>
	                    <td>${adminPermission.teamName}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="infoGrade" /></th>
	                    <td>
		                    
		                    <select name="infoGrade" id="infoGrade">
		                    	<option value="B" <c:if test="${adminPermission.infoGrade eq 'B'}">selected="selected"</c:if>><ikep4j:message pre="${prePopup}" key="auth4" /></option>
		                    	<option value="C" <c:if test="${adminPermission.infoGrade eq 'C'}">selected="selected"</c:if>><ikep4j:message pre="${prePopup}" key="auth5" /></option>		                    	
		                    </select>
		                    
	                    </td>
	                </tr>
	                <tr>
	                	<th><ikep4j:message pre="${prePopup}" key="period" /></th>
	                	<td>
	                		<span id="periodDiv1"></span>
	                    	<span id="periodDiv2"></span>
	                	</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="teamMonthCnt" /></th>
	                    <td>${adminPermission.teamObligationCnt}</td>
	                </tr>
	                <tr>
	                    <th scope="row"><ikep4j:message pre="${prePopup}" key="myMonthCnt" /></th>
	                    <td>
	                    	<input name="myCnt" id="myCnt" title="" class="inputbox" type="text" maxlength="2" size="2" value="${adminPermission.obligationCnt}"/>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
	    <!--//blockDetail End-->	
		
		<!--blockButton Start-->
		<div class="blockButton"> 			
			<ul>
	            <li><a class="button" href="#a" id="applyBtn" name="applyBtn"><span><ikep4j:message pre='${preButton}' key='modify' /></span></a></li>
	            <li><a class="button" href="#a" id="cancelBtn" name="cancelBtn"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>&nbsp;
	        </ul>
		</div>
		<!--//blockButton End-->
		<div class="blockBlank_5px"></div>	
	
	<!--//popup_contents End-->
</div>
<!--//popup End-->
</form>