
(function($) {
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
		
	});
		
	// 일반 Function
	selectItem = function(item) {		
		unselect();	
		curItem = $.data(item, "data");			
		$(item).addClass("ui-selected");							
	};
		
	unselect = function() {
		var $items = $("#ulResult > li").removeClass("ui-selected");
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
		
		// 결재 타입  0: 결재 ,1:합의(필), 2:합의(선) , 3: 수신
	//	var apprType = $('input[name=apprType]').val();
		var apprType = 3;
		
		// 결재 유형  0: 내부결재 ,1:협조공문
	//	var defLineType = $('input[name=defLineType]:checked').val();

		// 부서 선택 부여는 (결재 유형:협조 공문 & 결재 타입:수신) 시에만		
		if(apprType==3 ) {
			selectType = 'ALL';
		} else {
			selectType = 'USER';
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
				alert(Message_None_Dept);
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
		var dupAppCheck =false;
		var $result = $("#ulResult");
					
		var selectApprType = $("input[name=apprType]").val();
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.	

		var checkDupReceive =	0;
			
		var result = $result.children().each(function() {			
			var data = $.data(this, "data");
			arrAddedItems.push(data.type == "group" ? data.code : data.id);
			
			if( selectApprType == 3 && data.apprType == 3 ) {
				
				if( addItems[0].type == "group" ) {	// 추가 항목이 그룹인경우 이전 추가항목의사용자체크
					var chkGrp = data.type == "group" ? "" : data.group;

					if( chkGrp == addItems[0].code ) {
						checkDupReceive++;
					}	
				} else {// 추가 항목이 그룹이 아닌경우 이전추가항목의 그룹 체크
					var chkGrp = data.type == "group" ? data.code : "";
				
					if( chkGrp == addItems[0].group ) {
						checkDupReceive++;
					}						
				}
			}
						
		});
	
		var cntAddedItem = 0;
	
		// 결재선 정보 초기화
		//initDefLineInfo();
		var dupCheck = 0;

		if(checkDupReceive==0) {		
			$.each(addItems, function(idx) {	
				tmpItems = this;
				if($apprLine!=null){
					$.each($apprLine,function(){
		
						if(this.type=='group' ){
							if(tmpItems.type=='group' && this.code==tmpItems.code) {		
								dupAppCheck=true;
								return;
							}
						}
					});	
				}
				if(dupAppCheck){
					alert(Message_Dup_Line);
					return false;
				}			
				/*
				if(checkUserId==this.id){
					alert('기안자를 수신처 지정에 추가할 수 없습니다.');
					return;
				}
				*/
				
				//if (arrAddedItems.length == 0 || $.inArray(this.type == "group" ? this.code : this.id, arrAddedItems) == -1) {
				if (arrAddedItems.length == 0 || $.inArray(this.type == "group" ? this.code : this.id, arrAddedItems) == -1) {
					var elItem;			
				
					//this.type				=	item.type;
					if(this.apprType==undefined)
						this.apprType		=	3;
				
					if(this.type=='group') {
						this.userName		=	"";
						if(this.teamName	== undefined)
							this.teamName	=	this.name;
						this.approverType	=	1;
						this.id			=	this.code;
						this.name			=	apprType[this.apprType]+" "+this.teamName;
						this.jobTitleName	="";
					} else {
						this.approverType		=	0;		//결재유형 사용자:0,부서:1
						this.name				=	apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName+" "+this.teamName;
					}
					this.apprOrder			=	this.apprOrder;			
					this.apprStatus			=	this.apprStatus;
					
					if(this.lineModifyAuth==undefined){
						this.lineModifyAuth	=	0;				
					} 
					if(this.docModifyAuth==undefined){
						this.docModifyAuth	=	0;				
					} 
					if(this.readModifyAuth==undefined){
						this.readModifyAuth	=	0;				
					} 							
		
					this.apprDate			=	this.apprDate;
					//this.name				=	apprType[this.apprType]+" "+this.userName +" "+this.jobTitleName;
					//this.teamName			=	this.teamName;
				
					if( this.type == "group" ){
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					} else if(this.type=='user' && this.group!='jobDuty') {
						elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);				
					} else {
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					}
	
					$.data(elItem[0], "data", this);
	
					// 추가된 Item selected
					elItem.trigger("click");	
	
					cntAddedItem++;
					
				}
			});
		}
		if(!dupAppCheck){
			if( checkDupReceive>0 && addItems[0].type == "group") {
				alert(Message_No_Add_Dept);
			} else if( checkDupReceive>0 && addItems[0].type == "user") {
				alert(Message_No_Add_User);
			} else if(cntAddedItem == 0 && $result.children().length > 0) {
				alert(Message_No_Add_Line);
			} else {
				$("#cntSelectedItem").text(arrAddedItems.length + cntAddedItem);				
			}
		}
	};

		
	dblClickOrg = function() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
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
		
})(jQuery);
