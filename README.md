## A Maven plugin for [Java Primitive Specializations Generator](https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator)

### Quick start

1. Add to your `pom.xml`:
    ```xml
        <build>
            <plugins>
                <!-- other plugins... -->
                
                <plugin>
                    <groupId>io.timeandspace</groupId>
                    <artifactId>jpsg-maven-plugin</artifactId>
                    <version>1.2</version>
                    <executions>
                        <execution>
                            <id>jpsg</id>
                            <goals>
                                <goal>jpsg</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    ```
    
2. Create file `CharIterator.java` in `src/main/javaTemplates/test` directory:
    ```java
    package mypackage;
    
    import java.util.Iterator;
    
    public interface CharIterator extends Iterator<Character> {
    
        char nextChar();
    }
    ```
    
3. Run `mvn compile`

4. [For IntelliJ users] Right click on `pom.xml` in project explorer &rarr; Maven &rarr; Reimport

5. Open `target/generated-sources/jpsg/mypackage` directory in project explorer and enjoy
`ByteIterator`..`DoubleIterator` specializations!

See [the JPSG repository](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator) for more info about how
to use the generator.

### Configuration

##### `defaultTypes`
For all template files without `/* with */` blocks in the beginning JPSG attempts to deduce
dimensions from the name of the template file, taking possible options from this `defaultTypes`
configuration. See [more details](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#with-default-types) in
the section about `/* with */` blocks in JPSG tutorial. 

Type: `String`. <br>
Format: [`<javaTypeOptions>`](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#dimensions-bnf). <br>
Default value: `byte|char|short|int|long|float|double`.

Example:
```xml
<configuration>
  <defaultTypes>int|long|float|double</defaultTypes>
</configuration>
```

##### `sourceDirectory`
The source directory which JPSG traverses and considers all files in it as template files.

Type: `File`. <br>
Default value: `${basedir}/src/main/javaTemplates`.

You can generate test sources by using multiple plugin executions and `build-helper-maven-plugin`,
see an example in [`jpsg-maven-plugin-test/pom.xml`](jpsg-maven-plugin-test/pom.xml).

##### `targetDirectory`
The target directory where JPSG puts specialized sources.

Type: `File`. <br>
Default value: `${project.build.directory}/generated-sources/jpsg`.

[`jpsg-maven-plugin-test/pom.xml`](jpsg-maven-plugin-test/pom.xml) includes an example of setting a
different target directory for a JPSG execution.

##### `never`
For all dimensions defined in the beginnings of template files in `/* with */` blocks, or deduced
automatically by JPSG (see [`defaultTypes`](#defaulttypes) configuration above), or defined inside
template files at any level of nesting, JPSG will skip generating code for the specified options.

Type: `List<String>`. <br>
Format: [`<options>`](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#dimensions-bnf). <br>
Default value: none (empty list).

Examples:
```xml
<configuration>
  <never>
    <never>byte|short|char</never>
    <never>Assert</never>
  </never>
</configuration>
```
Alternative:
```xml
<configuration>
  <never>byte|short|char|Assert</never>
</configuration>
```

##### `excludes`
JPSG doesn't generate specialization files for dimension contexts (either determined by
[`/* with */` blocks](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#-with--blocks) in the
beginnings of the template files, or deduced using `defaultTypes`) that match any of the conditions
configured via `excludes`. See [more details](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#excludestring-conditions)
in the description of the equivalent method `JpsgTask.exclude()` for the JPSG Gradle plugin. 

Type: `List<String>`. <br>
Format: [`<simple-condition>`](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#conditions-bnf). <br>
Default value: none (empty list).

Example:
```xml
<configuration>
  <excludes>
    <exclude>object key byte|short|char|object value</exclude>
    <exclude>byte key short|char value</exclude>
    <exclude>Disabled extraChecks Enabled advancedStatistics</exclude>
  </excludes>
</configuration>
```

##### `with`
JPSG adds the provided dimensions to the generation contexts in each template file. Each dimension
must have a single option. See [more details](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#withstring-dimensions) in
the description of the equivalent method `JpsgTask.with()` for the JPSG Gradle plugin.

Type: `List<String>`. <br>
Format: [`<dimensions>`](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator#dimensions-bnf). Every
dimension must have only a single option. <br>
Default value: none (empty list).

Examples:
```xml
<configuration>
  <with>
    <with>Enabled extraChecks Disabled advancedStatistics</with>
    <with>Assert extraCheckStyle</with>
    <with>java8 minSupportedJavaVersion</with>
  </with>
</configuration>
```
Alternative:
```xml
<configuration>
  <with>Enabled extraChecks Disabled advancedStatistics Assert extraCheckStyle java8 minSupportedJavaVersion</with>
</configuration>
```

### Requirements

Maven 3.0 or newer, OpenJDK 1.7 or newer as the Maven's runtime
