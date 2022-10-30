package kafkakru.admin.controller;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.Topic;
import kafkakru.admin.model.HttpResponse;
import kafkakru.admin.service.TopicService;

@V1Version
@RestController
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping("topics")
    HttpResponse<Set<Topic>> getTopics() throws ExecutionException, InterruptedException {
        return HttpResponse.success(
            this.topicService.getTopics()
        );
    }
}
