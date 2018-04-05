<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />
<c:set var="prePerList"  value="ui.support.addressbook.person.list" />
<c:set var="prePerDetail"  value="ui.support.addressbook.person.detail" />
<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
(function($) {
	
	var validator ;
	var validOptionsABPerson = { 
			rules : {
				personName : {
					required : true,
					maxlength : 15
				},
				companyName : {
					//required : true,
					maxlength : 20
				},
				teamName : {
					//required : true,
					maxlength : 20
				},
				jobRankName : {
					//required : true,
					maxlength : 20
				},
				mailAddress : {
					email : true,
					maxlength : 50
				},
				mobilePhoneno : {
					numberHyphen : true,
					maxlength : 20
				},
				officePhoneno : {
					numberHyphen : true,
					maxlength : 20
				},
				personMemo : {
					maxlength : 150
				}
			},
			messages : {
				personName : {
					required : "<ikep4j:message key='NotNull.person.personName'/>",
					maxlength : "<ikep4j:message key='Size.person.personName'/>"
				},
				companyName : {
					maxlength : "<ikep4j:message key='Size.person.companyName'/>"
				},
				teamName : {
					maxlength : "<ikep4j:message key='Size.person.teamName'/>"
				},
				jobRankName : {
					maxlength : "<ikep4j:message key='Size.person.jobRankName'/>"
				},
				mailAddress : {
					email : "<ikep4j:message key='Email.person.mailAddress'/>",
					maxlength : "<ikep4j:message key='Size.person.mailAddresse'/>"
				},
				mobilePhoneno : {
					numberHyphen : "<ikep4j:message key='Tel.person.mobilePhoneno'/>",
					maxlength : "<ikep4j:message key='Size.person.mobilePhoneno'/>"
				},
				officePhoneno : {
					numberHyphen : "<ikep4j:message key='Tel.person.officePhoneno'/>",
					maxlength : "<ikep4j:message key='Size.person.officePhoneno'/>"
				},
				personMemo : {
					maxlength : "<ikep4j:message key='Size.person.personMemo'/>"
				}
			},
			notice : {
				personName : {
					message : "<ikep4j:message key='Size.person.personName'/>"
				},
				companyName : {
					message : "<ikep4j:message key='Size.person.companyName'/>"
				},
				teamName : {
					message : "<ikep4j:message key='Size.person.teamName'/>"
				},
				jobRankName : {
					message : "<ikep4j:message key='Size.person.jobRankName'/>"
				},
				mailAddress : {
					message : "<ikep4j:message key='Size.person.mailAddresse'/>"
				},
				mobilePhoneno : {
					message : "<ikep4j:message key='Size.person.mobilePhoneno'/>"
				},
				officePhoneno : {
					message : "<ikep4j:message key='Size.person.officePhoneno'/>"
				},
				personMemo : {
					message : "<ikep4j:message key='Size.person.personMemo'/>"
				}
		    },
			submitHandler : function(form) {
				
				$jq.ajax({
				    url : "<c:url value='/support/addressbook/savePerson.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	alert('저장하였습니다');
				    	iKEP.closePop();
				    },
				    error : function(xhr, exMessage) {
				    	var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
				
			}				
	}	
	$jq(document).ready(function() {
		validator =  new iKEP.Validator("#personForm", validOptionsABPerson);
		
		$jq('#addrbook_person_save').click(function(){
			$("#personForm").trigger("submit");
		});	
		
		$jq("#cancelBtn").click(function() {
	            iKEP.closePop();            
	    });
		  
	});
})(jQuery);  
//-->
</script>

<div id="popup">
    <!--popup_title Start-->
      <div id="popup_title_2">
           <div class="popup_bgTitle_l"></div>
                    <h1><ikep4j:message pre='${prePerList}' key='personcreate.title'/></h1>
                    <a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
      </div>
    <!--//popup_title End-->  
    <!--popup_contents Start-->
      <div id="popup_contents">                  
             <form id="personForm" action="" name="personForm" method="post" onsubmit="return false" >                              
                <input name="personId" title="" type="hidden" value="<c:out value='${person.personId}'/>" />
                <input name="userType" title="" type="hidden" value="<c:out value='${person.userType}'/>" />
                 <!--blockDetail Start-->
                <div class="blockDetail">                   
                 <table summary="<ikep4j:message pre='${preSumMessage}' key='personInputTable'/>">
                       <colgroup>
                       <col width="15%"/>
                       <col width="35%"/>
                       <col width="15%"/>
                       <col width="35%"/>
                       </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row" >
                                          <span class="colorPoint">*</span><ikep4j:message pre='${prePerList}' key='personName'/></th>
                                    <td style=" vertical-align:middle">
                                        <div>
                                        <input name="personName" maxlength="15" title="<ikep4j:message pre='${prePerList}' key='personName'/>" class="inputbox w95" type="text" value="<c:out value='${person.personName}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> />                  
                                        </div>
                                    </td>                           
                                    <th scope="row" ><ikep4j:message pre='${prePerDetail}' key='addrgroupName'/></th>
                                    <td >
                                        <div>
                                        <select name="addrgroupIdList" class="multi w100" title="<ikep4j:message pre='${prePerDetail}' key='addrgroupName'/>" >
                                        <c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
                                            <option value="${addrgroup.addrgroupId}" <c:if test="${person.addrgroupId == addrgroup.addrgroupId}">selected="selected"</c:if>><c:out value='${addrgroup.addrgroupName}'/></option>
                                        </c:forEach>
                                            <option value="" <c:if test="${empty person.addrgroupId}">selected="selected"</c:if>><ikep4j:message pre='${prePrivate}' key='undiffer.title'/></option>
                                        </select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row"><ikep4j:message pre='${prePerList}' key='companyName'/></th>
                                    <td><div><input name="companyName" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='companyName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.companyName}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                    <th scope="row"><ikep4j:message pre='${prePerDetail}' key='teamName'/></th>
                                    <td><div><input name="teamName" maxlength="20" title="<ikep4j:message pre='${prePerDetail}' key='teamName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.teamName}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                </tr>
                                <tr>
                                    <th scope="row"><ikep4j:message pre='${prePerList}' key='jobRankName'/></th>
                                    <td><div><input name="jobRankName" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='jobRankName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.jobRankName}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                    <th scope="row"><ikep4j:message pre='${prePerList}' key='mailAddress'/></th>
                                    <td><div><input name="mailAddress" maxlength="50" title="<ikep4j:message pre='${prePerList}' key='mailAddress'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.mailAddress}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                </tr>
                                <tr>
                                    <th scope="row"><ikep4j:message pre='${prePerList}' key='mobilePhoneno'/></th>
                                    <td><div><input name="mobilePhoneno" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='mobilePhoneno'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.mobilePhoneno}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                    <th scope="row"><ikep4j:message pre='${prePerList}' key='officePhoneno'/></th>
                                    <td><div><input name="officePhoneno" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='officePhoneno'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.officePhoneno}'/>" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> /></div></td>
                                </tr>
                                <tr>
                                    <th scope="row"><ikep4j:message pre='${prePerDetail}' key='memo'/></th>
                                    <td colspan="3"><div><textarea name="personMemo" class="w100" title="<ikep4j:message pre='${prePerDetail}' key='memo'/>" cols="0" rows="3" <c:if test="${person.userType == 'I'}">readonly="readonly"</c:if> ><c:out value="${person.personMemo}"/></textarea></div></td>
                                </tr>
                            </tbody>
                        </table>
                
                </div>
                </form>
             <!--blockButton Start-->
             <div class="blockButton"> 
                <ul>
                    <li><a class="button" href="#" id="addrbook_person_save"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
                    <li><a class="button" href="#" id="cancelBtn"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
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