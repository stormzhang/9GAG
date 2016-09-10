from django.contrib import admin
from  .models import Gist

class GistAdmin(admin.ModelAdmin):
    pass
admin.site.register(Gist, GistAdmin)
# Register your models here.
