package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final PersonService personService;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "person-address-topic", groupId = "personal-consumer-tracker-api-group")
    public void consumerPersonDto(PersonDto personDto) {
        personService.processPerson(personDto);
        LOGGER.info("Received payload {}", personDto.toString());
    }

}
