from flask import Flask
from flask_restx import Api

from app import AppConfig
from app.controller.topic_controller import Topic

Flask.config_class = AppConfig
app = Flask(__name__)
app.config.from_yaml('application.yaml')

api = Api(
    app,
    title="kafka admin server",
    description="kafka admin client server",
)

api.add_namespace(Topic, '/todos') 

if __name__ == '__main__':
    app.run('localhost', port=app.config['port'])