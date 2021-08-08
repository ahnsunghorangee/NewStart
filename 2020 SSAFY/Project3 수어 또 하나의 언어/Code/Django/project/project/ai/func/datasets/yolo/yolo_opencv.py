import cv2
import argparse
import numpy as np
from requests import Request
from requests import Session

def setting():
    print('hi')
    ap = argparse.ArgumentParser()
    ap.add_argument('-i', '--image')
    ap.add_argument('-c', '--config', default='.\\yolov3.cfg')
    ap.add_argument('-w', '--weights', default='.\\yolov3.weights')
    ap.add_argument('-cl', '--classes', default='.\\yolov3.txt')

    args = ap.parse_args()
    print(args.config)
    classes = None

    with open(args.classes, 'r') as f:
        classes = [line.strip() for line in f.readlines()]

    COLORS = np.random.uniform(0, 255, size=(len(classes), 3))

    return args, classes, COLORS

def get_output_layers(net):
    
    layer_names = net.getLayerNames()
    
    output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

    return output_layers


def draw_prediction(img, class_id, confidence, x, y, x_plus_w, y_plus_h, classes, COLORS):
    label = str(classes[class_id])
    color = COLORS[class_id]
    cv2.rectangle(img, (x,y), (x_plus_w,y_plus_h), color, 2)
    cv2.putText(img, label, (x-10,y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)
    print(label)
    print(en_to_kor(label))

def en_to_kor(text):
    s = Session()
    headers = {
        'X-Naver-Client-Id' : '',
        'X-Naver-Client-Secret' : ''
    }

    payload = {
        'source' : 'en',
        'target' : 'ko',
        'text' : 'a ' + text
    }

    url = 'https://openapi.naver.com/v1/papago/n2mt'

    req = Request('POST',url, data=payload, headers=headers)
    prepped = req.prepare()
    res = s.send(prepped)

    result = res.json()['message']['result']['translatedText']

    return result

def object_detection(image):
    args,classes, COLORS = setting()
    print(args)
    image = cv2.imread(image)

    Width = image.shape[1]
    Height = image.shape[0]
    scale = 0.00392

    net = cv2.dnn.readNet(args.weights, args.config)
    blob = cv2.dnn.blobFromImage(image, scale, (416,416), (0,0,0), True, crop=False)
    net.setInput(blob)
    outs = net.forward(get_output_layers(net))

    class_ids = []
    confidences = []
    boxes = []
    conf_threshold = 0.5
    nms_threshold = 0.4

    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > 0.5:
                center_x = int(detection[0] * Width)
                center_y = int(detection[1] * Height)
                w = int(detection[2] * Width)
                h = int(detection[3] * Height)
                x = center_x - w / 2
                y = center_y - h / 2
                class_ids.append(class_id)
                confidences.append(float(confidence))
                boxes.append([x, y, w, h])


    indices = cv2.dnn.NMSBoxes(boxes, confidences, conf_threshold, nms_threshold)

    for i in indices:
        i = i[0]
        box = boxes[i]
        x = box[0]
        y = box[1]
        w = box[2]
        h = box[3]
        draw_prediction(image, class_ids[i], confidences[i], round(x), round(y), round(x+w), round(y+h),classes,COLORS)

    imageHeight, imageWidth = image.shape[:2]
    print(imageHeight, imageWidth)
    resizeHeight = int(0.3 * imageHeight)
    resizeWidth = int(0.3 * imageWidth)
    resize_image = cv2.resize(image, (resizeHeight, resizeWidth), interpolation=cv2.INTER_CUBIC)


    cv2.imshow("object detection", resize_image)
    cv2.waitKey()

    cv2.imwrite("object-output.jpg", resize_image)
    cv2.destroyAllWindows()

# object_detection('./dog.jpg')