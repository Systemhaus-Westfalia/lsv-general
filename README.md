# ADempiere LSV

A Location for El Salvador

## Requirements
- [JDK 11 or later](https://adoptium.net/)
- [Gradle 8.0.1 or later](https://gradle.org/install/)


### Packages Names
All implementation has some default packages like `org.shw.lsv`

```Java
org.shw.lsv.setup
org.shw.lsv.util
org.shw.lsv.util.support
org.shw.lsv.util.support.findex
```

### Model Deploy class
The main deploy class `org.shw.lsv.setup.CreateFindex` used for deploy default provider [Findex](https://findex.la/)

### Functionality Definition
All abstraction for this functionality is in `org.shw.lsv.util.support`, these files has two files:

- `IDeclarationProvider`: This interface must be implemented by provider, the main functionality for this is connect with provider, implement security and endpoints for publish documents
- `IDeclarationDocument`: A representation of document to publish, the main method to implement is `getDocumentValues()`, this method allows return a map with all values to publish (assume that is a JSON body).


### Functionality Implementation
A first implementation for this is own package`org.shw.lsv.util.support.findex`, this package have some classes:

- `Findex`: Main connector implementation
- `Invoice`: A implementation for invoice document

## Binary Project

You can get all binaries from github [here](https://central.sonatype.com/artifact/io.github.adempiere/location-el-salvador/1.0.0).

All contruction is from github actions


## Some XML's:

All dictionary changes are writing from XML and all XML's hare `xml/migration`


## How to add this library?

Is very easy.

- Gradle

```Java
implementation 'io.github.adempiere:location-el-salvador:1.0.0'
```

- SBT

```
libraryDependencies += "io.github.adempiere" % "location-el-salvador" % "1.0.0"
```

- Apache Maven

```
<dependency>
    <groupId>io.github.adempiere</groupId>
    <artifactId>location-el-salvador</artifactId>
    <version>1.0.0</version>
</dependency>
```