package kafkakru.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.Entry;
import kafkakru.admin.dto.Topic;
import kafkakru.admin.dto.request.ConfigModifyRequest;
import kafkakru.admin.dto.request.CreateTopicRequest;
import kafkakru.admin.model.HttpResponse;
import kafkakru.admin.service.TopicService;

@V1Version
@RestController
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("topics")
    boolean createTopic(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {
        return this.topicService.create(createTopicRequest);
    }

    @GetMapping("topics")
    HttpResponse<Set<Topic>> getTopics() throws ExecutionException, InterruptedException {
        return HttpResponse.success(
            this.topicService.get()
        );
    }

    @GetMapping("topics/{topicName}")
    Topic getTopic(@PathVariable  String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.getAll(topicName);
    }

    @DeleteMapping("topics/{topicName}")
    boolean deleteTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.delete(topicName);
    }

    @GetMapping("topic-configs")
    Map<String, List<Entry<String, String>>> getTopicConfigs() throws ExecutionException, InterruptedException {
        return this.topicService.getAllConfigs();
    }

    @GetMapping("topic-configs/{topicName}")
    List<Entry<String, String>> getTopicConfig(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.getConfig(topicName);
    }

    @PutMapping("/topics/{topicName}")
    boolean updateBrokerConfig(@PathVariable String topicName, ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        return this.topicService.updateConfig(topicName, configModifyRequest);
    }
}
