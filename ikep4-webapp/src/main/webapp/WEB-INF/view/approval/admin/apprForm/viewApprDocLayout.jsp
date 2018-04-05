<%--
=====================================================
* 기능 설명 : 미리보기 - view
* 작성자    : wonchu
* 모드      : 0:모달창, 1:팝업호출, 2:미리보기, 4:수정이력
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.approval.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script  type="text/javascript">

<!--// 

	(function($) {
		
		var dialogWindow; // 팝업개체		
		
        
		//- onload시 수행할 코드
        $(document).ready(function() {   
            <c:if test="${apprDoc.mode eq '0'}">
            fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			};
            </c:if> 
            
            <c:if test="${apprDoc.mode eq '4'}">
		        var params = iKEP.getPopupArgument();
		        
	            $("#saveForm textarea[name=formLayoutData]").val(JSON.stringify(params.formLayoutData));
	            $("#saveForm textarea[name=formData]").val(JSON.stringify(params.formData));
				$("#saveForm textarea[name=formEditorData]").val(params.formEditorData);
		    </c:if>
            
            
            iFC.object.formLayoutData =  $.parseJSON($("#saveForm textarea[name=formLayoutData]").val());
            iFC.object.formData       =  $.parseJSON($("#saveForm textarea[name=formData]").val());
	        
	        
            <c:if test="${apprDoc.mode ne '0'}">
				$("body").css("padding", "0 10px");
		    </c:if>
		   
	        iFC.mode = "view";
	        if(iFC.object.formLayoutData.title){
	            iFU.setUserInfo("${user.userId}", "${user.localeCode}");
	            iFU.printForm();	
	        }
	        
	        $("#formEditorDataTd").html($("#saveForm textarea[name=formEditorData]").val());
	
		    //- 버튼영역 실행
			$("#detailFormButton a").click(function(){
			    switch (this.id) {
                    case "saveButton":
                        $("#saveForm").submit();
        			    break;
                    case "closeButton":
                <c:choose>
                    <c:when test="${apprDoc.mode eq '0'}">
                       dialogWindow.close();
                    </c:when>
                    <c:otherwise>
					    parent.close();
				    </c:otherwise>
			    </c:choose>
                        break;
	                default:
	                    break;
	            }   
            });
        }); 
	})(jQuery);  

//-->
</script>
<h1 class="none">contnet area</h1>
<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	<!--blockDetail Start-->
	<form id="detailForm" name="detailForm" method="post">
	<div class="blockDetail" style="border-top:1px solid #e0e0e0;">
		<table id="layoutTable" summary="">
			<caption></caption>
			<tbody>
			</tbody>
			<tfoot>
            </tfoot>
		</table>
	</div>
	
	<div class="blockDetail" style="border-top:1px solid #e0e0e0;<c:if test="${apprDoc.mode eq '2'}">display:none;</c:if>">
		<table summary="">
			<caption></caption>
			<tbody>
			</tbody>
			<tfoot>
			    <tr height="10">
    			    <td id="formEditorDataTd"></td>
    			</tr>
            </tfoot>
		</table>
	</div>
	</form>
	<form id="saveForm" name="saveForm" method="post" action="updateApprDocLayout.do">
	    <spring:bind path="apprDoc.mode">
        <input type="hidden" name="${status.expression}" value="${status.value}">
        </spring:bind>
        <spring:bind path="apprDoc.formId">
        <input type="hidden" name="${status.expression}" value="${status.value}">
        </spring:bind>
	    <spring:bind path="apprDoc.formData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
	    <spring:bind path="apprDoc.formEditorData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        <spring:bind path="apprDoc.formLayoutData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
    </form>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div id="detailFormButton" class="blockButton"> 
		<ul>
		    <c:if test="${apprDoc.mode ne '4'}">    
			<li><a id="saveButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
	        </c:if>
		    <c:if test="${apprDoc.mode ne '2'}">
			<li><a id="closeButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${apprDoc.mode ne '0'}">
	<div class="blockBlank_20px"></div>
	</c:if>
	<!--//blockButton End-->
</div>