<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.forum.discussionForm" />
<c:set var="refererUrl"><%=request.getHeader("referer") %></c:set> <%--전페이지주소--%>

<c:choose>
	<c:when test="${fn:contains(refererUrl,'myActivity.do') || fn:contains(refererUrl,'popularList.do') || fn:contains(refererUrl,'bestList.do') || fn:contains(refererUrl,'popularList.do') || fn:contains(refererUrl,'lastList.do')|| fn:contains(refererUrl,'getView.do')}">
		<c:set var="listUrl" value="${refererUrl }"/>
	</c:when>
	<c:otherwise>
		<c:set var="listUrl" value="main.do"/>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
//<![CDATA[	
	//validation
	var validOptions = {
		rules : {
			title :	{
				required : true,
				maxlength  : 500
			}
			,contents :	{
				required : true,
				maxlength  : 2000
			}
			,tag :	{
				required : true,
				maxlength  : 100
				,tagCount :10
				,tagDuplicate: true
				,tagWord : true
			}
			,imageId : "required"
		},
		messages : {
			title : {
				required : "<ikep4j:message key='NotEmpty.frDiscussion.title' />",
				maxlength : "<ikep4j:message key='Size.frDiscussion.title' />"
			}
			,contents : {
				required : "<ikep4j:message key='NotEmpty.frDiscussion.contents' />",
				maxlength : "<ikep4j:message key='Size.frDiscussion.contents' />"
			}
			,tag : {
				required : "<ikep4j:message key='NotEmpty.frDiscussion.tagName'/>",
				maxlength : "<ikep4j:message key='Size.frDiscussion.tagName' arguments='100'/>"
				,tagCount :"<ikep4j:message key='MaxCount.collpack.forum.tag' arguments='10'/>"
				,tagDuplicate :"<ikep4j:message key='Duplicate.collpack.forum.tag'/>"	
				,tagWord :"<ikep4j:message key='Check.forum.tagWord'/>"
			}
			,imageId : {
				required : "<ikep4j:message key='NotEmpty.frDiscussion.imageId'/>"
			}
		},
		notice : {
			title : "<ikep4j:message key='NotEmpty.frDiscussion.titleNotice' />",
			contents : "<ikep4j:message key='NotEmpty.frDiscussion.contentsNotice' />",
			tag: "<ikep4j:message key='NotEmpty.frDiscussion.tagNameNotice'/>"
			,imageId: "<ikep4j:message key='NotEmpty.frDiscussion.imageIdNotice'/>"
		}

	};
	
	

	function submitAction(){
		
		var f = $jq('#frDiscussionForm');
		
		f.submit();
		
	}
		 

	function goList(){
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != ''}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		location.href='${frDiscussion.listType}List.do?'+param;
	}
	
	 //업로드완료후 fileId 리턴
	function afterFileUpload(status, fileId, fileName, message, gubun) {
		$jq("#viewDiv").html('<img src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId='+fileId+'&thumbnailYn=Y" width="100" height="100" alt="image"/>');
		$jq("input[name=imageId]").val(fileId);

	};
	
    $jq(document).ready(function() {

           //파일업로드 버튼 이벤트 바인딩
   		$jq('#fileuploadBtn').click(function(event) {

            //파일업로드 팝업창
            //파라미터(이벤트객체이름,에디터에서사용여부(0:일반,1:에디터에서),이미지여부(0:모든파일,1:이미지파일만 가능))
   			iKEP.fileUpload(event.target.id,'0','1');

   		});

   		new iKEP.Validator('#frDiscussionForm', validOptions);
        
	});
  //]]>	
	</script>
 

<h1 class="none">Contents Area</h1>
				
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>
	<c:choose>
		<c:when test="${empty(param.discussionId)}">
			<ikep4j:message pre='${preUi}' key='title'/>
		</c:when>
		<c:otherwise>
			<ikep4j:message pre='${preUi}' key='title.update'/>
		</c:otherwise>
	</c:choose>
	</h3>
</div>
<!--//subTitle_2 End-->


<form id="frDiscussionForm" method="post" action="<c:url value='createDiscussion.do'/>" >
	<spring:bind path="frDiscussion.listType">
		<input name="${status.expression}" type="hidden" value="${status.value}" />
	</spring:bind> 
	<input name="docIframe" type="hidden" value="${param.docIframe}"  />
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="table">
			<caption></caption>
			<tbody>
			
				<spring:bind path="frDiscussion.title">
					<tr> 
						<th width="18%" scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='dis.title'/></label></th>
						<td >	
							<div>
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${status.value}" />
							<c:forEach items="${status.errorMessages}" var="error">
					       		<label for="${status.expression}" class="serverError"> ${error}</label>
					        </c:forEach>
					        </div>
						</td> 
					</tr>				
				</spring:bind>
				<spring:bind path="frDiscussion.categoryId">
					<tr> 
						<th width="18%" scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='category'/></label></th>
						<td ><div>
							<select name="${status.expression}" id="${status.expression}">
								<c:forEach var="category" items="${categoryList}">
									<option value="${category.categoryId}" <c:if test="${category.categoryId eq status.value}">selected="selected"</c:if>>
										${category.categoryName}
									</option>
								</c:forEach>
							</select>
							</div>
						</td> 
					</tr>				
				</spring:bind>
				
				<spring:bind path="frDiscussion.contents">
					<tr> 
						<th width="18%" scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='contents'/></label></th>
						<td><div>
							<textarea id="${status.expression}" name="${status.expression}" class="w100" cols="" rows="20" >${status.value}</textarea>
							</div>
							<c:forEach items="${status.errorMessages}" var="error">
					       		<label for="${status.expression}" class="serverError"> ${error}</label>
					        </c:forEach>
						</td> 
					</tr>				
				</spring:bind> 
				
				<spring:bind path="frDiscussion.imageId">
				<tr>
					<th scope="row"><label for="fileuploadBtn"><ikep4j:message pre='${preUi}' key='img'/></label></th>
					<td> <div>
						<span id="viewDiv" >
							<c:if test="${!empty(status.value)}">
								<img src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${status.value}&amp;thumbnailYn=Y" width="100" height="100" alt="image"/>
							</c:if>
							</span> 
						   <input id="${status.expression}" name="${status.expression}" class="inputbox w80" type="hidden" value="${status.value}" title="${status.expression}" />
							<a class="button" href="#a" >
								<span id="fileuploadBtn" ><ikep4j:message pre='${preUi}' key='fileCon'/></span>
							</a>
							<c:forEach items="${status.errorMessages}" var="error">
					       		<label for="${status.expression}" class="serverError"> ${error}</label>
					        </c:forEach>
						</div>
					</td>
				</tr>
				</spring:bind>
				
				<spring:bind path="frDiscussion.tag">
				<tr>
					<th scope="row"><label for="tag"><ikep4j:message pre='${preUi}' key='tag'/></label></th>
					<td>
						<div>
						<c:choose>
							<c:when test="${!empty(status.value)}">
								<c:set var="tagText" value="${status.value}"/>
							</c:when>
							<c:otherwise>
								<c:set var="tagText" ><c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach></c:set>
							</c:otherwise>
						</c:choose>
						<input name="${status.expression}" class="inputbox w100" id="tag" type="text" value="${tagText}"/>
				        <div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='tagContents'/></div>
				        <c:forEach items="${status.errorMessages}" var="error">
				       		<label for="${status.expression}" class="serverError"> ${error}</label>
				        </c:forEach>
				        </div>
					</td>
				</tr>
				</spring:bind>							
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
											
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#a" onclick="submitAction();" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
			<li><a class="button" href="${listUrl }" title="<ikep4j:message pre='${preUi}' key='list'/>"><span><ikep4j:message pre='${preUi}' key='list'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</form>							
