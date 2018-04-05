<%--
=====================================================
* 기능 설명 : 미리보기 - new
* 작성자    : wonchu
* 모드      : 0:모달창, 1:팝업호출, 2:미리보기, 3:버전이력
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.approval.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>     
<script  type="text/javascript">

<!--// 

	(function($) {
		<c:if test="${apprDoc.mode eq '0'}">
		var dialogWindow; // 팝업개체
		</c:if>
		
		//validation
	    var validOptions = {
            rules  : {
            },
            messages : {
            },    
            notice : {
            },      
	        submitHandler : function(form) {
	            
	            var json = iFU.itemToFormData(form, $(form).find("input[name=formId]").val(), iFC.object.formLayoutData.title);
	            if(json=="false") return false;
	            
	            var editor = $(form).find("textarea[name=formEditorData]").ckeditorGet();
                editor.updateElement();
                
                $("body").ajaxLoadStart("button");
	            $("#saveForm").find("textarea[name=formEditorData]").val($(form).find("textarea[name=formEditorData]").val());
    	        $("#saveForm").find("textarea[name=formData]").val(JSON.stringify(json));
	            $("#saveForm").submit();
            }
		};
		
        
		//- onload시 수행할 코드
        $(document).ready(function() {   
            <c:if test="${apprDoc.mode eq '0'}">
            fnCaller = function (params, dialog) {
				if(params) {
					$("#saveForm textarea[name=formLayoutData]").val(JSON.stringify(params.formLayoutData));
					$("#detailForm textarea[name=formEditorData]").val(params.formEditorData);
				}
				
				dialogWindow = dialog;
		    </c:if>   
		    
		    <c:if test="${apprDoc.mode eq '1' || apprDoc.mode eq '3'}">

		        var params = iKEP.getPopupArgument();
		        
		        $("#saveForm textarea[name=formLayoutData]").val(JSON.stringify(params.formLayoutData));
				$("#detailForm textarea[name=formEditorData]").val(params.formEditorData);
				<c:if test="${apprDoc.mode eq '3'}">
				    $("#detailForm textarea[name=formGuide]").val(params.formGuide);
				</c:if>
				$("body").css("padding", "0 10px");
		    </c:if>
		        
                iFC.object.formLayoutData = $.parseJSON($("#saveForm textarea[name=formLayoutData]").val());
            
                fullCkeditorConfig.height="250";
			    $("#detailForm textarea[name=formEditorData]").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "editor" }));
				
				iFC.mode = "new";
				if(iFC.object.formLayoutData.title){
		            iFU.setUserInfo("${user.userId}", "${user.localeCode}");
		            iFU.printForm();
		        }
		        
		        //- rules 셋팅 
                if(iFC.object.rules!=""){
                    validOptions.rules = $jq.parseJSON("{" + iFC.object.rules + "}");
                }

                //- messages 셋팅
                if(iFC.object.messages!=""){
                    validOptions.messages = $jq.parseJSON("{" + iFC.object.messages + "}");
                }
        
                new iKEP.Validator("#detailForm", validOptions);
			<c:if test="${apprDoc.mode eq '0'}">
			};
			</c:if>  
			
		    //- 버튼영역 실행  
			$("#detailFormButton a").click(function(){
			    switch (this.id) {
                    case "saveButton":
                        $("#detailForm").trigger("submit");
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
		<table id="editorTable" summary="">
			<caption></caption>
			<tbody>
			    <tr>
					<td>
					    <spring:bind path="apprDoc.formEditorData">
					    <textarea name="${status.expression}" class="inputbox w100 fullEditor">${status.value}</textarea>
					    </spring:bind>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<c:if test="${apprDoc.mode eq '3'}">
	<h3>작성안내</h3>
	<div class="blockDetail">
		<table id="editorTable" summary="">
			<caption></caption>
			<tbody>
			    <tr>
					<td>
					    <spring:bind path="apprDoc.formGuide">
					    <textarea name="${status.expression}" rows="5" class="inputbox w100">${status.value}</textarea>
					    </spring:bind>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	</form>
	<form id="saveForm" name="saveForm" method="post" action="viewApprDocLayout.do">
	    <spring:bind path="apprDoc.mode">
        <input type="hidden" name="${status.expression}" value="${status.value}">
        </spring:bind>
	    <spring:bind path="apprDoc.formId">
        <input type="hidden" name="${status.expression}" value="test001">
        </spring:bind>
	    <spring:bind path="apprDoc.formData">
        <textarea name="${status.expression}" style="display:none;"></textarea>
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
			<li><a id="saveButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
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