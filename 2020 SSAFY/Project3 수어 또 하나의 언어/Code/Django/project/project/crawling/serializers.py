from rest_framework import serializers
from .models import Word

class WordSerializer(serializers.ModelSerializer):
    class Meta:
        model = Word
        fields = ['id', 'word','imageUrl1','imageUrl2','imageUrl3','imageUrl4','videoUrl','desc']