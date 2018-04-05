<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<html>

${functionTag }
<script>
	function test() {
		var mm = document.all["mm"].value;
		alert(mm);
	}
	
	function addRecord(objId) {
		alert(objId);
	}
	
	j = 0; // 현재 제어할 table의 tr의 index값. (0부터 시작함) 
	function addLine(objId){
		var table	= document.all[objId + "_table"];
		var lineCount	= table.rows.length;
	    Row = table.insertRow();
		Cell = Row.insertCell();
	    insert	= 	"<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">							" +
				    "<tr>                                                                   			" +
				    "<td align=\"center\"  width=\"20\">"+lineCount+"</td>                        				" +
				    "<td><input type=\"text\" name=\""+objId+"_" + lineCount +"\" style=\"width:100%\"/></td>  	" +
				    "</tr>                                                                  			" +
				    "</table>                                                               			";
		Cell.innerHTML = insert; 
	} 	
	
	function delLine(objId){ 
		var table	= document.all[objId + "_table"];
		var lineCount	= table.rows.length-1;
	    if(lineCount > 0){ 
	    	table.deleteRow(lineCount); 

	    } 
	} 	
	
	function command() {
		var form		= document.all["frm_Command"];
		var url			= iKEP.getContextRoot() + "/workflow/workflow.do";
		alert("url : " + url);
		form.action		= url;
		form.submit();
	}
	
	function completeClose() {
		opener.location.reload();
		this.close();
	}
	
	function showErrorMsg(message) {
		alert(message);
	}
</script>
<body topmargin="0" leftmargin="0">


<table border="1" width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr height="40" bgcolor="#8989af">
		<td>
			&nbsp;&nbsp;<b><font color="#ffffff">WorkInstance Information</font></b>
		</td>
	</tr>
	<tr>
		<td>
			<table id="" border="0" width="100%" height="100%">
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						Process Information
						<form id="frm_Command" method="post"  target="ifrm_Deploy">
							<input type="hidden" name="command" value=""/>                                                    		
							<input type="hidden" name="processId" value="${processId}"/>                                                  		
							<input type="hidden" name="processInsId" value="${instanceId}"/>                                               		
							<input type="hidden" name="activityId" value="${activityId}"/>                                                 		
							<input type="hidden" name="insLogId" value="${logId}"/>                                                 			
							<input type="hidden" name="userId" value="${user.userId }"/> 						
						
							<table id="" border="0" width="100%" height="100%" bgcolor="#ffffff">
								<tr>
									<td valign="top">
										<div class="tableTop">
											<div class="listInfo">
											<table id="" border="1" width="100%" cellpadding="0" cellspacing="0">
												<tr height="30">
													<td width="150" align="left" bgcolor="#bebecf">
														&nbsp;Process Id [Name]
													</td>
													<td bgcolor="#dededf">
														&nbsp;${processId} [${processName}]
													</td>
												</tr>
												<tr height="30">
													<td width="150" align="left" bgcolor="#bebecf">
														&nbsp;Activity Id [Name]
													</td>
													<td bgcolor="#dededf">
														&nbsp;${activityId} [${activityName}]
													</td>
												</tr>
												<tr height="30">
													<td width="150" align="left" bgcolor="#bebecf">
														&nbsp;InstanceId [LogId]
													</td>
													<td bgcolor="#dededf">
														&nbsp;${instanceId} [${logId}]
													</td>
												</tr>										
												<tr height="30">
													<td width="150" align="left" bgcolor="#bebecf">
														&nbsp;Title
													</td>
													<td bgcolor="#dededf">
														&nbsp;${title}
													</td>
												</tr>
											</table>
											</div>
										</div>
										<br>
										Process Data Information
										<br><br>
										<table id="" border="1" width="100%" cellpadding="0" cellspacing="0">
											<tr height="30">
												<td width="150" align="left" bgcolor="#bebecf">
													&nbsp;<b>Input Parameter</b>
												</td>
												<td width="150" align="left" bgcolor="#bebecf">
													&nbsp;<b>Output Parameter</b>
												</td>
											</tr>
											<tr height="30">
												<td width="50%" align="left" bgcolor="#dededf" valign="top">
													<table width="100%"  style="font-size:13;" border="1" cellpadding="0" cellspacing="0">
														<c:forEach var="inputParam" items="${inputParam}" varStatus="status">
															<tr height="32">
																<td width="65%">
																<c:choose>
																	<c:when test="${inputParam.useIndex == 'FALSE'}">
																		<font color="#6666cc">
																		<b>
																		${inputParam.datafieldName}
																		(${fn:substring(inputParam.datafieldId, 3, fn:length(inputParam.datafieldId))}) 
																		</b>
																		</font>
																	</c:when>
																	<c:otherwise>
																		${inputParam.datafieldName}
																		(${fn:substring(inputParam.datafieldId, 3, fn:length(inputParam.datafieldId))})																	
																	</c:otherwise>
																</c:choose>	
																</td>
																<td align="right">
																<c:choose>
																	<c:when test="${inputParam.arrayType == 'TRUE'}">
																		
																		<c:choose>
																			<c:when test="${inputParam.arrayList[0] != null}">	
																			
																				<c:forEach var="arrayList" items="${inputParam.arrayList}" varStatus="status">														
																				<table width="100%" cellpadding="0" cellspacing="0">
																					<tr>
																						<td width="20" align="center">
																							${status.index }
																						</td>
																						<td>
																							<input type="text" name="${inputParam.datafieldId}" value="${inputParam.arrayList[status.index]}" style="width:100%" readOnly/>
																						</td>
																					</tr>
																				</table>	
																				</c:forEach>
																				
																			</c:when>
																			<c:otherwise>
																					<table width="100%" cellpadding="0" cellspacing="0">
																						<tr>
																							<td width="20" align="center">
																								0
																							</td>
																							<td>
																								<input type="text" name="${inputParam.datafieldId}" value="" style="width:100%" readOnly/>
																							</td>
																						</tr>
																					</table>
																			</c:otherwise>
																		</c:choose>
																				
																	</c:when>
																	<c:otherwise>
																		<input type="text" name="${inputParam.datafieldId}" value="${inputParam.datafieldValue}" style="width:100%"/>
																	</c:otherwise>
																</c:choose>	
																</td>
															</tr>
														</c:forEach>
													</table>													
												</td>
												<td width="50%" align="left" bgcolor="#dededf" valign="top">
													<table width="100%"  style="font-size:13;" border="1" cellpadding="0" cellspacing="0">
														<c:forEach var="outputParam" items="${outputParam}" varStatus="status">
															<tr height="32">
																<td width="65%">
																	<table width="100%" border="0" style="font-size:13;">
														
																		<tr>
																			<td>
																				<c:choose>
																					<c:when test="${outputParam.useIndex == 'FALSE'}">
																						<font color="#6666cc">
																						<b>
																						${outputParam.datafieldName}
																						(${fn:substring(outputParam.datafieldId, 4, fn:length(outputParam.datafieldId))})
																						</b>
																						</font>
																					</c:when>
																					<c:otherwise>
																						${outputParam.datafieldName}
																						(${fn:substring(outputParam.datafieldId, 4, fn:length(outputParam.datafieldId))})
																					</c:otherwise>
																				</c:choose>																				
																			</td>
																		<c:if test="${outputParam.arrayType == 'TRUE'}">
																			<td align="right">
																			<input type="button" name="add" value="add" onclick="addLine('${outputParam.datafieldId}')"/>
																			<input type="button" name="del" value="del" onclick="delLine('${outputParam.datafieldId}')"/>
																			</td>
																		</c:if>
																		</tr>
																	</table>
																</td>
																<td>
																	<table id="${outputParam.datafieldId}_table" width="100%" cellpadding="0" cellspacing="0">
																		<tr>
																			<td align="right">
																			<c:choose>
																				<c:when test="${outputParam.arrayType == 'TRUE'}">

																					<c:choose>
																						<c:when test="${outputParam.arrayList[0] != null}">	
																						
																							<c:forEach var="arrayList" items="${outputParam.arrayList}" varStatus="status">														
																							<table width="100%" cellpadding="0" cellspacing="0">
																								<tr>
																									<td width="20" align="center">
																										${status.index }
																									</td>
																									<td>
																										<input type="text" name="${outputParam.datafieldId}_${status.index }" value="${outputParam.arrayList[status.index]}" style="width:100%" />
																									</td>
																								</tr>
																							</table>	
																							</c:forEach>
																						</c:when>
																						<c:otherwise>
																							<table width="100%" cellpadding="0" cellspacing="0">
																								<tr>
																									<td width="20" align="center">
																										0
																									</td>
																									<td>
																										<input type="text" name="${outputParam.datafieldId}_0" style="width:100%"/>
																									</td>
																								</tr>
																							</table>																																										
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise><input type="text" name="${outputParam.datafieldId}" style="width:100%" value="${outputParam.datafieldValue}"/></c:otherwise>
																			</c:choose>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</form>	
							
							
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr height="10">
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp; 
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="40">
		<td>
			<table border="0" width="100%" height="100%">
				<tr>
					<td width="80%" align="left">
						&nbsp;${completeBtnTag}
					</td>
					<td width="20%" align="right">
						&nbsp;<input type="button" name="closeWin" value="Close" onclick="window.close()"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<iframe name="ifrm_Deploy" style="display:none;width:100%;height:50"></iframe>	 


</body>
</html>