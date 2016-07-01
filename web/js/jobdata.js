/**
 *  job数据展现
 */
(function(win, $) {

    var jobdata = {};
    jobdata.host = "http://localhost:8888/services/crawler/data";

    //获取数据列表
    jobdata.list = function(params, callback) {
        if (!params.name || !params.group) {
            return false;
        }
        api.get(jobdata.host + "/list", params, callback);
    };

    //清空所有数据
    jobdata.deleteAll = function(params, callback) {
        if (!params.name || !params.group) {
            return false;
        }
        api.get(jobdata.host + "/delete", params, callback);
    };

    jobdata.export = function(params, callback) {
        if (!params.name || !params.group || !params.format) {
            return false;
        }
        var url = jobdata.host + "/export";
        callback && callback(url);
    };

    win.jobdata = jobdata;

})(window, jQuery);
