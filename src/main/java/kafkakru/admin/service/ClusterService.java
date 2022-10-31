package kafkakru.admin.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.acl.AclOperation;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.dto.Node;

@Service
@RequiredArgsConstructor
public class ClusterService {
    private final AdminClient kafkaAdminClient;

    public String getClusterId() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeCluster()
            .clusterId()
            .get();
    }

    public List<Node> getNodes() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeCluster()
            .nodes()
            .thenApply(it -> it.stream()
                .map(Node::from)
                .collect(Collectors.toList()))
            .get();
    }

    public Node getController() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeCluster()
            .controller()
            .thenApply(Node::from)
            .get();
    }

    public Set<AclOperation> getAclOperations() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeCluster()
            .authorizedOperations()
            .get();
    }
}
