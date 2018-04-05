<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.socialpack.microblogging.poll.header" /> 
<c:set var="prList"     value="ui.socialpack.microblogging.poll.list" />
<c:set var="preDetail"  value="ui.socialpack.microblogging.poll.detail" />
<c:set var="preButton"  value="ui.socialpack.microblogging.poll.button" /> 
<c:set var="preMessage" value="ui.socialpack.microblogging.poll.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!--   

(function($) {
	
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$("#answerTitles").html('');
		
		$("#startDate").datepicker()
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
    	
    	$("#endDate").datepicker()
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$jq('#submitBtn').click( function() {
			
			selectedALL("answerTitles");
			
			if($jq("input[name=question]").val() == "") {
				alert('<ikep4j:message pre='${preMessage}' key='question' />');
				return;
			}
			
			if($jq("input[select=answerTitles]").children().size < 1) {
				alert('<ikep4j:message pre='${preMessage}' key='answerTitle' />');
				return;
			}

			var startDateVal 	= $("#startDate").val().replace('.','').replace('.','');
			var endDateVal 		= $("#endDate").val().replace('.','').replace('.','');
			
			if(startDateVal > endDateVal){
				alert('<ikep4j:message pre='${preMessage}' key='lateStartDate' />');
				return;
			}
			
			$jq("#pollForm").submit();
			
		});
		
		$jq("#cancelBtn").click(function() {

			$jq('form[name=pollForm]')[0].reset();
		});
		
		$jq("#addAnswer").click(function() {
			var $sel = $jq("select[name=answerTitles]");
			var $val = $jq("input[name=answerTitle]").val();
			
			if($val != "") {
				var $option = $('<option/>').appendTo($sel).attr('value', $val).html($val);
				$jq("input[name=answerTitle]").val("");
			}
		});
		
		$jq('#answerTitle').keypress( function(event) { 
			
			if(event.which == 13)
				$jq('#addAnswer').trigger("click");
		});
	
		$jq('#deleteAnswer').click(function(event) { 
			selectRemove("answerTitles");
		});
		
	});
	
	selectRemove = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");

		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$(this).remove();
				}
			});
		}
	};
	
	selectedALL = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
	
})(jQuery);  

//-->
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title">
					<h1><ikep4j:message pre='${preDetail}' key='title' /></h1>
					<a href="#a" onclick="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
							
					<!--blockDetail Start-->
					<div class="blockDetail">
					
						<form id="pollForm" method="post" action="<c:url value="/socialpack/microblogging/pollSave.do"/>" >
							
							<table summary="<ikep4j:message pre='${preDetail}' key='title' />">
							<caption></caption>	
					
							<tbody>
							
								<spring:bind path="poll.question">
								<tr> 
									<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
									<td>
										<input 
										id="${status.expression}" 
										name="${status.expression}" 
										type="text" 
										class="inputbox" 
										value="${status.value}" 
										size="56" 
										title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
										/>
									</td> 
								</tr>				
								</spring:bind>
								
								<spring:bind path="poll.answerTitle">
								<tr> 
									<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
									<td>
										<input 
										id="${status.expression}" 
										name="${status.expression}" 
										type="text" 
										class="inputbox" 
										value="${status.value}" 
										size="56" 
										title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
										/>
										<a class="button_s" href="#a" id="addAnswer"><span><img src="<c:url value="/base/images/icon/ic_btn_download.gif"/>" alt="" />
											<ikep4j:message pre='${preButton}' key='add' /></span></a>
										
										<div style="padding-top:4px;">
											<select name="answerTitles" id="answerTitles" size="8" multiple="multiple" class="multi w80" title="answerTitles" >
												<option/>
								             										
											</select>										
											<a class="button_s valign_bottom" href="#a"><span id="deleteAnswer"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />
												<ikep4j:message pre='${preButton}' key='delete' /></span></a>
										</div>
										
									</td> 
								</tr>				
								</spring:bind>	
							
								<tr> 
									<th scope="row"><ikep4j:message pre='${preDetail}' key='term' /></th>
									<td>
										<spring:bind path="poll.startDate">
											<input 
											type="text" 
											class="inputbox datepicker" 
											id="${status.expression}" 
											name="${status.expression}" 
											title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" 
											value="${status.value}" 
											size="9" /> 
											<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" /> 
										</spring:bind>		
										 ~ 					
										<spring:bind path="poll.endDate">
											<input 
											type="text" 
											class="inputbox datepicker" 
											id="${status.expression}" 
											name="${status.expression}" 
											title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" 
											value="${status.value}" 
											size="9" /> 
											<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" /> 
										</spring:bind>								
									</td> 
								</tr>		
																
								<spring:bind path="poll.chartType">
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
									<td>
										<input 
										name="${status.expression}" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='chartType.bar' />" 
										value="0" 
										checked="checked"
										/> <ikep4j:message pre='${preDetail}' key='chartType.bar' />
										
										<input 
										name="${status.expression}" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='chartType.pie' />" 
										value="1" 
										/> <ikep4j:message pre='${preDetail}' key='chartType.pie' />
										
										<input 
										name="${status.expression}" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='chartType.yesno' />" 
										value="2" 
										/> <ikep4j:message pre='${preDetail}' key='chartType.yesno' />
									</td>
								</tr>	
								</spring:bind>	
											
							</tbody>					
							
							</table>
						
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
							<li><a class="button" id="cancelBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
			       
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
		
		
		
		
	