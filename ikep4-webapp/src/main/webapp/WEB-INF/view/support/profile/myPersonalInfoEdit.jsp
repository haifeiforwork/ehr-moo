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

(function($) {
	
	var validOptionsMyPersonalInfo = {
			rules : {
				userPassword : {
					required : true,
					maxlength : 20
				},
				birthday : {
					required : true,
					maxlength : 20
				},
				homePhoneNo : {
					numberHyphen : true,
					maxlength : 30
				},
				homeZipcode : {
					maxlength : 10
				},
				homeBasicAddress : {
					maxlength : 15
				},
				homeDetailAddress : {
					maxlength : 25
				}
			},
			messages : {
				userPassword : {
					required : "<ikep4j:message key='NotNull.user.userPassword' />",
					maxlength : "<ikep4j:message key='Size.user.userPassword' />"
				},
				birthday : {
					required : "<ikep4j:message key='NotNull.user.birthday' />",
					maxlength : "<ikep4j:message key='Size.user.birthday' />"
				},
				homePhoneNo : {
					numberHyphen : "<ikep4j:message key='Tel.user.homePhoneNo' />",
					maxlength : "<ikep4j:message key='Size.user.homePhoneNo' />"
				},
				homeZipcode : {
					numberHyphen : "<ikep4j:message key='ZipCode.user.homeZipcode' />",
					maxlength : "<ikep4j:message key='Size.user.homeZipcode' />"
				},
				homeBasicAddress : {
					maxlength : "<ikep4j:message key='Size.user.homeBasicAddress' />"
				},
				homeDetailAddress : {
					maxlength : "<ikep4j:message key='Size.user.homeDetailAddress' />"
				}
			},
			notice : {
				userPassword : {
					message : "<ikep4j:message key='Size.user.userPassword' />"
				},
				birthday : {
					message : "<ikep4j:message key='Size.user.birthday' />"
				},
				homePhoneNo : {
					message : "<ikep4j:message key='Size.user.homePhoneNo' />"
				},
				homeZipcode : {
					message : "<ikep4j:message key='Size.user.homeZipcode' />"
				},
				homeBasicAddress : {
					message : "<ikep4j:message key='Size.user.homeBasicAddress' />"
				},
				homeDetailAddress : {
					message : "<ikep4j:message key='Size.user.homeDetailAddress' />"
				}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/support/profile/updateMyPsInfo.do'/>",
				    data : $jq('form[name=myPsInfoForm]').serialize(),
				    type : "post",
				    success : function(result) {
						getMyPsInfo('cancel');
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	$jq(document).ready(function() {
		validator = new iKEP.Validator("#myPsInfoForm", validOptionsMyPersonalInfo);
	});
	
	// Calendar

	$("#birthday").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
	$("#weddingAnniv").datepicker({dateFormat: 'yy.mm.dd'}) .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

})(jQuery);  

//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:choose>
<c:when test="${myPsInfo != null && editAuthFlag=='true'}">
<input name="userId" value="<c:out value='${myPsInfo.userId}'/>" type="hidden" />
<input name="updaterId" value="<c:out value='${myPsInfo.userId}'/>" type="hidden" />
<input name="updaterName" value="<c:out value='${myPsInfo.userName}'/>" type="hidden" />

<table summary="<ikep4j:message pre='${preProfileMain}' key='myPsInfo.title'/>" >
	<caption></caption>
	<tbody>
		<tr>
			<th scope="row" ><span class="colorPoint">*</span> <ikep4j:message pre='${preProfileMain}' key='myPsInfo.password'/></th>
			<td colspan="3"><div><input id="userPassword" name="userPassword" type="password" maxlength="20" class="inputbox" title="password" value="" size="20" /></div></td>
		</tr>
		<tr>
			<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre='${preProfileMain}' key='myPsInfo.birthday'/></th>
			<td><div><input id="birthday" name="birthday"  readonly="readonly" type="text" maxlength="10" class="inputbox" title="birthday" value="<c:out value="${myPsInfo.birthday}"/>" size="20" /> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></div></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.weddingAnniv'/></th>
			<td><div><input id="weddingAnniv" name="weddingAnniv"  readonly="readonly" type="text" maxlength="10" class="inputbox" title="weddingAnniv" value="<c:out value="${myPsInfo.weddingAnniv}"/>" size="20" /> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></div></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homePhoneNo'/></th>
			<td><div><input id="homePhoneNo" name="homePhoneNo" type="text" maxlength="30" class="inputbox" title="homePhoneNo" value="<c:out value="${myPsInfo.homePhoneNo}"/>" size="20" /></div></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeZipcode'/></th>
			<td><div><input id="homeZipcode" name="homeZipcode" type="text" maxlength="15" class="inputbox" title="homeZipcode" value="<c:out value="${myPsInfo.homeZipcode}"/>" size="20" /></div></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeBasicAddress'/></th>
			<td><div><input id="homeBasicAddress" name="homeBasicAddress" type="text" maxlength="15" class="inputbox" title="homeBasicAddress" value="<c:out value="${myPsInfo.homeBasicAddress}"/>" size="20" /></div></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeDetailAddress'/></th>
			<td><div><input id="homeDetailAddress" name="homeDetailAddress" type="text" maxlength="25" class="inputbox" title="homeDetailAddress" value="<c:out value="${myPsInfo.homeDetailAddress}"/>" size="20" /></div></td>
		</tr>														
	</tbody>
</table>

</c:when>
<c:otherwise>
	<ikep4j:message pre='${preMsgProfile}' key='nodata'/>
</c:otherwise>
</c:choose>
<!--//My Personal Info Edit End-->		

