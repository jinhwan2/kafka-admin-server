package kafkakru.admin.service;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.dto.Topic;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final AdminClient kafkaAdminClient;

    public Set<Topic> getTopics() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listTopics()
            .listings()
            .thenApply(it -> it.stream()
                .map(topic -> new Topic(topic.name(), topic.topicId().toString()))
                .collect(Collectors.toSet())
            ).get();
    }
}
