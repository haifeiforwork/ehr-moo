<%--
=====================================================
* 기능 설명 : 양식 이력 
* 작성자    : wonchu
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"    value="ui.approval.apprForm.list" />
<c:set var="preCode"    value="ui.approval.common.code" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preSearch"  value="ui.approval.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
	<!--
	
	(function($){
		
		var dialogWindow;  
		
		//- onload시 수행할 코드
		$(document).ready(function(){
		   
		    //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			};
			
		    //- close button  
            $("#closeButton").click(function(){
                dialogWindow.close();
            });
		});
		
		viewHistory = function(n){
		    var formLayoutData  = $("textarea[name=formLayoutData" + n + "]").val()!=""?$("textarea[name=formLayoutData" + n + "]").val():"{}";
		    var url = iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/createApprDocLayout.do?mode=3";
    	    var options = {
        		windowTitle : $("input[name=popupTitle" + n + "]").val(),
        		documentTitle : $("input[name=popupTitle" + n + "]").val(),
        		width:850, height:600, modal:true,
        		argument:{"formLayoutData" : $.parseJSON(formLayoutData), "formEditorData" : $("textarea[name=formEditorData" + n + "]").val(), "formGuide" : $("textarea[name=formGuide" + n + "]").val()},
        		callback : function(result) {
        			iKEP.debug(result);
        		}
        	};
        	iKEP.portletPopupOpen(url, options);
		    
		};
		
	})(jQuery);
	
	
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">content area</h1>
<div class="blockBlank_10px"></div>
<div class="blockListTable">
	<table summary="">
		<caption></caption>
		<col style="width:10%;"/>
	    <col style="width:20%;"/>
	    <col style="width:10%;"/>
	    <col style="width:50%;"/>
	    <col style="width:10%;"/>
		<thead>
			<tr>
				<th scope="col">ver</th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updateDate'/></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updaterName'/></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updateReason'/></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updateDetail'/></th>
			</tr>
		</thead>
		<tbody>
	    <c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>
		    </c:when>
		    <c:otherwise>
		        <c:forEach var="item" items="${apprFormHistoryList}" varStatus="i">
				<tr>
				    <td>
                        ${item.formVersion}
                        <input type="hidden" name="popupTitle${i.index}" value="ver${item.formVersion}">
                        <textarea name="formLayoutData${i.index}" style="display:none;">${item.formLayoutData}</textarea>
                        <textarea name="formEditorData${i.index}" style="display:none;">${item.formEditorData}</textarea>
                        <textarea name="formGuide${i.index}" style="display:none;">${item.formGuide}</textarea>
				    </td>
				    <td class="textLeft">
				        <a href="#a" onclick="setTemplateData(${i.index});"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${item.updateDate}"/></a>
				    </td>
				    <td>
                        ${item.updaterName}
				    </td>
				    <td>
				        <c:if test="${item.formVersion eq 1}">
                            <ikep4j:message pre='${preList}' key='initVersion'/>
                        </c:if> 
                        <c:if test="${item.formVersion ne 1}">
                            <% pageContext.setAttribute("newLineChar", "\n"); %> 
					        ${fn:replace(item.changeReason, newLineChar, '<br/>')}	
                        </c:if>
				    </td>
				    <td><a class="button_ic valign_bottom" href="#a" onclick="viewHistory(${i.index});"><span><ikep4j:message pre='${preList}' key='view'/></span></a></td>
			    </tr>
			    </c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
		</tbody>
	</table>
</div>
<!--//blockListTable End-->	
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<li><a id="closeButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
	</ul>
</div>
<!--blockButton End-->

</div>  
<!--//mainContents End-->