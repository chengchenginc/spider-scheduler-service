(function(win, $) {

    var api = {};
    api.request = function(url, callback, context) {
        jQuery.ajax(url, {
            data: context.body,
            dataType: "JSON",
            method: context.method,
            success: function(reponse) {
                callback && callback(reponse);
            }
        });
    };

    api.get = function(url, data,callback) {
        var context = {
            method: 'GET',
            body: {}
        };
        var argsArr = Array();
        for (arg in data){
           argsArr.push(arg+"="+data[arg]);
        }
        if(argsArr.length > 0){
            url = url+"?"+argsArr.join("&");
        }
        api.request(url, callback, context);
    };

    api.post = function(url, data, callback) {
        var context = {
            method: 'POST',
            body: data
        };
        api.request(url, callback, context);
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

    window.api = api;
})(window, jQuery);
