<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preAddrList"  value="ui.support.addressbook.addrgroup.list" />
<c:set var="preAddrButton"  value="ui.support.addressbook.addrgroup.button" />
<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="preAddrValidMessage" value="message.support.addressbook.addrgroup.validation" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value='/base/js/units/support/addressbook/addressbook.js'/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	var validator ;
	//var validator ;
	var validOptionsABGroup = { 
			rules : {
				addrgroupName : {
					required : true
				}
			},
			messages : {
				addrgroupName : {
					required : "<ikep4j:message key='NotNull.addrgroup.addrgroupName'/>"
				}
			},
			submitHandler : function(form) {
			
				$(form).find('select[name=addrpersonList] option').each(function(){
					this.selected = true;
				});
				
				$jq.ajax({
				    url : "<c:url value='/support/addressbook/saveAddrgroupList.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	//Addressbook.getAddrgroupList('${groupType}');
				    	Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');
				    },
				    error : function(xhr, exMessage) {
				    	var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
				
			}
	};
	
	setUser = function(result) {

			$jq("#addrgroupSaveForm").find('select[name=addrpersonList] option').each(function(){
				if( $(this).val() == '' ){
					$(this).remove();
				}
			});
		
			if(result && result.length > 0) {
				var templateName = ["addrBookItemGroup", "addrBookItemUser", "addrBookItemAddrUser"];
				jQuery.template(templateName[0], '<option value="\${code}">\${name}<\/option>');
				jQuery.template(templateName[1], '<option value="\${id}|I">\${userName} \${jobTitleName} \${teamName} [<ikep4j:message pre='${prePublic}' key='company'/>]<\/option>');
				jQuery.template(templateName[2], '<option value="\${id}|O">\${userName} \${jobTitleName} \${teamName} [<ikep4j:message pre='${prePublic}' key='outer'/>]<\/option>');

				var orgItems = [];
				var $select = $jq("#addrgroupSaveForm").find("[name=addrpersonList]");
				$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				
				jQuery.each(result, function() {
					
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = templateName[0]; break;
						case "user" : identity = this.id+"|I" ; tpl = templateName[1]; break;
						case "addruser" : identity = this.id+"|O" ; tpl = templateName[2]; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						jQuery.tmpl(tpl, this).appendTo($select)
							.data("data", this);
					}
				});
			}
			
	}
	
	setAddrSelectList = function(data) {

		$jq("#addrgroupSaveForm").find('select[name=addrpersonList] option').each(function(){
			if( $(this).val() == '' ){
				$(this).remove();
			}
		});
		
		if(data && data.length > 0) {
			
			var templateName = ["addrBookItemUser"];
			jQuery.template(templateName[0], '<option value="\${personId}|O">\${personName} \${jobTitle} \${teamName} [<ikep4j:message pre='${prePublic}' key='outer'/>]<\/option>');
			
			var orgItems = [];
			var $select = $jq("#addrgroupSaveForm").find("[name=addrpersonList]");
			$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
			
			jQuery.each(data, function() {
				var identity = "";
				var tpl = "";
				if(identity = this.personId+"|O" ) {
					tpl = templateName[0];
				}

				if(jQuery.inArray(identity, orgItems) == -1) {
					var $option = jQuery.tmpl(tpl, this).appendTo($select);
					jQuery.data($option[0], "data", this);
				}
			});
		}
		
	};
	
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	$jq(document).ready(function() {
		

		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		// 화면 로딩시 각각 페이지 호출 종료
		
		$jq('#btnAddrbook').click( function(event) { 
			iKEP.popupAddrPerson("Y", setAddrSelectList); 
		});
		
		// 조직도 팝업 테스트
		$jq("#btnAddrControl").click(function() {
			
			var items = [];
			var $sel = $jq("#addrgroupSaveForm").find("[name=addrpersonList]");
			
			<c:if test="${addrgroup.groupType == 'O'}">
			iKEP.showAddressBook(setUser, items, {selectType:"user", isAppend:true, tabs:{common:0}});
			</c:if>
			<c:if test="${addrgroup.groupType == 'P' || addrgroup.groupType == 'G'}">
			iKEP.showAddressBook(setUser, items, {selectType:"user", isAppend:true, tabs:{common:0,personal:0}});
			</c:if>

		});
		
		$jq("#btnAddrDelete").click(function() {
			var $sel = $jq("#addrgroupSaveForm").find("[name=addrpersonList]");
			$sel.find("option:selected").each(function() {
				$jq(this).remove();
			});
		});
		
		validator =  new iKEP.Validator("#addrgroupSaveForm", validOptionsABGroup);
		
		$jq("#btnAddrgroupSave").click(function(){
			$jq.each($("#addrgroupSaveForm select[name=categoryId] option:selected"),function(index){
				if($jq(this).text() != '') {			
        			$jq("#categoryId").val($jq(this).val());
        			$jq("#categoryName").val($jq(this).text());	
        		}
    		});
			
			$("#addrgroupSaveForm").trigger("submit");
		});
		
	});

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
				
				<div id="pageTitle">
					<h2>
					<c:if test="${groupType == 'P'}">
						<ikep4j:message pre='${prePrivate}' key='managecreate.title'/>
					</c:if>
					<c:if test="${groupType == 'G'}">
						<ikep4j:message pre='${prePrivate}' key='managecreate.ptitle'/>
					</c:if>
					<c:if test="${groupType == 'O'}">
						<ikep4j:message pre='${prePublic}' key='managecreate.title'/>
					</c:if>
					</h2>
				</div>
							
				<div id="listbody">
					<!--blockListTable Start-->
					<c:if test="${addrgroup != null}">
					<form id="addrgroupSaveForm" action="" method="post" onsubmit="return false">
					
					<input name="addrgroupId" title="" type="hidden" value="<c:out value='${addrgroup.addrgroupId}'/>" />
					<input name="groupType" title="" type="hidden" value="<c:out value='${addrgroup.groupType}'/>" />
		          	<input id="categoryName" name="categoryName" title="" type="hidden" value="" />	
		          		
		      		<div class="blockDetail">
						<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
							<tbody>
								<c:if test="${addrgroup.groupType == 'O'}">
									<tr>
										<th scope="row" width="18%">카테고리</th>
										<td width="82%">
											<select id="categoryId" name="categoryId">
										    	<c:forEach var="code" items="${categoryList}">
													<option value="${code.categoryId}" <c:if test="${code.categoryId == addrgroup.categoryId}">selected="selected"</c:if>>${code.categoryName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th scope="row" width="18%">메신저 사용</th>
										<td width="82%">
											<select id="messengerUse" name="messengerUse">
												<option value="1" <c:if test="${addrgroup.messengerUse == '1'}">selected="selected"</c:if>>사용</option>
												<option value="" <c:if test="${addrgroup.messengerUse == ''}">selected="selected"</c:if>>미사용</option>
											</select>
										</td>
									</tr>
									<tr>
										<th scope="row" width="18%">정렬 순서</th>
										<td width="82%">
											<input name="addrgroupSeqId" class="inputbox" type="text" value="<c:out value='${addrgroup.addrgroupSeqId}'/>" size="6" maxlength="6" />
										</td>
									</tr>
								</c:if>
								<tr>
									<th scope="row" width="18%"><span class="colorPoint">*</span><ikep4j:message pre='${preAddrList}' key='addrgroupName'/></th>
									<td width="82%">
										<div><input name="addrgroupName"  title="<ikep4j:message pre='${preAddrList}' key='addrgroupName'/>" class="inputbox w100" type="text" value="<c:out value='${addrgroup.addrgroupName}'/>" /></div>
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preAddrList}' key='addrgrouplist'/></th>
									<td>
										<!-- <input name="" title="대상자" class="inputbox" type="text" size="20" /> -->
										<a class="button_ic" id="btnAddrControl" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preAddrButton}' key='orgPopup'/></span></a>
										<c:if test="${addrgroup.groupType == 'P'}">
										<a class="button_ic" id="btnAddrbook" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preAddrButton}' key='addrPopup'/></span></a>
										</c:if>
										<div style="padding-top:4px;">
										
											<c:choose>
												<c:when test="${personList.entity == null}">
													<select name="addrpersonList" multiple="multiple" size="15" class="multi w80" title="<ikep4j:message pre='${preAddrList}' key='addrgrouplist'/>" >
														<option value="">&nbsp;</option>
													</select>
												</c:when>
												<c:otherwise>
													<select name="addrpersonList" multiple="multiple" size="15" class="multi w80" title="<ikep4j:message pre='${preAddrList}' key='addrgrouplist'/>">
														<c:forEach var="person" items="${personList.entity}" varStatus="status">
															<c:choose>
																<c:when test="${person.userType == 'I'}">
																	<c:set var="userType" >
																		<ikep4j:message pre='${prePublic}' key='company'/>
																	</c:set>
																</c:when>
																<c:otherwise>
																	<c:set var="userType" >
																		<ikep4j:message pre='${prePublic}' key='outer'/>
																	</c:set>
																</c:otherwise>
															</c:choose>
															<option value="${person.personId}|${person.userType}"><c:out value="${person.personName}"/> <c:out value="${person.jobRankName}"/> <c:out value="${person.teamName}"/> [${userType}]</option>
														</c:forEach>
													</select>
												</c:otherwise>
												</c:choose>
										
											
											<a class="button_ic valign_bottom"  id="btnAddrDelete" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" /><ikep4j:message pre='${preAddrButton}' key='delete1'/></span></a>
											<!-- <span class="totalNum_s">총 <p style="display:inline" id="addrgroupTotalCount"><c:out value="${addrgroup.addrgroupUserCnt}"/></p>명</span> -->
										</div>					
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preAddrList}' key='addrgroupMemo'/></th>
									<td>
										<div><textarea name="addrgroupMemo" class="w100" title="<ikep4j:message pre='${preAddrList}' key='addrgroupMemo'/>" cols="0" rows="5"><c:out value="${addrgroup.addrgroupMemo}"/></textarea></div>
									</td>
										
								</tr>
							</tbody>
						</table>
					</div>
					</c:if>	
					<!--//blockListTable End-->	
													
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<c:if test="${addrgroup.groupType == 'O'}">
								<li><a class="button" href="#a" id="btnAddrgroupSave"><span><ikep4j:message pre='${preAddrButton}' key='save'/></span></a></li>
								<li><a class="button" href="#a" onclick="Addressbook.getAddrgroupList('${addrgroup.groupType}');"><span><ikep4j:message pre='${preAddrButton}' key='cancel'/></span></a></li>
							</c:if>
							<c:if test="${addrgroup.groupType == 'P'||addrgroup.groupType == 'G'}">
								<li><a class="button" href="#a" id="btnAddrgroupSave"><span><ikep4j:message pre='${preAddrButton}' key='save'/></span></a></li>
								<li><a class="button" href="#a" onclick="Addressbook.getAddrgroupList('${addrgroup.groupType}');"><span><ikep4j:message pre='${preAddrButton}' key='cancel'/></span></a></li>
							</c:if>
						</ul>
					</div>
					<!--//blockButton End-->
					</form>				
				</div>
				<!--//mainContents End-->
				<div class="clear"></div>
			</div>
			<!--//blockMain End-->		
		</div>			