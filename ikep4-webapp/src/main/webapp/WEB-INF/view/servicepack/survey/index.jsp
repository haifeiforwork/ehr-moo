<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.index" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 
<c:set var="preProcess"  value="ui.servicepack.main.process" /> 
<ikep4j:message pre='${preProcess}' key='img' var="titleImg"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!-- pageTitle Start -->
				<div id="pageTitle"> 
					<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
				</div> 
				<!--//pageTitle End-->
				
				<div class="survey_process">
					<img src="<c:url value='/base/images/common/${titleImg}'/>" alt="Survey Process" />
					<!--프로세스 이미지에 대한 대체텍스트-->
					<div class="none">
						<ul>
							<li>1. <ikep4j:message pre='${preProcess}' key='1' />
								<ul>
									<li>1-1) <ikep4j:message pre='${preProcess}' key='1.1' />
										<ul>
											<li>1-1-1) <ikep4j:message pre='${preProcess}' key='1.1.1' /></li>
											<li>1-1-2) <ikep4j:message pre='${preProcess}' key='1.1.2' /></li>
										</ul>
									</li>
									<li>1-2) <ikep4j:message pre='${preProcess}' key='1.2' />
										<ul>
											<li>1-2-1) <ikep4j:message pre='${preProcess}' key='1.2.1' /></li>
											<li>1-2-2) <ikep4j:message pre='${preProcess}' key='1.2.2' /></li>
										</ul>
									</li>
									<li>1-4) <ikep4j:message pre='${preProcess}' key='1.4' />
										<ul>
											<li>1-4-1) <ikep4j:message pre='${preProcess}' key='1.4.1' /></li>
											<li>1-4-2) <ikep4j:message pre='${preProcess}' key='1.4.2' /></li>
										</ul>
									</li>
									<li>1-5) <ikep4j:message pre='${preProcess}' key='1.5' /></li>
								</ul>
							</li>
							<li>2. <ikep4j:message pre='${preProcess}' key='2' />
								<ul>
									<li>2-1) <ikep4j:message pre='${preProcess}' key='2.1' /></li>
								</ul>
							</li>
							<li>3. <ikep4j:message pre='${preProcess}' key='3' />
								<ul>
									<li>3-1) <ikep4j:message pre='${preProcess}' key='3.1' /></li>
									<li>3-2) <ikep4j:message pre='${preProcess}' key='3.2' /></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
 
			</div>


