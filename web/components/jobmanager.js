/**
*job manager 操作
*/
(function(win,$){
	var jobManager = {};

  jobManager.host = "http://localhost:8888/services/job"

	jobManager.list = function(state,callback){
		var url = "/list";
    var data = {};
		if("undefined"!=typeof(state) && state!=null){
			data.state = state;
		}
		api.get(jobManager.host+"/list",data,callback);
	};

	jobManager.delJob = function(name,group,callback){
      api.get(jobManager.host+"/delete",{name:name,group:group},callback);
	};


	jobManager.triggerJob = function(name,group,callback){
      api.get(jobManager.host+"/trigger",{name:name,group:group},callback);
	};


	jobManager.addJob = function(name,group,callback){
	};


	jobManager.modifyJobTrigger = function(name,group,cron,callback){
      api.post(jobManager.host+"/modify",{name:name,group:group,cron:encodeURIComponent(cron)},callback);
	};


	jobManager.stopJob = function(name,group,callback){
     api.get(jobManager.host+"/stop",{name:name,group:group},callback);
	};


  jobManager.restartJob = function(name,group,callback){
     api.get(jobManager.host+"/restart",{name:name,group:group},callback);
	};


	win.jobManager = jobManager;

})(window,jQuery);
