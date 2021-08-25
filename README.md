# Resulter

Tired of throwing exceptions or returning nonsense? Well, Resulter will save you! 

## Resultable
Resulter provides simple to use Results. Why? Well, there are few benefits.
- Fewer exceptions and/or returning nonsense messages
- Clear understanding of what went wrong with **message()** and **error()** methods
- gRPC implementation
- Project Reactor support


#### Example
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
    final Resultable result = isAccessAllowed(userId);
    if (result.isFail()) {
        return result;
    }
    
    return SimpleResult.ok();
}
```