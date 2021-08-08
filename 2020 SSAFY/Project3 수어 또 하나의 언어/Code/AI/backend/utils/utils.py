import matplotlib.pyplot as plt


# Req. 2-2	세팅 값 저장
def save_config(config):
	# config.caption_file_path = 'abcd'
	print(config.caption_file_path)
	pass


# Req. 4-1	이미지와 캡션 시각화
def visualize_img_caption(image, caption):
	image_path = '.\\datasets\\images\\' + image
	img = plt.imread(image_path)
	plt.title(caption)
	plt.imshow(img)
	plt.show()