test:
	@mvn test

run:
	@mvn compile && mvn package && java -jar target/Turtle-Interpreter-0.1.0.jar

upgrade:
	@mvn release:update-versions
	@mvn versions:use-latest-releases
