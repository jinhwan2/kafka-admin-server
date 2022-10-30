package kafkakru.admin.controller;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.CreateTopicRequest;
import kafkakru.admin.dto.Topic;
import kafkakru.admin.model.HttpResponse;
import kafkakru.admin.service.TopicService;

@V1Version
@RestController
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("topics")
    boolean createTopic(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {
        return this.topicService.createTopic(createTopicRequest);
    }

    @GetMapping("topics")
    HttpResponse<Set<Topic>> getTopics() throws ExecutionException, InterruptedException {
        return HttpResponse.success(
            this.topicService.getTopics()
        );
    }

    @GetMapping("topics/{topicName}")
    Topic getTopic(@PathVariable  String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.getTopic(topicName);
    }

    @DeleteMapping("topics/{topicName}")
    boolean deleteTopic(@PathVariable String topicName) {
        return this.topicService.deleteTopic(topicName);
    }
}
