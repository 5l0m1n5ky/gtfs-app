package com.gtfsapp.api.config.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.gtfsapp.api.model.dto.VehicleDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer-group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.consumer-auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, VehicleDto> vehicleConsumerFactory() {

        JsonDeserializer<VehicleDto> deserializer = new JsonDeserializer<>(VehicleDto.class);
        deserializer.addTrustedPackages("com.gtfsapp.api.model.dto");

        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        return new DefaultKafkaConsumerFactory<>(configProperties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehicleDto> vehicleKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VehicleDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(vehicleConsumerFactory());
        return factory;
    }
}
