# spider-scheduler-service #
## 说明 ##
基于webmagic-core爬取数据，基于quartz持久化存储，采用dubbox提供rest服务的，定时计划任务
TODO：数据存储（ES或mysqk），数据导入


## 技术栈 ##
1. webmagic-core
2. quartz
3. dubbox
4. mysql或ES


## 目前功能说明 ##
1. 采集列表结构化数据
2. 采集详细页面结构化数据
3. 编辑采集任务

## 功能截图 ##
1. 首页
	![](https://github.com/chengchengInc/spider-scheduler-service/blob/master/web/img/index.png?raw=true)
2. 新增任务
	![](https://raw.githubusercontent.com/chengchengInc/spider-scheduler-service/master/web/img/addtask.png)
3. 查看爬去数据
	![](https://github.com/chengchengInc/spider-scheduler-service/blob/master/web/img/viewdata.png?raw=true) 



## Next TODO ##
1. 接口数据爬取
2. 支持Ajax（基于selenium）渲染数据爬取
3. 扩展支持模拟登录


## 问题反馈与建议 ##
email:chengchenginc@gmail.com
