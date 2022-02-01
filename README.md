# Resulter

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.iamceph.resulter/resulter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.iamceph.resulter/resulter)
[![javadoc](https://javadoc.io/badge2/com.iamceph.resulter/resulter/javadoc.svg)](https://javadoc.io/doc/com.iamceph.resulter/resulter)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7bcf72e06caa4df8abab10b8ff2e2a87)](https://www.codacy.com/gh/iamceph/resulter/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iamceph/resulter&amp;utm_campaign=Badge_Grade)

Tired of throwing exceptions or returning nonsense? Well, Resulter will save you!

## Resultable

Resulter provides simple to use Results. Why? Well, there are few benefits.

- Fewer exceptions or returning nonsense messages.
- Clear understanding of what went wrong with **message()** and **error()** methods.
- gRPC implementation.
- Project Reactor support.

### Dependency

The dependency for Resulter is available
on [Maven Central](https://search.maven.org/artifact/com.iamceph.resulter/resulter).

- Gradle

```groovy
implementation 'com.iamceph.resulter:resulter-core:1.1.4'
```

- Maven

```xml
<dependency>
    <groupId>com.iamceph.resulter</groupId>
    <artifactId>resulter-core</artifactId>
    <version>1.1.4</version>
</dependency>
```

### Example

```java
//this operation ended normally
public class Test {
    public Resultable doSomething() {
        Resultable okResult = Resultable.ok();

        //this operation failed because it cannot find given user.
        Resultable failedResult = Resultable.fail("Cannot find user!");
    }
}
```

#### Example usage

```java
public class UserService {
    public Resultable isAccessAllowed(Integer userId) {
        if (userId == 1) {
            return Resultable.ok();
        }

        return Resultable.fail("User is not allowed to access.");
    }

    public DataResultable<User> anotherCheck(Integer userId) {
        //let's get if the user can enter
        final Resultable result = isAccessAllowed(userId);
        if (result.isFail()) {
            return result.transform();
        }

        //this will fail if the user does not exist in the repo, neat, right? :)
        return DataResultable.failIfNull(userRepository.findUser(userId));
    }
}
```
