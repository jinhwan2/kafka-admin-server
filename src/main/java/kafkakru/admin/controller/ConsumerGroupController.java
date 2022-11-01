package kafkakru.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.common.TopicPartition;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.ConsumerGroup;
import kafkakru.admin.dto.ConsumerGroupId;
import kafkakru.admin.dto.OffSetAndMetadata;
import kafkakru.admin.dto.request.OffsetChangeRequest;
import kafkakru.admin.service.ConsumerGroupService;

@V1Version
@RestController
@RequiredArgsConstructor
public class ConsumerGroupController {
    private final ConsumerGroupService consumerGroupService;

    @GetMapping("consumer-group-ids")
    List<ConsumerGroupId> getConsumerGroupIds() throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getAll();
    }

    @GetMapping("consumer-groups/{groupId}")
    ConsumerGroup getConsumerGroupInfo(@PathVariable String groupId) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(List.of(groupId))
            .get(groupId);
    }

    @GetMapping("consumer-groups")
    Map<String, ConsumerGroup> getConsumerGroupInfos(@RequestParam List<String> groupIds) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(groupIds);
    }

    @GetMapping("consumer-groups/{groupId}/offsets")
    List<Map<TopicPartition, OffSetAndMetadata>> getConsumerGroupOffsets(@PathVariable  String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getOffset(groupId);
    }

    @DeleteMapping("consumer-groups/{groupId}")
    boolean deleteConsumerGroup(@PathVariable String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.delete(groupId);
    }

    @PutMapping("consumer-groups/{groupId}")
    boolean updateConsumerGroupOffsetAndMetadata(@PathVariable String groupId, @RequestBody OffsetChangeRequest offsetChangeRequest) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.updateOffsetAndMetadata(groupId, offsetChangeRequest);
    }
}
