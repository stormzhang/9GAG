from django.contrib.auth.models import User
from django.core.management import BaseCommand
from django.contrib.sites.models import Site
# from emessages import console as emc
# from tips import console as tc
from requests import get as _get
from pprint import pprint
from gistsapi.models import Gist

class Command(BaseCommand):
    @staticmethod
    def populate():
        request = _get(r"https://api.github.com/gists")
        gists = request.json()
        for item in gists:
            Gist.save_github_api(item)


    def handle(self, *args, **options):
        self.populate()
