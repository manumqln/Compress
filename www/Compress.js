var Compress = function(){} 
Compress.prototype.zip = function(ids, successCallback, errorCallback)  
{  
	try{ 
		cordova.exec(successCallback, errorCallback, 'Compress', 'zip', ids);  
	}  
	catch(e){}  
};  

Compress.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.compress = new Compress();
  return window.plugins.compress;
};

cordova.addConstructor(Compress.install);