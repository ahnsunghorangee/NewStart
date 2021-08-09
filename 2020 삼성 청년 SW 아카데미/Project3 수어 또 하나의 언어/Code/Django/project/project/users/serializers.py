from rest_framework import serializers
from .models import User, Voca, Phone

class PhoneSerializer(serializers.ModelSerializer):
    class Meta:
        model = Phone
        fields = ['id','user_id','number']

class VocaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Voca
        fields = ['id','user_id','word','video','date']

class UserSerializer(serializers.ModelSerializer):
    voca_set = VocaSerializer(many=True)
    voca_count = serializers.IntegerField(source='voca_set.count')
    phone_set = PhoneSerializer(many=True)
    class Meta:
        model = User
        fields = ['id','nickname','email','email','gender','birthday','age_range','voca_set','voca_count','phone_set']

class UserFirstSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id','nickname','email','email','gender','birthday','age_range']

