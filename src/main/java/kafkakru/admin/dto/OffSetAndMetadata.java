package kafkakru.admin.dto;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OffSetAndMetadata {
    long offset;
    String metadata;
    Integer leaderEpoch; // use null to represent the absence of a leader

    public static OffSetAndMetadata from(OffsetAndMetadata offsetAndMetadata) {
        return OffSetAndMetadata.builder()
            .offset(offsetAndMetadata.offset())
            .metadata(offsetAndMetadata.metadata())
            .leaderEpoch(offsetAndMetadata.leaderEpoch().orElse(null))
            .build();
    }
}
