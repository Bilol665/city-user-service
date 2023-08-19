package uz.pdp.cityuserservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import uz.pdp.cityuserservice.domain.dto.MailDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topic;
    //private final KafkaTemplate<MailDto, String> kafkaTemplate;
    private final KafkaTemplate<String,String>kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void produceMessage(MailDto mailDto) {
//        log.info("Sending message to kafka -> {}", mailDto);
//        Message<MailDto> message = MessageBuilder
//                .withPayload(mailDto)
//                .setHeader(KafkaHeaders.TOPIC, topic)
//                .build();
//        CompletableFuture<SendResult<MailDto, String>> completableFuture = kafkaTemplate.send(message);
//        try {
//            SendResult<MailDto, String> mailDtoSendResult = completableFuture.get();
//            ProducerRecord<MailDto, String> producerRecord = mailDtoSendResult.getProducerRecord();
//            log.info("Producer record info -> {}", producerRecord);
//            RecordMetadata recordMetadata = mailDtoSendResult.getRecordMetadata();
//            log.info("Record Metadata -> {}", recordMetadata);
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
        try {
            String json=objectMapper.writeValueAsString(mailDto);
            kafkaTemplate.send(topic,json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
