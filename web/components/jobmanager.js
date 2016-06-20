/**
*job manager 操作
*/
(function(win,$){
	var jobManager = {};

	jobManager.list = function(state,callback){
		var url = "/list";
		if("undefined"!=typeof(state) && state!=null){
			url +="?state="+state;
		}
		api.get(api.host+"/list",callback);
	};

	jobManager.delJob = function(name,group,callback){
	};


	jobManager.triggerJob = function(name,group,callback){
	};


	jobManager.addJob = function(name,group,callback){
	};


	jobManager.modifyJobTrigger = function(name,group,cron,callback){
	};


	jobManager.stopJob = function(name,group,callback){
	};

    
	var api = {};
	api.host = "http://localhost:8888/services/job";


	api.request = function (url, callback, context) {
		jQuery.ajax(url,{
			data:context.body,
			dataType:"JSON",
			method:context.method,
			success:function(reponse){
				callback(reponse);
			}
		});
	};


	api.get=function(url,callback){
		var context = {
			method: 'GET',
			body:{}
		};
		api.request(url,callback,context);
	};

	api.post=function(url,data,callback){
		var context = {
			method: 'POST',
			body: JSON.stringify(data)
		};
		api.request(url,callback,context);
	};

	function checkStatus(response) {
	  if (response.status >= 200 && response.status < 300) {
		return response;
	  } else {
		var error = new Error(response.statusText)
		error.response = response;
		throw error;
	  }
	}

	function parseJSON(response) {
	  return response.text();
	}


	window.jobManager = jobManager;
})(window,jQuery);