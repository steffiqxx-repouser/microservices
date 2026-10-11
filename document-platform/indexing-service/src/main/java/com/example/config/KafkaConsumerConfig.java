package com.example.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.messaging.event.DocumentUploadedEvent;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    ConsumerFactory<String, DocumentUploadedEvent> documentUploadedConsumerFactory(
            KafkaProperties kafkaProperties
    ) {

        var jsonDeserializer =
                new JsonDeserializer<>(DocumentUploadedEvent.class);

        jsonDeserializer.addTrustedPackages(
                "com.example.indexing.messaging.event"
        );

        return new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DocumentUploadedEvent>
    documentUploadedKafkaListenerContainerFactory(
            ConsumerFactory<String, DocumentUploadedEvent> consumerFactory
    ) {

        var factory =
                new ConcurrentKafkaListenerContainerFactory<
                        String,
                        DocumentUploadedEvent>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
