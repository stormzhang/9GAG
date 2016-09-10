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
    PAGE_LIMIT = 10

    def filter_queryset(self, queryset):
        # Getting the arguments.
        query_string = self.request.GET
        if 'page' in query_string:
            # this why the user scrolls back in time but in the relevant updated gists (lower then).
            queryset = queryset.filter(updated_at__lt=query_string['page'])
        # Setting the category.
        if 'category' in query_string:
            if query_string['category'] == 'fresh':
                # base behavior
                pass
            elif query_string['category'] == 'hot':
                # see the most commented.
                queryset = queryset.filter(comments__gte=0)
            elif query_string['category'] == 'trending':
                # TODO: implament
                queryset = queryset.filter(comments__gte=10)

        # hack paging with updated_at and PAGE_LIMIT
        queryset = queryset.order_by('updated_at').reverse()[:self.PAGE_LIMIT]
        return super(GistViewSet, self).filter_queryset(queryset)

    queryset = Gist.objects.all()
    serializer_class = GistSerializer


# Send user to register.
@login_required()
def home(request):
    username = request.user.username
    # Authenticated user.. say hello.
    # TODO: go back to app.
    return HttpResponse("Hello {}".format(username))
