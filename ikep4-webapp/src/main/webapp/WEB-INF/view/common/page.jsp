<div class="pageNum">
	<ul>
		<li><a class="btn_page_first" href="#a" onclick="pageSubmit(1);"><span><ikep4j:message pre="${pagePrefix}" key="first"/></span></a></li>
		<li><a class="btn_page_pre" href="#a" onclick="pageSubmit(${pageCondition.previousPage});"><span><ikep4j:message pre="${pagePrefix}" key="previous"/></span></a></li>
		<c:forEach var="pageIndex" begin="${pageCondition.thisStartPage}" end="${pageCondition.thisEndPage}" step="1">
		<c:if test="${pageIndex eq pageCondition.page}">
			<li><span>${pageIndex}</span></li>
		</c:if>
		<c:if test="${pageIndex ne pageCondition.page}">
			<li><span<c:if test="${pageCondition.thisStartPage eq pageIndex}"> class="liFirst"</c:if><c:if test="${pageCondition.thisEndPage eq pageIndex}"> class="liLast"</c:if>><a href="#a" onclick="pageSubmit(${pageIndex});">${pageIndex}</a></span></li>
		</c:if>
		</c:forEach>
		<li><a class="btn_page_next" href="#a" onclick="pageSubmit(${pageCondition.nextPage});"><span><ikep4j:message pre="${pagePrefix}" key="next"/></span></a></li>
		<li><a class="btn_page_last" href="#a" onclick="pageSubmit(${pageCondition.totalPage});"><span><ikep4j:message pre="${pagePrefix}" key="last"/></span></a></li>
	</ul>
</div>