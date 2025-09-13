# STYLES OF EXPRESSIONS
## LAMBDA EXPRESSIONS
There are basically the writing styles of regular java functions.  
They are used to concisely represent a single method interface using an expression. The three styles are:

regular java function:

```java
int add(int x, int y) {
    return x+y;
}
```

1. Block Body (shortcut level 1): This style uses curly braces to define a block of code. It can contain multiple
   statements and requires
   an explicit return statement if a value is returned.
   Example:
   ```java
   (int x, int y) -> {
       return x+y;
   }
   ```
2. Expression Body (shortcut level 2): This style is more concise and is used when the lambda expression consists of a single expression.
   The result of the expression is automatically returned.
   Example:
   ```java
   (int x, int y) -> x + y, OR
   (x, y) -> x + y
   ```
   
3. No Parameter: This style is used when the lambda expression does not take any parameters. It is often used for
   functional interfaces that do not require input.
   Example:
   ```java
   () -> System.out.println("Hello, World!")
   ```

## FUNCTIONAL INTERFACES
Functional interfaces are interfaces that contain a single abstract method. They can have multiple default or static methods, but only one abstract method. 
So, we must implement that single abstract method and use it. Some common (main) functional interfaces in Java include:
- `Predicate<I>`: Represents a function which accepts single input `<I>` and returns a Boolean using the `test()` method.
  ```java
  # inline
  t -> { return t.getAge() > 60; }, OR
  t -> t.getAge() > 60, OR
  Employee::isRetired
  
  # method reference
  Predicate<Employee> isRetired = Employee::isRetired;
  Boolean isRetired = isRetired.test(new Employee("name", 61)); // returns true
  ```
- `Function<I, O>`: Represents a function that accepts single input `<I>` and returns output `<O>` using `apply()` method.
  ```java
  # inline
  t -> { return t.getName(); }, OR
  t -> t.getName(), OR
  Employee::getName
  
  # method reference
  Function<Employee, String> getName = Employee::getName;
  String employeeName = getName.apply(new Employee("name", 61)); // returns "name"
  ```
- `Consumer<I>`: Represents an operation that accepts a single input `<I>` and returns no result and runs `accept()` method.
  ```java
  # inline
  t -> { kafkaUtils.publish(t); }, OR
  t -> kafkaUtils.publish(t), OR
  kafkaUtils::publish
  
  # method reference
  Consumer<Employee> publishEmployee = kafkaUtils::publish;
  publishEmployee.accept(new Employee("name", 61)); // publishes Employee to Kafka
  ```
  
- `Supplier<O>`: Represents a supplier that returns output `<O>` using `get()` method.
  ```java
  # inline
  () -> { return new Employee(); }, OR
  () -> new Employee(), OR
  Employee::new
  
  # method reference
  Supplier<Employee> employeeSupplier = Employee::new; 
  employeeSupplier.get() // returns Employee, OR
  
  Supplier<List<Employee>> employeeListSupplier = List<Employee>.of(new Employee("name1"), new Employee("name2"));
  employeeListSupplier.get() // returns List<Employee>
  ```


# IMPLEMENTATIONS
## STREAMS OPERATIONS
Streams operations can be broadly classified into two categories: 
- Intermediate
  These operations return a new stream and are lazy in nature. This is the difference between usual collections and streams.
  They do not modify the original stream but instead create a new stream that can be further processed. 
  Examples includes `filter()`, `map()` etc.

- Terminal operations
  These operations produce a result or a side effect and mark the end of the stream processing. 
  They do not return a new stream but instead return a concrete value or perform an action. 
  Examples includes `collect()`, `forEach()`, `reduce()` etc.


