package kafkakru.admin.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import kafkakru.admin.dto.Entry;

/**
 * package 내부에서만 사용하는 서비스
 */
@Service
class ConfigService extends AbstractKafkaAdminClientService{
    public ConfigService(AdminClient kafkaAdminClient) {
        super(kafkaAdminClient);
    }

    Map<String, List<Entry<String, String>>> getConfigs(Collection<ConfigResource> resources) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeConfigs(resources)
            .all()
            .thenApply(it -> it.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().name(), e -> e.getValue().entries().stream()
                    .map(config -> Entry.of(config.name(), config.value()))
                    .collect(Collectors.toList())))
            ).get();
    }

    List<Entry<String, String>> getConfig(ConfigResource resource) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeConfigs(List.of(resource))
            .all()
            .thenApply(it -> it.values().stream()
                .flatMap(e -> e.entries().stream()
                    .map(config -> Entry.of(config.name(), config.value()))
                ).collect(Collectors.toList())
            ).get();
    }

    boolean update(ConfigResource resource, Map<String, String> config) throws ExecutionException, InterruptedException {
        List<AlterConfigOp> alterConfigOps = config.entrySet().stream()
            .map(it -> new ConfigEntry(it.getKey(), it.getValue()))
            .map(it -> new AlterConfigOp(it, AlterConfigOp.OpType.SET))
            .collect(Collectors.toList());

        this.kafkaAdminClient.incrementalAlterConfigs(Map.of(resource, alterConfigOps))
            .all()
            .get();

        return true;
    }
}
