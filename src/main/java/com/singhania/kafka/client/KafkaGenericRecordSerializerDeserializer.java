//package com.singhania.kafka.client;
//
//import java.util.Map;
//
//import org.apache.kafka.common.serialization.Deserializer;
//import org.apache.kafka.common.serialization.Serializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.ByteBufferOutput;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
//import com.esotericsoftware.kryo.serializers.FieldSerializer;
//
//public class KafkaFoiRequestSerializerDeserializer implements Serializer<KafkaFoiRequest>, Deserializer<KafkaFoiRequest> {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaFoiRequestSerializerDeserializer.class);
//
//	@SuppressWarnings("rawtypes")
//	protected ThreadLocal kryos = new ThreadLocal() {
//		@Override
//		protected Kryo initialValue() {
//			Kryo kryo = new Kryo();
//			kryo.getFieldSerializerConfig()
//					.setCachedFieldNameStrategy(FieldSerializer.CachedFieldNameStrategy.EXTENDED);
//			CompatibleFieldSerializer serializer = new CompatibleFieldSerializer(kryo, KafkaFoiRequest.class);
//			kryo.register(KafkaFoiRequest.class, serializer);
//			return kryo;
//		}
//	};
//
//	@Override
//	public byte[] serialize(String arg0, KafkaFoiRequest req) {
//		try {
//			int initSize = 1024;
//			Output output = new ByteBufferOutput(initSize, initSize * 64); // Setting buffer size 1-64KB
//			((Kryo) kryos.get()).writeObject(output, req);
//			output.flush();
//			output.close();
//			return output.toBytes();
//		} catch (Exception e) {
//			LOGGER.error("Error reading bytes {}", req);
//			throw new RuntimeException("Unable to serialize input data ", e);
//		}
//	}
//
//	@Override
//	public KafkaFoiRequest deserialize(String arg0, byte[] bytes) {
//		try {
//			Input input = new Input(bytes);
//			KafkaFoiRequest req = ((Kryo) kryos.get()).readObject(input, KafkaFoiRequest.class);
//			input.close();
//			return req;
//		} catch (Exception e) {
//			LOGGER.error("Error reading bytes {}", bytes);
//			throw new IllegalArgumentException("Error reading bytes", e);
//		}
//	}
//
//	@Override
//	public void close() {
//		/**
//		 *
//		 * Auto-generated method stub
//		 */
//	}
//
//	@Override
//	public void configure(Map<String, ?> arg0, boolean arg1) {
//		/**
//		 *
//		 * Auto-generated method stub
//		 */
//	}
//}
