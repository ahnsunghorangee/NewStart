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

        # 파라미터 셋팅
params = dict()
params["model_folder"] = "./util/models/"
params["face"] = True
params["body"] = 0
params["face_detector"] = 2

opWrapper = op.WrapperPython()
opWrapper.configure(params)
opWrapper.start()
datum = op.Datum()
# 시간측정

#손,코 데이터 불러옴
with open('./step1/hndata.pickle.', 'rb') as fr:
    hnData = pickle.load(fr)

imagePaths = list(paths.list_images('./step2'))
print(imagePaths)
start = time.time()

for idx, imagePath in enumerate(imagePaths):
    imageToProcess = imread(imagePath)
    filename = imagePath.split(os.path.sep)[-1]
    faceRectangles = [
        op.Rectangle(hnData[filename][4] - 100.0, hnData[filename][5] - 100.0, 200.0, 200.0),
    ]

    datum.cvInputData = imageToProcess
    datum.faceRectangles = faceRectangles
    opWrapper.emplaceAndPop([datum])

    # 이미지 출력
    # cv2.imshow("OpenPose 1.6.0 - Tutorial Python API", datum.cvOutputData)
    # cv2.waitKey(0)
    # 이미지 저장
    # for imagePath in imagePaths:
    label = imagePath.split(os.path.sep)[-2]
    check_folder('./step3', label)
    print('step3', idx, imagePath)
    print('time : ', time.time() - start)
    imwrite('./step3/' + label + '/' + filename, datum.cvOutputData)
    # cv2.imwrite('./imagetest/image%d.jpg' % imgCount,datum.cvOutputData)