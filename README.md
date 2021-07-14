# happyTicketingAPI

Build Status: [![CircleCI](https://circleci.com/gh/Pofay/happyTicketingAPI.svg?style=svg)](https://circleci.com/gh/Pofay/happyTicketingAPI)

A :smile: RESTful backend for a Ticketing System

## Development

### Basic Running Configuration

First, please add a `application.properties` to your `resources` folder with this content so that you're able to run the application:

```
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url = jdbc:mysql://localhost:3306/happyTicketingDB?useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.show-sql=false
server.port=8010
```

You also need to declare a configuration file for pusher. Make sure you have a Pusher account and a created application to request for its actual values.

```
app_id = 
key = 
secret = 
cluster = 
```

