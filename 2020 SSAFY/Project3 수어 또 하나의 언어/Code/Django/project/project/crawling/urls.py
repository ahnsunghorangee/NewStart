from django.urls import path
from . import views

app_name = 'crawlings'

urlpatterns =[
    path('word', views.word_insert, name='word_insert'),
]
