# from .kafka_admin_client_service import kafkaAdminClient

class TopicService():
    def get_topic(self):
        return "get_topic"

    def delete_topic(self):
        return "delete_topic"

    def create_topic(self):
        return "add_topic"

topicService = TopicService()