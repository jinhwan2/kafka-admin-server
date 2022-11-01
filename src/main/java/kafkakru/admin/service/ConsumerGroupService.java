package kafkakru.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import kafkakru.admin.dto.ConsumerGroup;
import kafkakru.admin.dto.ConsumerGroupId;
import kafkakru.admin.dto.OffSetAndMetadata;
import kafkakru.admin.dto.request.OffsetChangeRequest;

@Service
public class ConsumerGroupService extends AbstractKafkaAdminClientService {
    public ConsumerGroupService(AdminClient kafkaAdminClient) {
        super(kafkaAdminClient);
    }

    public List<ConsumerGroupId> getAll() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listConsumerGroups()
            .all()
            .thenApply(it -> it.stream()
                .map(ConsumerGroupId::from)
                .collect(Collectors.toList()))
            .get();
    }

    public Map<String, ConsumerGroup> get(List<String> groupIds) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeConsumerGroups(groupIds)
            .all()
            .thenApply(it -> it.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e ->  ConsumerGroup.from(e.getValue()))))
            .get();
    }

    public List<Map<TopicPartition, OffSetAndMetadata>> getOffset(String groupId) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listConsumerGroupOffsets(groupId)
            .all()
            .thenApply(it -> it.values().stream()
                .map(Map::entrySet)
                .map(entries -> entries.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> OffSetAndMetadata.from(e.getValue()))))
                .collect(Collectors.toList()))
            .get();
    }


    public boolean updateOffsetAndMetadata(String groupId, OffsetChangeRequest offsetChangeRequest) throws ExecutionException, InterruptedException {
        Map<TopicPartition, OffsetAndMetadata> offsets = offsetChangeRequest.getOffsets().entrySet().stream()
            .collect(Collectors.toMap(it -> this.getTopicPartition(it.getKey()), it -> this.convertOffsetAndMetadata(it.getValue())));

        this.kafkaAdminClient.alterConsumerGroupOffsets(groupId, offsets)
            .all()
            .get();

        return true;
    }

    private TopicPartition getTopicPartition(String topicPartitionString) {
        final String separator = "-";
        String[] splitKeys = topicPartitionString.split(separator);
        return new TopicPartition(splitKeys[0], Integer.parseInt(splitKeys[1]));
    }

    private OffsetAndMetadata convertOffsetAndMetadata(OffSetAndMetadata offSetAndMetadata) {
        return new OffsetAndMetadata(offSetAndMetadata.getOffset(),
            Optional.ofNullable(offSetAndMetadata.getLeaderEpoch()),
            offSetAndMetadata.getMetadata());
    }

    public boolean delete(String groupIds) throws ExecutionException, InterruptedException {
        this.kafkaAdminClient.deleteConsumerGroups(List.of(groupIds))
            .all()
            .get();

        return true;
    }
}
