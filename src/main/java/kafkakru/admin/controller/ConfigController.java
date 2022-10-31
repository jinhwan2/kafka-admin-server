package kafkakru.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.Pair;
import kafkakru.admin.service.ConfigService;

@V1Version
@RestController
@RequiredArgsConstructor
public class ConfigController {
    private final ConfigService configService;

    @GetMapping("/brokers")
    Map<String, List<Pair<String, String>>> getBrokerConfigs() throws ExecutionException, InterruptedException {
        return this.configService.getBrokerConfigs();
    };
}
