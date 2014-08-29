var Compress = function(){};  
Compress.prototype.zip = function(successCallback, errorCallback)  
{  
	try{         
		cordova.exec(successCallback, errorCallback, 'Compress', 'addDirToZipArchive', []);  
	}  
	catch(e){}  
};  

if(!window.plugins) {  
    window.plugins = {};  
}  
if (!window.plugins.Compress) {  
	window.plugins.Compress = new Compress();  
}  