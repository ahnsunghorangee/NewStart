## YOLO V3

> (You Only Look Once) 딥러닝 기반의 물체인식 알고리즘



#### 필요한 라이브러리

- Numpy

- OpenCV

```
  pip install numpy
  pip install opencv-python
```

  

#### 필요한 파라미터

- input image
- YOLO config file
- YOLO weights
- class names

YOLO weights 파일은 아래 링크에서 다운 받을 수 있다.

```
https://pjreddie.com/media/files/yolov3.weights
```

터미널에서 실행할 때는

```
$ python yolo_opencv.py --image dog.jpg --config yolov3.cfg --weights yolov3.weights --classes yolov3.txt
```

Pycharm에서 실행할 때는

```
Edit Configurations > Parametes 에다가 밑에 코드 입력 
--image dog.jpg --config yolov3.cfg --weights yolov3.weights --classes yolov3.txt
```

