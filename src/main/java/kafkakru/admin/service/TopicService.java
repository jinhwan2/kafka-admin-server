package kafkakru.admin.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.dto.CreateTopicRequest;
import kafkakru.admin.dto.Topic;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final AdminClient kafkaAdminClient;

    public boolean createTopic(CreateTopicRequest createTopicRequest) {
        NewTopic newTopic = new NewTopic(createTopicRequest.getTopicName(), createTopicRequest.getNumPartitions(), createTopicRequest.getReplicationFactor().shortValue());

        return this.kafkaAdminClient.createTopics(List.of(newTopic))
            .all()
            .isDone();
    }

    public Set<Topic> getTopics() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listTopics()
            .listings()
            .thenApply(it -> it.stream()
                .map(topic -> new Topic(topic.name(), topic.topicId().toString()))
                .collect(Collectors.toSet())
            ).get();
    }

    public Topic getTopic(String topicName) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeTopics(List.of(topicName))
            .allTopicNames()
            .thenApply(it -> {
                TopicDescription topic = it.get(topicName);
                return new Topic(topic.name(), topic.topicId().toString());
            }).get();
    }

    public boolean deleteTopic(String topicName) {
        return this.kafkaAdminClient.deleteTopics(List.of(topicName))
            .all()
            .isDone();
    }
}
