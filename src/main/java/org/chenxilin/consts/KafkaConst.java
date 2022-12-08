package org.chenxilin.consts;

/**
 * @author chenxilin
 */
public class KafkaConst {
    public static final String PARALLELISM = "job.kafka.producer.parallelism";
    public static final String SERVERS = "kafka.producer.bootstrap.servers";
    public static final String TOPIC = "kafka.producer.topic";
    public static final String JSON_FILE = "kafka.producer.jsonfile";
    public static final String STYLE = "kafka.producer.style";
    public static final String RATE = "kafka.producer.rate";
    public static final String ADD = "kafka.producer.time.add";
    public static final String TIME_KEY = "kafka.producer.time.key";
    public static final String TIME_FORMAT = "kafka.producer.time.format";
    public static final String ADDITIONAL_FIELDS = "kafka.producer.additionalFields";

    public static final int DEFAULT_PARALLELISM = 1;
    public static final String DEFAULT_SERVERS = "127.0.0.1:9092";
    public static final String DEFAULT_TOPIC = "chenxilin";
    public static final String DEFAULT_JSON_FILE = "chenxilin.json";
    public static final String DEFAULT_STYLE = "cycle";
    public static final String ONCE_STYLE = "once";
    public static final int DEFAULT_RATE = -1;
    public static final boolean DEFAULT_ADD = false;
    public static final String DEFAULT_TIME_KEY = "ingestTime";
    public static final String DEFAULT_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String DEFAULT_ADDITIONAL_FIELDS = "{}";
}
