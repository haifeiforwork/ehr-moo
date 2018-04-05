<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<c:set var="preTitle"    	value="ui.approval.npd.view.title" />
<c:set var="preButton2"  	value="ui.approval.collaboration.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$(".cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		$(document).ready(function(){
			
			// 저장
			$("#saveButton").click(function() {
				
				$("#editForm").submit();
				return false; 
			});
			
			var requredMsg = "<ikep4j:message pre='${preMessage}' key='required'/>";
			
			// [validation] ============================================= Start			
			var validOptions = {
				rules			: {
					reqScheduleCd : {required : true}
			  	},
			  	messages		: {
			  		reqScheduleCd : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='reqScheduleCd'/>"+ requredMsg}
			  	},
			    submitHandler : function(form) {
					var frm = $("#editForm");
			    	var reqScheduleCd = frm.find("input[name='reqScheduleCd']:checked").val();
	    			var reqScheduleLimitDate = frm.find("input[name='reqScheduleLimitDate']").val();
	    			
	    			if( reqScheduleCd == "50" && reqScheduleLimitDate =="") {
	    				alert("요구일정을 '기타' 선택시 요구일정 기타내역은 필수 입니다. ");
	    				frm.find("input[name='reqScheduleLimitDate']").focus();
	    				return;
	    			}
			    	
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) {
						return;
					}
					
			 		$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxUpdateReqScheduleCd.do",
						type : "post",
						data : $("#editForm").serialize(),
						loadingElement : "#saveButton",
						success : function(data, textStatus, jqXHR) {
							alert("<ikep4j:message pre='${preMessage2}' key='suceess' />");
							dialogWindow.callback();
							dialogWindow.close();
							return false;
						},
						error : function(jqXHR, textStatus, errorThrown) {
							
							var arrException = $jq.parseJSON(jqXHR.responseText).exception;
							alert(arrException[0].defaultMessage);
						}
					});
				}
			};
			validator = new iKEP.Validator("#editForm", validOptions);
			
			
			// 요구일정
			$("#editForm").find("input[name='reqScheduleCd']").change(function(){
				var val = $(this).val();
				if( val == 50) {
					$("#editForm").find("input[name='reqScheduleLimitDate']").attr("disabled", false);
					$("#editForm").find("#reqScheduleLimitDateCal").show();
				}else{
					$("#editForm").find("input[name='reqScheduleLimitDate']").attr("disabled", true);
					$("#editForm").find("#reqScheduleLimitDateCal").hide();
				}
			});
			
			$("#editForm").find("input[name='reqScheduleCd']:checked").trigger("change");
			
			
			var datepickerOpts = {
		    		dateFormat: 'yy.mm.dd', 
		    		yearRange:'c-20:c+10',
		    		beforeShow: function(input) {
		    		    var i_offset= $(input).offset(); //클릭된 input의 위치값 체크
		    		    setTimeout(function(){
		    		       $('#ui-datepicker-div').css();      //datepicker의 div의 포지션을 강제로 input 위치에 그리고 좌측은 모바일이여서 작기때문에 무조건 10px에 놓았다.
		    		    });
		    		}
		   		};
		    $(".calDate").datepicker( datepickerOpts).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
	})(jQuery);
	//-->
</script>
<style>
.nameArea {height: 40px; text-align: center;}
.container{width:950px;}
.clearMarPadd0{ margin: 0 !important; padding: 0 !important; border:0px !important; }
.w25percent{width:25% !important;}
.w30percent{width:30% !important;}
.w40percent{width:40% !important;}
.w50percent{width:50% !important;}
.w60percent{width:60% !important;}
.w90percent{width:90% !important;}
.w100percent{width:100% !important;}
.w20pix{width:20px; !important;}
.devReqTitleArea{text-align:center; width:60%; height: 100%; line-height: 50px; vertical-align: middle; font-size:30px; font-weight:bold;}
.floatLeft{float:left !important;}
.marginLeft10{margin-left:10px !important;}
.inputReadOnly{ border: 0 !important; background: #fff !important;}
.inputReadOnly2{ background: #fff;}
.container INPUT[type='text'] {width:100%;}
.inputNum{text-align:right !important;}
.thMinH{min-height: 28px !important;}
.paddingRight15{padding-right: 15px !important;}
</style>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="popup.create" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preTitle}" key="rejectReason" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="width: 98%;">
		<div>
			<form id="editForm" name="editForm" method="post" action="">
				<input type="hidden" name="mgntNo" value="${newProductDev.mgntNo }"/>
				<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
					<caption></caption>
					<tbody>
						<!-- 요구일정 -->
						<tr>
							<th class="thMinH textCenter"><ikep4j:message pre="${preTitle}" key="reqScheduleCd" /></th>
							<td colspan="7" style="">
								<div class="floatLeft" style="">
									<spring:bind path="newProductDev.reqScheduleCd">
										<c:forEach var="code" items="${c00004List}">
											<input type="radio" class="tcsElement radio" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
										</c:forEach>
									</spring:bind>
								</div>
								<div class="floatLeft marginLeft10" style="width: 150px;">
									<spring:bind path="newProductDev.reqScheduleLimitDate">
										<input name="${status.expression}" class="inputbox datepicker calDate inputReadOnly2" type="text" size="10" value="${status.value}"/>
									</spring:bind>
							        <img id="reqScheduleLimitDateCal" style="display: none;" src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 10px;"> 
			<ul>
				<c:if test="${npdPermission.reqScheduleCdBtnActive eq true}">
					<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton2}' key='save'/></span></a></li>
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</c:if>
				<c:if test="${npdPermission.reqScheduleCdBtnActive eq false}">
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//blockDetail End-->
