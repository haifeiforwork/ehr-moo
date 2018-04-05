<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<!--blockDetail Start-->
<div class="blockDetail">
	<c:choose>
		<c:when test="${editType == 'CREATE'}">
			<form id="socialBoardItemForm" name="socialBoardItemForm" method="post" action="<c:url value='/socialpack/socialblog/createSocialBoardItem.do'/>">
		</c:when>
		<c:otherwise>
			<form id="socialBoardItemForm" name="socialBoardItemForm" method="post" action="<c:url value='/socialpack/socialblog/updateSocialBoardItem.do'/>">
				<spring:bind path="socialBoardItem.itemId">
				<input name="${status.expression}"  value="${status.value}" type="hidden" /> 
				</spring:bind>
		</c:otherwise>
	</c:choose>
	<input name="msie"	type="hidden" value="0" />

	<table summary="<ikep4j:message pre='${preMain}' key='writePosting' />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<tbody> 
		<tr> 
			<spring:bind path="socialBoardItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div>
					<input 
					id="${status.expression}"
					name="${status.expression}" 
					type="text" 
					class="inputbox" style="width:98%;" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" />
				</div>
			</spring:bind>			
			</td>
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='category' /></th>
			<td>
				<div>
					<spring:bind path="socialBoardItem.categoryId">
					<select id="categoryId" name="categoryId">
						<option value=""><ikep4j:message pre='${preDetail}' key='category.select' /></option>
						<c:if test="${socialCategoryList != null}">
						<c:forEach var="socialCategoryList" items="${socialCategoryList}" varStatus="status">
							<option value="<c:out value='${socialCategoryList.categoryId}'/>" <c:if test="${socialCategoryList.categoryId eq socialBoardItem.categoryId}">selected="selected"</c:if>><c:out value="${socialCategoryList.categoryName}"/></option>
						</c:forEach>				
						</c:if>
					</select>
					</spring:bind>
				</div>
			</td>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='readPermission' /></th>
			<td>
				<div>
					<spring:bind path="socialBoardItem.readPermission">
					<label id="selectOpen"><input type="radio" class="radio" title="<ikep4j:message pre='${preDetail}' key='readPermission.open' />" name="readPermission" value="0" checked="checked" /><ikep4j:message pre='${preDetail}' key='readPermission.open' /></label>&nbsp;&nbsp;
					<label id="selectCancel"><input type="radio" class="radio" title="<ikep4j:message pre='${preDetail}' key='readPermission.close' />" name="readPermission" value="1"  /><ikep4j:message pre='${preDetail}' key='readPermission.close' /></label>
					</spring:bind>
				</div>
			</td>  
		</tr>		
		<tr>  
			<spring:bind path="socialBoardItem.tag">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div>
					<input 
					id="${status.expression}"
					name="${status.expression}" 
					type="text" 
					class="inputbox w40" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" />
				</div>
				<ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" />	
			</td>		
			</spring:bind>	
		</tr>						
		<tr>  
			<spring:bind path="socialBoardItem.contents">
			<td colspan="4" class="ckeditor">
				<div id="editorDiv"">
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
				</div>
			</td> 
			</spring:bind> 
		</tr>
		</tbody> 
	</table>
	<div id="fileUploadArea" class="mt10"></div>
	</form>
</div> 
<!--//blockDetail End--> 
<!--tableBottom Start-->
<div class="tableBottom mb20">										
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="listSocialBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			<li><a id="saveSocialBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		 </ul>
	</div>
	<!--//blockButton End--> 
</div>
<!--//tableBottom End--> 	