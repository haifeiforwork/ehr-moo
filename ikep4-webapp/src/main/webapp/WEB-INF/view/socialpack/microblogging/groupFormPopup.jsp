<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  		value="ui.socialpack.microblogging.group.detail" />
<c:set var="preButton"  		value="ui.socialpack.microblogging.button" />
<c:set var="preMessage"			value="ui.socialpack.microblogging.message" /> 
<c:set var="preGroupMessage"	value="ui.socialpack.microblogging.group.message" /> 
<c:set var="preNotice"			value="Notice.Mbgroup" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>
<script type="text/javascript">
<!--  
(function($) {

	// groupid 체크성공여부.
	var checkMbgroup = -1;
		
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#submitBtn").click(function() {
			//form을 submit하려고 시도한다.
			<c:if test="${empty mbgroup.mbgroupId}">
				$jq('form[name=mbgroupForm]').attr('action', '<c:url value="/socialpack/microblogging/mbgroup/createMbgroup.do"/>').submit();
			</c:if>
			<c:if test="${not empty mbgroup.mbgroupId}">
				$jq('form[name=mbgroupForm]').attr('action', '<c:url value="/socialpack/microblogging/mbgroup/updateMbgroup.do?mbgroupId=${mbgroup.mbgroupId}"/>').submit();
			</c:if>
		});

		// group id 중복확인 
		$jq("#checkDuplBtn").click(function() {
//alert($jq('#mbgroupId').val());
			if("" != $jq.trim($jq('#mbgroupId').val())){				
				$jq.get("<c:url value='/socialpack/microblogging/mbgroup/checkDuplMbgroupId.do'/>", 
						{mbgroupId: $jq('#mbgroupId').val()}, 
						function(data) {
							//alert(data);
	
							if(data == "N"){
								alert("<ikep4j:message pre='${preGroupMessage}' key='noGroupId' />");
								checkMbgroup = 1;
							}else{
								checkMbgroup = 0;
								alert("<ikep4j:message pre='${preGroupMessage}' key='existGroupId' />");
							}
				});
			}
		});
		
		// 데이타 정합성 체크부분
		checkValid =  function() {
			<c:if test="${empty mbgroup.mbgroupId}">
				switch(checkMbgroup) {
					case -1 : alert("<ikep4j:message pre='${preGroupMessage}' key='checkGroupId' />"); break;	// 중복 체크
					case 0 : alert("<ikep4j:message pre='${preGroupMessage}' key='existGroupId' />"); break;	// 중복
					case 1 : return true;
				}
				
				return false;
			</c:if>
			<c:if test="${!empty mbgroup.mbgroupId}">
				return true;
			</c:if>
		};
		
		// 정합성체크 옵션
		var validOptions = {
			rules : {
				mbgroupId : {
					required : true,
					maxlength : 20
				},
				mbgroupName : {
					required : true,
					maxlength : 60
				},
				mbgroupIntroduction : {
					required : false,
					maxlength : 1300
				}
			},
			messages : {
				mbgroupId : { 
		    		direction : "bottom",
		        	required  : "<ikep4j:message pre='${preNotice}' key='mbgroupId' />",
		        	maxlength : "<ikep4j:message pre='${preNotice}' key='mbgroupId.size' />"
		    	},
		    	mbgroupName : { 
		    		direction : "bottom",
		        	required  : "<ikep4j:message pre='${preNotice}' key='mbgroupName' />",
		        	maxlength : "<ikep4j:message pre='${preNotice}' key='mbgroupName.size' />"
		    	},
		    	mbgroupIntroduction : { 
		    		direction : "bottom",
		        	maxlength : "<ikep4j:message pre='${preNotice}' key='mbgroupIntroduction.size' />"
		    	}
			},
		    submitHandler : function(form) {
		    	if(!checkValid()) return false;

				var options = {
					beforeSubmit:function(){
	    			},
	    			success:function(data){
	    				data = $jq(data).text().replaceAll("<PRE>","");
						alert(data);							
						window.close();
	    			},
	    			error : function(xhr, exMessage) {
	    				xhr = $jq(xhr).text().replaceAll("<PRE>","");
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
	    			},

	    			type:'post',
	    			dataType:'html'
	    			
	    		};
	    		
				try {
	    			$("#mbgroupForm").ajaxSubmit(options);
				}
				catch(e) {}
		    	//form.submit();
			}
		};
		
		// form이 submit 될 때과 정합성옵션 연결된다.
		var validator = new iKEP.Validator("#mbgroupForm", validOptions);
		
		$("#mbgroupId").change(function(event) {
			checkMbgroup = -1;
		});
	});

})(jQuery);
//-->
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title">
		<c:if test="${empty mbgroup.mbgroupId}">
			<h1><ikep4j:message  key='ui.socialpack.microblogging.group.createTitle' /></h1>
		</c:if>
		<c:if test="${not empty mbgroup.mbgroupId}">
			<h1><ikep4j:message  key='ui.socialpack.microblogging.group.updateTitle' /></h1>
		</c:if>
		<a href="#a" onclick="javascrtipt:window.close();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<form id="mbgroupForm" name="mbgroupForm" method="post" action="" enctype="multipart/form-data">
			<table >
				<tbody>
					<spring:bind path="mbgroup.mbgroupId">
					<tr> 
						<th width="18%"  scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
						<td><div>
							<input 
							id="${status.expression}" 
							name="${status.expression}" 
							type="text" 
							class="inputbox" 
							value="${status.value}" 
							size="56" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							<c:if test="${not empty mbgroup.mbgroupId}"> disabled  ="disabled"  </c:if>
							/>
							<c:if test="${empty mbgroup.mbgroupId}">
								<a class="button_s" id="checkDuplBtn"><span><ikep4j:message pre='${preButton}' key='checkDuplication' /></span></a>
							</c:if>
						</div></td> 
					</tr>				
					</spring:bind>
					<spring:bind path="mbgroup.mbgroupName">
					<tr> 
						<th  scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
						<td><div>
							<input 
							id="${status.expression}" 
							name="${status.expression}" 
							type="text" 
							class="inputbox w100" 
							value="${status.value}" 
							size="56" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							/>
						</div></td> 
					</tr>				
					</spring:bind>
					<spring:bind path="mbgroup.mbgroupIntroduction">
					<tr> 
						<th  scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
						<td><div>
							<textarea 
							id="${status.expression}" 
							name="${status.expression}" 
							class="w100" 
							cols="" 
							rows="4"  
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							>${status.value}</textarea>
						</div></td> 
					</tr>				
					</spring:bind>
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='file' /></th>
						<td><input type="file" name="file" id="file" class="file" title="<ikep4j:message pre='${preDetail}' key='${file}' />" size="60"/></td>
					</tr>		
				</tbody>
			</table>
		</form>	
	</div>
	<!--//blockDetail End-->
				
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" id="submitBtn"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
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

