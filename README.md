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
                    <version>1.1</version>
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
    package test;
    
    import java.util.Iterator;
    
    public interface CharIterator extends Iterator<Character> {
    
        char nextChar();
    }
    ```
    
3. Run `mvn compile`

4. [For IntelliJ users] Right click on `pom.xml` in project explorer &rarr; Maven &rarr; Reimport

5. Open `target/generated-sources/jpsg/test` directory in project explorer and enjoy
`ByteIterator`..`DoubleIterator` specializations!

See [the JPSG repository](
https://github.com/TimeAndSpaceIO/java-primitive-specializations-generator) for more info about how
to use the generator.

### Requirements

Maven 3.0 or newer, OpenJDK 1.7 or newer as the Maven's runtime
