<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="ui.support.smsConfig"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="preMessage" value="message.support.smsConfig"/>

<script type="text/javascript">
//<!--
(function($) {
	$jq(document).ready(function() { 
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
		
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=userIds]:not(checked)').attr("checked", "checked");
			}else{
		   		$jq('input[name=userIds]:checked').attr("checked", "");
		    }
	    });
		
		$jq("#removeSmsConfig").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='valueRemove'/>")) {
				var $sel = $jq("input[name=userIds]:checked");
				if($sel.length <1) {
					alert("<ikep4j:message pre='${preMessage}' key='valueCheckBox'/>");
					return;	
				}
				
				$jq.ajax({     
					url : '<c:url value="/support/smsConfig/removeSmsConfig.do" />',     
					data : $jq("#searchForm").serialize(),      
					type : "post",     
					success : function(result) {         
						alert("<ikep4j:message pre='${preMessage}' key='removeOk'/>");
						$jq("#searchForm")[0].submit(); 
					} 
				});
			}
	    });
		
		var dialogPopLayer = $jq("#popLayer").dialog({width: 350, height:200, modal:true, resizable: false,autoOpen:false});
		//레이어 나오기
		popLayerOpen = function(user,info,smsCount) {
			$jq('#userInfo').text('');
			$jq('#userId').val('');
			$jq('#smsCount').val('');
			$jq('#viewSearch').show();
			$jq('#searchUser').val('');
			if (user != null && user != '') {
				$jq('#userInfo').text(info);
				$jq('#userId').val(user);
				$jq('#smsCount').val(smsCount);
				$jq('#viewSearch').hide();
			}
			dialogPopLayer.dialog( "open" );
		};
		
		$jq('#searchUser').keypress( function(event) {
		    //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);
		});
		
		$jq('#searchUserBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchUser').trigger("keypress");
		});
		
		setUser = function(data) {
			$jq.each(data, function() {
				var userId = jQuery.trim(this.id);
				$jq('#userInfo').text(this.userName+"/"+this.jobTitleName+"/"+ this.teamName);
				$jq('#userId').val(userId);
			});
		};
		
		$jq('#saveButton').click( function() {
			var userId = $jq('#userId').val();
			var smsCount = $jq('#smsCount').val();
			
			if (userId == "") {
				alert("<ikep4j:message pre='${preMessage}' key='valueUser'/>");
				return;
			}
			
			if (smsCount == "") {
				alert("<ikep4j:message pre='${preMessage}' key='valueSmsCount'/>");
				return;
			}
			
			if(smsCount.indexOf(" ") > -1) {
				alert("<ikep4j:message pre='${preMessage}' key='valueSmsCountNoSpace'/>");
				return false;
			}
			
			if(isNaN(smsCount)) {
				alert("<ikep4j:message pre='${preMessage}' key='valueSmsCountOnlyNumber'/>");
				return false;
			}
			
			$jq.ajax({     
				url : '<c:url value="/support/smsConfig/createSmsConfig.do" />',     
				data : {userId:userId, smsCount:smsCount},      
				type : "post",     
				success : function(result) {         
					alert("<ikep4j:message pre='${preMessage}' key='saveOk'/>");
					$jq("#searchForm")[0].submit(); 
				} 
			}); 
		});
		
		$jq('#cancleButton').click( function() {
			$jq("#popLayer").dialog("close");
		});
	});
	
	getList = function() {
		$("#searchForm")[0].submit();
	};
})(jQuery);
//-->
</script>
<div id="popLayer" title="<ikep4j:message pre='${prefix}'  key='popLayerTitle'/>">
	<div class="blockDetail">
		<table summary="Message">
			<caption></caption>
				<col style="width: 30%;"/>
				<col style="width: 70%;"/>
			<tbody>
				<tr id="viewSearch">
					<th scope="row"><ikep4j:message pre='${prefix}'  key='userSearch'/> </th>
					<td>
						<input id="searchUser" name="searchUser" type="text" class="inputbox" value='' size="20" />
						<input id="userId" name="userId" type="hidden" />
						<a id="searchUserBtn" href="#a" class="ic_search valign_middle"><span><ikep4j:message pre='${prefix}' key='button.search'/></span></a>
					</td>
				</tr>
				<tr id="view">
					<th scope="row"><ikep4j:message pre='${prefix}'  key='userTitle'/> </th>
					<td>
						<span id="userInfo"></span>
					</td>
				</tr>				
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='smsCount' /></th>
					<td>
						<input id="smsCount" type="text" class="inputbox" size="20" /> <ikep4j:message pre='${prefix}' key='count' />
					</td>
				</tr>							
			</tbody>
		</table>
	</div>
	<div class="blockButton" style="text-align:center"> 
		<a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.save'/></span></a>
		<a id="cancleButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.cancle'/></span></a>
	</div>
</div>

<form id="searchForm" method="post" action="<c:url value='/support/smsConfig/listSmsConfig.do'/>" onsubmit="return false;">
<!--tableTop Start-->
<div class="tableTop" style="margin: 10px;">
	<div class="tableTop_bgR"></div>
	<!--  h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2-->
	<div class="listInfo">  
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
			<c:forEach var="code" items="${smsCode.pageNumList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>) &nbsp;&nbsp;<ikep4j:message pre='${prefix}' key='smsDefCount'/>: ${smsDefConfig.smsCount} <ikep4j:message pre='${prefix}' key='count'/></div>
	</div>	  
	<div class="tableSearch"> 
		<spring:bind path="searchCondition.searchColumn">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<option value="USER_NAME" <c:if test="${'USER_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='selectUserName'/></option>
			</select>	
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
		</spring:bind>
		<a class="ic_search" href="#a" onclick="javascript:getList();" ><span><ikep4j:message pre='${prefix}' key='button.search'/></span></a> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--splitterBox Start-->
<div id="splitterBox" style="margin: 0px 10px 0px 10px;">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">					
			<table summary="summary">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%">
							<input type="checkbox" name="checkedAll" id="checkedAll" />
						</th>
						<th scope="col" width="25%">
							<ikep4j:message pre='${prefix}' key='userName'/>
						</th>
						<th scope="col" width="25%">
							<ikep4j:message pre='${prefix}' key='jobTitleName'/>
						</th>
						<th scope="col" width="25%">
							<ikep4j:message pre='${prefix}' key='teamName'/>
						</th>
						<th scope="col" width="20%">
							<ikep4j:message pre='${prefix}' key='smsCount'/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="5" class="emptyRecord"><ikep4j:message pre='${prefix}' key='noSmsCountData'/></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="item" items="${searchResult.entity}" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" name="userIds" value="${item.userId}"/>
								</td>
								<td>
									<a href="#a" onclick="popLayerOpen('${item.userId}','${item.userName}/${item.jobTitleName}/${item.teamName}','${item.smsCount}');">
										${item.userName}
									</a>
								</td>
								<td>${item.jobTitleName}</td>
								<td>${item.teamName}</td>
								<td>${item.smsCount}</td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>
				</tbody>
			</table>
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->
		</div>
		<!--//blockListTable End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="removeSmsConfig" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.delete'/></span></a></li>
				<li><a class="button" href="#a" onclick="popLayerOpen('sms_def_count','<ikep4j:message pre='${prefix}' key='smsDefCount'/>','${smsDefConfig.smsCount}');"><span><ikep4j:message pre='${prefix}' key='smsDefCountModify'/></span></a></li>
				<li><a class="button" href="#a" onclick="popLayerOpen();"><span><ikep4j:message pre='${prefix}' key='button.addForm'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</div>
<!--//splitterBox End-->
</form>