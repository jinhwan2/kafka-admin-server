package kafkakru.admin.dto;

import org.apache.kafka.clients.admin.ConsumerGroupListing;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConsumerGroupId {
    String groupId;
    boolean simpleConsumerGroup;

    public static ConsumerGroupId from(ConsumerGroupListing consumerGroupListing) {
        return ConsumerGroupId.builder()
            .groupId(consumerGroupListing.groupId())
            .simpleConsumerGroup(consumerGroupListing.isSimpleConsumerGroup())
            .build();
    }
}
