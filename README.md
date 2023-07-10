# check-tfij-style
A set of additional checks to use with [checkstyle](https://checkstyle.sourceforge.io/index.html).

## Content
* [Content](#Content)
* [Checks](#Checks)
  - [FieldsCountCheck](#FieldsCountCheck)
  - [MethodEmptyLinesCheck](#MethodEmptyLinesCheck)
  - [MethodParameterAlignmentCheck](#MethodParameterAlignmentCheck)
  - [MethodParameterLinesCheck](#MethodParameterLinesCheck)
  - [MethodCallParameterAlignmentCheck](#MethodCallParameterAlignmentCheck)
  - [MethodCallParameterLinesCheck](#MethodCallParameterLinesCheck)
  - [NameLengthCheck](#NameLengthCheck)
* [Configuration](#Configuration)
  - [Maven dependency](#Maven-dependency)
  - [Example checkstyle configuration](#Example-checkstyle-configuration)
  - [Example checkstyle maven plugin configuration](#Example-checkstyle-maven-plugin-configuration)
  - [Example checkstyle gradle plugin configuration](#Example-checkstyle-gradle-plugin-configuration)
* [Why external lib](#Why-external-lib)

## Checks

### FieldsCountCheck

Since 1.4.0

Verify if number of fields (all: static, not static, final, and not final) in the class is not above threshold.

This check can also verify number of fields not initialized on declaration.
It can be useful when you use [lombok](https://projectlombok.org/), and you don't declare constructors explicitly so there is no argument count check on the class constructor.

#### Configuration

```xml
<module name="FieldsCount"/>
```

##### Parameters

| parameter name                           | type | default value | description                                                                |
|------------------------------------------|------|---------------|----------------------------------------------------------------------------|
| maxFieldsCount                           | int  | 12            | threshold for number of fields in the class                                |
| maxUninitializedOnDeclarationFieldsCount | int  | 7             | threshold for number of not initialized on declaration fields in the class |

#### Violations

```
private static class SampleClass {

    private final String f1 = "1";
    private final String f2 = "2";
    private final String f3 = "3";
    private final String f4 = "4";
    private final String f5 = "5";
    private final String f6 = "6";
    private final String f7 = "7";
    private final String f8 = "8";
    private final String f9 = "9";
    private final String f10 = "10";
    private final String f11 = "11";
    private final String f12 = "12";
    private final String f13 = "13"; // violation, default thrashold of all fields is 12
}
```

```
private static class SampleClass {

    private final String f1 = "1";
    private final String f2 = "2";
    private final String f3;
    private final String f4;
    private final String f5;
    private final String f6;
    private final String f7;
    private final String f8;
    private final String f9;
    private final String f10; // violation, default thrashold of fields not initialized on declaration is 7
    private final String f11;
    private final String f12;
    private final String f13;
}
```

### MethodEmptyLinesCheck

Since 1.5.0

Verify if the method or constructor contains empty lines.
This check is to promote small methods with a single responsibility.

Empty lines in methods are a bad practice.
Usually, an empty line in a method separates logical parts.
If the method has several logical parts, it is worth dividing it into smaller ones and expressing these logical parts in the method names.

#### Configuration

```xml
<module name="MethodEmptyLines"/>
```

#### Violations

##### Valid formatting

```
public void foo() {
        System.out.pringline("1");
        System.out.pringline("2");
        System
                .out
                .pringline("3");
}
```

##### Not valid formatting

```
public void foo() {

        System.out.pringline("1");
        System.out.pringline("2");
        
        System
                .out
                .pringline("3");
        
}
```

### MethodParameterAlignmentCheck

Since 1.0.0

When parameters in method/constructor/record declaration are on multiple lines, verify if these lines are aligned.

#### Configuration

```xml
<module name="MethodParameterAlignment"/>
```

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

Since 1.0.0

Verify if method/constructor arguments in declaration are either a single line or they are broken up into multiple lines, 
each on an individual line.

#### Configuration

```xml
<module name="MethodParameterLines"/>
```

##### Parameters

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

Since 1.2.0

When parameters in method/constructor call are on multiple lines, verify if these lines are aligned.

#### Configuration

```xml
<module name="MethodCallParameterAlignment"/>
```

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

### MethodCallParameterLinesCheck

Since 1.2.0

Verify if call method/constructor arguments are either a single line or they are broken up into multiple lines,
each on an individual line.

#### Configuration

```xml
<module name="MethodCallParameterLines"/>
```

```xml
<module name="MethodCallParameterLines">
  <property name="ignoreMethods" value="Map.of"/>
</module>
```

##### Parameters

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

### NameLengthCheck

Since 1.3.0

Verify length of identifiers - method name, class name, package name, var name etc.

#### Configuration

```xml
<module name="NameLength"/>
```

##### Parameters

| parameter name         | type | default value | description                                            |
|------------------------|------|---------------|--------------------------------------------------------|
| maxPackageNameLength   | int  | 20            | Define max length ot package segment name.             |
| maxClassNameLength     | int  | 50            | Define max length ot class name.                       |
| maxInterfaceNameLength | int  | 50            | Define max length ot interface name.                   |
| maxRecordNameLength    | int  | 50            | Define max length ot record class name.                |
| maxEnumClassNameLength | int  | 50            | Define max length ot enum class name.                  |
| maxEnumConstNameLength | int  | 50            | Define max length ot enum const name.                  |
| maxMethodNameLength    | int  | 50            | Define max length ot method name.                      |
| maxParameterNameLength | int  | 30            | Define max length ot parameter name.                   |
| maxVariableNameLength  | int  | 30            | Define max length ot variable and class property name. |

#### Violations

To configure the check:

```xml
<module name="NameLength" />
```

Which results in the following violations:
```java
class MongoDbOrderViolationRepository { // valid class name - length = 31
  public List<OrderViolation> findByUnit(Unit unit) { // valid method name - length = 10
    ...
  }
}
```

```java
class SpringAwareCachedMongoDbOrderViolationRepositoryImpl { // non valid name - length = 51
  public List<OrderViolation> findByReportDateAndByBusinessUnitAndIsActiveAndNumberOfActionsGratedThenAndEnv(...) { // non valid method name - length = 78
  
  }
}
```

To configure with custom max length:

```xml
<module name="NameLength">
  <property name="maxClassNameLength" value="40"/>
  <property name="maxVariableNameLength" value="20"/>
  ...
</module>
```

## Configuration

### Maven dependency

```xml
<dependency>
    <groupId>pl.tfij</groupId>
    <artifactId>check-tfij-style</artifactId>
    <version>1.3.0</version>
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
    <module name="FieldsCountCheck"/>
    <module name="MethodEmptyLines"/>
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
            <version>1.5.1</version>
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
    checkstyle("pl.tfij:check-tfij-style:1.5.1")
}
```

## Why external lib

The checkstyle is a powerful library that has many users.
The success of this library meant that any changes require time-consuming analyzes and discussions, e.g. whether the change is backward compatible, whether is it consistent with all other checks, how to eventually deprecate this check, etc.
An example is the [https://github.com/checkstyle/checkstyle/issues/9118](https://github.com/checkstyle/checkstyle/issues/9118) issue where the proposal and PoC was presented two years ago and there is still no decision about future steps.
For this reason, I decided to release my own checks as a separate library that can be used together with the core checkstyle.
