package kafkakru.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import kafkakru.admin.dto.Entry;
import kafkakru.admin.dto.Topic;
import kafkakru.admin.dto.request.ConfigModifyRequest;
import kafkakru.admin.dto.request.CreateTopicRequest;

@Service
public class TopicService extends AbstractKafkaAdminClientService{
    private final ConfigService configService;
    private static final ConfigResource.Type RESOURCE_TYPE = ConfigResource.Type.TOPIC;

    public TopicService(AdminClient kafkaAdminClient, ConfigService configService) {
        super(kafkaAdminClient);
        this.configService = configService;
    }

    public boolean create(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(createTopicRequest.getTopicName(), createTopicRequest.getNumPartitions(), createTopicRequest.getReplicationFactor().shortValue());

        this.kafkaAdminClient.createTopics(List.of(newTopic))
            .all()
            .get();

        return true;
    }

    public Set<String> getAll() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listTopics()
            .listings()
            .thenApply(it -> it.stream()
                .map(TopicListing::name)
                .collect(Collectors.toSet())
            ).get();
    }

    public Map<String, Topic> get(List<String> topicNames) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeTopics(topicNames)
            .allTopicNames()
            .thenApply(it -> it.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    TopicDescription topic = e.getValue();
                    return new Topic(topic.name(), topic.topicId().toString());
                }))
            ).get();

    }

    public boolean delete(String topicName) throws ExecutionException, InterruptedException {
        this.kafkaAdminClient.deleteTopics(List.of(topicName))
            .all()
            .get();

        return true;
    }

    public List<Entry<String, String>> getConfig(String topicName) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, topicName);
        return this.configService.getConfig(configResource);
    }

    public boolean updateConfig(String topicName, ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, topicName);
        return this.configService.update(configResource, configModifyRequest.getConfig());
    }
}
