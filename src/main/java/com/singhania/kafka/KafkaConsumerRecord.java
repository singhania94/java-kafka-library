package com.singhania.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.RecordBatch;

public class AceKafkaConsumerRecord<K, V> {
	public static final long NO_TIMESTAMP = RecordBatch.NO_TIMESTAMP;
    public static final int NULL_SIZE = -1;
    public static final int NULL_CHECKSUM = -1;

    private final String topic;
    private final int partition;
    private final long offset;
    private final long timestamp;
    private final int serializedKeySize;
    private final int serializedValueSize;
    private final K key;
    private final V value;

	public AceKafkaConsumerRecord(ConsumerRecord<K, V> record) {
        this.topic = record.topic();
        this.partition = record.partition();
        this.offset = record.offset();
        this.timestamp = record.timestamp();
        this.serializedKeySize = record.serializedKeySize();
        this.serializedValueSize = record.serializedValueSize();
        this.key = record.key();
        this.value = record.value();

    }

    /**
     * The topic this record is received from
     */
    public String topic() {
        return this.topic;
    }

    /**
     * The partition from which this record is received
     */
    public int partition() {
        return this.partition;
    }

    /**
     * The key (or null if no key is specified)
     */
    public K key() {
        return key;
    }

    /**
     * The value
     */
    public V value() {
        return value;
    }

    /**
     * The position of this record in the corresponding Kafka partition.
     */
    public long offset() {
        return offset;
    }

    /**
     * The timestamp of this record
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * The size of the serialized, uncompressed key in bytes. If key is null, the returned size
     * is -1.
     */
    public int serializedKeySize() {
        return this.serializedKeySize;
    }

    /**
     * The size of the serialized, uncompressed value in bytes. If value is null, the
     * returned size is -1.
     */
    public int serializedValueSize() {
        return this.serializedValueSize;
    }

    @Override
    public String toString() {
        return "AceKafkaRecord(topic = " + topic() + ", partition = " + partition() + ", offset = " + offset()
        	   + " = " + timestamp
               + ", serialized key size = "  + serializedKeySize
               + ", serialized value size = " + serializedValueSize
               + ", key = " + key + ", value = " + value + ")";
    }
}
