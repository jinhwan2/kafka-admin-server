package kafkakru.admin.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.dto.Node;
import kafkakru.admin.dto.Pair;

@Service
@RequiredArgsConstructor
public class ConfigService {
    private final AdminClient kafkaAdminClient;
    private final ClusterService clusterService;

    public Map<String, List<Pair<String, String>>> getBrokerConfigs() throws ExecutionException, InterruptedException {
        List<ConfigResource> configResources = clusterService.getNodes().stream()
            .map(Node::getId)
            .map(it -> new ConfigResource(ConfigResource.Type.BROKER, it))
            .collect(Collectors.toList());

        return kafkaAdminClient.describeConfigs(configResources)
            .all()
            .thenApply(it -> it.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().name(), e -> e.getValue().entries().stream()
                    .map(config -> Pair.of(config.name(), config.value()))
                    .collect(Collectors.toList())))
            ).get();
    }
}
