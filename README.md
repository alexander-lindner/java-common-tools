# java-common-tools

A java tool chain for various little helper and tools

## Example of the random stream tool

```java
int keyLength = 50;
int count = 1000000;
final List<String> keys = Random.build(Generator.alphanumeric())
                                .parallelUniqueStream(keyLength, count)
                                .filter(s -> s.startsWith("a"))
                                .collect(Collectors.toList());
// *keys* contains ~1/62 *count* strings like aYC8KQhxrJgrnTau08TbWoha7dswQTrTQ1fI3TZLBVEKrFUTs2
``` 
# Install it

https://mvnrepository.com/artifact/org.alindner.tools/common/1.1.0

Use it via Maven
```xml
<dependency>
    <groupId>org.alindner.tools</groupId>
    <artifactId>common</artifactId>
    <version>1.1.0</version>
</dependency>
```
or Gradle
```groovy
compile "org.alindner.tools:common:1.1.0"
```

# Use it

See the tutorials on our [wiki](https://github.com/alexander-lindner/java-common-tools/wiki).