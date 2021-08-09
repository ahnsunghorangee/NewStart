# -*- coding: utf-8 -*-
from imutils import paths
import cv2
import os
import numpy
import sys
from sys import platform
import time
import pickle

def imwrite(filename, img, params=None):
    try:
        ext = os.path.splitext(filename)[1]
        result, n = cv2.imencode(ext, img, params)

        if result:
            with open(filename, mode='w+b') as f:
                n.tofile(f)
            return True
        else:
            return False
    except Exception as e:
        print(e)
        return False

def imread(filename, flags=cv2.IMREAD_COLOR, dtype=numpy.uint8):
    try:
        n = numpy.fromfile(filename, dtype)
        img = cv2.imdecode(n, flags)
        return img
    except Exception as e:
        print(e)
        return None

def check_folder(path, name):
    check_path = os.path.join(path, name)
    try:
        if not os.path.isdir(check_path):
            os.makedirs(check_path)
    except:
        pass


#오픈포즈 세팅
# Import Openpose (Windows/Ubuntu/OSX)
dir_path = os.path.dirname(os.path.realpath(__file__))
try:
    # Windows Import
    if platform == "win32":
        # Change these variables to point to the correct folder (Release/x64 etc.)
        sys.path.insert(0,dir_path + './util/Release');
        os.environ['PATH']  = os.environ['PATH'] + ';' + dir_path + './util/Release;' +  dir_path + './util/bin;'
        import pyopenpose as op
except ImportError as e:
        print('Error: OpenPose library could not be found. Did you enable `BUILD_PYTHON` in CMake and have this Python script in the right folder?')
        raise e

params = dict()
params["model_folder"] = "./util/models/"
params["hand"] = True
params["body"] = 0
params["hand_detector"] = 2

opWrapper = op.WrapperPython()
opWrapper.configure(params)
opWrapper.start()
datum = op.Datum()

#손,코 데이터 불러옴
with open('./step1/hndata.pickle.', 'rb') as fr:
    hnData = pickle.load(fr)

print("images loading")
imagePaths = list(paths.list_images('./step1'))
print('imagePaths',imagePaths)

start = time.time()
for idx, imagePath in enumerate(imagePaths):
    classname = imagePath.split(os.path.sep)[-2]
    filename = imagePath.split(os.path.sep)[-1]
    imageToProcess = imread(imagePath)

    handRectangles = [
        # 왼손, 오른손
        [
            op.Rectangle(hnData[filename][0] - 100.0, hnData[filename][1] - 100.0, 200.0, 200.0),
            op.Rectangle(hnData[filename][2] - 100.0, hnData[filename][3] - 100.0, 200.0, 200.0),
        ],
    ]

    # Create new datum
    datum = op.Datum()
    datum.cvInputData = imageToProcess
    datum.handRectangles = handRectangles

    # Process and display image
    opWrapper.emplaceAndPop([datum])

    #결과값을 numpy로 변환
    a = datum.handKeypoints
    a = numpy.array(a)

    # 왼손 오른손이 모두 검출되지 않았을 경우
    leftCheck = 0
    for count in range(20):
        if (a[0][0][count][2] > 0.6):
            leftCheck = 0;
            break;
        else:
            leftCheck = 1;
    rightCheck = 0
    for count in range(20):
        if (a[1][0][count][2] > 0.6):
            rightCheck = 0;
            break;
        else:
            rightCheck = 1;
    if leftCheck == 1 and rightCheck == 1:
        continue

    # 손 마스킹한 이미지 step2 폴더에 저장
    check_folder('./step2', classname)
    check_folder('./step2-2', classname)
    imwrite('./step2/' + classname + '/' + filename, datum.cvOutputData)
    imwrite('./step2-2/' + classname + '/' + filename, imageToProcess)

    print('step2', idx, imagePath)
    print('time : ', time.time() - start)
