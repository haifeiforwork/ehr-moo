<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.admin.apprOfficial.header" />
<c:set var="preForm"  	value="ui.approval.admin.apprOfficial" />
<c:set var="preValidation" value="ui.approval.admin.apprOfficial.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   

<script type="text/javascript">
	<!--
	
	
	(function($){
		
		deleteOfficial = function(officialId){
			
			if(!confirm('<ikep4j:message pre='${preMessage}' key='delete' />')) {
				return;
			}
			
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprOfficial/deleteApprOfficial.do" />',     
				data :  {officialId:officialId},     
				type : "post",     
				success : function(result) {         
					location.href = iKEP.getContextRoot()+"/approval/work/apprOfficial/listApprOfficial.do";
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
			
		};
		
		apprDocPC = function(html,fileName){
			
			dialog = new iKEP.Dialog({     
				title 		: "PC저장",
				url 		: "<c:url value='/approval/work/apprOfficial/createApprOfficialPC.do'/>",
				modal 		: true,
				width 		: 500,
				height 		: 150,
				params 		: {"html":html,"fileName":fileName},
				callback : function(result) {
					
				}
			});
		};
		
		apprDocPrint = function(html,fileName){
			
			 var url = iKEP.getContextRoot() + "/approval/work/apprOfficial/viewApprOfficialPrint.do";
			 iKEP.popupOpen(url, {width:800, height:600, argument:{"printHTML":html,"fileName":fileName}}, "printPopup");
			
		};
		
		viewApprDoc = function(apprId){
			
			//var url = "<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do?apprId=${apprOfficial.apprId}&listType=&popupYn=true'/>";
			
			//iKEP.popupOpen(url, {width:800, height:600}, "viewApprDoc");
			
			iKEP.showDialog({     
				title 		: "문서 상세",
		    	url 		: iKEP.getContextRoot() + "/approval/work/apprWorkDoc/viewApprDoc.do?&popupYn=true&apprId=" + apprId + "&listType=listApprIntegrate",
		    	modal 		: true,
		    	width 		: 850,
		    	height 		: 500,
		    	callback	: function(result) {
				    
				}
			});
		};
		
		$(document).ready(function(){
			
			//- user info
    		iFU.setUserInfo("${user.userId}", "${user.localeCode}", "${apprDoc.registerId}");
    		
    		//- initialize form
    		iFU.initializeApprDocForm("#officialForm", "view");
    		
    		if($("#officialForm textarea[name=formLayoutData]").val()!=""){
    		    iFC.object.formLayoutData =  $.parseJSON($("#officialForm textarea[name=formLayoutData]").val());
	            iFC.object.formData       =  $.parseJSON($("#officialForm textarea[name=formData]").val());
	            iFU.printForm();	
	        }
    		
			// 버튼영역 실행
        	$("#formButtonDiv a").click(function(){
        	    switch (this.id) {
        	    	
	        	    case "apprOriginalBtn":
	        	    	viewApprDoc('${apprOfficial.apprId}');
	                    break;
					
	        	    case "deleteBtn":
                        deleteOfficial('${apprOfficial.officialId}');
                        break;
                    
	        	    case "savePcBtn":
			            apprDocPC($("#guideConFrame").html(),'${apprOfficial.title}');
                        break;
                         
        	        case "printBtn":
        	        	apprDocPrint($("#guideConFrame").html(),'${apprOfficial.title}');
        			    break;
        			    
        	        case "listBtn":
        	        	location.href = iKEP.getContextRoot()+"/approval/work/apprOfficial/listApprOfficial.do";
        			    break;
        			    
                    default:
                        break;
                }   
            });
		});
	})(jQuery);
	//-->
</script>


				<h1 class="none">컨텐츠영역</h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle" class="btnline">
					<h2><ikep4j:message pre="${preHeader}" key="title3" /></h2>
					<!--blockButton Start-->
					<div class="blockButton" id="formButtonDiv"> 
						<ul>
							<li><a id="apprOriginalBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='apprOriginal'/></span></a></li>
							<li><a id="deleteBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
							<li><a id="savePcBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePc'/></span></a></li>
							<li><a id="printBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='print'/></span></a></li>
							<li><a id="listBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>                                                                          
						</ul>
					</div>
					<!--//blockButton End-->		
				</div>
				<!--//pageTitle End-->
				
	
				<!--blockDetail Start-->
				<div class="Approval" id="guideConFrame">
                
                
					<form id="officialForm" name="officialForm" method="post" action="">
					 	
					 	<div style="height:20px;"></div>
					 	
					 	<spring:bind path="apprDoc.formLayoutData">
					    	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
					    </spring:bind>
					    
					    <spring:bind path="apprDoc.formData">
					    	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
					    </spring:bind>
						
					   	
					   	<!-- 결재 테이블 시작 -->
						<table summary="결재 문서 공문시행 화면">
							<caption></caption>
							<tbody>
							
								<c:choose>
							   		<c:when test="${apprOfficial.headerAlign eq '0'}">
							   			<c:set var="headerClassName" value="title tl"/>
							   		</c:when>
							   		<c:when test="${apprOfficial.headerAlign eq '1'}">
							   			<c:set var="headerClassName" value="title tc"/>
							   		</c:when>
							   		<c:when test="${apprOfficial.headerAlign eq '2'}">
							   			<c:set var="headerClassName" value="title tr"/>
							   		</c:when>
							   	</c:choose>
					   	
								<tr>
									<td class="${headerClassName}">
	                                    <c:choose>
										    <c:when test="${apprOfficial.headerType eq '0'}">
										    	<p>${apprOfficial.headerTitle}</p>
											</c:when>
											<c:when test="${apprOfficial.headerType eq '1'}">
												<img src="${ikepBaseUrl}/support/fileupload/downloadFile.do?fileId=${apprOfficial.headerImgId}" alt="header image" />
											</c:when>
										</c:choose>
										
									</td>
								</tr>
								<tr>
									<td class="pb10">
										<ul >
											<li>${apprOfficial.companyAddress}</li>
											<li>TEL : ${apprOfficial.companyTel} / FAX : ${apprOfficial.companyFax}
												<c:if test="${apprOfficial.isMail eq '1'}"> / ${apprOfficial.companyMail}</c:if>
												<c:if test="${apprOfficial.isInCharge eq '1'}"> / ${apprOfficial.inCharge}</c:if>
											</li>                                        
										</ul>
									</td>
								</tr>
								<tr><td><hr  noshade class="line1"/></td></tr>
								<tr>
									<td class="pt5 pl10">
										<ul>
											<li class="pt5"><ikep4j:message pre='${preForm}' key='officialDocNo'/> : ${apprOfficial.officialDocNo}</li>
											<li class="pt5"><ikep4j:message pre='${preForm}' key='receiver1'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<ikep4j:message pre='${preForm}' key='receiver2'/> : ${apprOfficial.receiver}</li>
											<li class="pt5"><ikep4j:message pre='${preForm}' key='reference1'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<ikep4j:message pre='${preForm}' key='reference2'/> : ${apprOfficial.reference}</li>
											<li class="pt5"><ikep4j:message pre='${preForm}' key='title1'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<ikep4j:message pre='${preForm}' key='title2'/> : ${apprOfficial.title}</li>
										</ul>
									</td>
								</tr>
								<tr><td class="pt10"><hr  noshade class="line2"/></td></tr>
								<tr>
									<td class="content">
										<c:if test="${not empty apprForm.formLayoutData}">
										<div id="formLayoutDiv" class="Approval_1">
					                		<table id="layoutTable" summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
					                			<caption></caption>
					                			<tbody>
					                			</tbody>
					                			<tfoot>
					                            </tfoot>
					                        </table>
					                    </div>    
				                       </c:if>
				                       <c:if test="${apprForm.isApprEditor eq 1}">
				                       <br/>
										${apprOfficial.formEditorData}
										</c:if>
									</td>
								</tr>          
								  
								<c:choose>
							   		<c:when test="${apprOfficial.footerAlign eq '0'}">
							   			<c:set var="footerClassName" value="title tl"/>
							   		</c:when>
							   		<c:when test="${apprOfficial.footerAlign eq '1'}">
							   			<c:set var="footerClassName" value="title tc"/>
							   		</c:when>
							   		<c:when test="${apprOfficial.footerAlign eq '2'}">
							   			<c:set var="footerClassName" value="title tr"/>
							   		</c:when>
							   	</c:choose>
					   	
								<tr>
									<td class="${footerClassName}">
										<c:choose>
										    <c:when test="${apprOfficial.footerType eq '0'}">
										    	<p class="t1">${apprOfficial.footerTitle}</span>
										    	<c:if test="${apprOfficial.isSeal eq '1'}">
													<img src="${ikepBaseUrl}/support/fileupload/downloadFile.do?fileId=${apprOfficial.sealId}" alt="sign image" />
									       		</c:if>
									       		</p>
											</c:when>
											<c:when test="${apprOfficial.footerType eq '1'}">
												<img src="${ikepBaseUrl}/support/fileupload/downloadFile.do?fileId=${apprOfficial.footerImgId}" alt="footer image" />
												<c:if test="${apprOfficial.isSeal eq '1'}">
													<img src="${ikepBaseUrl}/support/fileupload/downloadFile.do?fileId=${apprOfficial.sealId}" alt="sign image" />
									       		</c:if>
											</c:when>
										</c:choose>
									</td>
								</tr>  
							</tbody>
						</table>
						<!-- //결재 테이블 끝 -->  
						
					</form>
				
				</div>
				<!--//blockDetail End-->
			
			