<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preAddrList"  value="ui.support.addressbook.addrgroup.list" />
<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="preAddrButton"  value="ui.support.addressbook.addrgroup.button" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
		
<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	$jq(document).ready(function() {
		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		// 화면 로딩시 각각 페이지 호출 종료
		
	});
	
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	// 그룹 수정
	editAddrgroupList = function(groupType,saveMode,addrgroupId) {
		
		var selAddrgroupId = '';
		if( saveMode == 'EDIT'){
			if( addrgroupId == '' ){ 
				selAddrgroupId = $(".radio:checked").val();
			}else{
				selAddrgroupId = addrgroupId
			}
			
		}

		if( saveMode == 'EDIT' && (selAddrgroupId == null || selAddrgroupId == '') ){
			alert("<ikep4j:message pre='${preAddrMessage}' key='noselect.edit'/> ");
			return false;
		}else{
			
			document.location.href = "<c:url value='/support/addressbook/editAddrgroupList.do'/>?addrgroupId="+selAddrgroupId+"&groupType="+ groupType ;
			
		}
	};
	
    // 그룹 삭제 
	deleteAddrgroupList = function(groupType) {
    	
		var addrgroupId = $(".radio:checked").val();
		if( addrgroupId == null ){
			alert("<ikep4j:message pre='${preAddrMessage}' key='noselect.delete'/>");
			return false;
		}else{
			if(confirm("삭제하시겠습니까?")){
			$jq.ajax({
			    url : '<c:url value="/support/addressbook/deleteAddrgroupList.do"/>',
			    data : {"addrgroupId":addrgroupId},
			    type : "get",
			    success : function(result) {
			    	//if( groupType == 'P' ){
			    	//	Addressbook.getPrivateAddrgroupView();
			    	//}else{
			    	//	Addressbook.getPublicAddrgroupView();
			    	//}
			    	Addressbook.getAddrgroupList(groupType);
			    }
			});
			
			}
			

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
					<h2>
					<c:if test="${groupType == 'P'}">
						<ikep4j:message pre='${prePrivate}' key='management.title'/>
					</c:if>
					<c:if test="${groupType == 'G'}">
						<ikep4j:message pre='${prePrivate}' key='management.ptitle'/>
					</c:if>
					<c:if test="${groupType == 'O'}">
						<ikep4j:message pre='${prePublic}' key='management.title'/>
					</c:if>
					</h2>
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
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
						<thead>
							<tr>
								<th width="5%" scope="col">&nbsp;</th>
								<c:if test="${groupType == 'O'}">
								<th width="10%" scope="col">카테고리</th>
								</c:if>
								<th width="20%" scope="col"><ikep4j:message pre='${preAddrList}' key='addrgroupName'/></th>
								<th width="10%" scope="col"><ikep4j:message pre='${preAddrList}' key='addrgroupMemberCnt'/></th>
								<c:if test="${groupType == 'O'}">
								<th width="15%" scope="col">메신저 사용</th>
								<th width="15%" scope="col">정렬 순서</th>
								</c:if>
								<th width="25%" scope="col"><ikep4j:message pre='${preAddrList}' key='addrgroupMemo'/></th>
							</tr>
						</thead>
												
						<tbody>
						<c:choose>
							<c:when test="${addrgroupList.emptyRecord}">
								<tr>
								<c:if test="${groupType == 'P' || groupType == 'G'}">
									<td colspan="4">
										<ikep4j:message pre='${preAddrMessage}' key='nodata'/>
									</td>
								</c:if>
								<c:if test="${groupType == 'O'}">
									<td colspan="6">
										<ikep4j:message pre='${preAddrMessage}' key='nodata'/>
									</td>
								</c:if>
							</tr>			        
							</c:when>
							<c:otherwise>
							<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
							
							<c:if test="${status.index%2 == 0}">
							<tr>
							</c:if>
							<c:if test="${status.index%2 != 0}">
							<tr class="bgSelected">
							</c:if>
							
								<td class="textCenter"><input type="radio" class="radio" title="<c:out value="${group.addrgroupName}"/>" name="addrgroupflag" value="<c:out value="${addrgroup.addrgroupId}"/>"  /></td>
								<c:if test="${groupType == 'P' || groupType == 'G'}">
									<td class="textLeft"><a href="#a" onclick="editAddrgroupList('${groupType}','EDIT','${addrgroup.addrgroupId}');" ><c:out value="${addrgroup.addrgroupName}"/></a></td>
								</c:if>
								<c:if test="${groupType == 'O'}">
									<td class="textLeft"><c:out value="${addrgroup.categoryName}"/></td>
									<td class="textLeft"><a href="#a" onclick="editAddrgroupList('${groupType}','EDIT','${addrgroup.addrgroupId}');" ><c:out value="${addrgroup.addrgroupName}"/></a></td>
								</c:if>
								<td><c:out value="${addrgroup.addrgroupUserCnt}"/></td>
								<c:if test="${groupType == 'O'}">
								<td>
									<c:if test="${addrgroup.messengerUse == 1}">
										사용
									</c:if>
									<c:if test="${addrgroup.messengerUse != 1}">
										미사용
									</c:if>
								</td>
								<td>
									${addrgroup.addrgroupSeqId}
								</td>
								</c:if>
								<td class="textLeft"><c:out value="${addrgroup.addrgroupMemo}"/></td>
							</tr>
							</c:forEach>
							</c:otherwise>	
						</c:choose>																	
						</tbody>

					</table>
					<div class="pageNum">
					</div>
						
				
				</div>
				<!--//blockListTable End-->	
					
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${groupType == 'P' || groupType == 'G'}">
						<li><a class="button" href="#a" onclick="editAddrgroupList('${groupType}','CREATE','');"><span><ikep4j:message pre='${preAddrButton}' key='create'/></span></a></li>
						<li><a class="button" href="#a" onclick="editAddrgroupList('${groupType}','EDIT','');"><span><ikep4j:message pre='${preAddrButton}' key='edit'/></span></a></li>
						<li><a class="button" href="#a" onclick="deleteAddrgroupList('${groupType}');"><span><ikep4j:message pre='${preAddrButton}' key='delete'/></span></a></li>
						</c:if>
						<c:if test="${groupType == 'O' }">
						<li><a class="button" href="#a" onclick="editAddrgroupList('${groupType}','CREATE','');"><span><ikep4j:message pre='${preAddrButton}' key='create'/></span></a></li>
						<li><a class="button" href="#a" onclick="editAddrgroupList('${groupType}','EDIT','');"><span><ikep4j:message pre='${preAddrButton}' key='edit'/></span></a></li>
						<li><a class="button" href="#a" onclick="deleteAddrgroupList('${groupType}');"><span><ikep4j:message pre='${preAddrButton}' key='delete'/></span></a></li>
						</c:if>
					</ul>
				</div>
				<!--//blockButton End-->
				</div>
				
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->				