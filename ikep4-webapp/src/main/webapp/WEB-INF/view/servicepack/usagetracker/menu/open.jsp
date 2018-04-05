<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preButton"  value="ui.servicepack.usagetracker.common.button" /> 
<c:set var="preCommon"  value="ui.servicepack.usagetracker.common" /> 
<c:set var="preContent"  value="ui.servicepack.usagetracker.logconfig" /> 
<c:set var="preConfirm"  value="message.servicepack.usagetracker.common.confirm" /> 
<c:set var="preSucess"  value="message.servicepack.usagetracker.common.sucess" /> 
<c:set var="preHeader"  value="ui.servicepack.usagetracker.menu" /> 
<c:set var="preValid"  value="message.servicepack.valid" /> 


<ikep4j:message pre='${preHeader}' key="menuName" var="menuName"/>
<ikep4j:message pre='${preHeader}' key="menuNameEn" var="menuNameEn"/>
<ikep4j:message pre='${preHeader}' key="menuNameJp" var="menuNameJp"/>
<ikep4j:message pre='${preHeader}' key="menuUrl"  var="menuUrl"/>
<ikep4j:message pre='${preHeader}' key="requestParameter"  var="requestParameter"/>
<ikep4j:message pre='${preHeader}' key="usage"  var="usage"/>
			    	

<script  type="text/javascript">

var dialogWindow = null;
var fnCaller;
var flag ="";
(function($){

	fnCaller = function (params, dialog) {
	
		if(params) {
		}
		dialogWindow = dialog;
		
		$("#btnCancel").click(function() {
			dialogWindow.close();
		});
	};
	
	
	popupPortalMenuUrl = function() {
		var url = "<c:url value='/admin/screen/menu/popupPortalMenuUrlList.do' />";
		iKEP.popupOpen(url, {width:800, height:510, callback:getUrlValue}, "popupPortalMenuUrl");

	};
	
	getUrlValue = function(menuInfo) {

		$.ajax({
			url  : '<c:url value="/admin/screen/menu/popupPortalMenuMessage.do" />',
			data : {menuId:menuInfo.menuId},
			type : "post",
			success : function(result){
				iKEP.debug(result);
			<c:forEach var="item" items="${utMenu.i18nMessageList}" varStatus="loopStatus">
				document.getElementsByName("i18nMessageList[${loopStatus.index}].itemMessage")[0].value = result[${loopStatus.index}];
			</c:forEach>	
				$("input[name=menuUrl]").val(menuInfo.url);
			}
			
		});
	};
	

	
})(jQuery);	

(function($) {  
	$(document).ready(function() {
	
			var validOptions = {
			    rules : {
			    	<c:forEach var="item" items="${utMenu.i18nMessageList}" varStatus="loopStatus">
			    	"i18nMessageList[${loopStatus.index}].itemMessage" : {required: true},
			    	</c:forEach>
			    	
			    	menuUrl : {required : true},
			    	usage : {required : true}
			    },
			    messages : {
			    	<c:forEach var="item" items="${utMenu.i18nMessageList}" varStatus="loopStatus">
			    	"i18nMessageList[${loopStatus.index}].itemMessage" : {
			    		 direction : "bottom",
				         required : "<ikep4j:message  pre='${preValid}' key='required' arguments='${menuName}'/>"
			    		},
			    	</c:forEach>
			    	
			        menuUrl : {
			            direction : "bottom",
			            required : "<ikep4j:message  pre='${preValid}' key='required' arguments='${menuUrl}'/>"
			        },
			        usage : {
			            direction : "bottom",
			            required : "<ikep4j:message  pre='${preValid}' key='required' arguments='${usage}'/>"
			        }
			    },
			    submitHandler : function(form) {
			    	    	
			    	
			    	if( confirm("<ikep4j:message pre='${preConfirm}' key='save'/>") )
			 		{
			    		
			  			  $.post('<c:url value="/servicepack/usagetracker/menu/createOrUpdateAjax.do"/>', $("#saveForm").serialize())
			  			  .success(function(data){

			  				  	if(data.resultFlag=='1'){
			  				    alert("<ikep4j:message pre='${preCommon}' key='alreadyAdded'/>");
			  				  	}else{
			  					alert("<ikep4j:message pre='${preSucess}' key='save'/>");
			  					var result = new Object();
			  					result.reload=true;
			  					dialogWindow.callback(result);
			  					dialogWindow.close();
			  				  	}
			  					
			  		       }).error(function(event, request, settings){
			  		           alert("error");
			  		       });		
			 		}
				}
			 };

			
		new iKEP.Validator("#saveForm", validOptions);
		
		$("#btnModify").click(function() {
			$("#saveForm").trigger("submit");
			return false; 
		});	
		
		$("#btnSave").click(function() {
			$("#saveForm").trigger("submit");
			return false; 
		});	
		
		$("#btnDel").click(function() {
			if( confirm("<ikep4j:message pre='${preConfirm}' key='delete'/>") )
	 		{
	  			  $.post('<c:url value="/servicepack/usagetracker/menu/deleteAjax.do"/>', $("#deleteForm").serialize()).success(function(data){
	  					alert("<ikep4j:message pre='${preSucess}' key='delete'/>.");
	  					var result = new Object();
	  					result.reload=true;
	  					dialogWindow.callback(result);
	  					dialogWindow.close();
	  		       }).error(function(event, request, settings){
	  		           alert("error");
	  		       });		
	 		}
			return false; 
		});	
		
		
		
		
	}); 
})(jQuery);


</script>



<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div class="" id="layer_p" title="Category 추가">
<form id="deleteForm" method="post" action="<c:url value='/servicepack/usagetracker/menu/list.do' />">  
	<input type="hidden" name="menuId" value="${utMenu.menuId}" title=""/>
</form>
<form id="saveForm" method="post" action="<c:url value='/servicepack/usagetracker/menu/list.do' />">  
<input type="hidden" name="menuId" value="${utMenu.menuId}" title=""/>
<input type="hidden" name="systemUrlName" id="systemUrlName" value="" />
<input type="hidden" name="resultFlag" id="resultFlag" value="${resultFlag}" title=""/>
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="메뉴 등록">
			<caption></caption>
			<tbody>
				<c:forEach var="item" items="${utMenu.i18nMessageList}" varStatus="loopStatus">
					<tr>
					<th scope="row" width="18%"><span class="colorPoint">*</span><ikep4j:message key='ui.portal.admin.screen.menuurl.popupPortalMenuUrlList.main.menuName' />(<c:out value="${item.localeCode}"/>)</th>
					<td width="82%">
					<div>
						<spring:bind path="utMenu.i18nMessageList[${loopStatus.index}].itemMessage">
							<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${item.itemMessage}"  size="50"  class="inputbox" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${item.localeCode}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${item.fieldName}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${item.messageId}" />
						</spring:bind>
					</div>
					</td>
					</tr>
				</c:forEach>
				<tr>
					<spring:bind path="utMenu.menuUrl">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preHeader}' key='${status.expression}' /></th>
					<td width="82%" class="textLeft">
						<div>
						<input 
						name="${status.expression}" 
						id="${status.expression}" 
						type="text" 
						class="inputbox w100" 
						value="${status.value}" 
						size="30" 
						title="<ikep4j:message pre='${preHeader}' key='${status.expression}' />"
						/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						<a href="#" class="button_ic" onclick="popupPortalMenuUrl(); return false;" title="<ikep4j:message pre='${preButton}' key='example' />">
							<span>참조</span>
						</a>
						</div>																	
					</td>
					</spring:bind>	
				</tr>
				<tr>
					<spring:bind path="utMenu.requestParameter">
					<th scope="row" width="18%"><ikep4j:message pre='${preHeader}' key='${status.expression}' /></th>
					<td  width="82%">
						<div>
						<input 
						name="${status.expression}" 
						id="${status.expression}" 
						type="text" 
						class="inputbox w100" 
						value="${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preHeader}' key='${status.expression}' />"
						/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</div>							
					</td>
					</spring:bind>	
				</tr>
				<tr>
					<spring:bind path="utMenu.usage">
						<th scope="row"><ikep4j:message pre='${preHeader}' key='${status.expression}' /></th>
						<td>
						<c:forEach var="code" begin="0" end="1" step="1"> 
							<input name="${status.expression}" 
							type="radio" 
							class="radio" 
							value="${code}" 
							size="40" 
							title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
							<c:if test="${code eq utMenu.usage}">checked="checked"</c:if>
							/> 
							<ikep4j:message pre='${preCommon}' key='useage.${code}'/>
						</c:forEach> 
						</td>
					</spring:bind>	
				</tr>							
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:choose>
					<c:when test="${!empty utMenu.menuId}">
						<li><a class="button" id="btnModify" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
						<li><a class="button" id="btnDel"    href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
						
					</c:when>
					<c:otherwise>
						<li><a class="button" id="btnSave"   href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
					</c:otherwise>
				</c:choose>
				<li><a class="button" id="btnCancel" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
	<!--//blockButton End-->
</form>	
</div>	
<!-- //Modal window End -->