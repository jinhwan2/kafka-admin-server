## Kafka Admin Server
Kafka HTTP admin server

## swagger
### local
http://localhost:3000/swagger-ui.html

## phase
`local / dev`

## properties
start with `kafka.admin` 
```yaml
kafka:
  admin:
    servers: localhost:9092,localhost:9093,localhost:9094 # bootstrap-servers
```

## local test env
https://github.com/conduktor/kafka-stack-docker-compose 사용 

### 3 zookeeper - 3 kafka case
**run as daemon**
```bash
docker-compose -f zk-multiple-kafka-multiple.yml up  -d
```
**shutdown**
```bash
docker-compose -f zk-multiple-kafka-multiple.yml down
```
