from django.shortcuts import render

# Create your views here.
from django.shortcuts import get_object_or_404
from rest_framework.response import Response  # 응답하는 메서드
from rest_framework.decorators import api_view  # 요청 방식을 필터링
from .models import User, Voca, Phone
from .serializers import UserSerializer, VocaSerializer, UserFirstSerializer, PhoneSerializer
from django.core.serializers import serialize
import json

from sdk.api.message import Message
from sdk.exceptions import CoolsmsException

@api_view(['POST'])
def user_insert(request):
    if request.method == 'POST':
        users = User.objects.all()
        data = json.loads(serialize('json',users))
        req = json.loads(request.body)
        id = req['id']

        pk = User.objects.filter(id=id)
        if not pk.exists():
            serializer = UserFirstSerializer(data=request.data)
            if(serializer.is_valid(raise_exception=True)):
                serializer.save()
            return Response(serializer.data)
        else:
            user = get_object_or_404(User, pk=id)
            serializer = UserSerializer(user)
            return Response(serializer.data)

@api_view(['GET'])
def user_vocaList(request, user_pk):
    if request.method == 'GET':
        vocaList = Voca.objects.filter(user_id=user_pk)
        serializer = VocaSerializer(vocaList, many=True)
        return Response(serializer.data)

@api_view(['POST'])
def voca_list(request):
    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))
        serializer = VocaSerializer(data=request.data)  # JSON => python
        if serializer.is_valid(raise_exception=True):
            serializer.save(user_id=data['user_id'])
        return Response(serializer.data)

@api_view(['DELETE'])
def voca_detail(request,voca_pk):
    if request.method == 'DELETE':
        voca = get_object_or_404(Voca, pk=voca_pk)
        voca.delete()
        return Response({'message': '성공적으로 삭제되었습니다.'})

@api_view(['GET'])
def user_phoneList(request, user_pk):
    if request.method == 'GET':
        phoneList = Phone.objects.filter(user_id=user_pk)
        serializer = PhoneSerializer(phoneList, many=True)
        return Response(serializer.data)

@api_view(['POST'])
def phone_insert(request):
    if request.method == 'POST':
        req = json.loads(request.body)
        serializer = PhoneSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save(user_id=req['user_id'])
        return Response({'message':'전화번호 등록 성공'})

@api_view(['PUT', 'DELETE'])
def phone_detail(request, phone_pk):
    if request.method == 'PUT':
        phone = get_object_or_404(Phone, pk=phone_pk)
        serializer = PhoneSerializer(data=request.data, instance=phone)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response({'message' : '성공적으로 수정'})
    elif request.method == 'DELETE':
        phone = get_object_or_404(Phone, pk=phone_pk)
        phone.delete()
        return Response({'message':'성공적으로 삭제'})

@api_view(['POST'])
def send_message(request):
    if request.method == 'POST':
        req = json.loads(request.body)
        user_pk = req['user_id']
        nickname = req['nickname']
        word = req['word']
        data = Phone.objects.filter(user_id=user_pk)
        dataList = data.values()
        print('list',dataList.values())

        phoneList = []
        for data in dataList:
            # print('in list', dataList.values[i])
            phoneList.append(data['number'])

        convertList = ','.join(phoneList)
        api_key = ""
        api_secret = ""
        ## 4 params(to, from, type, text) are mandatory. must be filled
        params = dict()
        params['type'] = 'sms'  # Message type ( sms, lms, mms, ata )
        params['to'] = convertList  # Recipients Number '01000000000,01000000001'
        params['from'] = ''  # Sender number
        params['text'] = nickname + '님이 "'+word+'"라고 보냈습니다. 도와주세요. S:with'  # Message
        cool = Message(api_key, api_secret)
        try:
            response = cool.send(params)
            print("Success Count : %s" % response['success_count'])
            print("Error Count : %s" % response['error_count'])
            print("Group ID : %s" % response['group_id'])
            if "error_list" in response:
                print("Error List : %s" % response['error_list'])
        except CoolsmsException as e:
            print("Error Code : %s" % e.code)
            print("Error Message : %s" % e.msg)


        return Response({"message":"문자보내기 성공"})