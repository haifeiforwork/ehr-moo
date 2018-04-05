<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  		value="ui.socialpack.microblogging.groupMember.detail" />
<c:set var="preLabel"  			value="ui.socialpack.microblogging.groupMember.label" />
<c:set var="preButton"  		value="ui.socialpack.microblogging.button" />
<c:set var="preMessage"			value="ui.socialpack.microblogging.message" /> 
<c:set var="preGroupMemberMessage"	value="ui.socialpack.microblogging.groupMember.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
<!--   

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$("#memberId").html('');

		<c:if test="${not empty status }">
			alert('${status}');
			window.close();
		</c:if>
		
		// 팝업 띄울 때 넘겨줬던 parameter 로 셋팅.
		var param = iKEP.getPopupArgument();

		$jq('#mbgroupId').val(param.mbgroupId);
		$jq('#mbgroupName').html(param.mbgroupName);
		
		//주소록 버튼에 이벤트 바인딩
        $jq('#addrBtn').click( function() {
            var $select = $jq("select[name=memberId]");
            var items = $.map($select.children(), function(option) {
            	return $(option).data("data");
            });
			iKEP.showAddressBook(null, items, {selectElement:$select, selectType:"user"});
		});

		
		$jq('#submitBtn').click( function() { 
			
			selectedALL("memberId");
			
			if($jq("input[select=memberId]").children().size < 1) {
				alert('<ikep4j:message pre='${preGroupMemberMessage}' key='memberId' />');
				return;
			}
			
			$jq.post("<c:url value='/socialpack/microblogging/mbgroupMember/createMbgroupMembers.do'/>",
					$jq('form[name=mbgroupMemberForm]').originalSerialize(), 
					function(data) {
						alert(data);
						window.close();
			});
			
		});
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
			
		/*$jq('#deleteUser').click(function(event) { 
			selectRemove("memberId");
		});*/
		
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
			
				<form name="mbgroupMemberForm" id="mbgroupMemberForm" method="post" action="" onsubmit="return false;">
				<table >
					<tbody>
						<tr> 
							<td>
								<input id="mbgroupId" name="mbgroupId" type="hidden" />
																		
								<div style="padding-top:4px;">
									<select name="memberId" id="memberId" size="8" multiple="multiple" class="multi w80" >
           								<option/>		
									</select>			
									
									<a class="button_ic" href="#a">
										<span id="addrBtn"><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" /><ikep4j:message pre='ui.support.popup.button' key='address' /></span>
									</a>	
									<!-- a class="button_s valign_top" href="#a" >
										<span name="addrBtn" id="addrBtn"><ikep4j:message pre='ui.support.popup.button' key='address' /></span>
									</a>						
									< a class="button_s valign_bottom" href="#a">
										<span id="deleteUser"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />
										<ikep4j:message pre='${preButton}' key='delete' /></span>
									</a-->
								</div>
								
							</td> 
						</tr>	
						<tr>
							<td>		
								<div style="text-align:left;">
									<ikep4j:message pre='${preLabel}' key='inviteIntroA' />
										<span id="mbgroupName"></span>
									<ikep4j:message pre='${preLabel}' key='inviteIntroB' />
								</div>
							</td>
						</tr>			
					</tbody>					
					</table>
				</form>
					
			</div>
			<!--//blockDetail End-->
													
			<!--blockButton Start-->
			<div class="blockButton"> 
				<ul>
					<li><a class="button" id="submitBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='invite' /></span></a></li>
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

