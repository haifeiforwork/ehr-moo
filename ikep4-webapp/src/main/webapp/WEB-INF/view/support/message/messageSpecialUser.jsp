<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.message.messageSpecialUser.header" /> 
<c:set var="preList"    value="ui.support.message.messageSpecialUser.list" />
<c:set var="preSearch"  value="ui.support.message.searchCondition" /> 
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="prePop"  value="ui.support.message.messageSpecialUser.pop" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!-- 
	
	$jq(document).ready(function() { 
		$jq("#searchMessageListButton").click(function() {   
			$jq("#searchForm").submit(); 
			return false; 
		});  
		
		$jq("select[name=pagePerRecord]").change(function() {
            $jq("#searchMessageListButton").click();  
        }); 
		
		$jq("#checkboxAllMessageItem").click(function() { 
			$jq("input[name=checkboxMessageItem]").attr("checked", $jq(this).is(":checked"));  
		}); 
		
		var dialogPopLayer = $jq("#popLayer").dialog({width: 350, height:200, modal:true, resizable: false,autoOpen:false});
		//레이어 나오기
		popLayerOpen = function(user,info,filesize) {
			$jq('#userInfo').text('');
			$jq('#userId').val('');
			$jq('#searchUser').val('');
			$jq('#userFilesize').val('');
			$jq('#viewSearch').show();
			if (user != null && user != '') {
				$jq('#userInfo').text(info);
				$jq('#userId').val(user);
				$jq('#userFilesize').val(filesize);
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
				$jq('#userInfo').text(this.userName+" "+this.jobTitleName+" "+ this.teamName);
				$jq('#userId').val(this.id);
			});
		};
		
		$jq('#saveButton').click( function() {
			setId = $jq('#userId').val();
			setFilesize = $jq('#userFilesize').val();
			if ($jq('#userId').val() == "") {
				alert("<ikep4j:message pre='${preMsg}' key='valueUser'/>");
				return;
			}
			if ($jq('#userFilesize').val() == "") {
				alert("<ikep4j:message pre='${preMsg}' key='valueUserFilesize'/>");
				return;
			}
			
			$jq.get('<c:url value="/support/message/messageSpecialUserUpdate.do?userId='+setId+'&maxMonthFilesize='+setFilesize+'" />')
		    .success(function() { 
		    	alert("<ikep4j:message pre='${preMsg}' key='saveOk'/>");
		    	$jq("#searchForm").submit(); 
				return false; 
		    })    
			.error(function(event, request, settings) { alert("error"); }); 
		});
		
		$jq('#cancleButton').click( function() {
			$jq("#popLayer").dialog("close");
		});
		
		$jq("#detailTopButton a").click(function(){
			//선택된 항목이 없다면 리턴.
			if($jq("input[name=checkboxMessageItem]:checked").length == 0){
				return false;
			}
            if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
               	$jq('#searchForm').attr('action', '<c:url value="/support/message/deleteSpecialUserList.do"/>');
   				$jq('#searchForm').submit();
            }
        });
		
		$jq("input[name=userFilesize]")
        .css("ime-mode", "disabled") 
		.keypress(function() {
             if (event.keyCode < 48 || event.keyCode > 57) { event.returnValue = false; }
        });
		 
	});
//-->
</script>
<!--popup Start-->
<div id="popLayer" title="<ikep4j:message pre='${prePop}'  key='popTitle'/>">
	<div class="blockDetail">
		<table summary="Message">
			<caption></caption>
				<col style="width: 30%;"/>
				<col style="width: 70%;"/>
			<tbody>
				<tr>
					<td id="viewSearch" colspan="2">
						<div class="tableSearch">
							<span><ikep4j:message pre='${prePop}'  key='userSearch'/> : </span>
							<input id="searchUser" name="searchUser" type="text" class="inputbox" title="<ikep4j:message pre='${prePop}'  key='userTitle'/>"  value='' size="20" />
							<input id="userId" type="text" style="display: none;" /> 
							<a id="searchUserBtn" name="searchUserBtn" href="#a"  class="ic_search"><span>Search</span></a> 
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prePop}'  key='userTitle'/> </th>
					<td>
						<span id="userInfo"></span>
					</td>
				</tr>				
				<tr>
					<th scope="row"><ikep4j:message pre='${preList}' key='maxMonthFilesize' /></th>
					<td>
						<input id="userFilesize" type="text" class="inputbox" title="<ikep4j:message pre='${preList}' key='maxMonthFilesize' />" size="20" /> MB
					</td>
				</tr>							
			</tbody>
		</table>
	</div>
	<div class="blockButton" style="text-align:center"> 
		<a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
		<a id="cancleButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
	</div>
</div>
<!--popup End-->

		<!--blockListTable Start-->
		<form id="searchForm" method="post" action="<c:url value='/support/message/messageSpecialUser.do' />">  
	<div class="blockListTable msgTable" style="margin-bottom:0;">
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
			<div id="detailTopButton" class="tableTop_btn">
				<a id="deleteListBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</div>
			<div class="listInfo">
				<spring:bind path="searchCondition.pagePerRecord" >  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${messageCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			</div>					
	
			<div class="tableSearch">
				<ikep4j:message pre='${preSearch}' key='userName'/>
				<spring:bind path="searchCondition.userName">  					
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
				</spring:bind>
				<a id="searchMessageListButton" name="searchMessageListButton" href="#a" class="ic_search"><span>Search</span></a>
			</div>			
			<div class="clear"></div>
		</div>
	</div>
		<!--//tableTop End-->
		<div class="blockListTable msgTable">
			<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
				<caption></caption>
				<col style="width: 3%;"/>
				<col style="width: 20%;"/>
				<col style="width: 20%;"/>
				<col style="width: 20%;"/>
				<col style="width: *;"/>
				<col style="width: 20%;"/>
				<thead>
					<tr>
						<th scope="col"><input id="checkboxAllMessageItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='userName' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='jobPositionName' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='teamName' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='maxMonthFilesize' /></th>
						<th scope="col" class="tdLast"><ikep4j:message pre='${preList}' key='registDate' /></th>
						
					</tr>
				</thead>
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="messageSpecialUser" items="${searchResult.entity}" varStatus = "status"> 
							<tr>
								<td><input name="checkboxMessageItem" class="checkbox" title="checkbox" type="checkbox" value="${messageSpecialUser.userId}" /></td>
								<c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
								      <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userName}/${messageSpecialUser.jobPositionName}/${messageSpecialUser.teamName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.userName}</td>
									  <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userName}/${messageSpecialUser.jobPositionName}/${messageSpecialUser.teamName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.jobPositionName}</td>
									  <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userName}/${messageSpecialUser.jobPositionName}/${messageSpecialUser.teamName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.teamName}</td>
								  </c:when>
							      <c:otherwise>
							       	  <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userEnglishName}/${messageSpecialUser.jobPositionEnglishName}/${messageSpecialUser.teamEnglishName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.userEnglishName}</td>
							       	  <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userEnglishName}/${messageSpecialUser.jobPositionEnglishName}/${messageSpecialUser.teamEnglishName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.jobPositionEnglishName}</td>
									  <td style="cursor:hand;" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userEnglishName}/${messageSpecialUser.jobPositionEnglishName}/${messageSpecialUser.teamEnglishName}','${messageSpecialUser.maxMonthFilesize}');">${messageSpecialUser.teamEnglishName}</td>
							      </c:otherwise>
							    </c:choose>
							    
								<td class="textRight" style="cursor:hand;">
									<c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">
											<a href="#none" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userName}/${messageSpecialUser.jobPositionName}/${messageSpecialUser.teamName}','${messageSpecialUser.maxMonthFilesize}');" >
										</c:when>
							      		<c:otherwise>
							      			<a href="#none" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userEnglishName}/${messageSpecialUser.jobPositionEnglishName}/${messageSpecialUser.teamEnglishName}','${messageSpecialUser.maxMonthFilesize}');" >
							      		</c:otherwise>
							      	</c:choose>
									${messageSpecialUser.maxMonthFilesize}MB</a>
								</td>
								<td class="tdLast" style="cursor:hand;">
									<c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">
											<a href="#none" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userName}/${messageSpecialUser.jobPositionName}/${messageSpecialUser.teamName}','${messageSpecialUser.maxMonthFilesize}');" >
										</c:when>
							      		<c:otherwise>
							      			<a href="#none" onclick="popLayerOpen('${messageSpecialUser.userId}','${messageSpecialUser.userEnglishName}/${messageSpecialUser.jobPositionEnglishName}/${messageSpecialUser.teamEnglishName}','${messageSpecialUser.maxMonthFilesize}');" >
							      		</c:otherwise>
							      	</c:choose>
									<ikep4j:timezone date="${messageSpecialUser.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></a>
								</td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
			<!--Page Numbur Start-->
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchMessageListButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->			
		</div>
		<!--//blockListTable End-->	
		</form>
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#none" onclick="popLayerOpen();"><span><ikep4j:message pre='${preButton}' key='addUser'/></span></a></li>
			</ul>
		</div>
		<!--blockButton End-->
