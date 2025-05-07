package com.pm.billingservice.kafka;

import billing.events.BillingAccountEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "billing-account", groupId = "billing-service")
    public void consumeEvent(byte[] event){
        // Deserialize the event
        try {
            BillingAccountEvent billingAccountEvent = BillingAccountEvent.parseFrom(event);
            log.info("Received billing account event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    billingAccountEvent.getPatientId(),
                    billingAccountEvent.getName(),
                    billingAccountEvent.getEmail());

            //check if the patients billing account doesn't exist, if not then create it

        } catch (InvalidProtocolBufferException e) {
          log.error("Error deserializing BillingAccountEvent: {}", e.getMessage());
        }
    }
}
