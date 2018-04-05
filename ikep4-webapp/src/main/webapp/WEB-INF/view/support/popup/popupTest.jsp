<%@ include file="/base/common/taglibs.jsp"%>


<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>




<script type="text/javascript" language="javascript">

(function($) {
	
	setAddress = function(data) {
		
		var addStr="";
		
		$jq.each(data, function() {
			addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
		})
		
		$jq("#addrDiv").html(addStr.substring(0,addStr.length-1));

	};
	
	
	setUser = function(data) {

		var str="";		
		$jq("#userDiv").children().remove();
		
		$jq(data).each(function(index) {
			//str = str + data[index].userId + " : " + data[index].userName + ", "
			$jq("#userDiv").append("<li>"+data[index].userName + " " + data[index].jobTitleName + " " + data[index].mobile +"</li>")
		});
		
		//$jq("#userDiv").html(str);

		
	};

	
	setGroup = function(data) {

		var str="";		
		$jq("#groupDiv").html("");
		
		$jq(data).each(function(index) {
			str = str + data[index].groupId + " : " + data[index].groupName + ", "
		});
		
		$jq("#groupDiv").html(str);

		
	};

	
	setGroupType = function(data) {

		var str="";		
		$jq("#groupTypeDiv").html("");
		
		$jq(data).each(function(index) {
			str = str + data[index].groupId + " : " + data[index].groupName + " : " + data[index].groupTypeName + ", "
		});
		
		$jq("#groupTypeDiv").html(str);

		
	};
	
	
	
	setRole = function(data) {

		var str="";
		$jq("#roleDiv").html("");
		
		$jq(data).each(function(index) {
			str = str + data[index].roleId + " : " + data[index].roleName + ", "
		});
		
		$jq("#roleDiv").html(str);

		
	};
	
	
	
	setJobClass = function(data) {

		var str="";
		$jq("#jobClassDiv").html("");
		
		$jq(data).each(function(index) {
			str = str + data[index].jobClassCode + " : " + data[index].jobClassName + ", "
		});
		
		$jq("#jobClassDiv").html(str);

		
	};
	
	
	
	setJobDuty = function(data) {

		var str="";
		$jq("#jobDutyDiv").html("");
		
		$jq(data).each(function(index) {
			str = str + data[index].jobDutyCode + " : " + data[index].jobDutyName + ", "
		});
		
		$jq("#jobDutyDiv").html(str);

		
	};

	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq('#addrBtn1').click( function() { 
			iKEP.showAddressBook(setAddress, "", {selectType:"user", isAppend:true, tabs:{common:1,personal:1,collaboration:1,sns:1}});
		});
		
		$jq('#addrBtn2').click( function() { 
			iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:true, tabs:{common:1,personal:1,collaboration:1,sns:1}});
		});
		
		$jq('#addrBtn3').click( function() { 
			iKEP.showAddressBook(setAddress, "", {selectType:"all", isAppend:true, tabs:{common:1,personal:1,collaboration:1,sns:1}});
		});
		
		$jq('#userId').keypress( function(event) { 
			iKEP.searchUser(event, "Y", setUser, "id"); 
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
		
		
		$jq('#groupTypeName').keypress( function(event) { 
			iKEP.searchGroupType(event, "Y", setGroupType); 
		});
		
		$jq('#groupTypeSearchBtn').click( function() { 
			$jq('#groupTypeName').trigger("keypress");
		});
		
		
		$jq('#roleName').keypress( function(event) { 
			iKEP.searchRole(event, "Y", setRole); 
		});
		
		$jq('#roleSearchBtn').click( function() { 
			$jq('#roleName').trigger("keypress");
		});
		
		
		$jq('#jobClassName').keypress( function(event) { 
			iKEP.searchJobClass(event, "Y", setJobClass); 
		});
		
		$jq('#jobClassSearchBtn').click( function() { 
			$jq('#jobClassName').trigger("keypress");
		});
		
		
		$jq('#jobDutyName').keypress( function(event) { 
			iKEP.searchJobDuty(event, "Y", setJobDuty); 
		});
		
		$jq('#jobDutySearchBtn').click( function() { 
			$jq('#jobDutyName').trigger("keypress");
		});
		
		
		
		
	});
	
})(jQuery); 
 
 </script>

<br></br> 
 
	<a class="button" href="#" >
		<span name="addrBtn1" id="addrBtn1"><ikep4j:message pre='ui.support.popup.button' key='address' />(USER)</span>
	</a>
	&nbsp;&nbsp;
	<a class="button" href="#" >
		<span name="addrBtn2" id="addrBtn2"><ikep4j:message pre='ui.support.popup.button' key='address' />(GROUP)</span>
	</a>
	&nbsp;&nbsp;
	<a class="button" href="#" >
		<span name="addrBtn3" id="addrBtn3"><ikep4j:message pre='ui.support.popup.button' key='address' />(ALL)</span>
	</a>
	
	<br/><span id="addrDiv"></span> 
	 
<br></br> 

	<ikep4j:message pre='ui.support.popup.user.header' key='title' /> : 
	<input type="text" name="userName" id="userName" value="" />  
	<a class="button" href="#" >
		<span name="userSearchBtn" id="userSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	</br> 
	
	User Id Search : <input type="text" name="userId" id="userId" value="" />  
	
	
	<div class="shuttle_r">
		<div class="sbox">
			<div class="shuttleCon" style="height:100px;">
				<ul id="userDiv" class="list-selectable"></ul>
			</div>
		</div>
	</div>		
	
<br></br> 

	<ikep4j:message pre='ui.support.popup.group.header' key='title' /> : 
	<input type="text" name="groupName" id="groupName" value=""/> 
	<a class="button" href="#" >
		<span name="groupSearchBtn" id="groupSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	
	<br/><span id="groupDiv"></span> 
	
<br></br> 

	<ikep4j:message pre='ui.support.popup.groupType.header' key='title' /> : 
	<input type="text" name="groupTypeName" id="groupTypeName" value=""/> 
	<a class="button" href="#" >
		<span name="groupTypeSearchBtn" id="groupTypeSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	
	<br/><span id="groupTypeDiv"></span> 
	
<br></br> 

	<ikep4j:message pre='ui.support.popup.role.header' key='title' /> : 
	<input type="text" name="roleName" id="roleName" value=""/>
	<a class="button" href="#" >
		<span name="roleSearchBtn" id="roleSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	
	<br/><span id="roleDiv"></span> 
	
<br></br> 

	<ikep4j:message pre='ui.support.popup.jobclass.header' key='title' /> : 
	<input type="text" name="jobClassName" id="jobClassName" value=""/>
	<a class="button" href="#" >
		<span name="jobClassSearchBtn" id="jobClassSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	
	<br/><span id="jobClassDiv"></span> 
	
<br></br> 

	<ikep4j:message pre='ui.support.popup.jobduty.header' key='title' /> : 
	<input type="text" name="jobDutyName" id="jobDutyName" value=""/>
	<a class="button" href="#" >
		<span name="jobDutySearchBtn" id="jobDutySearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span>
	</a>
	
	<br/><span id="jobDutyDiv"></span> 	


		

	