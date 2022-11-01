package kafkakru.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class CreateTopicRequest {
    @NonNull
    String topicName;

    int numPartitions;

    @NonNull
    Integer replicationFactor;
}
