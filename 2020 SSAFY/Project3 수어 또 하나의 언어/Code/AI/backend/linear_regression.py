import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

from models.linear_model import LinearModel

# Req. 1-1	데이터 읽기 및 시각화
# 데이터 불러오기
train_data = np.load(".\\datasets\\linear_train.npy")
test_x = np.load(".\\datasets\\linear_test_x.npy")

# tf 형식에 맞게 변환
x_data = np.expand_dims(train_data[:, 0], axis=1)
y_data = train_data[:, 1]

# Req. 1-2	선형 모델 클래스 구현
# 모델 생성
model = LinearModel(num_units=1)

# Req. 1-3	최적화 함수 및 손실 함수 바인딩
# 최적화 함수, 손실함수와 모델 바인딩
model.compile(optimizer=tf.keras.optimizers.SGD(learning_rate=0.001),
              loss=tf.keras.losses.MSE,
              metrics=[tf.keras.metrics.MeanSquaredError()])

# Req. 1-4	모델 학습 함수 구현
# 모델 학습
model.fit(x=x_data,
          y=y_data,
          epochs=10,
          batch_size=32)

# 모델 테스트
prediction = model.predict(x=test_x,
                           batch_size=None)

# Req. 1-5	예측 및 결과 시각화
# 결과 시각화
plt.scatter(x_data, y_data, s=5, label="train data")
plt.scatter(test_x, prediction, s=5, label="prediction data")
plt.legend()
plt.show()

# 모델 정리
model.summary()
