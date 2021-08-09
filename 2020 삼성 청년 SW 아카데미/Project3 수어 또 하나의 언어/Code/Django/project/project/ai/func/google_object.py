import os
import glob
import cv2
import numpy as np
from google.cloud import vision
import base64
import csv

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = os.path.dirname(os.path.abspath(__file__)) +"/datasets/winter-sum-241407-8886c2c92665.json"
# path = os.path.dirname(os.path.abspath(__file__)) + '\\datasets\\dog.jpg'
client = vision.ImageAnnotatorClient()


def draw_prediction(img, class_id, x, y, x_plus_w, y_plus_h, label, COLORS):
    color = COLORS[class_id]
    cv2.rectangle(img, (x, y), (x_plus_w, y_plus_h), color, 2)
    label = translated(label)
    # print(label)
    # cv2.putText(img, label, (x - 10, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)
    return img, label

def translated(text):
    from google.cloud import translate_v2 as translate
    translate_client = translate.Client()

    client = translate.Client()
    result = client.translate(text, target_language='ko')
    # print(result['translatedText'])
    return result['translatedText']

def findName(text):
    f = open('./ai/func/datasets/filename.csv', 'r',encoding='cp949')
    rdr = csv.reader(f)
    for list in rdr:
        if list[6] == text and list[1] == '1' and list[3] == '정면':
            return list[5][:-3] + "webm"


def run(image):
    imgdata = base64.b64decode(image)
    filename = 'object_detection.jpg'  # I assume you have a way of picking unique filenames
    with open(filename, 'wb') as f:
        f.write(imgdata)
    path = 'object_detection.jpg'
    with open('object_detection.jpg', 'rb') as image_file:
        content = image_file.read()
    image = vision.types.Image(content=content)

    img = cv2.imread(path)
    temp_img = cv2.imread(path)

    objects = client.object_localization(image=image).localized_object_annotations
    COLORS = np.random.uniform(0, 255, size=(len(objects), 3))
    list = []
    for f in os.listdir('result/'):
        os.remove('result/' + f)
    roi = "roi"
    for idx, object_ in enumerate(objects):
        # print('\n{} (confidence: {})'.format(object_.name, object_.score))
        data = dict()
        x = object_.bounding_poly.normalized_vertices[0].x * img.shape[1]
        y = object_.bounding_poly.normalized_vertices[0].y * img.shape[0]
        w = object_.bounding_poly.normalized_vertices[2].x * img.shape[1]
        h = object_.bounding_poly.normalized_vertices[2].y * img.shape[0]
        img, label = draw_prediction(img, idx, round(x), round(y), round(w), round(h), object_.name, COLORS)
        subImg = temp_img[ round(y):round(h),round(x):round(w)]

        videoname = findName(label)
        # print(round(x),round(w), round(y),round(h))
        # cv2.namedWindow('cutting', cv2.WINDOW_NORMAL)
        # print(object_.bounding_poly.normalized_vertices)
        roi_name = roi + str(idx) + ".jpg"
        cv2.imwrite("result/" + roi_name, subImg)
        with open("result/" + roi_name, "rb") as img_file:
            my_string = base64.b64encode(img_file.read()).decode('utf-8')
        data['label'] = label
        data['src'] = my_string
        data['videoname'] = videoname
        list.append(data)
        #
        # for vertex in object_.bounding_poly.normalized_vertices:
        #     print(' - ({}, {})'.format(vertex.x, vertex.y))

    cv2.imwrite("object_result.jpg", img)
    # cv2.imshow('result', img)
    # cv2.waitKey()
    return list