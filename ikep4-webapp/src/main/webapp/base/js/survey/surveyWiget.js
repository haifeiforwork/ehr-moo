

(function($) {
	var questionGroupAllBlock;
	var oldQuestionGroupAllBlock;
	var questionBlock;
	var titleBlock;
	var targetFileImg;
	var oldQuestionId;
	var oldQuestionBlock;
	var curQuestionId;
	var questionBlockPop;
	var selectQuestionGroupId;
	var saveQuestionId;
	var addQuestionGroupId;
	var oldQuestionTitle;
	var oldAnswerTitle = [];
	var imgFileId = [];
	var imageIndex=0;

	var mode;
	var editLink ='&nbsp;<a href="#a" class="edit_btn none"><img align="top" src="'+contextRoot+'/base/images/icon/ic_edit.gif" alt="Edit" /></a>'+
				'<a href="#a" class="save_btn none"><img src="'+contextRoot+'/base/images/icon/ic_save.gif" alt="Save" /></a>'+
				'&nbsp;<a href="#a" class="cancel_btn none"><img src="'+contextRoot+'/base/images/icon/ic_cancel.gif" alt="Cancel" /></a>';
	

	$(document).ready(function() {
	
		$(document).ajaxStop($.unblockUI);		

		$("#accordion").accordion({ 
			autoHeight: false,
			navigation: true
		});

		//업로드완료후 fileId 리턴
		afterFileUpload = function(status, fileId, fileName, message, gubun) {
			$(questionBlock).find("#image"+imageIndex).attr('src',contextRoot +'/support/fileupload/downloadFile.do?fileId='+fileId);
			//$jq("#image"+imageIndex).attr('src',contextRoot +'/support/fileupload/downloadFile.do?fileId='+fileId);
			$(questionBlock).find("#image"+imageIndex).attr('width','50');
			$(questionBlock).find("#image"+imageIndex).attr('height','50');			
				
			$(questionBlock).find("#image"+imageIndex).data('fileId',fileId);	

			imgFileId[imageIndex] = fileId;			
		};
		
   		fileUpload=function(index) {
   			if(mode!="edit")
   				return;
			iKEP.fileUpload('image'+index,'0','1');	
			imageIndex=index;		
		};
		
		//accordion init
		var init = function(option){
			var stop = false;
			//설문그룹 재생성
			$("#questionAllBlock").accordion( "destroy" );
			
			$("#questionAllBlock h3").click(function(event) {
				if (stop) {
					event.stopImmediatePropagation();
					event.preventDefault();
					stop = false;
				}
			});

			var icons = {
						header: "ui-icon-circle-arrow-e",
						headerSelected: "ui-icon-circle-arrow-s"
					};
					
			 $("#questionAllBlock").accordion({ 
				   header: '>div>h3',
				   autoHeight: false,
				   //collapsible: true,
				   clearStyle: true,
				   event: 'click',
				   changestart: function(event, ui) {
				   		$("#questionAllBlock .questionGroupAllBlock").each(function(){
				   			$(this).find(".surveyBox_1>.questionGroupContents").addClass("none"); // jQuery object, activated header
							$(this).find(".surveyBox_1>.surveyBox_resize").addClass("none");
							$(this).find(".questionGroupBlock").hide();
							
							$(questionGroupAllBlock).find('.group_edit_btn').addClass("none");
				   		});
				   },
				   change: function(event, ui) { 

				   		questionGroupAllBlock = $(ui.newHeader).parent();
						$(ui.newHeader).find(".questionGroupContents").removeClass("none"); // jQuery object, activated header
						$(ui.newHeader).find(".surveyBox_resize").removeClass("none");
						
						$(questionGroupAllBlock).find(".questionGroupBlock").show();
						
						// Edit Icon show
						$(questionGroupAllBlock).find('.group_edit_btn').removeClass("none");						
						
						
						/*var target_offset = $(questionGroupAllBlock).offset();
						var target_top = target_offset.top-200;
						$('html, body').animate({scrollTop:target_top}, 500);
						*/
				   },
				   create: function(event, ui) { 
				   		
	 				    $("#questionAllBlock").accordion( "activate" , 0 ); 
				   		questionGroupAllBlock = $("#questionAllBlock .questionGroupAllBlock:first");
						$(questionGroupAllBlock).find(".surveyBox_1>.questionGroupContents").removeClass("none"); // jQuery object, activated header
						$(questionGroupAllBlock).find(".surveyBox_1>.surveyBox_resize").removeClass("none");
						
						$(questionGroupAllBlock).find('.group_edit_btn').removeClass("none");
				   }
			}).sortable({
							axis: "y",
							handle: "h3",
							stop: function(event, ui) {
								stop = true;
								
								$("#questionAllBlock .questionGroupAllBlock").each(function(index){
									var param=groupAjax(this,index+1,'update');
									if( param.oldQuestionGroupSeq != param.questionGroupSeq )
										$(this).questionSave(param);
								});
							}
						});
			
			if( option=='new')
			{
				var headerSize = $("#questionAllBlock>div>h3").size()-1;
				$("#questionAllBlock").accordion( "activate" , headerSize );
			}
		};
	   
	   
		var showBlock = function(){
			$.blockUI({ message: '<h1><img src="'+contextRoot+'/base/images/common/loading.gif" /> Saving...</h1>',
				overlayCSS:  { 
					backgroundColor: '#000', 
					opacity:         0
				} , 
				css: { 
					border: 'none', 
					padding: '15px', 
					'-webkit-border-radius': '10px', 
					'-moz-border-radius': '10px', 
					opacity: .5, 
					color: '#000' 
				}
			}); 
		};

		var fnStatusQuestionType = function(status){
			var nParent = questionBlock;
			var changeTab="S";
			
			switch (status) {
				case 'A0' : 
							changeTab='S';
							break;
				case 'A1' : 
							changeTab='S';
							break;	
				case 'A2':
							changeTab='S';
							break;
				case 'A3':
							changeTab='S';
							break;
				case 'A4' :
							changeTab='D';
							break;
				case 'A5' :
							changeTab='D';
							break;				
				case 'A6':
							changeTab='D';
							break;
				case 'A7':
							changeTab='D';
							break;
							
				default:
						    changeTab='S';
							break;
			}
			
			switch (status) {
				case 'A0':
				case 'A1':
				case 'A2':
				case 'A3':
				case 'A4':
				case 'A5':
				case 'A6':
				case 'A7':
					$("#answerRowCntLayer").show();
					$("#statusQuestionTypeLayer").show();
					$("#statusDisplayTypeLayer").show();
					$("#columnCountLayer").show();
					break;
				case 'B0':
					$("#answerRowCntLayer").hide();
					$("#statusQuestionTypeLayer").hide();
					$("#statusDisplayTypeLayer").hide();
					$("#columnCountLayer").hide();
					break;
				case 'B1':
				case 'B2':
					$("#answerRowCntLayer").show();
					$("#statusQuestionTypeLayer").hide();
					$("#statusDisplayTypeLayer").hide();
					$("#columnCountLayer").hide();
					break;
				case 'B3':
					$("#answerRowCntLayer").hide();
					$("#statusQuestionTypeLayer").hide();
					$("#statusDisplayTypeLayer").hide();
					$("#columnCountLayer").hide();
					break;
				case 'C0':
				case 'D0':
				case 'D1':
				case 'D2':
				case 'D3':
				case 'D4':
				case 'D5':
				case 'D6':
				case 'D7':$("#answerRowCntLayer").show();
						  $("#statusQuestionTypeLayer").hide();
						  $("#statusDisplayTypeLayer").hide();
						  $("#columnCountLayer").hide();
						  break;	
				default:
					break;
			}					
			
			
			
			$("input[name='statusQuestionType']").each(function(){
				if ($(this).val() == changeTab ) 
					$(this).attr("checked", true);
				else 
					$(this).removeAttr("checked");
			});
			
			
			$('#selectQuestionType >a').text( $("#P"+status).text() );
			
		};
		
		var fnStatusRequiredAnswer = function(status){

			$("input[name='statusRequiredAnswer']").each(function(){
				if ($(this).val() == status) 
					$(this).attr("checked", true);
				else 
					$(this).removeAttr("checked");
			});
		};
		
		var fnStatusDisplayType = function(status){
			
			if ( status == 2 ) 
				$('#columnCountLayer').show();
			else 
				$('#columnCountLayer').hide();
								
			$("input[name='statusDisplayType']").each(function(){
				if ($(this).val() == status) 
					$(this).attr("checked", true);
				else 
					$(this).removeAttr("checked");
			});
		};

		
		// 질문 Layer 클릭시 활성 및 이전 활성화 질문 Layer 비활성
		$("#questionAllBlock").delegate(".surveyBox_2","click", function(){				

			var surveyListBlock =  $(this).find(".surveyListBlock");
			curQuestionId		= $(surveyListBlock).data("questionId");

			// 수정모드에서 현재 Block 클릭시 
			if(mode=="edit" && oldQuestionId==curQuestionId) {
				return;
			} else if(mode=="edit" && oldQuestionId!=curQuestionId) { // 수정모드에서  다른 Block 클릭시
				
				var i=0;
				questionTitle=$(oldQuestionBlock).find("input[name='questionTitle']").val();
				if(questionTitle==undefined ||  questionTitle=="") {
					i++;
					//alert(nullQTitle);
					//return false;
				}
				if(i==0) {
					$(oldQuestionBlock).find("input[name='answerTitle']").each(function(index){
						if($(this).val()==undefined ||  $(this).val()=="") {
							i++;
							//alert(nullATitle);
							//return false;
						}
					});					
				}

				if(i>0) {					
					displayRowCancelSet();
					mode="";
				} else {
					var param = questionAjax(oldQuestionBlock,-1,'update');
					$(oldQuestionBlock).answerSave(param);
				}					
			}
			
			// 질문을 클릭시 
			$('#rightAllBlock').show();
			questionBlock = this;
			
			// 이전 선택 질문 Box 
			$(".surveyBox_2").each( function(){			
				$(this).removeClass("selected");
				$(this).children('div').addClass("none");
				$(this).children('table').find(".ic_arb").each( function(){
					$(this).addClass("none");
				} );
		
				$(this).find('.edit_btn').addClass("none");
				$(this).find('.save_btn').addClass("none");
				$(this).find('.cancel_btn').addClass("none");				
				
				if($(this).find("input[name='questionTitle']").val() !="undefined" &&  $(this).find("input[name='questionTitle']").val()!="")
				{
					$(this).find(".questionTitle").html($(this).find("input[name='questionTitle']").val());
					$(this).find("input[name='answerTitle']").each(function() {
						var value = $(this).val();
						$(this).parent().html(value);
					});				
				}				
			});	
		
			// 선택된 질문 
			$(this).find('.edit_btn').removeClass("none")
			$(this).addClass("selected");
			$(this).children('div').removeClass("none");
			$(this).children('table').find(".ic_arb").each( function(){
				$(this).removeClass("none");
			} );

			
			var surveyListBlock	= $(this).find(".surveyListBlock");
			var questionType	= $(surveyListBlock).data("questionType");
			var requiredAnswer	= $(surveyListBlock).data('requiredAnswer');
			var columnCount		= $(surveyListBlock).data('columnCount');
			var displayType		= $(surveyListBlock).data('displayType');
			var answerRowCnt	= $(surveyListBlock).data('answerRowCnt');
			var questionId		= $(surveyListBlock).data("questionId");
			
		
			//right leyear settting			
			$("#answerRowCnt").val(answerRowCnt);
			$("#statusColumnCount").val(columnCount);
			fnStatusQuestionType(questionType);
			fnStatusRequiredAnswer(requiredAnswer);
			fnStatusDisplayType(displayType);
			
			// 타입선택레이어 팝업								
			if( !$('#surveySelLayerTop').hasClass("none") ){
				$('#selectQuestionType').click();				
			}			

			if(oldQuestionId!=questionId) {	
				oldQuestionId=questionId; //$(surveyListBlock).data("questionId");				
				oldQuestionBlock=questionBlock;
			}			
		});


		// 질문 수정 버튼 클릭시
		$("#questionAllBlock").delegate(".edit_btn","click", function(){
			
			$(questionBlock).find('.edit_btn').addClass("none");
			$(questionBlock).find('.save_btn').removeClass("none");
			$(questionBlock).find('.cancel_btn').removeClass("none");
			
			var surveyListBlock = $(questionBlock).find(".surveyListBlock");
			var questionType	= $(surveyListBlock).data("questionType");
			var requiredAnswer	= $(surveyListBlock).data('requiredAnswer');
			var columnCount		= $(surveyListBlock).data('columnCount');
			var displayType		= $(surveyListBlock).data('displayType');
			var answerRowCnt	= $(surveyListBlock).data('answerRowCnt');
			var questionId		= $(surveyListBlock).data("questionId");	

			var questionTitle	= $(surveyListBlock).find(".questionTitle").html();
			oldQuestionTitle	= questionTitle;
			
			if(compQTitle==questionTitle)
				questionTitle="";
			
			if(questionType	=="D0" || questionType=="D1" || questionType=="D2" || questionType=="D3" || questionType=="D4" || questionType=="D5" || questionType=="D6"  ) {	
				$(surveyListBlock).find(".questionTitle").html('<input type="text" name="questionTitle" value="'+questionTitle+'" class="input w70">');							
			} else {
				$(surveyListBlock).find(".questionTitle").html('<input type="text" name="questionTitle" value="'+questionTitle+'" class="input w80">');	
			}
			
			var answerTitle = $jq.makeArray( $(surveyListBlock).find(".answerTitle") );

			var fileId = [];
			
			$jq.each( $jq.makeArray( $(surveyListBlock).find(".fileId")) , function(index,obj){
				fileId[index]=$(obj).text();

			});

			$jq.each(answerTitle, function(index,obj){
				oldAnswerTitle[index]=$(obj).text();	// 문항 수정시 cancel 버튼 클릭시 이전 메시지 원위치 시키기
				
				var ansTitle=$(obj).text();
				
				if( compATitle == ansTitle )
					ansTitle="";
				
				// 설문 문항 타입이 이미지 일경우 첨부 파일 추가하기
				if(questionType	=="A1" || questionType=="A5") {
					$(obj).html('<input type="text" name="answerTitle" value="'+ansTitle+'" class="input w50" />');
					if(fileId[index]== undefined || fileId[index]=="")
						$(obj).append(' <a href="#a" onclick="fileUpload('+index+')"><img src="'+contextRoot+'/base/images/icon/ic_img_add.png" id="image'+index+'"  alt="image"  name="xfileuploadBtn" /></a> ');
				} else if(questionType	=="B0" || questionType=="B2"|| questionType=="B3") {

				} else {
					$(obj).html('<input type="text" name="answerTitle" value="'+ansTitle+'" class="input" size="80" />');
				}				
			} );
			
			// 수정시 설문 문항 타입 숨기기
			$jq('#rightAllBlock').hide();	
			
			// 수정시에 문항 표시 방법의 수직/수평/컬럼의 형태를 수직으로 변경하여 처리함
			if(displayType!="0")
				displayRowView2();
			
			// 수정모드 : 현재 Layer 클릭 또는 다른 Layer 클릭시 Save 또는 유지 시키기 
			mode ="edit";
		});
		
		
		// 해당 질문 Cancel 버튼 클릭시 --> $("#questionAllBlock").delegate(".surveyBox_2","click", function() 이동함
		$("#questionAllBlock").delegate(".cancel_btn","click", function(){
			displayRowCancelSet();
			var surveyListBlock = $(questionBlock).find(".surveyListBlock");
			$(surveyListBlock).find(".attFile").each( function(){
				$(this).removeClass("none");
			});			
			imgFileId=[];
			mode="";
		});
		
		// Save 버튼 클릭시 해당 문항 저장
		$("#questionAllBlock").delegate(".save_btn","click", function(){
		
			// 입력 박스 NULL 체크		
			questionTitle= $jq('input[name="questionTitle"]').val();
			questionTitle = $jq.trim(questionTitle);

			// input Box 데이터 이면
			if( questionTitle != null && questionTitle !="" ) {


				/**
				scale1Title=$jq('input[name="scale1Title"]').val();
				scale2Title=$jq('input[name="scale2Title"]').val();
				scale3Title=$jq('input[name="scale3Title"]').val();

				scale1Title=$(surveyListBlock).find(".scale1Title").html();
				scale2Title=$(surveyListBlock).find(".scale2Title").html();
				scale3Title=$(surveyListBlock).find(".scale3Title").html();	
				**/					
				
				var i=0;	
		
				$jq('input[name="answerTitle"]').each(function(index) {					
					if($.trim($(this).val())=="")
						i++;
				});		

				if(i>0)
				{
					alert(nullATitle);
					return;				
				}
		
			} else {
				alert(nullQTitle);
				return;
			}		
				
			var param = questionAjax(questionBlock,-1,'update');
			$(questionBlock).answerSave(param);
		});		
			
		//그룹생성	
		$("#addQuestionGroupButton").click(function(){
			oldQuestionGroupAllBlock = questionGroupAllBlock;
			var tempQuestionBlock = $("#questionGroupTemplate .questionGroupAllBlock").clone(true,true);
			$(tempQuestionBlock).data("questionGroupId","");
			$(tempQuestionBlock).data("questionGroupSeq","");
			
			var param = groupAjax(tempQuestionBlock,-1,'insert');
			$(tempQuestionBlock).questionSave(param);
			$(tempQuestionBlock).insertBefore($(this).parent());	
			
			questionGroupTitle = $(tempQuestionBlock).find(".questionGroupTitle").html();

			var editGroupLink ="<span style='display:inline;' class='group_edit_btn none'><a href='#a' onclick=\"editGroupTitle('"+param.surveyId+"','"+addQuestionGroupId+"')\" style='padding-left:10px;padding-top:3px;display:inline; background:none;'><img src='"+contextRoot+"/base/images/icon/ic_edit.gif' alt='' align='absmiddle' /></a></span>";
			$(tempQuestionBlock).find('.questionGroupTitle').after(editGroupLink);
			
			$(questionGroupAllBlock).find('.group_edit_btn').addClass("none");
			
			init('new');
			
			/*****
				$("#questionAllBlock .questionGroupAllBlock").each(function(){
				alert($(this).find(".surveyBox_1>.questionGroupContents").text())
				//$(this).find(".surveyBox_1>.questionGroupContents").addClass("none"); // jQuery object, activated header
				//$(this).find(".surveyBox_1>.surveyBox_resize").addClass("none");
				//$(this).find(".questionGroupBlock").hide();
							
				$(this).find('.group_edit_btn').addClass("none");
				alert($(this).find('.group_edit_btn').text());
			});
			$(oldQuestionGroupAllBlock).find('.group_edit_btn').addClass("none");	
			******/   					
		});
		
		var groupAjax = function(currentBlock,seq,reqestType){
			var questionGroupId = $(currentBlock).data("questionGroupId");
			var oldQuestionGroupSeq = $(currentBlock).data("questionGroupSeq");
			var questionGroupSeq = seq==-1?$(currentBlock).data("questionGroupSeq"):seq;
			var questionGroupTitle = $(currentBlock).find(".questionGroupTitle").html();
			var questionGroupContents = $(currentBlock).find(".questionGroupContents").html();
			var surveyId = $("#questionAllBlock").data("surveyId");
			
			var url = "";
			
			//그룹아이디가 없을경우에해당
			if( questionGroupId == null || questionGroupId == '' || questionGroupId=='undefined')
				url = contextRoot+'/servicepack/survey/question/createQuestionGroup.do';
			else if( reqestType == 'remove' )
			    url = contextRoot+'/servicepack/survey/question/removeQuestionGroup.do';	
			else
				url = contextRoot+'/servicepack/survey/question/updateQuestionGroup.do';	
				
			var param={"surveyId":surveyId,"questionGroupId":questionGroupId,"title":questionGroupTitle,"contents":questionGroupContents,"questionGroupSeq":questionGroupSeq,"oldQuestionGroupSeq":oldQuestionGroupSeq,"url":url};	
		
			return param;
		};
		$.fn.questionSave = function(settings) {
			var currentBlock=this;
	     	var config = {"surveyId":'',"url":''};
	     	if (settings) $.extend(config, settings); 
			
			 $jq.ajax({
			 	type: 'post',
			 	async:false,
			 	cache: false, 
			 	beforeSend: function(){
			 		showBlock();
   				},
			 	contentType: 'application/json',
			 	url: config.url,
			 	data: JSON.stringify(config)
			 })
			 .success(function(data) { 
				//alert(data.questionGroupId);
				$(currentBlock).data("questionGroupId",data.questionGroupId);
				$(currentBlock).data("questionGroupSeq",data.questionGroupSeq);
				addQuestionGroupId = data.questionGroupId
		    })    
			.error(function(event, request, settings) { 
					alert("error"); 
			});  
			
			//location.href=contextRoot+"/servicepack/survey/question/createQuestion.do?surveyId="+settings.surveyId;
	     	return this;     	
	 
	   };	   
		
		var questionAjax = function(currentBlock,seq,reqestType){
			
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");

			//기본정보셋팅			
			var surveyId = $("#questionAllBlock").data("surveyId");			
			
			var questionGroupId	= $(questionGroupAllBlock).data("questionGroupId");
			var columnCount		= $(surveyListBlock).data('columnCount');
			var displayType		= $(surveyListBlock).data('displayType');
			var questionId		= $(surveyListBlock).data('questionId');
			var questionSeq		= $(surveyListBlock).data('questionSeq');
			var questionType	= $(surveyListBlock).data('questionType');
			var requiredAnswer	= $(surveyListBlock).data('requiredAnswer');		
			var answerRowCnt	= $(surveyListBlock).data('answerRowCnt');				
			var questionTitle	= "";
			var answerList		= [];
			var scale1Title		= $(surveyListBlock).find(".scale1Title").html();
			var scale2Title		= $(surveyListBlock).find(".scale2Title").html();
			var scale3Title		= $(surveyListBlock).find(".scale3Title").html();


			var answerId = [];
			
			$jq.each($jq.makeArray($(surveyListBlock).find(".answerId")), function(index,obj){
				answerId[index]=$(obj).text();
			});	

			questionTitle = $jq('input[name="questionTitle"]').val();
			questionTitle = $.trim(questionTitle);
			

			// 수정상태가 아닌경우			
			if( questionTitle == undefined ||  questionTitle == null || questionTitle ==""  ) {

				questionTitle=$(surveyListBlock).find(".questionTitle").html();				
				
				var img = "";
				answerBlock=$(surveyListBlock).find(".answerTitle").each(function(index){
								
					if(imgFileId[index]==undefined || imgFileId[index]==null || imgFileId[index]=="")
						img = "";
					else
						img = imgFileId[index];
					//$(this).data('answerId'),			
					var answer = {"answerId":answerId[index],
								  "answerSeq":$(this).data('answerSeq'),
								  "title":$(this).html(),
								  "img":img
					 };
					 answerList.push(answer);
				});			
			} else {  // 수정상태에서 저장한 경우
			
				if(questionType =="B0" || questionType=="B2" || questionType=="B3") {
				
					$jq.each($jq.makeArray($(surveyListBlock).find(".answerTitle")), function(index,obj){
						var seq = index+1;
						var answer = {"answerId":answerId[index],
									  "answerSeq":seq,
									  "title":$(this).html(),
									  "img":img
						 };

						 answerList.push(answer);
					});		
				} else {
							
					var img = "";
					answerBlock = $jq('input[name="answerTitle"]').each(function(index) {
						var seq = index+1;
						if(imgFileId[index]==undefined || imgFileId[index]==null || imgFileId[index]=="")
							img = "";
						else
							img = imgFileId[index];
							
						var answer = {"answerId":answerId[index],
									  "answerSeq":seq,
									  "title":$(this).val(),
									  "img":img
						 };
						 answerList.push(answer);				
					});			
				}
			} 
			
	
			var url = "";

			//그룹아이디가 없을경우에해당
			if( questionId == null || questionId == '' || questionId=='undefined')
				url = contextRoot+'/servicepack/survey/question/createQuestion.do';
			else
				if( reqestType == 'remove' )
					 url = contextRoot+'/servicepack/survey/question/removeQuestion.do';
				else 
					url = contextRoot+'/servicepack/survey/question/updateQuestion.do';	
				
			var param={"surveyId":surveyId,	"questionGroupId":questionGroupId,"title":questionTitle,"columnCount":columnCount,
						"displayType":displayType,"questionId":questionId,"questionSeq":questionSeq,"questionType":questionType,
						"requiredAnswer":requiredAnswer,"scale1":scale1Title,"scale2":scale2Title,"scale3":scale3Title,"url":url,"answer":answerList,"answerRowCnt":answerRowCnt
						};	
									
			return param;
		};
		
		// 문항 저장
		$.fn.answerSave = function(settings) {	   
			var currentBlock=this;				
			var surveyListBlock =  $(this).find(".surveyListBlock");
			
	     	var config = {"surveyId":'',"url":''};
	     	if (settings) $.extend(config, settings); 
			
			 $jq.ajax({
			 	type: 'post',
			 	async:false,
			 	cache: false, 
			 	beforeSend: function(){
			 		 showBlock();
   				},
			 	contentType: 'application/json',
			 	url: config.url,
			 	data: JSON.stringify(config)
			 })
			 .success(function(data) { 
				$(surveyListBlock).data("questionId",data.questionId);
				$(surveyListBlock).data("questionSeq",data.questionSeq);
				saveQuestionId = data.questionId;

				if(data.questionType=="A1" || data.questionType=="A5") {
					$.each(data.answer, function(index, obj){
						imgFileId[index]=obj.img;
					});
					
					$jq.each($jq.makeArray($(surveyListBlock).find(".fileId")), function(index,obj){
						$(obj).text(imgFileId[index]);
					});	
											
					// 수정시에 문항 표시 방법의 수직/수평/컬럼의 형태를 수직으로 변경한 부분을 원위치로 돌림
					$(surveyListBlock).find(".attFile").each( function(index){
						if(imgFileId[index]==undefined || imgFileId[index]=="") {
							$(this).addClass("none");
						} else {
							$(this).html('<a href="#a" onclick="fileUpload(\''+index+'\')"><img src="'+contextRoot+'/support/fileupload/downloadFile.do?fileId='+imgFileId[index]+'" id="image'+index+'"  width="50" height="50" alt="image" /></a>');
							$(this).removeClass("none");
						}
					});
							
				}							
		    })    
			.error(function(event, request, settings) { 
				alert("error"); 
			});  
			
			// 질문 수정 모드 해제
			mode="";	  		
					
			// 수정시에 문항 표시 방법의 수직/수평/컬럼의 형태를 수직으로 변경한 부분을 원위치로 돌림
			displayRowSaveSet();	
			
			// 파일 첨부 초기화	  		
	  		imgFileId=[];
	     	return this;
	 
	   };
	   		
		//문항추가
		$("a[name='addQuestionButton']").click(function(){
		
			if(mode=="edit") {
				displayRowCancelSet();
			}
			var parentTag = $(this).parent().parent().get(0);

			questionBlock = $("#questionsTemplate>.surveyBox_2").clone(true);
			
			$(questionBlock).find(".surveyListBlock").data("columnCount",3)
													.data("displayType",0)
													.data("questionId ","")
													.data("questionSeq","")
													.data("questionType","A0")
													.data("requiredAnswer",0)
													.data("answerRowCnt",4);
			$(questionBlock).appendTo($(parentTag));			
	
			changeUL('A0');
			updateQuestionSeq();			

			var param = questionAjax(questionBlock,-1,'insert');
			var item =$(questionBlock).answerSave(param);
			
			questionTitle = $(questionBlock).find(".questionTitle").html();
			
			$(questionBlock).find('.questionTitle').after(editLink);				
			$(questionBlock).click();
						
		});
		
		//문항이전에 문항추가
		$("a[name='insertBeforItems']").click(function(e){
			e.preventDefault();
			e.stopImmediatePropagation();
			e.stopPropagation();
			
			if(mode=="edit") {
				displayRowCancelSet();
			}
				
			var nParent = questionBlock;
			questionBlock = $("#questionsTemplate>.surveyBox_2").clone(true);
			$(questionBlock).find(".surveyListBlock").data("columnCount",3)
													.data("displayType",0)
													.data("questionId ","")
													.data("questionSeq","")
													.data("questionType","A0")
													.data("requiredAnswer",0)
													.data("answerRowCnt",4);
			$(nParent).before($(questionBlock));
			changeUL('A0');
			updateQuestionSeq();
			
			var param = questionAjax(questionBlock,-1,'insert');
			$(questionBlock).answerSave(param);			
			
			questionTitle = $(questionBlock).find(".questionTitle").html();

			$(questionBlock).find('.questionTitle').after(editLink);				
						
			$(questionBlock).click();
		});
		
		var updateQuestionSeq = function(){
			//서버에서 작업 아작스 동기화문제			
			$('#questionAllBlock .qnaSeq').each(function(index){
				$(this).text('Q'+(index+1));
			});			
			$(questionGroupAllBlock).find(".surveyBox_2").each(function(index){
				$(this).find(".surveyList").data('questionSeq',index+1);
			});			
		};
		
		//그룹이전으로 이동
		$("a[name='moveBeforGroups']").click(function(){
			var pParent = $(questionGroupAllBlock).prev();			
			if (pParent) {
				var nParent = questionGroupAllBlock;
				$(nParent).insertBefore($(pParent));				
				$("#questionAllBlock div[class='questionGroupAllBlock']").each(function(index){
					var param = groupAjax(this, index + 1);
					if (param.oldQuestionGroupSeq != param.questionGroupSeq) 
						$(this).questionSave(param);
				});
			}
		});
		
		//그룹 이후로 이동
		$("a[name='moveAfterGroups']").click(function(){
			var pParent = $(questionGroupAllBlock).next();			
			if (pParent) {
				var nParent = questionGroupAllBlock;
				$(nParent).insertAfter($(pParent));				
				$("#questionAllBlock div[class='questionGroupAllBlock']").each(function(index){
					var param = groupAjax(this, index + 1);
					if (param.oldQuestionGroupSeq != param.questionGroupSeq) 
						$(this).questionSave(param);
				});
			}
		});
		
		
		//문항제거
		$("a[name='removeItems']").click(function(){
			if( confirm(deletequestionconfirm) )
	 		{
				var nParent = questionBlock;
				var param = questionAjax(questionBlock,-1,'remove');
				$(nParent).answerSave(param);
				$(nParent).remove();
				
				updateQuestionSeq();
			}
	 		return false; 	
		});
		
		
		//그룹제거
		$("a[name='removequestionGroup']").click(function(){
			if( confirm(deletegroupconfirm) )
	 		{
	 			var param=groupAjax(questionGroupAllBlock,-1,'remove');
				$(questionGroupAllBlock).questionSave(param);
				$(questionGroupAllBlock).remove();
				init();
	 		}
	 		return false; 
		});		
		
		//문항이후에 문항추가
		$("a[name='insertAfterItems']").click(function(e){
		
			e.preventDefault();
			e.stopImmediatePropagation();
			e.stopPropagation();
			
			if(mode=="edit") {
				displayRowCancelSet();
			}		
			
			var nParent = questionBlock;
			questionBlock = $("#questionsTemplate>.surveyBox_2").clone(true);
			$(questionBlock).find(".surveyListBlock").data("columnCount",3)
													.data("displayType",0)
													.data("questionId ","")
													.data("questionSeq","")
													.data("questionType","A0")
													.data("requiredAnswer",0)
													.data("answerRowCnt",4);
			$(nParent).after($(questionBlock));
			changeUL('A0');
			updateQuestionSeq();
			
			var param = questionAjax(questionBlock,-1,'insert');
			$(questionBlock).answerSave(param);
			
			questionTitle = $(questionBlock).find(".questionTitle").html();

			$(questionBlock).find('.questionTitle').after(editLink);		
			$(questionBlock).click();			
		});
		
		//문항 이전으로 이동
		$("a[name='moveBeforItems']").click(function(){

			// 수정모드에서 이전으로 이동시 입력항목이 없는 경우 기본 입력내용으로 대체후 이동시킴
			if(mode=="edit") { 	
				var i=0;
				questionTitle=$(questionBlock).find("input[name='questionTitle']").val();
				if(questionTitle==undefined ||  questionTitle=="") {
					i++;
				}
				if(i==0) {
					$(questionBlock).find("input[name='answerTitle']").each(function(index){
						if($(this).val()==undefined ||  $(this).val()=="") {
							i++;
						}
					});					
				}
				if(i>0) {					
					displayRowCancelSet();
					$(questionBlock).find('.edit_btn').removeClass("none");
					$(questionBlock).find('.save_btn').addClass("none");
					$(questionBlock).find('.cancel_btn').addClass("none");								
				} else {
					$(questionBlock).find('.edit_btn').removeClass("none");
					$(questionBlock).find('.save_btn').addClass("none");
					$(questionBlock).find('.cancel_btn').addClass("none");				
				}
			}

			var pParent = $(questionBlock).prev();
			
			var hasPre = $(pParent).hasClass("surveyBox_2");			
			if( hasPre )
			{
				var nParent = questionBlock;
				$(nParent).insertBefore($(pParent));
				updateQuestionSeq();
				
				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);
			}
			
		});
		
		//문항 이후로 이동
		$("a[name='moveAfterItems']").click(function(){
		
			// 수정모드에서 이후으로 이동시 입력항목이 없는 경우 기본 입력내용으로 대체후 이동시킴
			if(mode=="edit") { 	
				var i=0;
				questionTitle=$(questionBlock).find("input[name='questionTitle']").val();
				if(questionTitle==undefined ||  questionTitle=="") {
					i++;
				}
				if(i==0) {
					$(questionBlock).find("input[name='answerTitle']").each(function(index){
						if($(this).val()==undefined ||  $(this).val()=="") {
							i++;
						}
					});					
				}
				if(i>0) {					
					displayRowCancelSet();
					$(questionBlock).find('.edit_btn').removeClass("none");
					$(questionBlock).find('.save_btn').addClass("none");
					$(questionBlock).find('.cancel_btn').addClass("none");								
				} else {
					$(questionBlock).find('.edit_btn').removeClass("none");
					$(questionBlock).find('.save_btn').addClass("none");
					$(questionBlock).find('.cancel_btn').addClass("none");				
				}
			}		
		
			var pParent = $(questionBlock).next();
			var hasNext = $(pParent).hasClass("surveyBox_2");
			
			if( hasNext )
			{			
				var nParent = questionBlock;
				$(nParent).insertAfter($(pParent));
				updateQuestionSeq();
				
				//이후추가는 ++한번더
				var currentIndex = $(questionBlock).find(".surveyList").data('questionSeq');
				$(questionBlock).find(".surveyList").data('questionSeq',currentIndex+1);				
				var currentIndex = $(questionBlock).find(".surveyList").data('questionSeq');
				
				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);
			}	
			
		});

		//답변 갯수 down
		$("#answerRowCntMinus").click(function(){

			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var answerRowCnt = parseInt($('#answerRowCnt').val());
			var questionType = $(surveyListBlock).data('questionType');
			
			if( answerRowCnt >=2 )			
			{
				answerRowCnt--;
				$('#answerRowCnt').val(answerRowCnt);
				$(surveyListBlock).data("answerRowCnt",answerRowCnt);				
				
				//두개다 선택형일경우
				changeView(questionType);
				displayRowView();
				
				var param = questionAjax(questionBlock,-1,'update');
				
				$(questionBlock).answerSave(param);
				
				questionTitle = $(questionBlock).find(".questionTitle").html();
				$(questionBlock).find('.questionTitle').after(editLink);					
				$(questionBlock).click();
			}
		});

		
		//답변 갯수 up
		$("#answerRowCntPlus").click(function(){

			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var answerRowCnt = parseInt($('#answerRowCnt').val());
			var questionType = $(surveyListBlock).data('questionType');
			
			if( answerRowCnt <=100 )			
			{
				answerRowCnt++;
				$('#answerRowCnt').val(answerRowCnt);
				$(surveyListBlock).data("answerRowCnt",answerRowCnt);
				
				//두개다 선택형일경우
				changeView(questionType);
				displayRowView();
				
				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);
				
				questionTitle = $(questionBlock).find(".questionTitle").html();
				$(questionBlock).find('.questionTitle').after(editLink);					
				$(questionBlock).click();
			}
		});
		

		
		//답변타입 선택 - 단일/다중
		$("input[name='statusQuestionType']").click(function(){
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var statusQuestionType =$(this).val();			
			var questionType = $(surveyListBlock).data("questionType");
			
			switch (statusQuestionType) {
				case 'S' : 
							if( questionType =='A4') questionType ='A0';
							else if( questionType =='A5') questionType ='A1';
							else if( questionType =='A6') questionType ='A2';
							else if( questionType =='A7') questionType ='A3';
							$(questionBlock).find(".answerBlock :checkbox")
								.replaceWith('<input name="" type="radio" title="" class="radio" value="" />');
								
							break;
				case 'D' : 
							if( questionType =='A0') questionType ='A4';
							else if( questionType =='A1') questionType ='A5';
							else if( questionType =='A2') questionType ='A6';
							else if( questionType =='A3') questionType ='A7';
							$(questionBlock).find(".answerBlock :radio")
								.replaceWith('<input name="" type="checkbox" title="" class="checkbox" value="" />');
							break;	
				default:
							break;
			}
			// 답변 타입 수정시 -- 단일/다중 자동 변경되게
			fnStatusQuestionType(questionType);
			
			$(surveyListBlock).data("questionType",questionType);
			
			var param = questionAjax(questionBlock,-1,'update');
			$(questionBlock).answerSave(param);			
		});
		
		//답변 필수 여부 선택 - 필수/필수아님
		$("input[name='statusRequiredAnswer']").click(function(){
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			$(surveyListBlock).data("requiredAnswer",$(this).val());
			
			var param = questionAjax(questionBlock,-1,'update');
			$(questionBlock).answerSave(param);
		});
		

		// 문항 표시 방법 display type  수직,수평,컬럼
		$("input[name='statusDisplayType']").click(function(){
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var displayType = $(surveyListBlock).data('displayType',$(this).val());
			
			displayRowView();
			
			var param = questionAjax(questionBlock,-1,'update');
			$(questionBlock).answerSave(param);
		});
		// 문항 표시
		function displayRowView(){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var displayType = $(surveyListBlock).data('displayType');
			var columnCount = $(surveyListBlock).data('columnCount');
			
			$(questionBlock).find(".answerBlock label").unwrap();
			
			if (displayType == '0') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock label").wrap("<li  class='bg_none'/>");
			}
			else if (displayType == '1') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock").children().wrapAll("<li  class='bg_none'/>");
			}else{
				$('[id^=columnCount]').show();
				
				var html="<li  class='bg_none'>";
				var loopCnt=0;
				$(questionBlock).find(".answerBlock label").each(function(index){
					if(  columnCount == loopCnt )
					{
						if( index > 0 )
							html =html + "</li>";
							
						html= html + "<li  class='bg_none'>";
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";
						loopCnt=0;
					}
					else
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";
						
					loopCnt++;
				});
				html= html+"</li>";
				
				$(questionBlock).find(".answerBlock").empty();
				$(questionBlock).find(".answerBlock").append(html);
			}
			
			updateQuestionSeq();
		}
		function displayRowView2(){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var displayType = $(surveyListBlock).data('displayType');
			var columnCount = $(surveyListBlock).data('columnCount');
			
			$(questionBlock).find(".answerBlock label").unwrap();

			if (displayType != '0') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock label").wrap("<li  class='bg_none'/>");			
			}
			
			//updateQuestionSeq();
		}
		function displayRowSaveSet(){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var questionType = $(surveyListBlock).data('questionType');
			var displayType = $(surveyListBlock).data('displayType');
			var columnCount = $(surveyListBlock).data('columnCount');
			
			// 설문 제목 되돌리기
			$(questionBlock).find(".questionTitle").html($(surveyListBlock).find("input[name='questionTitle']").val());
			
			if(questionType =="B0" || questionType=="B2" || questionType=="B3") {
				$jq.each($jq.makeArray($(surveyListBlock).find(".answerTitle")), function(index,obj){
					$(this).html($(this).html());
				});
			} else {			
				$(questionBlock).find("input[name='answerTitle']").each(function() {
					var value = $(this).val();
					$(this).parent().html(value);
				});
			}			
			$(questionBlock).find(".answerBlock label").unwrap();	
								
			if (displayType == '0') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock label").wrap("<li  class='bg_none'/>");
			}
			else if (displayType == '1') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock").children().wrapAll("<li  class='bg_none'/>");
			}else{
				$('[id^=columnCount]').show();
				
				var html="<li  class='bg_none'>";
				var loopCnt=0;
				$(questionBlock).find(".answerBlock label").each(function(index){
					if(  columnCount == loopCnt )
					{
						if( index > 0 )
							html =html + "</li>";
							
						html= html + "<li  class='bg_none'>";
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";
						loopCnt=0;
					}
					else
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";

					loopCnt++;
				});
				html= html+"</li>";
				
				$(questionBlock).find(".answerBlock").empty();
				$(questionBlock).find(".answerBlock").append(html);
			}
				
			//updateQuestionSeq();
		}
		function displayRowCancelSet(){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var displayType = $(surveyListBlock).data('displayType');
			var columnCount = $(surveyListBlock).data('columnCount');
			
			$(questionBlock).find(".answerBlock label").unwrap();
			// 설문 제목 되돌리기
			$(questionBlock).find(".questionTitle").html(oldQuestionTitle);
			$(questionBlock).find(".answerTitle").each(function(index) {
				$(this).html(oldAnswerTitle[index]);
			});					
				
			if (displayType == '0') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock label").wrap("<li  class='bg_none'/>");
			}
			else if (displayType == '1') {
				$('[id^=columnCount]').hide();
				$(questionBlock).find(".answerBlock").children().wrapAll("<li  class='bg_none'/>");
			}else{
				$('[id^=columnCount]').show();
				
				var html="<li  class='bg_none'>";
				var loopCnt=0;
				$(questionBlock).find(".answerBlock label").each(function(index){
					if(  columnCount == loopCnt )
					{
						if( index > 0 )
							html =html + "</li>";
							
						html= html + "<li  class='bg_none'>";
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";
						loopCnt=0;
					}
					else
						html= html + "<label style='padding-right:10px;'>" + $(this).html() +"</label>";
						
					loopCnt++;
				});
				html= html+"</li>";
				
				$(questionBlock).find(".answerBlock").empty();
				$(questionBlock).find(".answerBlock").append(html);
			}
			
			//updateQuestionSeq();
		}		
		//문항 컬럼수  Minus
		$("#columnCountMinus").click(function(){
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var columnCount = $(surveyListBlock).data('columnCount');
			
			if( columnCount >=2 )			
			{
				columnCount--;
				$('#statusColumnCount').val(columnCount);
				$(surveyListBlock).data('columnCount',columnCount);
				
				displayRowView();
				
				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);
			}
		});
		
		//문항 컬럼수 Plus
		$("#columnCountPlus").click(function(){
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var columnCount = $(surveyListBlock).data('columnCount');
			
			if( columnCount <=100 )			
			{
				columnCount++;
				$('#statusColumnCount').val(columnCount);
				$(surveyListBlock).data('columnCount',columnCount);
				displayRowView();
				
				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);		
			}
		});
		
		// 타입선택레이어 팝업
		$("#selectQuestionType").toggle(
			function(){
				$('#surveySelLayerTop').removeClass("none");
				var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
				var questionType = $(surveyListBlock).data("questionType");
			
				$('#surveySelLayerTop .surveyList').hide();
				$('#'+questionType).show();
				
				switch (questionType) {
					case 'A0': 
					case 'A1':
					case 'A2':
					case 'A3':
					case 'A4':
					case 'A5':
					case 'A6':
					case 'A7':
								$('#accordion').accordion( "activate" , 0 );
							    break;	
					case 'B0':
					case 'B1':
					case 'B2':
					case 'B3':
								$('#accordion').accordion( "activate" , 1 );
							    break;
					case 'C0':
								$('#accordion').accordion( "activate" , 2 );
							    break;
					case 'D0':
					case 'D1':
					case 'D2':
					case 'D3':
					case 'D4':
					case 'D5':
					case 'D6':
					case 'D7':
							    $('#accordion').accordion( "activate" , 3 );
							    break;	
					default:    $('#accordion').accordion( "activate" , 0 );
								$('#A0').show();
								break;
				}				
			},function(){
				$('#surveySelLayerTop').addClass("none");
			}
		);
		
		// 설문 문항 타입
		$("a[name='selectTypeButton']").hover(
			function(){
				var idKey = $(this).attr("id").replace('P','');
				$('#'+idKey).show();
			},
			function(){
				var idKey = $(this).attr("id").replace('P','');
				$('#'+idKey).hide();
			}
		);
		
		// 설문 문항 타입
		$("a[name='selectTypeButton']").click(function(){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var preQuestionType = $(surveyListBlock).data('questionType');
			var questionType = $(this).attr("id").replace('P','');

			$(surveyListBlock).data('questionType',questionType);
			$('#'+questionType).hide();
			
			$('#selectQuestionType').click();			

			//변경되었을경우에만 작업
			if( preQuestionType != questionType )
			{
				$('#selectQuestionType >a').text( $(this).text() );
				
				changeView(questionType);

				var param = questionAjax(questionBlock,-1,'update');
				$(questionBlock).answerSave(param);
				
				questionTitle = $(questionBlock).find(".questionTitle").html();

				$(questionBlock).find('.questionTitle').after(editLink);				
				$(questionBlock).click();								
			}					
		});
		
		var changeView = function(questionType){
			//두개다 선택형일경우
			if (questionType.indexOf("A") >= 0 ){
				changeUL(questionType);
				displayRowView();
			}else if( questionType.indexOf("B") >= 0) {	
				changeUL(questionType);
			}
			else if (questionType.indexOf("C") >= 0 || questionType.indexOf("D") >= 0) {
				changeTable(questionType);
			}
		};
		
		
		var appendImg = function(){
			var nParent = questionBlock;
			var answerBlock =  $(questionBlock).find(".answerBlock");
			
			$(answerBlock).children().each(function(){
				$(this).find("label").append('<img src="'+contextRoot+'/support/fileupload/downloadFile.do?fileId=xxxxx" alt="image" />'); 
			});
		};		
		
		var changeUL = function(questionType){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var answerRowCnt = parseInt($(surveyListBlock).data('answerRowCnt'));			
			
			var questionTitle = $(surveyListBlock).find(".questionTitle").html();
			var answerTitleList= [];
			
			$(surveyListBlock).find(".answerTitle").each(function(){
				answerTitleList.push($(this).html());
				//alert($(this).html());
			});
			
			$(surveyListBlock).empty();
			$(surveyListBlock).append( $('#a0Template').children().clone(true,true) );

			if(questionTitle != null && questionTitle !='' && questionTitle !='undefined')
				$(surveyListBlock).find(".questionTitle").html(questionTitle);
			
			var answerBlock =  $(questionBlock).find(".answerBlock");
			$(answerBlock).empty();
						
			//B타입중에서 1개만 해야할경우
			switch (questionType) {
						case 'A0': 
						case 'A1': 
						case 'A2': 
						case 'A3': 
						case 'A4': 
						case 'A5': 
						case 'A6': 
						case 'A7': $("#answerRowCntLayer").show();
								   $("#statusQuestionTypeLayer").show();
								   $("#statusDisplayTypeLayer").show();
								   $("#columnCountLayer").show(); 
						           break;
						case 'B0': answerRowCnt =1;
								   $("#answerRowCntLayer").hide();
								   $("#statusQuestionTypeLayer").hide();
								   $("#statusDisplayTypeLayer").hide();
								   $("#columnCountLayer").hide();
								   break;
						case 'B1': 		   
						case 'B2': $("#answerRowCntLayer").show();
								   $("#statusQuestionTypeLayer").hide();
								   $("#statusDisplayTypeLayer").hide();
								   $("#columnCountLayer").hide(); 
								   break;		   
						case 'B3':
								   answerRowCnt =1;
								   $("#answerRowCntLayer").hide();
								   $("#statusQuestionTypeLayer").hide();
								   $("#statusDisplayTypeLayer").hide();
								   $("#columnCountLayer").hide(); 	
								   break;
						default:
							break;
			}
			
			for(i=0; i<answerRowCnt; i++)
			{
				var tBody = "";				
				if (i < answerRowCnt-1) {
					tBody = $('#T'+questionType+'>ul li:first').clone(true,true);
				}
				else
				{	
					switch (questionType) {
						case 'A2':
							tBody = $('#T' + questionType + '>ul li:last').clone(true, true);
							break;
						case 'A3':
							tBody = $('#T' + questionType + '>ul li:last').clone(true, true);
							break;
						case 'A6':
							tBody = $('#T' + questionType + '>ul li:last').clone(true, true);
							break;
						case 'A7':
							tBody = $('#T' + questionType + '>ul li:last').clone(true, true);
							break;
						default:
							tBody = $('#T'+questionType+'>ul li:first').clone(true,true);
							break;
					}
				}
				
				if( answerTitleList[i] !=null && answerTitleList[i] !='' && answerTitleList[i] !='undefined' ) 
					$( tBody ).find(".answerTitle").html( answerTitleList[i] );
				
				$( tBody ).appendTo( answerBlock );				
			}
		};	
		
		
		var changeTable = function(questionType){
			var nParent = questionBlock;
			var surveyListBlock =  $(questionBlock).find(".surveyListBlock");
			var answerRowCnt = parseInt($(surveyListBlock).data('answerRowCnt'));
			var surveyId =$('#questionAllBlock').data('surveyId')
			var questionId		= $(surveyListBlock).data("questionId");
			var questionTitle = $(surveyListBlock).find(".questionTitle").html();
			
			var answerTitleList= [];
			
			$(surveyListBlock).find(".answerTitle").each(function(){
				answerTitleList.push($(this).html());
			});
			
			var scale1Title = $(surveyListBlock).find(".scale1Title").html();
			var scale2Title = $(surveyListBlock).find(".scale2Title").html();
			var scale3Title = $(surveyListBlock).find(".scale3Title").html();

			$(surveyListBlock).empty();
			$(surveyListBlock).append( $('#d0Template>.blockDetail').clone(true,true) );
	
			var answerBlock =  $(questionBlock).find(".answerBlock");
			var answerBlockHeader =  $(questionBlock).find(".answerBlockHeader");
			
			$(answerBlock).empty();
			$(answerBlockHeader).empty();
			
			// left lear show hide
			$("#answerRowCntLayer").show();
		    $("#statusQuestionTypeLayer").hide();
		    $("#statusDisplayTypeLayer").hide();
		    $("#columnCountLayer").hide(); 
			
			var tHeader = $('#T'+questionType+'>.blockDetail>table>thead tr').clone(true,true);
			if(scale1Title != null && scale1Title != '' && scale1Title !='undefined') $(tHeader).find(".scale1Title").html(scale1Title);
			if(scale2Title != null && scale2Title != '' && scale2Title !='undefined') $(tHeader).find(".scale2Title").html(scale2Title);
			if(scale3Title != null && scale3Title != '' && scale3Title !='undefined') $(tHeader).find(".scale3Title").html(scale3Title);
			$(answerBlockHeader).append( tHeader );
				
			for(i=0; i<=answerRowCnt-1; i++)
			{
				var tBody = $('#T'+questionType+'>.blockDetail>table>tbody>tr:first').clone(true,true);
				if( answerTitleList[i] != null && answerTitleList[i] !='' && answerTitleList[i] !='undefined' ) $( tBody ).find(".answerTitle").html( answerTitleList[i] );
				$(tBody).appendTo( answerBlock );
			}
			if( questionTitle !=null && questionTitle !='' && questionTitle !='undefined') {
				$(answerBlockHeader).find(".questionTitle").html(questionTitle);
			}
		};	
		
		$("#questionAllBlock label").click(function(e){
			e.preventDefault();
			e.stopImmediatePropagation();
			e.stopPropagation();
			return false;	
		});
		
		editGroupTitle=function(surveyId,questionGroupId){
				//e.preventDefault();
				//e.stopImmediatePropagation();
				//e.stopPropagation();
				//questionGroupAllBlock
				//questionGroupAllBlock =  $jq.find("#questionGroupAllBlock_"+questionGroupId);

				var title = $(questionGroupAllBlock).find(".questionGroupTitle").html();
				var contents = $(questionGroupAllBlock).find(".questionGroupContents ").html();
				//alert(contents);

				var editTitle = "Edit";

				$("#inputTextAreaLayer").show();
				$("#inputTextAreaLayer").select();
				$("#inputTextAreaLayer").focus();
				$("textarea[name='title']").val(title.replace(/(<br\s*\/?>)/gi, "\n"));
				$("textarea[name='contents']").val(contents.replace(/(<br\s*\/?>)/gi, "\n"));
				
		};		
		
		// 현재 공통 설문 그룹/질문/답변항목 수정폼 화면
		//$("#questionAllBlock").delegate(".questionGroupTitle,.questionGroupContents,.questionTitle,.answerTitle,[class^='scale']","dblclick",function(e){
		/*$("#questionAllBlock").delegate(".questionGroupTitle,.questionGroupContents","dblclick",function(e){
				e.preventDefault();
				e.stopImmediatePropagation();
				e.stopPropagation();
				
				var editTitle = "Edit";
				
				if ( $(this).hasClass("questionGroupTitle") )
					editTitle=questionGroupTitle;
				else if ( $(this).hasClass("questionGroupContents") )
					editTitle=questionGroupContents;
				else if ( $(this).hasClass("questionTitle") )
					editTitle=questionTitle;
				else if ( $(this).hasClass("answerTitle") )
					editTitle=answerTitle;
				else 
					editTitle=scaleTitle;				
				
				$("#inputTextAreaLayer>.smsvs_layer>.smsvs_layer_title").html(editTitle+" "+updateTitle);			
			
				var getText = $(this).html();
				titleBlock = this;
				$("#inputTextAreaLayer").show();
				$("#inputTextAreaLayer").select();
				$("#inputTextAreaLayer").focus();
				$("#inputAllValue").val(getText.replace(/(<br\s*\/?>)/gi, "\n"));
				$("#inputAllValue").select();
				$("#inputAllValue").focus();
				
				$("#inputTextAreaLayer").position({
				  my: "left center",
				  at: "left center",
				  of:  $(titleBlock),
				  collision: "fit"
				});
				
				//alert(e.currentTarget);
				
		});*/
		
		
		/*$("#questionAllBlock .questionGroupTitle,.questionGroupContents,.questionTitle,.answerTitle,[class^='scale']").each(function(){
			if( $(this).html() == '' ) $(this).html('not value');	
		});*/

		// 현재 공통 설문 그룹/질문/답변항목 취소
		$("#inputTextAreaLayerClose,#inputTextAreaLayerClose1").click(function(){
			$("#inputTextAreaLayer").hide();
		});
		

		var groupAjax1 = function(currentBlock,seq,reqestType){
			var questionGroupId = $(currentBlock).data("questionGroupId");
			var oldQuestionGroupSeq = $(currentBlock).data("questionGroupSeq");
			var questionGroupSeq = seq==-1?$(currentBlock).data("questionGroupSeq"):seq;
			var title = $('#title').val();
			var contents = $("#contents").val();
			//alert(contents);
			var surveyId = $("#questionAllBlock").data("surveyId");
			
			var url = "";
			
			//alert(questionGroupId);
			
			//그룹아이디가 없을경우에해당
			if( questionGroupId == null || questionGroupId == '' || questionGroupId=='undefined')
				url = contextRoot+'/servicepack/survey/question/createQuestionGroup.do';
			else if( reqestType == 'remove' )
			    url = contextRoot+'/servicepack/survey/question/removeQuestionGroup.do';	
			else
				url = contextRoot+'/servicepack/survey/question/updateQuestionGroup.do';	

				
			var param={"surveyId":surveyId,"questionGroupId":questionGroupId,"title":title,"contents":contents,"questionGroupSeq":questionGroupSeq,"oldQuestionGroupSeq":oldQuestionGroupSeq,"url":url};	
			
			return param;
		};
		// 현재 공통 설문 그룹/질문/답변항목 수정처리
		$("#inputTextAreaLayerSave").click(function(){
			
			var title = jQuery.trim($('#title').val());

			if (title.length <= 0) {
				alert(minInputValue);
				return false;
			}
			
			if (title.length > 300) {
					alert(maxInputValue);
					return false;
			}
			
			var contents = jQuery.trim($('#contents').val());
			//alert(contents);
			
			if (contents.length <= 0) {
				alert(minInputValue);
				return false;
			}
			
			if (contents.length > 1000) {
					alert(maxInputValue);
					return false;
			}			
			
			//$(titleBlock).html( value.replace(/\n/gi, "<br/>") );
			$("#inputTextAreaLayer").hide();
			
			
			var param=groupAjax1(questionGroupAllBlock,-1,'update');
			$(questionGroupAllBlock).questionSave(param);	
			
			$(questionGroupAllBlock).find(".questionGroupTitle").html(title.replace(/\n/gi, "<br>"));
			$(questionGroupAllBlock).find(".questionGroupContents ").html(contents.replace(/\n/gi, "<br>"));

			
		});
		
		init('init');
		
		/**
		 * 양식 미리보기 화면 오픈.
		 */
		$("#previewButton").click(function(){
				iKEP.showDialog({     
				title 		: 'Preview',
				url 		: contextRoot+"/servicepack/survey/previewSurvey.do?surveyId="+ $('#questionAllBlock').data('surveyId'),
				modal 		: true,
				width 		: 800,
				height 		: 500
			});
		});	
		
		
		
		
	});
	
	//첫번째 컬럼을 선택하기 위해서는 onload로 해야함 jquery data사용
	$(window).load(function () { 
  		questionBlock =$(questionGroupAllBlock).find(".questionGroupBlock>.surveyBox_2:first");
		$(questionBlock).click();
	});

})(jQuery);




