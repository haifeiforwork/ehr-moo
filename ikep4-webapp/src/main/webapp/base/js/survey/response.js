(function($) {
	$jq(document).ready(function() {
		
		$('.O').click(function(){
			$(this).parent().parent().find(".surveyList_num :not(:radio[name^='q'])").removeAttr('disabled');
		});
		
		$('.X').click(function(){
			$(this).parent().parent().find(".surveyList_num :not(:radio[name^='q'])").attr('disabled','disabled').removeAttr("checked");
		});
		
		$(this).find(".O,.X").each(function(){
			$(this).parent().parent().find(".surveyList_num :not(:radio[name^='q'])").attr('disabled','disabled').removeAttr("checked");
		});
/*		
		$("input[name^='q']:radio").click(function(data) {		
			var name		= $(this).attr("name");
			var txtVal		= $(this).val().split('|');
			var textName	= txtVal[2];
			if(textName == $('#'+name).attr("name"))
				$("#"+name).attr("disabled","");
			else 
				$("#"+name).attr("disabled","disabled");	
		});
		
		$("input[name^='q']:checkbox").click(function(data) {	
			var name		= $(this).attr("name");
			var txtVal		= $(this).val().split('|');
			var textName	= txtVal[2];
			var check		= false;			
			$("input[name='"+name+"']:checkbox:checked").each(function() {	
				if(textName == $('#'+name).attr("name")) {
					check = true;
				} 		
			});

			if( check)
				$("#"+name).attr("disabled","");
			else
				$("#"+name).attr("disabled","disabled");	
		});				
*/

		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#saveButton').click( function() {
			//$jq('#surveyForm').submit();
			var result = validdation();
			
			if( result )
			{
				$("#surveyForm").submit();
			}
			
		});
		
		
		
		
		
		var validdation = function(){
			var valid =true;
			var rqUL = $(".required");
			
			 $(".required").each(function(){
				var questionType = $(this).find(":input[name='data-questionType']").val();
				
				
				
				if( questionType ==undefined )
					questionType = $(this).prev().val();
				
				var title = $(this).attr('title');
				
				var val="";
				
				switch (questionType) {
					case 'A0': 
					case 'A1': 
					case 'A2': 
					case 'A3': 
					           val = $(this).find(":radio:checked").val(); 
							   break;
					case 'A4': 
					case 'A5': 
					case 'A6': 
					case 'A7': 
							   val = $(this).find(":checkbox:checked").val(); 
					           break;
					case 'B0': 
					case 'B1': 
					case 'B2': 
					case 'B3':
					           $(this).find(":input").each(function(){
									val = $(this).val();
									if( val == '' ){
										$(this).focus();
										return false;
									} 
							   }); 
							   break;
					case 'C0':
					           $(this).find("select option:selected").each(function(){
									val = $(this).val();
									if( val == '' ){
										return false;
									} 
							   }); 
							   break;	
					case 'D0': 
					case 'D1': 
					case 'D2': 
					case 'D3': 
					case 'D6':
							   val = $(this).find(":radio:checked").val(); 
							   break;
					case 'D4': 
					case 'D5':  val = $(this).find(":radio[name^='q']:checked").val();
								if( val =='' || val==undefined) break; //첫번째 값
								else
								{
									var OX = $(this).find(":radio[name^='q']:checked").attr('title');
									if( OX=='X') break;
									else
									{
										val = $(this).find(":not(:radio[name^='q']):checked").val();
									}
								} 
								break;
					case 'D7': 
					           val = $(this).find(":radio:checked").val(); 
							   break;  	   
					default:
						break;
				}
				
				
				if( val =='' || val==undefined)
				{
					valid =false;
					alert('['+title +'] 은 필수항목입니다. 답변해주세요.');
					return false;
				}	
						
			});
			
			return valid;
		};
		
	});
})(jQuery);