<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript">

	/**
	 * @type : function
	 * @desc : 수정화면으로 이동한다.
	 * <pre>
	 * 	f_UpdateApForm('10000000001');
	 * </pre>
	 *
	 * @param : 양식ID
	 * @return : none
	 */
	function f_UpdateApForm(formId) {
	   	document.location.href="updateApForm.do?formId=" + formId;
	}
	
	/**
	 * @type : function
	 * @desc : 미리보기 팝업 화면을 보여준다.
	 * @param : 양식ID
	 * @return : none
	 */
	function f_ViewApForm(formId) {
	   	document.location.href="viewApForm.do?formId=" + formId;
	}

	/**
	 * @type : function
	 * @desc : 페이지 onload 이벤트
	 * @return : none
	 */
	$jq(document).ready(function() {		
		
		$jq("#apFormTab").tabs();

	});
</script>
	
<h1 class="none">컨텐츠영역</h1>

<!--pageTitle Start-->

<div id="pageTitle">
	<h2><spring:message code="message.wfapproval.admin.apform.view.main.title"/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst">Home</li>
			<li>1depth</li>
			<li>2depth</li>
			<li class="liLast">최종depth</li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->
		
<h1 class="none">
	<spring:message code="message.wfapproval.admin.apform.view.main.title"/>
</h1>

<div class="blockBlank_10px"></div>

<div id="guideConFrame">

	<!--tab Start-->		
	<div id="apFormTab" class="iKEP_tab">
	    <ul>
	        <li><a href="#tabs-1">기본정보</a></li>
	        <li><a href="#tabs-2">상세정보</a></li>
	    </ul>
	    <div class="tab_con">
	    
	    	<!--양식 기본정보-->
	        <div id="tabs-1">
	        	<div class="blockBlank_10px"></div>

				<!--subTitle_2 Start
				<div class="subTitle_2 noline">
					<h3><spring:message code="message.wfapproval.admin.apform.form.form.subtitle"/></h3>
				</div>
				-->
				<!--//subTitle_2 End-->
				
	        	<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<spring:message code="message.wfapproval.admin.apform.form.form.subtitle"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.form.formClassCd"/></th>
								<td width="82%">
									<select name="formClassCd" title="<spring:message code="message.wfapproval.admin.apform.view.form.formClassCd"/>">
										<option value="${apForm.formClassCd}">${apForm.formClassCd}</option>
									</select>				
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.form.formTypeCd"/></th>
								<td width="82%">
									<select name="formTypeCd" title="<spring:message code="message.wfapproval.admin.apform.view.form.formTypeCd"/>">
										<option value="${apForm.formTypeCd}">${apForm.formTypeCd}</option>
									</select>				
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.form.formName"/></th>
								<td>
									<input 	type="text" class="inputbox" title="<spring:message code="message.wfapproval.admin.apform.view.form.formName"/>" 
											name="formName" value="${apForm.formName}" size="80" readonly/>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.form.isUse"/></th>
								<td>
									<label>
										<input type="radio" class="radio" title="사용" name="isUse" value="${apForm.isUse}" checked />사용
									</label>&nbsp;&nbsp;
									<label>
										<input type="radio" class="radio" title="사용안함" name="isUse" value="${apForm.isUse}"  />사용안함
									</label>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.form.formDesc"/></th>
								<td>
									<textarea 	name="formDesc" cols="100" rows="8"  readonly
												title="<spring:message code="message.wfapproval.admin.apform.view.form.formDesc"/>">${apForm.formDesc}</textarea>
								</td>
							</tr>														
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#" onclick="javascript:f_UpdateApForm(${apForm.formId})">
								<span><spring:message code="message.wfapproval.admin.apform.button.update"/></span>
						</a></li>
						<li><a class="button" href="#" onclick="javascript:location.href='listApForm.do'">
								<span><spring:message code="message.wfapproval.admin.apform.button.list"/></span>
						</a></li>
					</ul>
				</div>
				<!--//blockButton End-->
	        </div>
	        
	        <!--양식 상세정보-->
	        <div id="tabs-2">
	        
	        	<div class="blockBlank_10px"></div>
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle1"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
	        	<!--결재설정 blockDetail Start-->
				<div class="blockDetail">
					<table summary="<spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.mailReqCd"/></th>
								<td width="32%">
									<select name="mailReqCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.mailReqCd"/>">
										<option value="${apFormTpl.mailReqCd}">${apFormTpl.mailReqCd}</option>
									</select>
								</td>
								<th width="18%" scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.mailEndCd"/></th>
								<td width="32%">
									<select name="mailEndCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.mailEndCd"/>">
										<option value="${apFormTpl.mailEndCd}">${apFormTpl.mailEndCd}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.mailRetCd"/></th>
								<td>
									<select name="mailRetCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.mailRetCd"/>">
										<option value="${apFormTpl.mailRetCd}">${apFormTpl.mailRetCd}</option>
									</select>
								</td>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.mailInfoCd"/></th>
								<td>
									<select name="mailInfoCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.mailInfoCd"/>">
										<option value="${apFormTpl.mailInfoCd}">${apFormTpl.mailInfoCd}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprConfig"/></th>
								<td>
									<label>
										<input 	type="checkbox" class="checkbox" name="isAppr" value="${apFormTpl.isAppr}" checked="checked"
												title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.isAppr"/>" />
										<spring:message code="message.wfapproval.admin.apform.view.formtpl.isAppr"/>
									</label>&nbsp;&nbsp;
									<label>
										<input 	type="checkbox" class="checkbox" name="isDiscuss" value="${apFormTpl.isDiscuss}"
												title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.isDiscuss"/>" />
										<spring:message code="message.wfapproval.admin.apform.view.formtpl.isDiscuss"/>
									</label>
								</td>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.discussCd"/></th>
								<td>
									<select name="discussCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.discussCd"/>">
										<option value="${apFormTpl.discussCd}">${apFormTpl.discussCd}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprLineCnt"/></th>
								<td>
									<select name="apprLineCnt" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprLineCnt"/>">
										<option value="${apFormTpl.apprLineCnt}">${apFormTpl.apprLineCnt}</option>
									</select>
								</td>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.discussLineCnt"/></th>
								<td>
									<select name="discussLineCnt" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.discussLineCnt"/>">
										<option value="${apFormTpl.discussLineCnt}">${apFormTpl.discussLineCnt}</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle2"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--양식설정 blockDetail Start-->
				<div class="blockDetail">
					<table summary="<spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle2"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprPeriod"/></th>
								<td width="32%">
									<select name="isApprPeriod" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprPeriod"/>">
										<option value="${apFormTpl.isApprPeriod}">${apFormTpl.isApprPeriod}</option>
									</select>
								</td>
								<th width="18%" scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprPeriodCd"/></th>
								<td width="32%">
									<select name="apprPeriodCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprPeriodCd"/>">
										<option value="${apFormTpl.apprPeriodCd}">${apFormTpl.apprPeriodCd}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprType"/></th>
								<td>
									<label>
										<input type="radio" class="radio" title="일반" name="isApprType" value="${apFormTpl.isApprType}" checked />일반
									</label>&nbsp;&nbsp;
									<label>
										<input type="radio" class="radio" title="기밀" name="isApprType" value="${apFormTpl.isApprType}"  />기밀
									</label>
								</td>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprTypeCd"/></th>
								<td>
									<select name="apprTypeCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprTypeCd"/>">
										<option value="${apFormTpl.apprTypeCd}">${apFormTpl.apprTypeCd}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprDoc"/></th>
								<td>
									<select name="isApprDoc" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprDoc"/>">
										<option value="${apFormTpl.isApprDoc}">${apFormTpl.isApprDoc}</option>
									</select>
								</td>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprDocCd"/></th>
								<td>
									<select name="apprDocCd" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprDocCd"/>">
										<option value="${apFormTpl.apprDocCd}">${apFormTpl.apprDocCd}</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle3"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--양식내용 blockDetail Start-->
				<div class="blockDetail">
					<table summary="<spring:message code="message.wfapproval.admin.apform.form.formtpl.subtitle3"/>">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprTitle"/></th>
								<td width="82%">
									<input 	type="text" class="inputbox" title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprTitle"/>" 
											name="apprTitle" value="${apFormTpl.apprTitle}" size="100" readonly/>
									<input 	type="checkbox" class="checkbox" name="isApprTitle" value="${apFormTpl.isApprTitle}" checked="checked"
											title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprTitle"/>" />
									<spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprTitle"/>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.apprFormData"/></th>
								<td>
									<textarea 	name="apprFormData" cols="150" rows="15"  readonly
												title="<spring:message code="message.wfapproval.admin.apform.view.formtpl.apprFormData"/>">${apFormTpl.apprFormData}</textarea>
								</td>
							</tr>
							<tr>
								<th scope="row"><spring:message code="message.wfapproval.admin.apform.view.formtpl.isApprAttach"/></th>
								<td>
									<label>
										<input type="radio" class="radio" title="사용" name="isApprType" value="${apFormTpl.isApprAttach}" checked />사용
									</label>&nbsp;&nbsp;
									<label>
										<input type="radio" class="radio" title="사용안함" name="isApprType" value="${apFormTpl.isApprAttach}"  />사용안함
									</label>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#" onclick="javascript:f_UpdateApForm(${apForm.formId})">
								<span><spring:message code="message.wfapproval.admin.apform.button.update"/></span>
						</a></li>
						<li><a class="button" href="#" onclick="javascript:f_ViewApForm(${apForm.formId})">
								<span><spring:message code="message.wfapproval.admin.apform.button.view"/></span>
						</a></li>
						<li><a class="button" href="#" onclick="javascript:location.href='listApForm.do'">
								<span><spring:message code="message.wfapproval.admin.apform.button.list"/></span>
						</a></li>
					</ul>
				</div>
				<!--//blockButton End-->
	        </div>
	    </div>                
	</div>
	<!--//tab End-->
	
</div>

