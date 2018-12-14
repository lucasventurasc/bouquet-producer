To run application tests:

./gradlew test
To run mutation tests:

./gradlew pitest
The index.html will be generated at $project_direcotry/build/reports/pitest/index.html

After run mutation tests if you want to open the report through command line type: *(must have firefox installed)

./gradlew piport
Gradle piport is equivalent as open the $project_direcotry/build/reports/pitest/index.html manually

How to run the application from the code
*For follow you have to set the JAVA_HOME environment variable with java 8 or superior.

Clone the repository and within project folder root, type

For windows
Generate application jar typing on terminal:

gradlew.bat jar
For linux
Generate application jar typing on terminal:

./gradlew jar
Running jar
With that command a jar will be created at $project_directory/build/libs/bouquet-producer-0.1.jar

Within project folder, type on terminal:

java -jar build\libs\bouquet-producer-0.1.jar

How to run with Docker
On project directory type
```bash
docker build . -t bouquet-producer
```

To run

```bash
docker run -it bouquet-producer
```

Copy and Paste your stream to the terminal and the program should run.