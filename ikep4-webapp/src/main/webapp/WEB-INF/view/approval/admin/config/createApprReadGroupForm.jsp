<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preForm"  	value="ui.approval.work.entrust.form" />
<c:set var="preValidation" value="ui.approval.admin.apprReadGroup.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
<c:set var="preHeader"	value="ui.approval.admin.apprReadGroup.listView.header"/>
<c:set var="preSearch"	value="ui.approval.admin.apprReadGroup.listView.search"/>
<c:set var="preList"	value="ui.approval.admin.apprReadGroup.listView.list"/>
<c:set var="preButton"	value="ui.approval.admin.apprReadGroup.listView.button"/>	

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	
	(function($){
		
		var viewUnitDelegateDialog;
		var form = $("#myForm");
		
		/**
		 * 위임 설정 화면 오픈.
		 */
		viewUnitDelegateDialog = function () {
			
			$instanceLogId	= $("input[name=userId]:hidden");
			
			viewApFormProcessDialog = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre='${preForm}' key='subTitle'/>",
				url 		: "updateUnitEntrustForm.do?userId="+$instanceLogId.val(),
				modal 		: true,
				width 		: 650,
				height 		: 200,
				params 		: {userId:$instanceLogId.val()}
			});
		};
		
		//검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
		//result: 검색되거나 선택된 값을 배열형태로 리턴함
     	setAddress = function(data) {
			var name="";
			var userId="";
			$.each(data, function() {
				name = $.trim(this.name);
				userId = $.trim(this.id);	
			})
			
			$("#userName").val(name);
			$("#userId").val(userId);
			
			$jq("select[name=groupSet] option").remove();
			getGroupListAjax(userId);
		};
		
		setUser = function(data) {
			var name="";
			var userId="";
			
			$jq.each(data, function() {
				name = $jq.trim(this.userName);
				userId = $jq.trim(this.id);	
			})
			$jq("#userName").val(name);
			$jq("#userId").val(userId);
			
			$jq("select[name=groupSet] option").remove();
			getGroupListAjax(userId);
		};
		
		/* 부서 정보 추가 by 주소록 */
		setGroupAddress = function(data) {   
			var $jqsel = $jq("select[name=groupSet]").children().remove().end();
			$jq.each(data, function() {
				if(this.type=="group") {
				    $jq.tmpl(iKEP.template.groupOption, this).appendTo($jqsel).data("data", this);
				}else {
				    $jq.tmpl(iKEP.template.userOption, this).appendTo($jqsel).data("data", this);
				}
		    });
			setSelectOptionCount("groupSet", "#groupSetSpan");
			
			if($jq("select[name=groupSet] option").size() > 0) {
				$('#requiredMsg').text("");
			}
		};

		/* select option을 스트링으로 변환 */
		setGroupSet = function(form) {
			var result="", receiveType="";
		    $jq("select[name=groupSet] option").each(function() {
		    	if(result!="") result += ";";
		        receiveType = $jq(this).data("data").type=="group"?"0":"1";
		        result += $jq(this).val(); 
		    });
		    $jq("input[name=groupId]").val(result);
		};
		
		setSelectOptionCount = function(name, target){
		    $jq(target).html($jq("select[name=" + name + "] option").size());
		};

		/* 기선텍된 값인지 체크 */
		hasSelectOptionValue = function(name, value) {
		    var b = false;
		    $jq("select[name=" + name + "] option").each(function() {
		        if(value==$jq(this).val()){
		            b = true;
		            return false;
		        }
		    });  
		    return b;  
		};
		
		getGroupListAjax = function(userId) {
			$jq.ajax({
				url : '<c:url value="existApprReadGroupList.do" />',
				data : {userId:userId},
				type : "post",
				success : function(result) {
					if(result.length > 0) {
						$select = $("#groupSet");
						for(var i=0;i<result.length;i++) {
							groupInfo = {
									type : "group",
									code : result[i].groupId,
									name : result[i].groupName
								};
					
								$.tmpl(iKEP.template.groupOption, groupInfo).appendTo($select)
									.data("data", groupInfo);
						}
					}
					setSelectOptionCount("groupSet", "#groupSetSpan");
				},
				error:function(){
					alert("fail");
				}
			});	
		};
		
		$(document).ready(function(){
			
			$jq("#apprReadGroupLinkOfLeft").click();
			
			$("#viewUnitDelegateButton").click(function(){
				viewUnitDelegateDialog();
			});
			
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
			
          	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
	        $jq('#userName').keypress( function(event) {
	        	//파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
				iKEP.searchUser(event, "N", setUser);			
			});
			
		    //검색버튼을 클릭했을때 이벤트 바인딩
			$jq('#addrSearchBtn').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#userName').trigger("keypress");
			});
		    
			$jq('#btnDeleteControl').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#userName').val('');
				$jq('#userId').val('');
				$jq("select[name=groupSet] option").remove();
				setSelectOptionCount("groupSet", "#groupSetSpan");
			});
            
            // 부서 찾기 버튼
            $jq("#groupAddressButton").click(function(){
                var items = [];
        	    $jq("select[name=groupSet]").children().each(function() {
        		    items.push($jq(this).data("data"));
        	    });
        	    iKEP.showAddressBook(setGroupAddress, items, {selectType:"group", isAppend:false, selectMaxCnt:50, tabs:{common:1}});
            });
            
            // 열람권한 부서 삭제 버튼
            $jq("#groupDeleteButton").click(function(){
            	$jq("select[name=groupSet] option:selected").remove();
            	setSelectOptionCount("groupSet", "#groupSetSpan");
            });
			
			$("#saveReadGroupButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
			
			//입력값 확인
			var validOptions = { 
				submitHandler	: function(form) {
					if($jq("select[name=groupSet] option").size() == 0) {
						$('#requiredMsg').text("<ikep4j:message pre='${preValidation}' key='groupName'/>");
						return false;
					}
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
					setGroupSet(form);
	                form.submit();
	                return true;
	            },
	        rules			: {
            		userName 	: { required: true }
// 	               ,groupSet 	: { required: true }
            },
            messages		: {
           			userName	: { 
           				direction 		: "bottom",
						required		: "<ikep4j:message pre='${preValidation}' key='userName'/>" 
					}
//             		,groupSet	: { 
//         				direction 		: "bottom",
// 						required		: "<ikep4j:message pre='${preValidation}' key='groupName'/>" 
// 					}
           		}
			}; 
			
			$("#listReadGroupButton").click(function() {   
				location.href="<c:url value='/approval/admin/config/listApprReadGroup.do'/>";
			});
			
			new iKEP.Validator("#myForm", validOptions);
		    
		});
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
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

<form id="myForm" name="myForm" method="post" action="<c:url value='/approval/admin/config/createApprReadGroup.do'/>">

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="apprReadGroup.userName">
					<th scope="row" width="10%"><ikep4j:message pre='${preList}' key='${status.expression}'/></th>
					<td>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preList}' key='${status.expression}'/>" value="${apprReadGroup.userName}" size="12" readonly="readonly"/>
						<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
							<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />Search</span>
						</a>
						<a id="addrBtn" href="#a" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preForm}' key='organization'/></span>
						</a>
						<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
							<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />Delete</span>
						</a>
					</td>
					</spring:bind>
					<spring:bind path="apprReadGroup.userId">
						<input type="hidden" id="${status.expression}" name="${status.expression}" value="${apprReadGroup.userId}"/>
					</spring:bind>
				</tr>
				<tr>
					<th width="15%" scope="row"><ikep4j:message pre='${preList}' key='groupName' /></th>
					<td width="85%" valign="bottom">
					    <spring:bind path="apprReadGroup.groupId">
						<input type="hidden" name="${status.expression}" id="${status.expression}" />
						</spring:bind>
						<a id="groupAddressButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="">Address</span></a>
						<span id="requiredMsg"></span>
						<div class="blockBlank_5px"></div>
						<spring:bind path="apprReadGroup.groupSet">
						<select name="${status.expression}" id="${status.expression}" class="inputbox w50" size="5" multiple="multiple"></select>
						</spring:bind>
						<a id="groupDeleteButton" class="button_ic" href="#a" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="">Delete</span></a> <span style="vertical-align:bottom;">(총 <span id="groupSetSpan" style="vertical-align:bottom">0</span>)</span>
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
			<li><a id="saveReadGroupButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="listReadGroupButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->