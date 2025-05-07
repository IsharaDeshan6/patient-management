package com.pm.patientservice.kafka;

import billing.events.BillingAccountEvent;
import com.pm.patientservice.entity.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public void sendEvent(Patient patient){

        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            kafkaTemplate.send("patient",patientEvent.toByteArray());

        }catch (Exception e){
            log.error("Error sending PatientCreated event to Kafka: {}", patientEvent);
        }

    }

    public void sendBillingAccountEvent(String patientId, String name, String email) {

        BillingAccountEvent event = BillingAccountEvent.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .setEventType("BILLING_ACCOUNT_CREATE_REQUESTED")
                .build();

        try {
            kafkaTemplate.send("billing-account",event.toByteArray());
            log.info("Sent BillingAccount event to Kafka: {}", event);
        }catch (Exception e){
            log.error("Error sending BillingAccount event to Kafka: {}", event);
        }

    }
}
