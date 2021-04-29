Github Profile Application
==================================

This application fetch the github profile data through graphQL and display it to you.


Config Github personal access token
--------------------------------------
To fetch the data app required your github `Personal access token`. 
You can generate it from github `Settings > Developer settings > Personal access token`

App fetch the token from system environment variable called `GITHUB_API_TOKEN`. 
So you should set your token to that env variable before building the application.