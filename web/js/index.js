$(function() {
    jobManager.list(null, function(data) {
        $('#scheduler_list_table').bootstrapTable({
            data: data,
            columns: [{
                checkbox: true
            }, {
                field: "name",
                title: "任务名",
                width: '10%'
            }, {
                field: "group",
                title: "任务组",
                width: '10%'
            }, {
                field: "cronExpression",
                title: "cron表达式",
                class: "editable",
                width: '20%'
            }, {
                field: "status",
                title: "状态",
                width: '10%'
            }, {
                field: "className",
                title: "任务类"
            }, {
                field: "description",
                title: "描述"
            }],
            pagination: true, //是否显示分页（*）
            sidePagination: "client",
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100],
            clickToSelect: false, //是否启用点击选中行
            search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            //queryParams: queryParams,//传递参数（*）
            height: 400,
            toolbar: '#toolbar',
            locale: 'zh-CN'
        });


        $(".editable").editable().on("save",function(e, params){
            return false;
        });
        var queryParams = function(params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
                departmentname: $("#txt_search_departmentname").val(),
                statu: $("#txt_search_statu").val()
            };
            return temp;
        };

    });
});
