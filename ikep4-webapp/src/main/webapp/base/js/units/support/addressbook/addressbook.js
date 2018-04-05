/**
 * Addressbook 공통 Javascript addressbook.js
 */

	var Addressbook = new (function() {
		
		var _contextRoot = window["contextRoot"] || "";
		this.getContextRoot = function () { return _contextRoot; };
		
	})();
	
	// 주소록 메뉴 전체 조회
	Addressbook.getLeftMenuView = function() {
		$jq.ajax({
		    url : this.getContextRoot() + "/support/addressbook/readAddrbookMenu.do",
		    type : "get",
		    success : function(result) {
		    	$jq("#leftMenu").html(result);
		    }
		});
	};
	
	// 주소록 body 조회
	Addressbook.getAddrMainView = function(addrgroupId,groupType,addrgroupName) {
		$jq.ajax({
		    url : this.getContextRoot() + "/support/addressbook/listAddrbook.do",
		    data : {'addrgroupId':addrgroupId,'groupType':groupType,'addrgroupName':addrgroupName},
		    type : "get",
		    success : function(result) {
		    	$jq("#mainContents").html(result);
		    }
		});
	}; 
	
	// 팁 그룹 조회
	Addressbook.getTeamAddrgroupView = function(addrgroupId) {
		$jq.ajax({
		    url : this.getContextRoot() +  "/support/addressbook/readAddrgroupListByGroupType.do",
		    data : {'addrgroupId':addrgroupId,'groupType':'T'},
		    type : "get",
		    success : function(result) {
		    	$jq(".private_Team").html(result);
		    }
		});
	};
	
	// 개인 그룹 조회
	Addressbook.getPrivateAddrgroupView = function(addrgroupId) {
		$jq.ajax({
		    url : this.getContextRoot() +  "/support/addressbook/readAddrgroupListByGroupType.do",
		    data : {'addrgroupId':addrgroupId,'groupType':'P'},
		    type : "get",
		    success : function(result) {
		    	$jq(".private_group").html(result);
		    }
		});
	};
	
	// 개인 공용 그룹 조회
	Addressbook.getPrivatePaddrgroupView = function(addrgroupId) {
		$jq.ajax({
		    url : this.getContextRoot() +  "/support/addressbook/readAddrgroupListByGroupType.do",
		    data : {'addrgroupId':addrgroupId,'groupType':'G'},
		    type : "get",
		    success : function(result) {
		    	$jq(".prip_group").html(result);
		    }
		});
	};
	
	// 공용 그룹 조회 
	Addressbook.getPublicAddrgroupView = function(addrgroupId) {
		$jq.ajax({
		    url : this.getContextRoot() + "/support/addressbook/readAddrgroupListByGroupType.do",
		    data : {'addrgroupId':addrgroupId,'groupType':'O'},
		    type : "get",
		    success : function(result) {
		    	$jq(".public_group").html(result);
		    }
		});
	}; 
	
	/*
	// 등록된 연락처의 타이틀 창 조회
	Addressbook.getPersonListTitleView = function(addrgroupId,groupType,addrgroupName,addrgroupNavigation) {

		$jq.ajax({
		    url : this.getContextRoot() +  "/support/addressbook/readAddrbookListTitle.do",
		    data : {'addrgroupId':addrgroupId,'groupType':groupType,'addrgroupName':addrgroupName,'addrgroupNavigation':addrgroupNavigation },
		    type : "get",
		    success : function(result) {
		    	$jq("#pageTitle").html(result);
		    }
		});
		
	};
	*/
	
	// 그룹 리스트 Home 조회
	Addressbook.getAddrHome = function(addrgroupId,groupType,addrgroupName) {		
		addrgroupName = encodeURIComponent(addrgroupName);		
		document.location.href = this.getContextRoot() + "/support/addressbook/addressbookHome.do?addrgroupId="+addrgroupId+"&groupType="+groupType+"&addrgroupName="+addrgroupName ;
	}; 
	
	// 그룹 리스트 조회
	Addressbook.getAddrgroupList = function(groupType) {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/listAddrgroupList.do?groupType="+groupType ;
		
	}; 
	
	Addressbook.editCategory = function() {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/editCategory.do" ;
		
	}; 
	
	// Social Connection 조회
	Addressbook.getSocialConnection = function() {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/listSocialConnection.do" ;

	};
	
	// Contact History 조회
	Addressbook.getContactHistroy = function() {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/listContactHistory.do" ;
		
	};
	
	// Contact History 조회
	Addressbook.getContactHistroyList = function(registerId,searchType,isFirst) {

		var searchContactId = '';
		if(isFirst == 'false' ){
			try{
				if("pre" == searchType){
					searchContactId = $jq('input[name=contactId]:hidden').last().attr("value");
				}else{
					searchContactId = $jq('input[name=contactId]:hidden').first().attr("value");
				}
			}catch(e){}
		}

		$jq.ajax({
		    url : this.getContextRoot() + "/support/addressbook/readContactHistory.do",
		    data : {'registerId':registerId,'searchContactId':searchContactId,'searchType':searchType },
		    type : "get",
		    success : function(result) {
		    	if("pre" == searchType){
		    		$jq("#contacthist").append(result);
		    	}else{
		    		$jq("#contacthist").prepend(result);
		    	}
		    }
		});
		
	};
	
	// Addressbook Import
	Addressbook.getAddrbookImport = function(gType) {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/listAddrbookImport.do?gType="+gType ;
	
	};
	
	// Addressbook Export
	Addressbook.getAddrbookExport = function(gType) {
		
		document.location.href = this.getContextRoot() +  "/support/addressbook/listAddrbookExport.do?gType="+gType ;
		
	};
	
	
	Addressbook.editPerson = function(addrgroupId,groupType,personId) {
		
		document.location.href = this.getContextRoot() + "/support/addressbook/editPerson.do?addrgroupId="+addrgroupId+"&groupType="+ groupType+"&personId="+ personId ;

	};
	
	
	Addressbook.addrbookTeamplatePopup = function(groupType) {
		iKEP.popupOpen(this.getContextRoot() + "/support/addressbook/downAddrbookImportTemplatePopup.do?groupType="+groupType, {width:900, height:650, resizable:true, callback : function(result){}});
	}; 
	
