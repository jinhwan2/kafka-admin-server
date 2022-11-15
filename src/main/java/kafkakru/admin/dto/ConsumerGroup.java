package kafkakru.admin.dto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.MemberDescription;
import org.apache.kafka.common.ConsumerGroupState;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.acl.AclOperation;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConsumerGroup {
    String groupId;
    boolean isSimpleConsumerGroup;
    Collection<Member> members;
    String partitionAssignor;
    ConsumerGroupState state;
    Node coordinator;
    Set<AclOperation> authorizedOperations;

    public static ConsumerGroup from(ConsumerGroupDescription consumerGroupDescription) {
        List<Member> convertMembers = consumerGroupDescription.members().stream()
            .map(Member::from)
            .collect(Collectors.toList());

        return ConsumerGroup.builder()
            .groupId(consumerGroupDescription.groupId())
            .isSimpleConsumerGroup(consumerGroupDescription.isSimpleConsumerGroup())
            .members(convertMembers)
            .state(consumerGroupDescription.state())
            .coordinator(consumerGroupDescription.coordinator())
            .authorizedOperations(consumerGroupDescription.authorizedOperations())
            .build();
    }

    @Value
    @Builder
    private static class Member {
        String memberId;
        String groupInstanceId;
        String clientId;
        String host;
        MemberAssignment assignment;

        public static Member from(MemberDescription memberDescription) {
            return Member.builder()
                .memberId(memberDescription.consumerId())
                .groupInstanceId(memberDescription.groupInstanceId().orElse(null))
                .clientId(memberDescription.clientId())
                .host(memberDescription.host())
                .assignment(new MemberAssignment(memberDescription.assignment().topicPartitions()))
                .build();
        }
    }

    @Value
    private static class MemberAssignment {
        Set<String> topicPartitions;

        public MemberAssignment(Set<TopicPartition> topicPartitions) {
            this.topicPartitions = topicPartitions.stream()
                .map(TopicPartition::toString)
                .collect(Collectors.toSet());
        }
    }
}
