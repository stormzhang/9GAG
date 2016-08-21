from django.contrib.auth.models import User
from django.core.management import BaseCommand
from django.contrib.sites.models import Site
# from emessages import console as emc
# from tips import console as tc


class Command(BaseCommand):
    @staticmethod
    def populate():
        pass



    def handle(self, *args, **options):
        self.populate()
