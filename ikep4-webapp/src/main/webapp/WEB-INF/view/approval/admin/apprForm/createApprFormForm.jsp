<%--
=====================================================
* 기능 설명 : 양식 생성
* 작성자    : wonchu
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"      value="ui.approval.apprForm.header" /> 
<c:set var="preList"        value="ui.approval.apprForm.list" />
<c:set var="preView"        value="ui.approval.apprForm.view" />
<c:set var="preIfm"         value="ui.approval.apprForm.iFM" />
<c:set var="preIfmMessage"  value="message.approval.apprForm.iFM" />
<c:set var="preCode"        value="ui.approval.common.code" />
<c:set var="preButton"      value="ui.approval.common.button" />
<c:set var="preViewMessage" value="message.approval.apprForm.view" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>     
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>    
<script type="text/javascript">
<!--// 
    
    iFC.message.removeAuthUser  = "<ikep4j:message pre='${preIfmMessage}' key='removeAuthUser'/>";
    iFC.message.notFoundUser    = "<ikep4j:message pre='${preIfmMessage}' key='notFoundUser'/>";
    iFC.message.linePopUp       = "<ikep4j:message pre='${preIfm}' key='linePopUp'/>";
    
	(function($) {
		
		//- onload시 수행할 코드
		$(document).ready(function() {
			
			// 메뉴선택
		    $("#listApprFormLinkOfLeft").click();
		    
		    //- validation
    	    var validOptions = {
                rules  : {
                    formParentName : {
                        required : true
                    },
                    systemId : {
                        required : true
                    },
                    formName : {
                        required : true
                    },
                    apprDocType : {
                        required : true
                    },
                    formDesc : {
                        required : true
                    },
                    apprPeriodCd : {
                        required : true
                    },
                    isApprPeriod : {
                        required : true
                    },
                    apprSecurityType : {
                        required : true
                    },
                    isApprSecurity : {
                        required : true
                    }
                },
                messages : {
                    formParentName : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    systemId : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    formName : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprDocType : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    formDesc : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprPeriodCd : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    isApprPeriod : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    apprSecurityType : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    },
                    isApprSecurity : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            
    	            if(confirm("<ikep4j:message pre='${preViewMessage}' key='save'/>")){
    	                // 수신참조 저장
    	                iFU.setReferenceSet("#apprInfoForm");
    	                form.submit();
    	            }else{
    	                return false;
    	            }
                }
    		};	      
    		
    		//- 결재정보 폼초기 작업  
    		iFU.initializeApprInfoForm("#apprInfoForm", "new");
           
            //- validator mapping  
            new iKEP.Validator("#apprInfoForm", validOptions);
        });  
	})(jQuery);  

//-->
</script>
<h1 class="none">contnet area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='title.create'/></h2>
</div>
<!--//pageTitle End-->

<div id="guideConFrame">
<form id="apprInfoForm" name="apprInfoForm" method="post" action="createApprForm.do">
	<!--tab Start-->		
	<div id="formTabDiv" class="iKEP_tab">
	    <ul>
	        <li><a href="#tabs-1"><ikep4j:message pre='${preView}' key='tabOne'/></a></li>
	        <li title="<ikep4j:message pre='${preViewMessage}' key='tabTwo'/>"><a href="#tabs-2"><ikep4j:message pre='${preView}' key='tabTwo'/></a></li>
	    </ul>
	    <div class="tab_con">
	    
	    	<!--양식 기본정보-->
	        <div id="tabs-1">
	        	<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='basicInfo'/><span class="colorPoint">*</span></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
						<caption></caption>
						<tbody>
						    <tr>
						        <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='systemId'/></th>
						        <td width="35%">
						        <spring:bind path="apprForm.systemId">
									<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
									    <c:forEach var="item" items="${apprSystemList}">
									    <option value="${item.systemId}" <c:if test="${item.systemType eq 0}">selected="selected"</c:if>>${item.systemName}</option>
									    </c:forEach>
								    </select>
							    </spring:bind>    
						        </td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='formParentName'/></th>
								<td width="35%"><div>
								    <spring:bind path="apprForm.formParentId">
								    <input type="hidden" name="${status.expression}" value="${status.value}" />
									</spring:bind>
									<spring:bind path="apprForm.formParentName">
									<input type="text"   name="${status.expression}" value="${status.value}" class="inputbox w20" readonly="readonly" title="<ikep4j:message pre='${preView}' key='formParentName'/>"/>
									</spring:bind>
                                    <a id="categoryButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_subgroup.gif"/>" alt="">Category</span></a>
								</div></td>
							</tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preList}' key='formName'/></th>
								<td width="35%"><div>
								    <spring:bind path="apprForm.formName">
									<input type="text" name="${status.expression}" value="" class="inputbox w100" title="<ikep4j:message pre='${preList}' key='${status.expression}'/>"/>
									</spring:bind>
								</div></td>
					            <th width="15%" scope="row"><ikep4j:message pre='${preList}' key='apprDocType'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.apprDocType">
									<input type="radio" name="${status.expression}" class="radio" value="0" checked="checked" title="<ikep4j:message pre='${preList}' key='apprDocType0'/>" /> <ikep4j:message pre='${preList}' key='apprDocType0'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="1" title="<ikep4j:message pre='${preList}' key='apprDocType1'/>" /> <ikep4j:message pre='${preList}' key='apprDocType1'/>
                                    </spring:bind>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preView}' key='formDesc'/></th>
								<td colspan="3"><div>
								    <spring:bind path="apprForm.formDesc">
									<textarea name="${status.expression}" class="inputbox w100" rows="3" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>"></textarea>
									</spring:bind>
								</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='apprConfig'/><span class="colorPoint">*</span></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='apprConfig'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprTitle'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprTitle">
									<input type="radio" name="${status.expression}" class="radio" value="1" checked="checked" title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" name="${status.expression}" class="radio" value="0" title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
                                    </spring:bind>
								</td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprAttach'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprAttach">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preCode}' key='use'/>"> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" checked="checked" title="<ikep4j:message pre='${preCode}' key='unuse'/>"> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
								</td>
							</tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprPeriodCd'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.apprPeriodCd">
									<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>">
									    <option value=""><ikep4j:message pre='${preView}' key='select'/></option>
									    <c:forEach var="item" items="${apprPeriodList}">
									    <option value="${item.codeId}" <c:if test="${item.codeId eq status.value}">selected="selected"</c:if>>${item.codeName}</option>    
									    </c:forEach>
								    </select>
								    </spring:bind>
								</td>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isApprPeriod'/></th>
								<td width="35%">
								    <spring:bind path="apprForm.isApprPeriod">
    							    <input type="radio" class="radio" name="${status.expression}" value="0" checked="checked" title="<ikep4j:message pre='${preCode}' key='user'/>"> <ikep4j:message pre='${preCode}' key='user'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preCode}' key='admin'/>"> <ikep4j:message pre='${preCode}' key='admin'/>
								    </spring:bind>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preView}' key='apprSecurityType'/></th>
								<td>
								    <spring:bind path="apprForm.apprSecurityType">
									    <input type="radio" class="radio" name="${status.expression}" value="0" checked="checked" title="<ikep4j:message pre='${preView}' key='gerneral'/>"> <ikep4j:message pre='${preView}' key='gerneral'/>
                                        <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preView}' key='security'/>"> <ikep4j:message pre='${preView}' key='security'/>
								    </spring:bind>
								</td>
								<th scope="row"><ikep4j:message pre='${preView}' key='isApprSecurity'/></th>
								<td>
								    <spring:bind path="apprForm.isApprSecurity">
    							    <input type="radio" class="radio" name="${status.expression}" value="0" checked="checked" title="<ikep4j:message pre='${preCode}' key='user'/>"> <ikep4j:message pre='${preCode}' key='user'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preCode}' key='admin'/>"> <ikep4j:message pre='${preCode}' key='admin'/>
								    </spring:bind>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline" style="position:relative">
					<h3><ikep4j:message pre='${preView}' key='apprLineSet'/><span class="colorPoint">*</span></h3>
					<a class="button_ic valign_bottom" href="#a" onclick="iFU.addLinePopUp();" style="position:absolute;top:-4px;right:6px;"><span><img src="<c:url value="/base/images/icon/ic_btn_assign.gif"/>" alt="" style="vertical-align:middle;"><ikep4j:message pre='${preView}' key='apprLineSet'/></span></a>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='apprLineSet'/>">
						<caption></caption>
						<tbody>
						    <tr id="isDefLineUpdateTr">
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='isDefLineUpdate'/></th>
								<td width="85%"><div>
								    <spring:bind path="apprForm.isDefLineUpdate">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" <c:if test="${status.value eq 1}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='use'/>" /> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" <c:if test="${status.value eq 0}">checked="checked"</c:if> title="<ikep4j:message pre='${preCode}' key='unuse'/>" /> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
							    </div></td>
				            </tr>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='defLineSet'/></th>
								<td width="85%"><div>
								    <spring:bind path="apprForm.defLineUse">
								    <input type="hidden"  name="${status.expression}" value="0" />
								    </spring:bind>
								    <spring:bind path="apprForm.defLineId">
								    <input type="hidden"  name="${status.expression}" value="" />
								    </spring:bind>
								    <spring:bind path="apprForm.defLineSet">
									<select id="${status.expression}" name="${status.expression}" class="inputbox w100" size="7" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>"">
							            <option value=""><ikep4j:message pre='${preView}' key='blankDefLineSet'/></option>
							        </select>
							        <div id="dfLineDiv" style="margin-top:10px;"></div>
									</spring:bind>
								</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preView}' key='addInfo'/></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='addInfo'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='authUse'/></th>
								<td width="85%">
								    <spring:bind path="apprForm.authUse">
    							    <input type="radio" class="radio" name="${status.expression}" value="1" title="<ikep4j:message pre='${preCode}' key='use'/>"> <ikep4j:message pre='${preCode}' key='use'/>
                                    <input type="radio" class="radio" name="${status.expression}" value="0" checked="checked" title="<ikep4j:message pre='${preCode}' key='unuse'/>"> <ikep4j:message pre='${preCode}' key='unuse'/>
								    </spring:bind>
								    <div id="authUserDiv">
									<spring:bind path="apprForm.authUserId">
									<input type="hidden" name="${status.expression}" />
									</spring:bind>
									<spring:bind path="apprForm.authUserName">
									<input type="text" id="${status.expression}" name="${status.expression}" value="" class="inputbox w20" />
									</spring:bind>
									<a id="authUserSearchButton"  class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="">Search</span></a>
									<a id="authUserAddressButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="">Address</span></a>
									<a id="authUserDeleteButton"  class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="">Delete</span></a>
								    </div>
								</td>
							</tr>
							
							<tr>
								<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='referenceId'/></th>
								<td width="85%">
								    <spring:bind path="apprForm.referenceId">
									<input type="hidden" name="${status.expression}" />
									</spring:bind>
									<spring:bind path="apprForm.referenceName">
									<input type="text" name="${status.expression}" value="" class="inputbox w20" style="vertical-align:bottom" />
									</spring:bind>
									<a id="referenceSearchButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="">Search</span></a>
									<a id="referenceAddressButton" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="">Address</span></a>
									<div class="blockBlank_5px"></div>
									<spring:bind path="apprForm.referenceSet">
									<select name="${status.expression}" class="inputbox w50" size="5" multiple="multiple" title="<ikep4j:message pre='${preView}' key='referenceId'/>"></select>
									</spring:bind>
									<a id="referenceDeleteButton" class="button_ic" href="#a" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="">Delete</span></a> <span style="vertical-align:bottom;">&nbsp;&nbsp;(total <span id="referenceSetSpan" style="vertical-align:bottom">0</span>)</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="formButtonDiv" class="blockButton"> 
    				<ul>
    					<li><a id="saveButton"      class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
    					<li><a id="listButton" 	    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
    				</ul>
    			</div>
				<!--//blockDetail End-->
	        </div>
	    
	        <!--양식 상세정보-->
	        <div id="tabs-2"></div>
	        <!--blockButton Start-->
			<!--//blockButton End-->
	    </div>                              
	</div>
	<!--//tab End-->
</form>	
</div>