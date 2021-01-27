# Imagine This Server
[![CircleCI](https://circleci.com/gh/ImagineThisUCL/ImagineThisServer.svg?style=shield&circle-token=13c4c39d3b96704a63bebcd85ae60c7036be3371)](https://app.circleci.com/pipelines/github/ImagineThisUCL)

## How to run
We are using [Intellij IDEA](https://www.jetbrains.com/idea/) to implement the backend and haven't tested it yet on other IDE. You might want to install that and get this project running correctly before you run the ImaginThisWeb project. Press run or use "Run - Run - ImaginethisserverApplication" to start this project. After that you may go back to run ImaginThisWeb project. 

## Possible Issues you might run into while running this:
If you are running this project for the first time, you might encounter:
### 1. Seting template
If you are running this project for the first time, Intellij might ask you to set the running configuration first. Choose "Spring boot" within the example templates. Setting the main class to the right location, and you should fix it. 
### 2. Error: Java switch is an preview function
You will need to swithch your language using "File - Project Structure - Project Settings - Project" and "File - Project Structure - Modules" to switch the language level into Java 13 preview.
### 3. error: package com.google.gson does not exist
Your might want to Reimport all maven projects and restart your Intellij



## How to host the backend in your local server

There is a executable JAR file named imageinethisserver-0.0.1-SNAPSHOT.jar in the repository for you to download. You can use the this file to host the backend directly. 

### 1. Make sure you have installed openjdk 1.8 or a higher version in your computer. 

### 2. Go to the directory where the jar file exists via your terminal

### 3. Run command 'java -jar imageinethisserver-0.0.1-SNAPSHOT.jar'

### 4. The backend code should be hosted in the http://localhost:8080 



## Swagger

The ImagineThis server now includes swagger into the code base. When the application is running, go to http://localhost:8080/swagger-ui.html to access the swagger-ui.

On the swagger-ui page, you can easily check the API endpoint specifications and models. The purpose of using swagger is to simplify the testing process for both front end developer and back end developer. If you're willing to test the API, follow the procedure below:

### 1. Click on the specific controller tab that you wish to test, this operation will list all testable endpoints for that controller.

### 2. Click on the endpoint that you wish to test.

### 3. Click the `Try it out` button.

### 4. Input the required field and click on the `Execute` button, and then you can see the response information.