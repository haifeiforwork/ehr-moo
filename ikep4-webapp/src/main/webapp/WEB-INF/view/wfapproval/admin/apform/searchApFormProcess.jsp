<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.admin.apform.process.header" />
<c:set var="preProcess" value="ui.wfapproval.admin.apform.process" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!-- 
	
	var debug = true;
	
	/**
	 * Debug Exception 메세지를 반환한다.
	 * 
	 * @param {Object} sT Exception Title
	 * @param {Object} oE Exception
	 */
	function DebugE(sT, oE)
	{
		var sMsg =	"Javascript Debug \n\n"		+
					"[Title		: "+sT+"]\n" 	+
					"[Message	: ("+(oE.number & 0xFFFF)+")"+oE.description+"]\n\n";
					
		if(debug) alert(sMsg);
	}
	
	
	(function($) {
		
		var dialogWindow;
		var selectData;
	
		/**
		 * 프로세스 선택
		 * @param {Object} data
		 */
		f_getProcessInfo = function (procId, procName, procType, procVer){
			selectData = {
				id		: procId,
				name	: procName,
				type	: procType,
				ver		: procVer
			};
			
			//프로세스 화면생성
			f_viewProcessInfo();
		};
		
		/**
		 * 프로세스 화면 생성
		 */
		f_viewProcessInfo = function (){
			
			if(! selectData) return;
			
			if (selectData.name) {
				//상세화면 프로세스명 설정.
				$("#selectProcess").text(" > " + selectData.name);
			}
			
			var url = '<c:url value="/workflow/modeler/prism.do?mode=model"/>'
					+ "&processType=workflow"
					+ "&partitionId=approval"
					+ "&processId=" + selectData.id
					+ "&version=" + selectData.ver  
					+ "&minimapView=false"
					+ "&saveView=false"
					+ "&refreshView=false"
					+ "&scale=0.8";
					
					/*
					 http://ikep.lgcns.com:8080/ikep4-webapp/workflow/modeler/prism.do?
					 mode=model&
					 processType=workflow&
					 partitionId=approval&
					 processId=process4&
					 version=1.0&
					 minimapView=false&
					 saveView=false&
					 refreshView=false
					 */
			
			//alert(url);
			$("#processDetail").html('<iframe id="prism" src="' + url + '" frameborder="0" height="100%" width="100%;" scrolling="no"></iframe>');
		};
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() { 
		
			fnCaller = function (params, dialog) {
				if(params) {
					//alert(params.id);
					//selectData = params;
					
					selectData = {
						id		: params.id,
						name	: params.name,
						type	: params.type,
						ver		: params.ver
					};
					
					//프로세스 화면생성
					f_viewProcessInfo();
				}
				
				dialogWindow 	= dialog;
			};
			
			//프로세스 목록
			$("#processList").load('<c:url value="/wfapproval/admin/apform/ajaxListApFormProcess.do"/>')
       		.error(function(event, request, settings) { alert("error"); });
			
			/**
		    * ApForm 버튼영역 실행
		    */
			$("#searchApFormProcessButton a").click(function(){
	            switch (this.id) {
	                case "applyProcessButton":
						if (!selectData) {
							alert("선택한 결재선이 없습니다.");
							return false;
						}
						
						dialogWindow.callback(selectData);
						dialogWindow.close();
						break;
	                case "cancelProcessButton":
	                    dialogWindow.close();
	                    break;
	                default:
	                    break;
	            }
	        });
			
		});
		
	})(jQuery);  
	
//-->
</script>
			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<!--
<div id="pageTitle">
	<h2 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div>
</div>
-->
<!--//pageTitle End-->

<!--popup Start
<div id="popup" style="min-width:100%">--><!--컨텐츠가 깨지지 않게 min-width를 상황에 따라 설정할 것-->

	<!--popup_title Start
	<div id="popup_title">
		<h1><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>
		<a href="#a"><span><ikep4j:message pre="${preButton}" key="close" /></span></a>
	</div>-->
	<!--//popup_title End-->

	<!--popup_contents Start
	<div id="popup_contents">-->
		
		<!--blockShuttle Start-->
		<div class="blockShuttle" style="height:98%;" summary="<ikep4j:message pre="${preProcess}" key="subTitle1" />" >
				
			<div class="shuttle_l" style="width:39%;">
			<!--<div class="shuttle_l" style="width:98%; border=1;">-->
			<form id="apCodeTreeForm" name="apCodeTreeForm" method="post" action="<c:url value='/wfapproval/admin/apform/listApForm.do' />">
				<div class="sbox" style="height:490px;"><!--셔틀높이 좌우를 style로 설정할 것-->
					<div class="shuttleTitle">
						<ikep4j:message pre="${preProcess}" key="subTitle1" />
					</div>
					<div id="processList" class="shuttleTree"></div>
				</div>
			</form>
			</div>
			<div class="shuttle_m" style="display:none;width:0%;">
			<!--
				<h2 class="none">수신 셔틀</h2>
				<ul style="margin-top:35px;">
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_add.gif"/>" alt="Add" /></a></li>
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_del.gif"/>" alt="Del" /></a></li>
				</ul>
				<h2 class="none">참조 셔틀</h2>
				<ul style="margin-top:78px;">
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_add.gif"/>" alt="Add" /></a></li>
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_del.gif"/>" alt="Del" /></a></li>
				</ul>
				<h2 class="none">비밀참조 셔틀</h2>
				<ul style="margin-top:78px;">
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_add.gif"/>" alt="Add" /></a></li>
					<li><a href="#"><img src="<c:url value="/base/images/common/btn_shuttle_del.gif"/>" alt="Del" /></a></li>
				</ul>
			-->
			</div>
			<div class="shuttle_r" style="width:60%;">
			<!--<div class="shuttle_r" style="display:none;width:0%;">-->
				<div class="sbox" style="height:490px;">
					<div class="shuttleTitle">
						<ikep4j:message pre="${preProcess}" key="subTitle2" />  <span id="selectProcess"></span>
					</div>
					<div id="processDetail" class="shuttleCon" style="height:90%;">
						결재선 목록 선택시 상세정보
					</div>
				</div>
			</div>		
			<div class="clear"></div>
			
			<!--blockButton Start-->
			<div id="searchApFormProcessButton" class="blockButton" style="text-align:center;"> 
				<ul>
					<li><a id="applyProcessButton"   	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
					<li><a id="cancelProcessButton" 	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</ul>
			</div>
			<!--//blockButton End-->
		
		</div>
		<!--//blockShuttle End-->
		
	<!--</div>
	//popup_contents End-->

	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--popup_footer End-->
	
<!--</div>
//popup End-->
