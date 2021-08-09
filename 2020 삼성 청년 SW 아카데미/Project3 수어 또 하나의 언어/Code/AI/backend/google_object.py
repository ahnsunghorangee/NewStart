import os
import cv2
import numpy as np
from google.cloud import vision

os.environ["GOOGLE_APPLICATION_CREDENTIALS"]=".\\datasets\\winter-sum-241407-8886c2c92665.json"
# path = '.\\yolo\\dog.jpg'
client = vision.ImageAnnotatorClient()

def draw_prediction(img, class_id, x, y, x_plus_w, y_plus_h, label, COLORS):
    color = COLORS[class_id]
    cv2.rectangle(img, (x, y), (x_plus_w, y_plus_h), color, 2)
    label = translated(label)
    # print(label)
    cv2.putText(img, label, (x - 10, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)
    return img, label

def translated(text):
    from google.cloud import translate_v2 as translate
    translate_client = translate.Client()

    client = translate.Client()
    result = client.translate(text, target_language='ko')
    # print(result['translatedText'])
    return result['translatedText']

def detection(path):
    with open(path, 'rb') as image_file:
        content = image_file.read()
    image = vision.types.Image(content=content)
    img = cv2.imread(path)

    list = []

    objects = client.object_localization(image=image).localized_object_annotations
    COLORS = np.random.uniform(0, 255, size=(len(objects), 3))
    for idx, object_ in enumerate(objects):
        # print('\n{} (confidence: {})'.format(object_.name, object_.score))
        x = object_.bounding_poly.normalized_vertices[0].x * img.shape[1]
        y = object_.bounding_poly.normalized_vertices[0].y * img.shape[0]
        w = object_.bounding_poly.normalized_vertices[2].x * img.shape[1]
        h = object_.bounding_poly.normalized_vertices[2].y * img.shape[0]
        img,label = draw_prediction(img, idx, round(x), round(y), round(w), round(h), object_.name, COLORS)
        list.append(label)
        #
        # for vertex in object_.bounding_poly.normalized_vertices:
        #     print(' - ({}, {})'.format(vertex.x, vertex.y))

    cv2.imshow('result',img)
    cv2.waitKey()
    return list