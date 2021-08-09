from yolo import yolo_opencv
import text_detection
import google_object

# yolo_opencv.object_detection('.\\yolo\\dog.jpg')

# 텍스트 디텍션 함수 테스트
text_list = text_detection.detection('.\\datasets\\kakao.png')
for i in text_list:
    print(i)

# 구글 vision api 함수 테스트
object_list = google_object.detection('.\\yolo\\dog.jpg')
for i in object_list:
    print(i)
