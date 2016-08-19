# WIP-server

## Description
Django, RESTful  server to support the app

## Install
In the directory/ [virtual machine](https://virtualenvwrapper.readthedocs.io/en/latest/)
```bash
install pip3
pip3 install -r requirements.txt
python manage.py migrate --noinput
python manage.py runserver
```
## routes
### get
- [ ]  `/hot` Shows the public gists hot data from github.
- [ ]  `/fresh` Shows the public gists fresh data from github.
- [ ]  `/user/gist/comments` Shows the public gist comments from github.
- [ ]  `/user/gist/likes` Shows the public gist likes from github/server.

### post
- [ ]  `/login/<client-id>` With the github client-id the server will identify the user.
- [ ]  `/user/<client-id>/<gist-id>/?` Post a comment.
- [ ]  `/user/gists` Shows the User public gist.
- [ ]  `/user/<gist-id>/comments` Shows the user gist comments from github.
- [ ]  `/user/<gist-id>/likes` Shows the user gist likes from github.

