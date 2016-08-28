import json

from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render, render_to_response
# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
# Serializers define the API representation.
from rest_framework import serializers, viewsets

# ViewSets define the view behavior.
from .models import Gist


# Serializers define the API representation.
class GistSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Gist
        fields = ('git_id',
                  'self_url',
                  'title',
                  'owner_name',
                  'owner_id',
                  'recommended_gists',
                  'script_url',
                  'comments',
                  'created_at',
                  'updated_at',
                  )


class GistViewSet(viewsets.ModelViewSet):
    def filter_queryset(self, queryset):
        if not self.request.GET:
            return super(GistViewSet, self).filter_queryset(queryset)
        query_string = self.request.GET
        if query_string['category'] == 'fresh':
            return super(GistViewSet, self).filter_queryset(queryset.order_by('updated_at').reverse())
        if query_string['category'] == 'hot':
            return super(GistViewSet, self).filter_queryset(
                queryset.filter(comments__gte=0).order_by('comments').reverse())
        if query_string['category'] == 'trending':
            pass

        return super(GistViewSet, self).filter_queryset(queryset)

    queryset = Gist.objects.all()
    serializer_class = GistSerializer


@login_required(login_url='/')
def base(request):
    # TODO: Json response
    return HttpResponse("Hello.")

	