<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!--
//입력체크




(function($) {
	
	var validOptionsProfileMain = { 
			rules : {
				fax : {
					numberHyphen : true,
					maxlength : 20
				},					
				officeBasicAddress : {
					maxlength : 100
				}
			},
			messages : {
				fax : {
					numberHyphen : "<ikep4j:message key='Tel.user.faxNo' />",
					maxlength : "<ikep4j:message key='Size.user.faxNo' />"
				},	
				officeBasicAddress : {
					maxlength : "<ikep4j:message key='Size.user.officeBasicAddress' />"
				}
			},
			notice : {
				fax : {
					message : "<ikep4j:message key='Size.user.faxNo' />"
				},					
				officeBasicAddress : {
					message : "<ikep4j:message key='Size.user.officeBasicAddress' />"
				}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/support/profile/updateProfile.do'/>",
				    data : $jq('form[name=profileMainForm]').serialize(),
				    type : "post",
				    success : function(result) {
						//getProfile();
				    	parent.parent.document.location.href = "<c:url value='/portal/main/portalMain.do?mainFrameUrl=/portal/main/listUserMain.do?rightFrameUrl=/support/profile/getProfile.do?targetUserId=${targetUserId}'/>" ;
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	$jq(document).ready(function() {
		validator = new iKEP.Validator("#profileMainForm", validOptionsProfileMain);	
		var tmpCurrentJob1 = "${profile.currentJob}";
		var tmpCurrnetJob2 = tmpCurrentJob1.split("<br>");
		for(i=0;i<tmpCurrnetJob2.length;i++){
			if(tmpCurrnetJob2[i] != ""){
				$jq("#currentJob"+(i+1)).val(tmpCurrnetJob2[i].substring(3,tmpCurrnetJob2[i].length));
			}
		}
	
		//iKEP.checkLength("#currentJob", updateCheckCurrentJob);		
		//$jq("#currentJob").focus();
		//iKEP.checkLength("#detailJob", updateCheckDetailJob);	
	});
})(jQuery);  


//-->
</script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:if test="${profile != null}">
<input name="userId" value="<c:out value='${profile.userId}'/>" type="hidden" />
<input name="updaterId" value="<c:out value='${profile.userId}'/>" type="hidden" />
<input name="updaterName" value="<c:out value='${profile.userName}'/>" type="hidden" />
<input id="officeBasicAddress" name="officeBasicAddress" type="hidden" value="<c:out value="${profile.officeBasicAddress}"/>" />
<input type="hidden" id="nationCode" name="nationCode" value="${profile.nationCode}" />
<input id="fax" name="fax"  type="hidden" value="<c:out value="${profile.fax}"/>" />
<div class="prInfo_team">
	<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${userTeamName}"/></c:when><c:otherwise><c:out value="${userTeamEnglishName}"/></c:otherwise></c:choose>
	<c:if test="${userOtherTeamName != ' '}">
	<br/><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${userOtherTeamName}</c:when><c:otherwise>${userOtherTeamEnglishName}</c:otherwise></c:choose>
	</c:if>
</div>
<table summary="<ikep4j:message pre='${preProfileMain}' key='profile.info'/>" >
	<caption></caption>
	<tbody>
		<tr >
			<th scope="row" style="width:15%;" ><ikep4j:message pre='${preProfileMain}' key='phone.title'/></th>
			<td style="width:85%;">
			
			<div><c:out value='${profile.mobile}'/> / <c:out value='${profile.officePhoneNo}'/></div></td>

		</tr>
		<tr>
			<th scope="row" ><ikep4j:message pre='${preProfileMain}' key='mailid.title'/></th>
			<td><div><c:out value='${profile.mail}'/></div></td>			
		</tr>
		<tr>
			<th scope="row" ></th>
			<td >
			<input type="hidden" id="preMailPassword" name="preMailPassword" class="inputbox" value="${profile.mailPassword}" />
			</td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='locale.title'/></th>
			<td><span style="display:inline;">
					<%-- select id="localeCode" name="localeCode">
					<c:if test="${localCodeList != null}">
						<c:forEach var="locList" items="${localCodeList}" varStatus="status">
							<option value="<c:out value='${locList.localeCode}'/>" <c:if test="${locList.localeCode == profile.localeCode}">selected="selected"</c:if> ><c:out value='${locList.localeName}'/></option>
						</c:forEach>
					</c:if>
					</select --%> 
					<select id="timezoneId" name="timezoneId">
					<c:if test="${timezoneList != null}">
						<c:forEach var="tzoneList" items="${timezoneList}" varStatus="status">
							<option value="<c:out value='${tzoneList.timezoneId}'/>" <c:if test="${tzoneList.timezoneId == profile.timezoneId}">selected="selected"</c:if>><c:out value='${tzoneList.timezoneName}'/></option>
						</c:forEach>
					</c:if>
					</select>
				</span>
			</td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='work.title'/></th>
			<td>
				<span >
				    <%-- <textarea id="currentJob" name="currentJob" title="<ikep4j:message pre='${preProfileMain}' key='currentJob.input'/>" cols="60%" rows="3" ><c:out value='${profile.currentJob}' /></textarea> --%>
					<input type="hidden" name="currentJob" id="currentJob" />
					1.<input type="text" name="currentJob1" id="currentJob1" class="inputbox w40" /><br>
					2.<input type="text" name="currentJob2" id="currentJob2" class="inputbox w40" /><br>
					3.<input type="text" name="currentJob3" id="currentJob3" class="inputbox w40" />
				</span>
			</td>
		</tr>			
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='work.details'/></th>
			<td>
				<span >
				    <textarea id="detailJob" name="detailJob" cols="60%" rows="8" ><c:out value='${profile.detailJob}' /></textarea>
					<!-- <span style="right:10px;"><span id="textLengthDJ" >0</span>/200</span> -->
				</span>
			</td>
		</tr>														
	</tbody>
</table>

</c:if>
<!--//pr_tl End-->			
<script>
        //document.getElementById("textLengthCJ").innerHTML = document.getElementById("currentJob").value.length;
        //document.getElementById("textLengthDJ").innerHTML = document.getElementById("detailJob").value.length;
</script>
