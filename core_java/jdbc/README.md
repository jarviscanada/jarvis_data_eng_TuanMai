# Core Java JDBC Application

## Introduction
The JDBC application allows for a connection between Java applications and a 
Relational Database Management System (RDBMS). The JDBC application will connect to PostgreSQL
and allow CRUD data access patterns to interact with data. 

## ER Design
![JDBC ER Diagram](./.assets/JDBC%20ER%20Diagram.png)

## Design Patterns
Data Access Objects (DAO) is a pattern that provides an abstract interface to a database and an 
abstraction of data persistence. 
DAO allows more specific data operations without exposing details of the database. DAO creates separation 
of low level data and accessing API or operations from high level business services.

Repository Design Pattern is a pattern that is an abstraction of a collection of objects. 
Repository pattern focuses only on single table access per class. Instead of joining in the database, 
in the repository pattern you join in the code. 