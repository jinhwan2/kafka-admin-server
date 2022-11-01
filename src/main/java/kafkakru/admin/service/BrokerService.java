package kafkakru.admin.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import kafkakru.admin.dto.Entry;
import kafkakru.admin.dto.Node;
import kafkakru.admin.dto.request.ConfigModifyRequest;

@Service
public class BrokerService extends AbstractKafkaAdminClientService {
    private final ClusterService clusterService;
    private final ConfigService configService;
    private static final ConfigResource.Type RESOURCE_TYPE = ConfigResource.Type.BROKER;

    public BrokerService(AdminClient kafkaAdminClient, ClusterService clusterService,ConfigService configService) {
        super(kafkaAdminClient);
        this.clusterService = clusterService;
        this.configService = configService;
    }

    public Map<String, List<Entry<String, String>>> getAllConfigs() throws ExecutionException, InterruptedException {
        List<ConfigResource> configResources = clusterService.getNodes().stream()
            .map(Node::getId)
            .map(it -> new ConfigResource(RESOURCE_TYPE, it))
            .collect(Collectors.toList());

        return this.configService.getConfigs(configResources);
    }

    public List<Entry<String, String>> getConfig(String nodeId) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, nodeId);
        return this.configService.getConfig(configResource);
    }

    public boolean updateConfig(String nodeId, ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, nodeId);
        return this.configService.update(configResource, configModifyRequest.getConfig());
    }
}
