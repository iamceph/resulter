# Resulter
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.iamceph.resulter/resulter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.iamceph.resulter/resulter)
[![javadoc](https://javadoc.io/badge2/com.iamceph.resulter/resulter/javadoc.svg)](https://javadoc.io/doc/com.iamceph.resulter/resulter)

Tired of throwing exceptions or returning nonsense? Well, Resulter will save you! 

## Resultable
Resulter provides simple to use Results. Why? Well, there are few benefits.
  - Fewer exceptions or returning nonsense messages.
  - Clear understanding of what went wrong with **message()** and **error()** methods.
  - gRPC implementation.
  - Project Reactor support.
  
### Example
```java
//this operation ended normally
Resultable okResult = SimpleResult.ok();

//this operation failed because it cannot find given user.
Resultable failedResult = SimpleResult.fail("Cannot find user!");
```

#### Example usage
```java

public Resultable isAccessAllowed(Integer userId) {
    if (userId == 1) {
        return SimpleResult.ok();
        }
    
    return SimpleResult.fail("User is not allowed to access.");
}

public DataResultable<User> anotherCheck(Integer userId) {
    //lets get if the user can enter
    final Resultable result = isAccessAllowed(userId);
    if (result.isFail()) {
        return result;
    }
    
    //this will fail if the user does not exist in the repo, neat, right? :)
    return DataResult.failIfNull(userRepository.findUser(userId));
}
```
