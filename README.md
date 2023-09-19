# BookService
BookService is a web based back-end service (API) in java that serves calls for managing a book collection. It allows:
1. Creation of a new book
2. Listing of all books
3. Searching for a book by any words in the title
4. Posting a new rating for a book

The book creation requires a title, existed author, type(i.e. Mystery, Fantasy, Horror) and optionally a rating (1-5).
When the service responds with book(s) details, with respect to book rating, it provides its average.
Service uses PostgresSql DB to store books and authors.

## Usage notes
- Before book creation it requires to create author, author id is mandatory field for a new book.
- By default `find books` endpoint returns 100 records per page. Books are sorted by rate and sorting cannot be changed (by requirement).  
- Book rating formula : services stores total number of rates and sum all rating points, divides total points on number of rates and throws remainder.  

## Tech notes

- Build script uses arm64 docker image `arm64v8/openjdk:18`. In case of using x86-64 platform it requires to change image to `openjdk:18` in the build.gradle. 
- Service is written using Hexagonal architecture. Code is splitted by the layers : Domain, Application, Adapter. 
- Project has three source sets : Code, Tests, Integration Tests


## Run application

`./gradlew composeUpDb` starts database only

`./gradlew bootRun` starts standalone service that connects to db container

## Run application in container

`./gradlew composeUp -PwithBuild` builds the service image and start application with database in containers (Execution command without flag `withBuild` runs image that was built before).

`./gradlew composeDown` stops the containers.

`./gradlew cleanDbVolume` removes database state files.

## Swagger

When application runs the descriptions are be available at the path [/v3/api-docs](http://localhost:8080/v3/api-docs) and [swagger](http://localhost:8080/swagger-ui/index.html)
