<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>	
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script>
	(function($) {
		//=========================================================================
        // * 프로세스 기본정보 탭을 호출한다.
        //=========================================================================
		getReturnProcessDefaultInfo = function(){
			getProcessDefaultInfo();
		}
		
		//=========================================================================
        // * XPDL 소스팝업을 띄운다.
        //=========================================================================
		getXPDLSource = function(){
			try {
				$("#dialog-xpdl_source").val(formatXml(xmlStr(xmlUtil.getDocument())));
    			$("#dialog-xpdl_source").dialog({title: "XPDL Source", modal: true, width: 873, height: 600, scroll:"yes"});
				$("#dialog-xpdl_source").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$("#dialog-xpdl_source").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
				$("#dialog-xpdl_source").css("width", "860px");
			}catch(e){
				alert("<ikep4j:message pre='${preModeler}' key='message.4'/>");
			}
		}
		
		//=========================================================================
        // * 디자인 미리보기.
        //=========================================================================
		getViewer = function(){
			var url = '<c:url value="prism.do?mode=model&processId="/>' + encodeURIComponent(gEntityProcessId) +
			"&version=" + encodeURIComponent(gEntityProcessVer) +
			"&processType=workflow&minimapView=false&saveView=false&refreshView=false" +
			"&partitionId=" + encodeURIComponent(gEntityPartitionId);
			
			var viewer = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre='${preModeler}' key='button.view'/>",
				url 		: url,
				modal 		: true,
				width 		: 750,
				height 		: 400,
				params 		: null,
				callback	: null,
				open : function(event,ui){
					$(this).dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
					$(this).dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			    },
			});
		}
	})(jQuery);
</script>

<!--content_leftbox ui-layout-west start-->
<div class="content_leftbox ui-layout-west">
	<!--lnb start-->	
	<div class="lnb">
		<h1 class="none"><ikep4j:message pre='${preModeler}' key='layout.message.1'/></h1>
		<h2>Process Tree</h2>			
		<div class="l_tree">
			<div class="partition">
				<a class="button createPartition" href="#a" id="createPartition">
					<span><img src='<c:url value="/base/images/workflow/ic_plus.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.add'/>"/><ikep4j:message pre='${preModeler}' key='button.add'/></span>
				</a>
				<a class="button deletePartition" href="#a" id="deletePartition">
					<span><img src='<c:url value="/base/images/workflow/ic_plus.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.delete'/>"/><ikep4j:message pre='${preModeler}' key='button.delete'/></span>
				</a>
				<a class="button treeRefresh" href="#a" id="treeRefresh">
					<span><img src='<c:url value="/base/images/workflow/ic_plus.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.lineup'/>"/><ikep4j:message pre='${preModeler}' key='button.lineup'/></span>
				</a>
			</div>
			<div id="tree" class="l_tree_wrap" style="overflow-y:auto;"></div>
		</div>
	</div>
	<!--//lnb end-->
</div>		
<!--//content_leftbox ui-layout-west end-->

<!--content_rightbox ui-layout-center start-->
<div class="content_rightbox ui-layout-center">
		<h1 class="none"><ikep4j:message pre='${preModeler}' key='layout.message.2'/></h1>

		<!--t_box start-->
		<div class="t_box center-north">
			
			<!--t_box_tab start-->
			<div class="t_box_tab">		
				<!--tab Start-->		
				<div id="divTab_work1" class="iKEP_tab_work1">
					<ul>
						<li>
							<a href="#bodyTabs-1">
								<span id="prismStatusProcessId" onclick="getReturnProcessDefaultInfo();" style="cursor:hand;">
									<ikep4j:message pre='${preModeler}' key='button.newprocess'/>
								</span>
							</a>
						</li>
						<li>
							<a href="#bodyTabs-2">
								<span onclick="getXPDLSource();" style="cursor:hand;">
									<ikep4j:message pre='${preModeler}' key='button.source'/>
								</span>
							</a>
						</li>
						<li>
							<a href="#bodyTabs-2">
								<span onclick="getViewer();" style="cursor:hand;">
									<ikep4j:message pre='${preModeler}' key='button.view'/>
								</span>
							</a>
						</li>
					</ul>
				</div>		
				<!--//tab End-->			
			</div>
			<!--//t_box_tab end-->
			
			<!--t_box_conbox_l start-->	
			<div class="t_box_conbox_l">
					
				<!--t_box_btn textRight start-->		
				<div class="t_box_btn textRight">
					<ul>
						<li>				
							<div class="btn add">
								<a class="button createProcess" href="#a" id="createProcess">
									<span>
										<img src='<c:url value="/base/images/workflow/ic_add.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.add'/>" />
										<ikep4j:message pre='${preModeler}' key='button.add'/>
									</span>
								</a>
							</div>		
						</li>
						<li>				
							<div class="btn add">
								<a class="button saveProcess" href="#a" id="saveProcess">
									<span>
										<img src='<c:url value="/base/images/workflow/ic_save.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.save'/>" />
										<ikep4j:message pre='${preModeler}' key='button.save'/>
									</span>
								</a>
							</div>		
						</li>
						<li>				
							<div class="btn add">
								<a class="button deleteProcess" href="#a" id="deleteProcess">
									<span>
										<img src='<c:url value="/base/images/workflow/ic_save.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.delete'/>" />
										<ikep4j:message pre='${preModeler}' key='button.delete'/>
									</span>
								</a>
							</div>		
						</li>
						<li>				
							<div class="btn add">
								<a class="button deployProcess" href="#a">
									<span>
										<img src='<c:url value="/base/images/workflow/ic_arrange.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.batch'/>" />
										<ikep4j:message pre='${preModeler}' key='button.batch'/>
									</span>
								</a>
							</div>		
						</li>
						<li>				
							<div class="btn add">
								<a class="button undeployProcess" href="#a">
									<span>
										<img src='<c:url value="/base/images/workflow/ic_arrange_cancel.gif"/>' alt="<ikep4j:message pre='${preModeler}' key='button.unbatch'/>" />
										<ikep4j:message pre='${preModeler}' key='button.unbatch'/>
									</span>
								</a>
							</div>		
						</li>
					</ul>
				</div>	
				<!--//t_box_btn textRight End-->
				
				<!--t_box_con start-->	
				<div class="t_box_con" style="display:none;">
					<span class="ty1">
						<strong><ikep4j:message pre='${preModeler}' key='form.state.state'/></strong>
					</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<img id="prismStatusProcessState" src='<c:url value="/base/images/workflow/img_red.gif"/>' width="12" height="13" alt="<ikep4j:message pre='${preModeler}' key='layout.message.4'/>" />
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span class="ty1">
						<strong><ikep4j:message pre='${preModeler}' key='form.state.ver'/></strong>
					</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span id="prismStatusProcessVer" class="ty1">&nbsp;</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span class="ty1">
						<strong><ikep4j:message pre='${preModeler}' key='form.state.savestate'/></strong>
					</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span id="prismStatusState" class="ty1">&nbsp;</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span class="ty1">
						<strong><ikep4j:message pre='${preModeler}' key='form.state.savedate'/></strong>
					</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span id="prismStatusSaveDate" class="ty1">&nbsp;</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span class="ty1">
						<strong><ikep4j:message pre='${preModeler}' key='form.state.isbatch'/></strong>
					</span>
					<img src='<c:url value="/base/images/workflow/line_tab_box.gif"/>' width="2" height="11" alt="<ikep4j:message pre='${preModeler}' key='layout.message.3'/>" />
					<span id="prismStatusIsDeploy" class="ty1"></span>
					<div class="t_box_conbox_r"></div>
				</div>	
				<!--//t_box_con End-->	
				
			</div>
			<!--//t_box_conbox_l end-->			
			
		</div>
		<!--//t_box end-->	
		
		<!--p_box_con start-->
		<div class="p_box_con center-center">
			<table style="height:100%;width:100%">
				<tr>
					<td style="text-align:center;">	
						<ikep4j:message pre='${preModeler}' key='message.process.none'/><br>
						<ikep4j:message pre='${preModeler}' key='message.process'/> <a href="#createProcess" class="bold createProcess"><ikep4j:message pre='${preModeler}' key='button.add'/></a> <ikep4j:message pre='${preModeler}' key='message.action'/>. 
					</td>
				</tr>
			</table>	
		</div>
		<!--//p_box_con end-->
		
		<!--p_box_info start-->
		<div class="p_box_info_con p_box_info center-south">
			<table style="height:100%;width:100%;">
				<tr>
					<td style="text-align:center;">
						<ikep4j:message pre='${preModeler}' key='message.process.none'/><br>
						<ikep4j:message pre='${preModeler}' key='message.process'/> <a href="#createProcess" class="bold createProcess"><ikep4j:message pre='${preModeler}' key='button.add'/></a> <ikep4j:message pre='${preModeler}' key='message.action'/>. 
					</td>
				</tr>
			</table>	
		</div>	
		<!--//p_box_info end-->		

</div>
<!--//content_rightbox ui-layout-center end-->