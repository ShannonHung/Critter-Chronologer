# Lesson 3 Final Project: Critter Chronologer 
###### tags: `Udacity`


# 01 Project Instructions
**Critter Chronologer**
Design and implement the data model for Critter Chronologer, a Software as a Service application that provides a scheduling interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.

**Starter Code**
You can access and download the starter code here. Instructions for setting up your system for the project are included in the project README.

**Task 1: Configure Properties**
The starter project contains a blank `application.properties` file. Provide the appropriate connection url and credentials for Spring to configure your external db as the primary data source. If you want Spring to automatically generate schema for you, set values for initialization-mode and ddl-auto to customize the way Spring generates or updates your schema.

**Task 2: Configure Unit Tests**
Create a new `application.properties` file in your test/resources directory and configure it to use an H2 in-memory database for unit tests. The starter project includes a set of functional tests to help you make sure you’ve met the requirements. More information can be found about the unit tests and how to run them in the [Testing section of the README](https://github.com/udacity/nd035-c3-data-stores-and-persistence-project-starter#testing). They will fail for now because the starter project controller methods are incomplete, but you can use them to check your progress as you go.

The starter project also includes a Postman collection under src/main/resource/Udacity.postman_collection.json. Importing this collection into the Postman application will provide you with a variety of requests you can submit. You should use them to confirm functionality of your external data source as you work. You will also find information about the Postman collection in the [project README.](https://github.com/udacity/nd035-c3-data-stores-and-persistence-project-starter#postman)

> A successful Postman request
![](https://i.imgur.com/6zvuT5g.png)


**Task 3: Design Entities To Represent Your Data**
You’ll need to decide how to persist your information. To complete this project, you will need to store the following:
1. Two different kinds of users - Employees and Customers.
2. Any type of pet, such as cats, dogs, lizards, hedgehogs, toucans, etc. We don't want to discriminate against owners of odd pets!
3. Schedules that indicate one or more employees will be meeting one or more pets to perform one or more activities on a specific day.

As you consider your design, think about how you want these entities to be stored in your database. Be deliberate about whether you are representing data via inheritance or composition and use the tools that Spring and Hibernate provide to create the appropriate tables to relate your data. Remember that the Data Transfer Objects represent the structure of request and response data, but do not have to represent the structure of your persistence model. The Data Transfer Objects are represented by the [NAME]DTO.java files in the starter code.

**Task 4: Create Tables in your Database**
There are a variety of ways to create the tables your program will use. Hibernate can automatically generate them when you launch the application, or you may use a schema.sql to manually define and create the tables. Automatic generation is a simpler, quicker solution and recommended for this project. Just as some of your furry clients would choose to Hibernate, we suggest the same! That being said, you may choose any method you wish.

Once your tables are constructed, review the schema in MySQL workbench or in another tool of your choice. Hibernate’s HQL and Spring Data’s JPQL allow you to work with Java objects, but it’s still useful to look at the SQL schema verify your Entity design produces the type of tables you want.

**Task 5: Create a Data Access Layer**
It’s important to isolate requests to the database from the business logic of our application in order to minimize the impact of changes. In other words, we want a pet door to let your marmot, wombat, and capybara reach the database while keeping your product owner out. There are a variety of ways to do this. You can use the Data Access Object pattern, Spring Data Repositories, or some combination thereof to create a single DAO or Repository for each type of Entity you created in the previous step. These will handle persistence requests pertaining to those Entities.

For this project, you may elect to use any of the persistence strategies discussed in this course. That could mean using a JdbcTemplate to execute native queries, using Hibernate and EntityManager, or using Spring Data JPA. Regardless of your approach, be sure to encapsulate persistence logic inside your Data layer.

**Task 6: Create a Service Layer**
This project is designed so that the majority of the work can be performed by the Data layer, but you still need another layer that can combine calls to multiple DAOs or Repositories. Create Service objects that can handle requests from the Controller layer and make the appropriate calls to the Data layer. Avoid exposing the starter code’s DTO objects to your Service layer. The Service layer should work with Entities or primitives, but not DTOs.

**Task 7: Update the Controller to use Services**
Inject Service references into the provided Controller classes and use those Services to complete the methods in the Controller. Once you have completed each request, test it using the JUnit tests and Postman.

**Task 8: Review and Refactor**
Your project should now support the following workflow:
1. Create a new customer.
2. Create a pet for that customer.
3. Create an employee.
4. Update the employee’s schedule.
5. Find out which employees with the right skills are available on a given date.
6. Schedule one or more employees to do a set of activities with one or more pets.
7. Look up currently scheduled events for an employee, a pet, or a customer.

If you can do all these things, you should be able to send all the requests from the Postman collection, and your unit tests should pass.

Once both of these things are working, take another pass through your project and look for opportunities to clean things up. Do you need to add comments? Are you handling exceptional cases? Would anything be easier if you changed your data model? Do you have clear separation between your layers? Is there any functionality not requested by the Controllers that would be nice to add?


