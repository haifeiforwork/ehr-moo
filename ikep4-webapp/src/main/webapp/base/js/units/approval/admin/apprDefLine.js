

(function($) {
	// 합의 : 부서포함여부
	var agreeFlag	=	true;

	$(document).ready(function() {	
		$("#treeDept").bind("dblclick", dblClickOrg);		
		$("#divSearchResult").bind("dblclick", dblClickOrg);		
		$("#btnItemAdd").click(applyItem);		
		$("#btnItemRemove").click(function() {
			var $items = $("#ulResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			} else {
				alert(Message_Delete_Line);
			}
		});
		
		$("#btnItemRemoveAll").click(function() {
			var $items = $("#ulResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			});
		});		
	
		// 결재유형 자동선택
		if('${apprDefLine.defLineId}' == '') {
			$("#defLineType input:radio").eq(0).attr('checked',true);
			$("#defLineWay input:radio").eq(0).attr('checked',true);
		} else {		
			$("#apprType input:radio").eq(0).attr('checked',true);
		}

		// 결재방법 변경 Select Box 변경시
		$("#apprTypeChange").change(function() {
			if(curItem != undefined ) {
				curItem.apprType=$("#apprTypeChange").val();				
				$("#ulResult .ui-selected").text(apprType[curItem.apprType] + " " + curItem.orgName + " " + curItem.jobTitleName + " " + curItem.teamName);
				attrToggle(curItem.apprType+'');
			}		
		}).trigger('change');
		
		// 결재자 직책 Select Box 변경시
		$("#approverJobTitle").change(function() {
			if(curItem != undefined ) {				
				//curItem.approverJobTitle=$("#approverJobTitle").val();
				if(	curItem.approverJobTitle.indexOf('T')>0)			
					curItem.jobType=0;//호칭	
				else 
					curItem.jobType=1;//직책		
				curItem.jobTitleName=$("#approverJobTitle option:selected").text();
				curItem.approverJobTitle=$("#approverJobTitle option:selected").val();	
				$("#ulResult .ui-selected").text(apprType[curItem.apprType] + " " + curItem.orgName + " " + curItem.jobTitleName + " " + curItem.teamName);
			}		
		}).trigger('change');
		
		// 결재선 수정권한
		$("input[name=lineModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.lineModifyAuth=1;			
				} else {
					curItem.lineModifyAuth=0;
				}
			}
		});
		
		// 문서 수정권한
		$("input[name=docModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.docModifyAuth=1;			
				} else {
					curItem.docModifyAuth=0;
				}
			}
		});
								
		// 참조/열람권자 추가권한
		$("input[name=readModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.readModifyAuth=1;			
				} else {
					curItem.readModifyAuth=0;
				}
			}
		});
		
		// 결재 유형에 따른 결재타입 활성/비활성
		$("#defLineType input:radio").change(function() {
			// 내부결재
			if(this.checked && $(this).val()==0){				
				$("#apprType input:radio").eq(0).attr('checked',true);
				$("#apprType span").eq(3).addClass('none');
				
				// 협조공문의 수신 지정 Data 삭제 
				var $resultItems = $("#ulResult").children();				
				$.each($resultItems, function() {				
					var data = $.extend({}, $.data(this, "data"));
					if(data.apprType==3) {
						$(this).remove();
					}
				});			
			} else if(this.checked && $(this).val()==1) { // 협조공문
				$("#apprType span").eq(3).removeClass('none');
				
				var $resultItems = $("#ulResult").children();				
				// 협조공문의 수신 Select box option 삭제를 위해서 추가 
				$.each($resultItems, function() {
					var data = $.extend({}, $.data(this, "data"));
				});				
			}
			// 협조공문의 수신 Select box option 삭제
			changeApprType();			
		
		}).trigger('change');	
	
		//$("#ulResult").selectable();
		$( "#ulResult" ).sortable({
			placeholder: "ui-state-highlight",
			forcePlaceholderSize:true,
			revert:true
		});
		
		$("#ulResult > li").live({
		    "click" : function(event){
		    	selectItem(event.target);
		    }
		});		
		
		// 검색 Tab 검색버튼
		$("#btnSearch").click(function(){
			var keyword = $("#schKeyword").val();
			var selectType = $('input[name=selectType]:hidden').val();
			if(!keyword) {
				alert(Message_Search_Key);
				return false;
			}			

			$("#divSearchResult").jstree("destroy").empty();
			$("#divSearchResult").unbind("dblclick");
			
			$.post(iKEP.getContextRoot()+"/support/popup/requestSearchGroup.do", {keyword:keyword,selectType:selectType})
				.success(function(result) { 				
					if(result.items.length > 0) {
						$("#divSearchResult").jstree({
								plugins : [ "themes", "json_data", "ui" ],
								json_data : { data : iKEP.arrayToTreeData(result.items) },
								themes : {dots:false}
						});			
						$("#divSearchResult").bind("dblclick", applyItem);						
					}
				})
				.error(function(event, request, settings) { alert("error"); });
		});
		
		// 검색 Tab 검색어 클릭
		$("#schKeyword").bind("keypress", function(event) {
			if(event.which == 13) {
				$("#btnSearch").trigger("click");
			}
		});		
		
		// 미리보기
		$("#btnPreview").click(function(){ 
			$target =$("#previewDetailDiv");
			$target.toggle();
			//$target.empty();
			$preView =$("#divPreviewResult");
			$preView.empty();
			
			var $resultItems = $("#ulResult").children();
			var idx=1;
			var name="";
			var type="";
			var teamName="";
			$.each($resultItems, function() {
				
				var data = $.extend({}, $.data(this, "data"));					
				type = apprType[data.apprType];				
				if(data.type=="group" && data.approverType=='3') {
					teamName=Message_Leader;
					name = data.orgName;
				} else if(data.type=="group") {
					teamName=data.orgName;
					name = data.orgName;
				} else {
					teamName=data.teamName;
					name = data.orgName;
				}
				$preView.append("<tr><td width='25%'>"+name+"</td><td width='40%'>"+teamName +"</td><td>"+type +"</td></tr>");
				idx++;		
			});	
			$("#divPreviewResult").addClass("textCenter");
		});		
	});
		
	
	// 일반 Function
	selectItem = function(item) {		
		unselect();	
		curItem = $.data(item, "data");

		
		$(item).addClass("ui-selected");		
		
		// 협조 공문일 경우 수신 Select box 표시
		$("#apprTypeDiv").show();
		
		// 결재방법 변경 Select Box Option
		changeApprType();
		
		// 결재 방법 Select Box Selected
		attrToggle(curItem.apprType+'');
		
		// 기존 결재선변경 권한 세팅값 설정
		if(curItem.lineModifyAuth==1)	
			$("#lineModifyAuth").attr('checked',true);
		else
			$("#lineModifyAuth").attr('checked',false);
			
		// 기존 문서변경 권한 세팅값 설정
		if(curItem.docModifyAuth==1)	
			$("#docModifyAuth").attr('checked',true);
		else
			$("#docModifyAuth").attr('checked',false);		
		
		// 기존 참조자/열람권자 권한 세팅값 설정
		if(curItem.readModifyAuth==1)	
			$("#readModifyAuth").attr('checked',true);
		else
			$("#readModifyAuth").attr('checked',false);
								
		// 결재자 직책
		if(curItem!=undefined && curItem.type=='user') {
			changeUserInfo(curItem.approverId);
		} else {
			$("#approverJobTitle").empty();
		}			
	};
	
	changeApprType	=	function() {
		$("#apprTypeChange").empty();
		
		if( $('input[name=defLineType]:checked').val()==0 ) {
			
			if(curItem != undefined && curItem.type=='group' && curItem.approverType=='1' ) {
				
			} else {
				$("#apprTypeChange").append("<option value='0'>" + apprType[0] +"</option>");
			}
			$("#apprTypeChange").append("<option value='1'>" + apprType[1] +"</option>");
			$("#apprTypeChange").append("<option value='2'>" + apprType[2] +"</option>");
		} else {
			if(curItem != undefined && curItem.type=='group' && curItem.approverType=='1' ) {
				
			} else {
				$("#apprTypeChange").append("<option value='0'>" + apprType[0] +"</option>");
			}
			$("#apprTypeChange").append("<option value='1'>" + apprType[1] +"</option>");
			$("#apprTypeChange").append("<option value='2'>" + apprType[2] +"</option>");
			$("#apprTypeChange").append("<option value='3'>" + apprType[3] +"</option>");		
		}	
		if(curItem != undefined)
			$("#apprTypeChange > option[value='"+curItem.apprType+"']").attr("selected",true);		
	
	};
	unselect = function() {
		var $items = $("#ulResult > li").removeClass("ui-selected");
		//$("#ulResult > li > span").addClass("none");
	};
	
	applyItem = function() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		//핵심 tab명을 정확히 입력해야 함.
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item			
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree	
		var $treeItems = $tree.find("a.jstree-clicked").parent();
		
		if($treeItems.length <= 0) {
			alert(Message_Select_Item);
			return false;
		}
			
		var selectType = $('input[name=selectType]:hidden').val();
		var leaderType = $('input[name=leaderType]:hidden').val();
		// 결재 타입  0: 결재 ,1:합의(필), 2:합의(선) , 3: 수신
		var apprType = $('input[name=apprType]:checked').val();		
		// 결재 유형  0: 내부결재 ,1:협조공문
		var defLineType = $('input[name=defLineType]:checked').val();

		// 합의 부서선택 가능시
		if( agreeFlag ) {
			if( apprType!=0 || leaderType=='1' ) {
				selectType = 'ALL';
			} else {
				selectType = 'USER';
			}			
		} else {
			// 합의 부서 선택 불가시
			// 부서 선택 부여는 (결재 유형:협조 공문 & 결재 타입:수신) 시에만
			if(apprType==3 && defLineType==1) {
				selectType = 'ALL';
			} else {
				selectType = 'USER';
			}
		}

		if( selectType == 'USER' ){
			var items = [];
			$treeItems.each(function(idx) {
				var $li = $(this);
				if( $.parseJSON($li.attr("data")).type == 'user' ){
					items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
				}
			});
				
			if( $treeItems.length != items.length ){
				if(apprType==0 && leaderType=='0'){
					alert(Message_Dept_Line);
				} else {
					alert(Message_None_Dept);
				}				
			}
			if( items.length > 0){
				appendItem(items);
			}
		}else{
			var items = [];
			$treeItems.each(function(idx) {
				var $li = $(this);
				items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
			});
			appendItem(items);
		}	
	};
	
	appendItem = function(addItems) {	// 선택한 목록 추가

		var $result			= $("#ulResult");		
		var selectType		= $('input[name=apprType]:checked').val();	
		var defLineType		= $('input[name=defLineType]:checked').val();	
		var leaderType		= $('input[name=leaderType]:hidden').val();
		var arrAddedItems 	=	[];	// 이미 추가 되어 있는 목록을 뽑아온다.	

		var checkDupReceive	=	0;
 	
		var result = $result.children().each(function() {			
			var data = $.data(this, "data");
		
			//arrAddedItems.push(data.type == "group" ? data.code : data.id);
 			
			arrAddedItems.push(data.approverId);
			
			// 수신처 선택시 부서,팀원 중복 제거
			if( selectType == 3 && data.apprType == 3 ) {
				
				if( addItems[0].type == "group" && leaderType=='0') {	// 추가 항목이 그룹인경우 이전 추가항목의사용자체크
					//var chkGrp = data.type == "group" ? "" : data.group;
					var chkGrp = data.type == "group" ? "" : data.approverId;
					if( chkGrp == addItems[0].code ) {
						checkDupReceive++;
					}	
				} else {// 추가 항목이 그룹이 아닌경우 이전추가항목의 그룹 체크
					var chkGrp = data.type == "group" ? data.approverId : "";
					if( chkGrp == addItems[0].group ) {
						checkDupReceive++;
					}						
				}
			}			
		});
		
		var cntAddedItem = 0;
 
		// 결재선 정보 초기화
		initDefLineInfo();

		if(checkDupReceive==0) {
			$.each(addItems, function(idx) {	

				var data = setData(this);
								
				if (arrAddedItems.length == 0 || 
						$.inArray( data.approverId, arrAddedItems) == -1) 
						//$.inArray( this.type == "group" ? this.code : this.id, arrAddedItems) == -1)
				{
					var elItem;			
					
					if(this.type=='group') {
						elItem = $.tmpl("tmplResultUserItem", data).appendTo($result);				
					} else if(this.type=='user' && this.group!='jobDuty') {
						elItem = $.tmpl("tmplResultUserItem", data).appendTo($result);				
					} else {
						elItem = $.tmpl("tmplResultGroupItem", data).appendTo($result);
					}
	
					$.data(elItem[0], "data", data);
	
					// 추가된 Item selected
					elItem.trigger("click");	
	
					cntAddedItem++;
					
				}
			});
		}
		
		if( checkDupReceive>0 && addItems[0].type == "group") {
			alert(Message_No_Add_Dept);
		} else if( checkDupReceive>0 && addItems[0].type == "user") {
			alert(Message_No_Add_User);
		} else if(cntAddedItem == 0 && $result.children().length > 0) {
			alert(Message_No_Add_Line);
		} else {
			$("#cntSelectedItem").text(arrAddedItems.length + cntAddedItem);				
		}
	};
	
	// 부서/사용자/직책/리더 선택시 초기 세팅
	setData=function(apprLine){
		
		var items = [];
		// 결재 타입  0: 결재 ,1:합의(필), 2:합의(선) , 3: 수신
		var selectApprType	=	$('input[name=apprType]:checked').val();
		var leaderType		=	$('input[name=leaderType]:hidden').val();
		var approverWay		=	0;
		var approverType	=	0;
		var approverId		=	"";
	
		// 리더
		if(apprLine.type == "group" && leaderType=="1") {
			apprLine.orgName	=	apprLine.name;
			apprLine.name		=	apprType[selectApprType]+ " " + apprLine.name +" 리더";
			apprLine.teamName	=	"리더";
			approverWay			=	1;	// 간접
			approverType		=	3;	// 리더
			approverId			=	apprLine.code+":LD";	
			apprLine.jobTitleName=	"";
			apprLine.jobTitleCode=	"";		
		} else if(apprLine.type == "group") {	// 부서
			apprLine.orgName	=	apprLine.name;
			apprLine.name		=	apprType[selectApprType]+ " " + apprLine.name;
			apprLine.teamName	=	"";
			approverWay			=	0;
			approverType		=	1;
			approverId			=	apprLine.code; 
			apprLine.jobTitleName=	"";	
			apprLine.jobTitleCode=	"";	
		} else {
			if(apprLine.group!='jobDuty') {	// 사용자
				apprLine.orgName	=	apprLine.userName;
				apprLine.name		=	apprType[selectApprType]+ " " + apprLine.userName + " " + apprLine.jobTitleName+ " " + apprLine.teamName;
				apprLine.teamName	=	apprLine.teamName;
				approverWay			=	0;	// 간접
				approverType		=	0;	// role	
				approverId			=	apprLine.id; 	
			} else {						// Role 직책
				apprLine.orgName	=	apprLine.name;
				apprLine.name		=	apprType[selectApprType]+ " " + apprLine.name;	
				apprLine.teamName	=	"";
				approverWay			=	1;
				approverType		=	2;
				approverId			=	apprLine.id; 
				apprLine.jobTitleName=	"";	
				apprLine.jobTitleCode=	"";	
			}
		}

		items.push({
			type					:	apprLine.type,			
			apprType				:	selectApprType,
			approverId				:	approverId,
			group					:	apprLine.group,
			name					:	apprLine.name,
			orgName					:	apprLine.orgName,
			teamName				:	apprLine.teamName,
			jobType					:	0,
			approverJobTitle		:	apprLine.jobTitleCode,	
			approverJobTitleName	:	apprLine.jobTitleName,
			approverWay				:	approverWay,
			approverType			:	approverType,
			apprStatus				:	0,
			lineModifyAuth			:	0,
			docModifyAuth			:	0,	
			readModifyAuth			:	0
		});		

		return items[0];
	};	
	
	var initDefLineInfo=function(){
		// 결재 타입  0: 결재 ,1:합의(필), 2:합의(선) , 3: 수신
		var apprType = $('input[name=apprType]:checked').val();

		// 권한설정 : 결재자만 , 합의방법 선택 부여는 (합의) 시에만
		if(apprType!=0 ) {
			$("#setAuth").hide();
			$("#setAuth").next('div').addClass('none');
		} else {
			$("#setAuth").show();
			$("#setAuth").next('div').removeClass('none');
			
			$("#lineModifyAuth").attr('checked',true);
			$("#docModifyAuth").attr('checked',true);
			$("#readModifyAuth").attr('checked',true);
		}

		if(this.type=="group"){
			$("#jobTitle").hide();
		} else {
			$("#jobTitle").show();
		}	
	};

	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때	
	dblClickOrg = function() {	
		//핵심 tabname정확히
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

		var $treeItems = $tree.find("a.jstree-clicked").parent();
		var $subSelect = $treeItems.children("ul").children("li");
		
		var selectType = $('input[name=selectType]:hidden').val();		
		
		if( selectType == 'GROUP' ){
			if($subSelect.length > 0){
				$tree.find("a.jstree-clicked").removeClass();
				$subSelect.each(function(idx){
					$subSelect.find("a").addClass('jstree-clicked');
				});
			}else{
				applyItem();
			}
		}else if( selectType == 'USER' ){
			if( $.parseJSON($treeItems.attr("data")).type == 'group' ){
				if($subSelect.length <= 0){
					if( $.parseJSON($treeItems.attr("data")).hasChild > 0 ){
						//$tree("open_node", $treeItems);
						//$treeItems.children("ins").trigger("click");
					}else{
						alert(Message_No_Dept_Usr);
					}
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						if( $.parseJSON($(this).attr("data")).type == 'user' ){
							$(this).find("a").addClass('jstree-clicked');
						}
					});
				}
			}else{
				applyItem();
			}
		}else{

			if( $.parseJSON($treeItems.attr("data")).type == 'group' ){
				if($subSelect.length <= 0){
					if( $.parseJSON($treeItems.attr("data")).hasChild > 0 ){
						//alert("open subdirectory");	
					}else{
						//applyItem();
						alert(Message_Btn_Add);
					}					
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						//$(this).find("a").addClass('jstree-clicked');
						if( $.parseJSON($(this).attr("data")).type == 'user' ){
							$(this).find("a").addClass('jstree-clicked');
						}
					});
				}
			}else{
				applyItem();
			}
		}		
	};
	
	attrToggle=function(apprType) {
		switch(apprType) {
			case '0' : 
				$("#jobTitle").show();
				$("#setAuth").show();
				$("#setAuth").next('div').show();
				break;
			case '1' :
				if( agreeFlag ) {
					if(curItem.type=="group")
						$("#jobTitle").hide();
					else
						$("#jobTitle").show();				
				} else {
					$("#jobTitle").hide();
				}
				$("#setAuth").hide();
				$("#setAuth").next('div').hide();
				
				curItem.lineModifyAuth=0;
				curItem.docModifyAuth=0;
				curItem.readModifyAuth=0;				
				break;				
			case '2' :
				if( agreeFlag ) {
					if(curItem.type=="group")
						$("#jobTitle").hide();
					else
						$("#jobTitle").show();				
				} else {
					$("#jobTitle").hide();
				}
				$("#setAuth").hide();
				$("#setAuth").next('div').hide();	
				
				curItem.lineModifyAuth=0;
				curItem.docModifyAuth=0;
				curItem.readModifyAuth=0;							
				break;
			case '3' :
				$("#setAuth").hide();
				$("#setAuth").next('div').hide();
				curItem.lineModifyAuth=0;
				curItem.docModifyAuth=0;
				curItem.readModifyAuth=0;					
				if(curItem.type=="group")
					$("#jobTitle").hide();
				else
					$("#jobTitle").show();
				break;
		}	
	
	};
	
})(jQuery);		


