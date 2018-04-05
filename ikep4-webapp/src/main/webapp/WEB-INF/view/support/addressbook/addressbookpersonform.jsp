<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="prePerList"  value="ui.support.addressbook.person.list" />
<c:set var="prePerDetail"  value="ui.support.addressbook.person.detail" />
<c:set var="prePerButton"  value="ui.support.addressbook.person.button" /> 
<c:set var="prePerMessage" value="message.support.addressbook.person" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	// 목록 이동
    goPersonList = function(addrgroupId,groupType,addrgroupName) {
		
		if( groupType == 'P' ){
			
			if( addrgroupId == '' || addrgroupId === undefined ){
				addrgroupName = "<ikep4j:message pre='${prePrivate}' key='totalList.title'/>";
			}else{
				addrgroupName = addrgroupName;
			}
			Addressbook.getAddrHome(addrgroupId,groupType,addrgroupName);
		}else{
			
			if( addrgroupId == '' || addrgroupId === undefined ){
				addrgroupName = "<ikep4j:message pre='${prePublic}' key='title'/>";
			}else{
				addrgroupName = addrgroupName;
			}
			
			Addressbook.getAddrHome(addrgroupId,groupType,addrgroupName);
		}

    };
    
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
					//required : "<ikep4j:message key='NotNull.person.companyName'/>",
					maxlength : "<ikep4j:message key='Size.person.companyName'/>"
				},
				teamName : {
					//required : "<ikep4j:message key='NotNull.person.teamName'/>",
					maxlength : "<ikep4j:message key='Size.person.teamName'/>"
				},
				jobRankName : {
					//required : "<ikep4j:message key='NotNull.person.jobRankName'/>",
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
				    	
				    	<c:if test="${type == 'CREATE'}">
				    	var listAddrgroupId = $(form).find("[name=addrgroupIdList]").val();
			    		var listAddrgroupName = $(form).find("option:selected").text();
			    		</c:if>
			    		<c:if test="${type != 'CREATE'}">
			    		var listAddrgroupId = '${addrgroupId}';
			    		var listAddrgroupName = '${addrgroupName}';
						</c:if>	
						
			    		goPersonList(listAddrgroupId,'${groupType}',listAddrgroupName);
				    },
				    error : function(xhr, exMessage) {
				    	var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
				
			}				
	}
	
	$jq(document).ready(function() {
		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		// 화면 로딩시 각각 페이지 호출 종료
		validator =  new iKEP.Validator("#personForm", validOptionsABPerson);
		
		$jq('#addrbook_person_save').click(function(){
			$("#personForm").trigger("submit");
		});
		
	});

	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	// Person 저장
    savePerson = function(addrgroupId,groupType) {
		
		$("#personForm").trigger("submit");

    };
    
    // Person 연락처 삭제 
    deletePerson= function(addrgroupId,groupType,userName,addrgroupName,userType,personId) {
    	if(confirm('<ikep4j:message pre="${prePerMessage}" key="confirm.delete"/>')){
    		
    		$jq.ajax({
    		    url : '<c:url value="/support/addressbook/deletePerson.do"/>',
    		    data : {'userType':userType,'personId':personId},
    		    type : "get",
    		    success : function(result) {
    		    	goPersonList(addrgroupId,groupType,addrgroupName);
    		    }
    		}).error(function(event, request, settings) { alert("error"); });
    	}
	    
	};
	
})(jQuery);  
//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu">

				<h1 class="none"><ikep4j:message pre='${preHeader}' key='leftmenu'/></h1>
				<h2><a href="#a" onclick="goAddrbookMain()"><ikep4j:message pre='${preHeader}' key='english.title'/></a></h2>
				<div class="blockButton_2 mb10"> 
					<a class="button_2" onclick="Addressbook.editPerson('','P','');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_addressbook.gif'/>" alt="" /><ikep4j:message pre='${prePrivate}' key='newPerson.title'/></span></a>				
				</div>		
				<div class="left_fixed">
					<ul>
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='title'/></a>
							<ul class="boxList_child private_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('P')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('P');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('P');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>	
						</li>	
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='grouptitle'/></a>
							<ul class="boxList_child prip_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('G')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('G');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('G');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>
						</li>		
						<li class="liFirst no_child"><a href="#a" onclick="Addressbook.getAddrHome('${user.groupId}','T','${user.teamName}');">${user.teamName}<ikep4j:message pre='${prePrivate}' key='title'/></a></li>	
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='title'/></a>
							<ul class="boxList_child public_group">
								<li>&nbsp;</li>
							</ul>				
						</li>
						
						<c:if test="${publicManageFlag == 'true'}">
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='administrator.title'/></a>
							<ul>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.getAddrgroupList('O');"><ikep4j:message pre='${prePublic}' key='management.title'/></a></li>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.editCategory();">공용그룹 카테고리 관리</a></li>
							</ul>					
						</li>
						</c:if>											
					</ul>										
				</div>
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents" >
			
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<c:if test="${type == 'CREATE'}">
					<h2><ikep4j:message pre='${prePerList}' key='personcreate.title'/></h2>
					</c:if>
					<c:if test="${type != 'CREATE'}">
						<c:if test="${((groupType == 'P' and person.userType == 'O')or (groupType == 'O' and publicManageFlag == 'true')) }">
							<h2><ikep4j:message pre='${prePerList}' key='personedit.title'/></h2>
						</c:if>
						<c:if test="${!((groupType == 'P' and person.userType == 'O')or (groupType == 'O' and publicManageFlag == 'true')) }">
							<h2><ikep4j:message pre='${prePerList}' key='personview.title'/></h2>
						</c:if>
					</c:if>
				</div>
				<!--//subTitle_2 End-->
				
				<!--pageTitle Start-->
				<!-- 
				<div id="pageTitle">
				<h2><c:out value="${addrgroupName}"/></h2>

					<div id="pageLocation">
						<c:if test="${listNavi != null}">
						<ul>
							<c:forEach var="navi" items="${listNavi}" varStatus="status">
								<c:if test="${(status.index == 0) }">
								<li class="liFirst"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 < listNaviSize) }">
								<li><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 == listNaviSize) }">
								<li class="liLast"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
							</c:forEach>
						</ul>
						</c:if>
					</div>
				</div>
				-->
				<!--//pageTitle End-->
	
				<div id="listbody">
				<c:if test="${person != null}">
				<!--blockListTable Start-->
				<form id="personForm" action="" name="personForm" method="post" onsubmit="return false" >
				<input name="groupType" title="" type="hidden" value="<c:out value='${groupType}'/>" />           		
           		<input name="personId" title="" type="hidden" value="<c:out value='${person.personId}'/>" />
           		<input name="userType" title="" type="hidden" value="<c:out value='${person.userType}'/>" />
           		<!--blockDetail Start-->
           		
           		<!--blockDetail Start-->
				<div class="blockDetail">
					
					<!--Edit disabled -->
					<c:if test="${!(type == 'CREATE' or (groupType == 'P' and person.userType == 'O')or (groupType == 'O' and publicManageFlag == 'true')) }">
						<table summary="<ikep4j:message pre='${preSumMessage}' key='personInputTable'/>">
							<tbody>
								<tr>
									<th scope="row" ><span class="colorPoint">*</span><ikep4j:message pre='${prePerList}' key='personName'/></th>
									<td  colspan=3  style="vertical-align:middle">
										<c:out value='${person.personName}'/>
									</td>								
								</tr>
								<tr>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerList}' key='companyName'/></th>
									<td width="32%">
										<c:out value='${person.companyName}'/>
									</td>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerDetail}' key='teamName'/></th>
									<td width="32%">
										<c:out value='${person.teamName}'/>
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='jobRankName'/></th>
									<td>
										<c:out value='${person.jobRankName}'/>
									</td>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='mailAddress'/></th>
									<td>
										<c:out value='${person.mailAddress}'/>
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='mobilePhoneno'/></th>
									<td>
										<c:out value='${person.mobilePhoneno}'/>
									</td>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerList}' key='officePhoneno'/></th>
									<td>
										<c:out value='${person.officePhoneno}'/>
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerDetail}' key='memo'/></th>
									<td colspan="3">
										<c:out value="${person.personMemo}"/>
									</td>
								</tr>
							</tbody>
						</table>
					
					</c:if>
					
					<!--Editable -->
					<c:if test="${(type == 'CREATE' or (groupType == 'P' and person.userType == 'O')or (groupType == 'O' and publicManageFlag == 'true')) }">
						<table summary="<ikep4j:message pre='${preSumMessage}' key='personInputTable'/>">
							<tbody>
								<tr>
									<th scope="row" ><span class="colorPoint">*</span><ikep4j:message pre='${prePerList}' key='personName'/></th>
									<td <c:if test="${groupType != 'P' and person.userType != 'O'}"> colspan="3" </c:if>  style=" vertical-align:middle">
										<div>
										<input name="personName" maxlength="15" title="<ikep4j:message pre='${prePerList}' key='personName'/>" class="inputbox w95" type="text" value="<c:out value='${person.personName}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> />					
										<!-- <input name="" title="대상자" class="inputbox" type="text" size="20" />
										<a href="#a" class="ic_search" style=" vertical-align:middle"><span>검색</span></a>
										<a class="button_ic" href="#a"><span><img src="../../images/icon/ic_btn_search.gif" alt="" />주소록</span></a>-->
										</div>
									</td>
									<c:if test="${groupType == 'P'}">
									<th scope="row" ><ikep4j:message pre='${prePerDetail}' key='addrgroupName'/></th>
									<td >
										<div>
										<select name="addrgroupIdList" class="multi w80" title="<ikep4j:message pre='${prePerDetail}' key='addrgroupName'/>" >
										<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
											<option value="${addrgroup.addrgroupId}" <c:if test="${addrgroupId == addrgroup.addrgroupId}">selected="selected"</c:if>><c:out value='${addrgroup.addrgroupName}'/></option>
										</c:forEach>
											<option value="" <c:if test="${addrgroupId == ''}">selected="selected"</c:if> ><ikep4j:message pre='${prePrivate}' key='undiffer.title'/></option>
										</select>
										</div>
									</td>
									</c:if>
								</tr>
								<tr>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerList}' key='companyName'/></th>
									<td width="32%"><div><input name="companyName" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='companyName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.companyName}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerDetail}' key='teamName'/></th>
									<td width="32%"><div><input name="teamName" maxlength="20" title="<ikep4j:message pre='${prePerDetail}' key='teamName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.teamName}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='jobRankName'/></th>
									<td><div><input name="jobRankName" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='jobRankName'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.jobRankName}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='mailAddress'/></th>
									<td><div><input name="mailAddress" maxlength="50" title="<ikep4j:message pre='${prePerList}' key='mailAddress'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.mailAddress}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerList}' key='mobilePhoneno'/></th>
									<td><div><input name="mobilePhoneno" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='mobilePhoneno'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.mobilePhoneno}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
									<th scope="row" width="18%"><ikep4j:message pre='${prePerList}' key='officePhoneno'/></th>
									<td><div><input name="officePhoneno" maxlength="20" title="<ikep4j:message pre='${prePerList}' key='officePhoneno'/>" class="inputbox w95" type="text" size="20" value="<c:out value='${person.officePhoneno}'/>" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> /></div></td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${prePerDetail}' key='memo'/></th>
									<td colspan="3"><div><textarea name="personMemo" class="w100" title="<ikep4j:message pre='${prePerDetail}' key='memo'/>" cols="0" rows="3" <c:if test="${dispType == 'VIEW'}">readonly="readonly"</c:if> ><c:out value="${person.personMemo}"/></textarea></div></td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
				</form>
				<!--//blockListTable End-->	
										
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${(groupType == 'P' and person.userType == 'O')or (groupType == 'O' and publicManageFlag == 'true') }">
							<c:if test="${type != 'CREATE'}">
							<li><a class="button" href="#a" onclick="deletePerson('${addrgroupId}','${groupType}','<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose>','${addrgroupName}','${person.userType}','${person.personId}');"><span><ikep4j:message pre='${prePerButton}' key='addrdelete'/></span></a></li>
							<li><a class="button" href="#a" id="addrbook_person_save" ><span>등록</span></a></li>
							</c:if>
						</c:if>
						<c:if test="${type == 'CREATE'}">
							<li><a class="button" href="#a" id="addrbook_person_save" ><span>등록</span></a></li>
						</c:if>
						<li><a class="button" href="#a" onclick="goPersonList('${addrgroupId}','${groupType}','${addrgroupName}');"><span><ikep4j:message pre='${prePerButton}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
				</c:if>	
				</div>
								
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->	