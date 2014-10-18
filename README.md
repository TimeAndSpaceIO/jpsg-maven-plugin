## A Maven plugin for [JPSG](https://github.com/OpenHFT/Koloboke/tree/master/jpsg)

### Quick start

1. Add to you `pom.xml`:
    ```xml
        <build>
            <plugins>
                <!-- other plugins... -->
                
                <plugin>
                    <groupId>net.openhft</groupId>
                    <artifactId>jpsg-maven-plugin</artifactId>
                    <version>1.0</version>
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
