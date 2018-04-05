<%--
=====================================================
* 기능 설명 : 기안문 이력 
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
		   
		   // 메뉴선택
		   $("#apprFormLinkOfLeft").click();
		   
		    //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			};
			
			// 탭 생성
            $jq("#docHistoryTabDiv").tabs();
			
		    //- close button  
            $("#closeButton").click(function(){
                dialogWindow.close();
            });
		});
		
		viewHistory = function(n){
		    var formLayoutData  = $("textarea[name=formLayoutData" + n + "]").val()!=""?$("textarea[name=formLayoutData" + n + "]").val():"{}";
		    var formData        = $("textarea[name=formData" + n + "]").val()!=""?$("textarea[name=formData" + n + "]").val():"{}";
		    
		    var url = iKEP.getContextRoot() + "/approval/admin/apprAdminDoc/viewApprDocLayout.do?mode=4";
    	    var options = {
        		windowTitle : $("input[name=popupTitle" + n + "]").val(),
        		documentTitle : $("input[name=popupTitle" + n + "]").val(),
        		width:850, height:600, modal:true,
        		argument:{"formLayoutData" : $.parseJSON(formLayoutData), "formEditorData" : $("textarea[name=formEditorData" + n + "]").val(), "formData" : $.parseJSON(formData)},
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
<div id="docHistoryTabDiv" class="iKEP_tab">
    <ul>
        <li><a href="#tabs-1"><ikep4j:message pre='${preList}' key='apprLineHistory'/></a></li>
        <li><a href="#tabs-2"><ikep4j:message pre='${preList}' key='apprDocHistory'/></a></li>
    </ul>
    <div class="tab_con">
    	<!--양식 기본정보-->
        <div id="tabs-1">
			<div class="blockListTable">
            	<table summary="">
            		<caption></caption>
            		<col style="width:5%;"/>
            	    <col style="width:10%;"/>
            	    <col style="width:15%;"/>
            	    <col style="width:7%;"/>
            	    <col/>
            	    <col style="width:20%;"/>
            		<thead>
            			<tr>
            				<th scope="col">No</th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updaterName'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateDate'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateType'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateContent'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateReason'/></th>            				
            			</tr>
            		</thead>
            		<tbody>
            		        <c:forEach var="item" items="${versionList}" varStatus="i">
            				<tr>
            				    <td>${i.count}</td>
            				    <td>${item.registerName}</td>
            				    <td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${item.registDate}"/></td>
            				    <td>
            		                <c:if test="${item.version eq 1.0}"><ikep4j:message pre='${preList}' key='updateInit'/></c:if>
                                    <c:if test="${item.version ne 1.0}"><ikep4j:message pre='${preList}' key='edit'/></c:if>
            				    </td>
            				    <td class="textLeft">
            				   		<c:set var="cnt" value="0"/>
            						<c:forEach var="itemDetail" items="${apprLineVersionList}" varStatus="status">
            						
            						<c:if test="${item.version==itemDetail.version}">
            						
            						<c:choose>
            						<c:when test="${itemDetail.apprType==0}"><c:set var="color" value="black"/></c:when>
            						<c:when test="${itemDetail.apprType==1}"><c:set var="color" value="green"/></c:when>
            						<c:when test="${itemDetail.apprType==2}"><c:set var="color" value="blue"/></c:when>
            						</c:choose>
            						<c:if test="${cnt!=0 && itemDetail.apprOrder==apprOrder && item.cnt!=cnt}">
            						|
            						</c:if>            						

            						<c:if test="${cnt!=0 &&itemDetail.apprOrder!=apprOrder && item.cnt!=cnt}">
            						>
            						</c:if>
							
            						<c:if test="${itemDetail.approverType==0}">
            						<font color="${color}">${itemDetail.approverName}</font>
            						</c:if>
            						
            						<c:if test="${itemDetail.approverType!=0}">
            						<font color="${color}">${itemDetail.approverGroupName}</font>
            						</c:if>
            						

            						
            						<c:set var="apprOrder" value="${itemDetail.apprOrder}"/>
            						<c:set var="cnt" value="${cnt+1}"/>
            						</c:if>
            						
            						</c:forEach>
            				    </td>            				    
            				    <td class="textLeft">
									<% pageContext.setAttribute("newLineChar", "\n"); %> 
									${fn:replace(item.modifyReason, newLineChar, '<br/>')}	
            				    </td>
            			    </tr>
            			    </c:forEach>				      

            		</tbody>
            	</table>
            </div>            
            
        </div>    
        <div id="tabs-2">
            <div class="blockListTable">
            	<table summary="">
            		<caption></caption>
            		<col style="width:7%;"/>
            	    <col style="width:20%;"/>
            	    <col style="width:20%;"/>
            	    <col style="width:7%;"/>
            	    <col style="width:39%;"/>
            	    <col style="width:7%;"/>
            		<thead>
            			<tr>
            				<th scope="col">No</th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updaterName'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateDate'/></th>
            				<th scope="col"><ikep4j:message pre='${preList}' key='updateType'/></th>
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
            		        <c:forEach var="item" items="${apprDocHistoryList}" varStatus="i">
            				<tr>
            				    <td>
                                    ${item.docVersion}
                                    <input type="hidden" name="popupTitle${i.index}" value="<ikep4j:message pre='${preList}' key='editHistory'/> ver${item.docVersion}.0" />
                                    <textarea name="formLayoutData${i.index}" style="display:none;">${item.formLayoutData}</textarea>
                                    <textarea name="formData${i.index}" style="display:none;">${item.formData}</textarea>
                                    <textarea name="formEditorData${i.index}" style="display:none;">${item.formEditorData}</textarea>
            				    </td>
            				    <td>
                                    ${item.registerName}
            				    </td>
            				    <td class="textLeft">
            				        <ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${item.registDate}"/>
            				    </td>
            				    <td>
            		                <c:if test="${item.docVersion eq 1}">
                                        <ikep4j:message pre='${preList}' key='updateInit'/>
                                    </c:if>
                                    <c:if test="${item.docVersion ne 1}">
                                        <ikep4j:message pre='${preList}' key='edit'/>
                                    </c:if>
            				    </td>
            				    <td class="textLeft">
            				        <c:if test="${item.docVersion eq 1}">
                                        <ikep4j:message pre='${preList}' key='updateInit'/>
                                    </c:if> 
                                    <c:if test="${item.docVersion ne 1}">
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
        </div>
    </div>
</div>    
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<li><a id="closeButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
	</ul>
</div>
<!--blockButton End-->

</div>  
<!--//mainContents End-->