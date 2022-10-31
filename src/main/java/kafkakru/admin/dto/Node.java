package kafkakru.admin.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Node {
    int id;
    String host;
    String rack;

    public static Node from(org.apache.kafka.common.Node node) {
        return Node.builder()
            .id(node.id())
            .host(node.host() + ":" + node.port())
            .rack(node.rack())
            .build();
    }
}
