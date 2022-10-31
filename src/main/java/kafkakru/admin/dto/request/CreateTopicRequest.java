package kafkakru.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CreateTopicRequest {
    String topicName;
    int numPartitions;
    Integer replicationFactor;
}
