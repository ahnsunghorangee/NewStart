## **디렉토리 구조도**

> /project/project: project에 대한 기본 settings과 초기 urls 설정
>
> /project/ai: AI 함수를 실행시키는 urls 설정
>
> /project/ai/func: text_detection, object_detection, video_detection에 대한 함수
>
> /project/crawling: [국립국어원 한국수어사전]에서 단어 크롤링
>
> /project/notices: 게시판, 댓글 기능
>
> /project/users: 유저정보, 단어, 긴급연락망, 문자보내기 기능



## **API 요청**

https://docs.google.com/spreadsheets/d/1zwSpI-WW6NMjS3xbgSPEEW6ZUJLgsfPLZeZYsekbk54/edit?usp=sharing



## 코드 실행하기

### 0. 가상환경 export

여기서는 제공해주는 requirements.yaml 사용하면 된다.

```
conda env export > [파일이름].yaml
```

### 1. AWS Anaconda 설치하기

1. 아나콘다 리눅스버전으로 설치

2. install anaconda

   ```
   bash ~/anaconda.sh -b -p $HOME/anaconda
   ```

3. update anaconda path

   ```
   echo -e '\nexport PATH=$HOME/anaconda/bin:$PATH' >> $HOME/.bashrc && source $HOME/.bashrc
   ```

4. 혹시 conda를 입력하면 menu들이 나와야 하는데 정상적으로 $PATH등록이 안되었다면, PATH를 export를 해준다.

   ```
   export PATH=~/anaconda3/bin:$PATH
   ```

### 2. AWS 시작하기

1. ssh -i cert.pem ubuntu@serverName

2. 가상환경 만들기

   ```
   conda create -n [가상환경 명] python=3.7
   ```

3. 가상환경 실행하기 (만약 root이면 ubuntu로 바꿔야된다. su - ubuntu)

   ```
   source activate [가상환경 명]
   ```

4. Django Server 실행하기

   ```
   python manage.py runserver
   ```

5. 타 가상환경 모듈 import 하기

   ```
   pip3 install -r requirements.txt
   ```

6. 가상환경 종료하기

   ``` 
   source deactivate [가상환경 명]
   ```

7. 가상환경 목록확인

   ```
   conda env list
   ```

8. 가상환경 제거하기

   ```
   conda env remove -n [가상환경 명]
   ```

### 3. Nginx 업데이트 하기

sudo cp -f /home/ubuntu/project/backend/project/project/.config/nginx/project.conf.ini /etc/nginx/sites-available/project.conf

sudo ln -sf /etc/nginx/sites-available/project.conf /etc/nginx/sites-enabled/project.conf

sudo systemctl daemon-reload

sudo systemctl restart uwsgi nginx

### 4. migrate하기

1. [app 명]/models.py에서 모델을 변경한다.

2. Django server 실행하기

   ```
   python manage.py runserver
   ```

3. 변경사항에 대한 migrations 만들기

   ```
   python manage.py makemigrations
   ```

4. 변경사항을 데이터베이스에 적용하기

   ``` 
   python manage.py migrate
   ```



## Cross Origin 해결방법

1. 파이썬 패키지 설치

   ``` 
   pip install django-cors-headers
   ```

2. settings.py 설정 추가

   ```
   # settings.py
   
   INSTALLED_APPS = [
       ...
       'corsheaders',
   ]
   
   MIDDLEWARE = [
       ...
       'corsheaders.middleware.CorsMiddleware',
   ]
   
   CORS_ORIGIN_WHITELIST = (
       'http://localhost:3000',
       'http://127.0.0.1:3000',
       'http://serverName'
   )
   ```

   