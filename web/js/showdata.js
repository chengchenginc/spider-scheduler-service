$(function() {

    var url = window.location.href;
    var PageParam = purl(url).param();

    function showData(params, callback) {
        if (!params.limit) {
            params.limit = 10;
        }
        if (!params.page) {
            params.page = 1;
        }
        if(params.query){
            params.query = encodeURIComponent(JSON.stringify(params.query));
        }
        jobdata.list(params, function(response) {
            callback && callback(response);
        });
    };


    $("#pagination").on("page", function(event, num) {
        PageParam.page = num;
        showData(PageParam, function(response) {
            $('#scheduler_data_list').bootstrapTable('load', response.data);
        });
    });

    $(".btn-deleteAll").click(function() {
        jobdata.deleteAll(PageParam, function(response) {
            window.location.reload();
        })
    });

    showData(PageParam, function(response) {
        var columns = new Array();
        if (response.data.length > 0) {
            var item = response.data[0];
            for (arg in item) {
                columns.push({
                    field: arg,
                    title: arg
                });
            }
        }
        $('#scheduler_data_list').bootstrapTable({
            data: response.data,
            columns: columns,
            checkboxHeader: false,
            pagination: false, //是否显示分页（*）
            pageNumber: response.page,
            pageSize: response.limit,
            sidePagination: "client",
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            locale: 'zh-CN'
        });
        $('#pagination').bootpag({
            total: Math.ceil(response.total / response.limit), // total pages
            page: response.page, // default page
            maxVisible: 5, // visible pagination
            leaps: false, // next/prev leaps through maxVisible
            //href: 'showdata.html?name='+PageParam.name+'&group='+PageParam.group+'&page={{number}}',
            //hrefVariable: '{{number}}',
        });
    });
    $(".btn-request-api").click(function() {
        var url = $("#request_url").val();
        var params = purl(url).param();
        if(params.query){
          params.query = eval("("+params.query+")")
        }
        showData(params, function(response) {
            $("#scheduler_data_json").html(renderjson.set_show_to_level('all')(response.data));
        });
    });

    function initUrl() {
        var url = "";
        for (var i in PageParam) {
            url += i + "=" + PageParam[i] + "&";
        }
        var last = url.lastIndexOf("&");
        if (last == (url.length - 1)) {
            url = url.substr(0, last);
        }
        $("#request_url").val(jobdata.host + "?" + url);
    }
    initUrl();


});
