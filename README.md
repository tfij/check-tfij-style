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
  - [Example maven plugin configuration](#Example-maven-plugin-configuration)

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
    <version>1.1.0</version>
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
        <module name="pl.tfij.checktfijstyle.checks.MethodParameterAlignmentCheck"></module>
        <module name="pl.tfij.checktfijstyle.checks.MethodParameterLinesCheck"></module>
    </module>
</module>
```

### Example maven plugin configuration

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
            <version>1.1.0</version>
        </dependency>
    </dependencies>
</plugin>
```
