from tensorflow.keras.models import load_model
from tensorflow.keras.layers import AveragePooling2D
from tensorflow.keras.applications import ResNet50
from tensorflow.keras.layers import Dropout
from tensorflow.keras.layers import Flatten
from tensorflow.keras.layers import Dense
from tensorflow.keras.layers import Input
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import SGD
from collections import deque
import numpy as np
import argparse
import pickle
import cv2
from PIL import ImageFont, ImageDraw, Image
import os
import base64

print('모델 로딩중')
lb = pickle.loads(open(os.path.dirname(os.path.abspath(__file__))+'/model/class.pickle', "rb").read())
model = load_model(os.path.dirname(os.path.abspath(__file__))+'/model/model.h5')
# baseModel = ResNet50(weights="imagenet", include_top=False,
# 	input_tensor=Input(shape=(224, 224, 3)))
# print(len(lb.classes_))
#
# # construct the head of the model that will be placed on top of the
# # the base model
# headModel = baseModel.output
# headModel = AveragePooling2D(pool_size=(7, 7))(headModel)
# headModel = Flatten(name="flatten")(headModel)
# headModel = Dense(512, activation="relu")(headModel)
# headModel = Dropout(0.5)(headModel)
# headModel = Dense(len(lb.classes_), activation="softmax")(headModel)
#
# # place the head FC model on top of the base model (this will become
# # the actual model we will train)
# model = Model(inputs=baseModel.input, outputs=headModel)
#
# # 가중치를 복원합니다
# model.load_weights(os.path.dirname(os.path.abspath(__file__))+'/model/model.h5')

print('모델 로딩 완료')


def imread(filename, flags=cv2.IMREAD_COLOR, dtype=np.uint8):
    try:
        n = np.fromfile(filename, dtype)
        img = cv2.imdecode(n, flags)
        return img
    except Exception as e:
        print(e)
        return None


def run(path):

    print('run실행')

    cap = cv2.VideoCapture(path)

    mean = np.array([123.68, 116.779, 103.939][::1], dtype="float32")
    Q = deque(maxlen=len(lb.classes_))
    writer = None
    (W, H) = (None, None)
    text = ''
    acc = 0
    results = []
    while True:
        # read the next frame from the file
        (grabbed, frame) = cap.read()

        # if the frame was not grabbed, then we have reached the end
        # of the stream
        if not grabbed:
            break
        if not frame.any():
            break

        # if the frame dimensions are empty, grab them
        if W is None or H is None:
            (H, W) = frame.shape[:2]

        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        frame = cv2.resize(frame, (224, 224)).astype("float32")
        frame -= mean

        preds = model.predict(np.expand_dims(frame, axis=0))[0]
        predval = np.argmax(preds)
        if preds[predval] > 0.65:
            Q.append(preds)
        if Q:
            results = np.array(Q).mean(axis=0)
            # print('result',results)
            i = np.argmax(results)
            label = lb.classes_[i]
            if results.any():
                acc = results[i]
            text = label
    cap.release()
    cv2.destroyAllWindows()

    print(text)
    list = []
    data = {}
    data['accuracy'] = acc
    data['answer'] = text

    list.append(data)

    return list
