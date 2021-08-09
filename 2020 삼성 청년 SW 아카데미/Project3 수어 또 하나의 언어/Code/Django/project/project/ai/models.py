from django.db import models

# Create your models here.
class Image(models.Model):
    images = models.ImageField(blank=True, upload_to="imagebase64", null=True)