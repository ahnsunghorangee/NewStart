from config import config
from data import preprocess 
from utils import utils


# config 저장
utils.save_config(config())

# 이미지 경로 및 캡션 불러오기
img_paths, captions = preprocess.get_path_caption(config())

# 전체 데이터셋을 분리해 저장하기
train_dataset_path, val_dataset_path = preprocess.dataset_split_save(config())

# 저장된 데이터셋 불러오기
img_paths, caption = preprocess.get_data_file(train_dataset_path)

# 이미지와 캡션 시각화 하기
utils.visualize_img_caption(img_paths[0],caption[0])