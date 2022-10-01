# check-tfij-style
A set of additional checks to use with [checkstyle](https://checkstyle.sourceforge.io/index.html).

## Content
* [Content](#Content)
* [Checks](#Checks)
  - [MethodParameterAlignmentCheck](#MethodParameterAlignmentCheck)
  - [MethodParameterLinesCheck](#MethodParameterLinesCheck)
  - [MethodCallParameterAlignmentCheck](#MethodCallParameterAlignmentCheck)
  - [MethodCallParameterLinesCheck](#MethodCallParameterLinesCheck)
* [Configuration](#Configuration)
  - [Maven dependency](#Maven-dependency)
  - [Example checkstyle configuration](#Example-checkstyle-configuration)
  - [Example checkstyle maven plugin configuration](#Example-checkstyle-maven-plugin-configuration)
  - [Example checkstyle gradle plugin configuration](#Example-checkstyle-gradle-plugin-configuration)
* [Why external lib](#Why-external-lib)

## Checks

### MethodParameterAlignmentCheck

When parameters in method/constructor declaration are on multiple lines, verify if these lines are aligned.

#### Violations

##### Valid formatting

```
public SimpleClass(String foo,
                   String bar,
                   String baz) {
}
```

##### Not valid formatting

```
public SimpleClass(String foo,
            String bar,
            String baz) {
}
```

### MethodParameterLinesCheck

Verify if method/constructor arguments in declaration are either a single line or they are broken up into multiple lines, 
each on an individual line.

#### Parameters

| parameter name  | type    | default value | description                  |
|-----------------|---------|---------------|------------------------------|
| allowSingleLine | boolean | true          | allow single lines arguments |

#### Violations

##### Valid formatting

```
public SimpleClass(String foo, String bar, String baz) {
}
```

```
public SimpleClass(String foo,
                   String bar,
                   String baz) {
}
```

##### Not valid formatting

```
public SimpleClass(String foo, String bar,
                   String baz) {
}
```

### MethodCallParameterAlignmentCheck

#### Violations

##### Valid formatting

```
List.of(1, 2, 3);
```

```
List.of(1,
        2,
        3);
```

```
List.of(
    1,
    2,
    3);
```

```
List.of(1,
        2, 3);
```

```
Map.of("a", 1,
       "b", 2);
```

##### Not valid formatting

```
List.of(1,
     2,
     3);
```

```
List.of(
      1,
            2,
      3);
```

When parameters in method/constructor call are on multiple lines, verify if these lines are aligned.

### MethodCallParameterLinesCheck

Verify if call method/constructor arguments are either a single line or they are broken up into multiple lines,
each on an individual line.

#### Parameters

| parameter name | type     | default value | description                                                                                             |
|----------------|----------|---------------|---------------------------------------------------------------------------------------------------------|
| ignoreMethods  | String[] | []            | allows to ignore methods from verification, e.g. `Map.of` method, or `of` method, or `Foo` constructor. |

#### Violations

##### Valid formatting

```
int i = foo(1, 2, 3);
```

```
int i = foo(1, 
    2, 
    3);
```

##### Not valid formatting

```
int i = foo(1, 2, 
    3);
```

## Configuration

### Maven dependency

```xml
<dependency>
    <groupId>pl.tfij</groupId>
    <artifactId>check-tfij-style</artifactId>
    <version>1.2.1</version>
</dependency>
```

### Example checkstyle configuration

```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodParameterAlignment"/>
    <module name="MethodParameterLines"/>
    <module name="MethodCallParameterAlignment"/>
    <module name="MethodCallParameterLines">
      <property name="ignoreMethods" value="Map.of"/>
    </module>
  </module>
</module>
```

### Example checkstyle maven plugin configuration

To use check from this library with `maven-checkstyle-plugin`,
you have to add the library as a maven dependency to the plugin.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.2.0</version>
    <configuration>
        <configLocation>src/main/resources/checkstyle.xml</configLocation>
        <encoding>UTF-8</encoding>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
        <linkXRef>false</linkXRef>
    </configuration>
    <executions>
        <execution>
            <id>validate</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>pl.tfij</groupId>
            <artifactId>check-tfij-style</artifactId>
            <version>1.2.1</version>
        </dependency>
    </dependencies>
</plugin>
```

### Example checkstyle gradle plugin configuration

```
plugins {
    java
    checkstyle
}

dependencies {
    checkstyle("pl.tfij:check-tfij-style:1.2.1")
}
```

## Why external lib

The checkstyle is a powerful library that has many users.
The success of this library meant that any changes require time-consuming analyzes and discussions, e.g. whether the change is backward compatible, whether is it consistent with all other checks, how to eventually deprecate this check, etc.
An example is the [https://github.com/checkstyle/checkstyle/issues/9118](https://github.com/checkstyle/checkstyle/issues/9118) issue where the proposal and PoC was presented two years ago and there is still no decision about future steps.
For this reason, I decided to release my own checks as a separate library that can be used together with the core checkstyle.
