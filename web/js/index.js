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
            },{
                title: "操作",
                formatter:function(value,row,index){
                  var view = '<a href="showdata.html?name='+row.name+'&group='+row.group+'" target="_blank">查看数据</a>';
                  var edit = '<a href="#pop_scheduler" class="btn-fancybox-update"  data-type="update" data-name="'+row.name+'" data-group="'+row.group+'" style="margin-left:30px;">编辑</a>';
                  return view+edit;
                }
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

    function refreshTable(){
        jobManager.list(null, function(data) {
            $('#scheduler_list_table').bootstrapTable("load",data);
        });
    }

    $("#btn_stop").click(function(){
        var rows = $('#scheduler_list_table').bootstrapTable("getSelections");
        if(rows.length>0){
           var row = rows[0];
           jobManager.stopJob(row.name,row.group,function(){
              refreshTable();
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
           refreshTable();
        }
    });

    //弹出层
    $(".btn-fancybox-add").fancybox({
          beforeShow:function(){
              var $pop = $($(this).attr("href"));
              $pop.find("h1").text("新增采集任务");
          },
          afterClose:function(){
              var $form =  $("form[name='form_scheduler']");
              $form[0].reset();
          }
    });

  //编辑弹出层
    $(".btn-fancybox-update").fancybox({
          beforeShow:function(){
              var $pop = $($(this).attr("href"));
              $pop.find("h1").text("修改采集任务");
              $pop.find("form").data("update",true);
          },
          afterClose:function(){
              var $form =  $("form[name='form_scheduler']");
              $form.removeData("update");
              $form[0].reset();
          }
    });

    function showEditJob(name,group){

    };

});
