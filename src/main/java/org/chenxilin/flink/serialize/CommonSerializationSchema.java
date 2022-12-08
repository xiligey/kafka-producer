package org.chenxilin.flink.serialize;

import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;

/**
 * @author chenxilin
 */
@Data
public class CommonSerializationSchema<T> implements KafkaSerializationSchema<T> {

    private final ObjectMapper mapper;
    private String topic;

    public CommonSerializationSchema(String topic) {
        this.mapper = new ObjectMapper();
        this.topic = topic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(T message, @Nullable Long timestamp) {
        try {
            return new ProducerRecord<>(topic, mapper.writeValueAsBytes(message));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Can not serialize record: " + message, e);
        }
    }
}
