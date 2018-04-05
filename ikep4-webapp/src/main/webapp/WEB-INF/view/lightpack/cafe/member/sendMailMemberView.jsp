<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.member.sendMailMember.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.member.sendMailMember.search" />
<c:set var="preDetail"  value="message.lightpack.cafe.member.sendMailMember.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.member.sendMailMember.button" />
<c:set var="preScript"  value="message.lightpack.cafe.member.sendMailMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>  

<script type="text/javascript">
<!-- 
var dialogWindow = null;
var fnCaller;


(function($) {
	fnCaller = function (params, dialog) {
		if(params) {
			if(params.items) {
				appendItem(params.items);
			}
			if(params.search) {
				$jq("#treesch").val(params.search);
			}
		}
		
		dialogWindow = dialog;
		//alert(dialog);
		$jq("#closeButton").click(function() {
			dialogWindow.close();
		});
	};

	selectedAll = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
	
	var validOptions = {
		    rules : {
		    	title :    {
		            required : true,
		            maxlength : 500
		        },
		        content :    {
		            required : true
		        }
		    },
		    messages : {
		    	title : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.mail.title" />",
		            maxlength : "<ikep4j:message key="Size.mail.title" />"
		        },
		        content : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.mail.content" />"
		        }
		    },
		    submitHandler : function(form) {
		    	
		    	if(confirm('<ikep4j:message key="ui.support.mail.message.send" />')) {
		    		$jq("#mainForm")[0].submit();
					$("#pageLodingDiv").ajaxLoadStart();
		    	}
		    }
		 };

	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#mainForm", validOptions);
		
		$jq("#sendMailButton").click(function() {
			
			var $sel = $jq("select[name=emailList]");
			if($sel.children().length <1) {
				alert('<ikep4j:message pre="${preScript}" key="select" />');
				return;	
			}
			
			selectedAll("emailList");

			$("#mainForm").submit();
				
			return false; 
		});
		$jq("#listButton").click(function() {
			document.location.href="<c:url value="/lightpack/cafe/member/listCafeMemberView.do"/>?cafeId=${cafe.cafeId}&listType=Member";
			return false; 
		});			
		$jq("#initMemberButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
			return false; 
		});		
	   	$jq("#deleteBtn").click(function(event) {
			event.preventDefault();
			var $emailList=$jq('#emailList');
			$jq('option:selected',$emailList).remove();
		});

		$jq('#addressBtn').click( function() { 
			var items = [];
			$jq("select[name=emailList]").children().each(function() {
				items.push($(this).data("data"));
			});

			iKEP.showAddressBook(setAddress, items, {selectType:"USER", tabs:{group:0,common:0,personal:0,collaboration:1,sns:0}});
		});

	   
		iKEP.iFrameContentResize();
	});
	
	setAddress = function(data) {
		var $sel = $jq("select[name=emailList]")
			.children().remove()
			.end();
		$jq.each(data, function() {
			this.addrType = "TO";
			$.tmpl(iKEP.template.userEmailOption, this)
				.appendTo($sel)
				.data("data", this);
		})
	};
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">
	
<form id="editorFileUploadParameter" action=""> 
	<input name="cafeId"  value="${cafe.cafeId}" type="hidden"/>	
	<input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 

</div> 

<form id="mainForm" name="mainForm" method="post" action="<c:url value='/lightpack/cafe/member/sendMail.do' />">

<input name="cafeId"  value="${cafe.cafeId}" type="hidden"/>	

<!--blockDetail Start-->
<div class="blockDetail">
<table summary="<ikep4j:message pre="${preHeader}" key="pageTitle" />">
	<caption></caption>
	<tbody>
		<tr>
		<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='type' /></th>
		<td width="82%">
			<input 
			name="sendType" 
			type="radio" 
			class="radio" 
			title="<ikep4j:message pre='${preDetail}' key='type.mail' />" 
			value="mail" 
			checked="checked"
			/> <ikep4j:message pre='${preDetail}' key='type.mail' />
			
			<input 
			name="sendType" 
			type="radio" 
			class="radio" 
			title="<ikep4j:message pre='${preDetail}' key='type.message' />" 
			value="message" 
			/> <ikep4j:message pre='${preDetail}' key='type.message' />
		</td>
		</tr>
		
		<tr>
		<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='sender' /></th>
		<td width="82%">
		<span>
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${user.userName}
				</c:when>
				<c:otherwise>
					${user.userEnglishName}
				</c:otherwise>
			</c:choose>
		</span>
		</td>
		</tr>
		
		<tr> 
		<th scope="row"><ikep4j:message pre='${preDetail}' key='receiverList' /></th>
		<td>
						
			<div style="padding-top:4px;">
			<select name="emailList" id="emailList" size="6" multiple="multiple" class="multi w60" >
				<c:forEach var="memberList" items="${memberList}">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						<option value="TO:${memberList.memberName}:${memberList.mailId}:${memberList.memberId}">TO:${memberList.memberName}:(${memberList.mailId})</option>
					</c:when>
					<c:otherwise>
						<option value="TO:${memberList.memberEnglishName}:${memberList.mailId}:${memberList.memberId}">TO:${memberList.memberEnglishName}:(${memberList.mailId})</option>
					</c:otherwise>
					</c:choose>				
				</c:forEach>
			</select>	
			
			<a class="button_s valign_bottom" href="#"><span id="addressBtn"><ikep4j:message pre='${preButton}' key='address' /></span></a>										
			<!--a class="button_s valign_bottom" href="#a"><span id="updateBtn"><img src="<c:url value='/base/images/icon/ic_btn_enroll.gif'/>" alt="" />
			<ikep4j:message pre='${preButton}' key='modify' /></span></a-->
			<a class="button_s valign_bottom" href="#a"><span id="deleteBtn"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />
			<ikep4j:message pre='${preButton}' key='delete' /></span></a>
											
			</div>
		</td> 
		</tr>	

		<tr>
		<th scope="row"><ikep4j:message pre='${preDetail}' key='subject' /></th>
		<td>
		<input name="title" title="<ikep4j:message pre='${preDetail}' key='subject' />" class="inputbox w100" type="text" value="[${cafe.cafeName}]"/> 
		</td>
		</tr>

		<tr>  
		<td colspan="4">
		<textarea id="content"	name="content" class="inputbox w100 fullEditor" cols="" rows="20" title="<ikep4j:message pre='${preDetail}' key='contents' />"></textarea>
		</td> 
		</tr>						

	</tbody>
	</table>
</div>
<!--//blockDetail End-->
		
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="sendMailButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='send' /></span></a></li>
		<li><a id="listButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list' /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

</form>


</div>	
