# Core Java Twitter Application

## Introduction
The Twitter Application is an application that can post/show/delete Twitter Tweets via Twitter REST API.
This Twitter App is a simple implementation using the Tweet object to CR~~U~~D (create, read, ~~update~~, delete)
tweets from the CLI (command line interface). This project teaches the fundamentals of using RESP APIs, 
Twitter API integration, and Java 8. Also, learned from this project was the usage of testing with JUnit and
Mockito, and Json serialization/ deserialization, while thoroughly covering class dependencies and using 
Spring Framework and Spring IoC to manage dependencies. 

## Quick Start
To ensure that this application will work in the CLI. The required Twitter Consumer API keys and Access
tokens for your account are correctly passed in as this application uses them to create, show, or delete
tweets.

The required keys must be added as environment variables:

Environment Variable | Twitter Key
--------|--------
consumerKey | Twitter Consumer API Key
consumerSecret | Twitter Consumer API Secret Key
accessToken | Twitter Access Token
tokenSecret | Twitter Access Token Secret

To package the application, you can use `mvn package` to build the project. This will pack up the project in 
a Jar file located in the target direct with the target and project name.

The application has three main methods which can be ran: Create, Show, Delete.
Usage of the application: `TwitterCLIApp post|show|delete [options]`

- Create tweet
  - text has to be 140 characters long or less
  - latitude has to be within the range of -90.0 to +90.0
  - longitude has to be within the range of -180.0 to +180.0
```
Create Usage: TwitterCLIApp "post" "Tweet text under 140 characters" "latitude:longitude"

Example: TwitterCLIApp "post" "Create Tweet Test" "-1.0:1.0"
```

- Show tweet
  - requires the id string of a tweet
```
Show Usage: TwitterCLIApp "show" "tweet id string"

Example: TwitterCLIApp "show" "74927482"
```

- Delete tweet
```
Delete Usage: TwitterCLIApp "delete" "tweet id strings"

Example: TwitterCLIApp "delete" "75927482, 29348592, 42921829"
```

## Design
![Twitter UML Diagram](./.assets/Twitter%20UML%20Diagram.png)

- explain each component(app/main, controller, service, DAO) (30-50 words each)

This application has four main components, working from the bottom-up approach, DAO > Service > Controller >
App Main.

- `TwitterDao` - The Data Access Layer, handles the REST API data exchange with the external connection. 
The DAO calls the HttpHelper to send HTTP requests and get HTTP responses with a given URI.
- `TwitterService` - The service layer that handles the business logic of the application. Checking for 
correct format of arguments before sending the request to the DAO. The business logic that is handled are 
checking that the character length of the text is less than 140 characters, as well as ensure that the 
latitude and longitude are within their acceptable ranges.
- `TwitterController` - The controller layer manages the user's requests and sends it to the service layer, 
while also sending the response back to the user. The controller will take the request made by the user and 
call the service layer to pass over the data.
- `TwitterCLIApp` - The App Main class creates and initializes the other components and dependencies. The 
main is the class handling declaration of the required dependencies for all components to accept user 
commands and call the controller and pass the data over, 

## Spring 
Initially, the Twitter app had all dependencies setup manually in the main method. However, this will be 
complex and complicated in large applications when there are many components that need to be handled. 
To ensure that there are little dependency issues, Spring Framework was implemented in the Twitter application 
to manage the dependency problem. 

The first approach of Spring Framework `TwitterCLIBean` in the application was the use of building a configuration file that 
used @Bean and manually setting up each dependency. However, although Spring helps manage the dependency, 
there is still the problem of having to set up the dependencies manually. 

The second approach of Spring Framework `TwitterCLIComponentScan` was using the Spring's component scan to
be able to automatically search for the correct components and dependencies for each layer, setting it up 
automatically with constructors denoted with @Autowired. 

Finally, the last installment of the Spring Framework was the `TwitterCLISpringBoot` which utilizes Spring 
Boot's implementation to surpass and eliminate the boilerplate configurations required for setting up a Spring 
Application. 


## Models 
The models: Coordinates, Entities, Hashtag, Tweet, UserMention used in this application were implemented with
POJOs. These models are data of the Tweet object that is required in tweets. The data is read as JSON, however,
we will be converting JSON to objects, and objects to JSON. 

## Improvements
- Allow users to enter in their Consumer keys and access token keys easier
- Implement a way to edit tweets that are already posted
- Make location optional, as not everyone wants to include location