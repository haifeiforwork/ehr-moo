<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardAdmin.createAwardView" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardAdmin.createAwardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.awardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--  
(function($) {   
	
	setReadUser = function(data) {   
		
		var $sel = $jq("select[name=selectReadUserList]");
		
		$.each(data, function(index, value) {
			
			if($("#selectReadUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userAwardOption, this)
				.appendTo($sel)
				.data("data", this);   
			} 
		})   
		
		$("#selectReadUserListCount").text(displayUserCountText($("#selectReadUserList option").length));
	};
	
	setReadUserAddress = function(data) {   
		
		var $sel = $jq("select[name=selectReadUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.userAwardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		$("#selectReadUserListCount").text(displayUserCountText($("#selectReadUserList option").length));
	};
	
	setReadGroupAddress = function(data) { 
		
		var $sel = $jq("select[name=selectReadGroupList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.groupAwardOption, this)
				.appendTo($sel)
				.data("data", this); 
		});
		$("#selectReadGroupListCount").text(displayGroupCountText($("#selectReadGroupList option").length));
	}; 
	
	setReadGroup = function(data) {   
		
		var $sel = $jq("select[name=selectReadGroupList]");
		
		$.each(data, function(index, value) {  

			if($("#selectReadGroupList option[value=" + value.code + "]").length == 0) {
				$.tmpl(iKEP.template.groupNameOption, this)
				.appendTo($sel)
				.data("data", this); 
			} 
		}) 
		$("#selectReadGroupListCount").text(displayUserCountText($("#selectReadGroupList option").length));
	};  
	
	
	setReadRole = function(data) {
		var $sel = $jq("select[name='addrReadRoleList']");
		var str = "";
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true
				$jq.each($sel.children(), function() {
					if(this.value == data[index].roleId) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					str = data[index].roleName;
					
					var $option = $('<option/>').appendTo($sel).attr('value', data[index].roleId).html(str);
				}
			});
			
			$jq("#readRoleNameCount").html($sel.children().length);
		} else {
			alert("검색어에 해당하는 역할이 없습니다.");
		}
	};
	
	setWriteRole = function(data) {
		var $sel = $jq("select[name='addrWriteRoleList']");
		var str = "";
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true
				$jq.each($sel.children(), function() {
					if(this.value == data[index].roleId) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					str = data[index].roleName;
					
					var $option = $('<option/>').appendTo($sel).attr('value', data[index].roleId).html(str);
				}
			});
			
			$jq("#writeRoleNameCount").html($sel.children().length);
		} else {
			alert("검색어에 해당하는 역할이 없습니다.");
		}
	};
	
	setWriteUser = function(data) {  
		
		var $sel = $jq("select[name=selectWriteUserList]");
		
		$.each(data, function(index, value) {  
			if($("#selectWriteUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userAwardOption, this).appendTo($sel).data("data", this);   
			} 
		})   
		
		$("#selectWriteUserListCount").text(displayUserCountText($("#selectWriteUserList option").length));
	};
	
	setWriteUserAddress = function(data) {  
		
		var $sel = $jq("select[name=selectWriteUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.userAwardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		
		$("#selectWriteUserListCount").text(displayUserCountText($("#selectWriteUserList option").length));
	};
	
	setWriteGroupAddress = function(data) {  
		
		var $sel = $jq("select[name=selectWriteGroupList]").children().remove().end();
		
		$.each(data, function(index, value) {  
			$.tmpl(iKEP.template.groupAwardOption, this)
			.appendTo($sel)
			.data("data", this); 
		});
		$("#selectWriteGroupListCount").text(displayGroupCountText($("#selectWriteGroupList option").length));
	}; 
	
	setWriteGroup = function(data) {  
		
		var $sel = $jq("select[name=selectWriteGroupList]");
		
		$.each(data, function(index, value) {  
			if($("#selectWriteGroupList option[value=" + value.code + "]").length == 0) {
				$.tmpl(iKEP.template.groupNameOption, this)
				.appendTo($sel)
				.data("data", this);    
			} 
		}) 
		$("#selectWriteGroupListCount").text(displayGroupCountText($("#selectWriteGroupList option").length));
	}; 
	
	displayGroupCountText = function(count) {
		if(count == null) {
			count = 0;
		}
		return "<ikep4j:message pre='${preDetail}' key='all'/> " + count + "<ikep4j:message pre='${preDetail}' key='group'/>";
	};
	
	displayUserCountText = function(count) {
		if(count == null) {
			count = 0;
		}
		return "<ikep4j:message pre='${preDetail}' key='all'/> " + count + "<ikep4j:message pre='${preDetail}' key='myung'/>";
	};  
	<c:if test="${award.awardType eq 0}">
		displayWritePermissionTableRow = function() {
			if($("input[name=writePermission]:checked").attr("value") == "0") {
				$(".writePermissionTableRow").show();
				$(".writePermissionTableRow").children().show();
				$("input[name=readPermission]").attr("disabled",false);
			} else {
				$(".writePermissionTableRow").children().hide();
				$(".writePermissionTableRow").hide();
				$("#selectWriteUserList option").remove();
				$("#selectWriteGroupList option").remove();
				//등록 권한 부여시 조회 권한  부여
				$("input[name=readPermission]:eq(0)").attr("checked", true);
				displayReadPermissionTableRow();
				$("input[name=readPermission]").attr("disabled",true);
			} 
			
			iKEP.iFrameContentResize();
		};
		displayReadPermissionTableRow = function() {
			if($("input[name=readPermission]:checked").attr("value") == "0") {
				$(".readPermissionTableRow").show();
				$(".readPermissionTableRow").children().show();
			} else {
				$(".readPermissionTableRow").children().hide();
				$(".readPermissionTableRow").hide();
				$("#selectReadUserList option").remove();
				$("#selectReadGroupList option").remove();
			} 
			
			iKEP.iFrameContentResize(); 
		};
		
		displayCategoryTableRow = function() {
			if($("input[name=wordHead]:checked").attr("value") == "1") {
				$("#categoryId").show();
				$("#searchItemButton").show();	
				alert('<ikep4j:message pre='${preMessage}' key='wordHeadMessage'/>');
			} else {
				$("#categoryId").hide();
				$("#searchItemButton").hide();
				//$("#selectReadUserList option").remove();
				//$("#selectReadGroupList option").remove();
			} 
			
			iKEP.iFrameContentResize(); 
		};
	</c:if>
	
	<c:if test="${award.awardType eq 2}">
		displayReadPermissionTableRow = function() {
			if($("input[name=readPermission]:checked").attr("value") == "0") {
				$(".readPermissionTableRow").show();
				$(".readPermissionTableRow").children().show();
			} else {
				$(".readPermissionTableRow").children().hide();
				$(".readPermissionTableRow").hide();
				$("#selectReadUserList option").remove();
				$("#selectReadGroupList option").remove();
			} 
			
			iKEP.iFrameContentResize(); 
		};
	</c:if>
	
	displayAllowTypeHelpBox = function() {   
		if($("input[name=fileAttachOption]:checked").attr("value") == "0") {
			$("#fileExtensionHelpBox").show();
			$("#fileTypeHelpBox").hide();
		} else {
			$("#fileExtensionHelpBox").hide();
			$("#fileTypeHelpBox").show();
		}  
		iKEP.iFrameContentResize();
	};
	
	contentsReadPermissionUnUse = function() {   
		if($("input[name=contentsReadPermission]:checked").attr("value") == "0") {
			var rm = $("input[name=contentsReadMail]");
			//alert($(rm).length);
			$(rm[1]).attr("checked" ,true);
			//alert($(rm[1]).attr("value"));
		} 
	};
	
	
	$(document).ready(function() {  
		window.parent.topScroll(); 

		<c:if test="${award.awardType eq 0}">
			displayReadPermissionTableRow();
			displayWritePermissionTableRow();
			displayCategoryTableRow();
		</c:if>
		<c:if test="${award.awardType eq 2}">
			displayReadPermissionTableRow();
		</c:if>

		displayAllowTypeHelpBox();
		

		
		$("option.dummy").remove();
		
		new iKEP.Validator("#awardForm", {
			<c:if test="${award.awardType eq 0}">
			rules  : {
				awardName        : { required : true, rangelength : [1, 100] },  
				awardEnglishName        : { required : true, rangelength : [1, 100] },  
				awardDescription : { rangelength : [1, 2000] },  
				awardEnglishDescription : { rangelength : [1, 2000] },  
				allowType        : { required : true, rangelength : [1, 50] }   
			},
			messages : { 
				awardName        : {direction : "right",  required : "<ikep4j:message key='NotEmpty.award.awardName' />",       
					 				rangelength : "<ikep4j:message key='Size.award.awardName' />"}, 
				awardEnglishName        : {direction : "right",  required : "<ikep4j:message key='NotEmpty.award.awardEnglishName' />",       
						 			rangelength : "<ikep4j:message key='Size.award.awardEnglishName' />"}, 
	            awardDescription : {direction : "left",   rangelength : "<ikep4j:message key='Size.award.awardDescription' />"}, 
	            awardEnglishDescription : {direction : "left",   rangelength : "<ikep4j:message key='Size.award.awardEnglishDescription' />"}, 
	            allowType        : {
	            	direction   : "bottom", 
	            	required    : "<ikep4j:message key='NotEmpty.award.allowType' />",
	            	rangelength : "<ikep4j:message key='Size.award.allowType' />" }
  			},
			</c:if>
			<c:if test="${award.awardType eq 1}">
			rules  : {
				awardName        : { required : true, rangelength : [1, 100] },   
				awardEnglishName        : { required : true, rangelength : [1, 100] },   
				awardDescription : { rangelength : [1, 2000] },  
				awardEnglishDescription : { rangelength : [1, 2000] },    
				url              : { required : true, rangelength : [1, 500], url : true} 
			},
			messages : { 
				awardName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.award.awardName' />",       
									 rangelength : "<ikep4j:message key='Size.award.awardName' />"}, 
				awardEnglishName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.award.awardEnglishName' />",       
									rangelength : "<ikep4j:message key='Size.award.awardEnglishName' />"}, 
				awardDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.award.awardDescription' />"}, 
				awardEnglishDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.award.awardEnglishDescription' />"}, 
				url              : {
					direction   : "bottom",  
					required    : "<ikep4j:message key='NotEmpty.award.url' />",  
					rangelength : "<ikep4j:message key='Size.award.url' />", 
					url         : "<ikep4j:message key='Url.award.url' />"  
				}
			},
			</c:if>
			<c:if test="${award.awardType eq 2}">
			rules  : {
				awardName : {required : true, rangelength : [1, 100]},
				awardEnglishName : {required : true, rangelength : [1, 100]},
				awardDescription : { rangelength : [1, 2000] },  
				awardEnglishDescription : { rangelength : [1, 2000] }   
			},
			messages : { 
				awardName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.award.awardName' />",        
									rangelength : "<ikep4j:message key='Size.award.awardName' />"}, 
				awardEnglishName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.award.awardEnglishName' />",        
									rangelength : "<ikep4j:message key='Size.award.awardEnglishName' />"}, 
			    awardDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.award.awardDescription' />"},
			    awardEnglishDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.award.awardEnglishDescription' />"} 
			},
			</c:if>
		    submitHandler : function(form) {  
		    	<c:if test="${award.awardType eq 0}">
		    	var fileAttachOption = $("input[name=fileAttachOption]:checked").val();
		    	
		    	var allowType = $("input[name=allowType]").val();
		    	
		    	var allowFileType = ["txt", "img", "doc", "ado", "vod", "comp", "app", "all"];
		    	
		    	if(fileAttachOption == "1" ) { 
		    		var array = allowType.split(",");
		    		var exist = false;
		    		
		    		for(var lindex = 0, lmax = array.length; lindex < lmax; lindex++ ) { 
						for(var sindex = 0, smax = allowFileType.length; sindex < smax; sindex++ ) { 
							if(array[lindex] == allowFileType[sindex]) {
								exist = true;
							} 
						} 
						if(!exist) {
							alert("<ikep4j:message key='NotAllowedFileType.award.allowType' />");
							return false;
						}
		    		}  
		    	} else if (fileAttachOption == "0") { 
		    		if(allowType.match(/[0-9]/gi) != null) {
		    			alert("<ikep4j:message key='NotAllowedFileExtend.award.allowType' />");
						return false;	
		    			
		    		} 
		    	}
		    	</c:if>
		    	$("body").ajaxLoadStart("button");
		        form.submit();
		    }
		});   
		
		//커서 포커스를 첫번째 Input에 넣는다.
		$("input[name=awardName]").focus();
		
		$("a[name=createAwardButton]").click(function() {   
			if($("input[name=readPermission]:checked").val() == "0" && $("#selectReadUserList option").length + $("#selectReadGroupList option").length + $("#addrReadRoleList option").length == 0 ) {
				alert('<ikep4j:message pre='${preMessage}' key='notInputedReadPermission'/>');
				return false;
			}
			if($("input[name=writePermission]:checked").val() == "0" && $("#selectWriteUserList option").length + $("#selectWriteGroupList option").length + $("#addrWriteRoleList option").length == 0 ) {
				alert('<ikep4j:message pre='${preMessage}' key='notInputedWritePermission'/>');
				return false;
			}
			
			$("input[name^=readUserList], input[name^=readGroupList], input[name^=readRoleList], input[name^=writeRoleList], input[name^=writeUserList], input[name^=writeGroupList]").remove();
			
			$("#selectReadUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='readUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#awardForm" );   
				$("<input name='readUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#awardForm" );   
				$("<input name='readUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#awardForm" );   
				$("<input name='readUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#awardForm" );   
				$("<input name='readUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#awardForm" );   
			});
			 
			
			$("#selectReadGroupList option").each(function(index) {
				var groupInfo = $(this).attr("id").split("^"); 
				$("<input name='readGroupList[" + index + "].groupId'     type='hidden' value='"+ groupInfo[0] +"' />").appendTo( "#awardForm" );   
				$("<input name='readGroupList[" + index + "].groupName'   type='hidden' value='"+ groupInfo[1] +"' />").appendTo( "#awardForm" ); 
				
				if($(this).hasClass("hierarchied")) {
					$("<input name='readGroupList[" + index + "].hierarchied' type='hidden' value='1' />").appendTo( "#awardForm" );    
				} else {
					$("<input name='readGroupList[" + index + "].hierarchied' type='hidden' value='0' />").appendTo( "#awardForm" );  
				}
				
			}); 
			
			$("#addrReadRoleList option").each(function(index) {
				var roleInfo = $(this).attr("value"); 
				$("<input name='readRoleList[" + index + "].roleId'     type='hidden' value='"+ roleInfo +"' />").appendTo( "#awardForm" );   

			}); 
			
			$("#addrWriteRoleList option").each(function(index) {
				var roleInfo = $(this).attr("value"); 
				$("<input name='writeRoleList[" + index + "].roleId'     type='hidden' value='"+ roleInfo +"' />").appendTo( "#awardForm" );   

			}); 
			
			$("#selectWriteUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='writeUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#awardForm" );   
				$("<input name='writeUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#awardForm" );   
				$("<input name='writeUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#awardForm" );   
				$("<input name='writeUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#awardForm" );   
				$("<input name='writeUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#awardForm" );   
			});
			
			$("#selectWriteGroupList option").each(function(index) {
				var groupInfo = $(this).attr("id").split("^");
				
				$("<input name='writeGroupList[" + index + "].groupId'     type='hidden' value='"+ groupInfo[0] +"' />").appendTo( "#awardForm" );   
				$("<input name='writeGroupList[" + index + "].groupName'   type='hidden' value='"+ groupInfo[1] +"' />").appendTo( "#awardForm" );  
				
				if($(this).hasClass("hierarchied")) {
					$("<input name='writeGroupList[" + index + "].hierarchied' type='hidden' value='1' />").appendTo( "#awardForm" );    
				} else {
					$("<input name='writeGroupList[" + index + "].hierarchied' type='hidden' value='0' />").appendTo( "#awardForm" );  
				}  
			}); 
			 
			<c:if test="${award.awardType eq 0}">
			$("#awardForm").attr("action", "<c:url value='/lightpack/award/awardAdmin/createAwardTypeAward.do'/>");
			</c:if>
			<c:if test="${award.awardType eq 1}">
			$("#awardForm").attr("action", "<c:url value='/lightpack/award/awardAdmin/createLinkTypeAward.do'/>");
			</c:if>
			<c:if test="${award.awardType eq 2}">
			$("#awardForm").attr("action", "<c:url value='/lightpack/award/awardAdmin/createCategoryTypeAward.do'/>");
			</c:if>	 
			
			$("#awardForm").trigger("submit"); 
			
			return false; 
		}); 
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addReadUserButton").click( function() {
			
			var items = [];
			$jq("select[name=selectReadUserList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setReadUserAddress, items, {selectType:"user", tabs:{common:1}});
		});
		
		$("#readUserName").keypress( function(event) {  
			iKEP.searchUser(event, "Y", setReadUser); 
		}); 
        
		$("#searchReadUserButton").click( function() {  
			$("#readUserName").trigger("keypress");
		});
		
		$("#deleteReadUserButton").click( function() {  
			$("#selectReadUserList option:selected").remove();
			$("#selectReadUserListCount").text($("#selectReadUserList option").length);
		});
	
		
		
		$jq('#readRoleName').keypress( function(event) { 
			iKEP.searchRole(event, "Y", setReadRole); 
		});
		
		$jq('#readRoleSearchBtn').click( function() { 
			$jq('#readRoleName').trigger("keypress");
		});
		
		$jq('#readRoleSearchBtnAll').click(function(event) { 
			iKEP.popupRole("", "Y", setReadRole);	
		});
		
		$jq('#readRoleNameDelete').click(function(event) { 
			$("#addrReadRoleList option:selected").remove();
			$("#readRoleNameCount").text($("#addrReadRoleList option").length);
		});
		
		$jq('#writeRoleName').keypress( function(event) { 
			iKEP.searchRole(event, "Y", setWriteRole); 
		});
		
		$jq('#writeRoleSearchBtn').click( function() { 
			$jq('#writeRoleName').trigger("keypress");
		});
		
		$jq('#writeRoleSearchBtnAll').click(function(event) { 
			iKEP.popupRole("", "Y", setWriteRole);	
		});
		
		$jq('#writeRoleNameDelete').click(function(event) { 
			$("#addrWriteRoleList option:selected").remove();
			$("#writeRoleNameCount").text($("#addrWriteRoleList option").length);
		});
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addReadGroupButton").click( function() {
			
			var items = [];
			$jq("select[name=selectReadGroupList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setReadGroupAddress, items, {selectType:"group", tabs:{common:1}});
		});
		
		$("#readGroupName").keypress( function(event) {  
			iKEP.searchGroupType(event, "Y", setReadGroup); 

		}); 
        
		$("#searchReadGroupButton").click( function() {  
			$("#readGroupName").trigger("keypress");
		});
		
		$("#deleteReadGroupButton").click( function() {   
			$("#selectReadGroupList option:selected").remove();
			$("#selectReadGroupListCount").text($("#selectReadGroupList option").length);
		});
		 
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addWriteUserButton").click( function() {
			
			var items = [];
			$jq("select[name=selectWriteUserList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setWriteUserAddress, items, {selectType:"user", tabs:{common:1}});
		});
		
		$("#writeUserName").keypress( function(event) {  
			iKEP.searchUser(event, "Y", setWriteUser); 
		}); 
        
		$("#searchWriteUserButton").click( function() {  
			$("#writeUserName").trigger("keypress");
		});
		
		$("#searchItemButton").click( function() {  

			//var url = iKEP.getContextRoot() + '/support/favorite/getShortcut.do';
			//var url = "<c:url value='/lightpack/award/awardAdmin/listCategoryView.do'/>";
			//iKEP.popupOpen(url , {width:500, height:500});
			//setTimeout(mailSyncState, 10000);
		});
		
		$("#deleteWriteUserButton").click( function() {  
			$("#selectWriteUserList option:selected").remove();
			$("#selectWriteUserListCount").text(displayUserCountText($("#selectWriteUserList option").length));
		}); 
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addWriteGroupButton").click( function() {
			
			var items = [];
			$jq("select[name=selectWriteGroupList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setWriteGroupAddress, items, {selectType:"group", tabs:{common:1}});
		});
		
		$("#writeGroupName").keypress( function(event) {  
			iKEP.searchGroupType(event, "Y", setWriteGroup);  
		});
        
		$("#searchWriteGroupButton").click( function() {  
			$("#writeGroupName").trigger("keypress");
		});
		
		$("#deleteWriteGroupButton").click( function() {   
			$("#selectWriteGroupList option:selected").remove();
			$("#selectWriteGroupListCount").text(displayGroupCountText($("#selectWriteGroupList option").length));
		});
		<c:if test="${award.awardType eq 0}">
			$("input[name=writePermission]").click(function() { 
				displayWritePermissionTableRow();
			}); 
			
			$("input[name=readPermission]").click(function() { 
				displayReadPermissionTableRow();
			});
			
			$("input[name=wordHead]").click(function() { 
				displayCategoryTableRow();
			});
		</c:if>
		<c:if test="${award.awardType eq 2}">
			$("input[name=readPermission]").click(function() { 
				displayReadPermissionTableRow();
			});
		</c:if>
		$("input[name=fileAttachOption]").click(function () { 
			if($(this).val() == "1") {
				$("input[name=allowType]").val("all");	
			} else {
				$("input[name=allowType]").val("doc,ppt,excel,pdf,hwp,txt,zip,jpg,gif");	
			}
			
			displayAllowTypeHelpBox();  
			
		});
		
		
		$("input[name=contentsReadPermission]").click(function () { 
			
			contentsReadPermissionUnUse();
			
		});
		
		$("#writeChangeHierarchy").click(function() {
		
			$("#selectWriteGroupList option:selected").each(function() {
				var groupInfo = $(this).attr("id").split("^");
				
				if($(this).hasClass("hierarchied")) { 
					$(this).removeClass("hierarchied");
					$(this).text(groupInfo[1]);
				} else { 
					$(this).addClass("hierarchied");
					$(this).text("[H]" + groupInfo[1]);
				} 
			}); 
		});
		
		$("#readChangeHierarchy").click(function() {
			$("#selectReadGroupList option:selected").each(function() {
				var groupInfo = $(this).attr("id").split("^");
				
				if($(this).hasClass("hierarchied")) { 
					$(this).removeClass("hierarchied");
					$(this).text(groupInfo[1]);
				} else { 
					$(this).addClass("hierarchied");
					$(this).text("[H]" + groupInfo[1]);
				} 
			}); 
		});
		
		$("input[name=allowType]").val("all");	
		
		iKEP.iFrameContentResize();
		
		return false;  
		
	});
	
})(jQuery);

function changeCategoryList(optionTextList) {
	if(optionTextList != null && optionTextList.length > 0){
		$jq("#categoryId").empty();
		
		for(var i = 0; i < optionTextList.length; i++) {
			$jq("#categoryId").append("<option value=''>"+optionTextList[i]+"</option>");
		}
	}
	
}
//-->
</script> 
	<!--pageTitle Start-->
	<div id="pageTitle"> 
		<h2><ikep4j:message pre="${preHeader}" key="title" /></h2>
	</div> 
	<!--//pageTitle End--> 
	<form id="awardForm" method="post" action="null">  
	<div class="blockDetail">
		<input type="hidden" name="optionText" class="inputbox w20" style="width:137px !important;" /> 
		<spring:bind path="award.portalId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
		</spring:bind> 
		<c:if test="${not empty award.portalId}">
			<spring:bind path="award.awardParentId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
			</spring:bind> 		
		</c:if>	
		<spring:bind path="award.awardType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>				
		<spring:bind path="award.awardRootId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 
		<spring:bind path="award.portlet">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 		
		<spring:bind path="award.awardDelete">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 		
		<table summary="<ikep4j:message pre='${preDetail}' key='summary' />"> 
			<caption></caption>
			<colgroup>
				<col style="width: 10%;"/>
				<col style="width: 8%; "/>
				<col style="width: 82%;"/> 			
			</colgroup>  
			<spring:bind path="award.parentList">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='awardPath' /></th>
				<td>
					<c:forEach var="awardItem" varStatus="varStatus" items="${status.value}">  
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">${awardItem.awardName}</c:when>
							<c:otherwise>${awardItem.awardEnglishName}</c:otherwise>
						</c:choose>
						<c:if test="${not varStatus.last}"> ></c:if> 
					</c:forEach> 
				</td> 
			</tr>			
			</spring:bind> 	
			<spring:bind path="award.awardName">
			<tr> 
				<th scope="row" colspan="2"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>	
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>  
				</td> 
			</tr>				
			</spring:bind> 
			<spring:bind path="award.awardEnglishName">
			<tr> 
				<th scope="row" colspan="2"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>	
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>  
				</td> 
			</tr>				
			</spring:bind> 
			<c:if test="${award.awardType eq 0 or award.awardType eq 1 }">
			<spring:bind path="award.awardDescription">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox w100" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>	
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			<spring:bind path="award.awardEnglishDescription">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox w100" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>	
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			</c:if>
			<c:if test="${award.awardType eq 1 }">
 			<spring:bind path="award.url">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox  w100" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/> 
					</div>
					<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
				    </c:forEach>  					
				</td>  
			</tr>		
			</spring:bind>		
			<spring:bind path="award.awardPopup">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<c:forEach var="code" items="${awardCode.useUnuseList}"> 
							<label>
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code.key}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							<c:if test="${code.key eq status.value}">checked="checked"</c:if>
							/> 
							<ikep4j:message key='${code.value}'/>
							</label>
						</c:forEach> 
					</div> 					
				</td>  
			</tr>				
			</spring:bind> 	 
			</c:if>
			
			<c:if test="${award.awardType eq 2 }">
			<spring:bind path="award.readPermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					
					<c:forEach var="code" items="${awardCode.openClosedList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>
				</td>  
			</tr>	 			
			</spring:bind> 
			<tr class="readPermissionTableRow"> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td> 
					<input id="readUserName" name="readUserName" title="<ikep4j:message pre='${preDetail}' key='user' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchReadUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addReadUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5"> 
						<select title="select" id="selectReadUserList" name="selectReadUserList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='user' />" >  			
							<option class="dummy"></option> 
							<c:forEach var="user" items="${award.readUserList}"> 
								<option value="${userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteReadUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(award.readUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
					</div>	 
				</td>   
			</tr>
			<tr class="readPermissionTableRow">  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td>
					<input id="readGroupName" name="readGroupName" title="<ikep4j:message pre='${preDetail}' key='group' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchReadGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addReadGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
	  				<div class="mt5">  
						<select title="select" id="selectReadGroupList" name="selectReadGroupList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='group' />" >   
							<option class="dummy"></option> 
							<c:forEach var="group" items="${award.readGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
								</option>
							</c:forEach>  
						</select>					 
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="readChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteReadGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(award.readGroupList)}<ikep4j:message pre='${preDetail}' key='group' /></span> 
				 	</div>			 
				</td>
			</tr>	
			<tr class="readPermissionTableRow">
					<th scope="row">역할</th>
					<td>
						<input name="readRoleName" id="readRoleName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" >
							<span id="readRoleSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt=""/>
								<ikep4j:message pre="${preButton}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" >
							<span id="readRoleSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt=""/>
								List
							</span>
						</a>
						<div class="mt5">
							
								<select name="addrReadRoleList" id="addrReadRoleList" size="5" multiple="multiple" class="multi w60"  >
								</select>
							
								<a href="#" class="button_ic valign_bottom" onclick="return false;">
									<span id="readRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
								</a>
								<span class="valign_bottom">총 <span id="readRoleNameCount">0</span> 역할</span>
		
						</div>								
					</td>
				</tr>
			</c:if>
			
			<c:if test="${award.awardType eq 0}"> 
			<spring:bind path="award.listType">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td> 
					<div>
					<c:forEach var="code" items="${awardCode.listTypeList}"> 
						<input name="${status.expression}" type="radio" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> /> <ikep4j:message key='${code.value}'/>
					</c:forEach>
					</div>
				</td>  
			</tr>				
			</spring:bind>	 
			<spring:bind path="award.anonymous">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach>
					</div> 				
				</td> 
			</tr>				
			</spring:bind> 
			<spring:bind path="award.rss">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td> 
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>	
				</td> 
			</tr> 
			</spring:bind>  
			<spring:bind path="award.fileSize">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<select title="select" name="${status.expression}">
					<c:forEach var="code" items="${awardCode.fileSizeList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach> 
					</select> 
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>	 
			<spring:bind path="award.imageFileSize">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<select title="select" name="${status.expression}">
					<c:forEach var="code" items="${awardCode.imageFileSizeList}">
						<option  value="${code.key}"  <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select> 
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div> 
				</td> 
			</tr>				
			</spring:bind>	
			
			<spring:bind path="award.imageWidth">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<select title="select" name="${status.expression}">
					<c:forEach var="code" items="${awardCode.imageWidthList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select>
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div>
				</td>  
			</tr>				
			</spring:bind>	 
			<spring:bind path="award.pageNum">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<select title="select" name="${status.expression}">
					<c:forEach var="code" items="${awardCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<c:if test="${award.awardType eq 1 }"> 
			<spring:bind path="award.docPopup">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th> 
				<td> 
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</label>		
					</c:forEach>
					</div>  
				</td> 
			</tr>				
			</spring:bind>
			</c:if>	 
			<spring:bind path="award.reply">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<spring:bind path="award.linereply">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<spring:bind path="award.contentsReadPermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					<span class="tdInstruction"></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<spring:bind path="award.contentsReadMail">
			<tr> 
				<th scope="row" colspan="2">독서자 메일 발송</th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					<span class="tdInstruction"></span>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<%/*%>
			<spring:bind path="award.portlet">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.yesNoList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
					</div>
				</td> 
			</tr>				
			</spring:bind> 
			<spring:bind path="award.restrictionType">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<input name="${status.expression}"  
					type="text" 
					class="inputbox w100"  
					value="${status.value}" 
					size="40" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></div>
				</td> 
			</tr>				
			</spring:bind>	
			
			<spring:bind path="award.move"> 
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<%*/%> 
			<spring:bind path="award.wordHead"> 
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					&nbsp;&nbsp;
					<select id="categoryId" name="categoryId" <c:if test="${award.wordHead !=1 }">style="display:none"</c:if>>
						<c:forEach var="code" items="${awardItemCategoryList}">
							<option value="${code.categoryId}">${code.categoryName}</option>
						</c:forEach>
					</select>
					&nbsp;
					<a id="searchItemButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='categoryManage'/>"><span><ikep4j:message pre='${preButton}' key='categoryManage'/></span></a>
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			

			<spring:bind path="award.fileAttachOption"> 
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${awardCode.attechFileOptionList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<spring:bind path="award.allowType">
			<tr> 
				<th scope="row" colspan="2"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					<input name="${status.expression}"  
					type="text" 
					class="inputbox w100"  
					value="${status.value}" 
					size="40" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<div id="fileExtensionHelpBox">
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help1" /></div>  
					</div>
					<div id="fileTypeHelpBox">
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help2" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help3" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help4" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help5" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help6" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help7" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help8" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help9" /></div> 				
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help10" /></div> 
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help11" /></div> 
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help12" /></div> 
					</div>				
				</td> 
			</tr>				
			</spring:bind>	 
			<spring:bind path="award.readPermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					
					<c:forEach var="code" items="${awardCode.openClosedList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>
				</td>  
			</tr>	 			
			</spring:bind> 
			<tr class="readPermissionTableRow"> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td> 
					<input id="readUserName" name="readUserName" title="<ikep4j:message pre='${preDetail}' key='user' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchReadUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addReadUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5"> 
						<select title="select" id="selectReadUserList" name="selectReadUserList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='user' />" >  			
							<option class="dummy"></option> 
							<c:forEach var="user" items="${award.readUserList}"> 
								<option value="${userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteReadUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(award.readUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
					</div>	 
				</td>   
			</tr>
			<tr class="readPermissionTableRow">  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td>
					<input id="readGroupName" name="readGroupName" title="<ikep4j:message pre='${preDetail}' key='group' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchReadGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addReadGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
	  				<div class="mt5">  
						<select title="select" id="selectReadGroupList" name="selectReadGroupList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='group' />" >   
							<option class="dummy"></option> 
							<c:forEach var="group" items="${award.readGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
								</option>
							</c:forEach>  
						</select>					 
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="readChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteReadGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(award.readGroupList)}<ikep4j:message pre='${preDetail}' key='group' /></span> 
				 	</div>			 
				</td>
			</tr>	
			<tr class="readPermissionTableRow">
				<th scope="row">역할</th>
				<td>
					<input name="readRoleName" id="readRoleName" class="inputbox" type="text" size="20" />
					<a href="#" class="button_ic" onclick="return false;" >
						<span id="readRoleSearchBtn">
							<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt=""/>
							<ikep4j:message pre="${preButton}" key="search" />
						</span>
					</a>
					<a href="#" class="button_ic" onclick="return false;" >
						<span id="readRoleSearchBtnAll">
							<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt=""/>
							List
						</span>
					</a>
					<div class="mt5">
						
							<select name="addrReadRoleList" id="addrReadRoleList" size="5" multiple="multiple" class="multi w60"  >
							</select>
						
							<a href="#" class="button_ic valign_bottom" onclick="return false;">
								<span id="readRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
							</a>
							<span class="valign_bottom">총 <span id="readRoleNameCount">0</span> 역할</span>
	
					</div>								
				</td>
			</tr>
			 		
			<spring:bind path="award.writePermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${awardCode.openClosedList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
				</td>  
			</tr>				
			</spring:bind>   
			<tr class="writePermissionTableRow"> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='writePermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td> 
					<input id="writeUserName" name="writeUserName" title="<ikep4j:message pre='${preDetail}' key='user' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchWriteUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addWriteUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5">  
						<select title="select" id="selectWriteUserList" name="selectWriteUserList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='user' />" >
							<option class="dummy"></option>  			
							<c:forEach var="user" items="${award.writeUserList}"> 
								<option value="${userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteWriteUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectWriteUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(award.writeUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
					</div>	 
				</td>   
			</tr>
			<tr class="writePermissionTableRow">  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th>   
				<td> 
					<input id="writeGroupName" name="writeGroupName" title="<ikep4j:message pre='${preDetail}' key='group' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchWriteGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addWriteGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5">  
						<select title="select" id="selectWriteGroupList" name="selectWriteGroupList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='group' />" >   
							<option class="dummy"></option>
							<c:forEach var="group" items="${award.writeGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
								</option>
							</c:forEach>  
						</select>		  
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="writeChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${preButton}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteWriteGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span  id="selectWriteGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(award.writeGroupList)} <ikep4j:message pre='${preDetail}' key='group' /></span> 
				 	</div>	   
				</td>
			</tr> 
			<tr class="writePermissionTableRow">
					<th scope="row">역할</th>
					<td>
						<input name="writeRoleName" id="writeRoleName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" >
							<span id="writeRoleSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt=""/>
								<ikep4j:message pre="${preButton}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" >
							<span id="writeRoleSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt=""/>
								List
							</span>
						</a>
						<div class="mt5">
							
								<select name="addrWriteRoleList" id="addrWriteRoleList" size="5" multiple="multiple" class="multi w60"  >
								</select>
							
								<a href="#" class="button_ic valign_bottom" onclick="return false;">
									<span id="writeRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
								</a>
								<span class="valign_bottom">총 <span id="writeRoleNameCount">0</span> 역할</span>
		
						</div>								
					</td>
				</tr>
				
				<spring:bind path="award.mobileUse"> 
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${awardCode.useUnuseList}"> 
							<label>
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code.key}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							<c:if test="${code.key eq status.value}">checked="checked"</c:if>
							/> 
							<ikep4j:message key='${code.value}'/>
							</label>
						</c:forEach>  
						</div>
					</td> 
				</tr>				
				</spring:bind>	
				<spring:bind path="award.mobileWriteUse"> 
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${awardCode.useUnuseList}"> 
							<label>
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code.key}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							<c:if test="${code.key eq status.value}">checked="checked"</c:if>
							/> 
							<ikep4j:message key='${code.value}'/>
							</label>
						</c:forEach> 
						</div>
					</td> 
				</tr>				
				</spring:bind>	
			</c:if>
		</table>   
	</div>  
	<!--//blockDetail End-->
	</form>
	<!--blockButton Start-->
	<div class="blockButton" id="divButton"> 
		<ul> 
			<li>
				<a name="createAwardButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span>
				</a>
			</li>
			<li>
				<a name="listAwardViewButton" class="button" href="<c:url value='/lightpack/award/awardAdmin/listAwardView.do?awardRootId=0'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span>
				</a>
			</li>			
		</ul>
	</div>
	<!--//blockButton End--> 