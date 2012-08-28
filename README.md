#  Sencha and Clojure clickstart

This clickstart sets up a database, build service, repository and a basic Sencha and Clojure ORM application.
All built by maven or leiningen. 

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/CloudBees-community/sencha-clojure-maven/master/clickstart.json"><img src="https://s3.amazonaws.com/cloudbees-downloads/clickstart/clickstart-now.png"/></a>

Launch this clickstart and glory could be yours too ! Use it as a building block if you like.

You can launch this on Cloudbees via a clickstart automatically, or follow the instructions below. 

# Deploying manually:

## To build and deploy this on CloudBees, follow those steps:

Create application:

    bees app:create MYAPP_ID

Create database:

    bees db:create -u DB_USER -p DB_PASSWORD DBNAME

Bind database as datasource:

    bees app:bind -db DBNAME -a MYAPP_ID -as ClojureDB

Create a new Maven project in Jenkins, changing the following:

* Add this git repository (or yours, with this code) on Jenkins
* Also check "Deploy to CloudBees" with those parameters:

        Applications: First Match
        Application Id: MYAPP_ID
        Filename Pattern: target/*.war

* Optionally change the application container to Java EE, if you wish to deploy on JBoss. 

## To build this locally:

In the sencha-clojure-maven directory, open a command line, and invoke maven by typing "mvn package" to build the war file.
Alternatively, to build with leiningen, in the "backend" directory, call "lein ring war" to build a .war.
Then deploy it on cloudbees typing:

    bees app:deploy -t tomcat -a MYAPP_ID target/*.war

Or, for JBoss:

    bees app:deploy -t jboss -a MYAPP_ID target/*.war

## To deploy this locally:

You can try things out by changing the DB config to a simple H2 database stored in a file by editing backend/src/main/clojure/sencha/database.clj, to do so, set the db binding as follows:

    (def db testing)
    
And then, let leiningen start your test server with:

    lein ring server

If you wish to deploy the .war itself, make sure you have a MySQL database bound to java:comp/env/jdbc/ClojureDB, and then deploy in your favorite container.

## Note about Leiningen:

This is originally a maven project, but for your convenience, a project.clj file was added. For things to work properly with leiningen, while not altering the maven directory structure, there is a symlink from "backend/src/sencha" to "backend/src/main/clojure/sencha" which may not work on some operating systems.

## Note before deploying this on JBoss 7:

There are configuration changes to do in the following file, comments will indicate which lines to change and how.

    frontend/src/main/webapp/WEB-INF/web.xml
    
## Note about Databases:

In the backend/src/main/clojure/sencha/database.clj file, there are two available configurations, along with a create-drop database setup. You may want to change the database setup before going into production, and please note that the testing database is not suitable for deployment on CloudBees.
