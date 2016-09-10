from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render, render_to_response


# Create your views here.


@login_required()
def complete(request):
    return HttpResponse("Hello.")
