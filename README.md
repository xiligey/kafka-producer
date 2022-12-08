# Kafka推数工具

## 1.准备Flink1.12.1
## 2.准备三个文件

1)任务配置文件
```properties
############# kafka produce tool 配置 #############
# 本任务的并行度
job.kafka.producer.parallelism=1

# kafka地址 多个地址用,隔开
kafka.producer.bootstrap.servers=127.0.0.1:9092

# 数据落到哪个kafka topic
kafka.producer.topic=example

# 读取json文件的数据(文件每行为一个json)，将每一行作为一条记录推送到kafka
kafka.producer.jsonfile=example.json

# 推数据方式：
#    - cycle: 读取json文件内容并循环推送数据（默认方式）
#    - once: 读取json文件内容并只推送一次
kafka.producer.style=cycle

# 推数据速度
# 每秒推送几条数据（默认-1，不限速，能推多快推多快）
kafka.producer.rate=-1

# 是否需要将推送时间作为一个字段推送到kafka
kafka.producer.time.add=false

# 时间key值
kafka.producer.time.key=ingestTime

# 时间格式
kafka.producer.time.format=yyyy/MM/dd HH:mm:ss.SSS

# 自定义额外字段（是否需要额外推送字段），value需带双引号（目前仅支持字符串字段）
# 例子：kafka.producer.additionalFields={"key1": "value1", "key2": "value2"}
kafka.producer.additionalFields={}
```
2）json文件（要推到kafka的数据，一行一个json）

```json
{"taskId":"001","type":1,"taskSize":3,"subTaskId":"001000","subTaskIndex":0,"createTime":16356881000,"deliveryTime":16356881000,"algoParams":{"dataBeforeStart":1635500304000,"changeStartTime":1635500304000,"changeEndTime":1635500304000,"dataAfterEnd":1635500304000},"algoData":{"metadata":{"description":"this is a subtask of test task"},"dataBefore":{"timestamps":[1,2,3,4,5],"values":[6,7,8,9,10]},"dataAfter":{"timestamps":[1,2,3,4,5],"values":[6,7,8,9,10]}}}
{"taskId":"001","type":1,"taskSize":3,"subTaskId":"001000","subTaskIndex":0,"createTime":16356881000,"deliveryTime":16356881000,"algoParams":{"dataBeforeStart":1635500304000,"changeStartTime":1635500304000,"changeEndTime":1635500304000,"dataAfterEnd":1635500304000},"algoData":{"metadata":{"description":"this is a subtask of test task"},"dataBefore":{"timestamps":[1,2,3,4,5],"values":[6,7,8,9,10]},"dataAfter":{"timestamps":[1,2,3,4,5],"values":[6,7,8,9,10]}}}
```

3）打好的jar包：flink-1.0-SNAPSHOT.jar

## 3.运行flink任务
```shell
flink run -c org.chenxilin.Main kakfa-producer-1.0.jar example.properties 127.0.0.1:9000
```