# WIP-server

## Description
Django, RESTful server to support the gists metadata.

## Install
In the directory/ [virtual machine](https://virtualenvwrapper.readthedocs.io/en/latest/)

* Create a virtual env (optional)

        mkvirtualenv gistx-env
        
        workon gistx-env

* Install 

	install.cmd
    
* Or    
	* Install requirements

			install pip3
	        pip3 install -r requirements.txt


    
	* Create DB

	        python manage.py migrate

	* Create Superuser
	
	        python manage.py createsuperuser      
		
	* Run server

	        python manage.py runserver
        
* Register the social application
 
	`http://localhost:8000/admin/socialaccount/socialapp/`
		
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

