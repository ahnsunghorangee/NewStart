from django.shortcuts import render

from django.shortcuts import get_object_or_404
from rest_framework.response import Response  # 응답하는 메서드
from rest_framework.decorators import api_view  # 요청 방식을 필터링
from .models import Word
from .serializers import WordSerializer
from django.core.serializers import serialize
import json
from urllib.request import urlopen
from urllib.parse import quote_plus
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import os
import urllib.request

@api_view(['POST'])
def word_insert(request):
    if request.method == 'POST':
        print('rrr',request)
        req = json.loads(request.body)
        print('req',req)
        word = req['word']




        # 구글 드라이브 켜서 수어 번호 불러오기
        driver = webdriver.Chrome('./chromedriver.exe')
        driver.get('http://sldict.korean.go.kr/front/main/main.do')
        driver.find_element_by_xpath('//*[@id="searchKeyword"]').send_keys(word)
        driver.find_element_by_xpath('//*[@id="searchKeyword"]').send_keys(Keys.ENTER)

        imgLink = []
        for i in range(1, 3):  # 몇 개있는지 몰라 -> 낙현 질문
            try:
                img = driver.find_element_by_xpath(
                    '//*[@id="content"]/div/div[1]/div[4]/div[3]/div[3]/ul/li[1]/div[1]/div/a/img[2]')
                print(img)
            except:
                img = driver.find_element_by_xpath(
                    '//*[@id="content"]/div/div[1]/div[4]/div[3]/div[3]/ul/li[1]/div[1]/div/a/img[2]')
            imgLink.append(img.get_attribute('onclick'))

        num = imgLink[0].split("'")[1]  # 수어 번호

        baseUrl = 'http://sldict.korean.go.kr/front/sign/signContentsView.do?origin_no=' + num + '&top_category=CTE&category=&searchKeyword='

        # 진짜 url 만들기
        url = baseUrl + quote_plus(word)  # ASCII로 바꾸기

        html = urlopen(url).read()  # html 읽어서
        soup = BeautifulSoup(html, 'html.parser')  # html 분석하기


        img = soup.find_all(alt='수어동작 이미지')  # 이미지 뽑아오기
        driver.get(url)  # 동영상 뽑아오기
        video = driver.find_element_by_xpath('//*[@id="signViewForm"]/div[3]/div[1]/div[1]/a/video/source[1]')
        videoUrl = video.get_attribute('src')

        # 수어 이미지 다운
        n = 0
        for i in img:
            if n == 0:
                req['imageUrl1'] = i['src']
            elif n == 1:
                req['imageUrl2'] = i['src']
            elif n == 2:
                req['imageUrl3'] = i['src']
            else:
                req['imageUrl4'] = i['src']
            n += 1

        data = soup.find('dl', {'class': 'content_view_dis'})
        desc = data.find_all('dd')
        req['descUrl'] = desc[1].text

        print('다운로드 완료')
        driver.quit()

        req['videoUrl'] = videoUrl
        print('req',req)


        return Response(req)