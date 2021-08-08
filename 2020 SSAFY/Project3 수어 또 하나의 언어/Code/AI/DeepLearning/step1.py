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
params["body"] = 1

opWrapper = op.WrapperPython()
opWrapper.configure(params)
opWrapper.start()
datum = op.Datum()


#비디오 세팅
print("video loading")
root = './video'
videoPaths = os.listdir(root)
print('videoPath',videoPaths)

count = 0
hnData ={}
print(type(hnData))
#시간측정
start = time.time()
#비디오 분류 이름들
for videoPath in videoPaths:
    #분류 안에 들어있는 비디오들의 이름
    videos = os.listdir(root+'/'+videoPath)
    print(videos)
    #비디오 하나의 이름
    for video in videos:
        print('비디오 이름',root+'/'+videoPath+'/'+video)
        vidcap = cv2.VideoCapture(root+'/'+videoPath+'/'+video)
        while (vidcap.isOpened()):
            # read()는 grab()와 retrieve() 두 함수를  한 함수로불러옴
            # 두 함수를 동시에 불러오는 이유는 프레임이 존재하지 않을 때
            # grab() 함수를 이용하여 return false 혹은 NULL 값을 넘겨 주기 때문
            ret, image = vidcap.read()
            if ret == False:
                break;
            if (int(vidcap.get(1)) % 2 == 0):
                # imageToProcess = imread(imagePath)
                # # imageToProcess = cv2.imread(imagePath)
                datum.cvInputData = image
                opWrapper.emplaceAndPop([datum])
                convertData = datum.poseKeypoints
                if not convertData.any():
                    continue

                # 사람 못찾으면 종료
                try:
                    a, b, c = convertData.shape
                except:
                    continue

                # 데이터 저장
                leftHand = convertData[0][7]
                rightHand = convertData[0][4]
                nose = convertData[0][0]

                # 손목을 못찾았을 경우
                if not leftHand[leftHand > 0.1].any and rightHand[rightHand > 0.1].any:
                    continue

                filename = 'frame'+str(count)+'.jpg'
                hnData[filename] = [leftHand[0], leftHand[1], rightHand[0], rightHand[1], nose[0], nose[1]]

                # 캡쳐된 이미지를 저장
                check_folder('./step1',videoPath)
                imwrite('./step1/'+ videoPath+'/frame%d.jpg' % count, image)
                print('저장한 이미지 이름 ','./step1/'+ videoPath+'/frame%d.jpg' % count)
                count += 1

                print('step1', count)
                print('time : ', time.time() - start)


    vidcap.release()
with open('./step1/hndata.pickle','wb') as fw:
    pickle.dump(hnData,fw)
vidcap.release()
cv2.destroyAllWindows()

