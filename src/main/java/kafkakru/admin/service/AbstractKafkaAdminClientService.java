package kafkakru.admin.service;

import org.apache.kafka.clients.admin.AdminClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractKafkaAdminClientService {
    protected final AdminClient kafkaAdminClient;
}
