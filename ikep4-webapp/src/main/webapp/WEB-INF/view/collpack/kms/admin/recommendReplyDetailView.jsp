<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  

<c:set var="preHeader"  value="message.collpack.kms.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.board.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.board.boardItem.message.script" />  
<c:set var="prePopup"  value="message.collpack.kms.admin.permission.user.popup" />
 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1>이력 조회</h1>
		<a href="javascript:iKEP.closePop()"><span>close</span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->		
	
	<!--popup_contents Start-->
	<div id="popup_contents_2">
	
		<!--blockDetail Start-->
	    <div class="blockListTable">
<table summary="<ikep4j:message pre='${preList}' key='summary' />">  
	<caption></caption> 
	<thead>
		<tr>
			<th scope="col" width="10%">
				<ikep4j:message pre='${preList}' key='itemSeqId' />
			</th>
			<th scope="col" width="15%">
				<ikep4j:message pre='${preList}' key='isKnowhow' />
			</th>
			<th scope="col" width="12%">
				<ikep4j:message pre='${preList}' key='startDate' />
			</th>
			<th scope="col" width="*">
				<ikep4j:message pre='${preList}' key='title' />
			</th>
			<th scope="col" width="10%">
				<ikep4j:message pre='${preList}' key='registerName' />
			</th>
			<th scope="col" width="7%">
				<ikep4j:message pre='${preList}' key='attachFileCount' />
			</th>
			<th scope="col" width="7%">
				<ikep4j:message pre='${preList}' key='hitCount' />
			</th>
			<th scope="col" width="7%">
				<ikep4j:message pre='${preList}' key='recommendCount' />
			</th>
			<th scope="col" width="7%">
				<ikep4j:message pre='${preList}' key='linereplyCount' />
			</th>
		</tr>
	</thead> 
	<tbody>
		<c:choose>
		    <c:when test="${empty recommendReplyDetailList}">
				<tr>
					<td colspan="9" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="reitem" items="${recommendReplyDetailList}">
				<tr>
					<td>${reitem.itemSeqId}</td>
					<td>
						<c:if test="${reitem.isKnowhow == 0}">
							<ikep4j:message pre='${preList}' key='isKnowhow0' />
						</c:if>
						<c:if test="${reitem.isKnowhow == 1}">
							<ikep4j:message pre='${preList}' key='isKnowhow1' />
						</c:if>
						<c:if test="${reitem.isKnowhow == 3}">
							원문 게시판
						</c:if>
					</td>

					<td>
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${reitem.registDate}"/>
					</td>


					<td class="textLeft">
						<c:if test="${reitem.itemDisplay eq 1}"><span class="notice"></c:if> 
						<c:if test="${reitem.status == 5}">
						<span class="deletedItem">
						</c:if>
						<span>
						<c:if test="${reitem.status == 5}">
						<B>
						</c:if>
							<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readSearchItemView.do?itemId=${reitem.itemId}&isKnowhow=${reitem.isKnowhow}&popupYn=false'/>">${reitem.title}</a>
						<c:if test="${reitem.status == 5}">
						</B>
						</c:if>
						</span>	
					</td>
					<td>
						<div class="ellipsis"><a title="${reitem.registerName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${reitem.registerId}', 'bottom')">${reitem.registerName}</a></div>
					</td>
					<td>
						<c:if test="${reitem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if>
					</td>
					<td>${reitem.hitCount}</td>
					<td>${reitem.recommendCount}</td>
					<td>${reitem.linereplyCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose> 
	</tbody>
</table>
 </div>
	    <!--//blockDetail End-->	
		
		<!--blockButton Start-->
		<div class="blockButton"> 			
			<ul>
	            <li><a class="button" href="javascript:iKEP.closePop()" id="cancelBtn" name="cancelBtn"><span>close</span></a></li>&nbsp;
	        </ul>
		</div>
		<!--//blockButton End-->
		<div class="blockBlank_5px"></div>	
	
	<!--//popup_contents End-->
</div>