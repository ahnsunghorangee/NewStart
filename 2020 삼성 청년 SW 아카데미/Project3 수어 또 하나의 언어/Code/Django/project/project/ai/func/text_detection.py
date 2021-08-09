import io # 파일을 읽고 쓰기위한 모듈
import os # os의 기능을 사용하기 위한 모듈

# Imports the Google Cloud client library
from google.cloud import vision
from google.cloud.vision import types
import base64
import cv2
import csv

def findName(text):
    f = open('./ai/func/datasets/filename.csv', 'r',encoding="cp949")
    rdr = csv.reader(f)
    for list in rdr:
        if list[6] == text and list[1] == '1' and list[3] == '정면':
            return list[5][:-3] + "webm"

def detection(image):
    os.environ["GOOGLE_APPLICATION_CREDENTIALS"]=os.path.dirname(os.path.abspath(__file__)) + '/datasets/My Project-070d5c77d071.json'
    client = vision.ImageAnnotatorClient()

    imgdata = base64.b64decode(image)
    path = 'text_detection.jpg'  # I assume you have a way of picking unique filenames
    with open(path, 'wb') as f:
        f.write(imgdata)

    # 이미지 읽기
    with open(path, 'rb') as image_file:
        content = image_file.read()
    new_img = cv2.imread(path)
    img = vision.types.Image(content=content)

    response = client.text_detection(image=img)
    texts = response.text_annotations

    list = []

    # print(texts)

    for idx,text in enumerate(texts):
        isAlpha = False
        # print('\n"{}"'.format(text.description))
        vertices = (['({},{})'.format(vertex.x, vertex.y)
                    for vertex in text.bounding_poly.vertices])
        x = text.bounding_poly.vertices[0].x
        y = text.bounding_poly.vertices[0].y
        x2 = text.bounding_poly.vertices[2].x
        y2 = text.bounding_poly.vertices[2].y
        if (text.description.isdigit()):
            continue
        for t in text.description:
            if ('a' <= t <= 'z'):
                isAlpha = True
                break
            if ('A' <= t <= 'Z'):
                isAlpha = True
                break
            if ('0' <= t <= '9'):
                isAlpha = True
                break
        if (isAlpha):
            continue
        if idx != 0:
            cv2.rectangle(new_img, (x,y), (x2,y2), (255,0,0), 1)
        # cv2.rectangle(img, (10,10), (40,40), (255,0,0), 2)
        # print(vertices)

        data = dict()

        print(text.description)
        data['label'] = text.description
        data['videoname'] = findName(text.description)
        list.append(data)
    if len(list) != 0:
        list.remove(list[0])
    cv2.imwrite("text_result.jpg", new_img)
    # cv2.imshow('result',img)
    # cv2.waitKey()
    return list