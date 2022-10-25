#!/bin/bash
PROJECT_PATH=${PWD}/app

pip install -r ${PROJECT_PATH}/requirements.txt 
python -m app
