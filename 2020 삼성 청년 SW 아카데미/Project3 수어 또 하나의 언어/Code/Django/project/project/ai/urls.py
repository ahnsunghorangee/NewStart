from django.urls import path
from . import views


# namespace: app별로 url를 나누기 위함
# notices/
app_name = 'ai'

urlpatterns =[
    path('index', views.index, name='index'),
    path('textDetection', views.textDetection, name='textDetection'),
    path('objectDetection', views.objectDetection, name='objectDetection'),
    path('videoDetection', views.videoDetection, name='videoDetection'),
    path('word', views.word, name='word'),
]
