/**
 * 提交scheduler数据
 */
(function(win, $) {

    var scheduler = {};

    scheduler.checkSubmit = function() {
        return false;
    };

    scheduler.getSubmitData = function() {
        var formdata = {};
        formdata.name = $("#name").val();
        formdata.group = $("#group").val();
        formdata.cron = $("#cronExpression").val();
        formdata.className = $("#className").val();
        formdata.crawlerType = $("#crawlerType").val();
        formdata.listNodeSelector = $("#listNodeSelector").val();
        formdata.startUrl = $("#startUrl").val();
        if(formdata.crawlerType && formdata.crawlerType==2){
            //区域列表需要设置区域块样式
            if(!formdata.ListNodeSelector ){

            }
        }
        //字段解析设置
        var attributes = new Array();
        $(".row_attribute").each(function(){
            var attribute = {};
            var $row = $(this);
            attribute.name = $row.find("td:first input").val();
            attribute.selectType = $row.find("td:eq(1) select").val();
            attribute.selector = $row.find("td:eq(2) input").val();
            attribute.selectMethod = $row.find("td:eq(3) select").val();

            if(!attribute.name || !attribute.selectType || !attribute.selector || !attribute.selectMethod ){
                return false;
            }
            attributes.push(attribute);
        });
        if(attributes.length > 0){
          formdata.attributes = attributes;
        }else{
          //解析字段必须设置
          return false;
        }
        return formdata;
    };

    win.scheduler = scheduler;

})(window, jQuery);


$(function(){

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

  //提交按钮点击
  $(document).on("click",".btn-submit-scheduler",function(){
        var $form = $(this).parents("form");
        if($form.data("update")){
          
        }else{
          var data = scheduler.getSubmitData();
          jobManager.addJob(data,function(){
              refreshTable();
          });
        }
  });

});
