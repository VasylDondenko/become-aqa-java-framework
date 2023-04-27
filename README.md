## Properties Hierarchy
1. Command line -D properties
2. XML properties
3. dev.properties file

***If there is no necessary property provided as a system variable, not in the xml (if xml used to run tests) and not inside ***dev.properties*** - an Exception will be thrown.***
### Run variations:

`\ == or`
1. command line no properties - `mvn test`
2. command line with **-Denv=dev\qa** - `mvn test -Denv=qa`
3. command line with **-Denv=dev\qa** but ***dev\qa.properties*** doesn't have **name\age** property - `mvn test -Denv=qa`
4. command line with **-Dname=SomeName** or **-Dage=23432** - `mvn test -Dname=SomeCommandLineName`
5. command line with **-Denv=dev\qa** and **-Dname\age=SomeValue** - `mvn test -Denv=qa -Dname=SomeCommandLineName`
6. ***qa.xml***
7. ***dev.xml*** which doesn't contain **age property**

#### Results:
1. Properties from ***dev.properties***
2. Properties from ***qa.properties***
3. Exception "No Properties found. Please check the documentation for instructions to setting up testing data"
4. Age from ***dev.properties***, name from command line
5. Age from ***qa.properties***, name from command line
6. Properties from ***qa.xml***
7. Age from ***testng.xml***, Name from ***dev.properties***


If no system variables are provided - default `env` is `dev`

`env=qa` is the second option

System variables override any other variables `mvn test -Denv=qa -Dname=SomeName`, so the age will be taken from qa.properties and name will be "SomeName"

`mvn test` - Default env is set to `dev`. If there is no necessary property provided as a system variable, not in the xml (if xml used to run tests) and not inside ***dev.properties*** - an Exception will be thrown.
`mvn test -Denv=qa` - If there is no necessary property inside ***qa.properties*** - an Exception will be thrown.


# DOCKER
docker network create aqa-network
docker network connect aqa-network keycloak-aqa

docker build -t become-aqa-java .
docker run --name become-aqa-java --volume `pwd`/reports:/app/reports --network aqa-network become-aqa-java test -DargLine="-Dkeycloak.baseUrl=http://keycloak-aqa:8080"
