package org.chenxilin.flink.sink;

import lombok.Data;

import java.util.Properties;

/**
 * Kafka
 *
 * @author chenxilin
 */
@Data
public class KafkaSinkConfig {

    /**
     * kafka topic
     */
    private String topic;
    /**
     * Kafka集群连接的host/port组
     * 格式：host1:port1,host2:port2,…
     * 这些server仅仅是用于初始化的连接，以发现集群所有成员（成员可能会动态的变化）
     * 这个列表不需要包含所有的servers（数量尽量不止一个，以防其中一个down机了）
     */
    private String bootstrapServers;
    /**
     * 指producer需要多少个[server接收到数据之后发出的确认接收的信号]
     * 此配置实际上代表了数据备份的可用性。以下设置为常用选项：
     * （1）acks=0： 设置为0表示producer不需要等待任何确认收到的信息。副本将立即加到socket buffer并认为已经发送。
     * 没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）
     * 回馈的offset会总是设置为-1；
     * （2）acks=1： 这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。
     * 这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
     * （3）acks=all： 这意味着leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。
     */
    private int acks = 0;
    /**
     * 发送失败重试次数，默认为0
     * 当设置 retries > 0 时，如果发送失败，会自动尝试重新发送数据，发送次数为retries设置的值。
     * 如果设定了retries但没有把max.in.flight.requests.per.connection 设置成 1则可能会改变数据的顺序，
     * 因为如果这两个batch都是发送到同一个partition，并且第一个batch发送失败而第二个发送成功，
     * 则第二个batch中的消息记录会比第一个的达到得早。
     */
    private int retries = 0;
    /**
     * 批次大小（一次发送同一个partition多少条数据）
     * 为了改善客户端和Kafka集群的性能，减少请求次数，producer会把要发送到同一个partition的批量消息作为batch发送，
     * batch.size 是用来设置batch的字节大小。如果 batch.size 太小则可能会降低吞吐量，
     * 设置太大则可能会导致浪费，因为我们预先就需要腾出一个batch.size 大小的缓冲区来存贮将要发送达到该缓冲区的消息。
     * 若将该值设为0，则不会进行批处理。
     */
    private int batchSize = 16384;
    /**
     * producer会将request传输之间到达的所有records聚合到一个批请求。通常这个值发生在欠负载情况下，record到达速度快于发送。
     * 但是在某些场景下，client即使在正常负载下也期望减少请求数量。
     * 这个设置就是如此，通过人工添加少量时延，而不是立马发送一个record，producer会等待所给的时延，以让其他records发送出去，
     * 这样就会被聚合在一起。这个类似于TCP的Nagle算法。
     * 该设置给了batch的时延上限：当我们获得一个partition的batch.size大小的records，就会立即发送出去，而不管该设置；
     * 但是如果对于这个partition没有累积到足够的record，会linger指定的时间等待更多的records出现。该设置的默认值为0(无时延)。
     * 例如，设置linger.ms=5，会减少request发送的数量，但是在无负载下会增加5ms的发送时延。
     */
    private int lingerMs = 1;
    /**
     * kafka producer可以用来缓存数据的内存大小。
     * 该值实际为RecordAccumulator类中的BufferPool，即Producer所管理的最大内存。
     * 但不是所有producer管理的内存都用作缓存，一些额外的内存会用于压缩（如果引入压缩机制），同样还有一些用于维护请求。
     * 如果数据产生速度大于向broker发送的速度，producer会阻塞配置项max.block.ms所设定的值，超出这个时间则抛出异常。
     */
    private int bufferMemory = 33554432;
    /**
     * kafka消息的key的序列化类
     */
    private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * kafka消息的value的序列化类
     */
    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";

    public KafkaSinkConfig(String topic, String bootstrapServers) {
        this.topic = topic;
        this.bootstrapServers = bootstrapServers;
    }

    public Properties toProperties() {
        Properties properties = new Properties();
        properties.put("topic", topic);
        properties.put("bootstrap.servers", bootstrapServers);
        return properties;
    }
}
