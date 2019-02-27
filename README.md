# Web-Server

This is a HTTP Webserver that can handle upto a maximum concurrent request of
say x which is configurable in conf/config.xml.

It doesnt have any compile time dependency. At runtime in order to run Test cases you
would need JUnit 4.12 jars in your classpath.

You can directly download the bin,conf,lib directory and invoking start.bat in bin will get the server up and running on port specified in config.xml and it would be ready to serve the request for resources placed relative to the serverhome directory and webroot mentioned in config.

This project can be directly imported in your choice of Java based IDE and you are good to go without any dependency say for Logging java inbuilt Logging Framework is used
I have also create batch script to spin up the server easily and have implemented xml config parser in 
order to have all the setting at one place and easy to tune.
This project has references from https://github.com/ibogomolov/WebServer  
Further few good reads to get started can be https://medium.com/@ssaurel/create-a-simple-http-web-server-in-java-3fc12b29d5fd

