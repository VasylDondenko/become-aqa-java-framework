## Properties Hierarchy
1. Command line -D properties
2. XML properties
3. dev.properties file

### Run variations:

`\ == or`
1. command line no properties - `mvn test`
2. command line with **-Denv=dev\qa** - `mvn test -Denv=qa`
3. command line with **-Denv=dev\qa** but ***dev\qa.properties*** doesn't have **name\age** property - `mvn test -Denv=qa`
4. command line with **-Dname=SomeName** or **-Dage=23432** - `mvn test -Dname=SomeCommandLineName`
5. command line with **-Denv=dev\qa** and **-Dname\age=SomeValue** - `mvn test -Denv=qa -Dname=SomeCommandLineName`
6. ***testng.xml***
7. ***testng.xml*** which doesn't contain **age property**
8. ***qa.xml***
9. ***dev.xml*** which doesn't contain **age property**

#### Results:
1. Properties from ***dev.properties***
2. Properties from ***qa.properties***
3. Exception "No Properties found. Please check the documentation for instructions to setting up testing data"
4. Age from ***dev.properties***, name from command line
5. Age from ***qa.properties***, name from command line
6. Properties from ***testng.xml***
7. Age from ***testng.xml***, Name from ***dev.properties***
8. Properties from ***qa.xml***
9. Age from ***testng.xml***, Name from ***dev.properties***


If no system variables are provided - default `env` is `dev`

`env=qa` is the second option

System variables override any other variables `mvn test -Denv=qa -Dname=SomeName`, so the age will be taken from qa.properties and name will be "SomeName"

`mvn test` - Default env is set to `dev`. If there is no necessary property inside ***dev.properties*** - an Exception will be thrown.
`mvn test -Denv=qa` - If there is no necessary property inside ***qa.properties*** - an Exception will be thrown.