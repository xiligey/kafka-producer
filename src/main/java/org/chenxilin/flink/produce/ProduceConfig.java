package org.chenxilin.flink.produce;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Properties;

import static org.chenxilin.consts.KafkaConst.*;


/**
 * 常量
 *
 * @author chenxilin
 */
@Data
@Accessors(chain = true)
public class ProduceConfig implements Serializable {
    /**
     * flink任务并行度
     */
    private int jobParallelism = 1;
    /**
     * kafka地址
     */
    private String bootstrapServers = "localhost:9092";
    /**
     * kafka topic
     */
    private String topic = "chenxilin";
    /**
     * json文件路径
     */
    private String jsonFile = "chenxilin.json";
    /**
     * 推送方式，支持cycle（读取json文件内容并循环推送数据）、once（读取json文件内容并只推送一次）
     */
    private String producerStyle = "cycle";
    /**
     * 数据发送速度，每秒推送几条数据（默认-1，不限速，能推多快推多快）
     */
    private int producerRate = -1;
    /**
     * 是否需要将推送时间作为一个字段推送到kafka
     */
    private boolean addTime = false;
    /**
     * 时间key值
     */
    private String timeKey = "ingestTime";
    /**
     * 时间格式
     */
    private String timeFormat = "yyyy/MM/dd HH:mm:ss.SSS";
    /**
     * 自定义额外字段（是否需要额外推送字段），value需带双引号（因为目前仅支持字符串字段）
     * 例子：kafka.producer.additionalFields={"key1": "value1", "key2": "value2"}
     */
    private String additionalFields = "";
    /**
     * 调整字段
     */
    private String adjustField = "";

    /**
     * 构造器——读properties配置
     */
    public ProduceConfig(Properties properties) {
        this.jobParallelism = Integer.parseInt(properties.getOrDefault(PARALLELISM, DEFAULT_PARALLELISM).toString());
        this.bootstrapServers = properties.getProperty(SERVERS, DEFAULT_SERVERS);
        this.topic = properties.getProperty(TOPIC, DEFAULT_TOPIC);
        this.jsonFile = properties.getProperty(JSON_FILE, DEFAULT_JSON_FILE);
        this.producerStyle = properties.getProperty(STYLE, DEFAULT_STYLE);
        this.producerRate = Integer.parseInt(properties.getOrDefault(RATE, DEFAULT_RATE).toString());
        this.addTime = Boolean.parseBoolean(properties.getOrDefault(ADD, DEFAULT_ADD).toString());
        this.timeKey = properties.getProperty(TIME_KEY, DEFAULT_TIME_KEY);
        this.timeFormat = properties.getProperty(TIME_FORMAT, DEFAULT_TIME_FORMAT);
        this.additionalFields = properties.getProperty(ADDITIONAL_FIELDS, DEFAULT_ADDITIONAL_FIELDS);
        this.adjustField = properties.getProperty(ADJUST_FIELD, DEFAULT_ADJUST_FIELD);
    }
}
