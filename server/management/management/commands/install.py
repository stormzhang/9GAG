
from django.contrib.auth.models import User
from django.core.management import BaseCommand
from django.contrib.sites.models import Site
from allauth.socialaccount.models import SocialApp
from gistx.settings import GITHUB_APP_ID, GITHUB_API_SECRET
# from emessages import console as emc
# from tips import console as tc


class Command(BaseCommand):
    @staticmethod
    def install():
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

        # setting the site Social applications
        print("setting the Social applications .")
        social_app = SocialApp()
        social_app.provider = "github"
        social_app.name = "github provider"
        social_app.client_id =GITHUB_APP_ID
        social_app.secret = GITHUB_API_SECRET
        social_app.save()
        social_app.sites.add(site)
        social_app.save()


    def handle(self, *args, **options):
        self.install()
