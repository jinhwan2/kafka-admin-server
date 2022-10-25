import yact
from flask import Config

class AppConfig(Config):
    def from_yaml(self, config_file, directory=None):
        config = yact.from_file(config_file, directory=directory)
        for section in config.sections:
            self[section] = config[section]
