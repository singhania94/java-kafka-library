package com.citi.ace;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.record.RecordBatch;
import org.apache.kafka.common.requests.ProduceResponse;

public class AceKafkaProducerRecordMetadata {

    public static final int UNKNOWN_PARTITION = -1;

    private final long offset;
    private final long timestamp;
    private final int serializedKeySize;
    private final int serializedValueSize;
    private final String topic;
    private final int partition;

    public AceKafkaProducerRecordMetadata(RecordMetadata metadata) {
        this.offset = metadata.offset();
        this.timestamp = metadata.timestamp();
        this.serializedKeySize = metadata.serializedKeySize();
        this.serializedValueSize = metadata.serializedValueSize();
        this.topic = metadata.topic();
        this.partition = metadata.partition();
    }

    /**
     * Indicates whether the record metadata includes the offset.
     * @return true if the offset is included in the metadata, false otherwise.
     */
    public boolean hasOffset() {
        return this.offset != ProduceResponse.INVALID_OFFSET;
    }

    /**
     * The offset of the record in the topic/partition.
     * @return the offset of the record, or -1 if {{@link #hasOffset()}} returns false.
     */
    public long offset() {
        return this.offset;
    }

    /**
     * Indicates whether the record metadata includes the timestamp.
     * @return true if a valid timestamp exists, false otherwise.
     */
    public boolean hasTimestamp() {
        return this.timestamp != RecordBatch.NO_TIMESTAMP;
    }

    /**
     * The timestamp of the record in the topic/partition.
     *
     * @return the timestamp of the record, or -1 if the {{@link #hasTimestamp()}} returns false.
     */
    public long timestamp() {
        return this.timestamp;
    }

    /**
     * The size of the serialized, uncompressed key in bytes. If key is null, the returned size
     * is -1.
     */
    public int serializedKeySize() {
        return this.serializedKeySize;
    }

    /**
     * The size of the serialized, uncompressed value in bytes. If value is null, the returned
     * size is -1.
     */
    public int serializedValueSize() {
        return this.serializedValueSize;
    }

    /**
     * The topic the record was appended to
     */
    public String topic() {
        return this.topic;
    }

    /**
     * The partition the record was sent to
     */
    public int partition() {
        return this.partition;
    }

	@Override
	public String toString() {
		return "AceKafkaProducerRecordMetadata [offset=" + offset + ", timestamp=" + timestamp + ", topic=" + topic
				+ ", partition=" + partition + "]";
	}
}
