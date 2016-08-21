from django.contrib.auth.models import User
from django.core.management import BaseCommand
from django.contrib.sites.models import Site
# from emessages import console as emc
# from tips import console as tc


class Command(BaseCommand):
    @staticmethod
    def populate():
        # setting the admin user
        print("setting the admin user.")
        user = User.objects.create_superuser(
            username="admin",
            email='admin@gistx.com',
            password='123456789')
        user.full_clean()
        user.save()


        # setting the site name
        print("setting the site name.")
        site = Site.objects.all()[0]
        site.domain = 'gistx.com'
        site.name = 'WIP'
        site.save()

        # TODO: create a soical app
        print("create a soical app (url in README file).")


    def handle(self, *args, **options):
        self.populate()
