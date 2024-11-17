# Java Execution Tools

## Overview
`Java Execution Tools` is a Java project that provides utilities for executing commands and managing Java processes dynamically. It includes classes for executing system commands, managing Java jobs, and invoking methods at runtime using Java Reflection. This project is useful for developers who need to automate Java-based tasks, execute jobs programmatically, and interact with Java code in a dynamic manner.

## Project Components
The project consists of the following components:

### 1. CommandExecuter
The `CommandExecuter` class is responsible for executing system commands, specifically designed to execute Java processes or other command-line tasks. It provides utility methods to execute commands and start Java-based jobs based on given configurations.

### 2. JavaReflectionRunner
`JavaReflectionRunner` enables executing methods of a Java class at runtime using Java Reflection. It is useful for dynamically loading JAR files and invoking specific methods, such as executing a process without directly referencing its classes at compile time.

### 3. JavaJobConfiguration
The `JavaJobConfiguration` class defines a data structure for configuring Java jobs. It includes parameters such as the job name, JAR path, JVM path, starting class, and arguments, making it easy to configure and manage Java processes.

## Features
- **System Command Execution**: Execute any system command or Java-based command using `CommandExecuter`.
- **Java Job Execution**: Start Java jobs programmatically with configurable parameters.
- **Java Reflection**: Use Java Reflection to dynamically load classes, instantiate objects, and invoke methods at runtime.

## Installation
To use the `Java Execution Tools`, include the source files in your Java project. The classes do not have any external dependencies other than the standard Java libraries.

## Usage
Here are some examples of how you can use the `Java Execution Tools`:

### Execute a System Command
```java
CommandExecuter.connect("echo Hello, World!");
```
This code snippet executes a simple command to print "Hello, World!" to the console using `CommandExecuter`.

### Start a Java Job
```java
Map<String, String> jobConfigMap = new HashMap<>();
jobConfigMap.put(JavaJobConfiguration.PARAMETER_JVM_PATH, "path/to/java");
jobConfigMap.put(JavaJobConfiguration.PARAMETER_JAR_PATH, "path/to/your.jar");
jobConfigMap.put(JavaJobConfiguration.PARAMETER_START_CLASS, "com.example.Main");
jobConfigMap.put(JavaJobConfiguration.PARAMETER_START_DIR, "path/to/start/dir");
jobConfigMap.put(JavaJobConfiguration.PARAMETER_VM_ARGUMENTS, "-Xmx512m");
jobConfigMap.put(JavaJobConfiguration.PARAMETER_RUNNING_ARGUMENTS, "--arg1 value1");

try {
    CommandExecuter.startJobName(jobConfigMap);
} catch (IOException | InterruptedException e) {
    e.printStackTrace();
}
```
This snippet starts a Java job by executing a Java process using the provided configuration map.

### Use Java Reflection to Invoke a Method
```java
JavaReflectionRunner.main(new String[]{});
```
The `JavaReflectionRunner` class loads a specified JAR file and invokes a method (`startPaketStatistikUpdate`) from the loaded class, allowing you to interact with external Java components dynamically.

## Classes Summary
1. **`CommandExecuter`**: Handles execution of system commands and Java job processes.
    - **Methods**: `connect(String command)`, `startJobName(Map<String, String> jobConfigMap)`, `getOutputStringFromOutputStream(InputStream inputStream)`

2. **`JavaReflectionRunner`**: Loads a JAR file and invokes specified methods using Java Reflection.
    - **Methods**: `main(String[] args)`

3. **`JavaJobConfiguration`**: Represents the configuration for Java jobs, including paths and arguments.
    - **Properties**: `jobName`, `startDir`, `jarPath`, `jvmPath`, `vmArguments`

## Contributing
Feel free to contribute to the `Java Execution Tools` project by forking the repository, making changes, and submitting a pull request. Contributions for bug fixes, new features, or optimizations are always welcome.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

