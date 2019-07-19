# happyTicketingAPI
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

