
@pip3 install -r requirements.txt%*
@python manage.py makemigrations%*
@python manage.py migrate%*
@python manage.py install%*
@python manage.py populate%*
@start  http://localhost:8000/admin/login/ *
@python manage.py runserver%*
