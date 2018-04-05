<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="prefixMessage"  value="ui.approval.work.apprDisplay.alert"/>					
<c:set var="prefixUI" 		value="ui.approval.work.apprDisplay.form"/>
<c:set var="preButton"  	value="ui.approval.common.button" />	
<c:set var="preHeader" 		value="ui.approval.work.apprDisplay" />

<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	
	(function($){
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		$(document).ready(function(){
			
			$("#saveButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
		
			//입력값 확인
			var validOptions = {
				submitHandler	: function(form) {
					if (!confirm('<ikep4j:message pre="${preHeader}" key="confirmDisplay" />')) return false;
					
	                  $jq.ajax({     
							url : '<c:url value="/approval/work/apprDisplay/createApprDisplay.do" />',     
							data :  $jq("#myForm").serialize(),     
							type : "post",     
							async : false,
							success : function(result) {      
								if(result == "OK") {
									 dialogWindow.callback(result);
					                 dialogWindow.close();
								}
							},
							error : function(event, request, settings){
								 alert("error");
							}
					  });
	            }
			}; 
		    
			new iKEP.Validator("#myForm", validOptions);
			
		});
		
		
	})(jQuery);
	//-->
</script>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='formTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="formTitle" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="myForm" name="myForm" method="post" action="">
<input type="hidden" id="addrUserListAll" name="addrUserListAll" value=""/>
<input type="hidden" id="addrGroupTypeListAll" name="addrGroupTypeListAll" value=""/>

<spring:bind path="apprDisplay.apprId">
	<input type="hidden" name="${status.expression}" value="${apprId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre="${preHeader}" key="formTitle" />">
			<caption></caption>
			<tbody>
				<tr id="User_TR">
					<th rowspan="6" scope="row" class="textCenter" id="headTH">
						<ikep4j:message pre="${prefixUI}" key="resourceConfig" />
					</th>
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="user" /></th>
					<td>
						<input name="userName" id="userName" title="<ikep4j:message pre='${prefixUI}' key='user' />" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="userSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />">
							<span id="btnAddrControl">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="addressBook" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="addrUserList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='userName' />" ></select>
							</div>
							<div class="input_buttonBox02">
								<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="userNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />"/><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="userTypeNameCount">0</span><ikep4j:message pre="${prefixUI}" key="userNameCount" />
							</div>
						</div>								
					</td>
				</tr>	
				
				<tr id="Group_TR">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="group" /></th>
					<td>
						<input name="groupTypeName" id="groupTypeName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="groupTypeSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='listIcon' />">
							<span id="groupTypeSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='listIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="addressBook" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="addrGroupTypeList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='groupName' />" >
									
								</select>
							</div>
							<div class="input_buttonBox02">	
								<a href="#" class="button_ic mb5" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
									<span id="changeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${prefixUI}" key="groupHierarchy" /></span>
								</a>
								<br />
								<a href="#" class="button_ic mb5" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="groupTypeNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />"/><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="groupTypeNameCount">0</span><ikep4j:message pre="${prefixUI}" key="groupNameCount" />
							</div>
						</div>								
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="cancelButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>	
<!--//blockListTable End-->	
<script type="text/javascript">
(function($) {
	var $selectAddrUser = $("select[name='addrUserList']");
	var $selectAddrGroup = $("select[name='addrGroupTypeList']");
	
   	// 셀렉트 박스 카운트 넣기
   	$jq("#userTypeNameCount").html($selectAddrUser.children().length);
   	$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
   	
       //조직도 팝업 
	$jq("#btnAddrControl").click(function() {
		var items = $.map($selectAddrUser.children(), function(option) {
			return $(option).data("data");
		});

		iKEP.showAddressBook(showAddressBookCallback, items, {selectElement:$selectAddrUser, selectType:"user"});
	});

    //검색 관련 이벤트
    $jq('#userName').keypress( function(event) { 
		iKEP.searchUser(event, "Y", setUser); 
	});
	
	$jq('#userSearchBtn').click( function() { 
		$jq('#userName').trigger("keypress");
	});
	
	$jq('#groupTypeName').keypress( function(event) { 
		iKEP.searchGroup(event, "Y", setGroupType); 
	});
	
	$jq('#groupTypeSearchBtn').click( function() { 
		$jq('#groupTypeName').trigger("keypress");
	});
	
	
	//그룹목록 추가 버튼에 이벤트 바인딩
	$("#groupTypeSearchBtnAll").click( function() {
		var items = $.map($selectAddrGroup.children(), function(option) {
			return $(option).data("data");
		});
		
		iKEP.showAddressBook(setGroupAddress, items, {selectType:"group",tabs:{common:0}});
	});

	
	//삭제 이벤트
	$jq('#userNameDelete').click(function(event) { 
		selectRemove("addrUserList", "userTypeNameCount");	
	});
	
	$jq('#groupTypeNameDelete').click(function(event) { 
		selectRemove("addrGroupTypeList", "groupTypeNameCount");
	});
	
	$jq('#changeHierarchy').click(function(event) { 
		changeGroupHierarchy("addrGroupTypeList");
	});
	
	//하위 부서 목록을 저장하도록 표시
	changeGroupHierarchy = function (selectName) {
		var $sel = "";	
		var $obj = "";
		
		$sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$sel.find("option:selected").each(function () {
                $obj = $jq.parseJSON($(this).val());
                
                var groupId = $obj.groupId;
    			var hierarchyPermission = $obj.hierarchyPermission;
    			var text = $(this).text();
    			
    			if(hierarchyPermission == 0) {
    				$(this).text("[H]"+text);
    				$obj.hierarchyPermission = 1;
    			} else if(hierarchyPermission == 1) {
    				$(this).text(text.substring(3, text.length));
    				$obj.hierarchyPermission = 0;
    			} else {
    				
    			}
    			
    			$(this).val(JSON.stringify($obj));
			});
			
			setSecurityValue(selectName);
		}
		
	};
	
	// 조직도 popup후 callback
    var showAddressBookCallback = function(data) {
    	$jq("#userTypeNameCount").html($selectAddrUser.children().length);
    	
    	setSecurityValue('addrUserList');
    };
    
    var showExpAddressBookCallback = function(data) {
    	if(data.length > 0) {
			$jq(data).each(function(index) {
				$jq.each($selectAddrExpUser.children(), function() {
					if(this.value == "${user.userId}") {
						$jq(this).remove();
						
						alert("<ikep4j:message pre='${prefixMessage}' key='noUseMyAccount' />");
					}
				});
			});
		}
    	
    	$jq("#expUserTypeNameCount").html($selectAddrExpUser.children().length);
    	
    	setSecurityValue('addrExpUserList');
    };
	
	setUser = function(data) {
		var selectCheck;
		if(data.length > 0) {
			$('#userName').val("");
			$jq(data).each(function(index) {
				selectCheck = true;
				$jq.each($selectAddrUser.children(), function() {
					if(this.value == data[index].id) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					$.tmpl(iKEP.template.userOption, this).appendTo($selectAddrUser)
						.data("data", this);
				} 
			});
			
			$jq("#userTypeNameCount").html($selectAddrUser.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchUser' />");
		}
		
		setSecurityValue('addrUserList');
		
		var $lastItem = $selectAddrUser.children(":last");
		$selectAddrUser.scrollTop($lastItem.position().top + $lastItem.height());
	};
	
	setGroupType = function(data) {
		var selectCheck;
		if(data.length > 0) {
			$('#groupTypeName').val("");
			$jq(data).each(function(index, groupInfo) {
				selectCheck = true;
				$jq.each($selectAddrGroup.children(), function() {
					if($(this).data("data").code == groupInfo.code) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					$.tmpl(iKEP.template.groupOption, groupInfo).appendTo($selectAddrGroup)
						.data("data", groupInfo)
						.val(JSON.stringify({groupId:groupInfo.code, hierarchyPermission:0}));
				}
			});
			
			$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchGroup' />");
		}
		
		setSecurityValue('addrGroupTypeList');
	};
	
	/** 주소록에서 선택한 후 그룹 데이타 세팅 **/
	setGroupAddress = function(data) {
		$selectAddrGroup.children().remove();
		
		$.each(data, function() {
			$.tmpl(iKEP.template.groupOption, this).appendTo($selectAddrGroup)
				.data("data", this)
				.val(JSON.stringify({groupId:this.code, hierarchyPermission:0}));
		});
		
		$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
		
		setSecurityValue('addrGroupTypeList');
	};
	
	
	selectRemove = function (selectName, countName){
		var $sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$(this).remove();
				}
			});
			
			$jq("#"+countName).html($sel.children().length);
		}
		
		setSecurityValue(selectName);
	};
	
	selectedALL = function (selectName){
		var $sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
})(jQuery);

function initSecurityValue(){
	setSecurityValue('addrUserList');
	setSecurityValue('addrGroupTypeList');
};

function setSecurityValue(selectName){
	
	var selValue = "";
	var $sel = $jq("select[name='"+selectName+"']");
	
	if($sel) {
		$jq.each($sel.children(), function(index) {
			if(index == 0){
				selValue = this.value;
			}else{
				selValue = selValue+"^"+this.value;
			}
		});
	}
	
	$jq("input[name='"+selectName+"All']").val(selValue);
};


/** 수정시 기존 등록된 그룹 정보 세팅 **/
function changeHierarchyGroupName(selectName) {
	
	var $sel = $jq("select[name='"+selectName+"']");
	var $obj = "";
	var value = "";
	var text = "";
	var groupId = "";
	
	// option 에 주소록 연계를 위한 데이타 세팅
	<c:forEach var="group" items="${aclResourcePermissionRead.groupDetailList}">
		var item = {
			type:'group',				
			code:'${group.groupId}',
			name:'${group.groupName}',
			parent:'${group.parentGroupId}'	
		};
		
		$obj = $sel.find("option[value=${group.groupId}]");
		$obj.data("data", item);			
	</c:forEach>
	
	<c:forEach var="item" items="${aclResourcePermissionRead.groupPermissionList}">			
		
		<c:choose>
			<c:when test="${item.hierarchyPermission == 1}">
				$obj = $sel.find("option[value=${item.groupId}]");
				value = {'groupId':'${item.groupId}', 'hierarchyPermission':<c:out value="${item.hierarchyPermission}"/>};
				text = $obj.text();
				
				$obj.val(JSON.stringify(value));
				$obj.text("[H]"+text);					
			</c:when>
			<c:when test="${item.hierarchyPermission == 0}">
				$obj = $sel.find("option[value=${item.groupId}]");
				value = {'groupId':'${item.groupId}', 'hierarchyPermission':<c:out value="${item.hierarchyPermission}"/>};
				
				$obj.val(JSON.stringify(value));
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</c:forEach>
			
	setSecurityValue('addrGroupTypeList');
}

//changeHierarchyGroupName("addrGroupTypeList");
//initSecurityValue();
</script>