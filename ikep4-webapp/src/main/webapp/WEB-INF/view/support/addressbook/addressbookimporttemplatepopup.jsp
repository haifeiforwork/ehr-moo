<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />
<c:set var="preImportPopup"  value="ui.support.addressbook.import.popup" />
<c:set var="preImportBtn"  value="ui.support.addressbook.import.popup.button" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
					
	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preImportPopup}' key='title'/></h1>
		<a href="#a" onclick="iKEP.returnPopup('success')"><span><ikep4j:message pre='${preImportBtn}' key='close'/></span></a> 
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!--subTitle_2 Start-->
		<div id="pageTitle">
			<h2><ikep4j:message pre='${preImportPopup}' key='context.main'/>
			<c:if test="${groupType == 'G'}">
				<a class="button_pr" href="<c:url value='/base/file/support/addressbook/addressbook_group_sample_for_upload.xls'/>">
			</c:if>
			<c:if test="${groupType == 'P'}">
				<a class="button_pr" href="<c:url value='/base/file/support/addressbook/addressbook_sample_for_upload.xls'/>">
			</c:if>
			<span><img src="<c:url value='/base/images/icon/ic_xls.gif' />" alt="<ikep4j:message pre='${preImportBtn}' key='download'/>" class="valign_middle" style="padding-bottom:4px;"/><ikep4j:message pre='${preImportBtn}' key='download'/></span>
			</a></h2>
		</div>
		<!--//subTitle_2 End-->		
 
		<!--addressBox1 Start-->				
		<div class="addressBox1 addressBox1_c pty1">
			<div class="textLeft addressBox1_ty1"><ikep4j:message pre='${preImportPopup}' key='context.sub0'/></div>
			<div class="addressBox1_list">
				<ul>
					<c:if test="${groupType == 'P'}">
					<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.sub1'/></li>
					<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.sub2'/><br /><span class="txtPoint"><ikep4j:message pre='${preImportPopup}' key='context.sub2.column'/></span></li>
					<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.sub3'/></li>
					<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.sub4'/></li>	
					</c:if>		
					<c:if test="${groupType == 'G'}">
						<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.gsub1'/></li>
						<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.gsub2'/><br /><span class="txtPoint"><ikep4j:message pre='${preImportPopup}' key='context.gsub2.column'/></span></li>
						<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.gsub3'/></li>
						<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.gsub4'/><br /><span class="txtPoint"><ikep4j:message pre='${preImportPopup}' key='context.gsub4.column'/></span></li>
						<li class="textLeft"><ikep4j:message pre='${preImportPopup}' key='context.gsub5'/></li>
					</c:if>															
				</ul>
			</div>
			
			<div class="blockBlank_10px"></div>							
		
			<!--blockDetail Start-->					
			<div class="blockDetail">
				<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="9%" class="textCenter">이름</th>
							<c:if test="${groupType == 'G'}">
							<th scope="col" width="9%" class="textCenter">사번</th>
							</c:if>
							<th scope="col" width="10%" class="textCenter">이메일</th>
							<th scope="col" width="10%" class="textCenter">회사명</th>
							<th scope="col" width="10%" class="textCenter">부서명</th>
							<th scope="col" width="9%" class="textCenter">직급</th>
							<th scope="col" width="15%" class="textCenter">핸드폰 번호</th>
							<th scope="col" width="16%" class="textCenter">사무실 전화번호</th>
							<th scope="col" width="*" class="textCenter">메모</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${groupType == 'P'}">
						<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter">abc@abc.com</td>
							<td class="textCenter">영원무역</td>
							<td class="textCenter">영업1팀</td>
							<td class="textCenter">과장</td>
							<td class="textCenter">010-1234-1234</td>
							<td class="textCenter">02-1234-1234</td>
							<td>잘아는 사람</td>
						</tr>
						<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter">abc@abc.com</td>
							<td class="textCenter">영원무역</td>
							<td class="textCenter">영업1팀</td>
							<td class="textCenter">과장</td>
							<td class="textCenter">010-1234-1234</td>
							<td class="textCenter">02-1234-1234</td>
							<td>잘아는 사람</td>
						</tr>
						<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter">abc@abc.com</td>
							<td class="textCenter">영원무역</td>
							<td class="textCenter">영업1팀</td>
							<td class="textCenter">과장</td>
							<td class="textCenter">010-1234-1234</td>
							<td class="textCenter">02-1234-1234</td>
							<td>잘아는 사람</td>
						</tr>	
						</c:if>		
						<c:if test="${groupType == 'G'}">
							<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td></td>
						</tr>
						<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td></td>
						</tr>
						<tr>
							<td class="textCenter">홍길동</td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td class="textCenter"></td>
							<td></td>
						</tr>
						</c:if>					
					</tbody>
				</table>
			</div>
			<!--//blockDetail End-->		
		</div>		
		<!--//addressBox1 End-->	
		
		<!--blockButton Start-->
		<div class="blockButton textCenter"> 
			<ul>
				<li><a class="button" href="#a" onclick="iKEP.returnPopup('success')"><span><ikep4j:message pre='${preImportBtn}' key='close'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->		
		
	</div>
	<!--//popup_contents End-->	

					