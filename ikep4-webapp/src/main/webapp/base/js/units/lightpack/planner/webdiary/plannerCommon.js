(function( $ ){
	var dreg =/^(\d{4})[-|:|.]*(\d{2})[-|:|.]*(\d{2})(\d{2})(\d{2})$/;
	
	$.extend({
		  getUrlVars: function(){
		    var vars = {}, hash;
		    var strPos = {start:location.href.indexOf('?'), end:location.href.indexOf("#")};
		    if(strPos.start >= 0) {
			    var hashes = window.location.href.substring(strPos.start+1, strPos.end == -1 ? location.href.length : strPos.end).split('&');
			    for(var i = 0; i < hashes.length; i++) {
			      hash = hashes[i].split('=');
			      vars[hash[0]] = decodeURIComponent(hash[1]);
			    }
		    }
		    return vars;
		  },
		  getUrlVar: function(name){
		    return $.getUrlVars()[name];
		  }
	});
	$.extend({
		toDate: function(ds) {
			var d = ds;
			if(ds.length < 11) {
				d = ds + "0000";
			}
			var ar = dreg.exec(d);
			return new Date(ar[1],Number(ar[2]) - 1,ar[3],ar[4],ar[5]);
		}
	});
})( jQuery );