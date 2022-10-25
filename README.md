# kafka-admin-server
카프카 어드민 서버

# 환경구성
## venv
### 실행
```bash
. venv/bin/activate
```

### 종료
```bash
deactivate
```

## application 실행
```bash
. venv/bin/activate # 가상환경 실행
./run.sh # app 실행 스크립트
```

> run.sh 는 아래와 같이 구성되어 있습니다.
```bash
pip install -r requirements.txt # 의존성 다운로드
python -m app # app 구동
```