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
            checkboxHeader: false,
            singleSelect: true,
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
            $row = $(this).parent();
            var name = $row.find("td:eq(1)").text();
            var group = $row.find("td:eq(2)").text();
            var cron = params.submitValue;
            jobManager.modifyJobTrigger(name,group,cron,function(){

            });
            return false;
        });
        var queryParams = function(params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset //页码
            };
            return temp;
        };
    });

    //弹出层
    $(".btn-fancybox").fancybox({
          afterClose:function(){
              var $form =  $("form[name='form_scheduler']");
              $form[0].reset();
          }
    });

    //新增解析属性
    $(document).on("click",".btn-add-attribute",function(){
      var $this = $(this);
      var $table = $this.next();
      var template = $("#attribute-row-template").html();
      $(template).insertAfter($table.find("tr:last"));
    });


    //删除解析属性
    $(document).on("click",".btn-remove-attribute",function(){
      var $this = $(this);
      var $tr = $this.parent().parent();
      $tr.remove();
    });


    $("#btn_stop").click(function(){
        var rows = $('#scheduler_list_table').bootstrapTable("getSelections");
        if(rows.length>0){
           var row = rows[0];
           jobManager.stopJob(row.name,row.group,function(){

           });
        }
    });

    $("#btn_run_once").click(function(){
        var rows = $('#scheduler_list_table').bootstrapTable("getSelections");
        if(rows.length>0){
           var row = rows[0];
           jobManager.triggerJob(row.name,row.group);
        }
    });

    $("#btn_delete").click(function(){
        var rows = $('#scheduler_list_table').bootstrapTable("getSelections");
        if(rows.length>0){
           var row = rows[0];
           jobManager.delJob(row.name,row.group);
        }
    });


    $("#btn_restart").click(function(){
        var rows = $('#scheduler_list_table').bootstrapTable("getSelections");
        if(rows.length>0){
           var row = rows[0];
           jobManager.restartJob(row.name,row.group);
        }
    });

    //提交按钮点击
    $(document).on("click",".btn-submit-scheduler",function(){
          var data = scheduler.getSubmitData();
          jobManager.addJob(data,function(){

          });
    });

});
