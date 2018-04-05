<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#searchButton").click(function() {
			$.callProgress();
			$("#workHistoryForm").append("<input type=\"hidden\" name=\"imAdmin\" value=\"<c:out value="${params.imAdmin}"/>\"/>");
			$("#workHistoryForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"X\"/>");
			$("#workHistoryForm").submit();
		});
		
		$("#btnBasis, #btnDetail").click(function(){
			if(this.id == "btnBasis"){
				$("input[name=viewType]").val("BASIS");
			}else if(this.id == "btnDetail"){
				$("input[name=viewType]").val("DETAIL");
			}
			$("#searchButton").click();
		});
		
		$("select[name=imZeity]").change(function(event, params){
			
			if($("select[name=imZeity]").val() == ""){
				return;
			}
		
			$("#ajaxForm").html("");
			
			$(".parameter").each(function(index, value){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/retrieveEtSchkz.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						
						var target = $("select[name=imSchkz]");
						var data = result.ET_SCHKZ;
						
						target.html("");
						
						for(var i=0 ; i<data.length ; i++){
							if(params != undefined && data[i].KEY == params){
								target.append("<option value=\""+data[i].KEY+"\" selected >"+data[i].VALUE+"</option>");
							}else{
								target.append("<option value=\""+data[i].KEY+"\">"+data[i].VALUE+"</option>");
							}
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});
		
		$.initSet = function(){
			
			<c:if test="${empty params.imFirst}">
				$("#searchButton").click();
			</c:if>
			
			$("select[name=imZeity]").trigger("change", ["<c:out value='${params.imSchkz}'/>"]);
			
			<c:if test="${resultMap.EX_RESULT eq 'E' }">
				alert("<c:out value="${resultMap.EX_MESSAGE }"/>");
			</c:if>
			
			parent.iKEP.iFrameResize('#contentIframe');
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
.week-sum{ background-color:#FFDFDC !important;}
</style>

<c:choose>
	<c:when test="${!empty params.imOrgeh}">
		<c:set var="orgeh" value="${params.imOrgeh }"/>
	</c:when>
	<c:otherwise>
		<c:set var="orgeh" value="${resultMap.EX_FIRST_ORGEH }"/>
	</c:otherwise>
</c:choose>

<form id="workHistoryForm" name="workHistoryForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/workHistoryList.do'/>" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근무내역서</h1>
	
	<c:set var="etOrgeh" value="${resultMap.ET_ORGEH }"/>
	<c:set var="etZeity" value="${resultMap.ET_ZEITY }"/>
	<c:set var="etSchkz" value="${resultMap.ET_SCHKZ }"/>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">조회기간</span></td>
					<td>
						<input type="text" name="startDate" id="startDate" value="<c:out value="${params.startDate}"/>" class="input datepicker parameter" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;(<c:out value="${resultMap.EX_BEGWK }"/>)
						&nbsp;~&nbsp;
						<input type="text" name="endDate" id="endDate" value="<c:out value="${params.endDate}"/>" class="input datepicker parameter" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;(<c:out value="${resultMap.EX_ENDWK }"/>)
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">부서</span></td>
					<td>
						<select name="imOrgeh" id="imOrgeh">
							<c:forEach var="result" items="${etOrgeh }">
								<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq orgeh }"> selected </c:if>><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">근무반</span></td>
					<td>
						<select name="imZeity" id="imZeity" class="w150px parameter">
							<c:forEach var="result" items="${etZeity }">
								<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq params.imZeity}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
						<select name="imSchkz" id="imSchkz" class="w150px">
							<c:forEach var="result" items="${etSchkz }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBasis"><span>기본조회</span></a>
		<a href="#" class="button_img01" id="btnDetail"><span>상세조회</span></a>
	</div>
	
	<c:set var="etCalendar" value="${resultMap.ET_CALENDAR }"/>
	
	<c:set var="weekSumWktim" value=""/>
	<c:set var="weekSumOvtm"  value=""/>
	<c:set var="weekSumTotal" value=""/>
	<c:set var="weekSumVatm"  value=""/>
	
	<div class="table_box">
		<span style="display:inline-block; overflow-x:auto; overflow-y:hidden; .display:inline; *zoom:1;" class="list01">
			<c:if test="${params.viewType eq 'BASIS'}">
			
				<c:choose>
					<c:when test="${!empty mergeList }">
						
						<c:set var="tableWidth" value="${70 + resultMap.EX_CAL_WIDTH}"/>
						
						<table style="width:<c:out value="${tableWidth }"/>px" border="0" cellpadding="0" cellspacing="0">
							<caption></caption>
							<colgroup>
								<col width="70px"/>
								<col width="100px"/>
								<col width="120px"/>
								<col width="120px"/>
								<col width="100px"/>
								<c:forEach items="${etCalendar }" var="result" varStatus="inx">
									<col width="70px"/>
									<c:if test="${inx.count mod 7 eq 0 }">
										<col width="70px"/>
									</c:if>
								</c:forEach>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">성명</th>
									<th scope="col" rowspan="2">부서</th>
									<th scope="col" rowspan="2">포지션</th>
									<th scope="col" rowspan="2">근무반</th>
									<th scope="col" rowspan="2">근태상황</th>
									
									<c:forEach items="${etCalendar }" var="result" varStatus="inx">
										<th scope="col" width="70px">
											<fmt:parseDate var="dateString" value="${result.DATE}" pattern="yyyyMMdd" />
											<fmt:formatDate value="${dateString}" pattern="MM월dd일" />
										</th>
										<c:if test="${inx.count mod 7 eq 0 }">
											<th scope="col" rowspan="2" class="week-sum">소계</th>
										</c:if>
									</c:forEach>
								</tr>
								<tr>
									<c:forEach items="${etCalendar }" var="result">
										<th scope="col"><c:out value="${result.WEEK }"/></th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${mergeList }" var="mergeRow">
									<tr>
										<td rowspan="4">${mergeRow.ENAME }</td>
										<td rowspan="4">${mergeRow.ORGTX }</td>
										<td rowspan="4">${mergeRow.PLSTX }</td>
										<td rowspan="4">${mergeRow.RTEXT }</td>
										<td>근무일정</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">TTEXT<c:out value="${inx.index }"/></c:set>
											<td>
												<c:out value="${mergeRow[key] }"/>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">&nbsp;</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
										<td>기본근로 </td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">WKTIM<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00' }">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumWktim" value="${weekSumWktim + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumWktim}"/>
													<c:set var="weekSumWktim" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
									<td>연장근로 </td>
									<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
										<c:set var="key">OVTM<c:out value="${inx.index }"/></c:set>
										<td>
											<c:if test="${mergeRow[key] ne '0.00' }">
												<c:out value="${mergeRow[key] }"/>
												<c:set var="weekSumOvtm" value="${weekSumOvtm + mergeRow[key]}"/>
											</c:if>
										</td>
										<c:if test="${inx.count mod 7 eq 0 }">
											<td class="week-sum">
												<c:out value="${weekSumOvtm}"/>
												<c:set var="weekSumOvtm" value=""/>
											</td>
										</c:if>
									</c:forEach>
									</tr>
									<tr>
										<td>계(기본+연장)</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">TOTAL<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00' }">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumTotal" value="${weekSumTotal + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumTotal}"/>
													<c:set var="weekSumTotal" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<caption></caption>
							<colgroup>
								<col width="70px"/>
								<col width="100px"/>
								<col width="120px"/>
								<col width="120px"/>
								<col width="100px"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col">성명</th>
									<th scope="col">부서</th>
									<th scope="col">포지션</th>
									<th scope="col">근무반</th>
									<th scope="col">근태상황</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="5">조회된 데이터가 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${params.viewType eq 'DETAIL'}">
			
				<c:choose>
					<c:when test="${!empty mergeList }">
						
						<c:set var="tableWidth" value="${110 + resultMap.EX_CAL_WIDTH}"/>
						
						<table style="width:<c:out value="${tableWidth }"/>px" border="0" cellpadding="0" cellspacing="0">
							<caption></caption>
							<colgroup>
								<col width="70px"/>
								<col width="100px"/>
								<col width="120px"/>
								<col width="120px"/>
								<col width="70px"/>
								<col width="70px"/>
								<c:forEach items="${etCalendar }" var="result" varStatus="inx">
									<col width="70px"/>
									<c:if test="${inx.count mod 7 eq 0 }">
										<col width="70px"/>
										
									</c:if>
									
								</c:forEach>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" rowspan="2">성명</th>
									<th scope="col" rowspan="2">부서</th>
									<th scope="col" rowspan="2">포지션</th>
									<th scope="col" rowspan="2">근무반</th>
									<th scope="col" rowspan="2" colspan="2">근태상황</th>
									
									<c:forEach items="${etCalendar }" var="result" varStatus="inx">
										<th scope="col" width="70px">
											<fmt:parseDate var="dateString" value="${result.DATE}" pattern="yyyyMMdd" />
											<fmt:formatDate value="${dateString}" pattern="MM월dd일" />
										</th>
										<c:if test="${inx.count mod 7 eq 0 }">
											<th scope="col" rowspan="2" class="week-sum">소계</th>
										</c:if>
									</c:forEach>
								</tr>
								<tr>
									<c:forEach items="${etCalendar }" var="result">
										<th scope="col"><c:out value="${result.WEEK }"/></th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${mergeList }" var="mergeRow">
									<tr>
										<td rowspan="8">${mergeRow.ENAME }</td>
										<td rowspan="8">${mergeRow.ORGTX }</td>
										<td rowspan="8">${mergeRow.PLSTX }</td>
										<td rowspan="8">${mergeRow.RTEXT }</td>
										<td colspan="2">근무일정</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">TTEXT<c:out value="${inx.index }"/></c:set>
											<td>
												<c:out value="${mergeRow[key] }"/>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">&nbsp;</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
										<td colspan="2">기본근로 </td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">WKTIM<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00' }">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumWktim" value="${weekSumWktim + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumWktim}"/>
													<c:set var="weekSumWktim" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
									<td colspan="2">연장근로 </td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">OVTM<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00' }">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumOvtm" value="${weekSumOvtm + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumOvtm}"/>
													<c:set var="weekSumOvtm" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
										<td colspan="2">계(기본+연장)</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">TOTAL<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00' }">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumTotal" value="${weekSumTotal + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumTotal}"/>
													<c:set var="weekSumTotal" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
									
									<tr>
										<td rowspan="2">휴가</td>
										<td>유형</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">ATEXT<c:out value="${inx.index }"/></c:set>
											<td>
												<c:out value="${mergeRow[key] }"/>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">&nbsp;</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
										<td>대근자</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key1">TATX1<c:out value="${inx.index }"/></c:set>
											<c:set var="key2">TATX2<c:out value="${inx.index }"/></c:set>
											<c:set var="key3">TATX3<c:out value="${inx.index }"/></c:set>
											<c:set var="key4">TATX4<c:out value="${inx.index }"/></c:set>
											<td>
												<c:out value="${mergeRow[key1] }"/><c:if test="${!empty mergeRow[key1] && !empty mergeRow[key2] }"><br/></c:if>
												<c:out value="${mergeRow[key2] }"/><c:if test="${!empty mergeRow[key2] && !empty mergeRow[key3] }"><br/></c:if>
												<c:out value="${mergeRow[key3] }"/><c:if test="${!empty mergeRow[key3] && !empty mergeRow[key4] }"><br/></c:if>
												<c:out value="${mergeRow[key4] }"/>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">&nbsp;</td>
											</c:if>
										</c:forEach>
									</tr>
									
									<tr>
										<td rowspan="2">대근O/T</td>
										<td>휴가자</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">VA_ENAME<c:out value="${inx.index }"/></c:set>
											<td>
												<c:out value="${mergeRow[key] }"/>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">&nbsp;</td>
											</c:if>
										</c:forEach>
									</tr>
									<tr>
										<td>대근시간</td>
										<c:forEach items="${etCalendar }" var="calendar" varStatus="inx">
											<c:set var="key">VATM<c:out value="${inx.index }"/></c:set>
											<td>
												<c:if test="${mergeRow[key] ne '0.00'}">
													<c:out value="${mergeRow[key] }"/>
													<c:set var="weekSumVatm" value="${weekSumVatm + mergeRow[key]}"/>
												</c:if>
											</td>
											<c:if test="${inx.count mod 7 eq 0 }">
												<td class="week-sum">
													<c:out value="${weekSumVatm}"/>
													<c:set var="weekSumVatm" value=""/>
												</td>
											</c:if>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<caption></caption>
							<colgroup>
								<col width="70px"/>
								<col width="100px"/>
								<col width="120px"/>
								<col width="120px"/>
								<col width="100px"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col">성명</th>
									<th scope="col">부서</th>
									<th scope="col">포지션</th>
									<th scope="col">근무반</th>
									<th scope="col">근태상황</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="5">조회된 데이터가 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</c:if>
		</span>
	</div>
	<input type="hidden" name="viewType" value="<c:out value="${params.viewType}"/>"/>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
