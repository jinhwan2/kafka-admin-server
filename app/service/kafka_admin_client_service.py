from flask import Flask
from kafka import KafkaAdminClient

from app.__main__ import KafkaConfig

app = Flask(__name__)

kafkaAdminClient = KafkaAdminClient(bootstrap_servers=KafkaConfig['bootstrap_servers'])