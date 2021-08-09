import csv
from sklearn.model_selection import train_test_split

# Req. 3-1	이미지 경로 및 캡션 불러오기
def get_path_caption(config):
    f = open(config.caption_file_path, 'r')
    rdr = csv.reader(f)

    img_paths = []
    captions = []

    for line in rdr:
        # print(line)
        splitList = line[0].split('|')
        img_paths.append(splitList[0])
        captions.append(splitList[2])

    f.close()
    return img_paths, captions


# Req. 3-2	전체 데이터셋을 분리해 저장하기
# Req. 3-4	데이터 샘플링
def dataset_split_save(config):
    f = open(config.caption_file_path, 'r')
    rdr = csv.reader(f)

    path_list = []
    for line in rdr:
        path_list.append(line)

    train_dataset_path, val_dataset_path = train_test_split(path_list,
                                                        test_size=0.2,
                                                        shuffle=True,
                                                        random_state=1010)
    return train_dataset_path, val_dataset_path


# Req. 3-3	저장된 데이터셋 불러오기
def get_data_file(data):
    img_paths = []
    captions = []

    for line in data:
        splitList = line[0].split('|')
        img_paths.append(splitList[0])
        captions.append(splitList[2])

    return img_paths, captions