from django.db import models

import os
import sys
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
from users import models as userModel

# Create your models here.
class Notice(models.Model):
    # 모델의 기본키 필드는 별도로 지정하지 않으
    # 면 자동으로 추가된다.
    subject = models.CharField(max_length=100)
    content = models.TextField()
    email = models.CharField(max_length=50)
    date = models.DateTimeField(auto_now_add=True)
    url = models.TextField(null=True)
    user = models.ForeignKey(userModel.User, on_delete=models.CASCADE)


class Reply(models.Model):
    user = models.ForeignKey(userModel.User, on_delete=models.CASCADE)
    notice = models.ForeignKey(Notice, on_delete=models.CASCADE)
    content = models.TextField()
    date = models.DateTimeField(auto_now=True)
    nickname = models.CharField(max_length=50)