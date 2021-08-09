from django.db import models

# Create your models here.
class Word(models.Model):
    word = models.CharField(max_length=200)
    imageUrl1 = models.TextField(null=True)
    imageUrl2 = models.TextField(null=True)
    imageUrl3 = models.TextField(null=True)
    imageUrl4 = models.TextField(null=True)
    videoUrl = models.TextField(null=True)
    desc = models.TextField(null=True)
