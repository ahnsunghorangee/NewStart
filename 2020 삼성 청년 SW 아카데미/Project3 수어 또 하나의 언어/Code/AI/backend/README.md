# Obejct Detection

### Google Vision API

- 구글에서 제공하는 이미지속 물체 검출 API
- Text Detection에서도 사용가능
- tesseract보다 좀 더 정확한 결과를 도출할 수 있음



#### 사용방법

- API 등록후 JSON 파일 다운
- JSON파일을 원하는 위치에 옮긴 후 시스템 환경변수에 등록

```python
sets GOOGLE_APPLICATION_CREDENTIALS (json 파일 위치)\(json 파일 이름).json

# 환경변수가 적용이 안 될 경우 파이참에서 설정
import os
os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="/path/to/file.json"
```

- 필요한 라이브러리 설치

```python
pip install --upgrade google-api-python-client
pip install --upgrade google-cloud-vision
```

- 필요한 모듈 불러오기

```python
import io # 파일을 읽고 쓰기위한 모듈
import os # os의 기능을 사용하기 위한 모듈

# Imports the Google Cloud client library
from google.cloud import vision
from google.cloud.vision import types
```

- 필요한 변수 설정

```python
client = vision.ImageAnnotatorClient()
```



### Google Translation API

- 구글에서 제공하는 번역 API
- 여러개의 언어로 번역 가능



#### 사용방법

- Google Vision API와 마찬가지로 JSON 파일 다운 후 시스템 환경변수 추가
- 필요한 라이브러리 설치

```python
#Basic
pip install google-cloud-translate==2.0.1
#Advanced
pip install --upgrade google-cloud-translate
```

- 필요한 모듈 불러오기

```python
from google.cloud import translate_v2 as translate
client = translate.Client()
```

