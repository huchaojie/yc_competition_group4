function post(url,param,callback){
	var xhr
	try{
		xhr= new XMLHttpRequest();
		
	} catch(e){
		xhr=new ActiveXObject("xxxxxx");
	}
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4 && xhr.status==200){
			callback(xhr.responseText);
		}
	}
	xhr.open("POST",url);
	
	
	if( !(param instanceof FormData)){
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	xhr.send(param);
}