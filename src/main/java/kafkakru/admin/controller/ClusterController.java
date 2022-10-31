package kafkakru.admin.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.common.acl.AclOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import kafkakru.admin.annotation.V1Version;
import kafkakru.admin.dto.Node;
import kafkakru.admin.service.ClusterService;

@V1Version
@RestController
@RequiredArgsConstructor
public class ClusterController {
    private final ClusterService clusterService;

    @GetMapping("cluster-ids")
    String getClusterId() throws ExecutionException, InterruptedException {
        return this.clusterService.getClusterId();
    }

    @GetMapping("clusters/controller")
    Node getController() throws ExecutionException, InterruptedException {
        return this.clusterService.getController();
    }

    @GetMapping("cluster-nodes")
    List<Node> getNodes() throws ExecutionException, InterruptedException {
        return this.clusterService.getNodes();
    }

    @GetMapping("acls")
    Set<AclOperation> getAclOperations() throws ExecutionException, InterruptedException {
        return this.clusterService.getAclOperations();
    }
}