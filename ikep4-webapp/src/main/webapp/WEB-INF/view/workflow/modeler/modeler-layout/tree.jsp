<%@ include file="/base/common/taglibs.jsp"%>
<c:forEach var="partition" items="${processList.partitionList}">
	<li id=${partition.partitionId}>
		<a href='#' onclick="partitionIdClickListner('${partition.partitionId}', '${partition.partitionName}');">${partition.partitionName}</a>
		<ul>
			<c:forEach var="partitionProcess" items="${processList.partitionProcessList}">
				<c:forEach var="processModel" items="${processList.processModelList}">
					<c:if test="${partition.partitionId eq partitionProcess.partitionId && processModel.processId eq partitionProcess.processId && processModel.processVer eq partitionProcess.processVer}">
						<li id=${processModel.processId} onclick="processIdClickListner('${processModel.processId}', '${processModel.processName}', '${processModel.processVer}', '${partition.partitionId}', '${partition.partitionName}', '${processModel.description}', 'Y');">
							<a href='#'>${processModel.processName} (${processModel.processVer})</a>
						</li>
					</c:if>
				</c:forEach>
			</c:forEach>
		</ul>
	</li>
</c:forEach>



