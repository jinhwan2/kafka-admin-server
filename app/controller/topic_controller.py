
from flask_restx import Namespace, Resource
from app.model.http_response import HttpResponse
from app.service.topic_service import topicService

Topic = Namespace(
    name="Topic Manage",
    description="Topic 정보를 관리합니다.",
)

@Topic.route('')
class TodoPost(Resource):
    @Topic.response(200, 'Success')
    def get(self):
        """topic 목록 조회"""
        return HttpResponse.success(topicService.get_topic())

    @Topic.response(200, 'Success')
    def post(self):
        """topic 추가"""
        return HttpResponse.success(topicService.create_topic())

    @Topic.response(200, 'Success')
    def delete(self):
        """topic 제거"""
        return HttpResponse.success(topicService.delete_topic())