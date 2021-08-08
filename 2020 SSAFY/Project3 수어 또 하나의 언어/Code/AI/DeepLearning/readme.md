# 수어영상 인식

>
>
>### * 데이터 만들기 -> 학습 -> 테스트 순으로 진행 * 
>
>
>
>## 데이터 만들기
>
>1. video 폴더에 클래스별로 영상을 넣는다.
>2. step1.py 실행 -> step1 폴더에 영상이 이미지 변환되고 손목, 코 데이터가 들어있는 hnData.pickle 파일이 생성됨
>3. step2.py 실행 -> step2 폴더에 양손 마스킹 이미지, step2-2 폴더에 마스킹되지 않은 이미지가 생성됨
>4. step3.py 실행 -> step3 폴더에 양손 마스킹 + 얼굴 마스킹 된 이미지가 생성됨
>
>
>
>## 학습
>
>- triain.py 실행 --dataset --model --label-bin --epochs --plot 등의 옵션이 있다.
>
>
>
>## 테스트
>
>- 이미지 : predict_image
>- 영상 : predict_video
>- --model, --label-bin, --input, --output, --size 등의 옵션을 줄 수 있다.