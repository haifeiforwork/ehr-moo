
(function($) {
	
	// 합의 : 부서포함여부
	var agreeFlag	=	true;	

	// 결재선 정보
	var	items			=	[];
	
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
			
			$.post(iKEP.getContextRoot()+"/support/popup/requestSearchGroup.do", { keyword : keyword, selectType : selectType })
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
		
		// 결재방법 변경 Select Box 변경시
		$("#apprTypeChange").change(function() {
			if(curItem != undefined ) {			
				curItem.apprType=$("#apprTypeChange").val();
				$("#ulResult .ui-selected").text(apprType[curItem.apprType] + " "+curItem.userName + " " + curItem.jobTitleName + " "+ curItem.teamName);
				attrToggle(curItem.apprType+'');
			}		
		}).trigger('change');
		
		// 결재자 직책 Select Box 변경시
		$("#approverJobTitle").change(function() {
			if(curItem != undefined ) {		
				curItem.jobTitleName=$("#approverJobTitle option:selected").text();
				curItem.approverJobTitle=$("#approverJobTitle option:selected").val();
				$("#ulResult .ui-selected").text(apprType[curItem.apprType] + " "+curItem.userName + " " + curItem.jobTitleName + " "+ curItem.teamName);				
			}		
		}).trigger('change');
		
		// 결재선권한
		$("input[name=lineModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.lineModifyAuth=1;			
				} else {
					curItem.lineModifyAuth=0;
				}
			}
		});
		
		// 결재선권한
		$("input[name=docModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.docModifyAuth=1;			
				} else {
					curItem.docModifyAuth=0;
				}
			}
		});
								
		// 결재선권한
		$("input[name=readModifyAuth]").click(function() {
			if(curItem!=null && curItem!=undefined) {
				if(this.checked) {
					curItem.readModifyAuth=1;			
				} else {
					curItem.readModifyAuth=0;
				}
			}
		});
		
		// 결재 방법 자동 선택->결재
		$("#apprType input:radio").eq(0).attr('checked',true);
	
	
		//$("#ulResult").selectable();
		$( "#ulResult" ).sortable({
			placeholder: "ui-state-highlight",
			forcePlaceholderSize:true,
			revert:true,
			items : "li.move"
		});
		
		$("#ulResult > li").live({
		    "click" : function(event){

				var itemData = $.data(event.target, "data");

				if(itemData.apprStatus==0){
					$("#lineModifyAuth").attr('disabled',false);
					$("#docModifyAuth").attr('disabled',false);
					$("#readModifyAuth").attr('disabled',false);					
		    		selectItem(event.target);				
				}
				/**
				if( checkUserId==itemData.id || itemData.apprStatus>1) {

				} else {
					$("#lineModifyAuth").attr('disabled',false);
					$("#docModifyAuth").attr('disabled',false);
					$("#readModifyAuth").attr('disabled',false);					
		    		selectItem(event.target);
		    	}
		    	**/
		    }
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
		//$("#apprTypeChange > option[value='"+curItem.apprType+"']").attr("selected",true);
		
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

		if(!docModifyAuth) {
			$("#docModifyAuth").attr('disabled',true);
		}
									
		// 결재자 직책
		if(curItem!=undefined && curItem.type=='user') {
			changeUserInfo(curItem.id);
		}  else {
			$("#approverJobTitle").empty();
		} 
	};
	
	changeApprType	=	function() {
		$("#apprTypeChange").empty();
		if(curItem != undefined && curItem.type!='group') {
			$("#apprTypeChange").append("<option value='0'>" + apprType[0] +"</option>");
		}		
		//$("#apprTypeChange").append("<option value='0'>" + apprType[0] + "</option>");
		$("#apprTypeChange").append("<option value='1'>" + apprType[1] + "</option>");
		$("#apprTypeChange").append("<option value='2'>" + apprType[2] + "</option>");

		if(curItem != undefined)
			$("#apprTypeChange > option[value='"+curItem.apprType+"']").attr("selected",true);		
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
		var apprType = $('input[name=apprType]:checked').val();
		
		// 결재 유형  0: 내부결재 ,1:협조공문
		var defLineType = $('input[name=defLineType]:checked').val();


		// 합의 부서선택 가능시
		if( agreeFlag ) {
			if( apprType!=0 ) {
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
				if(apprType==0){
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

		var dupRcvCheck =false;
		var $result = $("#ulResult");					
		var selectApprType = $('input[name=apprType]:checked').val();		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.	
		
		var result = $result.children().each(function() {
			var data = $.data(this, "data");			
			arrAddedItems.push(data.type == "group" ? data.code : data.id);			
		});

		var cntAddedItem = 0;

	
		// 결재선 정보 초기화
		initDefLineInfo();
		var dupCheck = 0;
		
		$.each(addItems, function(idx) {	
			
			tmpItems = this;
			if($recvApprLine!=null){
				$.each($recvApprLine,function(){
	
					if(this.type=='group' ){
						if(tmpItems.type=='group' && this.code==tmpItems.code) {		
							dupRcvCheck=true;
							return;
						}
					}
				});			
			}
			if(dupRcvCheck){
				alert(Message_Dup_Rcv);
				return false;
			}
			
			/**
			if(checkUserId==this.id ) {
				if( $('input[name=apprId]').val()=='' ) {
					alert('기안자를 결재선 지정에 추가할 수 없습니다.');
					return;
				} else if( $('input[name=apprDocStatus]').val()=='6' && $('input[name=apprId]').val()!='' && $('input[name=parentApprId]').val()!='') {
					alert('접수자를 결재선 지정에 추가할 수 없습니다.');
					return;
				}				
			}
			**/
 
			if (arrAddedItems.length == 0 || $.inArray(this.type == "group" ? this.code : this.id, arrAddedItems) == -1) {
				var elItem;			
					
				if(this.type=="group") {
					// 조직도에서 추가
					if(this.apprType==undefined) {
						this.apprType			=	selectApprType;
					
						this.id			=	this.code;
						this.teamName	=	this.name;				
						this.name		=	apprType[this.apprType] + " " + this.name;
						this.userName	=	"";
						this.jobTitleName	=	"";
					} else { // 기존 li Box 
						this.name		=	apprType[this.apprType] + " " + this.teamName;
						this.userName	=	"";
						this.jobTitleName	=	"";						
					}
				} else {	
					if(this.apprType==undefined) {
						this.apprType			=	selectApprType;
					}
					this.name	=	apprType[this.apprType] + " " + this.userName + " " + this.jobTitleName; //+" "+this.teamName;
				}			
						
				this.approverType		=	this.type == "group" ? 1 : 0;//결재유형 사용자:0,부서:1
				this.apprOrder			=	this.apprOrder;			
				this.apprStatus			=	this.apprStatus==undefined?0:this.apprStatus;
				
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
			
				if( this.type == "group" ){
					elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
				} else if(this.type=='user' && this.group!='jobDuty') {
					elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);				
				} else {
					elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
				}

				// 기처리된 결재선은 이동불가 및 현재 결재자 정보도 이동불가
				//if( ( checkUserId==this.id && this.apprStatus>0 ) || ( this.apprStatus>1 && this.apprDate!="" && this.apprDate!=undefined) ) {
				if( this.apprStatus>0 ) {
					elItem.addClass("nomove");
					$("#lineModifyAuth").attr('disabled',true);
					$("#docModifyAuth").attr('disabled',true);
					$("#readModifyAuth").attr('disabled',true);			
					
					// 기존 결재선변경 권한 세팅값 설정
					if(this.lineModifyAuth==1)	{
						$("#lineModifyAuth").attr('checked',true);
					} else
						$("#lineModifyAuth").attr('checked',false);
			
					// 기존 문서변경 권한 세팅값 설정
					if(this.docModifyAuth==1)	
						$("#docModifyAuth").attr('checked',true);
					else
						$("#docModifyAuth").attr('checked',false);
		
					// 기존 참조자/열람권자 권한 세팅값 설정
					if(this.readModifyAuth==1)	
						$("#readModifyAuth").attr('checked',true);
					else
						$("#readModifyAuth").attr('checked',false);

					if(!docModifyAuth) {
						$("#docModifyAuth").attr('disabled',true);
					}	
											
				} else {
					elItem.addClass("move");
				}

				$.data(elItem[0], "data", this);
				
				// 추가된 Item selected
				elItem.trigger("click");	

				cntAddedItem++;
				
			}
		});
		if(!dupRcvCheck){
			if(cntAddedItem == 0 && $result.children().length > 0) {
				alert(Message_No_Add_Line);
			} else {
				$("#cntSelectedItem").text(arrAddedItems.length + cntAddedItem);				
			}
		}
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
			
			//$("#lineModifyAuth").attr('checked',false);
			//if(docModifyAuth==true) {
			//	$("#docModifyAuth").attr('disabled',true);
			//}			
			//$("#docModifyAuth").attr('checked',false);
			//$("#readModifyAuth").attr('checked',false);
		}

		if(this.type=="group"){
			$("#jobTitle").hide();
		} else {
			$("#jobTitle").show();
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
	
	// dup ApprLine Check
	// 배열요소 중복제거		
	Array.prototype.unique=function() {
		var newArray=[], len=this.length;
		label:for(var i=0; i<len; i++) {
			for(var j=0; j<newArray.length; j++)
				if(newArray[j]==this[i]) 
					continue label;
			newArray[newArray.length] = this[i];
		}
		return newArray;
	};	
})(jQuery);		


