# import the necessary packages
from tensorflow.keras.models import load_model
from collections import deque
import numpy as np
import argparse
import pickle
import cv2
from PIL import ImageFont, ImageDraw, Image

def imread(filename, flags=cv2.IMREAD_COLOR, dtype=np.uint8):
    try:
        n = np.fromfile(filename, dtype)
        img = cv2.imdecode(n, flags)
        return img
    except Exception as e:
        print(e)
        return None


# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-m", "--model", required=True,
	help="path to trained serialized model")
ap.add_argument("-l", "--label-bin", required=True,
	help="path to  label binarizer")
ap.add_argument("-i", "--input", required=True,
	help="path to our input video")
ap.add_argument("-o", "--output", default='output.jpg',
	help="path to our output video")
ap.add_argument("-s", "--size", type=int, default=128,
	help="size of queue for averaging")
args = vars(ap.parse_args())

# load the trained model and label binarizer from disk
print("[INFO] loading model and label binarizer...")
model = load_model(args["model"])
lb = pickle.loads(open(args["label_bin"], "rb").read())

# initialize the image mean for mean subtraction along with the
# predictions queue
mean = np.array([123.68, 116.779, 103.939][::1], dtype="float32")
Q = deque(maxlen=args["size"])

# initialize the video stream, pointer to output video file, and
# frame dimensions
# vs = cv2.VideoCapture(args["input"])
# writer = None
# (W, H) = (None, None)

inputImage = imread(args["input"])

inputImage = cv2.cvtColor(inputImage, cv2.COLOR_BGR2RGB)
inputImage = cv2.resize(inputImage, (224, 224)).astype("float32")
# evaluate the network
preds = model.predict(np.expand_dims(inputImage, axis=0))[0]


# perform prediction averaging over the current history of
# previous predictions
results = np.array(Q).mean(axis=0)
print('preds',preds)
i = np.argmax(preds)
print('result',results)
print('i',i)
label = lb.classes_[i]
print('label',label)

# draw the activity on the output frame
text = "activity: {}".format(label)
print('text',text)


# release the file pointers
print("[INFO] cleaning up...")
# writer.release()
# vs.release()