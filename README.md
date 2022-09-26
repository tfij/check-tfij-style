# check-tfij-style
A set of additional checks to use with [checkstyle](https://checkstyle.sourceforge.io/index.html).

## Checks

### Method Parameter Alignment Check

When parameters are on multiple lines, verify if these lines are aligned.

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

### Method Parameter Lines Check

Verify if method arguments are either a single line or they are broken up into multiple lines, each on an individual line.

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

## Configuration

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

### Example maven-checkstyle-plugin configuration

IMPORTANT: To use this check with `maven-checkstyle-plugin`,
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
            <version>1.0</version>
        </dependency>
    </dependencies>
</plugin>
```
