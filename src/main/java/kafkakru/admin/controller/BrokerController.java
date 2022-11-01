package kafkakru.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.Entry;
import kafkakru.admin.dto.request.ConfigModifyRequest;
import kafkakru.admin.service.BrokerService;

@V1Version
@RestController
@RequiredArgsConstructor
public class BrokerController {
    private final BrokerService brokerService;

    @GetMapping("/brokers")
    Map<String, List<Entry<String, String>>> getBrokerConfigs() throws ExecutionException, InterruptedException {
        return this.brokerService.getAllConfigs();
    }

    @GetMapping("/brokers/{nodeId}")
    List<Entry<String, String>> getBrokerConfig(@PathVariable String nodeId) throws ExecutionException, InterruptedException {
        return this.brokerService.getConfig(nodeId);
    }

    @PutMapping("/brokers/{nodeId}")
    boolean updateBrokerConfig(@PathVariable String nodeId, @RequestBody ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        return this.brokerService.updateConfig(nodeId, configModifyRequest);
    }
}
