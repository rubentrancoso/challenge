# Challenge
this is the response for this [challenge](https://github.com/concretesolutions/desafio-java)

In the root folder there's some helper scripts. 

The names are self-explanatory:
	
	build.sh
	buildimage.sh
	pushimage.sh
	run.sh
	runimage.sh
	stopimage.sh
	test.sh
	testimage.sh
	viewtree.sh

The application will run on `port 8080` by default but you can change whatever parameter you like on `application.yml`

Also in the root folder was left 3 exports from POSTMAN that were used to test the application.

    challenge-cloud.postman_environment.json
    challenge-local.postman_environment.json
    challenge.postman_collection.json
  
There's also unit tests and integration tests in the package itself.

The app is running in two addresses:

    https://concrete.doteva.com/hello
    http://mail.doteva.org:8081/hello

The urls follow the specification:

    POST - /register
    POST - /login
    GET  - /profile/{uuid}




### Development Notes
#### JVM starting to slow
in the case your jvm is getting much time to start take a look on your `/etc/hosts` file if it contains your actual hostname

extracted from [here](https://dzone.com/articles/fixing-the-slow-startup-time-of-my-java-applicatio)

```
127.0.0.1   localhost myhostname.doamin
::1         myhostname.doamin
```

#### Using spring devtools while in dev
spring-boot-devtools let you continuously run your application between changes
for that, on a terminal, run the `continuousbuild.sh` script and in the IDE start your app with `bootRun` as a Program Argument. 

extracted from [here](https://dzone.com/articles/continuous-auto-restart-with-spring-boot-devtools)

#### Auditing entities with created and modified fields
jpa can help to automatically update entities when they are creted and modified 

extracted from [here](https://programmingmitra.blogspot.com.br/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html)

#### RestTesmplate Issue - java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode
whenever RestTemplate receives a 401 status code it ignores ReponseBody

extracted from [here](https://stackoverflow.com/questions/27341604/exception-when-using-testresttemplate)

#### Map YAML lists to Objects

extracted from [here](https://www.fortisfio.com/yaml-file-mapping-values-to-object-list-with-spring-boot/)

#### JWT authentication


extracted from [here](https://dzone.com/articles/implementing-jwt-authentication-on-spring-boot-api)
