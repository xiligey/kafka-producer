package org.chenxilin.flink.sink;

import lombok.Data;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.chenxilin.flink.serialize.CommonSerializationSchema;

/**
 * @author chenxilin
 */
@Data
public class KafkaSink<IN> {

    private KafkaSinkConfig kafkaSinkConfig;
    private KafkaSerializationSchema<IN> serializer;

    public KafkaSink(KafkaSinkConfig config, KafkaSerializationSchema<IN> serializer) {
        this.kafkaSinkConfig = config;
        this.serializer = serializer;
    }

    public KafkaSink(KafkaSinkConfig config) {
        this.kafkaSinkConfig = config;
        this.serializer = new CommonSerializationSchema<>(config.getTopic());
    }

    public FlinkKafkaProducer<IN> buildProducer() {
        return new FlinkKafkaProducer<>(
                kafkaSinkConfig.getTopic(),
                serializer,
                kafkaSinkConfig.toProperties(),
                FlinkKafkaProducer.Semantic.EXACTLY_ONCE
        );
    }
}
