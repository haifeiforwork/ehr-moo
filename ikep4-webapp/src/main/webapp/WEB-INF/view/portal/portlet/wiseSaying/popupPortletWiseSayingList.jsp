<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preWiseSaying" value="ui.portal.portlet.wiseSaying" />

<c:set var="preAlert" value="message.portal.portlet.wiseSaying.popupPortletWiseSayingList.alert"/>
<c:set var="preTitle" value="${preWiseSaying}.popupPortletWiseSayingList.title"/>
<c:set var="preButton" value="${preWiseSaying}.popupPortletWiseSayingList.button"/>
<c:set var="preMain" value="${preWiseSaying}.popupPortletWiseSayingList.main"/>
<c:set var="preForm" value="${preWiseSaying}.popupPortletWiseSayingList.form"/>

<c:set var="preList" value="${preWiseSaying}.popupPortletWiseSayingList.list"/>
<c:set var="preSearch" value="${preWiseSaying}.popupPortletWiseSayingList.search" />

<script type="text/javascript">
//<![CDATA[
var portletConfigId = "${param.portletConfigId}";
function reloadWiseSaying() {
	if(opener) {
		try {
			var wiseSayingPortlet = opener.portletManager.getPortletByConfigId(portletConfigId);
			wiseSayingPortlet.loadContent();
		} catch(e) {};
	}
}

(function($) {
	// Get Wise Saying List
	getList = function() {		
		$jq("#searchForm").attr("action", "<c:url value='popupPortletWiseSayingList.do' />?portletConfigId="+portletConfigId);
		$jq("#searchForm")[0].submit();		
	};
	
	getForm = function(serializeData) {		
		$jq.ajax({
			url : "<c:url value='popupPortletWiseSayingForm.do'/>",
			data : serializeData,
			type : "post",
			success : function(result) {
				$jq("#viewDiv").html(result);
			},
			error : function(event, request, settings) {
				alert("<ikep4j:message pre='${preAlert}' key='error' />");
			}
		});		
		//showCreateButton();		
	};
	
	// Sort
	sort = function(sortColumn, sortType) {		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}		
		getList();		
	};	
	
	setWiseSaying = function(id) {		
		$jq.ajax({
			url : "<c:url value='popupPortletWiseSayingForm.do'/>",
			data : {
				wiseSayingId : id
			},
			type : "post",
			success : function(result) {				
				$jq("#viewDiv").html(result);				
			},
			error : function(event, request, settings) { 				
				alert("<ikep4j:message pre='${preAlert}' key='error' />");				
			}
		});		
	};
	
	/**
	afterFileUpload = function (status, fileId, fileName, message, targetId) {
		
		var htmlText = "<a class='image-gallery' href='<c:url value='/support/fileupload/downloadFile.do'/>?fileId="+fileId+"' title='"+fileName+"'>"+fileName+"</a>";
		
		$jq("#fileNameSpan").html(htmlText);
		$jq("input[name=imageFileId]").val(fileId);
		$jq("#attachCheck").val("1");
		
	};
	**/
	
	resetInputForm = function () {

		$jq("#writerName").val("");
		$jq("#writerEnglishName").val("");
		$jq("#wiseSayingContents").val("");
		$jq("#wiseSayingEnglishContents").val("");
		/** 파일 이미지 선택으로 변경함으로 주석처리 **/
		//$jq("#imageFileId").val("");
		//$jq("#attachCheck").val("0");
		//$jq("#fileNameSpan").html("");
		
		$jq("#writerName").focus();
		
	};
	
	/** 파일 이미지 선택으로 변경함으로 주석처리 
	checkAttachFile = function () {
		
		if(!$jq("#fileChk").is(":checked")) {
			if(confirm("<ikep4j:message pre='${preAlert}' key='checkAttach' />")) {
				$jq("#fileChk").attr("checked", false);
				$jq("#attachCheck").val("0");
				$jq("#fileDiv").show();
			} else {
				$jq("#fileChk").attr("checked", true);
				$jq("#attachCheck").val("1");				
				return;
			}
		} else {
			$jq("#fileChk").attr("checked", true);
			$jq("#attachCheck").val("1");
			$jq("#fileDiv").hide();
		}		
	};
	**/
	
	// Ready
	$jq(document).ready(function() { 
		
		$jq("#portletWiseSayingForm:input:visible:enabled:first").focus();
		
		/** 파일 이미지 선택으로 변경함으로 주석처리 **/
		/**
		iKEP.showGallery($jq("a.image-gallery"));		
		$jq("#fileuploadBtn").click(function(event) { 
			iKEP.fileUpload(event.target.id, '0', '1');	
		});
		**/
		
		// 전체선택
		$jq("#checkedAll").click(function() {
	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq("input[name=chk]:not(checked)").attr("checked", true);
			}else{
		   		$jq("input[name=chk]:checked").attr("checked", false);
		    }
	    });
		
		$jq("#createPopupWiseSayingButton").click(function() {			
			$jq("#portletWiseSayingForm").trigger("submit");			
		});
		
		$jq("#multiDeletePopupWiseSayingButton").click(function() {  
			var itemIds = new Array();
			
			$("input[name=chk]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});
			
			if($("input[name=chk]:checked").length < 1) {
				alert("<ikep4j:message pre='${preAlert}' key='checkMultiDelete' />");				
				return;
			}
			
			if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
				$.post("<c:url value='popupPortletWiseSayingMultiDelete.do'/>", {"itemId" : itemIds.toString()}) 
				.success(function(result) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");
					reloadWiseSaying();
					getList();					
				})
			}
		});
		
		$jq("#cancelPopupWiseSayingButton").click(function() {  
			resetInputForm();
		}); 
		
		$jq("#excelPopupWiseSayingButton").click(function() {			
			var url = "<c:url value='excelPortletWiseSayingForm.do'/>";			
			iKEP.popupOpen(url, {width:900, height:210});			
		});
		
		$jq("#newPopupWiseSayingButton").click(function() {
			getList();			
		});
		
		$jq("#updatePopupWiseSayingButton").click(function() {
			/** 파일 이미지 선택으로 변경함으로 주석처리
			<c:if test="${!empty portletWiseSaying.fileData}">
			if($jq("#fileChk").is(":checked") && ($jq("#imageFileId").val() != $jq("#oldImageFileId").val())) {
				$jq("#imageFileId").val($jq("#oldImageFileId").val());
			}
			</c:if>
			if($jq("#fileChk").is(":checked")){
				$jq("#attachCheck").val(true);
			}
			**/
			
			$jq("#portletWiseSayingForm").trigger("submit");
	
		});
		
		$jq("#deletePopupWiseSayingButton").click(function() { 
			
			if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
				$jq.ajax({
					url : "<c:url value='popupPortletWiseSayingDelete.do' />",
					data : $jq("#portletWiseSayingForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");						
						reloadWiseSaying();
						getList();
					},
					error : function(event, request, settings) { 
						alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");
					}
				});
			} else {
				return;
			}
			
		});
		
		$jq("#closePopupWiseSayingButton").click(function() {			
			iKEP.closePop();			
		});
		
		var url = "";
		
		<c:choose>
			<c:when test="${!empty portletWiseSaying.wiseSayingId}">
				url = "<c:url value='popupPortletWiseSayingUpdate.do' />";
			</c:when>
			<c:otherwise>
				url = "<c:url value='popupPortletWiseSayingCreate.do' />";
			</c:otherwise>
		</c:choose>
		
		//getForm();
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"writerName" : {
					required : true,
					maxlength : 100
				},
				"writerEnglishName" : {
					maxlength : 100
				},
				"wiseSayingContents" : {
					maxlength : 1000
				},
				"wiseSayingEnglishContents" : {
					maxlength : 1000
				},
				"usage" : "required"
			},
			messages : {
				"writerName" : {
					required : "<ikep4j:message key='NotEmpty.portletWiseSaying.writerName' />",
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.writerName' />"
				},
				"writerEnglishName" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.writerName' />"
				},
				"wiseSayingContents" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.wiseSayingContents' />"
				},
				"wiseSayingEnglishContents" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.wiseSayingContents' />"
				},
				"usage" : "<ikep4j:message key='message.portal.portlet.wiseSaying.usage'/>"
			},
			submitHandler : function() {		    	
				$jq.ajax({
					url : url,
					data : $jq("#portletWiseSayingForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");						
						$jq("#tempId").val(result);						
						reloadWiseSaying();
						getList();
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#portletWiseSayingForm", validOptions);

		/**
		 * Validation Logic End
		 */
	});
	
})(jQuery);  
//]]>	
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre="${preTitle}" key="popupTitle" /></h1>
		<a href="#" onclick="iKEP.closePop(); return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
			<span><ikep4j:message pre="${preButton}" key="close" /></span>
		</a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">

		<!--blockListTable Start-->
		<div class="blockListTable">			
		
			<div id="listDiv">
				<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false">  
				<input type="hidden" id="tempId" name="tempId" value="" />

					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}"/>
					</spring:bind>
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}"/>
					</spring:bind>
				
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre="${preTitle}" key="popupTitle" /></h2>
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" onchange="getList(); return false;">
								<c:forEach var="code" items="${portalCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">
								<ikep4j:message pre="${preSearch}" key="pageCount.info"/>
								<span>${searchResult.recordCount}</span>
							</div>
						</div>
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->	
				
					<table summary="<ikep4j:message pre='${preList}' key='tableSummary' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col"	width="5%">
									<input type="checkbox" name="checkedAll" id="checkedAll" />
								</th>
								<th scope="col"	width="10%">
									<ikep4j:message pre="${preList}" key="num" />
								</th>
								<th scope="col" width="30%">
									<a href="#" onclick="sort('WRITER_NAME', '<c:if test="${searchCondition.sortColumn eq 'WRITER_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preList}" key="writerName" />
									</a>
								</th>
								<th scope="col" width="*">
									<a href="#" onclick="sort('WISE_SAYING_CONTENTS', '<c:if test="${searchCondition.sortColumn eq 'WISE_SAYING_CONTENTS'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preList}" key="wiseSayingContents" />
									</a>
								</th>
								<th scope="col" width="10%">
									<a href="#" onclick="sort('USAGE', '<c:if test="${searchCondition.sortColumn eq 'USAGE'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preWiseSaying}" key="usage" />
									</a>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="wiseSayingList" items="${searchResult.entity}" varStatus="status">
							<tr>
								<td class="textCenter">
									<input type="checkbox" name="chk" value="${wiseSayingList.wiseSayingId}"/>
								</td>
								<td class="textCenter">
									${status.count}
								</td>
								<td class="textCenter">
									<a href="#" onclick="setWiseSaying('${wiseSayingList.wiseSayingId}'); return false;">
										${wiseSayingList.writerName}
									</a>
								</td>
								<td class="textLeft">
									<div class="ellipsis">
									${wiseSayingList.wiseSayingContents}
									</div>
								</td>
								<td class="textCenter">
									<c:choose>
										<c:when test="${wiseSayingList.usage == 1}">
											<ikep4j:message pre="${preWiseSaying}" key="usage.use" />
										</c:when>
										<c:otherwise>
											<ikep4j:message pre="${preWiseSaying}" key="usage.disabled" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							</c:forEach>
							
							<c:if test="${empty searchResult.entity}">
							<tr>
								<td colspan="4" class="textCenter">
									<ikep4j:message pre="${preList}" key="noData" />
								</td>
							</tr>
							</c:if>			
						</tbody>
						
					</table>	
				
					<!--Page Number Start--> 
					<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Number End-->
				
				</form>
			</div>
					
		</div>
		<!--//blockListTable End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li>
					<a href="#" class="button" id="multiDeletePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preList}' key='delete' />">
						<span><ikep4j:message pre="${preButton}" key="delete" /></span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
		
		<div class="blockBlank_10px"></div>
		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre="${preMain}" key="view" /></h3>
		</div>
		<!--//subTitle_2 End--> 
		
		<!--blockDetail Start-->
		<div class="blockDetail">
		
			<div id="viewDiv">
				<form id="portletWiseSayingForm" name="portletWiseSayingForm" action="" method="post">  
				<input type="hidden" id="wiseSayingId" name="wiseSayingId" value="${portletWiseSaying.wiseSayingId}"/>
				<!--  
				<input type="hidden" id="oldImageFileId" name="oldImageFileId" value="${portletWiseSaying.imageFileId}"/>
				<input type="hidden" id="attachCheck" name="attachCheck" value="0"/>
				-->
					
					<table summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
						<caption></caption>
						<colgroup>
							<col width="80px"/>
							<col width="100px"/>
							<col width="*"/>
						</colgroup>
						<tbody>
							<!--writerName Start-->		
							<tr>
								<th scope="row" rowspan="2">
									<span class="colorPoint">*</span>
									<ikep4j:message pre="${preForm}" key="writerName" />
								</th>
								<th scope="row">
									<span class="colorPoint">*</span>
									<ikep4j:message pre="${preForm}" key="writerName" />
								</th>
								<td>
									<div>
										<spring:bind path="portletWiseSaying.writerName">
										<input type="text" id="writerName" name="writerName" value="${portletWiseSaying.writerName}" class="inputbox w100" />
										</spring:bind>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<ikep4j:message pre="${preForm}" key="writerEnglishName" />
								</th>
								<td>
									<div>
										<spring:bind path="portletWiseSaying.writerEnglishName">
										<input type="text" id="writerEnglishName" name="writerEnglishName" value="${portletWiseSaying.writerEnglishName}" class="inputbox w100" />
										</spring:bind>
									</div>
								</td>
							</tr>
							<!--//writerName End-->
								
							<!--wiseSayingContents Start-->		
							<tr>
								<th scope="row" rowspan="2">
									<ikep4j:message pre="${preForm}" key="wiseSayingContents" />
								</th>
								<th scope="row">
									<ikep4j:message pre="${preForm}" key="wiseSayingContents" />
								</th>
								<td>
									<div>
										<spring:bind path="portletWiseSaying.wiseSayingContents">
										<textarea id="wiseSayingContents" name="wiseSayingContents" class="inputbox w100" cols="" rows="5" title="êµ­ë¬¸ë´ì©"><c:out value="${portletWiseSaying.wiseSayingContents}" /></textarea>
										</spring:bind>	
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<ikep4j:message pre="${preForm}" key="wiseSayingEnglishContents" />
								</th>
								<td>
									<div>
										<spring:bind path="portletWiseSaying.wiseSayingEnglishContents">
										<textarea id="wiseSayingEnglishContents" name="wiseSayingEnglishContents" class="inputbox w100" cols="" rows="5" title="ìë¬¸ë´ì©"><c:out value="${portletWiseSaying.wiseSayingEnglishContents}" /></textarea>
										</spring:bind>
									</div>
								</td>
							</tr>
							<!--//wiseSayingContents End-->	
							
							<tr>
								<th scope="row" colspan="2">
									<ikep4j:message pre="${preForm}" key="imageAttach" />
								</th>
								<td>
									<ul style="list-style:none;">
										<li style="float:left;width:120px;text-align:center;">
											<img src="<c:url value='/base/images/common/img_wisesaying.gif'/>" alt="wise image01"/><br/>
											<input type="radio" name="imageFileId" value="1" <c:if test="${portletWiseSaying.imageFileId == 1}">checked="true"</c:if> <c:if test="${empty portletWiseSaying.imageFileId}">checked="true"</c:if> />
										</li>
										<li style="float:left;width:120px;text-align:center;">
											<img src="<c:url value='/base/images/common/img_wisesaying2.gif'/>" alt="wise image02"/><br/>
											<input type="radio" name="imageFileId" value="2" <c:if test="${portletWiseSaying.imageFileId == 2}">checked="true"</c:if> />
										</li>
										<li style="float:left;width:120px;text-align:center;">
											<img src="<c:url value='/base/images/common/img_wisesaying3.gif'/>" alt="wise image03"/><br/>
											<input type="radio" name="imageFileId" value="3" <c:if test="${portletWiseSaying.imageFileId == 3}">checked="true"</c:if> />
										</li>
										<li style="float:left;width:120px;text-align:center;">
											<img src="<c:url value='/base/images/common/img_wisesaying4.gif'/>" alt="wise image04"/><br/>
											<input type="radio" name="imageFileId" value="4" <c:if test="${portletWiseSaying.imageFileId == 4}">checked="true"</c:if> />
										</li>
										<li style="float:left;width:120px;text-align:center;">
											<img src="<c:url value='/base/images/common/img_wisesaying5.gif'/>" alt="wise image05"/><br/>
											<input type="radio" name="imageFileId" value="5" <c:if test="${portletWiseSaying.imageFileId == 5}">checked="true"</c:if> />
										</li>
									</ul>
									<!--  파일 이미지 선택으로 변경함으로 주석처리
									<c:if test="${!empty portletWiseSaying.fileData}">
									<input type="checkbox" id="fileChk" name="fileChk" value="1" checked="checked" onclick="checkAttachFile();" />
									<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletWiseSaying.fileData.fileId}" title="${portletWiseSaying.fileData.fileRealName}">
											<c:out value="${portletWiseSaying.fileData.fileRealName}"/>
									</a>
									</c:if>
									
									<div id="fileDiv" <c:if test="${!empty portletWiseSaying.fileData}">style="display:none;"</c:if>>
										<span id="fileNameSpan"></span>
										<a class="button" href="#" onclick="return false;">
											<span id="fileuploadBtn"><ikep4j:message pre="${preButton}" key="attachFile" /></span>
										</a>
									</div>
									-->
								</td>
							</tr>
							<tr>
								<th scope="row" colspan="2">
									<ikep4j:message pre="${preWiseSaying}" key="usage" />
								</th>
								<td>
									<div>
										<label><input name="usage" type="radio" value="1" <c:if test="${portletWiseSaying.usage == 1}">checked</c:if> /><ikep4j:message pre="${preWiseSaying}" key="usage.use"/></label>&nbsp;&nbsp;&nbsp;
										<label><input name="usage" type="radio" value="0" <c:if test="${portletWiseSaying.usage != 1}">checked</c:if> /><ikep4j:message pre="${preWiseSaying}" key="usage.disabled"/></label>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					
				</form>
				
				<div class="blockBlank_10px"></div>
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:choose>
						<c:when test="${empty portletWiseSaying.wiseSayingId}">
						<li>
							<a href="#" class="button" id="createPopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='create' />">
								<span><ikep4j:message pre="${preButton}" key="create" /></span>
							</a>
						</li>
						<li>
							<a href="#" class="button" id="excelPopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='excel' />">
								<span><ikep4j:message pre="${preButton}" key="excel" /></span>
							</a>
						</li>
						<li>
							<a href="#" class="button" id="cancelPopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='cancel' />">
								<span><ikep4j:message pre="${preButton}" key="cancel" /></span>
							</a>
						</li>
						</c:when>
						<c:otherwise>
						<li>
							<a href="#" class="button" id="newPopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='new' />">
								<span><ikep4j:message pre="${preButton}" key="new" /></span>
							</a>
						</li>
						<li>
							<a href="#" class="button" id="updatePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='update' />">
								<span><ikep4j:message pre="${preButton}" key="update" /></span>
							</a>
						</li>
						<li>
							<a href="#" class="button" id="deletePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='delete' />">
								<span><ikep4j:message pre="${preButton}" key="delete" /></span>
							</a>
						</li>
						</c:otherwise>
						</c:choose>
						
						<li>
							<a href="#" class="button" id="closePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
								<span><ikep4j:message pre="${preButton}" key="close" /></span>
							</a>
						</li>
					</ul>
				</div>
				<!--//blockButton End-->
			</div>
		
		</div>
		<!--//blockDetail End--> 
	
	</div>
	<!--//popup_contents End-->

</div>
<!--//popup End-->