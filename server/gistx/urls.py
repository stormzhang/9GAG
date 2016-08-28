"""gistx URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from rest_framework import routers
from django.conf.urls import url, include
from django.contrib import admin

from gistsapi.views import GistViewSet
from management.urls import UserViewSet


# Routers provide an easy way of automatically determining the URL conf.
router = routers.DefaultRouter()
router.register(r'gists', GistViewSet)
router.register(r'users', UserViewSet)

urlpatterns = [
    # url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    url(r'^admin/', admin.site.urls),
    url(r'^accounts/', include('allauth.urls')),
    url(r'^api/', include(router.urls)),
    # url(r'^', include('gistsapi.urls')),
    # url(r'^', include('management.urls')),
]


