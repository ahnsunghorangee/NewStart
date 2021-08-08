from django.db import models

# Create your models here.
class User(models.Model):
    id = models.IntegerField(primary_key=True)
    nickname = models.CharField(max_length=50)
    email = models.CharField(max_length=50)
    gender = models.CharField(max_length=10)
    birthday = models.CharField(max_length=10)
    age_range = models.CharField(max_length=50)

class Voca(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    word = models.CharField(max_length=100)
    video = models.CharField(max_length=100)
    date = models.DateTimeField(auto_now=True)

class Phone(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    number = models.CharField(max_length=50)