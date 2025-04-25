package com.gtfsapp.api.config.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.gtfsapp.api.model.dto.AlertDto;
import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.producer-acks}")
    private String acks;

    @Value("${kafka.producer-retries}")
    private String retries;

    @Value("${kafka.producer-linger}")
    private String linger;

    @Value("${kafka.topic.alerts}")
    private String alertTopic;

    @Value("${kafka.topic.vehicles}")
    private String vehicleTopic;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProperties.put(ProducerConfig.ACKS_CONFIG, acks);
        configProperties.put(ProducerConfig.RETRIES_CONFIG, retries);
        configProperties.put(ProducerConfig.LINGER_MS_CONFIG, linger);

        return new DefaultKafkaProducerFactory<>(configProperties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaProducerService<AlertDto> alertKafkaProducer(KafkaTemplate<String, AlertDto> template) {
        return new KafkaProducerService<>(template, alertTopic);
    }

    @Bean
    public KafkaProducerService<VehicleDto> vehicleKafkaProducer(KafkaTemplate<String, VehicleDto> template) {
        return new KafkaProducerService<>(template, vehicleTopic);
    }

}
