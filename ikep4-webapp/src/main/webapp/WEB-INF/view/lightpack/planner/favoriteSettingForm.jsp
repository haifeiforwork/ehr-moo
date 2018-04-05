<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<c:set var="preDetail"  value="ui.portal.excelupload.detail" />
<c:set var="preButton"  value="ui.portal.excelupload.button" /> 
<c:set var="preButton2"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preMessage" value="ui.portal.excelupload.message" />
<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript" language="javascript">

(function($) {
	
	
	$jq(document).ready(function() { 
		
		<c:if test="${sflg=='1'}">
			opener.location.reload();
			iKEP.closePop();
		</c:if>
		
		$("#temptext").hide();
		<c:if test="${flg=='2'}">
		$select = $("#targetGroupList");
		<c:forEach var="targetGroup" items="${schedule.targetGroup}">
			groupInfo = {
				type : "group",
				code : "${targetGroup.targetGroupId}",
				name : "${targetGroup.targetGroupName}",
				parent : ""
			};

			$.tmpl(iKEP.template.groupOption, groupInfo).appendTo($select)
				.data("data", groupInfo);
		</c:forEach>
		$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
		</c:if>
		<c:if test="${flg=='1'}">
		var $select = $("#targetList"), userInfo, groupInfo;
		<c:forEach var="targetUser" items="${schedule.targetUser}">
			userInfo = {
				type : "user",
				id : "${targetUser.targetGroupId}",
				empNo : "",
						userName : "${targetUser.targetGroupName}",
						jobTitleName : "${targetUser.jobTitleName}",
						teamName : "${targetUser.teamName}",
				email : "",
				mobile : "",
				group : ""
			};

			$.tmpl(iKEP.template.userOption, userInfo).appendTo($select)
				.data("data", userInfo);
		</c:forEach>
		$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
		</c:if>
		
		
		$jq('#submitBtn').click( function() { 
			<c:if test="${flg=='2'}">
			$("#favoriteForm").find('select[name=targetGroupList] option').each(function(){
				this.selected = true;
			});
	    	</c:if>
			<c:if test="${flg=='1'}">
	    	$("#favoriteForm").find('select[name=targetList] option').each(function(){
				this.selected = true;
			});
	    	</c:if>
	    	alert("저장되었습니다.");
			$jq("#favoriteForm").trigger("submit");
			
			
		});
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
		
		
		$jq("#btnAddrControl,#btnGroupAddrControl").click(function() {
			var $container = $(this).parent();
			var $select = $("select", $container);
			var isUser = $select.attr("id") == "targetList";

			var items = [];
			$jq.each($select.children(), function() {
				items.push($.data(this, "data"));
			});

			iKEP.showAddressBook(function(result){
				/*$select.empty();
				$jq.each(result, function() {
					$.tmpl(isUser ? iKEP.template.userOption : iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				})*/
				
				var msgTotal = "<ikep4j:message key='ui.servicepack.survey.common.total'/>" + $select.children().length + (isUser ? "<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg'/>" : "<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg'/>");
				$("span.totalNum_s", $container).text(msgTotal);
			}, items, {selectElement:$select, selectType:isUser ? "user" : "group",selectMaxCnt:2000, tabs:{common:0, personal:1, collaboration:0, sns:0}});
			});
	    
	    $jq('#userName').keypress( function(event) {
			iKEP.searchUser(event, "Y", setUser);
		});
		
		$jq('#userSearchBtn').click( function() {
			$jq('#userName').trigger("keypress");
		});
		
	    $jq('#groupName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroup); 
		});
		
		$jq('#groupSearchBtn').click( function() { 
			$jq('#groupName').trigger("keypress");
		});
		$jq("#btnDeleteControl,#btnGroupDeleteControl").click(function() {
			
			$jq("#targetGroupList option:selected").each(function () { 
	            $jq(this).remove();
	        }); 
			
			$jq("#targetList option:selected").each(function () { 
	            $jq(this).remove();
	        });  
			
			var selectTree = $jq(this).attr("id");
			
			if( selectTree == "btnDeleteControl" )
			{	
				var $sel = $jq("#favoriteForm").find("#targetList");
				//$jq("#totalMember").text($sel.children().length);
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			}
			else
			{	
				var $sel = $jq("#favoriteForm").find("#targetGroupList");
				//$jq("#totalGroup").text($sel.children().length);
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			}
		});
		
		setUser = function(data) {
			if(data=="")			{
				alert('검색대상이 없습니다');
			}
			else {		
				var $select = $jq("#targetList");
				var duplicationUsers = [];
				$jq.each(data, function() {
					var hasOption = $select.find('option[value="'+this.id+'"]');

					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}	
				})
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
				
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				
				$jq('#userName').val('');
			}
		};
		
		
		setGroup = function(data) {
			if(data=="") {
				alert('검색대상이 없습니다');
			} else {
				var $select = $jq("#targetGroupList");
				var duplicationGroups = [];
				$jq.each(data, function() {
					var hasOption = $select.find('option[value="'+this.code+'"]');

					if(hasOption.length > 0){
						duplicationGroups.push(this.name);
					} else {
						$.tmpl(iKEP.template.groupOption, this).appendTo($select)
							.data("data", this);
					}	
				})
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
				
				if(duplicationGroups.length > 0) alert("[" + duplicationGroups.join(",") + "] " + iKEPLang.searchUserGroup.duplicateGroup);
				
				$jq('#groupName').val('');
			}
		};
		
	});
	
	// 
	
})(jQuery);  


	
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1>즐겨찾기 등록</h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
					
						<form id="favoriteForm" method="post" action="<c:url value="/lightpack/planner/calendar/favoriteSetting.do"/>" >
							<input type="hidden" name="flg" value="${flg}"/>
							<input type="text" name="temptext" id="temptext" value=""/>
							<!--directive Start-->
							<!--//directive End-->
							
							<table>
							<caption></caption>
								<colgroup>
									<col width="15%"/>
									<col width="85%"/>
								</colgroup>
					
							<tbody>
							
								<tr>
									<c:if test="${flg=='1'}">
									<th>사용자</th>
									<td>
										<input 
											name="userName" 
											id="userName" 
											type="text" 
											class="inputbox"
											size="20" 
											 title=""
											/>
										<a name="userSearchBtn" id="userSearchBtn" class="button_ic" href="#a" ><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" />검색</span></a>
										<a id="btnAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
										<div style="padding-top:4px;"> 
											<select name="targetList" id="targetList"  size="5" multiple="multiple" class="multi w80" ></select>									
											<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="<ikep4j:message  key='ui.servicepack.survey.common.button.delete' />"/>Delete</span></a>
											<span id='totalMember' class='totalNum_s'></span>
										</div>
									</td>
									</c:if>
									<c:if test="${flg=='2'}">
									<th>부서</th>
									<td>
										<input 
											name="groupName" 
											id="groupName" 
											type="text" 
											class="inputbox"
											size="20" 
											title="groupName"
											/>
										<a name="groupSearchBtn" id="groupSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" />검색</span></a>
										<a id="btnGroupAddrControl" name="btnGroupAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
										<div style="padding-top:4px;">
										    <c:set var="targetGroupListCnt" value="0"/>
											<select name="targetGroupList" id="targetGroupList"   size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre='${preList}' key='targetGroupList' />"></select>	
											<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
											<span id='totalGroup'  class='totalNum_s'><ikep4j:message key='ui.servicepack.survey.common.totalGroup' arguments="${targetGroupListCnt}"/></span>
										</div>
									</td>
									</c:if>
								</tr>
											
							</tbody>					
							
							</table>
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span>확인</span></a></li>
							<li><a class="button" id="cancelBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
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
		
		
		
		
	