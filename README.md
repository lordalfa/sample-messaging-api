#A Sample messaging REST API using Spring Boot
====================

## Introduction

This is a Sample application to demonstrate the use of [Spring Boot](http://projects.spring.io/spring-boot/).
I've been proposed a challenge for a work position asking for a Twitter-like REST API with the following features:

* create a simple user account
* follow another user
* fetch the list of the users someone is following
* fetch the list of the users following someone
* post a message
* list a user's messages and optionally messages from the followed people
* be able to do a simple search on messages using a search term
* secure the sensible endpoints with the use of an API TOKEN passed via query string.
* provide a script or something in order to test out the endpoints

Using:

* the spring framework
* spring jdbc named queries template
* mysql database

This application exposes these features in a secured manner, using the requested technologies, and trying to use 
all the best practices you can apply to REST API development, and development using the spring framework.

Due to the lack of good java + mysql hosting services (with a budget..) I came up with the idea of the 
standalone java application with a bundle of dependencies included.
Also, I included a [Swagger](http://swagger.io/) console, so that the API is easily testable (way better than using a simple script).
**Keep in mind that in order to be self contained, and directly runnable,the application could not package a mysql server.
So, as a default, the application uses an internal H2 in memory database, but can be forced to run versus your own mysql server 
by simply using command line parameters (see below)**

Also, API should be backed by HTTPS protocol for every call, but in order to keep the installation package simple, I relaxed on this topic.

## How to install & run

Being a maven application, packaging and running is straightforward
```
  $ mvn package
  $ java -jar target/<modulename>.jar
```

If you want to run without packaging, you can use the Spring Boot goals
```
  mvn spring-boot:run
```

The application uses an H2 in memory database, if you want to use a mysql database do this:
```
java -jar target\TEChallenge-0.1.jar --spring.profiles.active=mysql --spring.datasource.url=jdbc:mysql://<yourhost>:<yourport>/<yourdbname> --spring.datasource.username=<username> --spring.datasource.password=<password>
```

This activates the mysql profile, forcing the database to be initialized using mysql compliant sql script.

## Testing the API

In order to test the REST API, simply run the sample application and point your browser to your local [Tomcat](http://localhost:8080) 
which is embedded in the sample application.

there are a bunch of test users preloaded, each with their follower list set, and a few messages (usernames test1 to test4, password is 'password' for all)

You will see a customized Swagger dashboard with 3 API groups:

- Authentication API
- User Management API
- Messaging API

If you're familiar with Swagger, you know what to do, otherwise here's a brief walkthrough:

First you have to obtain an **API TOKEN**. In order to do so, you can create your own user, or just use a preloaded one.
To create a user use the Authentication API -> create menu (expand Authentication API folder, then expand the create API folder).
You can invoke the REST endpoint by filling the required field and pressing **TRY OUT** button.
After creating the user, you will receive an API TOKEN you can use. Just paste it in the form field in the upper right corner
which obviously reads "api token".
With that set, EVERY test you perform on the other APIs will be authenticated with the user owning the token.
If youu don't use a token, you will be prompted a 401 - Unauthorized response to every call.

Keep in mind that the User API and Message API are secured (you will need a token set) while the Authentication API is not 
(you can call those endpoints without using a token, for quite obvious reasons).

I explicitly forbode **ALL WRITE** operations performed on a given user by another user.
In fact, in order to comply with REST resource access, the username of the user you're performing an operation for, 
is ALWAYS included in the call itself (specifically as a Path Variable).
So, for instance, to make user1 follow user2 you would call (POST method)
```
http://localhost:8080/rest/api/users/user1/follow/user2?api_token=<yourtoken>
```
If you make this call using a token that doesn't belong to **user1** (the user being written) you will receive a *403 Forbidden* response.
While reading the list of followers of another user is definitely possible.

I felt like messages should be readable by anyone, not just the poster and the followers, so I removed the security check on the getMessage API that forced the logged user to ask for its own messages only.


