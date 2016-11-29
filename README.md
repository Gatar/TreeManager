# TreeManager
TreeManager is WebAPI allowing to manage a tree structure in internal database.

## Description

WebAPI provides functionalities for manage tree structure containing an integer value in each node. Tree structure could be saved in H2 internal database managed by JPA. During the operations on tree it exist as objects in memory. Communication with client has been assured via RESTfull.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Application is designed to work on Tomcat server. 


### Installing

To build .war file You must provide database to make integration test (od turn off them), as is described below.
You can use IDE or Maven in command line for make package:

```
maven package
```

## Running the tests

WebAPI has tests:
- Unit tests for all Service layer and tree manage logic

TreeClient, console client for this WebAPI, provides integration tests, which cover WebAPI controllers layer and H2 database.

Running tests 
```
maven test
```

### Break down into end to end tests

Test needs no additional resources. An example test tree structure used in application has been shown below.
![TreeSchema](https://github.com/Gatar/TreeManager/blob/master/Schema%20test%20tree.png)

## How to use it?

### Start

Default package file is .war, which could be send directly to server. By default database contains only one root node with id=1 and value=1.

### Get the tree

1. Tree could be received as JSON object parsed from NodeDTO class.
 *server_path/**__treemanager/getTree__*

### Tree Manage options

WebAPI provides series of possible modifications tree structure, which can be accesed via REST requests. 
1. Add new leaf to exising node, require node id in path
 *server_path/**__treemanager/addLeaf/{nodeId}__*

2. Move branch (node with children). Require POST request with *MoveBranchDTO* JSON parsed object containing node coordinates 
*server_path/**__treemanager/switchBranch__*

3. Remove node with children. Require node id in path
*server_path/**__treemanager/removeWithChildren/{nodeId}__*

4. Remove node without children. Children of removed node are connecting to parent of removed node. Require node id in path
*server_path/**__treemanager/removeWithoutChildren/{nodeId}__*

5. Change node value. Require POST request with _ChangeNodeValueDTO_ JSON parsed object containing node id and new value
*server_path/**__treemanager/changeValue__*

### Other options
Other options not influenting directly on existing tree structure.
1. Save tree into internal H2 database as Adjacency List.
*server_path/**__treemanager/saveToInternalDatabase__*

2. Load tree from database into memory as object structure.
*server_path/**__treemanager/loadFromInternalDatabase__*

3. Load example tree into memory for test purpose.
*server_path/**__treemanager/prepareForIntegrationTest__*

## Built With

* [Spring](https://projects.spring.io/spring-boot/) - The web framework used, version 1.4.1
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](http://junit.org/junit4/) - Unit testing framework
* [H2 Database](http://www.h2database.com/html/main.html) - Relational database supported by Spring



