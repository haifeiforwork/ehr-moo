<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.updateBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.updateBoardView.detail" />
<c:set var="preDetail2"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />  

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript"> 
<!--  
(function($) {    
	
setReadUser = function(data) {   
		
		var $sel = $jq("select[name=selectReadUserList]");
		
		$.each(data, function(index, value) {  
			if($("#selectReadUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);   
			} 
		})   
		
		$("#selectReadUserListCount").text(displayUserCountText($("#selectReadUserList option").length));
	};
	
	setReadUserAddress = function(data) {   
		
		var $sel = $jq("select[name=selectReadUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
			
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		$("#selectReadUserListCount").text(displayUserCountText($("#selectReadUserList option").length));
	};
	
	setReadGroupAddress = function(data) { 
		
		var $sel = $jq("select[name=selectReadGroupList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.groupBoardOption, this).appendTo($sel).data("data", this); 
		});
		$("#selectReadGroupListCount").text(displayGroupCountText($("#selectReadGroupList option").length));
	}; 
	
	setReadGroup = function(data) {   
		
		var $sel = $jq("select[name=selectReadGroupList]");
		
		$.each(data, function(index, value) {  

			if($("#selectReadGroupList option[value=" + value.code + "]").length == 0) {
				$.tmpl(iKEP.template.groupBoardOption, this)
				.appendTo($sel)
				.data("data", this); 
			} 
		}) 
		$("#selectReadGroupListCount").text(displayUserCountText($("#selectReadGroupList option").length));
	};  
	
	setWriteUser = function(data) {  
		
		var $sel = $jq("select[name=selectWriteUserList]");
		
		$.each(data, function(index, value) {  
			if($("#selectWriteUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);      
			} 
		})   
		
		$("#selectWriteUserListCount").text(displayUserCountText($("#selectWriteUserList option").length));
	};
	
	setWriteUserAddress = function(data) {  
		
		var $sel = $jq("select[name=selectWriteUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		
		$("#selectWriteUserListCount").text(displayUserCountText($("#selectWriteUserList option").length));
	};
	
	setWriteGroupAddress = function(data) { 
		
		var $sel = $jq("select[name=selectWriteGroupList]").children().remove().end();
		
		$.each(data, function(index, value) {  
			$.tmpl(iKEP.template.groupBoardOption, this)
			.appendTo($sel)
			.data("data", this); 
		});
		$("#selectWriteGroupListCount").text(displayGroupCountText($("#selectWriteGroupList option").length));
	}; 
	
	setWriteGroup = function(data) { 
		
		var $sel = $jq("select[name=selectWriteGroupList]");
		
		$.each(data, function(index, value) {  
			
			if($("#selectWriteGroupList option[value=" + value.code + "]").length == 0) {
				$.tmpl(iKEP.template.groupBoardOption, this)
				.appendTo($sel)
				.data("data", this);    
			} 
		}) 
		$("#selectWriteGroupListCount").text(displayGroupCountText($("#selectWriteGroupList option").length));
	}; 
	
	
	setAdminUser = function(data) {  
		
		var $sel = $jq("select[name=selectAdminUserList]");
		
		$.each(data, function(index, value) {  
			if($("#selectAdminUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);      
			} 
		})   
		
		$("#selectAdminUserListCount").text(displayUserCountText($("#selectAdminUserList option").length));
	};
	
	setAdminUserAddress = function(data) {  
		
		var $sel = $jq("select[name=selectAdminUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		
		$("#selectAdminUserListCount").text(displayUserCountText($("#selectAdminUserList option").length));
	};
	
	setAdminGroupAddress = function(data) { 
		
		var $sel = $jq("select[name=selectAdminGroupList]").children().remove().end();
		
		$.each(data, function(index, value) {  
			$.tmpl(iKEP.template.groupBoardOption, this)
			.appendTo($sel)
			.data("data", this); 
		});
		$("#selectAdminGroupListCount").text(displayGroupCountText($("#selectAdminGroupList option").length));
	}; 
	
	setAdminGroup = function(data) {  
		
		var $sel = $jq("select[name=selectAdminGroupList]");
		
		$.each(data, function(index, value) {  
			
			if($("#selectAdminGroupList option[value=" + value.code + "]").length == 0) {
				$.tmpl(iKEP.template.groupBoardOption, this)
				.appendTo($sel)
				.data("data", this);    
			} 
		}) 
		$("#selectAdminGroupListCount").text(displayGroupCountText($("#selectAdminGroupList option").length));
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
	
	setAdminRole = function(data) {
		var $sel = $jq("select[name='addrAdminRoleList']");
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
			
			$jq("#adminRoleNameCount").html($sel.children().length);
		} else {
			alert("검색어에 해당하는 역할이 없습니다.");
		}
	};
	
	setAddressInit = function() {
		
		var addrObj;
		var $sel;
		
		$sel = $jq("select[name=selectReadUserList]").children().remove().end();
		
		<c:forEach var="user" items="${board.readUserList}"> 
			addrObj = $.parseJSON('{"type":"user","id":"${user.userId}","userName":"${user.userName}","jobTitleName":"${user.jobTitleName}","teamName":"${user.teamName}"}');
			$.tmpl(iKEP.template.userBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
		
		$sel = $jq("select[name=selectReadGroupList]").children().remove().end();
		
		<c:forEach var="group" items="${board.readGroupList}"> 
			addrObj = $.parseJSON('{"type":"group","code":"${group.groupId}","name":"${group.groupName}"}');
			<c:if test="${group.hierarchied ne 1}">
			$.tmpl(iKEP.template.groupBoardOption, addrObj).appendTo($sel).data("data", addrObj);
			</c:if>
			<c:if test="${group.hierarchied eq 1}">
			$.tmpl(iKEP.template.higroupBoardOption, addrObj).appendTo($sel).data("data", addrObj);
			</c:if>
			
		</c:forEach>
		
		$sel = $jq("select[name=selectWriteUserList]").children().remove().end();
		
		<c:forEach var="user" items="${board.writeUserList}"> 
			addrObj = $.parseJSON('{"type":"user","id":"${user.userId}","userName":"${user.userName}","jobTitleName":"${user.jobTitleName}","teamName":"${user.teamName}"}');
			$.tmpl(iKEP.template.userBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
		
		$sel = $jq("select[name=selectWriteGroupList]").children().remove().end();
		
		<c:forEach var="group" items="${board.writeGroupList}"> 
			addrObj = $.parseJSON('{"type":"group","code":"${group.groupId}","name":"${group.groupName}"}');
			$.tmpl(iKEP.template.groupBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
		
	
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
	
	<c:if test="${board.boardType eq 0}">
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
		
		displayAdminPermissionTableRow = function() {
			if($("input[name=adminPermission]:checked").attr("value") == "0") {
				$(".adminPermissionTableRow").show();
				$(".adminPermissionTableRow").children().show();
				//$("input[name=readPermission]").attr("disabled",false);
			} else {
				$(".adminPermissionTableRow").children().hide();
				$(".adminPermissionTableRow").hide();
				//$("#selectWriteUserList option").remove();
				//$("#selectWriteGroupList option").remove();
				//등록 권한 부여시 조회 권한  부여
				//$("input[name=readPermission]:eq(0)").attr("checked", true);
				//displayReadPermissionTableRow();
				//$("input[name=readPermission]").attr("disabled",true);
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
			} else {
				$("#categoryId").hide();
				$("#searchItemButton").hide();
				//$("#selectReadUserList option").remove();
				//$("#selectReadGroupList option").remove();
			} 
			
			iKEP.iFrameContentResize(); 
		};
	</c:if>
	
	<c:if test="${board.boardType eq 2}">
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
	
	$(document).ready(function() {  
		window.parent.topScroll(); 
		
		<c:if test="${board.boardType eq 0}">
			displayReadPermissionTableRow();
			displayWritePermissionTableRow();
			displayAdminPermissionTableRow();
			displayCategoryTableRow();
		</c:if>
		<c:if test="${board.boardType eq 2}">
			displayReadPermissionTableRow();
		</c:if>	
		
			displayAllowTypeHelpBox();
		
		$("option.dummy").remove();
		
		new iKEP.Validator("#boardForm", {
			<c:if test="${board.boardType eq 0}">
			rules  : {
				boardName        : { required : true, rangelength : [1, 100] },  
				boardEnglishName        : { required : true, rangelength : [1, 100] },  
				boardDescription : { rangelength : [1, 2000] },  
				boardEnglishDescription : { rangelength : [1, 2000] },
				allowType        : { required : true, rangelength : [1, 50] }   
			},
			messages : { 
				boardName        : {direction : "right",  required : "<ikep4j:message key='NotEmpty.board.boardName' />",        
									rangelength : "<ikep4j:message key='Size.board.boardName' />"}, 
				boardEnglishName        : {direction : "right",  required : "<ikep4j:message key='NotEmpty.board.boardEnglishName' />",        
									rangelength : "<ikep4j:message key='Size.board.boardEnglishName' />"}, 
	            boardDescription : {direction : "left",   rangelength : "<ikep4j:message key='Size.board.boardDescription' />"},
	            boardEnglishDescription : {direction : "left",   rangelength : "<ikep4j:message key='Size.board.boardEnglishDescription' />"}, 
	            allowType        : {
	            	direction : "bottom", 
	            	required : "<ikep4j:message key='NotEmpty.board.allowType' />",  
	            	rangelength : "<ikep4j:message key='Size.board.allowType' />"}
			},
			</c:if>
			<c:if test="${board.boardType eq 1}">
			rules  : {
				boardName        : {required : true, rangelength : [1, 100] },   
				boardEnglishName        : {required : true, rangelength : [1, 100] }, 
				boardDescription : {rangelength : [1, 2000] },   
				boardEnglishDescription : {rangelength : [1, 2000] },   
				url              : {required : true, rangelength : [1, 500], url : true } 
			},
			messages : { 
				boardName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.board.boardName' />",        
									rangelength : "<ikep4j:message key='Size.board.boardName' />"}, 
				boardEnglishName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.board.boardEnglishName' />",        
									rangelength : "<ikep4j:message key='Size.board.boardEnglishName' />"},
		        boardDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.board.boardDescription' />"}, 
		        boardEnglishDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.board.boardEnglishDescription' />"},
				url              : {
					direction : "bottom",  
					required    : "<ikep4j:message key='NotEmpty.board.url' />",  
					rangelength : "<ikep4j:message key='Size.board.url' />", 
					url         : "<ikep4j:message key='Url.board.url' />"  
				}
			},
			</c:if>
			<c:if test="${board.boardType eq 2}">
			rules  : {
				boardName : {required : true, rangelength : [1, 100]},
				boardEnglishName : {required : true, rangelength : [1, 100]},
				boardDescription : {rangelength : [1, 2000] },   
				boardEnglishDescription : {rangelength : [1, 2000] }
			},
			messages : { 
				boardName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.board.boardName' />",        
								rangelength : "<ikep4j:message key='Size.board.boardName' />"}, 
				boardEnglishName        : {direction : "right", required : "<ikep4j:message key='NotEmpty.board.boardEnglishName' />",        
								rangelength : "<ikep4j:message key='Size.board.boardEnglishName' />"},
			    boardDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.board.boardDescription' />"},
			    boardEnglishDescription : {direction : "left",  rangelength : "<ikep4j:message key='Size.board.boardEnglishDescription' />"}
			},
			</c:if>
		    submitHandler : function(form) {   
		        <c:if test="${board.boardType eq 0}">
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
							alert("<ikep4j:message key='NotAllowedFileType.board.allowType' />");
							return false;
						}
		    		}  
		    	} else if (fileAttachOption == "0") { 
			    	if(allowType.match(/[0-9]/gi) != null) {
			    		alert("<ikep4j:message key='NotAllowedFileExtend.board.allowType' />");
						return false; 
			    	}
			    	
			    }	
		    	</c:if>
		    	$("body").ajaxLoadStart("button");
		        form.submit();
		    }
		});     
		//커서 포커스를 첫번째 Input에 넣는다.
		$("input[name=boardName]").focus();
		
		$("a[name=updateBoardButton]").click(function() {  
			if($("input[name=readPermission]:checked").val() == "0" && $("#selectReadUserList option").length + $("#selectReadGroupList option").length + $("#addrReadRoleList option").length== 0 ) {
				alert('<ikep4j:message pre='${preMessage}' key='notInputedReadPermission'/>');
				return false;
			}
			
			if($("input[name=writePermission]:checked").val() == "0" && $("#selectWriteUserList option").length + $("#selectWriteGroupList option").length + $("#addrWriteRoleList option").length == 0 ) {
				alert('<ikep4j:message pre='${preMessage}' key='notInputedWritePermission'/>');
				return false;
			}
			
			if($("input[name=adminPermission]:checked").val() == "0" && $("#selectAdminUserList option").length + $("#selectAdminGroupList option").length + $("#addrAdminRoleList option").length == 0 ) {
				alert('관리자 권한 사용자 혹은 그룹 정보가 입력되지 않았습니다.');
				return false;
			}
			
			$("input[name^=readUserList], input[name^=readGroupList], input[name^=writeUserList], input[name^=writeGroupList], input[name^=adminUserList], input[name^=adminGroupList]").remove();
			
			$("#selectReadUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='readUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='readUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#boardForm" );   
				$("<input name='readUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#boardForm" );   
				$("<input name='readUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#boardForm" );   
				$("<input name='readUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#boardForm" );   
			});
			
			$("#selectReadGroupList option").each(function(index) {
				var groupInfo = $(this).attr("id").split("^");
				
				$("<input name='readGroupList[" + index + "].groupId'   type='hidden' value='"+ groupInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='readGroupList[" + index + "].groupName' type='hidden' value='"+ groupInfo[1] +"' />").appendTo( "#boardForm" );    
				
				if($(this).hasClass("hierarchied")) {
					$("<input name='readGroupList[" + index + "].hierarchied' type='hidden' value='1' />").appendTo( "#boardForm" );    
				} else {
					$("<input name='readGroupList[" + index + "].hierarchied' type='hidden' value='0' />").appendTo( "#boardForm" );  
				}
 
			}); 
			$("#selectWriteUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='writeUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='writeUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#boardForm" );   
				$("<input name='writeUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#boardForm" );   
				$("<input name='writeUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#boardForm" );   
				$("<input name='writeUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#boardForm" );   
			});
			
			$("#selectWriteGroupList option").each(function(index) {
				var groupInfo = $(this).attr("id").split("^");
				
				$("<input name='writeGroupList[" + index + "].groupId'   type='hidden' value='"+ groupInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='writeGroupList[" + index + "].groupName' type='hidden' value='"+ groupInfo[1] +"' />").appendTo( "#boardForm" );    
				
				if($(this).hasClass("hierarchied")) {
					$("<input name='writeGroupList[" + index + "].hierarchied' type='hidden' value='1' />").appendTo( "#boardForm" );    
				} else {
					$("<input name='writeGroupList[" + index + "].hierarchied' type='hidden' value='0' />").appendTo( "#boardForm" );  
				}  
 
			}); 
			
			$("#selectAdminUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='adminUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='adminUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#boardForm" );   
				$("<input name='adminUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#boardForm" );   
				$("<input name='adminUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#boardForm" );   
				$("<input name='adminUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#boardForm" );   
			});
			
			$("#selectAdminGroupList option").each(function(index) {
				var groupInfo = $(this).attr("id").split("^");
				
				$("<input name='adminGroupList[" + index + "].groupId'   type='hidden' value='"+ groupInfo[0] +"' />").appendTo( "#boardForm" );   
				$("<input name='adminGroupList[" + index + "].groupName' type='hidden' value='"+ groupInfo[1] +"' />").appendTo( "#boardForm" );    
				
				if($(this).hasClass("hierarchied")) {
					$("<input name='adminGroupList[" + index + "].hierarchied' type='hidden' value='1' />").appendTo( "#boardForm" );    
				} else {
					$("<input name='adminGroupList[" + index + "].hierarchied' type='hidden' value='0' />").appendTo( "#boardForm" );  
				}  
 
			});
			 
			$("#addrReadRoleList option").each(function(index) {
				var roleInfo = $(this).attr("value"); 
				$("<input name='readRoleList[" + index + "].roleId'     type='hidden' value='"+ roleInfo +"' />").appendTo( "#boardForm" );   

			}); 
			
			$("#addrWriteRoleList option").each(function(index) {
				var roleInfo = $(this).attr("value"); 
				$("<input name='writeRoleList[" + index + "].roleId'     type='hidden' value='"+ roleInfo +"' />").appendTo( "#boardForm" );   

			}); 
			
			$("#addrAdminRoleList option").each(function(index) {
				var roleInfo = $(this).attr("value"); 
				$("<input name='adminRoleList[" + index + "].roleId'     type='hidden' value='"+ roleInfo +"' />").appendTo( "#boardForm" );   

			}); 
			
			<c:if test="${board.boardType eq 0}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/board/boardAdmin/updateBoardTypeBoard.do'/>");
			</c:if>
			<c:if test="${board.boardType eq 1}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/board/boardAdmin/updateLinkTypeBoard.do'/>");
			</c:if>
			<c:if test="${board.boardType eq 2}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/board/boardAdmin/updateCategoryTypeBoard.do'/>");
			</c:if>	 
			
			$("#boardForm").trigger("submit"); 
			
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
	
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addReadGroupButton").click( function() {
			var items = [];
			$jq("select[name=selectReadGroupList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setReadGroupAddress, items, {selectType:"group", tabs:{common:1}});
		});
		
		$("#readGroupName").live("keypress", function(event) {  
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
		
		$("#writeUserName").live("keypress", function(event) {  
			iKEP.searchUser(event, "Y", setWriteUser); 
		}); 
		
		$("#addAdminUserButton").click( function() {
			var items = [];
			$jq("select[name=selectAdminUserList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setAdminUserAddress, items, {selectType:"user", tabs:{common:1}});
		});
		
		$("#adminUserName").live("keypress", function(event) {  
			iKEP.searchUser(event, "Y", setAdminUser); 
		}); 
        
		$("#searchItemButton").click( function() {  
			var url = "<c:url value='/lightpack/board/boardAdmin/listCategoryView.do?boardId=${board.boardId}'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		$("#searchWriteUserButton").click( function() {  
			$("#writeUserName").trigger("keypress");
		});
		
		$("#deleteWriteUserButton").click( function() { 
			$("#selectWriteUserList option:selected").remove();
			$("#selectWriteUserListCount").text($("#selectWriteUserList option").length);
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
			$("#selectWriteGroupListCount").text($("#selectWriteGroupList option").length);
		});
		
		$("#searchAdminUserButton").click( function() {  
			$("#adminUserName").trigger("keypress");
		});
		
		$("#deleteAdminUserButton").click( function() {  
			$("#selectAdminUserList option:selected").remove();
			$("#selectAdminUserListCount").text($("#selectAdminUserList option").length);
		});
	
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addAdminGroupButton").click( function() {
			var items = [];
			$jq("select[name=selectAdminGroupList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setAdminGroupAddress, items, {selectType:"group", tabs:{common:1}});
		});
		
		$("#adminGroupName").keypress( function(event) {  
			iKEP.searchGroupType(event, "Y", setAdminGroup);  
		});
        
		$("#searchAdminGroupButton").click( function() {  
			$("#adminGroupName").trigger("keypress");
		});
		
		$("#deleteAdminGroupButton").click( function() {   
			$("#selectAdminGroupList option:selected").remove();
			$("#selectAdminGroupListCount").text($("#selectAdminGroupList option").length);
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
		
		$jq('#adminRoleName').keypress( function(event) { 
			iKEP.searchRole(event, "Y", setAdminRole); 
		});
		
		$jq('#adminRoleSearchBtn').click( function() { 
			$jq('#adminRoleName').trigger("keypress");
		});
		
		$jq('#adminRoleSearchBtnAll').click(function(event) { 
			iKEP.popupRole("", "Y", setAdminRole);	
		});
		
		$jq('#adminRoleNameDelete').click(function(event) { 
			$("#addrAdminRoleList option:selected").remove();
			$("#adminRoleNameCount").text($("#addrAdminRoleList option").length);
		});
		
		
		<c:if test="${board.boardType eq 0}">
			$("input[name=writePermission]").click(function() { 
				displayWritePermissionTableRow();
			}); 
			
			$("input[name=adminPermission]").click(function() { 
				displayAdminPermissionTableRow();
			}); 
			
			$("input[name=readPermission]").click(function() { 
				displayReadPermissionTableRow();
			}); 
			
			$("input[name=wordHead]").click(function() { 
				displayCategoryTableRow();
			});
		</c:if>
		
		<c:if test="${board.boardType eq 2}">
			$("input[name=readPermission]").click(function() { 
				displayReadPermissionTableRow();
			}); 
		</c:if>
		
		$("a[name=deleteBoardButton]").click(function() {    
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) { 
				$("#boardForm").submit();
			}
			
			return false; 
		});  
		
		$("a[name=listBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/board/boardAdmin/listBoardView.do?boardRootId=${board.boardRootId}'/>";
		}); 
	 
		
		$("a[name=deleteBoardButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/board/boardAdmin/deleteBoard.do?boardId=${board.boardId}'/>";
		});  
		
		$("input[name=fileAttachOption]").click(function () { 
			
			if($(this).val() == "1") {
				$("input[name=allowType]").val("all");	
			} else {
				$("input[name=allowType]").val("doc,ppt,excel,pdf,hwp,txt,zip,jpg,gif");	
			}
			
			
			displayAllowTypeHelpBox();  
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
		
		$("#adminChangeHierarchy").click(function() { 
			$("#selectAdminGroupList option:selected").each(function() {
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
		
		
		setAddressInit();
		
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
	<!--blockDetail Start-->
	<form id="boardForm" method="post" action="null">
	<div class="blockDetail"> 
			<spring:bind path="board.portalId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
			</spring:bind> 			
			<spring:bind path="board.boardType">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>				
			<spring:bind path="board.boardRootId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
			<spring:bind path="board.boardId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
			<spring:bind path="board.sortOrder">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
			<spring:bind path="board.boardParentId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
			<spring:bind path="board.portlet">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	
			<spring:bind path="board.boardDelete">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
			<table summary="<ikep4j:message pre='${preDetail}' key='summary' />"> 
				<caption></caption>
				<colgroup>
					<col style="width: 10%;"/>
					<col style="width: 8%;"/>
					<col style="width: 82%;"/> 			
				</colgroup>  
				<spring:bind path="board.parentList">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='boardPath' /></th>
					<td>
						<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}">  
							<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">${boardItem.boardName}</c:when>
								<c:otherwise>${boardItem.boardEnglishName}</c:otherwise>
							</c:choose>
							<c:if test="${not varStatus.last}"> ></c:if> 
						</c:forEach> 
					</td> 
				</tr>				
				</spring:bind> 				 		
				<spring:bind path="board.boardName">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
				<spring:bind path="board.boardEnglishName">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
				<c:if test="${board.boardType eq 0 or board.boardType eq 1 }">
				<spring:bind path="board.boardDescription">
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
				<spring:bind path="board.boardEnglishDescription">
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
				<c:if test="${board.boardType eq 1 }">
 				<spring:bind path="board.url">
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
				<spring:bind path="board.boardPopup">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
							<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				
				<c:if test="${board.boardType eq 2}"> 
					<spring:bind path="board.readPermission">
					<tr> 
						<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
						<td>
							<div>
							
							<c:forEach var="code" items="${boardCode.openClosedList}"> 
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
									<c:forEach var="user" items="${board.readUserList}"> 
										<option value="${user.userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
										${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
										</option>
									</c:forEach>  
								</select>	 
								<a id="deleteReadUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
								<span id="selectReadUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(board.readUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
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
									<c:forEach var="group" items="${board.readGroupList}"> 
										<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
										<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
										</option>
									</c:forEach>  
								</select>					 
								<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
									<span id="readChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
								</a>  
								<a id="deleteReadGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
								<span id="selectReadGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(board.readGroupList)}<ikep4j:message pre='${preDetail}' key='group' /></span> 
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
										<c:if test="${!empty board.readRoleList}">
										<c:forEach var="item" items="${board.readRoleList}">
											<option value="<c:out value='${item.roleId}'/>"><c:out value='${item.roleName}'/></option>
										</c:forEach>
										</c:if>
									</select>
								
									<a href="#" class="button_ic valign_bottom" onclick="return false;">
										<span id="readRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
									</a>
									<span class="valign_bottom">총 <span id="readRoleNameCount">0</span> 역할</span>
			
							</div>								
						</td>
					</tr> 	
				</c:if> 
				
				<c:if test="${board.boardType eq 0}"> 
				<spring:bind path="board.listType">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td> 
						<div>
						<c:forEach var="code" items="${boardCode.listTypeList}"> 
							<input name="${status.expression}" type="radio" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> /> <ikep4j:message key='${code.value}'/>
						</c:forEach>
						</div>
					</td> 
				</tr>				
				</spring:bind>	 
				<spring:bind path="board.anonymous">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.rss">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td> 
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.fileSize">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<select name="${status.expression}">
						<c:forEach var="code" items="${boardCode.fileSizeList}">
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
				<spring:bind path="board.imageFileSize">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<select name="${status.expression}">
						<c:forEach var="code" items="${boardCode.imageFileSizeList}">
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
				
				<spring:bind path="board.imageWidth">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<select name="${status.expression}">
						<c:forEach var="code" items="${boardCode.imageWidthList}">
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
				<spring:bind path="board.pageNum">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<select name="${status.expression}">
						<c:forEach var="code" items="${boardCode.pageNumList}">
							<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
							${code.value}
							</option>
						</c:forEach>
						</select>
						</div>
					</td> 
				</tr>				
				</spring:bind>	 
				<spring:bind path="board.docPopup">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th> 
					<td> 
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.reply">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.linereply">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.contentsReadPermission">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail2}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
							<label>
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code.key}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail2}' key='${status.expression}' />"
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
				<spring:bind path="board.contentsReadMail">
				<tr> 
					<th scope="row" colspan="2">독서자 메일 발송</th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
							<label>
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code.key}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail2}' key='${status.expression}' />"
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
				<%/* %>
				<spring:bind path="board.portlet">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.yesNoList}"> 
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
				<spring:bind path="board.restrictionType">
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
				
		
				<spring:bind path="board.move">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				
				<%*/ %>  
				<spring:bind path="board.wordHead">
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
							<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
							<select id="categoryId" name="categoryId" <c:if test="${board.wordHead !=1 }">style="display:none"</c:if>>
						    	<c:forEach var="code" items="${boardItemCategoryList}">
									<option value="${code.categoryId}">${code.categoryName}</option>
								</c:forEach>
							</select>
							&nbsp;
							<a id="searchItemButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='categoryManage'/>"><span><ikep4j:message pre='${preButton}' key='categoryManage'/></span></a>
						</div>
					</td> 
				</tr>				
				</spring:bind> 

				<spring:bind path="board.fileAttachOption"> 
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.attechFileOptionList}"> 
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
				<spring:bind path="board.allowType">
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
				<spring:bind path="board.readPermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
					
					<c:forEach var="code" items="${boardCode.openClosedList}"> 
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
							<c:forEach var="user" items="${board.readUserList}"> 
								<option value="${user.userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteReadUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(board.readUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
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
							<c:forEach var="group" items="${board.readGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.hierarchied} ${group.groupName}
								</option>
							</c:forEach>  
						</select>					 
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="readChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteReadGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectReadGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(board.readGroupList)}<ikep4j:message pre='${preDetail}' key='group' /></span> 
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
								<c:if test="${!empty board.readRoleList}">
								<c:forEach var="item" items="${board.readRoleList}">
									<option value="<c:out value='${item.roleId}'/>"><c:out value='${item.roleName}'/></option>
								</c:forEach>
								</c:if>
							</select>
						
							<a href="#" class="button_ic valign_bottom" onclick="return false;">
								<span id="readRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
							</a>
							<span class="valign_bottom">총 <span id="readRoleNameCount">0</span> 역할</span>
	
					</div>								
				</td>
			</tr> 		
			<spring:bind path="board.writePermission">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${boardCode.openClosedList}"> 
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
							<c:forEach var="user" items="${board.writeUserList}"> 
								<option value="${user.userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteWriteUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectWriteUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(board.writeUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
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
							<c:forEach var="group" items="${board.writeGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
								</option>
							</c:forEach>  
						</select>		  
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="writeChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${preButton}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteWriteGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span  id="selectWriteGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(board.writeGroupList)} <ikep4j:message pre='${preDetail}' key='group' /></span> 
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
								<c:if test="${!empty board.writeRoleList}">
								<c:forEach var="item" items="${board.writeRoleList}">
									<option value="<c:out value='${item.roleId}'/>"><c:out value='${item.roleName}'/></option>
								</c:forEach>
								</c:if>
							</select>
						
							<a href="#" class="button_ic valign_bottom" onclick="return false;">
								<span id="writeRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
							</a>
							<span class="valign_bottom">총 <span id="writeRoleNameCount">0</span> 역할</span>
	
					</div>								
				</td>
			</tr> 
			<spring:bind path="board.adminPermission">
			<tr> 
				<th scope="row" colspan="2">관리자 권한</th>
				<td>
					<c:forEach var="code" items="${boardCode.openClosedList}"> 
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
			<tr class="adminPermissionTableRow"> 
				<th scope="row" rowspan="3">관리자 권한 설정</th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td> 
					<input id="adminUserName" name="adminUserName" title="<ikep4j:message pre='${preDetail}' key='user' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchAdminUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addAdminUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5">  
						<select title="select" id="selectAdminUserList" name="selectAdminUserList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='user' />" >
							<option class="dummy"></option>  			
							<c:forEach var="user" items="${board.adminUserList}"> 
								<option value="${user.userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} ${user.teamName}
								</option>
							</c:forEach>  
						</select>	 
						<a id="deleteAdminUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectadminUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(board.adminUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
					</div>	 
				</td>   
			</tr>
			<tr class="adminPermissionTableRow">  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th>   
				<td> 
					<input id="adminGroupName" name="adminGroupName" title="<ikep4j:message pre='${preDetail}' key='group' />" class="inputbox valign_baseline" type="text" size="20" /> 
					<a id="searchAdminGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addAdminGroupButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<div class="mt5">  
						<select title="select" id="selectAdminGroupList" name="selectAdminGroupList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='group' />" >   
							<option class="dummy"></option>
							<c:forEach var="group" items="${board.adminGroupList}"> 
								<option value="${group.groupId}" id="${group.groupId}^${group.groupName}" class="<c:if test='${group.hierarchied eq 1}'>hierarchied</c:if>">
								<c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}
								</option>
							</c:forEach>  
						</select>		  
						<a href="#a" class="button_ic valign_bottom" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
							<span id="adminChangeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${preButton}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${preButton}" key="groupHierarchy" /></span>
						</a>  
						<a id="deleteAdminGroupButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span  id="selectAdminGroupListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' /> ${fn:length(board.adminGroupList)} <ikep4j:message pre='${preDetail}' key='group' /></span> 
				 	</div>	   
				</td>
			</tr> 
			<tr class="adminPermissionTableRow">
				<th scope="row">역할</th>
				<td>
					<input name="adminRoleName" id="adminRoleName" class="inputbox" type="text" size="20" />
					<a href="#" class="button_ic" onclick="return false;" >
						<span id="adminRoleSearchBtn">
							<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt=""/>
							<ikep4j:message pre="${preButton}" key="search" />
						</span>
					</a>
					<a href="#" class="button_ic" onclick="return false;" >
						<span id="adminRoleSearchBtnAll">
							<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt=""/>
							List
						</span>
					</a>
					<div class="mt5">
						
							<select name="addrAdminRoleList" id="addrAdminRoleList" size="5" multiple="multiple" class="multi w60"  >
								<c:if test="${!empty board.adminRoleList}">
								<c:forEach var="item" items="${board.adminRoleList}">
									<option value="<c:out value='${item.roleId}'/>"><c:out value='${item.roleName}'/></option>
								</c:forEach>
								</c:if>
							</select>
						
							<a href="#" class="button_ic valign_bottom" onclick="return false;">
								<span id="adminRoleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" />삭제</span>
							</a>
							<span class="valign_bottom">총 <span id="adminRoleNameCount">0</span> 역할</span>
	
					</div>								
				</td>
			</tr> 
			</c:if>
			<spring:bind path="board.mobileUse"> 
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
						<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
				<spring:bind path="board.mobileWriteUse"> 
				<tr> 
					<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
					<td>
						<div>
							<c:forEach var="code" items="${boardCode.useUnuseList}"> 
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
		</table>   
	</div>  
	<!--//blockDetail End-->
	</form>
	<!--blockButton Start-->
	<div class="blockButton" id="divButton"> 
		<ul> 
			<li><a name="updateBoardButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a name="listBoardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>			
		</ul>
	</div>
	<!--//blockButton End--> 
	
	
	