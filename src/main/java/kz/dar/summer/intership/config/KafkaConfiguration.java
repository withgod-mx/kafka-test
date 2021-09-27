package kz.dar.summer.intership.config;

import kz.dar.summer.intership.model.HelloWorld;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("summer-intership-test")
                .partitions(10)
                .replicas(1)
                .build();
    }


    public ConsumerFactory<String, HelloWorld> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hello-world");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(HelloWorld.class));
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, HelloWorld> kafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, HelloWorld> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(id = "test", topics = "summer-intership-test", containerFactory = "kafkaJsonListenerContainerFactory")
    public void listen(HelloWorld hello) {
        System.out.println(hello);
    }





}
