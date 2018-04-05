<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.profile.sub.career" /> 
<c:set var="preButton"  value="ui.support.profile.sub.career.button" /> 

<c:set var="preMessage"  value="message.support.profile.sub.career" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--

(function($) {
	var validatorPool = [];
	var validOptionsUpdateCareer = { 
			rules : {
				workStartDate : {
					required : true,
					date : true
				},
				workEndDate : {
					dateGap : ["#workStartDate", -1],
					date : true
				},
				companyName : {
					maxlength : 12
				},
				roleName : {
					maxlength : 18
				},
				workChange : {
					maxlength : 200
				}
			},
			messages : {
				workStartDate : {
					required : "<ikep4j:message key='NotNull.career.workStartDate' />",
					date : "<ikep4j:message key='Date.career.workStartDate' />"
				},
				workEndDate : {
					dateGap : "<ikep4j:message key='DateGap.career.workEndDate' />",
					date : "<ikep4j:message key='Date.career.workEndDate' />"
				},
				companyName : {
					maxlength : "<ikep4j:message key='Size.career.companyName' />"
				},
				roleName : {
					maxlength : "<ikep4j:message key='Size.career.roleName' />"
				},
				workChange : {
					maxlength : "<ikep4j:message key='Size.career.workChange' />"
				}
			},
			notice : {
				workStartDate : {
					message : "<ikep4j:message key='NotNull.career.workStartDate' />"
				},
				//workEndDate : {
				//	message : "<ikep4j:message key='Size.user.officePhoneNo' />"
				//},
				companyName : {
					message : "<ikep4j:message key='Size.career.companyName' />"
				},
				roleName : {
					message : "<ikep4j:message key='Size.career.roleName' />"
				},
				workChange : {
					message : "<ikep4j:message key='Size.career.workChange' />"
				}
		    },
			submitHandler : function(form) {

				$jq.ajax({
				    url : "<c:url value='/support/career/saveCareer.do'/>",
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	var type = $(form).data("actionMode");
				    	if( type == "CREATE"){
				    		readCareerView($jq.trim(result),$(form).find("li#createCareerBtn").children("a:eq(0)"),type);
				    	}else{
				    		readCareerView($jq.trim(result),$(form).find("div.innertable_btn").children("a:eq(0)"),type);
				    	}

				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $(form).data("validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});
			}
	}

	var dialogHandler;
	function fnCaller(param, dialog) {
		dialogHandler = dialog;
	}
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		getCareerList();
		// 화면 로딩시 각각 페이지 호출 종료

		$jq('#selectCareerlist').click(function(){
			getCareerList();
		});
		$jq('#selectCareerCard').click(function(){
			getCareerCard();
		});
		$jq('#exceldownCareer').click(function(){
			downloadCareerFile();
		});
		$jq('#printCareer').click(function(){
			window.print(); 
		});
		
		
	});
	
	getCareerList = function() {
		$jq("#addBtnCareer").show();
		$jq.ajax({
		    url : "<c:url value='/support/career/getCareerList.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#guideConFrame").html(result);
		    }
		});
	};
	
	getCareerCard = function() {
		$jq("#addBtnCareer").hide();
		$jq.ajax({
		    url : "<c:url value='/support/career/getCareerCard.do'/>",
		    data : {'targetUserId':'${targetUserId}'},
		    type : "get",
		    success : function(result) {
		    	$jq("#guideConFrame").html(result);
		    }
		});
	};

	readCareerView = function(careerId,btn,type) {
		var $container = $jq(btn).parents("div.container:first");

		$jq.ajax({
		    url : "<c:url value='/support/career/getCareerView.do'/>",
		    data : {'careerId':eval("'"+careerId+"'"),'type':eval("'"+type+"'")},
		    type : "get",
		    success : function(result) {
		    	if(type=='CREATE'){
		    		cancelCareer();
		    		$jq("#careerListPreView").prepend(result); 
		    	}else{
		    		$container.html(result);
		    	}
		    }
		});
	}; 
	
	updateCareer = function(careerId, btn) {
		var $container = $jq(btn).parents("div.container:first");

		$jq.ajax({
		    url : "<c:url value='/support/career/editCareer.do'/>",
		    data : {'careerId':eval("'"+careerId+"'")},
		    type : "get",
		    success : function(result) {
		    	$container.html(result);
		    	var $form = $("form", $container);
		    	validatorPool.push(new iKEP.Validator($form, validOptionsUpdateCareer));
		    	$form.find("div.innertable_btn").children("a:eq(0)").click(function(){
					$form.trigger("submit");
				});
		    	$form.data("actionMode", "UPDATE");
		    	$form.data("validatorId", validatorPool.length);
		    }
		});
	}; 


	addCareer = function() {
		var $container = $jq("#addCareerDiv");
		$jq.ajax({
		    url : "<c:url value='/support/career/editCareer.do'/>",
		    data : {'careerId':''},
		    type : "get",
		    success : function(result) {
		    	$container.html(result);
		    	var $form = $("form", $container);
		    	validatorPool.push(new iKEP.Validator($form, validOptionsUpdateCareer));
		    	$form.find("li#createCareerBtn").children("a:eq(0)").click(function(){
					$form.trigger("submit");
				});
		    	$form.data("actionMode", "CREATE");
		    	$form.data("validatorId", validatorPool.length);

		    }
		});
	}; 

	
	cancelCareer = function() {
		$jq("#addCareerDiv").empty();
	}; 
	
	/*
	saveCareer = function(btn,type) {
		var $careerform = $jq(btn).parents("form");
		
		$jq.ajax({
		    url : "<c:url value='/support/career/saveCareer.do'/>",
		    data : $careerform.serialize(),
		    type : "post",
		    success : function(result) {
		        //$("#divAjaxResult").html(result);
		    	//alert($jq.trim(result));
		    	readCareerView($jq.trim(result),btn,type);
		    	//dialogHandler.callback();
		    }
		});
	}; 
	*/
	
	deleteCareer = function(careerId,btn) {
		var $container = $jq(btn).parents("div.container:first");

		$jq.ajax({
			url : "<c:url value='/support/career/deleteCareer.do'/>",
			data : {'careerId':eval("'"+careerId+"'")},
			type : "get",
			success : function(result) {
				//$container.html(result);
				$container.remove();
			}
    	});
	}; 
	
	
	// Save Upload Files
	downloadCareerFile = function() {
		$jq.ajax({
		    url : '<c:url value="/support/career/chkDownloadExcelCareer.do"/>',
		    data : {'targetUserId':'${targetUserId}'},
		    type : "get",
		    success : function(result) {
		    	if( result > 0 ){
		    		$("#saveForm").submit();
		    	}else{
		    		alert("<ikep4j:message pre='${preMessage}' key='nodata'/>");
		    	}
		    }
		}).error(function(event, request, settings) { alert("error"); });
	};

	
})(jQuery);  
//-->
</script>
<div class="conPadding_4">
	<!--blockListTable Start-->
	<div class="blockListTable summaryView" style="margin-bottom:0 !important;"><!--tableTop Start-->
		<div class="mb10">							
			<div class="mt5"></div>
			<form id="saveForm" method="post" action="<c:url value='/support/career/downloadExcelCareer.do'/>">
				<input name="targetUserId" type="hidden" value="${targetUserId}" />
			</form>
			<div class="buttongroup">
					<!-- <a class="button_s" href="#a" id="selectCareerlist"><span>이력카드보기</span></a>
					<a class="button_s" href="#a" id="selectCareerlist"><span>이력카드보기</span></a>-->
					<label id="selectCareerlist"><input type="radio" class="radio" title="<ikep4j:message pre='${preHeader}' key='type.list'/>" name="careerradio" value="" checked="checked" /><ikep4j:message pre='${preHeader}' key='type.list'/></label>&nbsp;&nbsp;
					<label id="selectCareerCard"><input type="radio" class="radio" title="<ikep4j:message pre='${preHeader}' key='type.card'/>" name="careerradio" value=""  /><ikep4j:message pre='${preHeader}' key='type.card'/></label>
					<span class="buttongroup_ic"><a href="#a"><img id="exceldownCareer" src="<c:url value='/base/images/icon/ic_xls.gif' />" alt="<ikep4j:message pre='${preHeader}' key='excelDownload'/>" /></a></span>
					<span class="buttongroup_ic"><a href="#a"><img id="printCareer" src="<c:url value='/base/images/icon/ic_print .gif"' /> alt="<ikep4j:message pre='${preHeader}' key='printlScreen'/>" /></a></span>
			</div>
			<div class="clear"></div>
		</div>					
	</div>
	
	<div id="guideConFrame" style="padding-top:0 !important;"></div>
	<div><ikep4j:message pre='${preHeader}' key='maxCareerRow'/></div>
</div>